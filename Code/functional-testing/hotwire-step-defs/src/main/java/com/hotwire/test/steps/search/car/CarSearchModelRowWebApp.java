/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car;

import com.hotwire.selenium.desktop.row.GlobalNavigationFragment;
import com.hotwire.selenium.desktop.row.billing.CarDetailsPage;
import com.hotwire.selenium.desktop.row.models.CarDataVerificationParameters;
import com.hotwire.selenium.desktop.row.models.CarIntlSolutionModel;
import com.hotwire.selenium.desktop.row.results.CarResultsPage;
import com.hotwire.selenium.desktop.row.search.CarLandingPage;
import com.hotwire.selenium.desktop.row.search.CarSearchFragment;
import com.hotwire.test.steps.search.SearchParameters;
import hotwire.util.DateUtils;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author v-mzabuga
 * @author v-ypuchkova
 * @since 7/23/12
 */
public class CarSearchModelRowWebApp extends CarSearchModelTemplate {

    @Autowired
    @Qualifier("carDataVerificationParameters")
    private CarDataVerificationParameters carDataVerificationParameters;

    @Override
    public void findFare(String selectionCriteria) {
        CarLandingPage carLandingPage = getGlobalNavigationFragment().navigateToCarLandingPage();
        CarSearchFragment fareFinderOnCLP = carLandingPage.getCarSearchFragment();
        fillSearchParameters(fareFinderOnCLP)
            .driversAge(searchParameters.getDriversAge())
            .country(searchParameters.getCountryOfResidence());

        if (carDataVerificationParameters.isShouldSaveDataForVerification()) {
            try {
                verificationDataOnFareFinder(fareFinderOnCLP);
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            fareFinderOnCLP.clickSearchButton();
        }
    }

    @Override
    public void findFareOnResultsPage() {
        CarSearchFragment fareFinderOnResults =
            new CarSearchFragment(getWebdriverInstance(), By.className("carResultsFareFinder"));
        fillSearchParameters(fareFinderOnResults).clickSearchButton();
    }

    private CarSearchFragment fillSearchParameters(CarSearchFragment fareFinder) {
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        fareFinder.dropOffLocation(searchParameters.getDropOffLocation()).
            startTime(searchParameters.getPickupTime()).
                      endTime(searchParameters.getDropoffTime()).
                      pickUpLocation(globalSearchParameters.getDestinationLocation()).
                      startDate(globalSearchParameters.getStartDate()).
                      endDate(globalSearchParameters.getEndDate());
        return fareFinder;
    }

    private void verificationDataOnFareFinder(CarSearchFragment fareFinderOnCLP) throws ParseException {
        //verify default value for driver's age parameter
        assertThat(fareFinderOnCLP.getDriversAge().equals(CarSearchFragment.DEFAULT_VALUE_FOR_DRIVERS_AGE));

        CarIntlSolutionModel solutionSetOnCLP = fareFinderOnCLP.getSolutionOptionsSet();
        setSolutionOptionsSetParameters(solutionSetOnCLP, fareFinderOnCLP);
        fareFinderOnCLP.clickSearchButton();
        CarSearchFragment fareFinderOnResults =
            new CarSearchFragment(getWebdriverInstance(), By.className("carResultsFareFinder"));
        CarIntlSolutionModel solutionSetOnResults = fareFinderOnResults.getSolutionOptionsSet();
        setSolutionOptionsSetParameters(solutionSetOnResults, fareFinderOnResults);
        carDataVerificationParameters.setOptionSetForSearch(solutionSetOnCLP);
        assertThat(solutionSetOnResults.match(solutionSetOnCLP));
    }

    private void setSolutionOptionsSetParameters(CarIntlSolutionModel solutionSet, CarSearchFragment fareFinder)
        throws ParseException {
        Date startDateWithoutTime =
            new SimpleDateFormat(fareFinder.getFormatForDate()).parse(fareFinder.getStartDate());
        solutionSet.setPickUpDate(DateUtils.makeDate(startDateWithoutTime, fareFinder.getStartTime()));

        Date endDateWithoutTime = new SimpleDateFormat(fareFinder.getFormatForDate()).parse(fareFinder.getEndDate());
        solutionSet.setDropOffDate(DateUtils.makeDate(endDateWithoutTime, fareFinder.getEndTime()));
    }

    @Override
    public void navigateToSearchPage() {
        getGlobalNavigationFragment().navigateToCarLandingPage();
    }

    @Override
    public void navigateToResultsPage() {
        new CarResultsPage(getWebdriverInstance());
    }

    @Override
    public void navigateToDetailsPage() {
        new CarDetailsPage(getWebdriverInstance());
    }

    private GlobalNavigationFragment getGlobalNavigationFragment() {
        return new GlobalNavigationFragment(getWebdriverInstance());
    }

    @Override
    public void clickOnTotalInclusiveRatePopup() {
        new CarResultsPage(getWebdriverInstance()).clickOnTotalInclusiveRatePopup();
    }

    @Override
    public void verifyCarLandingPage(Boolean carTrawlerAvailable) {
        GlobalNavigationFragment globalNavigationFragment = new GlobalNavigationFragment(getWebdriverInstance());
        String url = globalNavigationFragment.getCarLandingPageUrl();
        if (carTrawlerAvailable) {
            assertThat(url).contains("cars.hotwire.com");
        }
        else {
            assertThat(url).doesNotContain("cars.hotwire.com");
        }
    }

    @Override
    public void verifyResultsPage() {
        CarResultsPage resultsPage = new CarResultsPage(getWebdriverInstance());

        assertThat(resultsPage.verifyIsLocalResults())
            .as("Expected at least one car result on page, but nothing.")
            .isTrue();
    }
}

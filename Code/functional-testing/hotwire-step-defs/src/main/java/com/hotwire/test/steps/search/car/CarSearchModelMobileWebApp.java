/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.car;


import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.results.car.MobileCarMultipleLocationsPage;
import com.hotwire.selenium.mobile.results.car.MobileCarResultsPage;
import com.hotwire.selenium.mobile.search.CarSearchFragment;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.util.webdriver.functions.PageName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.hotwire.selenium.mobile.results.car.MobileCarResultsPageProvider.getMobileCarResultsPage;
import static com.hotwire.selenium.mobile.results.car.MobileCarResultsPageProvider.isLocalCarResults;
import static com.hotwire.selenium.mobile.results.car.MobileCarResultsPageProvider.isResultListEmpty;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author LRyzhikov
 * @since 5/30/12
 */
public class CarSearchModelMobileWebApp extends CarSearchModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarSearchModelMobileWebApp.class.getName());

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParameters carSearchParameters;

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel applicationModel;

    @Override
    public void findFare(String selectionCriteria) {
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        CarSearchFragment fareFinder = new MobileHotwireHomePage(getWebdriverInstance()).findCarRental();

        fareFinder
            .withPickUpTime(searchParameters.getPickupTime())
            .withDropOffTime(searchParameters.getDropoffTime())
            .withDestinationLocation(globalSearchParameters.getDestinationLocation())
            .withStartDate(globalSearchParameters.getStartDate())
            .withEndDate(globalSearchParameters.getEndDate()).findFare();

        logSession("Requesting quotes");

        // Workaround for nonDev environments. Dev is on Google geo adapter, but everyone else is using VirtualEarth.
        if (new PageName().apply(getWebdriverInstance()).contains("multiple.locations")) {
//            throw new PendingException(
//                "Got disambiguation list. hwVirtualEarthGeoAdapter sucks balls. PendingException thrown because " +
//                "getting to this list is either from an address or POI location search attempt and choosing from " +
//                "this list we can't guarantee expected city results.");
            LOGGER.info("Got disambiguation list. Getting to this list is either" +
                        " from an address or POI location search attempt and choosing from " +
                        "this list we can't guarantee expected city results.");
        }
    }

    @Override
    public void navigateToSearchPage() {
        MobileHotwireHomePage mHomePage = new MobileHotwireHomePage(getWebdriverInstance());
        mHomePage.navigateToCarSearch();
    }

    //Todo extract logic to page object
    @Override
    public void navigateToResultsPage() {
        List<WebElement> elements =
            getWebdriverInstance().findElements(By.xpath("//*[text()[contains(.,'Car rental results')]]"));
        elements.get(0).click();
    }

    @Override
    public void verifyResultsPage(String carSearchType) {

        if ("airport".equalsIgnoreCase(carSearchType)) {
            assertThat(isLocalCarResults(getWebdriverInstance())).as(
                "Expected to see airport results page. Found one local car results on the page")
                                                                 .isFalse();

        }
        else if ("local".equalsIgnoreCase(carSearchType)) {
            assertThat(isLocalCarResults(getWebdriverInstance())).as(
                "Expected to see local results page." +
                " Found no local car results on the page and found to be on the airport page")
                                                                 .isTrue();
        }
        else {
            throw new RuntimeException("Error! Unsupported car search type. Expecting airport or local. Found " +
                                       carSearchType);
        }
    }

    @Override
    public void verifyResultsPage() {

        assertThat(isResultListEmpty(getWebdriverInstance())).as(
                    "Result list is empty")
                .isFalse();
    }

    @Override
    public void verifyMisspelledCityHandling(String expectedCity) {
        MobileCarMultipleLocationsPage multipleLocationsPage =
            new MobileCarMultipleLocationsPage(getWebdriverInstance());
        List<String> suggestedLocations = multipleLocationsPage.getSuggestedLocations();
        assertThat(suggestedLocations.toString())
            .as("Suggested location contains expected city: " + expectedCity)
            .contains(expectedCity);
    }

    @Override
    public void verifyFareFinderOnResults() {
        DateFormat formatterDate = new SimpleDateFormat("EEE,MMMdd,");
        MobileCarResultsPage resultsPage = getMobileCarResultsPage(getWebdriverInstance());
        String dateAndTimeFromPage = resultsPage.getSearchDatesAndTime().replaceAll("\\s+", "");
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();

        String startDate = formatterDate.format(globalSearchParameters.getStartDate());
        String endDate = formatterDate.format(globalSearchParameters.getEndDate());

        String startTime = CarSearchFragment.TIME.get(carSearchParameters.getPickupTime());
        String endTime = CarSearchFragment.TIME.get(carSearchParameters.getDropoffTime());

        assertThat(dateAndTimeFromPage)
            .as("Search dates on result page are the same as on home page")
            .isEqualToIgnoringCase(startDate + startTime + "-" + endDate + endTime);
    }
}

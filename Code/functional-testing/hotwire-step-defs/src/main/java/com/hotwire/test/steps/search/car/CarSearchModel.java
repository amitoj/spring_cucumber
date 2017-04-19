/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car;

import com.hotwire.selenium.desktop.us.results.car.fragments.depositFilter.CarDepositFilter;
import com.hotwire.test.steps.search.SearchModel;

import java.util.Date;

/**
 * @author v-mzabuga
 * @since 7/17/12
 */
public interface CarSearchModel extends SearchModel {
    /**
     * Take the user to the search page so that they can enter their search criteria
     */
    void navigateToSearchPage();

    void navigateToResultsPage();

    void verifyCarResultsNotFromCache();

    void navigatetoSupplierLandingPage(String suppliername);

    void confirmSupplierPage(String suppliername);

    /**
     * Verify that the user is on the car results page based on the car search type (airport or local)
     */
    void verifyResultsPage(String carSearchType);

    void verifyRentalDaysOnBillingPage(Integer dayCount);

    void verifyRentalCarProtectionCostOnBillingPage(Integer protectionCost);

    void verifyCarLandingPage(Boolean carTrawlerAvailable);

    void findFareOnResultsPage();

    void navigateToDetailsPage();

    void clickOnTotalInclusiveRatePopup();

    void verifyTripWatcherInResults(boolean condition);

    void verifyTripWatcherEmailField(boolean state, String emailId);

    void verifyTripWatcherCopy(String state);

    void watchTrip(boolean state, String emailId);

    void verifyEmailFieldErrorValidation();

    /**
     * Checks that deposit filter is accessible
     */
    boolean isDepositFilterPresent();

    /**
     * Selection of deposit card type on car results page
     */
    void selectDepositOptionByType(CarDepositFilter.DepositTypes cardType);

    /**
     * Check that deposit type is checked correctly.
     */
    void verifyDepositTypeSelection(CarDepositFilter.DepositTypes cardType);

    /**
     * verify order of car's search results
     */
    void verifyThatCheapestSolutionIsFirst();

    /**
     * verify that car results page contain only opaque or retails solutions
     * @param typeOfSolution - opaque or retail
     */
    void verifyOnlyRetailOrOpaqueResultsPage(String typeOfSolution);

    /**
     * Expand or collapse explanation for debit cards.
     * @param action
     */
    void openDepositFilterExplanationPopup(boolean action);

    /**
     * @return true if deposit explanation is open and false otherwise
     */
    boolean isDebitDepositExplanationOpen();

    /**
     * Get debit card explanation
     */
    String getDebitCardExplanation();

    /**
     * verify that user is redirected to that solution we are expecting it should be.
     */

    void verifyUserIsRedirectedToTheLowestPriceSolution();

    void verifyFareFinderOnResults();

    void verifyTimesInFareFinder(String typeOfTime);

    void verifyDefaultDatesInFareFinder();

    void clickSearch();

    void clickUpdateButton();

    void setDates(Date startDate, Date endDate);

    void clickOnTheCarStartTime();

    void verifyIfNearbyAirportModuleIsNotPresent();

    void verifyIfNearbyAirportModuleIsPresent();

    void verifyCarSearchDataInDB();

    void saveSearchIdForCarResults();

    void verifyStatusCodeFromDB(String expectedCode);

    void verifyCheapestRetailCarPriceWithDB();

    void compareSearchIdForCarResults();

    void compareTaxesAndFeesOnDetailsWithDB();

    void compareDailyRateOnDetailsWithDB();

    void verifyStatusCodeCountInDB(Integer count);

    void verifyCommissionsForRetailCar();

    void verifyMisspelledCityHandling(String expectedCity);

    void verifyCarResultsPageDidntChange();

    void verifyDepositFilterPopupExplanation(String text);

    void verifyDepositFilterText(String text);

    void makeCarSearchFromResultPage();

    void clickOnSearchButton();

    void verifyIntentMediaExistingElements();

    void validateMeSoBanners();
}




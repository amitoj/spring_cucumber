/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car;

import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.index.CarIndexPage;
import com.hotwire.selenium.desktop.us.results.car.fragments.depositFilter.CarDepositFilter;
import com.hotwire.selenium.desktop.us.search.SearchPage;
import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.testing.UnimplementedTestException;

import java.util.Date;

/**
 * User: v-vzyryanov
 * Date: 12/5/12
 */
public class CarSearchModelTemplate extends SearchModelTemplate<CarSearchParameters> implements CarSearchModel {

    public SearchPage delegateSearchPage() {
        if (searchParameters.getGlobalSearchParameters().isLandingPage()) {
            return new CarIndexPage(getWebdriverInstance());
        }
        return new HomePage(getWebdriverInstance());
    }

    @Override
    public void navigateToSearchPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void navigateToResultsPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void navigatetoSupplierLandingPage(String suppliername) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void confirmSupplierPage(String suppliername) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyLocationOnResultsPage(String location) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyResultsPage(String carSearchType) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyRentalDaysOnBillingPage(Integer dayCount) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyRentalCarProtectionCostOnBillingPage(Integer protectionCost) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCarLandingPage(Boolean carTrawlerAvailable) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void findFareOnResultsPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void navigateToDetailsPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickOnTotalInclusiveRatePopup() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public boolean isDepositFilterPresent() {
        throw new UnimplementedTestException("Not implemented yet");
    }


    @Override
    public void selectDepositOptionByType(CarDepositFilter.DepositTypes cardType) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyDepositTypeSelection(CarDepositFilter.DepositTypes cardType) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyThatCheapestSolutionIsFirst() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyOnlyRetailOrOpaqueResultsPage(String typeOfSolution) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void openDepositFilterExplanationPopup(boolean action) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public boolean isDebitDepositExplanationOpen() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public String getDebitCardExplanation() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyTripWatcherInResults(boolean condition) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyTripWatcherEmailField(boolean state, String emailId) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyTripWatcherCopy(String state) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void watchTrip(boolean state, String emailId) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    public void verifyEmailFieldErrorValidation() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    public void verifyUserIsRedirectedToTheLowestPriceSolution() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCheapestRetailCarPriceWithDB() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    public void verifyFareFinderOnResults() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyTimesInFareFinder(String typeOfTime) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    public void verifyDefaultDatesInFareFinder() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickSearch() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickUpdateButton() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void setDates(Date startDate, Date endDate) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickOnTheCarStartTime() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyIfNearbyAirportModuleIsNotPresent() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyIfNearbyAirportModuleIsPresent() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCarSearchDataInDB() {
        throw new UnimplementedTestException("Not implemented yet");
    }
    @Override
    public void saveSearchIdForCarResults() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyStatusCodeFromDB(String expectedCode) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public  void compareSearchIdForCarResults() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public  void compareTaxesAndFeesOnDetailsWithDB() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void compareDailyRateOnDetailsWithDB() {
        throw  new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyStatusCodeCountInDB(Integer count) {
        throw  new UnimplementedTestException("Not implemented yet");
    }
    @Override
    public void verifyCommissionsForRetailCar() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCarResultsNotFromCache() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyMisspelledCityHandling(String expectedCity) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCarResultsPageDidntChange() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyDepositFilterPopupExplanation(String text) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyDepositFilterText(String text) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void makeCarSearchFromResultPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    /* (non-Javadoc)
     * @see com.hotwire.test.steps.search.car.CarSearchModel#clickOnSearchButton()
     */
    @Override
    public void clickOnSearchButton() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyIntentMediaExistingElements() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void validateMeSoBanners() {
        throw new UnimplementedTestException("Not implemented yet");
    }
}










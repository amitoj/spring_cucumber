/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.air;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import com.hotwire.selenium.desktop.us.search.AirSearchFragment;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.testing.UnimplementedTestException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-sshubey
 */
public class AirSearchModelTemplate extends SearchModelTemplate<AirSearchParameters> implements AirSearchModel {


    @Override
    public void showOpaqueFlightWithOutPriceMask() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void showOpaqueFlightWithPriceMask() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyMFI(AirSearchParameters airSearchParameters) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public HashMap<Integer, String> mapOrigDestCities(AirSearchParameters airSearchParameters) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    /*
    @Override
    public void showActivateServicesTab() {
        throw new UnimplementedTestException("Not implemented yet");
    }
    */

    /*
    @Override
    public void completeRetailFlightClickable() {
        throw new UnimplementedTestException("Not implemented yet");
    }
    */

    @Override
    public void showRetailFlightWithSpeedBump() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void showOpaqueFlightWithSpeedBump() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifySpeedBumpAndSaveText(AirSearchParameters airSearchParameters) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyColumnA(AirSearchParameters airSearchParameters) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyErrorMessage(String error) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void invokeAirFareFinder() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void seeAllCitiesCount() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void validateSeeAllCitiesPopUp(String field) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void paginationCheckInResultsPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void setRoutesInfo(AirSearchParameters airSearchParameters) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void doFlexSearch() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void showBFSSearchResults() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void showITASearchResults() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    /*
    @Override
    public void showIntentMediaModule() {
        throw new UnimplementedTestException("Not implemented yet");
    }
    */

    @Override
    public void verifyIntentMediaModule() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickChangeSearch() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyChangeSearchLayer(String fromLocation, String toLocation) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void changeSearchLocations(AirSearchParameters airSearchParameters) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyChangeofRoute(String fromLocation, String toLocation) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyStartEndDates() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyResultsPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void getSelectedDateList() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyBFSAirResults() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyITAAirResults() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void forcedInsurancePanel() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void oldInsurancePanel() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyLocationsOnAirResultsPage(String fromLocation, String toLocation) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void searchPartnerWithIML() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void verifyPartnerSearchResult() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void typeDeparture(String location) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void typeDestination(String location) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void setDates(Date startDate, Date endDate) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickAirSearch() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickOutOfDisambiguationLayer() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickAirDepartureField() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void watchThisTrip(boolean state, String email) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void sortResultsAndVerify(String sortType) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyLowestPriceModule() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickAndVerifyUpdateLayerCalendarElement(boolean useDatePicker) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void launchSearch() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyDestinationFieldIsFilledProperly() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void confirmNumberSellableRetailAndAirportTypeCodeInDB(AuthenticationParameters authenticationParameters) {
        throw new UnimplementedTestException("Implemented me");
    }

    @Override
    public void validateMultiCityPage() throws ParseException {
        throw new UnimplementedTestException("Implemented me");
    }

    @Override
    public void validateMeSoBanners() {
        throw new UnimplementedTestException("Implemented me");
    }

    @Override
    public void validateNumberOfPassengers() {
        throw new UnimplementedTestException("Implemented me");
    }

    @Override
    public void verifyOriginationLocationOnFareFinder(String location) {
        assertThat(new AirSearchFragment(getWebdriverInstance()).getFromLocation())
                .as("destination location doesn't contain " + location)
                .containsIgnoringCase(location);
    }

    @Override
    public void verifyDestinationLocationOnFareFinder(String location) {
        assertThat(new AirSearchFragment(getWebdriverInstance()).getToLocation())
                .as("destination location doesn't contain " + location)
                .containsIgnoringCase(location);
    }
}



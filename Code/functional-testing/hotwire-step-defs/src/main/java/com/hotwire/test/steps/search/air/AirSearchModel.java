/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.air;

import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.search.SearchModel;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * Air search model declares generic and air specific search functionality.
 */
public interface AirSearchModel extends SearchModel {

    //void showActivateServicesTab();

    void showOpaqueFlightWithOutPriceMask();

    void showOpaqueFlightWithPriceMask();

    //Media Fill-In Layer
    void verifyMFI(AirSearchParameters airSearchParameters);

    HashMap<Integer, String> mapOrigDestCities(AirSearchParameters airSearchParameters);

    //void completeRetailFlightClickable();

    void showRetailFlightWithSpeedBump();

    void showOpaqueFlightWithSpeedBump();

    void verifySpeedBumpAndSaveText(AirSearchParameters airSearchParameters);

    void verifyColumnA(AirSearchParameters airSearchParameters);

    void verifyErrorMessage(String error);

    void invokeAirFareFinder();

    void seeAllCitiesCount();

    void validateSeeAllCitiesPopUp(String field);

    void paginationCheckInResultsPage();

    void setRoutesInfo(AirSearchParameters airSearchParameters);

    void doFlexSearch();

    void showBFSSearchResults();

    void showITASearchResults();

    //void showIntentMediaModule();

    void verifyIntentMediaModule();

    void clickChangeSearch();

    void verifyChangeSearchLayer(String fromLocation, String toLocation);

    void changeSearchLocations(AirSearchParameters airSearchParameters);

    void verifyChangeofRoute(String fromLocation, String toLocation);

    void verifyStartEndDates();

    @Override
    void verifyResultsPage();

    void getSelectedDateList();

    void verifyBFSAirResults();

    void verifyITAAirResults();

    void forcedInsurancePanel();

    void oldInsurancePanel();

    void verifyLocationsOnAirResultsPage(String fromLocation, String toLocation);

    void searchPartnerWithIML();

    void verifyPartnerSearchResult();

    void typeDeparture(String location);

    void typeDestination(String location);

    void setDates(Date startDate, Date endDate);

    void clickAirSearch();

    void clickOutOfDisambiguationLayer();

    void clickAirDepartureField();

    void watchThisTrip(boolean state, String email);

    void sortResultsAndVerify(String sortType);

    void verifyLowestPriceModule();

    void clickAndVerifyUpdateLayerCalendarElement(boolean useDatePicker);

    void verifyDestinationFieldIsFilledProperly();

    void confirmNumberSellableRetailAndAirportTypeCodeInDB(AuthenticationParameters authenticationParameters);

    void validateMultiCityPage() throws ParseException;

    void validateMeSoBanners();

    void validateNumberOfPassengers();

    void verifyOriginationLocationOnFareFinder(String location);

    void verifyDestinationLocationOnFareFinder(String location);
}



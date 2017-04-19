/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.db;


import cucumber.api.DataTable;

/**
 * @author adeshmukh
 *
 */
public interface DbModel {

    boolean assertProcessingStatusOfFaxes(String processStatus);

    void verifyTripTypeAndActiveDate(String trip_type, String dateVerifying);

    void setXnetDefaultUser();

    boolean validateXnetDefaultUserData();

    boolean retailHotelFetchData(int limit);

    boolean validateWEXActivationData();

    boolean validateWEXDeativationData();

    boolean validateCurrencyExchangeJob();

    boolean validateOrderAddOnJob();

    boolean validateAuthReversalJob();

    boolean validateXnetBooking(String roomType, String baseAmount);

    void getCustomerWithoutWatchedTrips();

    void checkWatchedTripInDB(String vertical);

    void verifyValuesInReferralTables(String country, String marketingType, String referralType);

    void extractDBDetailsAndCompareMail();

    void updateOpaqueAmenitiesCodes();

    void verifyPurchaseStatusAmountAndCurrency();

    void verifyAmenitiesMatchAfterUpdate();

    void checkPartnersInClickTrackingTable(DataTable table);
}

/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.db;

import static org.fest.assertions.Assertions.assertThat;

import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author adeshmukh
 *
 */
public class DbSteps extends AbstractSteps {

    @Autowired
    @Qualifier("dbModel")
    private DbModel dbModel;

    @Given("^the fax processing status is (.*)$")
    public void assertProcessingStatusOfFaxes(String processStatus) {
        assertThat(dbModel.assertProcessingStatusOfFaxes(processStatus)).isTrue();
    }

    @Then("^I verify trip_type in DB is equals (1|3)(\\sand active date is one day after end date)?$")
    public void verifyTripTypeAndActiveDate(String trip_type, String dateVerifying) {
        dbModel.verifyTripTypeAndActiveDate(trip_type, dateVerifying);
    }

    @Given("^I set the xnet default user data$")
    public void setXnetDefaultUser() {
        dbModel.setXnetDefaultUser();
    }

    @Then("^I validate the xnet default user data$")
    public void validateXnetDefaultUser() {
        assertThat(dbModel.validateXnetDefaultUserData()).as("is_default_user_created flag was not updated").isTrue();
    }

    @Then("^the number of active IAN hotels is greater than (\\d+)$")
    public void validateRetailHotelData(int number) {
        assertThat(dbModel.retailHotelFetchData(number)).as("Retail Hotel data is tampered").isTrue();
    }

    @Then("^I validate the WEXActivationData$")
    public void validateWEXActivationData() {
        assertThat(dbModel.validateWEXActivationData()).isTrue();
    }

    @Then("^I validate the WEXDeactivationData$")
    public void validateWEXDeactivationData() {
        assertThat(dbModel.validateWEXActivationData()).isTrue();
    }

    @Then("^I validate the CurrencyExchangeJob$")
    public void validateCurrencyExchangeJob() {
        assertThat(dbModel.validateCurrencyExchangeJob()).isTrue();
    }

    @Then("^I validate the OrderAddOnJob$")
    public void validateOrderAddOnJob() {
        assertThat(dbModel.validateOrderAddOnJob()).isTrue();
    }

    @Then("^I validate the AuthReversalJob$")
    public void validateAuthReversalJob() {
        assertThat(dbModel.validateAuthReversalJob()).isTrue();
    }

    @Then("^(.*) room is successfully booked for (.*) dollars$")
    public void validateXnetBooking(String roomType, String baseAmount) {
        assertThat(dbModel.validateXnetBooking(roomType, baseAmount)).isTrue();
    }

    @Given("^customer without watched trips$")
    public void getCustomerWithoutWatchedTrips() {
        dbModel.getCustomerWithoutWatchedTrips();
    }

    @Then("^I check watched trip in DB for (car|air|hotel)$")
    public void confirmWatchedTripInDB(String vertical) {
        dbModel.checkWatchedTripInDB(vertical);
    }

    @Then("^I verify the values in REGISTRATION_REFERRAL table for \"(.*)\" and (ONLINE|DB|OFFLINE|AFFILIATE)$")
    public void verifyValuesInRegistrationReferralTable(String country, String marketingType) throws Throwable {
        dbModel.verifyValuesInReferralTables(country, marketingType, "registration");
    }

    @Then("^I verify the values in SEARCH_REFERRAL table for \"(.*)\" and (ONLINE|DB|OFFLINE|AFFILIATE)$")
    public void verifyValuesInSearchReferralTable(String country, String marketingType) throws Throwable {
        dbModel.verifyValuesInReferralTables(country, marketingType, "search");
    }

    @Then("^I verify the values in PURCHASE_REFERRAL table for \"(.*)\" and (ONLINE|DB|OFFLINE|AFFILIATE)$")
    public void verifyValuesInPurchaseReferralTable(String country, String marketingType) throws Throwable {
        dbModel.verifyValuesInReferralTables(country, marketingType, "purchase");
    }

    @When("^I extract details from db and validate against email$")
    public void extractDBDetailsAndCompareMail() throws Throwable {
        dbModel.extractDBDetailsAndCompareMail();
    }

    @Given("^I update opaque amenity codes in DB for recent purchase$")
    public void updateAmenitiesCodes() throws Throwable {
        dbModel.updateOpaqueAmenitiesCodes();
    }

    @Then("^I verify purchase status, amount and currency$")
    public void verifyPurchaseStatusAmountAndCurrency() throws Throwable {
        dbModel.verifyPurchaseStatusAmountAndCurrency();
    }

    @Given("^I verify that the listed amenities remained unchanged$")
    public void verifyAmenitiesMatchAfterUpdate() throws Throwable {
        dbModel.verifyAmenitiesMatchAfterUpdate();
    }

    @Given("^I check partners in click tracking table in DB$")
    public void checkPartnersInClickTrackingTable(DataTable table) {
        dbModel.checkPartnersInClickTrackingTable(table);
    }
}

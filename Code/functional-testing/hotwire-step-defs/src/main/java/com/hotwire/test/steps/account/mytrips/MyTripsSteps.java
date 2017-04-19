/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.account.mytrips;

import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 *  MyTripsSteps
 */
public class MyTripsSteps extends AbstractSteps {

    @Autowired
    @Qualifier("myTripsModel")
    private MyTripsModel myTripsModel;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParameters carSearchParameters;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @When("^I want to access my booked trips$")
    public void accessMyBookedTrips() {
        myTripsModel.navigateToMyTripsPage();
    }

    @Then("^my booked trip summaries are available$")
    public void verifyBookedTripDetails() {
        myTripsModel.verifyMyTripSummaryPage();
    }

    @Then("^my booked (hotel|car|flight) trip details are available$")
    public void verifyAllBookedTripDetails(String bookingType) {
        myTripsModel.verifyTripDetails(bookingType);
    }

    @Then("^my booked trip details are available$")
    public void navigatetoTripDetails() {
        myTripsModel.navigateToTripDetails();
    }

    @When("^I want to access my trips$")
    public void accessMyTrips() {
        myTripsModel.accessMyTrips();
    }

    @When("^I select trip details from the first (hotel|car|flight) trip summary$")
    public void selectFirstTripSummaryItem(String bookingType) {
        myTripsModel.selectFirstTripSummaryItem(bookingType);
    }

    @When("^I should see correct information on the car trip details page$")
    public void verifyCarTripDetailsPageHasCorrectInformation() {
        myTripsModel.verifyCarTripDetailsPageHasCorrectInformation(authenticationParameters,
                carSearchParameters,
                purchaseParameters);
    }

}

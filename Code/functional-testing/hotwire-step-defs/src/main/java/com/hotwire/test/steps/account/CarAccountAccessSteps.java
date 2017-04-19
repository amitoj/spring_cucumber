/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.search.SearchParameters;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * CarAccountAccessSteps
 */
public class CarAccountAccessSteps extends AbstractSteps {

    private static final String CAR_TRIP_TYPE = "car";
    private static final String CAR_TRIP_STATUS_RESERVED = "reserved";
    private static final String CAR_TRIP_STATUS_BOOKED = "booked";
    private static final String CAR_TRIP_STATUS_CANCELLED = "cancelled";
    private static final String TRIP_TAB_NAME = "Trips";
    private static final String WATCHED_TRIPS_TAB_NAME = "Watched trips";

    @Autowired
    @Qualifier("accountAccess")
    private AccountAccessModel accountAccessModel;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    /**
     * Search a booked trip in my account.
     *
     * @param tripType   - type of purches {car, air, hotel}
     * @param location   String
     * @param tripStatus - {booked, reserved, cancelled}
     */
    @Then("^I check the past (car|air|hotel) purchase in \"([^\"]*)\" has been (booked|reserved|cancelled)$")
    public void confirmTripStatus(String tripType, String location, String tripStatus) {
        accountAccessModel.accessAccountTab(TRIP_TAB_NAME);
        accountAccessModel.confirmRecentlyBookedTripDetails(tripType, location, tripStatus, searchParameters);
    }

    @Then("^I cancel the past car purchase in \"([^\"]*)\"(\\sfrom Intl site)?$")
    public void cancelPastPurchase(String location, String isIntl) {
        accountAccessModel.accessAccountTab(TRIP_TAB_NAME);
        if (isIntl == null) {
            accountAccessModel.cancelPastPurchase(CAR_TRIP_TYPE, location, CAR_TRIP_STATUS_RESERVED, searchParameters);
        }
        else {
            accountAccessModel.cancelPastPurchase(CAR_TRIP_TYPE, location, CAR_TRIP_STATUS_BOOKED, searchParameters);
        }
    }

    @Then("^I check the past trip in \"([^\"]*)\" has been watched$")
    public void confirmTripWatch(String location) {
        accountAccessModel.accessAccountTab(TRIP_TAB_NAME);
        accountAccessModel.accessSubAccountTab(WATCHED_TRIPS_TAB_NAME);
        accountAccessModel.confirmRecentlyWatchedTrip(location, searchParameters);

    }

    @Then("^I stop watching all trips$")
    public void stopWatchingAllTrips() {
        accountAccessModel.stopWatchingLatestTrip();
    }


}

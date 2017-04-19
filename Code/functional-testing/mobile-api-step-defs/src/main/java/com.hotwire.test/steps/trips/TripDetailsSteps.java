/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.trips;

import com.hotwire.test.steps.AbstractSteps;
import cucumber.api.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-dsobko
 * Since: 01/20/15
 */
public class TripDetailsSteps extends AbstractSteps {

    @Autowired
    private TripDetailsService tripDetailsService;


    @And("^I execute trip details request for (hotel|car|air) itinerary for (random|payable) user$")
    public void executeTripDetailsRequestForItinerary(String vertical, String user) {
        tripDetailsService.executeTripDetailsRequestForItinerary(user, vertical);
    }

    @And("^I execute trip details request for random (hotel|car|air) itinerary$")
    public void executeTripDetailsRequestForRandomItinerary(String vertical) {
        tripDetailsService.executeTripDetailsRequestForRandomItinerary(vertical);
    }

    @And("^trip details response (is|is not) present$")
    public void verifyTripDetailsResponse(String condition) {
        if (condition.contains("not")) {
            tripDetailsService.verifyRetrieveTripDetailsResponseIsAbsent();
        }
        else {
            tripDetailsService.verifyRetrieveTripDetailsResponse();
        }
    }

    @And("^I see address and latitude/longitude are present in trip details$")
    public void verifyAddressLongitudeLatitudeTripDetails() {
        tripDetailsService.verifyAddressLongitudeLatitudeTripDetails();
    }

    @And("^I see itinerary number is present in trip details$")
    public void verifyItineraryInTripDetails() {
        tripDetailsService.verifyItineraryInTripDetails();
    }

    @And("^I see booking status is present in trip details$")
    public void verifyBookingStatusInTripDetails() {
        tripDetailsService.verifyBookingStatusInTripDetails();
    }

    @And("^I see product vertical is (hotel|car|air)$")
    public void verifyProdVerticalInTripDetails(String vertical) {
        tripDetailsService.verifyProdVerticalInTripDetails(vertical);
    }

    @And("^I see booking dates and location are present in trip details$")
    public void verifyBookingDatesLocationInTripDetails() {
        tripDetailsService.verifyBookingDatesLocationInTripDetails();
    }

    @And("^I see car reservation details are present in trip details$")
    public void verifyCarReservationDetails() {
        tripDetailsService.verifyCarReservationDetails();
    }

    @And("^I see charges are present in trip details$")
    public void verifySummaryOfChargesInTripDetails() {
        tripDetailsService.verifySummaryOfChargesInTripDetails();
    }


}

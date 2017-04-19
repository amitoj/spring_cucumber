/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.hotel_amenities;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-dpuchkov
 * Since: 03/17/15
 */
public class HotelAmenitiesSteps extends AbstractSteps {

    @Autowired
    private HotelAmenitiesService hotelAmenitiesService;

    @Autowired
    private RequestProperties requestProperties;


    @And("^I use hotel id (\\d+)$")
    public void setHotelID(long hotelID) {
        requestProperties.setHotelId(hotelID);
    }

    @When("^I execute the hotel amenities request with room type (.*)$")
    public void executeHotelAmenitiesRequest(String roomType) {
        hotelAmenitiesService.executeHotelAmenitiesRequest(roomType);
    }

    @Then("^I see hotel amenities are present$")
    public void verifyHotelAmenities() {
        hotelAmenitiesService.verifyHotelAmenities();
    }

}

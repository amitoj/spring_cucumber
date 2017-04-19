/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.hotel.search;

import com.hotwire.test.steps.bex.BexAbstractSteps;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelSearchSteps extends BexAbstractSteps {

    @Autowired
    private HotelSearchModelWebApp model;

    @Given("^I want to book hotel for the (.*) future$")
    // Setup random valid start and end dates in the near feature
    public void setRandomValidSearchDates(String future) {
        if (future.equalsIgnoreCase("near")) {
            model.selectNearDates();
        }
        else if (future.equalsIgnoreCase("far")) {
            model.selectFarDates();
        }

    }

    @Given("^I perform search for hotels near (.*)$")
    public void searchForHotelsNearCity(String city) {
        model.searchForHotelNearCity(city);
    }

    @Given("^I select hotel from result list$")
    public void selectHotelFromResults() {
        model.selectRandomHotelFromList();
    }


}





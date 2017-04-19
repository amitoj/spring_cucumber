/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.hotel.details;

import com.hotwire.test.steps.bex.BexAbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelDetailsSteps extends BexAbstractSteps {

    @Autowired
    private HotelDetailsModelWebApp model;

    @Given("^I select recommended room$")
    public void selectHotelFromResults() {
        model.clickRecommendedBookOption();
    }

    @Given("^I select pay now option$")
    public void selectPaymentOption() {
        model.clickPayNow();
    }

    @Then("^I verify destination location on BEX results page is San Francisco$")
    public void verifyDestinationIsSanFrancisco() {
        model.verifyDestination("San Francisco");
    }
}


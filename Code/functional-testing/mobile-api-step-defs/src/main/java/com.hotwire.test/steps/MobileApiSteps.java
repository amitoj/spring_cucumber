/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-ngolodiuk
 * Since: 12/19/14
 */
public class MobileApiSteps extends AbstractSteps {

    @Autowired
    private RequestProperties requestProperties;

    @Given("^Mobile API is accessible$")
    public void mobileApiIsAccessible() {
        //TBD
    }

    @Given("^I set country code (.*)$")
    public void setCountryCode(String countryCode) {
        requestProperties.setCountryCode(countryCode);
    }

    @Given("^I use client id (\\d+)$")
    public void setClientID(long arg1) {
        requestProperties.setClientId(arg1);
    }

    @Given("^I use customer (-?\\d+)$")
    public void setCustomerID(long arg1) {
        requestProperties.setCustomerId(arg1);
    }

    @Given("^I use LatLong (.*)$")
    public void setLatLong(String latLong) {
        requestProperties.setLatLong(latLong);
    }

    @Given("^I set the channel ID to (.*)$")
    public void setChannelID(String channelID) {
        requestProperties.setChannelID(channelID);
    }

    @And("^my currency is (USD|GBP|EUR|CAD|AUD|NOK|DKK|SEK)$")
    public void setUpCurrency(String currency) {
        requestProperties.setCurrencyCode(currency);
    }

    @Given("^I use (\\d+) limit and (\\d+) offset$")
    public void setLimitOffset(int limit, int offset) {
        requestProperties.setLimit(limit);
        requestProperties.setOffset(offset);
    }

}


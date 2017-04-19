/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.car.steps;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: v-vjneiman
 * Date: 6/27/13
 * Time: 3:40 AM
 */
public class CarSearchCustomerCareSteps extends AbstractSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSearchCustomerCareSteps.class.getSimpleName());
    private static final String DATE_FORMAT = "HH:mm z";
    private static final String LIVE_CHAT_TZ_ID = TimeZone.getTimeZone("PST").inDaylightTime(new Date()) ? "PDT" :
            "PST";
    private static final String LIVE_CHAT_START = "05:00 " + LIVE_CHAT_TZ_ID;
    private static final String LIVE_CHAT_END = "22:00 " + LIVE_CHAT_TZ_ID;
    private static final String LIVE_CHAT_AVAILABLE = "Current time is within live chat operating hours.";
    private static final String LIVE_CHAT_UNAVAILABLE = "Current time is outside of customer care live chat operating" +
            " hours.";

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel model;

    @Then("^I choose live chat from the (car details|result set) module$")
    public void selectLiveChat(String moduleLocation) {
        boolean chatState = isLiveChatAvailableForCurrentTime();
        if (chatState) {
            LOGGER.info(LIVE_CHAT_AVAILABLE + " Proceeding with clicking live chat link and continuing with test flow" +
                    ".");
            model.selectLiveChat(moduleLocation);
        }
        else {
            LOGGER.info(LIVE_CHAT_UNAVAILABLE + " Asserting that Live Chat is not displayed.");
            boolean displayState = false;
            model.assertLiveChatDisplayState(displayState, moduleLocation);
        }
    }

    @Then("^I (will|will not) see the live chat prechat popup$")
    public void verifyLivePrechat(String state) {
        boolean chatState = isLiveChatAvailableForCurrentTime();
        if (chatState) {
            LOGGER.info(LIVE_CHAT_AVAILABLE + " Verifying pre chat window state.");
            model.verifyLivePreChatDisplayed(state.equals("will") ? true : false);
        }
        else {
            LOGGER.info(LIVE_CHAT_UNAVAILABLE);
        }
    }

    @Then("^customer care module is displayed on billing$")
    public void verifyCustomerCareModuleOnBilling() {
        model.verifyCustomerCareModules();
    }

    private Date getCurrentTime() {
        // Live chat hours in PST/PDT so the current date has to be in PST/PDT.
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(LIVE_CHAT_TZ_ID), Locale.US);
        SimpleDateFormat now = new SimpleDateFormat(DATE_FORMAT);
        String time = now.format(cal.getTime());
        return getDateFromFormattedTime(time);
    }

    private Date getDateFromFormattedTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date date = format.parse(time);
            return date;
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isLiveChatAvailableForCurrentTime() {
        Date now = getCurrentTime();
        return now.compareTo(getDateFromFormattedTime(LIVE_CHAT_START)) >= 0 &&
                now.compareTo(getDateFromFormattedTime(LIVE_CHAT_END)) <= 0;
    }
}


/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.customercare;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * CustomerCareModuleModelSteps
 */
public class CustomerCareModuleModelSteps extends AbstractSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCareModuleModelSteps.class.getSimpleName());
    private static final String DATE_FORMAT = "HH:mm z";
    private static final String LIVE_CHAT_TZ_ID =
        TimeZone.getTimeZone("PST").inDaylightTime(new Date()) ? "PDT" : "PST";
    private static final String LIVE_CHAT_START = "05:00 " + LIVE_CHAT_TZ_ID;
    private static final String LIVE_CHAT_END = "22:00 " + LIVE_CHAT_TZ_ID;
    private static final String LIVE_CHAT_AVAILABLE = "Current time is within live chat operating hours.";
    private static final String LIVE_CHAT_UNAVAILABLE =
        "Current time is outside of customer care live chat operating hours.";

    @Autowired
    @Qualifier("customerCareModuleModel")
    private CustomerCareModuleModel model;

    @Then("^All customer care modules will be displayed in the (opaque results|retail results|opaque details " +
          "page|retail details page)$")
    public void verifyCustomerCareModules(String pageType) {
        if (pageType.trim().contains("results")) {
            model.verifyCustomerCareModules();
        }
        else if (pageType.trim().contains("details")) {
            model.veriyfDetailsCustomerCareModules();
        }
    }

    @When("^I choose live chat from the (results|details) customer care module$")
    public void selectLiveChat(String page) {
        boolean chatState = isLiveChatAvailableForCurrentTime();
        if (chatState) {
            LOGGER.info(
                LIVE_CHAT_AVAILABLE + " Proceeding with clicking live chat link and continuing with test flow.");
            if (page.trim().equals("results")) {
                model.selectResultsLiveChat();
            }
            else if (page.trim().equals("details")) {
                model.selectDetailsLiveChat();
            }
        }
        else {
            LOGGER.info(LIVE_CHAT_UNAVAILABLE + " Asserting that Live Chat is not displayed.");
            boolean displayState = false;
            if (page.trim().equals("results")) {
                model.assertResultsLiveChatDisplayState(displayState);
            }
            else if (page.trim().equals("details")) {
                model.assertDetailsLiveChatDisplayState(displayState);
            }
        }
    }

    @When("^I cancel the live chat$")
    public void cancelLivePreChat() {
        boolean chatState = isLiveChatAvailableForCurrentTime();
        if (chatState) {
            LOGGER.info(LIVE_CHAT_AVAILABLE + " Click cancel chat from pre chat window.");
            model.cancelLivePreChat();
        }
        else {
            LOGGER.info(LIVE_CHAT_UNAVAILABLE);
        }
    }

    @Then("^I (will|will not) see the live chat prechat form$")
    public void verifyLivePrechat(String state) {
        boolean chatState = isLiveChatAvailableForCurrentTime();
        if (chatState) {
            LOGGER.info(LIVE_CHAT_AVAILABLE + " Verifying pre chat window state.");
            model.verifyLivePreChat(state.equals("will"));
        }
        else {
            LOGGER.info(LIVE_CHAT_UNAVAILABLE);
        }
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
            return format.parse(time);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isLiveChatAvailableForCurrentTime() {
        Date now = getCurrentTime();
        return now.compareTo(getDateFromFormattedTime(LIVE_CHAT_START)) >= 0 &&
               now.compareTo(getDateFromFormattedTime(LIVE_CHAT_END)) < 0;
    }
}

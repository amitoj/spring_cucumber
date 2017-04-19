/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.customercare;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;

public class CustomerCareModuleModelRowWebApp extends WebdriverAwareModel implements CustomerCareModuleModel {

    private static final Pattern INTL_PHONE_PATTERN =
        Pattern.compile("\\d{4} \\d{3} \\d{4}", Pattern.MULTILINE | Pattern.CANON_EQ);
    private static final Pattern INTL_REF_PATTERN = Pattern.compile("(\\d{4})", Pattern.MULTILINE | Pattern.CANON_EQ);

    @Override
    public void verifyCustomerCareModules() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        // Verify the results customer care module.
        Matcher phoneMatcher = INTL_PHONE_PATTERN.matcher(resultsPage.getCustomerCarePhoneNumber());
        Matcher referenceNumberMatcher = INTL_REF_PATTERN.matcher(resultsPage.getTopCustomerCareReferenceNumber());
        assertThat(phoneMatcher.find())
            .as("Phone number not found in top customer care module.")
            .isTrue();
        assertThat(referenceNumberMatcher.find())
            .as("Reference number not found in top customer care module.")
            .isTrue();
    }

    @Override
    public void veriyfDetailsCustomerCareModules() {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        Matcher phoneMatcher = INTL_PHONE_PATTERN.matcher(detailsPage.getCustomerCarePhoneNumber());
        Matcher referenceNumberMatcher = INTL_REF_PATTERN.matcher(detailsPage.getTopCustomerCareReferenceNumber());
        assertThat(phoneMatcher.find())
            .as("Phone number not found in top customer care module.")
            .isTrue();
        assertThat(referenceNumberMatcher.find())
            .as("Reference number not found in top customer care module.")
            .isTrue();
    }

    @Override
    public void selectDetailsLiveChat() {
        throw new UnimplementedTestException("Live chat is not implemented on intl POS.");
    }

    @Override
    public void selectResultsLiveChat() {
        throw new UnimplementedTestException("Live chat is not implemented on intl POS.");
    }

    @Override
    public void cancelLivePreChat() {
        throw new UnimplementedTestException("Live chat is not implemented on intl POS.");
    }

    @Override
    public void verifyLivePreChat(boolean isDisplayed) {
        throw new UnimplementedTestException("Live chat is not implemented on intl POS.");
    }

    @Override
    public void assertResultsLiveChatDisplayState(boolean displayState) {
        throw new UnimplementedTestException("Live chat is not implemented on intl POS.");
    }

    @Override
    public void assertDetailsLiveChatDisplayState(boolean displayState) {
        // There is no live chat for intl POS.
        assertThat(new HotelDetailsPage(getWebdriverInstance()).isLiveChatDisplayed())
            .as("Intl pos should not have live chat displayed. Hotel details page shows otherwise.")
            .isFalse();
    }
}

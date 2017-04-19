/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.customercare;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.springframework.beans.factory.annotation.Autowired;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.us.livechat.LivePreChatUSPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.util.webdriver.WebDriverManager;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * CustomerCareModuleModelWebApp
 */
public class CustomerCareModuleModelWebApp extends WebdriverAwareModel implements CustomerCareModuleModel {

    private static final Pattern US_PHONE_PATTERN =
        Pattern.compile("(1-)?\\d{3}-\\d{3}-\\d{4}", Pattern.MULTILINE | Pattern.CANON_EQ);
    private static final Pattern US_REF_NUMBER =
        Pattern.compile("(\\d{4,})", Pattern.MULTILINE | Pattern.CANON_EQ);
    private static final Pattern INTL_PHONE_PATTERN =
        Pattern.compile("\\d{4} \\d{3} \\d{4}", Pattern.MULTILINE | Pattern.CANON_EQ);
    private static final Pattern INTL_REF_NUMBER =
        Pattern.compile("(\\d{4})", Pattern.MULTILINE | Pattern.CANON_EQ);

    @Autowired
    private PurchaseParameters purchaseParameters;

    public void setPurchaseParameters(PurchaseParameters purchaseParameters) {
        this.purchaseParameters = purchaseParameters;
    }

    @Override
    public void verifyCustomerCareModules() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        // Verify the results customer care module.
        Matcher phoneMatcher = US_PHONE_PATTERN.matcher(resultsPage.getCustomerCarePhoneNumber());
        Matcher referenceNumberMatcher = US_REF_NUMBER.matcher(resultsPage.getTopCustomerCareReferenceNumber());
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
        Matcher phoneMatcher = null;
        Matcher referenceNumberMatcher = null;
        if (purchaseParameters.getUserInformation().getCountry().equals("United States") ||
            purchaseParameters.getUserInformation().getCountry().equals("Canada")) {
            phoneMatcher = US_PHONE_PATTERN.matcher(detailsPage.getCustomerCarePhoneNumber());
            referenceNumberMatcher = US_REF_NUMBER.matcher(detailsPage.getTopCustomerCareReferenceNumber());
        }
        else {
            phoneMatcher = INTL_PHONE_PATTERN.matcher(detailsPage.getCustomerCarePhoneNumber());
            referenceNumberMatcher = INTL_REF_NUMBER.matcher(detailsPage.getTopCustomerCareReferenceNumber());
        }

        assertThat(phoneMatcher.find())
            .as("Phone number not found in top customer care module.")
            .isTrue();
        assertThat(referenceNumberMatcher.find())
            .as("Reference number not found in top customer care module.")
            .isTrue();
    }

    @Override
    public void selectResultsLiveChat() {
        new HotelResultsPage(getWebdriverInstance()).openLiveChat();
    }

    @Override
    public void selectDetailsLiveChat() {
        HotelDetailsPage detailsPage =  new HotelDetailsPage(getWebdriverInstance());
        // Need to get rid of extraneous popups like stupid intent media popup when leaving results page to details.
        try {
            new WebDriverWait(getWebdriverInstance(), 5).until(waitForUnexpectedPopup());
        }
        catch (TimeoutException e) { /* do nothing */ }
        WebDriverManager.closeAllPopups.apply(getWebdriverInstance());
        detailsPage.openLiveChat();
    }

    private ExpectedCondition<Boolean> waitForUnexpectedPopup() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.getWindowHandles().size() > 1;
            }
        };
    }
    @Override
    public void cancelLivePreChat() {
        switchToNonRootWindow();
        new LivePreChatUSPage(getWebdriverInstance()).cancelChat();
    }

    @Override
    public void verifyLivePreChat(boolean isDisplayed) {
        try {
            if (isDisplayed) {
                switchToNonRootWindow();
                new LivePreChatUSPage(getWebdriverInstance());
            }
            else {
                assertThat(getWebdriverInstance().getWindowHandles().size() == 1).isTrue();
            }
        }
        finally {
            getWebdriverInstance().switchTo().window(WebDriverManager.getRootWindowHandle(getWebdriverInstance()));
        }
    }

    private void switchToNonRootWindow() {
        String rootWindowHandle = WebDriverManager.getRootWindowHandle(getWebdriverInstance());
        Set<String> handles = getWebdriverInstance().getWindowHandles();
        assertThat(handles.size())
                .as("Expected new chat window.")
                .isGreaterThan(1);
        for (String handle : handles) {
            if (!handle.equals(rootWindowHandle)) {
                getWebdriverInstance().switchTo().window(handle);
                break;
            }
        }
    }

    @Override
    public void assertResultsLiveChatDisplayState(boolean displayState) {
        boolean isDisplayed = new HotelResultsPage(getWebdriverInstance()).isLiveChatDisplayed();
        assertThat(isDisplayed)
                .as("Expected display of live chat to be " + displayState + " but got " + isDisplayed + " instead")
                .isEqualTo(displayState);
    }

    @Override
    public void assertDetailsLiveChatDisplayState(boolean displayState) {
        boolean isDisplayed = new HotelDetailsPage(getWebdriverInstance()).isLiveChatDisplayed();
        assertThat(isDisplayed)
                .as("Expected display of live chat to be " + displayState + " but got " + isDisplayed + " instead")
                .isEqualTo(displayState);
    }
}

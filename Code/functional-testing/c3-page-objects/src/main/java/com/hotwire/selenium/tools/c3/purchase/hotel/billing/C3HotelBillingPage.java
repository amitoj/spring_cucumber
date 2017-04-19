/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel.billing;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object in C3 Frame that show hotel billing page
 */
public class C3HotelBillingPage extends ToolsAbstractPage {

    public C3HotelBillingPage(WebDriver webdriver) {
        super(webdriver, By.className("onePageBillingForm"));
    }

    public C3HotelBillingPage(WebDriver webdriver, By keyPageElement) {
        super(webdriver, keyPageElement);
    }

    public boolean isSavedCardAvailable() {
        return isElementDisplayed(By.cssSelector("div.savedCardInfoBox a"));
    }

    public boolean isSavedCardCodeAvailable() {
        return isElementDisplayed(By.cssSelector("div.savedCardInfoBox a"));
    }

    public void book() {
        findOne("input#agreementCheckbox").click();
        findOne("button#confirmPaymentBtn").click();
    }

    public String getValidationMessage() {
        return findOne("div.msgBox.errorMessages ul li", EXTRA_WAIT).getText().replace("\n\n", " ");
    }

    public boolean isInsuranceDisplayed() {
        return isElementDisplayed(By.id("tripInsuranceContent"), EXTRA_WAIT);
    }

    public String getTermsBulletsText() {
        return findOne("div.hotwireTerms").getText().replace("\n", "");
    }

    public String getBookingNotesBulletsText() {
        return findOne("div.bookingNote").getText();
    }
}

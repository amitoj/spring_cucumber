/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.view.ViewIds;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: hkarkhanis
 * Date: May 24, 2012
 * Time: 11:11:49 PM
 */
public class MobileCarBillingPage extends MobileAbstractPage {

    private static final int MAX_SEARCH_PAGE_WAIT_SECONDS = 20;

    @FindBy (xpath = "//label[@for='existingCC0']")
    private WebElement existingCC;

    public MobileCarBillingPage(WebDriver webdriver, String expectedPageName) {
        super(webdriver, expectedPageName);
    }

    public MobileCarBillingPage(WebDriver webdriver) {
        super(webdriver, ViewIds.TILE_BILLING_CAR_TRAVELER_INFO);
    }

    public int getMaxPageWaitSeconds() {
        return MAX_SEARCH_PAGE_WAIT_SECONDS;
    }

    public MobileCarTravelerInfoGuestFragment fillGuestTravelerInfo() {
        return new MobileCarTravelerInfoGuestFragment(getWebDriver(), ViewIds.TILE_BILLING_CAR_TRAVELER_INFO);
    }

    public MobileCarTravelerInfoUserFragment fillUserTravelerInfo() {
        return new MobileCarTravelerInfoUserFragment(getWebDriver(), ViewIds.TILE_BILLING_CAR_TRAVELER_INFO);
    }

    public PaymentMethodFragmentGuest fillPaymentMethod() {
        // Car has multi page billing experience for both registered users and guest users.
        return new PaymentMethodFragmentGuest(getWebDriver(), ViewIds.TILE_BILLING_PAYMENT_INFO);
    }


    public CarInsuranceFragment selectInsuranceOption() {
        return new CarInsuranceFragment(getWebDriver(), ViewIds.TILE_BILLING_CAR_INSURANCE_INFO);
    }

    public void book() {
        new PurchaseReviewFragment(
                getWebDriver(),
                ViewIds.TILE_CAR_BILLING_REVIEW_DETAILS)
                .continuePanel();
    }

    public void verifySavedPaymentMethod(List<String> savedCCName) {
        assertThat(existingCC.getText())
                .as("The saved payment method doesn't match w/ saved information")
                .containsIgnoringCase(savedCCName.get(0));
    }
}

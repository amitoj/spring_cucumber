/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.onepage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcAddFeaturesFragment;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 5:23 AM
 */
public class OpAddFeaturesFragment extends AcAddFeaturesFragment {

    {
        identifier = "#protectionAndPayment .featureOptions, #protectionAndPayment .radio, " +
                     "#protectionAndPayment h1.unavailable";

        insuranceDynamic =
                "div#protectionAndPayment div.rentalCarProtection input[name='orderAddOnForm.cdwQuoteId'], " +
                "div#protectionAndPayment input[name='orderAddOnForm.cdwQuoteId']";
        insuranceStatic = "div#protectionAndPayment div.infoDetails input[name='billingForm.orderAddOnForm" +
                ".isRentalCarProtectionInsuranceRequested']";
        insuranceUnavailable = ".protectionWrapper .unavailable";
        insuranceUnavailableText = "Rental Car Damage Protection is unavailable";
        insuranceRadioButtonsStr = insuranceDynamic + ", " + insuranceStatic;
    }

    public OpAddFeaturesFragment(WebDriver webDriver) {
        super(webDriver, By.id("protectionAndPayment"));
    }

    @Override
    public void continuePanel() {
        // No action
    }
}

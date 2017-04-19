/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.paypal;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author v-ozelenov
 * @since 2013.05
 */
public class PayPalPurchaseSteps extends AbstractSteps {

    @Autowired
    @Qualifier("payPalModel")
    private PayPalModel model;

    /**
     * DEPRECATED
     * We need to log in to PayPal developer for booking with test account
     */
//    @Given("^I'm logged in PayPal developer$")
//    public void loginPayPalAdmin() {
//        model.loginPayPalAdmin();
//    }

    /**
     * Log in to test PayPal account while booking
     */
    @Given("^I confirm booking on PayPal sandbox$")
    public void loginPayPalSandbox() {
        if (!isPaypalSandboxAvailable()) {
            throw new PendingException("Paypal sandbox is unavailable on Friday.");
        }
        model.loginPayPalSandbox();
    }

    /**
     * Clicking on cancel link on PayPal sandbox login page
     */
    @Given("^I cancel booking on PayPal sandbox$")
    public void cancelPayPalSandbox() {
        if (!isPaypalSandboxAvailable()) {
            throw new PendingException("Paypal sandbox is unavailable on Friday.");
        }
        model.cancelPayPalSandbox();
    }

    @Given("^I return to the Hotwire site$")
    public void return_to_the_Hotwire_site() {
        model.returnToHotwireFromPaypal();
    }

    public static synchronized boolean isPaypalSandboxAvailable() {
        Calendar cal = Calendar.getInstance();
        Logger.getAnonymousLogger().info(">>>>> LOCALE" + Locale.getDefault(Locale.Category.FORMAT));
        if ((cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && cal.get(Calendar.HOUR_OF_DAY) >= 22) ||
             cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            return false;
        }
        return true;
    }
}

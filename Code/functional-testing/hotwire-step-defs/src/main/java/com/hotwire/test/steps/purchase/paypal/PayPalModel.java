/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.paypal;

/**
 * @author v-ozelenov
 * @since 2013.05
 */
public interface PayPalModel {

    /*
    * Log in to PayPal Developer website
     */
//    void loginPayPalAdmin();

    /**
     * Login in PayPal sandbox account
     */
    void loginPayPalSandbox();
    /**
     * Cancel while logging in PayPal sandbox account
     */
    void cancelPayPalSandbox();

    /**
     * Return to Hotwire site from Paypal Sandbox     */
    void returnToHotwireFromPaypal();
}



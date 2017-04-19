/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.onepage;

import com.hotwire.selenium.desktop.us.billing.car.impl.CarPaymentMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcPaymentMethodFragment;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 7:04 AM
 */
public class OpPaymentMethodFragment extends AcPaymentMethodFragment {

    /*
    * Payment method
    */
    {
        container = "div#newCreditCard ";
        paymentOptionCard = "input#defaultPmIdNewCC";
        cardNumber = "input[name='billingForm.paymentForm._NAE_acctNumber']";
        cardExpMonth = "select[name='billingForm.paymentForm.cardMonth']";
        cardExpYear = "select[name='billingForm.paymentForm.cardYear']";
        cardSecCode = "div.securityCode input[id$=cpvNumber], input[name='billingForm.paymentForm._NAE_cpvNumber']";
        billFirstName = "input[id$=billingFirstName]";
        billLastName = "input[id$=billingLastName]";
        billAddress = "input[name='billingForm.paymentForm._NAE_stAddress1']";
        billCountry = "select[name='billingForm.paymentForm.country']";
        billCity = "input[name='billingForm.paymentForm.city']";
        billZipCode = "input[name='billingForm.paymentForm._NAE_zip']";
        billState = "*[name='billingForm.paymentForm.state']";
        countryFld = ".newPaymentMethodWrapper .countryFields";
    }

    public OpPaymentMethodFragment(WebDriver webDriver) {
        super(webDriver, By.id("paymentSection"));
    }

    @Override
    public CarPaymentMethod continuePanel() {
        //No action
        return this;
    }
}

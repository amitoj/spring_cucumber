/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.air.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-ozelenov on 4/1/2014.
 */
public class C3AirPaymentInfoFragment extends C3AirBillingPage {
    public C3AirPaymentInfoFragment(WebDriver webdriver) {
        super(webdriver, By.id("billingPanelPaymentInfoComp"));
    }

    @Override
    public void proceed() {
        findOne("div#billingPanelPaymentInfoComp-contentElementRefreshable input.continueBtn").click();
    }

    public C3AirPaymentInfoFragment setVisaCreditCard(String number, String securityCode) {
        chooseVisaFromDropDown();
        setText("input[name='paymentForm._NAE_acctNumber']", number);
        selectValue("div.paymentForm select[name='paymentForm.cardMonth']", "1");
        selectValue("div.paymentForm select[name='paymentForm.cardYear']", "2028");
        setText("div.paymentForm input[name='paymentForm._NAE_cpvNumber']", securityCode);
        return this;
    }

    private void chooseVisaFromDropDown() {
        try {
            selectValue("select#cardSelector", "1");
        }
        catch (NoSuchElementException e) {
            logger.info("No Visa card selector");
        }
    }

    public C3AirPaymentInfoFragment setBillingName(String fn, String ln) {
        setText("div.paymentForm input.billingWideName", fn);
        setText("div.paymentForm input.billingLastName", ln);
        return this;
    }

    public C3AirPaymentInfoFragment setUSBillingAddress(String address, String city,
                                                      String state, String zip) {
        setText("div.paymentForm input[name='paymentForm._NAE_stAddress1']", address);
        selectValue("div.paymentForm select[name='paymentForm.country']", "US");
        setText("div.paymentForm input[name='paymentForm.city']", city);
        selectValue("div.paymentForm select[name='paymentForm.state']", state);
        setText("div.paymentForm input[name='paymentForm._NAE_zip']", zip);
        return this;
    }

    public C3AirPaymentInfoFragment useSavedCreditCard() {
        findOne("div#savedPaymentMethods", DEFAULT_WAIT);
        setText("div.cardIdFieldContainer  input.cardId", "111");
        return this;
    }

    public C3AirPaymentInfoFragment useCreditCard() {
        findOne("INPUT#defaultPmIdNewCC", DEFAULT_WAIT).click();
        return this;
    }
}

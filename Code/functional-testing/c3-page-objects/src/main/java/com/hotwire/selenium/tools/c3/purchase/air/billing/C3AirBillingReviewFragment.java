/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.air.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-ozelenov on 4/1/2014.
 */
public class C3AirBillingReviewFragment extends C3AirBillingPage {
    public C3AirBillingReviewFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.agreementTxt"));
    }

    public C3AirBillingReviewFragment agreeTerms() {
        findOne("input#agreementChecked", DEFAULT_WAIT).click();
        return this;
    }

    @Override
    public void proceed() {
        findOne("input[name='btnPurchase']", DEFAULT_WAIT).click();
    }
}

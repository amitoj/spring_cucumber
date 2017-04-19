/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel.billing;

import com.hotwire.util.webdriver.functions.IsAjaxDone;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/25/14
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3TripInsuranceFragment extends C3HotelBillingPage {
    private static final String INSURANCE_YES = "input#insurancePurchaseCheckTrue";
    private static final String INSURANCE_NO = "input#insurancePurchaseCheckFalse";

    public C3TripInsuranceFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.tripInsuranceHtml"));
    }

    public void setInsurance(boolean withInsurance) {
        String insuranceRadioBtn;
        if (withInsurance) {
            insuranceRadioBtn = INSURANCE_YES;
        }
        else {
            insuranceRadioBtn = INSURANCE_NO;
        }
        findOne(insuranceRadioBtn, DEFAULT_WAIT).click();
        new WebDriverWait(getWebDriver(), EXTRA_WAIT).until(new IsAjaxDone());
    }

    public boolean getInsurance() {
        try {
            findOne("input#silk_insuranceYes");
            return true;
        }
        catch (NoSuchElementException e) {
            findOne("input#silk_insuranceNo");
            return false;
        }
    }

    public String getInsuranceText() {
        return findOne("div#tripInsuranceContent").getText();
    }

}

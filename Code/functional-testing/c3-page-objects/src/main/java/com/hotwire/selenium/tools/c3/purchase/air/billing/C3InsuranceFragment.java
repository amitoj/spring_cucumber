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
 * Created by v-ozelenov on 3/31/2014.
 */
public class C3InsuranceFragment extends C3AirBillingPage {
    private static String rentalCarProtectionTrue = ".rentalCarProtection input#insurancePurchaseCheckTrue";
    private static String rentalCarProtectionFalse = ".rentalCarProtection input#insurancePurchaseCheckFalse";
    private static String tripProtectionTrue = ".tripProtection input#insurancePurchaseCheckTrue";
    private static String tripProtectionFalse = ".tripProtection input#insurancePurchaseCheckFalse";

    public C3InsuranceFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector("img[alt='Trip Protection']"));
    }

    @Override
    public void proceed() {
        findOne("div#billingPanelOtherFeaturesComp-contentElementRefreshable input.continueBtn").click();
    }

    public C3AirBillingPage setInsurance(boolean withTripInsurance, boolean withCarInsurance) {

        if (withTripInsurance) {
            findOne(tripProtectionTrue).click();
        }
        else {
            findOne(tripProtectionFalse).click();
        }

        //if car insurance module exits
        try {
            if (withCarInsurance) {
                findOne(rentalCarProtectionTrue).click();
            }
            else {
                findOne(rentalCarProtectionFalse).click();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return this;
    }


}

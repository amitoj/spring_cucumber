/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.mailRebate;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-vyulun on 7/10/2014.
 */

public class C3mailInRebateSearchForm extends ToolsAbstractPage {

    public C3mailInRebateSearchForm(WebDriver webDriver) {
        super(webDriver, By.xpath("//input[@value='Add Record to Mail-in Rebate']"));
    }

    public void addRecordToMainInRebate(String itinerary, String epi,
                                        String rebateAmount, String code) {
        findOne("#email", DEFAULT_WAIT).clear();
        findOne("#email").sendKeys(itinerary);
       // findOne("#epiId").clear();
       // findOne("#epiId").sendKeys(epi);
        String statusCode = code;
        if (statusCode.equals("A")) {
            statusCode = "A -- Allowed";
        }
        else if (statusCode.equals("R")) {
            statusCode = "R -- Require Resubmission";
        }
        else {
            statusCode = "D -- Deny";
        }
        findOne("#statusCode").sendKeys(statusCode);
        findOne("#rebateAmount").clear();
        findOne("#rebateAmount").sendKeys(rebateAmount);
        findOne(By.xpath("//input[@value='Add Record to Mail-in Rebate']"), DEFAULT_WAIT).click();
    }
}

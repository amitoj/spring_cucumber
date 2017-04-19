/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.refund;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * User: v-ozelenov
 * Date: 12/7/13
 * Time: 1:10 AM
 * C3CarRefundPage  - contains methods that is different for car refunds.
 */
public class C3CarRefundPage extends C3RefundPage {

    public C3CarRefundPage(WebDriver webdriverInstance) {
        super(webdriverInstance);
    }

    public void fillPartialRefundValues() {
        WebElement daysNum = new WebDriverWait(getWebDriver(), 1)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("numberOfDays")));
        new Select(daysNum).selectByVisibleText("1");
    }
}

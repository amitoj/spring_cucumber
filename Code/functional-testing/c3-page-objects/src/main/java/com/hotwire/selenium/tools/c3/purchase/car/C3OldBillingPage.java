/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.purchase.car;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
Simplified Old Billing Page for C3
 */
public class C3OldBillingPage extends ToolsAbstractPage {
    public C3OldBillingPage(WebDriver webDriver) {
        super(webDriver, By.cssSelector("#agreementChecked"));
    }

    public WebElement getAgreementCheckBox() {
        return findOne("#agreementChecked", DEFAULT_WAIT);
    }

    public WebElement getBookTripButton() {
        return findOne(By.name("btnPurchase"), DEFAULT_WAIT);
    }
}

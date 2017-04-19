/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Payment fragment for logged user
 */
public class PaymentMethodFragmentUser extends AbstractPaymentMethodFragment {

    public PaymentMethodFragmentUser(WebDriver webdriver, String expectedPageName) {
        super(webdriver, expectedPageName);
        WebElement savedCreditCards = getWebDriver().findElement(By.id("savedCcList"));
        new Select(savedCreditCards).selectByVisibleText("Pay with new card");
    }
}

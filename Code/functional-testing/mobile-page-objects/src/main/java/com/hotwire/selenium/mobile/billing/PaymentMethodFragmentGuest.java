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
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Guset payment method fragment
 */
public class PaymentMethodFragmentGuest extends AbstractPaymentMethodFragment {

    @FindBy(id = "ccType")
    private WebElement ccType;

    @FindBy(className = "submit")
    private WebElement btnPaymentInfo;

    public PaymentMethodFragmentGuest(WebDriver webdriver, String expectedPageName) {
        super(webdriver, expectedPageName);
        List<WebElement> payWithNewCard = getWebDriver().findElements(By.id("newCCRadio"));
        if (!payWithNewCard.isEmpty() && payWithNewCard.size() == 1) {
            payWithNewCard.get(0).click();
        }
    }

    public void continuePanel() {
        btnPaymentInfo.click();
    }
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.discount;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

public class DiscountBillingErrorPage extends AbstractUSPage {

    @FindBy(css = "#billingPanelContainerComp .errorMessages")
    private WebElement discountText;

    public DiscountBillingErrorPage(final WebDriver webdriver) {
        super(webdriver, By.id("billingPanelContainerComp"));
    }

    public Boolean hasDiscountError() {
        try {
            return discountText.getText().contains("discount");
        }
        catch (NoSuchElementException e) {
            return discountText.getText().contains("discount");
        }
    }
}

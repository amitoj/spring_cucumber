/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.paypal;

import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author v-ozelenov
 * @since 2013.05
 */
public class PayPalDeveloperPage extends AbstractPage {

    @FindBy(xpath = "//b[contains(text(),'Log In with PayPal')]")
    private WebElement payPalLoginLink;

    public PayPalDeveloperPage(WebDriver webdriver) {
        super(webdriver, By.xpath("//img[@alt='PayPal Developer Beta']"));
    }

    public void clickOnPayPalLogIn() {
        payPalLoginLink.click();
    }
}

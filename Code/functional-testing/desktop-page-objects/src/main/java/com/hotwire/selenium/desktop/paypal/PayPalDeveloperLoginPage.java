/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.paypal;

import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author v-ozelenov
 * @since 2013.05
 */
public class PayPalDeveloperLoginPage extends AbstractPage {

    @FindBy(id = "email")
    private WebElement dEmail;

    @FindBy(id = "password")
    private WebElement dPass;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement confirm;

    public PayPalDeveloperLoginPage(WebDriver webdriver) {
        super(webdriver);
    }

    public void logIn(String email, String pass) {

        dEmail.clear();
        dEmail.sendKeys(email);

        dPass.clear();
        dPass.sendKeys(pass);

        confirm.click();
    }
}

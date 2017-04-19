/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.fragments;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 6/24/14
 * Time: 7:01 AM
 */
public class CarBillingLoginFormFragment extends AbstractPageObject {

    private static final By CONTAINER = By.cssSelector("div#loginForm #detailsLoginForm");

    @FindBy(name = "billingForm.loginForm._NAE_email")
    private WebElement loginUserEmail;

    @FindBy(name = "billingForm.loginForm._NAE_password")
    private WebElement loginUserPassword;

    @FindBy(css = "button[type=submit]")
    private WebElement submit;

    public CarBillingLoginFormFragment(WebDriver webdriver) {
        super(webdriver, CONTAINER);
    }

    public void login(String login, String password) {
        sendKeys(loginUserEmail, login);
        sendKeys(loginUserPassword, password);
        submit.click();
    }
}

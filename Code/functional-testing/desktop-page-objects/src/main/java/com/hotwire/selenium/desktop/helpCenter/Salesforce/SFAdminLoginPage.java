/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter.Salesforce;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-asnitko
 * Date: 9/2/13
 */
public class SFAdminLoginPage extends AbstractPageObject {

    private static final String SALESFORCE_USER = "v-ggavaleshko@hotwire.com.qa";
    private static final String SALESFORCE_PASS = "ALPHA @ | 9 alpha kilo ! + < JULIET UNIFORM alpha 0 7 @13";

    By inputUser = By.id("username");
    By inputPass = By.id("password");
    By btnLogin = By.id("Login");

    public SFAdminLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public SFAdminIndexPage loginToSalesForce() {
        getWebDriver().findElement(inputUser).sendKeys(SALESFORCE_USER);
        getWebDriver().findElement(inputPass).sendKeys(SALESFORCE_PASS);
        getWebDriver().findElement(btnLogin).click();
        return new SFAdminIndexPage(getWebDriver());
    }

}

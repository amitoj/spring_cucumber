/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.mobile.MobileAbstractPage;

/**
 * @author vjong
 */
public class MobileChangePasswordPage extends MobileAbstractPage {

    @FindBy(id = "currentPassword")
    private WebElement currentPassword;

    @FindBy(id = "newPassword")
    private WebElement newPassword;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPassword;

    @FindBy(css = ".btn")
    private WebElement continueButton;

    public MobileChangePasswordPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tile.account.password.reset"});
    }

    public void changePassword(String oldPass, String newPass) {
        currentPassword.clear();
        currentPassword.sendKeys(oldPass);
        newPassword.clear();
        newPassword.sendKeys(newPass);
        confirmPassword.clear();
        confirmPassword.sendKeys(newPass);
        continueButton.click();
    }
}

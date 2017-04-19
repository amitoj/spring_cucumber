/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.hotelextranet;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * @author adeshmukh
 *
 */
public class ExtranetChangePasswordPage extends AbstractUSPage {

    @FindBy(css = "input#oldPassword")
    private WebElement currentPassword;

    @FindBy(css = "input#newPassword")
    private WebElement newPassword;

    @FindBy(css = "input#confirmPassword")
    private WebElement confirmNewPassword;

    @FindBy(css = "button.btn.blueBt")
    private WebElement changePasswordButton;

    /**
     * @param webDriver
     */
    public ExtranetChangePasswordPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void changePassword(String old_password, String new_password) {
        currentPassword.clear();
        currentPassword.sendKeys(old_password);
        currentPassword.sendKeys(Keys.TAB);
        newPassword.clear();
        newPassword.sendKeys(new_password);
        newPassword.sendKeys(Keys.TAB);
        confirmNewPassword.clear();
        confirmNewPassword.sendKeys(new_password);
        changePasswordButton.click();
    }

}

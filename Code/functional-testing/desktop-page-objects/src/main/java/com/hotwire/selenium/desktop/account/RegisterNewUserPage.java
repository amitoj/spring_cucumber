/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * User: v-abudyak
 * Date: 4/6/12
 * Time: 6:33 PM
 */
public class RegisterNewUserPage extends AbstractPageObject {


    @FindBy(name = "firstName")
    public WebElement firstName;

    @FindBy(name = "lastName")
    public WebElement lastName;

    @FindBy(css = "[name='country'], select#countryCode")
    public WebElement countryCode;

    @FindBy(css = "[name='_NAE_zip'], input#postalCode")
    public WebElement zipCode;

    @FindBy(css = "[name='_NAE_email'], input#email")
    public WebElement email;

    // Old registration
    @FindBy(css = "[name='_NAE_confirmEmail']")
    public WebElement confirmEmail;

    @FindBy(css = "[name='_NAE_password'], input#password")
    public WebElement password;

    @FindBy(css = "[name='_NAE_confirmPassword'], input#confirmPassword")
    public WebElement confirmPassword;

    @FindBy(id = "dbmSubscription1")
    public WebElement mailSubscriptions;

    // New registration
    @FindBy(css = "input#privacyPolicyConfirm")
    public WebElement privacyConfirmCheckbox;

    @FindBy(css = "div#signUpBox button.btn, .registerModule button.btn")
    public WebElement registerNewUser;


    public RegisterNewUserPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div#signUpBox, .registerModule"));
    }

    public Object registerNewUser(String firstName, String lastName, String country, String zipCode,
                                  String email, String confirmEmail, String password, String confirmPassword) {

        //-----SPECS-----
        // Created Vladimir Y
        // 03/27/2012
        //---------------
        boolean isClassic = getWebDriver().findElements(By.cssSelector("div#signUpBox")).size() > 0;

        sendKeys(this.firstName, firstName);
        sendKeys(this.lastName, lastName);
        if (!"".equalsIgnoreCase(country)) {
            Select countrySelect = new Select(this.countryCode);
            countrySelect.selectByVisibleText(country);
        }
        sendKeys(this.zipCode, zipCode);
        sendKeys(this.email, email);
        if (isClassic) {
            sendKeys(this.confirmEmail, confirmEmail);
        }
        sendKeys(this.password, password);
        sendKeys(this.confirmPassword, confirmPassword);
        if (!isClassic) {
            this.privacyConfirmCheckbox.click();
        }

        this.registerNewUser.submit();
        return new Object();
    }

    public void clickRegisterNewUser() {
        registerNewUser.submit();
    }

    public RegisterNewUserPage setFirstName(String firstName) {
        sendKeys(this.firstName, firstName);
        return this;
    }

    public RegisterNewUserPage setLastName(String lastName) {
        sendKeys(this.lastName, lastName);
        return this;
    }

    public RegisterNewUserPage setCountryCode(String countryCode) {
        Select countrySelect = new Select(this.countryCode);
        countrySelect.selectByVisibleText(countryCode);
        return this;
    }

    public RegisterNewUserPage setEmail(String email) {
        sendKeys(this.email, email);
        return this;
    }

    public RegisterNewUserPage setPassword(String password) {
        sendKeys(this.password, password);
        return this;
    }

    public RegisterNewUserPage setConfirmPassword(String confirmPassword) {
        sendKeys(this.confirmPassword, confirmPassword);
        return this;
    }

    public RegisterNewUserPage setZipCode(String zipCode) {
        sendKeys(this.zipCode, zipCode);
        return this;
    }

    public String getRegistrationPageURL() {
        return getWebDriver().getCurrentUrl();
    }

    public RegisterNewUserPage checkPrivacyConfirmCheckbox(Boolean checked) {

        if (isCheckBoxChecked(privacyConfirmCheckbox) && checked) {
            return this;
        }
        else {
            privacyConfirmCheckbox.click();
            return this;
        }
    }

    public RegisterNewUserPage checkMailSubscription(Boolean checked) {
        try {
            if (isCheckBoxChecked(mailSubscriptions) && checked) {
                return this;
            }
            else {
                mailSubscriptions.click();
                return this;
            }
        }
        catch (WebDriverException e) {
            return this;
        }
    }

    private Boolean isCheckBoxChecked(WebElement checkBox) {
        try {
            return checkBox.getAttribute("checked").equals("true");
        }
        catch (NullPointerException e) {
            return false;
        }
    }
}

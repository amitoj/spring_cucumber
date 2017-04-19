/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.account;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by IntelliJ IDEA.
 * User: v-vyulun
 * Date: 3/27/12
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileRegisterNewUserPage extends MobileAbstractPage {


    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "countryCode")
    private WebElement countryCode;

    @FindBy(id = "postalCode")
    private WebElement zipCode;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "confirmEmail")
    private WebElement confirmEmail;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPassword;


    @FindBy(name = "dbmSubscription")
    private WebElement subsRadioButton;

    @FindBy(xpath = "//form//button")
    private WebElement registerNewUser;

    @FindBy(partialLinkText = "Already a member? Sign in now")
    private WebElement alreadyMember;

    public MobileRegisterNewUserPage(WebDriver webdriver) {
        super(webdriver);
    }

    public void registerNewUser() {
        this.registerNewUser.click();
    }

    public MobileRegisterNewUserPage withFirstName(String firstName) {
        this.firstName.click();
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
        return this;
    }

    public MobileRegisterNewUserPage withLastName(String lastName) {
        this.lastName.click();
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
        return this;
    }

    public MobileRegisterNewUserPage withCountryCode(String countryCode) {
        Select countryList = new Select(this.countryCode);
        countryList.selectByVisibleText(countryCode);
        return this;
    }

    public MobileRegisterNewUserPage withZipCode(String zipCode) {
        this.zipCode.click();
        this.zipCode.clear();
        this.zipCode.sendKeys(zipCode);
        return this;
    }

    public MobileRegisterNewUserPage withEmail(String email) {
        this.email.click();
        this.email.clear();
        this.email.sendKeys(email);
        return this;
    }

    public MobileRegisterNewUserPage withConfirmEmail(String confirmEmail) {
        this.confirmEmail.click();
        this.confirmEmail.clear();
        this.confirmEmail.sendKeys(confirmEmail);
        return this;
    }

    public MobileRegisterNewUserPage withPassword(String password) {
        this.password.click();
        this.password.clear();
        this.password.sendKeys(password);
        return this;
    }

    public MobileRegisterNewUserPage withConfirmPassword(String confirmPassword) {
        this.confirmPassword.click();
        this.confirmPassword.clear();
        this.confirmPassword.sendKeys(confirmPassword);
        return this;
    }
}

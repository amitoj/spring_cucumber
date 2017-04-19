/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.account;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * SignIn page class for mobile app
 */
public class SignInPage extends MobileAbstractPage {

    @FindBy(id = "email")
    public WebElement username;

    @FindBy(css = "#password, #psswd")
    public WebElement password;

    @FindBy(xpath = "//button[@data-bdd='continue' or @type='submit']")
    public WebElement submitButton;

    @FindBy(className = "passwdAssist")
    public WebElement passwordAssistance;

    // the css selector for .btns.a .btn.d allow this to run in production and needs to be removed in the future
    @FindBy(css = ".register, .btns.a .btn.d")
    private WebElement registerNewUser;

    public SignInPage(WebDriver webdriver) {
        super(webdriver, "tile.account.signin");
    }

    public void signIn() {
        this.submitButton.click();
    }

    public SignInPage withUserName(String username) {
        this.username.click();
        this.username.clear();
        this.username.sendKeys(username);
        return this;
    }

    public SignInPage withPassword(String password) {
        this.password.click();
        this.password.clear();
        this.password.sendKeys(password);
        return this;
    }

    public MobileRegisterNewUserPage goToCreateNewUser() {
        this.registerNewUser.click();
        MobileRegisterNewUserPage mRNU = new MobileRegisterNewUserPage(getWebDriver());
        return mRNU;
    }

    /**
     * or gives you a chance to inspect error strings.
     *
     * @return
     */
    public boolean hasAuthenticationError() {
        return assertHasFormErrors("Please correct the following:") &&
                hasAuthenticationErrorOnUsername();
    }


    public boolean hasAuthenticationErrorOnUsername() {
        return assertHasFormErrors("The email address or password is incorrect.") ||
                assertHasFormErrors("Email address is not valid.");
    }

    public boolean hasAuthenticationErrorOnPassword() {
        return assertHasFormErrors("The email address or password is incorrect.") ||
                assertHasFormErrors("Password must contain 6-30 characters and no spaces. It is case-sensitive.");
    }

}

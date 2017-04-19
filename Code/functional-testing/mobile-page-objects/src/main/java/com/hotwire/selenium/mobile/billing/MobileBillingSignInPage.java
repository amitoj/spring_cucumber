/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.billing;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by IntelliJ IDEA. User: v-vyulun Date: 4/6/12 Time: 7:34 PM To change this template use File | Settings |
 * File Templates.
 */
public class MobileBillingSignInPage extends MobileAbstractPage {

    @FindBy(xpath = "//*[contains(text(), 'Continue as guest')]/..")
    private WebElement continueAsGuestBtn;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement email;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;

    @FindBy(xpath = "//div[@class= 'btns d']//*[contains(text(), 'Sign in')]/..")
    private WebElement signInBtn;

    @FindBy(css = "header[role='banner'] span a")
    private WebElement backButton;

    public MobileBillingSignInPage(WebDriver driver) {
        super(driver, new String[]{"tile.billing.signIn"});
    }

    public MobileBillingPg continueAsUser(String email, String password) {
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        this.signInBtn.click();
        return new MobileBillingPg(getWebDriver());

    }

    public MobileBillingPg continueAsGuest() {
        this.continueAsGuestBtn.click();
        return new MobileBillingPg(getWebDriver());
    }

    public MobileCarBillingPage continueCarPurchaseAsUser(String email, String password) {
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        this.signInBtn.click();
        return new MobileCarBillingPage(getWebDriver());

    }

    public MobileCarBillingPage continueCarPurchaseAsGuest() {
        this.continueAsGuestBtn.click();
        return new MobileCarBillingPage(getWebDriver());
    }

    public void clickBackButton() {
        backButton.click();
    }

}

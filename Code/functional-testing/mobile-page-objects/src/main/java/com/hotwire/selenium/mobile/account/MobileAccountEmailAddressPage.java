/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.account;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.mobile.MobileAbstractPage;
/**
 * Created by IntelliJ IDEA.
 * User: v-vyulun
 * Date: 3/29/12
 * Time: 1:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileAccountEmailAddressPage extends MobileAbstractPage {

    @FindBy (id = "currentEmail")
    private WebElement currentEmail;

    @FindBy (id = "email")
    private WebElement newEmail;

    @FindBy (id = "confirmEmail")
    private WebElement confirmEmail;

    @FindBy (css = ".btn.b.btn")
    private WebElement saveChanges;

    public MobileAccountEmailAddressPage(WebDriver webdriver) {
        super(webdriver);
    }

    public void checkEmail(String expectedEmail) {
        assertThat(expectedEmail.toLowerCase().equals(currentEmail.getAttribute("value").toLowerCase())).isTrue();
    }

    public void changeEmail(String currEmail, String nEmail) {
        currentEmail.clear();
        currentEmail.sendKeys(currEmail);
        newEmail.sendKeys(nEmail);
        confirmEmail.sendKeys(nEmail);
        saveChanges.submit();

    }

    public boolean isEmailBeenChanged() {
        return
        getWebDriver()
            .findElement(By.xpath("//*[contains(text(), 'Your email address has been reset')]")).isDisplayed();
    }
}

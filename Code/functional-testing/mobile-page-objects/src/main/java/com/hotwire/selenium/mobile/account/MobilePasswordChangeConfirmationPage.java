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
import com.hotwire.selenium.mobile.MobileHotwireHomePage;

/**
 * @author vjong
 *
 */
public class MobilePasswordChangeConfirmationPage extends MobileAbstractPage {

    @FindBy(css = ".btn")
    private WebElement continueButton;

    public MobilePasswordChangeConfirmationPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tile.account.password-change-confirm"});
    }

    public MobileHotwireHomePage clickContinue() {
        continueButton.click();
        return new MobileHotwireHomePage(getWebDriver());
    }
}

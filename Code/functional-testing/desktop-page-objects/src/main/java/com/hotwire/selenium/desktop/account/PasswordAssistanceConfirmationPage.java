/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vyulun
 * Date: 11/27/13
 * Time: 6:38 AM
 */
public class PasswordAssistanceConfirmationPage extends AbstractPageObject {

    @FindBy(xpath = "//div[@class='passwdConfirm']//div[contains(text(),'Please check for an email from us')]")
    private WebElement msgSuccessful;

    public PasswordAssistanceConfirmationPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tile.account.forgot-password-confirm"});
    }

    public boolean isMsgSuccessful() {
        return msgSuccessful.isDisplayed();
    }

}

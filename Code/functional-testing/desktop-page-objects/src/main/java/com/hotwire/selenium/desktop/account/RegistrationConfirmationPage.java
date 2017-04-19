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

/**
 * User: v-vzyryanov
 * Date: 9/22/14
 * Time: 12:26 AM
 */
public class RegistrationConfirmationPage extends AbstractPageObject {

    public RegistrationConfirmationPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tile.account.registration-confirm"});
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(".registerConfirm .buttons a");
    }
}

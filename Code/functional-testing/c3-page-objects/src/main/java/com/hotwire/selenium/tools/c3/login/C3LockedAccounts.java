/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.login;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object
 */
public class C3LockedAccounts extends ToolsAbstractPage {
    public C3LockedAccounts(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.accountsContainer"));
    }

    public void clickUnlock() {
        clickOnLink("Unlock", DEFAULT_WAIT);
    }
}

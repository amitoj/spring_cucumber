/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.WebDriver;


/**
 * User: jreichenberg
 * Date: 2/1/12
 * Time: 4:11 PM
 */
public class AbstractAccountPage extends AbstractPageObject {

    public AbstractAccountPage(WebDriver webdriver, String expectedPageName) {
        super(webdriver, new String[]{expectedPageName});
    }

    public AccountTopNavBar getAccountNav() {
        return new AccountTopNavBar(getWebDriver());
    }
}

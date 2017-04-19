/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.dmu;


import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * DMU Index Page
 */
public class DMUIndexPage extends ToolsAbstractPage {
    public DMUIndexPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.mainMenuContainer"));
    }

    public String getInvitationMsg() {
        return findOne("h1").getText();
    }

    public void selectMenu(String menuItem) {
        clickOnLink(menuItem, DEFAULT_WAIT);
    }
}

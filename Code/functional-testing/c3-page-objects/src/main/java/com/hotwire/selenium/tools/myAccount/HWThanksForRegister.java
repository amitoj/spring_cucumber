/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.myAccount;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 12/18/14
 * Time: 12:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class HWThanksForRegister extends ToolsAbstractPage {
    /**
     * Major Page Object constructor.
     * webdriver - gets from getter getWebDriverInstance() in WebDriverAwareModel
     * keyPageElement - element defined that page is loaded.
     */
    public HWThanksForRegister(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.registerConfirm"));
    }

    public String getTitle() {
        return findOne("div.registerConfirm div.title").getText().trim();
    }

    public String getMsg() {
        return findOne("div.registerConfirm p").getText().trim();
    }

    public void proceed() {
        findOne("div.registerConfirm div.buttons a").click();
    }
}

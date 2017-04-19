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
* Login page to C3 admin
 */
public class C3LoginPage extends ToolsAbstractPage {

    private static final String SUBMIT_BTN = "input[value='Login']";
    private static final String USER_FIELD = "input#userName";
    private static final String PASSWORD_FIELD = "input#password";

    public C3LoginPage(WebDriver webDriver) {
        super(webDriver, By.id("loginPage"));
    }

    public void login(String username, String password) {
        setText(USER_FIELD, username);
        setText(PASSWORD_FIELD, password);
        findOne(SUBMIT_BTN).click();
    }

    public String getValidationMsg() {
        return findOne("div.msgBoxBody ul li", DEFAULT_WAIT).getText();
    }


    public String getEmailFormat() {
        return findOne(USER_FIELD).getAttribute("class");
    }

    public String getPasswordFormat() {
        return findOne(PASSWORD_FIELD).getAttribute("class");
    }

}

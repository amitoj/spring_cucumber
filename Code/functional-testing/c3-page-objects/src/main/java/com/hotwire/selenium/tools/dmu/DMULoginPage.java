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
 * Database management utility login page
 */
public class DMULoginPage extends ToolsAbstractPage {
    public DMULoginPage(WebDriver webdriver) {
        super(webdriver, By.name("loginForm"));
    }

    public void setUsername(String text) {
        setText("input[name='username']", text);
    }

    public void setPass(String text) {
        setText("input[name='password']", text);
    }

    public void clickSubmit() {
        findOne("input[name='submit']").click();
    }

    public void login(String username, String password) {
        setUsername(username);
        setPass(password);
        clickSubmit();
    }

    public String getErrorMsg() {
        return findOne("font[color='red']", EXTRA_WAIT).getText();
    }

    public String getErrorMsgItems() {
        return findOne("ul", EXTRA_WAIT).getText();
    }
}

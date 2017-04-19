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
public class CSRUnlockForm extends ToolsAbstractPage {
    public CSRUnlockForm(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.createCustomerContainer"));
    }

    public void setNewPassword(String text) {
        setText("input[name='newPassword']", text);
    }

    public void setConfirmPassword(String text) {
        setText("input[name='newPasswordConfirmation']", text);
    }

    public void clickSubmit() {
        findOne("div.createCustomerContainer button").click();
    }

    public String getErrorMessage() {
        return findOne("div.errorBox", DEFAULT_WAIT).getText();
    }
}

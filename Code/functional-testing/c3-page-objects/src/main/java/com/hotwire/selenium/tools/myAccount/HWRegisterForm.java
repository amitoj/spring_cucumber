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
 * User: v-ozelenov
 * Date: 12/11/14
 * Time: 7:57 AM
 * Simplified Hotwire register page objects for Tools specific cases
 */
public class HWRegisterForm extends ToolsAbstractPage {
    /**
     * Major Page Object constructor.
     * webdriver - gets from getter getWebDriverInstance() in WebDriverAwareModel
     * keyPageElement - element defined that page is loaded.
     */
    public HWRegisterForm(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.registerModule"));
    }

    public void setCustomerNames(String fn, String ln) {
        setText("input#firstName", fn);
        setText("input#lastName", ln);
    }

    public void setCountry(String countryCode) {
        selectValue("select#countryCode", countryCode);
    }

    public void setPostalCode(String postalCode) {
        setText("input#postalCode", postalCode);
    }

    public void setEmail(String email) {
        setText("div.registerModule input#email", email);
    }

    public void setPassword(String pass) {
        setText("div.registerModule input#password", pass);
        setText("div.registerModule input#confirmPassword", pass);
    }

    public void submit() {
        findOne("input#privacyPolicyConfirm").click();
        findOne("div.registerModule div.buttons button").click();
    }
}

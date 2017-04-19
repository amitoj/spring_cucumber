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
 * Date: 12/11/14
 * Time: 7:18 AM
 * Simplified HWMyAccount Page Object
 */
public class HWMyAccount extends ToolsAbstractPage {

    public HWMyAccount(WebDriver webdriver) {
        super(webdriver, By.cssSelector("ul.accountNavClass"));
    }

    public void openPaymentInfo() {
        findOne("a[href$='/account/payment-info']").click();
    }
}

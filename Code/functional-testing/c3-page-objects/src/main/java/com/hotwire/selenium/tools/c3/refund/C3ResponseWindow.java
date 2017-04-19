/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.refund;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 10/23/14
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class C3ResponseWindow extends ToolsAbstractPage {
    private static String continueBtn = "input#continueId";
    private static String okBtn = "#refundResponse input[name='ok']";
    private static String cancelBtn = "#refundResponse input[name='cancel']";
    private static String anyOfBtn = "span[id='refundResponse'] input[name='cancel'], " +
            "span[id='refundResponse'] input[name='ok'], " +
            "span[id='refundResponse'] input[name='continue']";

    public C3ResponseWindow(WebDriver webdriver) {
        super(webdriver, By.cssSelector("#refundResponse"));
    }


    public String getTextOfConfirmRefundPopUp() {
        return findOne("#refundResponse", EXTRA_WAIT).getText();
    }

    public void confirmRefundPopUp(Double amount, String currency) {
        String response = findOne("#refundResponse", EXTRA_WAIT).getText();
        Assertions.assertThat(response.replace(",", "")).contains(currency).contains(amount.toString());
        findOne(continueBtn, EXTRA_WAIT).click();
    }

    public void clickOkOnRefundPopUp() {
        findOne(okBtn, EXTRA_WAIT).click();
    }

    public void closeWindow() {
        findOne(anyOfBtn).click();
    }
}

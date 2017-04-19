/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.refund;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 11/1/12
 * Time: 6:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3RefundDeterminationPage extends ToolsAbstractPage {

    private static final String REFUND_BTN = "input.displayButton[name='refund']";
    private static final String WORKFLOW_BTN = "input.displayButton[name='workflow']";

    public C3RefundDeterminationPage(WebDriver webDriver) {
        super(webDriver, By.cssSelector(REFUND_BTN));
    }

    public void clickRefund() {
        findOne(REFUND_BTN, EXTRA_WAIT).click();
    }

    public void clickCreateWorkFlow() {
        findOne(WORKFLOW_BTN, EXTRA_WAIT).click();
    }
}

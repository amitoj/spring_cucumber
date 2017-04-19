/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.riskManagement;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-vyulun on 6/25/2014.
 */

public class C3ClearOldTransactionsSearchForm extends ToolsAbstractPage {
    public C3ClearOldTransactionsSearchForm(WebDriver webDriver) {
        super(webDriver, By.name("clearOldTransactionsButton"));
    }



    public void clickClearOldTransactionButton() {
        findOne(By.name("clearOldTransactionsButton"),  DEFAULT_WAIT).click();
    }

    public void clearOldTransactions(int daysBefore, Integer amount) {
        selectDate("input#clearToDateEntry-field", getDateBefore(daysBefore));
        findOne("#clearBelowAmount", DEFAULT_WAIT).clear();
        findOne("#clearBelowAmount").sendKeys(amount.toString());
        this.clickClearOldTransactionButton();
    }
}

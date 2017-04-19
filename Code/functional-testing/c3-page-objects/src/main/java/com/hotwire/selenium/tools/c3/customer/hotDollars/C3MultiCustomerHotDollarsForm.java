/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.hotDollars;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Date;

/**
 * Page Object
 */
public class C3MultiCustomerHotDollarsForm extends ToolsAbstractPage {
    public C3MultiCustomerHotDollarsForm(WebDriver webdriver) {
        super(webdriver, By.id("massAddHotdollarsFormContainer"));
    }

    public void setEmails(String text) {
        setText("textarea#emailAddresses", text);
    }

    public void setCaseNotes(String text) {
        setText("textarea#caseNotes", text);
    }

    public void setHotDollarsAmount(String text) {
        setText("input[name='inputAmount']", text);
    }

    public void selectReason(String value) {
        selectValue("select#editReason", value);
    }

    public void submit() {
        findOne("input[name='save']").click();
    }

    public String getConfirmationMessage() {
        return findOne("div.successBox").getText();
    }

    public void setExpirationDate(Integer yearsToExpire) {
        selectDate("input[name='expirationDate']", getExpirationDate(yearsToExpire));
    }

    public Date getExpirationDate(Integer yearsToExpire) {
        return getDateAfter(360 * yearsToExpire);
    }

    public void unCheckUserNotification() {
        findOne("input[name='shouldNotifyUser']").click();
    }
}


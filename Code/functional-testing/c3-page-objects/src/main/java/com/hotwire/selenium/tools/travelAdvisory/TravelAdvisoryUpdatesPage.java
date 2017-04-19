/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.travelAdvisory;

import com.hotwire.selenium.desktop.row.elements.Checkbox;
import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * User: v-vzyryanov
 * Date: 9/26/13
 * Time: 4:12 AM
 * Page for travel advisories manipulation in C3.
 */
public class TravelAdvisoryUpdatesPage extends ToolsAbstractPage {
    private int index;

    public TravelAdvisoryUpdatesPage(WebDriver webDriver) {
        super(webDriver, By.className("formPrimaryHeader"));
    }

    public void removeIssue(int index) {
        findMany("input[value='REMOVE NOW']").get(index).click();
    }

    public void publishIssue(int index) {
        findMany("input[value='PUBLISH']").get(index).click();
    }

    public String getMsg() {
        return findOne("td#errorMessaging", EXTRA_WAIT).getText();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private void setIssueField(String fieldName, String text) {
        final WebElement webElement = findMany(By.name(fieldName)).get(index);
        webElement.clear();
        webElement.sendKeys(text);
    }

    public void setTitle(String title) {
        setIssueField("title", title);
    }

    public void setMessage(String msg) {
        setIssueField("travelAlertMessage", msg);
    }

    public void setDateUpdated(String date) {
        setIssueField("updatedDate", date);
    }

    public void setExpirationDate(String date) {
        setIssueField("expiryDate", date);
    }

    private void setCheckBox(String name, boolean value) {
        final WebElement webElement = findMany(By.name(name)).get(index);
        new Checkbox(webElement).check();
        if (value) {
            new Checkbox(webElement).check();
        }
        else {
            new Checkbox(webElement).uncheck();
        }
    }

    public void setDomesticCheckBox(boolean value) {
        setCheckBox("isAlertForDomestic", value);
    }

    public void setIntlCheckBox(boolean value) {
        setCheckBox("isAlertForInternational", value);
    }

}

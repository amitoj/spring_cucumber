/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-sshubey on 10/15/2014.
 */
public class C3CustomerMainPage extends ToolsAbstractPage {

    public C3CustomerMainPage(WebDriver webDriver) {
        super(webDriver, By.cssSelector("table.infobar"));
        waitProgressBar();
    }

    public void choosePastBooking() {
        findOne(By.xpath("//a[contains(text(), 'View past bookings') or contains(text(), 'View Past Bookings')]"))
                .click();
    }

    public void chooseRecentSearches() {
        clickOnLink("View recent searches", DEFAULT_WAIT);
    }

    public void clickEditAccInfo() {
        clickOnLink("Edit account information", DEFAULT_WAIT);
    }

    public void clickEditCustomerCaseHistory() {
        clickOnLink("Edit customer case history", DEFAULT_WAIT);
    }

    public void clickOnCreateWorkFlow() {
        clickOnLink("Create a Workflow entry for Itinerary", DEFAULT_WAIT);
    }

    public void clickOnBlockedHotels() {
        clickOnLink("View Blocked Hotels", DEFAULT_WAIT);
    }


    public void startSearchByReferenceNumber(String referenceNumber) {
        findOne("#searchReferenceNumber", DEFAULT_WAIT).sendKeys(referenceNumber);
        findOne("#submitBtnContainerForSearchReferenceNumberQueryForm", DEFAULT_WAIT).click();
    }

    public void clickSiteSearchLink() {
        findOne(By.xpath(".//form[@name='search']/input[@value='GO']"), DEFAULT_WAIT).click();
    }

    public String getReferenceNumberError() {
        return findOne(".errorMessages.refSrcFormErrBox>ul>li", DEFAULT_WAIT).getText();
    }
}

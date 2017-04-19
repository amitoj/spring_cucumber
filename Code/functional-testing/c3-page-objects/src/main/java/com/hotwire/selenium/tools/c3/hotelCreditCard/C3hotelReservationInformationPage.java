/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotelCreditCard;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-vyulun on 7/11/2014.
 */

public class C3hotelReservationInformationPage extends ToolsAbstractPage {

    public C3hotelReservationInformationPage(WebDriver webDriver) {
        super(webDriver, By.cssSelector("#faxRecipient"));
    }

    public void clickTotalCharges() {
        findOne(By.xpath("//tr[14]//td[@class='infobarValue'][2]//a"), DEFAULT_WAIT).click();
    }

    public void modifyCreditCard() {
        findOne(By.xpath("//input[@value='Modify Credit Card'][1]"), DEFAULT_WAIT).click();
    }

    public void updateTaxRate() {
        findOne(By.xpath("//input[@value='Update Tax Rates'][1]"), DEFAULT_WAIT).click();
    }

    public void clickMCCGroupLink() {
        findOne(By.xpath("//tr[12]//td[@class='infobarValue'][2]//a"), DEFAULT_WAIT).click();
    }

    public void changeFaxInfo(String name, String number) {
        findOne("#faxRecipient", DEFAULT_WAIT).clear();
        findOne("#faxRecipient", DEFAULT_WAIT).sendKeys(name);
        findOne("#faxNumber").clear();
        findOne("#faxNumber").sendKeys(number);
        findOne(By.xpath("//input[@value='send Fax to Hotel']")).click();
    }

    public String getGuestName() {
        return findOne(By.id("TESTID_guestName"), DEFAULT_WAIT).getText();
    }
}


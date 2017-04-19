/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Date;

/**
 * Page Object
 */
public class C3HotelFinanceSearchPage extends ToolsAbstractPage {
    public C3HotelFinanceSearchPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector(".formField.lastName>input"));
    }

    public void searchByDates(Date from, Date to) {
        selectDate("#minDateHotelSearch-field", from);
        selectDate(By.name("toDate"), to);
        findMany(".formButton").get(1).click(); //second button
    }

    public void searchByConfNum(String itineraryNumber) {
        findOne(By.name("hwItineraryNumber"), DEFAULT_WAIT).sendKeys(itineraryNumber);
        findMany(".formButton").get(0).click(); //first button
    }

    public void updateHotelTaxRates() {
        clickOnLink("Show", DEFAULT_WAIT);
        findOne(".formField.taxPercentage>input", DEFAULT_WAIT).sendKeys("3");
        findOne(".formField.taxPerNight>input", DEFAULT_WAIT).sendKeys("2");
        findOne(".formField.taxPerStay>input", DEFAULT_WAIT).sendKeys("1");
        findOne(".formField.notes>textarea", DEFAULT_WAIT).sendKeys("RTC-4344 test note");
        findOne(".formButton.singleButton").click();
    }

}

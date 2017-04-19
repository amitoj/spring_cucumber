/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car.fragments.fareFinder;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: v-vzyryanov
 * Date: 4/9/13
 * Time: 3:33 AM
 */
public class CcfCarFareFinder extends CarFareFinderTemplate {
    private static final String CCF_DATE_FORMAT_PATTERN = "M/d/yy";
    private static final String IDENTIFIER = "div.content form[action$='/car/launchsearch']";
    private static final String DEFAULT_SELECTED_TIME = "";

    @FindBy(name = "pickupLocation")
    private WebElement cCFPickupLocation;

    @FindBy(name = "dropOffLocation")
    private WebElement dropOffLocation;

    @FindBy(name = "startDate")
    private WebElement startDate;

    @FindBy(name = "endDate")
    private WebElement endDate;

    @FindBy(id = "pickupTime-button")
    private WebElement ccfPickupTime;

    @FindBy(id = "dropOffTime-button")
    private WebElement ccfDropOffTime;

    @FindBy(css = "div.searchBtn button.seleniumContinueButton")
    private WebElement search;

    public CcfCarFareFinder(WebDriver webDriver) {
        super(webDriver);
    }

    public void run() {
        search.click();
    }

    @Override
    public CarFareFinder setPickUpLocation(String value) {
        setText(cCFPickupLocation, value);
        return this;
    }

    @Override
    public CarFareFinder setDropOffLocation(String value) {
        setText(dropOffLocation, value);
        return this;
    }

    @Override
    public CarFareFinder setPickUpDate(Date date) {
        setText(startDate, (new SimpleDateFormat("MM/dd/yy")).format(date) + Keys.TAB);
        return this;
    }

    @Override
    public CarFareFinder setDropOffDate(Date date) {
        setText(endDate, (new SimpleDateFormat("MM/dd/yy")).format(date) + Keys.TAB);
        return this;
    }

    @Override
    public CarFareFinder setPickUpTime(String time) {
        ccfPickupTime.click();
        ccfPickupTime.sendKeys(Keys.PAGE_DOWN);
        String transformed = (time == null) ? "noon" : time.replace("am", " AM").replace("pm", " PM");
        getWebDriver().findElement(By.xpath("//ul[@id='pickupTime-menu']" +
                "//a[contains(text(), '" + transformed + "')]")).click();
        return this;
    }

    @Override
    public CarFareFinder setDropOffTime(String time) {
        ccfDropOffTime.click();
        ccfDropOffTime.sendKeys(Keys.PAGE_DOWN);
        String transformed = (time == null) ? "noon" : time.replace("am", " AM").replace("pm", " PM");
        getWebDriver().findElement(By.xpath("//ul[@id='dropOffTime-menu']" +
                "//a[contains(text(), '" + transformed + "')]")).click();
        return this;
    }

    @Override
    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(CCF_DATE_FORMAT_PATTERN);
    }

    private void setText(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
    }

    @Override
    public String getPickUpLocation() {
        return cCFPickupLocation.getAttribute("value");
    }

    @Override
    public String getPickUpTime() {
        return ccfPickupTime.getText();
    }

    @Override
    public String getDropOffTime() {
        return ccfDropOffTime.getText();
    }
}

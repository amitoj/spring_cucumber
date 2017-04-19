/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car.fragments.fareFinder;


import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * User: v-vzyryanov
 * Date: 12/20/12
 * Time: 1:17 AM
 */
public class CarFareFinderTemplate extends AbstractUSPage implements CarFareFinder {
    private static final String DATE_FORMAT_PATTERN = "MM/dd/yy";
    //private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT_PATTERN);
    private static final List<String> EXPECTED_LIST_OF_TIME = Arrays.asList("7:00am", "7:30am", "8:00am",
            "8:30am", "9:00am", "9:30am", "10:00am", "10:30am", "11:00am",
            "11:30am", "noon", "12:30pm", "1:00pm", "1:30pm", "2:00pm", "2:30pm",
            "3:00pm", "3:30pm", "4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm",
            "6:30pm", "7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm",
            "10:00pm", "10:30pm", "11:00pm", "11:30pm", "midnight", "12:30am",
            "1:00am", "1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am",
            "4:30am", "5:00am", "5:30am", "6:00am", "6:30am");

    @FindBy(id = "carRoundTrip")
    WebElement isRoundTrip;

    @FindBy(id = "carOneWay")
    WebElement isOneWay;

    @FindBy(name = "startLocation")
    WebElement pickUpLocation;

    @FindBy(id = "carEndLocation")
    WebElement dropOffLocation;

    @FindBy(name = "startDate")
    WebElement pickUpDate;

    @FindBy(name = "endDate")
    WebElement dropOffDate;

    @FindBy(css = "select[name=startTime] option[selected=selected]")
    WebElement pickUpTime;

    @FindBy(css = "select[name=endTime] option[selected=selected]")
    WebElement dropOffTime;

    @FindBy(css = "div.FareFinderComp button[type=submit]")
    WebElement searchBtn;

    public CarFareFinderTemplate(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getPickUpLocation() {
        return pickUpLocation.getAttribute("value");
    }

    @Override
    public String getDropOffLocation() {
        return dropOffLocation.getAttribute("value");
    }

    @Override
    public String getPickUpDate() {
        return pickUpDate.getAttribute("value");
    }

    @Override
    public String getDropOffDate() {
        return dropOffDate.getAttribute("value");
    }

    @Override
    public String getPickUpDatePlaceholder() {
        return pickUpDate.getAttribute("placeholder");
    }

    @Override
    public String getDropOffDatePlaceholder() {
        return dropOffDate.getAttribute("placeholder");
    }

    @Override
    public String getPickUpTime() {
        return pickUpTime.getText();
    }

    @Override
    public String getDropOffTime() {
        return dropOffTime.getText();
    }

    @Override
    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT_PATTERN);
    }

    public boolean isOneWay() {
        return isOneWay.isSelected();
    }

    @Override
    public CarFareFinder setPickUpLocation(String location) {
        pickUpLocation.clear();
        pickUpLocation.sendKeys(location);
        return this;
    }

    @Override
    public CarFareFinder setDropOffLocation(String location) {
        if (StringUtils.isNotEmpty(location)) {
            isOneWay.click();
            dropOffLocation.clear();
            dropOffLocation.sendKeys(location);
        }
        return this;
    }

    @Override
    public CarFareFinder setPickUpDate(Date date) {
        pickUpDate.clear();
        pickUpDate.click();
        pickUpDate.sendKeys((new SimpleDateFormat("MM/dd/yy")).format(date));
        return this;
    }

    @Override
    public CarFareFinder setDropOffDate(Date date) {
        dropOffDate.clear();
        dropOffDate.click();
        dropOffDate.sendKeys((new SimpleDateFormat("MM/dd/yy")).format(date));
        return this;
    }

    @Override
    public void run() {
        searchBtn.click();
    }

    @Override
    public CarFareFinder setDropOffTime(String time) {
        dropOffTime.sendKeys(time);
        return this;
    }

    @Override
    public CarFareFinder setPickUpTime(String time) {
        pickUpTime.sendKeys(time);
        return this;
    }

    public List<String> getExpectedListOfTime() {
        return EXPECTED_LIST_OF_TIME;
    }

    @Override
    public List<String> getListOfTimes(String type) {
        String selector;
        if (type.equals("drop off")) {
            selector = "endTime";
        }
        else {
            selector = "startTime";
        }

        getWebDriver().findElement(By.cssSelector("div.field." + selector + " span.ui-selectmenu-button a")).click();
        List<WebElement> times = getWebDriver().findElements(By.cssSelector("div.ui-selectmenu-menu li a"));
        List<String> stringList = new ArrayList<String>();
        for (int i = 0; i < 48; i++) {
            if (!times.get(i).getText().equals("")) {
                stringList.add(times.get(i).getText());
            }
        }
        assertThat(stringList.size()).as("Times count in " + type + " list isn't 48")
                .isEqualTo(48);
        return stringList;
    }
}

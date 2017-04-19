/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.search;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.widget.DatePicker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mobile car search fragment
 */
public class CarSearchFragment extends MobileAbstractPage {
    public static final Map<String, String> TIME = new HashMap<String, String>() {
        {
            put("30", "12:30am");
            put("100", "1:00am");
            put("130", "1:30am");
            put("200", "2:00am");
            put("230", "2:30am");
            put("300", "3:00am");
            put("330", "3:30am");
            put("400", "4:00am");
            put("430", "4:30am");
            put("500", "5:00am");
            put("530", "5:30am");
            put("600", "6:00am");
            put("630", "6:30am");
            put("700", "7:00am");
            put("730", "7:30am");
            put("800", "8:00am");
            put("830", "8:30am");
            put("900", "9:00am");
            put("930", "9:30am");
            put("1000", "10:00am");
            put("1030", "10:30am");
            put("1100", "11:00am");
            put("1130", "11:30am");
            put("1200", "Noon");
            put("1230", "12:30pm");
            put("1300", "1:00pm");
            put("1330", "1:30pm");
            put("1400", "2:00pm");
            put("1430", "2:30pm");
            put("1500", "3:00pm");
            put("1530", "3:30pm");
            put("1600", "4:00pm");
            put("1630", "4:30pm");
            put("1700", "5:00pm");
            put("1730", "5:30pm");
            put("1800", "6:00pm");
            put("1830", "6:30pm");
            put("1900", "7:00pm");
            put("1930", "7:30pm");
            put("2000", "8:00pm");
            put("2030", "8:30pm");
            put("2100", "9:00pm");
            put("2130", "9:30pm");
            put("2200", "10:00pm");
            put("2230", "10:30pm");
            put("2300", "11:00pm");
            put("2330", "11:30pm");
            put("0", "Midnight");
        }
    };

    @FindBy(xpath = "//*[@data-bdd='carDropoffDate']")
    protected WebElement dropoffDateField;

    @FindBy(xpath = "//*[@data-bdd='carPickupDate']")
    protected WebElement pickupDateField;

    @FindBy(name = "startLocation")
    private WebElement city;

    @FindBy(xpath = "//*[@data-bdd='car-location']")
    private WebElement carLocation;

    @FindBy(className = "ui-autocomplete")
    private WebElement autoComplete;

    @FindBy(className = "ui-menu-item")
    private List<WebElement> autoCompleteEntries;

    @FindBy(name = "startTime")
    private WebElement pickupTime;

    @FindBy(name = "endTime")
    private WebElement dropoffTime;

    @FindBy(xpath = "//button/*[text()='Find a Car']/..")
    private WebElement findCars;


    public CarSearchFragment(WebDriver webdriver) {
        super(webdriver);
    }

    public CarSearchFragment withDestinationLocation(String location) {
        this.carLocation.click();
        this.carLocation.sendKeys(location);
        this.carLocation.submit();

        // see if there are autocomplete entries
        if (autoCompleteEntries != null && !autoCompleteEntries.isEmpty()) {
            autoCompleteEntries.get(0).click();
        }
        return this;
    }

    public CarSearchFragment withStartDate(Date startDate) {
        new DatePicker(getWebDriver(), pickupDateField).selectDate(startDate);
        return this;
    }

    public CarSearchFragment withEndDate(Date endDate) {
        new DatePicker(getWebDriver(), dropoffDateField).selectDate(endDate);
        return this;
    }

    public void findFare() {
        this.city.submit();
    }

    public CarSearchFragment withPickUpTime(String pickUpTimeStr) {
        if (pickUpTimeStr != null) {
            new Select(pickupTime).selectByValue(pickUpTimeStr);
        }
        return this;
    }

    public CarSearchFragment withDropOffTime(String dropOffTimeStr) {
        if (dropOffTimeStr != null) {
            new Select(dropoffTime).selectByValue(dropOffTimeStr);
        }
        return this;
    }
}

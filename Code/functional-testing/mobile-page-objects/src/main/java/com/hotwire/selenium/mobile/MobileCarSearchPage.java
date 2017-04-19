/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile;

import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.hotwire.selenium.mobile.widget.DatePicker;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created by IntelliJ IDEA.
 * User: v-sshubey
 * Date: 4/16/12
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileCarSearchPage extends MobileAbstractPage {

    @FindBy(name = "startLocation")
    private WebElement city;

    @FindBy(name = "startDate")
    private WebElement pickupDateField;

    @FindBy(name = "startTime")
    private WebElement pickupTime;

    @FindBy(name = "endDate")
    private WebElement dropoffDateField;

    @FindBy(name = "endTime")
    private WebElement dropoffTime;

    @FindBy(xpath = "//button/*[text()='Find a Car']/..")
    private WebElement findCars;

    private Select selectPickupTime = new Select(pickupTime);

    private Select selectDropoffTime = new Select(dropoffTime);

    public MobileCarSearchPage(WebDriver driver) {
        super(driver, "tile.car.landing");
    }

    public void findFare(String city,
                         Date pickupDate,
                         String pickupTime,
                         Date dropoffDate,
                         String dropoffTime) {
        this.city.click();
        this.city.sendKeys(city);

        DatePicker startDatePicker = new DatePicker(getWebDriver(), pickupDateField);
        startDatePicker.selectDate(pickupDate);

        DatePicker endDatePicker = new DatePicker(getWebDriver(), dropoffDateField);
        endDatePicker.selectDate(dropoffDate);

        if (pickupTime != null) {
            this.selectPickupTime.selectByValue(pickupTime);
        }

        if (dropoffTime != null) {
            this.selectDropoffTime.selectByValue(dropoffTime);
        }
        this.city.submit();
    }

    public void verifyErrorText(String errorText) {
        assertThat(getErrorText().contains(errorText))
                .as("Expected error text \'" + errorText + "\' was not found on the page")
                .isTrue();
    }

}

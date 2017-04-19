/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.widget.JQueryDatepicker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.text.ParseException;
import java.util.Date;

public class NewFareFinder extends AbstractFareFinder {

    private WebElement container;

    @FindBy(name = "startDate")
    private WebElement startDate;

    @FindBy(name = "endDate")
    private WebElement endDate;

    @FindBy(name = "numRooms")
    private WebElement numRooms;

    public NewFareFinder(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
        container = webDriver.findElement(containerLocator);
    }

    public Date getStartDate() throws ParseException {
        JQueryDatepicker jQueryDatepicker = new JQueryDatepicker(getWebDriver(), this.startDate);
        return jQueryDatepicker.getDate();
    }

    @Override
    public NewFareFinder setStartDate(Date startDate) {
        JQueryDatepicker jQueryDatepicker = new JQueryDatepicker(getWebDriver(), this.startDate);
        jQueryDatepicker.setDate(startDate);
        return this;
    }

    public Date getEndDate() throws ParseException {
        JQueryDatepicker jQueryDatepicker = new JQueryDatepicker(getWebDriver(), this.endDate);
        return jQueryDatepicker.getDate();
    }

    @Override
    public NewFareFinder setEndDate(Date endDate) {
        JQueryDatepicker jQueryDatepicker = new JQueryDatepicker(getWebDriver(), this.endDate);
        jQueryDatepicker.setDate(endDate);
        return this;
    }

    public Integer getRoomCount() {
        return Integer.parseInt(numRooms.getAttribute("value"));
    }

    @Override
    public NewFareFinder setRoomCount(Integer roomCount) {
        Select select = new Select(numRooms);
        select.selectByValue(roomCount.toString());
        return this;
    }

    public Integer getAdultsCount(Integer roomNumber) {
        return Integer
            .parseInt(container.findElement(By.id("rooms" + roomNumber + ".numAdults")).getAttribute("value"));
    }

    public NewFareFinder setAdultsCount(Integer roomNumber, Integer adultsCount) {
        Select select = new Select(container.findElement(By.id("rooms" + roomNumber + ".numAdults")));
        select.selectByValue(adultsCount.toString());
        return this;
    }

    public Integer getChildrenCount(Integer roomNumber) {
        return Integer.parseInt(
            container.findElement(By.id("rooms" + roomNumber + ".numChildren")).getAttribute("value"));
    }

    public NewFareFinder setChildrenCount(Integer roomNumber, Integer childrenCount) {
        Select select = new Select(container.findElement(By.id("rooms" + roomNumber + ".numChildren")));
        select.selectByValue(childrenCount.toString());
        return this;
    }

    public Integer getChildrenAge(Integer roomNumber, Integer childrenNumber) {
        return Integer.parseInt(container.findElement(By.id("rooms" + roomNumber + ".childrenAges" + childrenNumber))
                                         .getAttribute("value"));
    }

    public NewFareFinder setChildrenAge(Integer roomNumber, Integer childrenNumber, Integer childrenAge) {
        Select select =
            new Select(container.findElement(By.id("rooms" + roomNumber + ".childrenAges" + childrenNumber)));
        select.selectByValue(childrenAge.toString());
        return this;
    }
}

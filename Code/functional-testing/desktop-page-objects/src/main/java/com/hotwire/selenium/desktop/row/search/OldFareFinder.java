/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.widget.DatePicker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Date;

public class OldFareFinder extends AbstractFareFinder {

    @FindBy(css = "input[name=startDate]")
    private WebElement startDate;

    @FindBy(css = "input[name=endDate]")
    private WebElement endDate;

    @FindBy(css = "select#numRooms")
    private Select numRooms;

    @FindBy(css = "select#numAdults")
    private Select numAdults;

    @FindBy(css = "select#numChildren")
    private Select numChildren;

    public OldFareFinder(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
    }

    @Override
    public OldFareFinder setStartDate(Date startDate) {
        DatePicker datePicker = new DatePicker(getWebDriver(), this.startDate);
        datePicker.selectDate(startDate);
        return this;
    }

    @Override
    public OldFareFinder setEndDate(Date endDate) {
        DatePicker datePicker = new DatePicker(getWebDriver(), this.endDate);
        datePicker.selectDate(endDate);
        return this;
    }

    @Override
    public OldFareFinder setRoomCount(Integer roomCount) {
        numRooms.selectByValue(roomCount.toString());
        return this;
    }

    public OldFareFinder setAdultsCount(Integer adultsCount) {
        numAdults.selectByValue(adultsCount.toString());
        return this;
    }

    public OldFareFinder setChildrenCount(Integer childrenCount) {
        numChildren.selectByValue(childrenCount.toString());
        return this;
    }
}

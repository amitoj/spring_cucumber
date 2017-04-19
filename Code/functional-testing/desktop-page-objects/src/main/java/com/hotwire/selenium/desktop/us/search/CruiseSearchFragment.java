/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.search;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.ui.ExtendedSelect;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created with IntelliJ IDEA. User: akrivin Date: 10/16/12 Time: 11:43 AM To
 * change this template use File | Settings | File Templates.
 */

public class CruiseSearchFragment extends AbstractUSPage {

    @FindBy(id = "cruiseDestination")
    private WebElement cruiseDestination;

    @FindBy(id = "cruiseLine")
    private WebElement cruiseLine;

    @FindBy(id = "selectPort")
    private WebElement selectPort;

    @FindBy(id = "departureMonth")
    private WebElement departureMonth;

    @FindBy(id = "cruiseDuration")
    private WebElement cruiseDuration;

    @FindBy(id = "hasSeniors")
    private WebElement hasSeniors;

    @FindBy(xpath = ".//button[contains(@class, 'btn')]")
    private WebElement searchButton;

    public CruiseSearchFragment(WebDriver webdriver, By containerLocator) {
        super(webdriver, containerLocator);
    }

    public CruiseSearchFragment(WebDriver webdriver, WebElement container) {
        super(webdriver, container);
    }

    public void findFare() {
        searchButton.click();
    }

    public CruiseSearchFragment withDestination(String destination) {
        ExtendedSelect selectCruiseDestination = new ExtendedSelect(this.cruiseDestination);
        String loc = destination == null ? selectCruiseDestination.getFirstSelectedOption().getText() : destination;
        selectCruiseDestination.selectIfVisibleTextStartsWithText(loc);
        return this;
    }

    public CruiseSearchFragment withCruiseLine(String cruiseLine) {
        ExtendedSelect selectCruiseLine = new ExtendedSelect(this.cruiseLine);
        selectCruiseLine.selectIfVisibleTextStartsWithText(
                cruiseLine == null ? selectCruiseLine.getFirstSelectedOption().getText() : cruiseLine);
        return this;
    }

    public CruiseSearchFragment withDeparturePort(String departurePort) {
        ExtendedSelect selectSelectPort = new ExtendedSelect(this.selectPort);
        selectSelectPort.selectIfVisibleTextContainsText(
                departurePort == null ? selectSelectPort.getFirstSelectedOption().getText() : departurePort);
        return this;
    }

    public CruiseSearchFragment withDepartureMonth(String cruiseDepartureMonth) {
        ExtendedSelect selectDepartureMonth = new ExtendedSelect(this.departureMonth);
        selectDepartureMonth.selectIfVisibleTextStartsWithText(
            cruiseDepartureMonth == null ? selectDepartureMonth.getFirstSelectedOption().getText() :
                cruiseDepartureMonth);
        return this;
    }

    public CruiseSearchFragment withDuration(String cruiseDuration) {
        ExtendedSelect selectCruiseDuration = new ExtendedSelect(this.cruiseDuration);
        selectCruiseDuration.selectIfVisibleTextStartsWithText(
                cruiseDuration == null ? selectCruiseDuration.getFirstSelectedOption().getText() : cruiseDuration);
        return this;
    }

    public CruiseSearchFragment withSeniorRates(boolean checkSeniorRates) {
        new Select(hasSeniors).selectByValue(checkSeniorRates ? "Y" : "N");
        return this;
    }
}

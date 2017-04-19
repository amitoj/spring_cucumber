/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.selenium.desktop.widget.DropDownSelector;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * Created with IntelliJ IDEA. User: akrivin Date: 10/16/12 Time: 11:43 AM To
 * change this template use File | Settings | File Templates.
 */

public class PackagesSearchFragment extends AbstractUSPage {

    private static final By SEARCH_BTN_BY = By.cssSelector(
            "#vacationIndexForm button, button[id='findVacationButton']," +
                    " .airFields:not(.hidden) .showFieldsWhenPackageSelection button.btn");

    private static final By NUM_OF_ROOMS = By.cssSelector("#vacationUpdateableFields #vacationsNoOfRooms");
    private static final By NUM_OF_ADULT = By.cssSelector("#vacationUpdateableFields #numAdult");
    private static final By NUM_OF_CHILD = By.cssSelector("#vacationUpdateableFields #numChild");
    private static final By NUM_OF_SENIORS = By.cssSelector("#vacationUpdateableFields .numSeniors select");
    private static final By START_TIME = By.cssSelector("#vacationIndexForm #vacationExtendedStartTime");
    private static final By END_TIME = By.cssSelector("#vacationIndexForm #vacationExtendedEndTime");

    private static final By CHILD_AGE_PER_ROOM =
            By.cssSelector("select[name^='childrenAge']");

    @FindBy(css = ".vacationIndexForm input[name='origCity'], .airFields input[name='origCity']")
    private WebElement fromLocation;

    @FindBy(css = ".vacationIndexForm input[name='destCity'], .airFields input[name='destinationCity']")
    private WebElement toLocation;

    @FindBy(css = ".vacationIndexForm input[name='startDate'], .airFields input[name='startDate']")
    private WebElement startDateField;

    @FindBy(css = ".vacationIndexForm input[name='endDate'], .airFields input[name='endDate']")
    private WebElement endDateField;

    @FindBy(name = "partialHotelStayStartDate")
    private WebElement hotelStartDateField;

    @FindBy(name = "partialHotelStayEndDate")
    private WebElement hotelEndDateField;

    public PackagesSearchFragment(WebDriver webdriver) {
        super(webdriver, By.id("vacationIndexForm"));
    }

    public PackagesSearchFragment(WebDriver webdriver, WebElement element) {
        super(webdriver, element);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return SEARCH_BTN_BY;
    }

    public void findFare() {
        getWebDriver().findElement(SEARCH_BTN_BY).click();
    }

    public PackagesSearchFragment waitForStableSearchButtonLocation(final WebDriver driver) {
        new WebDriverWait(driver, 20).until(new IsElementLocationStable(driver, SEARCH_BTN_BY, 10));
        return this;
    }

    public PackagesSearchFragment withOrigLocation(String origLocation) {
        if (StringUtils.isNotBlank(origLocation)) {
            sendKeys(fromLocation, origLocation);
        }
        return this;
    }

    public PackagesSearchFragment withHotelStartDate(Date hotelStartDate) {
        if (hotelStartDate != null) {
            hotelStartDateField.clear();
            DatePicker hotelStartPicker = new DatePicker(getWebDriver(), hotelStartDateField);
            hotelStartPicker.typeDate(DatePicker.getFormattedDate(hotelStartDate));
        }
        return this;
    }

    public PackagesSearchFragment withHotelEndDate(Date hotelEndDate) {
        if (hotelEndDate != null) {
            hotelEndDateField.clear();
            DatePicker hotelEndPicker = new DatePicker(getWebDriver(), hotelEndDateField);
            hotelEndPicker.typeDate(DatePicker.getFormattedDate(hotelEndDate));
        }
        return this;
    }

    public PackagesSearchFragment withNumberOfHotelRooms(Integer numberOfHotelRooms) {
        String numRooms = numberOfHotelRooms == null ? null : String.valueOf(numberOfHotelRooms);
        new DropDownSelector(getWebDriver(), NUM_OF_ROOMS)
                .selectByVisibleText(numRooms);
        return this;
    }

    public PackagesSearchFragment withNumberOfAdults(Integer numberOfAdults) {
        String numAdults = numberOfAdults == null ? null : String.valueOf(numberOfAdults);
        new DropDownSelector(getWebDriver(), NUM_OF_ADULT)
                .selectByVisibleText(numAdults);
        return this;
    }

    public PackagesSearchFragment withNumberOfChildren(Integer numberOfChildren) {
        String numChildren = numberOfChildren == null ? null : String.valueOf(numberOfChildren);
        new DropDownSelector(getWebDriver(), NUM_OF_CHILD)
                .selectByVisibleText(numChildren);
        return this;
    }

    public PackagesSearchFragment withNumberOfSeniors(Integer numberOfSeniors) {
        String numSeniors = numberOfSeniors == null ? null : String.valueOf(numberOfSeniors);
        new DropDownSelector(getWebDriver(), NUM_OF_SENIORS)
                .selectByVisibleText(numSeniors);
        return this;
    }

    public PackagesSearchFragment withNumberOfChildAge(Integer numberOfChildAge) {
        String childAge = numberOfChildAge == null ? "1" : String.valueOf(numberOfChildAge);
        new DropDownSelector(getWebDriver(), CHILD_AGE_PER_ROOM).selectByVisibleText(childAge);
        return this;
    }

    public PackagesSearchFragment withStartAnyTime(String startAnyTime) {
        String startTime = startAnyTime == null ? null : startAnyTime;
        new DropDownSelector(getWebDriver(), START_TIME).selectByVisibleText(startTime);
        return this;
    }

    public PackagesSearchFragment withEndAnyTime(String endAnyTime) {
        String endTime = endAnyTime == null ? null : endAnyTime;
        new DropDownSelector(getWebDriver(), END_TIME).selectByVisibleText(endTime);
        return this;
    }

    public PackagesSearchFragment withDestinationLocation(String destination) {
        sendKeys(toLocation, destination);
        return this;
    }

    public PackagesSearchFragment withStartDate(Date startDate) {
        startDateField.clear();
        DatePicker startDatePicker = new DatePicker(getWebDriver(), startDateField);
        startDatePicker.typeDate(DatePicker.getFormattedDate(startDate));
        return this;
    }

    public PackagesSearchFragment withEndDate(Date endDate) {
        endDateField.clear();
        DatePicker endDatePicker = new DatePicker(getWebDriver(), endDateField);
        endDatePicker.typeDate(DatePicker.getFormattedDate(endDate));
        return this;
    }
}

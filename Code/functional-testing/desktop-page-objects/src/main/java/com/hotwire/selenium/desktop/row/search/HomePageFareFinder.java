/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Date;

/**
 *
 */
public class HomePageFareFinder extends AbstractRowPage {

    private static final String FARE_FINDER_AUTOCOMPLETE_CONTENT = "yui-ac-content";

    private static final long DEFAULT_WAIT = 20;

    @FindBy(xpath = "//form[contains(@action, 'launchsearch')]")
    private WebElement fareFinderForm;

    @FindBy(name = "location")
    private WebElement destination;

    @FindBy(name = "startDate")
    private WebElement checkInDateField;

    @FindBy(name = "endDate")
    private WebElement checkOutDateField;

    @FindBy(name = "numRooms")
    private WebElement rooms;

    @FindBy(name = "numAdults")
    private WebElement roomsAdults;

    @FindBy(name = "numChildren")
    private WebElement roomsChild;

    @FindBy(css = "button[type=submit]")
    private WebElement findButton;

    public HomePageFareFinder(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div[class*='FareFinder']"));
    }

    @SuppressWarnings("unused")
    private void findFare(String destinationLocation, Date startDate, Date endDate,
                          Integer numberOfHotelRooms, Integer numberOfAdults, Integer numberOfChildren) {
        this.destination.click();
        this.destination.clear();
        this.destination.sendKeys(destinationLocation + Keys.TAB);
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(
                        PageObjectUtils.webElementVisibleTestFunction(
                                fareFinderForm.findElement(By.className(FARE_FINDER_AUTOCOMPLETE_CONTENT)), false));

        DatePicker startDatePicker = new DatePicker(getWebDriver(), checkInDateField);
        startDatePicker.selectDate(startDate);

        DatePicker endDatePicker = new DatePicker(getWebDriver(), checkOutDateField);
        endDatePicker.selectDate(endDate);

        Select roomsCountSelect = new Select(rooms);
        roomsCountSelect.selectByVisibleText((numberOfHotelRooms == null) ? "1" : Integer.toString(numberOfHotelRooms));

        Select adultsCountSelect = new Select(roomsAdults);
        adultsCountSelect.selectByVisibleText((numberOfAdults == null) ? "2" : Integer.toString(numberOfAdults));

        Select childCountSelect = new Select(roomsChild);
        childCountSelect.selectByVisibleText((numberOfChildren == null) ? "0" : Integer.toString(numberOfChildren));

        // todo: changing this from click to submit till we figure out issue with calendar popup
        findButton.submit();
    }

    public void submit() {
        findButton.submit();
    }
}

/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.row.models.CarIntlSolutionModel;
import com.hotwire.selenium.desktop.widget.DropDownSelector;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author v-ypuchkova
 * @since 2012.10
 */
public class CarSearchFragment extends AbstractRowPage {

    public static final Integer DEFAULT_VALUE_FOR_DRIVERS_AGE = 30;

    @FindBy(className = "seleniumCarRoundTrip")
    private WebElement roundTripRadio;

    @FindBy(className = "seleniumCarOneWay")
    private WebElement oneWayRadio;

    @FindBy(className = "seleniumPickupLocation")
    private WebElement pickUpLocation;

    @FindBy(className = "seleniumDropoffLocation")
    private WebElement dropOffLocation;

    @FindBy(name = "startDate")
    private WebElement startDateField;

    @FindBy(className = "seleniumPickupTime")
    private WebElement startTimeCar;

    @FindBy(name = "endDate")
    private WebElement endDateField;

    @FindBy(className = "seleniumDropOffTime")
    private WebElement endTimeCar;

    @FindBy(className = "seleniumDriverAge")
    private WebElement driversAge;

    @FindBy(className = "seleniumCountryOfResidence")
    private WebElement countryOfResidence;

    @FindBy(className = "seleniumContinueButton")
    private WebElement searchButton;

    public CarSearchFragment(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
    }

    public CarIntlSolutionModel getSolutionOptionsSet() {
        CarIntlSolutionModel carSolutionModel = new CarIntlSolutionModel();

        carSolutionModel.setPickUpLocation(getPickUpLocation());
        if (isOneWay()) {
            carSolutionModel.setDropOffLocation(getDropOffLocation());
        }
        return carSolutionModel;
    }

    public String getFormatForDate() {
        return startDateField.getAttribute("placeholder").replaceAll("mm", "MM");
    }

    public String getPickUpLocation() {
        return pickUpLocation.getAttribute("value");
    }

    public String getDropOffLocation() {
        return dropOffLocation.getAttribute("value");
    }

    public boolean isOneWay() {
        return oneWayRadio.isSelected();
    }

    public Integer getEndTime() {
        return Integer.parseInt(endTimeCar.getAttribute("value"));
    }

    public Integer getStartTime() {
        return Integer.parseInt(startTimeCar.getAttribute("value"));
    }

    public String getEndDate() {
        return endDateField.getAttribute("value");
    }

    public String getStartDate() {
        return startDateField.getAttribute("value");
    }

    public Integer getDriversAge() {
        return Integer.parseInt(driversAge.getAttribute("value"));
    }

    public CarSearchFragment driversAge(String driversAge) {
        if (driversAge != null) {
            this.driversAge.click();
            this.driversAge.sendKeys(driversAge);
        }
        return this;
    }

    public CarSearchFragment country(String country) {
        DropDownSelector countrySelector = new DropDownSelector(getWebDriver(), countryOfResidence);
        countrySelector.selectByVisibleText((country != null) ? country : "United Kingdom");
        return this;
    }

    public CarSearchFragment endDate(Date endDate) {
        endDateField.clear();
        endDateField.sendKeys(new SimpleDateFormat(getFormatForDate()).format(endDate));
        return this;
    }

    public CarSearchFragment startTime(String startTime) {
        DropDownSelector startTimeSelector = new DropDownSelector(getWebDriver(), startTimeCar);
        startTimeSelector.selectByVisibleText((startTime != null) ? startTime : "noon");
        return this;
    }

    public CarSearchFragment endTime(String endTime) {
        DropDownSelector endTimeSelector = new DropDownSelector(getWebDriver(), endTimeCar);
        endTimeSelector.selectByVisibleText((endTime != null) ? endTime : "noon");
        return this;
    }

    public CarSearchFragment startDate(Date startDate) {
        startDateField.clear();
        startDateField.sendKeys(new SimpleDateFormat(getFormatForDate()).format(startDate));
        return this;
    }

    public CarSearchFragment pickUpLocation(String destinationLocation) {
        pickUpLocation.clear();
        pickUpLocation.sendKeys(destinationLocation);
        return this;
    }

    public CarSearchFragment dropOffLocation(String dropOffLocation) {
        if (StringUtils.isNotEmpty(dropOffLocation)) {
            oneWayRadio.click();
            this.dropOffLocation.click();
            this.dropOffLocation.sendKeys(dropOffLocation);
        }
        return this;
    }

    public void clickSearchButton() {
        searchButton.click();
    }
}

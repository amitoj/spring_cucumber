/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class ActivitiesSearchFragment extends AbstractUSPage {

    private static final long DEFAULT_WAIT = 20;

    @FindBy(xpath = "//div[@id='cityList']/label")
    List<WebElement> destinationsList;

    @FindBy(xpath = "//input[@name='date1']")
    private WebElement startDateField;

    @FindBy(xpath = "//*[@id='ActivityFareFinderForm']")
    private WebElement activityFareFinder;

    @FindBy(xpath = "//input[@name='date2']")
    private WebElement endDateField;

    @FindBy(xpath = "//*[@id='cityAutocomplete']")
    private WebElement destLocation;

    @FindBy(xpath = "//*[@id='ActivityFareFinderForm']/fieldset/button")
    private WebElement submit;

    public ActivitiesSearchFragment(WebDriver webDriver) {
        super(webDriver, "tiles-def.activities.index");
    }


    /*
     This function extracts the names of all the cities in
     activities Fare Finder
     */
    private ArrayList<String> getDestinations() {
        ArrayList<String> destinations = new ArrayList<>();
        for (WebElement city : destinationsList) {
            String destCity = city.getText();
            destinations.add(destCity);
        }
        return destinations;
    }

    public void findFare(String destination, Date startDate, Date endDate) {

        //Check to see if we landed on the activities page or not; validate fareFinder

        try {
            new WebDriverWait(getWebDriver(), DEFAULT_WAIT).
                    until(PageObjectUtils.
                            webElementVisibleTestFunction(activityFareFinder, true));
        }
        catch (NoSuchElementException e) {
            logger.info("Activities page has an error. No activities fare finder");
        }

        //First check if city mentioned is already there in the finder;
        //else enter it into the city field.

        ArrayList<String> destinationNames = getDestinations();
        if (destinationNames.contains(destination)) {
            getWebDriver().findElement(By.xpath(".//input[@value='" + destination + "']")).click();
        }
        else {
            destLocation.clear();
            destLocation.sendKeys(destination + Keys.TAB);
        }
        startDateField.clear();
        DatePicker startDatePicker = new DatePicker(getWebDriver(), startDateField);
        startDatePicker.typeDate(DatePicker.getFormattedDate(startDate) + Keys.TAB);
        endDateField.clear();
        DatePicker endDatePicker = new DatePicker(getWebDriver(), endDateField);
        endDatePicker.typeDate(DatePicker.getFormattedDate(endDate) + Keys.TAB);
        submit.click();
    }
}

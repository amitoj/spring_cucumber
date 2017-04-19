/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.search;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.widget.DatePicker;
import com.hotwire.util.webdriver.po.PageObjectUtils;

/**
 * HotelSearchFragment of mobile page
 */
public class HotelSearchFragment extends MobileAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelSearchFragment.class.getSimpleName());
    private static final int TIME_OUT_IN_SECONDS = 10;
    private static final String DESTINATION_LAYER_LOCATIONS_LIST = "form.destinations div.layer table tr td";

    @FindBy(name = "location")
    private WebElement destCity;

    @FindBy(xpath = "//*[@data-bdd='hotel-location']")
    private WebElement hotelLocation;

    @FindBy(xpath = "//*[@data-bdd='hotelCheckinDate']")
    private WebElement startDateField;

    @FindBy(xpath = "//*[@data-bdd='hotelCheckoutDate']")
    private WebElement endDateField;

    @FindBy(xpath = "//*[@data-bdd='roomsChange']")
    private WebElement change;

    @FindBy(name = "numRooms")
    private WebElement hotelRooms;

    @FindBy(name = "numAdults")
    private WebElement hotelRoomAdults;

    @FindBy(name = "numChildren")
    private WebElement hotelRoomChild;

    @FindBy(xpath = "//button[contains(@class, 'btn')]")
    private WebElement findHotels;

    public HotelSearchFragment(WebDriver webdriver) {
        super(webdriver, "tile.home");
        try {
            // Not sure if this is triggered by persona or not, but clicking this reloads the
            // page wiping out any previously entered data. So do this first.

            // TO DO: If the following 3 lines are due to persona step, then refactor this to happen
            // on some kind of switch/boolean and edit the mobile implementation model to take
            // this switch boolean variable.
            change.click();
            new WebDriverWait(getWebDriver(), TIME_OUT_IN_SECONDS)
                    .until(PageObjectUtils.webElementVisibleTestFunction(hotelRooms, true));
        }
        catch (NoSuchElementException e) {
            // TO DO: Fix this. Why are we doing this try/catch? And which element not found are we guarding against?
            // The change.click() or the hotelRooms element? And do we just want to do nothing on this exception?
            LOGGER.warn("Dig it! /com/hotwire/selenium/mobile/search/HotelSearchFragment.java:58");
        }
    }

    public void findFare() {
        destCity.submit();
    }

    public HotelSearchFragment withDestinationLocation(String destinationLocation) {
        return withDestinationLocation(destinationLocation, false);
    }

    public HotelSearchFragment withDestinationLocation(String destinationLocation, boolean isPropertySearch) {
        typeLocationAndClickOnMatch(destinationLocation, isPropertySearch ? destinationLocation.length() : 3);
        return this;
    }

    public HotelSearchFragment withDestinationLocation(AbstractMap.SimpleEntry<String, String> coordinates) {
        hotelLocation.click();

        new WebDriverWait(getWebDriver(), TIME_OUT_IN_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.cloak")));

        getWebDriver().findElement(By.cssSelector("td.location-true")).click();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("navigator.geolocation.getCurrentPosition = ")
                .append("function(success) { success({coords: {latitude: ");

        stringBuilder.append(coordinates.getKey());
        stringBuilder.append(", longitude: ");
        stringBuilder.append(coordinates.getValue());
        stringBuilder.append("}}); }");
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getWebDriver();
        javascriptExecutor.executeScript(stringBuilder.toString());

        return this;
    }

    private void typeLocationAndClickOnMatch(String destinationLocation, int subStringLength) {
        // Split up string to grab the first 3 characters as this is the first trigger to the
        // autocomplete menu showing up. Guard against IndexOutOfBoundsException.
        int substringLength = subStringLength;
        String startString = (destinationLocation.length() > substringLength) ? destinationLocation.substring(0,
            substringLength) : destinationLocation;
        // Get the start autocomplete list. This list is always visible. It initially contains "Current Location" item.
        List<WebElement> acList = getWebDriver().findElements(By.cssSelector(DESTINATION_LAYER_LOCATIONS_LIST));

        hotelLocation.click();
        hotelLocation.clear();
        hotelLocation.sendKeys(startString);
        try {
            waitForAutoCompleteListToChange(getWebDriver(), acList);
        }
        catch (TimeoutException e) { /* Do nothing. The user might type something that has no matches. */ }

        acList = getWebDriver().findElements(By.cssSelector(DESTINATION_LAYER_LOCATIONS_LIST));
        boolean matchFound = false;
        for (WebElement item : acList) {
            if (item.getText().trim().toLowerCase().equals(destinationLocation.trim().toLowerCase())) {
                logger.info("Exact location match for " + destinationLocation +
                    " found in location autocomplete. Clicking menu item.");
                matchFound = true;
                item.click();
                break;
            }
        }
        if (!matchFound) {
            for (WebElement item : acList) {
                if (item.getText().trim().toLowerCase().contains(destinationLocation.trim().toLowerCase())) {
                    logger.info("Autocomplete menu item found that contains " + destinationLocation +
                        " as substring. Clicking menu item.");
                    matchFound = true;
                    item.click();
                    break;
                }
            }
            if (!matchFound) {
                logger.info("NO exact or substring location match for " + destinationLocation +
                    ". Typing location manually.");
                for (int i = 0; i < substringLength; i++) {
                    hotelLocation.sendKeys(Keys.BACK_SPACE);
                }
                hotelLocation.sendKeys(destinationLocation + Keys.ESCAPE);
                hotelLocation.submit();
            }
        }
    }

    public static void waitForAutoCompleteListToChange(final WebDriver webdriver, final List<WebElement> startList) {
        new WebDriverWait(webdriver, 5).until(
            new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver input) {
                    List<WebElement> currentList = webdriver.findElements(
                        By.cssSelector(DESTINATION_LAYER_LOCATIONS_LIST));
                    LOGGER.info("Waiting for AutoComplete list to change.");
                    if (startList.containsAll(currentList) && currentList.containsAll(startList) &&
                        startList.size() == currentList.size()) {
                        return false;
                    }
                    LOGGER.info("AutoComplete list changed.");
                    return true;
                }
            });
    }

    public String getCheckInDate() {
        return startDateField.getAttribute("value");
    }

    public HotelSearchFragment setStartDate(Date date) {
        DatePicker datePicker = new DatePicker(getWebDriver(), startDateField);
        datePicker.selectDate(date);
        return this;
    }

    public String getCheckOutDate() {
        return endDateField.getAttribute("value");
    }

    public HotelSearchFragment setEndDate(Date date) {
        DatePicker datePicker = new DatePicker(getWebDriver(), endDateField);
        datePicker.selectDate(date);
        return this;
    }

    public HotelSearchFragment withNumberOfHotelRooms(Integer numberOfHotelRooms) {
        //TO DO: change parameter type to int, this method should be only called when argument not null
        if (numberOfHotelRooms != null) {
            hotelRooms.sendKeys(String.valueOf(numberOfHotelRooms));
        }
        return this;
    }

    public HotelSearchFragment withNumberOfAdults(Integer numberOfAdults) {
        //TO DO: change parameter type to int, this method should be only called when argument not null
        if (numberOfAdults != null) {
            hotelRoomAdults.sendKeys(String.valueOf(numberOfAdults));
        }
        return this;
    }

    public HotelSearchFragment withNumberOfChildren(Integer numberOfChildren) {
        //TO DO: change parameter type to int, this method should be only called when argument not null
        if (numberOfChildren != null) {
            hotelRoomChild.sendKeys(String.valueOf(numberOfChildren));
        }
        return this;
    }

    public List<String> getSearchSuggestions(String destinationLocation) {
        List<String> searchSuggestions = new ArrayList<String>();
        hotelLocation.click();

        new WebDriverWait(getWebDriver(), TIME_OUT_IN_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.cloak")));

        hotelLocation.clear();
        hotelLocation.sendKeys(destinationLocation);

        new WebDriverWait(getWebDriver(), TIME_OUT_IN_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr.ng-scope")));

        for (WebElement e : getWebDriver().findElements(By.cssSelector("form[name='destinations'] td"))) {
            searchSuggestions.add(e.getText());
        }

        hotelLocation.submit();

        new WebDriverWait(getWebDriver(), TIME_OUT_IN_SECONDS)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.cloak")));

        searchSuggestions.remove(0);
        return searchSuggestions;
    }
}

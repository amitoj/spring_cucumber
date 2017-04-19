/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.results.AirResultsPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 *
 */
public class AirChangeSearchLayer extends AbstractUSPage {

    private static final long DEFAULT_WAIT = 20;
    private static final String SEARCH_LAYER_ID = "changeSearchPopupLayerComp-panel";

    @FindBy(css = "div#changeSearchPopupLayerComp-panel_c")
    private WebElement changeAirSearchLayer;

    @FindBy(css = "div#resultsUpdatingLayer-bd")
    private WebElement updateLayer;

    @FindBy(name = "origCity")
    private WebElement fromLocation;

    @FindBy(css = "[name='startDate']")
    private WebElement startDate;

    @FindBy(css = "[name='endDate']")
    private WebElement endDate;

    @FindBy(name = "destinationCity")
    private WebElement toLocation;

    @FindBy(css = "div#changeSearchPopupLayerComp-panel_h")
    private WebElement routeHeader;

    @FindBy(css = "span.price")
    private List<WebElement> flights;

    @FindBy(css = "button.rtBtn.btn, [id='ffAirRoundTripButton']")
    private WebElement searchButton;

    public AirChangeSearchLayer(WebDriver webdriver) {
        super(webdriver, By.id(SEARCH_LAYER_ID));
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
            .until(PageObjectUtils.webElementVisibleTestFunction(startDate, true));
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
            .until(PageObjectUtils.webElementVisibleTestFunction(endDate, true));
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
            .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector("select[name='noOfTickets']"), true));
    }

    private void getCurrentState() {
        try {
            new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                    .until(PageObjectUtils.webElementVisibleTestFunction(searchButton, true));
        }
        catch (NoSuchElementException e) {
            logger.info("ChangeSearchLayer didn't invoke");
        }
    }


    public boolean isChangeSearchLayerInvoked(String fromLocation, String toLocation) {
        AirChangeSearchLayer airChangeSearchLayer = new AirChangeSearchLayer(getWebDriver());
        getCurrentState();
        boolean verifyLocations = true;
        String actualFromLocation = airChangeSearchLayer.fromLocation.getAttribute("value");
        String actualToLocation = airChangeSearchLayer.toLocation.getAttribute("value");

        if (!actualFromLocation.contains(fromLocation) || !actualToLocation.contains(toLocation)) {
            verifyLocations = false;
        }
        logger.info("Locations are: " + actualFromLocation + " & " + actualToLocation);
        return verifyLocations;
    }

    public AirResultsPage changeSearchLocations(String fromLocation, String toLocation) {
        AirChangeSearchLayer airChangeSearchLayer = new AirChangeSearchLayer(getWebDriver());
        getCurrentState();
        try {
            airChangeSearchLayer.fromLocation.clear();
        }
        catch (NoSuchElementException e) {
            logger.info("Change layer is not invoked");
        }
        airChangeSearchLayer.fromLocation.sendKeys(fromLocation + Keys.TAB);
        airChangeSearchLayer.toLocation.clear();
        airChangeSearchLayer.toLocation.sendKeys(toLocation + Keys.TAB);
        airChangeSearchLayer.searchButton.click();
        return new AirResultsPage(getWebDriver());
    }

    public List<String> getSelectedDates() {
        getCurrentState();
        List<String> datesList = new ArrayList<String>();
        datesList.add(0, startDate.getAttribute("value"));
        datesList.add(1, endDate.getAttribute("value"));
        return datesList;
    }

    @SuppressWarnings("deprecation")
    public boolean verifySelectedDates(List<String> selectedDateList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
        AirResultsPage airResultsPage = new AirResultsPage(getWebDriver());
        return airResultsPage.verifyChangeRoutes(
                dateFormat.format(new Date(selectedDateList.get(0))),
                dateFormat.format(new Date(selectedDateList.get(1))));
    }

    public void clickStartDate(boolean useDatePicker) {
        if (useDatePicker) {
            getWebDriver().findElements(By.cssSelector(".calendarPickDate")).get(0).click();
        }
        else {
            startDate.click();
        }
    }

    public void clickEndDate(boolean useDatePicker) {
        if (useDatePicker) {
            getWebDriver().findElements(By.cssSelector(".calendarPickDate")).get(1).click();
        }
        else {
            endDate.click();
        }
    }

    public void closeDatePickerLayer() {
        getWebDriver().findElement(By.cssSelector("select[name='noOfTickets']")).click();
    }

    public By getDatePickerLayerByElement() {
        return By.cssSelector("div[id='ui-datepicker-div']");
    }

    public boolean isDatePickerDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector("div[id='ui-datepicker-div']")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.angular;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.selenium.desktop.widget.DatePicker.DateFormats;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.ui.ExtendedSelect;

/**
 * @author vjong
 *
 */
public class AngularFareFinderFragment extends AbstractUSPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AngularHomePage.class.getSimpleName());

    @FindBy (name = "destination")
    private WebElement destination;

    @FindBy(id = "startDate")
    private WebElement startDate;

    @FindBy(id = "endDate")
    private WebElement endDate;

    @FindBy(id = "rooms")
    private WebElement rooms;

    @FindBy(id = "adult")
    private WebElement adults;

    @FindBy(id = "children")
    private WebElement children;

    @FindBy(css = "button[data-bdd='search']")
    private WebElement searchButton;

    @FindBy(css = "div.resultsException ul[id='disambiguous']")
    private WebElement disambiguousDropdown;

    private String dateFormat;

    public AngularFareFinderFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(".site-content"), new Wait<Boolean>(new VisibilityOf(By.name("destination"))));
        dateFormat = DatePicker.getDateFormat(DateFormats.DESKTOP_US_YYYY);
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void searchForHotels(String destination, Date startDate, Date endDate, Integer numberOfHotelRooms,
            Integer numberOfAdults, Integer numberOfChildren, Boolean enableHComSearch) {
        typeDestination(destination, false);
        enterStartDate(startDate);
        enterEndDate(endDate);
        selectHotelRooms(numberOfHotelRooms);
        selectNumberOfAdults(numberOfAdults);
        selectNumberOfChildren(numberOfChildren);
        searchHcom(enableHComSearch);
        clickSearchButton();
    }

    public void typeDestination(String destination, boolean selectFromAutoComplete) {
        this.destination.clear();
        this.destination.click();
        this.destination.sendKeys(destination);
        if (selectFromAutoComplete) {
            selectDestinationFromDropdown(destination);
        }
        else {
            // Don't select from dropdown list. Get rid of dropdown list if visible.
            this.destination.sendKeys(Keys.ESCAPE);
        }
    }

    /**
     * This chooses the first item from the dropdown that contains the destination string.
     *
     * @param destination
     */
    private void selectDestinationFromDropdown(String destination) {
        try {
            new WebDriverWait(getWebDriver(), 3).until(
                new VisibilityOf(By.cssSelector(".site-content ul.dropdown-menu")));
        }
        catch (TimeoutException e) {
            LOGGER.info("AutoComplete menu didn't show for " + destination + " destination.");
            return;
        }
        List<WebElement> locations = getWebDriver().findElements(By.cssSelector(".site-content ul.dropdown-menu li a"));
        for (WebElement element : locations) {
            if (element.getText().trim().contains(destination)) {
                element.click();
                return;
            }
        }
        this.destination.sendKeys(Keys.ESCAPE);
    }

    public void enterStartDate(Date startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        this.startDate.clear();
        this.startDate.sendKeys(sdf.format(startDate));
    }

    public void enterEndDate(Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        this.endDate.clear();
        this.endDate.sendKeys(sdf.format(endDate));
    }

    public void enterStartDate(String startDate) {
        this.startDate.clear();
        this.startDate.sendKeys(startDate);
    }

    public void enterEndDate(String endDate) {
        this.endDate.clear();
        this.endDate.sendKeys(endDate);
    }

    public void selectHotelRooms(Integer numberOfHotelRooms) {
        if (numberOfHotelRooms != null) {
            new ExtendedSelect(rooms).selectByVisibleText(numberOfHotelRooms.toString());
        }
    }

    public void selectNumberOfAdults(Integer numberOfAdults) {
        if (numberOfAdults != null) {
            new ExtendedSelect(adults).selectByVisibleText(numberOfAdults.toString());
        }
    }

    public void selectNumberOfChildren(Integer numberOfChildren) {
        if (numberOfChildren != null) {
            new ExtendedSelect(children).selectByVisibleText(numberOfChildren.toString());
        }
    }

    public void searchHcom(Boolean enableHComSearch) {
        if (enableHComSearch != null && enableHComSearch) {
            LOGGER.info("HCom check box not implemented yet.");
        }
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public boolean isDestinationErrorDisplayed() {
        try {
            return getWebDriver().findElement(By.xpath("//label[contains(@class, 'error') and @for='destination']"))
                .isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isStartDateErrorDisplayed() {
        try {
            return getWebDriver().findElement(By.xpath("//label[contains(@class, 'error') and @for='start']"))
                .isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEndDateErrorDisplayed() {
        try {
            return getWebDriver().findElement(By.xpath("//label[contains(@class, 'error') and @for='end']"))
                .isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isDisambiguationListDisplayed() {
        try {
            return disambiguousDropdown.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getDestinationLocationValue() {
        return destination.getAttribute("value");
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.angular.AngularHomePage;
import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.ui.ExtendedSelect;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *  Sem landing page fare finder
 */
public class SemPageFareFinder extends AbstractRowPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AngularHomePage.class.getSimpleName());

    private final String dateFormat;

    @FindBy(xpath = "//form[contains(@action, 'launchsearch')]")
    private WebElement fareFinderForm;

    @FindBy(name = "location")
    private WebElement destination;

    @FindBy(name = "startDate")
    private WebElement startDate;

    @FindBy(name = "endDate")
    private WebElement endDate;

    @FindBy(name = "numRooms")
    private WebElement rooms;

    @FindBy(name = "numAdults")
    private WebElement adults;

    @FindBy(name = "numChildren")
    private WebElement children;

    @FindBy(css = ".findHotelButton")
    private WebElement findButton;

    public SemPageFareFinder(WebDriver webdriver) {
        super(webdriver, By.className("pane-hotwire-sem-page-hotel-farefinder"));
        dateFormat = DatePicker.getDateFormat(DatePicker.DateFormats.DESKTOP_US_YYYY);
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
        submit();
    }

    public void submit() {
        findButton.submit();
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
            new org.openqa.selenium.support.ui.WebDriverWait(getWebDriver(), 3).until(
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
}

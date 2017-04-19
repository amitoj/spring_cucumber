/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: v-vzyryanov
 * Date: 12/7/12
 * Time: 5:06 AM
 */
public class CarRentalsPage extends AbstractDesktopPage implements CarPartnersPage {

    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.UK);

    @FindBy(css = "div#fareFinder input#location")
    private WebElement location;

    @FindBy(css = "div#fareFinder input#pickupDate")
    private WebElement pickUpDate;

    @FindBy(css = "div#fareFinder input#dropoffDate")
    private WebElement dropOffDate;

    @FindBy(css = "div#fareFinder select#pickupTime")
    private WebElement pickUpTime;

    @FindBy(css = "div#fareFinder select#dropoffTime")
    private WebElement dropOffTime;

    public CarRentalsPage(WebDriver webdriver) {
        super(webdriver);

        new WebDriverWait(webdriver, 10)
            .until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#tile-fareFinder, #location")));
    }

    private String configureSearchQuery(String loc, String pickUp, String dropOff) {
        return QUERY_FORMAT.replaceAll("%LOCATION%", loc)
                .replaceAll("%PICKUPDATE%", pickUp)
                .replaceAll("%DROPOFFDATE%", dropOff)
                .replaceAll("( )+", " ").trim();
    }

    @Override
    public NAME getName() {
        return NAME.CR;
    }

    @Override
    public String getActualSearchQuery() {
        return configureSearchQuery(
                location.getAttribute("value"),
                pickUpDate.getAttribute("value") + " 12:00",
                dropOffDate.getAttribute("value") + " 12:00"
        );
    }

    @Override
    public String getExpectedSearchQuery(Date pickUp, Date dropOff) {
        return configureSearchQuery(
                "%LOCATION%",
                dateFormat.format(pickUp) + " 12:00",
                dateFormat.format(dropOff) + " 12:00"
        );
    }

    @Override
    public void close() {
        getWebDriver().close();
    }
}

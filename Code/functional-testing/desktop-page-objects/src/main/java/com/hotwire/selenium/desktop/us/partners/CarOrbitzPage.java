/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: v-vzyryanov
 * Date: 4/16/13
 * Time: 5:53 AM
 */
public class CarOrbitzPage extends AbstractDesktopPage implements CarPartnersPage {

    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("M/d/yy", Locale.UK);

    @FindBy(name = "car.airport.orig.key")
    private WebElement location;

    @FindBy(name = "car.airport.startDate")
    private WebElement pickUpDate;

    @FindBy(name = "car.airport.endDate")
    private WebElement dropOffDate;

    public CarOrbitzPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div#search"));
    }

    @Override
    public NAME getName() {
        return NAME.ORBITZ;
    }

    private String configureSearchQuery(String loc, String pickUp, String dropOff) {
        return QUERY_FORMAT.replaceAll("%LOCATION%", loc)
                .replaceAll("%PICKUPDATE%", pickUp)
                .replaceAll("%DROPOFFDATE%", dropOff)
                .replaceAll("( )+", " ").trim();
    }

    @Override
    public String getActualSearchQuery() {
        return configureSearchQuery(
                location.getAttribute("value"),
                pickUpDate.getAttribute("value"),
                dropOffDate.getAttribute("value")
        );
    }

    @Override
    public String getExpectedSearchQuery(Date pickUp, Date dropOff) {
        return configureSearchQuery(
                "%LOCATION%",
                DATE_FORMATTER.format(pickUp),
                DATE_FORMATTER.format(dropOff)
        );
    }

    @Override
    public void close() {
        getWebDriver().close();
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
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
 * Date: 4/15/13
 * Time: 7:19 AM
 */
public class CarKayakPage extends AbstractDesktopPage implements CarPartnersPage {

    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.UK);

    @FindBy(css = "input#inlinepickuplocation")
    private WebElement pickUpLocation;

    @FindBy(css = "input#inlinedropofflocation")
    private WebElement dropOffLocation;

    @FindBy(css = "input#inline_pickup_date")
    private WebElement pickUpDate;

    @FindBy(css = "input#inline_dropoff_date")
    private WebElement dropOffDate;

    @FindBy(css = "form#inlinesearchagain")
    private WebElement searchParametersContainer;

    public CarKayakPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("form#inlinesearchagain"));
    }

    @Override
    public NAME getName() {
        return NAME.KAYAK;
    }

    @Override
    public String getActualSearchQuery() {
        return QUERY_FORMAT.replaceAll("%LOCATION%", pickUpLocation.getAttribute("value") + " - " + dropOffLocation
                .getAttribute("value"))
                .replaceAll("%PICKUPDATE%", pickUpDate.getAttribute("value"))
                .replaceAll("%DROPOFFDATE%", dropOffDate.getAttribute("value"))
                .replaceAll("( )+", " ").trim();
    }

    @Override
    public String getExpectedSearchQuery(Date pickUp, Date dropOff) {
        return QUERY_FORMAT
                .replaceAll("%PICKUPDATE%", dateFormat.format(pickUp))
                .replaceAll("%DROPOFFDATE%", dateFormat.format(dropOff))
                .replaceAll("( )+", " ").trim();
    }

    @Override
    public void close() {
        getWebDriver().close();
    }
}

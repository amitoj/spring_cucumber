/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.account;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.openqa.selenium.By.cssSelector;

/**
 * Created with IntelliJ IDEA.
 * User: v-jneiman
 * Date: 6/17/13
 */
public class AccountTripsPageWatchedTrips extends AbstractPageObject {

    private static final String XPATH_LOCATION = ".//a[contains(text(), '%LOCATION%')]";
    private static final String XPATH_DATE = ".//li[contains(@class, 'description')]//div";
    private static final String CSS_CONTAINER = "#tileName-A5 .yourWatchedTrips .tripsList";

    @FindBy(css = "ul.tripsList li div.watchedTrips")
    List<WebElement> trips;

    public AccountTripsPageWatchedTrips(WebDriver webdriver) {
        super(webdriver, cssSelector(CSS_CONTAINER));
    }

    public void stopWatchingAllTrips() {
        for (WebElement trip : trips) {
            trip.findElement(By.xpath("//a[contains(text(), ' Stop watching')]")).click();
        }
    }

    public WebElement hasRecentlyWatchedTripDetails(String location,
                                                    String formattedStartDate,
                                                    String formattedEndDate) {

        for (WebElement trip : trips) {
            try {
                trip.findElement(By.xpath(XPATH_LOCATION.replaceAll("%LOCATION%", location)));
                trip.findElement(By.xpath(XPATH_DATE.replaceAll("%StartDate%", formattedStartDate)
                        .replaceAll("%EndDate%", formattedEndDate)));
                return trip;
            }
            catch (Exception e) {
                // no action
            }
        }
        return null;
    }
}

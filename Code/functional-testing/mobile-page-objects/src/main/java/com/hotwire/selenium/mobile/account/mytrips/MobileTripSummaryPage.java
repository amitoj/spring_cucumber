/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.account.mytrips;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.view.ViewIds;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MobileTripSummaryPage extends MobileAbstractPage {

    @FindBy(xpath = "//*[@data-bdd='trip-summary-hotel']")
    private List<WebElement> hotelTripsList;

    @FindBy(xpath = "//*[@data-bdd='trip-summary-car']")
    private List<WebElement> carTripsList;

    @FindBy(xpath = "//*[@data-bdd='trip-summary-air']")
    private List<WebElement> flightTripsList;

    public MobileTripSummaryPage(WebDriver webdriver) {
        super(webdriver, ViewIds.TILE_MY_TRIP_SUMMARY);
    }

    public boolean hasAtLeastOneOfTripType(String tripType) {
        boolean hasAtLeastOneTrip = false;
        if ("hotel".equals(tripType)) {
            hasAtLeastOneTrip = !hotelTripsList.isEmpty();
        }
        else if ("car".equals(tripType)) {
            hasAtLeastOneTrip = !carTripsList.isEmpty();
        }
        else if ("flight".equals(tripType)) {
            hasAtLeastOneTrip = !flightTripsList.isEmpty();
        }
        return hasAtLeastOneTrip;
    }

    public void navigateToTripDetailsPage(String tripType) {
        if ("hotel".equals(tripType)) {
            hotelTripsList.get(0).click();
        }
        else if ("car".equals(tripType)) {
            carTripsList.get(0).click();
        }
        else if ("flight".equals(tripType)) {
            flightTripsList.get(0).click();
        }
    }

    public void navigateToTripDetails() {
        if (hotelTripsList.size() != 0) {
            hotelTripsList.get(0).click();
        }
        else if (carTripsList.size() != 0) {
            carTripsList.get(0).click();
        }
        else if (flightTripsList.size() != 0) {
            flightTripsList.get(0).click();
        }
    }

    public Boolean hasRecentlyBookedTrip(String tripStartDate) {
        List<WebElement> bookedTrips = getWebDriver().findElements(By.cssSelector(".booking a"));
        for (WebElement trip : bookedTrips) {
            LoggerFactory.getLogger(this.getClass().getSimpleName())
                         .info("Actual: " + trip.getText() + " - Expecting: " + tripStartDate);
            if (trip.getText().contains(tripStartDate + " -")) {
                return true;
            }
        }
        return false;
    }
}

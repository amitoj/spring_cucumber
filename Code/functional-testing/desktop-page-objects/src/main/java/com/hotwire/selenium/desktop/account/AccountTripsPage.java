/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.account;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.po.AbstractPageObject;

/**
 * Created by IntelliJ IDEA.
 * User: vyulun
 * Date: 01/13/14
 * To change this template use File | Settings | File Templates.
 */
public class AccountTripsPage extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTripsPage.class);

    private static final String WATCHED_TRIP_TAB =
            "//ul[@class='subNavClass']//li//a[contains(text(),'Watched trips')]";

    @FindBy(xpath = WATCHED_TRIP_TAB)
    protected WebElement watchedTripTab;

    // No matter which way you get this element, technically a list of webelements will be returned.
    // need to have a better way of selecting which add to stay button to interact with.
    // Right now, for tests,
    // when a hotel is booked, it should be the first listed in my accounts, so we're just grabbing the
    // first add to my stay link.
    @FindBy(xpath = ".//*[@class='tripsList']/li[1]")
    private WebElement recentlyBookedTrip;

    @FindBy(linkText = "Add to your stay")
    private WebElement addToMyStayLink;

    @FindBy(css = "ul.tripsList li .trips")
    private List<WebElement> allTrips;

    @FindBy(css = "ul.tripsList li .trips .hotelTrip")
    private List<WebElement> hotelTrips;

    @FindBy(css = "ul.tripsList li .trips .airTrip")
    private List<WebElement> airTrips;

    @FindBy(css = "ul.tripsList li .trips .carTrip")
    private List<WebElement> carTrips;

    public AccountTripsPage(WebDriver driver) {
        super(driver, new String[] {"tile.account.trips"});
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(".content ul.subNavClass");
    }

    public WebElement getSubAccountTab(String textContent) {
        String xpath = "//div[@class='subNavBar']//a[contains(., '%s')]";
        return getWebDriver().findElement(By.xpath(String.format(xpath, textContent)));
    }

    /**
     * TripTypes
     */
    private static enum TripTypes {
        BOOKED("Booking confirmed"),
        CANCELLED("Cancelled"),
        RESERVED("Reservation confirmed");

        private String text;

        TripTypes(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }



    public Boolean hasRecentlyBookedTrip(String tripStartDate) {
        try {
            recentlyBookedTrip.getText().contains(tripStartDate);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Search for a trip with defined params.
     *
     * @param tripType           String - {car, hotel, air}
     * @param location           String
     * @param tripStatus         String status of a trip {booked, reserved, cancelled}
     * @param formattedStartDate String formatted pick up date
     * @param formattedEndDate   String formatted drop off date
     * @return WebElement if trip was found or null
     */
    public WebElement hasRecentlyBookedTripDetails(String tripType,
                                                   String location,
                                                   String tripStatus,
                                                   String formattedStartDate,
                                                   String formattedEndDate) {


        LOGGER.info("Search for a " + tripType + " trip in " + location +
                " (" + formattedStartDate + " - " + formattedEndDate + ")");
        List<WebElement> trips = getWebDriver().findElements(
                By.cssSelector("ul.tripsList li div.trips div." + tripType + "Trip"));
        if (trips.isEmpty()) {
            LOGGER.info(
                "hasRecentlyBookedTripDetails() trips list is empty. Need a wait for list item(s) visiblity");
        }

        for (WebElement trip : trips) {
            try {
                if (trip.getAttribute("class").contains("collapsed")) {
                    LOGGER.info("Expand trip details");
                    trip.findElement(By.cssSelector("div.arrow a")).click();
                }
                trip.findElement(By.xpath(".//a[contains(@class, 'tripDetails')][contains(text(), '" +
                        location + "')]"));
                trip.findElement(By.xpath(".//div[contains(@class, 'dates')]//label[contains(text(), '" +
                        formattedStartDate + " - " + formattedEndDate + "')]"));
                trip.findElement(By.xpath(".//div[contains(@class, 'actions')]//div[contains(text(), '" +
                        TripTypes.valueOf(tripStatus.toUpperCase()) + "')]"));
                return trip;
            }
            catch (Exception e) {
                // no action
            }
        }

        LOGGER.info(tripType + " trip in " + location + " (" + formattedStartDate + " - " + formattedEndDate + ")" +
                " has not " + TripTypes.valueOf(tripStatus.toUpperCase()));
        return null;
    }

    /**
     * Click on <Cancel reservation> button in trip details block
     */
    public void cancelRecentlyBookedTrip(String tripType,
                                         String location,
                                         String tripStatus,
                                         String formattedStartDate,
                                         String formattedEndDate) {

        WebElement trip = hasRecentlyBookedTripDetails(tripType, location, tripStatus,
                formattedStartDate, formattedEndDate);
        trip.findElement(By.xpath("//div//a[contains(@class, 'smallBtn')][contains(text()," +
                " 'Cancel reservation')]")).click();
    }

    public WebElement getAddToMyStayLink() {
        return addToMyStayLink;
    }

    public static String getWatchedTripTab() {
        return WATCHED_TRIP_TAB;
    }

    public void clickFirstTripSummaryDetails(String bookingType) {
        if (bookingType.equalsIgnoreCase("hotel")) {
            if (hotelTrips.size() == 0) {
                throw new ZeroResultsTestException("0 hotel trips in my trips.");
            }
            hotelTrips.get(0).findElement(By.cssSelector("div.actions a")).click();
        }
        else if (bookingType.equalsIgnoreCase("flight")) {
            if (airTrips.size() == 0) {
                throw new ZeroResultsTestException("0 air trips in my trips.");
            }
            airTrips.get(0).findElement(By.cssSelector("div.actions a")).click();
        }
        else {
            // assume car trips
            if (carTrips.size() == 0) {
                throw new ZeroResultsTestException("0 car trips in my trips.");
            }
            carTrips.get(0).findElement(By.cssSelector("div.actions a")).click();
        }
    }
}

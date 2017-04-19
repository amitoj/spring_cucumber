/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;


import com.hotwire.selenium.desktop.row.AbstractRowPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AirportDealLandingPage extends AbstractRowPage {


    @FindBy(css = "div.AirportDealsSubscriptionModule")
    private WebElement airportDealsBanner;

    @FindBy(css = "div.weekendDealsModule.city")
    private WebElement weekendDealsModule;

    @FindBy(css = "div.weekendDealsModule.airport")
    private WebElement dealsNearAirport;

    public AirportDealLandingPage(WebDriver webdriver) {
        super(webdriver, "tile.hotel.geo.low.level.airportDeals");
    }

    public boolean isAirportDealsBannerDisplayed() {
        try {
            return airportDealsBanner.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isWeekendDealsModulePresent() {
        try {
            return weekendDealsModule.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isNearbyAirportDealsModulePresent() {
        try {
            return dealsNearAirport.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
* Hotel Details page - is between results and billing
 * Simplified PageObject for C3
 */
public class C3HotelDetailsPage extends ToolsAbstractPage {
    private static String location = "div.searchDetails .destinationCity";
    private static String startDate = "div.searchDetails .startEndDate";
    private static String endDate = "div.searchDetails .startEndDate";
    private static String rooms = "div.searchDetails .noRooms, div.roomsCount";
    private static String adults = "div.searchDetails .noAdults";
    private static String children = "div.searchDetails .noChildren";

    public C3HotelDetailsPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.hotelSearchDetails, div.detailsAmenityIcons"));
    }

    public void select() {
        findOne("div.priceCol a", DEFAULT_WAIT).click();
    }

    public String getReferenceNumber() {
        return findOne("span.customerCareRefNumber", DEFAULT_WAIT).getText().replaceAll("[^0-9]", "");
    }

    public String getLocation() {
        return findOne(location).getText().trim();
    }

    public String getStartDate() {
        return findOne(startDate).getText().trim().split("-")[0].trim();
    }

    public String getEndDate() {
        return findOne(endDate).getText().trim().split("-")[1].trim();
    }

    public String getRooms() {
        return findOne(rooms).getText().split(" ")[0].trim();
    }

    public String getAdults() {
        return findOne(adults).getText().split(" ")[0].trim();
    }

    public String getChildren() {
        return findOne(children).getText().split(" ")[0].trim();
    }
}

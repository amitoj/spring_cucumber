/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 10/9/14
 * Time: 3:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3BlockedHotelsPage extends ToolsAbstractPage {

    public C3BlockedHotelsPage(WebDriver webdriver) {
        super(webdriver, By.id("c3BlockedHotelsSummaryForm"));
    }

    public List<WebElement> getBlockedHotels() {
        return findMany(By.className("evenRow"));
    }

    public boolean isHotelInBlockedList(String itinerary) {
        List<WebElement> blockedHotels = getBlockedHotels();

        for (WebElement hotel : blockedHotels) {
            if (hotel.getText().contains(itinerary)) {
                return true;
            }

        }
        return false;
    }

    public String  getInformationOfBlockedHotel(String itinerary) {
        List<WebElement> blockedHotels =  getBlockedHotels();

        for (WebElement hotel: blockedHotels) {
            if (hotel.getText().contains(itinerary)) {
                return hotel.getText();
            }
        }
        return null;
    }

    public void clickUnblockHotel(String itinerary) {
        List<WebElement> blockedHotels =  getBlockedHotels();

        for (WebElement hotel: blockedHotels) {
            if (hotel.getText().contains(itinerary)) {
                hotel.findElement(By.xpath("//button[contains(text(),'Unblock')]")).click();
            }

        }
    }
}

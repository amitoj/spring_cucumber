/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


/**
 * Simplified C3 Hotel Confirmation Page
  */
public class C3HotelConfirmationPage extends ToolsAbstractPage {

    public C3HotelConfirmationPage(WebDriver webdriver) {
        super(webdriver, By.className("hotelInfo"));
    }

    public String getStarRating() {
        return findOne(".starsAmount.resultsCount").getAttribute("title").split(" out")[0];
    }

    public String getHotelAddress() {
        return findOne(".contactInfo>span").getText();
    }

    public String getHotelGuestName() {
        return findOne(".last>strong").getText();
    }

    public List<String> getAmenities() {
        List<String> amenities = new ArrayList<>();
        for (int i = 0; i < findMany(".generalAmenityName").size(); i++) {
            amenities.add(findMany(".generalAmenityName").get(i).getText());
        }
        return amenities;
    }

    public String getItineraryNumber() {
        //different version tests on dev and QA
        try {
            WebElement itineraryBlock1 = findOne("div.hotelConfirmationNumber  span:last-child");
            return itineraryBlock1.getText().replaceFirst("(.*):", "").trim();
        }
        catch (NoSuchElementException e) {
            return findOne("div.confNumber[data-itinerary]").getAttribute("data-itinerary");
        }
    }

    public String getFullHotelName() {
        //mixed selector
        return findOne("div.hotelName, div.hotelInfo h2").getText();
    }

    public String getInfoMsg() {
        //mixed selector
        return findOne("div.msgBoxBody li").getText();
    }

    public String getConfirmationCode() {
        String codesText = findOne(".confNumber.node").getText();
        return codesText.split("\n")[1];
    }

    public String getTotalAmount() {
        return findOne("span#totalCharge").getText().replace(",", "");
    }

    public String getHotDollarAmount() {
        return findOne("span#hotCredit").getText().replace(",", "");
    }

    public String getRatePerNight() {
        return findOne("#pricePerNight").getText();
    }

    public Integer getNights() {
        return Integer.parseInt(findOne("#numNights").getText());
    }

    public Integer getRooms() {
        return Integer.parseInt(findOne("#numRooms").getText());
    }

    public String getTaxesAndFees() {
        return findOne("#taxesAndFees").getText();
    }

    public String getInsuranceTotal() {
        return findOne("#insurance").getText();
    }


    public String getCheckInDate() {
        return findOne(".dateAndRoomInfo.nodeIndent > div.node > strong").getText().split(" - ")[0];
    }

    public String getCheckOutDate() {
        return findOne(".dateAndRoomInfo.nodeIndent > div.node > strong").getText().split(" - ")[1];
    }
}

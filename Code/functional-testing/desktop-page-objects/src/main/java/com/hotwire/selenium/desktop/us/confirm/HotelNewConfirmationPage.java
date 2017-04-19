/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.confirm;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.utils.WebElementUtils;

/**
 * This class represents hotel's new confirmation page.
 *
 * @author Amol Deshmukh
 */
public class HotelNewConfirmationPage extends AbstractUSPage {

    private static final String RATE_PER_NIGHT =
        ".tripSummaryModule .leftModule .priceCol .priceAlign, .priceDetail span[id='pricePerNight']";
    private static final String TAXES_AND_FEES =
        ".tripSummaryModule .rightModule span[id='taxesAndFees'], .taxDetail span#taxesAndFees";
    private static final String ROOM =
        ".tripSummaryModule .leftModule .leftCol span[id='numRooms'], .priceDetail span[id='numRooms']";
    private static final String NIGHTS =
        ".tripSummaryModule .leftModule .leftCol span[id='numNights'], .priceDetail span[id='numNights']";
    private static final String TRIP_TOTAL =
        ".tripSummaryModule .priceFgColor .priceAlign, .tripTotalPrice span[id='totalCharge']";
    private static final String TRAVEL_INSURANCE = ".tripInsurance";
    private static final String AMENITIES = ".generalAmenityName";
    private static final String RATING = "span.ratingText";

    @FindBy(css = AMENITIES)
    private WebElement amenities;

    @FindBy(css = RATING)
    private WebElement tripAdvisorRating;

    @FindBy(css = RATE_PER_NIGHT)
    private WebElement ratePerNight;

    @FindBy(css = TAXES_AND_FEES)
    private WebElement taxesAndFees;

    @FindBy(css = ROOM)
    private WebElement rooms;

    @FindBy(css = NIGHTS)
    private WebElement nights;

    @FindBy(css = TRIP_TOTAL)
    private WebElement tripTotal;

    @FindBy(css = TRAVEL_INSURANCE)
    private WebElement travelInsurance;

    @FindBy(css = ".reviewTrip .details .data .first, .confirmTripSummary .dateAndRoomInfo .node")
    private WebElement travelDates;

    @FindBy(css = ".hotelConfirmationNumber span, .hotelAndGuestInfo .confNumber")
    private List<WebElement> confirmElements;


    public HotelNewConfirmationPage(WebDriver webDriver) {
        super(webDriver, new String[] {"tile.hotel.purchase-confirm", "tile.hotel.booking-confirm"});
    }

    public String getRatePerNight() {
        return WebElementUtils.getText(ratePerNight);
    }

    public int getNights() {
        return Integer.parseInt(WebElementUtils.getText(nights));
    }

    public int getRooms() {
        return Integer.parseInt(WebElementUtils.getText(rooms));
    }

    public String getTaxesAndFees() {
        return WebElementUtils.getText(taxesAndFees);
    }

    public String getTripTotal() {
        return WebElementUtils.getText(tripTotal);
    }

    public String getConfirmationNumber() {
        return confirmElements.get(0).getAttribute("data-confirm");
    }

    public String getItineraryNumber() {
        return confirmElements.get(1).getAttribute("data-itinerary");
    }

    public String getTravelInsurance() {
        if (getWebDriver().findElements(By.cssSelector(TRAVEL_INSURANCE)).size() > 0) {
            return WebElementUtils.getText(travelInsurance).replace("Hotel protection", "").trim().replace("$", "");
        }
        else {
            return null;
        }
    }

    public String getCheckInDate() {
        return travelDates.getText().split("-")[0].trim();
    }

    public String getCheckOutDate() {
        return travelDates.getText().split("-")[1].trim();
    }

    public List<WebElement> getAmenities() {
        List<WebElement> hotelAmenities = getWebDriver().findElements(By.cssSelector(AMENITIES));
        return hotelAmenities;
    }

    public WebElement getTripAdvisorRanking() {
        return tripAdvisorRating;
    }
}

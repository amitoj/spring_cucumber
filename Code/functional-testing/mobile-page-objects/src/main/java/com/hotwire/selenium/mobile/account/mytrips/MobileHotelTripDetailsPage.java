/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.account.mytrips;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.mobile.view.ViewIds;

public class MobileHotelTripDetailsPage extends MobileAbstractTripDetailsPage {

    @FindBy(xpath = "//*[@data-bdd='hotel-information-summary']")
    private WebElement hotelInformation;

    @FindBy(xpath = "//*[@data-bdd='hotel-confirmation-information']")
    private WebElement confirmationInformation;

    @FindBy(xpath = "//*[@data-bdd='hotel-trip-details']")
    private WebElement tripDetails;

    @FindBy(xpath = "//*[@data-bdd='hotel-cost-summary']")
    private WebElement summaryOfCharges;

    @FindBy(xpath = "//*[@data-bdd='hotel-know-before']")
    private WebElement knowBeforeYouGo;

    public MobileHotelTripDetailsPage(WebDriver webdriver) {
        super(webdriver, ViewIds.TILE_HOTEL_TRIP_DETAILS);
    }

    @Override
    public void verifyTripDetailsPage() {
        assertThat(hotelInformation.isDisplayed()).as(
                "Expected Hotel Information to be displayed on the Hotel Trip Details Page").isTrue();
        assertThat(confirmationInformation.isDisplayed()).as(
                "Expected Confirmation Information to be displayed on the Hotel Trip Details Page").isTrue();
        assertThat(tripDetails.isDisplayed())
                .as("Expected Trip Details to be displayed on the Hotel Trip Details Page").isTrue();
        assertThat(summaryOfCharges.isDisplayed()).as(
                "Expected Summary of Charges to be displayed on the Hotel Trip Details Page").isTrue();
        assertThat(knowBeforeYouGo.isDisplayed()).as(
                "Expected Know Before You Go to be displayed on the Hotel Trip Details Page").isTrue();
    }
}

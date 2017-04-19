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

public class MobileCarTripDetailsPage extends MobileAbstractTripDetailsPage {

    @FindBy(xpath = "//*[@data-bdd='car-information-summary']")
    private WebElement carInformation;

    @FindBy(xpath = "//*[@data-bdd='car-confirmation-information']")
    private WebElement confirmationInformation;

    @FindBy(xpath = "//*[@data-bdd='car-trip-details']")
    private WebElement tripDetails;

    @FindBy(xpath = "//*[@data-bdd='car-cost-summary']")
    private WebElement summaryOfCharges;

    @FindBy(xpath = "//*[@data-bdd='car-know-before']")
    private WebElement knowBeforeYouGo;

    @FindBy(xpath = "//*[@data-bdd='car-booking-rules']")
    private WebElement bookingRules;

    public MobileCarTripDetailsPage(WebDriver webdriver) {
        super(webdriver, ViewIds.TILE_CAR_TRIP_DETAILS);
    }

    @Override
    public void verifyTripDetailsPage() {
        assertThat(carInformation.isDisplayed()).as(
            "Expected Car Information to be displayed on the Car Trip Details Page").isTrue();
        assertThat(confirmationInformation.isDisplayed()).as(
            "Expected Confirmation Information to be displayed on the Car Trip Details Page").isTrue();
        assertThat(tripDetails.isDisplayed()).as("Expected Trip Details to be displayed on the Car Trip Details Page")
                                             .isTrue();
        assertThat(summaryOfCharges.isDisplayed()).as(
            "Expected Summary of Charges to be displayed on the Car Trip Details Page").isTrue();
        assertThat(knowBeforeYouGo.isDisplayed()).as(
            "Expected Know Before You Go to be displayed on the Car Trip Details Page").isTrue();
        assertThat(bookingRules.isDisplayed())
            .as("Expected Booking Rules to be displayed on the Car Trip Details Page").isTrue();
    }
}

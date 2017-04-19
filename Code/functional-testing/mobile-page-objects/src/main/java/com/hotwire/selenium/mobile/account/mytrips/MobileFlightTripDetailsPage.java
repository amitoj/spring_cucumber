/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.account.mytrips;

import com.hotwire.selenium.mobile.view.ViewIds;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class MobileFlightTripDetailsPage extends MobileAbstractTripDetailsPage {

    @FindBy(xpath = "//*[@data-bdd='air-trip-details']")
    private WebElement tripDetails;

    @FindBy(xpath = "//*[@data-bdd='air-confirmation-numbers']")
    private WebElement confirmationNumbers;

    @FindBy(xpath = "//*[@data-bdd='air-flight-details']")
    private WebElement flightDetails;

    @FindBy(xpath = "//*[@data-bdd='air-passenger-information']")
    private WebElement passengerInformation;

    @FindBy(xpath = "//*[@data-bdd='air-carrier-phone']")
    private List<WebElement> carrierPhoneNumbers;

    public MobileFlightTripDetailsPage(WebDriver webdriver) {
        super(webdriver, ViewIds.TILE_FLIGHT_TRIP_DETAILS);
    }

    @Override
    public void verifyTripDetailsPage() {
        assertThat(tripDetails.isDisplayed()).as(
            "Expected Trip Details to be displayed on the Flight Trip Details Page").isTrue();

        assertThat(confirmationNumbers.isDisplayed()).as(
            "Expected Confirmation Numbers to be displayed on the Flight Trip Details Page").isTrue();

        assertThat(flightDetails.isDisplayed()).as(
            "Expected Flight Details to be displayed on the Flight Trip Details Page").isTrue();

        assertThat(passengerInformation.isDisplayed()).as(
            "Expected Passenger Information to be displayed on the Flight Trip Details Page").isTrue();

        for (WebElement element : carrierPhoneNumbers) {
            assertThat(element.isDisplayed())
                .as("Expected at least one Carrier Phone Number to be displayed on the Flight Trip Details Page")
                .isTrue();
        }
    }
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account.mytrips;

import com.hotwire.selenium.desktop.account.AccountTripsPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.confirm.CarConfirmationPage;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import com.hotwire.testing.UnimplementedTestException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.fest.assertions.Assertions.assertThat;


/**
 * interface MyTripsModelWebApp
 */
public class MyTripsModelWebApp extends WebdriverAwareModel implements MyTripsModel {

    @Override
    public void navigateToMyTripsPage() {
        GlobalHeader header = new GlobalHeader(getWebdriverInstance());
        header.navigateToMyTrips();
    }

    @Override
    public void verifyMyTripSummaryPage() {
        new AccountTripsPage(getWebdriverInstance());
    }

    @Override
    public void verifyTripDetails(String bookingType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToTripDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectFirstTripSummaryItem(String bookingType) {
        AccountTripsPage myTripsPage = new AccountTripsPage(getWebdriverInstance());
        myTripsPage.clickFirstTripSummaryDetails(bookingType);
    }

    @Override
    public void accessMyTrips() {
        new AbstractUSPage(getWebdriverInstance()).getGlobalHeader().navigateToMyTripsAsSignedInUser();
    }

    @Override
    public void verifyCarTripDetailsPageHasCorrectInformation(AuthenticationParameters authenticationParameters,
                                                              CarSearchParameters carSearchParameters,
                                                              PurchaseParameters purchaseParameters) {
        CarConfirmationPage tripDetails = new CarConfirmationPage(getWebdriverInstance(),
                "tiles-def.account.car-purchase-details");

        DateFormat formatterDate1 = new SimpleDateFormat("EEE, MMM d, yyyy");

        String startDate = formatterDate1.format(carSearchParameters.getGlobalSearchParameters().getStartDate()) +
                " at " + carSearchParameters.getPickupTime();
        String endDate = formatterDate1.format(carSearchParameters.getGlobalSearchParameters().getEndDate()) +
                " at " + carSearchParameters.getDropoffTime();

        assertThat(tripDetails.getItineraryNumber())
                .as("Itinerary number on trip details page is correct: ")
                .isEqualTo(purchaseParameters.getDisplayNumber());
        assertThat(tripDetails.getDriverName())
                .as("Driver name is correct : ")
                .isEqualTo(purchaseParameters.getUserInformation().getFirstName() +
                " " + purchaseParameters.getUserInformation().getLastName());

        String carModel  = carSearchParameters.getSelectedSearchSolution().getCarModels().split("or")[0].trim();
        assertThat(tripDetails.getCarName())
                .as("Car name is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getCarName());
        assertThat(tripDetails.getCarModels()).as("Car model is correct: ").containsIgnoringCase(carModel);

        String pickUpDateOnPage = tripDetails.getPickupDate() + " at " + tripDetails.getPickupTime();
        String dropOffDateOnPage = tripDetails.getDropoffDate() + " at " + tripDetails.getDropoffTime();
        assertThat(pickUpDateOnPage).as("Pick up date is correct: ").isEqualToIgnoringCase(startDate);
        assertThat(dropOffDateOnPage).as("Drop off date is correct: ").isEqualToIgnoringCase(endDate);
        assertThat(carSearchParameters.getGlobalSearchParameters().getDestinationLocation())
                .as("Location is correct: ")
                .containsIgnoringCase(tripDetails.getPickupLocationCity().split(",")[0].trim());
        assertThat(tripDetails.getMileage())
                .as("Mileage is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getMileage());
        assertThat(tripDetails.getPeopleCapacity())
                .as("People capacity is correct:")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getPeopleCapacity());
        assertThat(tripDetails.getLargePackageCapacity())
                .as("Large package capacity is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getLargePackageCapacity());
        assertThat(tripDetails.getSmallPackageCapacity())
                .as("Small package capacity is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getSmallPackageCapacity());
        assertThat(tripDetails.getContactEmail())
                .as("Contact email is correct: ")
                .isEqualToIgnoringCase(authenticationParameters.getUsername());
        assertThat(tripDetails.getTotalPrice())
                .as("Total price is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getTotalPrice());
        assertThat(tripDetails.getRentalDaysCount())
                .as("Rental days is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getRentalDaysCount());
        assertThat(tripDetails.getPerDayPrice())
                .as("Pice per day is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getPerDayPrice());
        assertThat(tripDetails.getTaxesAndFees())
                .as("Taxes and fees is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getTaxesAndFees());
        assertThat(tripDetails.getDamageProtection())
                .as("Damage protection is correct: ")
                .isEqualTo(carSearchParameters.getSelectedSearchSolution().getDamageProtection());

    }
}

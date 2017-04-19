/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.air;

import java.text.ParseException;

import com.hotwire.selenium.desktop.us.results.AirResultsPage;
import com.hotwire.test.steps.purchase.PurchaseModelTemplate;
import com.hotwire.testing.UnimplementedTestException;

/**
 * @author v-ypuchkova
 * @since 2013.04
 */
public class AirPurchaseModelTemplate extends PurchaseModelTemplate implements AirPurchaseModel {

    @Override
    public void addRentalCar() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setPassengerName(Integer number, String firstName, String middleName, String lastName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setRandomPassengerNameBirthdayGender(Integer number) {
        throw new UnimplementedTestException("Implement me!");
    };


    @Override
    public void fillTravelerAndPassengerInfo(boolean guestOrUser) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillCreditCard() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void saveBillingInformation(String cardName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void continueToReviewBillingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectInsuranceAddonOption(boolean choice) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void continueOnBillingPageOnPaymentFragment() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyRedFieldsOnAirBillingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void landOnAirResultsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySearchParametersOnExpediaAirResultsPage() throws ParseException {

    }

    @Override
    public void compareMaskedOpaqueWithRetailPrice() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareUnmaskedOpaqueWithRetailPrice() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyActiviesGT() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyOpaqueNotificationPopup() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void opaqueQuoteInResults() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectTravalerInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySeatPreference() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyGT() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void getFlightPriceFromDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareResultsAndDetailsPrice(boolean match) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareTopPriceWithRetailPriceOpaqueMasked() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareTopPriceWithOpaquePriceOpaqueUnMasked() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void addRentalCarViaPackage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyConfirmationPageCarCrossSell(boolean shouldBeDisplayed) {
        throw new UnimplementedTestException("Air not implemented on ROW or mobile!");
    }

    @Override
    public void clickConfirmationPageCarCrossSell() {
        throw new UnimplementedTestException("Air not implemented on ROW or mobile!");
    }

    @Override
    public void selectFlightAbovePrice(double expectedPrice) {
        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());
        airResultsPage.getFlightAbovePrice(expectedPrice);
    }

    @Override
    public void clickOnBookNow() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillTravelerInformation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void payWithSavedCCard() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateProtectionDetailsOnDb(String insuranceCost) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateInsuranceCostIsPercentageOfTotal(double expectedPercentage) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public String getFlightInsuranceCost() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public String getInsuranceCheckText() {
        throw new UnimplementedTestException("Implement me!");
    }

}

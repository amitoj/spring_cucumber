/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.air;

import com.hotwire.test.steps.purchase.PurchaseModel;

import java.text.ParseException;

/**
 * @author v-ypuchkova
 * @since 2013.04
 */
public interface AirPurchaseModel extends PurchaseModel {

    void addRentalCar();

    void setPassengerName(Integer number, String firstName, String middleName, String lastName);

    void setRandomPassengerNameBirthdayGender(Integer number);

    void fillTravelerAndPassengerInfo(boolean guestOrUser);

    void fillCreditCard();

    void saveBillingInformation(String cardName);

    void continueToReviewBillingPage();

    void selectInsuranceAddonOption(boolean choice);

    void continueOnBillingPageOnPaymentFragment();

    void verifyRedFieldsOnAirBillingPage();

    void landOnAirResultsPage();

    void verifySearchParametersOnExpediaAirResultsPage() throws ParseException;

    void compareMaskedOpaqueWithRetailPrice();

    void compareUnmaskedOpaqueWithRetailPrice();

    void verifyActiviesGT();

    void verifyOpaqueNotificationPopup();

    void opaqueQuoteInResults();

    void selectTravalerInfo();

    void verifySeatPreference();

    void verifyGT();

    void getFlightPriceFromDetails();

    void compareResultsAndDetailsPrice(boolean match);

    void compareTopPriceWithRetailPriceOpaqueMasked();

    void compareTopPriceWithOpaquePriceOpaqueUnMasked();

    void addRentalCarViaPackage();

    void verifyConfirmationPageCarCrossSell(boolean shouldBeDisplayed);

    void clickConfirmationPageCarCrossSell();

    void selectFlightAbovePrice(double expectedPrice);

    void clickOnBookNow();

    void fillTravelerInformation();

    void payWithSavedCCard();

    void validateProtectionDetailsOnDb(String insuranceCost);

    void validateInsuranceCostIsPercentageOfTotal(double expectedPercentage);

    String getFlightInsuranceCost();

    String getInsuranceCheckText();
}






/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.hotel;

import com.hotwire.test.steps.purchase.PurchaseModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;

/**
 * @author v-ypuchkova
 * @since 2013.04
 */
public interface HotelPurchaseModel extends PurchaseModel {

    /**
     * this comment
     */
    void fillTravelInfoForHotel();

    @Override
    void saveBillingInformation(String cardName);

    @Override
    void saveBillingInformation(String cardName, String password);

    void saveBillingTotalInBean();

    @Override
    void fillCreditCard();

    void selectSavedCreditCard();

    void checkTicketTypesSortedOrNot();

    void selectActivities();

    void validateBillingErrors(String bookingProfile, boolean isSignedInUser);

    void validateTravelerErrors(String bookingProfile);

    void checkMoreDetailsLinks();

    void verifySummaryOfChargesChargesOnOpaqueHotelBillingPage();

    void verifyErrorMessage();

    void verifyMandatoryFee(String fee);

    void verifyDiscountBannerOnHotelDetails();

    void verifyDiscountBannerOnHotelItinerary(boolean isSignedInUser);

    void verifyMarketingBeacon();

    void continueToBillingFromDetailsBySelectingPrice();

    void landonBillingPageToPurchase();

    void verifyFeesAndTaxesOnConfirmation();

    void verifyValidationMarks(String fields, String marks);

    void verifyResortFeesOnDetailsPage(boolean resortFeeVisible);

    void verifyLastHotelBookedOnOpaqueDetailsPage();

    void chooseRetailResortResult();

    void verifyResortFeesOnBillingTripSummary(boolean resortFeeVisible);

    void completeHotelBookingWithSavedUser();

    void verifySummaryOfChargesOnNewBillingPage();

    void chooseAddNewTravelerFromSelection();

    void verifyDetailsPage(String detailsType);

    void verifyEmailsPopulatedInTravelerInfoBillingPage();

    void verifyPointrollPixelOnCertainPage(String pageName);

    void verifyTriggItPixelOnCertainPage(String pageName);

    void filteredByHoodName(String hoodName);

    void setADAAmenities();

    void checkHotelADAAmenityConsist();

    void fillTravelerUserInfoForGuestUser();

    void fillTravelerUserInfoForLoggedUser();

    void chooseRetailHeroResult();

    void goBackToResultsFromDetailsPage();

    void validateCreditCardLogoVisibility(String visibility, String cardName);

    void verifySaveBillingInfoVisibility(boolean isDisplayed);

    void chooseHotelResultWhoseNameContains(String nameSubstring);

    void validateInsuranceCostRegardingRoomNumbers();

    void verifyAllPaymentOptionsEnabled();

    void verifyCreditCardOnlyEnabled();

    void verifyInsuranceModuleVisibilityOnBillingPage(boolean isVisible);

    void verifyInsuranceUpdateOnBillingTripSummary(boolean isDisplayed);

    void verifyPurchaseDetailsOnBillingAndConfirmationPages();

    String getItineraryNumber();

    void chooseSolutionByName(String name);

    void clickEditCreditCardLink();

    void fillSavedCreditCard();

    int getSolutionNumberWithPrice(String cost, String condition);

    void chooseOpaqueResultWithRoomType(String roomType);

    void clickBackToResultsButton();

    void verifyHotelDetailsPage();

    void verifyHotelTypeOnDetails(String hotelType);

    void verifyHotelTypeOnOneBilling(String hotelType);

    void chooseCurrency(String currency);

    void clickConfirmationPageCarCrossSell();

    void verifyConfirmationPageCarCrossSell(boolean shouldBeDisplayed);

    void verifyOnBillingPage();

    void navigateBackToDetailsPage();

    @Override
    void continueOnBillingPageOnPaymentFragment();

    void verifyRetailChargesAreStoredInDBCorrectly();

    void verifyOpaqueChargesAreStoredInDBCorrectly();

    void verifyAutofillCountryCode(String countryCode);

    void filteredByStarRating(String rating);

    void fillTravelerInfo();

    void fillBillingInfo();

    void selectHotelResultByStarRating(String rating);

    void validateInsuranceCostIsPercentageOfTotal(int expectedPercentage);

    void fillInTravelerInfoWithPhoneNumberAndBook();

    void validateInsuranceCostInDb(String insuranceCost);

    void checkInsuranceCostForHotelOnBillingPage(String insuranceCost);

    String getHotelProtectionCostOnConfirmation();

    //void selectHotelResult();

    void bookSelectedHotel();

    void processInsuranceOnBillingPage();

    void processAgreeAndTerms();

    void processPaymentByType(PurchaseParameters.PaymentMethodType methodType);

    void editCard();
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.trips;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-ngolodiuk
 * Since: 12/26/14
 */
public class TripSummarySteps extends AbstractSteps {

    @Autowired
    @Qualifier("abstractUser")
    UserInformation userInfo;

    @Autowired
    private RequestProperties requestProperties;

    @Autowired
    private TripSummaryService tripSummaryService;


    @When("^I execute trip summary request with sorting by" +
            " (purchase date|travel date|default)?( with start date)?( and with end date)?$")
    public void executeTripSummaryRequestUsingSortingMethod(String sortingMethod, String startDate, String endDate) {
        tripSummaryService.executeTripSummaryRequest(sortingMethod, startDate != null, endDate != null);
    }

    @When("^I want to get (first|second) page of trip summary with sorting" +
            " by (purchase date|travel date|default)?( with start date)?( and with end date)?$")
    public void executeTripSummaryRequestWithPagination(String pageNumber,
                                                        String sortingMethod, String startDate, String endDate) {
        tripSummaryService.executeTripSummaryRequestWithPagination(pageNumber,
                sortingMethod, startDate != null, endDate != null);
    }

    @Then("^I exclude canceled trips from the trip summary$")
    public void excludeCanceledTripsFromTripSummary() {
        requestProperties.setExcludeCanceledRefunded(true);
    }

    @Then("^trip summary is present$")
    public void verifyTripSummaryResponse() {
        tripSummaryService.verifyTripSummaryResponse();
    }

    @Then("^first page of trip summary response is present$")
    public void verifyFirstTripSummaryResponse() {
        tripSummaryService.verifyFirstPageOfTripSummaryResponse();
    }

    @Then("^second page of trip summary response is present$")
    public void verifySecondTripSummaryResponse() {
        tripSummaryService.verifySecondPageOfTripSummaryResponse();
    }


    @And("^I see confirmation code in trip reservation details$")
    public void verifyConfirmationCode() {
        tripSummaryService.verifyConfirmationCode();
    }

    @And("^I see itinerary number in trip reservation details$")
    public void verifyItineraryNumber() {
        tripSummaryService.verifyItineraryNumber();
    }

    @And("^I see booking status in trip reservation details$")
    public void verifyBookingStatus() {
        tripSummaryService.verifyBookingStatus();
    }

    @And("^I see product vertical in trip reservation details$")
    public void verifyProductVertical() {
        tripSummaryService.verifyProductVertical();
    }

    @And("^I see supplier info in trip reservation details$")
    public void verifySupplierInfo() {
        tripSummaryService.verifySupplierInfo();
    }

    @And("^I see booking dates and location in trip reservation details$")
    public void verifyBookingDatesAndLocation() {
        tripSummaryService.verifyBookingDatesAndLocation();
    }

    @And("^I see sorting method is present in response$")
    public void verifySortingMethodIsPresent() {
        tripSummaryService.verifySortingMethodIsPresent();
    }

    @And("^I see sorting method is present in both pages$")
    public void verifySortingMethodIsPresentInBothPages() {
        tripSummaryService.verifySortingMethodIsPresentInBothPages();
    }

    @And("^I see sorting method is by (purchase|travel) date$")
    public void verifySortingMethodInResponse(String sortingMethod) {
        tripSummaryService.verifySortingMethodInResponse(sortingMethod);
    }

    @And("^I see sorting method in both pages is by (purchase|travel) date$")
    public void verifySortingMethodInBothPages(String sortingMethod) {
        tripSummaryService.verifySortingMethodInBothPages(sortingMethod);
    }

    @And("^I see trips are in correct date range$")
    public void verifyTripsAreInCorrectDateRangeInResponse() {
        tripSummaryService.verifyTripsAreInCorrectDateRangeInResponse();
    }

    @And("^I see in both pages trips are in correct date range$")
    public void verifyTripsAreInCorrectDateRangeInBothPages() {
        tripSummaryService.verifyTripsAreInCorrectDateRangeInBothPages();
    }

    @And("^I see trips are sorted by (purchase|travel) date$")
    public void verifyTripsAreSortedByPurchaseDate(String sortingType) {
        if (sortingType.equalsIgnoreCase("purchase")) {
            tripSummaryService.verifyTripsAreSortedByPurchaseDateInResponse();
        }
        else {
            tripSummaryService.verifyTripsAreSortedByTravelDateInResponse();
        }
    }

    @And("^I see in both pages trips are sorted by (purchase|travel) date$")
    public void verifyTripsAreSortedByPurchaseDateInBothPages(String sortingType) {
        if (sortingType.equalsIgnoreCase("purchase")) {
            tripSummaryService.verifyTripsAreSortedByPurchaseDateInBothPages();
        }
        else {
            tripSummaryService.verifyTripsAreSortedByTravelDateInBothPages();
        }
    }

    @And("^I see there are no cancelled trips in response$")
    public void verifyCancelledTripsAreAbsent() {
        tripSummaryService.verifyCancelledTripsAreAbsent();
    }

    @And("^I see address and latitude/longitude are present in trip summary$")
    public void verifyAddressLatitudeLongitude() {
        tripSummaryService.verifyAddressLatitudeLongitude();
    }

    @And("^I see hotel check-in check-out time are present$")
    public void verifyCheckInCheckOutTime() {
        tripSummaryService.verifyCheckInCheckOutTime();
    }

    @Then("^customer profile response is present$")
    public void verifyCustomerProfileResponse() {
        tripSummaryService.verifyCustomerProfileResponse();
    }

    @Then("^I want to get information about payment cards in customer's profile$")
    public void retrievePaymentCardInformation() {
        tripSummaryService.executeCustomerProfileRequestWithCreditCardData();
    }

    @And("^I see random card is present in Customer Profile response$")
    public void verifyRandomCreditCardInfo() {
        tripSummaryService.verifyRandomCreditCardFromCustomerProfile();
    }

    @And("^I add new credit card with random credentials$")
    public void addCreditCard() {
        tripSummaryService.addCreditCard();
    }

    @And("^I try to add new credit card with invalid (number|expiration date)$")
    public void addInvalidCreditCard(String invalidCriteria) {
        tripSummaryService.addInvalidCreditCard(invalidCriteria);
    }

    @And("^I see card was added$")
    public void verifyCardWasAdded() {
        tripSummaryService.verifyCreditCardAdded();
    }

    @And("^I see card was not added$")
    public void verifyCardWasNotAdded() {
        tripSummaryService.verifyCreditCardNotAdded();
    }

    @Then("^I see random card is present in Trip Summary response$")
    public void verifyRandomCreditCardInTripSummary() {
        tripSummaryService.verifyRandomCreditCardFromTripSummaryResponse();
    }

    @And("^I delete added random credit card$")
    public void executeDeleteCreditCardRequest() {
        tripSummaryService.executeDeleteCreditCardRequest();
    }

    @And("^I see deleting of credit card was successful$")
    public void verifyDeleteCreditCardResponse() {
        tripSummaryService.verifyDeleteCreditCardResponse();
    }

}

/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.steps;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.purchase.HotelPurchaseService;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * User: v-dsobko
 * Since: 1/27/15
 */

public class HotelPurchaseSteps extends AbstractSteps {

    @Autowired
    HotelPurchaseService hotelPurchaseService;

    @Autowired
    RequestProperties requestProperties;


    @When("^I execute (opaque|retail) hotel details request$")
    public void executeDetailsRequest(String detailsType) {
        hotelPurchaseService.executeDetailsRequest(detailsType);
    }

    @When("^I use (valid|invalid) hotel coupon code$")
    public void useValidHotelCouponCode(String couponType) {
        if (couponType.equalsIgnoreCase("valid")) {
            hotelPurchaseService.setCouponCode("minimum 3 star rating");
        }
        else {
            hotelPurchaseService.setCouponCode("invalid");
        }
    }

    @Then("^booking summary of charges is present$")
    public void verifyBookingSummaryOfCharges() {
        hotelPurchaseService.checkBookingSummaryOfCharges();
    }

    @Then("^booking number of hotel amenities is present$")
    public void checkBookingNumberOfHotelAmenities() {
        hotelPurchaseService.checkBookingNumberOfHotelAmenities(0);
    }

    @Then("^booking number of hotel photos is present$")
    public void checkBookingNumberOfHotelPhotos() {
        hotelPurchaseService.checkBookingNumberOfHotelPhotos();
    }

    @Then("^extend my stay is present$")
    public void checkExtendMyStay() {
        hotelPurchaseService.checkExtendMyStay();
    }

    @Then("^room info is present$")
    public void room_info_is_present() {
        hotelPurchaseService.checkRoomInfo();
    }

    @Then("^hotel details meta info is present$")
    public void verifyDetailsMetaData() {
        hotelPurchaseService.verifyDetailsMetaData();
    }

    @And("^(opaque|retail) hotel summary of charges is present( in details)?$")
    public void verifyHotelSummaryOfCharges(String solutionType, String page) {
        if (page != null) {
            hotelPurchaseService.verifySummaryOfChargesOnDetails(solutionType);
        }
        else {
            hotelPurchaseService.verifySummaryOfChargesOnBooking();
        }
    }

    @Then("^opaque details number of hotel amenities is greater than (\\d+)$")
    public void verifyOpaqueDetailsNumberOfHotelAmenities(int arg1) {
        hotelPurchaseService.verifyOpaqueDetailsNumberOfHotelAmenities(arg1);
    }

    @Then("^(opaque|retail) details number of sample hotels is greater than (\\d+)$")
    public void verifyOpaqueDetailsNumberOfSampleHotels(String solutionType, int arg1) {
        hotelPurchaseService.verifyOpaqueDetailsNumberOfSampleHotels(solutionType, arg1);
    }

    @Then("^(opaque|retail) details hotel additional info is present$")
    public void verifyOpaqueDetailsAdditionalInfo(String solutionType) {
        hotelPurchaseService.verifyOpaqueDetailsAdditionalInfo(solutionType);
    }

    @Then("^(opaque|retail) details accessibility copy is present$")
    public void verifyOpaqueDetailsAccessibility(String solutionType) {
        hotelPurchaseService.verifyOpaqueDetailsAccessibility(solutionType);
    }

    @Then("^(opaque|retail) details know before you go copy is present$")
    public void verifyOpaqueDetailsKnowBeforeYouGo(String solutionType) {
        hotelPurchaseService.verifyOpaqueDetailsKnowBeforeYouGo(solutionType);
    }

    @Then("^(opaque|retail) details cancellation policy is present$")
    public void verifyOpaqueDetailsCancellationPolicy(String solutionType) {
        hotelPurchaseService.verifyOpaqueDetailsCancellationPolicy(solutionType);
    }

    @Then("^opaque details nearby airports section is present$")
    public void verifyNearbyAirports() {
        hotelPurchaseService.verifyNearbyAirports();
    }

    @Then("^number of trip advisor reviews is greater than (\\d+)$")
    public void verifyNumberOfTripAdvisorReviews(int arg1) {
        hotelPurchaseService.verifyNumberOfTripAdvisorReviews(arg1);
    }

    @Then("^hotel address is present$")
    public void verifyRetailHotelAddress() {
        hotelPurchaseService.verifyRetailHotelAddress();
    }

    @Then("^number of hotel images is greater than (\\d+)$")
    public void verifyNumberOfHotelImages(int arg1) {
        hotelPurchaseService.verifyNumberOfHotelImages(arg1);
    }

    @And("^I see proper fields are revealed for the solution$")
    public void verifyRevealFields() {
        hotelPurchaseService.verifyRevealFieldsForDetails();
    }

    @And("^I see solution is not revealed$")
    public void verifyOpacityIsMaintained() {
        hotelPurchaseService.verifyOpacityIsMaintainedForDetails();
    }

    @And("^(opaque|retail) details legal links are present$")
    public void verifyDetailsLegalLinks(String solutionType) {
        hotelPurchaseService.verifyDetailsLegalLinks(solutionType);
    }

    @Then("^last hotel booked data is present$")
    public void verifyLastHotelBookedData() {
        hotelPurchaseService.verifyLastHotelBookedData();
    }

    @And("^room types info is available$")
    public void verifyRoomTypesInfo() {
        hotelPurchaseService.verifyRoomTypesInfo();
    }

    @And("^associated deals are present$")
    public void verifyAssociatedDealsInfo() {
        hotelPurchaseService.verifyAssociatedDealsInfo();
    }

    @Then("^opaque details distance measurement unit is (.*)$")
    public void verifyDistanceMeasurementUnit(String unit) {
        hotelPurchaseService.verifyDistanceMeasurementUnit(unit);
    }

    @Then("^I get credit card invalid message for hotel booking request$")
    public void verifyInvalidMessageOnBookingWithCreditCard() throws DatatypeConfigurationException {
        hotelPurchaseService.executeBookingRequestAndCatchCardValidationError();
    }

    @Then("^I get hot dollars invalid message for hotel booking request$")
    public void verifyInvalidMessageOnBookingWithHotDollars() throws DatatypeConfigurationException {
        hotelPurchaseService.executeBookingRequestAndCatchHotDollarsError();
    }

    @Then("^I get coupon code invalid message for hotel booking request$")
    public void verifyInvalidMessageOnBookingWithCouponCode() throws DatatypeConfigurationException {
        hotelPurchaseService.executeBookingRequestAndCatchCouponCodeError();
    }

    @And("^I see correct booking currency in response$")
    public void verifyCurrencyInBookingResponse() {
        hotelPurchaseService.verifyCurrencyInBookingResponse();
    }

}

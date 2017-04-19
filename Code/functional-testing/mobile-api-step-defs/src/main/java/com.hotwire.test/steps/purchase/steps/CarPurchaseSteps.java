/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.steps;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.purchase.CarPurchaseService;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * User: v-dsobko
 * Since: 1/27/15
 */

public class CarPurchaseSteps extends AbstractSteps {

    @Autowired
    CarPurchaseService carPurchaseService;

    @Autowired
    RequestProperties requestProperties;


    @When("^I execute car details request$")
    public void executeCarDetailsRequest() {
        carPurchaseService.executeCarDetails();
    }


    @Given("^I verify car details result ID$")
    public void checkCarDetails() {
        carPurchaseService.verifyCarDetailsResultId();
    }

    @And("^car type images are present in details response$")
    public void verifyCarTypeImagesUrlInDetails() {
        carPurchaseService.verifyCarTypeImagesUrlInDetails();
    }

    @And("^car info metadata is present in details$")
    public void verifyCarInfoMetadataOnDetails() {
        carPurchaseService.verifyCarInfoMetadataOnDetails();
    }

    @And("^search criteria info is present$")
    public void verifySearchCriteria() {
        carPurchaseService.verifySearchCriteria();
    }

    @And("^terms of use are present in details$")
    public void verifyTermsOfUse() {
        carPurchaseService.verifyTermsOfUseOnDetails();
    }

    @Then("^debit unfriendly flag is present in details$")
    public void verifyDebitUnfriendlyFlag() {
        carPurchaseService.verifyDebitUnfriendlyFlagOnDetails();
    }

    @Then("^accepted card type field is present in details$")
    public void verifyAcceptedCardTypeField() {
        carPurchaseService.verifyAcceptedCardTypeFieldOnDetails();
    }

    @Then("^prepaid retail field is present in details$")
    public void verifyPrepaidRetailField() {
        carPurchaseService.verifyPrepaidRetailFieldOnDetails();
    }

    @Then("^price (up|down|same) flag is true$")
    public void verifyPriceChange(String isPriceUp) {
        carPurchaseService.verifyPriceChange(isPriceUp);
    }

    @And("^I make reservation with no payment$")
    public void setUpReservationWithNoPayment() {
        requestProperties.setIsPaymentRequired(false);
    }

    @Then("^car summary of charges is present( in details)?$")
    public void verifyCarSummaryOfCharges(String page) {
        if (page != null) {
            carPurchaseService.verifySummaryOfChargesOnDetails();
        }
        else {
            carPurchaseService.verifySummaryOfChargesOnBooking();
        }
    }

    @And("^I (do|do not) select insurance$")
    public void setUpInsuranceForBooking(String negation) {
        carPurchaseService.setInsurance("do".equalsIgnoreCase(negation));
    }

    @Then("^insurance info is (present|absent)( in details)?$")
    public void verifyInsuranceInfo(String insurancePresence, String page) throws Throwable {
        if (page != null) {
            carPurchaseService.verifyInsuranceInfoOnDetails();
        }
        else {
            carPurchaseService.verifyInsuranceInfoOnBooking();
        }
    }

    @And("^car type images are present in booking response$")
    public void verifyCarTypeImageUrlInBooking() {
        carPurchaseService.verifyCarTypeImageUrlInBooking();
    }

    @Then("^accepted card type field is present in booking response$")
    public void verifyAcceptedCardTypeInBooking()  {
        carPurchaseService.verifyAcceptedCardTypeInBooking();
    }

    @Then("^I see correct booking currency in car response$")
    public void verifyCurrencyInCarBookingResponse()  {
        carPurchaseService.verifyCurrencyInCarBookingResponse();
    }

    @Then("^I get credit card invalid message for car booking request$")
    public void verifyInvalidMessageOnBookingWithCreditCard() throws DatatypeConfigurationException {
        carPurchaseService.executeBookingRequestAndCatchCardValidationError();
    }

    @Then("^I get hot dollars invalid message for car booking request$")
    public void verifyInvalidMessageOnBookingWithHotDollars() throws DatatypeConfigurationException {
        carPurchaseService.executeBookingRequestAndCatchHotDollarsError();
    }

    @And("^agency longitude and latitude is present in (details|booking) response$")
    public void verifyLongitudeAndLatitudePresenceInBooking(String responseType) {
        if ("details".equalsIgnoreCase(responseType)) {
            carPurchaseService.verifyLongitudeAndLatitudePresenceInDetails();
        }
        else if ("booking".equalsIgnoreCase(responseType)) {
            carPurchaseService.verifyLongitudeAndLatitudePresenceInBooking();
        }
    }

}

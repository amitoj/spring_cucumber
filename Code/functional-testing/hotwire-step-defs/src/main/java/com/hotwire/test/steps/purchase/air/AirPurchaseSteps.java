/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.air;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;

/**
 * @author v-mzabuga
 * @since 2012.07
 */
public class AirPurchaseSteps extends AbstractSteps {
    private static Logger LOGGER = LoggerFactory.getLogger(AirPurchaseSteps.class);
    private String insuranceCost;

    @Autowired
    @Qualifier("airPurchaseModel")
    private AirPurchaseModel airPurchaseModel;
    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    public void setInsuranceCost(String insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    @When("^I choose a flight(.*) as (.*) a quote$")
    public void selectFirstAirResult(String purchaseOption, String guestOrUser) {
        selectFirstAirResultWithRentalCar(purchaseOption, "", purchaseOption,
                guestOrUser);
    }

    @When("^I choose (retail|opaque) flight and (added) rental car to (purchase) as (user|guest)$")
    public void selectFirstAirResultWithRentalCar(String fairType,
                                                  String rentalOption, String purchaseOption, String guestOrUser) {
        Boolean param = false;

        if (fairType.contains("opaque")) {
            purchaseParameters.setOpaqueRetail("opaque");
        }
        else {
            purchaseParameters.setOpaqueRetail("retail");
        }
        purchaseParameters.setResultNumberToSelect(1);
        airPurchaseModel.selectPGood();
        if (rentalOption.contains("add")) {
            airPurchaseModel.addRentalCar();
        }
        airPurchaseModel.continueToBillingFromDetails();
        if (guestOrUser.equals("user")) {
            param = true;
        }
        if (purchaseOption.contains("purchase")) {
            airPurchaseModel.completePurchase(param);
        }
    }

    @When("^I verify car addon module doesn't exist on the page$")
    public void verifyCarAddonModule() {
        try {
            airPurchaseModel.addRentalCar();
        }
        catch (Exception e) {
            return;
        }
        throw new RuntimeException("Car addon module was found on the page");
    }

    @When("^I add rental car$")
    public void addRentalCar() {
        airPurchaseModel.addRentalCar();
    }

    @And("^(\\d+)(st|nd|rd|th)? passenger first name \"([^\"]*)\", " +
                                         "middle name \"([^\"]*)\" and last name \"([^\"]*)\"$")
    public void setPassengerName(Integer number, String suffix,
                                 String firstName, String middleName, String lastName) {
        airPurchaseModel.setPassengerName(number, firstName, middleName, lastName);
    };

    @And("^(\\d+)(st|nd|rd|th)? passenger with random full name and valid birthday and gender$")
    public void setRandomPassengerNameBirthdayGender(Integer number, String suffix) {
        airPurchaseModel.setRandomPassengerNameBirthdayGender(number);
    };

    @When("^I fill in the traveler information for flight and select the activities and services as (guest|user)$")
    public void fillTravelInfoForFlight(String isUser) {
        airPurchaseModel.fillTravelerAndPassengerInfo(!"guest".equalsIgnoreCase(isUser));
    }

    @When("^I choose (retail|opaque) flight$")
    public void chooseFirstFlightFromResults(String fairType) {
        if (fairType.contains("opaque")) {
            purchaseParameters.setOpaqueRetail("opaque");
        }
        else {
            purchaseParameters.setOpaqueRetail("retail");
        }
        purchaseParameters.setResultNumberToSelect(1);
        airPurchaseModel.selectPGood();
    }

    @When("^I choose a non-opaque quote$")
    public void chooseNonOpaqueQuoteFromResults() {
        purchaseParameters.setOpaqueRetail("retail");
        purchaseParameters.setResultNumberToSelect(1);
        // Do not kill opaque popup notification.
        airPurchaseModel.selectPGood(false);
    }

    @When("^I see an opaque quote in air results$")
    public void opaqueQuoteInResults() {
        airPurchaseModel.opaqueQuoteInResults();
    }

    @Then("^I will see an opaque notification$")
    public void verifyOpaqueNotificationPopup() {
        airPurchaseModel.verifyOpaqueNotificationPopup();
    }

    @When("^I land on air results page$")
    public void landOnAirResultsPage() {
        airPurchaseModel.landOnAirResultsPage();
    }

    @When("^I verify search parameters on Expedia air results page$")
    public void landOnExpediaResultsPage() throws ParseException {
        airPurchaseModel.verifySearchParametersOnExpediaAirResultsPage();
    }


    @And("^I book that flight$")
    public void bookFlightFromDetails() {
        airPurchaseModel.continueToBillingFromDetails();
    }

    @And("^I select the traveler information for flight$")
    public void selectTravalerInfo() {
        airPurchaseModel.selectTravalerInfo();
    }

    @And("^I should see the seat preference for flight$")
    public void selectSeatPreference() {
        airPurchaseModel.verifySeatPreference();
    }

    @Then("^Display Activities and Ground Transportation$")
    public void verifyActiviesGT() {
        airPurchaseModel.verifyActiviesGT();
    }

    @Then("^I should not see the ground transportation$")
    public void verifyGT() {
        // airPurchaseModel.verifyGT();
    }

    @When("^I continue to review billing page$")
    public void continueToReviewBillingPage() {
        airPurchaseModel.continueToReviewBillingPage();
    }

    @Then("^the opaque price should be less then retail price$")
    public void compareOpaqueWithRetailPrice() {
        airPurchaseModel.compareMaskedOpaqueWithRetailPrice();
    }

    @Then("^the unmasked opaque price should be less then retail price$")
    public void compareUnmaskedOpaqueWithRetailPrice() {
        airPurchaseModel.compareUnmaskedOpaqueWithRetailPrice();
    }

    @And("^I get choose flight price$")
    public void getFlightPriceFromDetails() {
        airPurchaseModel.getFlightPriceFromDetails();
    }

    @Then("^Unit price (not)?equal to total price$")
    public void compareResultsAndDetailsPrice(String match) {
        boolean matchstatus = StringUtils.isEmpty(match);
        airPurchaseModel.compareResultsAndDetailsPrice(matchstatus);
    }

    @Then("^the top price should be equal to retail price$")
    public void compareTopPriceWithRetailPriceOpaqueMasked() {
        airPurchaseModel.compareTopPriceWithRetailPriceOpaqueMasked();
    }

    @Then("^the top price should be equal to opaque price$")
    public void compareTopPriceWithOpaquePriceOpaqueUnMasked() {
        airPurchaseModel.compareTopPriceWithOpaquePriceOpaqueUnMasked();
    }

    @When("^I choose (retail|opaque) flight with rental car as package$")
    public void selectAirPlusCarPackage(String fairType) {
        Boolean param = false;

        if (fairType.contains("opaque")) {
            purchaseParameters.setOpaqueRetail("opaque");
        }
        else {
            purchaseParameters.setOpaqueRetail("retail");
        }
        purchaseParameters.setResultNumberToSelect(1);
        airPurchaseModel.selectPGood();
        airPurchaseModel.addRentalCarViaPackage();
        //airPurchaseModel.continueToBillingFromDetails();
        airPurchaseModel.completePurchase(param);
    }

    @When("^I choose (retail|opaque) flight with rental car as package with out complete purchase$")
    public void selectAirPlusCarPackageWith(String fairType) {
        if (fairType.contains("opaque")) {
            purchaseParameters.setOpaqueRetail("opaque");
        }
        else {
            purchaseParameters.setOpaqueRetail("retail");
        }
        purchaseParameters.setResultNumberToSelect(1);
        airPurchaseModel.selectPGood();
        airPurchaseModel.addRentalCarViaPackage();
        //airPurchaseModel.continueToBillingFromDetails();
        //airPurchaseModel.completePurchase(param);
    }

    @When("^I click the air confirmation page car cross sell$")
    public void clickConfirmationPageCarCrossSell() {
        airPurchaseModel.clickConfirmationPageCarCrossSell();
    }

    @Then("^I (will|will not) see the air confirmation car cross sell$")
    public void verifyConfirmationPageCarCrossSell(String state) {
        airPurchaseModel.verifyConfirmationPageCarCrossSell(state.equals("will") ? true : false);
    }

    @Then("^I see all necessary CC fields on Air billing page marked RED$")
    public void verifyRedFieldsOnAirBillingPage() {
        airPurchaseModel.verifyRedFieldsOnAirBillingPage();
    }

    @Then("^I select flight above \\$(\\d+) USD$")
    public void selectFlightAbovePrice(double expectedPrice) throws Throwable {
        airPurchaseModel.selectFlightAbovePrice(expectedPrice);
    }

    @Then("^I book selected flight$")
    public void clickOnBookNow() {
        airPurchaseModel.clickOnBookNow();
    }

    @Then("^I fill in the traveler information as logged user$")
    public void fillTravelerInformation() throws Throwable {
        airPurchaseModel.fillTravelerInformation();
    }

    @Then("^I pay with saved credit card$")
    public void payWithSavedCCard() {
        airPurchaseModel.payWithSavedCCard();
    }

    @Then("^I validate protection details on the Db$")
    public void validateProtectionDetailsOnDb() throws Throwable {
        airPurchaseModel.validateProtectionDetailsOnDb(insuranceCost);
    }

    @Then("^I validate flight insurance is \"([^\"]*)\"% of total cost$")
    public void validateInsuranceCostIsPercentageOfTotal(String strExpectedPercentage) {
        double expectedPercentage = Double.parseDouble(strExpectedPercentage);
        airPurchaseModel.validateInsuranceCostIsPercentageOfTotal(expectedPercentage);
    }

    @Then("^I get insurance cost from review details fragment$")
    public void getFlightInsuranceCost() {
        String cost = airPurchaseModel.getFlightInsuranceCost();
        setInsuranceCost(cost);
    }

    @Then("^I get insurance cost multiplied by (\\d+) passengers$")
    public void getFlightInsuranceCheckedText(int passengers) {
        String text = airPurchaseModel.getInsuranceCheckText();
        Double subText = Double.parseDouble(text.substring(30, 35)) * passengers;
        setInsuranceCost(subText.toString());
    }
}








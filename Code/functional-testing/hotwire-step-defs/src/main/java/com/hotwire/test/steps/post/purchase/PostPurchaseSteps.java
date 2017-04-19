/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.post.purchase;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-ngolodiuk
 * Since: 8/21/14
 */

public class PostPurchaseSteps extends AbstractSteps {

    @Autowired
    @Qualifier("carPostPurchaseModel")
    private PostPurchaseModel model;

    @Given("^I want see print receipt$")
    public void printVersionConfirmationPage() {
        model.clickToSeePrintVersion();
    }

    @Then("^I should see print version page$")
    public void verifyPrintVersionPage() {
        model.verifyPrintVersionPage();
    }

    @Then("^I verify Cancel button on Share itinerary window$")
    public void verifyCancelButtonShareItineraryWindow() {
        model.verifyCancelButtonShareItineraryWindow();
    }

    @When("^I share intinerary via email (.*)$")
    public void shareItineraryViaEmail(String email) {
        model.shareCarItineraryViaEmail(email);
    }

    @When("^I share intinerary for the multiplicity emails (.*) (.*)$")
    public void shareItineraryForMultiplicityEmails(String firstEmail, String secondEmail) {
        model.shareItineraryForMultiplicityEmails(firstEmail, secondEmail);
    }

    @When("^I share intinerary with bad and good emails$")
    public void shareItineraryWithBadAndGoodEmails() {
        model.shareItineraryWithBadAndGoodEmails();
    }

    @When("^I verify Print Trip Details page$")
    public  void verifyPrintTripDetailsPage() {
        model.verifyPrintTripDetailsPage();
    }
}

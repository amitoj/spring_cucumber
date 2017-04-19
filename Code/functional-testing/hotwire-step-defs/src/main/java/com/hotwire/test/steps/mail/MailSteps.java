/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.mail;

/**
 * @author vzyryanov
 *
 */

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 *   MailSteps
 */
public class MailSteps extends AbstractSteps {

    @Autowired
    @Qualifier("mailModel")
    private MailModel mailModel;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParameters carSearchParameters;

    @Autowired
    @Qualifier("hotelSearchParameters")
    private HotelSearchParameters hotelSearchParameters;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;


    @Then("^I should receive an email with \"([^\"]*)\" in subject$")
    public void checkEmail(String subject) throws MessagingException {
        mailModel.checkEmail(subject);
    }

    @Given("^I get (\\d+) emails from (.*) with subject (.*) since last (\\d+) minutes with following in the body:$")
    public void mailFinder(Integer count, String from, String subject, Integer startTime, List<String> body) {
        assertThat(mailModel.assertEmailReceived(from, subject, startTime, count, body)).isTrue();
    }

    @Given("^I get (\\d+) emails from (.*) with subject (.*) since last (\\d+) minutes$")
    public void mailFinder(Integer count, String from, String subject, Integer startTime) {
        assertThat(mailModel.assertEmailReceived(from, subject, startTime, count)).isTrue();
    }

    @Then("^I receive an email with \"([^\"]*)\" in subject and \"([^\"]*)\" text in body$")
    public void verifyTextInEmail(String subject, String expectedText) {
        mailModel.verifyTextInEmail(subject, expectedText);
    }

    @Then("^I should see correct information in the (car|hotel) confirmation letter$")
    public void verifyCarConfirmationLetter(String vertical) {
        if ("car".equals(vertical)) {
            mailModel.verifyCarConfirmationLetter(authenticationParameters, carSearchParameters,
                    purchaseParameters);
        }
        else if ("hotel".equals(vertical)) {
            mailModel.verifyHotelConfirmationLetter(authenticationParameters, hotelSearchParameters,
                    purchaseParameters);
        }
    }

    @Then("^I should see correct information in the car share itinerary letter$")
    public void verifyShareItineraryLetter() {
        mailModel.verifyCarShareItineraryLetter(authenticationParameters, carSearchParameters,
                purchaseParameters, "qa_regression", "Hotwire655!");
    }

    @Then("^I should see correct information in the car share itinerary letter for the several recipients$")
    public void verifyShareItineraryLetterForSeveralRecipients() {
        mailModel.verifyCarShareItineraryLetter(authenticationParameters, carSearchParameters,
                purchaseParameters, "qa_regression", "Hotwire655!");
        mailModel.verifyCarShareItineraryLetter(authenticationParameters, carSearchParameters,
                purchaseParameters, "v-ikomarov", "Expedia#27");
    }

    @Then("^I go to reset password page from received email$")
    public void goToResetPasswordPageFromEmail() {
        mailModel.goToResetPasswordPageFromEmail("qa_regression", "Hotwire655!");
    }


    @Then("^I verify old URL from hotwire password assistance email generate an error$")
    public void verifyOldFromHotwirePasswordAssistanceEmailGenerateError() {
        mailModel.verifyOldFromHotwirePasswordAssistanceEmailGenerateError("qa_regression", "Hotwire655!");
    }

    @Then("^I receive email cancellation letter for retail car$")
    public  void  verifyRetailCarCancellationLetter() {
        mailModel.verifyRetailCarCancellationLetter(purchaseParameters, "qa_regression", "Hotwire655!");
    }

    @When("^I update the current zip code with \"([^\"]*)\" and save changes$")
    public void updateZipCode(String zipCode) throws Throwable {
        mailModel.updateZipCode(zipCode);
    }

    @Then("^The new zipcode for the customer should be reflected in CUSTOMER table$")
    public void newZipcodeIsReflectedInCUSTOMERTable() throws Throwable {
        mailModel.newZipcodeIsReflectedInCUSTOMERTable();
    }

    @Then("^The DBM sign up page should be displayed$")
    public void dbmSignUpPageShouldBeDisplayed() throws Throwable {
        mailModel.dbmSignUpPageShouldBeDisplayed();
    }
}

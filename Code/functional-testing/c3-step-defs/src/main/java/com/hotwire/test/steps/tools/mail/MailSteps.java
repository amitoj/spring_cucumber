/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.mail;

/**
 * @author vzyryanov
 *
 */

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.mail.MessagingException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 *   MailSteps
 */
public class MailSteps extends ToolsAbstractSteps {

    @Autowired
    @Qualifier("mailModel")
    private MailModel mailModel;

    @Autowired
    @Qualifier("c3ItineraryInfo")
    private C3ItineraryInfo c3ItineraryInfo;

    @Then("^I should receive an email with \"([^\"]*)\" in subject$")
    public void checkEmail(String subject) throws MessagingException {
        mailModel.checkEmail(subject);
    }

    //update needed
    @Given("^I get (\\d+) emails from (.*) with subject (.*) since last (\\d+) minutes with following in the body:$")
    public void mailFinder(Integer count, String from, String subject, Integer startTime, List<String> body) {
        assertThat(mailModel.assertEmailReceived(from, subject, startTime, count, body)).isTrue();
    }

    @Given("^I get (\\d+) emails from (.*) with subject (.*) since last (\\d+) minutes$")
    public void mailFinder(Integer count, String from, String subject, Integer startTime) {
        assertThat(mailModel.assertEmailReceived(from, subject, startTime, count)).isTrue();
    }

    @Then("^I receive an email with recent itinerary in body$")
    public void verifyItineraryInEmail() throws Exception {
        mailModel.verifyTextInEmail(c3ItineraryInfo.getItineraryNumber());
    }

    @Then("^I should receive two confirmation emails with same information")
    public void verifyWeHaveTwoConfirmationEmailsWithSameInformation() {
        mailModel.verifyWeHaveTwoConfirmationHotelEmailsWithSameInformation();
    }


    @Then("^I receive an email for recent partial refund$")
    public void verifyEmailForPartialRefund() throws Exception {
        mailModel.verifyEmailForRecentPartialRefund(c3ItineraryInfo.getItineraryNumber(),
                c3ItineraryInfo.getCurrencyCode() + c3ItineraryInfo.getRefundAmount().toString());
    }

    //update needed
    @Then("^I go to reset password page from received email$")
    public void goToResetPasswordPageFromEmail() {
        mailModel.goToResetPasswordPageFromEmail("qa_regression", "Hotwire655!");
    }

    //update needed
    @Then("^I verify old URL from hotwire password assistance email generate an error$")
    public void verifyOldFromHotwirePasswordAssistanceEmailGenerateError() {
        mailModel.verifyOldFromHotwirePasswordAssistanceEmailGenerateError("qa_regression", "Hotwire655!");
    }

    @Then("^I receive an email with temporary password for CSR account$")
    public void verifyTemporaryPasswordEmailForCSRAccount() {
        mailModel.verifyTemporaryPasswordEmailForCSRAccount();
    }

    @Then("^I receive password assistance email$")
    public void verifyPasswordAssistanceEmail() {
        mailModel.verifyPasswordAssistanceEmail();
    }
}


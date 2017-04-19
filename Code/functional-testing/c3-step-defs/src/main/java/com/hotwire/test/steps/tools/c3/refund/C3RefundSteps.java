/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.refund;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * User: v-abudyak
 * Date: 10/01/13
 * Time: 9:27 AM
 */

public class C3RefundSteps extends ToolsAbstractSteps {

    @Autowired
    @Qualifier("refundModel")
    private C3RefundModel model;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private SimpleJdbcDaoSupport databaseSupport;

    @Given("^I choose (.*) recovery reason$")
    public void chooseTestBookingRecoveryReason(String reason) {
        c3ItineraryInfo.setRefundReason(reason);
        model.chooseRecoveryReason();
    }

    @Given("^I make full air refund from partial with Medical recovery reason$")
    public void makeFullAirRefundFromPartial() {
        c3ItineraryInfo.setRefundReason("Medical");
        model.makeFullAirRefundFromPartialWithMedicalReason();
    }


    @Given("^I( don't)? have all needed documents for refund$")
    public void setPossessionOfDocuments(String option) {
        if (null == option) {
            model.setDocumentsForCourtSummons(true);
        }
        else {
            model.setDocumentsForCourtSummons(false);
        }

    }


    @Given("^I try to make refund with Test Booking reason made not by Hotwire$")
    public void chooseTestBookingRecoveryReasonMadeNotByHotwire() {
        model.fillTestBookingRecoveryMadeNotByHotwire();
    }


    @Given("^I choose (.*) recovery reason and click on create workflow$")
    public void chooseCreateWorkFlow(String reason) {
        c3ItineraryInfo.setRefundReason(reason);
        model.chooseCreateWorkFlow();
    }

    @Given("^I create workflow during refund$")
    public void createWorkFlowDuringRefund() {
        model.createWorkFlowDuringRefund();
    }

    @When("^I do a full refund with HotDollars$")
    public void doFullRefundWithHotDollars() {
        model.doFullRefundWithHotDollars(true);
    }

    @When("^I do a (full|partial) refund$")
    public void doRefund(String type) {
        if ("full".equals(type)) {
            model.doFullRefund();
        }
        else {
            model.doPartialRefund();
        }
        if (!model.haveDocumentsForCourtSummons()) {
            model.setPendingStatusOfPurchase(true);
        }
    }

    @When("^I do a partial refund only for (return|depart) segment and (first|second|both) passengers$")
    public void doPartialRefundForReturnSegments(String segment, String passengers) {
        model.doPartialRefund(segment, passengers);
    }

    @When("^I make a partial refund with Death recovery reason$")
    public void partialRefundWithDeathReason() {
        c3ItineraryInfo.setRefundReason("Death");
        model.chooseDeathRecoveryReasonWithPossessionCertificateOfDeath();
        model.doPartialRefund();
    }

    @Then("^I see refund confirmation message$")
    public void verifyRefundConfirmation() {
        model.verifyRefundConfirmation(null);
    }

    @Then("^I see refunded HotDollars in total HotDollars amount$")
    public void verifyRefundedHotDollarsInTotalHotDollarsAmount() {
        model.verifyRefundedHotDollarsInTotalHotDollarsAmount();
    }

    @Then("^I see refund confirmation message with zero value$")
    public void verifyRefundConfirmationWithZeroValue() {
        model.verifyRefundConfirmation(0.00);
    }


    @Then("^I see pending review confirmation message$")
    public void verifyPendingReviewConfirmation() {
        model.verifyPendingReviewConfirmation();
    }

    @Then("^I open itinerary details after refund via breadcrumb$")
    public void openItineraryDetailsAfterRefund() {
        model.openItineraryDetailsByClickOnLinkInBreadcrumbs();
    }

    @When("^I cancel itinerary$")
    public void i_cancel_itinerary() {
        model.doCancellation();
    }

    @Then("^I see \"([^\"]*)\" in confirmation message$")
    public void i_see_confirmation_message(String message) {
        model.verifyC3Message(message);
    }

    @Then("^I see customer email in confirmation message$")
    public void verifyCustomerEmailInConfirmationMsg() {
        model.verifyC3Message(customerInfo.getEmail());
    }

    @Then("^I see purchase status is \"([^\"]*)\"$")
    public void i_see_purchase_status_is(String status) {
        model.verifyPurchaseStatus(status);
    }

    @Then("^I (don't |)see View link in \"Case attached\" column for purchase$")
    public void viewLinkIsAbsentForItinerary(String isViewLinkVisible) {
        if ("don't ".equals(isViewLinkVisible)) {
            model.viewLinkIsAbsentForItinerary();
        }
        else {
            model.viewLinkForCancelledItinerary();
        }
    }

    @When("^I resend booking confirmation$")
    public void i_resend_booking_confirmation() {
        model.doResendBookingConfirmation();
    }

    @Then("^I see \"([^\"]*)\" share itinerary confirmation message$")
    public void i_see_share_itinerary_confirmation_message(String text) {
        model.verifyShareItinerary(text);
    }

    @Given("^I click share itinerary$")
    public void chooseShareItinerary() {
        model.chooseShareItinerary();
    }

    @Given("^I cancel share itinerary$")
    public void cancelShareItinerary() {
        model.cancelShareItinerary();
    }

    @When("^share itinerary window is (closed|opened)$")
    public void checkShareItineraryWindowClosed(String state) {
        model.checkShareItineraryWindowClosed("closed".equals(state));
    }

    @Given("^I leave fields empty and try to share itinerary$")
    public void leaveFieldsEmptyAndShareItinerary() {
        model.clickConfirmShareItinerary();
    }

    @Then("^I see share itinerary \"(.*)\" error message$")
    public void verifyShareItineraryErrorMsg(String msg) {
        model.verifyShareItineraryErrorMsg(msg);
    }

    @Given("^I confirm share itinerary$")
    public void confirmShareItinerary() {
        model.clickConfirmShareItinerary();
    }

    @Given("^I fill share itinerary form$")
    public void fillShareItineraryForm() {
        model.fillShareItineraryForm();
    }

    @Given("^I enter (in|another )?valid email to share itinerary form$")
    public void enterInvalidEmail(String condition) {
        if ("in".equals(condition)) {
            model.enterEmailForShareItinerary(false);
        }
        else {
            model.enterEmailForShareItinerary(true);
        }
    }

    /**
     * Only if need to get 30500 status code from DB!
     * @bshukaylo
     */
    @Then("^I verify status code and amount for a refunded hotel purchase with DB$")
    public void verifyStatusCodeAndAmountForRefundedPurchase() {
        model.verifyStatusCodeAndAmountForRefundedHotelPurchase();
    }

    @Then("^I verify status code and amount for a partial refunded air purchase with DB$")
    public void verifyStatusCodeAndAmountForPartiallyRefundedAirPurchase() {
        //30040
        model.verifyStatusCodeAndAmountForPartiallyRefundedAirPurchase();
    }

    @Then("^I verify void full refund status code for air purchase in DB$")
    public void verifyStatusCodeForVoidFullRefundedAirPurchase() {
        model.verifyStatusCodeForVoidFullRefundedAirPurchase();
    }

    @Then("^I verify full refund status code for purchase in DB$")
    public void verifyStatusCodeForFullRefundedPurchase() {
        model.verifyStatusCodeForFullRefundedPurchase();
    }

    @Then("^I verify confirmation refund response window contains \"(.*)\" text$")
    public void verifyTextInRefundResponsePopUp(String text) {
        model.confirmRefundResponsePopUpText(text);
    }

    @Then("^I close response window$")
    public void closeRefundResponsePopUp() {
        model.closeRefundResponsePopUp();
    }

    @Then("^I do an avs override on itinerary details page$")
    public void doAvsOverride() {
        model.doAvsOverride();
    }

    @When("^I do a cpv override on itinerary details page$")
    public void doCpvOverride() {
        model.doCpvOverride();
    }

    @Then("^I verify status codes in DB for cpv overridden purchase$")
    public void verifyStatusCodeForCpvOverriddenPurchase() {
        model.verifyStatusCodeForCpvOverriddenPurchase();
    }

    @Then("^I verify status codes in DB for avs overridden purchase$")
    public void verifyStatusCodeForAvsOverriddenPurchase() {
        model.verifyStatusCodeForAvsOverriddenPurchase();
    }

}

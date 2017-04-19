/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.casenotes;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.AppMetaData;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 5/8/13
 * Time: 6:32 AM
 * Steps for verification of C3 Case Notes functionality
 */
public class C3CaseNotesSteps extends ToolsAbstractSteps {

    @Autowired
    private AppMetaData appMetaData;

    @Autowired
    @Qualifier("caseNotesModel")
    private C3CaseNotesModel model;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Given("^I'm filling the case notes box \"([^\"]*)\" text$")
    public void setCaseNotesText(String text) {
        model.fillCaseNotesText(text);
    }

    @Given("^I fill case notes mandatory fields$")
    public void fillCaseNecessaryFields() {
        model.fillCaseNecessaryFields();
    }

    @When("^I enter \"([^\"]*)\" value into Notes field$")
    public void i_enter_number_into_Notes_field(String arg1) {
        model.fillCaseNotesText(arg1);
    }

    @Given("^I select Outbound call method of contact$")
    public void selectOutboundCall() {
        model.switchToCaseNotesFrame();
        model.selectMethodOfContact();
    }

    @When("^I click Terms of Use drop down list$")
    public void i_click_Terms_of_Use_dropdown() {
        model.selectTermOfUse();
    }

    @Then("^I see \"([^\"]*)\" value$")
    public void i_see_value(String arg1) {
        model.chooseTermOfUseValue(arg1);
    }

    @When("^I save notes$")
    public void saveCase() {
        model.saveCase();
    }

    @When("^I close notes$")
    public void closeCase() {
        model.closeCase();
    }

    @When("^New case note is created and saved in DB with correct referral source$")
    public void checkReferralSource4CaseNoteInDB() {
        String referralSource = appMetaData.getReferralParams().get("REFERRAL_SOURCE");
        model.checkReferralSource4CaseNoteInDB(referralSource);
    }

    @Then("^I see error message about credit card number$")
    public void verifyCreditCardWarningMessage() {
        model.verifyCreditCardErrorMessage();
    }

    @Then("^Notes with \"([^\"]*)\" number are not saved$")
    public void verifyNotesNotSaved(String arg1) {
        model.verifyNotesNotSaved(arg1);
    }

    @Then("^notes for itinerary are saved$")
    public void case_notes_are_created() {
        model.verifyCaseNotesCreated(c3ItineraryInfo.getItineraryNumber());
    }

    @When("^I clear credit card number in notes$")
    public void i_clear_credit_card_number_in_notes() {
        model.fillCaseNotesText("testNotes");
    }

    @Then("^notes are saved$")
    public void notes_are_saved() {
        model.verifyCaseNotesSaved();
    }

    @Then("^I see recent case notes are closed$")
    public void notes_are_closed() {
        model.verifyCaseNotesClosed();
    }

    @When("^I search for recent case notes$")
    public void searchRecentCaseNotes() {
        model.switchToDefaultContent();
        model.switchToFrame("c3Frame");
        model.searchRecentCaseNotes();
    }

    @Then("^I see \"([^\"]*)\" in the notes$")
    public void i_see_value_in_the_notes(String arg1) {
        model.verifyCaseNotesHasValue(arg1);
    }

    @Then("^I see \"([^\"]*)\" in case notes search results$")
    public void verifySomeValueCaseNotesSearchResults(String msg) {
        model.verifySomeValueCaseNotesSearchResults(msg);
    }

    @Then("^all fields are correct in case notes search results$")
    public void verifyAllFieldsInCaseNotesSearchResults() {
        model.verifyAllFieldsInCaseNotesSearchResults();
    }

    @Then("^I see case note text in results$")
    public void verifyCaseNotestextInResults() {
        model.verifySomeValueCaseNotesSearchResults(c3ItineraryInfo.getCaseNotesText());
    }

    @When("^I see other languages in language drop down$")
    public void verifyCaseNoteLanguages() {
        model.verifyCaseNoteLanguages();
    }

    @Then("^default language is (.*)$")
    public void verifyDefaultLanguage(String language) {
        model.verifyDefaultLanguage(language);
    }

    @Then("^I see case note ID in results$")
    public void verifyCaseNotesID() {
        model.verifyCaseNotesResultsID();
    }

    @Then("^I verify case note content in results$")
    public void verifyCaseNoteContent() {
        model.verifyCaseNoteContent();
    }

    @Then("^I see case note email in results$")
    public void verifyCaseNotesEmail() {
        model.verifyCaseNotesResultsEmail();
    }

    @Then("^I see case note results$")
    public void verifyCaseNoteResults() {
        model.verifyCaseNoteResults();
    }

    @Given("^I see case notes frame$")
    public void selectNewCaseNotes() {
        model.switchToCaseNotesFrame();
        model.setCaseID();
    }

    @Given("^I copy latest result deeplink url$")
    public void copyLatestResultsDeepLink() {
        model.copyLatestResultsDeepLink();
    }

    @Given("^I copy latest details deeplink url$")
    public void copyLatestDetailsDeepLink() {
        model.copyLatestDetailsDeepLink();
    }

    @When("^I cannot change Method of contact$")
    public void verifyContacMethod() {
        model.verifyContactMethod();
    }

    @Given("^I click on New inbound call case link$")
    public void createInboundCase() {
        model.clickNewCaseNotes();
    }

    @Given("^I see unique CaseID$")
    public void checkUniqueCaseID() {
        model.switchToCaseNotesFrame();
        model.checkUniqueCaseID();
    }

    @Given("^I see \"(.*)\" in case notes frame$")
    public void verifyTextinCaseNotesDescription(String keyText) {
        model.verifyCaseNotesHasValue(keyText);
    }

    @Given("^I see (addition|subtraction) of Hot Dollars to multiple customers in case notes$")
    public void verifyMassHotDollarsCaseNotes(String operation) {
        model.verifyMassHotDollarsCaseNotes(operation);
    }

    @Then("^(.*) level disposition appears in case notes frame$")
    public void verifyNextDisposition(String next) {
        if (next.equals("2nd")) {
            model.verifySecondDisposition();
        }
        else if (next.equals("3rd")) {
            model.verifyThirdDisposition();
        }
    }

    @Then("^Outcomes appears in case notes frame$")
    public void verifyOutcomes() {
        model.verifyOutcomes();
    }

    @Then("^all other dispositions are (.*)$")
    public void verifyAllDispositionsAndOutcomes(String value) {
        model.verifyAllDispositionsAndOutcomes(value);
    }

    @Then("^Close case button is (.*)$")
    public void verifyCloseCaseButtonGreyedOut(String option) {
        model.verifyCloseCaseButtonGreyedOut(option);
    }

    @When("^I select (.*) in (1st|2nd|3rd) level disposition$")
    public void selectSomeDisposition(String disposition, String level) {
        model.selectSomeDisposition(disposition, level);
    }

    @Then("^I see Help Desk Contacted dropdown with Yes/No values in it$")
    public void verifyHelpDeskContactDropDown() {
        model.verifyHelpDeskContactDropDown();
    }

    @Then("^Save note button is (.*)$")
    public void verifySaveNoteBtnAvailability(String expectedStatus) {
        model.verifySaveNoteBtnAvailability(expectedStatus);
    }

    @When("^I enter name of contact$")
    public void enterNameOfContact() {
        model.enterNameOfContact();
    }

    @When("^I select any Outcome$")
    public void selectAnyOutcome() {
        model.selectOutcome();
    }

    @Then("^case frame is (displayed|not displayed) on the page$")
    public void verifyNoCaseFrame(String action) {
        if (action.equals("not displayed")) {
            model.verifyNoCaseFrame();
        }
        else {
            model.verifyCaseFrame();
        }
    }
}

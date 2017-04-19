/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.casenotes;

import com.hotwire.selenium.tools.c3.casenotes.C3CaseNotesResultsPage;
import com.hotwire.selenium.tools.c3.casenotes.C3CaseNotesFrame;
import com.hotwire.selenium.tools.c3.casenotes.C3SearchForCasePage;
import com.hotwire.selenium.tools.c3.C3IndexPage;
import com.hotwire.selenium.tools.c3.customer.C3SearchCustomerPage;
import com.hotwire.test.steps.tools.bean.AppMetaData;
import com.hotwire.test.steps.tools.bean.c3.C3CaseNoteInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.util.db.c3.C3CaseNotesDao;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-abudyak
 * Date: 5/8/13
 * Time: 6:33 AM
 */
public class C3CaseNotesModel extends ToolsAbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3CaseNotesModel.class);

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private C3CaseNoteInfo c3CaseNoteInfo;

    @Autowired
    private AppMetaData appMetaData;

    public void fillCaseNecessaryFields() {
        C3CaseNotesFrame c3CaseNotesFrame = new C3CaseNotesFrame(getWebdriverInstance());
        c3CaseNotesFrame.setNameOfContact("Test BDD Contact");
        c3CaseNoteInfo.setNameOfContact(c3CaseNotesFrame.getNameOfContact());
        c3CaseNoteInfo.setChatEmailTrackingNumber("");
        c3CaseNotesFrame.chooseTermOfUseValue();
        c3CaseNoteInfo.setTermsOfUse("advised caller");
        c3CaseNotesFrame.selectFirstLevelDisposition();
        c3CaseNoteInfo.setFirstLevelDisposition(c3CaseNotesFrame.getFirstLevelDispositionText());
        c3CaseNotesFrame.selectSecondLevelDisposition();
        c3CaseNoteInfo.setSecondLevelDisposition(c3CaseNotesFrame.getSecondDispositionText());
        c3CaseNotesFrame.selectThirdLevelDisposition();
        c3CaseNoteInfo.setThirdLevelDisposition(c3CaseNotesFrame.getThirdDispositionText());
        c3CaseNotesFrame.selectOutcome();
        c3CaseNoteInfo.setOutcome(c3CaseNotesFrame.getOutcomesText());
    }

    public void fillCaseNotesText(String text) {
        new C3CaseNotesFrame(getWebdriverInstance()).setCaseNotesText(text);
        c3CaseNoteInfo.setNotes(text);
    }

    public void saveCase() {
        C3CaseNotesFrame c3SearchCustomerPage = new C3CaseNotesFrame(getWebdriverInstance());
        c3SearchCustomerPage.clickSaveNote();
    }

    public void closeCase() {
        C3CaseNotesFrame caseNotesFrame = new C3CaseNotesFrame(getWebdriverInstance());
        c3ItineraryInfo.setCaseNotesNumber(caseNotesFrame.getCaseNotesID());
        caseNotesFrame.clickCloseCase();
    }

    public void verifyCreditCardErrorMessage() {
        C3CaseNotesFrame c3SearchCustomerPage = new C3CaseNotesFrame(getWebdriverInstance());
        assertThat(c3SearchCustomerPage.getErrorMessage()).as("Error message should be present").
                contains("Credit card numbers are prohibited.");
    }

    public void verifyNotesNotSaved(String arg1) {
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).checkCreditCardNumberInDesc(arg1))
                .as("Credit card number is present").isFalse();
    }

    public void verifyCaseNotesSaved() {
        boolean var = new C3CaseNotesFrame(getWebdriverInstance()).checkCaseNotesCreated(c3CaseNoteInfo.getNotes());
        LOGGER.info("Var =  " + var + "");
        assertThat(var).as("Notes text isn't present. Case Notes are not saved").isTrue();
    }

    public void verifyCaseNotesCreated(String itinerary) {
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).checkCaseNotesCreated(itinerary))
                .as("Case Notes are not present").isTrue();
    }

    public void verifyCaseNotesClosed() {
        assertThat(new C3CaseNotesResultsPage(getWebdriverInstance()).getCaseNotesDescription()).contains("closed");
    }

    public void verifyCaseNotesHasValue(String value) {
        LOGGER.info("Verify that message \"" + value + "\" was appear in case notes section..");
        switchToCaseNotesFrame();
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getCaseNotesDescription())
                .as("Notes hasn't " + value).contains(value);
    }

    public void selectTermOfUse() {
        new C3CaseNotesFrame(getWebdriverInstance()).selectTermOfUse();
    }

    public void selectMethodOfContact() {
        new C3CaseNotesFrame(getWebdriverInstance()).selectOutBound();
        c3CaseNoteInfo.setMethodOfContact("Outbound call");
    }

    public void chooseTermOfUseValue(String value) {
        new C3CaseNotesFrame(getWebdriverInstance()).chooseTermOfUseValue(value);
    }

    public void verifyCaseNotesResultsID() {
        assertThat(new C3CaseNotesResultsPage(getWebdriverInstance()).getCaseNoteID())
                .contains(c3ItineraryInfo.getCaseNotesID());
    }

    public void verifyCaseNotesResultsEmail() {
        assertThat(new C3CaseNotesResultsPage(getWebdriverInstance()).getCaseNoteEmail())
                .contains(customerInfo.getEmail());
    }

    public void verifyCaseNoteResults() {
        new C3CaseNotesResultsPage(getWebdriverInstance()).verifySearchResults();
    }

    public void switchToCaseNotesFrame() {
        switchToDefaultContent();
        switchToFrame("notesFrame");
    }

    public void clickNewCaseNotes() {
        new C3IndexPage(getWebdriverInstance()).clickOnCreateNewInboundCallCaseLink();
        c3CaseNoteInfo.setMethodOfContact("Inbound call");
    }

    public void verifySomeValueCaseNotesSearchResults(String msg) {
        assertThat(new C3CaseNotesResultsPage(getWebdriverInstance()).getCaseNotesDescription()).contains(msg);
    }

    public void verifyAllFieldsInCaseNotesSearchResults() {
        String caseNoteDescrip = new C3CaseNotesResultsPage(getWebdriverInstance()).getCaseNotesDescription();
        assertThat(caseNoteDescrip).contains("Case" + c3CaseNoteInfo.getCaseId().toString() + " closed");
        assertThat(caseNoteDescrip).contains("Method of contact: " + c3CaseNoteInfo.getMethodOfContact());
        assertThat(caseNoteDescrip).contains("Name of Contact: " + c3CaseNoteInfo.getNameOfContact());
        assertThat(caseNoteDescrip).contains("Chat/Email Tracking Number:" +
                c3CaseNoteInfo.getChatEmailTrackingNumber());
        assertThat(caseNoteDescrip).contains("Help desk contacted: ");
        assertThat(caseNoteDescrip).contains("Supplier contacted: ");
        assertThat(caseNoteDescrip).contains("Terms of Use: " + c3CaseNoteInfo.getTermsOfUse());
        assertThat(caseNoteDescrip).contains("Privacy Policy: ");
        assertThat(caseNoteDescrip).contains("1rst level disposition: " + c3CaseNoteInfo.getFirstLevelDisposition());
        assertThat(caseNoteDescrip).contains("2nd level disposition: " + c3CaseNoteInfo.getSecondLevelDisposition());
        assertThat(caseNoteDescrip).contains("3rd level disposition: " + c3CaseNoteInfo.getThirdLevelDisposition());
        assertThat(caseNoteDescrip).contains("Outcome: " + c3CaseNoteInfo.getOutcome());
    }

    public void searchRecentCaseNotes() {
        new C3SearchCustomerPage(getWebdriverInstance()).searchForCaseNotes();
        new C3SearchForCasePage(getWebdriverInstance()).searchCaseByID(c3ItineraryInfo.getCaseNotesID());
    }

    public void verifyDefaultLanguage(String language) {
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getSelectedLanguage())
                .as("Default selected language").isEqualTo(language);
    }

    public void verifyMassHotDollarsCaseNotes(String operation) {
        String caseNotes = new C3CaseNotesResultsPage(getWebdriverInstance()).getCaseNotesDescription();
        assertThat(caseNotes).contains(operation).contains("HotDollars to multiple customers");
        Double actualAmount = c3ItineraryInfo.extractPrice(
                caseNotes.split("\n")[6].replaceAll("that expires on.*$", ""));
        assertThat(actualAmount).isEqualTo(customerInfo.getHotDollarsChangeAmount());
    }

    public void verifyContactMethod() {
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).isMethodOfContactDropdownDisplayed()).isFalse();
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getMethodOfContact())
                .contains(c3CaseNoteInfo.getMethodOfContact());
    }

    public void verifyCaseNoteLanguages() {
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getLanguages()).contains("French");
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getLanguages()).contains("German");
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getLanguages()).contains("Italian");
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getLanguages()).contains("Norwegian");
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getLanguages()).contains("Portuguese");
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getLanguages()).contains("Spanish");
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).getLanguages()).contains("Swedish");
    }

    public void selectFirstDisposition(String dispositionName) {
        new C3CaseNotesFrame(getWebdriverInstance()).selectFirstLevelDisposition(dispositionName);
    }

    public void verifySecondDisposition() {
        C3CaseNotesFrame caseNotesFrame = new C3CaseNotesFrame(getWebdriverInstance());
        assertThat(caseNotesFrame.isSecondDispositionDisplayed()).isTrue();
        assertThat(caseNotesFrame.isThirdDispositionDisplayed()).isFalse();
        assertThat(caseNotesFrame.isOutcomesDisplayed()).isFalse();
    }

    public void verifyThirdDisposition() {
        C3CaseNotesFrame caseNotesFrame = new C3CaseNotesFrame(getWebdriverInstance());
        assertThat(caseNotesFrame.isThirdDispositionDisplayed()).isTrue();
        assertThat(caseNotesFrame.isOutcomesDisplayed()).isFalse();
    }

    public void verifyOutcomes() {
        assertThat(new C3CaseNotesFrame(getWebdriverInstance()).isOutcomesDisplayed()).isTrue();
    }

    public void verifyAllDispositionsAndOutcomes(String value) {
        if (value.equals("Z DEAD AIR")) {
            value = value.substring(2);
        }
        C3CaseNotesFrame caseNotesFrame = new C3CaseNotesFrame(getWebdriverInstance());
        assertThat(caseNotesFrame.isSecondDispositionDisplayed()).isTrue();
        assertThat(caseNotesFrame.getSecondDispositionText()).isEqualTo(value);
        assertThat(caseNotesFrame.isThirdDispositionDisplayed()).isTrue();
        assertThat(caseNotesFrame.getThirdDispositionText()).isEqualTo(value);
        assertThat(caseNotesFrame.isOutcomesDisplayed()).isTrue();
        assertThat(caseNotesFrame.getOutcomesText()).isEqualTo(value);
    }

    public void verifyCloseCaseButtonGreyedOut(String option) {
        if (option.contains("greyed out")) {
            assertThat(new C3CaseNotesFrame(getWebdriverInstance()).isCloseCaseButtonDisabled()).isTrue();
        }
        else if (option.contains("available")) {
            assertThat(new C3CaseNotesFrame(getWebdriverInstance()).isCloseCaseButtonDisabled()).isFalse();
        }
    }

    public void selectSomeDisposition(String dispositionName, String level) {
        C3CaseNotesFrame caseNotesFrame = new C3CaseNotesFrame(getWebdriverInstance());
        if (level.equals("1st")) {
            caseNotesFrame.selectFirstLevelDisposition(dispositionName);
        }
        else if (level.equals("2nd")) {
            caseNotesFrame.selectSecondLevelDisposition(dispositionName);
        }
        else if (level.equals("3rd")) {
            caseNotesFrame.selectThirdLevelDisposition(dispositionName);
        }
    }

    public void verifyHelpDeskContactDropDown() {
        C3CaseNotesFrame caseNotesFrame = new C3CaseNotesFrame(getWebdriverInstance());
        assertThat(caseNotesFrame.isHelpDeskContactedDisplayed()).isTrue();
        caseNotesFrame.selectHelpDeskContacted("Yes");
        caseNotesFrame.selectHelpDeskContacted("No");

    }

    public void checkUniqueCaseID() {
        C3CaseNotesFrame caseNotesFrame = new C3CaseNotesFrame(getWebdriverInstance());
        String caseID = caseNotesFrame.getCaseNotesID();
        assertThat(new C3CaseNotesDao(getDataBaseConnection()).checkCaseIDinDB(caseID))
                .as(caseID + " caseID not unique. It existing in DB ").isFalse();
    }

    public void checkReferralSource4CaseNoteInDB(String referralSource) {
        String caseID = c3ItineraryInfo.getCaseNotesID();
        String actualDB = new C3CaseNotesDao(getDataBaseConnection()).getReferralSource4CaseNoteInDB(caseID);
        assertThat(actualDB).as("CaseID - " + caseID + ", " + actualDB +
                " actual in DB, but expected " + referralSource).isEqualTo(referralSource);
    }

    public void verifyCaseNoteContent() {

        C3CaseNotesResultsPage caseNotesResultsPage = new C3CaseNotesResultsPage(getWebdriverInstance());

        List<String> caseNoteContentArray = caseNotesResultsPage.getCaseNoteContent();
        StringBuilder caseNotesContent =  new StringBuilder();
        for (String elem: caseNoteContentArray) {
            caseNotesContent.append(elem);
        }
        String allCaseNoteContent =  String.valueOf(caseNotesContent);
        assertThat(allCaseNoteContent).as("Case note content contains correct itinerary number")
                .contains(c3ItineraryInfo.getItineraryNumber());
        assertThat(allCaseNoteContent).as("Case note content contains correct notes")
                .contains(c3CaseNoteInfo.getNotes());
        assertThat(allCaseNoteContent).as("Case note content contains correct case ID")
                .contains(c3ItineraryInfo.getCaseNotesID());
        assertThat(allCaseNoteContent).as("Case note content contains correct method of contact")
                .contains(c3CaseNoteInfo.getMethodOfContact());
    }

    public void copyLatestResultsDeepLink() {
        String url = new C3CaseNotesFrame(getWebdriverInstance()).clickOnLatestResultUrl();
        assertThat(url).contains("prepareResults.jsp?resultId");
        appMetaData.setDeeplinkResultsUrl(url);
    }

    public void copyLatestDetailsDeepLink() {
        String url = new C3CaseNotesFrame(getWebdriverInstance()).clickOnLatestDetailsUrl();
        assertThat(url).contains("deeplink-details.jsp?resultId=");
        appMetaData.setDeeplinkDetailsUrl(url);
    }

    public void verifySaveNoteBtnAvailability(String expectedStatus) {
        if (expectedStatus.equals("disabled")) {
            assertThat(new C3CaseNotesFrame(getWebdriverInstance()).isSaveNoteButtonDisabled()).isTrue();
        }
        else if (expectedStatus.equals("available")) {
            assertThat(new C3CaseNotesFrame(getWebdriverInstance()).isSaveNoteButtonDisabled()).isFalse();
        }
        else {
            unimplementedTest();
        }
    }

    public void enterNameOfContact() {
        new C3CaseNotesFrame(getWebdriverInstance()).setNameOfContact("Test BDD Contact");
    }

    public void selectOutcome() {
        new C3CaseNotesFrame(getWebdriverInstance()).selectOutcome();
    }

    public void verifyNoCaseFrame() {
        assertThat(isCaseNoteFrameDisplayed()).isFalse();
    }

    public void verifyCaseFrame() {
        assertThat(isCaseNoteFrameDisplayed()).isTrue();
    }

    public void setCaseID() {
        c3CaseNoteInfo.setCaseId(new C3CaseNotesFrame(getWebdriverInstance()).getCaseNotesID());
    }
}

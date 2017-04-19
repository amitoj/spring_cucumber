/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.hotelSupply;


import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.c3.C3HotelSupplyInfo;
import com.hotwire.util.webdriver.WebDriverManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 5/5/14
 * Time: 1:32 PM
 * Contains all steps for Hotel Supply functionality
 */

public class C3HotelSupplySteps extends ToolsAbstractSteps {

    @Autowired
    private C3HotelSupplyModel model;

    @Autowired
    private C3HotelSupplyInfo c3HotelSupplyInfo;

    private String testTitle = "Test_title_" + System.currentTimeMillis();

    @Given("^some hotel from Database$")
    public void getHotelFromDB() {
        model.getHotelFromDataBase();
    }

    @Given("^delete hotel reports config exception for \"(.*)\" hotel_id from DB$")
    public void deleteHotelReportsConfigExceptionFromDB(String hotelID) {
        model.deleteHotelReportsConfigExceptionFromDB(hotelID);
    }

    @Given("^I get a hotel ID with reservations in near future$")
    public void getHotelFromDBWithReservations() {
        model.getHotelIDWithReservations(0, 10);
    }

    @Given("^I get a hotel ID with existing purchase$")
    public void getHotelID_DisplayID_4Purchase() {
        model.getHotelID_DisplayID_4Purchase();
    }

    @Given("^the hotel ID$")
    public void getHotelIDFromDB() {
        model.getHotelIDFromDataBase();
    }

    @Given("^some invalid hotel ID$")
    public void getInvalidHotelID() {
        c3HotelSupplyInfo.setHotelID("jhrei763456j");
    }

    @Given("^hotel with (unique|incorrect) phone number$")
    public void getHotelByPhoneNumber(String type) {
        if (type.equals("unique")) {
            model.getHotelWithUniquePhoneNum();
        }
        else {
            model.getHotelWithIncorrectPhoneNum();
        }

    }

    @Given("^hotel with oversells$")
    public void setUpHotelWithOversells() {
        model.setUpHotelWithOversells();
    }

    @Given("^I get a hotel ID with oversells in near future$")
    public void setUpHotelWithOversellsBetweenDates() {
        model.getHotelIDWithOversellsBetweenDates(0, 10);
    }

    @Given("^I search hotel by ID$")
    public void searchByHotelIDFromDB() {
        model.searchByHotelID(c3HotelSupplyInfo.getHotelID());
    }

    @When("^I search hotel by name only$")
    public void searchByNameOnly() {
        model.searchByNameOnly();
    }

    @When("^I search hotel by name and (state|country|address|zip code)$")
    public void searchHotelByNameState(String address) {
        model.searchHotelByNameAndAddress(address);
    }

    @Then("^I search hotel by (state|country|address|zip code)$")
    public void searchHotelByAddressPart(String address) {
        model.searchHotelByAddressPart(address);
    }

    @When("^I search hotel by phone number$")
    public void searchHotelByPhoneNumber() {
        model.searchHotelByPhoneNumber();
    }

    @When("^I search hotel by 3 letters of hotel name and country$")
    public void searchHotelByPartOfHotelName() {
        model.searchHotelByPartOfHotelName();
    }

    @Given("^I search for reservations on Hotel Oversells page$")
    public void i_search_for_reservations_on_Hotel_Oversells_page() {
        model.searchForHotelOversells();
    }

    @Then("^I see message \"(.*)\"$")
    public void verifyErrorMessage(String message) {
        model.verifyErrorMessage(message);
    }

    @Given("^I see new oversells results window$")
    public void i_select_several_purchases() {
        model.waitForResultWindow();
        model.switchToNewWindow();
    }

    @Then("^I am able to click Save Document button$")
    public void ableToClickSaveButton() {
        model.clickSaveDocument();
        model.switchToDefaultWindow();
        model.switchToFrame("c3Frame");
    }

    @When("^I click Create Workflow button$")
    public void i_click_Create_Workflow_button() {
        model.clickCreateWorkflow();
        model.getWebdriverInstance()
                .switchTo()
                .window(WebDriverManager.getRootWindowHandle(model.getWebdriverInstance()));
        model.switchToFrame("c3Frame");
    }

    @Then("^I see confirmation message \"(.*)\"$")
    public void i_see_amenities_confirmation_message(String message) {
        model.checkSaveAmenitiesConfirmation(message);
    }

    @Then("^I see \"([^\"]*)\" oversell confirmation message$")
    public void i_see_confirmation_message(String value) {
        model.verifyOversellErrorMessage(value);
    }

    @Then("^I see new (.*) case note was added to existing case$")
    public void i_see_new_case_note_was_added_to_existing_case(String caseNoteType) {
        if (caseNoteType.equals("oversell")) {
            model.verifyHotelCaseNotes();
        }
        else if (caseNoteType.equals("amenities")) {
            model.checkCaseNotesText();
        }
    }

    @Then("^I should (?:(not))? be able to deactivate hotel$")
    public void verifyAbilityToDeactivateHotel(String negation) {
        model.verifyAbilityToDeactivateHotel(StringUtils.isEmpty(negation));
    }

    @Then("^I deactivate hotel$")
    public void deactivateHotel() {
        model.deactivateHotel();
    }

    @Then("^I reactivate hotel$")
    public void reactivateHotel() {
        model.reactivateHotel();
    }

    @Then("^I fill hotel activation form$")
    public void fillHotelActivationForm() {
        model.fillHotelActivationForm();
    }

    @Then("^I submit hotel activation form$")
    public void submitHotelActivationForm() {
        model.submitHotelActivationForm();
    }

    @Then("^I see validation message on hotel activation form$")
    public void verifyValidationMessage() {
        model.verifyValidationMessage();
    }

    @Then("^I cancel hotel activation form$")
    public void cancelHotelActivationForm() {
        model.cancelHotelActivationForm();
    }

    @Then("^hotel is active$")
    public void verifyHotelIsActive() {
        model.verifyHotelIsActive();
    }

    @Then("^I see the message that hotel deactivated$")
    public void verifyHotelDeactivated() {
        model.verifyHotelDeactivated();
    }

    @Then("^I see the message that hotel reactivated$")
    public void verifyHotelReactivated() {
        model.verifyHotelReactivated();
    }

    @Given("^I am on Hotel Amenities page$")
    public void i_am_on_Hotel_Amenities_page() {
        model.proceedToAmenitiesPage();
    }

    @When("^I \"([^\"]*)\" \"([^\"]*)\" amenity$")
    public void i_add_minor_amenity(String action, String amenityType) {
        model.operateWithAmenities(action, amenityType);
    }

    @When("^I change status bunch of amenities$")
    public void i_change_status_bunch_of_amenities() {
        model.changeStatusBunchOfAmenities();
    }

    @When("^I save changes$")
    public void i_save_changes() {
        model.saveChangesForAmenities();
    }

    @Then("^I (don't )?see \"(.*)\" link in common tasks$")
    public void checkLinkInCommonTasks(String negation, String link) {
        model.verifyLinkInCommonTasks(StringUtils.isEmpty(negation), link);
    }

    @Then("^I open \"(.*)\" link in common tasks$")
    public void openLinkInCommonTasks(String link) {
        model.openLinkInCommonTasks(link);
    }

    @Then("^I see Hotel Supplier info corresponds to Database$")
    public void verifyHotelSupplierInfo() {
        model.verifyHotelSupplierInfoWithDB();
    }

    @Then("^I see \"(.*)\" message in common tasks$")
    public void checkLinkInCommonTasks(String message) {
        model.verifyMessageInCommonTasks(message);
    }

    @Then("^I update Hotel Tax Rates$")
    public void updateHotelTaxRates() {
        model.updateHotelTaxRates();
    }

    @When("^I select hotel on search results page$")
    public void selectHotelOnSearchResults() {
        model.selectHotelOnSearchResults();
    }

    @When("^I open hotel reports delivery page$")
    public void selectHotelReportsDelivery() {
        model.selectHotelReportsDelivery();
    }

    @When("^I uncheck (.*) report on delivery page$")
    public void uncheckHotelReportsDelivery(String reportType) {
        model.uncheckHotelReportsDelivery(reportType);
    }

    @When("^I verify hotel report status for (.*)in DB$")
    public void verifyHotelReportsStatusInDB(String reportType) {
        model.verifyHotelReportsStatusInDB(reportType);
    }

    @When("^I try to send hotel report by invalid fax number$")
    public void tryToSendReportByInvalidFax() {
        model.tryToSendReportByInvalidFax();
    }

    @When("^I try to send hotel report by invalid email$")
    public void tryToSendReportByInvalidEmail() {
        model.tryToSendReportByInvalidEmail();
    }

    @When("^I create workflow for hotel$")
    public void createWorkflowForHotel() {
        model.createWorkflowForHotel(testTitle);
    }

    @When("^I check current workflow for hotel$")
    public void checkWorkflowForHotel() {
        model.switchToDefaultContent();
        model.switchToFrame("notesFrame");
        model.checkWorkflowForHotel(testTitle);
    }

    @When("^hotel reservation information is correct$")
    public void checkHotelReservationsNearFuture() {
        model.selectHotelReservationsNearFuture(1, 10);
        model.waitForResultWindow();
        model.switchToNewWindow();
        model.checkHotelReservationsNearFuture(0, 10);
    }

    @When("^hotel finance information is correct$")
    public void checkHotelFinanceNearFuture() {
        model.selectHotelFinanceNearFuture(1, 10);
        model.switchToNewWindow();
        model.checkHotelFinanceNearFuture(0, 10);
    }

    @When("^hotel oversells information is correct$")
    public void checkHotelOversellsNearFuture() {
        model.searchForHotelOversellsBetweenDates(1, 10);
        model.waitForResultWindow();
        model.switchToNewWindow();
        model.checkHotelOversellsNearFuture(0, 10);
    }

    @When("^I search hotel guest information by itinerary$")
    public void searchHotelGuestInformationByItinerary() {
        model.selectHotelFinanceInformationByItinerary();
    }

    @When("^Hotel Reservation Details is correct$")
    public void checkHotelReservationDetails() {
        model.checkHotelReservationDetails();
    }

    @When("^I click Manually Bill/Credit Itinerary link$")
    public void clickManuallyBillCreditItineraryLink() {
        model.clickManuallyBillCreditItineraryLink();
    }


    @When("^I successfully create Manual Billing$")
    public void createAndCheckManualBilling() {
        model.createManualBilling();
        model.checkManualBilling();
    }

    @When("^I successfully create Manual Credit")
    public void createAndCheckManualCredit() {
        model.createManualCredit();
        model.checkManualCredit();
    }

    @When("^There is no Resubmit CPV option on the itinerary$")
    public void noResubmitCPVOptionForItinerary() {
        model.checkC3AVSPurchase();
        model.checkResubmitCPVoption();
    }

    @Then("^I verify status code and amount for a manual credit in DB$")
    public void verifyStatusAndAmountManualCredit() {
        model.verifyStatusAndAmountManualCredit();
    }

    @Then("^I verify status code and amount for a manual billing in DB$")
    public void verifyStatusAndAmountManualBilling() {
        model.verifyStatusAndAmountManualBilling();
    }
}

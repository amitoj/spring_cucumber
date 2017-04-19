/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.customer.account;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 9/24/13
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */

public class C3CustomerAccSteps extends ToolsAbstractSteps {

    @Autowired
    private C3CustomerAccModel model;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Given("^I want see (.*) tab$")
    public void selectVertical(String verticalName) {
        hotwireProduct.setProductVertical(verticalName);
        model.selectVertical();
    }

    @Given("^I should see (.*) tab$")
    public void getVerticalTab(String verticalName) {
        model.checkVerticalTab(verticalName);
    }

    @Given("^I see partial refunded purchase with" +
            " (Partially Voided|Partially Refunded|any) status( and correct refund amount)?$")
    public void verifyPartialRefundedPurchaseWithStatus(String status, String amountVerify) {
        model.verifyPartialRefundedPurchase(status, amountVerify);
    }

    @Given("I see recent purchase with (.*) status$")
    public void verifyPastPurchase(String status) {
        model.verifyStatusOfPurchase(status);
    }

    @Given("^I want to see (air|hotel|car|) ?purchases of the customer$")
    public void chooseHotelPurchase(String verticalName) {
        boolean isVerticalSet = !StringUtils.isEmpty(verticalName);
        if (isVerticalSet) {
            hotwireProduct.setProductVertical(verticalName);
        }
        model.selectPurchase();
    }

    @Then("^I see (.*) status for last (.*) purchase$")
    public void verifyStatusOfLastPurchase(String status, String verticalName)  {
        hotwireProduct.setProductVertical(verticalName);
        model.verifyStatusOfLastPurchase(status);
    }

    @Then("^I see that last purchase \"Booked by a CSR\" is (Y|N)$")
    public void verifyWasLastPurchaseBookedByACSR(String expected) {
        model.verifyWasLastPurchaseBookedByACSR(expected);
    }

    @Then("^I see correct customer information$")
    public void verifyCustomerInformation() {
        model.verifyCustomerInformation();
    }

    @Then("^I see only (.*) past purchase")
    public void verifyPastPurchaseCount(String expectedCount) {
        model.verifyPastPurchaseCount(expectedCount);
    }

    @Given("^I choose service recovery$")
    public void doServiceRecovery() {
        model.clickServiceRecovery();
    }

    @Given("^I save total HotDollars amount$")
    public void saveTotalHotDollarsAmount() {
        model.saveTotalHotDollarsAmount();
    }

    @Given("^I see (.*) results on page$")
    public void seeResultsOnPage(Integer number) {
        model.verifyPurchaseNumber(number);
    }

    @When("^I scroll page$")
    public void scrollPage() {
        model.scrollPage();
    }

    @Given("^I scroll past booking page to itinerary(| \\d+)$")
    public void scrollPastBookingPageToItinerary(String itinerary) {
        if ("".equals(itinerary)) {
            model.scrollPastBookingPageToItinerary(c3ItineraryInfo.getItineraryNumber());
        }
        else {
            model.scrollPastBookingPageToItinerary(itinerary.trim());
        }
    }

    @Given("^I create new customer in C3$")
    public void createRandomCustomer() {
        model.createNewCustomerInC3();
    }

    @Given("^I login as customer with known credentials$")
    public void createAccountOnDomesticSite()  {
        model.loginAsCustomerWithKnownCredentials();
    }

    @Given("^I create new customer (with|without) subscription$")
    public void createCustomerWithSubscription(String subscription) {
        if ("without".equalsIgnoreCase(subscription)) {
            model.createCustomerWithSubscription(false);
        }
        else {
            model.createCustomerWithSubscription(true);
        }
    }

    @When("^I fill data for new customer and submit$")
    public void fillRandomDataForCustomer() {
        model.fillDataForRandomCustomer();
    }

    @When("^I try create C3 customer with wrong confirm email$")
    public void createC3CustomerWithWrongConfirmEmail() {
        model.createAccountWithWrongEmailConfirmation();
    }

    @Then("^I see \"([^\"]*)\" create customer error message$")
    public void createCustomerErrorMessage(String msg) {
        model.verifyCreateCustomerErrorMessage(msg);
    }

    @When("^I try create C3 customer with blank fields$")
    public void tryCreateCustomerWithBlankFields() {
        model.tryCreateCustomerWithBlankFields();
    }

    @Then("^I see error messages for blank fields on C3 customer creating page$")
    public void verifyErrorsForBlankFieldsOnC3CustomerCreatingPage() {
        model.verifyErrorsForBlankFieldsOnC3CustomerCreatingPage();
    }

    @Then("^verify customer in DB$")
    public void verifyCustomerInDB() {
        model.verifyCustomerInDB();
    }

    @Given("^I add some phones to customer account$")
    public void addPhonesToCustomer()  {
        model.addRandomPhonesToCustomer();
    }

    @Given("^I see the message that account updated$")
    public void verifyPhoneChangedMsg()  {
        model.verifyPhoneChangedMsg();
    }

    @Given("^I edit customer account information$")
    public void editCustomerAccount()  {
        model.editCustomerAccount();
    }

    @Given("^I edit customer Hot Dollars$")
    public void editHotDollars()  {
        model.editHotDollars();
    }

    @Given("^I add Hot Dollars to itinerary$")
    public void addHotDollarsToItinerary()  {
        model.addHotDollarsToItinerary();
    }

    @When("^I go to multi-customer Hot Dollars Form$")
    public void goToMultiCustHotDollarsForm()  {
        model.goToMultiCustHotDollarsForm();
    }

    @Given("^Hot Dollars of (.*) customers$")
    public void setCustomerEmails(Integer numberOfCustomers)  {
        model.setCustomerEmailsAndHotDollars(numberOfCustomers);
    }

    @Given("^bunch of customer emails \"(.*)\"$")
    public void setBunchOfEmails(String emailsBunch)  {
        model.setBunchOfEmails(emailsBunch);
    }

    @Then("^I see multi-customer Hot Dollars Form$")
    public void verifyMultiCustomerHDForm()  {
        model.verifyMultiCustomerHDForm();
    }

    @When("^I enter given emails to multi-customer HotDollars Form$")
    public void setEmailsForHotDollarsAdding()  {
        model.setEmailsForHotDollarsAdding();
    }

    @Given("^I see Hot Dollars Form$")
    public void verifyHotDollarsForm()  {
        model.verifyHotDollarsForm();
    }

    @Given("^I close Hot Dollars Form$")
    public void closeHotDollarsForm()  {
        model.closeHotDollarsForm();
    }

    @Given("^I verify Hot Dollars form is opened and closed correctly$")
    public void verifyHotDollarsFormOpenAndClose()  {
        model.verifyHotDollarsFormOpenAndClose();
    }

    @Given("^I verify no hotdollars are available$")
    public void verifyNoHotDollarsAvailable()  {
        model.verifyNoHotDollarsAvailable();
    }

    @Given("^I check HotDollar activity for last purchase$")
    public void verifyHotDollarsActivityForPurchase()  {
        model.verifyHotDollarsActivityForPurchase();
    }

    @Given("^I open case notes for HotDollars$")
    public void openCaseNotesFromHotDollars()  {
        model.openCaseNotesFromHotDollars();
    }

    @Given("^I see (addition|subtraction) of HotDollars to multiple customers in case notes$")
    public void openCaseNotesFromHotDollars(String operation)  {
        model.verifyHotDollarsCaseNote(operation);
    }

    @When("^I add to customer account (.*) Hot Dollars$")
    public void addHotDollars(String amount)  {
        model.addHotDollars(amount);
    }

    @When("^I set to customer account (.*) Hot Dollars$")
    public void setHotDollars(String amount)  {
        model.setHotDollars(amount);
    }

    @When("^I add to customer account more HotDollars than total cost$")
    public void addHotDollarsMoreThanMax()  {
        model.addHotDollarsMoreThanMax();
    }

    @When("^I override (.*) Hot Dollars$")
    public void overwriteHotDollars(String amount)  {
        String hdAmount = amount;
        if (StringUtils.isEmpty(amount)) {
            hdAmount = customerInfo.getHotDollarsChange();
        }
        model.overrideHotDollars(hdAmount);
    }

    @When("^I subtract from customer account (.*) Hot Dollars$")
    public void minusHotDollars(String amount)  {
        model.addHotDollars("-" + amount);
    }

    @When("^I subtract from customer account more Hot Dollars than available$")
    public void minusHotDollarsMoreThanAvailable() {
        model.minusHotDollarsMoreThanAvailable();
    }

    @When("^I add to each email (.*) Hot Dollars with expiration (.*) years$")
    public void addHotDollarsToEachCustomer(String amount, Integer years)  {
        model.addHotDollarsToEachCustomer(amount, years);
        customerInfo.setHotDollarsChange(amount);
    }

    @When("^I see mass HotDollars report$")
    public void verifyMassHotDollarsReport()  {
        model.verifyMassHotDollarsReport();
    }

    @Then("^I see that email \"(.*)\" is (not exist|success)$")
    public void verifyEmailStatusInHDReport(String email, String status)  {
        model.verifyEmailStatusInHDReport(email, status);
    }

    @When("^I subtract from each email (.*) Hot Dollars with expiration (.*) years$")
    public void subtractHotDollarsFromEachCustomer(String amount, Integer years)  {
        model.addHotDollarsToEachCustomer("-" + amount, years);
        customerInfo.setHotDollarsChange(amount);
    }

    @Then("^I see message that Hot Dollars amount was updated to each customer$")
    public void verifyHDAddingtoEachCustomer()  {
        model.verifyHDAddingtoEachCustomer();
    }

    @Then("^I see that Hot Dollars added to customer$")
    public void verifyHotDollarsAdded()  {
        model.verifyHotDollarsAdded();
    }

    @Then("^I see error message about maximum HotDollars amount")
    public void verifyHotDollarsErrorMsg()  {
        model.verifyHotDollarsErrorMsg();
    }

    @Then("^I see that Hot Dollars subtracted from customer")
    public void verifyHotDollars()  {
        model.verifyHotDollarsSubtracted();
    }

    @Given("^I see Hot Dollars error message \"(.*)\"$")
    public void verifyHDErrorMsg(String msg) {
        model.verifyHDErrorMsg(msg);
    }

    @Then("^I see Hot Dollars transaction pop up$")
    public void verifyHotDollarsTransactionPopUp()  {
        model.verifyHotDollarsTransactionPopUp();
    }

    @Then("^I verify Hot Dollars amount in database$")
    public void verifyHotDollarsInDB()  {
        model.verifyHotDollarsInDB();
    }

    @Then("^I verify all customer's HotDollars in database$")
    public void verifyAllCustomersHotDollarsInDB()  {
        model.verifyAllCustomersHotDollarsInDB();
    }

    @Then("^I verify all HotDollars expiration dates in database$")
    public void verifyAllHotDollarsExpirationInDB()  {
        model.verifyAllHotDollarsExpirationInDB();
    }

    @Given("^I go to the customer account info$")
    public void i_go_to_the_customer_account_info() {
        model.clickEditAccInfo();
    }

    @When("^I open Edit Payment Methods form$")
    public void openEditPaymentMethodForm() {
        model.openEditPaymentMethodForm();
    }

    @When("^I open Add a new credit card form$")
    public void openAddNewCreditCardForm() {
        model.openAddPaymentMethodForm();
    }

    @Then("^I receive \"(.*)\" successful message$")
    public void verifySuccessMessageOnPaymentMethodForm(String message) {
        model.verifySuccessMessageOnPaymentMethodForm(message);
    }

    @Then("^I receive \"(.*)\" error message$")
    public void verifyErrorMessageOnPaymentMethodForm(String message) {
        model.verifyErrorMessageOnPaymentMethodForm(message);
    }

    @Given("^I see the message that customer information changed successfully$")
    public void verifyCustomerInfoChanged() {
        model.verifyCustomerInfoChanged();
    }

    @When("^I click Reset Password button$")
    public void resetPassword() {
        model.resetPassword();
    }

    @Then("^I confirm password reset$")
    public void confirmPasswordReset() {
        model.confirmPasswordReset();
    }

    @When("^I cancel customer password reset$")
    public void cancelPasswordReset() {
        model.cancelPasswordReset();
    }

    /**
     * Creates credit card with needed name
     * Other params are default visa params.
     */
    @And("^I add a new credit card named \"(.*)\"$")
    public void addANewCreditCardToCustomer(String cardName) {
        model.clickAddANewCardLink(cardName);
    }

    @When("^I set credit card nick name to \"(.*)\" and save$")
    public void setCreditCardNickName(String newName) {
        model.setCreditCardNickName(newName);
    }

    @When("^I set billing address to \"(.*)\" and save$")
    public void setCCBillingAddress(String billingAddress) {
        model.setCCBillingAddress(billingAddress);
    }

    @And("^I delete a credit card named \"(.*)\"$")
    public void deleteCreditCardWithSpecialName(String cardName) {
        model.deleteCreditCardWithSpecialName(cardName);
    }

    @Then("^card was deleted successfully$")
    public void verifyCardDeletedSuccess() {
        model.verifySuccessMessageOnPaymentMethodForm("Payment method has been deleted");
    }

    @Then("^card was added successfully$")
    public void verifyCardAddedSuccess() {
        model.verifySuccessMessageOnPaymentMethodForm("Payment method has been added");
    }

    @When ("^I (un)?subscribe account to newsletters from C3$")
    public void subscribeAccNewSeller(String negation) {
        model.activateSubsNewslettersC3(StringUtils.isEmpty(negation));
    }

    @Then("^I see in DataBase that customer subscription is (Y|N)$")
    public void confirmSubscriptionsDB(String subscription) {
        model.confirmSubscriptionsFromDB(subscription);
    }

    @Then("^I verify that customer fully unsubscribed in DB$")
    public void verifyCustomerFullyUnsubscribedDB() {
        model.verifyCustomerFullyUnsubscribedDB();
    }

    @Then("^I see message that account (.*)$")
    public void verifyAccountStatus(String action) {
        model.verifyAccStatus(action);
    }

    @When("^I deactivate account$")
    public void i_deactivate_account() {
        model.deactivateAccount();
    }

    @When("^I reactivate account$")
    public void i_reactivate_account() {
        model.reactivateAccount();
    }

    @When("^I cancel deactivation$")
    public void i_cancel_deactivation() {
        model.cancelDeactivation();
    }

    @Then("^I see no message displayed about customer account$")
    public void verifyAccMsg() {
        model.verifyNoMessageDisplayed();
    }

    @Then("^I edit itinerary case history$")
    public void clickEditItineraryCaseHistory() {
        model.clickEditItineraryCaseHistory();
    }

    @Then("^I verify content of Printer-Friendly page$")
    public void verifyPrinterFriendlyPage() {
        model.verifyPrinterFriendlyPage();
    }

    @Then("^I edit case entries for customer$")
    public void clickEditCaseEntriesForCustomer() {
        model.clickEditCaseEntriesForCustomer();
    }

    @Then("^I create and see new case note in history$")
    public void createAndSeeNewCaseNoteInHistory() {
        String textNote = "Test Note " + System.currentTimeMillis();
        model.createNewCaseNoteForHistory(textNote);
        model.verifyCreatedCaseNoteInHistory(textNote);
    }

    @Then("^I receive purchase details from database$")
    public void receiveDataForCarPurchase() {
        model.receiveDataForCarPurchase();
    }

    @Then("^I compare data on module with value from DB$")
    public void compareCarData() {
        model.compareCarData();
    }

    @Then("^I (.*)see message about password reset$")
    public void verifyMessageAboutPassword(String isMessageDisplayed) {
        model.verifyMessageAboutPassword(isMessageDisplayed);
    }

    @Then("^I see (.*) in Refunded by field$")
    public void verifyRefundedByField(String value) {
        model.verifyRefundedByField(value);
    }

    @Given("^I go to Black list management page$")
    public void gotoBlackListPage() {
        model.gotoBlackListPage();
    }

    @When("^I (add|remove) customer account (to|from) Black list$")
    public void addRemoveCustomerBlackList(String addRemove, String toFrom) {
        model.addRemoveCustomerBlackList(addRemove);
    }

    @Then("^verify that customer is (not |)in Black list in DB$")
    public void doesCustomerInBlackList(String isInBlackList) {
        if ("not ".equalsIgnoreCase(isInBlackList)) {
            model.doesCustomerInBlackList(false);
        }
        else {
            model.doesCustomerInBlackList(true);
        }
    }

    @Then("^I see message that customer was added to Black list$")
    public void verifyMessageAddedToBlackList() {
        model.verifyMessageAddedToBlackList();
    }

    @Then("^I see message that customer was removed from Black list$")
    public void verifyMessageRemovedFromBlackList() {
        model.verifyMessageRemovedFromBlackList();
    }

    @Then("^I see message that email is not valid for adding to Black list$")
    public void verifyMessageEmailNotValid() {
        model.verifyMessageEmailNotValid();
    }

    @Given("^I go to Blocked Hotels page$")
    public void gotoBlockedHotelsPage() {
        model.gotoBlockedHotelsPage();
    }

    @Given("^I clear all contact details for customer$")
    public void clearCustomerContactDetails() {
        model.clearCustomerContactDetails();
    }

    @When("^I update contact details for customer$")
    public void updateCustomerContactDetails() {
        model.updateCustomerContactDetails();
    }

    @When("I update email for customer with (wrong|valid) format")
    public void updateEmailWrongValidFormat(String isValid) {
        if ("wrong".equals(isValid)) {
            model.updateEmailForCustomer("bla-bla", "bla-bla");
        }
        else {
            model.updateEmailForCustomer(customerInfo.getEmail(), customerInfo.getEmail());
        }
    }

    @When("^I update email for customer with email that already registered$")
    public void updateEmailWithAlreadyRegisteredUser() {
        model.updateEmailWithAlreadyRegisteredUser();
    }

    @Then("^I see errors for blank contact details for customer$")
    public void verifyErrorForBlankContactDetailsForCustomer() {
        model.verifyErrorForBlankContactDetailsForCustomer();
    }

    @Then("^I see error for incorrect email for updated contact details for customer$")
    public void verifyErrorForIncorrectEmailForCustomer() {
        model.verifyErrorForIncorrectEmailForCustomer();
    }

    @Then("^I see error for email that this email already registered for updated contact details for customer$")
    public void verifyErrorForAlreadyRegisteredEmailForCustomer() {
        model.verifyErrorForAlreadyRegisteredEmailForCustomer();
    }

    @Then("^I see confirmation for updated contact details for customer$")
    public void verifyConfirmationForUpdatedContactDetailsForCustomer() {
        model.verifyConfirmationForUpdatedContactDetailsForCustomer();
    }

    @Given("^I am logged as \"([^\"]*)\" with \"([^\"]*)\"$")
    public void loggedIn(String user, String password) {
        model.attemptToAuthenticateUser(user, password);
    }

    @Given("^I am logged in$")
    public void tryToLogin() {
        model.attemptToAuthenticateUser(customerInfo.getEmail(), customerInfo.getPassword());
    }

    @Given("^I am logged as (non-)?express customer$")
    public void loginIntoC3ExpressCustomer(String isNotExpress) {
        model.loginOnSiteWithExpressCustomer(!"non-".equals(isNotExpress));
    }

    @Then("^I am(?:(?:\\s)(not))? authenticated$")
    public void verifyUserExistsAndIsAuthenticated(String nullOrNot) {
        model.verifyUserExistsAndIsAuthenticated(nullOrNot);
    }

    @Given("^I go to My Trips$")
    public void goToMyTrips() {
        model.goToMyTrips();
    }

    @Given("^I open my last booked trip$")
    public void openMyLastBookedTrip() {
        model.openMyLastBookedTrip();
    }

    @Given("^I logged using 5 last digits of credit card$")
    public void loginToMyAccountWith5Digits() {
        model.authenticateWith4LastDigits();
    }

}

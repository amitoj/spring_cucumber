/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.search;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.ProductVertical;
import com.hotwire.test.steps.tools.bean.c3.C3CaseNoteInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3CustomerDao;
import com.hotwire.util.db.c3.C3SearchDao;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 */

public class C3SearchSteps extends ToolsAbstractSteps {

    @Autowired
    private C3SearchModel model;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private C3CaseNoteInfo c3CaseNoteInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private SimpleJdbcDaoSupport databaseSupport;

    public SimpleJdbcDaoSupport getDataBaseConnection() {
        return databaseSupport;
    }

    @Given("^I click on View Resources$")
    public void clickOnViewResources()  {
        model.clickOnViewResources();
    }

    @Given("^I search for customer with \"([^\"]*)\" email$")
    public void searchByEmail(String email) {
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
        customerInfo.setEmail(email);
        String hd = new C3CustomerDao(getDataBaseConnection()).getHotDollarsAmount("test_hotewire_13242@hotwire.com");
        customerInfo.setHotDollars(hd == null ? "0.0" : hd);
        model.clickCustomerSearchLink();
        model.searchForCustomerByEmail(email);
//        model.discardAbandonedCaseNotesAlert();
    }

    @Given("^customer with (.*) email$")
    public void setCustomerEmail(String email)  {
        customerInfo.setEmail(email);
    }

    @Given("^customer with no (hotel|air|car) recent searches$")
    public void setCustomerWithNoRecentSearches(String vertical) {
        hotwireProduct.setProductVertical(vertical);
        customerInfo.setEmail(new C3SearchDao(databaseSupport)
                .getCustomerWithNoRecentSearch(hotwireProduct.getProductVertical().getProductCode()));
    }

    @Given("^customer with no recent searches$")
    public void setCustomerWithNoRecentSearches() {
        customerInfo.setEmail(new C3SearchDao(databaseSupport).getCustomerWithNoRecentSearch());
    }

    @Given("^customer with many recent searches$")
    public void setCustomerWithManySearches() {
        customerInfo.setEmail(new C3SearchDao(databaseSupport).getCustomerWithManyRecentSearch());
    }

    @Given("^I want to search customer by email$")
    public void searchByEmail() {
        searchByEmail(customerInfo.getEmail());
    }

    @Given("^I get recent search tab$")
    public void chooseRecentSearchTab() {
        model.selectRecentSearchPage();
    }

    @Given("^I go to Case history page$")
    public void chooseCaseHistoryPage() {
        model.selectCaseHistoryPage();
    }

    @Then("^I see Case for itinerary$")
    public void verifyCaseForItinerary() {
        model.verifyCaseNoteForItinerary();
    }

    @Given("^I see recent C3 search on the page$")
    public void verifyRecentC3SearchOnPage() {
        model.verifyRecentC3SearchOnPage();
    }

    @Given("^I return back to recent search page$")
    public void getBackToRecentSearchTab() {
        model.getBackToRecentSearchTab();
    }

    @Given("^I get (air|hotel|car) past booking$")
    public void choosePastBookingTab(String tab) {
        model.selectPastBookingLink();
        model.selectPastBookingTab(tab);
    }

    @Given("^I click PNR link for recent air purchase$")
    public void choosePNRLinkForItinerary() {
        model.choosePNRLinkForItinerary();
    }

    @Given("^I click GDS link for recent hotel purchase$")
    @And("^I select recent hotel purchase$")
    public void chooseGDSLinkForItinerary() {
        model.choosePNRLinkForItinerary();
    }

    @Given("^I get past bookings$")
    public void choosePastBookingTab() {
        model.selectPastBookingLink();
    }

    @Given("^I have no any purchase$")
    public void checkNoPurchase() {
        model.selectPastBookingTab("Air");
        model.checkNoPurchaseMessage("Air");
        model.selectPastBookingTab("Hotel");
        model.checkNoPurchaseMessage("Hotel");
        model.selectPastBookingTab("Car");
        model.checkNoPurchaseMessage("Car");
    }

    @Given("^I verify recent search by search ID$")
    public void checkRecentSearchBySearchID() {
        model.checkRecentSearchBySearchID();
    }

    @Given("^I verify recent search is (not )?done by CSR$")
    public void checkRecentSearchByCSR(String param) {
        if (param == null) {
            param = "";
        }

        model.checkRecentSearchByCSR(param);
    }

    @Given("^I verify (hotel|air|car) parameters for recent search$")
    public void checkParametersForRecentSearch(String verticalName) {
        hotwireProduct.setProductVertical(verticalName);
        switch (hotwireProduct.getProductVertical()) {
            case HOTEL:
                model.checkHotelParametersForRecentSearch();
                break;
            case AIR:
                model.checkAirParametersForRecentSearch();
                break;
            case CAR:
                model.checkCarParametersForRecentSearch();
                break;
            default:
                throw new UnimplementedTestException("Vertical is not set!");
        }
    }

    @Given("^I check customer information section for non express customer$")
    public void checkCustomerInfoForNonExpress() {
        model.checkCustomerInfoForNonExpress();
    }

    @Given("^I verify previous recent search is not done by CSR$")
    public void checkPreviousRecentSearchByCSR() {
        model.checkPreviousRecentSearchByCSR();
    }

    @Given("^I verify search ID (not )?appear on result page$")
    public void checkRecentSearchByViaResultsPage(String param) {
        //model.selectRecentSearchBySearchID();
        if (param != null) {
            model.verifySearchIDOnResultPage(false);
        }
        else {
            model.verifySearchIDOnResultPage(true);
        }
    }

    @Given("^I complete recent search through c3$")
    public void completeRecentSearchViaC3() {
        model.selectRecentSearchBySearchID();
    }

    @Given("^I make search for last search on recent search page C3$")
    public void completeLastSearchFromRecentSearch() {
        model.completeLastSearchFromRecentSearch();
    }

    @Given("^I complete to purchase recent search$")
    public void completeToPurchaseRecentSearch() {
        model.selectRecentSearchBySearchID();
    }

    @Given("^I verify past booking by itinerary$")
    public void checkPastBookingByItinerary() {
        model.checkPastBookingByItinerary();
    }

    @Given("^I click first car confirmation link$")
    public void clickFirstCarConfirmationLink() {
        model.clickFirstCarConfirmationLink();
    }

    @Given("^rarely payment holder last name from customers$")
    public void setPaymentHolderLastNameWithFewPurchases() {
        billingInfo.setCcLastName(new C3SearchDao(databaseSupport).getRarelyPaymentMethodHolderLastNameWithPurchases());
    }

    @Given("^rarely passenger last name$")
    public void setPassengerLastNameWithFewPurchases() {
        customerInfo.setLastName(new C3SearchDao(databaseSupport).getRarelyPassengerLastNameWithPurchases());
    }

    @Given("^rarely last name from customers$")
    public void setLastNameWithFewPurchases() {
        customerInfo.setLastName(new C3SearchDao(databaseSupport).getRarelyCustomerLastName());
    }

    @Given("^customer with many hotel purchases$")
    public void setCustomerWithManyPurchases() {
        customerInfo.setEmail(new C3SearchDao(databaseSupport).getEmailWithManyPurchases());
    }

    @Given("^customer without purchases$")
    public void setCustomerWithoutPurchases() {
        customerInfo.setEmail(new C3SearchDao(databaseSupport).getEmailWithoutPurchases());
    }

    @Given("^customer with existing purchases$")
    public void setCustomerWithPurchases() {
        customerInfo.setEmail(new C3SearchDao(databaseSupport).getEmailWithPurchases());
    }

    @Given("^email with few purchases$")
    public void setEmailWithFewPurchases() {
        customerInfo.setEmail(new C3SearchDao(databaseSupport).getEmailWithFewPurchase());
    }

    @Given("^customer with expired HotDollars")
    public void setCustomerWithExpiredHotDollars() {
        Map<String, Object> result = new C3SearchDao(databaseSupport).getCustomerWithExpiredHotDollars();
        customerInfo.setEmail(result.get("email").toString());
        customerInfo.setPassword("hotwire");
        customerInfo.setHotDollars(result.get("amount").toString());
    }

    @Given("^active fraud customer$")
    public void setActiveFraudCustomer() {
        customerInfo.setEmail(new C3CustomerDao(databaseSupport).getActiveFraudEmail());
    }

    @Given("^customer with existing phone number$")
    public void setPhoneNumberToCustomer() {
        customerInfo.setPhoneNumber(new C3SearchDao(databaseSupport).getExistingPhoneNumber());
    }

    @Given("^customer with existing phone number with few purchase$")
    public void setPhoneNumberToCustomerWithFewPurchase() {
        customerInfo.setPhoneNumber(new C3SearchDao(databaseSupport).getExistingPhoneNumberWithFewPurchase());
    }

    @When("^I search for given customer email$")
    public void searchForGivenCustomer() {
        model.clickCustomerSearchLink();
        model.searchForCustomerByEmail(customerInfo.getEmail());
    }

    @When("^I search for given customer purchase$")
    public void searchForGivenCustomerPurchase() {
        switch (customerInfo.getCustomerType()) {
            case PARTNER:
                model.clickPartnerSearchLink();
                model.searchForPartnerByItinerary(c3ItineraryInfo.getItineraryNumber());
                break;
            default:
                model.clickCustomerSearchLink();
                model.searchForCustomerByItinerary(c3ItineraryInfo.getItineraryNumber());
                break;
        }
        model.discardAbandonedCaseNotesAlert();
    }

    @Given("^I click Site search for customer$")
    public void i_click_site_search() {
        model.clickSiteSearchLink();
        model.switchToFrame("iframeSearch");
    }

    @When("^I search for given case note ID$")
    public void searchCaseNoteByID() {
        model.clickOnCaseNotesSearch();
        model.searchCaseNotesById();
    }

    @When("^I search for case notes by itinerary$")
    public void searchCaseNoteByItinerary() {
        model.clickOnCaseNotesSearch();
        model.searchCaseNotesByItinerary();
    }

    @When("I click on details upon confirmation$")
    public void clickOnDetailsUponConfirmation() {
        model.clickOnDetailsUponConfirmation();
    }

    @When("^I search for given case note email$")
    public void searchCaseNoteByEmail() {
        model.clickOnCaseNotesSearch();
        model.searchCaseNotesByEmail();
    }

    @When("^I search for case notes entered by csrcroz1 for (.*) days$")
    public void searchCaseNoteByEmail(Integer days) {
        model.clickOnCaseNotesSearch();
        model.searchCaseNotesByCSRName(days);
    }

    @When("^I go to the rejected purchase details page$")
    public void goToRejectedPurchaseDetails() {
        model.navigateToRejectedPurchaseDetails();
    }

    @Given("^I want to search for a customer$")
    public void searchForCustomer() {
        model.clickCustomerSearchLink();
    }

    @Given("^I want to search valid customer by email$")
    public void searchDefaultCustomerByEmail() {
        model.searchForCustomerByEmail(customerInfo.getEmail());
    }

    @Given("^I want to search customer with \"([^\"]*)\" phone number$")
    public void searchByPhone(String phone) {
        model.clickCustomerSearchLink();
        model.searchForCustomerByPhone(phone);
        model.discardAbandonedCaseNotesAlert();
    }

    @Given("^I want to search customer with alternate phone number$")
    public void searchByAltPhone() {
        model.clickCustomerSearchLink();
        model.searchForCustomerByPhone(customerInfo.getAltPhone());
        model.discardAbandonedCaseNotesAlert();
    }

    @Given("^I want to search customer with primary phone number$")
    public void searchByPrimaryPhone() {
        model.clickCustomerSearchLink();
        model.searchForCustomerByPhone(customerInfo.getPhoneNumber());
        model.discardAbandonedCaseNotesAlert();
    }

    @Given("^I want to search customer with existing confirmation number$")
    public void searchByExistingConfirmationNumber() {
        model.clickCustomerSearchLink();
        model.searchForCustomerByItinerary(c3ItineraryInfo.getItineraryNumber());
    }

    @And("^I want to search customer with \"([^\"]*)\" confirmation number$")
    public void searchBySpecificConfirmationNumber(String number) {
        model.clickCustomerSearchLink();
        model.searchForCustomerByItinerary(number);
    }

    @Given("^I search customer with (existing|invalid) PNR number$")
    public void searchByExistingPnrNumber(String type) {
        model.clickCustomerSearchLink();
        if (type.contains("invalid")) {
            model.searchForCustomerByPnr(c3ItineraryInfo.getPnrNumber() + "222");
        }
        else {
            model.searchForCustomerByPnr(c3ItineraryInfo.getPnrNumber());
        }
    }

    @Given("^I search customer with (existing|invalid) Customer ID$")
    public void searchByCustomerId(String type) {
        if (type.contains("invalid")) {
            model.searchForCustomerByCustomerId(RandomStringUtils.randomNumeric(3).toString());
        }
        else {
            model.searchForCustomerByCustomerId(customerInfo.getCustomerId());
        }
    }

    @Given("^I search customer with (existing|invalid) case ID$")
    public void searchByCaseId(String type) {
        if (type.contains("invalid")) {
            model.searchForCustomerByCaseId(RandomStringUtils.randomNumeric(3).toString());
        }
        else {
            model.searchForCustomerByCaseId(c3CaseNoteInfo.getCaseId());
        }
    }

    @Given("^I search customer with (existing|invalid) GDS reservation number$")
    public void searchByGDSNumber(String type) {
        if (type.contains("invalid")) {
            model.searchForCustomerByHotelReservationNumber(RandomStringUtils.randomNumeric(3).toString());
        }
        else {
            model.searchForCustomerByHotelReservationNumber(c3ItineraryInfo.getHotelReservationNumber());
        }
    }

    @Given("^I search customer with (existing|invalid) air ticket number$")
    public void searchByAirTicketNumber(String  type) {
        if (type.contains("invalid")) {
            model.searchForCustomerByAirTicketNumber(RandomStringUtils.randomNumeric(3).toString());
        }
        else {
            model.searchForCustomerByAirTicketNumber(c3ItineraryInfo.getAirTicketNumber());
        }
    }

    @Given("^I search customer with (existing|invalid) car reservation number$")
    public void searchByCarReservationNumber(String  type) {
        if (type.contains("invalid")) {
            model.searchForCustomerByCarReservationNumber(RandomStringUtils.randomNumeric(3).toString());
        }
        else {
            model.searchForCustomerByCarReservationNumber(c3ItineraryInfo.getCarReservationNumber());
        }
    }

    @When("^I search for customer with last name \"([^\"]*)\"$")
    public void i_search_for_customer_with_last_name(String arg1) {
        model.searchForCustomerByLastName(arg1);
    }

    @Then("^I see search results for \"([^\"]*)\"$")
    public void i_see_search_result(String value) {
        model.checkSearchResults(value);
    }

    @Then("^I see given customer name in search results$")
    public void verifyCustomerNameInResults() {
        model.checkSearchResults(customerInfo.getFullName());
    }

    @Then("^I see \"([^\"]*)\" error message$")
    public void i_see_error_message(String value) {
        model.verifyErrorMessage(value);
    }

    @Then("^I see the entered phone number in Primary Phone No. field$")
    public void verifyPrimaryPhone() {
        model.verifyPrimaryPhone();
    }

    @Then("^customer information is correct$")
    public void verifyCustomerInfo() {
        model.verifyCustomerInfo();
    }

    @Then("^customer type, watermark, breadcrumbs are correct$")
    public void verifyCustomerType() {
        model.verifyCustomerType();
    }

    @Then("^I see less than (\\d+) search results$")
    public void i_see_less_than_search_results(int arg1) {
        model.verifySearchCountResultsByPhone(arg1);
    }

    @When("^I select some customer$")
    public void i_select_some_customer() {
        model.selectSomeCustomer(0);
    }

    @Then("^I see itinerary details page$")
    public void verifyItineraryDetailsPage() {
        model.verifyItineraryDetailsPage();
    }

    @Then("^I verify template of payment details page for (full authorization|bill) table$")
    public void verifyTemplateOfPaymentDetailsPage(String table) {
        model.verifyTemplateOfPaymentDetailsPage(table);
    }


    @Then("^I see air departing segments")
    public void verifyAirDepartingSegments() {
        model.verifyAirDepartingSegments();
    }


    @Then("^I verify content of air departing segments")
    public void verifyContentOfAirDepartingSegments() {
        model.verifyContentOfAirDepartingSegments();
    }

    @Then("^I verify content of air returning segments")
    public void verifyContentOfAirReturningSegments() {
        model.verifyContentOfAirReturningSegments();
    }

    @Then("^I see air returning segments")
    public void verifyAirReturningSegments() {
        model.verifyAirReturningSegments();
    }

    @And("^I (block|unblock) hotel$")
    public void blockHotel(String option) {
        if ("block".equals(option)) {
            model.blockHotel();
        }
        else if ("unblock".equals(option)) {
            model.unblockHotel();
        }
    }

    @And("^I cancel hotel while (blocking|unblocking)$")
    public void cancelHotelBlocking(String option) {
        if ("blocking".equals(option)) {
            model.cancelHotelWhileBlocking();
        }
        else if ("unblocking".equals(option)) {
            model.cancelHotelWhileUnblocking();
        }
    }

    @And("^I click (block|unblock) hotel$")
    public void blockHotelFromItineraryDetailsPage(String option) {
        if ("block".equals(option)) {
            model.clickBlockHotelFromItineraryDetailsPage();
        }
        else if ("unblock".equals(option)) {
            model.clickUnblockHotelFromItineraryDetailsPage();
        }
    }

    @And("^I unblock hotel from blocked hotels page$")
    public void blockHotelFromBlockedHotelsPage() {
        model.unblockHotelFromFromBlockedHotelsPage();
    }

    @And("^I verify hotel is (blocked|unblocked) in DB$")
    public void verifyHotelIsBlockedInDB(String option) {
        if ("blocked".equals(option)) {
            model.verifyHotelIsBlockedInDB();
        }
        else if ("unblocked".equals(option)) {
            model.verifyHotelIsUnBlockedInDB();
        }
    }

    @And("^I see correct date of block for blocked hotel$")
    public void verifyHotelDateOfBlock() {
        model.verifyHotelDateOfBlock();
    }

    @And("^I(\\sdon't)? see hotel in blocked list$")
    public void verifyHotelInBlockedList(String option) {
        if (null == option) {
            model.verifyHotelInBlockedList();
        }
        else if ("don't".equals(option.trim())) {
            model.verifyHotelIsAbsentInBlockedList();
        }
    }

    @Then("^I verify necessary fields on (air|hotel|car|) itinerary details page$")
    public void verifyNecessaryFieldsOnItineraryDetailsPage(String verticalName) {
        hotwireProduct.setProductVertical(verticalName);
        switch (hotwireProduct.getProductVertical()) {
            case HOTEL:
                model.verifyNecessaryFieldsOnHotelItineraryDetailsPage();
                break;
            case CAR:
                model.verifyNecessaryFieldsOnCarItineraryDetailsPage();
                break;
            case AIR:
                model.verifyNecessaryFieldsOnAirItineraryDetailsPage();
                break;
            default:
                throw new UnimplementedTestException("Vertical is not set!");
        }
    }

    @Then("^I see customer details page$")
    public void verifyCustomerDetailsPage() {
        model.verifyCustomerDetailsPage();
    }

    @Given("^I could see billing page for itinerary$")
    public void tryToCheckDisplayBillingPageForItinerary() {
        model.gotoDisplayBillingPageForItinerary();
        model.switchToFrame("iframeBillingPage");
        model.checkDisplayBillingPageForItinerary();
    }

    @Then("^mobile device type is correct$")
    public void verifyMobileDeviceType() {
        model.verifyMobileDeviceType();
    }

    @Then("^application type is correct$")
    public void verifyApplicationType() {
        model.verifyApplicationType();
    }

    @Then("^I see PNR on itinerary details page$")
    public void verifyPNRNumber() {
        model.verifyPNRonItineraryDetails();
    }

    @Then("^I check information for GDS on itinerary details page$")
    public void verifyGDSInfo() {
        model.verifyNecessaryFieldsOnHotelItineraryDetailsPage();
    }

    @Then("^I check customer search error messages$")
    public void checkCustomerSearchErrors() {
        model.checkCustomerSearchErrors();
    }

    @When("^I click View itinerary page upon confirmation link$")
    public void i_click_link() {
        model.clickViewConfirmation();
    }

    @Then("^I see the confirmation page for that purchase$")
    public void verifyItineraryPageUponConfirmation() {
        model.switchToNewWindow();
        model.maximize();
        model.verifyItineraryPageUponConfirmation();
    }

    @Then("^I see the map$")
    public void verifyContactsOnMap() {
        model.verifyGoogleMap();
    }

    @Then("^I see details page upon confirmation$")
    public void verifyItineraryPageUponDetails() {
        model.switchToNewWindow();
        model.verifyItineraryPageUponDetails();
    }

    @Then("^I save hotel address")
    public void saveHotelNameFromItinDetails() {
        model.saveHotelAddress();
    }

    @When("^I search with reference number$")
    public void searchByReferenceNumber() {
        model.clickCustomerSearchLink();
        model.searchByReferenceNumber();
    }

    @When("^I start search with Search Reference Number on customer info page$")
    public void startSearchByReferenceNumberOnCustomerInfo() {
        model.startSearchByReferenceNumberOnCustomerInfo();
    }

    @When("^I get incorrect reference number$")
    public void getIncorrectReferenceNumber() {
        c3ItineraryInfo.setReferenceNumberResults("123ASD123");
    }

    @When("^I see reference number customer validation error$")
    public void checkReferenceNumberCustomerValidationError() {
        model.checkReferenceNumberCustomerValidationError();
    }

    @When("^I see incorrect reference number error$")
    public void checkReferenceNumberCorrectValidationError() {
        model.checkReferenceNumberCorrectValidationError();
    }

    @When("^I see the same reference number$")
    public void checkTheSameReferenceNumber() {
        model.checkTheSameReferenceNumber();
    }

    @And("^I click \"Resend Emails for checked\"$")
    public void resendEmailsForCheckesClick() {
        model.resendEmailsForCheckesClick();
    }

    @And("^I resend emails for multiply itineraries and check the ones$")
    public void resendEmailsForMultiplyItinerariesAndCheck() throws Exception {
        model.resendEmailsForMultiplyItinerariesAndCheck();
    }

    @And("^I search with a \"([^\"]*)\" reference number$")
    public void searchBySpecificReferenceNumber(String number) {
        model.clickCustomerSearchLink();
        model.searchByReferenceNumber(number);
    }

    @When("^I want to search customer with \"([^\"]*)\" firstname \"([^\"]*)\" lastname$")
    public void i_search_customer_with_firstname_lastname(String firstName, String lastName) {
        model.clickCustomerSearchLink();
        model.searchWithFirstLastName(firstName, lastName);
    }

    @When("^I want to search with given customer name$")
    public void searchWithGivenCustomerName() {
        model.clickCustomerSearchLink();
        model.searchWithFirstLastName(customerInfo.getFirstName(), customerInfo.getLastName());
    }

    @When("^I return from search for a customer to C3 Index page$")
    public void searchForAHotwireCustomerLinkClick() {
        model.returnToIndexC3Page();
    }

    @When("^I return to Customer Account$")
    public void returnToCustomerAccount() {
        model.returnToCustomerAccount();
    }

    @When("^I return to C3 Home page$")
    public void returnToC3HomePage() {
        model.returnToC3HomePage();
        model.switchToFrame("c3Frame");
    }

    @Then("^I see account page for \"(.*)\"$")
    public void verifyAccountPageByName(String accountName) {
        model.verifyAccountPageByName(accountName);
    }

    @Then("^I see multiple accounts search results$")
    public void verifyMultipleAccountsSearchResultsPage() {
        model.verifyMultipleAccountsSearchResultsPage();
    }

    @Then("^I choose (air|car|hotel) past purchases for first account$")
    public void choosePastPurchasesForFirstAccountOnMultiplePage(String vertical) {
        model.choosePastPurchasesForFirstAccountOnMultiplePage(vertical);
    }

    @Then("^I see account page related for current Customer ID$")
    public void verifyAccountPageByCustomerId() {
        model.verifyAccountPageByCustomerId();
    }

    @Then("^I see account page related for current case ID$")
    public void verifyAccountPageByCaseId() {
        model.verifyAccountPageByCaseId();
    }

    @Then("^I see account page related for current GDS reservation number$")
    public void verifyPurchaseByGDS() {
        model.verifyPurchaseByGDS();
    }

    @Then("^I see account page related for current air ticket number$")
    public void verifyPurchaseByAirTicketNumber() {
        model.verifyPurchaseByAirTicketNumber();
    }

    @Then("^I see account page related for current car reservation number$")
    public void verifyPurchaseByCarReservationNumber() {
        model.verifyPurchaseByCarReservationNumber();
    }

    @Given("^customer account for password reset$")
    @And("^customer account for deactivation$")
    public void setupCustomerForDeactivation() {
        customerInfo.setEmail(new C3CustomerDao(databaseSupport).getEmailForDeactivation());
        customerInfo.setPassword("hotwire"); //this is a default password for all load test accounts
                                            // which are used for deactivation
    }

    @Given("^(un)?subscribed customer account$")
    public void getUnsubscribedCustomer(String negation) {
        if (StringUtils.isEmpty(negation)) {
            customerInfo.setEmail(new C3CustomerDao(databaseSupport).getSubscribedCustomer());
        }
        else {
            customerInfo.setEmail(new C3CustomerDao(databaseSupport).getUnsubcribedCustomer());
        }
    }

    @Given("^customer hotel purchase with star rating$")
    public void setupHotelPurchaseWithStarRating() {
        hotwireProduct.setProductVertical(ProductVertical.HOTEL);
        model.setupHotelPurchaseWithStarRating();
    }

    @Given("^I (don't )?see hotel star rating link upon confirmation$")
    public void verifyHotelStarRatingLink(String negation) {
        model.verifyHotelStarRatingLink(StringUtils.isEmpty(negation));
    }

    @When("^I open hotel star rating$")
    public void openHotelStarRating() {
        model.openHotelStarRating();
    }

    @When("^I see new window with correct star rating$")
    public void verifyStarRating() {
        model.switchToNewWindow();
        model.verifyStarRatingWindow();
    }

    @When("^hotel star rating change is correct$")
    public void verifyStarRatingChange() {
        model.verifyStarRatingChange();
    }

    @When("^hotel survey information is correct$")
    public void verifyHotelSurvey() {
        model.verifyHotelSurvey();
    }

    @When("^I verify 'view all' and 'view 15' links$")
    public void verifyViewAllView15Links() {
        model.verifyViewAllView15Links();
    }

    @When("^I am looking for purchase done (not )?by csr$")
    public void getPurchasesDoneByCSR(String param) {
        if (param == null) {
            param = "";
        }
        model.getPurchaseDoneByCsr(param);
    }

    @When("^I click View case attached link$")
    public void clickViewCaseAttachedLink() {
        model.clickViewCaseAttachedLink();
    }

    @When("^I check international site booking flag$")
    public void checkIntlSiteBookingFlag() {
        model.checkIntlSiteBookingFlag();
    }

    @Given("^(Air|Hotel|Car) purchase refunded by CSR$")
    public void givenPurchaseRefundedByCRR(String vertical) {
        hotwireProduct.setProductVertical(vertical);
        model.givenPurchaseRefundedByCRR();
    }

    @Given("^(Air|Hotel|Car) purchase refunded by customer$")
    public void givenPurchaseRefundedByCustomer(String vertical) {
        hotwireProduct.setProductVertical(ProductVertical.HOTEL);
        model.givenPurchaseRefundedByCustomer();
    }

    @Given("^Hotel purchase for the blocking$")
    public void givenHotelPurchaseForBlocking() {
        hotwireProduct.setProductVertical(ProductVertical.HOTEL);
        model.givenHotelPurchaseForBlocking();
    }

    @Given("^I open purchase details$")
    public void openPurchaseDetailsByItinerary() {
        model.selectPastBookingLink();
        model.selectPastBookingTab(hotwireProduct.getProductVertical().getProductName());
        model.openPurchaseDetailsByItinerary(c3ItineraryInfo.getItineraryNumber());
    }

    @Then("^I see that Air purchase Search/Booked status is (Y|N)/(Y|N)$")
    public void verifySearchPurchaseStatus(String searched, String booked) {
        model.verifyAirSearchPurchaseStatus(searched, booked);
    }

    @Given("^customer without saved payment methods$")
    public void findCustomerWithoutCreditCards() {
        customerInfo.setEmail(new C3CustomerDao(databaseSupport).getCustomerEmailWithoutSavedPaymentMethods());
    }
}



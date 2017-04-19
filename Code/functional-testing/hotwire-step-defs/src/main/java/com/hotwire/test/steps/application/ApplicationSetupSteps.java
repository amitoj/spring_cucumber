/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.application;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step definitions for whole phoenix WebApp
 */
public class ApplicationSetupSteps extends AbstractSteps {

    @Resource
    protected Properties applicationProperties;

    @Autowired
    @Qualifier("applicationModel")
    ApplicationModel applicationModel;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Resource(name = "creditCards")
    private Map<String, UserInformation> creditCards;

    @Given("^I browse one page back$")
    public void browseOnePageBack() {
        applicationModel.browseOnePageBack();
    }

    @Given("^default dataset$")
    public void setupOrVerifyDefaultData() {
        applicationModel.setupOrVerifyDefaultData();
    }

    @Given("^the application is accessible$")
    public void setupOrVerifyApplicationAndAccessMethod() {
        applicationModel.setupOrVerifyApplicationAndAccessMethod();
        if (applicationModel.getPaymentProcessor().equals("CyberSource")) {
            applicationModel.selectCurrency("GBP", false);
            purchaseParameters.setUserInformation(creditCards.get("CyberSource"));
        }
    }

    /**
     * @param propName
     * @param propValue
     *            use before Given the application is accessible
     */
    @Given("^set property \"([^\"]*)\" to value \"([^\"]*)\"$")
    public void setUtilProperty(String propName, String propValue) {
        applicationProperties.setProperty(propName, propValue);
    }

    @Then("^set property \"([^\"]*)\" to default value$")
    public void dropUtilProperty(String propName) {
        applicationModel.dropRefreshUtilProperty(propName);
    }

    @Given("^set version test \"([^\"]*)\" to value \"([^\"]*)\"$")
    public void addVersionTest(String vtName, String vtValue) {
        applicationModel.addVersionTest(vtName, vtValue);
    }

    @Given("^I(?:'m| am) from \"([^\"]*)\" (?:POS|site)$")
    public void selectPOSWithSuffix(String pos) {
        selectPOS(pos);
    }

    @Given("^from \"([^\"]*)\"$")
    public void selectPOS(String pos) {
        applicationModel.selectPOS(pos);
    }

    @Given("^I load URL for (car|hotel|flight|deals) seo page$")
    public void loadSeoPageURL(String vertical) {
        if ("car".equals(vertical)) {
            applicationModel.setupURL_ToVisit("seo/car");
        }
        else if ("hotel".equals(vertical)) {
            applicationModel.setupURL_ToVisit("seo/hotel");
        }
        else if ("flight".equals(vertical)) {
            applicationModel.setupURL_ToVisit("seo/air");
        }
        else if ("deals".equals(vertical)) {
            applicationModel.setupURL_ToVisit("seo/deals");
        }
    }

    @Given("^I choose my currency code to (CHF|EUR|USD|CAD|AUD|NZD|GBP|DKK|NOK|SEK|HKD|SGD|MXN)$")
    public void selectCurrency(String currencyCode) {
        applicationModel.selectCurrency(currencyCode, false);
    }

    @When("^I force currency to be (EUR|USD)$")
    public void forceCurrency(String currencyCode) {
        applicationModel.selectCurrency(currencyCode, true);
    }

    /**
     * Verify that error was appear on page
     */
    @Given("^I receive immediate (?:\"([^\"]*)\" )?(error|priceCheck|successful) message$")
    public void receiveMessageOnPage(String msgText, String messageType) {
        if ("priceCheck".equals(messageType)) {
            applicationModel.verifyPriceCheckMessageOnPage(msgText, ErrorMessenger.MessageType.valueOf(messageType));
        }
        else {
            applicationModel.verifyMessageOnPage(msgText, ErrorMessenger.MessageType.valueOf(messageType));
        }
    }

    @Given("^I receive error message for the incorrect(\\sorigin|\\sdestination|\\sorigin and destination)? location$")
    public void verifyErrorMessageForIncorrectLocation(String typeOfLocation) {
        applicationModel.verifyErrorMessageForIncorrectLocation(typeOfLocation);
    }

    @Given("^I receive immediate DB (.*) status for the purchase$")
    public void receiveDBStatus(String status) {
        applicationModel.verifyDBPurchaseStatus(status);
    }

    @Given("^I receive immediate DB next status for SID/BID (registration|purchase|search) referrals table$")
    public void receiveDBRegistrationReferralsStatus(String table, List<String> status) {
        applicationModel.verifyDB_SIB_BID_Reg_Referral_Status(status, table);
    }

    @Then("^I verify (.*) user has(\\sno)? saved credit card in DB$")
    public void verifySavedCreditCardInDB(String userEmail, String condition) {
        applicationModel.verifySavedCreditCardInDB(userEmail, condition);
    }

    /** * Verify currency value */
    @Then("^currency is (CHF|EUR|USD|CAD|AUD|MXN|NZD|GBP|DKK|NOK|SEK|HKD|SGD)$")
    public void verifyCurrency(String currency) {
        applicationModel.verifyCurrency(currency);
    }

    @Then("^selected country is \"([^\"]*)\"$")
    public void verifyCountry(String country) {
        applicationModel.verifyCountry(country);
    }

    @Then("^the only supported currenc(?:y|ies) (?:is|are) \"([^\"]*)\"$")
    public void verifySupportedCurrencies(String supportedCurrencies) {
        applicationModel.verifySupportedCurrencies(supportedCurrencies.split("\\|"));
    }

    @Given("^Online Partner Marketing is available$")
    public void setupOnlinePartnerMarketing() {
        applicationModel.setupOnlinePartnerMarketing();
    }

    @Given("^Last Minute Cruise Deals are available$")
    public void activateLastMinuteCruiseDeals() {
        applicationModel.activateLastMinuteCruiseDeals();
    }

    @Given("^Top Cruise Deals are available$")
    public void activateTopCruiseDeals() {
        applicationModel.activateTopCruiseDeals();
    }

    @Given("^I access TripStarter$")
    public void accessToTripStarter() {
        applicationModel.loadTripStarter();
    }

    @Given("^I access GoLocalSearch$")
    public void accessToGoLocalSearch() {
        applicationModel.loadGoLocalSearch();
    }

    @Given("^old MFI layer is displayed$")
    public void displayOldMFILayer() {
        applicationModel.deactivateIntentMediaLayer();
    }

    @Given("^I have load international (US|UK|IE|SE|NO|DK|AU|NZ|DE|HK|SG|MX) hotwire site$")
    public void loadIntlHotwireSite(String country) {
        applicationModel.loadItnlSite(country);
    }

    @Then("^I should redirect www.hotwire.com/(US|UK|IE|SE|NO|DK|AU|NZ|DE|HK|SG|MX) page$")
    public void checkIntlHotwireSiteURL(String country) {
        applicationModel.checkItnlSiteURL(country);
    }

    @Given("^Hotwire Recommendations are available$")
    public void verifyHotwireRecommendations() {
        applicationModel.activateHwRecommendations();
    }

    @And("^validation marks are available$")
    public void validationMarksAreAvailable() {
        applicationModel.activateBillingValidationMarks();
    }

    /**
     * For testing purposes of this project only. Not to be used for real test cases and scenarios.
     */
    @Given("^I want to test session params$")
    public void setTestSessionModifyingParam() {
        applicationModel.setTestSessionModifyingParam();
    }

    /**
     * For testing purposes of this project only. Not to be used for real test cases and scenarios.
     */
    @Then("^I am on the homepage$")
    public void verifyHomepageLandingForTestingOnly() {
        applicationModel.verifyHomepageLandingForTestingOnly();
    }

    @Given("^I refresh page$")
    public void refreshPage() {
        applicationModel.refreshPage();
    }

    @Given("^updated agree and book section is available$")
    public void updatedAgreeAndBookSectionIsAvailable() {
        applicationModel.activateUpdatedAgreeAndBookSectionOnBilling();
    }

    @Given("^reduced number of fields in credit card section is enabled$")
    public void activateReducedSetOfCreditCardFields() {
        applicationModel.activateReducedNumberSetOfCreditCardFields();
    }

    @And("^opaque room photos support is activated$")
    public void activateOpaqueRoomPhotos() {
        applicationModel.activateOpaqueRoomPhotos();
    }

    @Given("^the details cross sell list (is|is not) active$")
    public void activateDetailsXsellList(String state) {
        applicationModel.activateDetailsXsellList(state.trim().equals("is") ? true : false);
    }

    @Given("^Intent Media layer enabled$")
    public void setConversgedHotelLanding() {
        applicationModel.addVersionTest("vt.IMVA2", "2");
    }

    @Given("^new air low price module$")
    public void setNewAirLowPriceModule() {
        applicationModel.setNewAirLowPriceModule();
    }

    @Given("^I have load URL with destination city (.*)$")
    public void loadURLwithDestination(String sDestination) {
        applicationModel.loadURLwithDestCity(sDestination);
    }

    /**
     * Used in car testcases for example for navigating to links like http://www.qa.hotwire.com/car/search-options.jsp?
     * startSearchType=N&startLocation=SFO&selectedCarTypes=ECAR&inputId=index
     * @param sParameter
     */
    @Given("^I navigate to external link (.*)$")
    public void navigateToExternalLink(String sParameter) {
        applicationModel.navigateToExternalLink(sParameter);
    }

    @Given("^I load site with URL path$")
    public void loadSiteWithPath(String path) {
        applicationModel.setupURL_ToVisit(path);
    }

    @Given("^I load URL for referral type (S|SB|N)$")
    public void loadURLForReferralType(String referralType) {
        applicationModel.loadURLForReferralType(referralType);
    }

    @Given("^I have load URL with SID/BID \'(.*)\' param$")
    public void loadURLwithSIDBID(String sSidBid) {
        applicationModel.setupURLwithSidBid(sSidBid);
    }

    @Given("^MeSo ad block is available$")
    public void activateMeSoAds() {
        applicationModel.addVersionTest("vt.MSA13", "2");
        applicationModel.addVersionTest("vt.ARPAD", "2");
    }

    @Given("^the angular application (is|is not) accessible$")
    public void enableAngularApp(String state) {
        applicationModel.enableAngularApp(state.equals("is") ? true : false);
    }

    @Given("^I close all existing pop-up windows$")
    public void closeAllPopUpWindows() {
        applicationModel.closeAllPopUpWindows();
    }

    @When("I click on \"(.*)\"$")
    public void clickOnLink(String linkText) {
        applicationModel.clickOnLink(linkText);
    }

    @When("^I see UHP page")
    public void verifyUHPPage() {
        applicationModel.verifyUHPPage();
    }

    @When("^I verify correct processing of the URL that does not exist on our site")
    public void verifyNonExistentPageForIncorrectURL() {
        applicationModel.verifyNonExistentPageForIncorrectURL();
    }

    @Then("^the old hotel index page is accessible$")
    public void goToAndVerifyOldHotelIndexPage() {
        applicationModel.goToAndVerifyOldHotelIndexPage();
    }

    @Given("^I submit a comment card with comment: \"([^\"]*)\" and page ratings: (\\d+),(\\d+),(\\d+),(\\d+)$")
    public void submitCommentCardWithCommentAndPageRatings(String comment, int content, int design,
        int usability, int overall) throws Throwable {
        applicationModel.submitCommentCard(comment, content - 1, design - 1, usability - 1, overall - 1);
    }

    @Then("^I confirm that the page header reads \"([^\"]*)\"$")
    public void confirmThePageHeader(String header) throws Throwable {
        applicationModel.confirmThePageHeader(header);
    }

    @Then("^Verify content in the page: \"([^\"]*)\"$")
    public void verifyContentInPage(String textInPage) throws Throwable {
        applicationModel.verifyContentInPage(textInPage);
    }

    @Given("^the application window is maximized$")
    public void applicationWindowMaximized() throws Throwable {
        applicationModel.maximizeWindow();
    }

    @When("^I enter an email address in subscription email field$")
    public void enterEmailAddressInSubscriptionEmailField() throws Throwable {
        applicationModel.enterEmailAddressInSubscriptionEmailField();
    }

    @Then("^I verify in DB that columns WANTS_NEWS_LETTER is set to Y and THIRD PARTY is null$")
    public void verifyDBcolumnsWantsNewsLetterAndThirdParty() throws Throwable {
        applicationModel.verifyDBcolumnsWantsNewsLetterAndThirdParty();
    }

    @When("^I enter an intl site registered email in subscription email field$")
    public void enterRegisteredEmailInSubscriptionEmailField() throws Throwable {
        applicationModel.enterRegisteredEmailInSubscriptionEmailField();
    }

    @Given("^we land on bex bucket$")
    public void goToBexBucket() {
        applicationModel.goToBexBucket();
    }

    @Given("^we have no cookies$")
    public void clearCookies() {
        applicationModel.clearCookies();
    }

    @Given("^ESS is enabled$")
    public void enableESS() throws Throwable {
        applicationModel.enableESS();
    }
}


/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.setup;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.AppMetaData;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.ProductVertical;
import com.hotwire.test.steps.tools.bean.c3.C3CsrAccount;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.test.steps.tools.bean.c3.C3MobileInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;

/**
 * v-vzyryanov
 */
public class ToolsSetupSteps extends ToolsAbstractSteps {

    @Autowired
    private ToolsSetupModel model;

    @Autowired
    private AppMetaData appMetaData;

    @Autowired
    private C3MobileInfo c3MobileInfo;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private C3CsrAccount c3CsrAccount;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Given("^(.*) application is accessible$")
    public void setupApplication(String application) throws MalformedURLException {
        appMetaData.setAppType(application);
        model.setupApplication();
    }

    @Given("^I delete all cookies$")
    public void deleteAllCookies() {
        model.deleteAllCookies();
    }

    @Given("^I close popups$")
    public void closePopups() {
        model.killPopups();
    }

    @Given("^I open saved url$")
    public void openSavedUrl() {
        model.openSavedUrl();
    }

    @Given("^I open saved results deeplink url$")
    public void openSavedDeeplinkResultsUrl() {
        model.openDeeplinkResultsUrl();
    }

    @Given("^I open saved details deeplink url$")
    public void openSavedDeeplinkDetailsUrl() {
        model.openDeeplinkDetailsUrl();
    }

    @Given("^(.*) application is accessible with (.*)$")
    public void setupApplicationWithVT(String application, String versionTest) throws MalformedURLException {
        appMetaData.addVersionTest(versionTest);
        setupApplication(application);
    }

    @Given("^I choose \"(.*)\" currency$")
    public void setCurrency(String currency)  {
        billingInfo.setCurrencyCode(currency);
        model.setCurrency();
    }

    @Given("^I go to confirmation page by direct link$")
    public void goToConfirmationPageByLink() throws MalformedURLException {
        model.goToConfirmationPageByLink();
    }

    @Given("^(new|guest|non-express|ex-express|express|express-elite|partner) customer$")
    public void setCustomerType(String customerType) {
        customerInfo.setCustomerType(customerType);
    }

    @Given("^(guest|non-express|ex-express|express|express-elite|partner) customer purchase$")
    public void setupCustomerTypeSpecificPurchase(String customerType) {
        customerInfo.setCustomerType(customerType);
        model.setupC3PurchaseByCustomerType();
        hotwireProduct.setProductVerticalByItinerary(c3ItineraryInfo.getItineraryNumber());
    }

    @Given("^customer with unique name$")
    public void setCustomerWithUniqueName() {
        model.setCustomerWithUniqueName();
    }

    @Given("^customer with popular name$")
    public void setCustomerWithPopularName() {
        model.setCustomerWithPopularName();
    }

    @Given("^I login into C3 with username \"(.*)\"$")
    public void loginIntoC3(String username) {
        model.loginIntoC3(username);
    }

    /**
     * Step for debugging unstable scenarios
     * @param itinerary form logs
     */
    @Given("^customer itinerary is \"(.*)\"$")
    public void debugItinerary(String itinerary) {
        c3ItineraryInfo.setItineraryNumber(itinerary);
    }

    @Given("^I switch to (.*) site using C3 Fare Finder$")
    public void switchToSiteInC3(String countryCode) {
        appMetaData.setIntl(true);
        model.switchToSiteInC3(countryCode);
        model.switchToFrame("iframeSearch");
        //model.clickLogo();
        //model.openLandingPage();
    }

    @Given("^I'm tried to login into C3 with username \"(.*)\" and password \"(.*)\"$")
    public void loginIntoC3(String username, String password) {
        model.tryToLoginInC3(username, password);
    }

    @Given("^I'm tried to login into C3 as given CSR with password \"(.*)\"$")
    public void loginIntoC3AsValidCSRWithSpecifiedPass(String password) {
        loginIntoC3(c3CsrAccount.getAgentName(), password);
    }

    @Given("I see C3 Login Page validation message \"(.*)\"$")
    public void verifyMsgOnC3LoginPage(String msg) {
        model.verifyMsgOnC3LoginPage(msg);
    }

    @Given("^customer (air|car|hotel|) ?purchase with itinerary (.*)$")
    public void setupC3PurchaseDebug(String verticalName, String itinerary) {
        hotwireProduct.setVertical(verticalName);
        c3ItineraryInfo.setItineraryNumber(itinerary);
    }

    @Given("^hotel purchase itinerary after AVC credit card failing$")
    public void setupC3HotelItineraryAVC() {
        hotwireProduct.setVertical("hotel");
        c3ItineraryInfo.setItineraryNumber(model.getItineraryAVC());
    }

    @Given("^customer INTL hotel purchase$")
    public void setupC3IntlPurchase() {
        model.setupC3IntlPurchase();
        appMetaData.setIntl(true);
        hotwireProduct.setVertical("hotel");
    }

    @Given("^customer (air|car|hotel|) ?purchase for (search|cancel|refund|partial refund)$")
    public void setupC3Purchase(String verticalName, String operation) {
        model.setupC3Purchase(verticalName, operation);
    }

    @Given("^customer with air partial refund$")
    public void setupC3CustomerWithPartialRefund() {
        model.setupC3CustomerWithAirPartialRefund();
    }


    @Given("^customer air purchase with email (.*) for partial refund$")
    public void setupC3AirPurchaseByEmail(String email) {
        hotwireProduct.setVertical("Air");
        model.setupC3AirPurchaseByEmail(email);
    }

    @Given("^customer itinerary for hotel with email \"(.*)\"$")
    public void setupC3PurchaseWithEmail(String email) {
        hotwireProduct.setVertical("hotel");
        model.setupC3PurchaseWithEmail(email);
        customerInfo.setEmail(email);
    }

    @Given("^customer (air|car|hotel) purchase without Case$")
    public void setupPurchaseWoCase(String verticalName) {
        //c3ItineraryInfo.setPurchaseOperation("search");
        hotwireProduct.setVertical(verticalName);
//        model.setupC3Purchase();
        model.setupCustomerPurchaseWoCase();
    }

    @Given("^itinerary for (opaque|retail) (car|air|hotel) confirmed purchase$")
    public void setupC3OpaqueRetailCarPurchase(String opacity, String vertical) {
        hotwireProduct.setOpacity(opacity);
        hotwireProduct.setVertical(vertical);
        if ("car".equals(vertical)) {
            model.setupC3CarOpacityPurchase(opacity);
        }
        else if ("air".equals(vertical)) {
            model.setupC3AirOpacityPurchase(opacity);
        }
        else if ("hotel".equals(vertical)) {
            model.setupC3HotelOpacityPurchase(opacity);
        }

    }

    @Given("^hotel purchase with(out)? Hot Dollars$")
    public void setupPurchaseWithHotDollars(String withoutHotdolllars) {
        hotwireProduct.setVertical("hotel");
        model.setupPurchaseWithHotDollars(StringUtils.isEmpty(withoutHotdolllars));
    }

    @Given("^customer email with Hot Dollars$")
    public void setupCustomerWithHotDollars() {
        model.setupCustomerWithHotDollars();
    }

    @Given("^customer email with many Hot Dollars$")
    public void setupCustomerWithManyHotDollars() {
        model.setupCustomerWithManyHotDollars();
    }

    @Given("^customer email with (air|hotel|car) purchases$")
    public void setupCustomerWithPurchases(String verticalName) {
        hotwireProduct.setVertical(verticalName);
        model.setupCustomerWithPurchases();
    }

    @Given("^mobile device purchase on (.*)$")
    public void setupC3MobilePurchase(String deviceType) {
        c3MobileInfo.setDeviceType(deviceType);
        model.setupC3MobilePurchase();
    }

    @Given("^desktop customer purchase$")
    public void setupDesktopPurchase() {
        model.setupDesktopPurchase();
    }

    @Given("^(opaque|retail) (air|car|hotel) purchase for LPG claim$")
    public void setupC3RetailOpaquePurchase(String productType, String verticalName) {
        hotwireProduct.setOpacity(productType);
        hotwireProduct.setVertical(verticalName);
        model.setupC3LPGClaim();
    }

    @Given("^(air|car|hotel) purchase for LPG refund request")
    public void setupLPGRefundRequest(String verticalName) {
        hotwireProduct.setVertical(verticalName);
        model.setupLPGRefundRequest();
    }

    @Given("^opaque car purchase with LPG claim made before$")
    public void setupPreviousLPGPurchase() {
        hotwireProduct.setOpacity("opaque");
        hotwireProduct.setVertical("car");
        model.setupPreviousLPGPurchase();
    }

    @Given("^opaque car purchase with expired LPG claim")
    public void setupExpiredLPGPurchase() {
        hotwireProduct.setOpacity("opaque");
        hotwireProduct.setVertical("car");
        model.setupExpiredLPGPurchase();
    }

    @Given("^(car|hotel) purchase with expired LPG refund request")
    public void setupExpiredLPGRefundRequest(String verticalName) {
        hotwireProduct.setVertical(verticalName);
        model.setupExpiredLPGRefundRequest();
    }

    @Given("^customer air purchase for void process$")
    public void setupC3CustomerAirVoid() {
        model.setupC3AirVoidPurchase();
        hotwireProduct.setProductVertical(ProductVertical.AIR);
    }

    @Given("^the customer (air|car|hotel|) ?past search$")
    public void setupPastSearch(String verticalName) {
        hotwireProduct.setVertical(verticalName);
        model.setupPastSearch();
    }

    @Given("^the customer with known (air|car|hotel) PNR purchase number$")
    public void setupC3CustomerWithPnrNumber(String vertical) {
        hotwireProduct.setVertical(vertical);
        model.setupPNRnumber();
    }

    @Given("^the customer with known customer ID$")
    public void setupCustomerId() {
        model.setupCustomerId();
    }

    @Given("^the customer with known case ID$")
    public void setupCaseId() {
        model.setupCaseId();
    }

    @Given("^the customer with known GDS reservation number$")
    public void setupHotelReservationNumber() {
        model.setupHotelReservationNumber();
    }

    @Given("^the customer with known air ticket number$")
    public void setupAirTicketNumber() {
        model.setupAirTicketNumber();
    }

    @Given("^the customer with known car reservation number$")
    public void setupCarReservationNumber() {
        model.setupCarReservationNumber();
    }

    @Given("^customer for (adding to|removing from) Black list$")
    public void setupCustomerForBlackList(String addRemove) {
        customerInfo.setEmail("admin_blacklistedaccnt" + System.currentTimeMillis() + "@test.com");
    }

    @Given("^customer email with wrong format$")
    public void setupCustomerEmailWrongFormat() {
        customerInfo.setEmail("xxxx@xxxx");
    }

    @Given("^purchase case note ID$")
    public void setupCaseNoteID() {
        model.setupCaseNoteID();
    }

    @Given("^purchase case note itinerary$")
    public void setupCaseNoteItinerary() {
        model.setupCaseNoteItinerary();
    }

    @Given("^I have valid IP Address$")
    public void setupIPAddressCustomer() {
        model.setupIPAddressCustomer();
    }

    @Given("^I have valid IP Address with few purchase$")
    public void setupIPAddressCustomerWithFewPurchase() {
        model.setupIPAddressCustomerWithFewPurchase();
    }

    @Given("^I have valid OrderID$")
    public void setupExistingOrderID() {
        model.setupExistingOrderID();
    }

    @Given("^I have display number with valid credit card$")
    public void setupDisplayNumberWithCreditCard() {
        model.setupDisplayNumberWithCreditCard();
    }

    @Given("^I have itinerary with valid IP Address$")
    public void setupItineraryWithIPAddressCustomer() {
        model.setupItineraryWithIPAddressCustomer();
    }

    @Given("^customer email with case notes$")
    public void setupCaseNoteEmail() {
        model.setupCaseNoteEmail();
    }


    @Given("^(opaque|retail) hotel purchase with(out)? insurance$")
    public void setupHotelCustomerWithInsurance(String opacity, String withOutInsurance) {
        hotwireProduct.setProductVertical(ProductVertical.HOTEL);
        hotwireProduct.setOpacity(opacity);
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.GUEST);
        boolean insurance = StringUtils.isEmpty(withOutInsurance);
        billingInfo.setInsurance(insurance);
        model.setupHotelPurchaseWithParams();
    }

    @Given("^I receive immediate \"([^\"]*)\" error message$")
    public void verifyDisplayedMessage(String message) {
        model.verifyMessageIsDisplayed(message);
    }

    @Given("^I see \"([^\"]*)\" text on page$")
    public void verifyTextOnPage(String message) {
        model.verifyTextOnPage(message);
    }

    @Given("^I open travel advisory updates page$")
    public void openTravelAdvisoryUpdates() {
        model.openTravelAdvisoryUpdates();
    }

    @Given("^I go to Help Center$")
    public void i_go_to_Help_Center() {
        model.goToHelpCenter();
    }

    @Given ("^I go to (.*) site$")
    public void i_go_to_locale_site(String locale) {
        model.openLocale(locale);
    }

    @Given("^I go to Hotel Overcharges Admin$")
    public void i_go_to_Hotel_Overcharges_Admin() {
        model.goToHotelOvercharges();
    }

    @When("I click on \"(.*)\"$")
    public void clickOnLink(String linkText) {
        model.clickOnLink(linkText);
    }

    @When("I see \"(.*)\" email link$")
    public void verifyMailTo(String email) {
        model.verifyMailTo(email);
    }

    @Then("^I see status \"(.*)\" in Database (.*) table$")
    public void verifyStatusInDataBase(String status, String table) {
        model.verifyStatusInDataBase(status, table);
    }

    @Given("I open (air|car|hotel) landing page")
    public void openLandingPage(String verticalName) {
        hotwireProduct.setVertical(verticalName);
        model.openLandingPage();
    }

    @Then("I see deals on page")
    public void verifyDealHash() {
        model.verifyDealHash();
    }

    @Given("^referral parameter (.*) is \"(.*)\"$")
    public void setupReferralParameter(String param, String value) {
        if (!param.isEmpty()) {
            appMetaData.setReferralParams(param, value);
        }
    }

    @Given("^I open referral link$")
    public void openReferralLink() throws MalformedURLException {
        model.openReferralLink();
    }

    @Then("^I see in search_referral table that (.*) is \"(.*)\"$")
    public void verifyReferralInDb(String param, String value) {
        model.verifyReferralInDb(param, value);
    }

    @Given("I go to car referral URL")
    public void goToCarReferralURL() {
        model.goToCarReferralURL();
    }

    @Then("there is (no)?\\s+?(pointroll|triggit) pixel on index page")
    public void verifyPixel(String negation, String pixelName) {
        model.verifyPixelPresence(StringUtils.isEmpty(negation), pixelName);
    }
    @Given("I following URL with blank confirmationNumber")
    public void gotoURLwithBlankConfirmationNumber() throws MalformedURLException {
        String referralSource = "test1.com";
        appMetaData.setReferralParams("REFERRAL_SOURCE", referralSource);
        model.setupURLwithBlankConfirmationNumber();
    }

    @Given("I following URL with blank searchReferenceNumber")
    public void gotoURLwithBlankSearchReferenceNumber() throws MalformedURLException {
        String referralSource = "test2.com";
        appMetaData.setReferralParams("REFERRAL_SOURCE", referralSource);
        model.setupURLwithBlankConfirmationNumber();
    }

    @Given("I following URL with both blank searchReferenceNumber and confirmationNumber")
    public void gotoURLwithBothBlankNumbers() throws MalformedURLException {
        String referralSource = "test3.com";
        appMetaData.setReferralParams("REFERRAL_SOURCE", referralSource);
        model.setupURLwithBlankConfirmationNumber();
    }

    @Given("I following URL with incorrect referral source")
    public void gotoURLwithIncorrectReferralSource() throws MalformedURLException {
        String referralSource = "12345678901";   // 11 characters
        appMetaData.setReferralParams("REFERRAL_SOURCE", referralSource);
        model.setupURLwithBlankConfirmationNumber();
        referralSource = "1234567890";          // 10 characters
        appMetaData.setReferralParams("REFERRAL_SOURCE", referralSource);
    }

    @Given("^I manage payment info on domestic site$")
    public void gotoPaymentInfoOnDomesticSite()  {
        model.gotoPaymentInfoOnDomesticSite();
    }

    @Given("^I verify expired hotdollars for customer on domestic site$")
    public void verifyExpiredHotDollarsOnDomesticSite()  {
        model.verifyExpiredHotDollarsOnDomesticSite();
    }

    @Given("^I verify no hotdollars are available on domestic site$")
    public void verifyNoHotDollarsAnailableOnDomesticSite()  {
        model.verifyNoHotDollarsAvailableOnDomesticSite();
    }

    @Given("^I verify remainder of hotdollars on domestic site$")
    public void verifyRemainderOfDollarsOnDomesticSite()  {
        model.verifyRemainderOfDollarsOnDomesticSite();
    }

    @Given("^I verify last hotdollars activity on domestic site$")
    public void verifyActivityOfDollarsOnDomesticSite()  {
        model.verifyHotDollarsActivityOnDomesticSite();
    }

    @When("^I switch to c3 frame$")
    public void switchToC3Frame() {
        model.switchToC3Frame();
    }

}

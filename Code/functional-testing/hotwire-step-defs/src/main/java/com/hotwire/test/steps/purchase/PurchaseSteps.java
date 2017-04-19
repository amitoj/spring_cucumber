/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase;

import static java.lang.String.format;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.authentication.AuthenticationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.purchase.air.AirPurchaseModel;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import com.hotwire.test.steps.purchase.hotel.HotelPurchaseModel;
import com.hotwire.test.steps.purchase.paypal.PayPalPurchaseSteps;
import com.hotwire.test.steps.search.SearchParameters;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * This class provides steps to select and buy a pgood.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public class PurchaseSteps extends AbstractSteps {
    private static Logger LOGGER = LoggerFactory.getLogger(PurchaseSteps.class.getSimpleName());

    @Autowired
    @Qualifier("purchaseModel")
    private PurchaseModel model;

    @Autowired
    @Qualifier("hotelPurchaseModel")
    private HotelPurchaseModel hotelPurchaseModel;

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel carPurchaseModel;

    @Autowired
    @Qualifier("airPurchaseModel")
    private AirPurchaseModel airPurchaseModel;

    @Autowired
    @Qualifier("authenticationModel")
    private AuthenticationModel authenticationModel;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("randomGuestUser")
    private UserInformation userInformation;

    @Autowired
    @Qualifier("authenticatedUser")
    private UserInformation authenticatedUser;

    @Autowired
    @Qualifier("blankBillingInformationUser")
    private UserInformation blankBillingInformationUser;

    @Autowired
    @Qualifier("specialCharactersBillingInformationUser")
    private UserInformation specialCharactersInformationUser;

    @Autowired
    @Qualifier("obsoleteBillingInformationUser")
    private UserInformation obsoleteBillingInformationUser;

    @Autowired
    @Qualifier("invalidPhoneNumberBillingInformationUser")
    private UserInformation invalidPhoneNumberBillingInformationUser;

    @Autowired
    @Qualifier("blankTravelerInformationUser")
    private UserInformation blankTravelerInformation;

    @Autowired
    @Qualifier("specialCharactersTravelerInformationUser")
    private UserInformation specialCharactersTravelerInformation;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    // master card
    @Autowired
    @Qualifier("masterCard4444")
    private UserInformation masterCard4444;

    // American Express card
    @Autowired
    @Qualifier("americanExp007")
    private UserInformation americanExp007;

    // Visa card
    @Autowired
    @Qualifier("visa1111")
    private UserInformation visa1111;

    // discover card
    @Autowired
    @Qualifier("discover9424")
    private UserInformation discover9424;

    // jcb card
    @Autowired
    @Qualifier("jcb1113")
    private UserInformation jcb1113;

 // maestro card
    @Autowired
    @Qualifier("maestro0162")
    private UserInformation maestro0162;

    // maestro card
    @Autowired
    @Qualifier("diners3143")
    private UserInformation diners3143;

    @Resource(name = "creditCards")
    private Map<String, UserInformation> creditCards;

    @Given("^I'm a guest user$")
    public void createGuestUser() {
        purchaseParameters.setUserInformation(userInformation);
        authenticationParameters.setCredentials(purchaseParameters.getUserInformation().getEmailId(),
                purchaseParameters.getUserInformation().getVmePassword());
    }

    @Given("^I'm a registered user$")
    public void createAuthenticatedUser() {
        purchaseParameters.setUserInformation(authenticatedUser);
        purchaseParameters.setOptForInsurance(false);
    }

    @When("^I am a (guest|registered) user trying to populate with" +
        " (blank|special characters|obsolete|numbers in name|numbers in address|" +
            "numbers in city|AAA in security) billing information$")
    public void attemptPopulateBillingInformation(String user, String bookingProfile) {
        boolean isRegisteredUser = "registered".equals(user);
        if ("blank".equals(bookingProfile)) {
            purchaseParameters.setUserInformation(blankBillingInformationUser);
        }
        else if ("special characters".equals(bookingProfile)) {
            purchaseParameters.setUserInformation(specialCharactersInformationUser);
        }
        else if ("obsolete".equals(bookingProfile)) {
            purchaseParameters.setUserInformation(obsoleteBillingInformationUser);
        }
        else if ("numbers in name".equals(bookingProfile)) {
            purchaseParameters.getUserInformation().setFirstName("132456");
            purchaseParameters.getUserInformation().setBillingInformationName("123456");
        }
        else if ("numbers in city".equals(bookingProfile)) {
            purchaseParameters.getUserInformation().setCity("123456");
        }
        else if ("numbers in address".equals(bookingProfile)) {
            purchaseParameters.getUserInformation().setBillingAddress("1");
        }
        else if ("AAA in security".equals(bookingProfile)) {
            purchaseParameters.getUserInformation().setSecurityCode("AAA");
        }
        else {
            throw new IllegalArgumentException();
        }
        model.attemptBillingInformationCompletion(isRegisteredUser);
    }

    @When("^I am a guest user trying to populate the traveler information page with" +
        " (blank|special characters|invalid phone number|blank country|digital name|too long name) " +
        "traveler information$")
    public void attemptPopulateTravelerInformation(String bookingProfile) {
        if ("blank".equals(bookingProfile)) {
            purchaseParameters.setUserInformation(blankTravelerInformation);
            model.fillTravelerInfo(blankTravelerInformation, false);
        }
        else if ("special characters".equals(bookingProfile)) {
            purchaseParameters.setUserInformation(specialCharactersTravelerInformation);
            model.fillTravelerInfo(specialCharactersTravelerInformation, false);
        }
        else if ("invalid phone number".equals(bookingProfile)) {
            purchaseParameters.setUserInformation(invalidPhoneNumberBillingInformationUser);
            model.fillTravelerInfo(invalidPhoneNumberBillingInformationUser, false);
        }
        else if ("blank country".equals(bookingProfile)) {
            userInformation.setPhoneCountryCode("");
            purchaseParameters.setUserInformation(userInformation);
            model.fillTravelerInfo(userInformation, false);
        }
        else if ("digital name".equals(bookingProfile)) {
            userInformation.setFirstName("12345");
            purchaseParameters.setUserInformation(userInformation);
            model.fillTravelerInfo(userInformation, false);
        }
        else if ("too long name".equals(bookingProfile)) {
            userInformation.setFirstName("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            purchaseParameters.setUserInformation(userInformation);
            model.fillTravelerInfo(userInformation, false);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @When("I continue on billing without filling billing information")
    public void continueOnBillingPageWoBillingInfo() {
        model.continueOnBillingPageOnPaymentFragment();
    }

    @When("^I fill in billing information and (save|don't save) billing information with name (.*)$")
    public void fillBillingInfo(String isSaveInfo, String cardName) {
        model.fillCreditCard();
        if ("save".equalsIgnoreCase(isSaveInfo)) {
            model.saveBillingInformation(cardName);
        }
    }

    @When("^I fill in credit card information$")
    public void fillCreditCard() {
        model.fillCreditCard();
    }

    @When("^I save billing information with name (.*)$")
    public void saveBillingInformation(String cardName) {
        model.saveBillingInformation(cardName);
    }

    @When("^I save billing information for guest user with name (.*) and password (.*)$")
    public void saveBillingInformation(String cardName, String password) {
        model.saveBillingInformation(cardName, password);
    }

    @When("^I am a registered user trying to populate with (blank|invalid|special characters) credentials$")
    public void attemptToSignIn(String credentialType) {
        AuthenticationParameters parameters = SerializationUtils.clone(authenticationParameters);
        if ("blank".equalsIgnoreCase(credentialType)) {
            parameters.setCredentials("", "");
        }
        else if ("invalid".equalsIgnoreCase(credentialType)) {
            parameters.setCredentials("badUserName@hotwire.com", "badPassword");
        }
        else if ("special characters".equalsIgnoreCase(credentialType)) {
            parameters.setCredentials("*a@hotwire.com", "badPassword");
        }
        else {
            throw new IllegalArgumentException(format("Illegal credentials type: %s", credentialType));
        }
        model.attemptToSignIn(parameters, true);
    }

    @Then("^I should see (blank|invalid|special characters) credential information errors$")
    public void validateCredentialInfoErrors(String credentialType) {
        model.validateCredentialInfoErrors(credentialType);
    }

    @Then("^I receive immediate confirmation$")
    public void receiveConfirmation() {
        model.receiveConfirmation();
    }

    @When("^I purchase as (.*) a quote$")
    public void completePurchase(String guestOrUser) {
        model.completePurchase("user".equals(guestOrUser));
    }

    @When("^I complete purchase with(out)? agreements$")        // Click on <Agree and book> button on billing page
    public void clickAgreeAndBookButton(String condition) {
        try {
            Thread.sleep(5000);
            model.clickOnAgreeAndBookButton(!"out".equals(condition));
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Given("^I (?:have|pay with) a valid (.*) credit card$")
    public void setCreditCardInfo(String creditCardType) {
        UserInformation information = creditCards.get(creditCardType);
        purchaseParameters.setUserInformation(information);
    }

    @When("^I continue from activities panel and again continue from insurance panel without selection$")
    public void continueFromActivitiesPanel() {
        model.continueFromActiviesAndInsurancePanels();
    }

    @Then("^I will see an error message$")
    public void continueWithoutInsurance() {
        model.validateInsuranceErrorMessage();
    }

    @When("^I proceed to (car|hotel) billing$")
    public void gotoVerticalBillingPage(String vertical) {
        if (StringUtils.isNotEmpty(vertical)) {
            searchParameters.setPGoodCode(PGoodCode.toPGoodType(vertical));
        }
        model.continueToBillingFromDetails();
    }

    @When("^I return to (car|hotel) details$")
    public void i_return_to_details_page(String vertical) {
        if (StringUtils.isNotEmpty(vertical)) {
            searchParameters.setPGoodCode(PGoodCode.toPGoodType(vertical));
        }
        model.returnToDetailsFromBilling();
    }

    @When("I see (car|hotel|air|sign in)(\\sresult|\\sdetails|\\sbilling)? page$")
    public void verifyPage(String vertical, String page) {
        switch (vertical) {
            case "hotel":
                hotelPurchaseModel.verifyPage(page);
                return;
            case "car":
                carPurchaseModel.verifyPage(page);
                return;
            case "air":
                airPurchaseModel.verifyPage(page);
                return;
            case "sign in":
                authenticationModel.verifySignInPage();
                return;
            default:
                return;
        }
    }

    @When("^I (choose|don't choose) insurance$")
    public void selectInsuranceAddonOption(String choice) {
        purchaseParameters.setOptForInsurance("choose".equals(choice.trim()));
        model.selectInsuranceAddonOption("choose".equals(choice.trim()));
    }

    @Then("^the purchase was failed due of$")
    public void purchaseFailedByReason(String reason) {
        model.verifyBookingFailureMessage(reason.replaceAll("\r?\n", " "));
    }

    @Given("Verify pointroll pixel on (.*) page")
    public void verifyPointrollPixelOnCertainPage(String pageName) {
        hotelPurchaseModel.verifyPointrollPixelOnCertainPage(pageName);
    }

    @Given("Verify triggit pixel on (.*) page")
    public void verifyTriggltPixelOnCertainPage(String pageName) {
        hotelPurchaseModel.verifyTriggItPixelOnCertainPage(pageName);
    }

    @Then("^(PayPal|HotDollars|CreditCard|SavedCreditCard|SavedVisa|SavedMasterCard|none)" +
          " payment is (available|disabled|unavailable)$")
    public void checkPaymentStatus(PurchaseParameters.PaymentMethodType methodType, String condition) {
        if (methodType.equals(PurchaseParameters.PaymentMethodType.PayPal) &&
                !PayPalPurchaseSteps.isPaypalSandboxAvailable()) {
            LOGGER.info("Paypal sandbox is unavailable on Thursday at 10pm through Friday. Skipping this step.");
            return;
        }
        setPaymentMethod(methodType);
        model.verifyPaymentStatus(condition);
    }

    @When("^I select (PayPal|savedVisa|hotDollars) payment option$")
    public void selectPaymentMethod(PurchaseParameters.PaymentMethodType methodType) {
        if (methodType == null) {
            throw new PendingException("No such payment option.");
        }
        if (methodType.equals(PurchaseParameters.PaymentMethodType.PayPal) &&
                !PayPalPurchaseSteps.isPaypalSandboxAvailable()) {
            throw new PendingException("Paypal sandbox is unavailable on Thursday at 11pm through Friday.");
        }
        setPaymentMethod(methodType);
        model.selectPaymentMethod();
    }

    public void setPaymentMethod(PurchaseParameters.PaymentMethodType methodType) {
        purchaseParameters.setPaymentMethodType(methodType);
    }

    @Then("^I should (be|not be) able to see one dollar auth in db$")
    public void verifyDollarOneAuth(String choice) {
        model.verifyDollarOneAuth();
    }

    // Master card
    @Given("^I'm a master card user$")
    public void createMasterGuestUser() {
        purchaseParameters.setUserInformation(masterCard4444);
    }

    // AmericanExp card
    @Given("^I'm a americanexp card user$")
    public void createAmericanExpGuestUser() {
        purchaseParameters.setUserInformation(americanExp007);
    }

    // visa card
    @Given("^I'm a visa card user$")
    public void createVisaGuestUser() {
        purchaseParameters.setUserInformation(visa1111);
    }

    // discover card
    @Given("^I'm a discover card user$")
    public void creatediscoverGuestUser() {
        purchaseParameters.setUserInformation(discover9424);
    }

    // jcb card
    @Given("^I'm a jcb card user$")
    public void createjcbGuestUser() {
        purchaseParameters.setUserInformation(jcb1113);
    }

 // jcb card
    @Given("^I'm a maestro card user$")
    public void createmaestroGuestUser() {
        purchaseParameters.setUserInformation(maestro0162);
    }

    @Given("^I'm a diners club card user$")
    public void createDinersClubGuestUser() {
        purchaseParameters.setUserInformation(diners3143);
    }

}



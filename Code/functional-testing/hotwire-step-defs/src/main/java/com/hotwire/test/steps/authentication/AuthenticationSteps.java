/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.authentication;

import com.hotwire.test.steps.search.SearchParameters;
import cucumber.api.java.en.And;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.text.ParseException;
import java.util.Date;

/**
 * Steps for user autorization
 */
public class AuthenticationSteps extends AbstractSteps {

    @Autowired
    @Qualifier("authenticationModel")
    private AuthenticationModel authenticationModel;

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel appModel;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("payableUser")
    private UserInformation payableUser;

    @Autowired
    private PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    @Given("^my name is (.*) and my password is (.*)$")
    public void setUserNameAndPassword(String username, String password) {
        authenticationParameters.setCredentials(username, password);
    }

    @Given("^user with (car|hotel|mixed) bookings and known departure date$")
    public void setUpUserWithBooking(String bookingType) throws ParseException {
//        authenticationParameters.setKnownGoodParameters();
        authenticationParameters.setCredentials("caps-non-express@hotwire.com", "hotwire");
        Date tripDepartureDate =
            authenticationModel.getTripDepartureDateFromDb(bookingType,
                                                           (authenticationParameters.getUsername()).toLowerCase());
        searchParameters.setStartDate(tripDepartureDate);
    }

    @Given("^I have valid(.*) credentials$")
    public void setValidUserNameAndPassword(String userType) {
        if (userType == null || StringUtils.isEmpty(userType.trim())) {
            authenticationParameters.setKnownGoodParameters();
        }
        else {
            authenticationParameters.setRandomUsernameAndPassword();
        }
    }

    @Given("^I(?:'m|\\sam) a valid user$")
    public void setValidUserNameAndPassword() {
        setValidUserNameAndPassword(null);
    }

    @Given("^I have an invalid username$")
    public void setInvalidUserNameAndPassword() {
        authenticationParameters.setKnownBadParameters();
    }

    @Given("^I have an existing username and an incorrect password$")
    public void setKnownGoodUsernameAndBadPassword() {
        authenticationParameters.setKnownGoodUsernameAndBadPassword();
    }

    @Given("^I have an invalid username and valid password$")
    public void setKnownBadUsernameAndGoodPassword() {
        authenticationParameters.setKnownBadUsernameAndGoodPassword();
    }

    @Given("^I have HotDollars$")
    public void setHotDollarsUser() {
        authenticationParameters.setHotDollarsUser();
    }

    @Given("^I'm a payable user$")
    public void setPayableUser() {
        authenticationParameters.setPayableUser();
        purchaseParameters.setUserInformation(payableUser);
    }

    @When("^I authenticate myself$")
    public void attemptToAuthenticateUser() {
        authenticationModel.attemptToAuthenticate(authenticationParameters);
    }

    @When("^I authenticate myself using Optional SignIn$")// Or a Given
    public void logInByOptionalSignIn() {
        authenticationModel.logInByOptionalSignIn();
    }

    @Given("^I am logged in$")
    public void loginWithValidCredentials() {
        authenticationParameters.setKnownGoodParameters();
        authenticationModel.attemptToAuthenticate(authenticationParameters);
    }

    @Then("^I am unable to log in$")
    public void verifyAuthenticationExceptionRaised() {
        authenticationModel.verifyAuthenticationExceptionRaised();
    }

    @Then("^a validation error exists on password$")
    public void verifyValidationErrorOnPassword() {
        authenticationModel.verifyValidationErrorOnPassword();
    }

    @Then("^a validation error exists on username$")
    public void verifyValidationErrorOnUsername() {
        authenticationModel.verifyValidationErrorOnUsername();
    }

    @Given("^I am logged in with (existing email|null|.*) and my credit card (.*)$")
    public void loginWithCreditcardNumber(String emailid, String creditcardnumber) {
        if (emailid.equals("existing email") || emailid.equals("null")) {
            emailid = authenticationParameters.getUsername();
        }
        authenticationModel.loginWithCreditcardNumber(emailid, creditcardnumber);
    }

    @Given("^I login with (.*) email and invalid credit card number to see error message$")
    public void loginWithInvalidCreditCardNumber(String email) {
        authenticationModel.loginWithInvalidCreditCardNumber(email);
    }

    @Given("^I log in 4 times to get error message$")
    public void loginInFourTimesToGetErrorMessage() {
        authenticationModel.loginInFourTimesToGetErrorMessage();
    }

    @Given("^I log in with existing email and (valid|invalid) trip departure date$")
    public void loginWithEmailAndTripDate(String dateValidity) {
        if ("invalid".equalsIgnoreCase(dateValidity)) {
            searchParameters.setStartDate(new Date("12/1/07"));
        }
        authenticationModel.loginWithDepartureDate(authenticationParameters.getUsername());
    }

    @Given("^Optional Sign in section is (available|unavailable)$")
    public void verifyValidationErrorOnUsername(String condition) {
        authenticationModel.optionalSignInStatus(condition);
    }

    @Given("^I open Optional SignIn popup$")
    public void openOptionalSignInPopUp() {
        authenticationModel.openOptionalSignInPopUp();
    }

    @Given("^I submit Optional SignIn form$")
    public void submitOptionalSignInForm() {
        authenticationModel.submitOptionalSignInForm();
    }

    @Given("^I write (.+) to Optional SignIn (email|password) field$")
    public void writeToOptionalSignInField(String fieldValue, String fieldType) {
        authenticationModel.writeToOptionalSignInField(fieldType, fieldValue);
    }

    @Given("^I click on Password Assistance$")
    public void goToPasswordAssistance() {
        authenticationModel.goToPasswordAssistance();
    }

    @Given("^I am on password assistance page$")
    public void verifyPasswordAssistancePage() {
        appModel.verifyPasswordAssistancePage();
    }

    @And("^I click on In a hurry link$")
    public void clickInAHurryLink() {
        authenticationModel.clickInAHurryLink();
    }


    @Given("^I am set username to (.*) in password assistance page$")
    public void setPasswordAssistanceEmail(String email) {
        if (email.equals("null")) {
            email = "";
        }
        authenticationModel.setPasswordAssistanceEmail(email);
    }

    @Given("^I am set password to (.*) and confirmation password to (.*) in password assistance page$")
    public void setPasswordAssistancePassword(String password, String confirmationPassword) {
        if (password.equals("null")) {
            password = "";
        }
        authenticationModel.setPasswordAssistancePassword(password, confirmationPassword);
    }

    @Given("^I am set blacklist username in password assistance page$")
    public void setPasswordAssistanceBlackListEmail() {
        String email = authenticationModel.getBlackListEmail();
        authenticationModel.setPasswordAssistanceEmail(email);
    }

    @Given("^I receive confirmation restore password message$")
    public void verifyPasswordAssistanceConfirmation() {
        authenticationModel.verifyPasswordAssistanceEmailConfirmation();
    }

    @Given("^Prepopulated email field should be (.*)$")
    public void verifyPrepopulatedForgotEmail(String email) {
        authenticationModel.verifyPrepopulatedPA_Email(email);
    }

    @Given("^I go to SignIn page$")
    public void goToSignInPage() {
        authenticationModel.goToSignInPage();
    }

    @Given("^I have tried sign in (.*) times$")
    public void tryToSignIn(Integer iTimes) {
        authenticationModel.tryToSignIn(iTimes);
    }

    @Given("^I try to sign in$")
    public void tryToSignIn() {
        authenticationModel.attemptToSignIn();
    }

    @When("I try to login with invalid password three times in a row")
    public void loginWithInvalidPasswordThreeTimes() {
        authenticationParameters.setRandomPassword();
        authenticationModel.goToSignInPage();
        authenticationModel.tryToSignIn(4);

    }

    @Then("I see pre-filled email on password assistance page")
    public void verifyPrefilledEmailOnPasswordAssistancePage() {
        authenticationModel.verifyPasswordAssistanceEmailPrefilled();
    }

    // Login steps - For testing different credit cards
    // Account used for signing in and making purchase with new card every time and no saved credit cards
    @Given("^I am signed in$")
    public void signInWithValidCredentials() {
        authenticationParameters.setloginParameters();
        authenticationModel.attemptToAuthenticate(authenticationParameters);
    }

    @Then("^Confirm the sign in error handling: \"([^\"]*)\"$")
    public void confirmSignInErrorHandling(String errorMsg) throws Throwable {
        authenticationModel.confirmErrorHandling(errorMsg);
    }
}

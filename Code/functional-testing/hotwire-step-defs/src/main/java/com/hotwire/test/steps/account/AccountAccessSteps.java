/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account;

import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.SearchParameters;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Steps for account access
 */
public class AccountAccessSteps extends AbstractSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountAccessSteps.class.getSimpleName());

    @Autowired
    @Qualifier("applicationModel")
    ApplicationModel applicationModel;

    @Autowired
    @Qualifier("purchaseParameters")
    PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("accountAccess")
    private AccountAccessModel accountAccessModel;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("validCanadianUser")
    private UserInformation validCanadianUser;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("newValidAuthenticationParameters")
    private AuthenticationParameters newValidAuthenticationParameters;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Then("^I will see my HFC status$")
    public void verifyHfcStatusAfterLogin() {
        accountAccessModel.verifyHfcStatusAfterLogin();
    }

    @When("^I access my account information$")
    public void accessAccountInformation() {
        accountAccessModel.accessAccountInformation();
    }

    @When("^I verify that email on Account Overview is correct$")
    public void verifyEmailOnOverviewPage() {
        accountAccessModel.verifyEmailOnOverviewPage();
    }

    @When("^I go to register and set (.*)$")
    public void goToRegistrationPage(String field) {
        accountAccessModel.setFieldOnRegisterForm(field);
    }

    @And("^I verify registration page URL$")
    public void verifyRegistrationPageURL() {
        accountAccessModel.verifyRegistrationPageURL();
    }

    @Then("^I see newsletter subscription checkbox is (checked|unchecked)$")
    public void verifyNewsletterSubscriptionCheckboxState(String state) {
        accountAccessModel.verifySubscriptionCheckboxState(state);
    }

    @And("^subscription proposal is (.*)$")
    public void verifySubscriptionProposal(String proposal) {
        accountAccessModel.verifySubscriptionProposal(proposal);
    }

    @Then("^I (have|do not have) an option to change my subscription state$")
    public void verifySubscriptionOptionPresence(String optionPresence) {
        accountAccessModel.verifySubscriptionOptionPresence(optionPresence);
    }

    @Then("^my account information is available$")
    public void verifyAccountInformation() {
        accountAccessModel.verifyAccountInformationIsAvailable();
    }

    @Then("^the new user is created$")
    public void theNewUserIsCreated() {
        accountAccessModel.verifyNewUserIsCreated();
    }

    @Then("^I am(?:(?:\\s)(not))? authenticated$")
    public void verifyUserExistsAndIsAuthenticated(String nullOrNot) {
        boolean expectedState = "not".equalsIgnoreCase(nullOrNot) ? false : true;
        boolean actualState = accountAccessModel.userExistsAndIsAuthenticated();
        assertThat(actualState)
            .as("Expected user authentication to be " + expectedState + " but was " + actualState)
            .isEqualTo(expectedState);
    }

    @Given("^I am a new user$")
    public void createNewRandomUser() {
        String username = newValidAuthenticationParameters.getUsername();
        String password = newValidAuthenticationParameters.getPassword();
        LOGGER.info("Setting new random username and password to \"" + username + ":" + password + "\"");
        authenticationParameters.setCredentials(username, password);
    }

    @Given("^I am canadian user$")
    public void setCanadianUser() {
        //String username = newValidAuthenticationParameters.getUsername();
        //String password = newValidAuthenticationParameters.getPassword();
        //LOGGER.info("Setting new random username and password to \"" + username + ":" + password + "\"");
        //authenticationParameters.setCredentials(username, password);
        purchaseParameters.setUserInformation(validCanadianUser);
        //authenticationParameters.setPayableUser();
    }

    @Given("^I would not subscribe to Hotwire deals$")
    public void doNotSubscribeToHotwireDealsWhenRegister() {
        accountAccessModel.doNotSubscribeToHotwireDealsWhenRegister();
    }

    @Given("^I create an account$")
    public void registerNewUserAndAuthenticate() {
        accountAccessModel.registerNewUserAndAuthenticate();
    }

    @Given("^I create an account and stay on this page$")
    public void registerNewUserAndStay() {
        accountAccessModel.registerNewUserAndStay();
    }

    @Given("^I'm authenticated (subscribed|unsubscribed) to newsletters user$")
    public void authenticateWithSubscription(String subscriptionState) {
        createNewRandomUser();
        if (subscriptionState.equalsIgnoreCase("unsubscribed")) {
            doNotSubscribeToHotwireDealsWhenRegister();
        }
        accountAccessModel.registerNewUserAndAuthenticate();
    }

    @Given("^I manage e-?mail subscriptions$")
    public void manageEmailSubscriptions() {
        accountAccessModel.manageEmailSubscriptions();
    }

    @Given("^I manage payment info$")
    public void managePaymentInfo() {
        accountAccessModel.managePaymentInfo();
    }

    @Given("^I manage my account info$")
    public void manageAccountInfo() {
        accountAccessModel.manageAccountInfo();
    }

    @Given("^I manage Traveler Names$")
    public void manageTraveleNames() {
        accountAccessModel.manageTraveleNames();
    }

    @Given("^I update first traveler's info")
    public void updateFirstTravelerInfo() {
        accountAccessModel.updateFirstTravelerInfo();
    }

    @Given("^I change my e-?mail address$")
    public void changeEmailAddress() {
        accountAccessModel.changeEmailAddress();
    }

    @Given("^I (un)?subscribe (?:to|from) (.*|all) e-?mail subscriptions?$")
    public void changeEmailSubscription(String subscribeString, String subscriptionName) {
        boolean subscribe = StringUtils.isEmpty(subscribeString);
        accountAccessModel.changeEmailSubscriptions(subscribe, subscriptionName);
    }

    @Given("^I access the subscription management link from an email$")
    public void changeEmailSubscriptionFromEmail() {
        //applicationModel.setupURL_ToVisit("account/emailSubscription.jsp?" +
          //      "cid=12d9b324251018b8c4d97c05ef62dd85&nid=n-test&vid=v-testvid&r=y");
        accountAccessModel.accessEmailSubscriptionsFromEmail();
    }

    @Then("^I receive confirmation my subscriptions were updated$")
    public void confirmSubscriptionsUpdated() {
        accountAccessModel.confirmSubscriptionsUpdated();
    }

    @Then("^I check subscription (on|off) options in DB$")
    public void confirmSubscriptionsDB(String available) {
        if (available.equals("on")) {
            accountAccessModel.confirmSubscriptionsFromDB(true);
        }
        else {
            accountAccessModel.confirmSubscriptionsFromDB(false);
        }
    }

    @Then("^I see I am subscribed to (.*)$")
    public void verifySubscription(String subscriptionName) {
        accountAccessModel.verifySubscription(subscriptionName);
    }

    @Then("^I receive confirmation my e-?mail address was updated$")
    public void confirmEmailUpdated() {
        accountAccessModel.confirmEmailUpdated();
    }

    @Given("^I change my password\\s?(.*invalid.*)?$")
    public void changePassword(String invalidString) {
        boolean isInvalid = StringUtils.contains(invalidString, "invalid");
        accountAccessModel.changePassword(isInvalid);
    }

    @Given("^I receive confirmation my password was updated$")
    public void confirmPasswordUpdate() {
        accountAccessModel.confirmPasswordUpdated();
    }

    @Given("^I receive confirmation the password I chose is invalid$")
    public void confirmPasswordInvalid() {
        accountAccessModel.confirmPasswordInvalid();
    }

    @Given("^I manage my account preferences$")
    public void manageAccountPreferences() {
        accountAccessModel.manageAccountPrefs();
    }

    @Given("^I add \"(.*)\" as a favorite airport")
    public void addFavoriteAirport(String airportName) {
        accountAccessModel.addFavoriteAirport(airportName);
    }

    @Given("^I remove my favorite airport$")
    public void removeFavoriteAirport() {
        accountAccessModel.removeFavoriteAirport();
    }

    @Given("^I receive confirmation my favorite airport was (removed|updated)$")
    public void confirmAirportUpdated(String removedOrUpdated) {
        boolean wasRemoved = StringUtils.contains(removedOrUpdated, "removed");
        accountAccessModel.confirmAirportUpdated(wasRemoved);
    }

    @When("^I attempt to navigate to ([^\"]*) page$")
    public void accessAccountInformation(String tabName) {
        accountAccessModel.accessAccountTab(tabName);
    }

    @When("^I see my recently booked trip$")
    public void confirmRecentlyBookedTrip() {
        String formattedDate = new SimpleDateFormat("MM/dd/yy").format(searchParameters.getStartDate());
        accountAccessModel.confirmRecentlyBookedTrip(formattedDate);
    }

    @When("^I see my recently booked trip from guest trip page$")
    public void confirmRecentlyBookedGuestTrip() {
        String formattedDate = DateUtils.formatDate(searchParameters.getStartDate(), "MM/dd/yy");
        accountAccessModel.confirmRecentlyBookedGuestTrip(formattedDate);
    }

    @When("^I am on home page$")
    public void navigateToHomePage() {
        accountAccessModel.navigateToHomePage();
    }

    @When("^I select add to my stay option for a booked trip$")
    public void selectAddToMyStay() {
        accountAccessModel.selectAddToMyStay();
    }

    @When("^I am taken to add hotel options page$")
    public void navigateToAddOptionsPage() {
        accountAccessModel.confirmAddOptionsPage();
    }

    @Then("^I should see cruise news should be (un)?subscribe$")
    public void verifyCruiseNewsSubscription(String subscribeString) {
        boolean subscribe = StringUtils.isEmpty(subscribeString);
        accountAccessModel.verifyCruiseNewsSubscription(subscribe);
    }

    @And("^I change the any of subscription by clicking save changes")
    public void changeDefaultSubscription() {
        accountAccessModel.changeDefaultSubscription();
    }

    @And("^the user information was stored correctly$")
    public void verifyUserInformationInDb() {
        String sql = "select first_name, last_name, is_active, lang_code, wants_news_letter, register_country_code," +
                " placement_code, site_id from customer where email = ?";
        Map mapSqlUserInfo = jdbcTemplate.queryForMap(sql, new Object[]{authenticationParameters.getUsername()});
        Map mapUserInfo = new LinkedCaseInsensitiveMap<Object>();
        String countryCode = "US";

        if ("United Kingdom".equalsIgnoreCase(purchaseParameters.getUserInformation().getCountry())) {
            countryCode = "GB";
        }

        mapUserInfo.put("FIRST_NAME", purchaseParameters.getUserInformation().getFirstName());
        mapUserInfo.put("LAST_NAME", purchaseParameters.getUserInformation().getLastName());
        mapUserInfo.put("IS_ACTIVE", "Y");
        mapUserInfo.put("LANG_CODE", "eng");
        mapUserInfo.put("WANTS_NEWS_LETTER", "Y");
        mapUserInfo.put("REGISTER_COUNTRY_CODE", countryCode);
        mapUserInfo.put("PLACEMENT_CODE", new BigDecimal(100061));
        mapUserInfo.put("SITE_ID", new BigDecimal(2));
        assertThat(mapSqlUserInfo).isEqualTo(mapUserInfo);
    }

    @Given("^I want to logout$")
    public void makeLogout() {
        accountAccessModel.logout();
    }

    @Given("^I navigate to sign in page$")
    public void navigateToSignIn() {
        accountAccessModel.navigateToSignInPage();
    }

    @Given("^I attempt login with (UPPER|LOWER) password$")
    public void enterSensitivePassword(String sens) {
        if (sens.equals("LOWER")) {
            accountAccessModel.enterLowerPassword();
        }
        if (sens.equals("UPPER")) {
            accountAccessModel.enterUpperPassword();
        }
    }

    @Given("^I am change my password with (UPPER|LOWER) confirmation password$")
    public void changeSensitivePassword(String sens) {
        if (sens.equals("LOWER")) {
            accountAccessModel.changeUserPassword("HOTWIRE333", "hotwire333");
        }
        if (sens.equals("UPPER")) {
            accountAccessModel.changeUserPassword("hotwire333", "HOTWIRE333");
        }
    }

    @Given("^I am change my password to '(.*)' and confirm it to '(.*)'$")
    public void changeUserPassword(String newPass, String confPass) {
        if (newPass.equals("null")) {
            newPass = "";
        }
        if (confPass.equals("null")) {
            confPass = "";
        }
        accountAccessModel.changeUserPassword(newPass, confPass);
    }

    @Then("^I change my email and verify the one in DB$")
    public  void changeEmailAndVerifyInDB() {
        accountAccessModel.changeEmailAndVerifyInDB();
    }

    @Given("^I access to 'Password Assistance' page$")
    public void navigateToPasswordAssistancePage() {
        accountAccessModel.loadPasswordAssistancePageFromUHP();
    }

    @When("^I navigate to My Trips page$")
    public void accessMyTripsPage() {
        accountAccessModel.navigateToMyTripsPage();
    }

    @Given("^I logout to cookied mode$")
    public void logoutToCookiedMode() {
        accountAccessModel.logoutToCookiedMode();
    }

    @Then("^I am in cookied mode$")
    public void verifyUserInCookiedMode() {
        accountAccessModel.verifyUserInCookiedMode();
    }

    @Then("^I get trip departure date validation error$")
    public void verifyTripDepartureValidationError() {
        accountAccessModel.verifyTripDepartureValidationError();
    }

    @When("^I attempt register new user with blank fields$")
    public void enterBlankRegistrationInfo() {
        accountAccessModel.enterBlankRegistrationInfo();
    }

    @When("^I attempt sign in with blank fields$")
    public void enterBlankSignInInfo() {
        accountAccessModel.enterBlankSignInInfo();
    }

    @Then("^I am return to the UHP on International site from login page$")
    public void verifyUkUhpPage() {
        accountAccessModel.verifyUkUhpPage();
    }

    @Then("^I verify logged user has(\\sno)? saved credit card on my account page$")
    public void verifySavedCreditCardOnMyAccountPage(String condition) {
        accountAccessModel.verifySavedCreditCardOnMyAccountPage(condition);
    }
}

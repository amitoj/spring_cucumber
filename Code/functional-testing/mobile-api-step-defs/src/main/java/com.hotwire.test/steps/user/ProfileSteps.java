/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.user;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.List;
import java.util.Random;

/**
 * User: v-dsobko
 * Since: 01/20/15
 */
public class ProfileSteps extends AbstractSteps {

    @Autowired
    @Qualifier("abstractUser")
    UserInformation userInfo;
    @Autowired
    @Qualifier("updatedCustomer")
    UserInformation updatedCustomer;

    @Autowired
    RequestProperties requestProperties;

    @Autowired
    private ProfileService profileService;

    @When("^I retrieve profile with account data$")
    public void retrieveProfileWithAccountData() {
        profileService.retrieveProfileWithAccountData();
    }

    @Then("^retrieve profile response is present$")
    public void retrieveProfileExist() {
        profileService.retrieveProfileExist();
    }

    @And("^account data is correct$")
    public void verifyAccountData() {
        profileService.verifyAccountData();
    }

    @Then("^I get (password mismatch|" +
          "wrong password|account exists|wrong email) error when try to execute registration request$")
    public void verifyErrorOnRegistration(String errorType) {
        String errorMessage = "";
        String errorCode = "1006";
        if ("password mismatch".equalsIgnoreCase(errorType)) {
            errorMessage = "Password and password confirmation do not match each other";
        }
        else if ("wrong password".equalsIgnoreCase(errorType)) {
            errorMessage = "Password must not contain spaces and must have 6-30 characters";
        }
        else if ("wrong email".equalsIgnoreCase(errorType)) {
            errorMessage = "Email has wrong format";
        }
        else if ("account exists".equalsIgnoreCase(errorType)) {
            errorMessage = "Uh oh. We could not create an account. " +
                    "That email address already belongs to an existing account.";
            errorCode = "1020";
        }
        profileService.verifyErrorOnRegistration(errorCode, errorMessage);
    }

    @Given("^I want to register new customer:$")
    public void initiateRegisteringNewCustomer(List<String> dataTable) {
        userInfo.setFirstName(dataTable.get(0));
        userInfo.setLastName(dataTable.get(1));
        userInfo.setPrimaryPhoneNumber(dataTable.get(2));

        String emailField = dataTable.get(3);
        if (emailField.contains("{")) {
            String randomizedEmail = getRandomizedEmail(emailField);
            userInfo.setEmailId(randomizedEmail);
        }
        else {
            userInfo.setEmailId(emailField);
        }
        userInfo.setPassword(dataTable.get(4));
        userInfo.setPasswordConfirmation(dataTable.get(5));
    }

    private String getRandomizedEmail(String emailField) {
        String[] strings = emailField.split("\\{\\D+\\}");
        Random rand = new Random();
        int randomNum = rand.nextInt((100000 - 1) + 1) + 1;
        return strings[0] + randomNum + strings[1];
    }

    @Given("^I want to register new non-US customer$")
    public void setUpNonUsCustomerForCreateRequest() {
        userInfo.setCountry("DE");
        userInfo.setEmailId(getRandomizedEmail("apitestuser+{rand}@hotwire.com"));
        userInfo.setPassword("hotwire");
        userInfo.setPasswordConfirmation("hotwire");
        userInfo.setPrimaryPhoneNumber("5555555");
    }

    @Given("^I want to register customer with different set of subscriptions$")
    public void setUpCustomerWithDifferentSubscriptions() {
        userInfo.setIsHotAlert(true);
        userInfo.setIsSavingsNotices(false);
        userInfo.setIsBigDeals(true);
        userInfo.setIsTripWatcher(false);
        userInfo.setEmailId(getRandomizedEmail("apitestuser+{rand}@hotwire.com"));
        userInfo.setPassword("hotwire");
        userInfo.setPasswordConfirmation("hotwire");
        userInfo.setPrimaryPhoneNumber("5555555");
    }

    @When("^I execute registration request$")
    public void executeRegistrationRequest() {
        profileService.executeRegistrationRequest();
    }

    @Then("^I should see customer is registered$")
    public void verifyCustomerIsRegistered() {
        profileService.verifyCreateCustomerResponse();
    }

    @When("^I execute retrieve profile request$")
    public void executeRetrieveProfileRequest() {
        profileService.retrieveProfileWithAccountData();
    }

    @When("^I execute retrieve profile request for payment data$")
    public void executeRetrieveProfileRequestForPayementData() {
        profileService.retrieveProfileWithPaymentData();
    }

    @When("^I execute retrieve profile request for favorite airport")
    public void executeRetrieveProfileRequestForFavoriteAirport() {
        profileService.retrieveProfileFavoriteAirport();
    }

    @When("^I execute retrieve profile request for traveler info")
    public void executeRetrieveProfileRequestForTravelerInfo() {
        profileService.retrieveProfileTravelerInfo();
    }

    @When("^I see new card present in customer profile$")
    public void executeRetrieveProfileRequestForNewCard() {
        profileService.retrieveProfileWithPaymentData();
    }

    @When("^I update customer email subscriptions to (all true|all false|random)$")
    public void updateEmailSubscriptions(String subscriptionsState) {
        profileService.createUpdateEmailSubscriptionsRequest(subscriptionsState);
        profileService.updateCustomerProfile();
    }

    @Then("^email subscriptions data is correct$")
    public void verifyEmailSubscriptions() {
        profileService.verifyEmailSubscriptionsData();
    }

    @When("^I update customer general info$")
    public void updateCustomerGeneralInfo() {
        requestProperties.setUserInformation(updatedCustomer);
        profileService.createUpdateCustomerGeneralDataRequest();
        profileService.updateCustomerProfile();
    }

    @Then("^customers general info is correct$")
    public void verifyCustomerGeneralInfo() {
        profileService.verifyCustomerGeneralInfo();
    }

    @When("^I update customers card billing info$")
    public void updateCustomerPaymentData() throws DatatypeConfigurationException {
        String cardToUpdate = requestProperties.getUserInformation().getSavedCardName();
        requestProperties.setUserInformation(updatedCustomer);
        profileService.createUpdateCustomerPaymentDataRequest(cardToUpdate, false);
        profileService.updateCustomerProfile();
    }

    @When("^I update customers favorite airport$")
    public void updateCustomersFavoriteAirport() {
        profileService.createUpdateCustomerFavoriteAirportRequest(false);
        profileService.updateCustomerProfile();
    }

    @When("^I delete customers favorite airport$")
    public void deleteCustomersFavoriteAirport() {
        profileService.addFavoriteAirportIfCustomerDoesNotHaveIt();
        profileService.createUpdateCustomerFavoriteAirportRequest(true);
        profileService.updateCustomerProfile();
    }

    @When("^I update customers traveler info")
    public void updateCustomersTravelerInfo() {
        requestProperties.setUserInformation(updatedCustomer);
        profileService.createUpdateCustomerTravelerInfoRequest();
        profileService.updateCustomerProfile();
    }

    @Then("^customers payment data is correct$")
    public void verifyCustomerPaymentData() {
        profileService.verifyCustomerPaymentData();
    }

    @Then("^customers favorite airport data is correct$")
    public void verifyCustomerFavoriteAirport() {
        profileService.verifyCustomerFavoriteAirport();
    }

    @Then("^customers primary traveler info is correct$")
    public void verifyCustomersTravelerInfo() {
        profileService.verifyCustomersTravelerInfo();
    }

    @Then("^customer does not have favorite airport$")
    public void verifyCustomerDoesNotHaveFavoriteAirport() {
        profileService.verifyCustomerDoesNotHaveFavoriteAirport();
    }

}

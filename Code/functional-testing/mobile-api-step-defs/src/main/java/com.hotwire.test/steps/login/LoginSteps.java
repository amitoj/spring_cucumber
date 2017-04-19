/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.login;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-ngolodiuk
 * Since: 1/8/15
 */
public class LoginSteps extends AbstractSteps {

    @Autowired
    LoginService loginService;

    @Autowired
    @Qualifier("abstractUser")
    UserInformation userInfo;

    @Autowired
    @Qualifier("hotDollarsUser")
    UserInformation hotDollarsUser;

    @Autowired
    @Qualifier("randomUser")
    UserInformation randomUser;

    @Autowired
    @Qualifier("randomUserWithCancelledTrips")
    UserInformation randomUserWithCancelledTrips;

    @Autowired
    RequestProperties requestProperties;

    @And("^I am logged in as (payable|hot dollars) user$")
    public void logInAsPayableUser(String user) throws Exception {
        if ("payable".equalsIgnoreCase(user)) {
            loginService.loginUser(userInfo);
        }
        else {
            loginService.loginUser(hotDollarsUser);
        }
    }

    @And("^I am logged in as random user with (booked|cancelled) trips$")
    public void logInAsRandomUser(String trips) throws Exception {
        if (trips.equalsIgnoreCase("booked")) {
            loginService.loginUser(randomUser);
        }
        else {
            loginService.loginUser(randomUserWithCancelledTrips);
        }
    }

    @Then("^I have unique oauth token for this customer$")
    public void verifyOauthTokenForCustomer() {
        loginService.verifyOauthTokenForCustomer();
    }

    @When("^I try to login with correct email and incorrect password$")
    public void tryToLoginWithCorrectEmailAndIncorrectCustomer() {
        loginService.tryToLoginWithCorrectEmailAndIncorrectCustomer();
    }

    @When("^I try to login with incorrect email and incorrect password$")
    public void tryToLoginWithIncorrectEmailAndIncorrectCustomer() {
        loginService.tryToLoginWithIncorrectEmailAndIncorrectCustomer();
    }

    @When("^login response is not present$")
    public void verifyLoginResponseIsNotPresent() {
        loginService.verifyLoginResponseIsNotPresent();
    }

}

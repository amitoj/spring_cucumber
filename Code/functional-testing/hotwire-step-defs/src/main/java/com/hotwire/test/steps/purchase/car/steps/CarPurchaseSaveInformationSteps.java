/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car.steps;

 import com.hotwire.test.steps.authentication.AuthenticationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import cucumber.api.java.en.Given;
 import cucumber.api.java.en.Then;
 import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-jneiman
 * Date: 8/22/13
 * Time: 12:27 AM
 */
public class CarPurchaseSaveInformationSteps extends AbstractSteps {

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel model;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("authenticationModel")
    private AuthenticationModel authenticationModel;

    @When("^I[\\s]*(?:(don\\'t))? save my information$")
    public void doSaveUsersInformation(String state) {
        model.doSaveUsersInformation(state);
    }

    @When("^I[\\s]*(?:(don\\'t))? save my payment information$")
    public void doSaveUsersPaymentInformation(String state) {
        if (!"don't".equals(state)) {
            model.doSaveUsersPaymentInformation(state);
        }
    }

    @Then("I enter name for credit card")
    public void enterNameCreditCard() {
        model.enterNameCreditCard();
    }

    @Given("^I am logged in after saving my information$")
    public void loginWithValidCredentials() {
        authenticationModel.attemptToAuthenticate(authenticationParameters);
    }

    @Given("^traveler information is populated$")
    public void verifyThatTravelerInformationIsPopulated() {
        model.verifyThatTravelerInformationIsPopulated();
    }

    @Given("^driver name on the confirmation page is (new|existing)$")
    public void check_driver_name_on_confirmation_page(String driverNameState) {
        model.checkDriverNameOnConfirmationPage(driverNameState);
    }

    @Given("^I choose driver name from existing drivers$")
    public void choose_driver_from_existing() {
        model.chooseDriverFromExisting();
    }

    @When("^I change my primary driver to \"([^\"]*)\"$")
    public void changePrimaryDriverName(String name) {
        String[] fullName = name.split("\\s");
        model.setNewPrimaryDriverName(fullName[0], fullName[1]);
    }
}

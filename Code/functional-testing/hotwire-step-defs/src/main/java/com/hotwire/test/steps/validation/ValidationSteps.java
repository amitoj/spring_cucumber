/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.validation;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-ngolodiuk
 * Since: 8/20/14
 */
public class ValidationSteps extends AbstractSteps {

    @Autowired
    @Qualifier("validationModel")
    private ValidationModel model;


    @Then("^I test Primary Driver field with valid and invalid values$")
    public void testPrimaryDriverFiled() {
        model.primaryDriverFieldTesting();
    }

    @Then("^I verify age confirmation error for primary driver$")
    public void primaryDriverAgeValidation() {
        model.primaryDriverAgeValidation();
    }

    @Then("^I test primary phone field with valid and invalid values$")
    public void primaryPhoneNumberTesting() {
        model.primaryPhoneNumberTesting();
    }

    @Then("^I test email address field with valid and invalid values$")
    public void emailAddressFieldTesting() {
        model.emailAddressFieldTesting();
    }
}

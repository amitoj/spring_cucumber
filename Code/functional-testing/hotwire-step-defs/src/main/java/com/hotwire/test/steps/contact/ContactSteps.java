/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.contact;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Contact steps
 */
public class ContactSteps extends AbstractSteps {

    @Autowired
    @Qualifier("contactModel")
    private ContactModel contactModel;

    @Then("^I should reach (us|uk|ie|no|au|nz|dk|se|de|hk|mx|sg) customer service phone number$")
    public void verifyPhoneNumber(String locale) throws Exception {
        contactModel.verifyPhoneNumber(locale);
    }

    @Then("^I verify customer service phone for the outside US/Canada users$")
    public void verifyPhoneNumberForOutsideUSUsers()  {
        contactModel.verifyPhoneNumberForOutsideUSUsers();
    }

}

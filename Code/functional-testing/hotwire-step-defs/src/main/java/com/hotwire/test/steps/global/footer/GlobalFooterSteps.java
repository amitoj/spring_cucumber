/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.global.footer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
/**
 * Global Footer Steps
 */
public class GlobalFooterSteps extends AbstractSteps {

    @Autowired
    @Qualifier("globalfooterModel")
    private GlobalFooterModel model;

    // Contact us steps
    @When("^I access Contact us$")
    public void access_Contact_us() throws Throwable {
        model.openContactUsPage();
    }

    @Then("^I see send email text$")
    public void verify_Send_Email() throws Throwable {
        model.verifySendEmailText();
    }

    // Site map steps
    @Then("^I access SiteMap$")
    public void access_SiteMap() throws Throwable {
        model.openSiteMapPage();
    }

    @Then("^I verify hotwire products$")
    public void verify_hotwire_products() throws Throwable {
        model.verifyHotwireProducts();
    }

    @Then("^I verify my account links$")
    public void verify_my_account_links() throws Throwable {
        model.verifyMyAccount();
    }

    //copyright
    @Then("^I verify hotwire legal disclaimer$")
    public void verify_hotwire_legal_declaimer() {
        model.verify_hotwire_legal_declaimer();
    }

    @Then("^I verify hotwire copyright info$")
    public void verify_hotwire_copyright_info() throws Throwable {
        model.verify_hotwire_copyright_info();
    }

}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.about.us;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * About us steps.
 */
public class AboutUsSteps extends AbstractSteps {

    @Autowired
    @Qualifier("aboutUsModel")
    private AboutUsModel model;

    @When("^I access About us$")
    public void access_About_us() throws Throwable {
        model.openAboutUsPage();
    }

    @Then("^I verify About us Texts$")
    public void verify_About_us_texts() throws Throwable {
        model.verifyTexts();
    }

    @Then("^I verify Company Links$")
    public void verify_Company_Links() throws Throwable {
        model.verifyCompanyLinks();
    }

    @Then("^I verify Legal Links$")
    public void verify_Legal_Links() throws Throwable {
        model.verifyLegalLinks();
    }

    @Then("^I verify Hotel partner Overview Link$")
    public void verify_Hotel_Partner_Link() throws Throwable {
        model.verifyHotelPartnerOverviewLink();
    }

    @Then("^I verify Press Releases Link$")
    public void verify_Press_Room_Link() throws Throwable {
        model.verifyPressReleaseLink();
    }

    @Then("^I verify Affiliates Link$")
    public void verify_Affiliates_Link() throws Throwable {
        model.verifyAffiliatesLink();
    }

}

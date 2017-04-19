/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.escapeToEurope.rome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 *
 * @author jhernandez
 *
 */
public class RomeSteps extends AbstractSteps {
    @Autowired
    @Qualifier("romeModel")
    private RomeModel model;

    @When("^I access the Global Footer Page$")
    public void launchGlobalFooter() {
        model.openGlobalFooter();
    }

    @When("^I click on the SiteMap Link$")
    public void navigateToSiteMapPage() {
        model.openSiteMapPage();
    }

    @When("^I click on the Rome Link$")
    public void navigateToRomePage() {
        model.openRomePage();
    }

    @Then("^Validate that \"([^\"]*)\" text is present$")
    public void validate_that_text_is_present(String arg1) throws Throwable {
        model.validateText(arg1);
    }

}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.accessibility;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Accessibility steps.
 */
public class AccessibilitySteps extends AbstractSteps {

    @Autowired
    @Qualifier("accessibilityModel")
    private AccessibilityModel model;

    @When("^I choose an accessibility friendly hotel$")
    public void chooseHotelResultsAccessibilitySolution() {
        model.chooseHotelResultsAccessibilitySolution();
    }

    @When("^I select the accessibilty needs$")
    public void clickAccessibilityLink() {
        model.clickAccessibilityLink();
    }

    @Then("^I see accessibility options$")
    public void verifyAccessibilityOptions() {
        model.verifyBillingPageAccessibilityOptions();
    }
}

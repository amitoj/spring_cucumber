/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.planningtools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * @author vjong
 *
 */
public class PlanningToolsSteps extends AbstractSteps {
    @Autowired
    @Qualifier("planningToolsModel")
    private PlanningToolsModel planningToolsModel;

    @Given("^I go to the Hotwire Travel Savings Indicator page$")
    public void gotoHotwireTravelSavingsIndicator() {
        planningToolsModel.gotoHotwireTravelSavingsIndicator();
    }

    @Given("^I go to the Travel Value Index page$")
    public void gotoTravelValueIndexPage() {
        planningToolsModel.gotoTravelValueIndexPage();
    }

    @Given("^I go to the Go Local Search page$")
    public void gotoGoLocalSearchPage() {
        planningToolsModel.gotoGoLocalSearchPage();
    }

    @Then("^I will see the Hotwire Travel Savings Indicator page$")
    public void verifyHotwireTravelSavingsIndicatorPage() {
        planningToolsModel.verifyHotwireTravelSavingsIndicatorPage();
    }

    @Then("^I will see the Travel Value Index page$")
    public void verifyTravelValueIndexPage() {
        planningToolsModel.verifyTravelValueIndexPage();
    }
}

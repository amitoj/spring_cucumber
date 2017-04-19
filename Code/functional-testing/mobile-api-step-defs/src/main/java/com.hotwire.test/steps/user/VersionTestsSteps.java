/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.user;

import com.hotwire.test.steps.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * User: v-dsobko
 * Since: 01/21/15
 */

public class VersionTestsSteps extends AbstractSteps {

    @Autowired
    private VersionTestsService versionTestsService;


    @When("^I execute the version tests request with scenarios \"([^\"]*)\"$")
    public void executeTheRequest(String vtsList) {
        List<String> versionTests = Arrays.asList(vtsList.split("\\,\\s"));
        versionTestsService.executeVersionTestRequest(versionTests);
    }

    @Then("^I get version scenario values applied$")
    public void verifyVersionScenarioValues() {
        versionTestsService.verifyVersionScenarioValues();
    }

    @Then("^I should get (one and the same|different|fgdfgdfg) scenario value when requesting it several times$")
    public void verifyScenarioVersionsWhenRequestingMultipleTimes(String expectedVersion) {
        boolean isOneAndTheSameVersion = ("one and the same".equalsIgnoreCase(expectedVersion)) ? true : false;
        versionTestsService.verifyScenarioVersionsWhenRequestingMultipleTimes(isOneAndTheSameVersion);
    }

    @Then("^I get (\\d+) as version scenario value$")
    public void verifyReceivingSpecificVTValue(int expectedValue)  {
        versionTestsService.verifyReceivingSpecificVTValue(Integer.toString(expectedValue));
    }

}

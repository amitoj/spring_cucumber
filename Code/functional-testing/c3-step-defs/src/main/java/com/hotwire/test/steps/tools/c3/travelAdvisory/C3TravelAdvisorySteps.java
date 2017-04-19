/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.travelAdvisory;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.TravelAdvisoryInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Then;

/**
 * User: v-vzyryanov
 * Date: 9/20/13
 * Time: 5:36 AM
 */
public class C3TravelAdvisorySteps extends ToolsAbstractSteps {

    @Autowired
    private C3TravelAdvisoryModel model;

    @Autowired
    private TravelAdvisoryInfo travelAdvisoryIssue;


    /**
     * Page: Home > Travel Advisory Updates
     * Removing advisory alert by index
     */
    @Then("^I remove (first|second) travel advisory issue$")
    public void removeAdvisoryIssue(String number) {
        travelAdvisoryIssue.setTravelAdvisoryIssue(number);
        model.removeIssue();
    }

    @Then("^I publish (first|second) travel advisory$")
    public void publishAdvisoryIssue(String number)  {
        travelAdvisoryIssue.setTravelAdvisoryIssue(number);
        model.publishIssue();
    }

    @Then("^I fill (first|second) travel advisory$")
    public void fillAdvisoryIssue(String number)  {
        travelAdvisoryIssue.setTravelAdvisoryIssue(number);
        model.fillTravelAdvisory();
    }

    @Then("^I clean up all travel advisory fields$")
    public void cleanUpTravelAdvisory()  {
        travelAdvisoryIssue.setTravelAdvisoryIssue("empty");
        model.fillTravelAdvisory();
    }

    @Then("^I write incorrect date$")
    public void writeIncorectFormatDate()  {
        travelAdvisoryIssue.setTravelAdvisoryIssue("wrong");
        model.fillTravelAdvisory();
    }

    @Then("^Travel Advisory module is (not )?displayed$")
    public void verifyModuleNotDisplayed(String negation) {
        model.verifyModuleNotDisplayed(StringUtils.isEmpty(negation));
    }


    @Then("^I (don't )?see (first|second) travel advisory title$")
    public void verifyTravelAdvisoryOnSite(String negation, String advisory) {
        travelAdvisoryIssue.setTravelAdvisoryIssue(advisory);
        model.verifyTravelAdvisoryOnSite(StringUtils.isEmpty(negation));
    }

    @Then("^I see the message that all fields are blank$")
    public void verifyBlankFieldsErrorMessage() {
        model.verifyBlankFieldsErrorMessage();
    }

    @Then("^I see the message that date is incorrect$")
    public void verifyIncorrectDate() {
        model.verifyIncorrectDate();
    }

    @Then("^I see the message that advisory is applied$")
    public void verifyAlertAppliedMsg() {
        model.verifyAlertAppliedMsg();
    }


}


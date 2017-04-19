/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.job;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author abareli
 *
 */
public class JobSteps extends AbstractSteps {

    @Autowired
    @Qualifier("jobModel")
    private JobModel jobModel;

    @Given("^I run the job \"([^\"]*)\" with output \"([^\"]*)\"$")
    public void runJob(List<String> command, String output) {
        System.out.println("I run the job****");
        jobModel.runJob(command, output);
    }

    @Given("^I check the job output$")
    public void checkJobOutput() {
        System.out.println("Check the job output****");
        jobModel.checkJobOutput();
    }

    @Given("^I wait for loading layer$")
    public void waitForLoadingLayer() {
        System.out.println("wait for loading layer****");
        jobModel.waitForLoadingLayer();
    }

    @Given("^I retrieve ref number from the opaque results page$")
    public void retrieveRefNum() {
        System.out.println("retrieve ref number from the opaque results page****");
        jobModel.retrieveReferenceNumber();
    }

    @Then("^I use reference to validate search time in DB$")
    public void validateTimeInDB() {
        System.out.println("use reference to validate search time in DB****");
        jobModel.validateTimeInDB();
    }

    @Given("^I set the following job params:$")
    public void setJobParameters(List<String> params) {
        jobModel.setJobParams(params);
    }

    @Given("^job logs will be stored in (.*)$")
    public void setJobDirectory(String directory) {
        jobModel.setJobDirectory(directory);
    }

    @When("^I run the job on (.*) port for (\\d+) seconds$")
    public void runJob(String port, Integer timeout) {
        jobModel.runJob(port, timeout);
    }

    @When("^I run the job on port (.*) and wait for success (.*)$")
    public void runJob(String port, String success) {
        assertThat(jobModel.runJob(port, success)).as("Job didn't run correctly").isTrue();
    }

    @Given("^I run the shell script (.*)$")
    public void runShellScript(String path) {
        jobModel.runShellScript(path);
    }

    @Given("^I want to refer to it as (.*)$")
    public void setReference(String refer) {
        jobModel.setReference(refer);
    }
}

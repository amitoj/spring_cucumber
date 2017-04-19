/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.workflow;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by v-ozelenov on 4/10/2014.
 */
public class C3WorkFlowSteps extends ToolsAbstractSteps {

    @Autowired
    private C3WorkFlowModel model;

    @Given("^I click on create workflow$")
    public void clickOnCreateWorkflow() {
        model.clickOnCreateWorkflow();
    }

    @Given("^I create LPG Workflow$")
    public void createLPGWorkFlow() {
        model.createLPGWorkFlow();
    }

    @Then("^I see message about successful LPG workflow creation$")
    public void verifyLPGWorkFlow() {
        model.verifyLPGWorkFlow();
    }

    @Then("^I see message that such workflow was already created for itinerary$")
    public void verifyLPGWorkFlowAlreadyCreated() {
        model.verifyLPGWorkFlowAlreadyCreated();
    }

    @Given("^I click on workflow link in most used resources$")
    public void clickOnWorkflowLink() {
        model.clickOnWorkflowLink();
    }

    @Given("^I could create workflow entry$")
    public void createAndCheckWorkflowEntry() {
        model.switchToNewWindow();
        model.createWorkflowEntry();
        model.checkWorkflowEntry();
    }

    @Given("^I create workflow entry$")
    public void createWorkflowEntry() {
        model.createWorkflowEntry();
        model.checkWorkflowEntry();
    }

    @Given("^I check workflow entry in DB$")
    public void checkWorkflowEntryInDB() {
        model.checkWorkflowEntryInDB();
    }
}

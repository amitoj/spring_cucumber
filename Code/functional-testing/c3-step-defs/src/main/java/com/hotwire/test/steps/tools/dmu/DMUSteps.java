/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.dmu;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Steps for DMU tool
 */
public class DMUSteps extends ToolsAbstractSteps {

    @Autowired
    private DMUModel dmuModel;

    @Given("^I login into DMU$")
    public void loginIntoDMU() {
        dmuModel.loginIntoDMU();
    }

    @Given("^I login into DMU with wrong credentials")
    public void loginIntoDMUWithFakeUser() {
        dmuModel.loginIntoDMUWithFakeUser();
    }

    @Given("^I submit DMU login form$")
    public void submitDMULogin() {
        dmuModel.submitDMULogin();
    }

    @Then("^I see DMU login error message$")
    public void verifyDMUErrorMsg() {
        dmuModel.verifyDMUErrorMsg();
    }

    @Then("^I see DMU login error message about wrong credentials$")
    public void verifyDMUWrongUserMSG() {
        dmuModel.verifyDMUWrongUserMSG();
    }

    @Given("^I see DMU index page$")
    public void verifyDMUIndexPage() {
        dmuModel.verifyDMUIndexPage();
    }

    @Given("^I write \"(.*)\" to DMU search box$")
    public void searchInDMU(String searchWord) {
        dmuModel.searchInDMU(searchWord);
    }

    @Given("^I see pop-up with matched procedures$")
    public void verifyProcedurePopUp() {
        dmuModel.verifyProcedurePopUp();
    }

    @Given("^I click on procedure$")
    public void chooseProcedureInPopUp() {
        dmuModel.chooseFirstProcedure();
    }

    @Given("^I see procedure page$")
    public void verifyProcedurePage() {
        dmuModel.verifyProcedurePage();
    }

    @Given("^I change partner status for vendor$")
    public void changePartnerVendor() {
        dmuModel.changePartnerVendor();
    }

    @Given("^I choose \"(.*)\" DMU menu$")
    public void chooseDmuMenu(String menuItem) {
        dmuModel.chooseDmuMenu(menuItem);
    }

    @Given("^I choose \"(.*)\" procedure from the list$")
    public void chooseProcedureFromList(String procedureName) {
        dmuModel.chooseProcedureFromList(procedureName);
    }

    @Given("^I run on non-prod environment$")
    public void runProcedureOnNonProd() {
        dmuModel.runProcedureOnNonProd();
    }

    @Given("^I see that procedure operations is correct$")
    public void verifyProcedureRunSuccessful() {
        dmuModel.verifyProcedureRunSuccessful();
    }

    @Given("^I commit on non-prod environment$")
    public void commitProcedure() {
        dmuModel.commitProcedure();
    }

    @Given("^I see that partner status is changed in database$")
    public void verifyProcedureInDb() {
        dmuModel.verifyProcedureInDb();
    }
}

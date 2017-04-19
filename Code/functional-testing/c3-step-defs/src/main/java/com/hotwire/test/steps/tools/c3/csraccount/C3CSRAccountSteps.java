/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.csraccount;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.c3.C3CsrAccount;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-abudyak
 * Date: 8/12/13
 * Time: 7:54 AM
 */
public class C3CSRAccountSteps extends ToolsAbstractSteps {

    @Autowired
    private C3CSRAccountModel model;

    @Autowired
    private C3CsrAccount c3CsrAccount;

    @Given("^I go to Edit CSR account page$")
    public void i_go_to_edit_CSR_account() {
        model.clickEditCsrAccount();
    }

    @Given("^I go to Risk Management admin page$")
    public void i_go_to_Risk_Management_Admin() {
        model.clickRiskManagementAdminLink();
    }

    @Given("^I go to Mail-In Rebate admin page$")
    public void i_go_to_Mail_In_Rebate_Admin() {
        model.clickMailInRebateAdminLink();
    }

    @Given("^I go to Hotel Credit card admin page$")
    public void i_go_to_Hotel_Credit_Card_Admin() {
        model.clickHotelCreditCardAdminLink();
    }

    @Given("^I go to Fraud Watch List admin page$")
    public void i_go_to_Fraud_Watch_List_Admin() {
        i_go_to_Risk_Management_Admin();
        model.clickFraudWatchListAdminLink();
    }

    @Given("^I go to Clear Old Transactions admin page$")
    public void i_go_to_Clear_Old_Transactions_Admin() {
        i_go_to_Risk_Management_Admin();
        model.clickClearOldTransactionsAdminLink();
    }

    @Given("^I go to (Air|Hotel|Car|Hotel International) transactions for review on admin page$")
    public void i_go_to_Transactions_For_Review_Admin(String vertical) {
        i_go_to_Risk_Management_Admin();
        model.selectTransactionsReviewVertical(vertical);
    }

    @When("^I create new CSR with \'(.*)\' user level$")
    public void createRandomCustomerWithUserLevel(String level) {
        model.createCSRWithUserLevel(level);
    }

    @When("^I create new CSR with \'(.*)\' email$")
    public void createRandomCSRCustomerWithEmail(String email) {
        model.createCSRWithEmail(email);
    }

    @When("^I click Reset Password on CSR account page$")
    public void resetPassword() {
        model.resetPassword();

    }

    @Given("^agent account$")
    public void setupCSRAccount() {
        model.setCSR();
    }

    @Given("^the agent account is (un)?locked$")
    public void verifyCSRLocked(String negation) {
        boolean status = !StringUtils.isEmpty(negation);
        model.verifyCSRLocked(status);
    }

    @When("^I search (.*) CSR account$")
    public void search_CSR_with(String status) {
        model.searchCsrAccountOnTab(status);
    }

    @When("^I make CSR (.*)$")
    public void i_make_CSR_inactive(String status) {
        model.changeStatusOfSelectedCsrAccount(status);
    }

    @Then("^I see CSR account in Active section$")
    public void i_see_CSR_in_status_active() {
        model.verifyCsrStatus("Active");
    }

    @Then("^I see CSR account in Inactive section$")
    public void i_see_CSR_in_status_inactive() {
        model.verifyCsrStatus("Inactive");
    }


    @Given("I click on unlock csr account$")
    public void clickOnUnLockCSR() {
        model.clickOnUnLockCSR();
    }

    @Given("I see CSR unlock form$")
    public void verifyCSRUnlockForm() {
        model.verifyCSRUnlockForm();
    }

    @Given("I unlock given user with validation verification")
    public void unlockGivenUser() {
        model.unlockGivenUser(c3CsrAccount.getAgentName());
    }

    @Given("^valid CSR agent$")
    public void setValidCSRAgent() {
        model.setValidAgent();
    }

    @Given("^valid CSR11 agent$")
    public void setValidCSR11Agent() {
        model.setValidAgent11();
    }

    @Given("no records for this agent created in Database$")
    public void verifyNoRecordsForThisCsrInDb() {
        model.verifyNoRecordsForThisCsrInDb();
    }

    @Given("agent login failures in Database is (.*)$")
    public void verifyAgentLoginFailuresInDB(Integer failuresNum) {
        model.verifyNumOfLoginFailuresInDb(failuresNum);
    }

    @Then("^I see \"([^\"]*)\" create csr message$")
    public void i_see_csr_create_message(String msg) {
        model.verifyCreateCSRMessage(msg);
    }

}

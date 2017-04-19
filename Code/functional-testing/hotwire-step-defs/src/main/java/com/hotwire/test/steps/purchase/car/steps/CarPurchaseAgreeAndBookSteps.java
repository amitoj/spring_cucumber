/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car.steps;

import com.hotwire.selenium.desktop.us.billing.car.impl.CarTravelerInfo;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author v-vzyryanov
 * @since 7/4/13
 */
public class CarPurchaseAgreeAndBookSteps extends AbstractSteps {

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel model;

    @Then("^rules and conditions are correct when (credit card|traveler's debit card|card of any type) is allowed$")
    public void verifyRulesAndRegulationsAccordingToDepositType(String depositType) {
        verify_deposit_checkbox_type_and_explanation(depositType);
    }

    @Then("^Deposit checkbox state is correct when (credit card|traveler's debit card|card of any type) is allow to " +
          "use$")
    public void verify_deposit_checkbox_type_and_explanation(String depositType) {

        if ("card of any type".equals(depositType)) {
            model.verifyDepositCheckboxTypeAndRulesAndRegulation(CarTravelerInfo.DepositCardAgreementType.NONE);
        }
        else if ("credit card".equals(depositType)) {
            model.verifyDepositCheckboxTypeAndRulesAndRegulation(
                CarTravelerInfo.DepositCardAgreementType.CREDIT_CARD_ONLY);
        }
        else if ("traveler's debit card".equals(depositType)) {
            model.verifyDepositCheckboxTypeAndRulesAndRegulation(
                CarTravelerInfo.DepositCardAgreementType.CREDIT_DEBIT_CARD_WITH_TICKET);
        }
    }

    @Then("^Deposit agreement checkbox is (checked|unchecked|absent)$")
    public void verifyDepositCheckboxState(String state) {
        if ("absent".equalsIgnoreCase(state)) {
            model.verifyDepositCheckboxPresence(false);
        }
        else {
            assertThat(model.isDepositCardAgreementConfirmed())
                .as("Deposit agreement checkbox is checked")
                .isEqualTo("checked".equals(state));
        }
    }

    @Then("^I (select|deselect) the deposit agreement's checkbox$")
    public void i_check_deposit_agreement_checkbox(String state) {
        model.selectDepositCardAgreementCheckbox("select".equals(state));
    }

    @Then("^Deposit agreement box is highlighted in red$")
    public void deposit_agreement_validation() {
        model.validateDepositCardAgreementBox();
    }

    @Then("^I read Terms of Use and Product Rules and Restrictions before booking$")
    public void readTermsOfUseAndProductRules() {
        model.readTermsOfUseAndProductRules();
    }
}

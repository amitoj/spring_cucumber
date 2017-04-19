/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.lowPriceGuarantee;


import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import cucumber.api.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/3/14
 * Time: 7:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class LowPriceGuaranteeSteps extends ToolsAbstractSteps {

    @Autowired
    private LowPriceGuaranteeModel model;

    @Given("^I click Low Price Guarantee Claim link$")
    public void clickOnLPGClaimLink() {
        model.clickOnLPGClaimLink();
    }

    @Given("^I fill LPG form$")
    public void fillLPGform() {
        model.fillLPGform();
    }

    @Given("^I see successful LPG message$")
    public void verifyLPGMsg() {
        model.verifyLPGMsg();
    }

    @Given("^I see error LPG message$")
    public void verifyFailedLPGMsg() {
        model.verifyFailedLPGMsg();
    }

    @Given("I don't see Low Price Guarantee Claim link")
    public void verifyLPGlinkNotPresent() {
        model.verifyLPGlinkNotPresent();
    }

    @Given("I (don't )?see LPG Request refund button")
    public void verifyLPGRefundBtn(String negation) {
        model.verifyLPGBtn(StringUtils.isEmpty(negation));
    }

    @Given("I click LPG refund request button")
    public void clickLPGRefundRequest() {
        model.clickLPGRefundRequest();
    }

    @Given("I see LPG refund request form")
    public void verifyLPGRefundRequestForm() {
        model.verifyLPGRefundRequestForm();
    }

    @Given("I fill LPG refund request form")
    public void fillLPGForm() {
        model.fillLPGForm();
    }

    @Given("I see message about successfully submitted LPG refund request")
    public void verifyLPGRefundRequestMsg() {
        model.verifyLPGRefundRequestMsg();
    }

    @Given("I see message that such request was already submitted")
    public void verifyInvalidLPGRefundMSG() {
        model.verifyInvalidLPGRefundMSG();
    }


    @Given("I see Low-Price Guarantee module")
    public void verifyLPGModule() {
        model.verifyLPGModule();
    }

}

/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car.steps;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-vzyryanov
 * Date: 6/14/13
 * Time: 4:05 AM
 */
public class CarPurchaseInsuranceSteps extends AbstractSteps {

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel model;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @When("^(Display|Suppress) insurance banner$")
    public void checkCarInsuranceBanner(String carInsuranceLogo) {
        model.checkCarInsuranceLogo(carInsuranceLogo.trim().equals("Display"));
    }

    @Given("^I[\\s]*(?:(don\\'t))? request insurance$")
    public void setInsuranceOption(String negation) {
        purchaseParameters.setOptForInsurance(StringUtils.isEmpty(negation));
    }

    @Given("^I skip insurance request$")
    public void skipInsuranceOption() {
        purchaseParameters.skipOptForInsurance();
    }

    @Then("^Rental car damage protection was (appear|unavailable|loaded dynamically|presented as static module)$")
    public void verifyCarInsuranceModuleTypeOnBillingPage(String typeOfModule) {
        model.verifyCarInsuranceModuleTypeOnBillingPage(typeOfModule);
    }
}

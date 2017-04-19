/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.search.hotel;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 7/16/13
 * Time: 8:12 AM
 * To change this template use File | Settings | File Templates.
 */

public class C3SearchForAHotelSteps extends ToolsAbstractSteps {

    @Autowired
    private C3SearchForAHotelModel model;

    @Then("^I open billing page$")
    public void openBillingPage() {
        model.openBillingPage();
    }

    @Then("^I see hotel billing page in frame$")
    public void verifyBillingPage() {
        model.verifyHotelBillingPage();
    }

    @Then("^I see hotwire terms bullets list$")
    public void verifyTermsBullets() {
        model.verifyTermsBullets();
    }

}




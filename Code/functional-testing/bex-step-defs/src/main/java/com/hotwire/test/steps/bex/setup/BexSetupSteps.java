/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.setup;

import com.hotwire.test.steps.bex.BexAbstractSteps;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/9/15
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BexSetupSteps extends BexAbstractSteps {
    @Autowired
    private BexSetupModel model;


    @Given("^BEX (.*) vertical is accessible$")
    public void bexAccesible(String vertical) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        model.abacusOverride();
        if (vertical.equalsIgnoreCase("hotel")) {
            model.goToHotel();
        }

    }

}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.application;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * User: v-vzyryanov
 * Date: 6/10/13
 * Time: 3:46 AM
 */
public class ApplicationSetupCarSteps extends AbstractSteps {

    @Resource
    protected Properties applicationProperties;

    @Autowired
    @Qualifier("applicationModel")
    ApplicationModel applicationModel;

    @Given("^activate car's version tests$")
    public void activateCarVersionTests() {
        applicationModel.activateCarVersionTests();
    }

    @Given("^billing on opaque details for cars is available$")
    public void unifyCarDetailsAndBooking() {
        applicationModel.unifyCarDetailsAndBilling();
    }

    @Given("^car ccf design is available$")
    public void activateCarCcfDefign() {
        applicationModel.addVersionTest("vt.CCF13", "4");
    }

    @Given("^local search for ccf is available$")
    public void activateLocalSearchForCcf() {
        applicationModel.addVersionTest("vt.CLS13", "2");
    }
}

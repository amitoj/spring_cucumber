/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.properties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

/**
 * @author adeshmukh
 *
 */
public class PropSteps extends AbstractSteps {

    @Autowired
    @Qualifier("propModel")
    private PropModel propModel;

    @Given("^I set the following properties:$")
    public void setProperties(List<String> properties) {
        propModel.setProperties(properties);
    }

    @And("^I reset the following properties:$")
    public void resetProperties(List<String> properties) {
        propModel.resetProperties(properties);
    }
}

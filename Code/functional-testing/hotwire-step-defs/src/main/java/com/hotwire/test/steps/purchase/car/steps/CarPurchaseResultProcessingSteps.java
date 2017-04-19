/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car.steps;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-vzyryanov
 * Date: 8/19/13
 * Time: 1:32 AM
 */
public class CarPurchaseResultProcessingSteps extends AbstractSteps {

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel model;

    @Given("^I select a car by cd code (\\w+)$")
    public void selectCarByCdCode(String carCdCode) {
        model.selectPGood(carCdCode);
    }

    @And("^I select a (opaque|retail) car with cd code \"([A-Z]{4})\"$")
    public void selectCarByTypeAndCdCode(String carType, String carCdCode) {
        model.selectPGood(carType, carCdCode);
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.car.steps;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-ozelenov
 * Since: 9/24/13
 */
public class CarSubscriberSteps extends AbstractSteps {

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel model;

    @Given("^subscriber checkbox is (checked|unchecked|unavailable)$")
    public void doSaveUsersInformation(String state) {
        model.verifySubscriberCheckBox(state);
    }
}

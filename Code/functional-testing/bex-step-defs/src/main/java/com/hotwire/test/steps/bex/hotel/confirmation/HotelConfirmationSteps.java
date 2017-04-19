/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.hotel.confirmation;

import com.hotwire.test.steps.bex.BexAbstractSteps;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 12:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelConfirmationSteps extends BexAbstractSteps {

    @Autowired
    private HotelConfirmationModelWebApp model;

    @Given("^I verify that hotel is booked successfully$")
    public void verifyBooking() throws InterruptedException {
        assertThat(model.verifySuccesfulBooking()).isTrue();
    }


}



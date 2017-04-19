/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.hotel.review;

import com.hotwire.test.steps.bex.BexAbstractSteps;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelReviewSteps extends BexAbstractSteps {

    @Autowired
    private HotelReviewModelWebApp model;

    @Given("^I complete Booking as name:(.*), phone:(.*), email:(.*)$")
    public void selectHotelFromResults(String name, String phone, String email) {
        model.completeReviewAndBook(name, phone, email);
    }

}



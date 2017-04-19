/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.hotel_reviews;

import com.hotwire.test.steps.AbstractSteps;
import cucumber.api.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-dsobko
 * Since: 02/07/15
 */
public class HotelReviewsSteps extends AbstractSteps {

    @Autowired
    private HotelReviewsService hotelReviewsService;

    @And("^I execute hotel reviews request$")
    public void executeHotelReviewsRequest() {
        hotelReviewsService.executeHotelReviewsRequest();
    }

    @And("^hotel reviews response is present$")
    public void verifyHotelReviewsResponse() {
        hotelReviewsService.verifyHotelReviewsResponse();
    }


}

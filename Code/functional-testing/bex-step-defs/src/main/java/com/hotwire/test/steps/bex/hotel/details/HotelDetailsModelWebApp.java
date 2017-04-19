/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.hotel.details;

import com.hotwire.selenium.bex.hotel.details.HotelDetailsPage;
import com.hotwire.test.steps.bex.BexAbstractModel;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelDetailsModelWebApp extends BexAbstractModel {

    public void clickRecommendedBookOption() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        hotelDetailsPage.selectRecommendedRoom();

    }

    public void clickPayNow() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        hotelDetailsPage.selectPayNow();
    }


    public void verifyDestination(String expectedDestination) {
        HotelDetailsPage hotelDetails = new HotelDetailsPage(getWebdriverInstance());
        String destination = hotelDetails.getSearchSummary();
        assertThat(destination.contains(expectedDestination.toLowerCase()));
    }
}


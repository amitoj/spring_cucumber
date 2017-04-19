/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.hotel.review;

import com.hotwire.selenium.bex.hotel.review.HotelReviewPage;
import com.hotwire.test.steps.bex.BexAbstractModel;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelReviewModelWebApp extends BexAbstractModel {

    public void completeReviewAndBook(String name, String phone, String email) {
        HotelReviewPage hotelReviewPage = new HotelReviewPage(getWebdriverInstance());
        hotelReviewPage.fillContactInfo(name, phone);
        hotelReviewPage.fillPaymentInfo();
        hotelReviewPage.fillEmailAddress(email);
        hotelReviewPage.completeBooking();
    }

}

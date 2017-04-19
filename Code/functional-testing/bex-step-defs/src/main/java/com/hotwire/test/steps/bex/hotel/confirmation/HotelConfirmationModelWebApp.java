/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.hotel.confirmation;

import com.hotwire.selenium.bex.hotel.confirmation.HotelConfirmationPage;
import com.hotwire.test.steps.bex.BexAbstractModel;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 12:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelConfirmationModelWebApp extends BexAbstractModel {

    public boolean verifySuccesfulBooking() throws InterruptedException {
        HotelConfirmationPage hotelConfirmationPage = new HotelConfirmationPage(getWebdriverInstance());
        return hotelConfirmationPage.getHeaderText().equalsIgnoreCase("Your hotel reservation is confirmed.");

    }


}

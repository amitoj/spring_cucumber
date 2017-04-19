/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotelCreditCard;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-vyulun on 7/11/2014.
 */

public class C3hotelInformationSearchForm extends ToolsAbstractPage {

    public C3hotelInformationSearchForm(WebDriver webDriver) {
        super(webDriver, By.cssSelector("#hotelName"));
    }

    public void searchHotelInformationByConfirmationNum(String itineraryNumber) {
        findOne("#confirmationNumber", DEFAULT_WAIT).sendKeys(itineraryNumber);
        submit();
    }

    public void searchHotelInformationByReservationNum(String reservationId) {
        findOne("#reservationNumber", DEFAULT_WAIT).sendKeys(reservationId);
        submit();
    }

    public void searchHotelInformationByCTA_Account(String ctaAccount) {
        findOne("#ctaNo", DEFAULT_WAIT).sendKeys(ctaAccount);
        findOne(By.name("Submit")).click();
    }

    public void searchHotelInformationByNameAndDates(String guestName) {
        findOne("#guestLastName", DEFAULT_WAIT).sendKeys(guestName);
        selectDate("#startingCheckInDate", getDateBefore(10));
        selectDate("#endingCheckInDate", getDateBefore(1));
        submit();
    }

    public void searchHotelInformationByHotelNameAndState(String hotelName, String state) {
        findOne("#hotelName", DEFAULT_WAIT).sendKeys(hotelName);
        findOne(By.name("hotelState")).sendKeys(state);
        submit();
    }

    public void submit() {
        findOne(By.name("Submit")).click();
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel.billing.TravelerFragments;

import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3HotelBillingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/24/14
 * Time: 6:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3GuestTravelerFragment extends C3HotelBillingPage {
    private static final String FIRST_NAME = "input[name='travelerInfoModel.travelers[0].firstName']";
    private static final String LAST_NAME = "input[name='travelerInfoModel.travelers[0].lastName']";
    private static final String EMAIL = "input[name='travelerInfoModel.emailAddress']";
    private static final String CONF_EMAIL = "input[name='travelerInfoModel.confirmEmailAddress']";
    private static final String PHONE = "input[name='travelerInfoModel.phoneNumber']";

    public C3GuestTravelerFragment(WebDriver webdriver) {
        super(webdriver, By.className("firstNameLine"));
        //super(webdriver, By.className("travelerInfoModule"));
    }

    public C3GuestTravelerFragment(WebDriver webdriver, By keyElement) {
        super(webdriver, keyElement);
    }


    public String getFirstName() {
        return findOne(FIRST_NAME).getAttribute("value");
    }

    public String getLastName() {
        return findOne(LAST_NAME).getAttribute("value");
    }

    public String getEmail() {
        return findOne(EMAIL).getAttribute("value");
    }

    public String getConfEmail() {
        return findOne(CONF_EMAIL).getAttribute("value");
    }

    public String getPhoneCountryCode() {
        return findOne(By.id("travelerInfoModel.phoneCountry")).getAttribute("value");
    }

    public String getPhone() {
        return findOne(PHONE).getAttribute("value");
    }

    public void setCustomerName(String fn, String ln) {
        setText(FIRST_NAME, fn);
        setText(LAST_NAME, ln);
    }

    public void setEmail(String email) {
        setText(EMAIL, email);
        setText(CONF_EMAIL, email);
    }

    public void setPhone(String phone) {
        setText(PHONE, phone);
    }

}

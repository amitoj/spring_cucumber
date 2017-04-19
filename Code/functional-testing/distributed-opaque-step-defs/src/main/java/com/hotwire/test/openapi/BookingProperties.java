/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.openapi;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 2/17/15
 * Time: 11:26 AM
 * Booking properties container
 */
public class BookingProperties {
    private String hotelBookingPath;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String currencyCode;
    private String hotelStatusPath;

    public void setHotelBookingPath(String hotelBookingPath) {
        this.hotelBookingPath = hotelBookingPath;
    }

    public String getHotelBookingPath() {
        return hotelBookingPath;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setHotelStatusPath(String hotelStatusPath) {
        this.hotelStatusPath = hotelStatusPath;
    }

    public String getHotelStatusPath() {
        return hotelStatusPath;
    }
}

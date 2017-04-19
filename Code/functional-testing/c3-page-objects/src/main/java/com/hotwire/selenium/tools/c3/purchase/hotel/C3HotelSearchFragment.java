/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel;

import com.hotwire.selenium.tools.c3.purchase.C3FareFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hotel Search Fragment in MultiVertical FareFinder in C3
 */
public class C3HotelSearchFragment extends C3FareFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3HotelSearchFragment.class.getName());

    public C3HotelSearchFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector("form.hotelFields"));
        LOGGER.info("US hotel purchase");
        setSearchForm("form.hotelFields");
    }

    public C3HotelSearchFragment(WebDriver webDriver, By keyElement) {
        super(webDriver, keyElement);
    }

    @Override
    public void setDestination(String fromDestination) {
        setText("input[name='destCity']", fromDestination);
    }

    public void setHotelRooms(Integer rooms) {
        try {
            logger.info("Old hotel room selector");
            selectValue("select#hotelRooms", rooms.toString());
        }
        catch (NoSuchElementException | ElementNotVisibleException | TimeoutException e) {
            logger.info("Old hotel room selector");
            selectLink("a#hotelRooms-button", rooms.toString());
        }
    }

    public void setGuests(Integer adults, Integer children) {
        try {
            logger.info("Old hotel room selector");
            selectValue("select#hotelRoomAdults", adults.toString());
            selectValue("select#hotelRoomChild", children.toString());
        }
        catch (NoSuchElementException | ElementNotVisibleException | TimeoutException e) {
            logger.info("Old hotel room selector");
            selectLink("a#hotelRoomAdults-button", adults.toString());
            selectLink("a#hotelRoomChild-button", children.toString());
        }
    }

    public void uncheckPartners() {
        try {
            findOne("div.partnerWrapper input[value='HO']").click();
        }
        catch (NoSuchElementException e) {
            LOGGER.info("No partners. Skip.");
        }
    }
}

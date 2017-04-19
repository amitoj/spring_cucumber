/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Date;

/**
 * Page Object
 */
public class C3HotelRetrieveGuestReservationsPage extends ToolsAbstractPage {
    public C3HotelRetrieveGuestReservationsPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("#minDateHotelSearch-field"));
    }

    public void searchByDates(Date from, Date to) {
        selectDate("#minDateHotelSearch-field", from);
        selectDate(By.name("toDate"), to);
        findOne(".formButton").click();
    }
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.purchase.air;

import com.hotwire.selenium.tools.c3.purchase.C3FareFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;



/**
 * Created by IntelliJ IDEA. User: v-abudyak Date: 5/4/12 Time: 6:32 PM To
 * change this template use File | Settings | File Templates.
 */

public class C3AirSearchFragment extends C3FareFinder {

    public C3AirSearchFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector("form.airFields"));
        setSearchForm("form.airFields");
    }

    public void setDestinations(String fromDestination, String toDestination) {
        setText("input[name='origCity']", fromDestination);
        setText("input[name='destinationCity']", toDestination);
    }


    public void setPassengers(Integer passengers) {
        try {
            logger.info("Old Passenger selector");
            selectValue("select[name='noOfTickets']", passengers.toString());
        }
        catch (NoSuchElementException | ElementNotVisibleException | TimeoutException e) {
            logger.info("New Passenger selector");
            selectLink("div.numTickets a", passengers.toString());
        }
    }
}

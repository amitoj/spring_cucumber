/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.myAccount;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/22/14
 * Time: 12:30 AM
 * Simplified My Trips Page Object
 */
public class HWMyTrips extends ToolsAbstractPage {
    public HWMyTrips(WebDriver webdriver) {
        super(webdriver, By.className("bookedTrips"));
    }

    public void selectLastBooking() {
        findOne("a.tripDetails").click();
    }
}

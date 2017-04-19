/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-ozelenov
 * Date: 2/20/14
 * Time: 7:53 AM
 * Verify Page after clicking on View details upon details
 */
public class C3HotelPageUponDetails extends ToolsAbstractPage {

    public C3HotelPageUponDetails(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div[data-jsname*='DetailsHotelMap']"));
    }

    public boolean isGoogleMapDisplayed() {
        return findOne("canvas", 4).isDisplayed();
    }
}

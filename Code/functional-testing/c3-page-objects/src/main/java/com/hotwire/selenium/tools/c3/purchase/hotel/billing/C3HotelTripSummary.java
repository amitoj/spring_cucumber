/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel.billing;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object
 */
public class C3HotelTripSummary extends ToolsAbstractPage {
    public C3HotelTripSummary(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.hotelTripSummary"));
    }

    public boolean isHotelProtectionAvailable() {
        return isElementDisplayed(By.cssSelector("div.tripInsurance"), DEFAULT_WAIT);
    }

    public String getHotwireTotal() {
        return findOne("span#totalCharge", DEFAULT_WAIT).getText();
    }

    public String getHotelProtectionAmount() {
        return findOne("span#insurance", DEFAULT_WAIT).getText();
    }

    public String getHotDollarsAmount() {
        freeze(1);
        return findOne("span#hotCredit", DEFAULT_WAIT).getText();
    }
}

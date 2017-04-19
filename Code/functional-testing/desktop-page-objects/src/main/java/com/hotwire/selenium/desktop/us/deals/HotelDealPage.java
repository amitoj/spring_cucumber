/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.deals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * User selected hotel deal on home page should be taken to hotel deal page.
 * Hotel deal page contains available deal options and fare finder.
 * Price change notification is optional.
 */
public class HotelDealPage extends AbstractUSPage {

    public HotelDealPage(WebDriver webDriver) {
        super(webDriver, "tiles-def.deals.engine.hotel.index", 20);
    }

    public boolean containsDeals() {
        return getWebDriver().findElements(By.cssSelector("div[data-jsname='DealsTableComp'] h1")).size() > 0;
    }

    public String getDealDescription() {
        return getWebDriver().findElement(By.cssSelector("div[data-jsname='DealsTableComp'] h1")).getText();
    }

    public void selectDealOption() {
        getWebDriver().findElement(By.cssSelector("table#dealData a")).click();
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.breadCrumbs;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * C3 breadcrumbs page object
 * Path links aka "Test Booking > View Past Bookings > View itinerary GP5539783692 details"
 */
public class C3BreadcrumbsFragment extends ToolsAbstractPage {
    public C3BreadcrumbsFragment(WebDriver webdriver) {
        super(webdriver, By.className("breadcrumbbar"));
    }

    public String getBreadcrumbs() {
        return findOne("td.breadcrumbbar", DEFAULT_WAIT).getText().trim();
    }

    public void gotoMainCustomerPage() {
        findOne("td.breadcrumbbar a", DEFAULT_WAIT).click();
    }
}

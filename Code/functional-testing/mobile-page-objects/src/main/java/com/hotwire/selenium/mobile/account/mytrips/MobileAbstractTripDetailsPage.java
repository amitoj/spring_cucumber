/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.account.mytrips;

import org.openqa.selenium.WebDriver;

import com.hotwire.selenium.mobile.MobileAbstractPage;

public abstract class MobileAbstractTripDetailsPage extends MobileAbstractPage {

    public MobileAbstractTripDetailsPage(WebDriver webdriver, String expectedPageName) {
        super(webdriver, expectedPageName);
    }

    public abstract void verifyTripDetailsPage();
}

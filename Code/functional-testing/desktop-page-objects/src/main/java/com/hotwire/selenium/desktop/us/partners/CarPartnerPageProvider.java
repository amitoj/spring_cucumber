/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: v-vzyryanov
 * Date: 4/15/13
 * Time: 4:40 AM
 */
public final class CarPartnerPageProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarPartnerPageProvider.class.getName());
    private static final int PAGE_LOAD_TIMEOUT = 20;

    private CarPartnerPageProvider() {
    }

    private static boolean checkUrl(String url, String host) {
        return url.startsWith(host);
    }

    public static CarPartnersPage get(WebDriver webDriver) {

        // Wait while inner content will be loaded
        webDriver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);

        String url = webDriver.getCurrentUrl();
        LOGGER.info("PARTNER URL: " + url);

        if (checkUrl(url, "http://www.bookingbuddy.com")) {
            return new CarBookingBuddyPage(webDriver);
        }
        if (checkUrl(url, "http://www.kayak.com")) {
            return new CarKayakPage(webDriver);
        }
        if (checkUrl(url, "http://www.carrentals.com")) {
            return new CarRentalsPage(webDriver);
        }
        if (checkUrl(url, "http://www.expedia.com")) {
            return new CarExpediaPage(webDriver);
        }
        if (checkUrl(url, "http://www.orbitz.com")) {
            return new CarOrbitzPage(webDriver);
        }

        throw new RuntimeException("Partner's page wasn't resolved");
    }

}

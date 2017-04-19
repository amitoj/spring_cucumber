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
 * User: v-vyulun
 * Date: 4/19/13
 * Time: 4:40 AM
 */
public final class HotelPartnerPageDetector {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelPartnerPageDetector.class.getName());
    private static final int PAGE_LOAD_TIMEOUT = 20;

    /**
     * Partners
     */
    public enum NAME {
        NONE("None"),
        EXPEDIA_COM("Expedia"),
        KAYAK_COM("Kayak"),
        ORBITZ_COM("Orbitz"),
        HOTELS_COM("Hotels"),
        PRICELINE_COM("PriceLine"),
        TRIVAGO("Trivago");

        private String pageName;
        NAME(String pageName) {
            this.pageName = pageName;
        }
    }

    private HotelPartnerPageDetector() {
    }

    private static boolean checkUrl(String url, String host) {
        return url.startsWith(host);
    }

    public static NAME detect(WebDriver webDriver) {

        // Wait while inner content will be loaded
        webDriver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);

        String url = webDriver.getCurrentUrl();
        LOGGER.info("PARTNER URL: " + url);

        for (NAME name : NAME.values()) {
            if (url.contains(name.pageName.toLowerCase())) {
                return name;
            }
        }
        throw new RuntimeException("Could not recognize the url " + url);
    }

    public static NAME validate(String partnerName) {
        for (NAME name : NAME.values()) {
            if (name.pageName.toLowerCase().trim().contains(partnerName.toLowerCase().trim()) ||
                    partnerName.toLowerCase().trim().contains(name.pageName.toLowerCase().trim())) {
                return name;
            }
        }

        throw new RuntimeException("Partner with name " + partnerName + " was not defined");
    }

}

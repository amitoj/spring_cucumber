/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.details.car;

import com.hotwire.selenium.mobile.details.car.impl.MobileNewCarDetailsPage;
import com.hotwire.selenium.mobile.details.car.impl.MobileOldCarDetailsPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MobilePageProviderUtils;

import java.util.List;

/**
 * User: v-jolopez
 * Date: 12/11/14
 */
public class MobileCarDetailsPageProvider extends MobilePageProviderUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileCarDetailsPageProvider.class);

    public static MobileCarDetailsPage get(WebDriver webdriver) {

        List<String> activatedVersionTests = getVersionTests(webdriver, "eVar1");
        LOGGER.info("vt: {}", activatedVersionTests);

        if (activatedVersionTests.contains("CMD14-02")) {
            return new MobileNewCarDetailsPage(webdriver);
        }
        else {
            return new MobileOldCarDetailsPage(webdriver);
        }
    }
}


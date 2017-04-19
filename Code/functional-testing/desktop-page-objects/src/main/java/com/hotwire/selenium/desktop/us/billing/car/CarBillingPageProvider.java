/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car;

import com.hotwire.selenium.desktop.us.billing.car.impl.CarBillingPage;
import com.hotwire.selenium.desktop.utils.DesktopPageProviderUtils;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 1/25/13
 * Time: 6:47 AM
 */
public class CarBillingPageProvider extends DesktopPageProviderUtils {

    public static final String TILES = "tile.car-details.*";

    public static CarBillingPage get(WebDriver webdriver) {

        List<String> activatedVersionTests = getVersionTests(webdriver, "eVar1");
        if (activatedVersionTests.contains("CCF13-04")) {
            return new CarBillingPage(webdriver, CarBillingPage.Type.CCF);
        }
        return new CarBillingPage(webdriver, CarBillingPage.Type.ONEPAGE);
    }
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car;

import com.hotwire.selenium.desktop.us.results.car.impl.CarResultsPageTemplate;
import com.hotwire.selenium.desktop.us.results.car.impl.CcfPage;
import com.hotwire.selenium.desktop.utils.DesktopPageProviderUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 1/16/13
 * Time: 6:43 AM
 */
public class CarResultsPageProvider extends DesktopPageProviderUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarResultsPageProvider.class);

    public static CarResultsPage get(WebDriver driver) {

        List<String> activatedVersionTests = getVersionTests(driver, "eVar1");
        LOGGER.info("vt: {}", activatedVersionTests);

        if (activatedVersionTests.contains("CCF13-04") ||
                "tile.car-spa.polling".equals(getPageTileDefinition(driver)) ||
                "tile.car-spa.noResults".equals(getPageTileDefinition(driver))) {
            return new CcfPage(driver);
        }
        else {
            return new CarResultsPageTemplate(driver);
        }
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results.car.fragments.fareFinder;

import com.hotwire.selenium.desktop.utils.DesktopPageProviderUtils;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import org.openqa.selenium.WebDriver;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 4/9/13
 * Time: 5:21 AM
 */
public class CarFareFinderProvider extends DesktopPageProviderUtils {

    public static CarFareFinder get(WebDriver webdriver) {
        new WebDriverWait(webdriver, MAX_SEARCH_WAITING)
                .until(PageObjectUtils.webElementVisibleTestFunction(
                        By.cssSelector("div.popupLayer img.waiting"), false));
        List<String> activatedVersionTests = getVersionTests(webdriver, "eVar1");

        if (activatedVersionTests.contains("CCF13-04") ||
                "tile.car-spa.polling".equals(getPageTileDefinition(webdriver))) {
            return new CcfCarFareFinder(webdriver);
        }
        return new CarFareFinderTemplate(webdriver);
    }
}

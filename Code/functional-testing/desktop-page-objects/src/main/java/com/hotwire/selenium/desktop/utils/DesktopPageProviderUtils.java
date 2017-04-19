/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author v-vzyryanov
 * @since 2/22/13
 */
public class DesktopPageProviderUtils {

    protected static final int MAX_SEARCH_WAITING = 30;

    /**
     * @return List of version tests used on current page
     */
    public static List<String> getVersionTests(WebDriver webdriver, String versionParameterName) {
        JavascriptExecutor js = (JavascriptExecutor) webdriver;
        Object jsObj = js.executeScript(
            "return AnalyticsSupport.getAnalyticsContextVariable('" + versionParameterName + "');"
        );

        if (jsObj instanceof List) {
            return (List<String>) jsObj;
        }

        if (jsObj instanceof String) {
            return Arrays.asList((String) jsObj);
        }

        return new ArrayList<>();
    }

    public static String getPageTileDefinition(WebDriver webdriver) {
        JavascriptExecutor js = (JavascriptExecutor) webdriver;
        Object jsObj = js.executeScript("return window.serverSideGlobalPassThrough.pageTileDefinition;");
        return (jsObj instanceof String) ? (String) jsObj : "";
    }
}

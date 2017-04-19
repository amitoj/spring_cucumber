/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: v-jolopez
 * Date: 12/11/14
 */
public class MobilePageProviderUtils {

    protected static final int MAX_SEARCH_WAITING = 30;

    protected MobilePageProviderUtils() {

    }


    /**
     * @return List of version tests used on current page
     */
    public static List<String> getVersionTests(WebDriver webdriver, String versionParameterName) {
        JavascriptExecutor js = (JavascriptExecutor) webdriver;
        Object jsObj = js.executeScript(
                "return AnalyticsSupport.getAnalyticsContextVariable('" + versionParameterName + "');",
                new Object[] {});

        if (jsObj instanceof ArrayList) {
            return (ArrayList<String>) jsObj;
        }

        if (jsObj instanceof String) {
            return Arrays.asList((String) jsObj);
        }

        return new ArrayList();
    }

    public static String getPageTileDefinition(WebDriver webdriver) {
        JavascriptExecutor js = (JavascriptExecutor) webdriver;
        Object jsObj = js.executeScript(
                "return window.serverSideGlobalPassThrough.pageTileDefinition;",
                new Object[] {});
        return (jsObj instanceof String) ? (String) jsObj : "";
    }

}


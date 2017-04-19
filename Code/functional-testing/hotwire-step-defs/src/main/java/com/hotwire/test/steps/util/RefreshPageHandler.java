/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.util;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.util.webdriver.WebDriverManager;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import cucumber.api.PendingException;

/**
 * @author vjong
 */
public final class RefreshPageHandler {
    // Retry search if still on UHP/landing page and no error message. Use the number of threads to determine how often
    // to retry.
    public static final int MAX_RETRIES = getMaxRetries();
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshPageHandler.class.getSimpleName());

    private RefreshPageHandler() { /* utility class */ }

    private static int getMaxRetries() {
        try {
            String count = System.getProperty("cucumber.thread.count");
            if (count != null) {
                int out = Integer.parseInt(count);
                return out;
            }
        }
        catch (Exception e) {
            // No op
        }
        return 1;
    }

    public static boolean reloadPageIfDropdownSkinNotLoaded(
            final WebDriver webdriver, By dropDownLocatorValue) {
        return reloadPageIfDropdownSkinNotLoaded(webdriver, dropDownLocatorValue, null);
    }

    public static boolean reloadPageIfDropdownSkinNotLoaded(
            final WebDriver webdriver, final By dropDownLocatorValue, final By uhpVertical) {
        LOGGER.info("Max retry search handler: " + MAX_RETRIES);
        // test versions NAC13 might be slow to load for fare finders. Wait for skin element to become visible.
        // Remove the wait condition if it is decided to go back to control version.
        try {
            new WebDriverWait(webdriver, 10).until(new VisibilityOf(dropDownLocatorValue));
            return false;   // Did not need to reload page.
        }
        catch (TimeoutException e) {
            LOGGER.info("Timed out waiting for drop down skin elements. Reloading page.");
            webdriver.navigate().refresh();
            WebDriverManager.acceptAlert(webdriver);
            if (uhpVertical != null) {
                // Click uhpVertical radio button.
                webdriver.findElement(uhpVertical).click();
            }

            try {
                new WebDriverWait(webdriver, 10).until(new VisibilityOf(dropDownLocatorValue));
                return true;    // Page was reloaded and succeeded.
            }
            catch (TimeoutException te) {
                // Page was reloaded but failed.
                throw new PendingException(
                    "Reload failed to fix issue with time out NAC13 drop down skin elements. " +
                    "Aborting test run.");
            }
        }
    }

}

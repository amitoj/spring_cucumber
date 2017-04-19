/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.travelsavingsindicator;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * @author vjong
 *
 */
public class TravelSavingsIndicatorPage extends AbstractUSPage {

    public TravelSavingsIndicatorPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.spotlight.hotel-rate-report");
    }

    public boolean isSavingsIndicatorTableDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(".columnLeft .universalVariant img")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

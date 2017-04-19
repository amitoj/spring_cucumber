/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.travelvalueindex;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * @author vjong
 *
 */
public class TravelValueIndexPage extends AbstractUSPage {

    public TravelValueIndexPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.destination-tvi");
    }

    public boolean isTravelValueIndexCompDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(".TVIComp")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isFareFinderDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(".FareFinderComp")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

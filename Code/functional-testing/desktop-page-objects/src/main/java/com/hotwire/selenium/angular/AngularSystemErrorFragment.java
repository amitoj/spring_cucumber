/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.angular;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.po.AbstractPage;

/**
 * @author vjong
 */
public class AngularSystemErrorFragment extends AbstractPage {

    public AngularSystemErrorFragment(WebDriver webdriver) {
        super(webdriver, new InvisibilityOf(By.cssSelector("img[ng-show='isSearchInProgress']")), 90);
    }

    public boolean isUIExceptionDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector("div[data-bdd='uiExceptions']")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAPIExceptionDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector("div[data-bdd='apiExceptions']")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

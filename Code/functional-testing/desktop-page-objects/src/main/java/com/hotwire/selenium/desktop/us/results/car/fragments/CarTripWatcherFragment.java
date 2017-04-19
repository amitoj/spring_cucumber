/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results.car.fragments;

import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-jneiman
 * Date: 6/11/13
 * Time: 8:06 AM
 */
public class CarTripWatcherFragment extends AbstractPage {

    public static final String TRIP_WATCHER_LAYER = ".greySideBar.priceDrop," +
            " .content.rightSide.needHelp.contentWatchThisTripNotifier";
    public static final String EMAIL = ".email";
    public static final String SEND =  ".btn";
    public static final String COPY = "//div[contains(@class,'carTripWatcher')]//p[1]//strong";
    public static final String TRIP_WATCHER_BLOCK = ".greySideBar.priceDrop";
    public static final String TRIP_WATCHER_TITLE = TRIP_WATCHER_BLOCK + " .body .title, .carWatching";
    public static final String TRIP_WATCHER_EMAIL = TRIP_WATCHER_BLOCK + " .email";
    public static final String TRIP_WATCHER_EMAIL_VALIDATION = TRIP_WATCHER_BLOCK + " .errorMessageSide .email";

    @FindBy(css = EMAIL)
    private WebElement email;

    @FindBy(css = SEND)
    private WebElement sendButton;

    @FindBy(xpath = COPY)
    private WebElement copy;

    public CarTripWatcherFragment(WebDriver webdriver) {
        super(webdriver);
    }

    public WebElement getSendButton() {
        return getWebDriver().findElement(By.cssSelector(TRIP_WATCHER_LAYER)).findElement(By.cssSelector(SEND));
    }

    public WebElement getCopy() {
        return getWebDriver().findElement(By.cssSelector(TRIP_WATCHER_LAYER)).findElement(By.xpath(COPY));
    }


    public boolean isTripWatcherDisplayed() {
        try {
            return getEmailTextField().isDisplayed() && getSendButton().isDisplayed();
        }
        catch (RuntimeException ex) {
            return false;
        }
    }

    public String getEmailValue() {
        return getEmailTextField().getAttribute("value");
    }

    public WebElement getEmailTextField() {
        return getWebDriver().findElement(By.cssSelector(TRIP_WATCHER_EMAIL));
    }

    public String getTripWatcherCopy() {
        return getWebDriver().findElement(By.cssSelector(TRIP_WATCHER_TITLE)).getText();
    }

    public void watchTrip() {
        getSendButton().click();
    }

    public boolean getEmailTextFieldValidationError() {
        return getWebDriver().findElement(By.cssSelector(TRIP_WATCHER_EMAIL_VALIDATION)).isDisplayed();
    }
}

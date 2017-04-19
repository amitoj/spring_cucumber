/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.row.search;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.row.AbstractRowPage;

/**
 * Page object of last minute page
 */
public class LastMinutePage extends AbstractRowPage {

    @FindBy(className = "landingSecretFareFinder")
    private WebElement fareFinder;

    @FindBy(className = "LastMinuteSubscriptionModule")
    private WebElement subscriptionModule;

    @FindBy(className = "StandardRateHotelsModule")
    private WebElement standardRateHotelsModule;

    @FindBy(className = "weekendDealsModule")
    private WebElement weekendDealsModule;

    public LastMinutePage(WebDriver webdriver) {
        super(webdriver);
    }

    public void verifyLastMinuteHotels() {
        assertThat(fareFinder.isDisplayed()).isTrue();
        assertThat(subscriptionModule.isDisplayed()).isTrue();
        assertThat(standardRateHotelsModule.isDisplayed() || weekendDealsModule.isDisplayed()).isTrue();
    }
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.helpCenter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 9/27/13
 */
public class TravelAdvisoryAlertsSiteFragment extends HelpCenterAbstractPage {

    private static final String ALERTS = "div.travelerAdvisor ul li a";

    public TravelAdvisoryAlertsSiteFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.travelerAdvisor"));
    }

    public boolean isDisplayed() {
        return isElementDisplayed(ALERTS);
    }

    public List<String> getAlerts() {
        List<String> alerts = new ArrayList();
        List<WebElement> alertLinks = findMany(ALERTS);
        for (WebElement alertLink : alertLinks) {
            alerts.add(alertLink.getText());
        }
        return alerts;
    }


    public void clickOnTravelAdvisoryAlert() {
        findOne(ALERTS).click();
    }
}

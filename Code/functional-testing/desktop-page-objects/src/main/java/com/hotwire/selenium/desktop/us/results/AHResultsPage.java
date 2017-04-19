/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.AbstractDesktopPage;

/**
 * @author v-jhernandeziniguez
 *
 */
public class AHResultsPage extends AbstractDesktopPage {
    @FindBy(id = "acol-flight-check-in")
    private WebElement checkIn;
    @FindBy(id = "acol-flight-check-out")
    private WebElement checkOut;
    public AHResultsPage(WebDriver webdriver) {
        super(webdriver);
    }

    public String getCheckIn() {
        return checkIn.getAttribute("value");
    }

    public String getCheckOut() {
        return checkOut.getAttribute("value");
    }

    public boolean isCheckInDisplayed() {
        try {
            checkIn.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

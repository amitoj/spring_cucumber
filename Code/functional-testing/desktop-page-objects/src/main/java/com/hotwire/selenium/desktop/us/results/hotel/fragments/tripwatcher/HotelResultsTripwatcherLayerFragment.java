/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.hotel.fragments.tripwatcher;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
/**
 * Class for the Hotel Trip watcher layer
 */
public class HotelResultsTripwatcherLayerFragment extends AbstractUSPage {
    private static final String TRIP_WATCHER_LAYER = "#tripWatcherLayer-layer, #speedBumpTripWatcherLayer-layer";
    private static final String EMAIL = "#tripWatcherLayer_email, #speedBumpTripWatcherLayer_email";
    private static final String SUBSCRIBE = ".watchthistripbtn .btn.smallBtn";
    private static final String SUCCESSFUL_MESSAGE_LOCATOR = ".success";
    private static final String SUCCESSFUL_MESSAGE = "We're now watching this trip for you!";
    private static final String CLOSE = "//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all" +
            " ui-draggable no-titlebar']/DIV[1]//A";

    @FindBy(css = EMAIL)
    private WebElement email;

    @FindBy(css = SUBSCRIBE)
    private WebElement subscribeButton;

    @FindBy(css = SUCCESSFUL_MESSAGE_LOCATOR)
    private  WebElement successfulMessage;

    @FindBy(xpath = CLOSE)
    private  WebElement closeButton;


    public HotelResultsTripwatcherLayerFragment(WebDriver webDriver) {
        super(webDriver);
    }

    public static final String getTripWatcherLayerLocator() {
        return TRIP_WATCHER_LAYER;
    }

    public boolean isTripWatcherDisplayed() {
        try {
            return email.isDisplayed() && subscribeButton.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void closeTripWatcherLayer() {
        closeButton.click();
    }

    public void watchThisTrip(String newEmail) {
        if (newEmail.trim().equals("empty")) {
            newEmail = "";
        }
        WebElement signupEmail = getVisibleTripWatcherEmailElement();
        signupEmail.clear();
        signupEmail.sendKeys(newEmail);
        getVisibleTripWatcherSubscribeButton().click();
        new WebDriverWait(getWebDriver(), EXTRA_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(".success"), true));
        assertThat(getWebDriver().findElement(By.cssSelector(".success")).getText())
            .contains(SUCCESSFUL_MESSAGE);
        closeButton.click();
    }

    public boolean isTripWatcherLayerFromSpeedBump() {
        for (WebElement element : getWebDriver().findElements(By.cssSelector(TRIP_WATCHER_LAYER))) {
            if (element.isDisplayed()) {
                if (element.getAttribute("id").equals("speedBumpTripWatcherLayer-layer")) {
                    return true;
                }
            }
        }
        return false;
    }

    public WebElement getVisibleTripWatcherSubscribeButton() {
        for (WebElement element : getWebDriver().findElements(By.cssSelector(SUBSCRIBE))) {
            if (element.isDisplayed()) {
                logger.info("Visible button element id: " + element.getAttribute("class"));
                return element;
            }
        }
        throw new RuntimeException("TripWatcher email not visible for use.");
    }

    public WebElement getVisibleTripWatcherEmailElement() {
        for (WebElement element : getWebDriver().findElements(By.cssSelector(EMAIL))) {
            if (element.isDisplayed()) {
                logger.info("Visible email element id: " + element.getAttribute("id"));
                return element;
            }
        }
        throw new RuntimeException("TripWatcher email not visible for use.");
    }

    public static void waitForTripWatcherVisibility(final WebDriver webdriver) {
        new WebDriverWait(webdriver, 20).until(
            new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver input) {
                    new HotelResultsPage(input);
                    for (WebElement element : input.findElements(By.cssSelector(TRIP_WATCHER_LAYER))) {
                        if (element.isDisplayed()) {
                            return true;
                        }
                    }
                    return false;
                }
            });
    }
}

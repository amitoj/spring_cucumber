/**
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results;

import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 1/28/14
 * Time: 8:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class AirTripWatcherFragment extends AbstractPage {
    private static final String TRIP_WATCHER_MODULE = ".wttBody," +
            " .content.rightSide.needHelp.contentWatchThisTripNotifier";
    private static final String EMAIL = ".email";
    private static final String SEND_BUTTON = ".btn";

    @FindBy(css = EMAIL)
    private WebElement email;

    @FindBy(css = SEND_BUTTON)
    private WebElement sendButton;

    public AirTripWatcherFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(TRIP_WATCHER_MODULE));

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            throw new UnimplementedTestException("Not implemented yet");
        }
    }

    public WebElement getEmail() {
        return getWebDriver().findElement(By.cssSelector(TRIP_WATCHER_MODULE)).findElement(By.cssSelector(EMAIL));
    }

    public WebElement getSendButton() {
        return getWebDriver().findElement(By.cssSelector(TRIP_WATCHER_MODULE)).findElement(By.cssSelector(SEND_BUTTON));
    }

    public void watchThisTrip() {
        getSendButton().click();
    }


}

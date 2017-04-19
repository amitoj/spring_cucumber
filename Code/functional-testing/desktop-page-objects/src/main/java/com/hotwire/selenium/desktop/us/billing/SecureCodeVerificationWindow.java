/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing;

import com.google.common.base.Function;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: v-achotemskoy
 * Date: 2/14/14
 * Time: 3:59 AM
 * <p/>
 * This page object describes SecureCode verification for credit card.
 * This window is a 3rd party iframe that appears for some credit cards after billing page.
 * Appears for all verticals.
 * <p/>
 * Tiles that used on this page -  "tile.hotel.confirm.pasOpener", "tile.hotel.confirm.pasCloser", "tiles-def.pas"
 */
public class SecureCodeVerificationWindow extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecureCodeVerificationWindow.class);

    public SecureCodeVerificationWindow(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected int getTimeout() {
        return 3;
    }

    public void proceed() {
        //such window appears after billing page, but before confirmation page.
        // It requires to enter additional 4 digits secure code.

        try {
            getWebDriver().switchTo().frame("pasframe");
            LOGGER.info("3DS verification is available for this card. Handling appeared window.");
        }
        catch (NoSuchFrameException noSuchFrameException) {
            //No action needed - secure code validation not available.
            return;
        }

        //on test environments secure code is - 1234
        getWebDriver().findElement(By.cssSelector("input[type='password']")).sendKeys("1234");
        getWebDriver().findElement(By.cssSelector("input[type='submit']")).click();
        LOGGER.info("Page tile is " + new PageName().apply(getWebDriver()));
        //getWebDriver().switchTo().frame(0);

        LOGGER.info("3DS secure verification completed. Waiting for next page.");
        new WebDriverWait(getWebDriver(), 30)
                .until(new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver webDriver) {
                        String regex = "tile.hotel.confirm.pasOpener|tile.hotel.confirm.pasCloser|tiles-def.pas|";
                        boolean securePageIsVisible = new PageName().apply(webDriver).matches(regex);
                        return !securePageIsVisible;
                    }
                });
    }
}

/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;

/**
 * Popup window on billing page.
 */
public class BillingTermsPopupWindow extends AbstractUSPage {

    public BillingTermsPopupWindow(WebDriver webDriver) {
        super(webDriver,
              "tiles-def.popup.terms-of-use-pop-up",
              new Wait<Boolean>(new VisibilityOf(By.cssSelector("ul[role='tablist']"))).maxWait(5));
    }

    public String getActiveTabText() {
        for (WebElement webElement : getWebDriver().findElements(By.cssSelector("div[role='tabpanel']"))) {
            if (webElement.isDisplayed()) {
                return webElement.getText();
            }
        }
        throw new NoSuchElementException("Could not find active tab!");
    }
}

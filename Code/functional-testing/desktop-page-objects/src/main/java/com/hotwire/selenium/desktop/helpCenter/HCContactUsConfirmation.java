/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-ozelenov
 * Date: 3/27/14
 */
public class HCContactUsConfirmation extends HelpCenterAbstractPage {
    public HCContactUsConfirmation(WebDriver webdriver) {
        super(webdriver, By.cssSelector("img[alt='Continue']"));
    }

    public boolean isConfirmationMessagePresent() {
        return findOne("p.first", HelpCenterAbstractPage.DEFAULT_WAIT).isDisplayed();
    }
}

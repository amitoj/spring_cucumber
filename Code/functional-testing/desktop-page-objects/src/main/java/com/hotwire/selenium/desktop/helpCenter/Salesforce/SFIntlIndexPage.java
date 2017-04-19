/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter.Salesforce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * User: v-ozelenov
 * Date: 3/18/14
 */
public class SFIntlIndexPage extends SFHCIndexPage {

    public SFIntlIndexPage(WebDriver webDriver) {
        super(webDriver, By.linkText("Help Centre"));
    }

    @Override
    public void clickAnyArticle() {
        findOne("div.intlTrAdvModule ul li a").click();
    }

    @Override
    public boolean verifyTravelAdvisories() {
        return findOne("div.intlTrAdvModule", DEFAULT_WAIT).isDisplayed();
    }
}

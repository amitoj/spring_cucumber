/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter.Salesforce;

import com.hotwire.selenium.desktop.helpCenter.HelpCenterAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-ozelenov
 * Date: 1/14/14
 */
public class SFAdminIndexPage  extends HelpCenterAbstractPage {

    public SFAdminIndexPage(WebDriver webDriver) {
        super(webDriver, By.id("tabBar"));
    }

    public void clickOnSmartContact() {
        clickOnLink("ContactCategoryMaps", DEFAULT_WAIT);
    }
}

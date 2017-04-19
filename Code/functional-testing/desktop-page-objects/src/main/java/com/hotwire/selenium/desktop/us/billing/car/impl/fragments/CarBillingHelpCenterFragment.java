/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.fragments;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-ngolodiuk
 * Since: 7/10/14
 */
public class CarBillingHelpCenterFragment extends AbstractPageObject {

    public CarBillingHelpCenterFragment(WebDriver webdriver) {
        super(webdriver, By.className("contactModuleHeader"));
    }

    public String getCustomerCarePhoneNumber() {
        return getWebDriver().findElement(By.cssSelector(".chatPhoneNumber")).getText();
    }

    public String getReferenceNumber() {
        return getWebDriver().findElement(By.cssSelector(".referenceNumber")).getText();
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.air.billing;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Simplified AirBillingPage for C3
 */
public abstract class C3AirBillingPage extends ToolsAbstractPage {

    public C3AirBillingPage(WebDriver webDriver, By keyElement) {
        super(webDriver, keyElement);
    }

    public abstract void proceed();


}

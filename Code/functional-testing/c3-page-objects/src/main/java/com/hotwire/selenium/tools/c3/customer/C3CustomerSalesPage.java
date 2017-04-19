/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 3/28/14
 * Time: 8:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3CustomerSalesPage extends ToolsAbstractPage {

    public C3CustomerSalesPage(WebDriver webdriver) {
        super(webdriver, By.id("TESTID_retToCustomerAcc"));
        //waitElementStopMoving("td.breadcrumbbar", EXTRA_WAIT);
    }

    public void returnToAccount() {
        findOne(By.id("TESTID_retToCustomerAcc"), DEFAULT_WAIT).click();
    }
}

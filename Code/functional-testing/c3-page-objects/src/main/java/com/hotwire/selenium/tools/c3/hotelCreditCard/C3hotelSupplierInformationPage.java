/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotelCreditCard;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-vyulun on 7/16/2014.
 */

public class C3hotelSupplierInformationPage extends ToolsAbstractPage {

    public C3hotelSupplierInformationPage(WebDriver webDriver) {
        super(webDriver, By.xpath("//input[@value='Email Tax Rate Update Request']"));
    }

}


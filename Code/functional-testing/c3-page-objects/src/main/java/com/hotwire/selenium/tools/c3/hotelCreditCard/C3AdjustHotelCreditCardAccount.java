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
 * Created by v-vyulun on 7/11/2014.
 */

public class C3AdjustHotelCreditCardAccount extends ToolsAbstractPage {

    public C3AdjustHotelCreditCardAccount(WebDriver webDriver) {
        super(webDriver, By.xpath("//td[text()='Adjust Hotel Credit Card Account']"));
    }

    public String getSendUpdateButtonText() {
        return findOne(By.xpath(".//input[@value='Send Update Request to Wright Express']")).getAttribute("value");
    }

    public String getViewSearchButtonText() {
        return findOne(By.xpath(".//input[@value='View Search Results']")).getAttribute("value");
    }
}


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
 * User: v-ikomarov
 * Date: 10/30/14
 * Time: 2:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3PaymentDetailsPage extends ToolsAbstractPage {
    public C3PaymentDetailsPage(WebDriver webdriver) {
        super(webdriver, By.xpath("//*[@class='displayHeader' and contains(text(),'Payment Details')]"));
    }

}

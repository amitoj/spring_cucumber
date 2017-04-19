/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.overcharges;


import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 11/20/13
 * Time: 7:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3OverchargesReportPage extends AbstractPage {

    public C3OverchargesReportPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void verifyReport() {
        getWebDriver().findElement(By.cssSelector("table#ovchreport"));
    }

    public Integer getSendButtonsNum() {
        return getWebDriver().findElements(By.cssSelector(".buttons a")).size();
    }

}

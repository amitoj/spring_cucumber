/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-vyulun
 * Date: 7/23/14
 * Time: 3:05 PM
 */

public class C3HotelReportsDeliveryPage extends ToolsAbstractPage {

    public C3HotelReportsDeliveryPage(WebDriver webdriver) {
        super(webdriver, By.name("alwaysFax"));
    }


    public void sendFax(String number) {
        findOne(By.name("alwaysFax"), DEFAULT_WAIT).click();
        findOne(".fax").clear();
        findOne(".fax").sendKeys(number);
        findOne(By.xpath("//input[@value='Save Changes']")).click();
    }

    public void sendEmail(String email) {
        findOne(By.name("email"), DEFAULT_WAIT).click();
        findOne(".email").clear();
        findOne(".email").sendKeys(email);
        findOne(By.xpath("//input[@value='Save Changes']")).click();
    }


    public void uncheckDARReport() {
        uncheckBox(By.xpath(".//input[@name='checkedReportCodes[1]']"));
        findOne(By.xpath(".//input[@value='Save Changes']"), DEFAULT_WAIT).click();
    }

    public void uncheckDFRReport() {
        uncheckBox(By.xpath(".//input[@name='checkedReportCodes[2]']"));
        findOne(By.xpath(".//input[@value='Save Changes']"), DEFAULT_WAIT).click();
    }

    public void uncheckCMRReport() {
        uncheckBox(By.xpath(".//input[@name='checkedReportCodes[3]']"));
        findOne(By.xpath(".//input[@value='Save Changes']"), DEFAULT_WAIT).click();
    }

    public void uncheckMEBRReport() {
        uncheckBox(By.xpath(".//input[@name='checkedReportCodes[4]']"));
        findOne(By.xpath(".//input[@value='Save Changes']"), DEFAULT_WAIT).click();
    }
}


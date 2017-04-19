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
 * User: v-asnitko
 * Date: 4/7/14
 * Time: 9:00 AM
 * Page object which contains all hotel supplier information
 */
public class C3HotelSupplierDetailsFragment extends ToolsAbstractPage {

    private static final String ROW_TMP = "//div[@class='hotelInfo'] //li//label[text()='%s']/..";

    public C3HotelSupplierDetailsFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector("div.hotelInfo"));
    }

    private String getValueByRowName(String rowName) {
        return getWebDriver().findElement(By.xpath(String.format(ROW_TMP, rowName))).getText();
    }

    public String getHotelId() {
        return getValueByRowName("Hotel ID");
    }

    public String getHotelFaxNumber() {
        return getValueByRowName("Hotel Fax Number");
    }

    public String getHotelPhoneNumber() {
        return getValueByRowName("Hotel Phone Number");
    }

    public String getHotelName() {
        return getValueByRowName("Hotel Name");
    }

    public String getHotelAddress() {
        return getValueByRowName("Hotel Address");
    }

}

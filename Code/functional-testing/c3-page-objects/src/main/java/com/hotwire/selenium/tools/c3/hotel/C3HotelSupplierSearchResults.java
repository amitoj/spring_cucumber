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
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 4/11/14
 * Time: 3:05 PM
 */

public class C3HotelSupplierSearchResults extends ToolsAbstractPage {

    public C3HotelSupplierSearchResults(WebDriver webdriver) {
        super(webdriver, By.cssSelector("a[href*='hotelSearchResults.jsp']"));
    }

    public void selectHotel(String hotelName) {
        clickOnLink(hotelName, 5);
    }

    public List<String> getHotelNamesList() {
        List<String> hotelNames = new ArrayList<String>();
        List<WebElement> results = findMany(By.xpath("//label[@for='hotelName']/a"));
        Integer listSize = results.size();
        for (Integer i = 1; i <= listSize; i++) {
            String text = results.get(i - 1).getText();
            hotelNames.add(text);
        }
        return hotelNames;
    }

    public List<String> getHotelStateList() {
        List<String> hotelStates = new ArrayList<String>();
        List<WebElement> results = findMany(By.xpath("//label[@for='State']"));
        Integer listSize = results.size();
        for (Integer i = 4; i <= listSize; i++) {
            String text = results.get(i - 1).getText();
            hotelStates.add(text);
        }
        return hotelStates;
    }
}


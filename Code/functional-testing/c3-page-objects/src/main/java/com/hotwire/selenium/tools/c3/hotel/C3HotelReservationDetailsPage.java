/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-vyulun
 * Date: 08/05/14
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class C3HotelReservationDetailsPage extends ToolsAbstractPage {

    public C3HotelReservationDetailsPage(WebDriver webdriver) {
        super(webdriver, By.xpath(".//*[text()='Hotel Reservation Details']"));
    }


    public Map getHotelReservationDetails() {
        Map row = new HashMap();
        List<WebElement> elements = findMany(".infobarValue");
        row.put("Last Name", elements.get(6).getText().split(" ")[1]);
        row.put("First Name", elements.get(6).getText().split(" ")[0]);
        row.put("Check-in date", elements.get(7).getText());
        row.put("Check-out date", elements.get(8).getText());
        row.put("Hotwire Confirmation", elements.get(0).getText().split(" ")[0]);
        row.put("Hotel Reservation", elements.get(1).getText().split(" ")[0]);
        row.put("Status of Reservation", elements.get(2).getText());
        row.put("Number of Rooms", elements.get(9).getText());
        row.put("Hotel Name", elements.get(3).getText());
        row.put("City", elements.get(4).getText().split(", ")[0]);
        row.put("State", elements.get(4).getText().split(", ")[1]);
        row.put("Hotel Phone", elements.get(5).getText());
        row.put("Total Supplier Cost", elements.get(16).getText().replace("$", "")
                .replace(",", ""));
        row.put("Charge to HW Card", elements.get(10).getText().replace("$", ""));
        return row;
    }
}


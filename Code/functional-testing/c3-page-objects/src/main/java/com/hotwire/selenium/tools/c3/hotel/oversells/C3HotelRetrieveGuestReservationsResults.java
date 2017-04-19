/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.hotel.oversells;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page Object
 */
public class C3HotelRetrieveGuestReservationsResults extends ToolsAbstractPage {
    public C3HotelRetrieveGuestReservationsResults(WebDriver webdriver) {
        super(webdriver, By.name("hotelGuestSelect[0]"));
    }

    public List<Map<String, Object>> getResultsTable() {
        List<Map<String, Object>> rows = new ArrayList<>();
        Map row;

        List<WebElement> elements = findMany(".sortable>tbody>tr");

        for (int j = 1; j < elements.size(); j++) {
            row = new HashMap();
            String[] data = elements.get(j).getText().split(" ");
            row.put("GUEST_LAST_NAME", data[1].replace(",", ""));
            row.put("GUEST_FIRST_NAME", data[2]);
            row.put("CHECK_IN_DATE", data[3]);
            row.put("CHECK_OUT_DATE", data[4]);
            row.put("PARTNER_SITE", data[5]);
            row.put("HW_ITINERARY_NUMBER", data[6]);
            row.put("RESERVATION_NUMBER", data[7]);
            rows.add(row);
        }
        return rows;
    }
}

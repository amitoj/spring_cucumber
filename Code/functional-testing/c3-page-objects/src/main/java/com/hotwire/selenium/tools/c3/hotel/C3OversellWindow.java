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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Oversells windows with search query results. New Workflow created from this window.
 */
public class C3OversellWindow extends ToolsAbstractPage {

    public C3OversellWindow(WebDriver webdriver) {
        super(webdriver, By.cssSelector("form[name='reservationForm']"));
    }

    public C3OversellWindow selectSomeOversell() {
        findOne("input[name='hotelGuestSelect[0]']", EXTRA_WAIT).click();
        return this;
    }

    public void btnSaveDocumentClick() {
        findOne("input[value='Save Document']").click();
    }

    public void btnCreateWorkflowClick(String value) {
        setText("textarea[name='instructions']", value);
        findOne("input[value='Create Workflow']").click();
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
            row.put("Opacity", data[9]);
            row.put("EXPRESS", data[10]);
            row.put("SITE_COUNTRY_CODE", data[11]);
            rows.add(row);
        }
        return rows;
    }
}

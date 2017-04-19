/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.riskManagement;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-vyulun on 6/25/2014.
 */

public class C3ReservationSearchResultsPage extends ToolsAbstractPage {
    public C3ReservationSearchResultsPage(WebDriver webDriver) {
        super(webDriver, By.xpath("//b[text()='Reservation Search Results']"));
    }

    public void selectItineraryByNum(Integer number) {
        findOne(By.xpath("//td[@class='displayField'][" + number + "]/label/a"), DEFAULT_WAIT).click();
    }

    public String getItineraryByNum(Integer number) {
        return findOne(By.xpath("//td[@class='displayField'][" + number + "]/label/a"), DEFAULT_WAIT).getText();
    }
}

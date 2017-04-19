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
 * Created by v-vyulun on 7/16/2014.
 */

public class C3hotelReservationSearchResultsPage extends ToolsAbstractPage {

    public C3hotelReservationSearchResultsPage(WebDriver webDriver) {
        super(webDriver, By.xpath("//*[text()='Hotel Reservation Search Results']"));
    }

    public void selectSearchResultByNum(Integer num) {
        findOne(By.xpath("//td[@class='displayField'][" + num + "]/label/a"), DEFAULT_WAIT).click();
    }

    public String getItineraryByNum(int num) {
        return findOne(By.xpath("//td[@class='displayField'][" + num + "]/label/a"), DEFAULT_WAIT)
                .getText().replace(" (H)", "");
    }
}


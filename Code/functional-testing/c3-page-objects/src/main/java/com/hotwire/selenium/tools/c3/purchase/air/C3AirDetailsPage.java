/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.air;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-ozelenov on 4/1/2014.
 * Simplified C3 Air Details Page
 */
public class C3AirDetailsPage extends ToolsAbstractPage {

    private final String startDate  = "div.outbound div.directionDetails strong";
    private final String endDate = "div.inbound div.directionDetails strong";
    private final String origCity = "div#tripTotalTable div.pb10";
    private final String destCity = "div#tripTotalTable div.pb10";
    private final String passengers = "//div[@id='tripTotalTable']//table[2]//tr[3]//td[2]";

    public C3AirDetailsPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("form[action*='/air/billing.jsp']"));
    }

    public void bookNow() {
        findOne("button.continueBig, button.continue", DEFAULT_WAIT).click();
    }

    public String getStartDate() {
        return findOne(startDate).getText();
    }

    public String getEndDate() {
        return findOne(endDate).getText();
    }

    public String getOrigCity() {
        try {
            return findOne(origCity).getText().split("to")[0].trim();
        }
        catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getDestCity() {
        try {
            return findOne(destCity).getText().split("to")[1].trim();
        }
        catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getPassengers() {
        return findOne(By.xpath(passengers)).getText();
    }
}

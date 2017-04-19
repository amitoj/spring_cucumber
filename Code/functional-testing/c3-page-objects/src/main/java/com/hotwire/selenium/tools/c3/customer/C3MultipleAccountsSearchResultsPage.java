/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.customer;



import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Created by v-bshukaylo on 9/15/2014.
 * page appears when we search for a customer by First/Last name
 * and there are more than one result
 */
public class C3MultipleAccountsSearchResultsPage extends ToolsAbstractPage {
    public C3MultipleAccountsSearchResultsPage(WebDriver webdriver) {
        super(webdriver, By.className("displayIterationPadding"));
    }

    public void selectPastPurchasesForFirstResults(String vertical) {
        String xpath;
        switch (vertical) {
            case "air":
                xpath = "//tr[2]/td[6]/a[1]";
                break;
            case "hotel":
                xpath = "//tr[2]/td[6]/a[2]";
                break;
            case "car":
                xpath = "//tr[2]/td[6]/a[3]";
                break;
            default:
                xpath = "";
                break;
        }
        findOne(By.xpath(xpath), DEFAULT_WAIT).click();
        verifyTextOnPage("View Past Bookings");
    }

}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * User: v-abudyak
 * Date: 6/17/13
 * Time: 3:37 AM
 * C3 Search Results Page
 */
public class C3SearchResultPage extends ToolsAbstractPage {
    private static final String RESULTS_HEADER = "td#displayPrimaryHeaderBar";
    private static final String BOOKING_LINK = "//td[@class='activeIteratedElement']//a[contains(text(), 'Hotel')]";

    public C3SearchResultPage(WebDriver webDriver) {
        super(webDriver, By.className("displayIterationPadding"));
    }

    public boolean isResultsPresent() {
        return isElementDisplayed(RESULTS_HEADER);
    }

    public String getResultsHeaderText() {
        return findOne(RESULTS_HEADER).getText();
    }

    public Integer countOfSearchResults() {
        List<WebElement> we = findMany(By.xpath(BOOKING_LINK));
        return we.size();
    }

    public void selectSomeCustomer(int number) {
        List<WebElement> we = findMany(By.xpath(BOOKING_LINK));
        we.get(number).click();
    }
}

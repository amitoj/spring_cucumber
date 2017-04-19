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

public class C3HotelTransactionsForReviewPage extends ToolsAbstractPage {
    public C3HotelTransactionsForReviewPage(WebDriver webDriver) {
        super(webDriver, By.xpath("//td[text()='The following are the Hotel transactions pending review.']"));
    }



    public void selectNextTransactionsForReview(Integer count) {
        if (count != 0) {
            count += 3;
        }
        for (int i = 3; i < count; i++) {
            checkBox(By.xpath("//tr[" + i + "]//input[@name [contains(., 'passOnReview')]]"));
        }
        findOne(By.name("updateButton"), DEFAULT_WAIT).click();
    }
}

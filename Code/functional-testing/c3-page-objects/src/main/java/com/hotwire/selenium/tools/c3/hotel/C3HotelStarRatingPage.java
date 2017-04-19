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

/**
 * Page Object
 */
public class C3HotelStarRatingPage extends ToolsAbstractPage {
    public C3HotelStarRatingPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div#hotelRatingsPopup"));
    }

    public String getHotwireStarRating() {
        return findOne("span#detHotwireRate").getText();
    }

    public String getExpediaStarRating() {
        return findOne("span#detExpediaRate").getText();
    }

    public String getLastRatingChange() {
        return findOne("span#detLastRateChangeDate").getText();
    }

    public String getRatingChange() {
        return findOne("span#detRatingChange").getText();
    }

    public String getHotelName() {
        return findOne("div#benchmarkHotelName").getText().replaceFirst("\\(.*\\)", "");
    }

    public String getBenchmarkHotelID() {
        return findOne("span.benchmarkHotelId", EXTRA_WAIT).getText().replace("(", "").replace(")", "");
    }

    public void verifySurveyFragment() {
        isElementDisplayed(By.id("ratingCurrentSection"));
    }

    public void verifyRatingChangeFragment() {
        isElementDisplayed(By.id("ratingAtTimeOfChangeSection"));
    }
}

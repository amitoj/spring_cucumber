/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Sem landing page, looks like secret hot rates page but has different
 * css classes and hidden marketing params like bid, sid, etc.
 */
public class SemLandingPage extends AbstractRowPage {

    @FindBy(css = "sem-page-title")
    private WebElement cityName;

    public SemLandingPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.sem-page"));
    }

    public String getCityName() {
        return cityName.getText();
    }

    public SemPageFareFinder getFareFinder() {
        return new SemPageFareFinder(getWebDriver());
    }

}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.index;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object that represents the desktop full site air index page
 *
 * @author prbhat
 */
public class AirIndexPage extends AbstractPageObject {

    @FindBy(css = ".universalDealsEngineModule .dealsTable tr td.price")
    private List<WebElement> airDeals;

    public AirIndexPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.air.index", "tile.hotwire.air"});
    }

    public boolean isAirDealsEmpty() {
        return airDeals.isEmpty();
    }

    public void selectFirstDeal() {
        airDeals.get(0).click();
    }

    public boolean isMesoBannersVisible() {
        WebElement mesoLeft;
        WebElement mesoRight;
        try {
            mesoLeft = getWebDriver().findElement(By.id("LEFT1"));
            mesoRight = getWebDriver().findElement(By.id("RIGHT1"));
        }
        catch (NoSuchElementException e) {
            return false;
        }

        return mesoLeft.getAttribute("data-displayed").equals("true") ||
                mesoLeft.getAttribute("data-loaded").equals("true") ||
                mesoRight.getAttribute("data-displayed").equals("true") ||
                mesoRight.getAttribute("data-loaded").equals("true");

    }
}



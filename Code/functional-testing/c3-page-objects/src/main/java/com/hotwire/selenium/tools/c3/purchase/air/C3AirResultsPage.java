/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.air;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import com.hotwire.selenium.tools.c3.purchase.ResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;




/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/3/14
 * Time: 12:59 AM
 * Simplified C3 Air Results page
 */
public class C3AirResultsPage extends ToolsAbstractPage implements ResultsPage {
    private static final int MAX_ATTEMPTS = 5;
    private final String firstResult = "form.priceBody a.btn";
    private int attempts;
    private final String changeSearchPopupLayer = "div#changeSearchPopupLayer";
    private final String openChangeSearch = "a.changeSearchOpener";
    private final String closeChangeSearch = "a.container-close";
    private final String startDate  = "startDate";
    private final String endDate = "endDate";
    private final String origCity = "origCity";
    private final String destCity = "destinationCity";
    private final String passengers = "noOfTickets";

    public C3AirResultsPage(WebDriver webdriver) {
        super(webdriver, By.id("tileName-results"));
         // , "tiles-def.air.results"
    }

    public void verifyResults() {
        findOne(By.xpath(firstResult), PAGE_WAIT);
    }

    public void verifyChangeSearchPopupLayer() {
        findOne(By.cssSelector(changeSearchPopupLayer), PAGE_WAIT);
    }

    /**
     * Recursion is using for handling PriceChange logic on results page
     */
    public void selectFirstResult() {
        findOne(firstResult, PAGE_WAIT).click();
        if (isPriceChangeDisplayed() && attempts < MAX_ATTEMPTS) {
            attempts++;
            selectFirstResult();
        }
    }

    private boolean isPriceChangeDisplayed() {
        try {
            findOne("div.PriceChangeComp", DEFAULT_WAIT);
            logger.info("Found price change comp. Retry. Attempts left: " + attempts);
            return true;
        }
        catch (TimeoutException | NoSuchElementException | ElementNotVisibleException e) {
            return false;
        }
    }

    public String getTypeOfFirstResult() {
        if (findOne(firstResult).getAttribute("id").toString().toLowerCase().contains("opaque")) {
            return "opaque";
        }
        else {
            return "retail";
        }
    }

    public String getReferenceNumber() {
        return findOne(".referenceNumber>p", PAGE_WAIT).getText().replace("-", "");
    }

    public void openChangeSearchPopup() {
        findOne(openChangeSearch).click();
    }

    public void closeChangeSearchPopup() {
        findOne(closeChangeSearch).click();
    }

    public String getOrigCity() {
        return findOne(By.name(origCity)).getAttribute("value");
    }

    public String getDestCity() {
        return findOne(By.name(destCity)).getAttribute("value");
    }

    public String getStartDate() {
        return findOne(By.name(startDate)).getAttribute("value");
    }

    public String getEndDate() {
        return findOne(By.name(endDate)).getAttribute("value");
    }

    public String getPassengers() {
        return new Select(findOne(By.name(passengers))).getFirstSelectedOption().getText();
    }
}

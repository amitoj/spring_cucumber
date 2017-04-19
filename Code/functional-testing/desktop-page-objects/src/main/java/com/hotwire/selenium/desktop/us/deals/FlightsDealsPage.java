/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.deals;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 *  FlightsDealsPage
 */
public class FlightsDealsPage extends AbstractDealsPage {

    private static final String DISCOUNT_INTL_FLIGHTS = ".//a[contains(text(), 'Discount international flights')]";
    private static final String INTL_FLIGHT_DEALS_HEADER = ".//h3[contains(text()," +
            " 'View flights by international destination')]";

    @FindBy (xpath = DISCOUNT_INTL_FLIGHTS)
    WebElement discountIntlFlights;


    public FlightsDealsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getDiscountIntlFlightsLink() {
        return discountIntlFlights;
    }

    public boolean isINTLFlightDealsHeaderExists() {
        try {
            getWebDriver().findElement(By.xpath(INTL_FLIGHT_DEALS_HEADER)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
        return true;

    }

    @Override
    public void verifyPage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

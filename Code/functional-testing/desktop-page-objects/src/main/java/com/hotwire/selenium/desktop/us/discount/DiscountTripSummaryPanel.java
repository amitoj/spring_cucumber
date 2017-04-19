/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.discount;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * */
public class DiscountTripSummaryPanel extends AbstractUSPage {

    public DiscountTripSummaryPanel(final WebDriver webdriver) {
        super(webdriver, By.cssSelector(".tripDetails, [id='tileName-A1'], div.hotelTripSummary"));
    }

    public Boolean hasDiscount() {
        boolean oldDisplayed = false;
        try {
            WebElement old = getWebDriver().findElement(By
                    .xpath("id('tripTotalTable')//*[@class='detail' and text()='Discount:']"));
            oldDisplayed = old.isDisplayed();
        }
        catch (NoSuchElementException e) {
            // Do nothing use initialized value.
        }
        boolean nextDisplayed = false;
        try {
            WebElement next = getWebDriver().findElement(By.cssSelector("#priceDetails .discountText, .discount"));
            nextDisplayed = next.isDisplayed();
        }
        catch (NoSuchElementException e) {
            // Do nothing use initialized value.
        }
        logger.info("Discount Displayed for OLD billing: " + oldDisplayed +
                ". Discount Displayed for NEW Details-Billing: " + nextDisplayed);
        return oldDisplayed || nextDisplayed;
    }
}

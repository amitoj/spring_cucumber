/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.discount;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Discount layer on hotel landing page
 */
public class DiscountLandingPage extends AbstractPageObject {

    @FindBy(css = "#discountCodeLayer-panel")
    private WebElement discountLayer;

    @FindBy(css = "#discountCodeLayer-panel .priceText")
    private WebElement discountPriceText;

    public DiscountLandingPage(final WebDriver webdriver) {
        super(webdriver, By.id("tileName-discountCodeLayer"));
    }

    public Boolean hasDiscount() {
        try {
            discountLayer.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getDiscountAmount() {
        try {
            return discountPriceText.getText();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean getIsDiscountPercent() {
        String discountType = discountLayer.findElement(By.cssSelector(".priceText")).getText();
        return discountType.contains("%");
    }

    public String getDiscountBannerText() {
        return discountLayer.findElement(By.cssSelector("div.discountFooter")).getText();
    }

    public String getDiscountRule() {
        return discountLayer.findElement(By.cssSelector(".discountRule")).getText();
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.onepage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.po.AbstractPageObject;

/**
 * @author vjong
 *
 */
public class HotelBillingPromotionFragment extends AbstractPageObject {
    private static final By CONTAINER = By.cssSelector("div.promoModule");
    private static final By SUCCESS_ERROR_CONTAINER = By.cssSelector("div#promoErrorSuccess .msgBox .msgBoxBody");

    @FindBy(css = "div.promoContent input#paymentInfoModel\\.discountCode")
    WebElement discountCodeTextBox;

    @FindBy(css = "div.promoContent button.btn")
    WebElement applyDiscountCodeButton;

    public HotelBillingPromotionFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.promoModule"));
    }

    public boolean isApplyPromotionStatusMessageDisplayed() {
        try {
            return getWebDriver().findElement(SUCCESS_ERROR_CONTAINER).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getApplyPromotionMessages() {
        return getWebDriver().findElement(SUCCESS_ERROR_CONTAINER).getText();
    }

    public static boolean isPromotionFragmentDisplayed(final WebDriver webdriver) {
        try {
            return webdriver.findElement(CONTAINER).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void applyPromotionCode(String promotionCode) {
        discountCodeTextBox.sendKeys(promotionCode);
        applyDiscountCodeButton.click();
    }
}

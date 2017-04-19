/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.billing;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by IntelliJ IDEA.
 * User: v-vyulun
 * Date: 4/6/12
 * Time: 7:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class PurchaseReviewFragment extends MobileAbstractPage {
    private static final String COUPON_CODE_TOGGLE = "div[id='couponBody'] form a";
    private static final String COUPON_CODE_INPUT =
        "div[id='couponBody'] form div[id='couponCodeForm'] input[id='discountCode']";
    private static final String COUPON_CODE_SUBMIT_BUTTON =
        "div[id='couponBody'] form div[id='couponCodeForm'] button.submit";
    private static final String COUPON_CODE_MESSAGE_BOX = "div[id='couponBody'] div.msgBoxBody";
    private static final String TRIP_SUMMARY_DISCOUNT_AMOUNT = "div[id='tripSummary'] span[data-bdd='hwDiscount']";

    @FindBy(css = "div[id='tripSummary'] span[data-bdd='total']")
    private WebElement totalPrice;

    @FindBy(css = "div.confirm button[id='bookAndConfirmBtn']")
    private WebElement btnPurchase;

    public PurchaseReviewFragment(WebDriver webDriver, String tileName) {
        super(webDriver, tileName);
    }

    public PurchaseReviewFragment(WebDriver webDriver, String[] tileNames) {
        super(webDriver, tileNames);
    }

    public void openCouponCodeForm() {
        if (!isCouponCodeInputDisplayed()) {
            getWebDriver().findElement(By.cssSelector(COUPON_CODE_TOGGLE)).click();
        }
    }

    public boolean isCouponCodeInputDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(COUPON_CODE_INPUT)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCouponCodeModuleDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(COUPON_CODE_TOGGLE)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void applyCouponCode(String code) {
        openCouponCodeForm();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(COUPON_CODE_INPUT)));
        getWebDriver().findElement(By.cssSelector(COUPON_CODE_INPUT)).sendKeys(code);
        clickCouponCodeSubmit();
        new WebDriverWait(getWebDriver(), 5).until(new IsAjaxDone());
    }

    public void clickCouponCodeSubmit() {
        getWebDriver().findElement(By.cssSelector(COUPON_CODE_SUBMIT_BUTTON)).click();
    }

    public String getApplyCouponCodeStatusMessage() {
        return getWebDriver().findElement(By.cssSelector(COUPON_CODE_MESSAGE_BOX)).getText();
    }

    public Float getTotalPrice() {
        return new Float(totalPrice.getText().replaceAll("[^\\d.,]", ""));
    }

    public float getDiscountAmount() {
        return new Float(getWebDriver().findElement(By.cssSelector(TRIP_SUMMARY_DISCOUNT_AMOUNT)).getText()
                                       .replaceAll("[^\\d.,]", ""));
    }

    public boolean isDiscountAmountDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(TRIP_SUMMARY_DISCOUNT_AMOUNT)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void continuePanel() {
        btnPurchase.click();
    }

}

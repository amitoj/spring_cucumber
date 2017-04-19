/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.view.ViewIds;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;

/**
 * Created by IntelliJ IDEA. User: v-vyulun Date: 4/6/12 Time: 7:37 PM
 */
public class MobileBillingPg extends MobileAbstractPage {
    private static final String ACCESSIBILITY_OPTIONS = "#accessibilityOptions input[type='checkbox']";
    private static final String ACCESSIBILITY_OPTIONS_TEXTS = "#accessibilityOptions label";
    private static final String HOT_DOLLARS_ID = "useHotdollars";

    @FindBy(xpath = "//*[@data-bdd='hotel-discount-banner']")
    private WebElement discountBanner;

    @FindBy(css = "#accessibility .arrowUp, #accessibility .arrowDown")
    private WebElement accessibilityNeeds;

    @FindBy(css = "#accessibility span")
    private WebElement accessibilityArrow;

    @FindBy(css = ACCESSIBILITY_OPTIONS)
    private List<WebElement> accessibilityCheckboxes;

    @FindBy(css = ACCESSIBILITY_OPTIONS_TEXTS)
    private List<WebElement> accessibilityCheckboxesTexts;

    @FindBy(id = HOT_DOLLARS_ID)
    private WebElement hotDollars;

    public MobileBillingPg(WebDriver driver) {
        super(driver); // , "tile.hotel.billing.traveler-info");
    }

    public MobileBillingPg(WebDriver driver, String container) {
        super(driver, container);
    }

    public TravelerInfoGuestFragment fillGuestTravelerInfo() {
        return new TravelerInfoGuestFragment(getWebDriver(), ViewIds.TILE_HOTEL_BILLING_TRAVELER_INFO);
    }

    public  TravelerInfoUserFragment fillUserTravelerInfo() {
        return new TravelerInfoUserFragment(getWebDriver(), ViewIds.TILE_HOTEL_BILLING_ONE_PAGE_CHECKOUT);
    }

    public AbstractPaymentMethodFragment fillPaymentMethod(boolean isSignedInUser) {

        if (isSignedInUser) {
            return new PaymentMethodFragmentUser(getWebDriver(), ViewIds.TILE_HOTEL_BILLING_ONE_PAGE_CHECKOUT);

        }
        else {
            return new PaymentMethodFragmentGuest(getWebDriver(), ViewIds.TILE_BILLING_PAYMENT_INFO);
        }
    }

    public String book(boolean isSignedInUser) {
        return book(isSignedInUser, null);
    }

    public String book(boolean isSignedInUser, String discount) {
        String total;
        try {
            new AbstractPaymentMethodFragment(getWebDriver(), "tile.billing.payment-info").clickContinueButton();
            String rawTotal = getWebDriver().findElement(By.cssSelector(".total .value")).getText();
            total = rawTotal.replaceAll("\\s+|::before|\"", "");
        }
        catch (RuntimeException e) {
            List<WebElement> elements = getWebDriver().findElements(By.cssSelector("div[id='costBreakdown'] div"));
            total = elements.get(elements.size() - 1).getText().split("\\s+|\n|<br>")[1].trim();
            new AbstractPaymentMethodFragment(getWebDriver(),
                "tile.hotel.billing.one-page-checkout").clickBookAndConfirmButton();
        }
        String tileId = ViewIds.TILE_HOTEL_BILLING_REVIEW_DETAILS;
        if (isSignedInUser) {
            tileId = ViewIds.TILE_HOTEL_BILLING_ONE_PAGE_CHECKOUT;
        }
        try {
            if (!StringUtils.isEmpty(discount)) {
                PurchaseReviewFragment review = new PurchaseReviewFragment(getWebDriver(), tileId);
                review.applyCouponCode(discount);
                String rawTotal = getWebDriver().findElement(By.cssSelector(".total .value")).getText();
                total = rawTotal.replaceAll("\\s+|::before|\"", "");
            }
            new PurchaseReviewFragment(getWebDriver(), tileId).continuePanel();
        }
        catch (RuntimeException e) {
            logger.info("No review fragment on account of registered user has done purchase\n" + e.getCause());
        }
        return total;
    }

    public boolean verifyDiscountBannerOnBillingPage() {
        return discountBanner.isDisplayed() ? true : false;
    }

    public void expandAccessibilityNeeds() {
        accessibilityNeeds.click();
        new WebDriverWait(getWebDriver(), 5)
            .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(ACCESSIBILITY_OPTIONS), 5));
    }
    public void collapseAccessibilityNeeds() {
        accessibilityNeeds.click();
        new WebDriverWait(getWebDriver(), 5)
            .until(new InvisibilityOf(By.cssSelector(ACCESSIBILITY_OPTIONS)));
    }

    public String getAccessibilityArrowConsist() {
        return accessibilityArrow.getAttribute("class");
    }

    public List<String> getAccessibilityTexts() {
        List<String> texts = new ArrayList<String>();
        for (WebElement element : accessibilityCheckboxesTexts) {
            texts.add(element.getText());
        }
        return texts;
    }

    public boolean isAccessibilityOptionsDisplayed() {
        for (WebElement element : accessibilityCheckboxes) {
            try {
                if (!element.isDisplayed()) {
                    return false;
                }
            }
            catch (NoSuchElementException e) {
                return false;
            }
        }
        return true;
    }

    public void clickHotDollars() {
        hotDollars.click();
    }
}

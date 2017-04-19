/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.onepage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hotwire.selenium.desktop.common.billing.HotelTripSummaryFragment;
import com.hotwire.selenium.desktop.us.billing.AbstractBillingPage;
import com.hotwire.selenium.desktop.us.billing.AdditionalFeatures;
import com.hotwire.selenium.desktop.us.billing.SelectPaymentMethodFragment;
import com.hotwire.util.webdriver.functions.IsAjaxDone;

/**
 * Holds basic hotel one page billing elements; other elements are in page fragments.
 *
 * @author dmuthuswamy
 * @since 2010.11
 */
public class HotelBillingOnePage extends AbstractBillingPage {

    private static final String SAVE_BILLING_CONTAINER = ".saveCardInfo";

    public HotelBillingOnePage(WebDriver driver) {
        super(driver);

        //...wait until Ajax is silent
        new com.hotwire.util.webdriver.functions.Wait<Boolean>(new IsAjaxDone())
            .maxWait(30).apply(getWebDriver());
    }

    public WebElement getBookButton() {
        return new HotelPurchaseReviewFragment(getWebDriver()).getBookButton();
    }

    @Override
    public void book() {
        new HotelPurchaseReviewFragment(getWebDriver()).continuePanel();
    }

    public HotelTravelerInfoFragment getTravelerInfoFragment() {
        return new HotelTravelerInfoFragment(getWebDriver());
    }

    public HotelTravelerInfoFragment fillTravelerInfo() {
        return new HotelTravelerInfoFragment(getWebDriver());
    }

    @Override
    public HotelVmeFragment getVmePaymentFragment() {
        new SelectPaymentMethodFragment(getWebDriver()).selectVme();
        return new HotelVmeFragment(getWebDriver());
    }

    @Override
    public HotelPayPalFragment fillPayPalPaymentMethod() {
        new SelectPaymentMethodFragment(getWebDriver()).selectPayPal();
        return new HotelPayPalFragment(getWebDriver());
    }

    public HotelCreditCardFragment fillCreditCard() {
        return new HotelCreditCardFragment(getWebDriver());
    }

    @Override
    public void selectAsGuest() {
        // No explicit action is required to purchase as a guest.
    }

    @Override
    public void selectAsUser(String sEmail, String sPassword) {
        HotelTravelerInfoFragment fragment = getTravelerInfoFragment();
        fragment.signInAsUser(sEmail, sPassword);
    }

    @Override
    public void submitPanel(WebElement panelContinueBtn) {

    }

    @Override
    public AdditionalFeatures fillAdditionalFeatures() {
        // Currently 1pg billing only supports trip insurance.
        return new HotelTripInsuranceFragment(getWebDriver());
    }

    public void bookPaypal() {
        new HotelPurchaseReviewFragment(getWebDriver())
            .setAgreeWithTerms(true)
            .agreeWithTerms()
            .getBookButton().click();
    }

    public List<WebElement> getAllPaymentMethodElements() {
        return getWebDriver().findElements(By.cssSelector(".selectPaymentMethod .cardMethod input.radioBtn"));
    }

    public String getSelectedPaymentMethodIDValue() {
        for (WebElement element : getWebDriver().findElements(By.cssSelector(
                ".selectPaymentMethod .cardMethod input.radioBtn"))) {
            if (element.isSelected()) {
                return element.getAttribute("id").trim();
            }
        }
        throw new RuntimeException("Could not find the payment method that was selected.");
    }

    public String getConfirmPaymentText() {
        return getWebDriver().findElement(By.cssSelector("div[id='tileName-termsAndConditions'] .confirmPayment-text"))
            .getText();
    }

    public HotelTripSummaryFragment getTripSummaryFragment() {
        return new HotelTripSummaryFragment(getWebDriver());
    }

    public void clickBackToDetailsLink() {
        getWebDriver().findElement(By.cssSelector(".backDetailsLink a")).click();
    }

    public HotelBillingPromotionFragment getHotelBillingPromotionFragment() {
        return new HotelBillingPromotionFragment(getWebDriver());
    }

    public boolean isHotelBillingPromotionFragmentDisplayed() {
        return HotelBillingPromotionFragment.isPromotionFragmentDisplayed(getWebDriver());
    }

    public boolean isSavedPaymentPresent() {
        try {
            return getWebDriver().findElement(By.cssSelector(".savedPaymentMethod")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPaymentMethodsFragmentPresent() {
        try {
            return getWebDriver().findElement(By.id("selectPaymentMethod")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }

    }


    public boolean isSaveBillingInfoModuleDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(SAVE_BILLING_CONTAINER)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

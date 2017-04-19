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
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.billing.AdditionalFeatures;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * This is a page object for trip insurance panel on hotel one page billing
 *
 * @author aguthrie
 *
 */
public class HotelTripInsuranceFragment extends AbstractUSPage implements AdditionalFeatures {

    @FindBy(id = "insurancePurchaseCheckTrue")
    private WebElement yesRadioButton;

    @FindBy(id = "insurancePurchaseCheckFalse")
    private WebElement noRadioButton;

    public HotelTripInsuranceFragment(WebDriver webDriver) {
        super(webDriver, By.id("tripInsurance"));
    }

    @Override
    public AdditionalFeatures withInsurance(boolean wantTripInsurance) {

        // Wait for the updating layer to disappear
        new WebDriverWait(getWebDriver(), 15)
            .until(ExpectedConditions.invisibilityOfElementLocated(By.className("loadingImage")));
        // Get current state of payment method fragment selection.
        String selectedIDValue = new HotelBillingOnePage(getWebDriver()).getSelectedPaymentMethodIDValue();
        logger.info("Current selected payment method ID: " + selectedIDValue);
        try {
            if (wantTripInsurance) {
                logger.info("Select insurance: YES");
                yesRadioButton.click();
            }
            else {
                logger.info("Select insurance: NO");
                noRadioButton.click();
            }
            // Wait until Ajax is silent
            new Wait<Boolean>(new IsAjaxDone()).maxWait(30).apply(getWebDriver());

            // Check if selected payment has changed.
            String current = new HotelBillingOnePage(getWebDriver()).getSelectedPaymentMethodIDValue();
            logger.info("Before selecting insurance: " + selectedIDValue + " - After selecting insurance: " + current);
            if (!current.equals(selectedIDValue)) {
                logger.info("Reselecting payment type due to insurance selection.");
                getWebDriver().findElement(By.id(selectedIDValue)).click();
            }
            new HotelBillingOnePage(getWebDriver());
        }
        catch (NoSuchElementException e) {
            logger.warn("Travel Insurance panel didn't load");
        }

        return this;
    }

    @Override
    public void continuePanel() {
        // No op
        logger.info("Hotel trip insurance was filled in..");
    }

    public String getTripInsuranceCost() {
        return getWebDriver().findElement(By.cssSelector(
            ".tripInsuranceModule .tripProtection .normalize span, " +
            ".tripInsuranceModule .tripProtection .benefits span")).getText().replaceAll("[^0-9.]", "");
    }

    public boolean isTripInsuranceUnavailable() {
        return getWebDriver().findElement(By.id("tripInsurance")).getText().contains("Trip Insurance is not available");
    }
}

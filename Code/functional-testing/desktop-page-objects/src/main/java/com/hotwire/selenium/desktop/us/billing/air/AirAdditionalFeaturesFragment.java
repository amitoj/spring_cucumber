/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.air;

import com.hotwire.selenium.desktop.us.billing.AdditionalFeatures;
import com.hotwire.selenium.desktop.us.billing.AdditionalFeaturesFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author vjong
 *
 */
public class AirAdditionalFeaturesFragment extends AdditionalFeaturesFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirAdditionalFeaturesFragment.class);

    private static final String OLD_CAR_ADDON_INSURANCE_RADIOS =
            "input[name='orderAddOnForm\\.isRentalCarProtectionInsuranceRequested']";
    private static final String CSS_INSURANCE_FALSE = "input[id='insurancePurchaseCheckFalse']";

    @FindBy(css = "input[id='insurancePurchaseCheckTrue']")
    private List<WebElement> yesInsuranceRadios;

    @FindBy(css = CSS_INSURANCE_FALSE)
    private List<WebElement> noInsuranceRadios;

    @FindBy(css = OLD_CAR_ADDON_INSURANCE_RADIOS)
    private List<WebElement> oldCarRentalProtectionRadios;

    public AirAdditionalFeaturesFragment(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(CSS_INSURANCE_FALSE + "," + OLD_CAR_ADDON_INSURANCE_RADIOS);
    }

    @Override
    public AdditionalFeatures withInsurance(boolean insurance) {
        // Some flows bypass the insurance panel but will still allow payment to
        // continue. For instance when changing
        // the currency to non-USD, the user is not allowed to purchase
        // insurance. In this case, payment can still
        // continue.
        if (tripInsuranceRadioFields.size() > 0) {
            // Since this panel is animated, make sure radio button location is stable before attempting click. Just
            // check the first item in what would be a list if using findElements. Assume other elements are then
            // stable.
            if (insurance) {
                // Handle all yes insurance elements on the page.
                for (WebElement element : yesInsuranceRadios) {
                    LOGGER.info("Select trip insurance..");
                    element.click();
                }
                // Check if this is the old car rental insurance addon.
                if (!oldCarRentalProtectionRadios.isEmpty()) {
                    // We have old car add on insurance section.
                    LOGGER.info("Got old car addon yes insurance element.");
                    oldCarRentalProtectionRadios.get(0).click();
                }
            }
            else {
                // Handle all no insurance elements on the page.
                for (WebElement element : noInsuranceRadios) {
                    LOGGER.info("Do not select trip insurance..");
                    element.click();
                }
                // Check if this is the old car rental insurance addon.
                if (!oldCarRentalProtectionRadios.isEmpty()) {
                    LOGGER.info("Got old car addon no insurance element.");
                    // We have old car add on insurance section.
                    oldCarRentalProtectionRadios.get(1).click();
                }
            }
        }
        return this;
    }
}

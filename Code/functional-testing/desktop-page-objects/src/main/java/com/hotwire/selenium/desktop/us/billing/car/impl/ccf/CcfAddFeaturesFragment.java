/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl.ccf;

import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcAddFeaturesFragment;
import com.hotwire.testing.UnimplementedTestException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 5:23 AM
 */
public class CcfAddFeaturesFragment extends AcAddFeaturesFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(CcfAddFeaturesFragment.class);

    {
        insuranceDynamic = ".rentalCarProtection, .tripProtection";
        insuranceStatic = "div.carSinglePageStaticInsurance.innerContent";
        insuranceUnavailable = "div#carSpaInsurance h2";
        insuranceUnavailableText = "Car Damage Protection is unavailable";
        insuranceRadioButtonsStr = insuranceDynamic + ", " + insuranceStatic;
        insuranceUnavailable = "#unavailable";
    }

    public CcfAddFeaturesFragment(WebDriver webDriver) {
        super(webDriver, By.id("carSpaInsurance"));
    }

    @Override
    public void continuePanel() {
        // No action
    }

    @Override
    public void requestInsurance(boolean reqInsurance) {

        if (isInsuranceUnavailable()) {
            if (reqInsurance) {
                throw new UnimplementedTestException("Insurance was required but options didn't appear");
            }
            return;
        }

        if (reqInsurance) {
            getTripInsuranceRadioFields().get(0).click();
            LOGGER.info("Insurance was selected: YES");
        }
        else {
            getTripInsuranceRadioFields().get(1).click();
            LOGGER.info("Insurance was selected: NO");
        }
    }

    /*
    * Additional features
    */
    @Override
    public List<WebElement> getTripInsuranceRadioFields() {
        new WebDriverWait(getWebDriver(), 10)
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath(".//*[@id='carSpaInsurance']//input[@type='radio']")));
        return getWebDriver().findElements(By.xpath(".//*[@id='carSpaInsurance']//input[@type='radio']"));
    }
}

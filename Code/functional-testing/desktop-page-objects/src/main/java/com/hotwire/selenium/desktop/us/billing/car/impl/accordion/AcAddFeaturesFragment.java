/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl.accordion;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarAdditionalFeatures;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.po.AbstractPageObject;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 5:23 AM
 */
public class AcAddFeaturesFragment extends AbstractPageObject implements CarAdditionalFeatures {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcPaymentMethodFragment.class);

    private static final String INSURANCE_BUTTONS =
        "input[id='insurancePurchaseCheckTrue'], input[id='yesBtn'], " +
        "input[id='insurancePurchaseCheckFalse'], input[id='noBtn']";

    protected String identifier = "";
    protected String insuranceRadioButtonsStr = INSURANCE_BUTTONS;
    protected String insuranceDynamic = "";
    protected String insuranceStatic = "";
    protected String insuranceUnavailable = ".protectionWrapper .unavailable";
    protected String insuranceUnavailableText = "";

    @FindBy(css = "label.normalize > span")
    private WebElement insuranceCost;

    @FindBy(css = "#insurancePurchaseCheckTrue, #yesBtn")
    private WebElement selectInsuranceOption;

    @FindBy(css = "#insurancePurchaseCheckFalse, #noBtn")
    private WebElement skipInsuranceOption;

    @FindBy(css = INSURANCE_BUTTONS)
    private List<WebElement> insuranceRadioButtons;

    @FindBy(name = "btnOrderAddOn")
    private WebElement btnOrderAddOn;

    protected AcAddFeaturesFragment(WebDriver webDriver, By container) {
        super(webDriver, container, new String[]{CarBillingPageProvider.TILES});
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector("#insurancePurchaseCheckTrue, #yesBtn, #protectionAndPayment h1.unavailable");
    }

    @Override
    protected int getTimeout() {
        return 60;
    }

    /**
     * Additional features
     */
    @Override
    public List<WebElement> getTripInsuranceRadioFields() {
        return getWebDriver().findElements(By.cssSelector(INSURANCE_BUTTONS));
    }

    @Override
    public void requestInsurance(boolean reqInsurance) {
        // Insurance unavailable header showed up. Ignore and attempt to proceed with purchase.
        if (isInsuranceUnavailable()) {
            return;
        }

        if (reqInsurance) {
            selectInsuranceOption.click();
            LOGGER.info("Insurance was selected: YES");
        }
        else if (!reqInsurance) {
            skipInsuranceOption.click();
            LOGGER.info("Insurance was selected: NO");
        }
        LOGGER.info("Insurance was processed successfully..");
        new WebDriverWait(getWebDriver(), 10).until(new IsAjaxDone());
    }

    @Override
    public void continuePanel() {
        btnOrderAddOn.click();
    }

    /**
     * Detecting insurance
     * @return type of insurance block on billing page
     */
    @Override
    public InsuranceType getInsuranceType() {
        if (!getWebDriver().findElements(By.cssSelector(insuranceDynamic)).isEmpty()) {
            return InsuranceType.DYNAMIC;
        }

        if (isInsuranceUnavailable()) {
            return InsuranceType.UNAVAILABLE;
        }

        if (!getWebDriver().findElements(By.cssSelector(insuranceStatic)).isEmpty()) {
            return InsuranceType.STATIC;
        }

        throw new NoSuchElementException("Insurance block wasn't detected..");
    }

    @Override
    public boolean isInsuranceUnavailable() {
        boolean isUnavailable = getWebDriver().findElements(By.cssSelector(insuranceUnavailable)).size() > 0;
        LOGGER.info("Insurance " + (isUnavailable ? "is not " : "is ") + "available.");
        return isUnavailable;
    }

    @Override
    public boolean isInsuranceSelected() {
        return selectInsuranceOption.isSelected();
    }

    @Override
    public WebElement getInsuranceCost() {
        return insuranceCost;
    }
}

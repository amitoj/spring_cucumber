/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing;

import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class provides the methods to access the additional features panel on us
 * billing page.
 *                     FeaturesFragment
 * @author Alok Gupta
 * @since 2012.04
 */
public class AdditionalFeaturesFragment extends AbstractPageObject implements AdditionalFeatures {

    protected static final long DEFAULT_WAIT = 5;
    protected static final String INSURANCE_RADIOS = ".//input[@type='radio']";

    private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalFeaturesFragment.class);

    @FindBy(name = "btnOrderAddOn")
    protected WebElement btnOrderAddOn;

    // Used to determine if we should even click the insurance radio fields
    @FindBy(xpath = INSURANCE_RADIOS)
    protected List<WebElement> tripInsuranceRadioFields;

    @FindBy(xpath = ".//div[@data-jsname='TravelInsuranceInterfaceComp']/h2/strong")
    private WebElement insuranceHeaderOne;

    @FindBy(xpath = ".//div[@data-jsname='TravelInsuranceInterfaceComp']/h2")
    private WebElement insuranceHeaderTwo;

    @FindBy(className = "errorMessages")
    private WebElement errorMessage;

    public AdditionalFeaturesFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector(
            "li[id='tripInsuranceContent'], div[id='tripInsuranceContent'], " +
                    "li[id='billingPanelOtherFeaturesComp'] .content"));
    }


    @Override
    public AdditionalFeatures withInsurance(boolean insurance) {

        if (tripInsuranceRadioFields.size() > 0) {
            // Since this panel is animated, make sure radio button location is stable before attempting click. Just
            // check the first item in what would be a list if using findElements. Assume other elements are then
            // stable.
            new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                    .until(new IsElementLocationStable(getWebDriver(), By.xpath(INSURANCE_RADIOS), 4));
            if (insurance) {
                tripInsuranceRadioFields.get(0).click();
            }
            else {
                tripInsuranceRadioFields.get(1).click();
            }
        }

        return this;
    }

    public boolean isOrderAddOnButtonAvailable() {
        try {
            return btnOrderAddOn.isEnabled() && btnOrderAddOn.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickOrderAddOnButton() {
        btnOrderAddOn.click();
    }

    @Override
    public void continuePanel() {
        btnOrderAddOn.click();
    }

    public boolean continueWithoutSelectingInsurance() {
        boolean checkHeading = true;
        if (getWebDriver().findElements(By.id("billingPanelActivitiesComp")).size() > 0) {
            //has destination services / activities....for now, just click-through on our way to additional features
            new ActivitiesFragment(getWebDriver()).continuePanel();
        }
        String header;
        //there are two versions of insurance panel
        try {
            header = insuranceHeaderOne.getText();
        }
        catch (NoSuchElementException e) {
            header = insuranceHeaderTwo.getText();
        }
        if (!header.contains("Protection") && !header.contains("recommended")) {
            checkHeading = false;
        }
        btnOrderAddOn.click();
        return checkHeading;
    }

    public boolean verifyErrorForInsurance() {
        boolean errorMsg = true;
        try {
            if (!this.errorMessage.isDisplayed()) {
                errorMsg = false;
            }
        }
        catch (NoSuchElementException e) {
            LOGGER.info("Error Message was not displayed");
        }
        return errorMsg;
    }


}

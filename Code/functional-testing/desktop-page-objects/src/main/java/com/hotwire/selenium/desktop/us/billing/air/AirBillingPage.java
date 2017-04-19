/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.air;

import com.hotwire.selenium.desktop.us.billing.AbstractBillingPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Air billing page.
 */
public class AirBillingPage extends AbstractBillingPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirBillingPage.class);

    @FindBy(id = "ageConfirmationCheckbox")
    private WebElement ageConfirmationCheckbox;

    @FindBy(name = "travelerForm.phoneNo")
    private WebElement primaryPhoneNumber;

    @FindBy(name = "travelerForm._NAE_email")
    private WebElement emailId;

    @FindBy(name = "travelerForm._NAE_confirmEmail")
    private WebElement confirmEmailId;

    @FindBy(name = "btnTravelerInfo")
    private WebElement travelerContinueBtn;

    public AirBillingPage(WebDriver webdriver) {
        super(webdriver);
    }

    public AirBillingPage setAge(String age) {
        try {
            if ("25+".equals(age)) {
                ageConfirmationCheckbox.click();
            }
        }
        catch (NoSuchElementException e) {
            LOGGER.info("Driver's age did not appear on page while air purchase without rental car");
        }
        return this;
    }

    public AirBillingPage setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber.clear();
        this.primaryPhoneNumber.sendKeys(primaryPhoneNumber);
        return this;
    }

    public AirBillingPage setEmailId(String emailId) {
        this.emailId.clear();
        this.emailId.sendKeys(emailId);
        this.confirmEmailId.clear();
        this.confirmEmailId.sendKeys(emailId);
        return this;
    }

    public AirAdditionalFeaturesFragment fillAdditionalFeatures() {
        return new AirAdditionalFeaturesFragment(getWebDriver());
    }

    public void submit() {
        submitPanel(travelerContinueBtn);
    }

    public AirCreditCardFragment fillCreditCard() {
        return new AirCreditCardFragment(getWebDriver());
    }

    public WebElement getEmailId() {
        return emailId;
    }

    public WebElement getConfirmEmailId() {
        return confirmEmailId;
    }
}

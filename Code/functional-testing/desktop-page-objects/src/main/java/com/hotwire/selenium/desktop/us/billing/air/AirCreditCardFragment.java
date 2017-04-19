/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.air;

import com.hotwire.selenium.desktop.us.billing.CreditCardInfoFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by v-sshubey on 7/7/2014.
 */
public class AirCreditCardFragment extends CreditCardInfoFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirCreditCardFragment.class);

    @FindBy(xpath = "//div[@id = 'newPaymentMethod']//input[@id = 'defaultPmIdNewCC']")
    private WebElement newCCRadio;

    @FindBy(xpath = "//div[@id = 'savedPaymentMethods']//input[@name = 'paymentForm.defaultPmId']")
    private WebElement savedCCRadio;

    @FindBy(name = "paymentForm.selectedCardType")
    private WebElement ccType;

    @FindBy(name = "paymentForm.cardDesc")
    private WebElement nameForNewSavedCard;

    @FindBy(xpath = "//input[@name='paymentForm.shouldSaveCC_Info' and @value='true']")
    private WebElement saveBillingInfoRadioButton;

    @FindBy(xpath = "//input[@name='paymentForm.shouldSaveCC_Info' and @value='false']")
    private WebElement doNotSaveBillingInfoRadioButton;

    @FindBy(className = "collapsedItem")
    private WebElement saveBillingInfoLink;

    @FindBy(name = "paymentForm._NAE_password")
    private WebElement password;

    @FindBy(name = "paymentForm._NAE_confirmPassword")
    private WebElement confirmPassword;

    public AirCreditCardFragment(WebDriver webDriver) {
        super(webDriver, By.id("billingPanelPaymentInfoComp"));
    }

    public void setPaymentMethodPanel(String creditCardType, String creditCardNumber,
                                      String creditCardExpirationMonth, String creditCardExpirationYear,
                                      String securityCode, String firstName, String lastName, String country,
                                      String billingAddress, String city, String state, String zipCode) {

        withCcType(creditCardType);
        withCcNumber(creditCardNumber);
        withCcExpMonth(creditCardExpirationMonth);
        withCcExpYear(creditCardExpirationYear);
        withSecurityCode(securityCode);
        withFirstName(firstName);
        withLastName(lastName);
        withCountry(country);
        withBillingAddress(billingAddress);
        withCity(city);
        withState(state);
        withZipCode(zipCode);
    }

    public AirCreditCardFragment withCcType(String creditCardType) {
        try {
            if (getWebDriver().findElements(By.name("paymentForm.selectedCardType")).size() > 0) {

                new WebDriverWait(getWebDriver(), 5)
                        .until(ExpectedConditions.visibilityOfElementLocated(By.name("paymentForm.selectedCardType")));

                new Select(getWebDriver().findElement(By.name("paymentForm.selectedCardType")))
                        .selectByVisibleText(creditCardType);
            }
        }
        catch (NoSuchElementException e) {
            LOGGER.info("Type of credit card is not available!");
        }
        return this;
    }

    public AirCreditCardFragment withCcNumber(String creditCardNumber) {
        this.typeNewPaymentCreditCardNumber(creditCardNumber);
        return this;
    }

    public AirCreditCardFragment withCcExpMonth(String creditCardExpMonth) {
        this.choosePaymentExpirationMonth(creditCardExpMonth);
        return this;
    }

    public AirCreditCardFragment withCcExpYear(String creditCardExpYear) {
        this.choosePaymentExpirationYear(creditCardExpYear);
        return this;
    }

    public AirCreditCardFragment withSecurityCode(String securityCode) {
        this.typePaymentSecurityCode(securityCode);
        return this;
    }

    public AirCreditCardFragment withFirstName(String firstName) {
        this.typePaymentFirstName(firstName);
        return this;
    }

    public AirCreditCardFragment withMiddleName(String middleName) {
        this.typePaymentLastName(middleName);
        return this;
    }

    public AirCreditCardFragment withLastName(String lastName) {
        this.typePaymentLastName(lastName);
        return this;
    }

    public AirCreditCardFragment withCountry(String country) {
        this.selectPaymentCountry(country);
        return this;
    }

    public AirCreditCardFragment withBillingAddress(String billingAddress) {
        this.typePaymentStreetAddress(billingAddress);
        return this;
    }

    public AirCreditCardFragment withCity(String city) {
        this.typePaymentCity(city);
        return this;
    }

    public AirCreditCardFragment withState(String state) {
        this.chooseOrEnterPaymentState(state);
        return this;
    }

    public AirCreditCardFragment withZipCode(String zipCode) {
        this.typePaymentZip(zipCode);
        return this;
    }

    public void newOrSavedCCUsing(boolean newOrSaved) {
        // false - new CC
        // true - saved CC if exist

        if (newOrSaved) {
            try {
                savedCCRadio.click();
            }
            catch (NoSuchElementException e) {
                LOGGER.info("Saved cards are absent in account!");
            }
        }
        else {
            try {
                newCCRadio.click();
            }
            catch (NoSuchElementException e) {
                LOGGER.info("Saved cards are absent in account!");
            }
        }
    }

    public void saveBillingInfo(String nameForSavedCard) {
        try {
            saveBillingInfoLink.click();
        }
        catch (NoSuchElementException e) {
            LOGGER.info("Save billing info block is opened");
        }
        saveBillingInfoRadioButton.click();
        sendKeys(nameForNewSavedCard, nameForSavedCard);
    }


    public WebElement getSavedCCRadio() {
        return savedCCRadio;
    }
}

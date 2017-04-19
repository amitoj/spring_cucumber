/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 *
 */
public class CreditCardInfoFragment extends AbstractPageObject {

    private static final String CREDIT_CARD_INFO_CONTAINER = ".BillingPanelPaymentInfoComp";
    // Saved payment info
    private static final String SAVED_PAYMENT_RADIO = ".//div[@class='PaymentOption']/input[@class='radio']";
    private static final String SAVED_PAYMENT_EDIT_LINK = "Edit card";
    private static final String NEW_PAYMENT_RADIOS = "paymentForm.defaultPmId";
    private static final String PAYMENT_CC_NUMBER = "paymentForm._NAE_acctNumber";
    private static final String PAYMENT_SECURITY_CODE = "cardId";
    private static final String PAYMENT_CARD_DESC = "paymentForm.cardDesc";
    private static final String PAYMENT_EXP_MONTH = "paymentForm.cardMonth";
    private static final String PAYMENT_EXP_YEAR = "paymentForm.cardYear";
    private static final String PAYMENT_FIRST_NAME = "paymentForm.billingFirstName";
    private static final String PAYMENT_MIDDLE_NAME = "paymentForm.billingMiddleName";
    private static final String PAYMENT_LAST_NAME = "paymentForm.billingLastName";
    private static final String PAYMENT_STREET_ADDRESS1 = "paymentForm._NAE_stAddress1";
    private static final String PAYMENT_STREET_ADDRESS2 = "paymentForm._NAE_stAddress2";
    private static final String PAYMENT_COUNTRY = "paymentForm.country";
    private static final String PAYMENT_CITY = "paymentForm.city";
    private static final String FIELDS_FOR_COUNTRY = ".fieldsForCountry";
    private static final String PAYMENT_STATE = "paymentForm.state";
    private static final String PAYMENT_CANADIAN_STATE = "paymentForm.canadianState";
    private static final String PAYMENT_INTL_STATE = "paymentForm.intlState";
    private static final String PAYMENT_ZIP = "paymentForm._NAE_zip";
    private static final String NEW_PAYMENT_SAVE_BILLING_INFO = "paymentForm.shouldSaveCC_Info";

    @FindBy(name = NEW_PAYMENT_RADIOS)
    private List<WebElement> newPaymentPaymentTypes;

    @FindBy(xpath = SAVED_PAYMENT_RADIO)
    private WebElement paymentRadioOption;

    @FindBy(linkText = SAVED_PAYMENT_EDIT_LINK)
    private WebElement paymentEditLink;

    @FindBy(name = PAYMENT_CC_NUMBER)
    private WebElement paymentCreditCardNumber;

    @FindBy(name = NEW_PAYMENT_SAVE_BILLING_INFO)
    private List<WebElement> newPaymentSaveBillingInfoRadios;

    @FindBy(className = PAYMENT_SECURITY_CODE)
    private WebElement paymentSecurityCode;

    @FindBy(name = PAYMENT_CARD_DESC)
    private WebElement paymentCardDesc;

    @FindBy(name = PAYMENT_EXP_MONTH)
    private WebElement paymentExpMonth;

    @FindBy(name = PAYMENT_EXP_YEAR)
    private WebElement paymentExpYear;

    @FindBy(name = PAYMENT_FIRST_NAME)
    private WebElement paymentFirstName;

    @FindBy(name = PAYMENT_MIDDLE_NAME)
    private WebElement paymentMiddleName;

    @FindBy(name = PAYMENT_LAST_NAME)
    private WebElement paymentLastName;

    @FindBy(name = PAYMENT_STREET_ADDRESS1)
    private WebElement paymentStreetAddress1;

    @FindBy(name = PAYMENT_STREET_ADDRESS2)
    private WebElement paymentStreetAddress2;

    @FindBy(name = PAYMENT_COUNTRY)
    private WebElement paymentCountry;

    @FindBy(name = PAYMENT_CITY)
    private WebElement paymentCity;

    @FindBy(css = FIELDS_FOR_COUNTRY)
    private WebElement fieldsForCountry;

    @FindBy(name = PAYMENT_STATE)
    private WebElement paymentState;

    @FindBy(name = PAYMENT_CANADIAN_STATE)
    private WebElement paymentCanadianState;

    @FindBy(name = PAYMENT_INTL_STATE)
    private WebElement paymentIntlState;

    @FindBy(name = PAYMENT_ZIP)
    private WebElement paymentZip;

    @FindBy(name = "btnPaymentInfo")
    private WebElement btnPaymentInfo;

    public CreditCardInfoFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(CREDIT_CARD_INFO_CONTAINER));
    }

    protected CreditCardInfoFragment(WebDriver webDriver, By container) {
        super(webDriver, container);
    }

    public CreditCardInfoFragment(WebDriver webdriver, boolean newCCInfo) {
        super(webdriver, By.cssSelector(CREDIT_CARD_INFO_CONTAINER +
            (newCCInfo ? " .newCreditCard" : " .savedPaymentOption")));
    }

    public boolean isPaymentTypeDisplayed() {
        try {
            return paymentRadioOption.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPaymentTypeSelected() {
        try {
            return paymentRadioOption.isSelected();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void choosePayment() {
        paymentRadioOption.click();
    }

    public void clickPaymentEditLink() {
        paymentEditLink.click();
    }

    public List<WebElement> getNewPaymentPaymentTypes() {
        return newPaymentPaymentTypes;
    }

    public void chooseNewPaymentType(int index) {
        newPaymentPaymentTypes.get(index).click();
    }

    public void typeNewPaymentCreditCardNumber(String ccNumber) {
        paymentCreditCardNumber.clear();
        paymentCreditCardNumber.sendKeys(ccNumber);
    }

    public void chooseNewPaymentSaveBillingInfo(boolean saveInfo) {
        if (saveInfo) {
            newPaymentSaveBillingInfoRadios.get(0).click();
        }
        else {
            newPaymentSaveBillingInfoRadios.get(1).click();
        }
    }

    public void typePaymentSecurityCode(String cvc) {
        paymentSecurityCode.clear();
        paymentSecurityCode.sendKeys(cvc);
    }

    public void typePaymentCardDescription(String description) {
        paymentCardDesc.clear();
        paymentCardDesc.sendKeys(description);
    }

    public void choosePaymentExpirationMonth(String month) {
        new Select(paymentExpMonth).selectByVisibleText(month);
    }

    public void choosePaymentExpirationYear(String year) {
        new Select(paymentExpYear).selectByVisibleText(year);
    }

    public void typePaymentFirstName(String fName) {
        paymentFirstName.clear();
        paymentFirstName.sendKeys(fName);
    }

    public void typePaymentMiddleName(String mName) {
        paymentMiddleName.clear();
        paymentMiddleName.sendKeys(mName);
    }

    public void typePaymentLastName(String lName) {
        paymentLastName.clear();
        paymentLastName.sendKeys(lName);
    }

    public void typePaymentStreetAddress(String street) {
        paymentStreetAddress1.clear();
        paymentStreetAddress1.sendKeys(street);
    }

    public void typePaymentStreetAddressContinued(String street) {
        paymentStreetAddress2.clear();
        paymentStreetAddress2.sendKeys(street);
    }

    public void selectPaymentCountry(String country) {
        new Select(paymentCountry).selectByVisibleText(country);
    }

    public void typePaymentCity(String city) {
        paymentCity.clear();
        paymentCity.sendKeys(city);
    }

    public void chooseOrEnterPaymentState(String state) {
        if (fieldsForCountry.getAttribute("class").contains("countryUS")) {
            Select select = new Select(paymentState);
            try {
                int index = Integer.parseInt(state);
                select.selectByIndex(index);
            }
            catch (NumberFormatException e) {
                select.selectByVisibleText(state);
            }
        }
        else if (fieldsForCountry.getAttribute("class").contains("countryCanada")) {
            Select select = new Select(paymentCanadianState);
            try {
                int index = Integer.parseInt(state);
                select.selectByIndex(index);
            }
            catch (NumberFormatException e) {
                select.selectByVisibleText(state);
            }
        }
        else {
            // Catchall for intl country being selected.
            paymentIntlState.sendKeys(state);
        }
    }

    public void typePaymentZip(String zip) {
        paymentZip.clear();
        paymentZip.sendKeys(zip);
    }

    public String colorPaymentCreditCardNumber() {
        return paymentCreditCardNumber.getCssValue("color");
    }

    public String colorPaymentSecurityCode() {
        return paymentSecurityCode.getCssValue("color");
    }

    public String colorPaymentExpMonth() {
        return paymentExpMonth.getCssValue("color");
    }

    public String colorPaymentExpYear() {
        return paymentExpYear.getCssValue("color");
    }

    public String colorPaymentFirstName() {
        return paymentFirstName.getCssValue("color");
    }

    public String colorPaymentLastName() {
        return paymentLastName.getCssValue("color");
    }

    public String colorPaymentStreetAddress1() {
        return paymentStreetAddress1.getCssValue("color");
    }

    public String colorPaymentCity() {
        return paymentCity.getCssValue("color");
    }

    public String colorPaymentState() {
        return paymentState.getCssValue("color");
    }

    public String colorPaymentZip() {
        return paymentZip.getCssValue("color");
    }

    public void clickContinueButton() {
        btnPaymentInfo.click();
    }
}

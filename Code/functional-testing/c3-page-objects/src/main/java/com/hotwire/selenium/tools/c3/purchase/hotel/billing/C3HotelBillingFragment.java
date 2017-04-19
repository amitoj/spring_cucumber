/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-ozelenov
 * Date: 4/25/14
 * Time: 1:52 AM
 */
public class C3HotelBillingFragment extends C3HotelBillingPage {
    private static final By CARD_COUNTRY =
            By.cssSelector("select[name='paymentInfoModel.newCreditCard.countryCode'] option[selected]");

    private static final String CARD_NUM = "input[name='paymentInfoModel.newCreditCard.creditCardNumber']";
    private static final String EXP_MONTH = "select[name='paymentInfoModel.newCreditCard.cardExpiryMonth']";
    private static final String EXP_YEAR = "select[name='paymentInfoModel.newCreditCard.cardExpiryYear']";
    private static final By CARD_FN = By.name("paymentInfoModel.newCreditCard.billingFirstName");
    private static final By CARD_LN = By.name("paymentInfoModel.newCreditCard.billingLastName");
    private static final By CARD_ADDRESS = By.name("paymentInfoModel.newCreditCard.billingAddress1");
    private static final By CARD_CITY = By.name("paymentInfoModel.newCreditCard.city");
    private static final String CARD_STATE = "select[name='paymentInfoModel.newCreditCard.province']";
    private static final By CARD_ZIP = By.name("paymentInfoModel.newCreditCard.postalCode");
    private static final By PAY_WITH_NEW_CARD =
            By.cssSelector("input#useNewCardPaymentRadioButton, input#newCreditCardPaymentRadioButton");

    public C3HotelBillingFragment(WebDriver webdriver) {
        super(webdriver, By.id("billingPaymentPanel"));
    }

    public String getCardNum() {
        return findOne(CARD_NUM).getAttribute("value");
    }

    public String getExpMonth() {
        return findOne(EXP_MONTH + " option[selected]").getText();
    }

    public String getExpYear() {
        return findOne(EXP_YEAR + " option[selected]").getText();
    }

    public String getCardFirstName() {
        return findOne(CARD_FN).getAttribute("value");
    }

    public String getCardLastName() {
        return findOne(CARD_LN).getAttribute("value");
    }

    public String getCountry() {
        return findOne(CARD_COUNTRY).getText();
    }

    public String getCardAddress() {
        return findOne(CARD_ADDRESS).getAttribute("value");
    }

    public String getCity() {
        return findOne(CARD_CITY).getAttribute("value");
    }

    public String getState() {
        return findOne(CARD_STATE + " option[selected]").getText();
    }

    public String getZip() {
        return findOne(CARD_ZIP).getAttribute("value");
    }

    public void setCreditCard(String number, String month, String year, String code) {
        setText(CARD_NUM, number, DEFAULT_WAIT);
        selectValue(EXP_MONTH, month);
        selectValue(EXP_YEAR, year);
        setText("input[name='paymentInfoModel.newCreditCard.cpvNumber']", code);
    }

    public void setCreditCardOwner(String fn, String ln) {
        setText(CARD_FN, fn);
        setText(CARD_LN, ln);
    }

    public void setCreditCardAddress(String address, String city) {
        setText(CARD_ADDRESS, address);
        setText(CARD_CITY, city);
    }

    public void setZipCode(String zip) {
        setText(CARD_ZIP, zip);
    }

    public void setCreditCardState(String state) {
        selectValue(CARD_STATE, state);
    }

    public void chooseNewCreditCard() {
        findOne(PAY_WITH_NEW_CARD).click();
    }

    public void setSavedCardCode() {
        setText("div.cpvNum input[type='password']", "111");
    }

    public void setCreditCardCountry(String creditCardCountry) {
        findOne(By.id("paymentInfoModel.newCreditCard.countryCode"), DEFAULT_WAIT).click();
        findOne(By.id("paymentInfoModel.newCreditCard.countryCode")).sendKeys(creditCardCountry);
        findOne(CARD_CITY).click();
    }
}

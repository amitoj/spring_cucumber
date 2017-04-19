/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.onepage;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.ui.ExtendedSelect;

/**
 * PayPal payment fragment
 */
public class HotelPayPalFragment extends AbstractPageObject {

    @FindBy(xpath = "//div[@class='cardMethod']/input[@radiobtnname='paypal']")
    private WebElement paymentMethodTypeFields;

    @FindBy(name = "paymentInfoModel.payPal.billingFirstName")
    private WebElement billerFirstName;

    @FindBy(name = "paymentInfoModel.payPal.billingLastName")
    private WebElement billerLastName;

    @FindBy(name = "paymentInfoModel.payPal.billingAddress1")
    private WebElement billingAddress;

    @FindBy(name = "paymentInfoModel.payPal.countryCode")
    private WebElement billingCountry;

    @FindBy(name = "paymentInfoModel.payPal.city")
    private WebElement billingCity;

    @FindBy(xpath = "//select[@name='paymentInfoModel.payPal.province' and contains(@class, 'bUS')]")
    private WebElement billingState;

    @FindBy(name = "paymentInfoModel.payPal.postalCode")
    private WebElement billingZipCode;

    public HotelPayPalFragment(WebDriver webDriver) {
        super(webDriver, By.id("paypalPaymentFields"));
    }

    public HotelPayPalFragment withBillingAddress(String billingAddress) {
        sendKeys(this.billingAddress, billingAddress);
        return this;
    }

    public HotelPayPalFragment withCountry(String country) {
        sendKeys(this.billingCountry, country);
        return this;
    }

    public HotelPayPalFragment withCity(String city) {
        sendKeys(this.billingCity, city);
        return this;
    }

    public HotelPayPalFragment withState(String state) {
        ExtendedSelect select = new ExtendedSelect(this.billingState);
        try {
            select.selectByIndex(Integer.parseInt(state));
        }
        catch (NumberFormatException e) {
            // state is non-numeric so treat like actual text.
            select.selectIfVisibleTextStartsWithText(state);
        }
        return this;
    }

    public HotelPayPalFragment withZipCode(String zipCode) {
        sendKeys(this.billingZipCode, zipCode);
        return this;
    }

    public HotelPayPalFragment withFirstName(String firstName) {
        sendKeys(this.billerFirstName, firstName);
        return this;
    }

    public HotelPayPalFragment withLastName(String lastName) {
        sendKeys(this.billerLastName, lastName);
        return this;
    }

    public HotelPayPalFragment continuePanel() {
        return this;
    }

    public boolean isEnabled() {
        return paymentMethodTypeFields.isEnabled();
    }
}

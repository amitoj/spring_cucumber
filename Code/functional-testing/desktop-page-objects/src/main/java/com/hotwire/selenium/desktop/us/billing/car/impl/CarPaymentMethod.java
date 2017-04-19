/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl;

import org.openqa.selenium.WebElement;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 7:05 AM
 */
public interface CarPaymentMethod {

    /**
     * Choose PayPal payment option
     */
    CarPaymentMethod processPayPal();

    /**
     * Choose HotDollars payment option
     */
    CarPaymentMethod processHotDollars();

    /**
     * Choose CreditCard payment option
     */
    CarPaymentMethod processCreditCard();

    /**
     * Choosing saved payment methods
     */

    CarPaymentMethod processSavedVisa(String securityCode);

    CarPaymentMethod processSavedMasterCard(String securityCode);

    /**
     * Getting  payment  buttons
     */

    WebElement getPayPalRadioButton();

    WebElement getHotDollarsButton();

    WebElement getCreditCardField();

    WebElement getSavedCreditCardButton();

    WebElement getSavedVisaButton();

    WebElement getSavedMasterCardButton();

    String getNameOfChosenPaymentMethod();

    boolean isHotDollarsModuleAvailable();

    String getHotDollarsMessage();

    WebElement getPaymentOptionCreditCard();

    /**
     * Input card holder's initials for credit card
     */
    CarPaymentMethod cardHolder(String firstName, String lastName);

    /**
     * Input credit card attributes
     */
    CarPaymentMethod creditCardNumber(String cardNumber);

    CarPaymentMethod expDate(String cardExpMonth, String cardExpYear);

    CarPaymentMethod creditCardSecurityCode(String cardSecCode);

    CarPaymentMethod savedVisaSecurityCode(String cardSecCode);

    /**
     * Fill in card holder's address information
     */
    CarPaymentMethod city(String city);

    CarPaymentMethod country(String country);

    CarPaymentMethod state(String state);

    CarPaymentMethod billingAddress(String address);

    CarPaymentMethod zipCode(String zipCode);

    CarPaymentMethod continuePanel();

    /**
     * Filling PayPal payment fields
     */
    CarPaymentMethod payPalUser(String firstName, String lastName);

    CarPaymentMethod payPalAddress(String address);

    CarPaymentMethod payPalCity(String city);

    CarPaymentMethod payPalState(String state);

    CarPaymentMethod payPalZipCode(String zip);

    /**
     * Verify that billing section is present on the page
     */
    boolean isBillingSectionPresent();

    /**
     * Verify that billing section has only one Credit Card payment method
     */
    boolean isCreditCardIsSingleAvailablePayment();

    void saveMyInformation();

    void savePaymentInformation();

    WebElement getPasswordField();

    WebElement getConfirmPasswordField();

    boolean isSaveMyInfoExpanded();

    void chooseCreditCardPaymentMethod();

    boolean isSavedPaymentPresent();

    void typeCreditCardNameField(String ccNumber);


}



/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 7/7/14
 * Time: 7:13 AM
 */
public class SelectPaymentMethodFragment extends AbstractPageObject {

    private static final By CONTAINER = By.cssSelector(
            "#paymentSection .paymentMethodSelectionWrapper, " + // CAR
            "#selectPaymentMethod" // HOTEL
    );

    @FindBy(css = "#defaultPmIdNewCC, #newCreditCardPaymentRadioButton")
    private WebElement creditCard;

    @FindBy(id = "defaultPmIdNewDC")
    private WebElement debitCard;

    @FindBy(css = "#defaultPmIdNewPP, #paypalRadioButton")
    private WebElement payPal;

    @FindBy(id = "vmeRadioButton")
    private WebElement vme;

    public SelectPaymentMethodFragment(WebDriver webdriver) {
        super(webdriver, CONTAINER);
    }

    public void selectPayPal() {
        payPal.click();
    }

    public void selectNewCreditCard() {
        creditCard.click();
    }

    public void selectVme() {
        vme.click();
    }
}



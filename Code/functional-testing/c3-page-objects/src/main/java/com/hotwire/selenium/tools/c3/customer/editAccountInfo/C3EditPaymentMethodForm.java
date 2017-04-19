/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.editAccountInfo;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 11/21/14
 * Time: 6:48 AM
 * Page object for Edit Payment Method and Add a new Credit Card pop-ups in C3
 */
public class C3EditPaymentMethodForm extends ToolsAbstractPage {
    private static String saveCardBtn = "input#Save";
    private static String deleteCardBtn = "input#deleteThisPayment";
    private static String cardName = "input#paymentMethodLogicalName";
    private  static String billingName = "input#billingName";
    private  static String cardNumber = "input#cardNumber";
    private  static String billingAddress = "input#billingAddress1";
    private  static String zip = "input#zipCode";
    private  static String country  = "select#country";
    private  static String month  = "select#cardMonth";
    private  static String year  = "select#cardYear";

    public C3EditPaymentMethodForm(WebDriver webdriver) {
        super(webdriver, By.cssSelector(saveCardBtn));
    }

    public void addACardWithName(String cardName) {
        setText(billingName, "Test Hotwire", EXTRA_WAIT);
        setText(cardNumber, "4111111111111111", DEFAULT_WAIT);
        setText(billingAddress, "333 Market Str", DEFAULT_WAIT);
        setText(zip, "94105", DEFAULT_WAIT);
        setText(this.cardName, cardName, DEFAULT_WAIT);
        selectValue(country, "US", DEFAULT_WAIT);
        selectValue(month, "1", DEFAULT_WAIT);
        selectValue(year, "2018", DEFAULT_WAIT);
        findOne(saveCardBtn, DEFAULT_WAIT).click();
    }

    public void deleteCreditCardByName(String cardName) {
        selectValueByVisibleText("select#paymentId", cardName, DEFAULT_WAIT);
        findOne(deleteCardBtn, DEFAULT_WAIT).click();
    }

    public void changeCreditCardName(String cardName) {
        findOne(this.cardName, DEFAULT_WAIT).clear();
        findOne(this.cardName, DEFAULT_WAIT).sendKeys(cardName);
        findOne(saveCardBtn, DEFAULT_WAIT).click();
    }

    public void changeBillingAddress(String billingAddress) {
        findOne(this.billingAddress, DEFAULT_WAIT).clear();
        findOne(this.billingAddress, DEFAULT_WAIT).sendKeys(billingAddress);
        findOne(saveCardBtn, DEFAULT_WAIT).click();
    }

    public String returnCardSuccessMessage() {
        return findOne("div#successBlock", DEFAULT_WAIT).getText();
    }

    public String returnCardErrorMessage() {
        return findOne("div#errorBlock", DEFAULT_WAIT).getText();
    }

}

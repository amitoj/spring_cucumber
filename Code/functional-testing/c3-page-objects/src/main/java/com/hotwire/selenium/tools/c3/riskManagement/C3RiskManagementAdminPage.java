/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.riskManagement;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-vyulun on 6/25/2014.
 */

public class C3RiskManagementAdminPage extends ToolsAbstractPage {
    public C3RiskManagementAdminPage(WebDriver webDriver) {
        super(webDriver, By.cssSelector("#allVerticalsRadio"));
    }

    public void selectVertical(String vertical) {
        if (vertical.contains("all")) {
            findOne("#allVerticalsRadio", DEFAULT_WAIT).click();
        }
        else {
            findOne("#" + vertical.toLowerCase() + "Radio", DEFAULT_WAIT).click();
        }
    }

    public void searchReservationByEmail(String email) {
        findOne("#customerEmail", DEFAULT_WAIT).sendKeys(email);
        submit();
    }

    public void searchReservationByItinerary(String itineraryNumber) {
        findOne("#displayNumber", DEFAULT_WAIT).sendKeys(itineraryNumber);
        submit();
    }

    public void searchReservationByCcNumber(String ccNumber) {
        findOne("#paymentMethodAccountNumber", DEFAULT_WAIT).sendKeys(ccNumber);
        submit();
    }

    public void searchReservationByPhoneNumber(String areaNumber, String phoneNumber) {
        findOne("#phoneAreaCode", DEFAULT_WAIT).sendKeys(areaNumber);
        findOne("#phoneNumber", DEFAULT_WAIT).sendKeys(phoneNumber);
        submit();
    }

    public void searchReservationByPassword(String password) {
        findOne("#accountPassword", DEFAULT_WAIT).sendKeys(password);
        submit();
    }

    public void searchReservationByCcNumberBin(String ccNumber) {
        findOne("#creditCardBin", DEFAULT_WAIT).sendKeys(ccNumber);
        submit();
    }

    public void searchReservationByPassengerLastName(String lastName) {
        findOne("#guestLastName", DEFAULT_WAIT).sendKeys(lastName);
        submit();
    }

    public void searchReservationByPaymentHolderLastName(String ccLastName) {
        findOne("#creditCardHolderLastName", DEFAULT_WAIT).sendKeys(ccLastName);
        submit();
    }

    public void searchReservationByCustomerLastName(String lastName) {
        findOne("#customerLastName", DEFAULT_WAIT).sendKeys(lastName);
        submit();
    }

    public void submit() {
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void clickOnFraudWatchList() {
        findOne(By.xpath("//a[text()='Fraud Watch List Input']"), DEFAULT_WAIT).click();
    }

    public void clickOnTransactionsReviewVertical(String vertical) {
        findOne(By.xpath("//a[text()='" + vertical + "']"), DEFAULT_WAIT).click();
    }

    public void searchReservationByIPAddress(String ipAddress) {
        findOne("#ipAddress", DEFAULT_WAIT).sendKeys(ipAddress);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByOrderID(String purchaseOrderId) {
        findOne("#purchaseOrderId", DEFAULT_WAIT).sendKeys(purchaseOrderId);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void clickOnClearOldTransactionsLink() {
        findOne(By.xpath("//a[text()='Clear Old Transactions']"), DEFAULT_WAIT).click();
    }
}

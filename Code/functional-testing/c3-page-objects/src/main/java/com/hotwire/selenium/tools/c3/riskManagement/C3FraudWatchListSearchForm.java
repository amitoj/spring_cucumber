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

public class C3FraudWatchListSearchForm extends ToolsAbstractPage {
    public C3FraudWatchListSearchForm(WebDriver webDriver) {
        super(webDriver, By.xpath("//input[@value='Search For Matching records']"));
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
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByItinerary(String itineraryNumber) {
        findOne("#displayNumber", DEFAULT_WAIT).sendKeys(itineraryNumber);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByCcNumber(String ccNumber) {
        findOne("#paymentMethodAccountNumber", DEFAULT_WAIT).sendKeys(ccNumber);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByPhoneNumber(String areaNumber, String phoneNumber) {
        findOne("#phoneAreaCode", DEFAULT_WAIT).sendKeys(areaNumber);
        findOne("#phoneNumber", DEFAULT_WAIT).sendKeys(phoneNumber);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByPassword(String password) {
        findOne("#accountPassword", DEFAULT_WAIT).sendKeys(password);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByCcNumberBin(String ccNumber) {
        findOne("#creditCardBin", DEFAULT_WAIT).sendKeys(ccNumber);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByPassengerLastName(String lastName) {
        findOne("#guestLastName", DEFAULT_WAIT).sendKeys(lastName);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByPaymentHolderLastName(String ccLastName) {
        findOne("#creditCardHolderLastName", DEFAULT_WAIT).sendKeys(ccLastName);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void searchReservationByCustomerLastName(String lastName) {
        findOne("#customerLastName", DEFAULT_WAIT).sendKeys(lastName);
        findOne(".formButton", DEFAULT_WAIT).click();
    }

    public void addFraudWatchReecord(String email, String source, String type) {
        findOne("#email", DEFAULT_WAIT).sendKeys(email);
        findOne("#sourceTypeCode", DEFAULT_WAIT).sendKeys(source);
        findOne("#fraudTypeCode", DEFAULT_WAIT).sendKeys(type);
        findOne(By.xpath("//input[@value='Add Record to Watch List']"),  DEFAULT_WAIT).click();
    }

    public void searchFraudWatchReecord(String email) {
        findOne("#email", DEFAULT_WAIT).sendKeys(email);
        findOne(By.xpath("//input[@value='Search For Matching records']"),  DEFAULT_WAIT).click();
    }
}

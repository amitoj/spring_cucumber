/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page for searching customers in C3
 */
public class C3SearchCustomerPage extends ToolsAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(C3SearchCustomerPage.class.getName());

    public C3SearchCustomerPage(WebDriver webDriver) {
        super(webDriver, By.cssSelector("form[name='customerQuery']"));
    }

    public void waitForRedArrow() {
        findOne("img[name='confirmationNumberGif']", DEFAULT_WAIT);
    }

    public void setEmailInput(String email) {
        LOGGER.info("Customer email:" + email);
        WebElement emailField = findOne("input#emailAddress", EXTRA_WAIT);
        emailField.sendKeys(email);
        waitForRedArrow();
    }

    public void searchByPhone(String phone) {
        searchCustomerBy("input#phoneNumber", phone);
    }

    public void setConfirmationNumber(String itin) {
        WebElement itineraryInput = findOne("input#confirmationNumber", EXTRA_WAIT);
        itineraryInput.sendKeys(itin);
        waitForRedArrow();
        itineraryInput.sendKeys(Keys.ENTER);
    }

    public void searchByPNR(String pnr) {
        searchCustomerBy("input#recordLocator", pnr);
    }

    public void clickSubmit() {
        findOne("input[name='Submit']").click();
    }

    private void searchCustomerBy(String cssLocator, String searchValue) {
        WebElement itineraryInput = findOne(cssLocator, EXTRA_WAIT);
        itineraryInput.sendKeys(searchValue);
        waitForRedArrow();
        itineraryInput.sendKeys(Keys.ENTER);
    }

    public void setLastName(String value) {
        searchCustomerBy("input#lastName", value);
    }

    public void setFirstName(String firstName) {
        setText("#firstName", firstName);
        waitForRedArrow();
    }

    public String getErrorMessage() {
        return findOne(".errHolder", PAGE_WAIT).getText();
    }

    public void searchByReferenceNumber(String number) {
        searchCustomerBy("#searchReferenceNumber", number);
    }

    public void searchForCaseNotes() {
        findOne("a[href*='caseQuery']", DEFAULT_WAIT).click();
    }

    public void returnToIndexC3Page() {
        findOne(By.xpath("//A[@class='menuItem homeItem']"), DEFAULT_WAIT).click();
    }

    public void setCustomerId(String customerId) {
        setText("input#customerId", customerId);
        waitForRedArrow();
    }

    public void setCaseId(String caseId) {
        setText("input#caseId", caseId);
        waitForRedArrow();
    }

    public void setAirTicketNumber(String airTicketNumber) {
        setText("input#ticketNumber", airTicketNumber);
        waitForRedArrow();
    }

    public void setCarReservationNumber(String carReservationNumber) {
        setText("input#carReservation", carReservationNumber);
        waitForRedArrow();
    }

    public void setHotelReservationNumber(String hotelReservationNumber) {
        setText("input#hotelReservation" , hotelReservationNumber);
        waitForRedArrow();
    }
}

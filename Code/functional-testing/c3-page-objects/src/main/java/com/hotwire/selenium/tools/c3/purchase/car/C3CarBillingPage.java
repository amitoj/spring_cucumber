/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.purchase.car;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;



import org.openqa.selenium.WebDriver;



/**
Simplified Car Biling Page for C3
 */
public class C3CarBillingPage extends ToolsAbstractPage {
    private final String location   = "div.carPickDrop div.desc, div.destDetails div.mb20";
    private final String startDate  = "div.subHeader h2";
    private final String startTime  = "div.subHeader h2";
    private final String endDate    = "div.subHeader h2";
    private final String endTime    = "div.subHeader h2";

    public C3CarBillingPage(WebDriver webDriver) {
        super(webDriver, By.className("reviewCarDetails"));
    }

    public void fillTravelerFullInfoPanel(String travelerFirstName,
                                          String travelerLastName,
                                          String travelerPrimaryPhoneNumber,
                                          String travelerEmail) {

        C3CarTravellerFragment cti = new C3CarTravellerFragment(getWebDriver());
        try {
            cti.setDriverName(travelerFirstName, travelerLastName);
            cti.travelerPhoneNumber(travelerPrimaryPhoneNumber);
            cti.setTravellerEmail(travelerEmail);
//            cti.creditCardAcceptance(true);
            cti.confirmAgeAndDeposit();
        }
        catch (NoSuchElementException | ElementNotVisibleException e) {
            cti.confirmAgeAndDeposit();
        }
    }

    public void fillInsurance(boolean withInsurance) {
        try {
            setDynamicInsurance(withInsurance);
        }
        catch (TimeoutException e) {
            setStaticInsurance(withInsurance);
        }
        freeze(1);
    }

    public void setDynamicInsurance(boolean withInsurance) {
        if (withInsurance) {
            findOne("input#insurancePurchaseCheckTrue", PAGE_WAIT).click();
        }
        else {
            findOne("input#insurancePurchaseCheckFalse", PAGE_WAIT).click();
        }
    }

    public void setStaticInsurance(boolean withInsurance) {
        if (withInsurance) {
            findOne("div#protectionAndPayment input#yesBtn").click();
        }
        else {
            findOne("div#protectionAndPayment input#noBtn").click();
        }
    }

    public void fillCreditCard(String creditCardNumber,
                               String creditCardExpMonth,
                               String creditCardExpYear,
                               String creditCardSecurityCode) {
        setText("input[name='billingForm.paymentForm._NAE_acctNumber']", creditCardNumber);
        selectValue("select[name='billingForm.paymentForm.cardMonth']", creditCardExpMonth);
        selectValue("select[name='billingForm.paymentForm.cardYear']", creditCardExpYear);
        for (WebElement securityCodeField : findMany("input[name='billingForm.paymentForm._NAE_cpvNumber']")) {
            if (securityCodeField.isDisplayed()) {
                securityCodeField.sendKeys(creditCardSecurityCode);
            }
        }
    }

    public void fillCardHolder(String firstName, String lastName) {
        setText("input[name='billingForm.paymentForm.billingFirstName']", firstName);
        setText("input[name='billingForm.paymentForm.billingLastName']", lastName);
    }

    public void fillCardHolderContacts(String billingAddress,
                                       String billingCity,
                                       String billingState,
                                       String billingZipCode) {
        setText("input[name='billingForm.paymentForm._NAE_stAddress1']", billingAddress);
        setText("input[name='billingForm.paymentForm.city']", billingCity);
        selectValue("select[name='billingForm.paymentForm.state']", billingState);
        setText("input[name='billingForm.paymentForm._NAE_zip']", billingZipCode);
    }

    public String getReferenceNumber() {
        return getWebDriver().findElement(By.cssSelector("li.referenceNumber p")).getText();
    }

    public String getFirstTravelerName() {
        return getWebDriver().findElement(By.id("billingForm.travelerForm.driverFirstName")).getAttribute("value");
    }

    public String getLastTravelerName() {
        return getWebDriver().findElement(By.id("billingForm.travelerForm.driverLastName")).getAttribute("value");
    }

    public String getTravelerEmail() {
        return getWebDriver().findElement(By.id("billingForm.travelerForm._NAE_email")).getAttribute("value");
    }

    public String getTravelerConfEmail() {
        return getWebDriver().findElement(By.id("billingForm.travelerForm._NAE_confirmEmail")).getAttribute("value");
    }

    public String getTravelerPhone() {
        return getWebDriver().findElement(By.id("billingForm.travelerForm.phoneNo"))
                .getAttribute("value").replaceAll("-", "");
    }

    public boolean getTravelerAge() {
        return getWebDriver().findElement(By.id("is25User")).isSelected();
    }

    public void agreeAndBook() {
        findOne("input[name='billingForm.agreement']").click();
        findOne("button#billingConfirm").click();
    }

    public void payWithSavedCard() throws NoSuchElementException {
        findOne("div.savedPaymentMethod");
        setText("div.savedPaymentMethod input[name='billingForm.paymentForm._NAE_cpvNumber']", "111");
    }

    public String getPrimaryDiver() {
        return findOne("select[name='billingForm.travelerForm.participantTravelerIndexes'] option").getText();
    }

    public String getSavedEmail() {
        return findOne("div#driverEmail strong").getText();
    }

    public WebElement getStaticInsuranceModule() {
        return findOne("div#protection div.grayBottomGradient", PAGE_WAIT);
    }

    public String getLocation() {
        try {
            return findOne(location).getText().split("\n")[1];
        }
        catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getStartDate() {
        try {
            return findOne(startDate).getText().split(" to ")[0].split(" at ")[0].trim();
        }
        catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getStartTime() {
        try {
            return findOne(startTime).getText().split(" to ")[0].split(" at ")[1].trim();
        }
        catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getEndDate() {
        try {
            return findOne(endDate).getText().split(" to ")[1].split(" at ")[0].trim();
        }
        catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    public String getEndTime() {
        try {
            return findOne(endTime).getText().split(" to ")[1].split(" at ")[1].trim();
        }
        catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}

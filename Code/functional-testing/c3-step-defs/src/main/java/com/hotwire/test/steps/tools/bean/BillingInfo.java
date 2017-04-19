/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean;


/**
* BillingInfo is a container for all values on Billing Page
 */
public class BillingInfo extends ToolsAbstractBean {
    private String ccFirstName;
    private String ccLastName;
    private String ccType;
    private String ccNumber;
    private String ccExpMonth;
    private String ccExpYear;
    private String securityCode;
    private String billingAddress;
    private String country;
    private String city;
    private String state;
    private String zipCode;
    private boolean insurance = true;
    private boolean hotDollars;
    private String hotDollarsAmount;
    private String insuranceAmount;
    private String totalAmount;
    private Double totalAmountWithOutHotDollars;
    private String currencyCode;
    private boolean flagChangedCard = false;
    private boolean choosePayWithNewCreditCard = false;

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String cardType) {
        this.ccType = cardType;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpMonth() {
        return ccExpMonth;
    }

    public void setCcExpMonth(String ccExpMonth) {
        this.ccExpMonth = ccExpMonth;
    }

    public String getCcExpYear() {
        return ccExpYear;
    }

    public void setCcExpYear(String ccExpYear) {
        this.ccExpYear = ccExpYear;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public boolean isWithInsurance() {
        return insurance;
    }

    public void setHotDollars(boolean hotDollars) {
        this.hotDollars = hotDollars;
    }

    public boolean isWithHotDollars() {
        return hotDollars;
    }

    public void setHotDollarsAmount(String hotDollarsAmount) {
        this.hotDollarsAmount = hotDollarsAmount;
    }

    public String getHotDollarsAmountText() {
        return hotDollarsAmount;
    }

    public String getCcFirstName() {
        return ccFirstName;
    }

    public String getCcLastName() {
        return ccLastName;
    }

    public void setCcFirstName(String ccFirstName) {
        this.ccFirstName = ccFirstName;
    }

    public void setCcLastName(String ccLastName) {
        this.ccLastName = ccLastName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(String insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getTotalAmountText() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isChangedCardFlag() {
        return flagChangedCard;
    }

    public void setFlagChangedCard(boolean flagChangedCard) {
        this.flagChangedCard = flagChangedCard;
    }

    public boolean isChoosePayWithNewCreditCard() {
        return choosePayWithNewCreditCard;
    }

    public void setChoosePayWithNewCreditCard(boolean choosePayWithNewCreditCard) {
        this.choosePayWithNewCreditCard = choosePayWithNewCreditCard;
    }

    public Double getTotalAmountWithOutHotDollars() {
        return totalAmountWithOutHotDollars;
    }

    public void setTotalAmountWithOutHotDollars(Double totalAmountWithOutHotDollars) {
        this.totalAmountWithOutHotDollars = totalAmountWithOutHotDollars;
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;

/**
 * User: v-ngolodiuk
 * Since: 1/8/15
 */
public class UserInformation implements Serializable {

    private String ccType;
    private String firstName;
    private String middleName;
    private String lastName;
    private String primaryPhoneNumber;
    private String emailId;
    private String ccFirstName;
    private String ccMiddleName;
    private String ccLastName;
    private String ccNumber;
    private int securityCode;
    private String billingCountry;
    private String billingAddress;
    private String country;
    private String city;
    private String state;
    private String zipCode;
    private String phoneCountryCode;
    private String billingInformationName;
    private String savedCardName;
    private String password;
    private String passwordConfirmation;
    private long customerID;
    private String isHFCStatus;

    //driver info for Cars vertical
    private String driverFirstName;
    private String driverLastName;
    private String driverEmailAddress;
    private String driverPhoneNumber;

    //random credit card credentials
    private String randomCreditCardNumber;
    private String randomCreditCardNickName;
    private XMLGregorianCalendar expirationDate;
    private String randomEmailId;
    private String randomCustomerPassword;
    private String countryCode;

    //email subscriptions
    private boolean isTripWatcher;
    private boolean isSavingsNotices;
    private boolean isBigDeals;
    private boolean isHotAlert;
    private boolean isCruizeNews;

    private boolean isPromotionalDeals;
    private boolean isCreditEmailNotify;
    private String favoriteAirport;

    public boolean isPromotionalDeals() {
        return isPromotionalDeals;
    }

    public void setPromotionalDeals(boolean promotionalDeals) {
        isPromotionalDeals = promotionalDeals;
    }

    public boolean isCreditEmailNotify() {
        return isCreditEmailNotify;
    }

    public void setCreditEmailNotify(boolean creditEmailNotify) {
        isCreditEmailNotify = creditEmailNotify;
    }

    public String getHFCStatus() {
        return isHFCStatus;
    }

    public void setHFCStatus(String hFCStatus) {
        isHFCStatus = hFCStatus;
    }

    public boolean getIsBigDeals() {
        return isBigDeals;
    }

    public void setIsBigDeals(boolean bigDeals) {
        isBigDeals = bigDeals;
    }

    public boolean getIsTripWatcher() {
        return isTripWatcher;
    }

    public void setIsTripWatcher(boolean tripWatcher) {
        isTripWatcher = tripWatcher;
    }

    public boolean getIsSavingsNotices() {
        return isSavingsNotices;
    }

    public void setIsSavingsNotices(boolean savingsNotices) {
        isSavingsNotices = savingsNotices;
    }

    public boolean getIsHotAlert() {
        return isHotAlert;
    }

    public void setIsHotAlert(boolean hotAlert) {
        isHotAlert = hotAlert;
    }

    public boolean getIsCruizeNews() {
        return isCruizeNews;
    }

    public void setIsCruizeNews(boolean cruizeNews) {
        isCruizeNews = cruizeNews;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public String getDriverEmailAddress() {
        return driverEmailAddress;
    }

    public void setDriverEmailAddress(String driverEmailAddress) {
        this.driverEmailAddress = driverEmailAddress;
    }



    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCcFirstName() {
        return ccFirstName;
    }

    public void setCcFirstName(String ccFirstName) {
        this.ccFirstName = ccFirstName;
    }

    public String getCcMiddleName() {
        return ccMiddleName;
    }

    public void setCcMiddleName(String ccMiddleName) {
        this.ccMiddleName = ccMiddleName;
    }

    public String getCcLastName() {
        return ccLastName;
    }

    public void setCcLastName(String ccLastName) {
        this.ccLastName = ccLastName;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
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

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public String getBillingInformationName() {
        return billingInformationName;
    }

    public void setBillingInformationName(String billingInformationName) {
        this.billingInformationName = billingInformationName;
    }

    public String getSavedCardName() {
        return savedCardName;
    }

    public void setSavedCardName(String savedCardName) {
        this.savedCardName = savedCardName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public void setRandomCreditCardNumber(String randomCreditCardNumber) {
        this.randomCreditCardNumber = randomCreditCardNumber;
    }

    public String getRandomCreditCardNumber() {
        return randomCreditCardNumber;
    }

    public void setRandomCreditCardNickName(String randomCreditCardNickName) {
        this.randomCreditCardNickName = randomCreditCardNickName;
    }

    public String getRandomCreditCardNickName() {
        return randomCreditCardNickName;
    }

    public void setExpirationDate(XMLGregorianCalendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setRandomEmailId(String randomEmailId) {
        this.randomEmailId = randomEmailId;
    }

    public String getRandomEmailId() {
        return randomEmailId;
    }

    public void setRandomCustomerPassword(String randomCustomerPassword) {
        this.randomCustomerPassword = randomCustomerPassword;
    }

    public String getRandomCustomerPassword() {
        return randomCustomerPassword;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getFavoriteAirport() {
        return favoriteAirport;
    }

    public void setFavoriteAirport(String favoriteAirport) {
        this.favoriteAirport = favoriteAirport;
    }
}

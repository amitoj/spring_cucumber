/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account;

import java.io.Serializable;

/**
 * This class provides hold user related data.
 *
 * @author Alok Gupta
 * @since 2012.04
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
    private String ccExpMonth;
    private String ccExpYear;
    private String securityCode;
    private String billingCountry;
    private String billingAddress;
    private String country;
    private String city;
    private String state;
    private String zipCode;
    private String phoneCountryCode;
    private String billingInformationName;
    private String vmeUsername;
    private String vmePassword;
    /**
     * Information regarding the participant of this travel. Does not have to be
     * the account holder.
     */
    private ParticipantInformation participantInformation;
    private String phoneRegionCode;

    public UserInformation() {
        super();
        this.ccType = "";
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
        this.primaryPhoneNumber = "";
        this.emailId = "";
        this.ccFirstName = "";
        this.ccMiddleName = "";
        this.ccLastName = "";
        this.ccNumber = "";
        this.ccExpMonth = "";
        this.ccExpYear = "";
        this.securityCode = "";
        this.billingCountry = "";
        this.billingAddress = "";
        this.country = "";
        this.city = "";
        this.state = "";
        this.zipCode = "";
        this.phoneCountryCode = "";
        this.billingInformationName = "";
        this.vmeUsername = "";
        this.vmePassword = "";
    }

    public UserInformation(UserInformation userInformation) {
        super();
        this.ccType = userInformation.getCcType();
        this.firstName = userInformation.getFirstName();
        this.middleName = userInformation.getMiddleName();
        this.lastName = userInformation.getLastName();
        this.primaryPhoneNumber = userInformation.getPrimaryPhoneNumber();
        this.emailId = userInformation.getEmailId();
        this.ccFirstName = userInformation.getCcFirstName();
        this.ccMiddleName = userInformation.getCcMiddleName();
        this.ccLastName = userInformation.getCcLastName();
        this.ccNumber = userInformation.getCcNumber();
        this.ccExpMonth = userInformation.getCcExpMonth();
        this.ccExpYear = userInformation.getCcExpYear();
        this.securityCode = userInformation.getSecurityCode();
        this.billingCountry = userInformation.getBillingCountry();
        this.billingAddress = userInformation.getBillingAddress();
        this.country = userInformation.getCountry();
        this.city = userInformation.getCity();
        this.state = userInformation.getState();
        this.zipCode = userInformation.getZipCode();
        this.phoneCountryCode = userInformation.getPhoneCountryCode();
        this.billingInformationName = userInformation.getBillingInformationName();
        this.vmeUsername = userInformation.getVmeUsername();
        this.vmePassword = userInformation.getVmePassword();
    }

    public String getBillingInformationName() {
        return this.billingInformationName;
    }

    public void setBillingInformationName(String billingInformationName) {
        this.billingInformationName = billingInformationName;
    }

    public String getCcType() {
        return this.ccType;
    }

    public void setCcType(String cardType) {
        this.ccType = cardType;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrimaryPhoneNumber() {
        return this.primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public String getEmailId() {
        return this.emailId;
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
        return this.ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpMonth() {
        return this.ccExpMonth;
    }

    public void setCcExpMonth(String ccExpMonth) {
        this.ccExpMonth = ccExpMonth;
    }

    public String getCcExpYear() {
        return this.ccExpYear;
    }

    public void setCcExpYear(String ccExpYear) {
        this.ccExpYear = ccExpYear;
    }

    public String getSecurityCode() {
        return this.securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingAddress() {
        return this.billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneCountryCode() {
        return this.phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public ParticipantInformation getParticipantInformation() {
        return this.participantInformation;
    }

    public void setParticipantInformation(ParticipantInformation participantInformation) {
        this.participantInformation = participantInformation;
    }

    public String getVmeUsername() {
        return vmeUsername;
    }

    public void setVmeUsername(String vmeUsername) {
        this.vmeUsername = vmeUsername;
    }

    public String getVmePassword() {
        return vmePassword;
    }

    public void setVmePassword(String vmePassword) {
        this.vmePassword = vmePassword;
    }

    public void setPhoneRegionCode(String phoneRegionCode) {
        this.phoneRegionCode = phoneRegionCode;
    }

    public String getPhoneRegionCode() {
        return phoneRegionCode;
    }
}

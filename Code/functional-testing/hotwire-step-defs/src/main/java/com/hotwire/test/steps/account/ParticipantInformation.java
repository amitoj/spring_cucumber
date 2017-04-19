/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account;

/**
 * This class represents the participant's information. A participant is one of the people involved in the travel. This
 * may or may not be the account holder
 *
 * @author prbhat
 */
public class ParticipantInformation {
    /**
     * First name of the participant
     */
    private String firstName;

    /**
     * Last name of the participant
     */
    private String lastName;

    /**
     * Participant's country code of the phone number
     */
    private String countryCode;

    /**
     * Participant's phone number without the country code
     */
    private String phoneNumber;

    public ParticipantInformation(String firstName, String lastName, String countryCode, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}

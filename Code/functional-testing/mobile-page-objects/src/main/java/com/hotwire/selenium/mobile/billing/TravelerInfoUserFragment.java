/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.hotwire.selenium.mobile.MobileAbstractPage;

/**
 * This class represents the traveler info user fragment
 */

public class TravelerInfoUserFragment extends MobileAbstractPage {

    // Traveler Info
    @FindBy(className = "submit")
    private WebElement travelerContinueBtn;

    @FindBy(id = "guest")
    private WebElement travelerInformation;

    @FindBy(id = "firstName")
    private WebElement travelerFirstName;

    @FindBy(id = "lastName")
    private WebElement travelerLastName;

    @FindBy(id = "phone")
    private WebElement travelerPhoneNumber;

    public TravelerInfoUserFragment(WebDriver webDriver, String tile) {
        super(webDriver, tile);
    }

    public TravelerInfoUserFragment setTravelerFirstName(String travelerFirstName) {
        this.travelerFirstName.sendKeys(travelerFirstName);
        return this;
    }

    public TravelerInfoUserFragment setTravelerLastName(String travelerLastName) {
        this.travelerLastName.sendKeys(travelerLastName);
        return this;
    }

    public TravelerInfoUserFragment setTravelerPhoneNumber(String travelerPhoneNumber) {
        this.travelerPhoneNumber.sendKeys(travelerPhoneNumber);
        return this;
    }
}

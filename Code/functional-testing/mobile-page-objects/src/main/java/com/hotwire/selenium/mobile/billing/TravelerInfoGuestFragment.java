/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by IntelliJ IDEA.
 * User: v-vyulun
 * Date: 4/6/12
 * Time: 7:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class TravelerInfoGuestFragment extends MobileAbstractPage {

    //Traveler Info
    @FindBy(name = "travelers[0].firstName")
    private WebElement firstName;

    @FindBy (name = "travelers[0].lastName")
    private WebElement lastName;

    @FindBy (name = "phoneCountry")
    private WebElement phoneCountry;

    @FindBy (name = "phoneNumber")
    private WebElement phoneNumber;

    @FindBy (name = "emailAddress")
    private WebElement emailId;

    @FindBy (className = "submit")
    private WebElement travelerContinueBtn;

    public TravelerInfoGuestFragment(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
    }

    public TravelerInfoGuestFragment(WebDriver webDriver, String tile) {
        super(webDriver, tile);
    }

    public void continuePanel() {
        this.travelerContinueBtn.click();
    }

    public TravelerInfoGuestFragment withFirstName(String firstName) {
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
        return this;
    }

    public TravelerInfoGuestFragment withLastName(String lastName) {
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
        return this;
    }

    public TravelerInfoGuestFragment withPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.phoneNumber.sendKeys(primaryPhoneNumber);
        return this;
    }

    public TravelerInfoGuestFragment withEmailId(String emailId) {
        this.emailId.clear();
        this.emailId.sendKeys(emailId);
        return this;
    }

    public TravelerInfoGuestFragment withPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountry.clear();
        this.phoneCountry.sendKeys(phoneCountryCode);
        return this;
    }
}

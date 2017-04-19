/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author v-jolopez
 */
public class MobileCarTravelerInfoUserFragment  extends MobileAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileCarTravelerInfoUserFragment.class.getName());

    //Traveler Info
    @FindBy (name = "isDriverOldEnough")
    private WebElement isDriverOldEnough;

    @FindBy (name = "acceptsCreditCardOnlyOrDebitLocalTerms")
    private WebElement acceptsCreditCardOnlyOrDebitLocalTerms;

    @FindBy (className = "submit")
    private WebElement travelerContinueBtn;

    @FindBy(id = "firstName")
    private WebElement firstName;
    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "option1")
    private WebElement overAge25;

    @FindBy(id = "option2")
    private WebElement understandCCCheck;

    @FindBy(id = "phone")
    private WebElement phone;

    @FindBy(id = "guest")
    private WebElement primaryDriverList;


    public MobileCarTravelerInfoUserFragment(WebDriver webDriver, String tile) {
        super(webDriver, tile);
    }

    private boolean isExistingDriverList() {

        try {
            if (primaryDriverList.isDisplayed()) {
                return true;
            }

        }
        catch (NoSuchElementException e) {
            return false;
        }
        return false;
    }
    public MobileCarTravelerInfoUserFragment withFirstName(String firstName) {
        if (!isExistingDriverList()) {
            this.firstName.clear();
            this.firstName.sendKeys(firstName);
        }
        return this;
    }
    public MobileCarTravelerInfoUserFragment withLastName(String lastName) {
        if (!isExistingDriverList()) {
            this.lastName.clear();
            this.lastName.sendKeys(lastName);
        }
        return this;
    }

    public MobileCarTravelerInfoUserFragment withPhone(String phone) {
        this.phone.clear();
        this.phone.sendKeys(phone);
        return this;
    }
    public MobileCarTravelerInfoUserFragment withOption() {
        if (!this.overAge25.isSelected()) {
            this.overAge25.click();
        }
        return this;
    }
    public void continuePanel() {
        if (isAcceptsCreditCardOnlyOrDebitLocalTermsPresent()) {
            this.acceptsCreditCardOnlyOrDebitLocalTerms.click();
        }

        try {
            if (!this.understandCCCheck.isSelected()) {
                this.understandCCCheck.click();
            }
        }
        catch (NoSuchElementException e) {
            LOGGER.info("option 2 is not displayed for opaque.");
        }
        this.travelerContinueBtn.click();
    }

    /**
     * Function checks presence of acceptsCreditCardOnlyOrDebitLocalTerms since this Web Element is conditional on
     * options provided by car agency.
     * @return  true or false
     */
    private boolean isAcceptsCreditCardOnlyOrDebitLocalTermsPresent() {
        try {
            this.acceptsCreditCardOnlyOrDebitLocalTerms.getTagName();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}





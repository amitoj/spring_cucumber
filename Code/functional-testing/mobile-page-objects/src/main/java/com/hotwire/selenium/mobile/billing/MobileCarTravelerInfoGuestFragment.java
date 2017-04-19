/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.NoSuchElementException;
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
public class MobileCarTravelerInfoGuestFragment extends MobileAbstractPage {

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

    @FindBy (name = "isDriverOldEnough")
    private WebElement isDriverOldEnough;

    @FindBy (name = "acceptsCreditCardOnlyOrDebitLocalTerms")
    private WebElement acceptsCreditCardOnlyOrDebitLocalTerms;

    @FindBy (className = "submit")
    private WebElement travelerContinueBtn;


    public MobileCarTravelerInfoGuestFragment(WebDriver webDriver, String tile) {
        super(webDriver, tile);
    }

    public void continuePanel() {

        this.isDriverOldEnough.click();

        if (isAcceptsCreditCardOnlyOrDebitLocalTermsPresent()) {
            this.acceptsCreditCardOnlyOrDebitLocalTerms.click();
        }

        this.travelerContinueBtn.click();
    }

    public MobileCarTravelerInfoGuestFragment withFirstName(String firstName) {
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
        return this;
    }

    public MobileCarTravelerInfoGuestFragment withLastName(String lastName) {
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
        return this;
    }

    public MobileCarTravelerInfoGuestFragment withPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.phoneNumber.clear();
        this.phoneNumber.sendKeys(primaryPhoneNumber);
        return this;
    }

    public MobileCarTravelerInfoGuestFragment withEmailId(String emailId) {
        this.emailId.clear();
        this.emailId.sendKeys(emailId);
        return this;
    }

    public MobileCarTravelerInfoGuestFragment withPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountry.clear();
        this.phoneCountry.sendKeys("1");
        return this;
    }

    /**
     * Function checks presence of acceptsCreditCardOnlyOrDebitLocalTerms since this Web Element is conditional on
     * options provided by car agency.
     *
     * @return true or false
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

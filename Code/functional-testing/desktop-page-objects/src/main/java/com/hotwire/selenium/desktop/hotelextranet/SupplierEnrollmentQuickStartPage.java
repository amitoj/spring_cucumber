/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The supplier enrollment quick start form page object.
 */
public class SupplierEnrollmentQuickStartPage extends SupplierEnrollmentBaseForm {
    private static final String FIRST_NAME_ERROR_ID = "tfa_FirstName-D-E";
    private static final String LAST_NAME_ERROR_ID = "tfa_LastName-D-E";
    private static final String EMAIL_ERROR_ID = "tfa_Email-D-E";
    private static final String EMAIL_FORMAT_ERROR_ID = "tfa_Email-E";

    @FindBy(id = "tfa_FirstName")
    private WebElement firstName;

    @FindBy(id = "tfa_LastName")
    private WebElement lastName;

    @FindBy(id = "tfa_Email")
    private WebElement email;

    @FindBy(id = "wfPageNextId1")
    private WebElement nextButton;

    @FindBy(id = FIRST_NAME_ERROR_ID)
    private WebElement firstNameError;

    @FindBy(id = LAST_NAME_ERROR_ID)
    private WebElement lastNameError;

    @FindBy(id = EMAIL_ERROR_ID)
    private WebElement emailError;

    @FindBy(id = EMAIL_FORMAT_ERROR_ID)
    private WebElement emailFormatError;

    public SupplierEnrollmentQuickStartPage(WebDriver webdriver) {
        super(webdriver);
    }

    public void typeFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
    }

    public void typeLastName(String lastName) {
        this.lastName.sendKeys(lastName);
    }

    public void typeEmail(String email) {
        this.email.sendKeys(email);
    }

    public String getFirstNameErrorId() {
        return FIRST_NAME_ERROR_ID;
    }

    public String getLastNameErrorId() {
        return LAST_NAME_ERROR_ID;
    }

    public String getEmailErrorId() {
        return EMAIL_ERROR_ID;
    }

    public String getEmailFormatErrorId() {
        return EMAIL_FORMAT_ERROR_ID;
    }

    public boolean isFirstNameErrorDisplayed() {
        return firstNameError.isDisplayed();
    }

    public boolean isLastNameErrorDisplayed() {
        return lastNameError.isDisplayed();
    }

    public boolean isEmailErrorDisplayed() {
        return emailError.isDisplayed();
    }

    public boolean isEmailFormatErrorDisplayed() {
        return emailFormatError.isDisplayed();
    }

    @Override
    public void fillRequiredInputAndSubmit(List<String> inputs) {
        typeFirstName(inputs.get(0));
        typeLastName(inputs.get(1));
        typeEmail(inputs.get(2));
        clickNextButton();
    }

    @Override
    public void clickNextButton() {
        nextButton.click();
    }
}

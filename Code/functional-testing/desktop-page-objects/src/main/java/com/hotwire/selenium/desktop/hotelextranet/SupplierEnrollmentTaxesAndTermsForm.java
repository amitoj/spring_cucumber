/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Supplier enrollment taxes and supplier terms and conditions form page object.
 */
public class SupplierEnrollmentTaxesAndTermsForm extends SupplierEnrollmentBaseForm {
    @FindBy(id = "tfa_674")
    private WebElement agreeToTermsCheckbox;

    @FindBy(id = "tfa_620")
    private WebElement fullNameConfirmation;

    @FindBy(className = "primaryAction")
    private WebElement submitButton;

    public SupplierEnrollmentTaxesAndTermsForm(WebDriver webdriver) {
        super(webdriver);
    }

    public void checkAgreeToTermsCheckbox() {
        if (!agreeToTermsCheckbox.isSelected() && agreeToTermsCheckbox.isEnabled()) {
            agreeToTermsCheckbox.click();
        }
    }

    public void uncheckAgreeToTermsCheckbox() {
        if (agreeToTermsCheckbox.isSelected() && agreeToTermsCheckbox.isEnabled()) {
            agreeToTermsCheckbox.click();
        }
    }

    public void typeFullNameConfirmation(String fullName) {
        fullNameConfirmation.sendKeys(fullName);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    @Override
    public void fillRequiredInputAndSubmit(List<String> inputs) {
        fillRequiredInputAndSubmit(inputs, true);
    }

    public void fillRequiredInputAndSubmit(List<String> inputs, boolean checkAgreeToTerms) {
        if (checkAgreeToTerms) {
            checkAgreeToTermsCheckbox();
        }
        else {
            uncheckAgreeToTermsCheckbox();
        }
        typeFullNameConfirmation(inputs.get(0));
        clickNextButton();
    }

    @Override
    public void clickNextButton() {
        clickSubmitButton();
    }
}

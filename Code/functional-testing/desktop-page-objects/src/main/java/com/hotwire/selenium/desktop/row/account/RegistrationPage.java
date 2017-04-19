/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.account;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.row.elements.Button;
import com.hotwire.selenium.desktop.row.elements.Checkbox;
import com.hotwire.selenium.desktop.row.elements.Input;
import com.hotwire.selenium.desktop.row.elements.Label;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends AbstractRowPage {

    @FindBy(id = "firstName")
    private Input firstName;

    @FindBy(id = "lastName")
    private Input lastName;

    @FindBy(id = "countryCode")
    private Select countryCode;

    @FindBy(id = "postalCode")
    private Input postalCode;

    @FindBy(css = "div.registerModule input#email")
    private Input email;

    @FindBy(css = "div.registerModule input#password")
    private Input password;

    @FindBy(id = "confirmPassword")
    private Input confirmPassword;

    @FindBy(id = "dbmSubscription1")
    private Checkbox subscription;

    @FindBy(css = "label[for='dbmSubscription1']")
    private Label subscriptionLabel;

    @FindBy(id = "privacyPolicyConfirm")
    private Checkbox privacyPolicyConfirm;

    @FindBy(css = "div.registerModule button[type='submit']")
    private Button registerButton;

    public RegistrationPage(WebDriver webdriver) {
        super(webdriver, "tile.account.login");
    }

    public RegistrationPage setFirstName(String firstName) {
        this.firstName.setText(firstName);
        return this;
    }

    public RegistrationPage setLastName(String lastName) {
        this.lastName.setText(lastName);
        return this;
    }

    public RegistrationPage selectCountry(String country) {
        this.countryCode.selectByVisibleText(country);
        return this;
    }

    public RegistrationPage setPostalCode(String postalCode) {
        this.postalCode.setText(postalCode);
        return this;
    }

    public RegistrationPage setEmail(String email) {
        this.email.setText(email);
        return this;
    }

    public RegistrationPage setPassword(String password) {
        this.password.setText(password);
        return this;
    }

    public RegistrationPage setPasswordConfirmation(String password) {
        this.confirmPassword.setText(password);
        return this;
    }

    public boolean getSubscriptionState() {
        return this.subscription.isChecked();
    }

    public RegistrationPage setSubscriptionState(Boolean subscribe) {
        if (subscribe) {
            subscription.check();
        }
        else {
            subscription.uncheck();
        }
        return this;
    }

    public boolean getPrivacyPolicyConfirmState() {
        try {
            return privacyPolicyConfirm.isChecked();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public RegistrationPage setPrivacyPolicyConfirm(Boolean confirm) {
        if (confirm) {
            if (!getPrivacyPolicyConfirmState()) {
                privacyPolicyConfirm.check();
            }
        }
        else {
            if (getPrivacyPolicyConfirmState()) {
                privacyPolicyConfirm.uncheck();
            }
        }
        return this;
    }

    public String getSubscriptionProposal() {
        return subscriptionLabel.getText();
    }

    public void register() {
        registerButton.click();
    }
}

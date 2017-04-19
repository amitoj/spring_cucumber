/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Created by IntelliJ IDEA.
 * User: v-edjafarov
 * Date: Nov 10, 2011
 * Time: 6:34:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccountInfoPage extends AbstractAccountPage {

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "confirmEmail")
    private WebElement confirmEmailInput;

    @FindBy(xpath = "//form[contains(@action,'updateemail')]//button")
    private WebElement saveEmailChangesButton;

    @FindBy(xpath = "//form[contains(@action,'updateemail')]//div[text()='Confirmation']")
    private WebElement saveEmailConfirmationText;

    @FindBy(xpath = "//form[contains(@action,'updateemail')]//label[text()='Current email']/following-sibling::div[1]")
    private WebElement currentEmailText;

    @FindBy(id = "oldPassword")
    private WebElement oldPasswordInput;

    @FindBy(id = "newPasswordsModel.newPassword")
    private WebElement newPasswordInput;

    @FindBy(id = "newPasswordsModel.confirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//form[contains(@action,'updatepassword')]//button")
    private WebElement savePasswordChangesButton;

    @FindBy(xpath = "//form[contains(@action,'updatepassword')]//div[text()='Confirmation']")
    private WebElement savePasswordConfirmationText;

    @FindBy(xpath = "//div[contains(@class,'errorMessage')]//*[contains(text(),'Password must contain')" +
            " or contains(text(),'The two passwords need to match')]")
    private WebElement invalidPasswordErrorText;

    @FindBy(xpath = ".//li[contains(text(), 'Your email address has been updated.') ]")
    private WebElement changeEmailSuccessText;

    public AccountInfoPage(WebDriver driver) {
        super(driver, "tile.account.accountInfo");
    }

    public void changeEmail(String newEmail) {
        emailInput.sendKeys(newEmail);
        confirmEmailInput.sendKeys(newEmail);
        saveEmailChangesButton.submit();
    }

    public boolean isEmailSuccessfullyChanged() {
        return changeEmailSuccessText.isDisplayed();
    }

    public boolean isEmailConfirmationVisible() {
        return saveEmailConfirmationText.isDisplayed();
    }

    public String getCurrentEmail() {
        return currentEmailText.getText();
    }

    public void changePassword(String oldPassword, String newPassword) {
        oldPasswordInput.sendKeys(oldPassword);
        newPasswordInput.sendKeys(newPassword);
        confirmPasswordInput.sendKeys(newPassword);
        savePasswordChangesButton.submit();
    }

    public void changePassword(String oldPassword, String newPassword, String confirmPassword) {
        oldPasswordInput.sendKeys(oldPassword);
        newPasswordInput.sendKeys(newPassword);
        confirmPasswordInput.sendKeys(confirmPassword);
        savePasswordChangesButton.submit();
    }



    public boolean isPasswordChangedConfirmationVisible() {
        return savePasswordConfirmationText.isDisplayed();
    }

    public boolean isPasswordErrorVisible() {
        return invalidPasswordErrorText.isDisplayed();
    }
}

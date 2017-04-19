/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.hotelextranet;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.ExtendedSelect;

/**
 * @author adeshmukh
 *
 */
public class ExtranetUserPemissionsPage extends AbstractUSPage {


    @FindBy(css = "a[href*='=addUserType']")
    private WebElement addNewUser;

    @FindBy(name = "firstName")
    private WebElement firstNameField;

    @FindBy(name = "lastName")
    private WebElement lastNameField;

    @FindBy(name = "_NAE_email")
    private WebElement emailField;

    @FindBy(name = "_NAE_emailAgain")
    private WebElement confirmEmailField;

    @FindBy(name = "phone")
    private WebElement phoneField;

    @FindBy(name = "accessCode")
    private WebElement permissionDropDown;

    @FindBy(css = "button.btn.blueBt")
    private WebElement saveButton;

    @FindBy(linkText = "Change")
    private WebElement changePemission;

    @FindBy(linkText = "Delete")
    private WebElement deleteUser;

    @FindBy(linkText = "Users and permissions")
    private WebElement userPermissions;

    @FindBy(css = "div.errorMessages.confirmationMessage")
    private WebElement confirmation;

    /**
     * @param webdriver
     */
    public ExtranetUserPemissionsPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.xnet.edit-user-roles"});
    }


    public void test(String addUpdateDelete, String firstName, String lastName, String email, String phone,
        String permissionLevel) {
        getWebDriver().navigate().refresh();
        System.out.println(addUpdateDelete);
        System.out.println(firstName);
        System.out.println(phone);

    }

    public void addUser(String firstName, String lastName, String email, String phone, String permissionLevel) {
        addNewUser.click();
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        emailField.clear();
        emailField.sendKeys(email);
        confirmEmailField.clear();
        confirmEmailField.sendKeys(email);
        phoneField.clear();
        phoneField.sendKeys(phone);
        ExtendedSelect select = new ExtendedSelect(permissionDropDown);
        select.selectIfVisibleTextContainsText(permissionLevel);
        saveButton.click();
    }

    public void changeUserPermissions(String permissionLevel) {
        changePemission.click();
        ExtendedSelect select = new ExtendedSelect(permissionDropDown);
        select.selectIfVisibleTextContainsText(permissionLevel);
        saveButton.click();
    }

    public void deleteUser() {
        deleteUser.click();
    }

    public boolean validateConfirmationMessage() {
        new WebDriverWait(getWebDriver(), 20).until(PageObjectUtils.webElementVisibleTestFunction(confirmation, true));
        List<WebElement> message = getWebDriver().findElements(By.cssSelector("div.errorMessages.confirmationMessage"));
        if (message.size() == 1) {
            return true;
        }
        return false;
    }
}

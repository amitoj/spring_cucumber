/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-ozelenov
 * Date: 8/28/13
 * Time: 6:38 AM
 */
public class PasswordAssistancePage  extends AbstractPageObject {

    @FindBy(css = "input#email")
    private WebElement email;

    @FindBy(xpath = "//input[@name='_NAE_newPassword']")
    private WebElement password;

    @FindBy(xpath = "//input[@name='_NAE_confirmPassword']")
    private WebElement confirmPassword;

    @FindBy(css = ".btn")
    private WebElement btnConfirmNewPassword;

    @FindBy(css = ".btn.opacityCode-O")
    private WebElement btnContinue;

    @FindBy(css = "a.btn")
    private WebElement btnCancel;

    @FindBy(css = ".hurryGuestLink, .passwdAssistance>form>a")
    private WebElement inAHurryLink;

    public PasswordAssistancePage(WebDriver webdriver) {
        super(webdriver,
                new String[] {
                    "tile.account.pwdassistance",
                    "tiles-def.account.forgot-password",
                    "tiles-def.account.forgot-password-login"
                }
        );
    }

    public void continuePage() {
        this.btnContinue.submit();
    }

    public void inAHurryLinkClick() {
        this.inAHurryLink.click();
    }

    public void cancelPage() {
        this.btnCancel.click();
    }

    public void setEmail(String email) {
        this.email.sendKeys(email);
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getConfirmPassword() {
        return confirmPassword;
    }

    public WebElement getBtnConfirmNewPassword() {
        return btnConfirmNewPassword;
    }

    public void setPassword(String password) {
        getPassword().sendKeys(password);
    }

    public void setConfirmPassword(String confirmPassword) {
        getConfirmPassword().sendKeys(confirmPassword);
    }

    public String getEmailField() {
        return this.email.getAttribute("value");
    }

    public void enterEmailToRestore(String email) {
        this.setEmail(email);
        this.continuePage();
    }

    public void enterPasswordToReset(String password, String confirmationPassword) {
        setPassword(password);
        setConfirmPassword(confirmationPassword);
        getBtnConfirmNewPassword().click();
    }
}

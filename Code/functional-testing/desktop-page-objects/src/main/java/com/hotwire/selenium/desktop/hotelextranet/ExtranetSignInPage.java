/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.hotelextranet;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * SingIn page
 */
public class ExtranetSignInPage extends AbstractPageObject {

    @FindBy(name = "_NAE_email")
    private WebElement username;

    @FindBy(css = "form[name=xnetLoginForm] button")
    private WebElement submitButton;

    @FindBy(css = "div.errorMessages")
    private WebElement authenticationError;

    public ExtranetSignInPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.xnet.login"});
    }

    /**
     * Attempt to login Xnet with the parameters
     *
     * @param username
     * @param password
     * @return
     */
    public void xnetSignIn(String username, String password) {

        sendKeys(this.username, username);
        /**
         * IE9 driver does not interact with hidden elements.
         * As the password field has the style="display: none" we need to make it visible.
         */
        ((JavascriptExecutor) getWebDriver())
                .executeScript("$(\"input[name='_NAE_password']\").val(\"" + password + "\");");

        this.submitButton.click();
    }
}

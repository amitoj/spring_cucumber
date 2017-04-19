/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.row.authentication;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Daniel Ilyuschenko (v-dilyuschenko)
 * @since 2012.06
 */
public class LoginPage extends AbstractRowPage {

    private static final String TILES_DEF = "tile.account.login";
    private static final String XPATH_SIGN_IN_BUTTON = "//div[starts-with(@class,'signInModule') " +
            "or starts-with(@class, 'logInModule')]//div[@class='buttons']/button";

    /**
     * <div class="errorMessages">
     * <h6>Error</h6>
     * <p>Please correct the following field(s):</p>
     * <ul>
     * <li><b class="bold">Email address</b> is not valid.</li>
     * </ul>
     * </div>
     */
    @FindBy(css = "div.errorMessages")
    private WebElement authenticationError;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(xpath = "//div[@id='tileName-A2']//input[@id='email']")
    private WebElement guestEmail;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "lastDigitsOfCC")
    private WebElement lastDigits;

    @FindBy(className = "fieldError")
    private List<WebElement> errorFields;

    @FindBy(xpath = XPATH_SIGN_IN_BUTTON)
    private WebElement signInButton;

    @FindBy(xpath = "//div[@id='tileName-A2']//button")
    private WebElement guestSignIn;


    public LoginPage(WebDriver webdriver) {
        super(webdriver, TILES_DEF);
    }

    /**
     * Attempt to login with the parameters
     *
     * @param email
     * @param password
     * @return
     */
    @SuppressWarnings("unused")
    private void signIn(String email, String password) {
        this.email.clear();
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        this.signInButton.click();
    }

    public void signInAsGuest(String gEmail, String digits) {
        this.guestEmail.clear();
        this.guestEmail.sendKeys(gEmail);

        this.lastDigits.clear();
        this.lastDigits.sendKeys(digits);

        this.guestSignIn.click();
    }

    /**
     * or gives you a chance to inspect error strings.
     *
     * @return
     */
    public boolean hasAuthenticationError() {
        // this only authenticates one part -- which is to check if the field has error
        // we need to also check the message as the next step
        return errorFields.contains(email);
    }

}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us;

import com.hotwire.selenium.desktop.account.AccountTripsPage;
import com.hotwire.selenium.desktop.account.PasswordAssistancePage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.widget.JQueryDatepicker;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.ehoffman.testing.fest.webdriver.WebElementAssert.assertThat;

/**
 * Page Object for signing in to My Account
 */
public class SignInPage extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignInPage.class);

    @FindBy(css = "[name='_NAE_emailLogin'], .logInModule input[id='email']")
    private WebElement username;

    @FindBy(css = "[name='_NAE_passwordLogin'], .logInModule input[id='password']")
    private WebElement password;

    @FindBy(css = "[id='hwSignIn'], .logInModule button.btn")
    private WebElement submitButton;

    @FindBy(xpath = ".//*[@id='signInBox']/form/button")
    private WebElement btnSignIn;

    @FindBy(css = "div.errorMessages .msgBoxBody")
    private WebElement authenticationError;

    @FindBy(xpath = "//A[contains(text(),'Password assistance')]")
    private WebElement lnkPasswordAssistance;

    @FindBy(xpath = "//li[contains(text()," +
            "'The email address or last five digits of your credit card is incorrect')]")
    private WebElement loginErrorMessage;

    //Find Reservation section
    @FindBy(css = ".tripFinderModule.grayOuterBox #email")
    private WebElement email4RF;

    @FindBy(css = ".tripFinderModule.grayOuterBox #lastDigitsOfCC")
    private WebElement last5digitsCC;

    @FindBy(css = ".tripFinderModule.grayOuterBox .btn")
    private WebElement continueButton4RF;

    @FindBy(css = "#travelStartDate-field")
    private WebElement tripdate;

    @FindBy(css = "div.errorMessages ul li")
    private WebElement errorMessage;

    public SignInPage(WebDriver webdriver) {
        super(webdriver, new String[]{"tile.account.login"});
    }

    public void signIn() {
        this.submitButton.click();
    }

    public boolean isLogginErrorMessageDisplayed() {
        try {
            return loginErrorMessage.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public SignInPage withUserName(String username) {
        sendKeys(this.username, username);
        return this;
    }

    public SignInPage withPassword(String password) {
        sendKeys(this.password, password);
        return this;
    }

    public boolean hasAuthenticationError() {
        return hasAuthenticationError("Please correct the following:") &&
                hasAuthenticationErrorOnUsername();
    }

    public boolean hasAuthenticationError(String message) {
        try {
            assertThat(authenticationError).isNotHidden().isDisplayed().textContains(message);
            // verify fields are red, and are email or password fields
            return true;
        }
        catch (AssertionError error) {
            error.printStackTrace();
            return false;
        }
    }

    public boolean hasAuthenticationErrorOnUsername() {
        return hasAuthenticationError("Please correct the following:") ||
                hasAuthenticationError("Email address is not valid.");
    }

    public boolean hasAuthenticationErrorOnPassword() {
        return hasAuthenticationError("Please correct the following:") ||
                hasAuthenticationError("Enter a password.");
    }

    public PasswordAssistancePage passwordAssistancePage() {
        this.lnkPasswordAssistance.click();
        return new PasswordAssistancePage(getWebDriver());
    }

    public AccountTripsPage findYourReservation(String email, String last5digits) {
        try {
            Thread.sleep(5000);  // for stable work
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("Search reservation for a '" + email.toLowerCase() +
                "' and for '" + last5digits + "' last five digits");
        this.email4RF.sendKeys(email.toLowerCase());
        this.last5digitsCC.sendKeys(last5digits);
        this.continueButton4RF.submit();
        return new AccountTripsPage(getWebDriver());
    }

    public void findYourReservationWithInvalidCard(String email) {
        try {
            Thread.sleep(5000);  // for stable work
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        String last5digits = "11112";
        LOGGER.info("Search reservation for a '" + email.toLowerCase() +
                "' and for '" + last5digits + "' last five digits");
        this.email4RF.sendKeys(email.toLowerCase());
        this.last5digitsCC.sendKeys(last5digits);
        this.continueButton4RF.submit();
        assertThat(this.isLogginErrorMessageDisplayed()).as("Error message wasn't displayed").isTrue();
    }

    public void findYourReservationViaTripDate(String email, Date tripDate) {
        LOGGER.info("Search reservation for a '" + email.toLowerCase() +
                "' and for '" + tripDate.toString() + "' dates");
        this.email4RF.sendKeys(email.toLowerCase());
        this.tripdate.click();
        JQueryDatepicker jQueryDatepicker = new JQueryDatepicker(getWebDriver(), this.tripdate);
        jQueryDatepicker.setDate(tripDate);
        this.continueButton4RF.submit();
    }

    public void verifySignInPage() {
        assertThat(btnSignIn.isDisplayed()).isTrue();
        assertThat(username.isDisplayed()).isTrue();
        assertThat(password.isDisplayed()).isTrue();

    }

    public String getErrorMessages() {
        return errorMessage.getText();
    }

    public GlobalHeader getGlobalHeader() {
        return new GlobalHeader(getWebDriver());
    }
}

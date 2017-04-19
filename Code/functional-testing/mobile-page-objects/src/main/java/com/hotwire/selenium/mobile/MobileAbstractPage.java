/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile;

import static org.ehoffman.testing.fest.webdriver.WebElementAssert.assertThat;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.hotwire.selenium.mobile.account.MobileMyAccountPage;
import com.hotwire.selenium.mobile.account.SignInPage;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.po.AbstractPage;

/**
 * Abstract page of mobile pages
 */
public class MobileAbstractPage extends AbstractPage {
    public static final long TIME_TO_WAIT = 10;
    public static int MAX_SEARCH_PAGE_WAIT_SECONDS = 70;

    @FindBy(partialLinkText = "Contact")
    private WebElement contact;

    @FindBy(partialLinkText = "Feedback")
    private WebElement feedback;

    @FindBy(xpath = "//*[@data-bdd='footer-link-signinout']")
    private WebElement signInOut;

    @FindBy(css = "div.errorMessages")
    private WebElement formError1;

    @FindBy(css = "div.errors")
    private WebElement formError2;

    @FindBy(xpath = "//*[@data-bdd='footer-link-myaccount']")
    private WebElement myAccount;

    public MobileAbstractPage(WebDriver webdriver) {
        super(webdriver);
    }

    public MobileAbstractPage(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
    }

    public MobileAbstractPage(WebDriver webdriver, String[] expectedPageNames) {
        super(webdriver, expectedPageNames);
    }

    public MobileAbstractPage(WebDriver webdriver, String[] expectedPageNames, long pageNameTimeout) {
        super(webdriver, expectedPageNames, pageNameTimeout);
    }

    public MobileAbstractPage(WebDriver webdriver, String expectedPageName) {
        super(webdriver, expectedPageName);
    }

    public MobileAbstractPage(WebDriver webdriver, String expectedPageName, long pageNameTimeout) {
        super(webdriver, expectedPageName, pageNameTimeout);
    }

    public MobileAbstractPage(WebDriver webDriver, By containerLocator, Function<WebDriver, ?> precondition) {
        super(webDriver, containerLocator, precondition);
    }

    public MobileAbstractPage(WebDriver webdriver, String expectedPageName, String searchLayerId, int maxWaitSecs) {
        super(webdriver, expectedPageName, new InvisibilityOf(By.cssSelector(searchLayerId)), maxWaitSecs);
    }

    public MobileAbstractPage(WebDriver webdriver, String expectedPageName, String searchLayerId, Long pageNameTimeout,
                              int maxWaitSecs) {
        super(webdriver, expectedPageName, new InvisibilityOf(By.cssSelector(searchLayerId)), pageNameTimeout,
              maxWaitSecs);
    }

    public MobileAbstractPage(WebDriver webdriver, String expectedPageName, ExpectedCondition<Boolean> condition,
                              int maxWaitSecs) {
        super(webdriver, expectedPageName, condition, maxWaitSecs);
    }

    public MobileAbstractPage(WebDriver webdriver, String expectedPageName, ExpectedCondition<Boolean> condition,
                              Long pageNameTimeout, int maxWaitSecs) {
        super(webdriver, expectedPageName, condition, pageNameTimeout, maxWaitSecs);
    }

    public boolean signedIn() {
        String linkText = "LINK TEXT: " + this.signInOut.getText();
        LoggerFactory.getLogger(MobileAbstractPage.class.getSimpleName()).info(linkText);

        if ("Sign out".equals(this.signInOut.getText())) {
            return true;
        }
        if ("Sign in".equals(this.signInOut.getText())) {
            return false;
        }
        throw new RuntimeException("Invalid page object");
    }

    public SignInPage navigateToSignInOrRegister() {
        this.signInOut.click();
        return new SignInPage(getWebDriver());
    }

    public void signOut() {
        if (signedIn()) {
            this.signInOut.click();
            Alert alertOut = getWebDriver().switchTo().alert();
            alertOut.accept();
        }
    }

    public boolean assertHasFormErrors(String message) {
        return assertHasFormErrors(message, getTheFormErrorOnPage());
    }

    private WebElement getTheFormErrorOnPage() {
        // Some pages on mobile use css "errorMessages" while others use "errors"
        return formError1.isDisplayed() ? formError1 : formError2;
    }

    public String getErrorText() {
        try {
            return  formError1.getText();
        }
        catch (WebDriverException e) {
            return formError2.getText();
        }
    }

    public MobileMyAccountPage navigateToMyAccountPage() {
        myAccount.click();
        return new MobileMyAccountPage(super.getWebDriver());
    }

    public boolean assertHasFormErrors(String message, WebElement webElement) {
        try {
            assertThat(webElement).isNotHidden().isDisplayed().textContains(message);
            // TO DO verify fields are red, and are email or password fields
            return true;
        }
        catch (AssertionError error) {
            error.printStackTrace();
            return false;
        }
    }
}

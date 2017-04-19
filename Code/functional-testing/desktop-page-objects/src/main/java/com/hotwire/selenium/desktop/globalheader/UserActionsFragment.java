/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.globalheader;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * User: v-vzyryanov
 * Date: 9/19/14
 * Time: 1:45 AM
 */
public class UserActionsFragment extends AbstractPageObject {

    private static final String CSS_USER_ACTIONS = ".headerSignIn #userActions";
    private static final String CSS_SIGN_IN_ACTION = "a#headerSignIn";
    private static final String CSS_ACCOUNT_OVERVIEW_ACTION = "a[href$='account/overview']";
    private static final String CSS_ACCOUNT_TRIPS = "a[href$='account-unverified/trips']";
    private static final String CSS_LOGOUT = "a[href$='account-unverified/logout']";
    private static final String CSS_ACTIONS = "div.action a";
    private static final String CSS_EXPRESS_LINK = "a.expressLink";

    @FindBy(css = CSS_SIGN_IN_ACTION)
    private WebElement signInAction;

    @FindBy(css = CSS_ACCOUNT_OVERVIEW_ACTION)
    private WebElement myAccountOverview;

    @FindBy(css = CSS_ACCOUNT_TRIPS)
    private WebElement myAccountTrips;

    @FindBy(css = CSS_LOGOUT)
    private WebElement logout;

    @FindBy(css = CSS_EXPRESS_LINK)
    private WebElement expressLink;

    public UserActionsFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(CSS_USER_ACTIONS));
        /**
         * Wait until the user menu is expanded
         */
        new WebDriverWait(getWebDriver(), getTimeout())
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(CSS_ACTIONS)));
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(CSS_USER_ACTIONS + " div.action a");
    }

    public void navigateToNewUser() {
        signInAction.click();
    }

    public void navigateToSignInPage() {
        signInAction.click();
    }

    public void navigateToMyAccount() {
        myAccountOverview.click();
    }

    public void navigateToMyTripsAsSignedInUser() {
        myAccountTrips.click();
    }

    public void logout() {
        logout.click();
    }

    public boolean isExpressLinkDisplayed() {
        try {
            return expressLink.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

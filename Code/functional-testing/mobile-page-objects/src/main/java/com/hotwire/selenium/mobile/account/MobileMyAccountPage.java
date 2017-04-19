/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.account;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.account.mytrips.MobileTripSummaryPage;

/**
 * Created by IntelliJ IDEA. User: v-vyulun Date: 3/29/12 Time: 1:04 PM To change this template use File | Settings |
 * File Templates.
 */
public class MobileMyAccountPage extends MobileAbstractPage {
    private static final String NAV_BUTTONS = "nav.btns a";
    // WebElements
    @FindBy(xpath = "//img[@alt='Back']")
    private WebElement back;

    @FindBy(xpath = "//img[@alt='Home']")
    private WebElement home;

    @FindBy(css = NAV_BUTTONS)
    private List<WebElement> navButtons;

    @FindBy(css = "a[data-bdd='footer-link-signinout']")
    private WebElement signOut;

    @FindBy(xpath = "//*[@data-bdd='footer-link-myaccount']")
    private WebElement myAccountLink;

    @FindBy(xpath = "//*[@data-bdd='email-id']")
    private WebElement myEmailID;

    @FindBy(xpath = "//*[@data-bdd='last-fivedigitsofcreditcard']")
    private WebElement myLastFiveDigits;

    @FindBy(xpath = "//*[@data-bdd='continue']")
    private WebElement continueButton;

    @FindBy(id = "email")
    private WebElement username;

    @FindBy(id = "cc-opt")
    private WebElement cardnumberSelect;

    public MobileMyAccountPage(WebDriver webdriver) {
        super(webdriver);
    }

    public MobileTripSummaryPage navigateToMyTripsPage() {
        getNavButtonByHrefSuffix("mybookedtrips").click();
        return new MobileTripSummaryPage(getWebDriver());
    }

    public MobileAccountEmailAddressPage navigateToEmailAddressPage() {
        getNavButtonByHrefSuffix("myemail").click();
        return new MobileAccountEmailAddressPage(getWebDriver());
    }

    public MobileChangePasswordPage navigateToChangePasswordPage() {
        getNavButtonByHrefSuffix("mypassword").click();
        return new MobileChangePasswordPage(getWebDriver());
    }

    public MobileAccountEmailSubscriptionsPage navigateToAccountEmailSubscriptionsPage() {
        getNavButtonByHrefSuffix("mydiv-subscriptions").click();
        return new MobileAccountEmailSubscriptionsPage(getWebDriver());
    }

    public void clickOnMyAccount() {
        myAccountLink.click();
    }

    public void loginWithCreditcardNumber(String emailid, String creditcardnumber) {
        myEmailID.click();
        myEmailID.sendKeys(emailid);
        cardnumberSelect.click();
        myLastFiveDigits.click();
        myLastFiveDigits.sendKeys(creditcardnumber);
        continueButton.click();
    }

    private WebElement getNavButtonByHrefSuffix(String hrefSuffix) {
        for (WebElement element : navButtons) {
            if (element.getAttribute("href").endsWith(hrefSuffix)) {
                return element;
            }
        }
        throw new NoSuchElementException("No such navigation WebElement with href ending with " + hrefSuffix);
    }
}

/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.authentication;

import static org.fest.assertions.Assertions.assertThat;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.account.MobileMyAccountPage;
import com.hotwire.selenium.mobile.account.SignInPage;

/**
 * AuthenticationModel implementation for mobile site.
 */
public class AuthenticationModelMobileWebApp extends AuthenticationModelTemplate {

    @Override
    public void attemptToAuthenticate(AuthenticationParameters parameters) {
        SignInPage signInPage;
        if (MobileAbstractPage.getCurrentPage() != null) {
            signInPage = ((MobileAbstractPage) MobileAbstractPage.getCurrentPage()).navigateToSignInOrRegister();
        }
        else {
            signInPage = new MobileHotwireHomePage(getWebdriverInstance()).navigateToSignInOrRegister();
        }
        signInPage.withUserName(parameters.getUsername()).withPassword(parameters.getPassword()).signIn();
    }

    @Override
    public void verifyAuthenticationExceptionRaised() {
        SignInPage page = new SignInPage(getWebdriverInstance());
        assertThat(page.hasAuthenticationError()).isTrue();
    }

    @Override
    public void verifyValidationErrorOnPassword() {
        SignInPage page = new SignInPage(getWebdriverInstance());
        assertThat(page.hasAuthenticationErrorOnPassword()).isTrue();
    }

    @Override
    public void verifyValidationErrorOnUsername() {
        SignInPage page = new SignInPage(getWebdriverInstance());
        assertThat(page.hasAuthenticationErrorOnPassword()).isTrue();
    }

    @Override
    public void loginWithCreditcardNumber(String email, String creditCardNumber) {
        MobileMyAccountPage mobileMyAccountPage = new MobileMyAccountPage(getWebdriverInstance());
        mobileMyAccountPage.clickOnMyAccount();
        mobileMyAccountPage.loginWithCreditcardNumber(email, creditCardNumber);
    }
}

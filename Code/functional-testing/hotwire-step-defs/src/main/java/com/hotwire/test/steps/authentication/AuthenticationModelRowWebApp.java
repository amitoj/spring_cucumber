/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.authentication;

import static org.fest.assertions.Assertions.assertThat;

import com.hotwire.selenium.desktop.us.SignInPage;
import org.openqa.selenium.support.PageFactory;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.row.authentication.LoginPage;
import com.hotwire.test.steps.purchase.PurchaseParameters;

/**
 * International user authentication model
 *
 * @author Daniel Ilyuschenko (v-dilyuschenko)
 * @since 2012.06
 */
public class AuthenticationModelRowWebApp extends AuthenticationModelTemplate {

    protected PurchaseParameters purchaseParameters;

    public void setPurchaseParameters(PurchaseParameters purchaseParameters) {
        this.purchaseParameters = purchaseParameters;
    }

    @Override
    public void attemptToAuthenticate(AuthenticationParameters parameters) {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        SignInPage signInPage = globalHeader.navigateToSignInPage();
        signInPage.withUserName(parameters.getUsername()).withPassword(parameters.getPassword()).signIn();
    }

    @Override
    public void verifyAuthenticationExceptionRaised() {
        LoginPage loginPage = PageFactory.initElements(getWebdriverInstance(), LoginPage.class);
        assertThat(loginPage.hasAuthenticationError()).isTrue();
    }
}

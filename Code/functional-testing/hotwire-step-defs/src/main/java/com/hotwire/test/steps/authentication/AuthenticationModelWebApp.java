/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.authentication;

import static org.fest.assertions.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.desktop.account.PasswordAssistanceConfirmationPage;
import com.hotwire.selenium.desktop.account.PasswordAssistancePage;
import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.SignInPage;
import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarTravelerInfo;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelTravelerInfoFragment;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;

/**
 *  Authentication methods for Domestic WebApp
 */
public class AuthenticationModelWebApp extends AuthenticationModelTemplate {

    private static int MAX_ATTEMPTS_TO_AUTHENTICATE = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationModelWebApp.class.getName());

    @Autowired
    @Qualifier("purchaseParameters")
    PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParameters carSearchParameters;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    @Override
    public void attemptToAuthenticate(AuthenticationParameters parameters) {
        AbstractUSPage page = new AbstractUSPage(getWebdriverInstance());
        if (!parameters.isRandomUser()) {
            page.getGlobalHeader()
                .navigateToSignInPage()
                .withUserName(parameters.getUsername())
                .withPassword(parameters.getPassword())
                .signIn();
        }
        else {
            authenticateRandomUser(page);
        }
        parameters.userLogIn();
    }

    /**
     * Try to authenticate as Random user more than 1 times if needed
     */
    private void authenticateRandomUser(AbstractUSPage page) {

        for (int i = 0; i < MAX_ATTEMPTS_TO_AUTHENTICATE; i++) {
            LOGGER.info("Authentication for " + authenticationParameters.getUsername());

            page.getGlobalHeader()
                    .navigateToSignInPage()
                    .withUserName(authenticationParameters.getUsername())
                    .withPassword(authenticationParameters.getPassword())
                    .signIn();

            if (!page.getGlobalHeader().isLoggedIn()) {
                LOGGER.warn(String.format("Authentication failed...\nEmail: %s\nPassword: %s",
                    authenticationParameters.getUsername(), authenticationParameters.getPassword()));
                authenticationParameters.regenerateRandomUsername();
            }
            else {
                break;
            }
        }
    }

    @Override
    public void verifyAuthenticationExceptionRaised() {
        assertThat(new SignInPage(getWebdriverInstance()).hasAuthenticationError()).isTrue();
    }

    @Override
    public void verifyValidationErrorOnPassword() {
        assertThat(new SignInPage(getWebdriverInstance()).hasAuthenticationErrorOnPassword()).isTrue();
    }

    @Override
    public void verifyValidationErrorOnUsername() {
        assertThat(new SignInPage(getWebdriverInstance()).hasAuthenticationErrorOnUsername()).isTrue();
    }

    @Override
    public void loginWithCreditcardNumber(String emailid, String creditcardnumber) {
        new SignInPage(getWebdriverInstance()).findYourReservation(emailid, creditcardnumber);
    }

    @Override
    public void loginWithInvalidCreditCardNumber(String email) {
        new SignInPage(getWebdriverInstance()).findYourReservationWithInvalidCard(email);
    }

    @Override
    public void loginInFourTimesToGetErrorMessage() {
        SignInPage signInPage = new SignInPage(getWebdriverInstance());
        for (int i = 0; i <= 3; i++) {
            signInPage
                    .withUserName(authenticationParameters.getUsername())
                    .withPassword(authenticationParameters.getPassword())
                    .signIn();
        }
        PasswordAssistancePage page = new PasswordAssistancePage(getWebdriverInstance());
        page.cancelPage();
    }

    @Override
    public void loginWithDepartureDate(String emailid) {
        SignInPage signInPage = new SignInPage(getWebdriverInstance());
        signInPage.findYourReservationViaTripDate(emailid.toLowerCase(), searchParameters.getStartDate());
    }

    @Override
    public void verifySignInPage() {
        new SignInPage(getWebdriverInstance()).verifySignInPage();
    }

    @Override
    public void goToSignInPage() {
        AbstractUSPage page = new AbstractUSPage(getWebdriverInstance());
        page.getGlobalHeader()
                .navigateToSignInPage();
    }

    @Override
    public void tryToSignIn(Integer iTimes) {
        SignInPage signInPage = new SignInPage(getWebdriverInstance());
        String password = authenticationParameters.getPassword();
        signInPage.withUserName(authenticationParameters.getUsername());

        for (Integer i = 0; i < iTimes; i++) {
            //type password in a loop while if we type wrong and submit the form, password field will be cleared
            signInPage.withPassword(password);
            signInPage.signIn();
        }
    }

    @Override
    public void attemptToSignIn() {
        SignInPage signInPage = new SignInPage(getWebdriverInstance());
        String password = authenticationParameters.getPassword();
        signInPage.withUserName(authenticationParameters.getUsername());
        signInPage.withPassword(password);
        signInPage.signIn();
    }

    @Override
    public void clickInAHurryLink() {
        PasswordAssistancePage page = new PasswordAssistancePage(getWebdriverInstance());
        page.inAHurryLinkClick();
    }

    @Override
    public void setPasswordAssistancePassword(String password, String confirmationPassword) {
        PasswordAssistancePage paPage = new PasswordAssistancePage(getWebdriverInstance());
        paPage.enterPasswordToReset(password, confirmationPassword);
    }

    @Override
    public void optionalSignInStatus(String state) {
        CarTravelerInfo travelerInfoFragment = CarBillingPageProvider.get(getWebdriverInstance())
                .getTravelerInfoFragment();

        if ("available".equals(state)) {
            assertThat(travelerInfoFragment.isOptionalSignInLinkDisplayed())
                    .as("Optional SignIn is unavailable").isTrue();
        }
        else if ("unavailable".equals(state)) {
            assertThat(travelerInfoFragment.isOptionalSignInLinkDisplayed())
                    .as("Optional SignIn is unavailable").isFalse();
        }
    }

    @Override
    public void openOptionalSignInPopUp() {
        if (searchParameters.getPGoodCode().equals(PGoodCode.H)) {
            new HotelTravelerInfoFragment(getWebdriverInstance()).clickSignInLink();
        }
        else {
            CarBillingPageProvider.get(getWebdriverInstance())
                    .getTravelerInfoFragment().openOptionalSignIn();
        }
    }

    @Override
    public void goToPasswordAssistance() {
        if (searchParameters.getPGoodCode().equals(PGoodCode.H)) {
            new HotelTravelerInfoFragment(getWebdriverInstance()).goToPasswordAssistance();
        }
        else {
            CarBillingPageProvider.get(getWebdriverInstance())
                    .getTravelerInfoFragment().goToPasswordAssistance();
        }
    }

    @Override
    public void logInByOptionalSignIn() {
        CarBillingPageProvider.get(getWebdriverInstance())
            .getTravelerInfoFragment().
            login(authenticationParameters.getUsername(), authenticationParameters.getPassword());
    }

    @Override
    public void submitOptionalSignInForm() {
        CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment().submitOptionalSignInForm();
        //instantiate new billing page after submit to avoid stale element exception
        CarBillingPageProvider.get(getWebdriverInstance());
    }

    @Override
    public void writeToOptionalSignInField(String fieldType, String fieldValue) {
        if ("email".equals(fieldType)) {
            CarBillingPageProvider.get(getWebdriverInstance())
                    .getTravelerInfoFragment().writeToOptionalSignInEmail(fieldValue);
        }
        else if ("password".equals(fieldType)) {
            CarBillingPageProvider.get(getWebdriverInstance())
                    .getTravelerInfoFragment().writeToOptionalSignInPassword(fieldValue);
        }
    }

    @Override
    public void setPasswordAssistanceEmail(String email) {
        PasswordAssistancePage paPage = new PasswordAssistancePage(getWebdriverInstance());
        paPage.enterEmailToRestore(email);
    }

    @Override
    public void verifyPasswordAssistanceEmailConfirmation() {
        PasswordAssistanceConfirmationPage paPage = new PasswordAssistanceConfirmationPage(getWebdriverInstance());
        assertThat(paPage.isMsgSuccessful()).
                as("Message 'Please check for an email from us ' is not displayed!").isTrue();
    }

    @Override
    public void verifyPrepopulatedPA_Email(String email) {
        PasswordAssistancePage paPage = new PasswordAssistancePage(getWebdriverInstance());
        assertThat(paPage.getEmailField()).as(
            "Expected email " + email + " is not displayed, actual email prepopulated " + paPage.getEmailField())
            .isEqualTo(email);
    }

    @Override
    public void confirmErrorHandling(String errorMsg) {
        SignInPage signIn = new SignInPage(getWebdriverInstance());
        assertThat(signIn.getErrorMessages().contains(errorMsg)).as(
            "Following error should be received: " + errorMsg).isTrue();
    }
}

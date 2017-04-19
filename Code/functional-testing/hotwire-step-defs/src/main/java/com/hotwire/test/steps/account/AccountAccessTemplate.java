/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account;

import static org.ehoffman.testing.fest.webdriver.WebElementAssert.assertThat;
import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;
import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.account.AccountOverviewPage;
import com.hotwire.selenium.desktop.common.billing.HotelBillingPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.row.account.RegistrationPage;
import com.hotwire.test.steps.application.SessionParameters;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.functions.PageName;

/**
 * interface AccountAccessTemplate
 */
public class AccountAccessTemplate extends WebdriverAwareModel implements AccountAccessModel {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AccountAccessTemplate.class.getSimpleName());
    protected AuthenticationParameters authenticationParameters;
    protected URL applicationUrl;
    protected Boolean subscribeWhenRegister = true;
    protected SessionParameters sessionParameters;

    @Resource(name = "posToCountryOrigin")
    protected HashMap<String, String> posToCountryOrigin;

    @Resource(name = "canadianZipCode")
    protected String canadianZipCode;

    private String expectedUserName;

    public void setAuthenticationParameters(AuthenticationParameters authenticationParameters) {
        this.authenticationParameters = authenticationParameters;
    }

    public String getExpectedUserName() {
        return this.expectedUserName;
    }

    public void setExpectedUserName(String expectedUserName) {
        this.expectedUserName = expectedUserName;
    }

    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public void setSessionParameters(SessionParameters sessionParameters) {
        this.sessionParameters = sessionParameters;
    }

    @Override
    public void accessAccountInformation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyEmailOnOverviewPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void doNotSubscribeToHotwireDealsWhenRegister() {
        this.subscribeWhenRegister = false;
    }

    @Override
    public void registerNewUserAndAuthenticate() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void registerNewUserAndStay() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyNewUserIsCreated() {
        throw new UnimplementedTestException("Implement me!");
    }

    // ROW and domestic
    @Override
    public boolean userExistsAndIsAuthenticated() {
        return  new GlobalHeader(getWebdriverInstance()).isLoggedIn();
    }

    @Override
    public void verifyAccountInformationIsAvailable() {
        AccountOverviewPage accountPage = new AccountOverviewPage(getWebdriverInstance());
        assertThat(accountPage.getUserName()).isEnabled().textContains(getExpectedUserName());
    }

    @Override
    public void accessAccountTab(String tabName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmRecentlyBookedTrip(String tripStartDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmRecentlyBookedGuestTrip(String tripStartDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmRecentlyBookedTripDetails(
            String tripType,
            String location,
            String tripStatus,
            SearchParameters searchParameters) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void cancelPastPurchase(
            String tripType,
            String location,
            String tripStatus,
            SearchParameters searchParameters) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToHomePage() {
        getWebdriverInstance().navigate().to(this.applicationUrl);
    }

    @Override
    public void selectAddToMyStay() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmAddOptionsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void manageEmailSubscriptions() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void accessEmailSubscriptionsFromEmail() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void changeEmailSubscriptions(boolean subscribe, String subscriptionName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmSubscriptionsUpdated() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySubscription(String subscriptionName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void manageAccountInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void manageTraveleNames() {
        throw new UnimplementedTestException("Implement me");
    }

    @Override
    public void updateFirstTravelerInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void changeEmailAddress() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmEmailUpdated() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void changePassword(boolean isInvalid) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmPasswordUpdated() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void manageAccountPrefs() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void addFavoriteAirport(String airportName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmAirportUpdated(boolean wasRemoved) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void removeFavoriteAirport() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmAirportRemoved() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmPasswordInvalid() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCruiseNewsSubscription(boolean subscribe) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void changeDefaultSubscription() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySubscriptionCheckboxState(final String state) {
        String pageName = new PageName().apply(getWebdriverInstance());

        if ("tile.account.login".equalsIgnoreCase(pageName)) {
            RegistrationPage registrationPage = new RegistrationPage(getWebdriverInstance());
            assertThat(registrationPage.getSubscriptionState())
                .as("Newsletter subscription checkbox state")
                .isEqualTo("checked".equals(state));
        }
        else if ("tile.hotel.billing.onePageBilling".equalsIgnoreCase(pageName)) {
            HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
            assertThat(billingPage.getSubscriptionState())
                .as("Newsletter subscription checkbox state")
                .isEqualTo("checked".equals(state));
        }
        else {
            throw new UnimplementedTestException("Implement me!");
        }
    }

    @Override
    public void verifySubscriptionProposal(final String proposal) {
        String pageName = new PageName().apply(getWebdriverInstance());

        if ("tile.account.login".equalsIgnoreCase(pageName)) {
            RegistrationPage registrationPage = new RegistrationPage(getWebdriverInstance());
            assertThat(registrationPage.getSubscriptionProposal())
                .as("Newsletter subscription proposal")
                .isEqualTo(proposal);
        }
        else if ("tile.hotel.billing.onePageBilling".equalsIgnoreCase(pageName)) {
            HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
            assertThat(billingPage.getSubscriptionProposal())
                .as("Newsletter subscription proposal")
                .isEqualTo(proposal);
        }
        else {
            throw new UnimplementedTestException("Implement me!");
        }
    }

    @Override
    public void verifySubscriptionOptionPresence(String isPresent) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void accessSubAccountTab(String watchedTripsTabName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmRecentlyWatchedTrip(String location, SearchParameters searchParameters) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void logout() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void enterLowerPassword() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void changeUserPassword(String newPass, String confPass) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void enterUpperPassword() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void loadPasswordAssistancePageFromUHP() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToSignInPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToMyTripsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void logoutToCookiedMode() {
        new GlobalHeader(getWebdriverInstance()).logoutToCookieMode();
    }

    @Override
    public void verifyUserInCookiedMode() {
        boolean cookied = new GlobalHeader(getWebdriverInstance()).isLoggedInCookieMode();
        assertThat(cookied)
            .as("Expected to be in cookied mode, but was not.")
            .isTrue();
    }

    @Override
    public void verifyHfcStatusAfterLogin() {
        assertThat(new GlobalHeader(getWebdriverInstance()).areHfcElementsDisplayed())
            .as("Expected HFC elements to be visible in Global Header elements. One or more elements not displayed.")
            .isTrue();
    }

    @Override
    public void setFieldOnRegisterForm(String fiild) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void verifyRegistrationPageURL() {
        throw new UnimplementedTestException("Implement me!");
    }


    @Override
    public void changeEmailAndVerifyInDB() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmSubscriptionsFromDB(boolean isOn) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTripDepartureValidationError() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void enterBlankRegistrationInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void enterBlankSignInInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyUkUhpPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySavedCreditCardOnMyAccountPage(String condition) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void managePaymentInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void stopWatchingLatestTrip() {
        throw new UnimplementedTestException("Implement me!");
    }
}

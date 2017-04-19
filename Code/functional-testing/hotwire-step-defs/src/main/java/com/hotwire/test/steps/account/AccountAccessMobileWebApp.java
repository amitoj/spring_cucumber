/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.account;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.account.MobileAccountEmailAddressPage;
import com.hotwire.selenium.mobile.account.MobileAccountEmailSubscriptionsPage;
import com.hotwire.selenium.mobile.account.MobileChangePasswordPage;
import com.hotwire.selenium.mobile.account.MobileMyAccountPage;
import com.hotwire.selenium.mobile.account.MobilePasswordChangeConfirmationPage;
import com.hotwire.selenium.mobile.account.mytrips.MobileTripSummaryPage;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 *
 */
public class AccountAccessMobileWebApp extends AccountAccessTemplate {
    private static final int DEFAULT_WAIT = 45;

    private EmailSubscriptionParameters emailSubscriptionParameters;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("newValidAuthenticationParameters")
    private AuthenticationParameters newValidAuthenticationParameters;



    @Override
    public void manageEmailSubscriptions() {
        new MobileMyAccountPage(getWebdriverInstance()).navigateToAccountEmailSubscriptionsPage();
    }

    @Override
    public void accessAccountTab(String tabName) {
        switch (tabName) {
            case "Trips":
                new MobileMyAccountPage(getWebdriverInstance()).navigateToMyTripsPage();
                break;
            default:
                throw new UnimplementedTestException("Implement Me!");
        }
    }

    @Override
    public void confirmRecentlyBookedTrip(String tripStartDate) {
        MobileTripSummaryPage tripsPage = new MobileTripSummaryPage(getWebdriverInstance());
        Boolean hasRecentlyBookedTrip = tripsPage.hasRecentlyBookedTrip(tripStartDate);
        assertThat(hasRecentlyBookedTrip)
                .as("First Trip on the Trips page does not match the startdate of most recently booked trip")
                .isTrue();
        System.out.println(purchaseParameters.getItinerary());
    }

    @Override
    public void manageAccountInfo() {
        // no-op. There is no account info navigation.
    }

    @Override
    public void changePassword(boolean isInvalid) {
        MobileChangePasswordPage passwordPage =
            new MobileMyAccountPage(getWebdriverInstance()).navigateToChangePasswordPage();
        if (isInvalid) {
            // Use invalid params.
            final String invalidPassword = "abc"; // Not longer than six chars.
            passwordPage.changePassword(authenticationParameters.getPassword(), invalidPassword);
        }
        else {
            // Use valid params.
            passwordPage.changePassword(authenticationParameters.getPassword(),
                newValidAuthenticationParameters.getPassword());
        }
    }

    @Override
    public void confirmPasswordUpdated() {
        new MobilePasswordChangeConfirmationPage(getWebdriverInstance()).clickContinue();
        new MobileHotwireHomePage(getWebdriverInstance());
    }

    @Override
    public void accessAccountInformation() {
        MobileHotwireHomePage mUHP = new MobileHotwireHomePage(getWebdriverInstance());
        mUHP.navigateToMyAccountPage();
    }

    @Override
    public void verifyAccountInformationIsAvailable() {
        MobileMyAccountPage mobileMyAccountPage = new MobileMyAccountPage(getWebdriverInstance());
        mobileMyAccountPage.navigateToEmailAddressPage().checkEmail(authenticationParameters.getUsername());
    }

    @Override
    public void registerNewUserAndAuthenticate() {
        MobileHotwireHomePage mUHP = new MobileHotwireHomePage(getWebdriverInstance());
        UserInformation userInformation = purchaseParameters.getUserInformation();
        String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
            userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode :
            userInformation.getZipCode();


        mUHP.navigateToSignInOrRegister()
                .goToCreateNewUser()
                .withFirstName("Test").withLastName("Hotwire")
                .withCountryCode(posToCountryOrigin.get(sessionParameters.getPointOfSale())).withZipCode(zipCode)
                .withEmail(this.authenticationParameters.getUsername())
                .withConfirmEmail(this.authenticationParameters.getUsername())
                .withPassword(this.authenticationParameters.getPassword())
                .withConfirmPassword(this.authenticationParameters.getPassword())
                .registerNewUser();
        newValidAuthenticationParameters.userLogIn();
        new MobileHotwireHomePage(getWebdriverInstance());
        new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(".loader img"), false));
    }

    @Override
    public void verifyNewUserIsCreated() {
        MobileHotwireHomePage mUHP = new MobileHotwireHomePage(getWebdriverInstance());
        mUHP.signOut();
        new MobileHotwireHomePage(getWebdriverInstance());
        new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(".loader img"), false));
        mUHP = new MobileHotwireHomePage(getWebdriverInstance());
        if (mUHP.signedIn()) {
            LoggerFactory.getLogger(AccountAccessMobileWebApp.class.getSimpleName())
                    .info("Still ain't logged out. Trying to log out again. WTF...");
            mUHP.signOut();
               /*comment wait for loading page to be dissapeared. will monitor if account.MobileWebApp.feature would be
               fixed*/
//            new MobileHotwireHomePage(getWebdriverInstance());
//            new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
//                    .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(".loader img"), false));
        }
        mUHP = new MobileHotwireHomePage(getWebdriverInstance());
        mUHP.navigateToSignInOrRegister()
                .withUserName(this.authenticationParameters.getUsername())
                .withPassword(this.authenticationParameters.getPassword())
                .signIn();
        new MobileHotwireHomePage(getWebdriverInstance());
        new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(".loader img"), false));

        mUHP = new MobileHotwireHomePage(getWebdriverInstance());

        mUHP.navigateToMyAccountPage();
        MobileMyAccountPage mobileMyAccountPage = new MobileMyAccountPage(getWebdriverInstance());
        mobileMyAccountPage.navigateToEmailAddressPage().checkEmail(authenticationParameters.getUsername());
    }

    @Override
    public boolean userExistsAndIsAuthenticated() {
        MobileAbstractPage page = new MobileAbstractPage(getWebdriverInstance());
        return page.signedIn();
    }

    @Override
    public void accessEmailSubscriptionsFromEmail() {
        MobileHotwireHomePage mobileHomePage = new MobileHotwireHomePage(getWebdriverInstance());
        String subscriptionManagementUrlFromEmail =
                "account/emailSubscription.jsp?" +
                        "cid=5838e1a387f4a278c4d97c05ef62dd85&nid=N-HFA-416&vid=V-HFA-416-V1SS-T6&did=D0079&r=y";
        mobileHomePage.setupURLToVisit(subscriptionManagementUrlFromEmail);
    }

    @Override
    public void changeEmailSubscriptions(boolean subscribe, String subscriptionName) {
        MobileAccountEmailSubscriptionsPage subscrPage =
                new MobileAccountEmailSubscriptionsPage(getWebdriverInstance());
        if (!subscribe && StringUtils.equals(subscriptionName, "all")) {
            this.emailSubscriptionParameters.setUnsubscribeAll(true);
            subscrPage.unsubscribeAll();
        }
        else if (subscribe && StringUtils.equals(subscriptionName, "all")) {
            this.emailSubscriptionParameters.setUnsubscribeAll(false);
            subscrPage.subscribeAll();
        }
        else {
            this.emailSubscriptionParameters.getSubscriptionMap().put(subscriptionName, subscribe);
            subscrPage.updateSubscriptions(this.emailSubscriptionParameters.getSubscriptionMap());
        }
        subscrPage.saveChanges();
    }

    @Override
    public void confirmSubscriptionsUpdated() {
        MobileAccountEmailSubscriptionsPage subscrPage =
                new MobileAccountEmailSubscriptionsPage(getWebdriverInstance());
        if (this.emailSubscriptionParameters.getUnsubscribeAll()) {
            assertThat(subscrPage.isConfirmationVisible()).
                    as("Confirmation box should be shown after updating subscriptions").isTrue();
            assertThat(subscrPage.isUnsubscribedFromAll()).
                    as("All subscriptions should be set to unsubscribed").isTrue();
        }
        else {
            assertThat(subscrPage.isConfirmationVisible()).
                    as("Confirmation box should be shown after updating subscriptions").isTrue();
            Map<String, Boolean> subscriptionMap = this.emailSubscriptionParameters.getSubscriptionMap();
            for (Map.Entry<String, Boolean> entry : subscriptionMap.entrySet()) {
                assertThat(subscrPage.isSubscribed(entry.getKey())).
                        as("Subscription should be what we set it to").isEqualTo(entry.getValue());
            }
        }
    }

    public void setEmailSubscriptionParameters(EmailSubscriptionParameters emailSubscriptionParameters) {
        this.emailSubscriptionParameters = emailSubscriptionParameters;
    }

    @Override
    public void verifyHfcStatusAfterLogin() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmPasswordInvalid() {

        MobileChangePasswordPage passwordPage =
            new MobileChangePasswordPage(getWebdriverInstance());
        //Password must contain 6-30 characters and no spaces. It is case-sensitive.
        assertThat(passwordPage.getErrorText()).
            as("Subscription should be what we set it to")
            .contains("assword must contain 6-30 characters and no spaces. It is case-sensitive.");
    }

    @Override
    public void changeEmailAddress() {
        String newEmail = "new" + authenticationParameters.getUsername();
        new MobileMyAccountPage(getWebdriverInstance()).navigateToEmailAddressPage();
        new MobileAccountEmailAddressPage(getWebdriverInstance())
            .changeEmail(authenticationParameters.getUsername(), newEmail);
    }

    @Override
    public void confirmEmailUpdated() {
        assertThat(new MobileAccountEmailAddressPage(getWebdriverInstance()).isEmailBeenChanged())
            .as("Email hasn't been changed")
            .isTrue();
    }

}





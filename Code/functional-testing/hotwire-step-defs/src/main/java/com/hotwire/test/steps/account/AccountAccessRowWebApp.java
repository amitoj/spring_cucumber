/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import com.hotwire.selenium.desktop.account.AccountEmailSubscriptionsPage;
import com.hotwire.selenium.desktop.account.AccountOverviewPage;
import com.hotwire.selenium.desktop.account.AccountTopNavBar;
import com.hotwire.selenium.desktop.account.AccountTripsPage;
import com.hotwire.selenium.desktop.common.billing.HotelBillingPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.row.account.RegistrationPage;
import com.hotwire.selenium.desktop.us.SignInPage;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.functions.PageName;

/**
 *
 */
public class AccountAccessRowWebApp extends AccountAccessTemplate {

    private static String TRIP_DATE_FORMAT = "EEE, MM/dd/yy";

    @Autowired
    private URL applicationUrl;

    @Autowired
    private EmailSubscriptionParameters emailSubscriptionParameters;

    @Autowired
    private PurchaseParameters purchaseParameters;

    public void setEmailSubscriptionParameters(EmailSubscriptionParameters emailSubscriptionParameters) {
        this.emailSubscriptionParameters = emailSubscriptionParameters;
    }

    public void setPurchaseParameters(PurchaseParameters purchaseParameters) {
        this.purchaseParameters = purchaseParameters;
    }

    @Override
    public void accessAccountInformation() {
        new WebDriverWait(getWebdriverInstance(), 5)
            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hwGlobalHeader")));
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        globalHeader.navigateToMyAccount();
    }

    @Override
    public void navigateToHomePage() {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        globalHeader.clickOnHotwireLogo();
    }

    @Override
    public void registerNewUserAndAuthenticate() {
        UserInformation userInformation = purchaseParameters.getUserInformation();

        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        globalHeader.goToRegistrationPage().registerNewUser(
                userInformation.getFirstName(),
                userInformation.getLastName(),
                userInformation.getCountry(),
                userInformation.getZipCode(),
                authenticationParameters.getUsername(),
                authenticationParameters.getUsername(),
                authenticationParameters.getPassword(),
                authenticationParameters.getPassword());
        globalHeader = new GlobalHeader(getWebdriverInstance());
        globalHeader.clickOnHotwireLogo();
    }

    @Override
    public void verifyNewUserIsCreated() {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        globalHeader.logOut();
        globalHeader = new GlobalHeader(getWebdriverInstance());
        SignInPage loginPage = globalHeader.navigateToSignInPage();
        loginPage.withPassword(authenticationParameters.getPassword()).
                withUserName(authenticationParameters.getUsername()).signIn();
        globalHeader = new GlobalHeader(getWebdriverInstance());
        assertThat(globalHeader.isLoggedIn());
    }

    @Override
    public void manageEmailSubscriptions() {
        AccountTopNavBar accountTopNavBar = new AccountTopNavBar(this.getWebdriverInstance());
        accountTopNavBar.getTopNavLink("Email Subscriptions").click();
    }

    @Override
    public void verifySubscription(String subscriptionName) {
        AccountEmailSubscriptionsPage subscriptionsPage = new AccountEmailSubscriptionsPage(getWebdriverInstance());
        assertThat(subscriptionsPage.isSubscribed(subscriptionName)).isTrue();
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
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
        if (isPresent.equalsIgnoreCase("have")) {
            assertThat(billingPage.isSubscriptionOptionPresent()).isTrue();
        }
        else {
            assertThat(billingPage.isSubscriptionOptionPresent()).isFalse();
        }
    }

    @Override
    public void accessAccountTab(String tabName) {
        AccountTopNavBar accountTopNavBar = new AccountTopNavBar(getWebdriverInstance());
        accountTopNavBar.getTopNavLink(tabName).click();
    }

    @Override
    public void confirmRecentlyBookedTripDetails(String tripType,
                                                 String location,
                                                 String tripStatus,
                                                 SearchParameters searchParameters) {
        AccountTripsPage tripsPage = new AccountTripsPage(getWebdriverInstance());

        assertThat(tripsPage.hasRecentlyBookedTripDetails(
                tripType,
                location,
                tripStatus,
                (new SimpleDateFormat(TRIP_DATE_FORMAT)).format(searchParameters.getStartDate()),
                (new SimpleDateFormat(TRIP_DATE_FORMAT)).format(searchParameters.getEndDate())))
                .as(tripType + " trip in <" + location + "> has not been " + tripStatus)
                .isNotNull();
    }

    @Override
    public void cancelPastPurchase(String tripType,
                                   String location,
                                   String tripStatus,
                                   SearchParameters searchParameters) {

        AccountTripsPage tripsPage = new AccountTripsPage(getWebdriverInstance());

        tripsPage.cancelRecentlyBookedTrip(
                tripType, location, tripStatus,
                (new SimpleDateFormat(TRIP_DATE_FORMAT)).format(searchParameters.getStartDate()),
                (new SimpleDateFormat(TRIP_DATE_FORMAT)).format(searchParameters.getEndDate()));

        getWebdriverInstance().findElement(By.partialLinkText("Yes")).click();
        confirmRecentlyBookedTripDetails(tripType, location, "cancelled", searchParameters);
    }

    @Override
    public void changeEmailSubscriptions(boolean subscribe, String subscriptionName) {
        AccountEmailSubscriptionsPage subscrPage = new AccountEmailSubscriptionsPage(getWebdriverInstance());
        if (!subscribe && StringUtils.equals(subscriptionName, "all")) {
            emailSubscriptionParameters.setUnsubscribeAll(true);
            subscrPage.unsubscribeAll();
        }
        else {
            emailSubscriptionParameters.getSubscriptionMap().put(subscriptionName, subscribe);
            subscrPage.updateSubscriptions(emailSubscriptionParameters.getSubscriptionMap());
        }
        subscrPage.saveChanges();
    }

    @Override
    public void confirmSubscriptionsUpdated() {
        AccountEmailSubscriptionsPage subscrPage = new AccountEmailSubscriptionsPage(getWebdriverInstance());
        assertThat(subscrPage.isConfirmationVisible()).
                as("Confirmation box should be shown after updating subscriptions").isTrue();
        Map<String, Boolean> subscriptionMap = emailSubscriptionParameters.getSubscriptionMap();
        for (Map.Entry<String, Boolean> entry : subscriptionMap.entrySet()) {
            assertThat(subscrPage.isSubscribed(entry.getKey())).
                    as("Subscription should be what we set it to").isEqualTo(entry.getValue());
        }
    }

    @Override
    public void verifyAccountInformationIsAvailable() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        AccountOverviewPage accountPage = new AccountOverviewPage(getWebdriverInstance());
        assertThat(accountPage.getUserName().getText()).
                isEqualTo(userInformation.getFirstName() + " " + userInformation.getLastName());
    }

    @Override
    public void enterBlankRegistrationInfo() {
        new GlobalHeader(getWebdriverInstance()).goToRegistrationPage().registerNewUser(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "");
    }

    @Override
    public void enterBlankSignInInfo() {
        new GlobalHeader(getWebdriverInstance())
                .navigateToSignInPage()
                .withUserName("")
                .withPassword("")
                .signIn();
    }

    @Override
    public void verifyUkUhpPage() {
        assertThat(getWebdriverInstance().getCurrentUrl().startsWith(applicationUrl.toString()))
                .as("URL should be start with" + applicationUrl.toString() +
                        "but it is" + getWebdriverInstance().getCurrentUrl())
                .isTrue();

    }
}

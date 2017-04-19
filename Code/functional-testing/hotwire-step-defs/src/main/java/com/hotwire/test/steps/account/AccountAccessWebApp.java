/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account;

import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotwire.selenium.desktop.account.AccountEmailSubscriptionsPage;
import com.hotwire.selenium.desktop.account.AccountInfoPage;
import com.hotwire.selenium.desktop.account.AccountOverviewPage;
import com.hotwire.selenium.desktop.account.AccountPaymentInfoPage;
import com.hotwire.selenium.desktop.account.AccountPreferencesPage;
import com.hotwire.selenium.desktop.account.AccountTopNavBar;
import com.hotwire.selenium.desktop.account.AccountTravelerNamesPage;
import com.hotwire.selenium.desktop.account.AccountTripsPage;
import com.hotwire.selenium.desktop.account.AccountTripsPageWatchedTrips;
import com.hotwire.selenium.desktop.account.AddToYourStayPage;
import com.hotwire.selenium.desktop.account.RegisterNewUserPage;
import com.hotwire.selenium.desktop.account.RegistrationConfirmationPage;
import com.hotwire.selenium.desktop.common.billing.HotelBillingPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.SignInPage;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.test.steps.search.air.AirSearchParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;

/**
 * interface AccountAccessWebApp
 */
public class AccountAccessWebApp extends AccountAccessTemplate {

    protected static String TRIP_DATE_FORMAT = "EEE, MM/dd/yy";
    protected static String TRIP_WATCHER_TRIP_DATE_FORMAT = "EEE, MM/dd";
    private static String TRIP_DEPARTURE_DATE_VALIDATION_MSG = "Error 001: We can't find a purchase matching the" +
            " email address and departure date you entered. Please try again.";
    @Autowired
    @Qualifier("purchaseParameters")
    PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("searchParameters")
    SearchParameters searchParameters;


    @Autowired
    @Qualifier("carSearchParameters")
    CarSearchParameters carSearchParameters;

    @Autowired
    @Qualifier("hotelSearchParameters")
    HotelSearchParameters hotelSearchParameters;

    @Autowired
    @Qualifier("airSearchParameters")
    AirSearchParameters airSearchParameters;

    @Autowired
    @Qualifier("applicationModel")
    ApplicationModel applicationModel;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EmailSubscriptionParameters emailSubscriptionParameters;
    /**
     * Valid auth params we'll use when we *change* our email / password, etc.
     */
    private AuthenticationParameters newValidAuthenticationParameters;

    @Override
    public void accessAccountInformation() {
        GlobalHeader header = new GlobalHeader(getWebdriverInstance());
        header.navigateToMyAccount();
    }

    @Override
    public void verifyEmailOnOverviewPage() {
        AccountOverviewPage accountOverviewPage = new AccountOverviewPage(getWebdriverInstance());
        String actual = accountOverviewPage.getUserEmail();
        String expected = authenticationParameters.getUsername();
        assertThat(actual.equalsIgnoreCase(expected))
                .as("Emails are not the same. Expected:" + expected +
                        ";but actual is:" + actual).isTrue();
    }

    @Override
    public void registerNewUserAndAuthenticate() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
            userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode :
            userInformation.getZipCode();

        new GlobalHeader(getWebdriverInstance()).goToRegistrationPage()
            .setFirstName(userInformation.getFirstName())
            .setLastName(userInformation.getLastName())
            .setCountryCode(posToCountryOrigin.get(sessionParameters.getPointOfSale()))
            .setZipCode(zipCode)
            .setEmail(authenticationParameters.getUsername())
            .setPassword(authenticationParameters.getPassword())
            .setConfirmPassword(authenticationParameters.getPassword())
            .checkMailSubscription(subscribeWhenRegister)
            .checkPrivacyConfirmCheckbox(true)
            .clickRegisterNewUser();

        new RegistrationConfirmationPage(getWebdriverInstance());
        new GlobalHeader(getWebdriverInstance()).clickOnHotwireLogo();
    }

    @Override
    public void registerNewUserAndStay() {
        UserInformation userInformation = purchaseParameters.getUserInformation();

        new GlobalHeader(getWebdriverInstance()).goToRegistrationPage()
                .setFirstName(userInformation.getFirstName())
                .setLastName(userInformation.getLastName())
                .setCountryCode(posToCountryOrigin.get(sessionParameters.getPointOfSale()))
                .setZipCode(userInformation.getZipCode())
                .setEmail(authenticationParameters.getUsername())
                .setPassword(authenticationParameters.getPassword())
                .setConfirmPassword(authenticationParameters.getPassword())
                .checkMailSubscription(subscribeWhenRegister)
                .checkPrivacyConfirmCheckbox(true)
                .clickRegisterNewUser();

        String url = new GlobalHeader(getWebdriverInstance()).getCurrentPageURL();
        assertThat(url.contains("account-unverified/doregister"))
                .as("Registration URL doesn't contain pattern: account-unverified/doregister").isTrue();

    }

    @Override
    public void verifyNewUserIsCreated() {
        GlobalHeader header = new GlobalHeader(getWebdriverInstance());
        if (header.isLoggedIn() || header.isLoggedInCookieMode()) {
            // Sign out to sign back in.
            header.logoutOfAccount();
        }
        header = new GlobalHeader(getWebdriverInstance());
        header.navigateToSignInPage()
                .getGlobalHeader()
                .navigateToSignInPage()
                .withUserName(authenticationParameters.getUsername())
                .withPassword(authenticationParameters.getPassword())
                .signIn();
        assertThat(new GlobalHeader(getWebdriverInstance()).isLoggedIn())
                .as("Expected created user to be logged in.")
                .isTrue();
    }

    @Override
    public void manageEmailSubscriptions() {
        new AccountOverviewPage(getWebdriverInstance()).getAccountNav().getEmlSbscrLink().click();
    }

    @Override
    public void managePaymentInfo() {
        AccountTopNavBar navBar = new AccountOverviewPage(getWebdriverInstance()).getAccountNav();
        navBar.getPmtInfoLink().click();
    }

    @Override
    public void stopWatchingLatestTrip() {
        AccountTripsPageWatchedTrips watchedTripsPage = new AccountTripsPageWatchedTrips(getWebdriverInstance());
        watchedTripsPage.stopWatchingAllTrips();
    }

    @Override
    public void manageAccountInfo() {
        AccountTopNavBar navBar = new AccountOverviewPage(getWebdriverInstance()).getAccountNav();
        navBar.getAccInfoLink().click();
    }

    @Override
    public void manageTraveleNames() {
        AccountTopNavBar navBar = new AccountOverviewPage(getWebdriverInstance()).getAccountNav();
        navBar.getTravNamesLink().click();
    }

    @Override
    public void updateFirstTravelerInfo() {
        AccountTravelerNamesPage accountTravelerNamesPage = new AccountTravelerNamesPage(getWebdriverInstance());
        String defaultFirstName = accountTravelerNamesPage.getFirstTravelerFirstName();
        String defaultLastName = accountTravelerNamesPage.getFirstTravelerLastName();
        accountTravelerNamesPage.setFirstTravelerFirstName(RandomStringUtils.randomAlphabetic(12));
        accountTravelerNamesPage.setFirstTravelerLastName(RandomStringUtils.randomAlphabetic(12));
        accountTravelerNamesPage.saveChanges();

        assertThat(accountTravelerNamesPage.verifyInfoChanged())
                .as("Confirmation message that traveler was updated is invalid").isTrue();

        accountTravelerNamesPage.setFirstTravelerFirstName(defaultFirstName);
        accountTravelerNamesPage.setFirstTravelerLastName(defaultLastName);
        accountTravelerNamesPage.saveChanges();
    }

    @Override
    public void manageAccountPrefs() {
        AccountTopNavBar navBar = new AccountOverviewPage(getWebdriverInstance()).getAccountNav();
        navBar.getPrefLink().click();
    }

    @Override
    public void addFavoriteAirport(String airportName) {
        AccountPreferencesPage prefsPage = new AccountPreferencesPage(getWebdriverInstance());
        prefsPage.addFavoriteAirport(airportName);
    }

    @Override
    public void removeFavoriteAirport() {
        AccountPreferencesPage prefsPage = new AccountPreferencesPage(getWebdriverInstance());
        prefsPage.removeFavoriteAirport();
    }

    @Override
    public void confirmAirportUpdated(boolean wasRemoved) {
        AccountPreferencesPage prefsPage = new AccountPreferencesPage(getWebdriverInstance());
        assertThat(prefsPage.isSuccessBoxVisible()).
                as("Confirmation box should be shown when user updates their favorite airport").isTrue();
        if (wasRemoved) {
            assertThat(prefsPage.getFavAirportInputText()).
                    as("Favorite airport input should be empty after fav airport is removed").isEmpty();
        }
    }

    @Override
    public void verifyCruiseNewsSubscription(boolean subscribe) {
        AccountEmailSubscriptionsPage subscrPage = new AccountEmailSubscriptionsPage(getWebdriverInstance());
        if (subscribe) {
            assertThat(subscrPage.isCruiseNewsUnSubscribe()).
                    as("Cruise news un-subscribed").isTrue();
        }
        else {
            assertThat(subscrPage.isCruiseNewsSubscribe()).
                    as("Cruise news subscribed").isTrue();
        }
    }

    @Override
    public void changeDefaultSubscription() {
        AccountEmailSubscriptionsPage subscrPage = new AccountEmailSubscriptionsPage(getWebdriverInstance());
        subscrPage.tripWatcherSubscribe();
    }

    @Override
    public void changeEmailAddress() {
        AccountInfoPage accountInfoPage = new AccountInfoPage(getWebdriverInstance());
        accountInfoPage.changeEmail(newValidAuthenticationParameters.getUsername());
    }

    @Override
    public void confirmEmailUpdated() {
        AccountInfoPage accountInfoPage = new AccountInfoPage(getWebdriverInstance());
        assertThat(accountInfoPage.isEmailConfirmationVisible()).
                as("Confirmation box should be shown when user updates their username").isTrue();
        assertThat(accountInfoPage.getCurrentEmail().toLowerCase())
                .isEqualTo(newValidAuthenticationParameters.getUsername().toLowerCase());
    }

    @Override
    public void changePassword(boolean isInvalid) {
        AccountInfoPage accountInfoPage = new AccountInfoPage(getWebdriverInstance());
        if (isInvalid) {
            // Use invalid params.
            final String invalidPassword = "abc"; // Not longer than six chars.
            accountInfoPage.changePassword(authenticationParameters.getPassword(), invalidPassword);
        }
        else {
            // Use valid params.
            accountInfoPage.changePassword(authenticationParameters.getPassword(),
                    newValidAuthenticationParameters.getPassword());
        }
    }

    @Override
    public void confirmPasswordUpdated() {
        AccountInfoPage accountInfoPage = new AccountInfoPage(getWebdriverInstance());
        assertThat(accountInfoPage.isPasswordChangedConfirmationVisible()).
                as("Confirmation box should be shown when user changes their password").isTrue();
    }

    @Override
    public void confirmPasswordInvalid() {
        AccountInfoPage accountInfoPage = new AccountInfoPage(getWebdriverInstance());
        assertThat(accountInfoPage.isPasswordErrorVisible()).
                as("Error should be shown when user uses invalid password").isTrue();
    }

    @Override
    public void changeEmailSubscriptions(boolean subscribe, String subscriptionName) {
        AccountEmailSubscriptionsPage subscriptionPage = new AccountEmailSubscriptionsPage(getWebdriverInstance());
        if (!subscribe && StringUtils.equals(subscriptionName, "all")) {
            emailSubscriptionParameters.setUnsubscribeAll(true);
            subscriptionPage.unsubscribeAll();
        }
        else {
            emailSubscriptionParameters.getSubscriptionMap().put(subscriptionName, subscribe);
            subscriptionPage.updateSubscriptions(emailSubscriptionParameters.getSubscriptionMap());
            subscriptionPage.saveChanges();
        }
    }

    @Override
    public void confirmSubscriptionsUpdated() {
        AccountEmailSubscriptionsPage subscrPage = new AccountEmailSubscriptionsPage(getWebdriverInstance());
        if (emailSubscriptionParameters.getUnsubscribeAll()) {
            assertThat(subscrPage.isConfirmationVisible())
                    .as("Confirmation box should be shown after updating subscriptions").isTrue();
            assertThat(subscrPage.isUnsubscribedFromAll())
                    .as("All subscriptions should be set to unsubscribed").isTrue();
        }
        else {
            assertThat(subscrPage.isConfirmationVisible())
                    .as("Confirmation box should be shown after updating subscriptions").isTrue();

            Map<String, Boolean> subscriptionMap = emailSubscriptionParameters.getSubscriptionMap();
            for (Map.Entry<String, Boolean> entry : subscriptionMap.entrySet()) {
                assertThat(subscrPage.isSubscribed(entry.getKey()))
                        .as("Subscription should be what we set it to").isEqualTo(entry.getValue());
            }
        }
    }

    @Override
    public void accessAccountTab(String tabName) {
        AccountTopNavBar accountTopNavBar = new AccountTopNavBar(getWebdriverInstance());
        accountTopNavBar.getTopNavLink(tabName).click();
    }

    @Override
    public void confirmRecentlyBookedGuestTrip(String tripStartDate) {
        AccountTripsPage tripsPage = new AccountTripsPage(getWebdriverInstance());
        Boolean hasRecentlyBookedTrip = tripsPage.hasRecentlyBookedTrip(tripStartDate);
        assertThat(hasRecentlyBookedTrip)
                .as("First Trip on the Trips page does not match the startdate of most recently booked trip")
                .isTrue();
        System.out.println(purchaseParameters.getItinerary());
    }

    @Override
    public void confirmRecentlyBookedTrip(String tripStartDate) {
        AccountTripsPage tripsPage = new AccountTripsPage(getWebdriverInstance());
        Boolean hasRecentlyBookedTrip = tripsPage.hasRecentlyBookedTrip(tripStartDate);
        assertThat(hasRecentlyBookedTrip)
                .as("First Trip on the Trips page does not match the startdate of most recently booked trip")
                .isTrue();
        System.out.println(purchaseParameters.getItinerary());
    }

    @Override
    public void confirmRecentlyBookedTripDetails(String tripType,
                                                 String location,
                                                 String tripStatus,
                                                 SearchParameters searchParameters) {
        AccountTripsPage tripsPage = new AccountTripsPage(getWebdriverInstance());
        if (tripType.equals("hotel")) {
            location = hotelSearchParameters.getSelectedSearchSolution().getHotelName();
        }

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
    public void confirmRecentlyWatchedTrip(String location, SearchParameters searchParameters) {
        AccountTripsPageWatchedTrips watchedTripsPage = new AccountTripsPageWatchedTrips(getWebdriverInstance());

        assertThat(watchedTripsPage.hasRecentlyWatchedTripDetails(
                location,
                (new SimpleDateFormat(TRIP_WATCHER_TRIP_DATE_FORMAT)).format(searchParameters.getStartDate()),
                (new SimpleDateFormat(TRIP_WATCHER_TRIP_DATE_FORMAT)).format(searchParameters.getEndDate())))
                .as("trip in <" + location + "> has not been found")
                .isNotNull();
    }

    /**
     * Cancellation of past purchase
     *
     * @param tripType - type of purchase {car | air | hotel}
     * @param location - detailed description in myAccount
     */
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

        confirmRecentlyBookedTripDetails(tripType, location, "cancelled", searchParameters);
    }

    @Override
    public void selectAddToMyStay() {
        AccountTripsPage tripsPage = new AccountTripsPage(getWebdriverInstance());
        tripsPage.getAddToMyStayLink().click();
    }

    @Override
    public void confirmAddOptionsPage() {
        new AddToYourStayPage(getWebdriverInstance());
    }

    public void setEmailSubscriptionParameters(EmailSubscriptionParameters emailSubscriptionParameters) {
        this.emailSubscriptionParameters = emailSubscriptionParameters;
    }

    public void setNewValidAuthenticationParameters(AuthenticationParameters newValidAuthenticationParameters) {
        this.newValidAuthenticationParameters = newValidAuthenticationParameters;
    }

    @Override
    public void accessSubAccountTab(String watchedTripsTabName) {
        AccountTripsPage tripPage = new AccountTripsPage(getWebdriverInstance());
        tripPage.getSubAccountTab(watchedTripsTabName).click();
    }

    @Override
    public void logout() {
        GlobalHeader header = new GlobalHeader(getWebdriverInstance());
        header.logOut();
    }

    @Override
    public void enterUpperPassword() {
        new GlobalHeader(getWebdriverInstance()).navigateToSignInPage()
                .getGlobalHeader()
                .navigateToSignInPage()
                .withUserName(authenticationParameters.getUsername())
                .withPassword(authenticationParameters.getPassword().toUpperCase())
                .signIn();
    }

    @Override
    public void enterLowerPassword() {
        new GlobalHeader(getWebdriverInstance()).navigateToSignInPage()
                .getGlobalHeader()
                .navigateToSignInPage()
                .withUserName(authenticationParameters.getUsername())
                .withPassword(authenticationParameters.getPassword().toLowerCase())
                .signIn();
    }

    @Override
    public void changeUserPassword(String newPass, String confPass) {
        AccountInfoPage accountInfoPage = new AccountInfoPage(getWebdriverInstance());
        accountInfoPage.changePassword(authenticationParameters.getPassword(), newPass, confPass);
    }

    @Override
    public void loadPasswordAssistancePageFromUHP() {
        GlobalHeader header;
        header = new GlobalHeader(getWebdriverInstance());
        header.navigateToSignInPage().passwordAssistancePage();
    }

    @Override
    public void navigateToSignInPage() {
        GlobalHeader header;
        header = new GlobalHeader(getWebdriverInstance());
        header.navigateToSignInPage();
    }

    @Override
    public void navigateToMyTripsPage() {
        GlobalHeader header = new GlobalHeader(getWebdriverInstance());
        header.navigateToMyTrips();
    }

    @Override
    public void setFieldOnRegisterForm(String field) {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        switch (field) {
            case "country":
                globalHeader.goToRegistrationPage().setCountryCode(posToCountryOrigin.
                    get(sessionParameters.getPointOfSale()));
                break;
            default:
                break;
        }

    }

    @Override
    public void verifyRegistrationPageURL() {
        RegisterNewUserPage page = new RegisterNewUserPage(getWebdriverInstance());
        String url = page.getRegistrationPageURL();
        assertThat(url.contains("account-unverified/login"))
                .as("Registration URL doesn't contain pattern: account-unverified/login").isTrue();
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
    public void changeEmailAndVerifyInDB() {
        AccountInfoPage accountInfoPage = new AccountInfoPage(getWebdriverInstance());


        String oldEmail = authenticationParameters.getUsername();
        String newEmail = "1234" + oldEmail;
        String customer_id = applicationModel.getCustomerIDFromDB(oldEmail);

        String sql1 = "select email from customer where customer_id = \'" + customer_id + "\'";


        accountInfoPage.changeEmail(newEmail);
        assertThat(accountInfoPage.isEmailSuccessfullyChanged())
                .as("Email was successfully updated")
                .isTrue();

        Map<String, Object> emailDB = jdbcTemplate.queryForMap(sql1);

        assertThat(!emailDB.get("EMAIL").equals(oldEmail))
                .as("New email: \'" + emailDB.get("EMAIL") + "\'" +
                        "  is NOT equals old email: \'" + oldEmail + "\'")
                .isTrue();

        assertThat(emailDB.get("EMAIL").equals(newEmail))
                .as("New email was correctly updated in DB : \'" + newEmail + "\'")
                .isTrue();
    }

    @Override
    public void confirmSubscriptionsFromDB(boolean isOn) {
        String subscOpt;
        String subscNews;
        if (isOn) {
            subscOpt = "01Y02Y03Y04Y05Y06Y07Y08Y09N10N11N12N";
            subscNews = "Y";
        }
        else {
            subscOpt = "01N02N03N04N05Y06Y07Y08N09N10N11N12N";
            subscNews = "Y"; //note WANTS_NEWS_LETTER in CUSTOMER table would still be
            // set as "Y" and DIV_SUBSCRIPTION_OPTED_IN_INFO column would be set
            // to "01N02N03N04N05Y06Y07Y08N09N10N11N12N" only if customer is
            // still subscribed for Travel Ticker newsletters
        }


        String sqlGetOptions = "select cds.div_subscription_opted_in_info from customer_div_subscription cds," +
                " customer c where cds.customer_id = c.customer_id and" +
                " c.email='" + authenticationParameters.getUsername() + "'";


        String sqlGetNews = "select c.wants_news_letter from customer_div_subscription cds, customer c where" +
                " cds.customer_id = c.customer_id and" +
                " c.email='" + authenticationParameters.getUsername() + "'";

        Map<String, Object> subsOptionsDB = jdbcTemplate.queryForMap(sqlGetOptions);

        assertThat(subsOptionsDB.get("DIV_SUBSCRIPTION_OPTED_IN_INFO").equals(subscOpt))
                .as("field DIV_SUBSCRIPTION_OPTED_IN_INFO: " + subsOptionsDB.get("DIV_SUBSCRIPTION_OPTED_IN_INFO") +
                " but expected " + subscOpt)
                .isTrue();

        Map<String, Object> subsNewsDB = jdbcTemplate.queryForMap(sqlGetNews);

        assertThat(subsNewsDB.get("WANTS_NEWS_LETTER").equals(subscNews))
                .as("field WANTS_NEWS_LETTER: " + subsNewsDB.get("WANTS_NEWS_LETTER") +
                " but expected " + subscNews)
                .isTrue();
    }


    @Override
    public void verifyTripDepartureValidationError() {
        SignInPage signIn = new SignInPage(getWebdriverInstance());
        assertThat(TRIP_DEPARTURE_DATE_VALIDATION_MSG.equalsIgnoreCase(signIn.getErrorMessages())).isTrue();
    }

    @Override
    public void verifySavedCreditCardOnMyAccountPage(String condition) {
        String last4CardDigit = purchaseParameters.getUserInformation().getCcNumber()
                .substring(purchaseParameters.getUserInformation().getCcNumber().length() - 4,
                        purchaseParameters.getUserInformation().getCcNumber().length());
        AccountPaymentInfoPage paymentInfoPage = new AccountPaymentInfoPage(getWebdriverInstance());

        try {
            assertThat(paymentInfoPage.getCreditCardNickName().endsWith(last4CardDigit))
                    .as("Nick name for saved card is correct").isTrue();
            assertThat(paymentInfoPage.getCreditCardNumber().endsWith(last4CardDigit))
                    .as("Credit card number is correct").isTrue();
            assertThat(paymentInfoPage.getCreditCardType().equals(purchaseParameters.getUserInformation().getCcType()))
                    .as("Credit card type for saved credit card is correct").isTrue();
            assertThat(paymentInfoPage.getYourName().trim()
                    .equals(purchaseParameters.getUserInformation().getFirstName() + " " +
                            purchaseParameters.getUserInformation().getLastName()))
                    .as("Your name for save card is correct").isTrue();
            assertThat(paymentInfoPage.getBillingAddress()
                    .equals(purchaseParameters.getUserInformation().getBillingAddress()))
                    .as("Billing address foe saved card is correct").isTrue();
            assertThat(paymentInfoPage.getExpirationDate().
                    equals(purchaseParameters.getUserInformation().getCcExpMonth() + "/"  +
                    purchaseParameters.getUserInformation().getCcExpYear()))
                    .as("Expiration date for saved credit card is correct").isTrue();
            if (" no".equals(condition)) {
                throw new RuntimeException("User has a saved card on My account page");
            }
        }
        catch (Exception e) {
            if (null == condition) {
                throw new RuntimeException("User has no saved card on My account page");
            }
        }
    }
}

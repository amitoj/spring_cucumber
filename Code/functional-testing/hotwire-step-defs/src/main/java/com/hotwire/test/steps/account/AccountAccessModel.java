/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account;

import com.hotwire.test.steps.search.SearchParameters;

/**
 * interface AccountAccessModel
 */
public interface AccountAccessModel {

    void accessAccountInformation();

    void verifyEmailOnOverviewPage();

    void verifyAccountInformationIsAvailable();

    void doNotSubscribeToHotwireDealsWhenRegister();

    void registerNewUserAndAuthenticate();

    void registerNewUserAndStay();

    void verifyNewUserIsCreated();

    boolean userExistsAndIsAuthenticated();

    void manageEmailSubscriptions();

    void accessEmailSubscriptionsFromEmail();

    void changeEmailSubscriptions(boolean subscribe, String subscriptionName);

    void confirmSubscriptionsUpdated();

    void verifySubscription(String subscriptionName);

    void manageAccountInfo();

    void manageTraveleNames();

    void updateFirstTravelerInfo();

    void changeEmailAddress();

    void confirmEmailUpdated();

    void changePassword(boolean isInvalid);

    void confirmPasswordUpdated();

    void manageAccountPrefs();

    void addFavoriteAirport(String airportName);

    /**
     * @param wasRemoved true if airport was removed, false otherwise
     */
    void confirmAirportUpdated(boolean wasRemoved);

    void removeFavoriteAirport();

    void confirmAirportRemoved();

    void confirmPasswordInvalid();

    void accessAccountTab(String tabName);

    void confirmRecentlyBookedTrip(String tripStartDate);

    void confirmRecentlyBookedGuestTrip(String tripStartDate);

    void confirmRecentlyBookedTripDetails(String tripType, String location,
                                          String tripStatus, SearchParameters searchParameters);

    /**
     * Cancellation of past purchase
     *
     * @param tripType - type of purchase {car | air | hotel}
     * @param location - detailed description in myAccount
     */
    void cancelPastPurchase(String tripType, String location, String tripStatus, SearchParameters searchParameters);

    void navigateToHomePage();

    void selectAddToMyStay();

    void confirmAddOptionsPage();

    void verifyCruiseNewsSubscription(boolean subscribe);

    void changeDefaultSubscription();

    void verifySubscriptionCheckboxState(final String state);

    void verifySubscriptionProposal(final String proposal);

    void verifySubscriptionOptionPresence(String isPresent);

    void accessSubAccountTab(String watchedTripsTabName);

    void confirmRecentlyWatchedTrip(String location, SearchParameters searchParameters);

    void logout();

    void enterUpperPassword();

    void enterLowerPassword();

    void changeUserPassword(String newPass, String confPass);

    void loadPasswordAssistancePageFromUHP();

    void navigateToSignInPage();

    void navigateToMyTripsPage();

    void logoutToCookiedMode();

    void verifyUserInCookiedMode();

    void verifyHfcStatusAfterLogin();

    void setFieldOnRegisterForm(String fiild);

    void verifyRegistrationPageURL();

    void changeEmailAndVerifyInDB();

    void confirmSubscriptionsFromDB(boolean isOn);

    void verifyTripDepartureValidationError();

    void enterBlankRegistrationInfo();

    void enterBlankSignInInfo();

    void verifyUkUhpPage();

    void verifySavedCreditCardOnMyAccountPage(String condition);

    void managePaymentInfo();

    void stopWatchingLatestTrip();
}

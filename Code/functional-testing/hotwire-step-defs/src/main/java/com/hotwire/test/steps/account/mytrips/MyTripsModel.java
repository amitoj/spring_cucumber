/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.account.mytrips;

import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;

/**
 * My Trips interface.
 */
public interface MyTripsModel {

    void navigateToMyTripsPage();

    void verifyMyTripSummaryPage();

    void verifyTripDetails(String bookingType);

    void navigateToTripDetails();

    void selectFirstTripSummaryItem(String bookingType);

    void accessMyTrips();

    void verifyCarTripDetailsPageHasCorrectInformation(AuthenticationParameters authenticationParameters,
                                                       CarSearchParameters carSearchParameters,
                                                       PurchaseParameters purchaseParameters);
}

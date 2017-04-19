/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account.mytrips;

import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import com.hotwire.testing.UnimplementedTestException;

/**
 * MyTripsModelRowWebApp
 */
public class MyTripsModelRowWebApp extends WebdriverAwareModel implements MyTripsModel {

    @Override
    public void navigateToMyTripsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyMyTripSummaryPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTripDetails(String bookingType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToTripDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectFirstTripSummaryItem(String bookingType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void accessMyTrips() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCarTripDetailsPageHasCorrectInformation(AuthenticationParameters authenticationParameters,
                                                              CarSearchParameters carSearchParameters,
                                                              PurchaseParameters purchaseParameters) {
        throw new UnimplementedTestException("Implement me!");
    }
}

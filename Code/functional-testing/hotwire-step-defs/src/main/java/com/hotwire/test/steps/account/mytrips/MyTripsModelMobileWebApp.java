/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.account.mytrips;

import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.account.mytrips.MobileAbstractTripDetailsPage;
import com.hotwire.selenium.mobile.account.mytrips.MobileCarTripDetailsPage;
import com.hotwire.selenium.mobile.account.mytrips.MobileFlightTripDetailsPage;
import com.hotwire.selenium.mobile.account.mytrips.MobileHotelTripDetailsPage;
import com.hotwire.selenium.mobile.account.mytrips.MobileTripSummaryPage;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import cucumber.api.PendingException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * MyTripsModelMobileWebApp
 */
public class MyTripsModelMobileWebApp extends WebdriverAwareModel implements MyTripsModel {

    @Override
    public void navigateToMyTripsPage() {
        MobileHotwireHomePage homePage = new MobileHotwireHomePage(getWebdriverInstance());
        homePage.navigateToMyTripsPage();
    }

    @Override
    public void verifyMyTripSummaryPage() {
        new MobileTripSummaryPage(getWebdriverInstance());
    }

    @Override
    public void verifyTripDetails(String tripType) {
        MobileTripSummaryPage tripSummaryPage = new MobileTripSummaryPage(getWebdriverInstance());
        assertThat(tripSummaryPage.hasAtLeastOneOfTripType(tripType)).as(
            "My Trips summary page should contain at least one " + tripType + " booking").isTrue();
        tripSummaryPage.navigateToTripDetailsPage(tripType);
        MobileAbstractTripDetailsPage detailsPage = getTripDetailsPage(tripType);
        if (detailsPage == null) {
            throw new PendingException("Returned trip details page was null," +
                                       " expected the details page for " + tripType);
        }
        detailsPage.verifyTripDetailsPage();
    }

    private MobileAbstractTripDetailsPage getTripDetailsPage(String tripType) {
        if ("hotel".equals(tripType)) {
            return new MobileHotelTripDetailsPage(getWebdriverInstance());
        }
        else if ("car".equals(tripType)) {
            return new MobileCarTripDetailsPage(getWebdriverInstance());
        }
        else if ("flight".equals(tripType)) {
            return new MobileFlightTripDetailsPage(getWebdriverInstance());
        }
        return null;
    }

    @Override
    public void navigateToTripDetails() {
        MobileTripSummaryPage tripSummaryPage = new MobileTripSummaryPage(getWebdriverInstance());
        tripSummaryPage.navigateToTripDetails();
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

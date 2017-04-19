/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.tripstarter;

import com.hotwire.selenium.desktop.us.tripstarter.TripStarterDetailsPage;
import com.hotwire.selenium.desktop.us.tripstarter.TripStarterSearchPage;


import static org.fest.assertions.Assertions.assertThat;

/**
 * class TripStarterSearchModelWebApp
 * updated Yulun Vladimir
 */
public class TripStarterSearchModelWebApp extends TripStarterSearchModelTemplate {

    @Override
    public void launchSearch(String originLocation, String destinationLocation) {
        TripStarterSearchPage tripStarterSearchPage = new TripStarterSearchPage(getWebdriverInstance());
        tripStarterSearchPage.setOriginLocation(originLocation)
                .setDestinationLocation(destinationLocation)
                .submit();
    }

    @Override
    public void verifyDetailsPage() {
        TripStarterDetailsPage tripStarterDetailsPage = new TripStarterDetailsPage(getWebdriverInstance());
        // image tripStarter details
        assertThat(tripStarterDetailsPage.getImgTripStarterDetails().isDisplayed())
                .as("Expected Tripstarter details image, but nothing")
                .isTrue();
        // link Tell us What u think
        assertThat(tripStarterDetailsPage.getLnkTellUsWhatYouThink().isDisplayed())
                .as("Expected link 'Tell us what u think' does not exist")
                .isTrue();
        //click Flights tab
        tripStarterDetailsPage.getTabFlight().click();
        assertThat(tripStarterDetailsPage.getTxtFligtsTabVerify().getText())
                .contains("Historical Average Airfares (based on 14- to 30-day advance searches)");
        //click Hotels tab
        tripStarterDetailsPage.getTabHotel().click();
        assertThat(tripStarterDetailsPage.getTxtHotelsTabVerify().getText())
                .contains("Historical Average Hotel Rates (based on 3-star, 3.5-star and 4-star hotels)");
    }

    @Override
    public void verifyWhenToGoModule() {
        String sExpectedText = "Activities are at their peak in winter...but so are the crowds. Summer";
        TripStarterDetailsPage tripStarterDetailsPage = new TripStarterDetailsPage(getWebdriverInstance());
        assertThat(tripStarterDetailsPage.getTxtWhenToGo().getText()).contains("When to go");
        assertThat(tripStarterDetailsPage.getTxtWhenToGo().getText()).contains(sExpectedText);
    }
}

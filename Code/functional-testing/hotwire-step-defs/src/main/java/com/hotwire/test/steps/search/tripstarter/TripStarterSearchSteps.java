/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.tripstarter;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * class TripStarterSearchSteps
 */
public class TripStarterSearchSteps extends AbstractSteps {

    @Autowired
    @Qualifier("tripStarterSearchModel")
    private TripStarterSearchModel tripStarterSearch;

    @Given("^I'm searching locations from \"([^\"]*)\" to \"([^\"]*)\" via tripstarter page$")
    public void setHotelDestinationLocation(String originLocation, String destinationLocation) {
        tripStarterSearch.launchSearch(originLocation, destinationLocation);
    }

    @Then("^I should see TripStarter details page$")
    public void should_see_TripStarter_details_page() {
        tripStarterSearch.verifyDetailsPage();
    }

    @Then("^I should see TripStarter WhenToGo module$")
    public void should_see_TripStarter_WhenToGo_module() {
        tripStarterSearch.verifyWhenToGoModule();
    }
}


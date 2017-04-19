/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.cruise;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.search.SearchParameters;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author akrivin
 * @since 10/16/12
 */
public class CruiseSearchSteps extends AbstractSteps {

    @Autowired
    @Qualifier("cruiseSearchModel")
    private CruiseSearchModel cruiseSearchModel;

    @Autowired
    @Qualifier("cruiseSearchParameters")
    private CruiseSearchParameters cruiseSearchParameters;

    @Given("^I'm searching for a cruise to \"([^\"]*)\"$")
    public void setCruiseDestination(String destinationLocation) {
        cruiseSearchParameters.setDestination(destinationLocation);

        SearchParameters globalSearchParameters = cruiseSearchParameters.getGlobalSearchParameters();
        globalSearchParameters.setDestinationLocation(destinationLocation);
    }

    @Given("^I want to go on a \"([^\"]*)\" ship$")
    public void setCruiseLine(String cruiseLine) {
        cruiseSearchParameters.setCruiseLine(cruiseLine);
    }

    @Given("^I want to sail from \"([^\"]*)\"$")
    public void setDeparturePort(String departurePort) {
        cruiseSearchParameters.setDeparturePort(departurePort);
    }

    @Given("^I am on the cruise home page$")
    public void navigateToCruisePage() {
        cruiseSearchModel.navigateToCruisePage();
    }

    @Given("^I have chosen a specific last minute cruise deal$")
    public void clickLastMinuteCruiseDeal() {
        cruiseSearchModel.clickLastMinuteCruiseDeal();
    }

    @Given("^I have chosen a specific top cruise deal$")
    public void clickTopCruiseDeal() {
        cruiseSearchModel.clickTopCruiseDeal();
    }

    @Then("^I will see cruise details$")
    public void verifyCruiseDetailsUrl() {
        cruiseSearchModel.verifyCruiseDetailsUrl();
    }

    @And("^I want to go on a \"([^\"]*)\"$")
    public void setdepartureMonth(String departureMonth) {
        cruiseSearchParameters.setDepartureMonth(departureMonth);
    }

    @And("^I want to go \"([^\"]*)\" duration$")
    public void setcruiseDuration(String cruiseDuration) {
        cruiseSearchParameters.setCruiseDuration(cruiseDuration);
    }

    @Then("I should see opinion link to provide feedback$")
    public void verifyOpinionLink() {
        cruiseSearchModel.verifyOpinionLink();
    }

    @And("^I navigate back from the cruise results page$")
    public void navigateBackFromCruiseResults() {
        cruiseSearchModel.navigateBackFromCruiseResultsPage();
    }
}

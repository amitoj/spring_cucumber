/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.activities;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.search.SearchParameters;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ActivitiesSearchSteps extends AbstractSteps {

    @Autowired
    @Qualifier("activitiesSearchModel")
    private ActivitiesSearchModel activitiesSearchModel;

    @Autowired
    @Qualifier("activitiesSearchParameters")
    private ActivitiesSearchParameters activitiesSearchParameters;

    @Given("^I want to see activities in (.*)$")
    public void setLocations(String destinationLocation) {
        SearchParameters globalSearchParameters = activitiesSearchParameters.getGlobalSearchParameters();
        globalSearchParameters.setDestinationLocation(destinationLocation);
    }

    @And("^I get all the activities in (.*)$")
    public void verifyVacationPage(String destination) {
        activitiesSearchModel.verifyVacationPage(destination);
    }

    @And("^I see an error message for not having any activities$")
    public void verifyErrorMessage() {
        activitiesSearchModel.verifyErrorMessage();
    }

}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.steps;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.search.SearchService;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.transformer.jchronic.ChronicConverter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * User: v-ngolodiuk
 * Since: 1/8/15
 */

public class SearchSteps extends AbstractSteps {

    @Autowired
    SearchService searchService;

    @Autowired
    private RequestProperties requestProperties;

    @Given("^I use result id of the first solution$")
    public void extractResultId() {
        searchService.extractResultId(0);
    }

    @Given("^destination location is (.*)$")
    public void setDestinationLocation(String destinationLocation) {
        requestProperties.setDestinationLocation(destinationLocation);
    }

    @Given("^date and time range is between (.*) and (.*)$")
    public void setDateTimeRange(@Transform(ChronicConverter.class) Calendar from,
                                 @Transform(ChronicConverter.class) Calendar to) throws Throwable {
        setStartAndEndDates(from, to);
    }

    private void setStartAndEndDates(Calendar from, Calendar to) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(from.getTime());

        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        requestProperties.setStartDate(datatypeFactory.newXMLGregorianCalendar(gregorianCalendar));
        gregorianCalendar.setTime(to.getTime());
        requestProperties.setEndDate(datatypeFactory.newXMLGregorianCalendar(gregorianCalendar));
    }

    @And("^rental date is in the nearest future$")
    public void rental_date_is_in_the_nearest_future() throws DatatypeConfigurationException {
        Random rand = new Random();
        int randomTo = rand.nextInt((40 - 1) + 1) + 1;
        GregorianCalendar from = new DateTime().plusDays(1).toGregorianCalendar();
        GregorianCalendar to = new DateTime().plusDays(randomTo).toGregorianCalendar();
        setStartAndEndDates(from, to);
    }


    @Then("^I see search results are present$")
    public void search_results_are_present() {
        searchService.checkSearchResponseExist();
        searchService.verifySearchResultsArePresent();
    }

    @Given("^(hotel|car) search has been executed$")
    public void hotel_Search_has_been_executed(String vertical) {
        requestProperties.setVertical(Character.toUpperCase(vertical.charAt(0)) + vertical.substring(1));
        searchService.checkSearchResponseExist();
    }



}

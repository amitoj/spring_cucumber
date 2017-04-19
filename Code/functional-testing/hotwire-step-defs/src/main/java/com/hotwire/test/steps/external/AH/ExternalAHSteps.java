/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.external.AH;

import static org.fest.assertions.Assertions.assertThat;

import com.hotwire.selenium.desktop.us.results.AHResultsPage;
import com.hotwire.selenium.desktop.us.results.ExternalAHPage;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author v-jhernandeziniguez
 *
 */
public class ExternalAHSteps extends AbstractSteps {

    private static ExternalAHPage page;
    private static Date today = new Date();
    private static AHResultsPage results;
    //private static String currentUrl;

    @Autowired
    @Qualifier("externalAHModel")
    private ExternalAHModel model;

    @Given("^I want to access url \"([^\"]*)\"$")
    public void accessDisambiguationPage(String baseUrl) throws Throwable {
        page = model.accessDisambiguationPage(baseUrl);
    }

    @When("^The page is loaded$")
    public void pageIsLoaded() throws Throwable {
        assertThat(page.isVailLinkDisplayed()).as("The page was not properly loaded").isTrue();
    }

    @Then("^I click on Vail option in the disambiguation page$")
    public void disambiguationPageDisplayed() throws Throwable {
        //DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        //String depDate = sdf.format(ExternalAHPage.addDays(today, 30));
        //String retDate = sdf.format(ExternalAHPage.addDays(today, 34));
        //currentUrl = model.getCurrentUrlAndAddDates(depDate, retDate);
        String airPortSelectionPrompt = page.getAirportSelectionText();
        assertThat(airPortSelectionPrompt).as("There is a mismatch on Strings").isEqualTo(page.getEXPECTED_TEXT());
        page.getVailLink().click();
    }

    @Then("^I should be redirected to results page$")
    public void redirectedToResultsPage() throws Throwable {
        results = model.redirectedToResults();
        assertThat(results.isCheckInDisplayed()).as("Results Page is not displayed").isTrue();
    }

    @Then("^departure date must match sysdate plus (\\d+) days$")
    public void departDate(int days) throws Throwable {
        page.setDepartureDate(today, days);
        System.out.println("-------------------------");
        System.out.println("Page: " + page.getDepartureDate() + " vs " + "results: " + results.getCheckIn());
        System.out.println("-------------------------");
        assertThat((page.getDepartureDate()).equals(results.getCheckIn()))
            .as("Both Values do not match")
            .isTrue();
    }

    @Then("^return date must match sysdate plus (\\d+) days$")
    public void returnDate(int days) throws Throwable {
        page.setReturnDate(today, 34);
        System.out.println("Results: " + results.getCheckOut());
        System.out.println("Page: " + page.getReturnDate());
        assertThat((page.getReturnDate()).equals(results.getCheckOut()))
            .as("Both Values do not match")
            .isTrue();
    }
}

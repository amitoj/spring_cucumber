/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.partners;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;

/**
 * User: v-vzyryanov
 * Date: 4/17/13
 * Time: 5:57 AM
 */
public class CarDareToCompareSteps extends AbstractSteps {

    @Autowired
    @Qualifier("partnersModel")
    private PartnersModel model;

    /**
     * Selecting partners to compare with
     * @param commaSeparatedPartnersList String - list of car's retailers to compare
     */
    @When("^I select next partners \"([^\"]*)\" in (first D2C module|second D2C module|fare finder)$")
    public void selectPartnersToCompareWith(String commaSeparatedPartnersList, String container) {
        model.selectPartnersToCompareWith(
            Arrays.asList(commaSeparatedPartnersList.split(",")),
            container
        );
    }

    /**
     * Verify that all popup windows were opened and search
     * criteria is the same.
     * @param params DataTable like below..
     *
     *  | - Partner ----- | - Location ---------------------------------------------------------- |
     *  |   Kayak         | New York, NY - John F Kennedy Intl (JFK) - New York (New York, NY US) |
     *  |   BookingBuddy  | New York City, NY (JFK)                                               |
     */
    @Then("^I check search options when I compare with other car's retailers$")
    public void verifyCarPartnerSearchOptions(DataTable params) {
        model.verifyCarPartnerSearchOptions(params);
    }

    @Then("^next partners \"([^\"]*)\" is (visible|invisible)$")
    public void verifyCarPartnerIsPresenting(String partner, String visibility) {
        model.verifyCarPartnerIsDisplayed(partner, visibility);
    }

    @Then("^I select all partners for comparison$")
    public void selectAllPartners() {
        model.selectAllCarRetailers();
    }

    @Then("^all of car retailers are selected$")
    public void verifyAllCarPartnersAreSelectedForComparison() {
        model.allCarRetailersSelected(true);
    }

    @Given("^I am able to compare all selected retailers from (first|second) D2C module$")
    public void compareAllRetailersFromD2C(String numberOfModule) {
        model.compareAllRetailersFromD2C(numberOfModule);
    }


}

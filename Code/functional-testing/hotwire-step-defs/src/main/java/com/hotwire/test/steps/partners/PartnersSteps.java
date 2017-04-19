/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.partners;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by IntelliJ IDEA.
 * User: vjong
 * Date: Jul 5, 2012
 * Time: 9:22:56 AM
 */
public class PartnersSteps extends AbstractSteps {

    @Autowired
    @Qualifier("partnersModel")
    private PartnersModel model;

    @Given("^I am directed by (ShopAtHome|Kayak|retail results sidedoor|opaque results sidedoor) to Hotwire$")
    public void navigatePartnersLink(String partner) {
        model.navigatePartnersLink(partner.trim());
    }

    @Given("^I follow a side door url$")
    public void followSideDoorUrl() throws UnsupportedEncodingException {
        model.followSideDoorUrl();
    }

    @When("^I want to go to the (Hotels|Cars|Flights|Vacations) landing page$")
    public void clickVerticalTab(String vertical) {
        model.clickVerticalTab(vertical);
    }

    @Then("^I will see the (ShopAtHome) banner$")
    public void verifyPartnerBannerIsDisplayed(String partner) {
        model.verifyPartnerBannerIsDisplayed(partner);
    }

    @Then("^I will see the partner deal in the results$")
    public void verifyPartnerDealInResults() {
        model.verifyPartnerDealInResults();
    }

    @When("^I subscribe to Hotwire deals$")
    public void subscribeToHotwireDeals() {
        model.subscribeToHotwireDeals();
    }

    @Then("^I receive subscription confirmation$")
    public void verifySubscriptionConfirmation() throws Throwable {
        model.verifySubscriptionConfirmation();
    }

    /**
     * Checks carRentals banner on car results page
     * Banner can contains static price (when we doesn't use
     * carRentals api to compare total prices with hotwire's prices)
     * or dynamic price in other way.
     */
    @Then("^I will see carRentals.com banner with (static|dynamic) price$")
    public void carRentalsBanner(String bannerType) {
        model.verifyCarRentalsBannerOnResultsPage(bannerType);
    }

    @Then("^\"Compare with (CarRentals.com|Expedia)\" checkbox is (checked|unchecked|hidden)$")
    public void verifyCompareWithPartnersCheckboxes(String partnerName, String state) {
        model.verifyCarCompareWithPartnersCheckboxesState(partnerName, state);
    }

    @Then("^I should[\\s]*(not)?[\\s]*see \"(CarRentals.com|Expedia)\" page$")
    public void verifyPartnerPopupVisibility(String condition, String partnerName) {
        model.verifyPartnerPopupVisibility(partnerName, !"not".equals(condition));
    }

    @Then("^I (will|will not) see the trip advisor badge on the hotel landing page$")
    public void verifyTripAdvisorBadge(String state) {
        model.verifyTripAdvisorBadge(state.trim().equals("will"));
    }

    @Then("^Google convergence pixel value corresponds to (\\d+) days to arrival$")
    public void verifyGoogleConvergencePixel(int daysToArrival) {
        model.verifyGoogleConvergencePixel(daysToArrival);
    }

    @Then("^I verify Destination cities pop-up window$")
    public void verifyDestinationCitiesPage() {
        model.verifyDestinationCitiesPage();
    }

    @Then("^MeSo ads is displayed on (home|air result|air details) page$")
    public void verifyMeSoAds(String page) {
        model.verifyMeSoAds(page);

    }
}

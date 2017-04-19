/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps;

import com.hotwire.test.openapi.ApiProperties;
import com.hotwire.test.openapi.DistributedOpaqueService;
import com.hotwire.test.openapi.HotwireProduct;
import com.hotwire.test.openapi.SearchProperties;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author pgleyzer
 */

@ContextConfiguration("classpath:cucumber-do-context.xml")
@DirtiesContext
public class DistributedOpaqueSteps {
    @Resource
    private Map<String, DistributedOpaqueService> models;

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private SearchProperties searchProperties;

    private DistributedOpaqueService service;

    private void setModel(String product) {
        apiProperties.setHotwireProduct(HotwireProduct.validate(product));
        service = models.get(apiProperties.getHotwireProduct().getSimpleName());
    }


    @Given("^I am (Public|Private) API user$")
    public void setApiType(String type) throws Throwable {
        apiProperties.setEndpoint(type);
    }

    @Given("^I send request to search (cars|hotels) in (.*)$")
    public void sendCarSearchRequest(String vertical, String destinationLocation) throws Throwable {
        setModel(vertical);
        searchProperties.setDestinationLocation(destinationLocation);
        service.sendSearchRequest();
    }

    @Then("^I get search results$")
    public void verifySearchResults() throws Throwable {
        service.verifySearchResults();
    }

    @Given("^I open the deeplink$")
    public void openDeepLink() {
        service.openDeepLink();
    }

    @Then("^I get details page html$")
    public void validateDetailsPage() {
        service.validateDetailsPage();
    }

    @When("^I store first solution details$")
    public void storeSearchSolutionDetails() throws Throwable {
        service.storeSearchSolutionDetails();
    }

    @When("^I request solutions availability$")
    public void requestSolutionsAvailability() throws Throwable {
        service.requestSolutionsAvailability();
    }

    @Then("^I get solutions details")
    public void verifySolutionsDetails() throws Throwable {
        service.verifySolutionsDetails();
    }

    @When("^I book it$")
    public void bookSolution() throws Throwable {
        service.bookSolution();
    }

    @When("^I refund it$")
    public void refundPurchase() throws Throwable {
        service.refundPurchase();
    }

    @Then("^I check the status of purchase$")
    public void checkPurchaseStatus() throws Throwable {
        service.checkPurchaseStatus();
    }

    @Then("^I see the purchase was refunded$")
    public void verifyRefund() throws Throwable {
        service.verifyRefund();
    }

    @Then("^I see that it was booked successfully$")
    public void verifyBooking() throws Throwable {
        service.verifyBooking();
    }

    @When("^I get confirmation response$")
    public void verifyConfirmation() throws Throwable {
        service.verifyConfirmation();
    }
    @Given("^I request neighborhoods in (.*)$")
    public void requestNeighborhoodsInfo(String location) throws Throwable {
        setModel("hotel");
        searchProperties.setDestinationLocation(location);
        service.requestNeighborhoodsInfo();
    }

    @Then("^I get neighborhoods info$")
    public void verifyNeighborhoodsInfo() throws Throwable {
        service.verifyNeighborhoodsInfo();
    }

    @Given("^I request deals for (\\d.\\d) star hotels$")
    public void requestHotelDeals(float starRating) throws Throwable {
        setModel("hotel");
        searchProperties.setStarRating(starRating);
        service.requestHotelDeals();
    }

    @Then("^I get hotel deals$")
    public void verifyHotelDeals() throws Throwable {
        service.verifyHotelDeals();
    }

    @Given("^I request list of hotel amenities$")
    public void requestAmenities() throws Throwable {
        setModel("hotel");
        service.requestAmenities();
    }

    @Then("^I get amenities info$")
    public void verifyAmenities() throws Throwable {
        service.verifyAmenities();
    }

    @Given("^I request hotel trt in (.*)$")
    public void requestHotelTrt(String location) throws Throwable {
        setModel("hotel");
        searchProperties.setDestinationLocation(location);
        service.requestHotelTrt();
    }

    @Then("^I get hotel trt info$")
    public void verifyHotelTrt() throws Throwable {
        service.verifyHotelTrt();
    }

}

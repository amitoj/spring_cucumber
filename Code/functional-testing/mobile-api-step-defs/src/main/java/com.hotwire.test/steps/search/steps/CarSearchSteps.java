/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.steps;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.search.SearchService;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * User: v-dsobko
 * Since: 1/23/15
 */

public class CarSearchSteps extends AbstractSteps {

    private static final String OPAQUE = "opaque";

    @Autowired
    SearchService searchService;

    @Autowired
    private RequestProperties requestProperties;

    @Autowired
    private RequestPaths requestPaths;

    @Given("^I use the following car search url (.*)$")
    public void useMobileURL(String customPath) {
        requestPaths.setCarSearchPath(customPath);
    }

    @When("^I execute car search request$")
    public void executeCarSearchRequest() throws DatatypeConfigurationException {
        searchService.executeCarSearch();
    }

    @And("^I use result id of the (first|second) (opaque|retail|prepaid) solution$")
    public void useSolutionToRequestForBooking(String numberOfSolution, String solutionType) {
        requestProperties.setIsOpaque(OPAQUE.equalsIgnoreCase(solutionType));
        if (numberOfSolution.equalsIgnoreCase("first")) {
            searchService.extractResultIdOfSpecificOpacity(solutionType, 1);
        }
        if (numberOfSolution.equalsIgnoreCase("second")) {
            searchService.extractResultIdOfSpecificOpacity(solutionType, 2);
        }
    }

    @Given("^I use the (.*) car type result id in the request for details$")
    public void useCarTypeResultIdToRequestDetails(String carType) {
        searchService.extractCarTypeResultIdForDetails(carType);
    }

    @And("^pickup location is (.*)$")
    public void setPickupLocation(String location) {
        requestProperties.setPickupLocation(location);
    }

    @Given("^dropoff location is (.*)$")
    public void setDropOffLocation(String location) {
        requestProperties.setDropOffLocation(location);
        requestProperties.setIsOneWayTrip(true);
    }

    @Given("^deposit method is (.*)$")
    public void setDepositMethod(String depositMethod) {
        requestProperties.setDepositMethod(depositMethod);
    }

    @Given("^age of drive is (.*)$")
    public void setAgeOfDriver(String arg1) {
        requestProperties.setAgeOfDriver(arg1);
    }

    @Then("^car opaque results are present$")
    public void verifyOpaqueResults() {
        assert searchService.verifyResultsOpacity(true);
    }

    @Then("^car retail results are present$")
    public void verifyRetailResults() {
        assert searchService.verifyResultsOpacity(false);
    }

    @And("^car search metadata is present$")
    public void verifyCarSearchMetadata() {
        searchService.verifyCarSearchMetadata();
    }

    @And("^number of car types is greater than (\\d+)$")
    public void verifyNumberOfCarTypes(int arg1) {
        searchService.verifyNumberOfCarTypes(arg1);
    }

    @And("^number of rental agencies is greater than (\\d+)$")
    public void verifyNumberOfCarAgencies(int arg1) {
        searchService.verifyNumberOfCarAgencies(arg1);
    }

    @And("^car info contains car type image URLs$")
    public void verifyCarTypeImageUrls() {
        searchService.verifyCarTypeImageUrls();
    }

    @Then("^at least one local destination search exists$")
    public void verifyLocalDestination() {
        assert searchService.verifyLocalDestination();
    }

    @Then("^for airport car I do(?:\\s(not))? have distance from my address information$")
    public void verifyDistanceFromAddress(String negateString) {
        searchService.verifyDistanceFromAddress(negateString);
    }

    @Then("^I get list of locations in disambiguation response$")
    public void verifyDisambiguationResponse() {
        searchService.verifyDisambiguationResponse();
    }

    @Then("^opaque car solutions have strike through price info$")
    public void verifyStrikeThroughPriceInfoForOpaqueCars()  {
        searchService.verifyStrikeThroughPrice();
    }

    @Then("^debit unfriendly flag is present in search results$")
    public void verifyDebitUnfriendlyFlag()  {
        searchService.verifyDebitUnfriendlyFlagOnSearch();
    }

    @Then("^accepted card type field is present in search results$")
    public void verifyAcceptedCardTypeField()  {
        searchService.verifyAcceptedCardTypeFieldOnSearch();
    }

    @Then("^prepaid retail field is present in search results$")
    public void verifyPrepaidRetailField()  {
        searchService.verifyPrepaidRetailFieldOnSearch();
    }

    @And("^agency longitude and latitude is present in search response$")
    public void verifyLongitudeAndLatitudePresenceInSearchResponse() {
        searchService.verifyLongitudeAndLatitudePresenceInSearch();
    }

    @And("^centroid is present in search response$")
    public void verifyCityCentroidInSearchResponse() {
        searchService.verifyCityCentroidInSearchResponse();
    }



}

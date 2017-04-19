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

public class HotelSearchSteps extends AbstractSteps {


    private static final String OPAQUE = "opaque";

    @Autowired
    SearchService searchService;

    @Autowired
    private RequestProperties requestProperties;

    @Autowired
    private RequestPaths requestPaths;

    @Given("^I use the following (opaque|retail) hotel search url (.*)$")
    public void useMobileURL(String searchType, String customPath) {
        if (searchType.equalsIgnoreCase(OPAQUE)) {
            requestPaths.setHotelSearchOpaquePath(customPath);
        }
        else {
            requestPaths.setHotelSearchRetailPath(customPath);
        }
    }

    @Given("^number of rooms is (\\d+)$")
    public void setNumberOfRooms(int numberOfRooms) {
        requestProperties.setNumberOfRooms(numberOfRooms);
    }

    @Given("^number of adults is (\\d+)$")
    public void setNumberOfAdults(int numberOfAdults) {
        requestProperties.setNumberOfAdults(numberOfAdults);
    }

    @Given("^number of children is (\\d+)$")
    public void setNumberOfChildren(int numberOfChildren) {
        requestProperties.setNumberOfChildren(numberOfChildren);
    }

    @When("^I execute (opaque|retail) hotel search request$")
    public void executeRequest(String searchScope) throws DatatypeConfigurationException {
        searchService.executeHotelSearch(searchScope);
    }

    @When("^I execute hotel deal search request$")
    public void executeDealSearchRequest() throws DatatypeConfigurationException {
        searchService.executeHotelDealSearch();
    }

    @And("^search criteria is present$")
    public void checkSearchCriteria() {
        searchService.checkSearchCriteria();
    }

    @And("^hotel search metadata (is|is not) present$")
    public void checkHotelSearchMetadata(String isPresent) {
        searchService.checkHotelSearchMetadata("is".equalsIgnoreCase(isPresent));
    }

    @Then("^number of neighborhoods is greater than (\\d+)$")
    public void checkNumberOfNeighborhoods(int arg1) {
        searchService.checkNumberOfNeighborhoods(arg1);
    }

    @Then("^number of search results is greater than (\\d+)$")
    public void checkNumberOfSearchResults(int arg1) {
        searchService.checkNumberOfSearchResults(arg1);
    }

    @And("^there are no errors and warnings$")
    public void checkNumberOfSearchResults() {
        searchService.checkNoErrorsAndWarningsAfterSearch();
    }

    @And("^there is Trip Advisor rating for MOLO hotels$")
    public void checkTripAdvisorRatingForMOLOSolutions() {
        searchService.checkTripAdvisorRatingForMOLOSolutions();
    }

    @And("^I use the result id for details (\\d+.\\d+) star hotel from neighborhood (\\d+)$")
    public void choose_result_from_given_neighborhood_for_details_with_star_rating(float rating, int neighborhoodId) {
        searchService.chooseResultFromNeighborhoodForDetailsWithStarRating(rating, neighborhoodId);
    }

    @Given("^I use result id of the opaque solution with (\\d+.\\d+) star rating$")
    public void extractOpaqueHotelResultIdWithRating(float rating) {
        searchService.extractOpaqueHotelResultIdWithRating(rating);
    }

    @Given("^I use the MOLO result id for details$")
    public void extractMOLOResultId() {
        searchService.chooseMOLOResultForDetails();
    }

    @Given("^I use the fully opaque result id for details$")
    public void extractFullyOpaquedResultId() {
        searchService.chooseOPAQUEResultForDetails();
    }

    @And("^(opaque|retail) solution details are present$")
    public void verifySolutionDetailsOnSearch(String solutionType) {
        if (solutionType.equalsIgnoreCase(OPAQUE)) {
            searchService.verifyOpaqueSolutionDetailsOnSearch();
        }
        else {
            searchService.verifyRetailSolutionDetailsOnSearch();
        }
    }

    @And("^MOLO solutions are absent$")
    public void verifyMOLOSolutionsAreAbsent() {
        searchService.verifyMOLOSolutionsAreAbsent();
    }

    @And("^(opaque|retail) results hotel amenities are present$")
    public void verifyAmenitiesOnResults(String resultType) {
        searchService.verifyAmenitiesOnResults(resultType);
    }

    @And("^results neighborhoods contain city id$")
    public void verifyNeighborhoodsContainCityId() {
        searchService.verifyNeighborhoodsContainCityId();
    }

    @And("^(opaque|retail) cost summary information is present$")
    public void verifyCostSummaryInformation(String resultType) {
        searchService.verifyCostSummaryInformation(resultType);
    }

    @And("^tag line for neighborhood is present$")
    public void verifyNeighborhoodTagLine() {
        searchService.verifyNeighborhoodTagLine();
    }

    @And("^number of star rating hotels is greater than (\\d+)$")
    public void verifyNumberOfStarRatingHotels(int minHotelsNumber) {
        searchService.verifyNumberOfStarRatingHotels(minHotelsNumber);
    }

    @And("^proper fields are revealed for MOLO solutions$")
    public void verifyProperFieldsAreRevealed() {
        searchService.verifyProperFieldsAreRevealed();
    }

    @And("^I execute xml exploit request body from (.*) file and receive UnmarshallingFailureException$")
    public void useXmlExploitRequestBody(String requestFile) throws Throwable {
        searchService.searchAndVerifyXmlExploit(requestFile);
    }

    @Given("^dealhash is (.*)$")
    public void setDealHash(String dealHash) {
        searchService.setDealHash(dealHash);
    }

    @And("^deal (UP|DOWN|UNAVAILABLE|NON_APPLICABLE|SAME) status is present$")
    public void verifyDealStatus(String dealStatus) {
        searchService.verifyDealStatus(dealStatus);
    }

}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.packages;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.search.SearchParameters;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.transformer.jchronic.ChronicConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Calendar;

/**
 * User: akrivin
 * Date: 10/16/12
 * Time: 2:22 PM
 */
public class PackagesSearchSteps extends AbstractSteps {

    @Autowired
    @Qualifier("packagesSearchModel")
    private PackagesSearchModel packagesSearchModel;

    @Autowired
    @Qualifier("packagesSearchParameters")
    private PackagesSearchParameters packagesSearchParameters;

    @Given("^I'm searching for \"([^\"]*)\" vacation from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void setAirDestinationLocation(String vacationPackage, String origLocation, String destinationLocation) {
        packagesSearchParameters.setOrigLocation(origLocation);
        packagesSearchParameters.setvacationPackage(vacationPackage);
        packagesSearchModel.clickVacationPackageRadioButton(vacationPackage);
        SearchParameters globalSearchParameters = packagesSearchParameters.getGlobalSearchParameters();
        globalSearchParameters.setDestinationLocation(destinationLocation);
    }

    @Given("^I will travel between \"([^\"]*)\" session and \"([^\"]*)\" session in vacation$")
    public void setTimeSlots(String startAnytime, String endAnytime) {
        packagesSearchParameters.setStartAnytime(startAnytime);
        packagesSearchParameters.setEndAnytime(endAnytime);
    }

    @Given("I want partial hotel stay between (.*) and (.*)$")
    public void setHotelPartialStay(@Transform(ChronicConverter.class) Calendar hotelStartDate,
                                    @Transform(ChronicConverter.class) Calendar hotelEndDate) {
        packagesSearchModel.invokeHotelPartialStay();
        packagesSearchParameters.sethotelStartdate(hotelStartDate.getTime());
        packagesSearchParameters.sethotelEnddate(hotelEndDate.getTime());
    }

    @Given("^I want (\\d+) room\\(s\\) in vacation$")
    public void setNumberOfHotelRooms(Integer numberOfHotelRooms) {
        packagesSearchParameters.setNumberOfHotelRooms(numberOfHotelRooms);
    }

    @Given("^I will be traveling with (\\d+) adults in vacation$")
    public void setNumberOfAdults(Integer numberOfAdults) {
        packagesSearchParameters.setNumberOfAdults(numberOfAdults);
    }

    @Given("^I will be traveling with (\\d+) seniors in vacation$")
    public void setNumberOfSeniors(Integer numberOfSeniors) {
        packagesSearchParameters.setNumberOfSeniors(numberOfSeniors);
    }

    @Given("^I will be traveling with \"([^\"]*)\" children with \"([^\"]*)\" years in vacation$")
    public void setNumberOfChildren(Integer numberOfChildren, Integer numberOfChildAge) {
        packagesSearchParameters.setNumberOfChildren(numberOfChildren);
        packagesSearchParameters.setNumberOfChildAge(numberOfChildAge);
    }

    @Then("^I should redirect vacation.com with respective OD pair results$")
    public void verifyPackagesResultsPage() {
        packagesSearchModel.verifyPackagesResultsPage();
    }

    @And("^I want to add more destinations")
    public void addMoreDestinations() {
        packagesSearchModel.addMoreDestinations();
    }

    @Then("^I should redirect vacation.com with two destination selected")
    public void verifyAddMoreDestinationResults() {
        packagesSearchModel.verifyAddMoreDestinationResults();
    }

    @Given("^I'm searching for a (HC|AC) package to \"([^\"]*)\"$")
    public void setHotelPackageSearch(String packageType, String destinationLocation) {
        packagesSearchParameters.setvacationPackage(packageType);
        packagesSearchModel.clickVacationPackageRadioButton(packageType);
        SearchParameters globalSearchParameters = packagesSearchParameters.getGlobalSearchParameters();
        globalSearchParameters.setDestinationLocation(destinationLocation);
        packagesSearchParameters.setOrigLocation(null);
    }

    @Given("^I see Air Plus Car package$")
    public void showAirPlusCar() {
        packagesSearchModel.showAirPlusCar();
    }

    @Given("^I click on an external link \"([^\"]*)\"$")
    public void accessExternalLink(String sURI) {
        packagesSearchModel.accessExternalLink(sURI);
    }

    @Then("^I see vacation deals for \"(.*)\" and \"(.*)\" pair$")
    public void verifyVacationURL(String destination, String origin) {
        packagesSearchModel.verifyVacationURL(destination, origin);
    }

    @Then("^I see AHC vacation search in DB for (.*)$")
    public void verifySearchVacationInDBForUser(String userName) {
        packagesSearchModel.verifyAHCVacationSearchInDBForUser(userName);
    }

    @Given("^I launch external AHC search$")
    public void launchExternalAHCSearch() {
        packagesSearchModel.launchExternalAHCSearch();
    }

    @Then("^I should redirect vacation.com with default child age is 11$")
    public void verifyDefaultChildAgeIs11() {
        packagesSearchModel.verifyDefaultChildAgeIs11();
    }

    @Given("^I launch a search with external link to theme page farefinder$")
    public void launchExternalCSearchToThemePage() {
        packagesSearchModel.launchExternalCSearchToThemePage();
    }

    @Then("I should see theme page farefinder with origination \"(.*)\" and destination \"(.*)\"")
    public void verifyPackageThemePage(String origination, String destination) {
        packagesSearchModel.verifyPackageThemePage(origination, destination);
    }

    @Given("I start package theme search without specifying search parameters")
    public void startPackageThemeSearchWithoutSpecifyingParameters() {
        packagesSearchModel.startPackageThemeSearchWithoutSpecifyingParameters();
    }

    @Then("I should redirect vacation.com with origination \"(.*)\" and destination \"(.*)\"")
    public void verifyODPairsOnPackagesResultsPage(String origination, String destination) {
        packagesSearchModel.verifyODPairsOnPackagesResultsPage(origination, destination);
    }

    @Then("^Confirm the following error is received: \"([^\"]*)\"$")
    public void confirmErrorIsReceivedAs(String errMsg) throws Throwable {
        packagesSearchModel.corfirmErrorMsgIsReceivedAs(errMsg);
    }
}

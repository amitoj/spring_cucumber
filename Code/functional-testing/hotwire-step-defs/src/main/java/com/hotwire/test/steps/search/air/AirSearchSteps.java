/**
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.air;

import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.search.SearchParameters;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.transformer.jchronic.ChronicConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class provides Air search steps.
 */

public class AirSearchSteps extends AbstractSteps {

    @Autowired
    @Qualifier("airSearchModel")
    private AirSearchModel airSearchModel;

    @Autowired
    @Qualifier("airSearchParameters")
    private AirSearchParameters airSearchParameters;

    @Autowired
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Given("^I'm searching for a flight from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void setAirDestinationLocation(String origLocation, String destinationLocation) {
        searchParameters.setPGoodCode(PGoodCode.A);
        airSearchParameters.setOrigLocation(origLocation);

        SearchParameters globalSearchParameters = airSearchParameters.getGlobalSearchParameters();
        globalSearchParameters.setDestinationLocation(destinationLocation);
    }

    @Given("^I will be flying with (\\d+) passengers$")
    public void setPassengerNumber(Integer passengerNumber) {
        airSearchParameters.setPassengers(passengerNumber);
    }

    @Given("^I want to travel one way (.*)$")
    public void setStartDate(@Transform(ChronicConverter.class) Calendar start) {
        searchParameters.setPGoodCode(PGoodCode.A);
        SearchParameters globalSearchParameters = airSearchParameters.getGlobalSearchParameters();
        globalSearchParameters.setStartDate(start.getTime());
        //explicitly set as to indicate that it was not omitted, but intended this way
        globalSearchParameters.setEndDate(null);
    }

    @Given("^The vt for retail speedbump is activated$")
    public void showRetailFlightWithSpeedBump() {
        airSearchModel.showRetailFlightWithSpeedBump();
    }

    @Given("^The vt for opaque speedbump is activated$")
    public void showOpaqueFlightWithSpeedBump() {
        airSearchModel.showOpaqueFlightWithSpeedBump();
    }

    @Then("^I should see a MFI layer$")
    public void validateMfiLayer() {
        airSearchModel.verifyMFI(airSearchParameters);
    }

    //private static Calendar hwCal = Calendar.getInstance();

    //public final static String DATEFORMAT_NORMAL = "MM/dd/yyyy";

    /**
     * Generate today date as string
     *
     * return date as string with format DATEFORMAT_NORMAL
     */
    /*
    @SuppressWarnings("unused")
    private String dateToString() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_NORMAL);
        return sdf.format(hwCal.getTime());
    }
    */
    /**
     * Generate date as string with shift from today date
     * negative shift mean shift to past
     *
     * param shift
     * return
     */
    /*
    @SuppressWarnings("unused")
    private String shiftDateToString(int shift) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_NORMAL);
        hwCal.setTime(new Date());
        hwCal.add(Calendar.DAY_OF_YEAR, shift);
        return sdf.format(hwCal.getTime());
    }
    */
    /*@Given("^I want to access the activities and services tab in Air billing page$")
    public void showActivateServicesTab() {
       airSearchModel.showActivateServicesTab();
       }*/

    @Given("^I want to access the opaque flight with out price mask$")
    public void showOpaqueFlightWithOutPriceMask() {
        airSearchModel.showOpaqueFlightWithOutPriceMask();
    }

    @Given("^I want to access the opaque flight with price mask$")
    public void showOpaqueFlightWithPriceMask() {
        airSearchModel.showOpaqueFlightWithPriceMask();
    }

    /*@Given("^I want to access the retail flight in Air results page$")
    public void completeRetailFlightClickable() {
        airSearchModel.completeRetailFlightClickable();
            }*/

    @Given("^I'm doing a search with flight number (\\d+) from \"([^\"]*)\" to \"([^\"]*)\" on (.*)$")
    public void setFlights(int number, String fromLocation, String toLocation,
                           @Transform(ChronicConverter.class) Calendar startDate) {
        searchParameters.setPGoodCode(PGoodCode.A);
        //same function is used to set all the routes
        if (number == 1) {
            SearchParameters globalSearchParameters = airSearchParameters.getGlobalSearchParameters();
            airSearchParameters.setOrigLocation(fromLocation);
            globalSearchParameters.setDestinationLocation(toLocation);
            globalSearchParameters.setStartDate(startDate.getTime());
            globalSearchParameters.setEndDate(null);
        }
        else if (number == 2) {
            airSearchParameters.setFirstFromLocation(fromLocation);
            airSearchParameters.setFirstToLocation(toLocation);
            airSearchParameters.setFirstStartDate(startDate.getTime());
        }
        else if (number == 3) {
            airSearchParameters.setSecondFromLocation(fromLocation);
            airSearchParameters.setSecondToLocation(toLocation);
            airSearchParameters.setSecondStartDate(startDate.getTime());
        }
        else if (number == 4) {
            airSearchParameters.setThirdFromLocation(fromLocation);
            airSearchParameters.setThirdToLocation(toLocation);
            airSearchParameters.setThirdStartDate(startDate.getTime());
        }
        else if (number == 5) {
            airSearchParameters.setFourthFromLocation(fromLocation);
            airSearchParameters.setFourthToLocation(toLocation);
            airSearchParameters.setFourthStartDate(startDate.getTime());
        }
    }

    @Then("^\"([^\"]*)\" Package speedbump and saving text$")
    public void packageSpeedBumpAndSavingText(String spVisiableStatus) {
        airSearchParameters.setSpVisiableStatus(spVisiableStatus);
        airSearchModel.verifySpeedBumpAndSaveText(airSearchParameters);
    }

    @And("^\"([^\"]*)\" Column A module$")
    public void columnAModule(String columnAStatus) {
        airSearchParameters.setColumnAStatus(columnAStatus);
        airSearchModel.verifyColumnA(airSearchParameters);
    }

    //each error is specified in a feature file. Example: IOC means 'Incorrect Original City'.
    //And for each error message there is corresponding error message stored in a hashmap in AirSearchModelWebApp
    @Then("^I see an error message for \"([^\"]*)\"$")
    public void validateErrorMessage(String error) {
        airSearchModel.verifyErrorMessage(error);
    }

    @Given("^I invoke air fare finder$")
    public void invokeAirFareFinder() {
        airSearchModel.invokeAirFareFinder();
    }

    @Then("^I will see 'select all cities' link$")
    public void countSelectAllCitiesLinks() {
        airSearchModel.seeAllCitiesCount();
    }

    @Then("^I see a correct pop-up after clicking 'see all cities' link above \"([^\"]*)\" field$")
    public void validateCitiesPopUp(String field) {
        airSearchModel.validateSeeAllCitiesPopUp(field);
    }

    @Then("^I should able to navigate across the air results using page navigation$")
    public void paginationCheckInResultsPage() {
        airSearchModel.paginationCheckInResultsPage();
    }

    @And("^I fill in the route info in air fare finder$")
    public void setRouteInfo() {
        airSearchModel.setRoutesInfo(airSearchParameters);
    }

    @Given("I want to access the BFS results in Air results page$")
    public void showBFSSearchResults() {
        airSearchModel.showBFSSearchResults();
    }

    @Given("I want to access the ITA results in Air results page")
    public void showITASearchResults() {
        airSearchModel.showITASearchResults();
    }

    /*@Given("I want to see intent media module on Air results page$")
    public void showIntentMediaModule(){
    airSearchModel.showIntentMediaModule();
    }*/

    @Then("I should see the intent media module on results page$")
    public void verifyIntentMediaModule() {
        airSearchModel.verifyIntentMediaModule();
    }

    @And("^I click on change your search link on air results page$")
    public void clickChangeSearchLink() {
        airSearchModel.clickChangeSearch();
    }

    @Then("^I see a new search layer with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyChangeSearchLayer(String fromLocation, String toLocation) {
        airSearchModel.verifyChangeSearchLayer(fromLocation, toLocation);
    }

    @And("^I change locations to \"([^\"]*)\" - \"([^\"]*)\" and search$")
    public void changeSearchLocations(String fromLocation, String toLocation) {
        airSearchParameters.setOrigLocation(fromLocation);
        airSearchParameters.getGlobalSearchParameters().setDestinationLocation(toLocation);
        airSearchModel.changeSearchLocations(airSearchParameters);
    }

    @And("^I click the update air search layer's (calendar icons|date text boxes) and verify date picker is displayed$")
    public void clickAndVerifyUpdateLayerCalendarElement(String elementType) {
        airSearchModel.clickAndVerifyUpdateLayerCalendarElement(elementType.equals("calendar icons") ? true : false);
    }

    @Then("^I will the updated search layer with changed search route and flight prices$")
    public void validateChangeMadeToRoute() {
        String toLocation = airSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        String fromLocation = airSearchParameters.getOrigLocation();
        airSearchModel.verifyChangeofRoute(fromLocation, toLocation);
    }

    @Then("^I see a changed route info on air results page$")
    public void verifyChangeOfRoute() {
        String fromLocation = airSearchParameters.getOrigLocation();
        String toLocation = airSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        airSearchModel.verifyChangeofRoute(fromLocation, toLocation);
    }

    @Then("^I will see results with correct start and end dates$")
    public void verifyStartEndDates() {
        airSearchModel.verifyStartEndDates();
    }

    @And("^I get the start and end dates$")
    public void getSelectedDates() {
        airSearchModel.getSelectedDateList();
    }

    @When("^I click activities banner on air confirmation page$")
    public void clickOnActivitiesBanner() {
        airSearchModel.clickBanner();
    }

    @Then("^I will see activities results page in a new window with correct air search params$")
    public void verifyActivitiesPage() {
        String destinationCity = airSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        Date startDate = airSearchParameters.getGlobalSearchParameters().getStartDate();
        Date endDate = airSearchParameters.getGlobalSearchParameters().getEndDate();
        airSearchModel.verifyActivitiesPage(destinationCity, endDate, startDate);
    }

    @Then("^I should see the BFS air results$")
    public void verifyBFSAirResults() {
        airSearchModel.verifyBFSAirResults();
    }

    @Then("^I should see the ITA air results$")
    public void verifyITAAirResults() {
        airSearchModel.verifyITAAirResults();
    }

    @Given("^I want to access forced version of Insurance Panel$")
    public void forcedVersionOfInsurance() {
        airSearchModel.forcedInsurancePanel();
    }

    @Given("^I want to access old version of Insurance Panel$")
    public void oldInsurancePanel() {
        airSearchModel.oldInsurancePanel();
    }

    @Given("^I should see right parameters in cross-cell modules$")
    public void verifyCrossCellModules() {
        String destination = airSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        String startDate = date.format(airSearchParameters.getGlobalSearchParameters().getStartDate());
        String endDate = date.format(airSearchParameters.getGlobalSearchParameters().getEndDate());
        airSearchModel.verifyCrossCellParameters(startDate, endDate, destination);
    }

    @Then ("^I should see air results page from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void verifyAirResultsPage(String fromLocation, String toLocation) {
        airSearchModel.verifyLocationsOnAirResultsPage(fromLocation, toLocation);
    }


    @When("^I search partner with IML$")
    public void i_search_partner_with_IML() throws Throwable {
        airSearchModel.searchPartnerWithIML();
    }

    @Then("^I see new partner window$")
    public void i_see_new_partner_window() throws Throwable {
        airSearchModel.verifyPartnerSearchResult();
    }

    @Given("^I type \"([^\"]*)\" air departure location$")
    public void typeDeparture(String location) {
        airSearchModel.typeDeparture(location);
    }

    @Given("^I type \"([^\"]*)\" air destination location$")
    public void typeDestination(String location) {
        airSearchModel.typeDestination(location);
    }

    @Given("^I set air dates between (.*) and (.*)$")
    public void hitSearchDates(@Transform(ChronicConverter.class)
                               Calendar start, @Transform(ChronicConverter.class) Calendar end) {

        airSearchModel.setDates(start.getTime(), end.getTime());
    }

    @When("^I start air search without specifying search parameters$")
    public void startAirSearchWithoutSpecifyingDestination() {
        searchParameters.setPGoodCode(PGoodCode.A);
        airSearchModel.clickAirSearch();
    }

    @When("^I click out of air disambiguation layer$")
    public void clickOutOfAirDisambiguationLayer() {
        airSearchModel.clickOutOfDisambiguationLayer();
    }

    @And("^I click on the air departure field$")
    public void clickAirDeparture() {
        airSearchModel.clickAirDepartureField();
    }

    @Then("^I(\\sdon't)? type email and watch the air trip$")
    public void watchTrip(String state) {
        String emailId = authenticationParameters.getUsername();
        //carSearchModel.watchTrip(" don't".equals(state), emailId);
        airSearchModel.watchThisTrip(" don't".equals(state), emailId);
    }

    @Then("^sorting air results by (Price|Departure|Arrival|Stops|Duration) will be sorted correctly$")
    public void sortResultsAndVerify(String sortType) {
        airSearchModel.sortResultsAndVerify(sortType);
    }

    @Then("^I see the lowest price module$")
    public void verifyLowestPriceModule() {
        airSearchModel.verifyLowestPriceModule();
    }

    @Then("^Destination field has been filled with destination city name$")
    public void verifyDestinationFieldIsFilledProperly() throws Throwable {
        airSearchModel.verifyDestinationFieldIsFilledProperly();
    }

    @Then("^I confirm the number of sellable retail and airport type code is set to Y in DB$")
    public void confirmNumberSellableRetailAndAirportTypeCodeInDB() throws Throwable {
        airSearchModel.confirmNumberSellableRetailAndAirportTypeCodeInDB(authenticationParameters);
    }

    @Then("^I validate MultiCity search results$")
    public void validateMultiCityPage() throws ParseException {
        airSearchModel.validateMultiCityPage();
    }

    @Then("^I see that maximum number of passengers to select is six$")
    public void verifyCorrectNumberOfPassengersToSelect() throws ParseException {
        airSearchModel.validateNumberOfPassengers();
    }


    @Then("^Air origination location is \"(.*)\" on farefinder$")
    public void verifyOriginationLocationOnFareFinder(String location) {
        airSearchModel.verifyOriginationLocationOnFareFinder(location);
    }

    @Then("^Air destination location is \"(.*)\" on farefinder$")
    public void verifyDestinationLocationOnFareFinder(String location) {
        airSearchModel.verifyDestinationLocationOnFareFinder(location);
    }
}

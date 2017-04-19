/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.desktop.row.models.CarDataVerificationParameters;
import com.hotwire.test.steps.account.AccountAccessModel;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.SearchParameters;

import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.transformer.jchronic.ChronicConverter;

/**
 * User: LRyzhikov
 * Date: 5/30/12
 */
public class CarSearchSteps extends AbstractSteps {

    @Autowired
    @Qualifier("carSearchModel")
    private CarSearchModel carSearchModel;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParameters carSearchParameters;

    @Autowired
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("accountAccess")
    private AccountAccessModel accountAccessModel;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("randomGuestUser")
    private UserInformation userInformation;

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel applicationModel;

    @Autowired
    @Qualifier("carDataVerificationParameters")
    private CarDataVerificationParameters carDataVerificationParameters;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Given("^I'm searching for a car in \"([^\"]*)\"$")
    public void setCarDestinationLocation(String loc) {
        searchParameters.setPGoodCode(PGoodCode.C);
        String location = (CarSearchLocationMap.supports(loc)) ? CarSearchLocationMap.getByType(loc) : loc;

        if (CarSearchLocationMap.supports(loc)) {
            carSearchParameters.getGlobalSearchParameters().setRandomLocation(true);
        }
        carSearchParameters.getGlobalSearchParameters().setDestinationLocation(location);
    }

    @And("^I navigate to car results page using external link$")
    public void navigateToCarResultsUsingExternalLink() {
        Calendar startCal = new GregorianCalendar();
        Calendar endCal = new GregorianCalendar();
        startCal.add(Calendar.DATE, 1);
        endCal.add(Calendar.DATE, 6);
        String externalUrl = "car/search-options.jsp?startLocation=San%20Diego,%20CA&startDay=" +
                startCal.get(Calendar.DAY_OF_MONTH) +
                "&startMonth=" + (startCal.get(Calendar.MONTH) + 1) + "&startYear=" + startCal.get(Calendar.YEAR) +
                "&endDay=" + endCal.get(Calendar.DAY_OF_MONTH) + "&endMonth=" + (endCal.get(Calendar.MONTH) + 1) +
                "&endYear=" + endCal.get(Calendar.YEAR) + "&startTime=1500&endTime=1500" +
                "&isUnderageDriver=false&startSearchType=N&inputId=index&selectedCarTypes=ECAR" +
                "&isDebitCardSelected=false&sid=S241&bid=B456";
        this.applicationModel.setupURL_ToVisit(externalUrl);
    }

    @Then("^I update search location to \"([^\"]*)\"$")
    public void updateSearch(String destinationLocation) {
        carSearchParameters.getGlobalSearchParameters().setDestinationLocation(destinationLocation);
        carSearchModel.findFareOnResultsPage();
    }

    @Given("^I'm searching for a car from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void setOneWayLocations(String pickUpLoc, String dropOffLoc) {
        searchParameters.setPGoodCode(PGoodCode.C);
        if (CarSearchLocationMap.supports(pickUpLoc)) {
            carSearchParameters.getGlobalSearchParameters()
                    .setDestinationLocation(CarSearchLocationMap.getByType(pickUpLoc));
            carSearchParameters.getGlobalSearchParameters().setRandomLocation(true);
        }
        else {
            carSearchParameters.getGlobalSearchParameters().setDestinationLocation(pickUpLoc);
        }

        if (CarSearchLocationMap.supports(dropOffLoc)) {
            carSearchParameters.setDropOffLocation(CarSearchLocationMap.getByType(dropOffLoc));
            carSearchParameters.getGlobalSearchParameters().setRandomLocation(true);
        }
        else {
            carSearchParameters.setDropOffLocation(dropOffLoc);
        }
    }

    @Given("^I want to pick up at (\\S+) and drop off at (\\S+)$")
    public void setSearchTimes(String startTime, String endTime) {
        carSearchParameters.setPickupTime(startTime);
        carSearchParameters.setDropoffTime(endTime);
    }

    @Given("^I want to verify list of (pick up|drop off) times in Farefinder")
    public void verifyTimes(String typeOfTime) {
        carSearchModel.verifyTimesInFareFinder(typeOfTime);
    }

    @Given("^I want to pick up with (\\d+) hours shift from now")
    public void setPickUpTimeWithShift(Integer timeShift) {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        calendar.add(Calendar.HOUR, timeShift);

        Date date = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("K:00a"); //Date format in FareFinder
        String startTime = dateFormat.format(date);
        startTime = startTime.toLowerCase();

        switch (startTime) {
            case "0:00pm":
                startTime = "noon";
                break;
            case "0:00am":
                startTime = "midnight";
                break;
            case "0:30am":
                startTime = "12:30am";
                break;
            default:
                break;
        }

        carSearchParameters.setPickupTime(startTime);
    }

    @Given("^I'm (\\d+) years old$")
    public void setDriversAge(String driversAge) {
        carSearchParameters.setDriversAge(driversAge);
    }

    @Given("^I'm from \"([^\"]*)\" country$")
    public void setCountryOfResidence(String countryOfResidence) {
        carSearchParameters.setCountryOfResidence(countryOfResidence);
    }

    @Given("^I'm from \"([^\"]*)\" and living in \"([^\"]*)\"$")
    public void setCountryOfResidenceAndState(String countryOfResidence, String state) {
        purchaseParameters.getUserInformation().setBillingCountry(countryOfResidence);
        purchaseParameters.getUserInformation().setState(state);
    }

    /**
     * This method will take the user to the car fare finder
     */
    @Given("^I navigate to the car search page$")
    @When("^I access car rental service$")
    public void navigateToSearchPage() {
        carSearchModel.navigateToSearchPage();
    }

    @Given("^I should be taken to the (?:(airport|local) )?results page$")
    public void verifyResultsPage(String carSearchType) {
        carSearchModel.verifyResultsPage(carSearchType);
    }

    @Then("^I should see details page$")
    public void navigateToDetailsPage() {
        carSearchModel.navigateToDetailsPage();
    }

    /**
     * This method will take the user to the car supplier page
     */
    @When("^I am on (hertz|alamo|budget|enterprise|europcar|national|dollar|thrifty|avis|sixt) landing page$")
    public void gotoSupplierLandingPage(String suppliername) throws Throwable {
        carSearchModel.navigatetoSupplierLandingPage(suppliername);
    }

    /**
     * In this method we click on "total inclusive rate" popup on results page
     */
    @When("^I want see total inclusive rate$")
    public void clickOnTotalInclusiveRatePopup() {
        carSearchModel.clickOnTotalInclusiveRatePopup();
    }

    @Then("^I see (hertz|alamo|budget|enterprise|europcar|national|dollar|thrifty|avis|sixt) banner$")
    public void confirmSupplierPage(String suppliername) throws Throwable {
        carSearchModel.confirmSupplierPage(suppliername);
    }

    @Then("^Rental days count equals to (\\d+) on billing page$")
    public void verifyRentalDaysOnBillingPage(Integer dayCount) {
        carSearchModel.verifyRentalDaysOnBillingPage(dayCount);
    }

    @Then("^Rental car protection cost equals to (\\d+) on billing page$")
    public void verifyRentalCarProtectionCostOnBillingPage(Integer protectionCost) {
        carSearchModel.verifyRentalCarProtectionCostOnBillingPage(protectionCost);
    }

    @Then("^The Car Trawler service is (available|not available)$")
    public void verifyCarTrawlerPage(String carTrawlerAvailability) throws Throwable {
        carSearchModel.verifyCarLandingPage(carTrawlerAvailability.equals("available"));
    }

    @Then("^The cheapest result is first$")
    public void verify_car_cheapest_solution_is_first() {
        carSearchModel.verifyThatCheapestSolutionIsFirst();
    }

    @Then("^I see only (opaque|retail) solutions on results page$")
    public void verifyOnlyRetailOrOpaqueResultsPage(String typeOfSolution) {
        carSearchModel.verifyOnlyRetailOrOpaqueResultsPage(typeOfSolution);
    }

    @Then("^I(?:(?:\\s)(don\'t))? see TripWatcher module$")
    public void verifyTripWatcherInresults(String condition) {
        carSearchModel.verifyTripWatcherInResults("don\'t".equals(condition));
    }

    @Then("^TripWatcher module(?:(?:\\s)(doesn\'t))? has email by default$")
    public void verifyTripWatcherEmailField(String state) {

        String emailId = accountAccessModel.userExistsAndIsAuthenticated() ?
                authenticationParameters.getUsername() : null;
        carSearchModel.verifyTripWatcherEmailField("doesn\'t".equals(state), emailId);
    }

    @Then("^Trip Watcher module has copy for (new|watched|full) trip$")
    public void verifyTripWatcherCopy(String state) {
        carSearchModel.verifyTripWatcherCopy(state);
    }

    @Then("^I(\\sdon't)? type email and watch the trip for (authenticated|random) user$")
    public void watchTrip(String state, String userType) {

        String emailId = null;
        if (userType.equals("authenticated")) {
            emailId = authenticationParameters.getUsername();
        }
        else if (userType.equals("random")) {
            emailId = userInformation.getEmailId();
        }


        carSearchModel.watchTrip(" don't".equals(state), emailId);
    }

    @Given("^I receive Trip Watcher email field error validation$")
    public void verifyEmailFieldErrorValidation() {
        carSearchModel.verifyEmailFieldErrorValidation();
    }

    @Then("^I am redirected to the lowest price solution$")
    public void verifyUserIsRedirectedToTheLowestPriceSolution() {
        carSearchModel.verifyUserIsRedirectedToTheLowestPriceSolution();
    }

    @When("^I start car search without specifying search parameters$")
    public void startCarSearchWithoutSpecifyingDestination() {
        searchParameters.setPGoodCode(PGoodCode.C);
        carSearchModel.clickSearch();
    }

    @When("I make car search from result page$")
    public void makeCarSearchFromResultPage() {
        searchParameters.setPGoodCode(PGoodCode.C);
        carSearchModel.makeCarSearchFromResultPage();
    }

    @Given("^I set car dates between (.*) and (.*)$")
    public void hitSearchDates(@Transform(ChronicConverter.class)
                               Calendar start, @Transform(ChronicConverter.class) Calendar end) {

        carSearchModel.setDates(start.getTime(), end.getTime());
    }

    @Given("^I note search_id for car results$")
    public void saveSearchIdNumber() {
        carSearchModel.saveSearchIdForCarResults();
    }

    @Then("^I see that \"Include nearby airport\" module is (not )?present$")
    public void verifyIfNearbyAirportModuleIsPresent(String condition) {
        if ("not ".equals(condition)) {
            carSearchModel.verifyIfNearbyAirportModuleIsNotPresent();
        }
        else {
            carSearchModel.verifyIfNearbyAirportModuleIsPresent();
        }
    }

    @Then("^I verify that STATUS_CODE from DB is equal to \"([^\"]*)\"$")
    public void verifyStatusCodeFromDB(String expectedCode) {
        carSearchModel.verifyStatusCodeFromDB(expectedCode);
    }

    @Then("^I verify cheapest retail car price in the grid with DB$")
    public void verifyCheapestRetailCarPriceWithDB() {
        carSearchModel.verifyCheapestRetailCarPriceWithDB();
    }

    @Then("^I compare car taxes and fees on details page with DB values$")
    public void compareTaxesAndFeesOnDetailsWithDB() {
        carSearchModel.compareTaxesAndFeesOnDetailsWithDB();
    }

    @Then("^I compare car daily rate on details page with DB values$")
    public void compareDailyRateOnDetailsWithDB() {
        carSearchModel.compareDailyRateOnDetailsWithDB();
    }

    @Then("^I verify that STATUS_CODE's count in DB is equal to (\\d+)$")
    public  void verifyStatusCodeCountInDB(Integer count) {
        carSearchModel.verifyStatusCodeCountInDB(count);
    }


    @Then("^search_id must be different from the previous result$")
    public void compareSearchIdNumber() {
        carSearchModel.compareSearchIdForCarResults();
    }

    @Then("^I verify necessary fields into DB for the car search$")
    public void verifyCarSearchDataInDB() {
        carSearchModel.verifyCarSearchDataInDB();
    }

    @Then("^I verify commissions for retail car in DB$")
    public void verifyCommissionsForRetailCar() {
        carSearchModel.verifyCommissionsForRetailCar();
    }

    @Then("^I verify car results not from cache$")
    public void verifyCarResultsNotFromCache() {
        carSearchModel.verifyCarResultsNotFromCache();
    }

    @Then("^I verify suggested location for misspelled city contains a \"(.*)\" in name$")
    public void verifyMisspelledCityHandling(String expectedCity) {
        carSearchModel.verifyMisspelledCityHandling(expectedCity);
    }

    @Then("^I verify that car results page didn't change after searching again$")
    public void verifyCarResultsPageDidntChange() {
        carSearchModel.verifyCarResultsPageDidntChange();
    }

    @When("I click on search from car results page$")
    public void clickOnSearchButton() {
        carSearchModel.clickOnSearchButton();
    }

}

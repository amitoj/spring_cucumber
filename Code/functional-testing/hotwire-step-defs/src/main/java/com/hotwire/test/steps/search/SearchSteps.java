/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.search.activities.ActivitiesSearchModel;
import com.hotwire.test.steps.search.air.AirSearchModel;
import com.hotwire.test.steps.search.car.CarSearchModel;
import com.hotwire.test.steps.search.cruise.CruiseSearchModel;
import com.hotwire.test.steps.search.hotel.HotelSearchModel;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.transformer.jchronic.ChronicConverter;
import org.joda.time.DateTime;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

/**
 * This class provides steps to execute a fare finder.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public class SearchSteps extends AbstractSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchSteps.class);

    @Autowired
    @Qualifier("searchModel")
    private SearchModel model;

    @Autowired
    @Qualifier("airSearchModel")
    private AirSearchModel airSearchModel;

    @Autowired
    @Qualifier("carSearchModel")
    private CarSearchModel carSearchModel;

    @Autowired
    @Qualifier("cruiseSearchModel")
    private CruiseSearchModel cruiseSearchModel;

    @Autowired
    @Qualifier("hotelSearchModel")
    private HotelSearchModel hotelSearchModel;

    @Autowired
    @Qualifier("activitiesSearchModel")
    private ActivitiesSearchModel activitiesSearchModel;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("webdriverInstance")
    private WebDriver webdriver;

    @Given("^I want to travel between (.*) and (.*)$")
    public void setSearchDates(@Transform(ChronicConverter.class)
                               Calendar start, @Transform(ChronicConverter.class) Calendar end) {
        searchParameters.setStartDate(start.getTime());
        searchParameters.setEndDate(end.getTime());
    }

    @Given("^I want to travel in the near future$")    // Setup random valid start and end dates in the near feature
    public void setRandomValidSearchDates() {
        // SearchParameters pgood needs to be set before calling this method. Air and car can use the same days.
        int plusWeeks = searchParameters.getPGoodCode().equals(PGoodCode.H) ? 2 : 4;
        int ranStartDays = searchParameters.getPGoodCode().equals(PGoodCode.H) ? 15 : 150;
        int ranEndDays = searchParameters.getPGoodCode().equals(PGoodCode.H) ? 29 : 44;
        LOGGER.info(
            "PGoodCode: " + searchParameters.getPGoodCode().toString() +
            " - plusWeeks: " + plusWeeks +
            " - ranStartDays: " + ranStartDays +
            " - ranEndDays: " + ranEndDays);
        Random random = new Random();
        searchParameters.setStartDate(
            (new DateTime()).plusWeeks(plusWeeks).plusDays(random.nextInt(ranStartDays)).toDate());

        Calendar cal = Calendar.getInstance();
        cal.setTime(searchParameters.getStartDate());

        // Add day initially to avoid exact same day searching. And set new date and calendar.
        cal.add(Calendar.DATE, 1);
        cal.setTime(cal.getTime());

        cal.add(Calendar.DATE, random.nextInt(ranEndDays));
        searchParameters.setEndDate(cal.getTime());
        searchParameters.setRandomDates(true);
    }

    /**
     * Enter dates like 09/07/14 and etc
     * This step is needed for testing car external search links
     */
    @And("^I want to travel with exact pickup (.*) and dropoff (.*) dates")
    public void setExactSearchDates(@Transform(ChronicConverter.class)
                                        Calendar start, @Transform(ChronicConverter.class) Calendar end) {
        searchParameters.setStartDate(start.getTime());
        searchParameters.setEndDate(end.getTime());
    }

    /**
     * The given on this method uses regex to match only
     * <pre>
     *  ^I request quotes$
     * </pre>  or
     * <pre>
     *  ^I request quotes by neighborhood$
     * </pre>
     * <p/>
     * It will capture the word "neighborhood" and pass it in to this method if present.
     * <p/>
     *
     * @param selectionCriteria
     */
    @Given("^I request(\\sproperty|\\ssearch|\\sdetails)? quotes(?:(?:\\sby\\s)" +
            "(neighborhood|neighborhood with everything filtered " +
            "out|Recommended|Distance to city|Property type|(?i)Price|(?i)Distance|(?i)Star rating|(?i)Best value)" +
            ")?(?:(?:\\sin\\s)(CAD|AUD|NZD|GBP|NOK|CHF|DKK|SEK|EUR|USD|HKD|MXN|SGD))?$")
    public void requestFares(String pageWithFareFinder, String selectionCriteria, String currencyCode) {
        if (pageWithFareFinder == null || pageWithFareFinder.trim().equals("property")) {
            LOGGER.info("Use default fare finder");
            if (pageWithFareFinder == null) {
                model.findFare(selectionCriteria);
            }
            else {
                // property search requested.
                model.findPropertyFare();
            }
        }
        else {
            if (pageWithFareFinder.trim().equals("details")) {
                LOGGER.info("Use default fare finder on details..");
                model.findFare(selectionCriteria, new HotelDetailsPage(webdriver).findHotelFare());
            }
            else {
                LOGGER.info("Use fare finder on results..");
                model.findFare(selectionCriteria, new HotelResultsPage(webdriver)
                        .getFareFinderFragment().findHotelFare());
            }
        }

        if (currencyCode != null) {
            // The user has elected to change the currency on the results page
            model.changeCurrency(currencyCode);
        }
    }

    @Given("^I launch a search$")
    public void launchSearch() {
        model.launchSearch();
    }

    /**
     * Step for choose vertical
     */
    @Given("^I'm looking for a (.*)$")
    public void setPGood(String verticalName) {
        searchParameters.setPGoodCode(PGoodCode.toPGoodType(verticalName));
    }

    @Then("^I land on the disambiguation page (with|without) an error message$")
    public void verifyDisambiguationPage(String condition) {
        if ("with".equalsIgnoreCase(condition)) {
            model.verifyDisambiguationPage(true /*hasErrorMessage*/);
        }
        else {
            model.verifyDisambiguationPage(false /*hasErrorMessage*/);
        }
    }

    @Then("^I see a (non-)?empty list of search results$")
    public void verifyIfResultsExistOnPageOrNot(String condition) {
        if ("non-".equals(condition)) {
            model.verifyResultsPage();
        }
        else {
            model.verifyNoResultsPage();
        }
    }

    @Then("^I see non-empty Refine your search module$")
    public void verifyNonEmptyRefineSearchModule() {
        model.verifyNonEmptyRefineSearchModule();
    }

    @Then("^I(\\sdon't)? see ([^\"]*) disambiguation layer$")
    public void verifyDisambuguationLayer(String condition, String type) {
        if (condition != null) {
            if (condition.trim().equals("don't")) {
                if (type.equalsIgnoreCase("car")) {
                    carSearchModel.verifyDisambiguationLayerIsNotVisible();
                }
                else if (type.equalsIgnoreCase("hotel")) {
                    hotelSearchModel.verifyDisambiguationLayerIsNotVisible();
                }

                else if (type.equalsIgnoreCase("air")) {
                    airSearchModel.verifyDisambiguationLayerIsNotVisible();
                }

            }
        }
        else {
            if (type.equalsIgnoreCase("car")) {
                carSearchModel.verifyDisambiguationLayerIsVisible();
            }
            else if (type.equalsIgnoreCase("hotel")) {
                hotelSearchModel.verifyDisambiguationLayerIsVisible();
            }

            else if (type.equalsIgnoreCase("air")) {
                airSearchModel.verifyDisambiguationLayerIsVisible();
            }


        }
    }


    @Then("^I see (.+) on disambiguation page$")
    public void verifyDisambiguationResults(String results) {
        model.verifyDisambiguationResults(Arrays.asList(results.split(";")));
    }

    @Then("^I see the same values in FareFinder as I filled$")
    public void verifyFareFinderOnResults() {
        carSearchModel.verifyFareFinderOnResults();
    }

    @And("^I see default dates in FareFinder$")
    public void verifyDefaultDatesInFareFinder() {
        carSearchModel.verifyDefaultDatesInFareFinder();
    }

    @Then ("^I should see (hotel|car|air|flight|cruise) search results page$")
    public void verifyResultsPage(String vertical) {
        if ("hotel".equals(vertical)) {
            hotelSearchModel.verifyHotelSearchResultsPage();
        }
        else if ("car".equals(vertical)) {
            carSearchModel.navigateToResultsPage();
        }
        else if ("air".equals(vertical) || "flight".equals(vertical)) {
            airSearchModel.verifyResultsPage();
        }
        else if ("cruise".equals(vertical)) {
            cruiseSearchModel.verifyCruiseSearchResultsUrl();
        }
    }

    @And("^I am notified about additional taxes and fees$")
    public void verifyDisclaimer() {
        hotelSearchModel.verifyDisclaimer();
    }

    @And("^I should see \"([^\"]*)\" in the Hotel location$")
    public void verifyLocation(String expectedLocation) {
        hotelSearchModel.verifyLocation(expectedLocation);
    }

    @Given("^I type \"([^\"]*)\" (hotel|car) location$")
    public void typeLocation(String location, String vertical) {
        if ("car".equalsIgnoreCase(vertical)) {
            carSearchModel.typeLocation(location);
        }
        else if ("hotel".equalsIgnoreCase(vertical)) {
            hotelSearchModel.typeLocation(location);
        }
    }

    @And("^I click out of car disambiguation layer$")
    public void clickOnTheCarStartTime() {
        carSearchModel.clickOnTheCarStartTime();
    }

    @And("^I click out of hotel disambiguation layer$")
    public void clickOnTheHotelChildrenFiled() {
        hotelSearchModel.clickOnTheHotelChildrenFiled();
    }

    @And("^I click on the (hotel|car) destination field$")
    public void clickOnTheHotelDestinationFiled(String vertical) {
        if ("hotel".equalsIgnoreCase(vertical)) {
            hotelSearchModel.clickOnTheDestinationFiled();
        }
        else if ("car".equals(vertical)) {
            carSearchModel.clickOnTheDestinationFiled();
        }
    }

    @And("^I press \"([^\"]*)\" key for (\\d+) times on autocomplete layer$")
    public void pressKeyOnAutocomlete(String keyName, int numberOfClicks) {
        model.pressKeyOnAutocomplete(keyName, numberOfClicks);
    }

    @Then("^search results contain \"([^\"]*)\" in location$")
    public void verifyResultsContainLocation(String location) {
        model.verifyLocationOnResultsPage(location);
    }

    @Then("^([^\"]*) search results contain expected location (.*)$")
    public void verifyTypeResultsContainLocation(String type, String location) {
        if (type.equalsIgnoreCase("car")) {
            carSearchModel.verifyLocationOnResultsPage(location);
        }
        else if (type.equalsIgnoreCase("hotel")) {
            hotelSearchModel.verifyLocationOnResultsPage(location);
        }

    }

    @Then("^search flight results contain \"([^\"]*)\" in location")
    public void verifyFlightResultsContainLocation(String location) {
        airSearchModel.verifyResultsContainLocation(location);
    }

    @Then("^Car farefinder contain \"([^\"]*)\" in location$")
    public void verifyCarFareFinderLocation(String location) {
        carSearchModel.verifyResultsContainLocation(location);
    }

    @Then("^(Roundtrip|One way) search is selected in Farefinder on car results page$")
    public void verifyTypeOFSearchOnCarResults(String typeOfSearch) {
        carSearchModel.verifyTypeOFSearchOnCarResults(typeOfSearch);
    }

    @Then("^I see(\\sfirst|\\ssecond|\\sthird)? suggested location is \"([^\"]*)\"(\\sunderlined and highlighted)?" +
            "(\\sand I click it)?$")
    public void verifySuggestedLocation(String number, String location, String style, String click) {
        model.verifySuggestedLocation(number, location, style, click);
    }

    @Then("^Check-out field prepopulated with (.*)$")
    public void setSearchDates(@Transform(ChronicConverter.class)
                               Calendar start) {
        model.verifyDatesValue("check-out", start.getTime());
    }

    @Then("I should see Call and book module for \"([^\"]*)\" on results page")
    public void verifyCallAndBookModule(String country) {
        hotelSearchModel.verifyCallAndBookModule(country);
    }

    @Then("^the results page contains \"([^\"]*)\" in location$")
    public void verifyResultsPageContainLocation(String location) {
        hotelSearchModel.verifyLocationOnResultsPage(location);
    }

    @Then("^I verify Intent Media elements exist$")
    public void verifyIntentMediaElements() {
        carSearchModel.verifyIntentMediaExistingElements();
    }

    @Then("^I verify MeSo Banners exist on (.*) page$")
    public void verifyMeSoBanners(String page) throws ParseException {
        if (page.equalsIgnoreCase("air landing")) {
            airSearchModel.validateMeSoBanners();
        }
        else if (page.equalsIgnoreCase("car results")) {
            carSearchModel.validateMeSoBanners();
        }
    }
}









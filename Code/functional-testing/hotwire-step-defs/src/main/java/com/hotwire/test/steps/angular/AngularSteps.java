/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.angular;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters.PaymentMethodType;
import com.hotwire.test.steps.purchase.paypal.PayPalPurchaseSteps;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.transformer.jchronic.ChronicConverter;

/**
 * @author vjong
 */
public class AngularSteps extends AbstractSteps {

    @Autowired
    @Qualifier("angularModel")
    AngularModel angularModel;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Given("^I click on the (angular hotel|hotel) launchsearch url$")
    public void clickPublicLaunchSearchUrlForAngular(String key) {
        angularModel.clickPublicLaunchSearchUrlForAngular(key);
    }

    @Given("^I go to the UHP$")
    public void goToUHP() {
        angularModel.goToUHP();
    }

    @Given("^I want to go to the (Hotels|Cars|Flights|Vacations) landing page from angular page$")
    public void goToVerticalFromAngularPage(String vertical) {
        angularModel.goToVerticalFromAngularPage(vertical.trim());
    }

    @Given("^I want to go to the (Hotels) angular landing page$")
    public void goToAngularLandingPage(String vertical) {
        //angularModel.goToAngularLandingPage(vertical);
        angularModel.goToAngularLandingPageUsingAnError();
    }

    @Given("^I request angular quotes$")
    public void requestQuotesInAngularSite() {
        angularModel.requestQuotesInAngularSite();
    }

    @Given("^I request angular quotes from non angular site using first result in autocomplete$")
    public void requestQuotesUsingFirstResult() {
        angularModel.requestQuotesInAngularSiteUsingFirstResult();
    }

    @Given("^I request angular quotes from angular results$")
    public void requestQuotesInAngularHotelResults() {
        angularModel.requestQuotesInAngularHotelResults();
    }

    @Given("^I launch angular search$")
    public void launchAngularSearchInHomepage() {
        angularModel.launchAngularSearchInHomepage();
    }

    @Given("^I launch results page angular search$")
    public void launchAngularSearchInResultsPage() {
        angularModel.launchAngularSearchInResultsPage();
    }

    @Given("^I want to select (.*) in angular country selector$")
    public void selectCountryInAngularSite(String country) {
        angularModel.selectCountryInAngularSite(country);
    }

    @Given("^I want to select (CAD|AUD|NOK|DKK|SEK|NZD|GBP|CHF|EUR|USD) in angular currency selector$")
    public void selectCurrencyInAngularSite(String currency) {
        angularModel.selectCurrencyInAngularSite(currency);
    }

    @Given("^I'm searching for a hotel with autocomplete in \"(.*)\"$")
    public void selectDestination(String destination) {
        angularModel.selectDestination(destination);
    }

    @Given("^I enter travel time between (.*) and (.*)$")
    public void setHotelTravelDates(@Transform(ChronicConverter.class) Calendar start,
                                    @Transform(ChronicConverter.class) Calendar end) {
        angularModel.setHotelTravelDates(start.getTime(), end.getTime());
    }

    @Given("^I select (.*) room\\(s\\)$")
    public void selectNumberOfRooms(String numRooms) {
        angularModel.selectNumberOfRooms(new Integer(numRooms));
    }

    @Given("^I get the angular results list of(\\s+|\\sprices|\\srecommended ratings|\\sstar ratings)$")
    public void getListOfCriteriaResults(String criteria) {
        angularModel.getListOfCriteriaResults(StringUtils.isEmpty(criteria.trim()) ? "prices" : criteria.trim());
    }

    @Given("^I want to go to (My Account|My Trips|Register)$")
    public void selectAngularMyAccount(String myAccountSelection) {
        angularModel.selectAngularMyAccount(myAccountSelection);
    }

    @Given("^I navigate to (Help center|Manage subscriptions)$")
    public void goToSupportPage(String pageType) {
        angularModel.goToSupportPage(pageType);
    }

    @Then("^I will be directed to (Help center|Manage subscriptions) page$")
    public void verifySupportPage(String pageType) {
        angularModel.verifySupportPage(pageType);
    }

    @Then("^I will be directed to sign in from (My Account|My Trips|Register)$")
    public void verifySignInNeeded(String myAccountSelection) {
        angularModel.verifySignInNeeded(myAccountSelection);
    }

    @When("^I filter angular hotel results by (Areas|Star Rating|Amenities)$")
    public void filterResults(String filterType) {
        angularModel.filterResults(filterType);
    }

    @Then("^The angular results will be filtered by (Areas|Star Rating|Amenities)$")
    public void verifyFilterResults(String filterType) {
        angularModel.verifyFilterResults(filterType);
    }

    @When("^I filter angular results with (.*) (Star Rating|Amenities|Accessibility Options)$")
    public void filterResults(String value, String filterType) {
        angularModel.filterResults(value, filterType);
    }

    @When("^I reset the Areas filter$")
    public void resetAreasFilter() {
        angularModel.resetAreasFilter();
    }

    @Then("^All filter options for (Areas|Star Rating|Amenities) with zero count will be disabled$")
    public void verifyStateOfZeroCountAreasFilterOptions(String filterType) {
        angularModel.verifyStateOfZeroCountAreasFilterOptions(filterType);
    }

    @Then("^The angular results will not be filtered by (Areas|Star Rating|Amenities)$")
    public void verifyResultsNotFilteredByAreas(String filter) {
        if (filter.equals("Areas")) {
            angularModel.verifyResultsNotFilteredByAreas();
        }
        else if (filter.equals("Star Rating")) {
            angularModel.verifyResultsNotFilteredByStarRating();
        }
        else {
            angularModel.verifyResultsNotFilteredByAmenities();
        }
    }

    @Then("^The angular details page will contain (.*) (Accessibility Options|Amenities)$")
    public void verfiyAngularDetailsContainsAmenity(String value, String filterType) {
        angularModel.verfiyAngularDetailsContainsAmenity(value, filterType);
    }

   /*
    @When("^I filter angular results with Accessible bathroom Accessibility Options$")
    public void I_filter_angular_results_with_Accessible_bathroom_Accessibility_Options() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^The angular details page will contain Accessible bathroom Accessibility Options$")
    public void The_angular_details_page_will_contain_Accessible_bathroom_Accessibility_Options() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }*/

    @When("^I Reset filters$")
    public void clickResetFiltersLink() {
        angularModel.clickResetFiltersLink();
    }

    @Then("^I (do|do not) see Reset filters link$")
    public void verifyResetFiltersLinkDisplayed(String state) {
        angularModel.verifyResetFiltersLinkDisplayed(state.equals("do") ? true : false);
    }

    @When("^I sort angular results by " +
          "(Popular|Price \\(low to high\\)|Price \\(high to low\\)|Recommended \\(high to low\\)|" +
          "Star rating \\(high to low\\)|Star rating \\(low to high\\))$")
    public void sortResultsByCriteria(String criteria) {
        angularModel.sortResultsByCriteria(criteria);
    }

    @When("^I book the hotel in the angular details page$")
    public void bookHotelInAngularDetailsPage() {
        angularModel.bookHotelInAngularDetailsPage();
    }

    @Then("^I will see the hotel billing page from the angular details page$")
    public void verifyHotelBillingPageFromAngularDetailsPage() {
        angularModel.verifyHotelBillingPageFromAngularDetailsPage();
    }

    @Then("^The angular results will be sorted by " +
          "(Popular|Price \\(low to high\\)|Price \\(high to low\\)|Recommended \\(high to low\\)|" +
          "Star rating \\(high to low\\)|Star rating \\(low to high\\))$")
    public void verifyResultsSortByCriteria(String criteria) {
        angularModel.verifyResultsSortByCriteria(criteria);
    }

    @Then("^The angular results will only contain (.*) (Star Rating|Amenities) solutions$")
    public void verifyFilterResults(String value, String filterType) {
        angularModel.verifyFilterResults(value, filterType);
    }

    @Then("^The angular results count will be correct for (.*) (Star Rating|Amenities)$")
    public void verifyFilteredResultsCount(String value, String filterType) {
        angularModel.verifyFilteredResultsCount(value, filterType);
    }

    @Then("^The angular results count will be correct for (Areas)$")
    public void verifyFilteredResultsCount(String filterType) {
        angularModel.verifyFilteredResultsCount(filterType);
    }

    @Then("^I see (.*) will be selected in angular country selector$")
    public void verifySelectedCountryInAngularSite(String country) {
        angularModel.verifySelectedCountryInAngularSite(country);
    }

    @Then("^I see the hotel landing page for (.*)$")
    public void verifySelectedCountryHotelLandingPage(String country) {
        angularModel.verifySelectedCountryHotelLandingPage(country);
    }

    @Then ("^I see (CAD|AUD|NOK|DKK|SEK|NZD|GBP|CHF|EUR|USD) will be selected in angular currency selector$")
    public void verifySelectedCurrencyInAngularSite(String currency) {
        angularModel.verifySelectedCurrencyInAngularSite(currency);
    }

    @Then("^I see angular hotel results$")
    public void verifyAngularHotelResults() {
        angularModel.verifyAngularHotelResults();
    }

    @Then("^I see angular hotel results from launchsearch url$")
    public void verifyAngularHotelResultsFromLaunchSearchUrl() {
        angularModel.verifyAngularHotelResultsFromLaunchSearchUrl();
    }

    @When("^I hit the browser back button$")
    public void navigateBackFromCurrentPage() {
        angularModel.navigateBackFromCurrentPage();
    }

    @When("^I go back to results from angular details page$")
    public void goBackToAngularResultsFromAngularDetailsPage() {
        angularModel.goBackToAngularResultsFromAngularDetailsPage();
    }

    @Then("^I save the angular results list$")
    public void storeAngularResultsList() {
        angularModel.storeAngularResultsList();
    }

    @Then("^the angular results list is the same as before$")
    public void verifyCurrentResultsListIsSameAsStoreResultsList() {
        angularModel.verifyCurrentResultsListIsSameAsStoreResultsList();
    }

    @Then("^I book the first hotel in the angular results$")
    public void bookFirstAngularSolution() {
        angularModel.bookFirstAngularSolution();
    }

    @Then("^I will see the angular details page$")
    public void verifyAngularHotelDetails() {
        angularModel.verifyAngularHotelDetails();
    }

    @Then("^I see customer care in angular hotel results$")
    public void verifyAngularHotelResultsCustomerCare() {
        angularModel.verifyAngularHotelResultsCustomerCare();
    }

    @Then("^I will see customer care in the angular details page$")
    public void verifyAngularHotelDetailsCustomerCare() {
        angularModel.verifyAngularHotelDetailsCustomerCare();
    }

    @When("^I purchase a hotel(.*) as (.*) a quote from angular site$")
    public void completePurchaseOfAlreadySelectedSolution(String purchaseOption, String guestOrUser) {
        if (StringUtils.isNotBlank(purchaseOption) && purchaseOption.contains("insurance")) {
            this.purchaseParameters.setOptForInsurance(true);
        }

        if (StringUtils.isNotBlank(purchaseOption)) {
            if (StringUtils.containsIgnoreCase(purchaseOption, "paypal")) {
                if (!PayPalPurchaseSteps.isPaypalSandboxAvailable()) {
                    throw new PendingException("Paypal sandbox is unavailable on Friday.");
                }
                this.purchaseParameters.setPaymentMethodType(PaymentMethodType.PayPal);
            }
            else if (StringUtils.containsIgnoreCase(purchaseOption, "v.me") ||
                    StringUtils.containsIgnoreCase(purchaseOption, "vme")) {
                this.purchaseParameters.setPaymentMethodType(PaymentMethodType.V_ME);
            }

            else if (!purchaseParameters.getPaymentMethodType().equals(PaymentMethodType.HotDollars)) {
                this.purchaseParameters.setPaymentMethodType(PaymentMethodType.CreditCard);
            }
        }
        angularModel.completePurchase(guestOrUser.equals("user"));
    }

    @Given("^I enter (.*) for destination$")
    public void typeDestinationString(String destination) {
        angularModel.typeDestination(destination.equals("blank") ? "" : destination);
    }

    @Given("^I enter (.*) for start date$")
    public void typeStartDate(String start) {
        angularModel.typeStartDate(start.equals("blank") ? "" : start);
    }

    @Given("^I enter (.*) for end date$")
    public void typeEndDate(String end) {
        angularModel.typeEndDate(end.equals("blank") ? "" : end);
    }

    @Then("^I will see fare finder (destination|start date|end date) error$")
    public void verifyFareFinderError(String element) {
        angularModel.verifyFareFinderError(element);
    }

    @Then("^I will see page UI error$")
    public void verifyHomeUIExceptionDisplayed() {
        angularModel.verifyPageUIExceptionDisplayed();
    }

    @Then("^I will see page UI error from disambiguation$")
    public void verifyPageUIExceptionDisplayedFromDisambiguation() {
        angularModel.verifyPageUIExceptionDisplayedFromDisambiguation();
    }

    @Then("^I will see page API error$")
    public void verifyHomeAPIExceptionDisplayed() {
        angularModel.verifyPageAPIExceptionDisplayed();
    }

    @Then("^I will see the disambiguation list of locations$")
    public void verifyDisambiguationLocationListDisplayed() {
        angularModel.verifyDisambiguationLocationListDisplayed();
    }

    @When("^I click the comment card link on the angular (home|results|details) page$")
    public void clickCommentCardLink(String page) {
        angularModel.clickCommentCardLink(page);
    }

    @Then("^the comment card will disappear off angular page$")
    public void verifyAngularCommentCardGone() {
        angularModel.verifyAngularCommentCardGone();
    }

    @Then("^The map is loaded on angular (results|details)$")
    public void verifyMapLoadedOnAngularResults(String page) {
        angularModel.verifyMapLoadedOnAngularPage(page);
    }

    @Then("^I verify destination location on angular results page is San Francisco$")
    public void verifyDestinationInAngularResultsIsSanFrancisco() {
        angularModel.verifyWeAreInTheRightDestination("San Francisco");
    }

}


/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.angular;
import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.angular.AngularFareFinderFragment;
import com.hotwire.selenium.angular.AngularGlobalHeader;
import com.hotwire.selenium.angular.AngularHomePage;
import com.hotwire.selenium.angular.AngularHotelDetailsPage;
import com.hotwire.selenium.angular.AngularHotelResultsPage;
import com.hotwire.selenium.angular.AngularSystemErrorFragment;
import com.hotwire.selenium.desktop.account.RegisterNewUserPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.us.SignInPage;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelBillingOnePage;
import com.hotwire.selenium.desktop.us.helpcenter.HelpCenterPage;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.selenium.desktop.utils.DesktopPageProviderUtils;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.deals.DealLinkFactory;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters.PaymentMethodType;
import com.hotwire.test.steps.purchase.PurchaseParametersImpl;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPage;

import cucumber.api.PendingException;

/**
 * @author vjong
 *
 */
public abstract class AngularModelTemplate extends WebdriverAwareModel implements AngularModel {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AngularModelTemplate.class.getSimpleName());
    protected List<String> searchResults;

    @Autowired
    @Qualifier("applicationUrl")
    protected URL applicationUrl;

    @Autowired
    @Qualifier("searchParameters")
    protected SearchParameters searchParameters;

    @Autowired
    @Qualifier("hotelSearchParameters")
    protected HotelSearchParameters hotelSearchParameters;

    @Autowired
    @Qualifier("purchaseParameters")
    protected PurchaseParametersImpl purchaseParameters;

    @Autowired
    @Qualifier("authenticationParameters")
    protected AuthenticationParameters authenticationParameters;

    @Resource(name = "canadianZipCode")
    protected String canadianZipCode;

    @Resource(name = "desktopPosCurrencyMap")
    protected HashMap<String, String> posCurrencyMap;


    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public void goToUHP() {
        new AngularGlobalHeader(getWebdriverInstance()).clickHotwireLogo();
    }

    @Override
    public void goToVerticalFromAngularPage(String vertical) {
        new AngularGlobalHeader(getWebdriverInstance()).navigateToVerticalLandingPage(vertical);
    }

    @Override
    public void goToAngularLandingPage(String vertical) {
        new GlobalHeader(getWebdriverInstance()).navigateToVerticalLandingPageFromNavigationTab(vertical, true);
    }

    @Override
    public void requestQuotesInAngularSite() {
        List <String> activatedVersionTests = DesktopPageProviderUtils.getVersionTests(getWebdriverInstance(), "eVar1");
        if (activatedVersionTests.contains("AWA14-2") && activatedVersionTests.contains("ALS14-2")) {
            new AngularHomePage(getWebdriverInstance()).searchForHotels(
                hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation(),
                hotelSearchParameters.getGlobalSearchParameters().getStartDate(),
                hotelSearchParameters.getGlobalSearchParameters().getEndDate(),
                hotelSearchParameters.getNumberOfHotelRooms(),
                hotelSearchParameters.getNumberOfAdults(),
                hotelSearchParameters.getNumberOfChildren(),
                hotelSearchParameters.getEnableHComSearch());

        }
        else {
            new HotelSearchFragment(getWebdriverInstance())
                .withNumberOfHotelRooms(hotelSearchParameters.getNumberOfHotelRooms())
                    .withNumberOfAdults(hotelSearchParameters.getNumberOfAdults())
                    .withNumberOfChildren(hotelSearchParameters.getNumberOfChildren())
                    .withDestinationLocation(hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation())
                    .withStartDate(hotelSearchParameters.getGlobalSearchParameters().getStartDate())
                    .withEndDate(hotelSearchParameters.getGlobalSearchParameters().getEndDate())
                    .withEnableSearchPartner(hotelSearchParameters.getEnableHComSearch()).launchSearch();

        }
    }

    public void requestQuotesInAngularSiteUsingFirstResult() {
        new HotelSearchFragment(getWebdriverInstance())
                .withNumberOfHotelRooms(hotelSearchParameters.getNumberOfHotelRooms())
                .withNumberOfAdults(hotelSearchParameters.getNumberOfAdults())
                .withNumberOfChildren(hotelSearchParameters.getNumberOfChildren())
                .withDestinationLocationAsFirstResultInAutocomplete(hotelSearchParameters.getGlobalSearchParameters()
                        .getDestinationLocation())
                .withStartDate(hotelSearchParameters.getGlobalSearchParameters().getStartDate())
                .withEndDate(hotelSearchParameters.getGlobalSearchParameters().getEndDate())
                .withEnableSearchPartner(hotelSearchParameters.getEnableHComSearch()).launchSearch();
    }


    @Override
    public void requestQuotesInAngularHotelResults() {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        resultsPage.openCollapsedFareFinder();
        new AngularHotelResultsPage(getWebdriverInstance()).searchForHotels(
            hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation(),
            hotelSearchParameters.getGlobalSearchParameters().getStartDate(),
            hotelSearchParameters.getGlobalSearchParameters().getEndDate(),
            hotelSearchParameters.getNumberOfHotelRooms(),
            hotelSearchParameters.getNumberOfAdults(),
            hotelSearchParameters.getNumberOfChildren(),
            hotelSearchParameters.getEnableHComSearch());
    }

    @Override
    public void launchAngularSearchInHomepage() {
        List <String> activatedVersionTests = DesktopPageProviderUtils.getVersionTests(getWebdriverInstance(), "eVar1");
        if (activatedVersionTests.contains("AWA14-2") && activatedVersionTests.contains("ALS14-2")) {
            new AngularHotelResultsPage(getWebdriverInstance()).getFareFinderFragment().clickSearchButton();
        }
        else {
            new HotelSearchFragment(getWebdriverInstance()).launchSearch();

        }
    }

    @Override
    public void launchAngularSearchInResultsPage() {
        new AngularHotelResultsPage(getWebdriverInstance()).getFareFinderFragment().clickSearchButton();
    }

    @Override
    public void verifyAngularHotelResults() {
        int numResults = new AngularHotelResultsPage(getWebdriverInstance()).getNumberOfSolutions();
        LOGGER.info("Hotel results page solutions count: " + numResults);
        assertThat(numResults)
            .as("Expected number of solutions to be greater than 0.")
            .isGreaterThan(0);
    }

    @Override
    public void storeAngularResultsList() {
        searchResults = new AngularHotelResultsPage(getWebdriverInstance()).getAllAreaNames();
    }

    @Override
    public void verifyCurrentResultsListIsSameAsStoreResultsList() {
        List<String> actual = new AngularHotelResultsPage(getWebdriverInstance()).getAllAreaNames();
        LOGGER.info("Area names lists:\n" + searchResults + "\n" + actual);
        assertThat(actual)
            .as("Expected lists to be equal but it wasn't.\n" + searchResults + "\n" + actual)
            .isEqualTo(searchResults);
    }

    @Override
    public void navigateBackFromCurrentPage() {
        getWebdriverInstance().navigate().back();
    }

    @Override
    public void goBackToAngularResultsFromAngularDetailsPage() {
        new AngularHotelDetailsPage(getWebdriverInstance()).clickBackToResultsLink();
    }

    @Override
    public void bookFirstAngularSolution() {
        SearchSolution solution = new AngularHotelResultsPage(getWebdriverInstance()).bookFirstSolution();
        LOGGER.info(
            "Chosen solution: " + solution.getHotelName() + " : " + solution.getRating() + " : " + solution.getPrice());
        hotelSearchParameters.setSelectedSearchSolution(solution);
    }

    @Override
    public void verifyAngularHotelDetails() {
        AngularHotelDetailsPage detailsPage = new AngularHotelDetailsPage(getWebdriverInstance());
        String name = detailsPage.getHotelName();
        LOGGER.info("Hotel name returned by angular details page is: " + name);
        assertThat(name)
            .as("Expected details page to return \"" +
                hotelSearchParameters.getSelectedSearchSolution().getHotelName() + "\" but got \"" +
                name + "\" instead.")
            .isEqualToIgnoringCase(hotelSearchParameters.getSelectedSearchSolution().getHotelName());
    }

    @Override
    public void selectCountryInAngularSite(String country) {
        new AngularGlobalHeader(getWebdriverInstance()).selectCountry(country);
    }

    @Override
    public void verifySelectedCountryInAngularSite(String country) {
        String selectedCountry = new AngularGlobalHeader(getWebdriverInstance()).getSelectedCountry();
        assertThat(selectedCountry)
            .as("Expected \"" + country + "\" to be selected but was \"" + selectedCountry + "\".")
            .isEqualToIgnoringCase(country);
    }

    /**
     * For intl redirect to existing hotel landing page for intl POS.
     */
    @Override
    public void verifySelectedCountryHotelLandingPage(String country) {
        GlobalHeader header = new HotelIndexPage(getWebdriverInstance(), false).getGlobalHeader();
        String landingPageCountry = header.getSelectedCountry();
        assertThat(landingPageCountry)
            .as("Expected landing page for " + country + " but got " + landingPageCountry)
            .isEqualTo(country);
        String selectedCurrency = header.getSelectedCurrencyCode();
        assertThat(selectedCurrency)
            .as("Expected " + posCurrencyMap.get(country) + " but got " + selectedCurrency)
            .isEqualToIgnoringCase(posCurrencyMap.get(country));
    }

    @Override
    public void selectCurrencyInAngularSite(String currency) {
        new AngularGlobalHeader(getWebdriverInstance()).selectCurrency(currency);
    }

    @Override
    public void verifySelectedCurrencyInAngularSite(String currency) {
        String selectedCurrency = new AngularGlobalHeader(getWebdriverInstance()).getSelectedCurrency();
        assertThat(selectedCurrency)
            .as("Expected " + currency + " to be selected but was " + selectedCurrency)
            .isEqualTo(currency);
    }

    @Override
    public void typeDestination(String destination) {
        new AngularFareFinderFragment(getWebdriverInstance()).typeDestination(destination, false);
    }

    @Override
    public void typeStartDate(String start) {
        new AngularFareFinderFragment(getWebdriverInstance()).enterStartDate(start);
    }

    @Override
    public void typeEndDate(String end) {
        new AngularFareFinderFragment(getWebdriverInstance()).enterEndDate(end);
    }

    @Override
    public void selectDestination(String destination) {
        new AngularHomePage(getWebdriverInstance()).getFareFinderFragment().typeDestination(destination, true);
    }

    @Override
    public void setHotelTravelDates(Date start, Date end) {
        AngularHomePage homePage = new AngularHomePage(getWebdriverInstance());
        homePage.getFareFinderFragment().enterStartDate(start);
        homePage.getFareFinderFragment().enterEndDate(end);
    }

    @Override
    public void selectNumberOfRooms(Integer numberOfHotelRooms) {
        new AngularHomePage(getWebdriverInstance()).getFareFinderFragment().selectHotelRooms(numberOfHotelRooms);
        hotelSearchParameters.setNumberOfHotelRooms(numberOfHotelRooms);
    }

    @Override
    public void filterResults(String filterType) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        if (filterType.equals("Star Rating")) {
            filterResults("3", filterType);
            searchResults = resultsPage.getAllResultsStarRatings();
        }
        else if (filterType.equals("Areas")) {
            resultsPage.filterByFirstFilterableArea();
            searchResults = resultsPage.getAllAreaNames();
        }
        else if (filterType.equals("Amenities")) {
            resultsPage.filterByFirstFilterableAmenity();
            searchResults = resultsPage.getAllResultsAmenitiesList();
        }
        else {
            throw new UnimplementedTestException(filterType + " filtering is not supported." +
                " Filter by accessibility need a value to filter by.");
        }
    }

    @Override
    public void filterResults(String value, String filterType) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        if (filterType.equals("Star Rating")) {
            resultsPage.filterByStarRating(value);
            searchResults = resultsPage.getAllResultsStarRatings();
        }
        else if (filterType.equals("Amenities")) {
            resultsPage.filterByAmenity(value);
            searchResults = resultsPage.getAllResultsAmenitiesList();
        }
        else if (filterType.equals("Accessibility Options")) {
            resultsPage.filterByAccessibilityAmenity(value);
            // don't store search results list since this won't have accessibility listed in text. Waiting for devs
            // to include pipe delimited list of all amenities in each solution.
        }
        else {
            throw new UnimplementedTestException(filterType + " filtering is not supported.");
        }
    }

    @Override
    public void verifyFilterResults(String filterType) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        if (filterType.equals("Star Rating")) {
            verifyFilterResults("3", filterType);
        }
        else if (filterType.equals("Areas")) {
            WebElement element = resultsPage.getFirstFilterableArea();
            String areaName = resultsPage.getAreaNameFromAreaFilterElement(element);
            System.out.println("areaName" + areaName);
            for (String area : resultsPage.getAllAreaNames()) {
                assertThat(area)
                    .as(areaName + " but got " + area)
                    .startsWith(areaName);
            }
            assertThat(resultsPage.isAllAreasFilterOptionChecked())
                .as("Expected All areas option to be unchecked.")
                .isFalse();
        }
        else if (filterType.equals("Amenities")) {
            WebElement element = resultsPage.getFirstFilterableAmenity();
            String amenityName = resultsPage.getAmenityNameFromAmenityFilterElement(element);
            for (String amenities : resultsPage.getAllResultsAmenitiesList()) {
                assertThat(amenities.contains(amenityName))
                    .as("Expected - " + amenities + " - to contain " + amenityName)
                    .isTrue();
            }
        }
        else {
            throw new UnimplementedTestException(filterType + " filtering is not supported." +
                " Filter by accessibility need a value to filter by.");
        }
    }

    @Override
    public void verifyFilterResults(String value, String filterType) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        if (filterType.equals("Star Rating")) {
            ArrayList<String> starRatings = resultsPage.getAllResultsStarRatings();
            for (String starRating : starRatings) {
                assertThat(starRating.startsWith(value))
                    .as("Expected a " + value + " or " + value + ".5 star hotel but got " + starRating)
                    .isTrue();
            }
        }
        else if (filterType.equals("Amenities")) {
            ArrayList<String> amenitesList = resultsPage.getAllResultsAmenitiesList();
            for (String amenities : amenitesList) {
                assertThat(amenities.contains(value))
                    .as("Expected amenities list - " + amenities + " - to contain " + value)
                    .isTrue();
            }
        }
        else {
            throw new UnimplementedTestException(filterType + " filtering is not supported.");
        }
    }

    @Override
    public void verifyFilteredResultsCount(String value, String filterType) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        int countFromFilter;
        List<String> searchResultStrings;
        if (filterType.equals("Star Rating")) {
            countFromFilter = resultsPage.getStarRatingFilterCount(value);
            searchResultStrings = resultsPage.getAllResultsStarRatings();
        }
        else if (filterType.equals("Amenities")) {
            countFromFilter = resultsPage.getAmenitiesFilterCount(value);
            searchResultStrings = resultsPage.getAllResultsAmenitiesList();
        }
        else {
            throw new UnimplementedTestException(filterType + " is not supported in this method.");
        }
        assertThat(searchResultStrings.size())
            .as("Expected " + countFromFilter + " solutions but got " + searchResultStrings.size() + " solutions.")
            .isEqualTo(countFromFilter);

    }

    @Override
    public void verifyFilteredResultsCount(String filterType) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        int countFromFilter;
        List<String> searchResultStrings;
        if (filterType.equals("Areas")) {
            countFromFilter = resultsPage.getFirstFilterableAreaResultCount();
            searchResultStrings = resultsPage.getAllAreaNames();
            //searchResultStrings.remove(0);
        }
        else {
            throw new UnimplementedTestException(filterType + " is not supported in this method.");
        }
        assertThat(searchResultStrings.size())
            .as("Expected " + countFromFilter + " solutions but got " + searchResultStrings.size() + " solutions.")
            .isEqualTo(countFromFilter);

    }

    @Override
    public void resetAreasFilter() {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        resultsPage.resetAreasFilter();
    }

    @Override
    public void verifyResultsNotFilteredByAreas() {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        List<String> allAreas = resultsPage.getAllAreaNames();
        LOGGER.info("Areas Lists:\n" + allAreas + "\n" + searchResults);
        assertThat(allAreas)
            .as("Expected results to not be filtered and different. " +
                "[" + StringUtils.join(allAreas, ", ") + "] compared to [" +
                searchResults + "]")
            .isNotEqualTo(searchResults);
        //assertThat(resultsPage.isAllAreasFilterOptionChecked())
          //  .as("Expected All areas filter option to be checked.")
            //.isTrue();
    }

    @Override
    public void verifyStateOfZeroCountAreasFilterOptions(String filterType) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        assertThat(resultsPage.areOptionsWithZeroCountDisabled(filterType))
            .as("Expected all zero count Areas filter options to be disabled but at least one option is enabled.")
            .isTrue();
    }

    @Override
    public void verifyResultsNotFilteredByStarRating() {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        List<String> allStars = resultsPage.getAllResultsStarRatings();
        LOGGER.info("Star Ratings Lists:\n" + allStars + "\n" + searchResults);
        assertThat(allStars)
            .as("Expected results to not be filtered and different. " +
                "[" + StringUtils.join(allStars, ", ") + "] compared to [" +
                searchResults + "]")
            .isNotEqualTo(searchResults);
    }

    @Override
    public void verifyResultsNotFilteredByAmenities() {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        List<String> allAmenities = resultsPage.getAllResultsAmenitiesList();
        LOGGER.info("Amenities Lists:\n" + allAmenities + "\n" + searchResults);
        assertThat(allAmenities)
            .as("Expected results to not be filtered and different. " +
                "[" + StringUtils.join(allAmenities, ", ") + "] compared to [" +
                searchResults + "]")
                .isNotEqualTo(searchResults);
    }

    @Override
    public void clickResetFiltersLink() {
        new AngularHotelResultsPage(getWebdriverInstance()).clickResetFiltersLink();
    }

    @Override
    public void verifyResetFiltersLinkDisplayed(boolean isDisplayed) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        boolean actual = resultsPage.isResetFiltersLinkDisplayed();
        assertThat(actual)
            .as("Expected Reset filters link displayed to be " + isDisplayed + " but got " + actual)
            .isEqualTo(isDisplayed);
    }

    @Override
    public void verfiyAngularDetailsContainsAmenity(String value, String filterType) {
        AngularHotelDetailsPage detailsPage = new AngularHotelDetailsPage(getWebdriverInstance());
        if (filterType.equals("Amenities")) {
            String list = detailsPage.getDetailsAmenitiesListAsString();
            assertThat(list.contains(value))
                .as("Expected list - " + list + " - to contain " + value)
                .isTrue();
        }
        else if (filterType.equals("Accessibility Options")) {
            String list = detailsPage.getDetailsAccessibilityListAsString();
            assertThat(list.contains(value))
                .as("Expected list - " + list + " - to contain " + value)
                .isTrue();
        }
        else {
            throw new UnimplementedTestException(filterType + " filtering is not supported for details page.");
        }
    }

    @Override
    public void sortResultsByCriteria(String criteria) {
        new AngularHotelResultsPage(getWebdriverInstance()).sortResultsBy(criteria);
    }

    @Override
    public void verifyResultsSortByCriteria(String criteria) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        if (criteria.equals("Popular")) {
            assertThat(resultsPage.getAllResultsPrices().equals(searchResults))
                .as("Expected: " + searchResults + " Got: " + resultsPage.getAllResultsPrices())
                .isTrue();
        }
        else if (criteria.equals("Price (low to high)")) {
            assertResultsSortedBy(resultsPage.getAllResultsPrices(), true);
        }
        else if (criteria.equals("Price (high to low)")) {
            assertResultsSortedBy(resultsPage.getAllResultsPrices(), false);
        }
        else if (criteria.contains("Recommended (high to low)")) {
            assertResultsSortedBy(resultsPage.getAllResultsRecommenedRatings(), false);
        }
        else if (criteria.equals("Star rating (high to low)")) {
            assertResultsSortedBy(resultsPage.getAllResultsStarRatings(), false);
        }
        else if (criteria.equals("Star rating (low to high)")) {
            assertResultsSortedBy(resultsPage.getAllResultsStarRatings(), true);
        }
        else {
            throw new RuntimeException("Invalid sort criteria - " + criteria);
        }
    }

    @Override
    public void getListOfCriteriaResults(String criteria) {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        if (criteria.equals("prices")) {
            searchResults = resultsPage.getAllResultsPrices();
        }
        else if (criteria.equals("recommended ratings")) {
            searchResults = resultsPage.getAllResultsRecommenedRatings();
        }
        else if (criteria.equals("star ratings")) {
            searchResults = resultsPage.getAllResultsStarRatings();
        }
        else {
            throw new RuntimeException(criteria + " list of results is not supported.");
        }
        LOGGER.info("LIST:\n" + StringUtils.join(searchResults, ", "));
    }

    @Override
    public void bookHotelInAngularDetailsPage() {
        AngularHotelDetailsPage detailsPage = new AngularHotelDetailsPage(getWebdriverInstance());
        purchaseParameters.setCostToSelect(new Float(detailsPage.getPricePerNight().replaceAll("[^\\d.]", "")));
        detailsPage.bookHotel();
    }

    @Override
    public void verifyHotelBillingPageFromAngularDetailsPage() {
        HotelBillingOnePage billingPage = new HotelBillingOnePage(getWebdriverInstance());
        String hotelNameOnBilling = billingPage.getTripSummaryFragment().getHotelOrNeighborhoodName();
        LOGGER.info(hotelSearchParameters.getSelectedSearchSolution().getHotelName() + " : " + hotelNameOnBilling);

        assertThat(hotelNameOnBilling)
            .as("Expected " + hotelSearchParameters.getSelectedSearchSolution().getHotelName() + " but got " +
                hotelNameOnBilling)
            // resorts, boutique, etc. shows as hotel in billing from angular details.
            //.isEqualTo(hotelSearchParameters.getSelectedSearchSolution().getHotelName());
            .containsIgnoringCase(
                hotelSearchParameters.getSelectedSearchSolution().getHotelName().replaceAll(" \\S*$", "").trim());

        assertThat(new Float(billingPage.getTripSummaryFragment().getStarRating().split(" ")[0]).floatValue())
            .as("Expected " + hotelSearchParameters.getSelectedSearchSolution().getRating().split(" ")[0] +
                " but got " + billingPage.getTripSummaryFragment().getStarRating().split(" ")[0])
            .isEqualTo(new Float(
                hotelSearchParameters.getSelectedSearchSolution().getRating().split(" ")[0]).floatValue());

        SimpleDateFormat sdfBilling = new SimpleDateFormat("E, MMM dd, yyyy");
        Date startDate = searchParameters.getStartDate();
        String billingStartDate = billingPage.getTripSummaryFragment().getCheckInDate();
        assertThat(billingStartDate)
            .as("Expected start date of " + sdfBilling.format(startDate) + " but got " + billingStartDate)
            .isEqualTo(sdfBilling.format(startDate));

        Date endDate = searchParameters.getEndDate();
        String billingEndDate = billingPage.getTripSummaryFragment().getCheckOutDate();
        assertThat(billingEndDate)
            .as("Expected start date of " + sdfBilling.format(endDate) + " but got " + billingEndDate)
            .isEqualTo(sdfBilling.format(endDate));

        Float billingPricePerNight = new Float(
            billingPage.getTripSummaryFragment().getPricePerNight().replaceAll("[^\\d.]", ""));
        assertThat(purchaseParameters.getCostToSelect())
            .as("Expected " + purchaseParameters.getCostToSelect() + " but got " + billingPricePerNight)
            .isEqualTo(billingPricePerNight);

        Integer numRooms = hotelSearchParameters.getNumberOfHotelRooms() != null ?
            hotelSearchParameters.getNumberOfHotelRooms() : 1;
        Integer billingNumRooms = new Integer(billingPage.getTripSummaryFragment().getNumberOfRooms());
        assertThat(billingNumRooms)
            .as("Expected " + billingNumRooms + " room(s) but got " + numRooms)
            .isEqualTo(numRooms);

        String priceDetail = billingPage.getTripSummaryFragment().getPriceDetailText();
        Integer adults = hotelSearchParameters.getNumberOfAdults() != null ?
            hotelSearchParameters.getNumberOfAdults() : 2;
        Integer children = hotelSearchParameters.getNumberOfChildren() != null ?
            hotelSearchParameters.getNumberOfChildren() : 0;
        assertThat(priceDetail.contains(adults + (adults > 1 ? " adults" : " adult")))
            .as("Expected to see " + adults + (adults > 1 ? " adults" : " adult") + " in price details.")
            .isTrue();
        if (children == 0 && priceDetail.contains("(" + adults)) {  // if it contains ( before adults, then vt.BBB14=1.
            assertThat(priceDetail.contains(children + " child") || priceDetail.contains(children + " children"))
                .as("Did not expect to see any children text in price details.")
                .isFalse();
        }
        else {
            if (children > 0) {
                assertThat(priceDetail.contains(children + (children > 1 ? " children" : " child ")))
                    .as("Expected to see " + children + (children > 1 ? " children" : " child") + " in price details.")
                    .isTrue();
            }
        }
    }

    @Override
    public void selectAngularMyAccount(String myAccountSelection) {
        LOGGER.info("Selecting " + myAccountSelection + " from My Account drop down.");
        new AngularGlobalHeader(getWebdriverInstance()).selectMyAccountItem(myAccountSelection);
    }

    @Override
    public void goToSupportPage(String pageType) {
        LOGGER.info("Selecting " + pageType + " from Support drop down.");
        new AngularGlobalHeader(getWebdriverInstance()).selectSupportItem(pageType);
    }

    @Override
    public void verifySupportPage(String pageType) {
        if (pageType.equals("Help center")) {
            new HelpCenterPage(getWebdriverInstance());
        }
        else if (pageType.equals("Manage subscriptions")) {
            // Until single sign on is implemented, we'll always go to sign in page.
            new SignInPage(getWebdriverInstance());
        }
        else {
            throw new UnimplementedTestException(pageType + " for angular support drop down not implemented.");
        }
    }

    @Override
    public void verifySignInNeeded(String myAccountSelection) {
        if (getWebdriverInstance().getCurrentUrl().endsWith("/disabledCookiesDetected")) {
            // Angular Hack...
            getWebdriverInstance().navigate().back();
            new AngularGlobalHeader(getWebdriverInstance()).selectMyAccountItem(myAccountSelection);
        }
        if (myAccountSelection.equals("Register")) {
            new RegisterNewUserPage(getWebdriverInstance());
        }
        else {
            new SignInPage(getWebdriverInstance());
        }
    }

    @Override
    public void verifyAngularHotelResultsCustomerCare() {
        AngularHotelResultsPage resultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        String phone = resultsPage.getCustomerCarePhoneNumber();
        String refNum = resultsPage.getCustomerCareRefNumber();
        assertThat(phone.matches("\\d{3}-\\d{3}-\\d{4}"))
            .as("Expected customer care phone number but got: " + phone)
            .isTrue();
        assertThat(refNum.matches("\\d+"))
            .as("Expected customer care reference number but got: " + refNum)
            .isTrue();
    }

    @Override
    public void verifyAngularHotelDetailsCustomerCare() {
        AngularHotelDetailsPage detailsPage = new AngularHotelDetailsPage(getWebdriverInstance());
        String phone = detailsPage.getCustomerCarePhoneNumber();
        String refNum = detailsPage.getCustomerCareRefNumber();
        assertThat(phone.matches("\\d{3}-\\d{3}-\\d{4}"))
            .as("Expected customer care phone number but got: " + phone)
            .isTrue();
        assertThat(refNum.matches("\\d+"))
            .as("Expected customer care reference number but got: " + refNum)
            .isTrue();
    }

    @Override
    public void verifyPageUIExceptionDisplayedFromDisambiguation() {
        if (!new AngularSystemErrorFragment(getWebdriverInstance()).isUIExceptionDisplayed()) {
            if (!hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation().equals(
                new AngularFareFinderFragment(getWebdriverInstance()).getDestinationLocationValue())) {
                // Looks like the app was able to resolve the input.
                throw new PendingException("App was able to resolve destination.");
            }
            throw new RuntimeException(
                "Expected Page UI Exception.\n" +
                hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation() + " : " +
                new AngularFareFinderFragment(getWebdriverInstance()).getDestinationLocationValue());
        }
    }

    @Override
    public void verifyPageUIExceptionDisplayed() {
        assertThat(new AngularSystemErrorFragment(getWebdriverInstance()).isUIExceptionDisplayed())
            .as("Expected UI exception to be displayed.")
            .isTrue();
    }

    @Override
    public void verifyPageAPIExceptionDisplayed() {
        assertThat(new AngularSystemErrorFragment(getWebdriverInstance()).isAPIExceptionDisplayed())
            .as("Expected API exception to be displayed.")
            .isTrue();
    }

    @Override
    public void verifyDisambiguationLocationListDisplayed() {
        assertThat(new AngularFareFinderFragment(getWebdriverInstance()).isDisambiguationListDisplayed())
            .as("Expected disambiguation list to be displayed.")
            .isTrue();
    }

    @Override
    public void verifyFareFinderError(String element) {
        AngularFareFinderFragment ffFragment = new AngularFareFinderFragment(getWebdriverInstance());
        boolean actual;
        if (element.equals("destination")) {
            actual = ffFragment.isDestinationErrorDisplayed();
        }
        else if (element.equals("start date")) {
            actual = ffFragment.isStartDateErrorDisplayed();
        }
        else if (element.equals("end date")) {
            actual = ffFragment.isEndDateErrorDisplayed();
        }
        else {
            throw new UnimplementedTestException(element + " is not valid fare finder error element.");
        }
        assertThat(actual)
            .as("Expected " + element + " error.")
            .isTrue();
    }

    @Override
    public void clickCommentCardLink(String page) {
        if (page.equals("home")) {
            new AngularHomePage(getWebdriverInstance()).clickOlabLink();
        }
        else if (page.equals("results")) {
            new AngularHotelResultsPage(getWebdriverInstance()).clickOlabLink();
        }
        else if (page.equals("details")) {
            new AngularHotelDetailsPage(getWebdriverInstance()).clickOlabLink();
        }
        else {
            throw new UnimplementedTestException(page + ": not valid page id for this method.");
        }
        LOGGER.info(">>>>> Switching to comment card window <<<<<");
        // Need to switch to comment card window. Looping through set as it is not guaranteed what order will be
        // returned. Assuming there will only be 2 windows opened as anymore will be problematic.
        WebDriver webdriverInstance = getWebdriverInstance();
        String parentWindow = webdriverInstance.getWindowHandle();
        //String parentTitle = webdriverInstance.getTitle();
        for (String handle : webdriverInstance.getWindowHandles()) {
            LOGGER.info("Browser window handle: " + handle);
            if (!handle.equals(parentWindow)) {
                webdriverInstance.switchTo().window(handle);
                // Assume the window that doesn't have the parent title is the comment card.
                if (webdriverInstance.getTitle().equals("Comment card")) {
                    LOGGER.info("Found comment card window.");
                    break;
                }
            }
        }
    }

    @Override
    public void verifyMapLoadedOnAngularPage(String page) {
        assertThat(
                page.equals("results") ?
                    new AngularHotelResultsPage(getWebdriverInstance()).isMapLoaded() :
                    new AngularHotelDetailsPage(getWebdriverInstance()).isMapLoaded())
            .as("Expected map to be loaded but it wasn't.")
            .isTrue();
    }

    @Override
    public void verifyAngularCommentCardGone() {
        // After submitting the comment card, it will disappear back to the calling search results page. We can
        // verify that the set of browser window handles will be 1, the parent browser window, and that the comment has
        // been sent.
        new AbstractPage(getWebdriverInstance());
        int windowHandleSize = getWebdriverInstance().getWindowHandles().size();
        LOGGER.info("Browser handles total: " + getWebdriverInstance().getWindowHandles().size());
        assertThat(getWebdriverInstance().getWindowHandles().size())
            .as("Expected window handle size to be 1 but was " + windowHandleSize)
            .isEqualTo(windowHandleSize);
    }

    @Override
    public void clickPublicLaunchSearchUrlForAngular(String key) {
        getWebdriverInstance().navigate().to(applicationUrl + new DealLinkFactory().getLaunchSearchUrl(key));
    }

    @Override
    public void verifyAngularHotelResultsFromLaunchSearchUrl() {
        int numResults = new AngularHotelResultsPage(getWebdriverInstance(), null).getNumberOfSolutions();
        LOGGER.info("Hotel results page solutions count: " + numResults);
        assertThat(numResults)
            .as("Expected number of solutions to be greater than 0.")
            .isGreaterThan(0);
    }
    /* ROW and domestic.*/
    @Override
    public void completePurchase(Boolean guestOrUser) {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());

        if (guestOrUser) {
            if (!new GlobalHeader(getWebdriverInstance()).isLoggedIn()) {
                hotelBillingPage.selectAsUser(authenticationParameters.getUsername(),
                        authenticationParameters.getPassword());
            }
            hotelBillingPage.fillTravelerInfo()
                    .withFirstName(userInformation.getFirstName())
                    .withLastName(userInformation.getLastName())
                    .withPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber())
                    .continuePanel();
        }
        else {
            hotelBillingPage.selectAsGuest();
            this.fillTravelerUserInfo();
        }

        // Insurance only for domestic site.
        if (new GlobalHeader(getWebdriverInstance()).currentlyOnDomesticSite()) {
            try {
                insuranceAddOnSelection(hotelBillingPage, purchaseParameters.getOptForInsurance());
            }
            catch (WebDriverException e) {
                LOGGER.debug("Insurance panel is unavailable.");
            }
            // Get new page if insurance is initially selected.
            hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        }
        if (purchaseParameters.getPaymentMethodType() == PaymentMethodType.PayPal) {
            hotelBillingPage.fillPayPalPaymentMethod()
                    .withBillingAddress(userInformation.getBillingAddress())
                    .withFirstName(userInformation.getFirstName())
                    .withLastName(userInformation.getFirstName())
                    .withCity(userInformation.getCity())
                    .withState(userInformation.getState())
                    .withZipCode(userInformation.getZipCode())
                    .continuePanel();
        }
        else if (purchaseParameters.getPaymentMethodType() == PaymentMethodType.V_ME) {
            hotelBillingPage.getVmePaymentFragment()
                    .fillVmePaymentMethodPanel(userInformation.getVmeUsername(), userInformation.getVmePassword());
        }
        else {
            try {
                LOGGER.debug("Looking for saved credit cards.");
                selectSavedCreditCard();
            }
            catch (NoSuchElementException ignored) {
                LOGGER.debug("No saved credit cards found - filling out form.");
                fillCreditCard();
            }
        }
        // Store total price from after selecting or declining insurance.
        saveBillingTotalInBean();

        // Wait for any ajax that might be happening before attempting to complete purchase.
        new Wait<Boolean>(new IsAjaxDone()).maxWait(30).apply(getWebdriverInstance());

        if (!purchaseParameters.getPaymentMethodType().equals(PurchaseParameters.PaymentMethodType.PayPal)) {
            LOGGER.info("PaymentMethodType is not paypal. Assume credit card type payment method.");
            hotelBillingPage.book();
            if (new PageName().apply(getWebdriverInstance()).contains(".search.results") &&
                    getWebdriverInstance().findElements(By.cssSelector(".errorMessage")).size() > 0) {
                throw new PendingException("Inventory check redirected to results page due to sold out.");
            }
        }
        else {
            LOGGER.info("PaymentMethodType set to paypal. Doing continuing to paypal flow.");
            hotelBillingPage.bookPaypal();
        }
    }

    protected void fillTravelerUserInfo() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        hotelBillingPage.fillTravelerInfo().withFirstName(userInformation.getFirstName())
                .withMiddleName(userInformation.getMiddleName()).withLastName(userInformation.getLastName())
                .withPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber())
                .withEmailId(userInformation.getEmailId()).continuePanel();
    }

    protected void insuranceAddOnSelection(HotelBillingOnePage billingPage, boolean wantInsurance) {
        billingPage.fillAdditionalFeatures().withInsurance(wantInsurance).continuePanel();
    }

    protected void selectSavedCreditCard() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        hotelBillingPage.fillCreditCard()
                .selectSavedCreditCard(purchaseParameters.getSavedCreditCard().getNames(),
                        userInformation.getSecurityCode());
    }

    protected void fillCreditCard() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());

        String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
                userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode :
                userInformation.getZipCode();
        hotelBillingPage.fillCreditCard()
                .withCcType(userInformation.getCcType())
                .withCcNumber(userInformation.getCcNumber())
                .withCcExpMonth(userInformation.getCcExpMonth())
                .withCcExpYear(userInformation.getCcExpYear())
                .withSecurityCode(userInformation.getSecurityCode())
                .withFirstName(userInformation.getCcFirstName())
                .withLastName(userInformation.getCcLastName())
                .withCountry(userInformation.getBillingCountry())
                .withBillingAddress(userInformation.getBillingAddress())
                .withCity(userInformation.getCity())
                .withState(userInformation.getState())
                .withZipCode(zipCode);
    }

    protected void saveBillingTotalInBean() {
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        String billingTotal = hotelBillingPage.getTripSummaryFragment().getRawTotalPrice();
        if (StringUtils.isEmpty(billingTotal)) {
            throw new PendingException("Billing total is empty!");
        }
        LOGGER.info("BILLING TOTAL: " + billingTotal);
        purchaseParameters.setBillingTotal(billingTotal);
    }

    private void assertResultsSortedBy(ArrayList<String> results, boolean doAscendingOrderBy) {
        LOGGER.info("LIST: " + StringUtils.join(results, ", "));
        for (int i = 1; i < results.size(); i++) {
            String x = results.get(i).replaceAll("[^\\d.]", "");
            String y = results.get(i - 1).replaceAll("[^\\d.]", "");
            Double a = new Double(x);
            Double b = new Double(y);
            LOGGER.info("Compare " + b.toString() + (doAscendingOrderBy ? " <= " : " >= ") + a.toString());
            if (doAscendingOrderBy) {
                assertThat(b)
                    .as("Expected (" + b + " <= " + a + ") to be true but got false instead.")
                    .isLessThanOrEqualTo(a);
            }
            else {
                // Descending order.
                assertThat(b)
                    .as("Expected (" + b + " >= " + a + ") to be true but got false instead.")
                    .isGreaterThanOrEqualTo(a);
            }
        }
    }

    public void verifyWeAreInTheRightDestination(String expectedDestination) {
        String destination = new AngularHotelResultsPage(getWebdriverInstance(), true)
                .getDestinationLocation().toLowerCase();
        assertThat(destination.contains(expectedDestination.toLowerCase()));
    }

    @Override
    public void goToAngularLandingPageUsingAnError() {
        List <String> activatedVersionTests = DesktopPageProviderUtils.getVersionTests(getWebdriverInstance(), "eVar1");
        if (activatedVersionTests.contains("AWA14-2") && activatedVersionTests.contains("ALS14-2")) {
            new AngularHomePage(getWebdriverInstance()).searchForHotels(
                        "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                        hotelSearchParameters.getGlobalSearchParameters().getStartDate(),
                        hotelSearchParameters.getGlobalSearchParameters().getEndDate(),
                        hotelSearchParameters.getNumberOfHotelRooms(),
                        hotelSearchParameters.getNumberOfAdults(),
                        hotelSearchParameters.getNumberOfChildren(),
                        hotelSearchParameters.getEnableHComSearch());

        }
        else {
            new HotelSearchFragment(getWebdriverInstance())
                        .withNumberOfHotelRooms(hotelSearchParameters.getNumberOfHotelRooms())
                        .withNumberOfAdults(hotelSearchParameters.getNumberOfAdults())
                        .withNumberOfChildren(hotelSearchParameters.getNumberOfChildren())
                        .withDestinationLocation("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                        .withStartDate(hotelSearchParameters.getGlobalSearchParameters().getStartDate())
                        .withEndDate(hotelSearchParameters.getGlobalSearchParameters().getEndDate())
                        .withEnableSearchPartner(hotelSearchParameters.getEnableHComSearch()).launchSearch();
        }
    }

}


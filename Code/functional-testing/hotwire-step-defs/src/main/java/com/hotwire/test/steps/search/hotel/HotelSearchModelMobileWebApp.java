/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.MobileCarSearchPage;
import com.hotwire.selenium.mobile.MobileHotelSearchPage;
import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.results.MobileHotelNoResultsPage;
import com.hotwire.selenium.mobile.results.MobileHotelResultsPage;
import com.hotwire.selenium.mobile.results.SearchSolution;
import com.hotwire.selenium.mobile.search.HotelSearchFragment;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.PageName;

import cucumber.api.PendingException;

/**
 * Created with IntelliJ IDEA. User: LRyzhikov Date: 5/30/12 Time: 1:38 PM To
 * change this template use File | Settings | File Templates.
 */
public class HotelSearchModelMobileWebApp extends HotelSearchModelTemplate {

    @Override
    public void chooseHotelsResultsByType(String resultsType) {
        MobileHotelResultsPage resultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        resultsPage.selectFirstResultByType(resultsType);
    }

    private SimpleEntry<String, String> getCoordinates(String location) {
        Pattern pattern = Pattern.compile("(-?[\\d]+.[\\d]+),[\\s]*(-?[\\d]+.[\\d]+)");
        Matcher matcher = pattern.matcher(location);

        if (matcher.matches()) {
            return new SimpleEntry<>(matcher.group(1), matcher.group(2));
        }

        return null;
    }

    @Override
    public void findFare(String selectionCriteria) {
        kickoffSearchWithParams();
        interactWithResults(selectionCriteria);
    }

    @Override
    public void findPropertyFare() {
        kickoffSearchWithParams(true);
    }

    @Override
    public void launchSearch() {
        kickoffSearchWithParams();
    }

    private void kickoffSearchWithParams() {
        kickoffSearchWithParams(false);
    }

    private void kickoffSearchWithParams(boolean isPropertySearch) {
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        SimpleEntry<String, String> coordinates =
                getCoordinates(globalSearchParameters.getDestinationLocation());

        MobileHotwireHomePage mobileHotwireHomePage = new MobileHotwireHomePage(getWebdriverInstance());
        HotelSearchFragment hotelSearchFragment = mobileHotwireHomePage.getHotelSearchFragment();
        hotelSearchFragment
                .withNumberOfHotelRooms(searchParameters.getNumberOfHotelRooms())
                .withNumberOfAdults(searchParameters.getNumberOfAdults())
                .withNumberOfChildren(searchParameters.getNumberOfChildren())
                .setStartDate(globalSearchParameters.getStartDate())
                .setEndDate(globalSearchParameters.getEndDate());

        if (coordinates == null) {
            if (!isPropertySearch) {
                hotelSearchFragment.withDestinationLocation(globalSearchParameters.getDestinationLocation());
            }
            else {
                hotelSearchFragment.withDestinationLocation(globalSearchParameters.getDestinationLocation(), true);
            }
        }
        else {
            hotelSearchFragment.withDestinationLocation(coordinates);
        }

        hotelSearchFragment.findFare();
    }

    @Override
    public void verifyCrossSellData(com.hotwire.selenium.desktop.searchsolution.SearchSolution solution) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotelDetailsPage(String pageType) {
        throw new UnimplementedTestException("Implement me!");
    }

    private void interactWithResults(String selectionCriteria) {

        // Verify that you are on the results page before you can filter/sort thru the results
        MobileHotelResultsPage resultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        resultsPage.assertResultOnPage();

        if ("neighborhood".equalsIgnoreCase(selectionCriteria)) {
            // The user wants to use location as a criteria for viewing results.
            // Take them to the area map interaction
            //this.navigateToAreaMap();
            this.filterOutTopNeighborhood();
            this.verifyHotelSearchResultsPage();
        }
        // If the selection criteria is not neighborhood and is not empty, it must be some kind of sort
        else if (selectionCriteria != null && !selectionCriteria.isEmpty()) {
            // The user wants to sort by the given criteria
            this.sortByCriteria(selectionCriteria);
        }
    }

    @Override
    public void changeCurrency(String currencyCode) {
        // Change the currency on the mobile hotel results page
        MobileHotelResultsPage resultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        resultsPage.changeCurrency(currencyCode);
    }

    /**
     * Click on the area map button on the mobile hotel results page
     */
    @Override
    public void navigateToAreaMap() {
        MobileHotelResultsPage resultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        resultsPage.clickAreaMap();
    }

    /**
     * Verify that we have search results for our hotel search.
     */
    @Override
    public void verifyHotelSearchResultsPage() {
        // verify that we are getting results back by looking for elements that have "priceLockup" css class.
        MobileHotelResultsPage resultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        resultsPage.assertResultOnPage();
    }

    // Not seen any references for these LOC and commented it now. Based on any future need will un comment the same.
    @Override
    public void filterOutTopNeighborhood() {
        /*MobileHotelResultsMapPage resultsMapPage =
         new MobileHotelResultsMapPage(getWebdriverInstance(), "tile.hotel.results.map");
        // Click on the filter button
        resultsMapPage.clickNeighborhoodFilter();
        // Filter out the first neighborhood
        resultsMapPage.filterOutTopNeighborhood();
        // Click on update results button
        resultsMapPage.clickNeighborhoodFilter();*/

        new MobileHotelResultsPage(getWebdriverInstance()).sortByNeighborhood();

    }

    @Override
    public void sortByCriteria(String sortCriteria) {
        new MobileHotelResultsPage(getWebdriverInstance()).sortByCriteria(sortCriteria);
        // Get new state of the results page from the clicking of the sorting option.
        // (preethi): This is currently broken for price.
        // Looks like the sorting for price is done by the total price
        // which isn't visible on the results page, but there's also a question of why the lower price per
        // night has a higher total price than the nightly rate of a dollar more.
        if (!"price".equalsIgnoreCase(sortCriteria)) {
            new MobileHotelResultsPage(getWebdriverInstance()).assertSortSuccessful(sortCriteria);
        }
        new MobileHotelResultsPage(getWebdriverInstance());
    }

    @Override
    public void verifyValidationErrorOnField(String validationError, String field) {
        if (("empty destination".equalsIgnoreCase(validationError) && "city".equalsIgnoreCase(field)) ||
                ("minimum length".equalsIgnoreCase(validationError) && "city".equalsIgnoreCase(field)) ||
                ("invalid check-out".equalsIgnoreCase(validationError) && "date".equalsIgnoreCase(field)) ||
                ("one night reservation".equalsIgnoreCase(validationError) && "date".equalsIgnoreCase(field)) ||
                ("invalid drop-off".equalsIgnoreCase(validationError) && "date".equalsIgnoreCase(field))) {

            // This error is on the home page
            MobileHotwireHomePage page = new MobileHotwireHomePage(getWebdriverInstance());
            page.hasValidationErrorOnField(validationError, field);
        }
        else if ("invalid destination".equalsIgnoreCase(validationError)) {
            verifyDisambiguationPage(true);
        }
        else if ("invalid car time".equalsIgnoreCase(validationError) && "time".equalsIgnoreCase(field)) {
            MobileCarSearchPage carSearchPage = new MobileCarSearchPage(getWebdriverInstance());
            carSearchPage.verifyErrorText("Drop off time is not valid. The drop off time must" +
                    " be 30 minutes after of your pick up.");
        }
        else {
            MobileHotelSearchPage page = new MobileHotelSearchPage(getWebdriverInstance());
            page.hasValidationErrorOnField(validationError, field);
        }
    }

    @Override
    public void verifyDisambiguationPage(boolean hasErrorMessage) {
        MobileAbstractPage hotelDisambiguationPage = new MobileAbstractPage(
            getWebdriverInstance(), new String[] {"tile.hotel.multiple.locations", "tile.hotel.landing"});
        if (hasErrorMessage) {
            if (new PageName().apply(getWebdriverInstance()).contains("hotel.multiple")) {
                hotelDisambiguationPage.assertHasFormErrors(
                    "Choose your city from the options below. If your city isn't listed, " +
                    "either you entered it incorrectly, or that destination is not yet available.");
            }
            else {
                hotelDisambiguationPage.assertHasFormErrors(
                    "We're sorry. We didn't find any results for your search. " +
                    "Please modify your search criteria and try again.");
            }
        }
    }

    @Override
    public void verifyResultsPage() {
        MobileHotelResultsPage resultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        resultsPage.assertResultOnPage();
    }

    @Override
    public void verifyNoResultsPage() {
        MobileHotelNoResultsPage resultsPage = new MobileHotelNoResultsPage(getWebdriverInstance());
        resultsPage.assertNoResultsOnPage();
    }

    @Override
    public void verifyRetailHeroIsPresent() {
        MobileHotelResultsPage resultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        if (resultsPage.isSoldOutMessageDisplayed()) {
            throw new ZeroResultsTestException("The expected hotel has no rooms available!");
        }
        resultsPage.assertRetailHeroIsPresent();
    }

    @Override
    public void verifyDiscountBannerOnFF() {
        MobileHotelSearchPage mobileHotelSearchPage = new MobileHotelSearchPage(getWebdriverInstance());
        logger.info("Discount banner avaliable on Hotel search page: " +
                mobileHotelSearchPage.verifyDiscountBannerOnOff());
        assertThat(mobileHotelSearchPage.verifyDiscountBannerOnOff()).
                as("Discount code banner avaliable or not on Fare Finder").isTrue();
    }

    @Override
    public void verifyDiscountBannerOnHotelResults() {
        MobileHotelResultsPage mobileHotelResultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        logger.info("Discount banner avaliable on Hotel results page: " +
                mobileHotelResultsPage.verifyDiscountBannerOnResultsPage());
        assertThat(mobileHotelResultsPage.verifyDiscountBannerOnResultsPage()).
                as("Discount code banner avaliable or not on Results page").isTrue();
    }

    @Override
    public void verifySuggestedLocation(String number, String location, String style, String click) {
        SearchParameters parameters = searchParameters.getGlobalSearchParameters();

        MobileHotwireHomePage mobileHotwireHomePage = new MobileHotwireHomePage(getWebdriverInstance());
        HotelSearchFragment hotelSearchFragment = mobileHotwireHomePage.getHotelSearchFragment();
        List<String> searchSuggestions = hotelSearchFragment.getSearchSuggestions(parameters.getDestinationLocation());

        assertThat(searchSuggestions).startsWith(location);
    }

    @Override
    public void verifyResultsContainLocation(String location) {
        MobileHotelResultsPage mobileHotelResultsPage = new MobileHotelResultsPage(getWebdriverInstance());

        Set<String> hotelNames = new HashSet<>();

        for (SearchSolution searchSolution : mobileHotelResultsPage.getSearchSolutionList()) {
            hotelNames.add(searchSolution.getHotelName());
        }

        assertThat(hotelNames).contains(location);
    }

    @Override
    public void changeHotelSearch() {
        MobileHotelResultsPage mobileHotelResultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        mobileHotelResultsPage.changeHotelSearch();
    }

    @Override
    public void verifyHotelSearchDetails() {
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        if (sessionModifingParams.asUrlParameters().contains("HMH14=2")) {
            new MobileHotwireHomePage(getWebdriverInstance());
        }
        else {
            MobileHotelSearchPage mobileHotwireHomePage = new MobileHotelSearchPage(getWebdriverInstance());
            mobileHotwireHomePage.verifyHotelSearchDetails(globalSearchParameters.getDestinationLocation());
        }
    }

    @Override
    public void verifySearchResultsPage(String resultsType) {
        new MobileHotelResultsPage(getWebdriverInstance());
    }

    @Override
    public void verifyResultsPageHasSolutions() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectHotelByName(String hotelName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAllInclusivePriceAtResults(String priceType, String resultsType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySearchCriteria() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyResultsCurrency(String currency) {
        MobileHotelResultsPage mobileHotelResultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        assertThat(mobileHotelResultsPage.getCurrency()).isEqualTo(currency);
    }

    @Override
    public void verifyCommentCardPopup() {
        throw new PendingException("Implement Me!");
    }

    @Override
    public void verifyDetailsPageFarefinder() {
        throw new PendingException("Implement Me!");
    }


    @Override
    public void verifyTripWatcherModuleInHotelResultsList(boolean isDisplayed) {
        throw new PendingException("Implement Me!");
    }

    @Override
    public void clickResultListTripWatcherModule() {
        throw new PendingException("Implement Me!");
    }

    @Override
    public void verifyTripWatcherLayerFromSpeedbump(boolean isFromSpeedbump) {
        throw new PendingException("Implement Me!");
    }

    @Override
    public void saveHotelSearchId() {
        throw new UnimplementedTestException("Implement ME!");
    }
}

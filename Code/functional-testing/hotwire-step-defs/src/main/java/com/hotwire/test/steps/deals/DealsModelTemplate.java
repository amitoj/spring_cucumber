/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.deals;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.fest.assertions.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.deals.HotelDealPage;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.search.hotel.HotelSearchParametersImpl;
import com.hotwire.testing.UnexpectedPageTestException;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import cucumber.api.PendingException;

/**
 * Base implementation of deals steps.
 */
public class DealsModelTemplate extends WebdriverAwareModel implements DealsModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(DealsModelTemplate.class.getSimpleName());

    @Autowired
    @Qualifier("hotelSearchParameters")
    protected HotelSearchParametersImpl hotelSearchParameters;

    private Deal deal;

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Override
    public void verifyBrowserTitle(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyEnctiptedDealHash() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void verifyDiscountDealsSortedAlphabetically(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHeaderForIntlDeals(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHeaderOnDealsPage(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAllLinksAreUnique(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyFarefiderOnDealsPage(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToOpmEnablerDeal(String provider, String inventoryTypeLink, boolean testOpm) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseTheFirstLinkFromMostPopularDestination(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseTheFirstLinkFromAllCitiesAndVerifyURL(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseTheFirstLinkFromAllCitiesAndVerifyLocationAndURL(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void goToSuperPageLink(String vertical, String channel) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void goToDBMLink(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void goToDBMLink(String vertical, String channel) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void goToSearchOptionsLink(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotelDetails(boolean shouldBeOpaque) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyInventoryErrorDisplayed() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCrossSellInSearchResults() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToCrossSellDeal() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCrossSellDetails(boolean doNewCrossSell, String expectedMarket) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void activateNewOpaqueCrossSell(boolean doNewCrossSell) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToLastMinuteHotels(String dealType, String topDestination) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToSecretHotRateHotels(String topDestination) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyMarketingDealHotels(String dealType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySecretHotRateHotels() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void goToCrossSellLink(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void findDealsFromLandingPage(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToDealsFromFooter(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyOpaqueTaglines(final String taglineType) {
        final String hotelName;
        HotelResultsPage hotelResultsPage = new HotelResultsPage(getWebdriverInstance());
        hotelName = "area";

        Set<String> hotelNames = new HashSet<String>();

        for (SearchSolution searchSolution : hotelResultsPage.getSearchSolutionList()) {
            hotelNames.add(searchSolution.getHotelName());
        }

        assertThat(hotelNames).satisfies(new Condition<Collection<?>>() {
            @Override
            public boolean matches(Collection<?> objects) {
                boolean hasCustomTagLines = false;

                for (Object o : objects) {
                    if (o.toString().contains(hotelName)) {
                        hasCustomTagLines = true;
                        break;
                    }
                }

                return !(hasCustomTagLines ^ taglineType.equalsIgnoreCase("custom"));
            }
        });

    }

    @Override
    public void verifyHotelDealFromLandingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCarDealFromLandingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectDetailsCrossSell(int dealIndex) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());

        if (!detailsPage.isHotelCrossSellDisplayed()) {
            throw new ZeroResultsTestException("No cross sell on details page.");
        }
        detailsPage.waitForUpperCrossSellNeighborhoodNamesVisibility();
        SearchSolution solution = detailsPage.getCrossSellsList().get(dealIndex);

        hotelSearchParameters.setSelectedSearchSolution(solution);
        detailsPage.selectCrossSell(solution.getNumber());
    }

    @Override
    public void verifyAirDealFromLandingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectTopDestinationCity() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectSuggestedDestination(String destinationType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void accessDestinationPopularHotel() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotelLAndingPage(SearchSolution solution) {
        throw new UnimplementedTestException("Implement me!");
    }

    /**
     * For converged hotel landing page at the moment. Works for ROW and domestic.
     */
    @Override
    public void verifyDealsModuleIsDisplayed() {
        HotelIndexPage index = new HotelIndexPage(getWebdriverInstance());
        boolean actual = index.isHotelDealsModuleDisplayed() && !index.isHotelDealsEmpty();
        assertThat(actual)
            .as("Expected non-empty deals module to be displayed, but this was false.")
            .isTrue();
    }

    @Override
    public void verifyDealsPage(String page) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectHotelDeal() {
        String pageName = new PageName().apply(getWebdriverInstance());
        Map<String, String> map;
        if (pageName.contains(".uhp.index") || pageName.contains("hotwire.home")) {
            map = new HomePage(getWebdriverInstance()).selectHotelDeal();
        }
        else if (pageName.contains(".hotel.index") || pageName.contains("hotwire.hotel")) {
            map = new HotelIndexPage(getWebdriverInstance()).selectHotelDeal();
            new WebDriverWait(getWebdriverInstance(), 30).until(new IsAjaxDone());
            new HotelResultsPage(getWebdriverInstance());
        }
        else {
            throw new UnexpectedPageTestException("Unexpected hotel page for getting deals from deals module: " +
                                                  pageName);
        }
        Deal newDeal = new Deal();
        newDeal.setLocation(map.get("location"));
        newDeal.setRating(map.get("optional"));
        setDeal(newDeal);
    }

    @Override
    public void verifyDealDetails() {
        if (new PageName().apply(getWebdriverInstance()).contains("search")) {
            LOGGER.info("Clicking deal triggered search.");
            new HotelResultsPage(getWebdriverInstance());
            return;
        }
        HotelDealPage hotelDealPage = new HotelDealPage(getWebdriverInstance());
        if (!hotelDealPage.containsDeals()) {
            throw new PendingException("There are no deals listed in the hotel deals page.");
        }
        hotelDealPage.getDealDescription();
        assertThat(hotelDealPage.getDealDescription())
                .as("Deal location, rating and price")
                .contains(getDeal().getLocation())
                .contains(getDeal().getRating());
    }

    @Override
    public void selectHotelDealOption() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySearchCriteria() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyVacationDealFromLandingPage() {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void navigateToDiscountInternational(String vertical) {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void verifyURLDiscountInternationalPage(String vertical) {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void verifyURLDiscountUSPage(String vertical) {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void verifyAboutOurCarsModule() {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void navigateThruFlightsDestinationPagination(String paginationOption) {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void clickOnBacktoPreviousPageLink() {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void verifyNextResultSetOfStates(int statesNumber) {
        throw new UnimplementedTestException("Implement Me!");
    }
}

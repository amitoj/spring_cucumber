/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.deals;

import com.hotwire.selenium.desktop.searchsolution.SearchSolution;

/**
 * clean up notes from Rex: Java/cucumber interface for OPM Enabler tests.
 * This interface needs work. See comments on methods.
 * <p/>
 * And change the name
 */
public interface DealsModel {

    void navigateToOpmEnablerDeal(String provider, String inventoryTypeLink, boolean testOpm);

    void chooseTheFirstLinkFromMostPopularDestination(String vertical);

    void chooseTheFirstLinkFromAllCitiesAndVerifyURL(String vertical);

    void chooseTheFirstLinkFromAllCitiesAndVerifyLocationAndURL(String vertical);

    void goToSuperPageLink(String vertical, String channel);

    void goToDBMLink(String vertical);

    void goToDBMLink(String vertical, String channel);

    void goToSearchOptionsLink(String vertical);

    void verifyHotelDetails(boolean shouldBeOpaque);

    void verifyInventoryErrorDisplayed();

    void verifyCrossSellInSearchResults();

    void navigateToCrossSellDeal();

    void navigateToDiscountInternational(String vertical);

    void verifyCrossSellDetails(boolean doNewCrossSell, String expectedMarket);

    void activateNewOpaqueCrossSell(boolean doNewCrossSell);

    void navigateToLastMinuteHotels(String dealType, String topDestination);

    void navigateToSecretHotRateHotels(String topDestination);

    void verifyMarketingDealHotels(String dealType);

    void verifySecretHotRateHotels();

    void goToCrossSellLink(String vertical);

    void verifyOpaqueTaglines(String taglineType);

    void findDealsFromLandingPage(String vertical);

    void navigateToDealsFromFooter(String vertical);

    void verifyHotelDealFromLandingPage();

    void verifyCarDealFromLandingPage();

    void selectDetailsCrossSell(int dealIndex);

    void verifyAirDealFromLandingPage();

    void selectTopDestinationCity();

    void selectSuggestedDestination(String destinationType);

    void accessDestinationPopularHotel();

    void verifyHotelLAndingPage(SearchSolution solution);

    void verifyDealsModuleIsDisplayed();

    void verifyHeaderOnDealsPage(String vertical);

    void verifyDiscountDealsSortedAlphabetically(String vertical);

    void verifyHeaderForIntlDeals(String vertical);

    void verifyAllLinksAreUnique(String vertical);

    void verifyFarefiderOnDealsPage(String vertical);

    void verifyBrowserTitle(String vertical);

    void verifyEnctiptedDealHash();

    void verifyDealsPage(String page);

    void selectHotelDeal();

    void verifyDealDetails();

    void selectHotelDealOption();

    void verifySearchCriteria();

    void verifyVacationDealFromLandingPage();

    void verifyURLDiscountInternationalPage(String vertical);

    void verifyURLDiscountUSPage(String vertical);

    void verifyAboutOurCarsModule();

    void navigateThruFlightsDestinationPagination(String paginationOption);

    void clickOnBacktoPreviousPageLink();

    void verifyNextResultSetOfStates(int statesNumber);
}

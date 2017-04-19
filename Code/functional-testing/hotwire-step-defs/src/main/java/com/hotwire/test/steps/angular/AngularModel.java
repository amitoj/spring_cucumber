/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.angular;

import java.util.Date;

/**
 * @author vjong
 *
 */
public interface AngularModel {

    void requestQuotesInAngularSite();

    void verifyAngularHotelResults();

    void bookFirstAngularSolution();

    void verifyAngularHotelDetails();

    void selectCountryInAngularSite(String country);

    void verifySelectedCountryInAngularSite(String country);

    void selectDestination(String destination);

    void setHotelTravelDates(Date start, Date end);

    void selectNumberOfRooms(Integer numberOfHotelRooms);

    void selectCurrencyInAngularSite(String currency);

    void verifySelectedCurrencyInAngularSite(String currency);

    void filterResults(String filterType);

    void filterResults(String value, String fitlerType);

    void verifyFilterResults(String filterType);

    void verifyFilterResults(String value, String filterType);

    void sortResultsByCriteria(String criteria);

    void verifyResultsSortByCriteria(String criteria);

    void getListOfCriteriaResults(String criteria);

    void bookHotelInAngularDetailsPage();

    void verifyHotelBillingPageFromAngularDetailsPage();

    void verifySelectedCountryHotelLandingPage(String country);

    void selectAngularMyAccount(String myAccountSelection);

    void verifySignInNeeded(String myAccountSelection);

    void resetAreasFilter();

    void verifyResultsNotFilteredByAreas();

    void verifyStateOfZeroCountAreasFilterOptions(String filterType);

    void verifyAngularHotelResultsCustomerCare();

    void verifyAngularHotelDetailsCustomerCare();

    void requestQuotesInAngularHotelResults();

    void completePurchase(Boolean guestOrUser);

    void verifyResultsNotFilteredByStarRating();

    void clickResetFiltersLink();

    void verifyResetFiltersLinkDisplayed(boolean isDisplayed);

    void launchAngularSearchInHomepage();

    void goToAngularLandingPage(String vertical);

    void storeAngularResultsList();

    void verifyCurrentResultsListIsSameAsStoreResultsList();

    void goBackToAngularResultsFromAngularDetailsPage();

    void navigateBackFromCurrentPage();

    void goToUHP();

    void verfiyAngularDetailsContainsAmenity(String value, String filterType);

    void verifyResultsNotFilteredByAmenities();

    void goToVerticalFromAngularPage(String vertical);

    void goToSupportPage(String pageType);

    void verifySupportPage(String pageType);

    void verifyFareFinderError(String element);

    void typeDestination(String destination);

    void typeStartDate(String start);

    void typeEndDate(String end);

    void launchAngularSearchInResultsPage();

    void verifyFilteredResultsCount(String value, String filterType);

    void verifyFilteredResultsCount(String filterType);

    void verifyPageUIExceptionDisplayed();

    void verifyPageUIExceptionDisplayedFromDisambiguation();

    void verifyDisambiguationLocationListDisplayed();

    void verifyPageAPIExceptionDisplayed();

    void clickCommentCardLink(String page);

    void verifyAngularCommentCardGone();

    void verifyMapLoadedOnAngularPage(String page);

    void clickPublicLaunchSearchUrlForAngular(String key);

    void verifyAngularHotelResultsFromLaunchSearchUrl();

    void verifyWeAreInTheRightDestination(String expectedDestination);

    void requestQuotesInAngularSiteUsingFirstResult();

    void goToAngularLandingPageUsingAnError();
}


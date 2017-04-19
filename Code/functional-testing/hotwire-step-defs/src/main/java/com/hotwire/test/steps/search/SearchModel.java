/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search;

import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;

import java.util.Date;
import java.util.List;

/**
 * This interface provides methods to execute a fare finder.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public interface SearchModel {

    void findFare(String selectionCriteria);

    void findFare(String selectionCriteria, HotelSearchFragment searchFragment);

    void launchSearch();

    void navigateToAreaMap();

    void changeCurrency(String currencyCode);

    void verifyResultsPage();

    void verifyNoResultsPage();

    void verifyDisambiguationPage(boolean hasErrorMessage);

    void verifyDisambiguationLayerIsVisible();

    void verifyDisambiguationLayerIsNotVisible();

    void verifyLocationOnResultsPage(String location);

    void verifyResultsContainLocation(String location);

    void verifyTypeOFSearchOnCarResults(String typeOfSearch);

    void clickOnTheDestinationFiled();

    void verifySuggestedLocation(String number, String location, String style, String click);

    void verifyActivitiesBanner(String destinationCity, Date startDate);

    void clickBanner();

    void verifyActivitiesPage(String destinationCity, Date endDate, Date startDate);

    void verifyCrossCellParameters(String startDate, String endDate, String destinationLocation);

    void setFirstLowestPriseSolution();

    void verifyDisambiguationResults(List<String> results);

    void verifyLocation(String expectedLocation);

    void typeLocation(String location);

    void pressKeyOnAutocomplete(String keyName, int numberOfClicks);

    void verifyNonEmptyRefineSearchModule();

    void findPropertyFare();

    void findPropertyFare(HotelSearchFragment searchFragment);

    void verifyDatesValue(String webElement, Date dateValue);
}

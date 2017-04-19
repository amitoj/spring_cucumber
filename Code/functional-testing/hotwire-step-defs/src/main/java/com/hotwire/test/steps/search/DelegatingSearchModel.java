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
 * This class provides methods to execute a fare finder.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public class DelegatingSearchModel extends SearchModelTemplate<SearchParameters> {

    private SearchModel hotelSearchModel;
    private SearchModel carSearchModel;
    private SearchModel airSearchModel;
    private SearchModel cruiseSearchModel;
    private SearchModel packagesSearchModel;
    private SearchModel activitiesSearchModel;

    public void setHotelSearchModel(SearchModel hotelSearchModel) {
        this.hotelSearchModel = hotelSearchModel;
    }

    public void setCarSearchModel(SearchModel carSearchModel) {
        this.carSearchModel = carSearchModel;
    }

    public void setAirSearchModel(SearchModel airSearchModel) {
        this.airSearchModel = airSearchModel;
    }

    public void setCruiseSearchModel(SearchModel cruiseSearchModel) {
        this.cruiseSearchModel = cruiseSearchModel;
    }

    public void setPackagesSearchModel(SearchModel packagesSearchModel) {
        this.packagesSearchModel = packagesSearchModel;
    }

    public void setActivitiesSearchModel(SearchModel activitiesSearchModel) {
        this.activitiesSearchModel = activitiesSearchModel;
    }

    @Override
    public void findFare(String selectionCriteria) {
        getSearchModelToDelegate().findFare(selectionCriteria);
    }

    @Override
    public void findFare(String selectionCriteria, HotelSearchFragment searchFragment) {
        getSearchModelToDelegate().findFare(selectionCriteria, searchFragment);
    }

    @Override
    public void findPropertyFare() {
        getSearchModelToDelegate().findPropertyFare();
    }

    @Override
    public void verifyDatesValue(String webElement, Date dateValue) {
        getSearchModelToDelegate().verifyDatesValue(webElement, dateValue);
    }

    @Override
    public void navigateToAreaMap() {
        getSearchModelToDelegate().navigateToAreaMap();
    }

    private SearchModel getSearchModelToDelegate() {
        switch (searchParameters.getPGoodCode()) {
            case H:
                return hotelSearchModel;
            case A:
                return airSearchModel;
            case C:
                return carSearchModel;
            case R:
                return cruiseSearchModel;
            case P:
                return packagesSearchModel;
            case V:
                return activitiesSearchModel;
            default:
                return null;
        }

    }

    @Override
    public void changeCurrency(String currencyCode) {
        getSearchModelToDelegate().changeCurrency(currencyCode);
    }

    @Override
    public void launchSearch() {
        getSearchModelToDelegate().launchSearch();
    }

    @Override
    public void verifyDisambiguationResults(List<String> results) {
        getSearchModelToDelegate().verifyDisambiguationResults(results);
    }

    @Override
    public void verifyDisambiguationPage(boolean hasErrorMessage) {
        getSearchModelToDelegate().verifyDisambiguationPage(hasErrorMessage);
    }

    @Override
    public void verifyResultsPage() {
        getSearchModelToDelegate().verifyResultsPage();
    }

    @Override
    public void verifyNoResultsPage() {
        getSearchModelToDelegate().verifyNoResultsPage();
    }

    @Override
    public void verifyDisambiguationLayerIsVisible() {
        getSearchModelToDelegate().verifyDisambiguationLayerIsVisible();
    }

    @Override
    public void verifyDisambiguationLayerIsNotVisible() {
        getSearchModelToDelegate().verifyDisambiguationLayerIsNotVisible();
    }

    @Override
    public void verifySuggestedLocation(String number, String location, String style, String click) {
        getSearchModelToDelegate().verifySuggestedLocation(number, location, style, click);
    }

    @Override
    public void verifyResultsContainLocation(String location) {
        getSearchModelToDelegate().verifyResultsContainLocation(location);
    }

    @Override
    public void verifyLocationOnResultsPage(String location) {
        getSearchModelToDelegate().verifyLocationOnResultsPage(location);
    }
}


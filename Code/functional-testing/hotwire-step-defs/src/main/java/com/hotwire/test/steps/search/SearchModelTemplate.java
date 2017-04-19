/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search;

import com.hotwire.selenium.desktop.us.results.car.CarDisambiguationPage;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * To change this template use File | Settings | File Templates.
 *
 * @param <T>
 */
public class SearchModelTemplate<T> extends WebdriverAwareModel implements SearchModel {
    protected final Logger logger = LoggerFactory.getLogger(SearchModelTemplate.class.getSimpleName());

    protected T searchParameters;

    protected URL applicationUrl;

    public void setSearchParameters(T searchParameters) {
        this.searchParameters = searchParameters;
    }

    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    @Override
    public void findFare(String selectionCriteria) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void findFare(String selectionCriteria, HotelSearchFragment searchFragment) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToAreaMap() {
        throw new UnimplementedTestException("Implement me!");
    }


    @Override
    public void changeCurrency(String currencyCode) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void launchSearch() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDisambiguationPage(boolean hasErrorMessage) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyResultsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setFirstLowestPriseSolution() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyNoResultsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyActivitiesBanner(String destinationCity, Date startDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void clickBanner() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyActivitiesPage(String destinationCity, Date endDate, Date startDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCrossCellParameters(String startDate, String endDate, String destinationLocation) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDisambiguationResults(List<String> results) {
        CarDisambiguationPage disPage = new CarDisambiguationPage(getWebdriverInstance());
        List<String> disResults = disPage.getResults();
        Collections.sort(disResults);
        Collections.sort(results);
        assertThat(disResults).as("Compare results with results in feature file").isEqualTo(results);
    }

    @Override
    public void verifyLocation(String expectedLocation) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void typeLocation(String location) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDisambiguationLayerIsVisible() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDisambiguationLayerIsNotVisible() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void clickOnTheDestinationFiled() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySuggestedLocation(String number, String location, String style, String click) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyLocationOnResultsPage(String location) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyResultsContainLocation(String location) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTypeOFSearchOnCarResults(String typeOfSearch) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void pressKeyOnAutocomplete(String keyName, int numberOfClicks) {
        Actions action = new Actions(getWebdriverInstance());
        int  i = 0;
        while (i < numberOfClicks) {
            action.sendKeys(Keys.valueOf(keyName)).perform();
            i++;
        }
    }

    @Override
    public void verifyNonEmptyRefineSearchModule() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void findPropertyFare() {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void findPropertyFare(HotelSearchFragment searchFragment) {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void verifyDatesValue(String webElement, Date dateValue) {
        throw new UnimplementedTestException("Implement Me!");
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.activities;

import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;

import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.index.HotwireVacationsIndexPage;
import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.test.steps.search.SearchParameters;

/**
 * Search model for activities
 */
public class ActivitiesSearchModelWebApp extends SearchModelTemplate<ActivitiesSearchParameters>
        implements ActivitiesSearchModel {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    public void findFare(String selectionCriteria) {
        HomePage homePage = new HomePage(getWebdriverInstance());
        SearchParameters search = searchParameters.getGlobalSearchParameters();
        homePage.findActivities().findFare(search.getDestinationLocation(), search.getStartDate(), search.getEndDate());
    }

    @Override
    public void verifyVacationPage(String destination) {
        HotwireVacationsIndexPage vacationPage = new HotwireVacationsIndexPage(getWebdriverInstance());

        String startDate = FORMATTER.format(searchParameters.getGlobalSearchParameters().getStartDate());
        String endDate = FORMATTER.format(searchParameters.getGlobalSearchParameters().getEndDate());

        assertThat(vacationPage.verifyVacationPageForActivities(destination, startDate, endDate))
                .as("Vacation URL has incorrect parameters").isTrue();
    }

    @Override
    public void verifyErrorMessage() {
        HotwireVacationsIndexPage vacationPage = new HotwireVacationsIndexPage(getWebdriverInstance());

        String destination = searchParameters.getGlobalSearchParameters().getDestinationLocation();
        String startDate = FORMATTER.format(searchParameters.getGlobalSearchParameters().getStartDate());
        String endDate = FORMATTER.format(searchParameters.getGlobalSearchParameters().getEndDate());

        assertThat(vacationPage.verifyErrorMessage(destination, startDate, endDate))
                .as("Vacation URL has incorrect parameters").isTrue();
    }
}

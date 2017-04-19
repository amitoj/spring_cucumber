/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.planningtools;

import static org.fest.assertions.Assertions.assertThat;

import com.hotwire.selenium.desktop.travelsavingsindicator.TravelSavingsIndicatorPage;
import com.hotwire.selenium.desktop.travelvalueindex.TravelValueIndexPage;
import com.hotwire.selenium.desktop.us.GlobalFooter;
import com.hotwire.test.steps.common.WebdriverAwareModel;

/**
 * @author vjong
 *
 */
public class PlanningToolsModelWebApp extends WebdriverAwareModel implements PlanningToolsModel {

    @Override
    public void gotoHotwireTravelSavingsIndicator() {
        new GlobalFooter(getWebdriverInstance())
            .navigateToPlanningToolsPage()
            .navigateToTravelSavingsIndicatorPage();
    }

    @Override
    public void verifyHotwireTravelSavingsIndicatorPage() {
        boolean tableDisplayed =
            new TravelSavingsIndicatorPage(getWebdriverInstance()).isSavingsIndicatorTableDisplayed();
        assertThat(tableDisplayed)
            .as("Expected savings indicator table to be displayed but it was not.")
            .isTrue();
    }

    @Override
    public void gotoTravelValueIndexPage() {
        new GlobalFooter(getWebdriverInstance())
            .navigateToPlanningToolsPage()
            .navigateToTravelValueIndexPage();
    }

    @Override
    public void verifyTravelValueIndexPage() {
        TravelValueIndexPage page = new TravelValueIndexPage(getWebdriverInstance());
        boolean allDisplayed =
            page.isTravelValueIndexCompDisplayed() && page.isFareFinderDisplayed();
        assertThat(allDisplayed)
            .as("Expected value index table and fare finder to be displayed, but one or both were not.")
            .isTrue();
    }

    @Override
    public void gotoGoLocalSearchPage() {
        new GlobalFooter(getWebdriverInstance())
            .navigateToPlanningToolsPage()
            .navigateToGoLocalSearchPage();
    }

}

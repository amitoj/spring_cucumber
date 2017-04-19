/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.golocalsearch;

import com.hotwire.selenium.desktop.us.golocalsearch.GoLocalSearchPage;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 1/30/14
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoLocalSearchModelWebApp extends GoLocalSearchModelTemplate {

    @Override
    public void launchSearch() {
        GoLocalSearchPage localSearchPage = new GoLocalSearchPage(getWebdriverInstance());
        localSearchPage.getFindButton().click();

    }

    @Override
    public void typeLocation(String location) {
        GoLocalSearchPage localSearchPage = new GoLocalSearchPage(getWebdriverInstance());
        localSearchPage.getLocation().clear();
        localSearchPage.getLocation().sendKeys(location);
    }

    @Override
    public void setDates(Date startDate, Date endDate) {
        GoLocalSearchPage localSearchPage = new GoLocalSearchPage(getWebdriverInstance());

        if (startDate != null) {
            localSearchPage.getStartDate().clear();
            localSearchPage.getStartDate().sendKeys((new SimpleDateFormat("MM/dd/yy")).format(startDate) + Keys.TAB);
        }

        if (endDate != null) {
            localSearchPage.getEndDate().clear();
            localSearchPage.getEndDate().sendKeys((new SimpleDateFormat("MM/dd/yy")).format(endDate) + Keys.TAB);
        }

    }
}

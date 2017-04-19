/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.planningtools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.travelsavingsindicator.TravelSavingsIndicatorPage;
import com.hotwire.selenium.desktop.travelvalueindex.TravelValueIndexPage;
import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.golocalsearch.GoLocalSearchPage;

/**
 * @author vjong
 *
 */
public class PlanningToolsPage extends AbstractUSPage {

    @FindBy(xpath = "//DIV[contains(@class, 'planningTool')]//A[contains(@href,'/travelsavingsindicator.jsp')]")
    private WebElement savingsIndicatorButton;

    @FindBy(xpath = "//DIV[contains(@class, 'planningTool')]//A[contains(@href,'/travel-value-index.jsp')]")
    private WebElement travelValueIndexButton;

    @FindBy(xpath = "//DIV[contains(@class, 'planningTool')]//A[contains(@href,'/local-trips.jsp')]")
    private WebElement goLocalSearchButton;

    public PlanningToolsPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.planning-tools");
    }

    public TravelSavingsIndicatorPage navigateToTravelSavingsIndicatorPage() {
        savingsIndicatorButton.click();
        return new TravelSavingsIndicatorPage(getWebDriver());
    }

    public TravelValueIndexPage navigateToTravelValueIndexPage() {
        travelValueIndexButton.click();
        return new TravelValueIndexPage(getWebDriver());
    }

    public GoLocalSearchPage navigateToGoLocalSearchPage() {
        goLocalSearchButton.click();
        return new GoLocalSearchPage(getWebDriver());
    }
}

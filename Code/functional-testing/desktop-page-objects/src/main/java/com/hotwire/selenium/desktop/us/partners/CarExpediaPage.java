/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: v-vzyryanov
 * Date: 12/7/12
 * Time: 5:07 AM
 */
public class CarExpediaPage extends AbstractDesktopPage implements CarPartnersPage {

    private static DateFormat DATE_FORMATTER_BY_TITLE = new SimpleDateFormat("EEEE, MM/dd/yyyy", Locale.UK);
    private static DateFormat DATE_FORMATTER_PLAYBACK = new SimpleDateFormat("MMM d", Locale.UK);

    /**
     * Expedia partner page has more that one type of design.
     * Returns search query which depends on current design.
     */

    @FindBy(css = "div.searchtitle")
    private WebElement searchParametersByTitleContainer;

    @FindBy(css = "div#playback-pane")
    private WebElement searchParametersPlaybackPaneContainer;

    public CarExpediaPage(WebDriver webdriver) {
        super(webdriver);

        new WebDriverWait(webdriver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div.searchtitle, div#playback-pane")));
    }

    @Override
    public NAME getName() {
        return NAME.EXPEDIA;
    }

    private String configureSearchQuery(String pickUp, String dropOff) {
        return QUERY_FORMAT.replaceAll("%PICKUPDATE%", pickUp)
                .replaceAll("%DROPOFFDATE%", dropOff)
                .replaceAll("( )+", " ").trim();
    }

    @Override
    public String getActualSearchQuery() {
        getWebDriver().manage().window().maximize();
        try {
            return searchParametersByTitleContainer.getText()
                .replaceAll("CHANGE SEARCH", " ")
                .replaceAll("( )+", " ")
                .trim();
        }
        catch (RuntimeException ex) { /* No op*/ }

        try {
            return searchParametersPlaybackPaneContainer
                    .getText()
                    .replaceAll("( )+", " ");
        }
        catch (RuntimeException ex) { /* No op*/ }

        return null;
    }

    @Override
    public String getExpectedSearchQuery(Date pickUp, Date dropOff) {
        try {
            searchParametersByTitleContainer.getText();
            return configureSearchQuery(
                    DATE_FORMATTER_BY_TITLE.format(pickUp) + " at 12:00 PM",
                    DATE_FORMATTER_BY_TITLE.format(dropOff) + " at 12:00 PM");
        }
        catch (RuntimeException ex) { /*No op*/ }

        try {
            searchParametersPlaybackPaneContainer.getText();
            return configureSearchQuery(
                    DATE_FORMATTER_PLAYBACK.format(pickUp),
                    DATE_FORMATTER_PLAYBACK.format(dropOff));
        }
        catch (RuntimeException ex) { /*No op*/ }

        return null;
    }

    @Override
    public void close() {
        getWebDriver().close();
    }
}

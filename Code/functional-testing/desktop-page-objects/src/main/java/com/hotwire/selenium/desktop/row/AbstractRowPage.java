/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.google.common.base.Function;
import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.util.webdriver.functions.InvisibilityOf;

/**
 * This base page for all pages on intl site
 *
 * @author Ivan Skrypka
 * @since 2012.05
 */
public class AbstractRowPage extends AbstractDesktopPage {
    public static final String DEFAULT_SEARCH_LAYER_ID =
        "[id='hotelResultsUpdatingLayer'], [id='carSearchingLayer'], [id='carUpdatingLayer']";

    public AbstractRowPage(WebDriver webdriver) {
        super(webdriver);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webdriver, String[] expectedPageNames) {
        super(webdriver, expectedPageNames);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webdriver, String[] expectedPageNames, String searchLayerId) {
        this(webdriver, expectedPageNames, searchLayerId, MAX_SEARCH_PAGE_WAIT_SECONDS);
    }

    public AbstractRowPage(WebDriver webdriver, String searchLayerId, int searchLayerMaxWait) {
        super(
            webdriver,
            new InvisibilityOf(
                By.cssSelector(searchLayerId),
                By.cssSelector(DEFAULT_PANEL_LOCATOR)),
            searchLayerMaxWait);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webdriver, String[] expectedPageNames, String searchLayerId,
                           int searchLayerMaxWait) {
        super(
                webdriver,
                expectedPageNames,
                new InvisibilityOf(
                        By.cssSelector(searchLayerId),
                        By.cssSelector(DEFAULT_PANEL_LOCATOR)),
                        searchLayerMaxWait);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webdriver, String expectedPageName) {
        super(webdriver, expectedPageName);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webdriver, String expectedPageName, String searchLayerId) {
        this(webdriver, new String[]{expectedPageName}, searchLayerId);
    }

    public AbstractRowPage(WebDriver webdriver, String expectedPageName, String searchLayerId, int searchLayerMaxWait) {
        this(webdriver, new String[] {expectedPageName}, searchLayerId, searchLayerMaxWait);
    }

    public AbstractRowPage(WebDriver webdriver, Function<WebDriver, ?> precondition) {
        super(webdriver, precondition);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webDriver, By containerLocator, Function<WebDriver, ?> precondition) {
        super(webDriver, containerLocator, precondition);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webdriver, String[] pageNames, ExpectedCondition<?>[] expectedConditions) {
        super(webdriver, pageNames, expectedConditions);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }

    public AbstractRowPage(WebDriver webdriver, String[] pageNames, ExpectedCondition<?>[] expectedConditions,
                           int maxExpectedConditionsWait) {
        super(webdriver, pageNames, expectedConditions, maxExpectedConditionsWait);
        PageFactory.initElements(new FieldWrapper(getWebDriver()), this);
    }
}

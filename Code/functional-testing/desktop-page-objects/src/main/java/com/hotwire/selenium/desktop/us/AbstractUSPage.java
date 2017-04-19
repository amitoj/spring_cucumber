/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us;

import com.google.common.base.Function;
import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 *
 */
public class AbstractUSPage extends AbstractDesktopPage {

    //wait for normal operation
    protected static final int DEFAULT_WAIT = 10;

    //wait for slow operation
    protected static final int EXTRA_WAIT = 20;

    public AbstractUSPage(WebDriver webDriver) {
        super(webDriver);
    }

    public AbstractUSPage(WebDriver webDriver, String searchLayerId, int waitInMillis) {
        super(webDriver,
                new InvisibilityOf(
                        By.cssSelector(searchLayerId),
                        By.cssSelector(DEFAULT_PANEL_LOCATOR)), waitInMillis);
    }

    public AbstractUSPage(WebDriver webDriver, By containerLocator, int waitInMillis) {
        super(webDriver, containerLocator);
        new WebDriverWait(getWebDriver(), waitInMillis)
                .until(PageObjectUtils.webElementVisibleTestFunction(containerLocator, true));
    }

    public AbstractUSPage(WebDriver webDriver, String[] expectedPageNames) {
        super(webDriver, expectedPageNames);
    }

    public AbstractUSPage(WebDriver webdriver, String[] expectedPageNames, String searchLayerId) {
        this(webdriver, expectedPageNames, searchLayerId, MAX_SEARCH_PAGE_WAIT_SECONDS);
    }

    public AbstractUSPage(WebDriver webDriver, String[] expectedPageNames, String searchLayerId, int maxWaitSecs) {
        super(webDriver,
                expectedPageNames,
                new InvisibilityOf(
                        By.cssSelector(searchLayerId),
                        By.cssSelector(DEFAULT_PANEL_LOCATOR)), maxWaitSecs);
    }

    public AbstractUSPage(WebDriver webdriver, String[] expectedPageNames, ExpectedCondition<?> expectedCondtion) {
        super(webdriver, expectedPageNames, expectedCondtion);
    }

    public AbstractUSPage(WebDriver webdriver, String[] expectedPageNames, ExpectedCondition<?> expectedCondtion,
                          int maxWaitSecs) {
        super(webdriver, expectedPageNames, expectedCondtion, maxWaitSecs);
    }

    public AbstractUSPage(WebDriver webDriver, String expectedPageName) {
        super(webDriver, new String[]{expectedPageName});
    }

    public AbstractUSPage(WebDriver webdriver, String expectedPageName, String searchLayerId) {
        this(webdriver, expectedPageName, searchLayerId, MAX_SEARCH_PAGE_WAIT_SECONDS);
    }

    public AbstractUSPage(WebDriver webdriver, String expectedPageName, String searchLayerId, int maxWaitSecs) {
        this(webdriver, new String[]{expectedPageName}, searchLayerId, maxWaitSecs);
    }

    public AbstractUSPage(WebDriver webdriver, String expectedPageName, ExpectedCondition<?> expectedCondtion) {
        super(webdriver, expectedPageName, expectedCondtion);
    }

    public AbstractUSPage(WebDriver webdriver, String expectedPageName, ExpectedCondition<?> expectedCondtion,
                          int maxWaitSecs) {
        super(webdriver, expectedPageName, expectedCondtion, maxWaitSecs);
    }

    public AbstractUSPage(WebDriver webDriver, Function<WebDriver, ?> precondition) {
        super(webDriver, precondition);
    }

    public AbstractUSPage(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
    }

    public AbstractUSPage(WebDriver webDriver, WebElement container) {
        super(webDriver, container);
    }

    public AbstractUSPage(WebDriver webDriver, By containerLocator, Function<WebDriver, ?> precondition) {
        super(webDriver, containerLocator, precondition);
    }

    public AbstractUSPage(WebDriver webdriver, String[] pageNames, ExpectedCondition<?>[] expectedConditions) {
        super(webdriver, pageNames, expectedConditions);
    }

    public AbstractUSPage(WebDriver webdriver, String[] pageNames, ExpectedCondition<?>[] expectedConditions,
                               int maxExpectedConditionsWait) {
        super(webdriver, pageNames, expectedConditions, maxExpectedConditionsWait);
    }
}

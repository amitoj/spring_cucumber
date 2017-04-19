/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop;

import java.util.NoSuchElementException;

import com.google.common.base.Function;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.util.webdriver.po.AbstractPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

import static org.ehoffman.testing.fest.webdriver.WebElementAssert.assertThat;

/**
 * User: jreichenberg
 * Date: 7/17/12
 * Time: 10:25 AM
 */
public class AbstractDesktopPage extends AbstractPage {

    public static final String DEFAULT_SEARCH_LAYER_ID = "#searchUpdatingLayer";
    public static final int MAX_SEARCH_PAGE_WAIT_SECONDS = 90;

    protected static final String DEFAULT_UPDATING_LAYER =
            "[id='resultsUpdatingLayer'], " +
                    "[id='updatingLayer'], " +
                    "[id='searchUpdatingLayer']";
    protected static final String DEFAULT_PANEL_LOCATOR = ".yui-panel, .loadingMask";

    @FindBy(css = "div.errorMessages ul li")
    private WebElement errorMessage;

    @FindBy(css = "#tileName-A3 h1")
    private WebElement pageHeader;

    public AbstractDesktopPage(WebDriver webdriver) {
        super(webdriver);
    }

    public AbstractDesktopPage(WebDriver webdriver, String[] expectedPageNames) {
        super(webdriver, expectedPageNames);
    }

    public AbstractDesktopPage(
            WebDriver webdriver,
            String[] expectedPageNames,
            ExpectedCondition<?> expectedCondition) {
        super(webdriver, expectedPageNames, expectedCondition);
    }

    public AbstractDesktopPage(WebDriver webdriver, String[] expectedPageNames, ExpectedCondition<?> expectedCondition,
                               int expectedConditionMaxWait) {
        super(webdriver, expectedPageNames, expectedCondition, expectedConditionMaxWait);
    }

    public AbstractDesktopPage(WebDriver webdriver, String expectedPageName) {
        super(webdriver, expectedPageName);
    }

    public AbstractDesktopPage(WebDriver webdriver, String expectedPageName, ExpectedCondition<?> expectedCondition) {
        super(webdriver, expectedPageName, expectedCondition);
    }

    public AbstractDesktopPage(WebDriver webdriver, String expectedPageName, ExpectedCondition<?> expectedCondition,
                               int expectedConditionMaxWait) {
        super(webdriver, expectedPageName, expectedCondition, expectedConditionMaxWait);
    }

    public AbstractDesktopPage(WebDriver webdriver, Function<WebDriver, ?> precondition) {
        super(webdriver, precondition);
    }

    public AbstractDesktopPage(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
    }

    public AbstractDesktopPage(WebDriver webDriver, WebElement container) {
        super(webDriver, container);
    }

    public AbstractDesktopPage(WebDriver webdriver, ExpectedCondition<?> expectedCondition, int expectedConditionWait) {
        super(webdriver, expectedCondition, expectedConditionWait);
    }

    public AbstractDesktopPage(WebDriver webDriver, By containerLocator, Function<WebDriver, ?> precondition) {
        super(webDriver, containerLocator, precondition);
    }

    public AbstractDesktopPage(WebDriver webdriver, String[] pageNames, ExpectedCondition<?>[] expectedConditions) {
        super(webdriver, pageNames, expectedConditions);
    }

    public AbstractDesktopPage(WebDriver webdriver, String[] pageNames, ExpectedCondition<?>[] expectedConditions,
                               int maxExpectedConditionsWait) {
        super(webdriver, pageNames, expectedConditions, maxExpectedConditionsWait);
    }

    public void waitForDoneFiltering() {
        waitForDoneFiltering(MAX_SEARCH_PAGE_WAIT_SECONDS);
    }

    public void waitForDoneFiltering(int maxWaitSeconds) {
        waitForMaskingLayerToClose(By.cssSelector(DEFAULT_UPDATING_LAYER), maxWaitSeconds);
    }

    public void waitForMaskingLayerToClose(By layerLocator, int maxWaitSeconds) {
        waitForMaskingLayerToClose(layerLocator, By.cssSelector(DEFAULT_PANEL_LOCATOR), maxWaitSeconds,
                true /* panel locator is sub-element of layerLocator */);
    }

    /**
     * Wait for a masking layer to close and not be visible. Only to be used when the load is not a page load, such as
     * JavaScript updating. Real page loads should not use this.
     *
     * @param layerLocator      The masking layer container
     * @param panelLocator      The panel locator that may be contained by the masking layer
     * @param maxWaitSeconds    The max time in seconds to wait
     * @param panelIsSubElement Determines if the panelLocator is
     *                          contained in the layerLocator in order to do the correct Wait.
     */
    public void waitForMaskingLayerToClose(By layerLocator, By panelLocator, int maxWaitSeconds,
                                           boolean panelIsSubElement) {
        new WebDriverWait(getWebDriver(), 2)
                .until(PageObjectUtils.webElementVisibleTestFunction(layerLocator, true));
        if (panelIsSubElement) {
            new WebDriverWait(getWebDriver(), maxWaitSeconds)
                    .until(PageObjectUtils.webElementVisibleTestFunction(layerLocator, panelLocator, false));
        }
        else {
            // do wait on panelLocator by itself.
            new WebDriverWait(getWebDriver(), maxWaitSeconds)
                    .until(PageObjectUtils.webElementVisibleTestFunction(panelLocator, false));
        }
    }

    /**
     * Does this page have a POS selector. Dirty way of determining if on Intl page.
     */
    public boolean currentlyOnDomesticSite() {
        boolean hasNavigationTabs = new GlobalHeader(getWebDriver()).hasVerticalNavigationTabs();
        logger.info("Has naviagation tabs: " + hasNavigationTabs);
        return hasNavigationTabs;
    }

    public GlobalHeader getGlobalHeader() {
        return new GlobalHeader(getWebDriver());
    }

    public String getErrorMessages() {
        return errorMessage.getText();
    }

    public boolean assertHasFormErrors(String message) {
        try {
            assertThat(errorMessage).isNotHidden().isDisplayed().textContains(message);
            // TO DO verify fields are red, and are email or password fields
            return true;
        }
        catch (AssertionError error) {
            error.printStackTrace();
            return false;
        }
    }

    public String getPageHeader() {
        return pageHeader.getText().trim();
    }

    public boolean isTextDisplayedInPage(String textInPage) {
        try {
            return getWebDriver().findElement(By.xpath("//*[contains(text(),'" + textInPage + "')]")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

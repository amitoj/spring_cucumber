/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.widget;

import org.apache.commons.lang3.StringUtils;
import org.fest.assertions.Fail;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Encapsulates either native drop-down select or jQuery UI custom drop-down widget.
 *
 * @author v-vzyryanov
 * @since 2012.17
 */
public class AutoComplete {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoComplete.class);

    private static final By AUTOCOMPLETE_CANDIDATES = By.xpath("..//li//div");
    private static final int AUTOCOMPLETE_DISPLAY_TIMEOUT_SECONDS = 5;
    private static final int AUTOCOMPLETE_POLLING_TIMEOUT_MS = 100;
    private static final int TIMEOUT = 5;
    private final WebElement nativeDropDown;
    private final WebDriver webDriver;

    public AutoComplete(WebDriver webdriver, WebElement nativeDropDown) {
        this.webDriver = webdriver;
        this.nativeDropDown = nativeDropDown;
        new WebDriverWait(webDriver, TIMEOUT).until(ExpectedConditions.visibilityOf(nativeDropDown));
    }

    /**
     * Select item from drop-down by its visible text
     */
    public void select(String itemText, int subStringLength) {

        LOGGER.info("Searching for {}", itemText);
        if (StringUtils.isEmpty(itemText)) {
            return;
        }

        String startString = (itemText.length() > subStringLength) ? itemText.substring(0, subStringLength) : itemText;

        nativeDropDown.clear();
        nativeDropDown.sendKeys(startString);
        nativeDropDown.sendKeys("");

        LOGGER.info("Enter search query: {}", startString);

        WebElement match;
        List<WebElement> candidates = getCandidates();

        if (candidates != null) {
            match = searchFullMatch(itemText, candidates);
            if (match != null) {
                try {
                    match.click();
                }
                catch (ElementNotVisibleException e) {
                    LOGGER.info("Focus lost on full match..");
                    typeLocationManually(itemText);
                }
                focusOut();
                return;
            }
            match = searchPartialMatch(itemText, candidates);
            if (match != null) {
                try {
                    match.click();
                }
                catch (ElementNotVisibleException e) {
                    LOGGER.info("Focus lost on partial match..");
                    typeLocationManually(itemText);
                }
                focusOut();
                return;
            }
        }
        LOGGER.info("Corresponding location did not find..");
        typeLocationManually(itemText);
        focusOut();
    }

    private void focusOut() {
        nativeDropDown.sendKeys(Keys.TAB);
    }

    private void typeLocationManually(String itemText) {
        nativeDropDown.clear();
        nativeDropDown.sendKeys(itemText);
        LOGGER.info("Typing full location text: {}", itemText);
    }

    private WebElement searchFullMatch(String itemText, List<WebElement> candidates) {
        if (candidates != null && !StringUtils.isEmpty(itemText)) {
            for (WebElement elm : candidates) {
                if (itemText.equals(elm.getText())) {
                    LOGGER.info("\"{}\" is selected..", elm.getText());
                    return elm;
                }
            }
        }
        return null;
    }

    private WebElement searchPartialMatch(String itemText, List<WebElement> candidates) {
        if (candidates != null && !StringUtils.isEmpty(itemText)) {
            for (WebElement elm : candidates) {
                if (elm.getText().contains(itemText)) {
                    LOGGER.info("\"{}\" is selected..", elm.getText());
                    return elm;
                }
            }
        }
        return null;
    }

    String getName() {
        return nativeDropDown.getAttribute("name");
    }

    WebElement getCandidateContainer() {
        return nativeDropDown.findElement(
                By.xpath("..//div[@class='yui-ac-container']//div[@class='yui-ac-content']//ul"));
    }

    List<WebElement> getCandidates() {
        try {

            WebElement container = getCandidateContainer();

            new WebDriverWait(webDriver, AUTOCOMPLETE_DISPLAY_TIMEOUT_SECONDS, AUTOCOMPLETE_POLLING_TIMEOUT_MS)
                    .until(ExpectedConditions.visibilityOf(container));

            List<WebElement> out = container.findElements(AUTOCOMPLETE_CANDIDATES);
            LOGGER.info("candidates: {}", out.size());

            int total = 0;
            for (WebElement elm : out) {
                String innerText = elm.getText();
                if (StringUtils.isNoneBlank(innerText)) {
                    LOGGER.info("{}", innerText);
                    total++;
                }
            }

            LOGGER.info("Found {} candidates..", total);

            if (total > 0) {
                return out;
            }
        }
        catch (NoSuchElementException | TimeoutException e) {
            LOGGER.info("Autocomplete layout didn't appear..");
        }
        return null;
    }

    public void selectFirstResult(String startString) {
        nativeDropDown.clear();
        nativeDropDown.sendKeys(startString);

        LOGGER.info("Enter search query: {}", startString);

        List<WebElement> candidates = getCandidates();

        if (candidates == null) {
            Fail.fail("autocomplete was not shown");
        }

        try {
            candidates.get(0).click();
            LOGGER.info("Found {} candidates..", candidates.get(0).getText());
        }
        catch (Exception e) {
            LOGGER.info("Found no candidates, " + e);
        }
    }
}

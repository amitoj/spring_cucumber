/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.hotel.fragments.filters;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * HotelResultsFilteringTabsPanelFragment
 */
public abstract class HotelResultsFilteringTabsPanelFragment extends AbstractPageObject {

    protected static final int RECOMMENDED_RADIO_FILTER_INDEX = 3;
    protected static final int DEFAULT_WAIT = 10;

    private static final String FILTERING_RADIO_BUTTONS = ".//input[@type='radio']";
    private static final String FILTERING_CHECKBOXES = ".//input[@type='checkbox']";
    private static final String NEXT_FILTER_FRAGMENT = " .nextFilter a";
    private static final String SUBMIT_BUTTON = ".//button[@type='submit']";

    @FindBy(xpath = FILTERING_CHECKBOXES)
    protected List<WebElement> filteringCheckboxes;

    @FindBy(xpath = FILTERING_RADIO_BUTTONS)
    private List<WebElement> filteringRadios;

    @FindBy(css = NEXT_FILTER_FRAGMENT)
    private WebElement nextFilterFragment;

    @FindBy(xpath = SUBMIT_BUTTON)
    private WebElement lastFilterFragmentSubmitButton;

    HotelResultsFilteringTabsPanelFragment(WebDriver webDriver, By container) {
        super(webDriver, container);
    }

    protected WebElement getRecommendedRadioWebElement() {
        return filteringRadios.get(RECOMMENDED_RADIO_FILTER_INDEX);
    }

    protected WebElement getFirstFilteringCheckboxElement() {
        return filteringCheckboxes.get(0);
    }

    public void checkFirstFilteringCheckbox() {
        WebElement checkbox = getFirstFilteringCheckboxElement();
        if (!checkbox.isSelected()) {
            checkbox.click();
            doWaitForUpdatingLayer();
        }
    }

    public void uncheckFirstFilteringCheckbox() {
        WebElement checkbox = getFirstFilteringCheckboxElement();
        if (checkbox.isSelected()) {
            checkbox.click();
            doWaitForUpdatingLayer();
        }
    }

    public void selectRecommendedRadioButton() {
        getRecommendedRadioWebElement().click();
        doWaitForUpdatingLayer();
    }

    public void submitFilteringChoice() {
        lastFilterFragmentSubmitButton.click();
    }

    public void doWaitForUpdatingLayer() {
        By locator = By.cssSelector("div[id='updatingLayer'] .loadingMask");
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT + 10)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}

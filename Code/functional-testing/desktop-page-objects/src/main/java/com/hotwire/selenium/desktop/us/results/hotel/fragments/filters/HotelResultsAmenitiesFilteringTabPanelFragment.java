/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.hotel.fragments.filters;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class HotelResultsAmenitiesFilteringTabPanelFragment extends HotelResultsFilteringTabsPanelFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelResultsAmenitiesFilteringTabPanelFragment.class);

    private static final String FRAGMENT_CONTAINER = "filter-amenity";
    private static final String AMENITIES_CHECKBOXES = ".//li[contains(@class, 'amenity')]/input[@type='checkbox']";
    private static final String FREE_AMENITIES_CHECKBOXES = ".//li[contains(@class, " +
            "'freebie')]/input[@type='checkbox']";
    private static final String ACCESSIBILITY_AMENITIES_CHECKBOXES = ".//li[contains(@class, " +
            "'accessibilityList')]/input[@type='checkbox']";
    private static final String SHOW_HIDE_ACCESSIBILITY_AMENITIES = ".//li[contains(@class, 'amenitiesAccess')]";
    private static final int DEFAULT_WAIT = 10;

    @FindBy(xpath = AMENITIES_CHECKBOXES)
    private List<WebElement> allNonAccessibleAmenities;

    @FindBy(xpath = FREE_AMENITIES_CHECKBOXES)
    private List<WebElement> freeAmenities;

    @FindBy(xpath = ACCESSIBILITY_AMENITIES_CHECKBOXES)
    private List<WebElement> accessibilityAmenities;

    @FindBy(xpath = SHOW_HIDE_ACCESSIBILITY_AMENITIES)
    private WebElement showHideAccessibilityLink;

    public HotelResultsAmenitiesFilteringTabPanelFragment(WebDriver webDriver) {
        super(webDriver, By.className(FRAGMENT_CONTAINER));
    }

    @Override
    public WebElement getFirstFilteringCheckboxElement() {
        return allNonAccessibleAmenities.get(0);
    }

    public WebElement getFirstFreeAmenityCheckboxElement() {
        return freeAmenities.get(0);
    }

    public WebElement getFirstAccessibilityAmenityCheckboxElement() {
        return accessibilityAmenities.get(0);
    }

    public void checkFirstFreeAmenityCheckbox() {
        WebElement element = getFirstFreeAmenityCheckboxElement();
        if (!element.isSelected()) {
            element.click();
            doWaitForUpdatingLayer();
        }
    }

    @Override
    public void checkFirstFilteringCheckbox() {
        for (WebElement element : filteringCheckboxes) {
            if (element.getAttribute("disabled") == null && !element.isSelected()) {
                element.click();
                doWaitForUpdatingLayer();
                break;
            }
        }
    }

    public void uncheckFirstFreeAmenityCheckbox() {
        WebElement element = getFirstFreeAmenityCheckboxElement();
        if (element.isSelected()) {
            element.click();
            doWaitForUpdatingLayer();
        }
    }

    public void checkAmenityByName(String amenityName) {
        int i = 1;
        for (WebElement element : allNonAccessibleAmenities) {
            LOGGER.info(">>>>> " + (i++) + ": " + element.getAttribute("id"));
            if (element.getAttribute("amenityname").equals(amenityName) && !element.isSelected()) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getWebDriver();
                javascriptExecutor.executeScript("$(arguments[0]).focus()", element);
                element.click();
                doWaitForUpdatingLayer();
                break;
            }
        }
    }

    public void uncheckAmenityByName(String amenityName) {
        for (WebElement element : allNonAccessibleAmenities) {
            if (element.getAttribute("amenityname").equals(amenityName) && element.isSelected()) {
                element.click();
                doWaitForUpdatingLayer();
                break;
            }
        }
    }

    public void checkFirstAccessibilityAmenityCheckbox() {
        WebElement element = getFirstAccessibilityAmenityCheckboxElement();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT * 2)
                .until(new IsElementLocationStable(getWebDriver(), By.xpath(ACCESSIBILITY_AMENITIES_CHECKBOXES)));
        if (!element.isSelected()) {
            element.click();
            doWaitForUpdatingLayer();
        }
    }

    public void uncheckFirstAccessibilityAmenityCheckbox() {
        WebElement element = getFirstAccessibilityAmenityCheckboxElement();
        if (element.isSelected()) {
            element.click();
            doWaitForUpdatingLayer();
        }
    }

    public boolean isShowHideAccessibilityAmenitiesLinkDisplayed() {
        try {
            return showHideAccessibilityLink.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void showAccessibilityAmenities() {
        if (!showHideAccessibilityLink.getAttribute("class").contains("open")) {
            showHideAccessibilityLink.click();
        }
        // Wait for slow animation of accessibility options to show by waiting for the first element in the
        // accessibility list to appear.
        new Wait<Boolean>(new VisibilityOf(getFirstAccessibilityAmenityCheckboxElement()))
                .maxWait(DEFAULT_WAIT).apply(getWebDriver());
    }

    public void hideAccessibilityAmenities() {
        if (showHideAccessibilityLink.getAttribute("class").contains("open")) {
            showHideAccessibilityLink.click();
        }
        // Wait for slow animation of accessibility options to show by waiting for the last element in the
        // accessibility list to disappear.
        new Wait<Boolean>(new InvisibilityOf(accessibilityAmenities.get(accessibilityAmenities.size() - 1)))
                .maxWait(DEFAULT_WAIT).apply(getWebDriver());
    }
}

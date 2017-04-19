/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.hotel.fragments.filters;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HotelResultsFilteringTabsFragment extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelResultsFilteringTabsFragment.class);
    private static final String TABS_CONTAINER = "tileName-filters";
    private static final String TAB_SELECTION_CONTAINER = ".//ul[contains(@class, 'ui-tabs-nav')]";
    private static final String RESET_FILTERS = " .clearFilter .filteredOut, .clearFilter a";

    @FindBy(xpath = TAB_SELECTION_CONTAINER)
    private WebElement tabContainer;

    @FindBy(css = RESET_FILTERS)
    private WebElement resetFilters;

    @FindBy(css = "#tabContainer ul li[role='tab'] a[role='presentation']")
    private List<WebElement> tabs;

    public HotelResultsFilteringTabsFragment(WebDriver webDriver) {
        super(webDriver, By.id(TABS_CONTAINER));
    }

    public void selectTabByTabText(String tabName) {
        for (WebElement aTag : tabs) {
            if (aTag.getText().trim().equals(tabName)) {
                LOGGER.info("Click on {} tab", tabName);
                aTag.click();
                break;
            }
        }
    }

    public void clickResetFilters() {
        resetFilters.click();

        By locator = By.cssSelector("div[id='updatingLayer'] .loadingMask");
        new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean isResetFiltersDisplayed() {
        try {
            return resetFilters.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

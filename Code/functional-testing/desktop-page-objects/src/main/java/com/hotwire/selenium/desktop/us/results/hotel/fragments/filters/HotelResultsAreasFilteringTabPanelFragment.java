/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results.hotel.fragments.filters;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.testing.ZeroResultsTestException;

public class HotelResultsAreasFilteringTabPanelFragment extends HotelResultsFilteringTabsPanelFragment {
    private static final String FRAGMENT_CONTAINER = "filter-area";
    private static final String CHECK_ALL = "checkAll";
    private static final String AREAS_CHECKBOXES = "selectedNeighborhoodIds";
    private static final String AREAS_CHECKBOXES_LIST = " .area";

    @FindBy(name = CHECK_ALL)
    private WebElement checkAll;

    // The actual checkbox elements
    @FindBy(name = AREAS_CHECKBOXES)
    private List<WebElement> areasCheckboxes;

    // The list items containing the checkbox elements and labels.
    @FindBy(css = AREAS_CHECKBOXES_LIST)
    private List<WebElement> areaCheckboxesList;

    public HotelResultsAreasFilteringTabPanelFragment(WebDriver webDriver) {
        super(webDriver, By.className(FRAGMENT_CONTAINER));
    }

    @Override
    protected WebElement getFirstFilteringCheckboxElement() {
        return areasCheckboxes.get(getIndexOfFirstEnabledCheckbox());
    }

    public void checkSelectAllAreas() {
        if (!checkAll.isSelected()) {
            checkAll.click();
            doWaitForUpdatingLayer();
        }
    }

    public void uncheckSelectAllAreas() {
        if (checkAll.isSelected()) {
            checkAll.click();
            doWaitForUpdatingLayer();
        }
    }

    public boolean isAllAreasCheckboxSelected() {
        try {
            return checkAll.isSelected();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getLabelForFirstAreaCheckbox() {
        return areaCheckboxesList.get(getIndexOfFirstEnabledCheckbox()).findElement(By.tagName("label")).getText();
    }

    public int getIndexOfFirstEnabledCheckbox() {
        for (int i = 0; i < areasCheckboxes.size(); i++) {
            if (areasCheckboxes.get(i).isEnabled()) {
                return i;
            }
        }
        throw new ZeroResultsTestException("0 areas filtering options.");
    }
}

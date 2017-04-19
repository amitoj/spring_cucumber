/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results.hotel.fragments.filters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HotelResultsStarRatingFilteringTabPanelFragment extends HotelResultsFilteringTabsPanelFragment {
    private static final String FRAGMENT_CONTAINER = "filter-star";
    private static final String STAR_RATING_CHECKBOXES = "[name='selectedStarRatingList']";

    @FindBy(css = STAR_RATING_CHECKBOXES)
    private List<WebElement> filteringRadios;

    public HotelResultsStarRatingFilteringTabPanelFragment(WebDriver webDriver) {
        super(webDriver, By.className(FRAGMENT_CONTAINER));
    }

    @Override
    protected WebElement getRecommendedRadioWebElement() {
        return filteringRadios.get(RECOMMENDED_RADIO_FILTER_INDEX - 1);
    }
}

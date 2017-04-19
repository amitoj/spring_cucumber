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

public class HotelResultsPriceFilteringTabPanelFragement extends HotelResultsFilteringTabsPanelFragment {
    private static final String FRAGMENT_CONTAINER = "filter-price";
    private static final String PRICE_SELECTION_RADIOS = "selectedPriceMax";

    @FindBy(name = PRICE_SELECTION_RADIOS)
    private List<WebElement> filteringRadios;

    public HotelResultsPriceFilteringTabPanelFragement(WebDriver webDriver) {
        super(webDriver, By.className(FRAGMENT_CONTAINER));
    }

    @Override
    protected WebElement getRecommendedRadioWebElement() {
        return filteringRadios.get(RECOMMENDED_RADIO_FILTER_INDEX);
    }
}

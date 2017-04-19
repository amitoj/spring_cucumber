/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.hotel.fragments.farefinder;

import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by IntelliJ IDEA.
 * User: vjong
 * Date: Aug 13, 2012
 * Time: 9:53:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelResultsFareFinderFragment extends HotelSearchFragment {
    private static final String FARE_FINDER_CONTAINER = "collapsibleFareFinderContainer";

    public HotelResultsFareFinderFragment(WebDriver webdriver) {
        super(webdriver, By.id(FARE_FINDER_CONTAINER));
    }

    public HotelSearchFragment findHotelFare() {
        HotelSearchFragment fragment = new HotelSearchFragment(getWebDriver(), By.id(FARE_FINDER_CONTAINER));
        if (fragment.isEditSearchLinkDisplayed() && !fragment.isDestinationLocationDisplayed()) {
            fragment.getEditSearch().click();
        }
        return fragment;
    }
}

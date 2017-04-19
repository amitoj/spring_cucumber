/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 3/14/14
 * Time: 1:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3IntlHotelSearchFragment extends C3HotelSearchFragment {
    private static final Logger LOGGER = LoggerFactory.getLogger(C3HotelSearchFragment.class.getName());

    public C3IntlHotelSearchFragment(WebDriver webDriver) {
        super(webDriver, By.id("homeFareFinder"));
        LOGGER.info("INTL hotel purchase");
        setSearchForm("form#homeFareFinder");
        setDateFormat(INTL_DATE_FORMAT);
    }

    @Override
    public void setDestination(String fromDestination) {
        setText("input#location", fromDestination);
    }
}

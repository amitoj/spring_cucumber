/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.hotel.search;

import com.hotwire.selenium.bex.hotel.results.HotelResultsPage;
import com.hotwire.selenium.bex.hotel.search.HotelSearchPage;
import com.hotwire.test.steps.bex.BexAbstractModel;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/5/15
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelSearchModelWebApp extends BexAbstractModel {

    public void selectNearDates() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(getWebdriverInstance());
        hotelSearchPage = new HotelSearchPage(getWebdriverInstance());
        hotelSearchPage.selectProxDates();

    }

    public void selectFarDates() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(getWebdriverInstance());
        hotelSearchPage = new HotelSearchPage(getWebdriverInstance());
        hotelSearchPage.selectFarDates();

    }

    public void searchForHotelNearCity(String city) {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(getWebdriverInstance());
        hotelSearchPage.searchForHotelsInCity(city);
    }


    public void selectRandomHotelFromList() {
        HotelResultsPage hotelResultsPage = new HotelResultsPage(getWebdriverInstance());
        hotelResultsPage.selectRandomHotel();

    }

}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 2/1/13
 * Time: 6:36 AM
 */
public interface CarDetails {

    /**
     * @return list of available vendors as array of string
     *         {vendor, dailyPrice, totalPrice, currency}
     */
    List<String[]> getVendorGridEntities();

    /**
     * Clicking on vendor's offer with specified params
     */
    void selectVendorEntityBy(String vendor, Float perDayPrice, Float totalPrice);

    /**
     * @return selected vendor's offer
     */
    String[] getSelectedVendorGridEntity();

    void continuePanel();

    /**
     * Drops user back to car details page.
     * Was implemented specially for retail cars to select different vendor's offers.
     */
    void goBack();

    /**
     * @return car options from car's details block(page)
     */
    CarSolutionModel getCarOptionsSet();

    void priceCheckAfterContinue();

    List<String> getCarFeatureList();

    boolean localSearchMapIsDisplayed();

    void clickOnVendorAddressLink();

    void closeVendorMap();

    int getVendorMapNum();

    boolean airportResultVendorGridIsDisplayed();

    List<String> getFootNoteList();
}

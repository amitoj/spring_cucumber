/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 2/6/13
 * Time: 2:56 AM
 */
public interface CarPurchaseReview {

    CarSolutionModel getCarOptionsSet();

    boolean isPrepaidSolution();
    void readTerms();
    void readRules();

    List<String> getGuarantees();
    boolean isImgWithLogosDisplayed();

    /**
     * @return String[] of rules and regulations from "Agree and Book"
     * block at bottom of the car billing page.
     */
    List<String> getRulesAndRegulations();
    void confirmBookingAgreements(boolean confirm);
    void book();

    /**
     * Confirm PayPal booking on car details page
     */
    void bookPayPal();

    String getPickUpLocation();
    String getDropOffLocation();
    String getPickUpDate();
    String getDropOffDate();
}




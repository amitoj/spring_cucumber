/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl;

import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 5:24 AM
 */
public interface CarAdditionalFeatures {

    public enum InsuranceType {
        DYNAMIC, STATIC, UNAVAILABLE
    }

    /**
     * @return type of insurance block on billing page
     */
    InsuranceType getInsuranceType();

    List<WebElement> getTripInsuranceRadioFields();
    void requestInsurance(boolean reqInsurance);
    boolean isInsuranceUnavailable();
    boolean isInsuranceSelected();
    void continuePanel();
    WebElement getInsuranceCost();
}

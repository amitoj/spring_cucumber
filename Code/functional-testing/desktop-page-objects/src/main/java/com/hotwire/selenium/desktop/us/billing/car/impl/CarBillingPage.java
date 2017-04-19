/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl;

import com.hotwire.testing.UnimplementedTestException;
import org.openqa.selenium.WebDriver;

import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcDetailsFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcPaymentMethodFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcPurchaseReviewFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.ccf.CcfAddFeaturesFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.ccf.CcfDetailsFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.ccf.CcfPaymentMethodFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.ccf.CcfPurchaseReviewFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.onepage.OpAddFeaturesFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.onepage.OpDetailsFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.onepage.OpPaymentMethodFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.onepage.OpPurchaseReviewFragment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: v-vzyryanov
 * Date: 1/25/13
 * Time: 6:54 AM
 */
public class CarBillingPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBillingPage.class);
    /**
     * This enum define Hotwire Car page versions
     */
    public static enum Type { NONE, ACCORDION, ONEPAGE, CCF }
    private Type implementType = Type.NONE;
    private WebDriver webDriver;

    public CarBillingPage(WebDriver webDriver, Type implType) {
        this.webDriver = webDriver;
        implementType = implType;
        LOGGER.info(implementType + " design of car billing page is activated..");
    }

    public CarTravelerInfo getTravelerInfoFragment() {
        switch (implementType) {
            case CCF:
                throw new UnimplementedTestException("Car Traveler Info Fragment is not implemented for CCF case. " +
                        "ImplementType = " + implementType);
            default:
                return new CarTravelerInfoFragment(getWebDriver());
        }
    }

    public CarAdditionalFeatures getAddFeaturesFragment() {
        switch (implementType) {
            case CCF:
                return  new CcfAddFeaturesFragment(getWebDriver());
            default:
                return  new OpAddFeaturesFragment(getWebDriver());
        }
    }

    public CarPaymentMethod getPaymentMethodFragment() {
        switch (implementType) {
            case ONEPAGE:
                return  new OpPaymentMethodFragment(getWebDriver());
            case CCF:
                return  new CcfPaymentMethodFragment(getWebDriver());
            default:
                return new AcPaymentMethodFragment(getWebDriver());
        }
    }

    public CarDetails getCarDetailsPage() {
        switch (implementType) {
            case ONEPAGE:
                return  new OpDetailsFragment(getWebDriver());
            case CCF:
                return  new CcfDetailsFragment(getWebDriver());
            default:
                return new AcDetailsFragment(getWebDriver());
        }
    }

    public CarPurchaseReview getCarPurchaseReviewFragment() {
        switch (implementType) {
            case ONEPAGE:
                return  new OpPurchaseReviewFragment(getWebDriver());
            case CCF:
                return  new CcfPurchaseReviewFragment(getWebDriver());
            default:
                return new AcPurchaseReviewFragment(getWebDriver());
        }
    }

    public boolean isPrepaidSolution() {
        return getCarPurchaseReviewFragment().isPrepaidSolution();
    }

    public void selectAsUser(String username, String password) {
        getTravelerInfoFragment().login(username, password);
    }

    public void selectAsGuest() {
        getTravelerInfoFragment().continueGuest();
    }

    public void book(boolean acceptAgreements) {
        CarPurchaseReview cpr = getCarPurchaseReviewFragment();
        cpr.confirmBookingAgreements(acceptAgreements);
        cpr.book();
    }

    public void bookPayPal(boolean acceptAgreements) {
        CarPurchaseReview cpr = getCarPurchaseReviewFragment();
        cpr.confirmBookingAgreements(acceptAgreements);
        cpr.bookPayPal();
    }

    private WebDriver getWebDriver() {
        return webDriver;
    }
}




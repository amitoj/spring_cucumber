/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.application;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * Session cotrol state parameters
 *
*/

public class SessionParameters {

    private static final Map<String, String> MOBILE_POINT_OF_SALE = ImmutableMap.<String, String>builder()
        .put("United Kingdom", "uk")
        .put("Deutschland", "de")
        .put("Ireland", "ie")
        .put("Sverige", "se")
        .put("Norge", "no")
        .put("Danmark", "dk")
        .put("Australia", "au")
        .put("New Zealand", "nz")
        .put("Singapore", "sg")
        .put("México", "mx")
        .put("Hong Kong", "hk").build();

    private String currencyCode;
    private String pointOfSale;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPointOfSale() {
        if (this.pointOfSale == null) {
            return "United States";
        }
        else {
            return this.pointOfSale;
        }
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public void setMobilePointOfSale(String pointOfSale) {
        this.pointOfSale = MOBILE_POINT_OF_SALE.get(pointOfSale);
    }

}

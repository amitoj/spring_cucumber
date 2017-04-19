/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.hotel;

/**
 * @author vjong
 * @since Aug 6, 2012
 */
public enum HotelResultsSortCriteria {
    PRICE("Price"),
    STAR("Star rating"),
    RECOMMENDED("Recommended"),
    DISTANCE("Distance"),
    DISTANCE_FROM_SEARCH("Distance"),
    BEST_VALUE("Best Value"),
    POPULAR("Popular");

    private String text;

    HotelResultsSortCriteria(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static HotelResultsSortCriteria fromString(String text) {
        if (text != null) {
            for (HotelResultsSortCriteria value : HotelResultsSortCriteria.values()) {
                if (text.equals(value.text)) {
                    return value;
                }
            }
        }
        return null;
    }
}

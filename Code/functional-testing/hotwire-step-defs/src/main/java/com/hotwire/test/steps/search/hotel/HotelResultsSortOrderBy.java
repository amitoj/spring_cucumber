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
public enum HotelResultsSortOrderBy {
    LOW_TO_HIGH("low to high"),
    HIGH_TO_LOW("high to low"),
    NEAR_TO_FAR("near to far");

    private String text;

    HotelResultsSortOrderBy(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static HotelResultsSortOrderBy fromString(String text) {
        if (text != null) {
            for (HotelResultsSortOrderBy value : HotelResultsSortOrderBy.values()) {
                if (text.equals(value.text)) {
                    return value;
                }
            }
        }
        return null;
    }
}

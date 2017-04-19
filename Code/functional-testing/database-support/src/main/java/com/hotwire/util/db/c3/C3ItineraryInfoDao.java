/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 12/12/14
 */
public class C3ItineraryInfoDao extends C3SearchDao {
    private static final String ITINERARY_CURRENCY =
            "SELECT customer_currency_code " +
            "FROM PURCHASE_ORDER\n" +
            "WHERE display_number = ?";

    public C3ItineraryInfoDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public String getItineraryCurrency(String itineraryNum) {
        return getValueFromDB(ITINERARY_CURRENCY, itineraryNum);
    }

}

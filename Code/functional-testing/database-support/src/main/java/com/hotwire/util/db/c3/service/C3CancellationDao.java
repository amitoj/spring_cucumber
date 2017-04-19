/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3.service;

import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3AbstractDao;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * SQL queries for cancellation in C3 Database verification
 */
public class C3CancellationDao extends C3AbstractDao {

    private static final String PURCHASE =
        "SELECT po.display_number FROM purchase_order po\n" +
        "JOIN customer c on po.customer_id = c.customer_id\n" +
        "WHERE po.pgood_class = ?\n" +
        "AND po.status_code in (30025, 30030)\n" +
        "AND po.create_date > sysdate - 1\n" +
        "AND po.site_id = 1\n" +
        "AND po.partner_id is NULL\n" +
        "ORDER BY po.create_date DESC";

    public C3CancellationDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    @Override
    public String getAirItinerary() {
        return getRandomValueFromDB(PURCHASE, "A");
    }

    @Override
    public String getCarItinerary() {
        return getRandomValueFromDB(PURCHASE, "C");
    }

    @Override
    public String getHotelItinerary() {
        return getRandomValueFromDB(PURCHASE, "H");
    }

    @Override
    public String getAirItinerary(String opacityCode) {
        throw new UnimplementedTestException("Query is not implemented");
    }

    @Override
    public String getCarItinerary(String opacityCode) {
        throw new UnimplementedTestException("Query is not implemented");
    }

    @Override
    public String getHotelItinerary(String opacityCode) {
        throw new UnimplementedTestException("Query is not implemented");
    }
}

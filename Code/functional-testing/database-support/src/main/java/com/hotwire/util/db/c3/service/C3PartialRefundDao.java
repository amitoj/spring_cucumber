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

import java.util.Map;

/**
 * SQL queries for partial refunds in C3 Database verification
 */
public class C3PartialRefundDao extends C3AbstractDao {
    private static final String AIR_PURCHASE =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_air_itinerary sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='A' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 150 " +
        "AND po.site_id = 1 " +
        "AND po.partner_id is NULL " +
        "AND c.email not like '%@expedia.com' " +
//        "AND po.customer_currency_code = 'USD' " +
        "AND r.quantity >= 1 " +
//        "AND sa.start_date > sysdate + 1 " +
        "AND po.total_amount > 0";

    private static final String CAR_PURCHASE =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_rental_car sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='C' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 30 " +
//        "AND po.customer_currency_code = 'USD' " +
        "AND po.site_id = 1 " +
        "AND po.partner_id is NULL " +
        "AND sa.opacity_code = 'Y' " +
        "AND c.email not like '%@expedia.com' " +
        "AND r.quantity > 1 " +
        "AND sa.start_date > sysdate + 1 " +
        "AND po.total_amount > 0";

    private static final String HOTEL_PURCHASE =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_hotel_room sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='H' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 30 " +
//        "AND po.customer_currency_code = 'USD' " +
        "AND po.site_id = 1 " +
        "AND po.partner_id is NULL " +
        "AND c.email not like '%@expedia.com' " +
        "AND r.hw_credit_card_id != -10 " +
        "AND r.quantity > 1 " +
        "AND sa.start_date > sysdate + 1 " +
        "AND po.total_amount > 0";


    private static final String AIR_PURCHASE_BY_EMAIL = "SELECT po.display_number " +
            " FROM purchase_order po JOIN customer c on po.customer_id = c.customer_id" +
            " JOIN reservation r on r.purchase_order_id = po.purchase_order_id" +
            " JOIN sold_air_itinerary sa on sa.pgood_id = r.pgood_id" +
            " WHERE po.pgood_class ='A' AND po.status_code = '30030' AND c.email = ?" +
            " AND r.quantity > 1" +
            " order by po.update_date desc";

    private static final  String EMAIL_AND_ITINERARY_WITH_AIR_PARTIAL_REFUND = "SELECT cu.email, po.display_number " +
            "FROM purchase_order po, customer cu WHERE po.status_code = '30040'" +
            "AND po.customer_id = cu.customer_id AND pgood_class = 'A' order by po.create_date desc";


    public C3PartialRefundDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    @Override
    public String getAirItinerary() {
        return getRandomValueFromDB(AIR_PURCHASE);
    }

    @Override
    public String getCarItinerary() {
        return getRandomValueFromDB(CAR_PURCHASE);
    }

    @Override
    public String getHotelItinerary() {
        return getRandomValueFromDB(HOTEL_PURCHASE);
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

    public String getAirPurchaseByEmail(String email) {
        return getValueFromDB(AIR_PURCHASE_BY_EMAIL, email);
    }

    public Map getEmailWithAirPartialRefund() {
        return getRowFromDB(EMAIL_AND_ITINERARY_WITH_AIR_PARTIAL_REFUND);
    }
}

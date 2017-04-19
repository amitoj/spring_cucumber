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
 * SQL queries for refunds in C3 Database verification
 */
public class C3RefundDao extends C3AbstractDao {

    private static final String AIR_PURCHASE_CONFIRMED =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_air_itinerary sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='A' " +
        "AND po.status_code = '30030' " +
//        "AND po.customer_currency_code = 'USD' " +
        "AND po.create_date > sysdate - 100 " +
        "AND po.create_date < sysdate - 5 " +
        "AND po.site_id = 1 " +
        "AND po.partner_id is NULL " +
        "AND c.email not like '%@expedia.com' " +
//        "AND sa.start_date > sysdate + 1 " +
        "AND po.total_amount > 0\n" +
        "AND c.customer_id NOT IN (SELECT CUSTOMER_ID FROM DISCOUNT_BALANCE)";

    private static final String CAR_PURCHASE_CONFIRMED =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_rental_car sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='C' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 30 " +
        "AND po.customer_currency_code = 'USD' " +
        "AND po.site_id = 1 " +
        "AND po.partner_id is NULL " +
        "AND sa.opacity_code = 'Y' " +
        "AND c.email not like '%@expedia.com' " +
        "AND sa.start_date > sysdate + 1 " +
        "AND po.total_amount > 0\n" +
        "AND (select count(r.reservation_id) " +
            "from reservation r where r.purchase_order_id = po.purchase_order_id) = 1\n" +
        "AND c.customer_id NOT IN (SELECT CUSTOMER_ID FROM DISCOUNT_BALANCE)";

    private static final String HOTEL_PURCHASE_CONFIRMED =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_hotel_room sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='H' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 30 " +
        "AND po.customer_currency_code = 'USD' " +
        "AND po.site_id = 1 " +
        "AND po.partner_id is NULL " +
        "AND c.email not like '%@expedia.com' " +
      //  "AND r.hw_credit_card_id != -10 " +
        "AND sa.start_date > sysdate + 1 " +
        "AND po.total_amount > 0\n" +
        "AND c.customer_id NOT IN (SELECT CUSTOMER_ID FROM DISCOUNT_BALANCE)";

    private static final String AIR_PURCHASE_CONFIRMED_VOID =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_air_itinerary sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='A' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 1 " +
        "AND po.site_id = 1 " +
        "AND sa.opacity_code = 'N' " +
        "AND c.email not like '%@expedia.com' " +
        "AND sa.start_date > sysdate + 1 " +
        "AND po.total_amount > 0";

    private static final String HOTEL_INTL_PURCHASE =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_hotel_room sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='H' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 30 " +
        "AND po.site_id <> 1 " +
        "AND po.partner_id is NULL " +
        "AND c.email not like '%@expedia.com' " +
        "AND r.hw_credit_card_id != -10 " +
        "AND sa.start_date > sysdate + 1 " +
        "AND po.total_amount > 0";

    public C3RefundDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    @Override
    public String getAirItinerary() {
        return getRandomValueFromDB(AIR_PURCHASE_CONFIRMED);
    }

    @Override
    public String getCarItinerary() {
        return getRandomValueFromDB(CAR_PURCHASE_CONFIRMED);
    }

    @Override
    public String getHotelItinerary() {
        return getRandomValueFromDB(HOTEL_PURCHASE_CONFIRMED);
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

    public String gerAirVoidPurchase() {
        return getRandomValueFromDB(AIR_PURCHASE_CONFIRMED_VOID);
    }

    public String getIntlHotelPurchase() {
        return getRandomValueFromDB(HOTEL_INTL_PURCHASE);
    }
}

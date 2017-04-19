/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3.lpg;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Queries for LPG verifications in My Account
 */
public class C3LPGRefundRequestDao extends C3LowPriceGuaranteeDao {
    private static final String AIR_REFUND_REQUEST =
        "select po.display_number, c.email, po.purchase_order_id, r.reservation_id from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_air_itinerary sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date > sysdate - 1\n" +
        "AND po.pgood_class ='A'\n" +
        "and c.email = 'caps-express@hotwire.com'\n" +
        "and po.total_amount > 0\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim)\n" +
        "AND po.status_code > '30025'";

    private static final String CAR_REFUND_REQUEST =
        "select po.display_number, c.email, po.purchase_order_id, r.reservation_id  from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_rental_car sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date > sysdate - 1\n" +
        "AND po.pgood_class ='C'\n" +
        "and c.email = 'caps-express@hotwire.com'\n" +
        "and po.total_amount > 0\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim)\n" +
        "AND po.status_code > '30025'";

    private static final String HOTEL_REFUND_REQUEST =
        "select po.display_number, c.email, po.purchase_order_id, r.reservation_id  from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_hotel_room sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date > sysdate - 1\n" +
        "AND po.pgood_class ='H'\n" +
        "and sai.is_opaque = 'Y'\n" +
        "and c.email = 'caps-express@hotwire.com'\n" +
        "and po.total_amount > 0\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim)\n" +
        "AND po.status_code > '30025'";

    private static final String LPG_CAR_EXPIRED =
        "select po.display_number, c.email, po.purchase_order_id, r.reservation_id from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_rental_car sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date < sysdate - 3\n" +
        "AND po.pgood_class ='C'\n" +
        "and sai.opacity_code = 'Y'\n" +
        "and po.total_amount > 0\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim) " +
        "AND c.PASSWORD IS NOT NULL " +
        "AND c.EMAIL LIKE 'test\\_hotwire\\_%@hotwire.com' ESCAPE '\\'";

    private static final String LPG_HOTEL_EXPIRED =
        "select po.display_number, c.email, po.purchase_order_id, r.reservation_id from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_hotel_room sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date < sysdate - 3\n" +
        "AND po.pgood_class ='H'\n" +
        "and sai.is_opaque = 'Y'\n" +
        "and po.total_amount > 0\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim) " +
        "AND c.PASSWORD IS NOT NULL " +
        "AND c.EMAIL LIKE 'test\\_hotwire\\_%@hotwire.com' ESCAPE '\\'";

    private Map<String, String> refundRequests = new HashMap<String, String>()
    {
        {
            put("AIR", AIR_REFUND_REQUEST);
            put("CAR", CAR_REFUND_REQUEST);
            put("HOTEL", HOTEL_REFUND_REQUEST);
        }
    };

    public C3LPGRefundRequestDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }


    public Map getPurchaseForLPGRefundRequest(String vertical) {
        return getRowFromDB(refundRequests.get(vertical.toUpperCase()));
    }


    public Map getItineraryWithExpiredRefundRequest(String vertical) {
        if (vertical.equalsIgnoreCase("hotel")) {
            return getRowFromDB(LPG_HOTEL_EXPIRED);
        }
        else {
            return getRowFromDB(LPG_CAR_EXPIRED);
        }
    }

}

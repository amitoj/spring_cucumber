/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3.lpg;

import com.hotwire.util.db.c3.C3SearchDao;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;


/**
 * Queries for LPG verifications in C3
 */
public class C3LowPriceGuaranteeDao extends C3SearchDao {

    private static final String AIR_PURCHASE =
        "select po.display_number from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_air_itinerary sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date > sysdate - 3" +
         "AND po.pgood_class ='A'\n" +
        "AND po.create_date > sysdate - 30 " +
        "AND po.create_date < sysdate - 5 " +
        "and sai.opacity_code = ?\n" +
        "and c.email not like '%@expedia.com'\n" +
        "and po.total_amount > 10\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim)\n" +
        "AND po.status_code = '30030'";

    private static final String CAR_PURCHASE =
        "select po.display_number from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_rental_car sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date > sysdate - 3\n" +
        "AND po.pgood_class ='C'\n" +
        "and sai.opacity_code = ?\n" +
        "and c.email not like '%@expedia.com'\n" +
        "and po.total_amount > 10\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim)\n" +
        "AND po.status_code = '30030'";

    private static final String HOTEL_PURCHASE =
        "select po.display_number from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_hotel_room sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date > sysdate - 3\n" +
        "AND po.pgood_class ='H'\n" +
        "and sai.is_opaque = ?\n" +
        "and c.email not like '%@expedia.com'\n" +
        "and po.total_amount > 10\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim)\n" +
        "AND po.status_code = '30030'";

    private static final String CAR_LPG_PREVIOUS =
        "select po.display_number from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_rental_car sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date > sysdate - 3\n" +
        "AND po.pgood_class ='C'\n" +
        "and sai.opacity_code = ?\n" +
        "and c.email not like '%@expedia.com'\n" +
        "and po.total_amount > 10\n" +
        "and r.RESERVATION_ID in (select reservation_id from low_price_guarantee_claim)";

    private static final String CAR_LPG_EXPIRED =
        "select po.display_number from purchase_order po\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join sold_rental_car sai on sai.pgood_id = r.pgood_id\n" +
        "join customer c on po.customer_id = c.customer_id\n" +
        "where sai.create_date < sysdate - 3\n" +
        "AND po.pgood_class ='C'\n" +
        "and sai.opacity_code = 'Y'\n" +
        "and c.email not like '%@expedia.com'\n" +
        "and po.total_amount > 10\n" +
        "and r.RESERVATION_ID NOT in (select reservation_id from low_price_guarantee_claim)";

    public C3LowPriceGuaranteeDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }


    public String getAirItinerary(String opacityCode) {
        return getRandomValueFromDB(AIR_PURCHASE, opacityCode);
    }


    public String getCarItinerary(String opacityCode) {
        return getRandomValueFromDB(CAR_PURCHASE, opacityCode);
    }

    public String getHotelItinerary(String opacityCode) {
        return getRandomValueFromDB(HOTEL_PURCHASE, opacityCode);
    }

    public String getCarItineraryWithPreviousLPG(String opacityCode) {
        return getValueFromDB(CAR_LPG_PREVIOUS, opacityCode);
    }

    public String getCarItineraryWithExpiredLPG() {
        return getValueFromDB(CAR_LPG_EXPIRED);
    }
}

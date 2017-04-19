/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 8/28/14
 * Time: 8:49 AM
 * Contains SQLs for Car itineraries verification in C3
 */
public class C3CarItineraryInfoDao extends C3SearchDao {

    private static final String CAR_ITINERARY_INFORMATION =
            "select po.display_number, r.reservation_num as pnr_number, src.opacity_code,\n" +
                    "p.first_name, p.middle_name, p.last_name,\n" +
                    "src.start_date, src.end_date,\n" +
                    "src.pickup_location_id,\n" +
                    "src.orig_city_id, src.location_id,\n" +
                    "src.dest_city_id,\n" +
                    "src.pickup_airport_code,\n" +
                    "cv.vendor_name\n" +
                    "from purchase_order po\n" +
                    "join reservation r on po.purchase_order_id = r.purchase_order_id\n" +
                    "join sold_rental_car src on r.pgood_id = src.pgood_id\n" +
                    "join participant p on r.reservation_id = p.reservation_id\n" +
                    "join car_vendor cv on src.car_vendor_cd = cv.car_vendor_cd\n" +
                    "where po.display_number = ?";

    public C3CarItineraryInfoDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public Map getCarItineraryInformation(String itineraryNumber) {
        return getRowFromDB(CAR_ITINERARY_INFORMATION, itineraryNumber);
    }
}

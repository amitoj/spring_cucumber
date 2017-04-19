/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.car;

import com.hotwire.util.db.AbstractDao;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * User: v-ngolodiuk
 * Since: 8/20/14
 */
public class CarsDao extends AbstractDao {

    private static final String VENDOR_DATA =
            "select car_vendor.vendor_name, car_service.car_vendor_location_type\n" +
                    "from car_vendor, car_service where car_service.car_vendor_cd = car_vendor.car_vendor_cd and\n" +
                    "car_service.pickup_airport_code = ?";

    private static final String VENDOR_LOCATION_TYPE =
            "select car_service.car_vendor_location_type\n" +
            "from car_vendor, car_service where car_service.car_vendor_cd = car_vendor.car_vendor_cd and\n" +
                    "car_service.pickup_airport_code = ?";

    private static final String VENDOR_LOCATION_TYPE_BY_DISPLAY_NUMBER =
            "select cs.car_vendor_location_type from search s, search_solution ss" +
            ", search_pgood sp, car_service cs, rental_car rc where\n" +
            "s.search_id = ss.search_id and ss.search_solution_id =" +
            "sp.search_solution_id and s.pickup_airport_code = cs.pickup_airport_code\n" +
            "and sp.pgood_id = rc.pgood_id and rc.car_vendor_cd =" +
            "cs.car_vendor_cd and ss.display_number = ?";

    private static final String STATUS_AND_NETWORK_CODES_FOR_FAILED_CAR_PURCHASE =
            "select status_code, network_status_code from " +
            "(select pr.* from purchase_order po, payment_receipt pr\n" +
            "where po.purchase_order_id = pr.purchase_order_id\n" +
            "and po.display_number = ?\n" +
            "order by pr.create_date desc) where rownum = 1";


    private static final String  GET_CAR_WATCHED_TRIP =
            "select WATCHED_TRIP_ID from WATCHED_TRIP where customer_id = ? " +
                    " and PGOOD_CODE = 'C'  and " +
                    "to_char(START_DATE, 'MM/DD/YYYY') = ? and " +
                    "to_char(END_DATE, 'MM/DD/YYYY') = ?  and ORIG_AIRPORT_CODE = ? ";

    private static final String SEARCH_PARTNERS_CODES =
            "select partner_code from click_tracking where create_date > sysdate - 60/86400 and  pgood_code = 'C' " +
            "order by create_date desc";

    public CarsDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public List<Map<String, Object>> getVendorsData(String location) {
        return verifyTableInDB(VENDOR_DATA, location);
    }

    public List<Map<String, Object>> getVendorLocationTypeData(String location) {
        return verifyTableInDB(VENDOR_LOCATION_TYPE, location);
    }

    public List<Map<String, Object>> getVendorLocationTypeDataByDisplayNumber(String displayNumber) {
        return verifyTableInDB(VENDOR_LOCATION_TYPE_BY_DISPLAY_NUMBER, displayNumber);
    }

    public List<Map<String, Object>> getStatusAndNetworkCodesForFailedPurchase(String displayNumber) {
        return verifyTableInDB(STATUS_AND_NETWORK_CODES_FOR_FAILED_CAR_PURCHASE, displayNumber);
    }

    public List<Map<String, Object>> getSearchPartnersCode() {
        return verifyTableInDB(SEARCH_PARTNERS_CODES);
    }

    public String getCarWatchedTrip(String customerId, String startDate, String endDate, String airportCode) {
        return getValueFromDB(GET_CAR_WATCHED_TRIP, customerId, startDate, endDate, airportCode);
    }
}

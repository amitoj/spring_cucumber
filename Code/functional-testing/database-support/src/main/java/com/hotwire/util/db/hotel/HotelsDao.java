/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.hotel;

import com.hotwire.util.db.AbstractDao;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.Map;

/**
 * Created by v-sshubey on 1/23/2015.
 */
public class HotelsDao extends AbstractDao {

    private static final String PARTNER_SEARCH_DATA =
            "select * from click_tracking where create_date > sysdate - 60/86400 " +
            "and pgood_code = 'H' order by create_date desc";

    private static final String  GET_HOTEL_WATCHED_TRIP =
            "select WATCHED_TRIP_ID from WATCHED_TRIP where customer_id = ? " +
                    " and PGOOD_CODE = 'H'  and " +
                    "to_char(START_DATE, 'MM/DD/YYYY') = ? and " +
                    "to_char(END_DATE, 'MM/DD/YYYY') = ? ";

    private static final String GET_HOTEL_NAME_ADDRESS =
            "select h.hotel_name, h.address_1 " +
            "from hotel h, sold_hotel_room s, reservation r, purchase_order p " +
            "where p.purchase_order_id = r.purchase_order_id " +
            "and r.pgood_id = s.pgood_id and s.hotel_id = h.hotel_id " +
            "and p.display_number = ?";

    public HotelsDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public Map getPartnerSearchData() {
        return getRowFromDB(PARTNER_SEARCH_DATA);
    }

    public String getHotelWatchedTrip(String customerId, String startDate, String endDate) {
        return getValueFromDB(GET_HOTEL_WATCHED_TRIP, customerId, startDate, endDate);
    }

    public Map getHotelNameAndAddress(String itinerary) {
        return getRowFromDB(GET_HOTEL_NAME_ADDRESS, itinerary);
    }

}

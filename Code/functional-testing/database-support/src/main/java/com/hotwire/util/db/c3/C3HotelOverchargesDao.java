/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.util.db.c3;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.time.DateFormatUtils.format;

/**
 * User: v-ngolodiuk
 * Date: 10/23/13
 * Class for matching overcharges results with database
 * */
public class C3HotelOverchargesDao extends C3SearchDao {
    private static final String OVERCHARGES_HOTEL_ID =
        "select ho.hotel_id from hotel_overcharge ho\n" +
        "join hotel_overcharge_contact hoc on ho.hotel_id = hoc.hotel_id\n" +
        "where ho_status_code = 1";

    private static final String HOTEL_ID_WITHOUT_OVERCHARGES =
        "select hotel_id from hotel\n" +
        "where hotel_id not in (select hotel_id from hotel_overcharge)";

    private static final String UNASSIGNED_OVERCHARGES =
            "select h.hotel_name, tbl2.overcharge_total, tbl2.overcharge_count, " +
                    " hoc.overcharge_date_modified, tbl2.last_overcharge_date " +
                    "from ( " +
                    "select hotel_id, sum(overcharge) as overcharge_total, " +
                    "count(hotel_id) as overcharge_count, max(create_date) as last_overcharge_date from " +
                    "(select ho.hotel_id, ho.charges_amount, ho.reservation_amount, " +
                    "(ho.charges_amount - ho.reservation_amount) as overcharge, ho.create_date " +
                    "from hotel_overcharge ho, hotel_overcharge_contact hoc " +
                    "where hoc.hotel_id = ho.hotel_id " +
                    "and ho.ho_status_code = '1' " +
                    "and hoc.csr_id is null " +
                    ") tbl1 " +
                    "group by hotel_id " +
                    "order by overcharge_total desc) " +
                    "tbl2, hotel h, hotel_overcharge_contact hoc " +
                    "where tbl2.hotel_id = h.hotel_id " +
                    "and tbl2.hotel_id = hoc.hotel_id";

    private  static  final String UPDATED_ITINERARY_OVERCHARGES_BY_HOTEL_NAME =
            "select note from hotel_overcharge_note where hotel_id  =" +
                    " (select hotel_id from hotel where hotel_name = ?)" +
                    " order by create_date desc";

    private static final String OVERCHARGE_HOTEL_WITH_STATE =
            "select h.hotel_name, s.state_code from hotel_overcharge ho\n" +
            "join hotel h on h.hotel_id = ho.hotel_id\n" +
            "join city c on c.city_id = h.city_id\n" +
            "join state s on s.state_id = c.state_id\n" +
            "where rownum < 50";

    private String amountChangedCTA =
        "select tr.hw_credit_card_id from hw_credit_card_trans tr\n" +
        "join reservation r on r.reservation_id = tr.reservation_id\n" +
        "join purchase_order po on po.purchase_order_id = r.PURCHASE_ORDER_ID\n" +
        "where po.display_number = ?";

    public C3HotelOverchargesDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public String getOverchargesHotel() {
        return getValueFromDB(OVERCHARGES_HOTEL_ID);
    }

    public String getHotelWithoutOvercharges() {
        return getValueFromDB(HOTEL_ID_WITHOUT_OVERCHARGES);
    }

    public String getCTANumber(String itinerary) {
        return verifyValueInDB(amountChangedCTA, itinerary);
    }

    public List<Map<String, Object>> getUnAssignedOvercharges() {
        List<Map<String, Object>> temp = verifyTableInDB(UNASSIGNED_OVERCHARGES);
        for (int i = 0; i < temp.size(); i++) {
            Date date = (Date) temp.get(i).get("OVERCHARGE_DATE_MODIFIED");
            temp.get(i).put("OVERCHARGE_DATE_MODIFIED", format(date, "MM/dd/yy"));
            date = (Date) temp.get(i).get("LAST_OVERCHARGE_DATE");
            temp.get(i).put("LAST_OVERCHARGE_DATE", format(date, "MM/dd/yy"));
        }
        return temp;
    }

    public String getOverchargesItineraryUpdateInDB(String fullHotelName) {
        return getValueFromDB(UPDATED_ITINERARY_OVERCHARGES_BY_HOTEL_NAME, fullHotelName);
    }

    public Map getOverchargeHotelWithState() {
        return getRandomRowFromDB(OVERCHARGE_HOTEL_WITH_STATE);
    }
}


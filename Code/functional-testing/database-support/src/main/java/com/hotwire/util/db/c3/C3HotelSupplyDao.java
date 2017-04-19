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
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 4/7/14
 * Time: 8:09 AM
 * This class contains SQL queries that are used only for hotel supplier searches
 */

/**
 * PURCHASE STATUS
 * po.status_code
 * 30030  probably AVC declined VY
 * 30030, 'Booking Confirmed'
 * 30040, 'Partially Refunded'
 * 30045, 'Pending Review with Itinerary Cancelled'
 * 30050, 'Cancelled'
 * 30060, 'Refunded'
 */
public class C3HotelSupplyDao extends C3SearchDao {

    private static final String QUERY_SOME_HOTEL =
        "SELECT h.hotel_id, h.hotel_name, h.address_1, h.hotel_phone, h.contact_fax, h.address_zip,\n" +
        "c.display_name as city_name, s.display_name as state_name, cc.display_name as country_name\n" +
        "FROM hotel h\n" +
        "join city c on h.city_id = c.city_id\n" +
        "join state s on c.state_id = s.state_id\n" +
        "join country cc on s.country_code = cc.country_code\n" +
        "WHERE hotel_phone is NOT NULL\n" +
        "AND contact_fax is NOT NULL\n" +
        "AND rownum =1";

    private static final String QUERY_HOTEL_WITH_UNIQUE_PHONE_NUM =
        "SELECT h.hotel_id, h.hotel_name, h.address_1, h.hotel_phone, h.contact_fax\n" +
        "FROM hotel h \n" +
        "JOIN (select hotel_id, hotel_phone, count(hotel_phone) as count from hotel\n " +
        "group by hotel_phone, hotel_id) h1   \n" +
        "on h.hotel_id = h1.hotel_id \n" +
        "where count <2\n" +
        "AND h.hotel_phone is NOT NULL\n" +
        "AND h.contact_fax is NOT NULL\n" +
        "AND ROWNUM=1";

    private static final String QUERY_MINOR_HOTEL_AMENITY_FOR_REMOVAL =
            "select a.amenity_name\n" +
                    "from  amenity a\n" +
                    "join hotel_amenity ha on a.amenity_code = ha.amenity_code\n" +
                    "where a.is_major = 'N' and a.active_ind = 'Y' and a.display_in_filter = 'B'\n" +
                    "and ha.active_ind = 'Y' and ha.hotel_id = ?";

    private static final String QUERY_MINOR_HOTEL_AMENITY_TO_ADD =
            "select t1.amenity_name\n" +
                    "from  (select amenity_code, amenity_name \n" +
                    "from amenity \n" +
                    "where is_major = 'N' and active_ind = 'Y' and display_in_filter = 'B') t1\n" +
                    "left join (select amenity_code from hotel_amenity where hotel_id = ? ) t2\n" +
                    "on t1.amenity_code = t2.amenity_code\n" +
                    "where t2.amenity_code is null";

    private static final String QUERY_MAYOR_HOTEL_AMENITY_FOR_REMOVAL =
            "select a.amenity_name\n" +
                    "from  amenity a\n" +
                    "join hotel_amenity ha on a.amenity_code = ha.amenity_code\n" +
                    "where a.is_major = 'Y' and a.active_ind = 'Y' and a.display_in_filter = 'B'\n" +
                    "and ha.active_ind = 'Y' and ha.hotel_id = ?";

    private static final String QUERY_MAYOR_HOTEL_AMENITY_TO_ADD =
            "select t1.amenity_name\n" +
            "from  (select amenity_code, amenity_name \n" +
            "from amenity \n" +
            "where is_major = 'Y' and active_ind = 'Y' and display_in_filter = 'B') t1\n" +
            "left join (select amenity_code from hotel_amenity where hotel_id = ? ) t2\n" +
            "on t1.amenity_code = t2.amenity_code\n" +
            "where t2.amenity_code is null";

    private static final String HOTEL_PURCHASE_WITH_STAR_RATING =
            "select po.display_number," +
            " h.HOTEL_ID," +
            " h.HOTEL_NAME," +
            "h.RATING_CODE," +
            " hb.EXPEDIA_STAR_RATING," +
            " h.RATING_CODE_CHANGE_DATE\n" +
            "from purchase_order po\n" +
            "join reservation r on  po.purchase_order_id = r.purchase_order_id\n" +
            "join sold_hotel_room shr on r.pgood_id = shr.pgood_id\n" +
            "join hotel h on h.HOTEL_ID = shr.HOTEL_ID\n" +
            "left join hotel_benchmark hb on shr.hotel_id = hb.hotel_id\n" +
            "where po.PARTNER_ID is NULL\n" +
            "AND po.create_date > sysdate - 30\n" +
            "AND po.status_code = '30030'\n" +
//            "AND h.hotel_id = '8880'\n"  +
            "AND rownum < 100";

    private static final String HOTEL_WITH_SURVEY =
            "SELECT HOTEL_ID FROM HOTEL_SURVEY WHERE HOTEL_ID = ? AND PURCHASE_ORDER_ID IS NOT NULL";

    private static final String HOTEL_WITH_RESERVATIONS =
            "select hotel_id \n" +
                    "from view_c3_hotel_reservation_info where\n" +
                    "check_in_date between sysdate + ? and sysdate + ? \n" +
                    "group by hotel_id having count(hotel_id) > 1 and count(hotel_id) < 50";

    private static final String HOTEL_WITH_OVERSELLS_BETWEEN_DATES =
            "select hotel_id \n" +
                    "from sold_hotel_room where\n" +
                    "start_date between  sysdate + ? and sysdate + ? \n" +
                    "group by hotel_id having count(hotel_id) < 50";


    private static final String RETRIEVE_GUEST_RESERVATIONS_RESULTS =
            "select * from ( select GUEST_LAST_NAME, GUEST_FIRST_NAME, CHECK_IN_DATE, CHECK_OUT_DATE, " +
                    "decode(partner_id, 31,'Expedia',1,'Expedia', NULL,'Hotwire','Hotwire') as \"PARTNER_SITE\", " +
                    "HW_ITINERARY_NUMBER, RESERVATION_NUMBER from view_c3_hotel_reservation_info where hotel_id = ? " +
                    "and check_in_date between sysdate + ? and sysdate + ? " +
                    "order by  UPPER(guest_last_name), CHECK_IN_DATE, CHECK_OUT_DATE desc)";

    private static final String RETRIEVE_GUEST_FINANCES_RESULTS =
            "select * from ( select GUEST_LAST_NAME, GUEST_FIRST_NAME, CHECK_IN_DATE, CHECK_OUT_DATE, " +
                    "HW_ITINERARY_NUMBER, RESERVATION_NUMBER from view_c3_hotel_reservation_info where hotel_id = ? " +
                    "and check_in_date between sysdate + ? and sysdate + ? " +
                    "order by  UPPER(guest_last_name), CHECK_IN_DATE, CHECK_OUT_DATE desc)";

    private static final String OVERSELLS_RESULTS =
            "select GUEST_LAST_NAME, GUEST_FIRST_NAME, CHECK_IN_DATE, CHECK_OUT_DATE," +
                    " decode(partner_id, 31,'Expedia',1,'Expedia', NULL,'Hotwire','Hotwire')  as \"PARTNER_SITE\"," +
                    " HW_ITINERARY_NUMBER, RESERVATION_NUMBER, decode " +
                    "(is_opaque, 'Y', 'opaque', 'O', 'opaque', 'N', 'retail', 'T'," +
                    " 'super opaque', 'Unknown') as \"Opacity\"," +
                    " CASE is_preferred_customer WHEN 'N' THEN CASE is_hfc_blocked WHEN 'N' " +
                    "THEN 'no' When 'Y' Then 'no' END WHEN 'Y' Then CASE is_hfc_blocked When 'N' " +
                    "Then CASE pricing_group_id WHEN 1 Then 'yes' When 2 Then 'no' When 3 Then 'no' " +
                    "When 4 Then 'no' When 5 Then 'no' End When 'Y' Then 'no' End END " +
                    "Express,site_country_code from view_c3_hotel_reservation_info " +
                    "where hotel_id = ? and check_in_date between sysdate + ? and sysdate + ? " +
                    "order by  UPPER(guest_last_name), CHECK_IN_DATE, RESERVATION_NUMBER";


    private static final String HOTEL_WITH_CHANGED_RATING =
            "SELECT HOTEL_ID FROM HOTEL WHERE RATING_CODE_CHANGE_DATE > sysdate - 180 AND HOTEL_ID = ?";


    private static final String QUERY_HOTEL_ID_BY_ITINERARY =
            "select hotel_id from purchase_order po\n" +
                    "\t join reservation r on r.reservation_id = po.purchase_order_id\n" +
                    "\t join sold_hotel_room shr on r.pgood_id = shr.pgood_id\n" +
                    "\t where po.display_number = ?";

    private static final String HOTEL_ID_ITINERARY_FOR_PURCHASE =
            "select shr.hotel_id, shr.start_date, shr.end_date, po.display_number \n" +
                    "from sold_hotel_room shr, reservation r, purchase_order po \n" +
                    "where shr.create_date > (sysdate - 1) \n" +
                    "  and r.create_date > (sysdate - 1) \n" +
                    "  and po.create_date > (sysdate - 1) \n" +
                    "  and shr.pgood_id = r.pgood_id \n" +
                    "  and r.reservation_id = po.purchase_order_id " +
                    "  and po.status_code = 30030\n" +
                    " and r.currency = 'USD' " +
                    " order by shr.hotel_id desc";

    private static final String HOTEL_RESERVATION_DETAILS =
            "Select p.last_name as \"Last Name\", p.first_name as \"First Name\"," +
                    " shr.start_date as \"Check-in date\",\n" +
                    "shr.end_date as \"Check-out date\", po.display_number as \"Hotwire Confirmation\"," +
                    " r.reservation_num as \"Hotel Reservation\",\n" +
                    "decode(po.status_code, 30040, 'Partially Refunded', 30060, 'Refunded', 30030," +
                    " 'Booking Confirmed', 30050, 'Cancelled'," +
                    " 30045, 'Pending Review with Itinerary Cancelled')" +
                    " as \"Status of Reservation\",\n" +
                    "(r.Quantity/(shr.End_Date-shr.Start_Date)) AS \"Number of Rooms\"," +
                    " h.hotel_name as \"Hotel Name\",\n" +
                    "c.city_name as \"City\", s.State_Name as \"State\", h.hotel_phone" +
                    " as \"Hotel Phone\",\n" +
                    " ((shr.base_amount*r.quantity) + shr.local_total_tax_amount)" +
                    " as \"Total Supplier Cost\",\n" +
                    "shr.local_base_amount as \"Charge to HW Card\"\n" +
                    "From   Participant p, Sold_Hotel_Room shr, Reservation r, Purchase_Order po," +
                    " Hotel h, City c, State s\n" +
                    "Where  shr.hotel_id=? and shr.pgood_id=r.pgood_id and" +
                    " r.purchase_order_id=po.purchase_order_id and\n" +
                    "p.reservation_id=r.reservation_id and po.display_number=? and" +
                    " shr.hotel_id=h.hotel_id and c.city_id=h.city_id\n" +
                    "and s.state_id=c.state_id";

    private  static  final  String DELETE_HOTEL_REPORTS_CONFIG_EXCEPTION =
            "delete from hotel_reports_config_exception where exception_id = '?'";

    private  static  final  String HOTEL_REPORTS_CONFIG_EXCEPTION =
            "select exception_type as type, hotel_report_id as report_id" +
                    " from hotel_reports_config_exception where exception_id =? and rownum=1 order by create_date";

    public C3HotelSupplyDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public Map getSomeHotel() {
        return getRowFromDB(QUERY_SOME_HOTEL);
    }

    public Map getHotelWithUniquePhoneNum() {
        return getRowFromDB(QUERY_HOTEL_WITH_UNIQUE_PHONE_NUM);
    }

    public String getSomeHotelID() {
        return getRandomValueFromDB("SELECT HOTEL_ID FROM HOTEL WHERE ACTIVE_IND = 'Y'");
    }

    public String getHotelIDWithReservations(String shiftDatesFrom, String shiftDatesTo) {
        return getRandomRowFromDB(HOTEL_WITH_RESERVATIONS, shiftDatesFrom, shiftDatesTo).get("HOTEL_ID").toString();
    }

    public String getHotelIDWithOversellsBetweenDates(String shiftDatesFrom, String shiftDatesTo) {
        return getRandomValueFromDB(HOTEL_WITH_OVERSELLS_BETWEEN_DATES, shiftDatesFrom, shiftDatesTo);
    }

    public String getMinorAmenityForRemoval(String hotelID) {
        return getValueFromDB(QUERY_MINOR_HOTEL_AMENITY_FOR_REMOVAL, hotelID);
    }

    public String getMinorAmenityForAdd(String hotelID) {
        return getValueFromDB(QUERY_MINOR_HOTEL_AMENITY_TO_ADD, hotelID);
    }

    public String getMajorAmenityForRemoval(String hotelID) {
        return getValueFromDB(QUERY_MAYOR_HOTEL_AMENITY_FOR_REMOVAL, hotelID);
    }

    public String getMajorAmenityForAdd(String hotelID) {
        return getValueFromDB(QUERY_MAYOR_HOTEL_AMENITY_TO_ADD, hotelID);
    }

    public Map getHotelPurchaseWithStarRating() {
        return getRowFromDB(HOTEL_PURCHASE_WITH_STAR_RATING);
    }

    public String getHotelIDByItinerary(String itineraryNumber) {
        return getValueFromDB(QUERY_HOTEL_ID_BY_ITINERARY, itineraryNumber);
    }

    public boolean isHotelHasSurvey(String hotelID) {
        return checkResultInDB(HOTEL_WITH_SURVEY, hotelID);
    }

    public boolean isHotelRatingChanged(String hotelID) {
        return checkResultInDB(HOTEL_WITH_CHANGED_RATING, hotelID);
    }

    public List<Map<String, Object>> getRetrieveGuestReservationsResults(String hotelID,
                                                                         String shiftDatesFrom, String shiftDatesTo) {
        List<Map<String, Object>> tmpTbl = verifyTableInDB(RETRIEVE_GUEST_RESERVATIONS_RESULTS,
                hotelID, shiftDatesFrom, shiftDatesTo);
        System.out.println(tmpTbl);
        for (Map<String, Object> inst : tmpTbl) {
            Date date = (Date) inst.get("CHECK_IN_DATE");
            inst.put("CHECK_IN_DATE", format(date, "MM/dd/yy"));
            date = (Date) inst.get("CHECK_OUT_DATE");
            inst.put("CHECK_OUT_DATE", format(date, "MM/dd/yy"));
        }
        return tmpTbl;
    }

    public List<Map<String, Object>> getRetrieveGuestFinancesResults(String hotelID,
                                                                         String shiftDatesFrom, String shiftDatesTo) {
        List<Map<String, Object>> tmpTbl = verifyTableInDB(RETRIEVE_GUEST_FINANCES_RESULTS,
                hotelID, shiftDatesFrom, shiftDatesTo);
        for (Map<String, Object> inst : tmpTbl) {
            Date date = (Date) inst.get("CHECK_IN_DATE");
            inst.put("CHECK_IN_DATE", format(date, "MM/dd/yy"));
            date = (Date) inst.get("CHECK_OUT_DATE");
            inst.put("CHECK_OUT_DATE", format(date, "MM/dd/yy"));
        }
        return tmpTbl;
    }

    public List<Map<String, Object>> getOversellResults(String hotelID, String shiftDatesFrom,
                                                        String shiftDatesTo) {
        List<Map<String, Object>> tmpTbl = verifyTableInDB(OVERSELLS_RESULTS,
                hotelID, shiftDatesFrom, shiftDatesTo);
        for (Map<String, Object> inst : tmpTbl) {
            Date date = (Date) inst.get("CHECK_IN_DATE");
            inst.put("CHECK_IN_DATE", format(date, "MM/dd/yy"));
            date = (Date) inst.get("CHECK_OUT_DATE");
            inst.put("CHECK_OUT_DATE", format(date, "MM/dd/yy"));
        }
        return tmpTbl;
    }

    public Map getHotelID_DisplayID_4Purchase() {
        return getRandomRowFromDB(HOTEL_ID_ITINERARY_FOR_PURCHASE);
    }

    public Map getHotelReservationDetails(String hotelID, String itineraryNumber) {
        Map<String, Object> temp = getRowFromDB(HOTEL_RESERVATION_DETAILS, hotelID, itineraryNumber);
        Date date = (Date) temp.get("Check-in date");
        temp.put("Check-in date", format(date, "E, MMM d, yyyy"));
        date = (Date) temp.get("Check-out date");
        temp.put("Check-out date", format(date, "E, MMM d, yyyy"));
        return temp;
    }

    public void deleteHotelReportsConfigException(String hotelID) {
        executeQuery(DELETE_HOTEL_REPORTS_CONFIG_EXCEPTION, hotelID);
    }

    public Map getHotelReports(String hotelID) {
        Map<String, String> temp = getRowFromDB(HOTEL_REPORTS_CONFIG_EXCEPTION, hotelID);
        return temp;
    }
}

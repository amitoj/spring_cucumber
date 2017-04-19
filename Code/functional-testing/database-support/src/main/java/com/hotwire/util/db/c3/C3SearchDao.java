/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3;

import com.hotwire.testing.UnimplementedTestException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 2/26/14
 * Time: 3:04 AM
 * This class contains SQL queries that are used for searches
 */
public class C3SearchDao extends C3AbstractDao {

    private static final String PURCHASE =
        "SELECT po.display_number FROM purchase_order po\n" +
        "WHERE po.pgood_class = ?\n" +
        "AND po.total_amount > 0\n" +
        "AND po.status_code > 30025\n" +
        "AND po.PARTNER_ID is NULL";

    private static final String PAST_SEARCH =
        "SELECT SEARCH_ID from SEARCH\n" +
        "WHERE class = ?\n" +
        "AND create_date > sysdate - 30\n" +
        "AND po.PARTNER_ID is NULL\n" +
        "AND rownum = 1";

    private static final String CUSTOMER_WITH_NO_RECENT_SEARCH_VERTICAL =
            "select email from customer " +
            "where customer_id not in (\n" +
            "SELECT customer_id from SEARCH  " +
            "where CLASS = ? group by customer_id" +
                    " having count(customer_id) > 1)";

    private static final String CUSTOMER_WITH_NO_RECENT_SEARCH =
            "select email from customer " +
                    "where customer_id not in (\n" +
                    "SELECT customer_id from SEARCH  " +
                    " group by customer_id" +
                    " having count(customer_id) > 1)";

    private static final String CUSTOMER_WITH_MANY_RECENT_SEARCH =
            "select email from customer " +
                    "where customer_id in (\n" +
                    "SELECT customer_id from SEARCH  " +
                    "WHERE create_date > sysdate - 1 " +
                    "group by customer_id " +
                    " having ((count(customer_id) > 15) and " +
                    " (count(customer_id) < 30)))";

    private static final String HOTEL_OVERSELLS =
        "select hotel_ID, START_DATE from sold_hotel_room\n" +
        "where hotel_id in (\n" +
        "SELECT hotel_id FROM sold_hotel_room group by hotel_id having count(hotel_ID) < 10 ) \n" +
        "and rownum = 1";

    private static final String RARELY_CUSTOMER_LAST_NAME_WITH_PURCHASES =
        "select cust.last_name\n" +
        "from customer cust\n" +
        "join (select c.last_name, count(c.last_name)\n" +
        "from customer c\n" +
        "having count(c.last_name) >1 and count(c.last_name) < 2000\n" +
        "group by c.last_name) ln on cust.last_name = ln.last_name\n" +
        "join purchase_order po on po.customer_id = cust.customer_id\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "group by cust.last_name";

    private static final String RARELY_PASSENGER_LAST_NAME_WITH_PURCHASES =
        "select p.last_name\n" +
        "from participant p\n" +
        "join (select * from purchase_order where create_date > sysdate - 90 ) po\n" +
                "on p.reservation_id = po.purchase_order_id\n" +
        "having count(p.last_name) < 10\n" +
        "group by p.last_name\n" +
        "order by count(p.last_name)";

    private static final String RARELY_PAYMENT_METHOD_HOLDER_LAST_NAME_WITH_PURCHASES =
        "select holder_last_name\n" +
        "from payment_method pm\n" +
        "join\n" +
        "(select pm.payment_method_id, count(pm.payment_method_id)\n" +
        "from payment_method pm\n" +
        "join purchase_order po on pm.payment_method_id = po.payment_method_id\n" +
        "having count(pm.payment_method_id) < 5\n" +
        "group by pm.payment_method_id\n" +
        "order by count(pm.payment_method_id)) pm_freq on pm.payment_method_id = pm_freq.payment_method_id\n" +
        "having count(pm.holder_last_name) < 100\n" +
        "group by pm.holder_last_name\n" +
        "order by count(pm.holder_last_name)";

    private static final String EMAIL_WITH_MANY_HOTEL_PURCHASES = "" +
        "select email from customer c\n" +
        "join (select customer_id, count(*) from purchase_order po\n" +
        "where pgood_class = 'H'\n" +
        "and create_date > sysdate - 90\n" +
        "and rownum < 1000\n" +
        "group by customer_ID\n" +
        "having count(*) > 30\n" +
        "order by count(*) desc ) q\n" +
        "on q.customer_id = c.customer_id\n" +
        "where rownum = 1";

    private static final String EMAIL_WITHOUT_PURCHASES =
            "select email from customer where customer_id not in (\n" +
            "select customer_id from purchase_order)";

    private static final String EMAIL_WITH_PURCHASES =
            "select email from customer where customer_id" +
            " = (select customer_id from" +
            " purchase_order where rownum=1)";

    private static final String PURCHASE_WITHOUT_CASE =
            "SELECT po.display_number FROM purchase_order po\n" +
            "JOIN customer c on po.customer_id = c.customer_id\n" +
            "WHERE po.pgood_class = ?\n" +
            "AND po.status_code in (30025, 30030)\n" +
            "AND po.create_date > sysdate - 1\n" +
            "AND po.site_id = 1\n" +
            "AND po.partner_id is NULL\n" +
            "AND po.c3_status_code = 'N'" +
            "ORDER BY po.create_date DESC";

    private static final String VERTICAL_PNR =
        "SELECT t1.reservation_num from (select reservation_num, count(reservation_num) as counter from reservation\n" +
        "group by reservation_num) t1\n" +
        "JOIN reservation r ON r.reservation_num=t1.reservation_num\n" +
        "JOIN purchase_order po ON r.purchase_order_id=po.purchase_order_id\n" +
        "WHERE po.pgood_class = ?\n" +
        "AND t1.counter < 2\n" +
        "AND rownum =1\n" +
        "AND t1.reservation_num is not null\n";

    private static final String HOTEL_PURCHASE_WITH_INSURANCE =
        "SELECT po.display_number FROM purchase_order po\n" +
        "JOIN customer c on po.customer_id = c.customer_id\n" +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "JOIN sold_hotel_room sa on sa.pgood_id = r.pgood_id\n" +
        "JOIN order_add_on oao on r.reservation_id = oao.reservation_id\n" +
        "JOIN payment_method pm on pm.payment_method_id = po.payment_method_id\n" +
        "WHERE po.pgood_class ='H'\n" +
        "AND po.status_code = '30030'\n" +
        "AND po.create_date > sysdate - 30\n" +
        "AND po.site_id = 1\n" +
        "AND po.partner_id is NULL\n" +
        "AND c.email not like '%@expedia.com'\n" +
        "AND sa.start_date > sysdate + 1\n" +
        "AND sa.is_opaque = ?\n" +
        "AND r.hw_credit_card_id != -10\n" +
        "AND oao.type_code = 1\n"  +
        "AND c.password is NULL\n" +
        "and pm.address_1 is not null\n" +
        "and pm.address_city is not null\n" +
        "and pm.address_zip is not null\n" +
        "AND rownum = 1 ";

    private static final String HOTEL_PURCHASE_WITHOUT_INSURANCE =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_hotel_room sa on sa.pgood_id = r.pgood_id " +
        "JOIN payment_method pm on pm.payment_method_id = po.payment_method_id\n" +
        "WHERE po.pgood_class ='H' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 30 " +
        "AND po.site_id = 1 " +
        "AND po.partner_id is NULL " +
        "AND c.email not like '%@expedia.com' " +
        "AND sa.start_date > sysdate + 1 " +
        "AND sa.is_opaque = ?\n" +
        "AND r.hw_credit_card_id != -10 " +
        "AND r.reservation_id NOT in (select reservation_id from order_add_on) " +
        "AND c.password is NULL\n" +
        "and pm.address_1 is not null\n" +
        "and pm.address_city is not null\n" +
        "and pm.address_zip is not null\n" +
        "AND rownum = 1 ";

    private static final String HOTEL_PURCHASE_WITH_HOTDOLLARS =
        "select display_number from discount_balance db\n" +
        "join customer c on c.customer_id = db.customer_id\n" +
        "join purchase_order po on po.CUSTOMER_ID = c.CUSTOMER_ID\n" +
        "where db.discount_code = 'HOTDOLLARS' \n" +
        "and db.expiration_date > sysdate\n" +
        "and db.AMOUNT > 5\n" +
        "and po.PGOOD_CLASS = 'H'\n" +
        "and c.password is NOT null\n" +
        "and rownum = 1";

    private static final String HOTEL_PURCHASE_WITHOUT_HOTDOLLARS =
        "select display_number from purchase_order po\n" +
        "join customer c on c.customer_id = po.customer_id\n" +
        "and po.customer_id not in (select customer_id from discount_balance)\n" +
        "and po.PGOOD_CLASS = 'H'\n" +
        "and rownum = 1";

    private static final String ITINERARY_WITH_IP_ADDRESS =
            "select ip_address from (\n" +
                    "select ip_address, count(ip_address) as ip_count from purchase_order\n" +
                    "where ip_address is not null and ip_address not like '66.151.155.244'\n" +
                    "group by ip_address)\n" +
                    "where ip_count < 10 and rownum=1";
         //"select display_number from purchase_order" +
           //         " where ip_address is not null and rownum = 1";

    private static final String PHONE_WITH_PURCHASE =
         "select tbl2.phone from " +
         "( select phone, count(phone) phone_count " +
         "from purchase_order po join customer c on " +
         "po.customer_id = c.customer_id group by c.phone)" +
         " tbl1 join customer tbl2 on tbl2.phone = tbl1.phone " +
         "join purchase_order tbl3 on tbl2.customer_id = tbl3.customer_id " +
         "where phone_count < 20 and tbl2.phone like '1|%|%' and rownum=1";

    private static final String PHONE_WITH_FEW_PURCHASE =
            "select c.phone from customer c, purchase_order po " +
            "where  c.customer_id = po.customer_id and c.phone is not null " +
            "and c.phone like '1|___|_______' group by c.phone  order by count(c.phone)";

    private static final String EXISTING_ORDER_ID_AND_DISPLAY_NUM =
            "select c.email, po.purchase_order_id, po.display_number, r.reservation_num\n" +
            " from purchase_order po, customer c, reservation r\n" +
            " where po.customer_id = c.customer_id\n" +
            " and r.purchase_order_id = po.purchase_order_id\n" +
            "and po.create_date > sysdate -1\n" +
            "and po.status_code >= 30025\n" +
            " and po.PGOOD_CLASS = 'H' and r.reservation_num like '%TEST_%'";

    private static final String REFERRAL_INFO =
            "select * from search_referral\n" +
                    "where referral_date > sysdate - .01\n" +
                    "and search_date > sysdate - .01" +
                    "and search_id = ?";

    private static final String ITINERARY_WITH_CREDIT_CARD =
            "select display_number\n" +
            "from purchase_order po\n" +
            "join reservation r on po.purchase_order_id = r.purchase_order_id\n" +
            "where r.hw_credit_card_id is not null and\n" +
            "  r.pgood_class = 'H' and rownum = 1";

    private static final String HOTEL_RESERVATION_BY_ITINERARY =
            "select r.reservation_num\n" +
            " from purchase_order po, reservation r\n" +
            " where r.purchase_order_id = po.purchase_order_id\n" +
            " and po.PGOOD_CLASS = 'H' and po.display_number = ?";

    private static final String ITINERARY_CAR_OPACITY_COMFIRMED_PURCHASE =
            "SELECT po.display_number FROM purchase_order po\n" +
            "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
            "join sold_rental_car sai on sai.pgood_id = r.pgood_id\n" +
            "WHERE po.pgood_class = 'C'\n" +
            "and sai.opacity_code = ?\n" +
            "AND po.total_amount > 0\n" +
            "AND po.status_code = 30030\n";
            //"AND po.PARTNER_ID is NULL";

    private static final String ITINERARY_AIR_OPACITY_COMFIRMED_PURCHASE =
          " select po.display_number from purchase_order po " +
                  "join reservation r on r.purchase_order_id = po.purchase_order_id " +
                  "join sold_air_itinerary sai on sai.pgood_id = r.pgood_id " +
                  "join customer c on po.customer_id = c.customer_id " +
                  "AND po.pgood_class ='A' and sai.opacity_code = ? AND po.status_code = '30030'";

    private static final String ITINERARY_HOTEL_OPACITY_COMFIRMED_PURCHASE =
            "select hw_itinerary_number " +
                    "from view_c3_hotel_reservation_info" +
                    " where is_opaque = ? and status_code = '30030' and pgood_class = 'H'";

    private static final String HOTEL_ID_CONF_NUM_FOR_PURCHASE =
            "select shr.hotel_id, shr.start_date, shr.end_date, po.display_number\n" +
                    "from sold_hotel_room shr, reservation r, purchase_order po\n" +
                    "where shr.create_date > (sysdate - 1)\n" +
                    "  and r.create_date > (sysdate - 1)\n" +
                    "  and po.create_date > (sysdate - 1)\n" +
                    "  and shr.pgood_id = r.pgood_id\n" +
                    "  and r.reservation_id = po.purchase_order_id\n" +
                    "order by shr.hotel_id desc;\n";

    private static final String ITINERARY_WITH_AVC_FAILING =
            "select display_number from Purchase_Order where pgood_class = 'H'" +
                    " and status_code = '30010' order by create_date desc";

    private static final String CAR_RESERVATION_NUMBER =
            "SELECT r.supplier_reservation_number\n" +
                    "from (select reservation_num, count(reservation_num) as counter from reservation\n" +
                    "        group by reservation_num) t1\n" +
                    "JOIN reservation r ON r.reservation_num=t1.reservation_num\n" +
                    "JOIN purchase_order po ON r.purchase_order_id=po.purchase_order_id\n" +
                    "WHERE po.pgood_class = 'C'\n" +
                    "AND t1.counter = 1\n" +
                    "AND r.supplier_reservation_number is not null";

    private static final String AIR_TICKET_NUMBER =
            "SELECT p.ticket_num\n" +
            "FROM\n" +
            "(SELECT reservation_num, COUNT(reservation_num) AS counter " +
                    "FROM reservation GROUP BY reservation_num) t\n" +
            "JOIN reservation r ON r.reservation_num = t.reservation_num\n" +
            "JOIN purchase_order po ON r.purchase_order_id = po.purchase_order_id\n" +
            "JOIN participant p ON p.reservation_id = r.reservation_id\n" +
            "WHERE po.pgood_class = 'A'\n" +
            "AND t.counter = 1\n" +
            "AND t.reservation_num IS NOT NULL\n" +
            "AND p.ticket_num IS NOT NULL";

    private static final String BLOCKED_HOTEL_BY_ITINERARY =
            "select chbs.* from customer_hotel_block_status chbs, purchase_order po\n" +
                    " where chbs.block_status = 'Y'  and po.display_number = ?\n" +
                    " and po.purchase_order_id = chbs.reservation_id";

    private static final String UNBLOCKED_HOTEL_BY_ITINERARY =
            "select chbs.* from customer_hotel_block_status chbs, purchase_order po\n" +
                    " where chbs.block_status = 'N' and po.display_number = ?\n" +
                    " and po.purchase_order_id = chbs.reservation_id";

    private static final String HOTEL_ITINERARY_FOR_BLOCKING =
            "select po.display_number from purchase_order po " +
            "join reservation re on po.purchase_order_id = re.purchase_order_id " +
            "join sold_hotel_room so on re.pgood_id = so.pgood_id " +
            "where so.start_date < sysdate and re.reservation_id " +
            "not in (select reservation_id from customer_hotel_block_status where block_status = 'N') " +
            "and rownum < 2000";

    private static final String SEARCH_ID_BY_EMAIL =
            "select sh.search_id from search sh, customer cu where cu.customer_id = sh.customer_id" +
                    " and cu.email = ?";

    private static final String SEARCH_ID_BY_REFERENCE =
            "select search_id from search_solution where display_number = ?";

    private static final String CUSTOMER_WITH_EXPIRED_HOTDOLLARS =
            "select c.email, b.amount from discount_balance b, customer c where" +
                    " expiration_date < sysdate and amount > 0 and c.customer_id=b.customer_id" +
                    " and c.password ='bd66a2f9fe0d985ae52744962dc53ac0'"; // password = hotwire

    private static final String EXPIRED_HD_FOR_CUSTOMER =
            "select b.amount from discount_balance b, customer c where" +
                    " c.customer_id = b.customer_id and c.email = ?" +
                    " and expiration_date < sysdate and amount > 0  order by b.create_date";

    private static final String EMAIL_WITH_FEW_PURCHASE =
                    "select c.email from (select customer_id, count(customer_id) as customercount " +
                    "from purchase_order where status_code >= '30025' group by customer_id " +
                    "order by count(customer_id)) a, customer c " +
                    "where a.customer_id = c.customer_id " +
                    "and rownum < 2";


    public C3SearchDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    @Override
    public String getAirItinerary() {
        return getValueFromDB(PURCHASE, "A");
    }

    @Override
    public String getCarItinerary() {
        return getValueFromDB(PURCHASE, "C");
    }

    @Override
    public String getHotelItinerary() {
        return getValueFromDB(PURCHASE, "H");
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

    public String getSearchID(String verticalID) {
        return getValueFromDB(PAST_SEARCH, verticalID);
    }

    public Map getHotelWithOversells() {
        return getRowFromDB(HOTEL_OVERSELLS);
    }

    public String getRarelyPassengerLastNameWithPurchases() {
        return getRandomValueFromDB(RARELY_PASSENGER_LAST_NAME_WITH_PURCHASES);
    }

    public String getRarelyPaymentMethodHolderLastNameWithPurchases() {
        return getRandomValueFromDB(RARELY_PAYMENT_METHOD_HOLDER_LAST_NAME_WITH_PURCHASES);
    }

    public String getRarelyCustomerLastName() {
        return getRandomValueFromDB(RARELY_CUSTOMER_LAST_NAME_WITH_PURCHASES);
    }

    public String getEmailWithManyPurchases() {
        return getValueFromDB(EMAIL_WITH_MANY_HOTEL_PURCHASES);
    }

    public String getEmailWithPurchases() {
        return getValueFromDB(EMAIL_WITH_PURCHASES);
    }

    public String getPNR(String verticalCode) {
        return getRandomValueFromDB(VERTICAL_PNR, verticalCode);
    }

    public String getHotelItineraryWithParams(boolean insurance, String opacityCode) {
        if (insurance) {
            return getRandomValueFromDB(HOTEL_PURCHASE_WITH_INSURANCE, opacityCode);
        }
        else {
            return getRandomValueFromDB(HOTEL_PURCHASE_WITHOUT_INSURANCE, opacityCode);
        }
    }

    public String getVerticalOfItinerary(String itinerary) {
        return getValueFromDB("select PGOOD_CLASS from purchase_order where display_number = ? ", itinerary);
    }


    public String getPurchaseWithHotDollars(boolean withHotDollars) {
        if (withHotDollars) {
            return getRandomValueFromDB(HOTEL_PURCHASE_WITH_HOTDOLLARS);
        }
        else {
            return getRandomValueFromDB(HOTEL_PURCHASE_WITHOUT_HOTDOLLARS);
        }
    }

    public String getExistingPhoneNumber() {
        return getRandomValueFromDB(PHONE_WITH_PURCHASE);
    }

    public String getExistingPhoneNumberWithFewPurchase() {
        return getValueFromDB(PHONE_WITH_FEW_PURCHASE);
    }

    public String getItineraryWithIPAddress() {
        return getRandomValueFromDB(ITINERARY_WITH_IP_ADDRESS);
    }

/*
    public Map getOrderIDandDisplayNum() {
        return getRowFromDB(EXISTING_ORDER_ID_AND_DISPLAY_NUM);
    }
    */

    public Map getRecentReferralInfo(String searchId) {
        return getRowFromDB(REFERRAL_INFO, searchId);
    }

    public String getItineraryWithCreditCard() {
        return getRandomValueFromDB(ITINERARY_WITH_CREDIT_CARD);
    }

    public String getReservationNumByItinerary(String itineraryNumber) {
        return getValueFromDB(HOTEL_RESERVATION_BY_ITINERARY, itineraryNumber);
    }

    public String getItineraryForOpacityCarPurchase(String opacity) {
        opacity = (opacity.equals("opaque")) ? "Y" : "N";
        return getRandomValueFromDB(ITINERARY_CAR_OPACITY_COMFIRMED_PURCHASE, opacity);
    }

    public String getItineraryForOpacityAirPurchase(String opacity) {
        opacity = (opacity.equals("opaque")) ? "Y" : "N";
        return getRandomValueFromDB(ITINERARY_AIR_OPACITY_COMFIRMED_PURCHASE, opacity);
    }

    public String getItineraryForOpacityHotelPurchase(String opacity) {
        opacity = (opacity.equals("opaque")) ? "Y" : "N";
        return getRandomValueFromDB(ITINERARY_HOTEL_OPACITY_COMFIRMED_PURCHASE, opacity);
    }

    public Map getHotelIdWithConfirmationNum() {
        return getRandomRowFromDB(HOTEL_ID_CONF_NUM_FOR_PURCHASE);
    }

    public Map getOrderIDandDisplayNum() {
        return getRowFromDB(EXISTING_ORDER_ID_AND_DISPLAY_NUM);
    }

    public String getCustomerWithNoRecentSearch(String productCode) {
        return getRandomValueFromDB(CUSTOMER_WITH_NO_RECENT_SEARCH_VERTICAL, productCode);
    }

    public String getCustomerWithNoRecentSearch() {
        return getRandomValueFromDB(CUSTOMER_WITH_NO_RECENT_SEARCH);
    }

    public String getCustomerWithManyRecentSearch() {
        return getRandomValueFromDB(CUSTOMER_WITH_MANY_RECENT_SEARCH);
    }

    public String getItineraryAVC() {
        return getRandomValueFromDB(ITINERARY_WITH_AVC_FAILING);
    }

    public String getEmailWithoutPurchases() {
        return getRandomValueFromDB(EMAIL_WITHOUT_PURCHASES);
    }

    public String getRandomHotelReservationNumber() { // HOTEL_RESERVATION_NUMBER is the same as Hotel PNR
        return getRandomValueFromDB(VERTICAL_PNR, "H");
    }

    public String getRandomCarReservationNumber() {
        return getRandomValueFromDB(CAR_RESERVATION_NUMBER);
    }

    public String getRandomAirTicketNumber() {
        return getRandomValueFromDB(AIR_TICKET_NUMBER);
    }

    public String getPurchaseWithoutCase(String vertical) {
        return getValueFromDB(PURCHASE_WITHOUT_CASE, vertical.substring(0, 1).toUpperCase());
    }

    public Map getBlockedHotelByItinerary(String itinerary) {
        return getRowFromDB(BLOCKED_HOTEL_BY_ITINERARY, itinerary);
    }

    public Map getUnblockedHotelByItinerary(String itinerary) {
        return getRowFromDB(UNBLOCKED_HOTEL_BY_ITINERARY, itinerary);
    }

    public String getHotelItineraryForBlocking() {
        return getRandomValueFromDB(HOTEL_ITINERARY_FOR_BLOCKING);
    }

    public String getSearchIdByReference(String reference) {
        return getValueFromDB(SEARCH_ID_BY_REFERENCE, reference);
    }

    public String getSearchIdByEmail(String email) {
        return getValueFromDB(SEARCH_ID_BY_EMAIL, email);
    }

    public Map getCustomerWithExpiredHotDollars() {
        return getRowFromDB(CUSTOMER_WITH_EXPIRED_HOTDOLLARS);
    }

    public List<Object> getExpiredHotDollarsForCustomer(String email) {
        return verifyRowsInDB(EXPIRED_HD_FOR_CUSTOMER, email);
    }

    public String getEmailWithFewPurchase() {
        return  getValueFromDB(EMAIL_WITH_FEW_PURCHASE);
    }
}









/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.util.db.c3;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/16/14
 * Time: 11:01 PM
 * Contains SQL methods for getting data for different types of C3 customers and their purchases.
 */
public class C3CustomerDao  extends C3SearchDao {
    private static final String GUEST_PURCHASE =
        "SELECT po.display_number FROM purchase_order po\n" +
        "JOIN customer c on po.customer_id = c.customer_id\n" +
        "WHERE po.pgood_class in  ('H','C')\n" +
        "AND po.status_code in (30025, 30030)\n" +
        "AND po.partner_id is NULL\n" +
        "AND c.email not like '%@expedia.com'\n" +
        "AND c.password is NULL";

    private static final String NON_EXPRESS_PURCHASE =
        "SELECT po.display_number FROM purchase_order po\n" +
        "JOIN customer c on po.customer_id = c.customer_id\n" +
         "join customer_stats cs on c.customer_id = cs.customer_id\n" +
        "WHERE po.pgood_class in  ('H','C')\n" +
        "AND po.status_code in (30025, 30030)\n" +
        "AND po.partner_id is NULL\n" +
        "AND c.email not like '%@expedia.com'\n" +
        "and is_preferred_customer = 'N'\n" +
        "AND c.password is not NULL\n" +
        "AND cs.is_preferred_change_date is null";

    private static final String EX_EXPRESS_PURCHASE =
        "SELECT po.display_number, is_preferred_change_date FROM purchase_order po\n" +
        "JOIN customer c on po.customer_id = c.customer_id\n" +
        "JOIN customer_stats cs on c.customer_id = cs.customer_id\n" +
        "WHERE po.pgood_class in  ('H','C')\n" +
        "AND po.status_code in (30025, 30030)\n" +
        "AND po.partner_id is NULL\n" +
        "AND c.email not like '%@expedia.com'\n" +
        "AND is_preferred_customer = 'N' and is_hfc_blocked = 'N'\n" +
        "AND cs.is_preferred_change_date is not null";

    private static final String EXPRESS_PURCHASE =
        "SELECT po.display_number FROM purchase_order po\n" +
        "JOIN customer c on po.customer_id = c.customer_id\n" +
        "join customer_stats cs on c.customer_id = cs.customer_id\n" +
        "WHERE po.pgood_class in  ('H','C')\n" +
        "AND po.status_code in (30025, 30030)\n" +
        "AND po.partner_id is NULL\n" +
        "AND c.email not like '%@expedia.com'\n" +
        "AND is_preferred_customer = 'Y' and is_hfc_blocked = 'N'\n" +
        "AND c.password is not NULL\n" +
        "AND pricing_group_id = 2";

    private static final String EXPRESS_ELITE_PURCHASE =
        "SELECT po.display_number FROM purchase_order po\n" +
        "JOIN customer c on po.customer_id = c.customer_id\n" +
        "JOIN customer_stats cs on c.customer_id = cs.customer_id\n" +
        "WHERE po.pgood_class in  ('H','C')\n" +
        "AND po.status_code in (30025, 30030)\n" +
        "AND po.create_date > sysdate - 1\n" +
        "AND po.partner_id is NULL\n" +
        "AND c.email not like '%@expedia.com'\n" +
        "AND is_preferred_customer = 'Y' and is_hfc_blocked = 'N'\n" +
        "AND pricing_group_id = 1\n" +
        "AND c.password is not NULL";

    private static final String PARTNER_PURCHASE =
        "SELECT po.display_number FROM purchase_order po\n" +
        "JOIN customer c on po.customer_id = c.customer_id\n" +
        "WHERE po.pgood_class in  ('H','C')\n" +
        "AND po.status_code in (30025, 30030)\n" +
        "AND partner_id = 1";

    private static final String UNSUBSCRIBED_CUSTOMER =
        "select email from customer \n" +
        "where email like '%loadtest%'\n" +
        "and wants_news_letter = 'N'";

    private static final String SUBSCRIBED_CUSTOMER =
            "select email from customer \n" +
            "where wants_news_letter = 'Y' \n" +
            "and email like '%loadtest%'";

    private static final String CUSTOMER_FOR_DEACTIVATION =
        "select email from customer \n" +
        "where email like '%loadtest%'\n" +
        "and is_active = 'Y'";

    private static final String HOT_DOLLARS_AMOUNT =
        "select SUM(amount) from discount_balance db\n" +
        "join customer c on c.CUSTOMER_ID = db.CUSTOMER_ID\n" +
        "where c.email = ?\n" +
        "and EXPIRATION_DATE > current_date";

    private static final String CUSTOMERS_HOT_DOLLARS =
        "select EMAIL, SUM(NVL(AMOUNT, 0)) HOTDOLLARS \n" +
        "from (select customer_id, email from customer where email not like '%@expedia.com' and rownum < ?) c\n" +
        "left join " +
        "(select db.CUSTOMER_ID, db.AMOUNT from discount_balance db where EXPIRATION_DATE > current_date) d\n" +
        "on c.CUSTOMER_ID = d.CUSTOMER_ID\n" +
        "group by c.EMAIL";

    private static final String HOT_DOLLARS_EXP_DATE =
        "select * from \n" +
        "(select db.EXPIRATION_DATE from discount_balance db\n" +
        "join customer c on c.CUSTOMER_ID = db.CUSTOMER_ID\n" +
        "where c.email = ?\n" +
        "order by db.create_date desc )";

    private  static  final String CUSTOMER_EMAIL_FROM_ITINERARY =
        "select email from customer c\n" +
        "join purchase_order po on po.customer_id = c.customer_id\n" +
        "where display_number = ?";

    private  static  final  String CUSTOMER_ITINERARY_FROM_EMAIL =
            "select display_number from purchase_order po \n" +
            "where status_code = '30030' \n" +
            "and po.pgood_class ='H' \n" +
            "and customer_id = (select customer_id from customer where email = ?) " +
            "order by create_date desc";

    private  static  final String FRAUD_ACTIVE_CUSTOMER =
        "select email from fraud_watch_list where is_active='Y'\n" +
        "and email like '%@%'";

    private  static  final String IP_ADDRESS_CUSTOMER =
        "select ip_address from (select ip_address, count(ip_address)\n" +
        "as ip_count from purchase_order where ip_address\n" +
        "is not null and ip_address not like '66.151.155.244'\n" +
        "group by ip_address) where ip_count < 10";

    private  static  final String IP_ADDRESS_CUSTOMER_WITH_FEW_PURCHASE =
            "select po.ip_address from customer c, purchase_order po " +
            "where po.ip_address is not null and po.ip_address not like '66.151.155.244' " +
            "and  c.customer_id = po.customer_id  " +
            "group by po.ip_address  order by count(po.ip_address)";

    private static final String CUSTOMER_ID =
            "select customer_id from customer";

    private static final String CUSTOMER_EMAIL_FROM_CUSTOMER_ID =
            "select email from customer where customer_id = ?";

    private static final String CUSTOMER_EMAIL_FROM_HOTEL_RESERVATION_NUMBER =
            "select email from reservation where reservation_num = ?";

    private static final String CUSTOMER_EMAIL_FROM_AIR_TICKET_NUMBER =
            "select c.email\n" +
                    "from participant p\n" +
                    "join reservation r on p.reservation_id = r.reservation_id\n" +
                    "join purchase_order po on po.purchase_order_id = r.purchase_order_id\n" +
                    "join customer c on c.customer_id = po.customer_id\n" +
                    "where po.pgood_class = 'A'\n" +
                    "and p.ticket_num = ?";

    private static final String CUSTOMER_EMAIL_FROM_CAR_RESERVATION_NUMBER =
            "select c.email\n" +
            "from reservation r\n" +
            "join purchase_order po on po.purchase_order_id = r.purchase_order_id\n" +
            "join customer c on c.customer_id = po.customer_id\n" +
            "where r.pgood_class = 'C'\n" +
            "and r.supplier_reservation_number = ?";

    private static final String CUSTOMER_EMAIL_WITH_HOTDOLLARS =
        "select c.email from discount_balance db\n" +
        "join customer c on c.customer_id = db.customer_id\n" +
        "where db.discount_code = 'HOTDOLLARS' \n" +
        "and db.expiration_date > sysdate \n" +
        "and db.AMOUNT > 5 \n" +
        "and c.PASSWORD is not null \n" +
        "and c.PASSWORD_GROUP is not null";

    private static final String CUSTOMER_EMAIL_WITH__MANY_HOTDOLLARS =
            "select c.email from discount_balance db\n" +
            "join customer c on c.customer_id = db.customer_id\n" +
            "where db.discount_code = 'HOTDOLLARS' \n" +
            "and db.expiration_date > sysdate\n" +
            "and db.AMOUNT > 5000";

    private static final String CUSTOMER_EMAIL_WITH_PURCHASES =
        "select c.*, co.display_name as country from purchase_order po\n" +
        "join customer c on c.CUSTOMER_ID = po.CUSTOMER_ID\n" +
        "join country co on co.country_code = c.REGISTER_COUNTRY_CODE\n" +
        "where po.status_code in (30025, 30030)\n" +
        "and EXTRACT(YEAR FROM po.create_date) = EXTRACT(YEAR FROM sysdate)\n " +
        "AND po.create_date > sysdate - 30\n " +
        "and po.pgood_class = ?\n " +
        "AND po.partner_id is NULL " +
        "and is_active = 'Y'\n" +
        "and rownum < 500";

    private static final String CUSTOMER_NEWS_SUBSCRIPTION =
        "select c.wants_news_letter from customer c where c.email= ?";

    private static final String CUSTOMER_NEWS_SUBSCRIPTION_OPTED =
        "select cds.div_subscription_opted_in_info \n" +
        "from customer_div_subscription cds, customer c \n" +
        "where cds.customer_id = c.customer_id and c.email= ?";

    private static final String CUSTOMER_INFO =
        "select c.*, p.first_name PARTICIPANT_FN, p.last_name PARTICIPANT_LN  from customer c\n" +
        "join purchase_order po on po.customer_id = c.CUSTOMER_ID\n" +
        "join reservation r on r.purchase_order_id = po.purchase_order_id\n" +
        "join participant p on r.reservation_id = p.reservation_id\n" +
        "where po.display_number = ?";

    private static final String CUSTOMER_BY_EMAIL_WITH_FIRST_LAST_NAME =
            "select count(*) from customer where email = ? and first_name = ? and last_name = ?";

    private static final String CUSTOMER_BY_EMAIL_IN_BLACK_LIST =
            "select count(*) from email_list where list_code = 'B' and email = ?";

    private static final String CUSTOMER_WITH_POPULAR_NAME =
            "SELECT FIRST_NAME, LAST_NAME FROM CUSTOMER\n" +
            "GROUP BY LAST_NAME, FIRST_NAME HAVING COUNT(*)>1 AND COUNT(*)<10";

    private static final String CUSTOMER_EMAIL_WITHOUT_SAVED_PAYMENT_METHODS =
            "select c.email\n" +
            "from customer c\n" +
            "left join payment_method pm on c.customer_id = pm.customer_id\n" +
            "where c.password is not null\n" +
            "and pm.customer_id is null\n" +
            "and c.default_payment_method_id is null\n" +
            "and c.is_active = 'Y'";

    private static final String TEST_CUSTOMER =
            "SELECT * FROM CUSTOMER " +
            "WHERE PASSWORD IS NOT NULL " +
            "AND IS_ACTIVE = 'Y' " +
            "AND REGISTER_ZIP = '00000' " +
            "AND EMAIL LIKE 'test\\_hotwire\\_%@hotwire.com' ESCAPE '\\'";

    private static final String CUSTOMER_TRAVELERS =
            "SELECT * FROM TRAVELER t JOIN CUSTOMER c on c.customer_id = t.customer_id WHERE c.email = ?";

    private static final String PAYMENT_RECEIPT_AMOUNT =
            "SELECT po.purchase_order_id, pr.create_date, pr.amount " +
            "FROM payment_receipt pr, purchase_order po " +
            "WHERE pr.purchase_order_id = po.purchase_order_id " +
            "AND po.display_number = ?";

    private static final String  CUSTOMER_WITHOUT_WATCHED_TRIPS =
            "select email from customer where " +
                    "password = 'bd66a2f9fe0d985ae52744962dc53ac0' " + //"hotwire" password
                    "and  customer_id not in (select customer_id from watched_trip)" +
                    "and email not like 'admin_blacklisted%' and IS_ACTIVE = 'Y'";


    private static final String CUSTOMER_REFERRAL_REGISTRATION =
            "select c.customer_id, c.email, rr.referral_type, " +
            "rr.REFERRER_ID, rr.LINK_ID, rr.REFERRAL_COUNTRY_ID, rr.VERSION_ID, " +
            "rr.KEYWORD_ID, rr.MATCH_TYPE_ID, rr.REFERRAL_DATE, rr.CREATE_DATE, rr.UPDATE_DATE " +
            "from customer c, registration_referral rr " +
            "where c.customer_id=rr.customer_id " +
            "and rr.create_date like ? " +
            "and c.email= ? " +
            "order by rr.create_date";

    private static final String CUSTOMER_REFERRAL_SEARCH =
            "select c.email, c.customer_id, sr.search_id, sr.REFERRAL_TYPE, " +
            "sr.REFERRER_ID, sr.LINK_ID, sr.VERSION_ID, sr.KEYWORD_ID, " +
            "sr.MATCH_TYPE_ID, sr.REFERRAL_DATE, sr.CREATE_DATE, sr.UPDATE_DATE, sr.REFERRAL_COUNTRY_ID " +
            "from customer c, search_referral sr, search s " +
            "where c.customer_id=s.customer_id " +
            "and s.search_id=sr.search_id " +
            "and sr.create_date like ? " +
            "and c.email= ? " +
            "order by sr.create_date";

    private static final String CUSTOMER_REFERRAL_PURCHASE =
            "select c.CUSTOMER_ID, c.email, pr.purchase_order_id, " +
            "pr.REFERRAL_TYPE, pr.REFERRER_ID, pr.LINK_ID, pr.VERSION_ID, pr.KEYWORD_ID, " +
            "pr.MATCH_TYPE_ID, pr.REFERRAL_DATE, pr.CREATE_DATE, pr.UPDATE_DATE, pr.REFERRAL_COUNTRY_ID " +
            "from purchase_referral pr, customer c, purchase_order po " +
            "where c.customer_id=po.customer_id " +
            "and po.purchase_order_id=pr.purchase_order_id " +
            "and pr.create_date like ? " +
            "and c.email = ? " +
            "order by pr.create_date";

    public C3CustomerDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public String getGuestPurchase() {
        return getRandomValueFromDB(GUEST_PURCHASE);
    }

    public String getNonExpressPurchase() {
        return getRandomValueFromDB(NON_EXPRESS_PURCHASE);
    }

    public String getExpressPurchase() {
        return getRandomValueFromDB(EXPRESS_PURCHASE);
    }

    public String getExpressElitePurchase() {
        return getRandomValueFromDB(EXPRESS_ELITE_PURCHASE);
    }

    public String getPartnerPurchase() {
        return getRandomValueFromDB(PARTNER_PURCHASE);
    }

    public String getEmailForDeactivation() {
        return getValueFromDB(CUSTOMER_FOR_DEACTIVATION);
    }

    public String getUnsubcribedCustomer() {
        return getRandomValueFromDB(UNSUBSCRIBED_CUSTOMER);
    }

    public String getSubscribedCustomer() {
        return getRandomValueFromDB(SUBSCRIBED_CUSTOMER);
    }

    public boolean doesCustomerHavePassword(String customerEmail) {
        return checkResultInDB("SELECT EMAIL FROM CUSTOMER c WHERE c.password is NOT NULL and c.email = ?",
                customerEmail);
    }

    public String getHotDollarsAmount(String email) {
        waitForDataBaseProcessing(1);
        return getValueFromDB(HOT_DOLLARS_AMOUNT, email);
    }

    public List<Map<String, Object>> getPaymentReceiptAmount(String itinerary) {
        waitForDataBaseProcessing(1);
        return verifyTableInDB(PAYMENT_RECEIPT_AMOUNT, itinerary);
    }

    public DateTime getHotDollarsExpirationDate(String email) {
        return new DateTime(verifyValueInDB(HOT_DOLLARS_EXP_DATE, email).replaceAll("\\s.*$", ""));
    }

    public List<Map<String, Object>> getCustomersEmailsWithHotDollars(Integer numberOfCustomers) {
        return verifyTableInDB(CUSTOMERS_HOT_DOLLARS, String.valueOf(numberOfCustomers + 1));
    }

    public String getEmailOfItinerary(String itinerary) {
        return getValueFromDB(CUSTOMER_EMAIL_FROM_ITINERARY, itinerary);
    }

    public String getItineraryOfEmail(String email) {
        return getValueFromDB(CUSTOMER_ITINERARY_FROM_EMAIL, email);
    }

    public String getCustomerSubscriptions(String customerEmail) {
        waitForDataBaseProcessing(1);
        return verifyValueInDB(CUSTOMER_NEWS_SUBSCRIPTION, customerEmail);
    }

    public String getCustomerSubscriptionOpted(String email) {
        return getValueFromDB(CUSTOMER_NEWS_SUBSCRIPTION_OPTED, email);
    }

    public Map getExExpressPurchase() {
        return getRowFromDB(EX_EXPRESS_PURCHASE);
    }

    public String getActiveFraudEmail() {
        return getRandomValueFromDB(FRAUD_ACTIVE_CUSTOMER);
    }

    public String getIPAddress() {
        return getValueFromDB(IP_ADDRESS_CUSTOMER);
    }

    public String getIPAddressWithFewPurchase() {
        return getValueFromDB(IP_ADDRESS_CUSTOMER_WITH_FEW_PURCHASE);
    }

    public String getCustomerId() {
        return getRandomValueFromDB(CUSTOMER_ID);
    }

    public String getEmailByCustomerId(String customerId) {
        return getValueFromDB(CUSTOMER_EMAIL_FROM_CUSTOMER_ID, customerId);
    }

    public String getEmailByHotelReservationNumber(String reservationNumber) {
        return getValueFromDB(CUSTOMER_EMAIL_FROM_HOTEL_RESERVATION_NUMBER, reservationNumber);
    }

    public String getEmailByAirTicketNumber(String ticketNumber) {
        return getValueFromDB(CUSTOMER_EMAIL_FROM_AIR_TICKET_NUMBER, ticketNumber);
    }

    public String getEmailByCarReservationNumber(String reservationNumber) {
        return getValueFromDB(CUSTOMER_EMAIL_FROM_CAR_RESERVATION_NUMBER, reservationNumber);
    }

    public String getCustomerEmailWithHotDollars() {
        return getRandomValueFromDB(CUSTOMER_EMAIL_WITH_HOTDOLLARS);
    }

    public String getCustomerEmailWithManyHotDollars() {
        return getRandomValueFromDB(CUSTOMER_EMAIL_WITH__MANY_HOTDOLLARS);
    }

    public Map getCustomerWithPurchases(String productChar) {
        return getRandomRowFromDB(CUSTOMER_EMAIL_WITH_PURCHASES, productChar);
    }


    public Map getCustomerByItinerary(String itinerary) {
        return getRowFromDB(CUSTOMER_INFO, itinerary);
    }

    public boolean doesCustomerInDB(String email, String firstName, String lastName) {
        waitForDataBaseProcessing(1);
        return getValueFromDB(CUSTOMER_BY_EMAIL_WITH_FIRST_LAST_NAME, email, firstName, lastName).equalsIgnoreCase("1");
    }

    public boolean doesCustomerInBlackList(String email) {
        return getValueFromDB(CUSTOMER_BY_EMAIL_IN_BLACK_LIST, email).equalsIgnoreCase("1");
    }

    public String getCustomerWithUniqueName() {
        return getValueFromDB("SELECT last_name FROM CUSTOMER WHERE UPPER(last_name) in (\n" +
                                    "SELECT UPPER(LAST_NAME) as LAST_NAME\n" +
                                    "FROM CUSTOMER\n" +
                                    "WHERE REGEXP_LIKE (LAST_NAME, '^[^ 0-9]')\n" +
                                    "GROUP BY UPPER(LAST_NAME) HAVING COUNT(*)=1)");
    }

    public Map getCustomerByEmail(String email) {
        return getRowFromDB("SELECT * FROM CUSTOMER WHERE EMAIL = ?", email);
    }

    public Map getCustomerByUniqueLastName(String lastName) {
        return getRowFromDB("SELECT * FROM CUSTOMER WHERE LAST_NAME = ?", lastName);
    }

    public Map getCustomerWithPopularName() {
        return getRowFromDB(CUSTOMER_WITH_POPULAR_NAME);
    }

    public String getCustomerEmailWithoutSavedPaymentMethods() {
        return getRandomValueFromDB(CUSTOMER_EMAIL_WITHOUT_SAVED_PAYMENT_METHODS);
    }

    public boolean isTestCustomerExist() {
        return checkResultInDB(TEST_CUSTOMER);
    }

    public Map getTestCustomer() {
        return getRowFromDB(TEST_CUSTOMER);
    }

    public String  getCustomerWithoutWatchedTrips() {
        return getValueFromDB(CUSTOMER_WITHOUT_WATCHED_TRIPS);
    }

    public boolean isCustomerHasTravelers(String email) {
        return checkResultInDB(CUSTOMER_TRAVELERS, email);
    }

    public Map getCustomerReferralRegistration(String data, String email) {
        waitForDataBaseProcessing(1);
        return getRowFromDB(CUSTOMER_REFERRAL_REGISTRATION, data, email);
    }

    public Map getCustomerReferralSearch(String data, String email) {
        waitForDataBaseProcessing(15); //such huge delay is really needed  for qa db
        return getRowFromDB(CUSTOMER_REFERRAL_SEARCH, data, email);
    }

    public Map getCustomerReferralPurchase(String data, String email) {
        waitForDataBaseProcessing(1);
        return getRowFromDB(CUSTOMER_REFERRAL_PURCHASE, data, email);
    }
}

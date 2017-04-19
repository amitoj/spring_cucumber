/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.util.db.c3;

import com.hotwire.util.db.c3.service.C3RefundDao;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import java.util.Map;

/**
 * Database Acess Object that we use for refund/recovery scenarios
 *
 * Explanation of common SQL lines:
 * ░rownum = 1 ░ get only first result ░ <br/>
 * ░pgood_class ░vertical name (Car, Hotel, Air)░  <br/>
 * ░c.email not like '%@expedia.com' ░ filtering emails that is used for live bookings░ <br/>
 * ░po.status_code = '30030'  ░30030 code means that this purchase is confirmed   ░<br/>
 * ░po.site_id = 1  ░US/Canada purchase   ░<br/>
 * ░r.hw_credit_card_id != -10 ░ this purchase is could be refunded ░  <br/>
 * ░sa.start_date > sysdate ░ booking check-in date is in the future ░   <br/>
 * ░po.create_date > sysdate - 30 ░ purchase was made not less than 30 days ago░  <br/>
 *
 */
public class C3RefundRecoveryCustomerDao extends C3RefundDao {

    private static final String ALLOWED_RECOVERY_YES =
        "select c.email " +
        "from customer c, customer_stats cs, customer_lifetime_value clv " +
        "where c.customer_id = cs.customer_id and cs.customer_id = clv.customer_id " +
        "and cs.is_preferred_customer = 'Y' and cs.is_hfc_blocked = 'N' and " +
        "pricing_group_id =1";

    private static final String ALLOWED_RECOVERY_NO =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        "JOIN customer_stats cs ON c.customer_id = cs.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_hotel_room sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='H' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 30 " +
        "AND po.site_id = 1 " +
        "AND po.PARTNER_ID is NULL\n" +
        "AND c.email not like '%@expedia.com' " +
        "AND r.quantity > 1 " +
        "AND sa.start_date > sysdate " +
        "AND r.hw_credit_card_id != -10 " +
        "AND cs.is_preferred_customer = 'Y' AND cs.is_hfc_blocked = 'N'";

    private static final String ALLOWED_RECOVERY_NOT_DISPLAYED =
        "SELECT po.display_number FROM purchase_order po " +
        "JOIN customer c on po.customer_id = c.customer_id " +
        " JOIN customer_stats cs ON c.customer_id = cs.customer_id " +
        "JOIN reservation r on r.purchase_order_id = po.purchase_order_id " +
        "JOIN sold_hotel_room sa on sa.pgood_id = r.pgood_id " +
        "WHERE po.pgood_class ='H' " +
        "AND po.status_code = '30030' " +
        "AND po.create_date > sysdate - 30 " +
        "AND po.site_id = 1 " +
        "AND c.email not like '%@expedia.com' " +
        "AND po.PARTNER_ID is NULL\n" +
        "AND r.quantity > 1 " +
        "AND r.hw_credit_card_id != -10 " +
        "AND sa.start_date > sysdate " +
        "AND (cs.is_preferred_customer = 'N' OR cs.is_hfc_blocked = 'Y')";

    private static final String STATUS_CODE_AND_AMOUNT_FOR_PARTIAL_REFUNDED_PURCHASE =
        "select status_code, amount from (select pr.* " +
        "from purchase_order po, payment_receipt pr\n" +
        "where po.purchase_order_id = pr.purchase_order_id\n" +
        "and po.display_number = 'REPLACE_ITINERARY'\n" +
        "order by pr.create_date desc) where rownum = 1";

    private static final String HOTEL_ITINERARY_ID_REFUNDED_BY_CUSTOMER =
            "SELECT po.display_number\n" +
            "from purchase_order po\n" +
            "join  refund_request rr on po.purchase_order_id = rr.purchase_order_id\n" +
            "where rr.is_self_service = 'Y'\n" +
            "and po.pgood_class = 'H'";

    private static final String CAR_ITINERARY_ID_REFUNDED_BY_CSR =
            "SELECT po.display_number\n" +
            "from purchase_order po\n" +
            "join  refund_request rr on po.purchase_order_id = rr.purchase_order_id\n" +
            "where rr.is_self_service = 'N'\n" +
            "and po.status_code = '30060'\n" +
            "and rr.is_rebooked = 'N'\n" +
            "and po.pgood_class = 'C'";

    private static final String HOTEL_ITINERARY_ID_REFUNDED_BY_CSR =
            "SELECT po.display_number\n" +
            "from purchase_order po\n" +
            "join  refund_request rr on po.purchase_order_id = rr.purchase_order_id\n" +
            "where rr.is_self_service = 'N'\n" +
            "and rr.is_rebooked = 'N'\n" +
            "and po.status_code = '30060'\n" +
            "and po.pgood_class = 'H'";

    private static final String STATUS_CODE_FOR_ITINERARY =
            "SELECT status_code\n" +
            "FROM purchase_order\n" +
            "WHERE display_number = ?";

    private static final String VERIFY_CPV_OVERRIDDEN_PURCHASE_FOR_ITINERARY =
            "select status_code, amount, hotwire_cpv_status_code \n" +
            "from (select * from (select pr.* from purchase_order po, payment_receipt pr " +
            "where po.purchase_order_id = pr.purchase_order_id and po.display_number = ? " +
            "order by pr.create_date desc) where STATUS_CODE = 30200 and  AMOUNT = 1 " +
            "and HOTWIRE_CPV_STATUS_CODE = 30325)";

    private static final String VERIFY_AVS_OVERRIDDEN_PURCHASE_FOR_ITINERARY =
            "select status_code, amount, hotwire_avs_status_code \n" +
                    "from (select * from (select pr.* from purchase_order po, payment_receipt pr " +
                    "where po.purchase_order_id = pr.purchase_order_id and po.display_number = ? " +
                    "order by pr.create_date desc) where STATUS_CODE = 30200 and  AMOUNT = 1 " +
                    "and HOTWIRE_AVS_STATUS_CODE = 30260)";

    private static final String VERIFY_AVS_OVERRIDDEN_PURCHASE_FOR_ITINERARY_WITH_AMOUNT =
            "select status_code, amount \n" +
                    "from (select * from (select pr.* from purchase_order po, payment_receipt pr " +
                    "where po.purchase_order_id = pr.purchase_order_id and po.display_number = ? " +
                    "order by pr.create_date desc) where STATUS_CODE = 30400 and  AMOUNT = ? )";

    public C3RefundRecoveryCustomerDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public String getCustomerEmailWithAllowedRecoveryYes() {
        return getValueFromDB(ALLOWED_RECOVERY_YES);
    }

    public String getItineraryWithAllowedRecoveryNo() {
        return getValueFromDB(ALLOWED_RECOVERY_NO);
    }

    public String getItinararyWithAllowedRecoveryNotDisplayed() {
        return  getValueFromDB(ALLOWED_RECOVERY_NOT_DISPLAYED);
    }

    public Map getStatusCodeAndAmountForRefundedPurchase(String itinerary) {
        String sql = STATUS_CODE_AND_AMOUNT_FOR_PARTIAL_REFUNDED_PURCHASE.replace("REPLACE_ITINERARY", itinerary);
        return getRowFromDB(sql);
    }

    public String getCarItineraryRefundedByCSR() {
        return getValueFromDB(CAR_ITINERARY_ID_REFUNDED_BY_CSR);
    }

    public String getHotelItineraryRefundedByCSR() {
        return getValueFromDB(HOTEL_ITINERARY_ID_REFUNDED_BY_CSR);
    }

    public String getHotelItineraryRefundedByCustomer() {
        return getValueFromDB(HOTEL_ITINERARY_ID_REFUNDED_BY_CUSTOMER);
    }

    public String getStatusCode(String itinerary) {
        return getValueFromDB(STATUS_CODE_FOR_ITINERARY, itinerary);
    }

    public Map getStatusCodeAmountAndCpvStatusCodeForOverriddenPurchase(String itinerary) {
        return getRowFromDB(VERIFY_CPV_OVERRIDDEN_PURCHASE_FOR_ITINERARY, itinerary);
    }

    public Map getStatusCodeAmountAndAvsStatusCodeForOverriddenPurchase(String itinerary) {
        return getRowFromDB(VERIFY_AVS_OVERRIDDEN_PURCHASE_FOR_ITINERARY, itinerary);
    }

    public Map getStatusCodeAndAmountForOverriddenPurchase(String itinerary, String amount) {
        return getRowFromDB(VERIFY_AVS_OVERRIDDEN_PURCHASE_FOR_ITINERARY_WITH_AMOUNT, itinerary, amount);
    }
}

/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.mobileapi;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.hotwire.util.db.AbstractDao;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: v-dsobko
 * Since: 01/12/15
 */
public class MobileApiDao extends AbstractDao {

    private static final String CUSTOMER_ID_BY_EMAIL =
            "select customer_id from customer where email = ?";

    private static final String RANDOM_ACTIVE_CUSTOMER_ID =
            "select email from customer where is_active = 'Y'";

    private static final String RANDOM_ITINERARY =
            "select display_number from purchase_order where pgood_class = ? and status_code >= 30020\n" +
            "and customer_id NOT IN (select customer_id from customer where email = ?)";

    private static final String ITINERARY_FROM_RANDOM_CUSTOMER =
            "select display_number from purchase_order where pgood_class = ? and status_code >= 30020\n" +
            "and customer_id = (select customer_id from customer where email = ?)";

    private static final String RANDOM_CUSTOMER_EMAIL =
             "SELECT email FROM (SELECT c.email AS email, COUNT (po.purchase_order_id) purch_count\n" +
             "            FROM purchase_order po, customer c\n" +
             "            WHERE c.customer_id = po.customer_id\n" +
             "                 AND po.status_code >= ?\n" +
             "                 AND po.partner_id IS NULL\n" +
             "                 AND c.is_active = 'Y'\n" +
             "                 AND c.password = 'bd66a2f9fe0d985ae52744962dc53ac0'\n" +
             "                 AND EXISTS\n" +
             "                        (SELECT 1\n" +
             "                           FROM purchase_order po_h\n" +
             "                          WHERE     c.customer_id = po_h.customer_id\n" +
             "                                AND po_h.partner_id IS NULL\n" +
             "                                AND po_h.status_code >= ?\n" +
             "                                AND po_h.pgood_class = 'H')\n" +
             "                 AND EXISTS\n" +
             "                        (SELECT 1\n" +
             "                           FROM purchase_order po_c\n" +
             "                          WHERE     c.customer_id = po_c.customer_id\n" +
             "                                AND po_c.partner_id IS NULL\n" +
             "                                AND po_c.status_code >= ?\n" +
             "                                AND po_c.pgood_class = 'C')\n" +
             "        GROUP BY c.email, po.customer_id)\n" +
             " WHERE purch_count BETWEEN 6 AND 100";

    private static final String SAVED_CREDIT_CARD_NICK_NAME =
        "select p.logical_name from payment_method p join credit_card c\n" +
        "on p.payment_method_id = c.payment_method_id\n" +
        "where p.customer_id = (select customer_id from customer where email = ?) and p.type_code = 'C'\n" +
        "and p.last_four_digits = '1111' and c.exp_month is not null and c.exp_year is not null";

    private static final String CURRENCY_CODES_FOR_DOMESTIC_POS =
        "select currency_code from currency WHERE is_active_domestic = 'Y'";

    private static final String CURRENCY_CODES_FOR_INTERNATIONAL_POS =
        "select currency_code from currency WHERE is_active_international = 'Y'";


    public MobileApiDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public String getCustomerId(String email) {
        return getValueFromDB(CUSTOMER_ID_BY_EMAIL, email);
    }

    public String getRandomItinerary(String vertical, String email) {
        return getValueFromDB(RANDOM_ITINERARY, vertical, email);
    }

    public String getItineraryFromRandomCustomer(String vertical, String email) {
        return getValueFromDB(ITINERARY_FROM_RANDOM_CUSTOMER, vertical, email);
    }

    public String getRandomCustomerEmail(Integer statusCode) {
        return getRandomValueFromDB(RANDOM_CUSTOMER_EMAIL, String.valueOf(statusCode),
                String.valueOf(statusCode), String.valueOf(statusCode));
    }

    public String getSavedCreditCardNickName(String email) {
        return getValueFromDB(SAVED_CREDIT_CARD_NICK_NAME, email);
    }

    public List<String> getCurrencyCodesForDomesticPos() {
        return new ArrayList<>(Collections2.transform(verifyRowsInDB(CURRENCY_CODES_FOR_DOMESTIC_POS),
                new Function<Object , String>() {
                @Nullable
                @Override
                public String apply(@Nullable Object o) {
                    return String.valueOf(o);
                }
            }));
    }

    public List<String > getCurrencyCodesForInternationalPos() {
        return new ArrayList<>(Collections2.transform(verifyRowsInDB(CURRENCY_CODES_FOR_INTERNATIONAL_POS),
                new Function<Object , String>() {
                @Nullable
                @Override
                public String apply(@Nullable Object o) {
                    return String.valueOf(o);
                }
            }));
    }
}

/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 8/14/14
 * Time: 2:11 AM
 * Database operation for insurance verification
 */
public class InsuranceDao extends AbstractDao {
    private static final String INSURANCE_INFO =
        "SELECT P.FIRST_NAME, P.LAST_NAME, P.TP_INSURANCE_POLICY_NUMBER FROM PARTICIPANT P\n" +
        "JOIN RESERVATION R ON R.RESERVATION_ID = P.RESERVATION_ID\n" +
        "JOIN PURCHASE_ORDER PO ON PO.PURCHASE_ORDER_ID =R.PURCHASE_ORDER_ID\n" +
        "WHERE DISPLAY_NUMBER = ?";

    public InsuranceDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public Map getInsuranceNumber(String hotwireConfirmationNum) {
        return getRowFromDB(INSURANCE_INFO, hotwireConfirmationNum);
    }
}

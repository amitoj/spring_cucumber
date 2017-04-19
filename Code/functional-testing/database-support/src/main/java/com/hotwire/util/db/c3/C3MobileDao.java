/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 6/3/14
 * Time: 6:31 AM
 * This class is for getting mobile purchases from DB
 */
public class C3MobileDao extends C3SearchDao {
    private static final String MOBILE_PURCHASE =
        "SELECT DISPLAY_NUMBER, DEVICE_TYPE, SITE_TYPE FROM PURCHASE_ORDER\n" +
        "WHERE device_type = ?\n" +
        "AND PARTNER_ID is NULL\n" +
        "AND status_code > 30025";

    private static final String DESKTOP_PURCHASE =
        "SELECT DISPLAY_NUMBER FROM PURCHASE_ORDER\n" +
        "WHERE device_type NOT in ('1000', '1050', '1020', '0', '1100', '1040', '1200')\n" +
        "AND PARTNER_ID is NULL\n" +
        "AND status_code > 30025";

    private Map<String, String> mobileDBCodes = new HashMap<String, String>()
    {
        {
            put("ANDROID", "1050");
            put("IPHONE", "1020");
            put("IPAD", "1000");
            put("OTHER", "0");
            put("TABLET", "1100");
            put("KINDLE", "1040");
        }
    };

    public C3MobileDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public Map getMobilePurchase(String deviceType) {
        return getRandomRowFromDB(MOBILE_PURCHASE, mobileDBCodes.get(deviceType.toUpperCase()));
    }

    public String getDesktopPurchase() {
        return getRandomValueFromDB(DESKTOP_PURCHASE);
    }
}

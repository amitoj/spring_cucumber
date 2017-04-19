/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.dmu;

import com.hotwire.util.db.AbstractDao;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 9/5/14
 * Time: 5:20 AM
 * SQL queries for Data Management Tools procedures verification
 */
public class DmuDao extends AbstractDao {
    private static final String CAR_VENDOR_PARTNER = "select is_partner from car_vendor where CAR_VENDOR_CD = ?";

    public DmuDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public String getCarVendorPartnerStatus(String partnerCode) {
        return getValueFromDB(CAR_VENDOR_PARTNER, partnerCode);
    }

}

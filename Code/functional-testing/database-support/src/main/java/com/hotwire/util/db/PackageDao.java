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
 * Created by v-sshubey on 3/3/2015.
 */
public class PackageDao extends AbstractDao {
    private static final String PACKAGE_SEARCH_PARAMETERS_BY_EMAIL =
            "select customer_id, create_date, class, bundled_code, package_destination_id, " +
                   "travel_start_time, travel_end_time, dest_city_id " +
            "from search " +
            "where create_date > sysdate -1/1440 " +
            "and customer_id = (select customer_id from CUSTOMER where email = ?)" +
            "order by create_date desc";

    public PackageDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public Map getPackageSearchParametersByCustomer(String email) {
        return getRowFromDB(PACKAGE_SEARCH_PARAMETERS_BY_EMAIL, email);
    }
}

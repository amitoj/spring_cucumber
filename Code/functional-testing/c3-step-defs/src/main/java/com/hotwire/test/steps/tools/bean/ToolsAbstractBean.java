/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 7/18/14
 * Time: 5:06 AM
 * This been provide Database connection to all beans
 */
public class ToolsAbstractBean {

    @Autowired
    private SimpleJdbcDaoSupport databaseSupport;

    public SimpleJdbcDaoSupport getDataBaseConnection() {
        return databaseSupport;
    }

    public String getValue(Map row, String value) {
        if (row.get(value) != null) {
            return row.get(value).toString();
        }
        else {
            return "";
        }
    }

}

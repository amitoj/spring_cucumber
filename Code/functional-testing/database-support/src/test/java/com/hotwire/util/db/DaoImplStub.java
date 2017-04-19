/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * Database access objects that  checks database connection and verify that purchase order table
 * is accessible and not empty
 *
 */
public class DaoImplStub extends AbstractDao{

    protected DaoImplStub(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }
}

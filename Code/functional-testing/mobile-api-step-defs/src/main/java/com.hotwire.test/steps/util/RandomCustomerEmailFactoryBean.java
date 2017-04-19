/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.util;

import com.hotwire.util.db.mobileapi.MobileApiDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

/**
 * User: v-dsobko
 * Since: 01/20/15
 */
public class RandomCustomerEmailFactoryBean extends AbstractDatabaseSupportFactoryBean<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomCustomerEmailFactoryBean.class.getName());
    private Integer statusCodeGreater;

    public void setStatusCodeGreater(Integer statusCodeGreater) {
        this.statusCodeGreater = statusCodeGreater;
    }

    @Override
    public String getObject() throws Exception {
        try {
            return  new MobileApiDao(databaseSupport).getRandomCustomerEmail(statusCodeGreater);
        }
        catch (CannotGetJdbcConnectionException e) {
            LOGGER.error("Exception during connection to DB " + e);
            return super.getObject();
        }
    }
}


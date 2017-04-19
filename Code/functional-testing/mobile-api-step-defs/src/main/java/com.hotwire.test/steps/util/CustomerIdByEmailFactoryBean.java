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
 * Since: 01/12/15
 */
public class CustomerIdByEmailFactoryBean extends AbstractDatabaseSupportFactoryBean<Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerIdByEmailFactoryBean.class.getName());

    private String emailId;

    @Override
    public Long getObject() throws Exception {
        try {
            return  Long.valueOf(new MobileApiDao(databaseSupport).getCustomerId(emailId));
        }
        catch (CannotGetJdbcConnectionException e) {
            LOGGER.error("Exception during connection to DB " + e);
            return super.getObject();
        }
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}

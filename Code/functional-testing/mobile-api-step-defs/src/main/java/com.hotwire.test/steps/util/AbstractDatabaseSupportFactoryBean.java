/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.util;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * @author v-vbychkovskyy
 * @param <T> default value type
 */
public abstract class AbstractDatabaseSupportFactoryBean<T> implements FactoryBean<T> {

    protected SimpleJdbcDaoSupport databaseSupport;
    private T defaultValue;

    @Override
    public T getObject() throws Exception {
        return defaultValue;
    }

    @Override
    public Class<?> getObjectType() {
        return defaultValue.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDatabaseSupport(SimpleJdbcDaoSupport databaseSupport) {
        this.databaseSupport = databaseSupport;
    }
}

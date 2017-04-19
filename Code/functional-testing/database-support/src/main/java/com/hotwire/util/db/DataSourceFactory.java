/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.util.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * User: v-vzyryanov
 * Date: 12/11/14
 * Time: 3:45 AM
 */
public class DataSourceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceFactory.class);

    private Map<String, String> profiles;
    private String username;
    private String password;

    private String env;

    public DataSourceFactory(Map<String, String> profiles) {
        this.profiles = profiles;
    }

    public DriverManagerDataSource getOracleDataSource() {

        if (!profiles.containsKey(env)) {
            throw new RuntimeException("Environment is not specified for " + env);
        }

        String connectionUrl = isJslave14() ? profiles.get("qaci") : profiles.get(env);
//        String connectionUrl = profiles.get(env);
        LOGGER.info("DB connection: user='{}' url='{}'", username, connectionUrl);

        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        source.setUrl(connectionUrl);
        source.setUsername(username);
        source.setPassword(password);

        return source;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    private boolean isJslave14() {
        try {
            String pc = java.net.InetAddress.getLocalHost().getHostName();
            LOGGER.info("Host name={}", pc);
            return pc.contains("jslave14");
        }
        catch (UnknownHostException e) {
            return false;
        }
    }
}

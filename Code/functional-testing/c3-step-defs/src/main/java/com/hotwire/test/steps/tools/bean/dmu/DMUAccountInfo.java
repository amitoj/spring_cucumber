/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean.dmu;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;

/**
 * Bean contains DMU related information
 */
public class DMUAccountInfo extends ToolsAbstractBean {
    private String username;
    private String password;
    private String host;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }
}

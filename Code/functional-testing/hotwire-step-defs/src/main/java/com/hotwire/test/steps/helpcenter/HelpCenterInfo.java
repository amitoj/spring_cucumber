/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.helpcenter;

import java.io.Serializable;

/**
 * User: v-ozelenov
 * Date: 1/14/14
 */
public class HelpCenterInfo implements Serializable {
    private String servicePhone;
    private String salesPhone;
    private String rowPhone;
    private String expressEmail;
    private String usExpressPhone;
    private String rowExpressPhone;
    private String articleCategory;
    private boolean intl;

    /**
     * Switching all smart contact modules in SalesForce Admin
     */
    public enum CONTACTS_MODE { ON, OFF }

    private CONTACTS_MODE mode;
    private boolean isExpressSession;

    public boolean isIntl() {
        return intl;
    }

    public void setIntl(boolean intl) {
        this.intl = intl;
    }

    public void setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory;
    }

    public String getArticleCategory() {
        return articleCategory;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setSalesPhone(String salesPhone) {
        this.salesPhone = salesPhone;
    }

    public String getSalesPhone() {
        return salesPhone;
    }

    public void setRowPhone(String rowPhone) {
        this.rowPhone = rowPhone;
    }

    public String getRowPhone() {
        return rowPhone;
    }

    public void setExpressEmail(String expressEmail) {
        this.expressEmail = expressEmail;
    }

    public String getExpressEmail() {
        return expressEmail;
    }

    public void setUsExpressPhone(String usExpressPhone) {
        this.usExpressPhone = usExpressPhone;
    }

    public String getUsExpressPhone() {
        return usExpressPhone;
    }

    public void setRowExpressPhone(String rowExpressPhone) {
        this.rowExpressPhone = rowExpressPhone;
    }

    public String getRowExpressPhone() {
        return rowExpressPhone;
    }


    public boolean isExpressSession() {
        return isExpressSession;
    }

    public void setExpressSession(boolean expressSession) {
        isExpressSession = expressSession;
    }

    public CONTACTS_MODE getMode() {
        return mode;
    }

    public void setMode(CONTACTS_MODE mode) {
        this.mode = mode;
    }

}

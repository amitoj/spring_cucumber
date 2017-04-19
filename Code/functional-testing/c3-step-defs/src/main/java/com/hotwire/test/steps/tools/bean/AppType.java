/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.bean;

import com.hotwire.testing.UnimplementedTestException;

import java.io.Serializable;


/**
* This enum contains application types supported by tools module
 *
 */
public enum AppType implements Serializable {
    C3("/ccc", "customer care central", "c3"),
    US("/us/index.jsp", "united states hotwire", "domestic"),
    UK("/uk", "united kingdom", "international"),
    IE("/ie", "ireland hotwire", "ireland"),
    DMU("/dmu", "data management utility", "dmu"),
    CMS("/", "content management system", "cms");


    private String appPath;
    private String appFullName;
    private String appCommonName;

    AppType(String appPath, String appFullName, String appCommonName) {
        this.appPath = appPath;
        this.appFullName = appFullName;
        this.appCommonName = appCommonName;
    }

    public static AppType validate(String applicationType) {
        for (AppType t : AppType.values()) {
            if (String.valueOf(t.appPath).equals(applicationType)) {
                return t;
            }
            else if (String.valueOf(t.appPath).equals(applicationType.toLowerCase())) {
                return t;
            }
            else if (String.valueOf(t.appFullName).equals(applicationType.toLowerCase())) {
                return t;
            }
            else if (String.valueOf(t.appCommonName).equals(applicationType.toLowerCase())) {
                return t;
            }
        }
        throw new UnimplementedTestException("Not expected application type: " + applicationType);
    }

    public String getAppPath() {
        return appPath;
    }

    public String getAppFullName() {
        return appFullName;
    }

    public String getAppCommonName() {
        return appCommonName;
    }

}

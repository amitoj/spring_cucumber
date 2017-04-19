/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean.c3;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 6/3/14
 * Time: 6:32 AM
 * For purchases made on mobile devices
 *
 */
public class C3MobileInfo extends ToolsAbstractBean {
    private String applicationType;
    private DeviceTypes deviceType;
    private Map mobileAppCodes;

    /**
     * Customer device type or operation system
     */
    public enum DeviceTypes { ANDROID, IPHONE, IPAD, OTHER, TABLET }

    public DeviceTypes getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String device) {
        this.deviceType = DeviceTypes.valueOf(device.toUpperCase());
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Map getMobileAppCodes() {
        return mobileAppCodes;
    }

    public void setMobileAppCodes(Map mobileAppCodes) {
        this.mobileAppCodes = mobileAppCodes;
    }
}

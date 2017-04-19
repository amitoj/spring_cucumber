/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean.dmu;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;

/**
 * Created with IntelliJ IDEA.
 * User: ozelenov
 * Date: 9/5/14
 * Time: 9:35 AM
 * Contains all fields for DMU verification.
 */
public class DMUProcedureInfo extends ToolsAbstractBean {
    private String procedureName;
    private String carVendor;

    private String carVendorPartnerStatus;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }


    public void setCarVendor(String carVendor) {
        this.carVendor = carVendor;
    }

    public String getCarVendor() {
        return carVendor;
    }

    public String getCarVendorPartnerStatus() {
        return carVendorPartnerStatus;
    }

    public String getNewPartnerStatus() {
        if (getCarVendorPartnerStatus().equals("Y")) {
            return "N";
        }
        else {
            return "Y";
        }
    }

    public void setCarVendorPartnerStatus(String carVendorPartnerStatus) {
        this.carVendorPartnerStatus = carVendorPartnerStatus;
    }
}

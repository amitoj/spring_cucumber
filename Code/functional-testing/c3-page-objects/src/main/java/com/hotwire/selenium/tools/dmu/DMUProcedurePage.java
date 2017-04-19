/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.dmu;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: ozelenov
 * Date: 9/5/14
 * Time: 10:04 AM
 * DMU Procedure Home Page
 */
public class DMUProcedurePage extends ToolsAbstractPage {
    public DMUProcedurePage(WebDriver webdriver) {
        super(webdriver, By.name("operationForm"));
    }

    public String getTitle() {
        return findOne("body>h1").getText();
    }

    public void chooseCarVendor(String vendorCode) {
        //select Ada vendor. Very rarely used vendor
        selectValue("select[name='param_Vendor']", vendorCode);
    }

    public void choseCarVendorPartnerStatus(String statusCode) {
        selectValue("select[name='param_Is Partner?']", statusCode);
    }

    public void setDMUScriptName(String scriptName) {
        setText("input[name='scriptName']", scriptName);
    }

    public void submit() {
        findOne("input[name='run']").click();
    }
}

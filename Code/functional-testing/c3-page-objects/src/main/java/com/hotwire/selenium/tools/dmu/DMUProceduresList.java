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
 * User: v-ozelenov
 * Date: 9/5/14
 * Time: 4:55 AM
 * List of procedure results
 */
public class DMUProceduresList extends ToolsAbstractPage {
    public DMUProceduresList(WebDriver webdriver) {
        super(webdriver, By.cssSelector("a[href*='enterParameters.do?operationId']"));
    }

    public void clickOnProcedureLink(String procedureName) {
        clickOnLink(procedureName, DEFAULT_WAIT);
    }
}

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
 * Time: 5:43 AM
 * Procedure confirmation page
 */
public class DMUProcedureConfirmationPage extends ToolsAbstractPage {
    public DMUProcedureConfirmationPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("input[type='submit'][value='Send Output to DBA']"));
    }

    public void submit() {
        findOne("input[type='submit'][value='Run & Commit on Non Prod Environments']").click();
    }
}

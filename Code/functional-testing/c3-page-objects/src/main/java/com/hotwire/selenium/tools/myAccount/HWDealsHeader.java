/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.myAccount;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-vyulun
 * Date: 9/24/14
 * Time: 2:32 AM
 * Hotwire WebApp Deals Header. for Csrcroz13 CSR.
 */
public class HWDealsHeader extends ToolsAbstractPage {
    static String INPUT_XID = ".message.long>input";
    static String SELECT_XID = "select[name=xid]";

    public HWDealsHeader(WebDriver webDriver) {
        super(webDriver, By.cssSelector("#tileName-queries>form>select"));
    }

    public boolean checkDealMaintenanceTab(String vertical) {
        return isElementDisplayed(By.xpath(".//a[text()='" + vertical + " Deal Maintenance']"));
    }

    public void clickDealMaintenanceTab(String vertical) {
        clickOnLink(vertical + " Deal Maintenance", DEFAULT_WAIT);
    }

    public void clickPreviewButton() {
        findOne(By.xpath(".//input[@value='Preview']")).click();
    }

    public void selectAnyQuery() {
        findOne(SELECT_XID, DEFAULT_WAIT).click();
        findOne(SELECT_XID).sendKeys("test123");
    }

    public void clickExecuteButton() {
        findOne(By.xpath(".//input[@value='Execute']")).click();
    }

    public boolean isXIDDisplayed() {
        return isElementDisplayed(By.cssSelector(INPUT_XID), EXTRA_WAIT);
    }

    public boolean checkDealsAreDisplayed() {
        return  isElementDisplayed(By.cssSelector("tr.odd>td"), EXTRA_WAIT);
    }
}


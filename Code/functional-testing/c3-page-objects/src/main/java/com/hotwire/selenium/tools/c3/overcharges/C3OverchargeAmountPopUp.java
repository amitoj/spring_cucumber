/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.overcharges;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/18/14
 * Time: 3:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3OverchargeAmountPopUp extends ToolsAbstractPage {
    public C3OverchargeAmountPopUp(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.ovchPopup"));
    }

    public String getChargeAmount() {
        return findOne("div.ovchPopup div[id*='ovchValuesRow'] span:nth-child(3)").getText();
    }

    public String getCTANumber() {
        freeze(1);
        return findOne("div.ovchPopup div[id*='ovchValuesRow'] span:nth-child(2)", EXTRA_WAIT).getText();
    }

    public boolean isChargesBlockDisplayed() {
        return isElementDisplayed("div.chargesBlock");
    }
}

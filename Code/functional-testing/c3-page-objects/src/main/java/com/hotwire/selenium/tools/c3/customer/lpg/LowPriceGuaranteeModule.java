/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.lpg;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/8/14
 * Time: 3:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class LowPriceGuaranteeModule extends ToolsAbstractPage {

    public String lpgRefundBtn = "button img[src*='btn-request-refund.png']";

    public LowPriceGuaranteeModule(WebDriver webdriver) {
        super(webdriver, By.xpath("//h3[contains(text(), 'Low-Price Guarantee')]"));
    }

    public LowPriceGuaranteeModule(WebDriver webdriver, By keyElement) {
        super(webdriver, keyElement);
    }

    public boolean isLPGLearnMoreDisplayed() {
        return isElementDisplayed(By.cssSelector("a[href*='low-price-guarantee-popup.jsp']"));
    }

    public boolean isLPGRefundBtnDisplayed() {
        return isElementDisplayed(By.cssSelector(lpgRefundBtn));
    }

    public void clickOnLPGRefund() {
        findOne(lpgRefundBtn).click();
    }

    public String getExpiredMsg() {
        return findOne("div.sidebarTitleModule div#tileName-body").getText();
    }
}

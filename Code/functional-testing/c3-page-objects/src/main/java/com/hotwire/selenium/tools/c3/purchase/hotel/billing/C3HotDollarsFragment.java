/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/25/14
 * Time: 3:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3HotDollarsFragment extends  C3HotelBillingPage {
    private static final String HD_CHECKBOX = "input[name='paymentInfoModel.isHotCreditChecked']";

    public C3HotDollarsFragment(WebDriver webdriver) {
        super(webdriver, By.className("hotDollarsSection"));
    }

    public void chooseHotDollars() {
        freeze(1);
        findOne("label[for='isHotCreditChecked']", DEFAULT_WAIT);
        findOne(HD_CHECKBOX).click();
        findOne("div#hotDollar span.infoTxt", EXTRA_WAIT);
    }

    public WebElement getHotDollars() {
        return findOne(HD_CHECKBOX);
    }

    public String getHotDollarsAmount() {
        return findOne(By.cssSelector(".hotDollars div div:last-child")).getText();
    }
}

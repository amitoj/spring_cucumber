/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.lpg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-ozelenov on 4/11/2014.
 * Hotel Specific LPG module
 */
public class HotelLPGModule extends LowPriceGuaranteeModule {
    public String lpgRefundBtn = "input.hotelRefundBtnPopup";

    public HotelLPGModule(WebDriver webdriver) {
        super(webdriver, By.className("priceGuarantee"));
        super.lpgRefundBtn = lpgRefundBtn;
    }

    @Override
    public String getExpiredMsg() {
        return findOne("p.priceText").getText();
    }
}

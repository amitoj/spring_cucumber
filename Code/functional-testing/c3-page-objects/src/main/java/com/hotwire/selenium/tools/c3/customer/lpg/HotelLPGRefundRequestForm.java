/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.lpg;

import org.openqa.selenium.WebDriver;

/**
 * Created by v-ozelenov on 4/11/2014.
 */
public class HotelLPGRefundRequestForm extends LPGRefundRequestForm {
    public HotelLPGRefundRequestForm(WebDriver webdriver) {
        super(webdriver);
    }

    @Override
    public void setDateFound() {
        selectDate("input#LPGRefundHotelDateFound-field", getCurrentDate());
    }

    @Override
    public String getHotwireTripPrice() {
        return findOne("span#itinHotwireTotalPrice", DEFAULT_WAIT).getText();
    }

    @Override
    public String getConfirmationMsg() {
        freeze(1);
        return findOne("div#LPGRefundLayerMain-body div#continueContent").getText();
    }
}

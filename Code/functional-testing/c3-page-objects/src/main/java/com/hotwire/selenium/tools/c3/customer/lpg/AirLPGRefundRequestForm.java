/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.lpg;

import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/22/14
 * Time: 3:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class AirLPGRefundRequestForm extends LPGRefundRequestForm {
    private static final String AIR_LPG_MSG =
            "Please note that it may take up to 3 business days for us to confirm the lower rate you submitted." +
            " If we are able to verify the lower rate, a refund will be issued within 10 business days.";

    public AirLPGRefundRequestForm(WebDriver webdriver) {
        super(webdriver);
        setMSG(AIR_LPG_MSG);
    }

    @Override
    public String getHotwireTripPrice() {
        return findOne("span#itinHotwireTotalPrice", DEFAULT_WAIT).getText();
    }
}

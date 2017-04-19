/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.refund;

import org.openqa.selenium.WebDriver;

/**
 * User: v-ozelenov
 * Date: 12/7/13
 * Time: 12:28 AM
 *  Contains refund methods that is different for hotel refund flow.
 */
public class C3HotelRefundPage extends C3RefundPage {

    public C3HotelRefundPage(WebDriver webdriverInstance) {
        super(webdriverInstance);
    }

    @Override
    public void fillPartialRefundValues() {
        findOne("select#numberOfRooms", DEFAULT_WAIT);
        selectValue("select#numberOfRooms", "1");
        selectValue("select#numberOfNights", "1");
    }
}

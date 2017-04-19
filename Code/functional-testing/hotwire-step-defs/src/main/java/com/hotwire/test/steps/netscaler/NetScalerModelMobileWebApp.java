/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.netscaler;

import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.search.HotelSearchFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class NetScalerModelMobileWebApp extends NetScalerModelTemplate {

    private void verifyDateFormat(String format, String actualDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date expectedDate = simpleDateFormat.parse(actualDate);
            assertThat(actualDate).isEqualTo(simpleDateFormat.format(expectedDate));
        }
        catch (ParseException e) {
            throw new AssertionError(String.format("<%s> does not match <%s> format", actualDate, format));
        }
    }

    @Override
    public void verifyLocalDateFormat(String format) {
        MobileHotwireHomePage mobileHotwireHomePage = new MobileHotwireHomePage(getWebdriverInstance());
        HotelSearchFragment hotelSearchFragment = mobileHotwireHomePage.getHotelSearchFragment();

        verifyDateFormat(format, hotelSearchFragment.getCheckInDate());
        verifyDateFormat(format, hotelSearchFragment.getCheckOutDate());
    }
}

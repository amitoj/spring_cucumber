/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.helpcenter;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.WebDriver;

public class HotelQASearchingBookingPage extends AbstractUSPage {

    public static final String TILES_DEF_HELPCENTER_HOTELS_SEARCHING_AND_BOOKING_INDEX =
        "tiles-def.helpcenter.hotels.searching-and-booking.index";

    public HotelQASearchingBookingPage(WebDriver webdriver) {
        super(webdriver, TILES_DEF_HELPCENTER_HOTELS_SEARCHING_AND_BOOKING_INDEX);
    }
}

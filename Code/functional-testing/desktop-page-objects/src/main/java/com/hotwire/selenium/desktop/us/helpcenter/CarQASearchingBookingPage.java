/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.helpcenter;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.WebDriver;

public class CarQASearchingBookingPage extends AbstractUSPage {

    public static final String TILES_DEF_HELPCENTER_CARS_SEARCHING_AND_BOOKING_INDEX =
        "tiles-def.helpcenter.cars.searching-and-booking.index";

    public CarQASearchingBookingPage(WebDriver webdriver) {
        super(webdriver, TILES_DEF_HELPCENTER_CARS_SEARCHING_AND_BOOKING_INDEX);
    }
}

/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile;

import org.openqa.selenium.WebDriver;

/**
 * This is the page object that represents a mobile hotel disambiguation page
 *
 * @author prbhat, agunawan
 */
public class MobileHotelDisambiguationPage extends MobileAbstractPage {

    public MobileHotelDisambiguationPage(WebDriver driver) {
        super(driver, "tile.hotel.multiple.locations");
    }
}

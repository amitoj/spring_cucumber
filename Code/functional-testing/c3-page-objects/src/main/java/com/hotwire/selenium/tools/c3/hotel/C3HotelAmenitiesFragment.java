/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * User: v-abudyak
 * Date: 7/16/13
 * Time: 9:10 AM
 */

public class C3HotelAmenitiesFragment extends ToolsAbstractPage {

    public C3HotelAmenitiesFragment(WebDriver webDriver) {
        super(webDriver, By.id("hotelAmenitiesForm"));
    }

    public void clickAmenity(String amenityName) {
        findOne(By.xpath(String.format("//div[input[@type='checkbox']][contains(., '%s')]//input", amenityName)))
                .click();
    }

    public String getConfirmationMessage() {
        return findOne(".errorMessages.c3Skin", PAGE_WAIT).getText();
    }

    public void saveChanges() {
        findOne("input[value='Save Changes']", DEFAULT_WAIT).click();
        try {
            findOne("#outreachEmailPopupLayer a.container-close", EXTRA_WAIT).click();
            findOne("input[value='Save Changes']", DEFAULT_WAIT).click();
        }
        catch (WebDriverException wde) {
            logger.info("Minor amenity was operated by SCR");
        }
    }
}






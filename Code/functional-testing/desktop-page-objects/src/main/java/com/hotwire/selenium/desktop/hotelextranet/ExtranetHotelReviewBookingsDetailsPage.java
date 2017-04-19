/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * Desktop page object for Extranet's hotel details page.
 *
 * @author Amol Deshmukh
 */
public class ExtranetHotelReviewBookingsDetailsPage extends AbstractUSPage {

    private static String ROW_XPATH = ".//*[@id='reviewBooking']/table/tbody[1]/tr[";

    @FindBy(css = ".roomDetails")
    private WebElement reviewBookingsTable;

    public ExtranetHotelReviewBookingsDetailsPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.xnet.review-bookings-details");
    }

    /**
     * Validate review bookings table exist
     */
    public boolean validateReviewBookingTableExist() {
        return reviewBookingsTable.isDisplayed();
    }

    /**
     * Assert the first 5 rows of the booking table.
     */
    public boolean validateRateInReviewBookingTable(String expectedRate) {
        boolean validateExpectedRate = true;
        for (int i = 1; i < 5; i++) {
            if (!getWebDriver().findElement(By.xpath(ROW_XPATH + i + "]/td[6]")).getText().contains(expectedRate)) {
                validateExpectedRate = false;
                break;
            }
        }
        return validateExpectedRate;
    }
}

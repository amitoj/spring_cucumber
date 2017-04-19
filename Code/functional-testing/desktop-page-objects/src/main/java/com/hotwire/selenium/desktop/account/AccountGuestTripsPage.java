/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Created by v-vyulun on 1/13/14.
 */
public class AccountGuestTripsPage extends AbstractAccountPage {

    @FindBy(xpath = "//div[@id='activeTrips']//div[@class='dates'][1]")
    private WebElement recentlyBookedTrip;


    public AccountGuestTripsPage(WebDriver driver) {
        super(driver, "tiles-def.account.guest-trips");
    }

    public Boolean hasRecentlyBookedTrip(String tripStartDate) {
        try {
            recentlyBookedTrip.getText().contains(tripStartDate);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

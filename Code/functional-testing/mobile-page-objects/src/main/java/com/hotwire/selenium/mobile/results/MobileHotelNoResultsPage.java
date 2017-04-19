/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.results;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This represents the page object for the mobile hotel no results page
 *
 * @author prbhat
 */
public class MobileHotelNoResultsPage extends MobileAbstractPage {

    @FindBy(className = "zeroResultsMssg")
    private WebElement errorMessage;

    public MobileHotelNoResultsPage(WebDriver webdriver) {
        super(webdriver, "tile.hotel.no.results.landing");
    }

    public void assertNoResultsOnPage() {
        assertHasFormErrors("We're sorry, we didn't find any results for your search. " +
                            "Please modify your search criteria and try again.", errorMessage);
    }
}

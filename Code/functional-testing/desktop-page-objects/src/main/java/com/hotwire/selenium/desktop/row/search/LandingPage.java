/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends AbstractRowPage {

    @FindBy(css = "div.hotelPriceModule  strong")
    private WebElement price;

    @FindBy(css = "div.hotelName")
    private WebElement hotelName;

    public LandingPage(WebDriver webdriver) {
        super(webdriver, "tile.hotel.details.retail.landing");
    }

    public String getPrice() {
        return price.getText();
    }

    public String getHotelName() {
        return hotelName.getText();
    }

}

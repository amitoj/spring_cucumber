/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.tripstarter;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TripStarterSearchPage extends AbstractUSPage {

    @FindBy(css = "#origCity")
    private WebElement origin;

    @FindBy(css = "#destCity")
    private WebElement destination;

    @FindBy(css = "button[type=submit]")
    private WebElement findButton;

    public TripStarterSearchPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.tripstarter.index");
    }

    public TripStarterSearchPage setDestinationLocation(String destinationLocation) {
        destination.clear();
        destination.sendKeys(destinationLocation);
        return this;
    }

    public TripStarterSearchPage setOriginLocation(String originLocation) {
        origin.clear();
        origin.sendKeys(originLocation);
        return this;
    }

    public void submit() {
        findButton.submit();
    }
}

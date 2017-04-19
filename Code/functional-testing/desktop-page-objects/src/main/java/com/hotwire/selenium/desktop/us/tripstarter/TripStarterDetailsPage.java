/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.tripstarter;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * class TripStarterDetailsPage
 */
public class TripStarterDetailsPage extends AbstractUSPage {

    @FindBy(xpath = "//IMG[@alt='TripStarter Details']")
    private WebElement imgTripStarterDetails;

    @FindBy(xpath = "//SPAN[contains(text(),'Tell us what you think')]")
    private WebElement lnkTellUsWhatYouThink;

    @FindBy(xpath = "//DIV[@id='pGraph']//DIV[@id='airTab']//H3[text()='Flights']")
    private WebElement tabFlight;

    @FindBy(xpath = "//DIV[@id='pGraph']//DIV[@id='hotelTab']//H3[text()='Hotels']")
    private WebElement tabHotel;

    @FindBy(css = "div#airGrph div.note")
    private WebElement txtFligtsTabVerify;

    @FindBy(css = "div#hotelGrph div.note")
    private WebElement txtHotelsTabVerify;

    @FindBy(xpath = "//DIV[@id='destinationDetails']//DIV[@class='content rightSide']")
    private WebElement txtWhenToGo;

    public TripStarterDetailsPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.tripstarter.details");
    }

    public WebElement getImgTripStarterDetails() {
        return imgTripStarterDetails;
    }

    public WebElement getLnkTellUsWhatYouThink() {
        return lnkTellUsWhatYouThink;
    }

    public WebElement getTabFlight() {
        return tabFlight;
    }

    public WebElement getTabHotel() {
        return tabHotel;
    }

    public WebElement getTxtFligtsTabVerify() {
        return txtFligtsTabVerify;
    }

    public WebElement getTxtWhenToGo() {
        return txtWhenToGo;
    }

    public WebElement getTxtHotelsTabVerify() {
        return txtHotelsTabVerify;
    }
}


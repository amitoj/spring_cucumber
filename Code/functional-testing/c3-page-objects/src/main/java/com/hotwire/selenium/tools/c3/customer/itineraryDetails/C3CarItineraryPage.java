/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.itineraryDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

/**
 * Page Object
 */
public class C3CarItineraryPage extends C3ItineraryDetailsPage {
    public C3CarItineraryPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.itniInfoContainer"));
        waitTextLoaded(By.id("itinNumber"), EXTRA_WAIT);
    }

    @Override
    public void doCancellation() {
        findOne("span.btnsHolder input[name='cancel']", DEFAULT_WAIT).click();
        findOne("div.cancelSection button", EXTRA_WAIT).click();
        try {
            findOne("div.cancelPopupHolder input[name='cancel']", DEFAULT_WAIT).click();
        }
        catch (TimeoutException e) {
            logger.info("Car purchase without double confirmation.");
        }
    }

    @Override
    public String getItineraryNumber() {
        return findOne("div#itinNumber", DEFAULT_WAIT).getText().replaceFirst("^HW# ", "");
    }

    @Override
    public String getTotalCost() {
        return findOne("div.transactionAmount div.rightCol.highlighted", EXTRA_WAIT)
                .getText().replaceAll("\\(.*\\)", "");
    }

    @Override
    public void serviceRecoveryClick() {
        findOne("span.btnsHolder input[name='serviceRecovery']", EXTRA_WAIT).click();
    }

    @Override
    public String getStatus() {
        return findOne("a#itinStatus", EXTRA_WAIT).getText().replace("?", "");
    }

    @Override
    public String getPNR() {
        return findOne("div#itinRecLocPNR").getText();
    }

    public String getPurchaseType() {
        freeze(DEFAULT_WAIT);
        return findOne("div#itinType", DEFAULT_WAIT).getText();
    }

    public String getDriverName() {
        return findOne("div#participantNames", DEFAULT_WAIT).getText();
    }

    public String getVendorName() {
        return findOne("div#itinCompanyName" , DEFAULT_WAIT).getText();
    }

    public String getCarReservationNumber() {
        return findOne(By.xpath("//div[@class='travelDetailsBlockContent']" +
                                "//span[text()='Reservation Number']/.." +
                                "/span[@class='value']"), DEFAULT_WAIT).getText();
        //div[@class='travelDetailsBlockContent']//span[text()='Reservation Number']/../span[@class='value']
    }

    public String getNumberOfDays() {
        return findOne(By.xpath("//div[@class='travelDetailsBlockContent']" +
                "//span[text()='Number of Days']/.." +
                "/span[@class='value']"), DEFAULT_WAIT).getText();
    }

    public String getPickUpLocation() {
        return findOne(By.xpath("//div[@class='travelDetailsBlockContent']" +
                "//span[text()='Pick-up Location']/.." +
                "/span[@class='value']"), DEFAULT_WAIT).getText();
    }

    public String getPickUpDate() {
        return findOne(By.xpath("//div[@class='travelDetailsBlockContent']" +
                "//span[text()='Pick-up Date']/.." +
                "/span[@class='value']"), DEFAULT_WAIT).getText();
    }

    public String getDropOffDate() {
        return findOne(By.xpath("//div[@class='travelDetailsBlockContent']" +
                "//span[text()='Drop-off Date']/.." +
                "/span[@class='value']"), DEFAULT_WAIT).getText();
    }

    public String getTripProtection() {
        return findOne(By.xpath("//div[@id='transactionAmountRentalProtection']")
                , DEFAULT_WAIT).getText();
    }

}

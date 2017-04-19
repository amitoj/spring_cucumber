/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.car;

import com.hotwire.selenium.tools.c3.purchase.C3ConfirmationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/7/14
 * Time: 5:29 AM
 * Simplified C3 Car Confirmation Page
 */
public class C3CarConfirmationPage extends C3ConfirmationPage {
    public C3CarConfirmationPage(WebDriver webdriver) {
        super(webdriver, By.xpath("//h4[text()='Car rental']"));
    }

    public String getItineraryNumber() {
        return findOne("div.confirmCodes div:last-child").getText().replaceFirst("^.*:", "");
    }

    public String getRefundRebookMsg() {
        return findOne("div.confirmMessages p").getText();
    }

    public String getDestinationLocation() {
        return findOne(By
                .xpath("//div[@id='stripedSidebarRightLayout']//span[contains(text(), 'silkCarStartCityState:')]//.."))
                .getText();
    }

    public String getDriverName() {
        return findOne(By
                .xpath("//div[@class = 'passengerDetails']/div[@class = 'colFirst']/div"))
                .getText();
    }

    public String getPickUpLocation() {
        return findOne(By
                .xpath("//div[@class='trip']//span[contains(text(), 'silkCarStartCityState:')]//.."))
                .getText();
    }

    public String getPickUpDateTime() {
        return findOne(By
                .xpath("//div[@class='trip']//span[contains(text(), 'silkCarStartDateTime:')]//.."))
                .getText();
    }

    public String getDropOffDateTime() {
        return findOne(By
                .xpath("//div[@class='trip']//span[contains(text(), 'silkCarEndDateTime:')]//.."))
                .getText();
    }

    public String getTotalPrice() {
        return findOne(By
                .xpath("//span[contains(text(), 'silkCarTotalEstimatedDue:')]//.."))
                .getText();
    }

    public String getCarCompany() {
        return findOne(By
                .xpath("//span[contains(text(), 'silkCarVendorName:')]//.."))
                .getText();
    }

    public String getCarReservationNumber() {
        String value = findOne(By
                .xpath("//span[contains(text(), 'silkCarItineraryNumber:')]//.."))
                .getText();
        String[] codeParts = value.split("code:");
        String[] parts = codeParts[1].split("- Your");
        return parts[0].trim();
    }





}

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

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 10/10/14
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3HotelBlockConfirmationPage extends ToolsAbstractPage {

    private final String rdoCustomer = "//input[@class='selectReasonCustomer']";
    private final String rdoHotel = "//input[@class='selectReasonHotel']";
    private final String selectReasonForHotel = "//select[@class='selectReasonHotel']";
    private final String selectReasonForCustomer = "//select[@class='selectReasonCustomer']";
    private final String blockButton = "//button[contains(text(),'Block hotel')]";
    private final String unblockButton = "//button[contains(text(),'Unblock hotel')]";
    private final String cancelButton = "//button[contains(text(),'Cancel')]";

    public C3HotelBlockConfirmationPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("#confirmBlock-bd, #confirmUnBlock-bd"));
    }

    public void clickCustomerRdo() {
        findOne(By.xpath(rdoCustomer)).click();
    }

    public void clickHotelPartnerRdo() {
        findOne(By.xpath(rdoHotel)).click();
    }

    public void setReasonForCustomer(String reason) {
        findOne(By.xpath(selectReasonForCustomer)).sendKeys(reason);
    }

    public void setReasonForHotelPartner(String reason) {
        findOne(By.xpath(selectReasonForHotel)).sendKeys(reason);
    }

    public void clickBlockHotel() {
        findOne(By.xpath(blockButton)).click();
    }

    public void clickUnblockHotel() {
        findOne(By.xpath(unblockButton)).click();
    }

    public void clickCancelHotel() {
        findOne(By.xpath(cancelButton)).click();
    }

    public void blockHotelByCustomer(String reason) {

        clickCustomerRdo();
        setReasonForCustomer(reason);
        clickBlockHotel();
    }


    public void unblockHotelByCustomer(String reason) {
        clickCustomerRdo();
        setReasonForCustomer(reason);
        clickUnblockHotel();
    }

    public void cancelHotelByCustomer(String reason) {
        clickCustomerRdo();
        setReasonForCustomer(reason);
        clickCancelHotel();
    }
}

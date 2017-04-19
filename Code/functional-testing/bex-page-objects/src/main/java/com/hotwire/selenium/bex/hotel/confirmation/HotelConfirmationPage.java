/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.bex.hotel.confirmation;

import com.hotwire.selenium.bex.BexAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelConfirmationPage extends BexAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelConfirmationPage.class.getSimpleName());

    @FindBy(css = ".itinerary-number")
    private WebElement itineraryNumber;

    public HotelConfirmationPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("#confirmation-header"));
        PageFactory.initElements(getWebDriver(), this);
    }

    public String getHeaderText() throws InterruptedException {
        try {
            getWebDriver().findElement(By.id("booking-loader")).isDisplayed();
            Thread.sleep(10000);
        }
        catch (RuntimeException ex) {
            Thread.sleep(6000);
        }

        return getWebDriver().findElement(By.xpath("//div[@id='purchase-summary']/h2")).getText();

    }

    public String getItineraryNumber() {
        return itineraryNumber.getText();
    }


}

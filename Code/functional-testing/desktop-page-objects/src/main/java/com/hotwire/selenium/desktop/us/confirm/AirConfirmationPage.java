/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.confirm;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.util.webdriver.po.AbstractPageObject;

/**
 * Created by IntelliJ IDEA. User: v-abudyak Date: 5/18/12 Time: 2:53 PM To change this template use File | Settings |
 * File Templates.
 */
public class AirConfirmationPage extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirConfirmationPage.class);

    @FindBy(xpath = "//*[@id='activity-banner']/div/h3")
    private WebElement activitiesBanner;

    @FindBy(xpath = ".//*[@id='tileName-B4']/div/div[1]/div[2]/div[1]/div/a/img")
    private WebElement hotelCrossSellModule;

    @FindBy(xpath = ".//*[@id='tileName-B4']/div/div[1]/div[2]/div[2]/div/a/img")
    private WebElement carCrossSellModule;

    @FindBy(css = ".confirmCopy")
    private WebElement confirmAndItineraryText;

    @FindBy(css = ".carCrossSellModule")
    private WebElement carCrossSell;

    public AirConfirmationPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.air.confirm.base", "tiles-def.account.air-purchase-details"});
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(".confirmCopy");
    }

    public String getConfirmationCode() {
        String codesText = confirmAndItineraryText.getText();
        String subString = codesText.substring(codesText.indexOf("code:"));
        int startIndex = subString.indexOf(": ") + 2;
        int endIndex = subString.indexOf(" - Your");
        return subString.substring(startIndex, endIndex).trim();
    }

    public String getHotwireItinerary() {
        String codesText = confirmAndItineraryText.getText();
        String subString = codesText.substring(codesText.indexOf("Hotwire Itinerary:"));
        int startIndex = subString.indexOf(":");
        int endIndex = subString.indexOf("Thank");
        return subString.substring(startIndex + 1, endIndex).trim();
    }

    public void clickBanner() {
        activitiesBanner.click();
    }

    public boolean verifyActivitiesBanner(String destinationCity, String startDate) {
        String banner = activitiesBanner.getText();
        boolean validateBanner = true;
        if (!banner.contains(destinationCity) || !banner.contains(startDate)) {
            validateBanner = false;
        }
        return validateBanner;
    }

    public boolean verifyCrossSellParameters(String destinationLocation, String startDate, String endDate) {
        return verifyHotelParameters(destinationLocation, startDate, endDate) &&
                verifyCarParameters(destinationLocation, startDate, endDate);
    }

    private boolean verifyHotelParameters(String destinationLocation, String startDate, String endDate) {
        boolean hotelModule = true;
        AirConfirmationPage airConfirmationPage = new AirConfirmationPage(getWebDriver());
        try {
            airConfirmationPage.hotelCrossSellModule.click();
            WebElement hotelCrossSellPanel = getWebDriver().findElement(By.id("newHotelPopup-panel"));
            String hotelDestination = hotelCrossSellPanel.findElement(By.name("destCity")).getAttribute("value");
            String hotelStartDate = hotelCrossSellPanel.findElement(By.name("startDate")).getAttribute("value");
            String hotelEndDate = hotelCrossSellPanel.findElement(By.name("endDate")).getAttribute("value");
            if (!hotelDestination.contains(destinationLocation) && !hotelStartDate.contains(startDate) &&
                    !hotelEndDate.contains(endDate)) {
                hotelModule = false;
            }
        }
        catch (NoSuchElementException e) {
            hotelModule = false;
            LOGGER.info("HotelCrossPanel wasn't loaded or some of the elements are missing");
        }
        return hotelModule;
    }

    private boolean verifyCarParameters(String destinationLocation, String startDate, String endDate) {
        boolean carModule = true;
        AirConfirmationPage airConfirmationPage = new AirConfirmationPage(getWebDriver());
        try {
            airConfirmationPage.carCrossSellModule.click();
            WebElement carCrossSellPanel = getWebDriver().findElement(By.id("newCarPopup-panel"));
            String carDestination = carCrossSellPanel.findElement(By.name("startLocation")).getAttribute("value");
            String carStartDate = carCrossSellPanel.findElement(By.name("startDate")).getAttribute("value");
            String carEndDate = carCrossSellPanel.findElement(By.name("endDate")).getAttribute("value");
            if (!carDestination.contains(destinationLocation) && !carStartDate.contains(startDate) &&
                    !carEndDate.contains(endDate)) {
                carModule = false;
            }
        }
        catch (NoSuchElementException e) {
            carModule = false;
            LOGGER.info("CarCrossSellPanel wasn't loaded or some of the elements are missing");
        }
        return carModule;
    }

    public void clickCarCrossSell() {
        carCrossSell.findElement(By.cssSelector("a.button")).click();
    }

    public boolean isCarCrossSellDisplayed() {
        try {
            return carCrossSell.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}

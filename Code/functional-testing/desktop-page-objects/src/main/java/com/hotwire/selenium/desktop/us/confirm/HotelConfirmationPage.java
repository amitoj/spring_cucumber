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

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.utils.WebElementUtils;

/**
 * This class represent a us hotel confirmation page.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public class HotelConfirmationPage extends AbstractUSPage {

    @FindBy(xpath = "//div[contains(text(), 'Discount')]")
    private WebElement discountText;

    @FindBy(xpath = "//div[@class='summaryOfCharges confirmPage']/div[1]/div[3]/span")
    private WebElement ratePerNight;

    @FindBy(xpath = "//div[@class='summaryOfCharges confirmPage']/div[1]/div[15]")
    private WebElement taxesAndFees;

    @FindBy(xpath = "//div[@class='summaryOfCharges confirmPage']/div[1]/div[7]")
    private WebElement nights;

    @FindBy(xpath = "//div[@class='summaryOfCharges confirmPage']/div[1]/div[11]")
    private WebElement rooms;

    @FindBy(xpath = "//div[@class='value mb mt20 finalTotal']")
    private WebElement tripTotal;

    @FindBy(xpath = "//span[@id='insurance']")
    private WebElement insuranceTotal;

    @FindBy(xpath = "//*[@id='activity-banner']/div/h3")
    private WebElement activitiesBanner;

    @FindBy(css = ".confirmCopy")
    private WebElement confirmAndItineraryText;

    @FindBy(css = ".carCrossSellModule")
    private WebElement carCrossSell;

    public HotelConfirmationPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.hotel.confirm.base", "tile.hotel.purchase-confirm",
            "tile.hotel.booking-confirm"});
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

    public Boolean verifyDiscountApplied() {
        try {
            return discountText.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Never null
     *
     * @return String
     */
    public String getRatePerNight() {
        return WebElementUtils.getText(ratePerNight);
    }

    public int getNights() {
        return (int) WebElementUtils.getDigitsOnly(nights);
    }

    public int getRooms() {
        return (int) WebElementUtils.getDigitsOnly(rooms);
    }

    public String getTaxesAndFees() {
        return WebElementUtils.getText(taxesAndFees);
    }

    public String getTripTotal() {
        return WebElementUtils.getText(tripTotal);
    }

    public String getInsuranceTotal() {
        return WebElementUtils.getText(insuranceTotal);
    }

    public boolean verifyActivitiesBanner(String destinationCity, String startDate) {
        String banner = activitiesBanner.getText();
        boolean validateBanner = true;
        if (!banner.contains(destinationCity) || !banner.contains(startDate)) {
            validateBanner = false;
        }
        return validateBanner;
    }

    public void clickBanner() {
        activitiesBanner.click();
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

/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.details;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * Created by IntelliJ IDEA.
 * User: v-vyulun
 * Date: 4/6/12
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileHotelDetailsPage extends MobileAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileHotelDetailsPage.class.getSimpleName());

    @FindBy(xpath = "//p[@class='i price']")
    private WebElement hotelPrice;

    @FindBy(xpath = "//span[@class='star']")
    private WebElement hotelStars;

    @FindBy(xpath = "//p[@class='i area']")
    private WebElement hotelArea;

    @FindBy(xpath = "//li[@class='search']")
    private WebElement hotelDates;

    @FindBy(xpath = "//li[@class='search']/strong")
    private WebElement roomsNumber;

    @FindBy(xpath = "//div[@class='total']")
    private WebElement totalPrice;

    @FindBy(xpath = "//div[@class='numbers']")
    private WebElement referenceNumber;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement continueBtn;

    @FindBy(xpath = "//*[@data-bdd='hotel-discount-banner']")
    private WebElement discountBanner;

    @FindBy(xpath = "//a[@class='priceLockup a']")
    private WebElement hotelPriceLink;

    @FindBy(css = "a img[alt='Back'], a#back")
    private WebElement backButton;

    @FindBy(css = "div.additional.ada div.amenities, .specialNeeds .features")
    private WebElement listADAAmenities;


    public MobileHotelDetailsPage(WebDriver driver) {
        super(driver, new String[]{"tile.hotel.details.opaque.overview"});
        new WebDriverWait(getWebDriver(), 10).until(new VisibilityOf(By.xpath("//button[@type='submit']")));
    }

    public void select() {
        continueBtn.submit();
    }

    public void priceSelect() {
        hotelPriceLink.click();
    }

    public boolean verifyDiscountBannerOnResultsPage() {
        return discountBanner.isDisplayed() ? true : false;
    }

    public boolean isAdditionalAdaFeaturesDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector("div.ada, .specialNeeds .features")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void goBackToResults() {
        backButton.click();
    }

    public String getADAAmenities() {
        try {
            String ada = listADAAmenities.getText();
            LOGGER.info("ADA amenities: " + listADAAmenities.getText());
            return ada;
        }
        catch (NoSuchElementException e) {
            LOGGER.info("ADA Amenities locator not found. If you're checking for ADA amenities, " +
                        "you need to verify the hotel you select has ADA amenities. Returning empty string.");
            return "";
        }
    }

}

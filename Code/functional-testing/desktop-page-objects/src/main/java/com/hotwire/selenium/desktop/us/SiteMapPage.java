/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Site Map Page
 */
public class SiteMapPage extends AbstractUSPage {

    private static final String TILES_DEF_SITEMAP_INDEX = "tile.site-map"; // tiles-def.sitemap.index - changed to
                                                                           // tile.site-map

    @FindBy(xpath = "//a[contains(@href, '/tripstarter/index.jsp')]")
    private WebElement tripstarter;

    // Hotwire Products Links
    @FindBy(xpath = "//div[@id='tileName-A1']/div/h2[1]")
    private WebElement hotwireProductsText;

    // Hotel links
    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[1]/li[1]/ul/li[2]/a")
    private WebElement searchForHotel;

    @FindBy(xpath = "//a[@id='starRatingLayer-opener']")
    private WebElement hotelRatings;

    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[1]/li[1]/ul/li[4]/a")
    private WebElement hotelSuppliers;

    // car rentals

    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[1]/li[2]/ul/li[2]/a")
    private WebElement searchCar;

    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[1]/li[2]/ul/li[3]/a")
    private WebElement typeOfRentalCars;

    // flight link
    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[1]/li[3]/ul/li[2]/a")
    private WebElement searchFlight;

    // My Account selectors
    @FindBy(xpath = "//div[@id='tileName-A1']/div/h2[2]")
    private WebElement myAccountText;

    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[2]/li[1]/a")
    private WebElement loginToHotwire;

    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[2]/li[2]/a")
    private WebElement createHotwireAccount;

    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[2]/li[3]/a")
    private WebElement registrationBenefits;

    @FindBy(xpath = "//div[@id='tileName-A1']/div/ul[2]/li[4]/a")
    private WebElement signUpForLowPrices;

    // Escape to Europe links
    @FindBy(xpath = "//a[contains(text(),'Rome')]")
    private WebElement rome;

    public SiteMapPage(WebDriver webdriver) {
        super(webdriver, TILES_DEF_SITEMAP_INDEX);
    }

    public WebElement getTripstarter() {
        return tripstarter;
    }

    public WebElement getHotwireProductsText() {
        return hotwireProductsText;
    }

    public WebElement getsearchForHotel() {
        return searchForHotel;
    }

    public WebElement gethotelRatings() {
        return hotelRatings;
    }

    public WebElement gethotelSuppliers() {
        return hotelSuppliers;
    }

    // car rentals

    public WebElement getsearchCar() {
        return searchCar;
    }

    public WebElement gettypeOfRentalCars() {
        return typeOfRentalCars;
    }

    // flight link
    public WebElement getsearchFlight() {
        return searchFlight;
    }

    public WebElement getmyAccountText() {
        return myAccountText;
    }

    public WebElement getloginToHotwire() {
        return loginToHotwire;
    }

    public WebElement getcreateHotwireAccount() {
        return createHotwireAccount;
    }

    public WebElement getregistrationBenefits() {
        return registrationBenefits;
    }

    public WebElement getsignUpForLowPrices() {
        return signUpForLowPrices;
    }

    public WebElement getRomeLink() {
        return rome;
    }
}

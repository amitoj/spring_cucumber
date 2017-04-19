/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.search;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultiVerticalFareFinder extends MobileAbstractPage {

    @FindBy(xpath = "//*[@data-bdd='search-hotel-button']")
    private WebElement searchHotel;

    @FindBy(xpath = "//*[@data-bdd='search-car-button']")
    private WebElement searchCar;


    public MultiVerticalFareFinder(WebDriver webdriver) {
        super(webdriver);
        // TODO Auto-generated constructor stub
    }

    public CarSearchFragment chooseCar() {
        searchCar.click();
        return new CarSearchFragment(getWebDriver());
    }


    public HotelSearchFragment chooseHotel() {
        // No need to click on searchHotel button since the hotel fare finder is open by default
        return new HotelSearchFragment(getWebDriver());
    }

}

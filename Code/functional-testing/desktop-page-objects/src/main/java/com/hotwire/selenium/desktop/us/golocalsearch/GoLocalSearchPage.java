/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.golocalsearch;

import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 1/30/14
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoLocalSearchPage extends HotelSearchFragment {
    private static final String LOCAL_SEARCH = ".tripForm";

    @FindBy(xpath = ".//*[contains(@id, 'destCity')]")
    private WebElement location;

    @FindBy(xpath = ".//INPUT[@name='startDate']")
    private WebElement startDate;

    @FindBy(xpath = ".//INPUT[@name='endDate']")
    private WebElement endDate;

    @FindBy(xpath = ".//button[@type='submit']")
    private WebElement findButton;


    public GoLocalSearchPage(WebDriver webDriver) {
       // super(webDriver, "tiles-def.hotel.localTrip.landing");
        super(webDriver, By.cssSelector(LOCAL_SEARCH));
    }

    public WebElement getLocation() {
        return location;
    }

    public WebElement getStartDate() {
        return startDate;
    }

    public WebElement getEndDate() {
        return endDate;
    }

    public WebElement getFindButton() {
        return findButton;
    }
}

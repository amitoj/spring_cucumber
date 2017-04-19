/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.angular;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPage;

/**
 * @author vjong
 *
 */
public class AngularHomePage extends AbstractPage {

    @FindBy(css = "div[data-bdd='olab-feedback']")
    private WebElement olabLink;

    public AngularHomePage(WebDriver webdriver) {
        super(webdriver, new Wait<Boolean>(new VisibilityOf(By.cssSelector(".site-content"))));
    }

    public void searchForHotels(String destination, Date startDate, Date endDate, Integer numberOfHotelRooms,
            Integer numberOfAdults, Integer numberOfChildren, Boolean enableHComSearch) {

        new AngularFareFinderFragment(getWebDriver()).searchForHotels(
            destination, startDate, endDate, numberOfHotelRooms, numberOfAdults, numberOfChildren, enableHComSearch);
    }

    public AngularFareFinderFragment getFareFinderFragment() {
        return new AngularFareFinderFragment(getWebDriver());
    }

    public void clickOlabLink() {
        olabLink.click();
    }
}

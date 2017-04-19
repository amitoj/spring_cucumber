/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import com.hotwire.selenium.tools.c3.purchase.air.C3AirSearchFragment;
import com.hotwire.selenium.tools.c3.purchase.car.C3CarSearchFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3HotelSearchFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 3/13/14
 * Time: 3:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3FareFinder extends ToolsAbstractPage {
    private String airRadioBtn = "ul.verticals li:last-child input";
    private String carRadioBtn = "ul.verticals li:nth-child(2) input";
    private String hotelRadioBtn = "ul.verticals li input";
    private String searchForm;

    public C3FareFinder(WebDriver webDriver) {
        super(webDriver, By.className("FareFinderComp"));
    }

    public C3FareFinder(WebDriver webDriver, By keyElement) {
        super(webDriver, keyElement);
    }

    public String getSearchForm() {
        return searchForm;
    }

    public void setSearchForm(String searchForm) {
        this.searchForm = searchForm;
    }

    public C3AirSearchFragment chooseAir() {
        findOne(airRadioBtn, DEFAULT_WAIT).click();
        return new C3AirSearchFragment(getWebDriver());
    }

    public C3CarSearchFragment chooseCar() {
        findOne(carRadioBtn, DEFAULT_WAIT).click();
        return new C3CarSearchFragment(getWebDriver());
    }

    public C3HotelSearchFragment chooseHotel() {
        findOne(hotelRadioBtn, DEFAULT_WAIT).click();
        return new C3HotelSearchFragment(getWebDriver());
    }

    public void setDates(Date startDate, Date endDate) {
        selectDate(getSearchForm() + " input[name='startDate']", startDate);
        selectDate(getSearchForm() + " input[name='endDate']", endDate);
    }

    public void setDestination(String fromDestination) {
        setText("input[name='startLocation']", fromDestination);
    }

    public String getDestination() {
        return findOne("input[name='startLocation']", DEFAULT_WAIT).getAttribute("value");
    }

    public String getStartDate() {
        return findOne(getSearchForm() + " input[name='startDate']", DEFAULT_WAIT).getAttribute("value");
    }

    public String getEndDate() {
        return findOne(getSearchForm() + " input[name='endDate']", DEFAULT_WAIT).getAttribute("value");
    }

    public void submit() {
        findOne(getSearchForm()).submit();
    }

    public void switchToSite(String countryCode) {
        selectValue("select[name='siteCountryCode']", countryCode.toLowerCase());
    }

    public void setTimes(String pickupTime, String dropoffTime) {
        findOne("#ui-id-2-button", DEFAULT_WAIT).click();
        //findOne("#ui-id-2-button", DEFAULT_WAIT).sendKeys(Keys.PAGE_DOWN);
        freeze(1);
        String transformed = (pickupTime == null) ? "noon" : pickupTime.
                replace("AM", "am").replace("PM", "pm").replace(" ", "");
        findOne(By.xpath("//ul[@id='ui-id-2-menu']//a[contains(text(), '" + transformed + "')]")).click();

        findOne("#ui-id-3-button", DEFAULT_WAIT).click();
        //findOne("#ui-id-3-button", DEFAULT_WAIT).sendKeys(Keys.PAGE_DOWN);
        freeze(1);
        transformed = (dropoffTime == null) ? "noon" : dropoffTime.
                replace("AM", "am").replace("PM", "pm").replace(" ", "");
        findOne(By.xpath("//ul[@id='ui-id-3-menu']//a[contains(text(), '" + transformed + "')]")).click();
    }
}

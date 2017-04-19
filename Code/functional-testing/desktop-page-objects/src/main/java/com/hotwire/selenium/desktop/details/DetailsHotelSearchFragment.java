/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.details;

import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 9/12/14
 * Time: 2:32 AM
 */
public class DetailsHotelSearchFragment extends HotelSearchFragment {

    private static final By CONTAINER = By.id("retailDatePickerTop");

    @FindBy(css = ".finderWrapper button[type='submit']")
    private WebElement submit;

    public DetailsHotelSearchFragment(WebDriver webDriver) {
        super(webDriver, CONTAINER);
    }

    public boolean isExpanded() {
        return getWebDriver().findElement(CONTAINER).getAttribute("class").contains("expanded") && submit.isDisplayed();
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return null;
    }

    @Override
    public HotelSearchFragment withDestinationLocation(String destination) {
        return this;
    }

    @Override
    public HotelSearchFragment withNumberOfHotelRooms(Integer numberOfHotelRooms) {
        return this;
    }

    @Override
    public HotelSearchFragment withNumberOfAdults(Integer numberOfAdults) {
        return this;
    }

    @Override
    public HotelSearchFragment withNumberOfChildren(Integer numberOfChildren) {
        return this;
    }

    @Override
    public HotelSearchFragment withEnableSearchPartner(Boolean searchPartner) {
        return this;
    }
}

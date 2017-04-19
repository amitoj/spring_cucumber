/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel.billing.TravelerFragments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/24/14
 * Time: 7:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3KnownTravelerFragment extends C3GuestTravelerFragment {
    private static final String PHONE = "div.phoneNumber input";
    private static final String EMAIL  = "div.emailAsText";

    public C3KnownTravelerFragment(WebDriver webdriver) {
        super(webdriver, By.className("signedInName"));
    }

    public C3KnownTravelerFragment(WebDriver webdriver, By element) {
        super(webdriver, element);
    }

    public String getName() {
        return findOne("select#guest option[value='0']").getText();
    }

    @Override
    public String getPhone() {
        return findOne(PHONE).getAttribute("value");
    }

    @Override
    public String getEmail() {
        return findOne(EMAIL).getText();
    }

    public void selectTraveler() {
        selectValue("select[name='travelerInfoModel.selectedTraveler']", "0");
    }

    public boolean isSelectAvailable() {
        return isElementDisplayed("select[name='travelerInfoModel.selectedTraveler']");
    }
}

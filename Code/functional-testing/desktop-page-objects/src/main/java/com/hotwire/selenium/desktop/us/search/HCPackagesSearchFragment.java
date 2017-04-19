/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;

import com.hotwire.selenium.desktop.widget.DropDownSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Hotel+Car fare finder fragment.
 */
class HCPackagesSearchFragment extends PackagesSearchFragment {

    public HCPackagesSearchFragment(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public PackagesSearchFragment withStartAnyTime(String startAnyTime) {
        String startTime = startAnyTime == null ? null : startAnyTime;
        String s = "#vacationIndexForm #vacationSimpleStartTime";
        new DropDownSelector(getWebDriver(), By.cssSelector(s)).selectByVisibleText(startTime);
        return this;
    }

    @Override
    public PackagesSearchFragment withEndAnyTime(String endAnyTime) {
        String endTime = endAnyTime == null ? null : endAnyTime;
        String s = "#vacationIndexForm #vacationSimpleEndTime";
        new DropDownSelector(getWebDriver(), By.cssSelector(s)).selectByVisibleText(endTime);
        return this;
    }
}

/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.purchase.car;

import com.hotwire.selenium.tools.c3.purchase.C3FareFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * C3CarSearchFragment
 */
public class C3CarSearchFragment extends C3FareFinder {

    public C3CarSearchFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector("form.carFields"));
        setSearchForm("form.carFields");
    }
}

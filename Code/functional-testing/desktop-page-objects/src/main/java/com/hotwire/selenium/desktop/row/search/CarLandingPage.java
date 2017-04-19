/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author v-ypuchkova
 * @since 2012.10
 */
public class CarLandingPage extends AbstractRowPage {
    public CarLandingPage(WebDriver webdriver) {
        super(webdriver);
    }

    public CarSearchFragment getCarSearchFragment() {
        return new CarSearchFragment(getWebDriver(), By.className("carFareFinderComp"));
    }
}

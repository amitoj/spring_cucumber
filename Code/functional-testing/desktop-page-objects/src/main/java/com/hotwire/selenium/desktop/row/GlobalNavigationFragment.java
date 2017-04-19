/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row;

import com.hotwire.selenium.desktop.row.search.CarLandingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author v-ypuchkova
 * @since 2013.01
 */
public class GlobalNavigationFragment extends AbstractRowPage {

    @FindBy(css = ".headerTab .cars a")
    private WebElement carLandingPageLink;

    public GlobalNavigationFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector(".headerTabMenuComp"));
    }

    public CarLandingPage navigateToCarLandingPage() {
        carLandingPageLink.click();
        return new CarLandingPage(getWebDriver());
    }

    public String getCarLandingPageUrl() {
        return carLandingPageLink.getAttribute("href");
    }
}

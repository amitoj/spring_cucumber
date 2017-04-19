/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.details;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.PageName;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * User: lryzhikov
 * Date: 5/3/12
 * Time: 3:17 PM
 */

public class CarDetailsPage extends AbstractUSPage {

    private static final String[] DETAILS_PAGE_TILES = new String[]{
        "tile.car-details.opaque-local.billing",
        "tiles-def.car.details.*",
        "tile.car-details.opaque.billing"};
    private static final String ABOUT_YOUR_CAR_RENTAL_LOGO =
            "//IMG[contains(@src,headlines/about-your-car-rental-headline.gif)]";

    @FindBy(xpath = "//img[contains(@alt, 'Continue')]//..")
    private WebElement carDetailContinueButton;

    @FindBy(css = "div.layoutColumnRight div.priceChangeMessage")
    private WebElement priceHasChanged;

    @FindBy(xpath = ABOUT_YOUR_CAR_RENTAL_LOGO)
    private WebElement imgAboutYourCarRental;


    public CarDetailsPage(WebDriver webdriver) {
        super(webdriver, DETAILS_PAGE_TILES);
    }

    public boolean isAboutCarRentalLogoDisplayed() {
        try {
            return this.imgAboutYourCarRental.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    private void checkPriceChanges() {
        try {
            logger.info(priceHasChanged.getText());
        }
        catch (NoSuchElementException e) {
            // No op
        }
    }

    public void select() {
        checkPriceChanges();
        carDetailContinueButton.click();
        String pageName = new PageName().apply(getWebDriver());

        if (pageName.contains("details")) {
            new CarDetailsPage(getWebDriver()).select();
        }
    }
}

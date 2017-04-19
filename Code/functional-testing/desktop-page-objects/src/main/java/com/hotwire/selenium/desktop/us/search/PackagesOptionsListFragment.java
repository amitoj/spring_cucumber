/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.search;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 6/19/14
 * Time: 6:46 AM
 */
class PackagesOptionsListFragment extends AbstractPageObject {

    public static final String CSS_CONTAINER = "#tileName-options .packageOptions";
    private static final By CONTAINER = By.cssSelector(CSS_CONTAINER);

    @FindBy(css = "li#fhcList input[type='radio']")
    private WebElement elmFlightHotelCar;

    @FindBy(css = "li#fhList input[type='radio']")
    private WebElement elmFlightHotel;

    @FindBy(css = "li#hcList input[type='radio']")
    private WebElement elmHotelCar;

    @FindBy(css = "li#ACRadioList2 input[type='radio']")
    private WebElement elmFlightCar;

    PackagesOptionsListFragment(WebDriver webdriver) {
        super(webdriver, CONTAINER);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(CSS_CONTAINER + " li input[name='vertical']");
    }

    public void selectPackageByType(String vacationPackage) {
        if (vacationPackage.contentEquals("AHC")) {
            elmFlightHotelCar.click();
        }
        else if (vacationPackage.contentEquals("AH")) {
            elmFlightHotel.click();
        }
        else if (vacationPackage.contentEquals("HC")) {
            elmHotelCar.click();
        }
        else if (vacationPackage.contentEquals("AC")) {
            elmFlightCar.click();
        }
        else {
            throw new RuntimeException("Invalid vacation package type.");
        }
    }
}

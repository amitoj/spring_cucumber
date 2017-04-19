/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.air;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 4/23/14
 * Time: 12:01 AM
 */
public class AirCarDriverInfoFragment extends AbstractPageObject {

    private static final By DRIVER_INFO_HEADER = By.xpath("//h2[contains(., 'Driver information')]");
    private static final By DRIVER_FIRST_NAME = By.name("travelerForm.carForm.driverFirstName");
    private static final By DRIVER_LAST_NAME = By.name("travelerForm.carForm.driverLastName");
    private static final By DRIVER_MIDDLE_NAME = By.name("travelerForm.carForm.driverMiddleName");

    public AirCarDriverInfoFragment(WebDriver webDriver) {
        super(webDriver);
    }

    public void fillDriverInformation(String firstName, String lastName, String middleName) {
        List<WebElement> driverInformation = getWebDriver().findElements(DRIVER_INFO_HEADER);
        if (!driverInformation.isEmpty()) {
            sendKeys(getWebDriver().findElement(DRIVER_FIRST_NAME), firstName);
            sendKeys(getWebDriver().findElement(DRIVER_LAST_NAME), lastName);
            sendKeys(getWebDriver().findElement(DRIVER_MIDDLE_NAME), middleName);
        }
    }
}

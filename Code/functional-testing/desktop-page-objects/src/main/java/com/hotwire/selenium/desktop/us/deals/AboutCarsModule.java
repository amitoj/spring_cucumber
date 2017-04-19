/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.deals;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 7/18/14
 * Time: 1:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class AboutCarsModule extends AbstractUSPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AboutCarsModule.class.getSimpleName());
    private static final String ABOUT_OUR_CARS = "#destinationDetails";
    private static final String ACTUAL_CAR_TYPES = ".content.rightSide ul li a";

    private  List<String> expectedCarTypes = Arrays.asList(
            "Economy car",
            "Compact car",
            "Mid-size car",
            "Standard car",
            "Full-size car",
            "Minivan",
            "Hybrid car",
            "Mid-size SUV",
            "Standard SUV",
            "Full-size SUV",
            "Premium car",
            "Convertible car",
            "Luxury car");


    public AboutCarsModule(WebDriver webDriver) {
        super(webDriver, By.cssSelector(ABOUT_OUR_CARS));
    }

    public List<WebElement> getCarTypes() {
        return getWebDriver().findElements(By.cssSelector(ACTUAL_CAR_TYPES));
    }

    public boolean isContainsAllExpectedCarTypes() {
        for (WebElement element : getCarTypes()) {
            if (!expectedCarTypes.contains(element.getText())) {
                LOGGER.info("ABOUT OUR CAR module doesn't contain " + element.getText());
                return false;
            }
        }
        return true;
    }
}

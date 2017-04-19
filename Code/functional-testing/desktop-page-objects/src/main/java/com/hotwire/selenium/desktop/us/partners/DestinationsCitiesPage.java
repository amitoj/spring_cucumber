/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA. User: v-ikomarov Date: 1/13/14 Time: 1:10 AM To change this template use File | Settings
 * | File Templates.
 */
public class DestinationsCitiesPage extends AbstractDesktopPage {


    public DestinationsCitiesPage(WebDriver webdriver) {
        super(webdriver);
    }

    public void verifyCitesForLetter(String letter) {
        List<WebElement> cites = getWebDriver()
            .findElements(By.xpath(".//a[@name='" + letter + "']//..//a[@onclick]"));

        assertThat(cites)
            .as("Amount of the cites which start from " + letter + " letter not empty")
            .isNotEmpty();
        Integer i = 1;
        for (WebElement city : cites) {
            assertThat(city.getText())
                .as(String.format("City N%d for letter %s should starts with %s", i, letter, letter))
                .startsWith(letter);
            assertThat(city.getText().length())
                .as(String.format("The length of the city + state %s more then 5 signs", city.getText()))
                .isGreaterThan(5);
            i++;
        }

        assertThat(!cites.isEmpty()).isTrue();
    }


    public void verifyUrl() {
        assertThat(getWebDriver().getCurrentUrl())
            .as("Popup window url contains:  /pop-up/hotelDestination ")
            .contains("/pop-up/hotelDestinations");
    }
}

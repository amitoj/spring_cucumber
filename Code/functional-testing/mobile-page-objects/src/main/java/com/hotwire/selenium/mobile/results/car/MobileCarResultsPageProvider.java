/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.results.car;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static utils.MobilePageProviderUtils.getVersionTests;

/**
 * User: achotemskoy
 * Date: 12/18/14
 * Time: 2:59 PM
 * This class is needed to determine which results page we got.
 * There is different pages for Airport and Local results.
 * And also there is MCR14 version test that changes layout for results page.
 */
public final class MobileCarResultsPageProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileCarResultsPageProvider.class);

    private MobileCarResultsPageProvider() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    public static MobileCarResultsPage getMobileCarResultsPage(WebDriver driver) {
        waitForResultsPageToLoad(driver);

        List<String> activatedVersionTests = getVersionTests(driver, "eVar1");
        LOGGER.info("vt: {}", activatedVersionTests);

        if (isLocalCarResults(driver)) {
            LOGGER.info("Local (City) results found.");
            return new MobileCarLocalResultsPage(driver);
        }
        else if (activatedVersionTests.contains("MCR14-02")) {
            LOGGER.info("Airport new results found. (MCR14=2 version test)");
            return new MobileCarAirportResultsMCR14Page(driver);
        }
        else {
            LOGGER.info("Airport old results found. (MCR14=1 version test)");
            return new MobileCarAirportResultsPage(driver);
        }
    }

    //Verify which type of results we have. Local results is results for city search. Layout is different in this case.
    public static boolean isLocalCarResults(WebDriver driver) {
        waitForResultsPageToLoad(driver);
        return driver.findElement(By.cssSelector("div#results")).getAttribute("class").contains("local");
    }

    public static boolean isResultListEmpty(WebDriver driver) {
        waitForResultsPageToLoad(driver);
        if (!isLocalCarResults(driver)) {
            return driver.findElement(By.cssSelector("div#results")).findElements(By.xpath("div[@class='result ref']"))
                .size() < 1;
        }

        else {
            return driver.findElement(By.cssSelector("div#results"))
                    .findElements(By.xpath("div[contains(@class,'location')]")).size() < 1;

        }
    }

    private static void waitForResultsPageToLoad(WebDriver driver) {
        new MobileAbstractPage(driver,
                "tile.car.results",
                new InvisibilityOf(By.cssSelector(".loading img")),
                MobileAbstractPage.TIME_TO_WAIT,
                MobileAbstractPage.MAX_SEARCH_PAGE_WAIT_SECONDS);

    }
}

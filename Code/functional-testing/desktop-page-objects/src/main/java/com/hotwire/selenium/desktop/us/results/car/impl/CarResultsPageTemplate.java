/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results.car.impl;


import com.hotwire.selenium.desktop.us.results.car.CarResultsPage;
import com.hotwire.selenium.desktop.us.results.car.fragments.CarSolutionFragment;
import com.hotwire.selenium.desktop.us.results.car.fragments.CarSolutionFragmentsList;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: v-achotemskoy
 * Date: 7/7/2015
 * Time: 8:29 AM
 * <p/>
 * Current main desktop car results page. It is turned on for 100%.
 */
public class CarResultsPageTemplate extends AbstractPageObject implements CarResultsPage {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CarResultsPageTemplate.class);
    private static final String[] PAGE_TILE_DEFINITIONS = new String[]
    {"tiles-def.car.results*", "tiles-def.car.no-results"};

    private static final By CONTAINER = By.cssSelector("#tileName-results");

    @FindBy(xpath = "id('tileName-discountCode')/div[1]")
    private WebElement discountCodeNote;

    public CarResultsPageTemplate(WebDriver webDriver) {
        super(webDriver, CONTAINER, PAGE_TILE_DEFINITIONS);
        LOGGER.info(getDesignId() + " was activated for car results page");
    }

    public CarResultsPageTemplate(WebDriver webDriver, String[] pageTileDefinition) {
        super(webDriver, pageTileDefinition);
        LOGGER.info(getDesignId() + " was activated for car results page");
    }

    /**
    * Used for logging. There is couple of implementations of CarResults page - Main and CCF.
     * @return String with name of CarResultsPage object were used.
    **/
    public String getDesignId() {
        return "Main version";
    }

    /**
    * Used in parent page objects - AbstractPageObject and others.
    * Results page takes more time to load, and sometimes it exceeds default 30 seconds timeout.
    * @return hardcoded 90;
    **/
    @Override
    protected int getTimeout() {
        return 90;
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(
                "#tileName-results .resultWrapper, #tileName-results .result, .noResults, #tileName-results .infoMsgs");
    }

    /**
    Basic method to start selecting candidates.
    Returns list that represents list of car solutions on car results page.
    Also provides different methods to filter list by different parameters.
    For example you need to select cheapest opaque solution:
    <p></p>
    CarResultsPageProvider.get(driver).getResult()
     .opaque()
     .cheapest()
     .select();
    <p></p>
    You can extend it with any filters that you need. If you don't care about type, price, and other - simply use:
     <p></p>
     CarResultsPageProvider.get(driver).getResult()
     .select();
     <p></p>
    This will choose first result for you.
    @return CarSolutionFragmentsList[CarSolutionFragment]
    */
    @Override
    public CarSolutionFragmentsList<CarSolutionFragment> getResult() {
        return new CarSolutionFragmentsList<>(getWebDriver());
    }

    @Override
    public Boolean isDiscountNoteDisplayed() {
        try {
            discountCodeNote.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Can be used to know is this results is local search results.
     */
    @Override
    public boolean isNearbyAirportModuleDisplayed() {
        try {
            getWebDriver().findElement(By.cssSelector("div.nearbyAirportsModule")).isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean isNoResultsMessageDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(".noResults h2, #tileName-results .infoMsgs"))
                    .isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getStartLocation() {
        return getWebDriver().findElement(By.id("carRefineSearch-startLocation")).getAttribute("value");
    }

    // Hot rate module - located at the left of the page, this is image that describes what means Hotwire Hot Rate.
    public boolean isWhatIsHotRateModuleDisplayed() {
        return getWebDriver().findElement(By.xpath("//*/img[@alt='Hotwire Hot Rate']")).isDisplayed();
    }

    // These method return fragment to verify that page contains required legal texts.
    // Different texts are shown depending on the results that we have. For example - when we have only Opaque results,
    // there will be only OpaqueRatesDisclaimer text.
    public LegalNotesFragment getLegalNotesFragment() {
        return new LegalNotesFragment(getWebDriver());
    }

    public boolean isIntentMediaElementsExisting() {
        try {
            getWebDriver().findElement(By.id("IntentMediaRail"));
            getWebDriver().findElement(By.id("IntentMediaIntercard"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isMeSoBannersDisplayed() {
        WebElement banner1;
        WebElement banner2;
        try {
            banner1 = getWebDriver().findElement(By.id("carResBan1"));
            banner2 = getWebDriver().findElement(By.id("carResBan2"));
        }
        catch (NoSuchElementException e) {
            return false;
        }

        return banner1.getAttribute("data-displayed").equals("true") ||
                banner1.getAttribute("data-loaded").equals("true") ||
                banner2.getAttribute("data-displayed").equals("true") ||
                banner2.getAttribute("data-loaded").equals("true");
    }

}




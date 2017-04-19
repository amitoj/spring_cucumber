/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.helpcenter;

import java.util.HashMap;
import java.util.Map;

import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.predicates.HasPageName;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class HelpCenterPage extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpCenterPage.class);

    private static final String TILES_DEF_HELPCENTER_INDEX = "tiles-def.helpcenter.index";

    private static Map<String, String> countryToCountryCodeMap = new HashMap<String, String>() {
        {
            put("United Kingdom", "uk");
            put("Australia", "au");
            put("Ireland", "ie");
            put("Sverige", "se");
            put("Norge", "no");
            put("Danmark", "dk");
            put("New Zealand", "nz");
            put("Deutschland", "de");
            put("Hong Kong", "hk");
            put("Singapore", "sg");
            put("México", "mx");
            put("Mexico", "mx");
        }
    };

    private static Map<String, String> countryContactPhoneMap = new HashMap<String, String>() {
        {
            put("United Kingdom", "0808 234 5903");
            put("Australia", "1 800 306 217");
            put("Ireland", "1 800 760738");
            put("Sverige", "020 797 237");
            put("Norge", "80 01 72 28");
            put("Danmark", "80 25 00 72");
            put("New Zealand", "0800 452 690");
            put("Deutschland", "0800 723 5637");
            put("Hong Kong", "800 933 475");
            put("Singapore", "800 120 6272");
            put("México", "001-8554643751");
            put("Mexico", "001-8554643751");
        }
    };

    @FindBy(xpath = "//a[contains(@href, '/hotels/searching-and-booking/index.jsp')]")
    private WebElement hotelsSearchingAndBookingQA;

    @FindBy(xpath = "//a[contains(@href, '/cars/searching-and-booking/index.jsp')]")
    private WebElement carsSearchingAndBookingQA;

    @FindBy(xpath = "//a[contains(@href, '/flights/searching-and-booking/index.jsp')]")
    private WebElement airsSearchingAndBookingQA;

    @FindBy(css = ".group.email")
    private WebElement groupEmail;

    public HelpCenterPage(WebDriver webdriver) {
        super(webdriver);
        if (webdriver.findElements(By.xpath("//meta[@name='pageName']")).size() > 0) {
            // Meta tag for page name exists.
            LOGGER.info("Page name meta tag exists. Checking if old help center page.");
            new WebDriverWait(getWebDriver(), 5).until(waitForExpectedPageName(webdriver));
        }
        else {
            LOGGER.info("Page name meta tag missing. Checking if new help center page.");
            new WebDriverWait(getWebDriver(), 5).until(new VisibilityOf(By
                .cssSelector(".homeContent .breadcrumbs li a")));
        }

    }

    public static Map<String, String> getCountryContactPhoneMap() {
        return countryContactPhoneMap;
    }

    public WebElement getHotelsSearchingAndBookingQA() {
        return hotelsSearchingAndBookingQA;
    }

    public WebElement getCarsSearchingAndBookingQA() {
        return carsSearchingAndBookingQA;
    }

    public WebElement getAirsSearchingAndBookingQA() {
        return airsSearchingAndBookingQA;
    }

    private ExpectedCondition<Boolean> waitForExpectedPageName(final WebDriver webdriver) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver wedriver) {
                return new HasPageName(TILES_DEF_HELPCENTER_INDEX).apply(new PageName().apply(webdriver));
            }
        };
    }

    public String getSupportInfo(String countryName) {
        return getWebDriver().findElement(
            By.xpath("//div[contains(@class, 'helpContact')]//span[@class='" +
                countryToCountryCodeMap.get(countryName) + "']//following-sibling::small")).getText();
    }

    public String getGroupEmailContent() {
        return getWebDriver().findElement(By.cssSelector(".group.email")).getText();
    }

    public String getSupportGroupContent() {
        return getWebDriver().findElement(By.xpath("//div[@class='panelBody']/child::div[@class='group']"))
            .getText();
    }

    public String getUIContactPhoneForCountry(String country) {
        return getWebDriver().findElement(
            By.xpath("//div[contains(@class, 'helpContact')]//span[@class='" +
                countryToCountryCodeMap.get(country) + "']//parent::p"))
                .getText();
    }

    public String getLocationSupporInfo() {
        return getWebDriver().findElement(
            By.className("locationSupportInfo")).getText();
    }

}

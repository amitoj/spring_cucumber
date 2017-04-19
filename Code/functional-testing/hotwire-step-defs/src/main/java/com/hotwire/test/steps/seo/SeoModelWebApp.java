/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.seo;

import java.util.ArrayList;

import com.hotwire.selenium.desktop.us.HomePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import com.hotwire.selenium.desktop.seo.DestinationListPage;
import com.hotwire.selenium.desktop.seo.InformationPageUS;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: vjong
 * Date: Jun 29, 2012
 * Time: 1:34:52 PM
 */
public class SeoModelWebApp implements SeoModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeoModelWebApp.class.getName());
    private static final int MAX_PAGE_CHECKS = 20;
    private static final String UNFRIENDLY_SEO_CHARS = "?%=+&";
    private static final long DEFAULT_WAIT_TIME = 20;

    private WebDriver webdriver;
    private InformationPageUS hotelsInfo;
    private DestinationListPage destinationListPage;
    private ArrayList<String> top100;
    private ArrayList<String> destinationList;

    public void setWebdriver(WebDriver webdriver) {
        this.webdriver = webdriver;
    }

    public void setTop100(ArrayList<String> top100) {
        this.top100 = top100;
    }

    public void setDestinationList(ArrayList<String> destinationList) {
        this.destinationList = destinationList;
    }

    @Override
    public void goToHotelsInformation() {
        HomePage homePage = new HomePage(webdriver);
        homePage.getHotelInfoPage().click();
        hotelsInfo = PageFactory.initElements(webdriver, InformationPageUS.class);
    }

    @Override
    public void getDestinationLinks(String set, String country) {
        waitForElementToBeVisible(By.xpath(hotelsInfo.getSeeAllLinkLocator()));
        if (set.trim().equals("all")) {
            LOGGER.info("Link set is 'all'. Going to see all destinations page.");
            hotelsInfo.clickSeeAllLink();
            if (country == null) {
                throw new RuntimeException("Expected non-null country for doing all destinations.");
            }
            webdriver.findElement(By.partialLinkText(country)).click();
            destinationListPage = PageFactory.initElements(webdriver, DestinationListPage.class);
            waitForElementToBeVisible(By.xpath(destinationListPage.getCityListLocator()));
            destinationList = destinationListPage.getDestinationUrls();

        }
        else {
            LOGGER.info("Link set is top 100.");
            top100 = hotelsInfo.getDestinationUrls("Hotel");
        }
    }

    private void waitForElementToBeVisible(final By locator) {
        new WebDriverWait(webdriver, DEFAULT_WAIT_TIME)
                .until(
                        new ExpectedCondition<Boolean>() {
                            @Override
                            public Boolean apply(WebDriver webdriver) {
                                boolean isDisplayed = webdriver.findElement(locator).isDisplayed();
                                LOGGER.info("Is '" + locator.toString() + "' displayed: " + isDisplayed);
                                return isDisplayed;
                            }
                        });
    }

    @Override
    public void verifyDestinationLinks(String source) {
        if (source.trim().equals("destination")) {
            // Verify the first couple links as these are dynamically generated and if one of the links is broken,
            // then all of them will be broken.
            for (int i = 0; i < MAX_PAGE_CHECKS; i++) {
                String href = destinationList.get(i);
                LOGGER.info("HREF: " + href);
                assertThat(StringUtils.containsAny(href, UNFRIENDLY_SEO_CHARS)).isFalse();
                webdriver.navigate().to(href);
                assertThat(webdriver.findElement(By.className("landingPageTitle")).getText().contains("Hotel Deals"))
                        .isTrue();
            }
        }
        else {
            // Top 100. Check all links for SEO unfriendly characters, but only check up to the max checks for the
            // destination page.
            int i = 0;
            for (String href : top100) {
                LOGGER.info("HREF: " + href);
                assertThat(StringUtils.containsAny(href, UNFRIENDLY_SEO_CHARS)).isFalse();
                if (i < MAX_PAGE_CHECKS) {
                    webdriver.navigate().to(href);
                    assertThat(webdriver.findElement(By.className("landingPageTitle")).getText().contains("Hotel " +
                            "Deals")).isTrue();
                }
                i++;
            }
        }
    }

    @Override
    public void verifyDefaultSelectedRegion(String region) {
        String selectedRegion = destinationListPage.getSelectedCountryFromDropdownList().trim();
        LOGGER.info("Expecting: " + region + ", Got: " + selectedRegion);
        assertThat(region.trim()).isEqualTo(selectedRegion);
    }
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.partners;

import static org.fest.assertions.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.details.AirDetailsPage;
import com.hotwire.selenium.desktop.us.partners.CarPartnerPageProvider;
import com.hotwire.selenium.desktop.us.partners.CarPartnersPage;
import com.hotwire.selenium.desktop.us.partners.DestinationsCitiesPage;
import com.hotwire.selenium.desktop.us.results.AirResultsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.desktop.us.results.car.fragments.banner.CrStaticHtmlBanner;
import com.hotwire.selenium.desktop.us.results.car.fragments.banner.CrStaticImgBanner;
import com.hotwire.selenium.desktop.us.results.car.fragments.compareWith.CompareWithFragment;
import com.hotwire.selenium.desktop.us.search.CarSearchFragment;
import com.hotwire.selenium.desktop.us.search.MultiVerticalFareFinder;
import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.SessionModifingParams;

import cucumber.api.DataTable;

/**
 * Created by IntelliJ IDEA.
 * User: vjong
 * Date: Jul 5, 2012
 * Time: 9:23:09 AM
 */
public class PartnersModelWebApp extends PartnersModelTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnersModelWebApp.class.getName());
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");
    private static final int DEFAULT_DAYS_TO_ADD = 3;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("sessionModifingParams")
    private SessionModifingParams sessionModifingParams;

    private HashMap<String, String> partnerUrls;
    private URL applicationUrl;
    private CompareWithFragment compareWithFragment;

    public void setPartnerUrls(HashMap<String, String> partnerUrls) {
        this.partnerUrls = partnerUrls;
    }

    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    @Override
    public void followSideDoorUrl() throws UnsupportedEncodingException {
        String url =
                applicationUrl +
                        "/hotel/search-options.jsp?inputId=index&destCity=San+Francisco," +
                        "+CA&startDate=%s&endDate=%s&sid=S174&bid=B265179";

        Date date = new Date();
        String startDate = DATE_FORMATTER.format(DateUtils.addDays(date, DEFAULT_DAYS_TO_ADD));
        String endDate = DATE_FORMATTER.format(DateUtils.addDays(date, DEFAULT_DAYS_TO_ADD * 2));
        String sideDoorUrl = String.format(url, startDate, endDate);
        LOGGER.info("Navigating to: " + sideDoorUrl);
        getWebdriverInstance().navigate().to(sideDoorUrl);
    }

    @Override
    public void verifyPartnerBannerIsDisplayed(String partner) {
        String partnerLocator = "//img[@title='" + partner + "']";
        assertThat(getWebdriverInstance().findElement(By.xpath(partnerLocator)).isDisplayed()).isTrue();
    }

    @Override
    public void clickVerticalTab(String vertical) {
        searchParameters.setLandingPage(true);
        new GlobalHeader(getWebdriverInstance()).navigateToVerticalLandingPageFromNavigationTab(vertical);
    }

    @Override
    public void navigatePartnersLink(String partner) {
        String partnerUrl = partnerUrls.get(partner);
        Date date = new Date();
        if (partnerUrl.contains("startDate=%s&endDate=%s")) {
            partnerUrl = String.format(
                    partnerUrl,
                    DATE_FORMATTER.format(DateUtils.addDays(date, DEFAULT_DAYS_TO_ADD)),
                    DATE_FORMATTER.format(DateUtils.addDays(date, DEFAULT_DAYS_TO_ADD * 2)));
        }
        String url = applicationUrl + partnerUrl;
        LOGGER.info("Navigating to: " + url);
        getWebdriverInstance().navigate().to(url);
    }

    @Override
    public void verifyPartnerDealInResults() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        if (!resultsPage.opaqueResultsAreDisplayed()) {
            LOGGER.info("Retail results displayed condition. Choosing opaque.");
            resultsPage.chooseOpaque();
        }
        // A compression message of either deal at lower price, deal at higher price, or no deal found will be seen.
        assertThat(resultsPage.heresYourDealIsDisplayed()).isTrue();
        LOGGER.info(resultsPage.getMessageBoxMessage());
    }

    @Override
    public void verifyCarCompareWithPartnersCheckboxesState(String partnerName, String state) {
        if (!searchParameters.isLandingPage()) {
            (new MultiVerticalFareFinder(getWebdriverInstance())).chooseCar();
        }

        assertThat((new CarSearchFragment(getWebdriverInstance())).checkCompareWithCheckboxState(partnerName, state))
                .as("Compare with " + partnerName + " is " + state)
                .isTrue();
    }

    @Override
    public void selectPartnersToCompareWith(List<String> partners, String blockId) {
        if ("fare finder".equals(blockId)) {
            new CarSearchFragment(getWebdriverInstance()).checkComparableCarPartner(partners);
        }
        else if ("first D2C module".equals(blockId)) {
            new CompareWithFragment(getWebdriverInstance()).selectPartners(partners);

        }
        else if ("second D2C module".equals(blockId)) {
            new CompareWithFragment(getWebdriverInstance(),
                    By.cssSelector(CompareWithFragment.SECONDARY_D2C_MODULE)).selectPartners(partners);
        }
    }

    /**
     * Used in verifyCarPartnerSearchOptions(DataTable params)
     * Comparing search opt on partner's page with our
     */
    @Override
    public void verifyCarPartnerSearchOptions(DataTable params) {

        try {
            Thread.sleep(15000);
        }
        catch (InterruptedException e) {
            /**
             * Wait while all popup windows were loaded...
             * Doesn't work without it.
             */
        }

        String rootWindowHandler = getWebdriverInstance().getWindowHandle();

        /**
         * Get handler of partner's popup windows
         * and wait while inner content was appear
         */
        Map<String, String[]> partnersSearchQueries = new HashMap<String, String[]>();

        for (String h : getWebdriverInstance().getWindowHandles()) {
            if (!h.equals(rootWindowHandler) && !h.isEmpty()) {
                WebDriver partnerPopupWebDriver = getWebdriverInstance().switchTo().window(h);

                CarPartnersPage partnersPage = CarPartnerPageProvider.get(partnerPopupWebDriver);

                /**
                 * Put actual and expected values from partner's page
                 * Expected object is needed because date format for each
                 * partner is different.
                 */
                partnersSearchQueries.put(
                        partnersPage.getName().toString(),
                        new String[]{
                            partnersPage.getActualSearchQuery(),
                            partnersPage.getExpectedSearchQuery(
                                    searchParameters.getStartDate(),
                                    searchParameters.getEndDate()
                            )
                        });

                /**
                 * If we try to verify params in "for" statement when
                 * case will be failed then all other popups
                 * will not be closed.
                 */
                partnersPage.close();
            }
        }

        /**
         * Switch back to main window
         */
        getWebdriverInstance().switchTo().window(rootWindowHandler);

        // Checking count of opened partner's windows
        assertThat(partnersSearchQueries.size())
                .as("Count of partner's popups doesn't correct")
                .isEqualTo(params.raw().size());

        for (List<String> row : params.raw()) {

            String[] values = partnersSearchQueries.get(row.get(0));

            String actual = values[0].replaceAll("[\n]", " ");
            assertThat(actual).as("Search query for " + row.get(0)).isNotNull();

            String expected = values[1];
            String expectedFormatted = expected.replaceAll("%LOCATION%", row.get(1));
            assertThat(expectedFormatted)
                    .as("Search query for " + row.get(0))
                    .contains(actual);
        }
    }

    @Override
    public void verifyPartnerPopupVisibility(String partnerName, boolean isVisible) {
        Set<String> handlers = getWebdriverInstance().getWindowHandles();
        if (!isVisible) {
            // Check that no one window does not appear.
            assertThat(handlers.size()).as("Partner's page was open").isEqualTo(1);
        }
        else {
            assertThat(handlers.size()).as("Partner's page wasn't open").isGreaterThan(1);
        }
    }

    @Override
    public void allCarRetailersSelected(boolean state) {
        assertThat(new CompareWithFragment(getWebdriverInstance()).verifyAllPartnersState(state))
                .as("Not all of partners were selected")
                .isTrue();
    }

    @Override
    public void selectAllCarRetailers() {
        new CompareWithFragment(getWebdriverInstance()).selectAllCarPartners();
    }

    @Override
    public void compareAllRetailersFromD2C(String numberOfModule) {
        if (numberOfModule.equalsIgnoreCase("first")) {
            new CompareWithFragment(getWebdriverInstance()).compare();
        }
        else {
            new CompareWithFragment(getWebdriverInstance(),
                    By.cssSelector(CompareWithFragment.SECONDARY_D2C_MODULE)).compare();
        }
    }

    @Override
    public void verifyCarRentalsBannerOnResultsPage(String bannerType) {

        if ("static".equals(bannerType)) {
            switch (sessionModifingParams.getParamValue("vt.CRB12")) {
                case 2:
                    new CrStaticImgBanner(getWebdriverInstance());
                    break;
                case 3:
                    new CrStaticHtmlBanner(getWebdriverInstance());
                    break;
                default:
                    new RuntimeException("Invalid parameters set");
            }
        }
        else if ("dynamic".equals(bannerType)) {
            new UnimplementedTestException("Not implement yet!");
        }
        else {
            new RuntimeException("Invalid parameters set");
        }
    }

    @Override
    public void verifyCarPartnerIsDisplayed(String partner, String visibility) {
        boolean vparam = true;
        if (visibility.contains("in")) {
            vparam = false;
        }

        assertThat(new CompareWithFragment(getWebdriverInstance()).verifyPartnersVisibility(partner, vparam))
            .as("Partner visibility state are not corresponding search type")
            .isTrue();
    }

    @Override
    public void verifyMeSoAds(String page) {
        switch(page) {
            case "home":
                assertThat(new HomePage(getWebdriverInstance()).checkMesoAdsIsDisplayed())
                        .as("MeSo banner is not displayed on the home page")
                        .isTrue();
                break;
            case "air result":
                assertThat(new AirResultsPage(getWebdriverInstance()).checkMesoAdsIsDisplayed())
                        .as("MeSo banner is not displayed on the air result page")
                        .isTrue();
                break;
            case "air details":
                assertThat(new AirDetailsPage(getWebdriverInstance(), "tiles-def.air.review-fare")
                        .checkMesoAdsIsDisplayed())
                        .as("MeSo banner is not displayed on the air details page")
                        .isTrue();
                break;
            default:
                break;
        }
    }

    @Override
    public void verifyDestinationCitiesPage() {
        ErrorMessenger msg = new ErrorMessenger(getWebdriverInstance());
        msg.getErrorMessagesBlock().get(0).findElement(By.xpath(".//a[contains(@onclick,seeAllCities)]")).click();
        try {
            Thread.sleep(15000);
        }
        catch (InterruptedException e) {
            /**
             * Wait while all popup windows were loaded...
             * Doesn't work without it.
             */
        }
        String rootWindowHandler = getWebdriverInstance().getWindowHandle();

        /**
         * Get handler of partner's popup windows
         * and wait while inner content was appear
         */

        for (String h : getWebdriverInstance().getWindowHandles()) {
            if (!h.equals(rootWindowHandler) && !h.isEmpty()) {
                WebDriver partnerPopupWebDriver = getWebdriverInstance().switchTo().window(h);

                DestinationsCitiesPage destinationsCitiesPage = new DestinationsCitiesPage(partnerPopupWebDriver);

                destinationsCitiesPage.verifyUrl();
                destinationsCitiesPage.verifyCitesForLetter("A");
                /**
                 * If we try to verify params in "for" statement when
                 * case will be failed then all other popups
                 * will not be closed.
                 */
                partnerPopupWebDriver.close();
            }
        }

    }

}

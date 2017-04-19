/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.deals;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.hotwire.selenium.desktop.us.deals.AboutCarsModule;
import com.hotwire.selenium.desktop.us.deals.CarDealsPage;
import com.hotwire.selenium.desktop.us.deals.DealsPage;
import com.hotwire.selenium.desktop.us.deals.FlightsDealsPage;
import com.hotwire.selenium.desktop.us.deals.HotelDealPage;
import com.hotwire.selenium.desktop.us.deals.VacationsPage;
import com.hotwire.selenium.desktop.us.search.CarSearchFragment;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.testing.UnimplementedTestException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.seo.InformationPageUS;
import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.GlobalFooter;
import com.hotwire.selenium.desktop.us.index.AirIndexPage;
import com.hotwire.selenium.desktop.us.index.CarIndexPage;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.selenium.desktop.us.results.AirResultsPage;
import com.hotwire.selenium.desktop.us.results.CarResultsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsSearchResultsFragment;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.po.PageObjectUtils;

import cucumber.api.PendingException;

/**
 * Domestic implementation of deals steps.
 */
public class DealsModelWebApp extends DealsModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(DealsModelWebApp.class.getName());
    private static final String OPM_VT = "&vt.OPM12=";
    private static final String SMART_OPAQUE_CROSS_SELL_VT = "vt.NWB12=1";

    private URL applicationUrl;


    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    @Override
    public void navigateToOpmEnablerDeal(String provider, String inventoryTypeLink, boolean testOpm) {
        String url = applicationUrl +
                     new DealLinkFactory().getLinkUrl(inventoryTypeLink, provider) +
                     OPM_VT + (testOpm ? "2" : "1");
        LOGGER.info("OPM Deals Link: " + url);
        getWebdriverInstance().navigate().to(url);
    }

    @Override
    public void verifyInventoryErrorDisplayed() {
        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains(".results")) {
            //we ended up on the results page polling...block/wait until that's done.
            new HotelResultsPage(getWebdriverInstance());
        }
        //now that results are in, we are either on the details page OR on the results page with an inventory message
        pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains(".results")) {
            if (getWebdriverInstance().findElements(By.cssSelector(" .featuredRetail")).size() > 0) {
                // vt.OPM12=2 set.
                LOGGER.info("After searching, expected invalid OPM deals link became valid on vt.OPM12=2.");
            }
            else {    // Expected behavior
                assertThat(new HotelResultsPage(getWebdriverInstance())
                        .getSearchResultsFragment().hasCompressionMessage()).isTrue();
            }
        }
        else {
            new HotelDetailsPage(getWebdriverInstance());
            LOGGER.info("After searching, expected invalid OPM deals link became valid.");
        }
    }

    @Override
    public void verifyHotelDetails(boolean shouldBeOpaque) {
        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains(".results")) {
            //we ended up on the results page polling...block/wait until that's done.
            new HotelResultsPage(getWebdriverInstance());
        }
        //now that results are in, we are either on the details page OR on the results page with an inventory message
        pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains(".results") && shouldBeOpaque) {    // If vt.OPM12=2
            assertThat(new HotelResultsPage(getWebdriverInstance()).opaqueResultsAreDisplayed()).isTrue();
            assertThat(getWebdriverInstance().findElement(By.cssSelector(" .featuredRetail")).isDisplayed()).isTrue();
            assertThat(getWebdriverInstance()
                    .findElement(By.cssSelector(" .featuredRetail .resultCTA.retail")).isDisplayed()).isTrue();
            assertThat(getWebdriverInstance()
                    .findElement(By.cssSelector(" .featuredRetail .resultInfo.retail")).isDisplayed()).isTrue();
        }
        else if (pageName.contains(".results")) {        // No inventory, expired deal.
            assertThat(new HotelResultsPage(getWebdriverInstance())
                    .getSearchResultsFragment().hasCompressionMessage()).isTrue();
            LOGGER.info("After searching, expected valid OPM deals link became expired.");
        }
        else {    // old OPM version vt.OPM12=1. Verify on retail details page. Expected behavior.
            assertThat(new HotelDetailsPage(getWebdriverInstance()).isOpaqueHotelDetailsPageDisplayed()).isFalse();
        }
    }

    @Override
    public void goToSuperPageLink(String vertical, String channel) {
        // This web app should not receive a request for international super page link
        if ("international".equalsIgnoreCase(channel)) {
            throw new PendingException("Desktop site has got a request for an international super page link." +
                                       " This is not right");
        }
        String url = new DealLinkFactory().getMarketingLinkUrl(vertical, "superPage");
        String fullUrl = getFullyQualifiedUrlForApplication(url);
        getWebdriverInstance().navigate().to(fullUrl);
    }

    @Override
    public void goToDBMLink(String vertical) {
        goToDBMLink(vertical, "dmb");
    }

    @Override
    public void goToDBMLink(String vertical, String channel) {
        String url = new DealLinkFactory().getMarketingLinkUrl(vertical, channel);
        String fullUrl = getFullyQualifiedUrlForApplication(url);
        LOGGER.info("Navigating to: " + fullUrl);
        getWebdriverInstance().navigate().to(fullUrl);
    }

    @Override
    public void goToSearchOptionsLink(String vertical) {
        String url = new DealLinkFactory().getMarketingLinkUrl(vertical, "search_option");
        String fullUrl = getFullyQualifiedUrlForApplication(url);
        getWebdriverInstance().navigate().to(fullUrl);
    }

    @Override
    public void goToCrossSellLink(String vertical) {
        String url = new DealLinkFactory().getMarketingLinkUrl(vertical, "cross_sell");
        String fullUrl = getFullyQualifiedUrlForApplication(url);
        getWebdriverInstance().navigate().to(fullUrl);
    }

    private String getFullyQualifiedUrlForApplication(String url) {
        String currentUrl = getWebdriverInstance().getCurrentUrl();
        String[] tokens;
        // Mobile needs a different behavior.
        if (currentUrl.contains("/mobile")) {
            tokens = currentUrl.split("/mobile");
        }
        else {
            tokens = currentUrl.split("\\?");
        }
        String rootUrl = tokens[0];
        // Protect against "//" in the url.
        if (rootUrl.endsWith("/") && url.startsWith("/")) {
            rootUrl = rootUrl.substring(0, rootUrl.length() - 1);
        }
        return rootUrl + url;
    }

    @Override
    public void verifyCrossSellInSearchResults() {
        assertThat(
                new HotelResultsPage(getWebdriverInstance())
                        .getSearchResultsFragment().isCrossSellDisplayedAndEnabled())
                .as("Expected to see cross sell deal in search results and enabled.")
                .isTrue();
    }

    @Override
    public void navigateToCrossSellDeal() {
        //  Fix method sig, remove conditional, and use implementation of winning version of
        // cross sell deal.
        // The different cross sell versions will have the same element locators according to dev.
        new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment().clickCrossSellLink();
    }

    @Override
    public void verifyCrossSellDetails(boolean doNewCrossSell, String expectedMarket) {
        //  Fix method sig, remove conditional, and use implementation of winning version of
        // cross sell deal.
        if (doNewCrossSell) {
            String pageName = new PageName().apply(getWebdriverInstance());
            if (pageName.contains(".results")) {
                // we ended up on the results page polling...block/wait until that's done.
                // Most likely opaque itinerary is no longer available.
                if (new HotelResultsSearchResultsFragment(getWebdriverInstance()).hasCompressionMessage()) {
                    throw new PendingException("Cross Sell itinerary no longer available");
                }
            }
            // Verify by instantiating hotel details page.
            assertThat(
                    new HotelDetailsPage(getWebdriverInstance()).isOpaqueHotelDetailsPageDisplayed())
                    .as("Expected opaque details page.")
                    .isTrue();
        }
        else {
            // Verify by instantiating a hotel results page object.
            if ("opaque".equals(expectedMarket)) {
                assertThat(new HotelResultsPage(getWebdriverInstance()).opaqueResultsAreDisplayed())
                        .as("Expected " + expectedMarket + " cross sell results to be visible.")
                        .isTrue();
            }
            else if ("retail".equals(expectedMarket)) {
                assertThat(!(new HotelResultsPage(getWebdriverInstance()).opaqueResultsAreDisplayed()))
                        .as("Expected " + expectedMarket + " cross sell results to be visible.")
                        .isTrue();
            }
            else {
                throw new PendingException(
                        "Got either an empty string for expectedMarket or " +
                        "the value was not 'retail' or 'opaque'");
            }
        }
    }

    @Override
    public void activateNewOpaqueCrossSell(boolean doNewCrossSell) {
        // Remove this method when winning version of cross sell deal is determined.
        if (doNewCrossSell) {
            // Add vt to applicationUrl and go to it.
            String url = applicationUrl + "/?" + SMART_OPAQUE_CROSS_SELL_VT;
            getWebdriverInstance().navigate().to(url);
        }
    }

    @Override
    public void findDealsFromLandingPage(String vertical) {
        String changedVertical = "";
        if ("flight".equals(vertical)) {
            changedVertical = "Flights";
        }
        else if ("hotel".equals(vertical)) {
            changedVertical = "Hotels";
        }
        else if ("car".equals(vertical)) {
            changedVertical = "Cars";
        }
        else if ("vacation package".equals(vertical)) {
            changedVertical = "Vacations";
        }
        new GlobalHeader(getWebdriverInstance()).navigateToVerticalLandingPageFromNavigationTab(changedVertical);

        if ("flight".equals(vertical)) {

            AirIndexPage aip = new AirIndexPage(getWebdriverInstance());
            assertThat(aip.isAirDealsEmpty())
                    .isFalse();
            aip.selectFirstDeal();
        }
        else if ("hotel".equals(vertical)) {
            HotelIndexPage hip = new HotelIndexPage(getWebdriverInstance());
            assertThat(hip.isHotelDealsEmpty())
                    .isFalse();

            if (!hip.isHotelDealsEmpty()) {
                hip.selectFirstDeal();
            }

        }
        else if ("car".equals(vertical)) {
            CarIndexPage cip = new CarIndexPage(getWebdriverInstance());
            assertThat(cip.isCarDealsEmpty())
                    .isFalse();
            cip.selectFirstDeal();
        }
        else if ("vacation package".equals(vertical)) {
            VacationsPage vp = new VacationsPage(getWebdriverInstance());
            vp.selectVacationDeal();
        }
    }

    @Override
    public void navigateToDealsFromFooter(String vertical) {
        if ("flight".equals(vertical)) {
            new GlobalFooter(getWebdriverInstance()).navigateToFlightDealsPage();
        }
        else if ("hotel".equals(vertical)) {
            new GlobalFooter(getWebdriverInstance()).navigateToHotelDealsPage();
        }
        else if ("car".equals(vertical)) {
            new GlobalFooter(getWebdriverInstance()).navigateToCarDealsPage();
        }
        else if ("vacations".equals(vertical)) {
            new GlobalFooter(getWebdriverInstance()).navigateToVacationsPage();
        }
        else if ("deals".equals(vertical)) {
            new GlobalFooter(getWebdriverInstance()).navigateToDealsPage();
        }
    }

    @Override
    public void verifyHotelDealFromLandingPage() {
        new AbstractUSPage(getWebdriverInstance(), "tiles-def.deals.engine.hotel.index");
        List<WebElement> dealsDetailButtons =
                getWebdriverInstance().findElements(By.cssSelector("#dealData .item td a img"));

        if (dealsDetailButtons.size() == 0) {
            throw new ZeroResultsTestException("0 results from clicked hotel landing page deal.");
        }
        for (WebElement detailButton : dealsDetailButtons) {
            assertThat(detailButton.isDisplayed()).isTrue();
        }
    }

    @Override
    public void verifyCarDealFromLandingPage() {
        if (new PageName().apply(getWebdriverInstance()).contains("car.results")) {
         // Search was triggered. This is valid behavior.
            new CarResultsPage(getWebdriverInstance());
            LOGGER.info("Clicking deal triggered search instead of deals page.");
            return;
        }
        new AbstractUSPage(getWebdriverInstance(), "tiles-def.deals.engine.car.index");
        List<WebElement> detailsButtons = getWebdriverInstance().findElements(By.cssSelector(".btn [alt='Details']"));
        if (detailsButtons.size() == 0) {
            throw new ZeroResultsTestException("0 results from clicked car landing page deal.");
        }
        for (WebElement detailsButton : detailsButtons) {
            assertThat(detailsButton.isDisplayed()).isTrue();
        }
    }

    @Override
    public void verifyAirDealFromLandingPage() {
        if (new PageName().apply(getWebdriverInstance()).contains("air.results")) {
            // Search was triggered. This is valid behavior.
            new AirResultsPage(getWebdriverInstance());
            LOGGER.info("Clicking deal triggered search instead of deals page.");
            return;
        }
        new AbstractUSPage(getWebdriverInstance(), "tiles-def.deals.engine.air.index*");
        List<WebElement> dealsTables = getWebdriverInstance()
                .findElements(By.cssSelector("[data-jsname='DealsTableComp'], [id='dealData']"));

        if (dealsTables.size() == 0) {
            throw new ZeroResultsTestException("0 results from clicked air landing page deal.");
        }
        for (WebElement dealsTable : dealsTables) {
            assertThat(dealsTable.isDisplayed()).isTrue();
        }
    }

    @Override
    public void verifyFarefiderOnDealsPage(String vertical) {
        InformationPageUS informationPageUS = new InformationPageUS(getWebdriverInstance());
        if ("hotel".equalsIgnoreCase(vertical)) {
            assertThat(informationPageUS.isHotelRadiobuttonChecked()).as(vertical + " radiobutton is checked").isTrue();
        }
        else if ("car".equalsIgnoreCase(vertical)) {
            assertThat(informationPageUS.isCarRadiobuttonChecked()).as(vertical + " radiobutton is checked").isTrue();
        }
        else if ("flight".equalsIgnoreCase(vertical)) {
            assertThat(informationPageUS.isAirRadiobuttonChecked()).as(vertical + " radiobutton is checked").isTrue();
        }

    }

    @Override
    public void verifyHeaderOnDealsPage(String vertical) {

        InformationPageUS informationPageUS = new InformationPageUS(getWebdriverInstance());
        if ("hotel".equalsIgnoreCase(vertical)) {
            assertThat(informationPageUS.getHotelDealsHeader().isDisplayed())
                    .as("The header of the Hotel deals page is visible").isTrue();
        }
        else if ("car".equalsIgnoreCase(vertical)) {
            assertThat(informationPageUS.getCarDealsHeader().isDisplayed())
                    .as("The header of the Car deals page is visible").isTrue();
        }
        else if ("flight".equalsIgnoreCase(vertical)) {
            assertThat(informationPageUS.getFlightDealsHeader().isDisplayed())
                    .as("The header of the Flight deals page is visible").isTrue();
        }
    }

    @Override
    public void verifyAllLinksAreUnique(String vertical) {
        InformationPageUS informationPageUS = new InformationPageUS(getWebdriverInstance());
        ArrayList<String> destinationUrls = informationPageUS.getDestinationUrls(vertical);
        for (int i = 0; i < destinationUrls.size(); i++) {
            System.out.println(destinationUrls.get(i));
            for (int j = i + 1; j < destinationUrls.size(); j++) {
                assertThat(destinationUrls.get(i)).isNotEqualTo(destinationUrls.get(j))
                        .as("Destination urls are unique and differ from each other");
            }
        }
    }

    @Override
    public void verifyBrowserTitle(String vertical) {
        assertThat(getWebdriverInstance().getTitle().equalsIgnoreCase("Hotwire " + vertical + "s"))
                .as("Browser title is correct: Hotwire " + vertical + "s").isTrue();
    }

    @Override
    public void verifyEnctiptedDealHash() {
        new WebDriverWait(getWebdriverInstance(), 100)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(".dealsModuleHeader, " +
                        ".universalDealsEngineModule"), true));
        assertThat(getWebdriverInstance().findElement(By.cssSelector(".dealsLabel, .dealsTable")).isDisplayed())
                .isTrue();
    }

    @Override
    public void verifyDealsPage(String page) {
        if (page.equals("deals")) {
            DealsPage dealsPage = new DealsPage(getWebdriverInstance());
            dealsPage.verifyPage();
        }
        else if (page.equals("vacations")) {
            VacationsPage vacationsPage = new VacationsPage(getWebdriverInstance());
            vacationsPage.verifyPage();
        }
    }

    @Override
    public void selectHotelDealOption() {
        HotelDealPage hotelDealPage = new HotelDealPage(getWebdriverInstance());
        hotelDealPage.selectDealOption();
    }

    @Override
    public void verifySearchCriteria() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        resultsPage.waitForUpdatingLayer();
        assertThat(resultsPage.getDestinationName())
            .as("Search location")
            .contains(getDeal().getLocation());
    }

    @Override
    public void verifyVacationDealFromLandingPage() {
        // From the vacation page's deals module, this will just be expedia's page. So not much to verify.
        new AbstractDesktopPage(
            getWebdriverInstance(), new InvisibilityOf(By.cssSelector(".biwInner img#processingImg")), 45);
        assertThat(getWebdriverInstance().findElements(By.id("packageSearchTitle")).size() > 0)
            .as("Expected to be on choose hotel step 1 page.")
            .isTrue();
    }

    @Override
    public void navigateToDiscountInternational(String vertical) {
        if ("flights".equals(vertical)) {
            FlightsDealsPage flightsDealsPage = new FlightsDealsPage(getWebdriverInstance());
            flightsDealsPage.getDiscountIntlFlightsLink().click();
        }
        else if ("cars".equals(vertical)) {
            CarDealsPage carDealsPage = new CarDealsPage(getWebdriverInstance());
            carDealsPage.getDiscountIntlCarsLink().click();
        }

    }

    @Override
    public void verifyDiscountDealsSortedAlphabetically(String vertical) {
        if ("flights".equals(vertical)) {
            FlightsDealsPage flightsDealsPage = new FlightsDealsPage(getWebdriverInstance());
            assertThat(flightsDealsPage.isDiscountLinksSortedAlphabetically())
                    .as("Discount international flights links sorted alphabetically")
                    .isTrue();
        }
        else if ("cars".equals(vertical)) {
            CarDealsPage carDealsPage = new CarDealsPage(getWebdriverInstance());
            assertThat(carDealsPage.isDiscountLinksSortedAlphabetically())
                    .as("Discount international car links sorted alphabetically")
                    .isTrue();
        }
        else if ("hotels".equals(vertical)) {
            throw new UnimplementedTestException("Implement Me!");
        }
    }

    @Override
    public void verifyHeaderForIntlDeals(String vertical) {
        if ("flights".equals(vertical)) {
            FlightsDealsPage flightsDealsPage = new FlightsDealsPage(getWebdriverInstance());
            assertThat(flightsDealsPage.isINTLFlightDealsHeaderExists())
                    .as("Header \' View flights by international destination  \' is visible on the page")
                    .isTrue();
        }
        else if ("cars".equals(vertical)) {
            throw new UnimplementedTestException("Implement Me!");
        }
        else if ("hotels".equals(vertical)) {
            throw new UnimplementedTestException("Implement Me!");
        }
    }

    @Override
    public void verifyURLDiscountInternationalPage(String vertical) {
        if ("car".equals(vertical)) {
            assertThat(getWebdriverInstance().getCurrentUrl().contains("/car-information/intl/index.jsp"))
                    .as("URL for discount car international page is correct")
                    .isTrue();
        }
        else if ("flight".equals(vertical)) {
            assertThat(getWebdriverInstance().getCurrentUrl().contains("/flight-information/intl/index.jsp"))
                    .as("URL for discount flight international page is correct")
                    .isTrue();
        }
        else if ("hotel".equals(vertical)) {
            assertThat(getWebdriverInstance().getCurrentUrl().contains("/hotel-information/intl/index.jsp"))
                    .as("URL for discount hotel international page is correct")
                    .isTrue();
        }
    }

    @Override
    public void verifyURLDiscountUSPage(String vertical) {
        if ("car".equals(vertical)) {
            assertThat(getWebdriverInstance().getCurrentUrl().contains("/car-information/us/"))
                    .as("URL for discount car US page is correct")
                    .isTrue();
        }
        else if ("flight".equals(vertical)) {
            assertThat(getWebdriverInstance().getCurrentUrl().contains("/flight-information/us/"))
                    .as("URL for discount flight US page is correct")
                    .isTrue();
        }
        else if ("hotel".equals(vertical)) {
            assertThat(getWebdriverInstance().getCurrentUrl().contains("/hotel-information/us/"))
                    .as("URL for discount hotel US page is correct")
                    .isTrue();
        }
    }

    @Override
    public void chooseTheFirstLinkFromAllCitiesAndVerifyURL(String vertical) {
        InformationPageUS informationPageUS = new InformationPageUS(getWebdriverInstance());
        ArrayList<WebElement> destinationUrls = informationPageUS.getDestinationUrlsWebElements(vertical);
        ArrayList<String> destinationUrlsText = informationPageUS.getDestinationUrls(vertical);
        destinationUrls.get(0).click();

        assertThat(destinationUrlsText.get(0).split("\\?")[0].equals(getWebdriverInstance().getCurrentUrl()))
                .as("Current URL is correct: " + getWebdriverInstance().getCurrentUrl())
                .isTrue();

    }

    @Override
    public void chooseTheFirstLinkFromAllCitiesAndVerifyLocationAndURL(String vertical) {
        InformationPageUS informationPageUS = new InformationPageUS(getWebdriverInstance());
        ArrayList<WebElement> destinationUrls = informationPageUS.getDestinationUrlsWebElements(vertical);
        ArrayList<String> destinationUrlsText = informationPageUS.getDestinationUrls(vertical);
        String firstLocationFromAllCities  = destinationUrls.get(0).getText();
        destinationUrls.get(0).click();


        if ("car".equals(vertical)) {
            CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance(), By.name("searchCar"));
            String[] location = carSearchFragment.getStartLocation().getAttribute("value").split(",");
            assertThat(firstLocationFromAllCities.contains(location[0]))
                    .as("Pre-populated location contains: " + location[0]).isTrue();
            assertThat(firstLocationFromAllCities.contains(location[1]))
                    .as("Pre-populated location contains: " + location[1]).isTrue();
        }
        else if ("flight".equals(vertical)) {
            throw new UnimplementedTestException("Implement Me!");
        }
        else if ("hotel".equals(vertical)) {
            HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance(),
                    By.className("landingFareFinder"));
            String[] location = hotelSearchFragment.getDestCity().getAttribute("value").split(",");
            assertThat(firstLocationFromAllCities.contains(location[0]))
                    .as("Pre-populated location contains: " + location[0]).isTrue();
            assertThat(firstLocationFromAllCities.contains(location[1]))
                    .as("Pre-populated location contains: " + location[1]).isTrue();

            hotelSearchFragment.getDestCity().clear();
        }

        assertThat(destinationUrlsText.get(0).split("\\?")[0].equals(getWebdriverInstance().getCurrentUrl()))
                .as("Current URL is correct: " + getWebdriverInstance().getCurrentUrl())
                .isTrue();

    }


    @Override
    public void verifyAboutOurCarsModule() {
        AboutCarsModule aboutCarsModule = new AboutCarsModule(getWebdriverInstance());
        assertThat(aboutCarsModule.isContainsAllExpectedCarTypes()).isTrue();
    }

    @Override
    public void chooseTheFirstLinkFromMostPopularDestination(String vertical) {
        InformationPageUS informationPageUS = new InformationPageUS(getWebdriverInstance());
        ArrayList<WebElement> destinationUrls = informationPageUS.getDestinationUrlsWebElements(vertical);
        destinationUrls.get(0).click();
    }

    @Override
    public void navigateThruFlightsDestinationPagination(String paginationOption) {
        new InformationPageUS(getWebdriverInstance()).clickPaginationOption(paginationOption);
    }

    @Override
    public void clickOnBacktoPreviousPageLink() {
        new InformationPageUS(getWebdriverInstance()).clickBackToPreviousPageLink();
    }

    @Override
    public void verifyNextResultSetOfStates(int statesNumber) {
        int uiNumberOfStates = new InformationPageUS(getWebdriverInstance())
            .getNumberOfStatesDisplayedInFlightsDestination();
        assertThat(uiNumberOfStates == statesNumber).as(
            "A set of " + statesNumber + " states should be displayed in view flights by destination section, " +
                "a set of " + uiNumberOfStates + " is being displayed instead.").isTrue();
    }
}

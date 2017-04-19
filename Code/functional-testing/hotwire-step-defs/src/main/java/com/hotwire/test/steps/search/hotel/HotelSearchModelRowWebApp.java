/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.row.HomePage;
import com.hotwire.selenium.desktop.row.search.FareFinder;
import com.hotwire.selenium.desktop.row.search.HomePageFareFinder;
import com.hotwire.selenium.desktop.row.search.OldFareFinder;
import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsSearchResultsFragment;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.PageName;

/**
 * RowWebApp implementation of HotelSearchModel.
 */
public class HotelSearchModelRowWebApp extends HotelSearchModelTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelSearchModelRowWebApp.class.getSimpleName());

    @Resource
    protected Properties applicationProperties;

    @Override
    public void verifyHotelResultsPageView(String viewType) {
        new HotelResultsPage(getWebdriverInstance());
    }

    @Override
    public void verifyAllInclusivePriceAtDetails() {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(detailsPage.isPriceAllInclusive());
    }

    @Override
    public void verifyValidationErrorOnField(String validationError, String field) {
        HotelIndexPage homePage = new HotelIndexPage(getWebdriverInstance());
        List<String> errorMessages = Arrays.asList(homePage.getErrorMessages().split("\n"));

        if ("location".equalsIgnoreCase(field)) {
            if ("unknown destination".equalsIgnoreCase(validationError)) {
                assertThat(StringUtils.join(errorMessages, " ")).contains(
                    "We couldn't find that destination. Check the spelling or enter a nearby city.");
            }
            else if ("empty destination".equalsIgnoreCase(validationError)) {
                assertThat(StringUtils.join(errorMessages, " ")).contains("Enter a destination");
            }
            else {
                super.verifyValidationErrorOnField(validationError, field);
            }
        }
        else if ("children age".equalsIgnoreCase(field)) {
            if ("children age".equalsIgnoreCase(validationError)) {
                assertThat(StringUtils.join(errorMessages, " ")).contains("Please provide the ages of children below.");
            }
            else {
                super.verifyValidationErrorOnField(validationError, field);
            }
        }
        else {
            super.verifyValidationErrorOnField(validationError, field);
        }
    }

    @Override
    public void launchSearch() {
        String pageName = new PageName().apply(getWebdriverInstance());

        assertThat(pageName).isNotNull();

        if (StringUtils.contains("tile.home", pageName)) {
            findFare("");
        }
        else {
            launchSearchWithOldFareFinder();
        }
    }

    private void launchSearchWithOldFareFinder() {
        // this method just start new search from landing page, but it will me reworked in nearest future
        HomePageFareFinder fareFinder = new HomePageFareFinder(getWebdriverInstance());
        fareFinder.submit();
    }

    @Override
    public void verifyResultsContainLocation(String location) {
        HotelResultsSearchResultsFragment results =
            new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment();

        ArrayList<String> hotelLocations = results.getNeighborhoodsFromOpaqueSearchResults();
        // Assert there are one or more results with the given location.
        boolean found = false;
        for (String neighborhood : hotelLocations) {
            if (neighborhood.contains(location)) {
                found = true;
                break;
            }
        }
        assertThat(found)
            .as("Expected " + location + " to be contained in at least 1 listed result.")
            .isTrue();
    }

    @Override
    public void verifyCustomerRating(String rating) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(rating.equalsIgnoreCase(detailsPage.getCustomerRating()));
    }

    @Override
    public void verifyPrice(String price) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(detailsPage.getPrice()).as("Price on details page").isEqualTo(price);
    }

    @Override
    public void verifyHotelSearchResultsPage() {
        new HotelResultsPage(getWebdriverInstance());
    }

    @Override
    public void verifySearchWinnerDeal(String dealType, SearchSolution expectedDeal) {
        HotelResultsPage results = new HotelResultsPage(getWebdriverInstance());
        assertThat(results.opaqueResultsAreDisplayed()).isEqualTo("opaque".equalsIgnoreCase(dealType));

        SearchSolution actualDeal = results.getSearchSolutionList().get(0);
        assertThat(actualDeal.getPrice().equalsIgnoreCase(expectedDeal.getPrice()));
        assertThat(actualDeal.getPriceLabel().equalsIgnoreCase(expectedDeal.getPriceLabel()));
    }

    @Override
    public void verifyOpaqueHotelsLabel(String label, String resultsType) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        String actHotelName = resultsPage.getSearchSolutionList().get(0).getHotelName();
        assertThat(actHotelName.contains(label)).isTrue();
    }

    @Override
    public void verifyCustomerCarePhoneNumber(String page, String phoneNumber) {
        if ("search form".equals(page)) {
            HomePage homePage = new HomePage(getWebdriverInstance());
            assertThat(homePage.getCustomerCarePhoneNumber()).as("customer care phone number").isEqualTo(phoneNumber);
        }
        else if ("map".equals(page)) {
            HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
            assertThat(resultsPage.getCustomerCarePhoneNumber()).as("customer care phone number")
                .isEqualTo(phoneNumber);
        }
        else if ("details".equals(page)) {
            HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
            assertThat(hotelDetailsPage.getCustomerCarePhoneNumber()).as("customer care phone number").isEqualTo(
                phoneNumber);
        }
        else {
            throw new UnimplementedTestException("Implement me!");
        }
    }

    @Override
    public void verifyDisclaimer() {
        String disclaimerText = "Hotels may charge additional mandatory taxes and fees, " +
            "such as city taxes and resort fees, that will be payable at the hotel.";
        new HotelResultsPage(getWebdriverInstance());
        getWebdriverInstance().findElement(By.xpath("//*[contains(text(), '" + disclaimerText + "')]"));
    }

    @Override
    public void verifySavingsLayer(boolean state) {
        new HotelResultsPage(getWebdriverInstance());
        boolean savingsLayerIsDisplayed;

        try {
            By by = By.cssSelector("div[id='dbmSubscriptionModule'] div");
            WebElement savingsLayer = getWebdriverInstance().findElement(by);
            savingsLayerIsDisplayed = savingsLayer.isDisplayed();
        }
        catch (NoSuchElementException e) {
            savingsLayerIsDisplayed = false;
        }

        assertThat(savingsLayerIsDisplayed).as("Savings layer persistence").isEqualTo(state);
    }

    @Override
    public void verifyFareFinderDestination(String topDestination) {
        FareFinder fareFinder = new OldFareFinder(getWebdriverInstance(), By.className("landingFareFinder"));
        if (topDestination.contains("(")) {
            String[] strings = topDestination.split("\\(");
            assertThat(fareFinder.getDestinationLocation().contains(strings[0].trim())).isTrue();
            assertThat(fareFinder.getDestinationLocation().contains(strings[1].trim())).isTrue();
        }
        else {
            assertThat(fareFinder.getDestinationLocation().contains(topDestination)).isTrue();
        }
    }

    @Override
    public void verifyFacilitiesTermUsage(String term) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        String text = detailsPage.getFacilitiesModuleText().trim();
        if (text.isEmpty()) {
            LOGGER.info("No facilities for this hotel");
            throw new ZeroResultsTestException("No facilities for this hotel");
        }
        assertThat(text.toLowerCase().contains(term)).isTrue();
        if ("facilities".equalsIgnoreCase(term)) {
            assertThat(text.contains("amenities")).isFalse();
        }
        else {
            assertThat(text.contains("facilities")).isFalse();
        }
    }

    @Override
    public void verifySemiOpaqueRoomPhotos() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        List<Boolean> opaquePhotos = new ArrayList<Boolean>();
        for (SearchSolution solution : resultsPage.getSearchSolutionList()) {
            if (solution.isRoomPhotosOpaque()) {
                opaquePhotos.add(true);
            }
        }
        assertThat(opaquePhotos.isEmpty()).isFalse();
    }

    @Override
    public void clickSearch() {
        HomePage hp = new HomePage(getWebdriverInstance());
        hp.getFareFinder().findFare();
    }

    @Override
    public void verifyHotelNeighborhood() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(hotelDetailsPage.isHotelNeighborhoodDisplayed())
            .as("Hotel neighborhood is displayed")
            .isTrue();
    }

    @Override
    public void displayMapPopup() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        hotelDetailsPage.putMouseOverPOI();
    }

    @Override
    public void verifyMapPopup() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(hotelDetailsPage.isMapPopupDisplayed())
            .as("POI description popup is displayed")
            .isTrue();
    }

    @Override
    public void verifyNoRoomPhotos() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(hotelDetailsPage.getNoRoomPhotosMessageText())
            .as("No room photos text")
            .contains("Why No Photos?")
            .contains("Secret Hot Rate hotels")
            .contains("In order to get prices this low, hotels request that we do not show hotel photo")
            .contains("we conceal the name of the hotel until immediately after you book")
            .contains("Call 0808 234 5903 with any questions");
    }

    @Override
    public void returnToSearchResults(String position) {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        hotelDetailsPage.returnToSearchResults(position);
    }

    @Override
    public void removeSessionCookieAndReloadPage() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getWebdriverInstance();
        javascriptExecutor.executeScript("document.cookie = 'JSESSIONID=; path=/'");
        getWebdriverInstance().navigate().to(getWebdriverInstance().getCurrentUrl());
    }

    @Override
    public void verifyBackToResultsLink() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(hotelDetailsPage.getBackToResultsLinks())
            .as("Back to results links")
            .isEmpty();
    }

    @Override
    public void verifyLowPriceGuaranteeOffer() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(hotelDetailsPage.getLowPriceGuaranteeOffer())
            .as("Back to results links")
            .contains("Low Price Guarantee")
            .contains("Within 48 hours of booking,")
            .contains("if you find a lower rate for an identical booking, ")
            .contains("we'll pay you the difference between the rates.");
    }

    @Override
    public void verifyKnowBeforeYouGoNotice() {
        HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(hotelDetailsPage.getKnowBeforeYouGoNotice())
            .as("Know Before You Go notice")
            .contains("Know before you go")
            .contains("Primary guest must be 21 years of age.")
            .contains("Your account will be charged at the time of booking for the full hotel room amount,")
            .contains("including all tax recovery charges and fees.")
            .contains("Bringing you rock-bottom prices on hotel rooms means bookings are final")
            .contains("(no refunds, no changes).");
    }

    @Override
    public void verifyTripAdvisorInResults(boolean isVisible) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        List<WebElement> resultsWithTripAdvisor =
            resultsPage.getSearchResultsFragment().getResultsWithTripAdvisorRatings();

        if (resultsWithTripAdvisor.size() == 0) {
            throw new ZeroResultsTestException("0 results containing a Trip Advisor module.");
        }
        for (WebElement resultRating : resultsWithTripAdvisor) {
            LOGGER.info("Verifiying Trip Advisor rating of " + resultRating.getAttribute("class") + " is displayed.");
            assertThat(resultRating.isDisplayed()).isTrue();
        }
    }
}

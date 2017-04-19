/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.deals;

import com.hotwire.selenium.desktop.row.HomePage;
import com.hotwire.selenium.desktop.row.search.AirportDealLandingPage;
import com.hotwire.selenium.desktop.row.search.GeoPage;
import com.hotwire.selenium.desktop.row.search.LandingPage;
import com.hotwire.selenium.desktop.row.search.LastMinutePage;
import com.hotwire.selenium.desktop.row.search.SecretHotRatesPage;
import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.testing.UnimplementedTestException;
import org.fest.assertions.Condition;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

/**
 * ROW implementation of deals steps.
 */
public class DealsModelRowWebApp extends DealsModelTemplate {

    @Override
    public void navigateToLastMinuteHotels(String dealType, String topDestination) {
        String url = new HomePage(getWebdriverInstance()).getTopDestinationUrl(topDestination);
        if ("Last minute".equalsIgnoreCase(dealType)) {
            getWebdriverInstance().navigate().to(url + "?kword=lastminute");
        }
        else if ("Airport Deal".equalsIgnoreCase(dealType)) {
            getWebdriverInstance().navigate().to(url + "?kword=airportdeals");
        }
        else {
            throw new UnimplementedTestException("Implement me!");
        }
    }

    @Override
    public void navigateToSecretHotRateHotels(String topDestination) {
        String url = new HomePage(getWebdriverInstance()).getTopDestinationUrl(topDestination);
        getWebdriverInstance().navigate().to(url + "?kword=secret");
    }

    @Override
    public void verifyMarketingDealHotels(String dealType) {
        if ("Airport Deal".equalsIgnoreCase(dealType)) {
            AirportDealLandingPage airportDealsPage = new AirportDealLandingPage(getWebdriverInstance());
            assertThat(airportDealsPage.isAirportDealsBannerDisplayed()).isTrue();
            assertThat(airportDealsPage.isWeekendDealsModulePresent()).isTrue();
            assertThat(airportDealsPage.isNearbyAirportDealsModulePresent()).isTrue();
        }
        else if ("Last minute".equalsIgnoreCase(dealType)) {
            new LastMinutePage(getWebdriverInstance()).verifyLastMinuteHotels();
        }
        else {
            throw new UnimplementedTestException("Implement me!");
        }
    }

    @Override
    public void verifySecretHotRateHotels() {
        new SecretHotRatesPage(getWebdriverInstance()).verifySecretHotRateHotels();
    }

    @Override
    public void verifyOpaqueTaglines(final String taglineType) {
        final String hotelName;
        HotelResultsPage hotelResultsPage = new HotelResultsPage(getWebdriverInstance());
        hotelName = "area";

        Set<String> hotelNames = new HashSet<String>();

        for (SearchSolution searchSolution : hotelResultsPage.getSearchSolutionList()) {
            hotelNames.add(searchSolution.getHotelName());
        }

        assertThat(hotelNames).satisfies(new Condition<Collection<?>>() {
            @Override
            public boolean matches(Collection<?> objects) {
                boolean hasCustomTagLines = false;

                for (Object o : objects) {
                    if (!o.toString().contains(hotelName)) {
                        hasCustomTagLines = true;
                    }
                }

                return !(hasCustomTagLines ^ taglineType.equalsIgnoreCase("custom"));
            }
        });

    }

    @Override
    public void navigateToCrossSellDeal() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        SearchSolution solution = resultsPage.clickCrossSellLink(0);
        hotelSearchParameters.setSelectedSearchSolution(solution);
    }

    @Override
    public void selectTopDestinationCity() {
        HomePage homePage = new HomePage(getWebdriverInstance());
        WebElement topDestination = homePage.getTopDestinations().get(0);
        hotelSearchParameters.setDestination(topDestination.getText());
        topDestination.click();
    }

    @Override
    public void selectSuggestedDestination(String destinationType) {
        GeoPage geoPage = new GeoPage(getWebdriverInstance());
        WebElement destination = geoPage.getDestinations(destinationType).get(0);
        hotelSearchParameters.setDestination(destination.getText());
        destination.click();
    }

    @Override
    public void accessDestinationPopularHotel() {
        GeoPage geoPage = new GeoPage(getWebdriverInstance());
        SearchSolution solution = geoPage.getPopularHotelsList().get(0);
        hotelSearchParameters.setSelectedSearchSolution(solution);
        geoPage.selectPopularHotel(solution.getNumber());
    }

    @Override
    public void verifyHotelLAndingPage(SearchSolution solution) {
        LandingPage landingPage = new LandingPage(getWebdriverInstance());
        hotelSearchParameters.getSelectedSearchSolution();
        assertThat(landingPage.getPrice().equals(solution.getPrice()));
        assertThat(landingPage.getHotelName().equalsIgnoreCase(solution.getHotelName()));
    }
}

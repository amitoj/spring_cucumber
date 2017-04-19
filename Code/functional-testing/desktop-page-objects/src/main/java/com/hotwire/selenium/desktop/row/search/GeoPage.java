/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.testing.UnimplementedTestException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class GeoPage extends AbstractRowPage {

    @FindBy(css = "div.popularHotels a")
    private List<WebElement> popularHotels;

    public GeoPage(WebDriver webdriver) {
        super(webdriver, "tile.hotel.geo.low.level");
    }

    public List<WebElement> getDestinations(String destinationType) {
        List<WebElement> topDestinations;
        if ("airport".equalsIgnoreCase(destinationType)) {
            topDestinations = getWebDriver().findElements(By.cssSelector("#lowMinAirportList a"));
        }
        else if ("landmark".equalsIgnoreCase(destinationType)) {
            topDestinations = getWebDriver().findElements(By.cssSelector("#lowMinPoiList a"));
        }
        else {
            throw new UnimplementedTestException("Unknown destination type" + destinationType);
        }

        return topDestinations;
    }

    public List<SearchSolution> getPopularHotelsList() {
        List<SearchSolution> popularHotelSolutions = new ArrayList<>();
        int number = 0;

        for (WebElement result : getWebDriver().findElements(By.cssSelector("div.popularHotels li"))) {
            SearchSolution searchSolution = new SearchSolution();
            searchSolution.setNumber(number++);

            String hotelName = result.findElement(By.cssSelector("a")).getText();
            searchSolution.setHotelName(hotelName);

            String hotelPrice = result.findElement(By.cssSelector("div.price")).getText();
            searchSolution.setPrice(hotelPrice);

            popularHotelSolutions.add(searchSolution);
        }
        return popularHotelSolutions;
    }

    public void selectPopularHotel(Integer number) {
        popularHotels.get(number).click();
    }

}

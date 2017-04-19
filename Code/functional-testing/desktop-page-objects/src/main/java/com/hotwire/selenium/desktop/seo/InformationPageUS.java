/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.seo;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vjong
 * Date: Jun 29, 2012
 * Time: 3:23:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class InformationPageUS extends AbstractUSPage {

    private static final String CAR_LEFT_COLUMN_DESTINATION_LINKS =
            "//div[contains(@class, 'discountLinks')]//div[@class='body']/ul[1]";
    private static final String CAR_RIGHT_COLUMN_DESTINATION_LINKS =
            "//div[contains(@class, 'discountLinks')]//div[@class='body']/ul[2]";
    private static final String HOTEL_LEFT_COLUMN_DESTINATION_LINKS = "dlleft";
    private static final String HOTEL_RIGHT_COLUMN_DESTINATION_LINKS = "dlright";
    private static final String FLIGHT_LEFT_COLUMN_DESTINATION_LINKS =
            "//*[text()[contains(.,'city destination')]" +
                    " or text()[contains(.,'All cities')]" +
                    " or text()[contains(.,'most popular destination')] ]//..//div[@class='body']//UL[1]";
    private static final String FLIGHT_RIGHT_COLUMN_DESTINATION_LINKS =
          "//*[text()[contains(.,'city destination')]" +
                  " or text()[contains(.,'All cities')]" +
                  " or text()[contains(.,'most popular destination')] ]//..//div[@class='body']//UL[2]";
    private static final String SEE_ALL_DESTINATIONS_LINK = "//a[contains(@href, 'allCountries')]";

    private static final String VIEW_FLIGHTS_BY_DESTINATION = "//h3[@class='header' and contains(text()," +
        " 'View flights by destination')]/following-sibling::div[@class='body']";

    @FindBy(xpath = SEE_ALL_DESTINATIONS_LINK)
    private WebElement seeAllLink;

    @FindBy(xpath = CAR_LEFT_COLUMN_DESTINATION_LINKS)
    private WebElement carLeftColumn;

    @FindBy(xpath = CAR_RIGHT_COLUMN_DESTINATION_LINKS)
    private WebElement carRightColumn;

    @FindBy(className = HOTEL_LEFT_COLUMN_DESTINATION_LINKS)
    private WebElement hotelLeftColumn;

    @FindBy(className = HOTEL_RIGHT_COLUMN_DESTINATION_LINKS)
    private WebElement hotelRightColumn;

    @FindBy(xpath = FLIGHT_LEFT_COLUMN_DESTINATION_LINKS)
    private WebElement flightLeftColumn;

    @FindBy(xpath = FLIGHT_RIGHT_COLUMN_DESTINATION_LINKS)
    private WebElement flightRightColumn;

    @FindBy(xpath = VIEW_FLIGHTS_BY_DESTINATION)
    private WebElement viewFlightsByDestinationList;

    @FindBy(xpath = "//h2[@class='subheadline' and contains( text(), 'Hotwire Hotel Deals')]")
    private WebElement hotelDealsHeader;

    @FindBy(xpath = "//h2[@class='subheadline' and contains( text(), 'Hotwire Rental Car Deals')]")
    private WebElement carDealsHeader;

    @FindBy(xpath = "//h2[@class='subheadline' and contains( text(), 'Hotwire Airfare Deals')]")
    private WebElement flightDealsHeader;

    @FindBy(css = "input[id*=flightRadio]")
    private WebElement airRadio;

    @FindBy(css = "input[id*=carRadio]")
    private WebElement carRadio;

    @FindBy(css = "input[id*=hotelRadio]")
    private WebElement hotelRadio;

    @FindBy(css = ".lnkLTArwGry")
    private WebElement backToPreviousPage;


    public InformationPageUS(WebDriver webDriver) {
        super(webDriver, new String[] {"tiles-def.seo-site-map.product-overview" + "",
            "tiles-def.seo-site-map.destination-group" + "", "tiles-def.seo-site-map.origin-destination"});
    }

    public String getSeeAllLinkLocator() {
        return SEE_ALL_DESTINATIONS_LINK;
    }

    public void clickSeeAllLink() {
        seeAllLink.click();
    }

    public void clickPaginationOption(String option) {
        getWebDriver().findElement(
            By.xpath("//*[@id='pagination']/descendant::div[@class='fLft' and contains(text(),'" + option + "')]"))
            .click();
    }

    public void clickBackToPreviousPageLink() {
        backToPreviousPage.click();
    }

    public ArrayList<String> getDestinationUrls(String vertical) {
        ArrayList<String> hrefs = new ArrayList<String>(getColumnUrls(true, vertical));
        hrefs.addAll(getColumnUrls(false, vertical));
        return hrefs;
    }

    public ArrayList<WebElement> getDestinationUrlsWebElements(String vertical) {
        ArrayList<WebElement> hrefs = new ArrayList<WebElement>(getColumnUrlsWebElements(true, vertical));
        hrefs.addAll(getColumnUrlsWebElements(false, vertical));
        return hrefs;
    }

    private ArrayList<WebElement> getColumnUrlsWebElements(boolean getLeftColumn, String vertical) {
        WebElement  leftColumn = null;
        WebElement  rightColumn = null;

        if ("hotel".equalsIgnoreCase(vertical)) {
            leftColumn = hotelLeftColumn;
            rightColumn = hotelRightColumn;
        }
        else if ("car".equalsIgnoreCase(vertical)) {
            leftColumn = carLeftColumn;
            rightColumn = carRightColumn;
        }
        else if ("flight".equalsIgnoreCase(vertical)) {
            leftColumn = flightLeftColumn;
            rightColumn = flightRightColumn;

        }

        ArrayList<WebElement> hrefs = new ArrayList<WebElement>();
        List<WebElement> aTags = null;
        try {
            if (getLeftColumn) {
                aTags = leftColumn.findElements(By.tagName("a"));

            }
            else {
                aTags = rightColumn.findElements(By.tagName("a"));
            }
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        if (null != aTags) {
            hrefs.addAll(aTags);
        }

        return hrefs;
    }

    private ArrayList<String> getColumnUrls(boolean getLeftColumn, String vertical) {
        ArrayList<WebElement>  urls = getColumnUrlsWebElements(getLeftColumn, vertical);
        ArrayList<String> urlText = new ArrayList<String>();

        for (WebElement e: urls) {
            urlText.add(e.getAttribute("href"));
        }

        return urlText;
    }

    public boolean isHotelRadiobuttonChecked() {
        return hotelRadio.isSelected();
    }

    public boolean isCarRadiobuttonChecked() {
        return carRadio.isSelected();
    }

    public boolean isAirRadiobuttonChecked() {
        return airRadio.isSelected();
    }

    public WebElement getHotelDealsHeader() {
        return hotelDealsHeader;
    }

    public WebElement getCarDealsHeader() {
        return carDealsHeader;
    }

    public WebElement getFlightDealsHeader() {
        return flightDealsHeader;
    }

    public int getNumberOfStatesDisplayedInFlightsDestination() {
        return viewFlightsByDestinationList.findElements(By.tagName("li")).size();
    }
}

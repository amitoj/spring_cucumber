/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.details;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a us hotel results page.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public class HotelDetailsPage extends AbstractUSPage {
    private static final String AMENITIES_LIST = "//span[contains(@class, 'amenityIcon-')]";
    private static final String FREE_AMENITIES_LIST = "//span[contains(@class, 'freebie')]";
    private static final String HOTEL_RESULTS_DETAILS_CONTAINER = "//div[contains(@class, 'hotelResult')]";
    private static final String HOTEL_CROSS_SELL = ".hotelCrossSell a";
    private static final String BOOK_NOW_BUTTON = " .largeButton";

    @FindBy(id = "tileName-B3")
    private WebElement hotelDetail;

    @FindBy(xpath = AMENITIES_LIST)
    private List<WebElement> amenities;

    @FindBy(xpath = FREE_AMENITIES_LIST)
    private List<WebElement> freeAmenities;

    @FindBy(xpath = HOTEL_RESULTS_DETAILS_CONTAINER)
    private WebElement hotelResultsDetails;

    @FindBy(css = HOTEL_CROSS_SELL)
    private WebElement hotelCrossSell;

    @FindBy(css = BOOK_NOW_BUTTON)
    private WebElement bookNowButton;

    public HotelDetailsPage(WebDriver webdriver) {
        super(webdriver, new String[]{"tiles-def.hotel.details.*", "tiles-def.hotel.details-new-car-add-on-top",
                                      "tile.hotel.details.*"});
    }

    public boolean isOpaqueHotelDetailsPageDisplayed() {
        return hotelResultsDetails.getAttribute("class").contains("opaqueResult") && hotelResultsDetails.isDisplayed();
    }

    public List<String> getAmenitiesList() {
        ArrayList<String> list = new ArrayList<String>();
        for (WebElement amenity : amenities) {
            list.add(amenity.getText().replaceAll(",", "").trim());
        }
        return list;
    }

    public List<String> getFreeAmenitiesList() {
        ArrayList<String> list = new ArrayList<String>();
        for (WebElement amenity : freeAmenities) {
            list.add(amenity.getText().replaceAll(",", "").trim());
        }
        return list;
    }

    public boolean isHotelCrossSellDisplayed() {
        try {
            return hotelCrossSell.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickHotelCrossSell() {
        hotelCrossSell.click();
    }

    public void select() {
        bookNowButton.click();
    }
}

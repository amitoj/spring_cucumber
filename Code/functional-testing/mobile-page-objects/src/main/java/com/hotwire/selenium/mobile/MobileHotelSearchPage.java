/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile;

import com.hotwire.selenium.mobile.widget.DatePicker;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: v-vyulun Date: 4/4/12 Time: 8:38 PM To change this template use File | Settings |
 * File Templates.
 */
public class MobileHotelSearchPage extends MobileAbstractPage {

    @FindBy(xpath = "//*[@data-bdd='location']")
    private WebElement destCity;

    @FindBy(name = "startDate")
    private WebElement startDateField;

    @FindBy(name = "endDate")
    private WebElement endDateField;

    @FindBy(name = "numRooms")
    private WebElement hotelRooms;

    @FindBy(name = "numAdults")
    private WebElement hotelRoomAdults;

    @FindBy(name = "numChildren")
    private WebElement hotelRoomChild;

    @FindBy(xpath = "//button[contains(@class, 'btn')]")
    private WebElement findHotels;

    @FindBy(xpath = "//*[@data-bdd='hotel-discount-banner']")
    private WebElement discountBanner;

    public MobileHotelSearchPage(WebDriver driver) {
        super(driver, "tile.hotel.landing");
    }

    @SuppressWarnings("unused")
    private void findFare(String destinationLocation, Date startDate, Date endDate, Integer numberOfHotelRooms,
                          Integer numberOfAdults, Integer numberOfChildren) {
        // If destination is "my location", then click on the geo location button
        this.destCity.click();
        this.destCity.sendKeys(destinationLocation);

        DatePicker startDatePicker = new DatePicker(getWebDriver(), startDateField);
        startDatePicker.selectDate(startDate);

        DatePicker endDatePicker = new DatePicker(getWebDriver(), endDateField);
        endDatePicker.selectDate(endDate);

        new WebDriverWait(getWebDriver(), 10).until(PageObjectUtils.webElementVisibleTestFunction(hotelRooms, true));

        // TODO: These default values should be placed in the model in the
        // spring context.... not hard coded in a file --Rex
        this.hotelRooms.sendKeys(numberOfHotelRooms == null ? "1" : String.valueOf(numberOfHotelRooms));
        this.hotelRoomAdults.sendKeys(numberOfAdults == null ? "2" : String.valueOf(numberOfAdults));
        this.hotelRoomChild.sendKeys(numberOfChildren == null ? "0" : String.valueOf(numberOfChildren));
        this.destCity.submit();
    }


    public void hasValidationErrorOnField(String validationError, String field) {
        if ("city".equalsIgnoreCase(field)) {
            if ("empty destination".equalsIgnoreCase(validationError)) {
                assertHasFormErrors("Minimum 3 characters required to perform successful search.");
            }
            else if ("invalid character".equalsIgnoreCase(validationError)) {
                assertHasFormErrors("Invalid entry. Use only letters and numbers, no special characters.");
            }
            else if ("invalid destination".equalsIgnoreCase(validationError)) {
                assertHasFormErrors(
                    "We're sorry. We didn't find any results for your search. Please modify your search criteria and " +
                    "try again.");
            }
            else if ("minimum length".equalsIgnoreCase(validationError)) {
                assertHasFormErrors("Minimum 3 characters required to perform successful search.");
            }

        }
        else if ("date".equalsIgnoreCase(field)) {
            if ("invalid check-out".equalsIgnoreCase(validationError)) {
                assertHasFormErrors("Check-out date is not valid. The check-out date must be after the check-in date.");
            }
            else if ("one night reservation".equalsIgnoreCase(validationError)) {
                assertHasFormErrors(
                    "Check-in date or check-out date is not valid. Reservations must include at least one night's " +
                    "stay.");
            }

        }
    }

    public void accessExternalHotelResultsLinkWithDiscountCode(String discountCode) {
        String[] tokens = getWebDriver().getCurrentUrl().split("\\/mobile");
        String rootUrl = tokens[0];
        String url = rootUrl +
                     "/mobile/hotel/search-options.jsp?inputId=hotel-index&destCity=San Francisco," +
                     "CA&startDay=10&startMonth=8&endDay=30&endMonth=8&xid=x-103&rid=r-149316101445&wid=w-3&rs=20501" +
                     "&referringDealId=149316101445&dccid=" + discountCode;
        getWebDriver().get(url);
    }

    public boolean verifyDiscountBannerOnOff() {
        return discountBanner.isDisplayed();

    }

    public boolean verifyHotelSearchDetails(String destinationCity) {
        return destinationCity.equalsIgnoreCase(destCity.getText());
    }
}

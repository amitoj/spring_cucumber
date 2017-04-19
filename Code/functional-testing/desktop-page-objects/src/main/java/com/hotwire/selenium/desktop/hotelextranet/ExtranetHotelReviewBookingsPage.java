/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import java.util.Date;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.util.webdriver.ui.ExtendedSelect;

/**
 * This page is about review booking details page.
 * @author adeshmukh
 *
 */

public class ExtranetHotelReviewBookingsPage extends AbstractUSPage {

    @FindBy(css = "form[name=xnetHotelBookingHistorySearchForm] button")
    private WebElement goButton;

    @FindBy(name = "startDate")
    private WebElement startDateField;

    @FindBy(name = "endDate")
    private WebElement endDateField;

    @FindBy(name = "searchType")
    private WebElement searchTypeField;

    @FindBy(linkText = "Review bookings")
    private WebElement reviewBookingsLink;

    @FindBy(name = "searchType")
    private WebElement searchBy;
    /**
     * <div class="errorMessages"> <h6>Error</h6>
     * <p>
     * Please correct the following field(s):
     * </p>
     * <ul>
     * <li><b class="bold">Hotwire ID</b> field is blank or invalid.</li>
     * </ul>
     * </div>
     */
    @FindBy(css = "div.errorMessages")
    private WebElement authenticationError;

    public ExtranetHotelReviewBookingsPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.xnet.review-bookings");
    }

    /**
     * Select the start and end date using the datepicker
     *
     * @param startDate
     * @param endDate
     *
     */

    public void selectDates(Date startDate, Date endDate, String searchType) {
        try {
            new DatePicker(getWebDriver(), startDateField).selectDate(startDate);
        }
        catch (NoSuchElementException e) {
            startDateField.clear();
            startDateField.sendKeys(DatePicker.getFormattedDate(startDate) + Keys.TAB);
        }
        try {
            new DatePicker(getWebDriver(), endDateField).selectDate(endDate);
        }
        catch (NoSuchElementException e) {
            endDateField.clear();
            endDateField.sendKeys(DatePicker.getFormattedDate(endDate) + Keys.TAB);
        }
        ExtendedSelect select = new ExtendedSelect(searchBy);
        select.selectIfVisibleTextContainsText(searchType);
    }

    /**
     * Select the specific search by option Arrival/Departure date
     *
     * @param searchBy
     *
     */
    public void selectSearchType(String searchBy) {
        searchTypeField.sendKeys(searchBy);
    }

    /**
     * Click Go button in hotel overview pages
     */
    public void clickGoButton() {
        goButton.click();
    }

    /**
     * Click Review Bookings link
     */
    public void clickReviewBookingLink() {
        reviewBookingsLink.click();
    }

}

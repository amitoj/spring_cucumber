/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.ExtendedSelect;

/**
 * This page is a page object of Hotel Overview Page
 *
 * @author sramakrishnan
 *
 */

public class ExtranetHotelOverviewPage extends AbstractPageObject {

    @FindBy(xpath = "//select[@name='selectedRoomTypes']/option")
    List<WebElement> roomTypes;

    @FindBy(id = "tileName-intro")
    private WebElement hotelNameId;

    @FindBy(name = "tonightOnlyDiscountStartHour")
    private WebElement startHourField;

    @FindBy(name = "tonightOnlyDiscountPercentage")
    private WebElement hotelDiscountField;

    @FindBy(css = "form[name=selectInventoryForm] button[type=submit]")
    private WebElement goButton;

    @FindBy(css = "form[name=tonightOnlyDiscountPercentageForm] button")
    private WebElement applyButton;

    @FindBy(name = "startDateStr")
    private WebElement startDateField;

    @FindBy(name = "endDateStr")
    private WebElement endDateField;

    @FindBy(name = "selectedRoomTypes")
    private WebElement roomTypeField;

    @FindBy(linkText = "Review bookings")
    private WebElement reviewBookingsLink;

    @FindBy(linkText = "Room types")
    private WebElement roomTypeLink;

    @FindBy(linkText = "Review changes")
    private WebElement reviewChangeLink;

    @FindBy(css = "div[class='errorMessages confirmationMessage'] p b")
    private WebElement successMessage;

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

    @FindBy(linkText = "Hotel details")
    private WebElement hotelDetails;

    @FindBy(linkText = "Users and permissions")
    private WebElement userPermissionLink;

    public ExtranetHotelOverviewPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.xnet.hotel-overview"});
    }

    /**
     * Select the start and end date using the datepicker
     *
     * @param startDate
     * @param endDate
     *
     */
    public void selectDates(Date startDate, Date endDate) {
        sendKeys(startDateField, DatePicker.getFormattedDate(startDate) + Keys.TAB);
        sendKeys(endDateField, DatePicker.getFormattedDate(endDate) + Keys.TAB);
    }

    /**
     * Select the specific room type or select all room type
     *
     * @param roomType
     *
     */
    public void selectRoomType(String roomType) {
        Select roomTypeSelect = new Select(roomTypeField);
        roomTypeSelect.deselectAll();
        roomTypeSelect.selectByVisibleText(roomType);
    }

    /**
     * Click Go button in hotel overview pages
     */
    public void clickGoButton() {
        goButton.submit();
    }

    /**
     * Click Review Bookings link
     */
    public void clickReviewBookingLink() {
        reviewBookingsLink.click();
    }

    /**
     * sets night fall discount for a hotel
     *
     * @param discount
     */
    public void setNighfallDiscount(String discount) {
        ExtendedSelect discountDropdown = new ExtendedSelect(hotelDiscountField);
        discountDropdown.selectIfVisibleTextContainsText(discount);
    }

    /**
     * sets night fall start hour for a hotel
     *
     * @param startHour
     */
    public void setNighfallStartHour(String startHour) {
        ExtendedSelect startHourDropdown = new ExtendedSelect(startHourField);
        startHourDropdown.selectIfVisibleTextContainsText(startHour);
    }

    /**
     * apply night fall changes
     */
    public void applyNightfallChanges() {
        applyButton.click();
    }

    /**
     * validate the confirmation message after applying the discount percent and start time.
     *
     * @param discountPercent
     * @param startHour
     * @return
     */
    public boolean validateConfirmation(String discountPercent, String startHour) {
        String confirmationMessage = successMessage.getText();
        if (confirmationMessage.contains(discountPercent) && confirmationMessage.contains(startHour)) {
            return true;
        }
        return false;
    }

    /**
     * click on hotel details link on the 'hotel overview' page
     */
    public void clickHotelDetails() {
        hotelDetails.click();
    }

    /**
     * click on roomType link on hotel overview page
     */
    public void clickRoomType() {
        roomTypeLink.click();
    }

    /**
     * Validate room types displayed on hotelOverview Page.
     *
     * @param roomType
     * @return
     */
    public boolean validateRoomType(String roomType) {
        boolean validate = false;
        for (WebElement roomName : roomTypes) {
            if (roomName.getText().contains(roomType)) {
                validate = true;
                break;
            }
        }
        return validate;
    }

    public void accessUserPermissionPage() {
        userPermissionLink.click();
    }

    public void accessReviewChangePage() {
        reviewChangeLink.click();
    }

}

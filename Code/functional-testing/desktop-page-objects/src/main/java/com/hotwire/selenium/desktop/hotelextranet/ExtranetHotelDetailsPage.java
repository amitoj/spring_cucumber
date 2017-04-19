/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.ExtendedSelect;

/**
 * Desktop page object for Extranet's hotel details page.
 *
 * @author Amol Deshmukh
 */
public class ExtranetHotelDetailsPage extends AbstractUSPage {

    @FindBy(name = "selectedLateBookingEndTime")
    private WebElement lateBookingEndTimeDropdown;

    @FindBy(css = "form[name='editHotelInformationPropertyForm'] button[type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[contains(@class,'errorMessages')]/p")
    private WebElement confirmation;

    @FindBy(xpath = "//select[@name='selectedLateBookingEndTime']/option[@selected='selected']")
    private WebElement selectedLateBookingTime;

    public ExtranetHotelDetailsPage(WebDriver webDriver) {
        super(webDriver, "tiles-def.xnet.edit-property-details");
        new WebDriverWait(webDriver, MAX_SEARCH_PAGE_WAIT_SECONDS).until(PageObjectUtils.webElementVisibleTestFunction(
            lateBookingEndTimeDropdown, true));
    }

    /**
     * selects late booking time from the dropdown.
     *
     * @param lateBookingEndTime
     */
    public void applyLateBookingEndTime(String lateBookingEndTime) {
        ExtendedSelect timeDropdown = new ExtendedSelect(lateBookingEndTimeDropdown);
        timeDropdown.selectIfVisibleTextContainsText(lateBookingEndTime);
        saveButton.click();
    }

    /**
     * validates the saved confirmation message and lateBookingTime set.
     *
     * @param lateBookingTime
     * @return
     */

    public boolean validateLateBookingConfirmaton(String lateBookingTime) {
        String confirmationMessage = null;
        String bookingTime = null;
        boolean savedConfirmation = true, dropdownValue = true;
        confirmationMessage = confirmation.getText();
        logger.info("validating confirmation message");
        if (!confirmationMessage.contains("Your changes have been saved")) {
            savedConfirmation = false;
        }
        bookingTime = selectedLateBookingTime.getText();
        if (!bookingTime.contains(lateBookingTime)) {
            dropdownValue = false;
        }
        return savedConfirmation && dropdownValue;
    }
}

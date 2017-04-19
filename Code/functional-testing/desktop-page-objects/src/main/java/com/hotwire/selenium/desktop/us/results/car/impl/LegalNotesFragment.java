/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car.impl;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-achotemskoy
 * Date: 6/13/14
 * Time: 12:47 AM
 */
public class LegalNotesFragment extends AbstractDesktopPage {

    private static final By CONTAINER = By.cssSelector("#tileName-rates");

    private static final String FOOTNOTES =
            "** The makes/models shown are examples only. We are unable to guarantee a specific make/model. " +
                    "Actual makes/models are subject to availability and vary by supplier.";

    private static final String FOOTNOTES_SAVINGS =
            "Savings based on average published rate we've found " +
                    "on leading retail travel sites in the last 24-48 hours " +
                    "for the same booking date, location and car class.";

    private static final String OPAQUE_RATES_DISCLAIMER_TEXT =
            "*Rates are shown in US dollars. " +
                    "Total cost for Hotwire Rates includes applicable tax recovery charges and fees.";

    private static final String RETAIL_RATES_DISCLAIMER_TEXT =
            "* Rates are shown in US dollars. " +
                    "Total cost for retail rate reservations is an estimate based on the agency’s published rates, " +
                    "taxes and fees, and are subject to change. " +
                    "There is no guarantee that this price will be in effect at the time of your booking.";

    private static final String MIXED_RATES_DISCLAIMER_TEXT =
            "* Rates are shown in US dollars. " +
                    "Total cost for Hotwire Rates includes applicable tax recovery charges and fees. " +
                    "Total cost for retail rate reservations is an estimate based on the agency’s published rates, " +
                    "taxes and fees, and are subject to change. " +
                    "There is no guarantee that this price will be in effect at the time of your booking.";

    @FindBy(xpath = "div")
    private WebElement footNotes;

    LegalNotesFragment(WebDriver webDriver) {
        super(webDriver, CONTAINER);
    }

    public boolean isCopiesBlockIsDisplayed() {
        return footNotes.isDisplayed();
    }

    public boolean isFootSavingsNotesDisplayed() {
        return footNotes.getText().contains(FOOTNOTES_SAVINGS);
    }

    public boolean isFootNotesDisplayed() {
        return footNotes.getText().contains(FOOTNOTES);
    }

    public boolean isRetailRatesDisclaimerDisplayed() {
        return footNotes.getText().contains(RETAIL_RATES_DISCLAIMER_TEXT);
    }

    public boolean isOpaqueRatesDisclaimerDisplayed() {
        return footNotes.getText().contains(OPAQUE_RATES_DISCLAIMER_TEXT);
    }

    public boolean isMixedRatesDisclaimerDisplayed() {
        return footNotes.getText().contains(MIXED_RATES_DISCLAIMER_TEXT);
    }

}

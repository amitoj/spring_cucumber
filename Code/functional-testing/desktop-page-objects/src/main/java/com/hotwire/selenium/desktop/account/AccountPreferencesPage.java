/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.PageObjectUtils;

/**
 *
 */
public class AccountPreferencesPage extends AbstractAccountPage {

    private static final String SAVE_BUTTON = "//button[contains(@class,'btn') and @type='submit']";
    private static final long DEFAULT_WAIT = 20;
    private static final long SLEEP_INTERVAL_MILLIS = 35;

    @FindBy(id = "favAirportNames0")
    private WebElement favAirportInput;

    @FindBy(xpath = SAVE_BUTTON)
    private WebElement saveChangesButton;

    @FindBy(css = " .msgBox.successMessages")
    private WebElement successBox;

    @FindBy(xpath = "//a[contains(@onclick,'remove')]")
    private WebElement removeAirportLink;

    @FindBy(className = "yui-ac-content")
    private WebElement autoCompleteContent;

    public AccountPreferencesPage(WebDriver driver) {
        super(driver, "tile.account.preferences");
    }

    public void addFavoriteAirport(String airportName) {
        // DO: Factor out clicking autocomplete in HotelSearchFragment to a generic place and
        // change the sendkeys below to use that method call when the autocomplete list for my
        // account is changed back to match in Prod/QA as QACI items do not save.
        favAirportInput.sendKeys(airportName + Keys.ESCAPE + Keys.TAB); // Do not choose from list.
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT, SLEEP_INTERVAL_MILLIS)
                .until(PageObjectUtils.webElementVisibleTestFunction(autoCompleteContent, false));
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(new IsElementLocationStable(getWebDriver(), By.xpath(SAVE_BUTTON)));
        saveChangesButton.click();
    }

    public boolean isSuccessBoxVisible() {
        try {
            return successBox.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getFavAirportInputText() {
        return favAirportInput.getText();
    }

    public void removeFavoriteAirport() {
        removeAirportLink.click();
    }

}

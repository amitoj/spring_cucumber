/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Supplier enrollment property information form page object.
 */
public class SupplierEnrollmentPropertyTypeForm extends SupplierEnrollmentBaseForm {
    private static final String STANDARD_HOTEL_RADIO_BUTTON_KEY = "Standard Hotel Rooms";
    private static final String ALL_INCLUSIVE_RADIO_BUTTON_KEY = "All-Inclusive Packages";
    private static final String CONDO_APT_RADIO_BUTTTON_KEY = "Condo / Apartment Hotel";
    private static final String STANDARD_HOTEL_RADIO_BUTTON_ID = "tfa_mapid09653055380";
    private static final String ALL_INCLUSIVE_RADIO_BUTTON_ID = "tfa_mapid09653072878";
    private static final String CONDO_APT_RADIO_BUTTTON_ID = "tfa_mapid09653093483";
    private static final String CRS_PROVIDING_TYPE_RADIO_BUTTON_KEY = "Central Reservation System";
    private static final String EXTRANET_PROVIDING_TYPE_RADIO_BUTTON_KEY = "Hotwire Extranet";
    private static final String CRS_PROVIDING_TYPE_RADIO_BUTTON_ID = "tfa_329";
    private static final String EXTRANET_PROVIDING_TYPE_RADIO_BUTTON_ID = "tfa_332";

    @FindBy(id = "tfa_Numberofroomsc")
    private WebElement numberOfRooms;

    @FindBy(id = "wfPageNextId3")
    private WebElement nextButton;

    // Mapping for visible text keys and ids for property type radio buttons.
    private HashMap<String, String> propertyTypeRadioButtonMap;
    // Mapping for visible text keys and ids for rate providers radio buttons.
    private HashMap<String, String> providingTypeRadioButtonMap;

    public SupplierEnrollmentPropertyTypeForm(WebDriver webdriver) {
        super(webdriver);
        initRadioButtonMaps();
    }

    /**
     * Set up maps for visible text keys and ids for radio buttons.
     */
    private void initRadioButtonMaps() {
        propertyTypeRadioButtonMap = new HashMap<String, String>();
        propertyTypeRadioButtonMap.put(STANDARD_HOTEL_RADIO_BUTTON_KEY, STANDARD_HOTEL_RADIO_BUTTON_ID);
        propertyTypeRadioButtonMap.put(ALL_INCLUSIVE_RADIO_BUTTON_KEY, ALL_INCLUSIVE_RADIO_BUTTON_ID);
        propertyTypeRadioButtonMap.put(CONDO_APT_RADIO_BUTTTON_KEY, CONDO_APT_RADIO_BUTTTON_ID);

        providingTypeRadioButtonMap = new HashMap<String, String>();
        providingTypeRadioButtonMap.put(CRS_PROVIDING_TYPE_RADIO_BUTTON_KEY, CRS_PROVIDING_TYPE_RADIO_BUTTON_ID);
        providingTypeRadioButtonMap.put(
                EXTRANET_PROVIDING_TYPE_RADIO_BUTTON_KEY, EXTRANET_PROVIDING_TYPE_RADIO_BUTTON_ID);
    }

    public void clickPropertyTypeRadioButton(String propertyType) {
        WebElement radio = webdriver.findElement(By.id(propertyTypeRadioButtonMap.get(propertyType)));
        radio.click();
    }

    public void typeNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms.sendKeys(numberOfRooms);
    }

    public void clickProvidingRatesToType(String providingRatesToType) {
        WebElement radio = webdriver.findElement(By.id(providingTypeRadioButtonMap.get(providingRatesToType)));
        radio.click();
    }

    @Override
    public void fillRequiredInputAndSubmit(List<String> inputs) {
        clickPropertyTypeRadioButton(inputs.get(0));
        typeNumberOfRooms(inputs.get(1));
        clickProvidingRatesToType(inputs.get(2));
        clickNextButton();
    }

    @Override
    public void clickNextButton() {
        nextButton.click();
    }
}

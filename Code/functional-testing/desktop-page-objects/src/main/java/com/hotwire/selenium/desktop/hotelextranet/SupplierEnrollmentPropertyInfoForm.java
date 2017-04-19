/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Property information form page object.
 */
public class SupplierEnrollmentPropertyInfoForm extends SupplierEnrollmentBaseForm {
    private static final String PROPERTY_NAME_ERROR_ID = "tfa_Company-D-E";
    private static final String STREET_ADDRESS_ERROR_ID = "tfa_Street-D-E";
    private static final String CITY_ERROR_ID = "tfa_City-D-E";
    private static final String COUNTRY_ERROR_ID = "tfa_Country-D-E";
    private static final String PROPERTY_PHONE_ERROR_ID = "tfa_Phone-D-E";
    private static final String WEBSITE_ERROR_ID = "tfa_Website-D-E";
    private static final String FAX_NUMBER_ERROR_ID = "tfa_Fax-D-E";

    @FindBy(id = "tfa_Company")
    private WebElement companyName;

    @FindBy(id = "tfa_Street")
    private WebElement streetAddress;

    @FindBy(id = "tfa_City")
    private WebElement city;

    @FindBy(id = "tfa_Country")
    private WebElement country;

    @FindBy(id = "tfa_Phone")
    private WebElement phoneNumber;

    @FindBy(id = "tfa_Website")
    private WebElement website;

    @FindBy(id = "tfa_Fax")
    private WebElement faxNumber;

    @FindBy(id = "wfPageNextId2")
    private WebElement nextButton;

    @FindBy(id = PROPERTY_NAME_ERROR_ID)
    private WebElement propertyNameError;

    @FindBy(id = STREET_ADDRESS_ERROR_ID)
    private WebElement streetError;

    @FindBy(id = CITY_ERROR_ID)
    private WebElement cityError;

    @FindBy(id = COUNTRY_ERROR_ID)
    private WebElement countryError;

    @FindBy(id = PROPERTY_PHONE_ERROR_ID)
    private WebElement phoneError;

    @FindBy(id = WEBSITE_ERROR_ID)
    private WebElement websiteError;

    @FindBy(id = FAX_NUMBER_ERROR_ID)
    private WebElement faxError;

    public SupplierEnrollmentPropertyInfoForm(WebDriver webdriver) {
        super(webdriver);
    }

    public void typePropertyName(String propertyName) {
        this.companyName.sendKeys(propertyName);
    }

    public void typeStreetAddress(String streetAddress) {
        this.streetAddress.sendKeys(streetAddress);
    }

    public void typeCity(String city) {
        this.city.sendKeys(city);
    }

    public void selectCountry(String country) {
        Select select = new Select(this.country);
        select.selectByVisibleText(country);
    }

    public void typePhoneNumber(String phoneNumber) {
        this.phoneNumber.sendKeys(phoneNumber);
    }

    public void typeWebsite(String website) {
        this.website.sendKeys(website);
    }

    public void typeFaxNumber(String faxNumber) {
        if (this.faxNumber.isEnabled()) {
            this.faxNumber.sendKeys(faxNumber);
        }
    }

    public String getPropertyNameErrorId() {
        return PROPERTY_NAME_ERROR_ID;
    }

    public String getStreetAddressErrorId() {
        return STREET_ADDRESS_ERROR_ID;
    }

    public String getCityErrorId() {
        return CITY_ERROR_ID;
    }

    public String getCountryErrorId() {
        return PROPERTY_NAME_ERROR_ID;
    }

    public String getPropertyPhoneErrorId() {
        return PROPERTY_PHONE_ERROR_ID;
    }

    public String getWebsiteErrorId() {
        return WEBSITE_ERROR_ID;
    }

    public String getFaxNumberErrorId() {
        return FAX_NUMBER_ERROR_ID;
    }

    public boolean isPropertyNameErrorDisplayed() {
        return propertyNameError.isDisplayed();
    }

    public boolean isStreetErrorDisplayed() {
        return propertyNameError.isDisplayed();
    }

    public boolean isCityErrorDisplayed() {
        return cityError.isDisplayed();
    }

    public boolean isCountryErrorDisplayed() {
        return countryError.isDisplayed();
    }

    public boolean isPhoneErrorDisplayed() {
        return phoneError.isDisplayed();
    }

    public boolean isWebsiteErrorDisplayed() {
        return websiteError.isDisplayed();
    }

    public boolean isFaxErrorDisplayed() {
        return faxError.isDisplayed();
    }

    @Override
    public void fillRequiredInputAndSubmit(List<String> inputs) {
        typePropertyName(inputs.get(0));
        typeStreetAddress(inputs.get(1));
        typeCity(inputs.get(2));
        selectCountry(inputs.get(3));
        typePhoneNumber(inputs.get(4));
        typeWebsite(inputs.get(5));
        typeFaxNumber(inputs.get(6));
        clickNextButton();
    }

    @Override
    public void clickNextButton() {
        nextButton.click();
    }
}

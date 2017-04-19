/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.billing;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.row.models.CarIntlSolutionModel;
import com.hotwire.selenium.desktop.widget.DropDownSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author v-ypuchkova
 * @since 2012.10
 */
public class CarDetailsPage extends AbstractRowPage {

    @FindBy(className = "seleniumPrice")
    private WebElement totalPrice;
    @FindBy(className = "seleniumCentsAmount")
    private WebElement cents;
    @FindBy(className = "seleniumCarModel")
    private WebElement carModel;
    @FindBy(className = "seleniumCarType")
    private WebElement carType;
    @FindBy(className = "carOptions")
    private WebElement amenities;
    @FindBy(className = "carPriceDetails")
    private WebElement priceDetailsModule;
    /*
     * TravelerInfo
     */
    @FindBy(className = "seleniumFirstName")
    private WebElement travelerFirstName;
    @FindBy(className = "seleniumLastName")
    private WebElement travelerLastName;
    @FindBy(className = "seleniumEmailAddress")
    private WebElement travelerEmailAddress;
    @FindBy(className = "seleniumConfirmEmailAddress")
    private WebElement confirmEmailAddress;
    @FindBy(className = "seleniumPhoneNumber")
    private WebElement travelerPhoneNumber;
    @FindBy(className = "seleniumDriverAge")
    private WebElement driversAge;
    @FindBy(className = "seleniumCountry")
    private WebElement country;
    @FindBy(className = "seleniumStreetAddress")
    private WebElement streetAddress;
    @FindBy(className = "seleniumCityAddress")
    private WebElement city;
    @FindBy(css = "#creditCardForm select.cardState, select.seleniumState, input.seleniumState")
    private WebElement state;
    @FindBy(className = "seleniumPostalCode")
    private WebElement postalCode;
    /*
     * BillingInfo
     */
    @FindBy(className = "seleniumCreditCardType")
    private WebElement cardType;
    @FindBy(className = "seleniumCreditCardNumber")
    private WebElement cardNumber;
    @FindBy(className = "seleniumCreditCardExpiryMonth")
    private WebElement cardExpiryMonth;
    @FindBy(className = "seleniumCreditCardExpiryYear")
    private WebElement cardExpiryYear;
    @FindBy(className = "seleniumCreditCardSecurityCode")
    private WebElement cvvNumber;

    @FindBy(className = "seleniumDepositTypeTermsAccepted")
    private WebElement agreementCheckBox;

    @FindBy(className = "seleniumBookButton")
    private WebElement bookButton;

    public CarDetailsPage(WebDriver webdriver) {
        super(webdriver, DEFAULT_SEARCH_LAYER_ID, MAX_SEARCH_PAGE_WAIT_SECONDS);
    }

    public void book() {
        if (!agreementCheckBox.isSelected()) {
            agreementCheckBox.click();
        }

        bookButton.click();
    }

    public CarDetailsPage phoneNumber(String phoneNumber) {
        this.travelerPhoneNumber.clear();
        this.travelerPhoneNumber.sendKeys(phoneNumber);
        return this;
    }

    public CarDetailsPage confirmEmailAddress(String emailAddress) {
        this.confirmEmailAddress.clear();
        this.confirmEmailAddress.sendKeys(emailAddress);
        return this;
    }

    public CarDetailsPage emailAddress(String emailAddress) {
        this.travelerEmailAddress.clear();
        this.travelerEmailAddress.sendKeys(emailAddress);
        return this;
    }

    public CarDetailsPage travelerLastName(String lastName) {
        this.travelerLastName.clear();
        this.travelerLastName.sendKeys(lastName);
        return this;
    }

    public CarDetailsPage travelFirstName(String firstName) {
        this.travelerFirstName.clear();
        this.travelerFirstName.sendKeys(firstName);
        return this;
    }

    public CarDetailsPage cardCvvNumber(String cvvNumber) {
        this.cvvNumber.click();
        this.cvvNumber.sendKeys(cvvNumber);
        return this;
    }

    public CarDetailsPage cardExpYear(String expYear) {
        DropDownSelector cardExpiryYearSelector = new DropDownSelector(getWebDriver(), cardExpiryYear);
        cardExpiryYearSelector.selectByVisibleText(expYear);
        return this;
    }

    public CarDetailsPage cardExpMonth(String expMonth) {
        DropDownSelector cardExpiryMonthSelector = new DropDownSelector(getWebDriver(), cardExpiryMonth);
        cardExpiryMonthSelector.selectByVisibleText(expMonth);
        return this;
    }

    public CarDetailsPage cardNumber(String cardNumber) {
        this.cardNumber.clear();
        this.cardNumber.sendKeys(cardNumber);
        return this;
    }

    public CarDetailsPage cardName(String cardName) {
        DropDownSelector cardTypeSelector = new DropDownSelector(getWebDriver(), cardType);
        cardTypeSelector.selectByVisibleText(cardName.toUpperCase());
        return this;
    }

    public CarDetailsPage postalCode(String postalCode) {
        this.postalCode.clear();
        this.postalCode.sendKeys(postalCode);
        return this;
    }

    public CarDetailsPage state(String billingState) {
        // for intl site for Canada, United States and Australia should choose state from drop-down
        if (getCountry().equals("CA") || getCountry().equals("AU") || getCountry().equals("US")) {
            DropDownSelector stateSelector = new DropDownSelector(getWebDriver(), state);
            stateSelector.selectByVisibleText(billingState);
        }
        else {
            state.clear();
            state.sendKeys(billingState);
        }
        return this;
    }

    public CarDetailsPage city(String city) {
        this.city.clear();
        this.city.sendKeys(city);
        return this;
    }

    public CarDetailsPage billingAddress(String billingAddress) {
        streetAddress.clear();
        streetAddress.sendKeys(billingAddress);
        return this;
    }

    public CarDetailsPage driversAge(String driversAge) {
        fillDriversAge(driversAge);
        return this;
    }

    public CarDetailsPage country(String countryOfResidence) {
        fillCountry(countryOfResidence);
        return this;
    }

    public CarIntlSolutionModel getSolutionOptionsSet() {
        CarIntlSolutionModel solutionSet = new CarIntlSolutionModel();
        solutionSet.setCarType(getCarType());
        solutionSet.setPeopleCapacity(getSeatingCapacity());
        solutionSet.setPackageCapacity(getTrunkCapacity());
        solutionSet.setTransmissionType(getTransmissionInfo());
        solutionSet.setConditionerInfo(getConditionerInfo());
        solutionSet.setDriverName(getFullDriverName());
        solutionSet.setCardNumber(getCardNumber());
        solutionSet.setExpiryDate(getExpiryDate());
        solutionSet.setPickUpLocation(getPickUpLocation());
        solutionSet.setDropOffLocation(getDropOffLocation());
        solutionSet.setPayableUponArrival(getPayableOnArrival());
        solutionSet.setAmountPaid(getPayableNowPrice());

        return solutionSet;
    }

    public void fillCountry(String countryOfResidence) {
        DropDownSelector countrySelector = new DropDownSelector(getWebDriver(), country);
        countrySelector.selectByVisibleText(countryOfResidence);
    }

    public void fillDriversAge(String driversAge) {
        this.driversAge.clear();
        this.driversAge.sendKeys(driversAge);
    }

    public Float getTotalPrice() {
        String price = totalPrice.getAttribute("data-price");
        return Float.parseFloat(price);
    }

    public Float getTotalPriceOnPriceDetails() {
        WebElement priceOnDetailsModule = priceDetailsModule.findElement(By.className("seleniumTotalPrice"));
        return Float.parseFloat(priceOnDetailsModule.getText().replaceAll("[^0-9.]", ""));
    }

    public String getCurrency() {
        return totalPrice.getText().replaceAll("[0-9 ]", "");
    }

    public String getCurrencyPriceDetails() {
        return priceDetailsModule.findElement(By.className("seleniumTotalPrice")).getText().replaceAll("[0-9.]", "");
    }

    public String getCarModel() {
        return carModel.getText();
    }

    public String getCarModelPriceDetails() {
        return priceDetailsModule.findElement(By.className("seleniumCarNamePriceDetails")).getText();
    }

    private String getCarType() {
        return carType.getText();
    }

    /*
     * Data about amenities
     */
    private Integer getSeatingCapacity() {
        WebElement seatingCapacity = amenities.findElement(By.className("seleniumSeatingCapacity"));
        return Integer.parseInt(seatingCapacity.getText().replaceAll("[^0-9]", ""));
    }

    private Integer getTrunkCapacity() {
        WebElement trunkCapacity = amenities.findElement(By.className("seleniumTrunkCapacity"));
        return Integer.parseInt(trunkCapacity.getText().replaceAll("[^0-9]", ""));
    }

    private String getTransmissionInfo() {
        return amenities.findElement(By.className("seleniumTransmission")).getText();
    }

    private boolean getConditionerInfo() {
        try {
            return amenities.findElement(By.className("seleniumConditioner")).isEnabled();
        }
        catch (Exception e) {
            // no action
        }
        return false;
    }

    public String getCountry() {
        return country.getAttribute("value");
    }

    public Integer getDriversAge() {
        return Integer.parseInt(driversAge.getAttribute("value"));
    }

    private String getFullDriverName() {
        return travelerFirstName.getAttribute("value") + " " + travelerLastName.getAttribute("value");
    }

    /*
     * Data from payment and billing module
     */
    private String getCardNumber() {
        try {
            String cardNumberWithoutSpaces = cardNumber.getAttribute("value").replaceAll(" ", "");
            int cardLength = cardNumberWithoutSpaces.length();
            return cardNumberWithoutSpaces.substring(cardLength - 4, cardLength);
        }
        catch (Exception ex) {
            // no action
        }
        return null;
    }

    private String getExpiryDate() {
        try {
            String cardExpiryMonthDate = cardExpiryMonth.getAttribute("value");
            if (cardExpiryMonthDate.startsWith("0")) {
                cardExpiryMonthDate = cardExpiryMonth.getAttribute("value").replaceAll("0", "");
            }
            return cardExpiryMonthDate + "/" + cardExpiryYear.getAttribute("value");
        }
        catch (Exception ex) {
            // no action
        }
        return null;
    }

    /*
     * Data from price details Module
     */
    private String getPickUpLocation() {
        return priceDetailsModule.findElement(By.className("seleniumPickupLocation")).getText();
    }

    private String getDropOffLocation() {
        try {
            return priceDetailsModule.findElement(By.className("seleniumDropOffLocation")).getText();
        }
        catch (Exception ex) {
            // no action
        }
        return null;
    }

    private Float getPayableOnArrival() {
        WebElement payableOnArrival = priceDetailsModule.findElement(By.className("seleniumPayableOnArrival"));
        return Float.parseFloat(payableOnArrival.getText().replaceAll("[^0-9.]", ""));
    }

    private Float getPayableNowPrice() {
        WebElement payableNowPrice = priceDetailsModule.findElement(By.className("seleniumPayableNowPrice"));
        return Float.parseFloat(payableNowPrice.getText().replaceAll("[^0-9.]", ""));
    }

    public String getPickUpDate() {
        return priceDetailsModule.findElement(By.className("seleniumStartDate")).getText();
    }

    public String getDropOffDate() {
        return priceDetailsModule.findElement(By.className("seleniumEndDate")).getText();
    }

    public void clickSearchAgainButtonForCountry() {
        country.findElement(By.xpath(
            "//select[contains(@class,'seleniumCountry')]//..//..//a[contains(@class,'seleniumSearchAgainButton')]"))
               .click();
    }

    public void clickSearchAgainButtonForDriversAge() {
        // we should lost focus on drivers age field. Only after that "Search again" button will be enabled
        travelerPhoneNumber.click();
        driversAge.findElement(By.xpath(
            "//input[contains(@class,'seleniumDriverAge')]//..//..//a[contains(@class,'seleniumSearchAgainButton')]"))
                  .click();
    }

    public boolean isCurrencyMessageEnabled() {
        return !priceDetailsModule.findElement(By.className("note")).getText().equals("");
    }

    public void clickBackToResultsLink() {
        getWebDriver().findElement(By.xpath("//div[contains(@class, 'backLink')]//a")).click();
    }
}

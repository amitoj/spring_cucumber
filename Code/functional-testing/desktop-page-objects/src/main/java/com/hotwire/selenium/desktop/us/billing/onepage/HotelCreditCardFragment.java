/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.onepage;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.ExtendedSelect;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Page fragment that represents the credit card fields on the one page billing page.
 * @author aguthrie
 */
public class HotelCreditCardFragment extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelCreditCardFragment.class);

    private static final String SAVE_BILLING_CONTAINER = ".saveCardInfo";
    private static final String SAVE_BILLING_RADIO_BUTTONS =
            SAVE_BILLING_CONTAINER + "input[name='paymentInfoModel\\.saveNewCreditCard']";


    @FindBy(id = "paymentInfoModel.newCreditCard.newCardDescription")
    private WebElement nameForNewSavedCard;

    @FindBy(id = "paymentInfoModel.password")
    private WebElement passwordForNewSavedCard;

    @FindBy(xpath = ".//input[@id='paymentInfoModel.saveNewCreditCard1' and @value='true']")
    private WebElement saveBillingInformation;

    @FindBy(xpath = ".//input[@id='paymentInfoModel.saveNewCreditCard2' and @value='false']")
    private WebElement doNotSaveBillingInformation;

    @FindBy(css = ".selectPaymentMethod .cardMethod input#newCreditCardPaymentRadioButton")
    private WebElement paymentMethodTypeFields;

    @FindBy(id = "paymentInfoModel.newCreditCard.cardType")
    private WebElement creditCardType;

    @FindBy(id = "paymentInfoModel.newCreditCard.creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(id = "paymentInfoModel.newCreditCard.cardExpiryMonth")
    private WebElement creditCardExpirationMonth;
    @FindBy(id = "paymentInfoModel.savedCreditCards0.cardExpiryMonth")
    private WebElement savedCardExpirationMonth;

    @FindBy(id = "paymentInfoModel.newCreditCard.cardExpiryYear")
    private WebElement creditCardExpirationYear;
    @FindBy(id = "paymentInfoModel.savedCreditCards0.cardExpiryYear")
    private WebElement savedCardExpirationYear;

    @FindBy(id = "paymentInfoModel.newCreditCard.cpvNumber")
    private WebElement securityCode;
    @FindBy(id = "paymentInfoModel.savedCreditCards[0].cpvNumber")
    private WebElement savedSecurityCode;

    @FindBy(id = "paymentInfoModel.newCreditCard.billingFirstName")
    private WebElement firstName;
    @FindBy(id = "paymentInfoModel.savedCreditCards[0].billingFirstName")
    private WebElement savedFirstName;

    @FindBy(id = "paymentInfoModel.newCreditCard.billingLastName")
    private WebElement lastName;
    @FindBy(id = "paymentInfoModel.savedCreditCards[0].billingLastName")
    private WebElement savedLastName;

    @FindBy(id = "paymentInfoModel.newCreditCard.countryCode")
    private WebElement country;
    @FindBy(id = "paymentInfoModel.savedCreditCards[0].countryCode")
    private WebElement savedCountry;

    @FindBy(id = "paymentInfoModel.newCreditCard.billingAddress1")
    private WebElement billingAddress;
    @FindBy(id = "paymentInfoModel.savedCreditCards[0].billingAddress1")
    private WebElement savedBillingAddress;

    @FindBy(id = "paymentInfoModel.newCreditCard.city")
    private WebElement city;
    @FindBy(id = "paymentInfoModel.savedCreditCards[0].city")
    private WebElement savedCity;

    @FindBy(id = "paymentInfoModel.newCreditCard.province")
    private List<WebElement> state;
    @FindBy(id = "paymentInfoModel.savedCreditCards[0].province")
    private List<WebElement> savedState;

    @FindBy(id = "paymentInfoModel.newCreditCard.postalCode")
    private WebElement zipCode;
    @FindBy(id = "paymentInfoModel.savedCreditCards[0].postalCode")
    private WebElement savedZipCode;

    @FindBy(name = SAVE_BILLING_RADIO_BUTTONS)
    private List<WebElement> saveBillingInfoRadioButtons;

    public HotelCreditCardFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector("#billingPaymentPanel"));
        //paymentMethodTypeFields.click();
        getPaymentMethodTypeFields().click();
    }

    public HotelCreditCardFragment(WebDriver webDriver, boolean isEdit) {
        super(webDriver, By.cssSelector("#billingPaymentPanel"));
    }

    private WebElement getPaymentMethodTypeFields() {
        if (isNewCardDisplayed()) {
            paymentMethodTypeFields = getWebDriver()
                    .findElement(By.cssSelector(
                            ".selectPaymentMethod .cardMethod input#useNewCardPaymentRadioButton"));
        }
        return paymentMethodTypeFields;
    }

    private boolean isNewCardDisplayed() {
        try {
            getWebDriver()
                    .findElement(By.cssSelector(
                            ".selectPaymentMethod .cardMethod input#useNewCardPaymentRadioButton"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectSavedCreditCard(List<String> savedCreditCardNames, String securityCode) {
        List<WebElement> savedCC = getWebDriver()
               .findElements(By.xpath("//div[@class='cardMethod']//label"));
        String listSavedCC = "";
        if (savedCC.size() > 0) {
            for (int i = 0; i < savedCC.size(); i++) {
                String ccText = savedCC.get(i).getText();
                listSavedCC += "'" + ccText + "', ";
                for (String savedCreditCardName : savedCreditCardNames) {
                    if (ccText.trim().toLowerCase().contains(savedCreditCardName.trim().toLowerCase())) {
                        savedCC.get(i).click();
                        String cpvId = "paymentInfoModel.savedCreditCards[" + i + "].cpvNumber";
                        getWebDriver().findElement(By.id(cpvId)).sendKeys(securityCode);
                        return;
                    }
                }
            }
        }
        LOGGER.error("List of saved CC " + listSavedCC + "don't contain the CC " + savedCreditCardNames);
        throw new NoSuchElementException("No saved credit card ending with " + savedCreditCardNames + " found!");
    }

    public HotelCreditCardFragment withCcType(String creditCardNumber) {
        this.paymentMethodTypeFields.click();
        return this;
    }

    public HotelCreditCardFragment withCcNumber(String creditCardNumber) {
        this.creditCardNumber.clear();
        this.creditCardNumber.sendKeys(creditCardNumber);
        return this;
    }

    public HotelCreditCardFragment withCcExpMonth(String creditCardExpirationMonth) {
        new Select(this.creditCardExpirationMonth).selectByVisibleText(creditCardExpirationMonth);
        return this;
    }

    public HotelCreditCardFragment withSavedCcExpMonth(String creditCardExpirationMonth) {
        new Select(this.savedCardExpirationMonth).selectByVisibleText(creditCardExpirationMonth);
        return this;
    }

    public HotelCreditCardFragment withCcExpYear(String creditCardExpirationYear) {
        new Select(this.creditCardExpirationYear).selectByVisibleText(creditCardExpirationYear);
        return this;
    }

    public HotelCreditCardFragment withSavedCcExpYear(String creditCardExpirationYear) {
        new Select(this.savedCardExpirationYear).selectByVisibleText(creditCardExpirationYear);
        return this;
    }

    public HotelCreditCardFragment withSecurityCode(String securityCode) {
        this.securityCode.clear();
        this.securityCode.sendKeys(securityCode);
        return this;
    }

    public HotelCreditCardFragment withSavedSecurityCode(String securityCode) {
        this.savedSecurityCode.clear();
        this.savedSecurityCode.sendKeys(securityCode);
        return this;
    }

    public HotelCreditCardFragment withBillingAddress(String billingAddress) {
        this.billingAddress.clear();
        this.billingAddress.sendKeys(billingAddress);
        return this;
    }

    public HotelCreditCardFragment withSavedBillingAddress(String billingAddress) {
        this.savedBillingAddress.clear();
        this.savedBillingAddress.sendKeys(billingAddress);
        return this;
    }

    public HotelCreditCardFragment withFirstName(String firstName) {
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
        return this;
    }

    public HotelCreditCardFragment withSavedFirstName(String firstName) {
        this.savedFirstName.clear();
        this.savedFirstName.sendKeys(firstName);
        return this;
    }

    public HotelCreditCardFragment withMiddleName(String middleName) {
        return this;
    }

    public HotelCreditCardFragment withLastName(String lastName) {
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
        return this;
    }

    public HotelCreditCardFragment withSavedLastName(String lastName) {
        this.savedLastName.clear();
        this.savedLastName.sendKeys(lastName);
        return this;
    }

    public HotelCreditCardFragment withCountry(String country) {
        new Select(this.country).selectByVisibleText(country);
        return this;
    }

    public HotelCreditCardFragment withSavedCountry(String country) {
        new Select(this.savedCountry).selectByVisibleText(country);
        return this;
    }

    public HotelCreditCardFragment withCity(String city) {
        this.city.clear();
        this.city.sendKeys(city);
        return this;
    }

    public HotelCreditCardFragment withSavedCity(String city) {
        this.savedCity.clear();
        this.savedCity.sendKeys(city);
        return this;
    }

    public HotelCreditCardFragment withState(String state) {
        WebElement displayedElement = null;
        for (WebElement item : this.state) {
            if (item.isDisplayed() && item.isEnabled()) {
                displayedElement = item;
                break;
            }
        }
        if (displayedElement == null) {
            return this;
        }

        ExtendedSelect select = new ExtendedSelect(displayedElement);
        try {
            select.selectByIndex(Integer.parseInt(state));
            return this;
        }
        catch (NumberFormatException e) {
            // Do nothing. State is non-numeric and continue.
        }
        if (displayedElement != null) {
            if (displayedElement.getTagName().equals("select")) {
                new ExtendedSelect(displayedElement).selectIfVisibleTextStartsWithText(state);
            }
            else {
                // Default to sendkeys. Most likely element is an input text
                // area.
                displayedElement.sendKeys(state);
            }
        }
        // Else do nothing as this page is doing country specific elements and
        // some countries
        // will not have this element.
        return this;
    }

    public HotelCreditCardFragment withSavedState(String state) {
        WebElement displayedElement = null;
        for (WebElement item : this.savedState) {
            if (item.isDisplayed()) {
                displayedElement = item;
                break;
            }
        }
        if (displayedElement == null) {
            return this;
        }
        ExtendedSelect select = new ExtendedSelect(displayedElement);
        try {
            select.selectByIndex(Integer.parseInt(state));
            return this;
        }
        catch (NumberFormatException e) {
            // Do nothing. State is non-numeric and continue.
        }
        if (displayedElement != null) {
            if (displayedElement.getTagName().equals("select")) {
                new ExtendedSelect(displayedElement).selectIfVisibleTextStartsWithText(state);
            }
            else {
                // Default to sendkeys. Most likely element is an input text
                // area.
                displayedElement.sendKeys(state);
            }
        }
        // Else do nothing as this page is doing country specific elements and
        // some countries
        // will not have this element.
        return this;
    }

    public HotelCreditCardFragment withZipCode(String zipCode) {
        try {
            this.zipCode.clear();
            this.zipCode.sendKeys(zipCode);
        }
        catch (WebDriverException e) {
            LOGGER.info("This country has no zipcode");
        }
        return this;
    }

    public HotelCreditCardFragment withSavedZipCode(String zipCode) {
        try {
            this.savedZipCode.clear();
            this.savedZipCode.sendKeys(zipCode);
        }
        catch (WebDriverException e) {
            LOGGER.info("This country has no zipcode");
        }
        return this;
    }

    public boolean isSaveBillingInfoModuleDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(SAVE_BILLING_CONTAINER)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public WebElement getSaveBillingInformation() {
        return saveBillingInformation;
    }

    public WebElement getDoNotSaveBillingInformation() {
        return doNotSaveBillingInformation;
    }

    public String getNameForNewSavedCard() {
        return nameForNewSavedCard.getAttribute("value");
    }

    public void setNameForNewSavedCard(String name) {
        nameForNewSavedCard.sendKeys(name);
    }

    public String getPasswordForNewSavedCard() {
        return passwordForNewSavedCard.getAttribute("value");
    }

    public void setPasswordForNewSavedCard(String password) {
        passwordForNewSavedCard.sendKeys(password);
    }

    public String getLabel() {
        List<WebElement> savedCC = getWebDriver()
                .findElements(By.xpath("//div[@class='cardMethod']//label"));
        return savedCC.get(0).getText();
    }
}

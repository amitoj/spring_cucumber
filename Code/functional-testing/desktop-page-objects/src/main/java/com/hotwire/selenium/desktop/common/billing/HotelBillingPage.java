/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.common.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.row.elements.Button;
import com.hotwire.selenium.desktop.row.elements.Checkbox;
import com.hotwire.selenium.desktop.row.elements.Input;
import com.hotwire.selenium.desktop.row.elements.Label;

/**
 * Created with IntelliJ IDEA.
 */
public class HotelBillingPage extends AbstractRowPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelBillingPage.class.getSimpleName());

    @FindBy(className = "errorMessages")
    private WebElement errorMessages;

    @FindBy(name = "travelerInfoModel.travelers[0].firstName")
    private Input firstName;

    @FindBy(name = "travelerInfoModel.travelers[0].lastName")
    private Input lastName;

    @FindBy(name = "travelerInfoModel.emailAddress")
    private Input emailAddress;

    @FindBy(name = "travelerInfoModel.confirmEmailAddress")
    private Input confirmEmailAddress;

    @FindBy(name = "travelerInfoModel.phoneCountry")
    private Input phoneCountry;

    @FindBy(name = "travelerInfoModel.phoneNumber")
    private Input phoneNumber;

    @FindBy(name = "travelerInfoModel.wantsHotwireNewsLetter")
    private Checkbox subscription;

    @FindBy(css = "label[for='travelerInfoModel.wantsHotwireNewsLetter']")
    private Label subscriptionLabel;

    @FindBy(name = "paymentInfoModel.newCreditCard.creditCardNumber")
    private Input creditCardNumber;

    @FindBy(name = "paymentInfoModel.newCreditCard.cardExpiryMonth")
    private WebElement cardExpiryMonth;

    @FindBy(name = "paymentInfoModel.newCreditCard.cardExpiryYear")
    private WebElement cardExpiryYear;

    @FindBy(name = "paymentInfoModel.newCreditCard.cpvNumber")
    private Input cpvNumber;

    @FindBy(name = "paymentInfoModel.newCreditCard.billingFirstName")
    private Input billingFirstName;

    @FindBy(name = "paymentInfoModel.newCreditCard.billingLastName")
    private Input billingLastName;

    @FindBy(name = "paymentInfoModel.newCreditCard.countryCode")
    private WebElement countryCode;

    @FindBy(xpath = "//*[@id='paymentInfoModel.newCreditCard.countryCode']/child::option[@selected='selected']")
    private WebElement selectedCountryCode;

    @FindBy(id = "paymentInfoModel.newCreditCard.billingAddress1")
    private Input billingAddress1;

    @FindBy(id = "paymentInfoModel.newCreditCard.billingAddress2")
    private Input billingAddress2;

    @FindBy(name = "paymentInfoModel.newCreditCard.city")
    private Input city;

    @FindBy(css = "select[name='paymentInfoModel.newCreditCard.province']")
    private WebElement province;

    @FindBy(name = "paymentInfoModel.newCreditCard.postalCode")
    private Input postalCode;

    @FindBy(css = ".saveCardInfo")
    private WebElement saveCreditCardModule;

    @FindBy(css = ".savedCardInfoBox>a")
    private WebElement editCreditCard;

    @FindBy(name = "billingReviewModel.agreement")
    private Checkbox agreement;

    @FindBy(id = "confirmPaymentBtn")
    private Button confirmPaymentBtn;

    @FindBy(css = "div.discountCodeNote")
    private WebElement discountBanner;

    @FindBy(css = ".hotelName")
    private WebElement hotelName;

    @FindBy(css = ".signInLink a")
    private WebElement signInLink;

    public HotelBillingPage(WebDriver webdriver) {
        super(webdriver);
    }

    public boolean isSubscriptionOptionPresent() {
        try {
            return subscription.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public String getErrorMessages() {
        return errorMessages.findElement(By.className("msgBoxBody")).getText();
    }

    public HotelBillingPage setFirstName(String firstName) {
        this.firstName.setText(firstName);
        return this;
    }

    public HotelBillingPage setLastName(String lastName) {
        this.lastName.setText(lastName);
        return this;
    }

    public HotelBillingPage setEmailAddress(String emailAddress) {
        if (emailAddress != null) {
            this.emailAddress.setText(emailAddress);
        }
        return this;
    }

    public HotelBillingPage setConfirmEmailAddress(String confirmEmailAddress) {
        this.confirmEmailAddress.setText(confirmEmailAddress);
        return this;
    }

    public HotelBillingPage setPhoneCountry(String phoneCountry) {
        this.phoneCountry.setText(phoneCountry);
        return this;
    }

    public HotelBillingPage setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setText(phoneNumber);
        return this;
    }

    public boolean getSubscriptionState() {
        return this.subscription.isChecked();
    }

    public HotelBillingPage setSubscriptionState(Boolean wantsHotwireNewsLetter) {
        if (!wantsHotwireNewsLetter.equals(this.subscription.isChecked())) {
            this.subscription.check();
        }
        else {
            this.subscription.uncheck();
        }
        return this;
    }

    public String getSubscriptionProposal() {
        return subscriptionLabel.getText();
    }

    public HotelBillingPage setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber.setText(creditCardNumber);
        return this;
    }

    public HotelBillingPage setCardExpiryMonth(String cardExpiryMonth) {
        if (!"".equalsIgnoreCase(cardExpiryMonth)) {
            Select select = new Select(this.cardExpiryMonth);
            select.selectByVisibleText(cardExpiryMonth);
        }
        return this;
    }

    public HotelBillingPage setCardExpiryYear(String ccExpYear) {
        if (!"".equalsIgnoreCase(ccExpYear)) {
            Select select = new Select(this.cardExpiryYear);
            select.selectByVisibleText(ccExpYear);
        }
        return this;
    }

    public HotelBillingPage setCVPNumber(String cpvNumber) {
        this.cpvNumber.setText(cpvNumber);
        return this;
    }

    public HotelBillingPage setBillingFirstName(String billingFirstName) {
        this.billingFirstName.setText(billingFirstName);
        return this;
    }

    public HotelBillingPage setBillingLastName(String billingLastName) {
        this.billingLastName.setText(billingLastName);
        return this;
    }

    public HotelBillingPage setCountryCode(String countryCode) {
        if (!"".equalsIgnoreCase(countryCode)) {
            Select select = new Select(this.countryCode);
            select.selectByVisibleText(countryCode);
        }
        return this;
    }

    public HotelBillingPage setBillingAddress1(String billingAddress1) {
        this.billingAddress1.setText(billingAddress1);
        return this;
    }

    public HotelBillingPage setCity(String city) {
        this.city.setText(city);
        return this;
    }

    public HotelBillingPage setProvince(String province) {
        boolean availableElement = false;
        for (WebElement element : getWebDriver().findElements(
            By.cssSelector("select[name='paymentInfoModel.newCreditCard.province']"))) {
            if (element.isDisplayed()) {
                Select select = new Select(element);
                // If province is a number, select by index. If not select by value of province.
                try {
                    select.selectByIndex(Integer.parseInt(province));
                    availableElement = true;
                    break;
                }
                catch (NumberFormatException e) {
                    select.selectByValue(province);
                    availableElement = true;
                    break;
                }
            }
            LOGGER.info("Select Province class: " + element.getAttribute("class") + " not displayed.");
        }
        if (!availableElement) {
            LOGGER.info("No selectable province elements visible for use.");
        }
        return this;
    }

    public HotelBillingPage setPostalCode(String postalCode) {
        if (this.postalCode != null && this.postalCode.isDisplayed()) {
            this.postalCode.setText(postalCode);
        }
        else {
            LOGGER.info("Postal code element is not displayed for use.");
        }
        return this;
    }

    public HotelBillingPage setAgreement(Boolean agreement) {
        if (agreement) {
            this.agreement.check();
        }
        else {
            this.agreement.uncheck();
        }
        return this;
    }

    public void confirmPayment() {
        this.confirmPaymentBtn.click();
    }

    public HotelTripSummaryFragment getTripSummaryFragment() {
        return new HotelTripSummaryFragment(getWebDriver());
    }

    public WebElement getAgreeAndBookFragment() {
        // Trying to find new agree and book module (AAB13=2 version test)
        return getWebDriver().findElement(By.cssSelector(".agreeAndBook, .termsAndConditions"));
    }

    public Input getFirstName() {
        return firstName;
    }

    public Input getLastName() {
        return lastName;
    }

    public Input getEmailAddress() {
        return emailAddress;
    }

    public Input getConfirmEmailAddress() {
        return confirmEmailAddress;
    }

    public Input getPhoneNumber() {
        return phoneNumber;
    }

    public String getHotelName() {
        return hotelName.getText();
    }

    public String getPhoneCountry() {
        return phoneCountry.getText();
    }

    public String getCountry() {
        return selectedCountryCode.getText();
    }

    public WebElement getBillingAddress1() {
        try {
            return getWebDriver().findElement(By.id("paymentInfoModel.newCreditCard.billingAddress1"));
        }
        catch (NoSuchElementException e) {
            return null;
        }

    }

    public WebElement getBillingAddress2() {
        try {
            return getWebDriver().findElement(By.id("paymentInfoModel.newCreditCard.billingAddress2"));
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    public WebElement getCity() {
        try {
            return getWebDriver().findElement(By.name("paymentInfoModel.newCreditCard.city"));
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    public WebElement getProvince() {
        // This billing page has 3 province selectors that may or may not be visible. Can't just grab the first one
        // and expect it to be visible.
        for (WebElement element : getWebDriver().findElements(
            By.cssSelector("select[name='paymentInfoModel.newCreditCard.province']"))) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        return null;
    }

    public WebElement getSaveCreditCardModule() {
        try {
            return getWebDriver().findElement(By.cssSelector(".saveCardInfo"));
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean isSavedCreditCardModuleDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(".saveCardInfo")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isDiscountBannerPresent() {
        try {
            discountBanner.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCreditCardLogoPresent(String cardName) {
        try {
            return getWebDriver().findElement(By.xpath(".//img[@title='" + cardName + "']")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickSignInLink() {
        signInLink.click();
    }

    public String getEmailFromSignInLinkModule() {
        new AbstractDesktopPage(getWebDriver(), By.cssSelector("div[id='billingSignInLayer']"));
        return getWebDriver().findElement(By.cssSelector("div[id='billingSignInLayer'] input[id='email']"))
            .getAttribute("value");
    }

    public HotelTripSummaryFragment getHotelTripSummaryFragment() {
        return new HotelTripSummaryFragment(getWebDriver());
    }

    public WebElement getEditCreditCard() {
        return editCreditCard;
    }
}

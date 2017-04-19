/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.accordion;

import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarPaymentMethod;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 7:04 AM
 */
public class AcPaymentMethodFragment extends AbstractPageObject implements CarPaymentMethod {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcPaymentMethodFragment.class);

    protected String container = "div#newPaymentMethods ";
    protected String savedContainer = ".savedPaymentMethod ";
    protected String xpathSavedVisaRbtn = "//div[@class='savedPaymentMethod'] " +
                                          "[label[strong[contains(text(), 'Visa')]]]/input";
    protected String xpathSavedMasterCardRbtn = "//div[@class='savedPaymentMethod'] " +
                                                "[label[strong[contains(text(), 'My Mastercard')]]]/input";
    protected String idSavedVisaCVV = "billingForm.paymentForm._NAE_cpvNumber";
    protected String paymentOptionCard = "div#newPaymentMethod input#defaultPmIdNewCC";
    protected String cardNumber = "input[name='paymentForm._NAE_acctNumber']";
    protected String cardExpMonth = "select[name='paymentForm.cardMonth']";
    protected String cardExpYear = "select[name='paymentForm.cardYear']";
    protected String cardSecCode = "input[name='billingForm.paymentForm._NAE_cpvNumber']:enabled";
    protected String billFirstName = "input[name='paymentForm.billingFirstName']";
    protected String billLastName = "input[name='paymentForm.billingLastName']";
    protected String billAddress = "input[name='paymentForm._NAE_stAddress1']";
    protected String billCountry = "select[name='paymentForm.country']";
    protected String billCity = "input[name='paymentForm.city']";
    protected String billZipCode = "input[name='paymentForm._NAE_zip']";
    protected String billState = "select[name='paymentForm.state']";
    protected String countryFld = ".newPaymentMethodWrapper .countryFields";
    protected String paymentCardType = "div#paymentCreditCardType select#cardTypeSelector";
    protected String billingSection = "div#tileName-paymentAndBilling";


    protected String cssSavedCreditCardBtn = savedContainer + " input[type*='radio']";
    protected String xpathPayPalZip = "//input[@id ='billingForm.paymentForm._NAE_zip'][not(@disabled)]";
    protected String xpathPayPalState = "//select[@id ='billingForm.paymentForm.state'][not(@disabled)]";
    protected String xpathPayPalCity = "//input[@id ='billingForm.paymentForm.city'][not(@disabled)]";
    protected String xpathPayPalAddress = "//input[@id ='billingForm.paymentForm._NAE_stAddress1'][not(@disabled)]";
    protected String idPayPalFirstName = "billingForm.paymentForm.accountHolderName";
    protected String idPayPalBtn = "defaultPmIdNewPP";
    protected String xpathSecurityCode = "//input[@id='billingForm.paymentForm._NAE_cpvNumber'][not(@disabled)]";
    protected String idCreditCardField = "billingForm.paymentForm._NAE_acctNumber";

    @FindBy(name = "btnPaymentInfo")
    private WebElement btnPaymentInfo;

    @FindBy(xpath = "//div[@id='saveInfoLayer']/a")
    private WebElement saveMyInfoLink;

    @FindBy(css = "div#savePersonalInfo input[id*='password']")
    private WebElement saveMyInfoPassword;

    @FindBy(css = "div#savePersonalInfo input[id*='confirmPassword']")
    private WebElement saveMyInfoConfirmPassword;

    @FindBy(css = "div.saveBillingInfo input#ccSave")
    private WebElement saveBillingInfoYes;

    @FindBy (css = "#ccDescField")
    private WebElement saveMyCreditCardName;

    public AcPaymentMethodFragment(WebDriver webDriver) {
        this(webDriver, By.id("billingPanelPaymentInfoComp-contentElementRefreshable"));
    }

    protected AcPaymentMethodFragment(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator, new String[]{CarBillingPageProvider.TILES});
    }

    /*
    * Payment method
    */
    @Override
    public WebElement getPaymentOptionCreditCard() {
        return getWebDriver().findElement(By.cssSelector(paymentOptionCard));
    }

    public WebElement getPaymentCreditCardType() {
        return getWebDriver().findElement(By.cssSelector(paymentCardType));
    }

    public WebElement getCreditCardNumber() {
        return getWebDriver().findElement(By.cssSelector(container + cardNumber));
    }

    public WebElement getCreditCardExpMonth() {
        return getWebDriver().findElement(By.cssSelector(container + cardExpMonth));
    }

    public WebElement getCreditCardExpYear() {
        return getWebDriver().findElement(By.cssSelector(container + cardExpYear));
    }

    public WebElement getCreditCardSecurityCode() {
        return getWebDriver().findElement(By.xpath(xpathSecurityCode));
    }

    public WebElement getSavedCreditCardSecurityCode() {
        return getWebDriver().findElement(By.cssSelector(savedContainer + cardSecCode));
    }

    public WebElement getBillerFirstName() {
        return getWebDriver().findElement(By.cssSelector(container + billFirstName));
    }

    public WebElement getBillerLastName() {
        return getWebDriver().findElement(By.cssSelector(container + billLastName));
    }

    public WebElement getBillingAddress() {
        return getWebDriver().findElement(By.cssSelector(container + billAddress));
    }

    public WebElement getBillingCountry() {
        return getWebDriver().findElement(By.cssSelector(container + billCountry));
    }

    public WebElement getBillingCity() {
        return getWebDriver().findElement(By.cssSelector(container + billCity));
    }

    public WebElement getBillingZipCode() {
        return getWebDriver().findElement(By.cssSelector(container + billZipCode));
    }

    public WebElement getPaymentState() {
        for (WebElement element : getWebDriver().findElements(By.cssSelector(
                container + " .countryFields .state select, " +
                container + " .countryFields .provinceCanada select, " +
                container + " .countryFields .provinceOther input"))) {
            LOGGER.info("PAYMENT STATE: " + element.getAttribute("id"));
            if (element.isDisplayed()) {
                return element;
            }
        }
        return null;
    }

    /**
     * Choose credit card payment option and fill all requered fields
     */
    @Override
    public CarPaymentMethod processCreditCard() {
        try {
            new WebDriverWait(getWebDriver(), 1).until(PageObjectUtils.webElementVisibleTestFunction(
                    By.cssSelector(paymentOptionCard), true));
            getPaymentOptionCreditCard().click();
            try {
                new Select(getPaymentCreditCardType()).selectByVisibleText("Visa");
            }
            catch (NoSuchElementException e) { /* No-op */ }
        }
        catch (TimeoutException e) { /* No-op */ }
        return this;
    }


    @Override
    public CarPaymentMethod processSavedVisa(String securityCode) {
        getSavedVisaButton().click();
        getSavedVisaSecurityCode().sendKeys(securityCode);
        return this;
    }

    @Override
    public CarPaymentMethod processSavedMasterCard(String securityCode) {
        getSavedMasterCardButton().click();
        getSavedMasterCardSecurityCode().sendKeys(securityCode);
        return this;
    }

    @Override
    public CarPaymentMethod processPayPal() {
        getPayPalRadioButton().click();
        return this;
    }

    @Override
    public CarPaymentMethod processHotDollars() {
        new HotDollarPaymentFragment(getWebDriver()).useIt();
        return this;
    }

    @Override
    public CarPaymentMethod payPalUser(String firstName, String lastName) {
        sendKeys(getPayPalFirstName(), firstName + " " + lastName);
        return this;
    }

    @Override
    public CarPaymentMethod payPalAddress(String address) {
        sendKeys(getPayPalAddress(), address);
        return this;
    }


    @Override
    public CarPaymentMethod payPalCity(String city) {
        sendKeys(getPayPalCity(), city);
        return this;
    }


    @Override
    public CarPaymentMethod payPalState(String state) {
        new Select(getPayPalState()).selectByIndex(Integer.parseInt(state));
        return this;
    }

    @Override
    public CarPaymentMethod payPalZipCode(String zip) {
        sendKeys(getPayPalZip(), zip);
        return this;
    }

    @Override
    public CarPaymentMethod cardHolder(String firstName, String lastName) {
        sendKeys(getBillerFirstName(), firstName);
        sendKeys(getBillerLastName(), lastName);
        return this;
    }

    @Override
    public CarPaymentMethod creditCardNumber(String cardNumber) {
        sendKeys(getCreditCardNumber(), cardNumber);
        return this;
    }

    @Override
    public CarPaymentMethod expDate(String cardExpMonth, String cardExpYear) {
        new Select(getCreditCardExpMonth()).selectByVisibleText(cardExpMonth);
        new Select(getCreditCardExpYear()).selectByVisibleText(cardExpYear);
        return this;
    }

    @Override
    public CarPaymentMethod zipCode(String zipCode) {
        sendKeys(getBillingZipCode(), zipCode);
        return this;
    }

    @Override
    public CarPaymentMethod state(String state) {
        WebElement element = getPaymentState();

        if ("select".equals(element.getTagName())) {
            // We got dropdown.
            Select select = new Select(element);
            try {
                int index = Integer.parseInt(state);
                select.selectByIndex(index);
            }
            catch (NumberFormatException e) {
                select.selectByVisibleText(state);
            }
        }
        else if ("input".equals(element.getTagName())) {
            sendKeys(element, state);
        }
        else {
            throw  new RuntimeException("Unsupported html tag of state field..");
        }
        return this;
    }

    @Override
    public CarPaymentMethod city(String city) {
        sendKeys(getBillingCity(), city);
        return this;
    }

    @Override
    public CarPaymentMethod country(String country) {
        new Select(getBillingCountry()).selectByVisibleText(country);
        return this;
    }

    @Override
    public CarPaymentMethod billingAddress(String address) {
        new WebDriverWait(getWebDriver(), 3).until(PageObjectUtils.
            webElementVisibleTestFunction(getBillingAddress(), true));
        sendKeys(getBillingAddress(), address);
        return this;
    }

    @Override
    public CarPaymentMethod continuePanel() {
        btnPaymentInfo.click();
        return this;
    }

    @Override
    public CarPaymentMethod creditCardSecurityCode(String cardSecCode) {
        sendKeys(getCreditCardSecurityCode(), cardSecCode);
        return this;
    }

    @Override
    public CarPaymentMethod savedVisaSecurityCode(String cardSecCode) {
        sendKeys(getSavedVisaSecurityCode(), cardSecCode);
        return this;
    }

    @Override
    public WebElement getPayPalRadioButton() {
        return getWebDriver().findElement(By.id(idPayPalBtn));
    }

    public WebElement getPayPalFirstName() {
        return getWebDriver().findElement(By.id(idPayPalFirstName));
    }

    public WebElement getPayPalAddress() {
        return getWebDriver().findElement(By.xpath(xpathPayPalAddress));
    }

    public WebElement getPayPalCity() {
        return getWebDriver().findElement(By.xpath(xpathPayPalCity));
    }

    public WebElement getPayPalState() {
        return getWebDriver().findElement(By.xpath(xpathPayPalState));
    }

    public WebElement getPayPalZip() {
        return getWebDriver().findElement(By.xpath(xpathPayPalZip));
    }

    @Override
    public WebElement getHotDollarsButton() {
        return new HotDollarPaymentFragment(getWebDriver()).getHotDollarsButton();
    }

    @Override
    public WebElement getCreditCardField() {
        return getWebDriver().findElement(By.id(idCreditCardField));
    }

    @Override
    public WebElement getSavedCreditCardButton() {
        return getWebDriver().findElement(By.cssSelector(cssSavedCreditCardBtn));
    }


    @Override
    public boolean isHotDollarsModuleAvailable() {
        if (getWebDriver().findElements(By.xpath("//div[@id='paymentSection']" +
                                                 "//div[@class='hotDollars']")).size() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public String getHotDollarsMessage() {
        return new HotDollarPaymentFragment(getWebDriver()).getMessage();
    }

    @Override
    public boolean isBillingSectionPresent() {
        return !getWebDriver().findElement(By.cssSelector("#paymentSection #newPaymentMethods")).
                getAttribute("class").contains("hidden");
    }

    @Override
    public boolean isCreditCardIsSingleAvailablePayment() {
        try {
            return getWebDriver().findElement(By.cssSelector("input#defaultPmIdNewCC")).
                getAttribute("class").contains("text");
        }
        catch (NoSuchElementException e) {
            return true;
        }
    }

    @Override
    public String getNameOfChosenPaymentMethod() {
        List<WebElement> listOfPaymentMethods = getWebDriver().findElements(By.cssSelector("div.paymentMethod" +
                " div.paymentMethodSelector div input[type='radio']"));
        for (WebElement a : listOfPaymentMethods) {
            if (a.isSelected()) {
                return a.getAttribute("id");
            }
        }
        return "null";
    }

    @Override
    public void saveMyInformation() {
        saveMyInfoLink.click();
    }

    @Override
    public void savePaymentInformation() {

        saveBillingInfoYes.click();
    }

    @Override
    public WebElement getPasswordField() {
        return this.saveMyInfoPassword;
    }

    @Override
    public WebElement getConfirmPasswordField() {
        return this.saveMyInfoConfirmPassword;
    }

    public WebElement getSavedVisaSecurityCode() {
        return getWebDriver().findElement(By.id(idSavedVisaCVV));
    }

    public WebElement getSavedMasterCardSecurityCode() {
        throw new UnimplementedTestException("Implement me!");
    }

    public WebElement getSavedVisaButton() {
        return new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSavedVisaRbtn)));
    }

    @Override
    public WebElement getSavedMasterCardButton() {
        return getWebDriver().findElement(By.xpath(xpathSavedMasterCardRbtn));
    }

    @Override
    public boolean isSaveMyInfoExpanded() {
        return getWebDriver().findElement(By.xpath("//div[@id='saveInfoLayer']")).
                getAttribute("class").contains("expanded");
    }

    public void chooseCreditCardPaymentMethod() {
        getWebDriver().findElement(By.cssSelector("#defaultPmIdNewCC")).click();
    }

    @Override
    public boolean isSavedPaymentPresent() {
        try {
            return getWebDriver().findElement(By.cssSelector(".savedPaymentMethod")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public void typeCreditCardNameField(String ccNumber) {
        saveMyCreditCardName.sendKeys(ccNumber);
    }

}



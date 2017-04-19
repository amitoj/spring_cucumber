/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl.ccf;

import com.hotwire.selenium.desktop.us.billing.car.impl.CarPaymentMethod;
import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcPaymentMethodFragment;
import com.hotwire.selenium.desktop.widget.DropDownSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 7:04 AM
 */
public class CcfPaymentMethodFragment extends AcPaymentMethodFragment {

    private String idSavedMCCVV = "SAVED_CREDIT_CARD_1_CVV";
    private String idSavedMCBtn = "CREDIT_CARD_1";
    private String idSavedVisaBtn = "CREDIT_CARD_0";
    private String nameHotDollarsBtn = "hotDollarsChecked";

    private String namePayPalZip = "payPal.billingAddress.postalCode";
    private String namePayPalState = "payPal.billingAddress.state";
    private String idPayPalCity = "payPal.billingAddress.city";
    private String idPayPalAddress = "payPal.billingAddress.address";
    private String idPayPalLastName = "payPal.holder.lastName";
    private String xpathPayPalBtn = "//input[@id='payPal']";
    private String cssCreditCardNum = "input[id*='CreditCardNumber']";

    @FindBy(css = "div.saveMyInfoTitle a")
    private WebElement saveMyInfoLink;

    @FindBy(css = "div.passwordWrapper input#saveInfo-password")
    private WebElement saveMyInfoPassword;

    @FindBy(css = "div.passwordWrapper input#confirmPassword")
    private WebElement saveMyInfoConfirmPassword;

    @FindBy(css = "div.saveMyInfoSwitcher input#saveInfo-yes")
    private WebElement saveBillingInfoYes;

    @FindBy(css = "div.saveMyInfoSwitcher input#saveInfo-no")
    private WebElement saveBillingInfoNo;


    public CcfPaymentMethodFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector("div#tileName-paymentAndBilling"));
    }

    /**
     * Payment method
     */
    {

        idSavedVisaCVV = "SAVED_CREDIT_CARD_0_CVV";
        cssSavedCreditCardBtn = "div[class*='savedCreditCard'] input[type*='radio']";
        idPayPalFirstName = "payPal.holder.firstName";
        xpathSecurityCode =  "//input[@id='creditCard.securityCode'][not(@disabled)]";

        container = "div#tileName-paymentDetailsForm ";
        cardNumber = "input[name='creditCard.creditCardNumber']";
        cardExpMonth = "select[name='creditCard.expiryMonth']";
        cardExpYear = "select[name='creditCard.expiryYear']";
        cardSecCode = "input[name='creditCard.securityCode']";
        billFirstName = "input[name='creditCard.billingFirstName']";
        billLastName = "input[name='creditCard.billingLastName']";
        billAddress = "input[name='creditCard.address']";
        billCountry = "select[name='creditCard.country']";
        billCity = "input[name='creditCard.city']";
        billZipCode = "input[name='creditCard.postalCode']";
        billState = "input[name='creditCard.state']";
        billingSection = "div#tileName-paymentAndBilling";
        paymentOptionCard = "input#creditCard";
    }


    /**
     * Save payment information section
     */

    public WebElement getPasswordField() {
        return this.saveMyInfoPassword;
    }

    public WebElement getConfirmPasswordField() {
        return this.saveMyInfoConfirmPassword;
    }

//    public CarPaymentMethod paymentType(String paymentType) {
//        if ("PayPal".equals(paymentType) ) {
//            getPayPalRadioButton().click();
//        }
//        return this;
//    }

    @Override
    public CarPaymentMethod payPalUser(String firstName, String lastName) {
        WebElement payPalFirstName = getPayPalFirstName();
        WebElement payPalLastName = getPayPalLastName();
        payPalFirstName.clear();
        payPalFirstName.sendKeys(firstName);

        payPalLastName.clear();
        payPalLastName.sendKeys(lastName);
        return this;
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
    public CarPaymentMethod continuePanel() {
        //No action
        return this;
    }

    //        String id = "state" + getBillingCountry().getAttribute("value");
//        return "#" + id + ", #creditCard-" + id + ", #creditCard-" + id + "-button" + ", select.cardState";
//    }
//
    public String getCountry() {
        return getBillingCountry().getAttribute("value");
    }

    @Override
    public WebElement getPaymentState() {
        return getWebDriver().findElement(By.cssSelector("#tileName-countrySelector select[name='creditCard.state']" +
            "[id*='" + getCountry() + "']"));
    }

    private WebElement getPaymentStateField() {
        return getWebDriver().findElement(By.cssSelector("#tileName-countrySelector input[name='creditCard.state']" +
            "[type='text']"));
    }

    @Override
    public CarPaymentMethod country(String country) {
        new DropDownSelector(getWebDriver(), getBillingCountry()).selectByVisibleText(country);
        return this;
    }

    @Override
    public CarPaymentMethod state(String state) {
        //We try find drop down for country. If drop down is not enabled we using textual field
        if (getCountry().equals("CA") || getCountry().equals("AU") || getCountry().equals("US")) {
            new DropDownSelector(getWebDriver(),
                getPaymentState()).selectByVisibleText(state);
        }
        else {
            // Default to sendkeys. Most likely an input text area web element.
            getPaymentStateField().clear();
            getPaymentStateField().sendKeys(state);
        }
        return this;
    }

    @Override
    public CarPaymentMethod expDate(String cardExpMonth, String cardExpYear) {
        new DropDownSelector(getWebDriver(), getCreditCardExpMonth()).selectByVisibleText(cardExpMonth);
        new DropDownSelector(getWebDriver(), getCreditCardExpYear()).selectByVisibleText(cardExpYear);
        return this;
    }

    @Override
    public WebElement getPayPalRadioButton() {
        return getWebDriver().findElement(By.xpath(xpathPayPalBtn));
    }

    @Override
    public WebElement getPayPalFirstName() {
        return getWebDriver().findElement(By.id(idPayPalFirstName));
    }

    public WebElement getPayPalLastName() {
        return getWebDriver().findElement(By.id(idPayPalLastName));
    }

    @Override
    public WebElement getPayPalAddress() {
        return getWebDriver().findElement(By.id(idPayPalAddress));
    }

    @Override
    public WebElement getPayPalCity() {
        return getWebDriver().findElement(By.id(idPayPalCity));
    }

    @Override
    public WebElement getPayPalState() {
        return getWebDriver().findElement(By.name(namePayPalState));
    }

    @Override
    public WebElement getPayPalZip() {
        return getWebDriver().findElement(By.name(namePayPalZip));
    }

    public WebElement getHotDollarsButton() {
        return getWebDriver().findElement(By.name(nameHotDollarsBtn));
    }

    public WebElement getSavedCreditCardButton() {
        return getWebDriver().findElement(By.cssSelector(cssSavedCreditCardBtn));
    }

    public WebElement getSavedVisaButton() {
        return new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions.visibilityOfElementLocated(By.id(idSavedVisaBtn)));
    }

    public WebElement getSavedMasterCardButton() {
        return new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions.visibilityOfElementLocated(By.id(idSavedMCBtn)));

    }

    public WebElement getSavedVisaSecurityCode() {
        return getWebDriver().findElement(By.id(idSavedVisaCVV));
    }

    public WebElement getSavedMasterCardSecurityCode() {
        return getWebDriver().findElement(By.id(idSavedMCCVV));
    }

    public boolean isBillingSectionPresent() {
        return getWebDriver().findElement(By.cssSelector(billingSection + " div#paymentWrapper")).
            getAttribute("style").isEmpty();
    }

    public WebElement getPaymentOptionCreditCard() {
        return getWebDriver().findElement(By.cssSelector(paymentOptionCard));
    }

    public WebElement getCreditCardSecurityCode() {
        return getWebDriver().findElement(By.xpath(xpathSecurityCode));
    }

    public WebElement getCreditCardField() {
        return getWebDriver().findElement(By.cssSelector(cssCreditCardNum));
    }

    public boolean isCreditCardIsSingleAvailablePayment() {
        return getWebDriver().
            findElement(By.cssSelector(billingSection + " div.paymentMethod div p"))
            .getAttribute("class").contains("text");
    }

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

    public boolean isSaveMyInfoExpanded() {
        return saveMyInfoLink.getAttribute("class").contains("minus");
    }
}


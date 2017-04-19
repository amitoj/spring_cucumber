/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * AbstractPaymentMethodFragment
 */
public class AbstractPaymentMethodFragment extends MobileAbstractPage {

    @FindBy(xpath = "//*[@data-bdd='ccNumber']")
    protected WebElement ccNumber;
    @FindBy(xpath = "//*[@data-bdd='expMonth']")
    protected WebElement ccExpMonth;
    @FindBy(xpath = "//*[@data-bdd='expYear']")
    protected WebElement ccExpYear;
    @FindBy(xpath = "//*[@data-bdd='cvv']")
    protected WebElement securityCode;
    @FindBy(xpath = "//*[@data-bdd='firstName']")
    protected WebElement firstName;
    @FindBy(xpath = "//*[@data-bdd='address1']")
    protected WebElement billingAddress;
    @FindBy(xpath = "//*[@data-bdd='country']")
    protected WebElement country;
    @FindBy(xpath = "//*[@data-bdd='city']")
    protected WebElement city;
    @FindBy(xpath = "//*[@data-bdd='state']")
    protected WebElement state;
    @FindBy(xpath = "//*[@data-bdd='zip']")
    protected WebElement zipCode;

    @FindBy(id = "savedCcList")
    protected WebElement savedCcList;

    @FindBy(id = "ccType")
    protected WebElement ccType;

    @FindBy(name = "hotelPaymentInfoContinue")
    protected WebElement continueButton;

    @FindBy(id = "bookAndConfirmBtn")
    protected WebElement bookAndConfirmButton;

    @FindBy(xpath = "//*[@data-bdd='lastName']")
    private WebElement lastName;

    @FindBy(xpath = "//*[@data-bdd='address2']")
    private WebElement billingAddress2;

    @FindBy(id = "saveBillInfo-input")
    private WebElement saveBillInfo;

    @FindBy(id = "ccname-input")
    private WebElement ccName;

    public AbstractPaymentMethodFragment(WebDriver webdriver,
        String expectedPageName) {
        super(webdriver, expectedPageName);
    }

    public void clickContinueButton() {
        this.continueButton.click();
    }

    public void clickBookAndConfirmButton() {
        this.bookAndConfirmButton.click();
    }

    public AbstractPaymentMethodFragment setCcType(String ccType) {
        new Select(this.ccType).selectByVisibleText(ccType);
        return this;
    }

    public AbstractPaymentMethodFragment setCcNumber(String ccNumber) {
        this.ccNumber.sendKeys(ccNumber);
        return this;
    }

    public  AbstractPaymentMethodFragment setCcExpMonth(String ccExpMonth) {
        new Select(this.ccExpMonth).selectByVisibleText(ccExpMonth);
        return this;
    }

    public AbstractPaymentMethodFragment setCcExpYear(String ccExpYear) {
        new Select(this.ccExpYear).selectByVisibleText(ccExpYear);
        return this;
    }

    public AbstractPaymentMethodFragment setSecurityCode(String securityCode) {
        this.securityCode.sendKeys(securityCode);
        return this;
    }

    public AbstractPaymentMethodFragment setFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
        return this;
    }

    public AbstractPaymentMethodFragment setLastName(String lastName) {
        this.lastName.sendKeys(lastName);
        return this;
    }

    public AbstractPaymentMethodFragment setBillingAddress(String billingAddress) {
        this.billingAddress.sendKeys(billingAddress);
        return this;
    }

    public AbstractPaymentMethodFragment setBillingAddress2(String billingAddress2) {
        this.billingAddress2.sendKeys(billingAddress2);
        return this;
    }

    public AbstractPaymentMethodFragment setCountry(String country) {
        new Select(this.country).selectByVisibleText(country);
        return this;
    }

    public AbstractPaymentMethodFragment setCity(String city) {
        this.city.sendKeys(city);
        return this;
    }

    public AbstractPaymentMethodFragment setState(String state, String country) {
        boolean isSelect = true;
        WebElement element;
        if (country.equalsIgnoreCase("united states")) {
            element = this.state;
        }
        else if (country.equalsIgnoreCase("canada")) {
            element = getWebDriver().findElement(By.cssSelector("select[id='province']"));
        }
        else if (country.equalsIgnoreCase("australia")) {
            element = getWebDriver().findElement(By.cssSelector("select[id='auProvince']"));
        }
        else {
            element = getWebDriver().findElement(By.cssSelector("input[id='other']"));
            isSelect = false;
        }
        if (!element.isDisplayed()) {
            return this;
        }

        if (!isSelect) {
            element.sendKeys(state);
        }
        else {
            if (element.isDisplayed()) {
                Select select = new Select(element);
                try {
                    int index = Integer.parseInt(state);
                    select.selectByIndex(index);
                }
                catch (NumberFormatException e) {
                    select.selectByValue(state);
                }
            }
        }
        return this;
    }

    public AbstractPaymentMethodFragment setZipCode(String zipCode) {
        this.zipCode.sendKeys(zipCode);
        return this;
    }

    public AbstractPaymentMethodFragment saveBillingInfo(String ccName) {
        this.saveBillInfo.click();
        this.ccName.clear();
        this.ccName.sendKeys(ccName);
        return this;
    }

    protected void submitData(String ccNumber, String ccExpMonth,
            String ccExpYear, String securityCode, String firstName,
            String lastName, String billingAddress, String country,
            String city, String state, String zipCode) {

        new Select(this.country).selectByVisibleText(country);
        this.ccNumber.clear();
        this.ccNumber.sendKeys(ccNumber);
        new Select(this.ccExpMonth).selectByVisibleText(ccExpMonth);
        new Select(this.ccExpYear).selectByVisibleText(ccExpYear);
        this.securityCode.clear();
        this.securityCode.sendKeys(securityCode);
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
        if (this.billingAddress.isDisplayed()) {
            this.billingAddress.clear();
            this.billingAddress.sendKeys(billingAddress);
        }
        if (this.city.isDisplayed()) {
            this.city.clear();
            this.city.sendKeys(city);
        }
        WebElement element;
        boolean isSelect = true;
        if (country.equalsIgnoreCase("united states")) {
            element = this.state;
        }
        else if (country.equalsIgnoreCase("canada")) {
            element = getWebDriver().findElement(By.cssSelector("select[id='province']"));
        }
        else if (country.equalsIgnoreCase("australia")) {
            element = getWebDriver().findElement(By.cssSelector("select[id='auProvince']"));
        }
        else {
            element = getWebDriver().findElement(By.cssSelector("input[id='other']"));
            isSelect = false;
        }
        if (!isSelect) {
            element.sendKeys(state);
        }
        else {
            if (element.isDisplayed()) {
                Select select = new Select(element);
                try {
                    int index = Integer.parseInt(state);
                    select.selectByIndex(index);
                }
                catch (NumberFormatException e) {
                    select.selectByValue(state);
                }
            }
        }
        this.zipCode.clear();
        this.zipCode.sendKeys(zipCode);
    }

    public AbstractPaymentMethodFragment setSavedCc(String savedCc) {
        Select ccList = new Select(this.savedCcList);
        ccList.selectByVisibleText(savedCc);
        return this;
    }

    public boolean isCcListAvalible() {
        try {
            this.savedCcList.isDisplayed();
            return true;
        }
        catch (WebDriverException e) {
            return false;
        }
    }

}



/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.editAccountInfo;

import com.hotwire.selenium.tools.ToolsAbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 6/19/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3EditAccInfoPage extends ToolsAbstractPage {
    private static String firstName = "input[name='firstName']";
    private static String middleName = "input[name='middleName']";
    private static String lastName = "input[name='lastName']";

    private static String countryPhone = "input[name='countryPhone']";
    private static String cityPhone = "input[name='cityPhone']";
    private static String primaryPhone = "input[name='primaryPhone']";

    private static String altCountryPhone = "input[name='altCountryPhone']";
    private static String altCityPhone = "input[name='altCityPhone']";
    private static String alternatePhone = "input[name='alternatePhone']";

    private static String registerZip = "input#registerZip";
    private static String countryCode = "select[name='countryCode']";

    private static String email = "input#email";
    private static String confirmEmail = "input#confirmEmail";

    private static String deliveryStAddress = "input#address";
    private static String deliveryStAddress2 = "input#deliveryStAddress2";
    private static String deliveryCity = "input[name='deliveryCity']";
    private static String deliveryState = "select[name='deliveryState']";
    private static String deliveryZip = "input[name='_NAE_deliveryZip']";

    private static String confirmBtn = "input[value='Confirm']";
    private static String resetPassword = "input[value='Reset Password']";
    private static String reactivateBtn = "input.deactivateInfobarButton[value='Reactivate Account']";
    private static String deactivateBtn = "input.deactivateInfobarButton[value='Deactivate Account']";
    private static String subscribeChb = "input#newsletter";
    private static String msgBox = "td#errorMessaging";

    public C3EditAccInfoPage(WebDriver webDriver) {
        super(webDriver, By.className("formPadding"));
    }

    public void setPrimaryPhone(String countryCode, String cityCode, String number) {
        setText(countryPhone, countryCode);
        setText(cityPhone, cityCode);
        setText(primaryPhone, number);
    }

    public void setAlternatePhone(String countryCode, String cityCode, String number) {
        setText(altCountryPhone, countryCode);
        setText(altCityPhone, cityCode);
        setText(alternatePhone, number);
    }

    public void clickSubmit() {
        findOne("input[name='countryPhone']").sendKeys(Keys.ENTER);
    }

    public void deactivateAccount() {
        findOne(deactivateBtn, EXTRA_WAIT).click();
        findOne(confirmBtn, DEFAULT_WAIT).click();
    }

    public void reactivateAccount() {
        findOne(reactivateBtn, DEFAULT_WAIT).click();
        findOne(confirmBtn, DEFAULT_WAIT).click();
    }

    public void cancelDeactivation() {
        findOne(deactivateBtn, DEFAULT_WAIT).click();
        findOne("input[name='cancelConfirm']", DEFAULT_WAIT).click();
    }

    public String getMessage() {
        String errors = "";
        List<WebElement> errorList = findMany(msgBox, DEFAULT_WAIT);
        for (WebElement elem : errorList) {
            errors += elem.getText();
        }
        return errors;
    }

    public boolean isMsgDisplayed() {
        return isElementDisplayed(By.cssSelector(msgBox));
    }

    public void editHotDollars() {
        clickOnLink("Edit Hot Dollars", DEFAULT_WAIT);
    }

    public void clickAddANewCardLink() {
        clickOnLink("Add a new credit card", DEFAULT_WAIT);
    }

    public void clickEditPaymentMethods() {
        clickOnLink("Edit Payment Methods", DEFAULT_WAIT);
    }

//    public String returnAnyMessage() {
//        return findOne("#errorMessaging", DEFAULT_WAIT).getText();
//    }

    public void changeSubscription() {
        findOne(subscribeChb, DEFAULT_WAIT).click();
        //We need to fill all required fields
        setText("input#address", "Test address");
        setText("input#city.inputOff", "Test city");
        selectValue("select[name='deliveryState']", "AA");
        setText("input#postal", "94124");
        setText("input#registerZip", "94124");
    }

    public boolean isCustomerSubscribed() {
        return findOne(subscribeChb).isSelected();
    }

    public void clickResetPasswordBtn() {
        findOne(resetPassword, 1).click();
    }

    public void confirmPasswordReset() {
        findOne("#titleLayerDiv input[value='ok']", 1).click();
    }

    public void cancelPasswordReset() {
        findOne("#titleLayerDiv input[value='cancel']", 1).click();
    }

    public String getFirstName() {
        return findOne(firstName).getText();
    }

    public String getMiddleName() {
        return findOne(middleName).getText();
    }

    public String getLastName() {
        return findOne(lastName).getText();
    }

    public String getCountryPhone() {
        return findOne(countryPhone).getText();
    }

    public String getCityPhone() {
        return findOne(cityPhone).getText();
    }

    public String getPrimaryPhone() {
        return findOne(primaryPhone).getText();
    }

    public String getAltCountryPhone() {
        return findOne(altCountryPhone).getText();
    }

    public String getAltCityPhone() {
        return findOne(altCityPhone).getText();
    }

    public String getAlternatePhone() {
        return findOne(alternatePhone).getText();
    }

    public String getRegisterZip() {
        return findOne(registerZip).getText();
    }

    public String getCountryCode() {
        return findOne(countryCode).getText();
    }

    public String getEmail() {
        return findOne(email).getText();
    }

    public String getConfirmEmail() {
        return findOne(confirmEmail).getText();
    }

    public String getDeliveryStAddress() {
        return findOne(deliveryStAddress).getText();
    }

    public String getDeliveryStAddress2() {
        return findOne(deliveryStAddress2).getText();
    }

    public String getDeliveryCity() {
        return findOne(deliveryCity).getText();
    }

    public String getDeliveryState() {
        return new Select(findOne(deliveryState)).getFirstSelectedOption().getText();
    }

    public String getDeliveryZip() {
        return findOne(deliveryZip).getText();
    }

    public void setFirstName(String text) {
        findOne(firstName).clear();
        findOne(firstName).sendKeys(text);
    }

    public void setMiddleName(String text) {
        findOne(middleName).clear();
        findOne(middleName).sendKeys(text);
    }

    public void setLastName(String text) {
        findOne(lastName).clear();
        findOne(lastName).sendKeys(text);
    }

    public void setCountryPhone(String text) {
        findOne(countryPhone).clear();
        findOne(countryPhone).sendKeys(text);
    }

    public void setCityPhone(String text) {
        findOne(cityPhone).clear();
        findOne(cityPhone).sendKeys(text);
    }

    public void setPrimaryPhone(String text) {
        findOne(primaryPhone).clear();
        findOne(primaryPhone).sendKeys(text);
    }

    public void setAltCountryPhone(String text) {
        findOne(altCountryPhone).clear();
        findOne(altCountryPhone).sendKeys(text);
    }

    public void setAltCityPhone(String text) {
        findOne(altCityPhone).clear();
        findOne(altCityPhone).sendKeys(text);
    }

    public void setAlternatePhone(String text) {
        findOne(alternatePhone).clear();
        findOne(alternatePhone).sendKeys(text);
    }

    public void setRegisterZip(String text) {
        findOne(registerZip).clear();
        findOne(registerZip).sendKeys(text);
    }

    public void setCountryCode(String text) {
        selectValueByVisibleText(countryCode, text, 1);
    }

    public void setEmail(String text) {
        findOne(email).clear();
        findOne(email).sendKeys(text);
    }

    public void setConfirmEmail(String text) {
        findOne(confirmEmail).clear();
        findOne(confirmEmail).sendKeys(text);
    }

    public void setDeliveryStAddress(String text) {
        findOne(deliveryStAddress).clear();
        findOne(deliveryStAddress).sendKeys(text);
    }

    public void setDeliveryStAddress2(String text) {
        findOne(deliveryStAddress2).clear();
        findOne(deliveryStAddress2).sendKeys(text);
    }

    public void setDeliveryCity(String text) {
        findOne(deliveryCity).clear();
        findOne(deliveryCity).sendKeys(text);
    }

    public void setDeliveryState(String text) {
        selectValueByVisibleText(deliveryState, text, 1);
//        findOne(deliveryState).clear();
//        findOne(deliveryState).sendKeys(text);
    }

    public void setDeliveryZip(String text) {
        findOne(deliveryZip).clear();
        findOne(deliveryZip).sendKeys(text);
    }

    public void clearContactDetails() {
        findOne(firstName).clear();
        findOne(middleName).clear();
        findOne(lastName).clear();

        findOne(countryPhone).clear();
        findOne(cityPhone).clear();
        findOne(primaryPhone).clear();

        findOne(altCountryPhone).clear();
        findOne(altCityPhone).clear();
        findOne(alternatePhone).clear();

        findOne(registerZip).clear();
        selectValueByVisibleText(countryCode, "-Select Country-", 1);

        findOne(email).clear();
        findOne(confirmEmail).clear();
    }
}

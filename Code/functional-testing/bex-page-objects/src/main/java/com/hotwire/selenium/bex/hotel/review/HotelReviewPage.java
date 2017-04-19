/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.bex.hotel.review;

import com.hotwire.selenium.bex.BexAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelReviewPage extends BexAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelReviewPage.class.getSimpleName());

    @FindBy(xpath = "//*[@class='text cko-field contact-name single-name-field']")
    private WebElement contactNameTextBox;

    @FindBy(id = "phone-number-room-0")
    private WebElement phoneNumberTextBox;

    @FindBy(name = "card_number")
    private WebElement cardNumberTextBox;

    @FindBy(name = "expiration_month")
    private WebElement ccExpirationMonthList;

    @FindBy(name = "expiration_year")
    private WebElement ccExpirationYearList;

    @FindBy(id = "new_card_security_code")
    private WebElement ccSecurityCodeTextBox;

    @FindBy(name = "cardholder_name")
    private WebElement cardHolderTextBox;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement contactEmailTextBox;

    @FindBy(id = "complete-booking")
    private WebElement completeBookingButton;


    public HotelReviewPage(WebDriver webdriver) {
        super(webdriver, By.id("page-header"));
        PageFactory.initElements(getWebDriver(), this);

    }

    public void fillContactInfo(String name, String phoneNumber) {
        contactNameTextBox.sendKeys(name);
        phoneNumberTextBox.sendKeys(phoneNumber);

    }

    public void fillPaymentInfo() {
        cardNumberTextBox.sendKeys("4111111111111111");
        Select expirationMonth = new Select(ccExpirationMonthList);
        expirationMonth.selectByIndex(2);
        Select expirationYear = new Select(ccExpirationYearList);
        expirationYear.selectByIndex(2);
        ccSecurityCodeTextBox.sendKeys("254");
    }


    public void fillEmailAddress(String email) {
        contactEmailTextBox.sendKeys(email);

    }

    public void completeBooking() {

        completeBookingButton.click();
    }

}

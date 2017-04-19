/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.air.billing;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by v-ozelenov on 3/31/2014.
 */
public class C3AirMultiPassengerInfoFragment extends C3AirBillingPage {
    public C3AirMultiPassengerInfoFragment(WebDriver webDriver) {
        super(webDriver, By.id("travelers"));
    }


    @Override
    public void proceed() {
        findOne("div#billingPanelTravelerInfoComp-" +
                "contentElementRefreshable input.continueBtn").click();
    }

    public C3AirMultiPassengerInfoFragment fillGuestName() {
        for (WebElement name : findMany("div#travelers div.name input")) {
            if (name.isDisplayed()) {
                name.sendKeys("Test" + RandomStringUtils.randomAlphabetic(5));
            }
        }
        return this;
    }

    public C3AirMultiPassengerInfoFragment fillCustomerName() {
        List<WebElement> travelerName;

        try {
            travelerName = findMany(By.xpath("//input[contains(@name, 'travelerForm.psgrFirstName')]"));
            for (WebElement name : travelerName) {
                name.sendKeys("FirstName" + RandomStringUtils.randomAlphabetic(3));
            }
        }
        catch (Exception e) {
            logger.info("Travelers first name pre-filled");
        }

        try {
            travelerName = findMany(By.xpath("//input[contains(@name, 'travelerForm.psgrLastName')]"));
            for (WebElement name : travelerName) {
                name.sendKeys("LastName" + RandomStringUtils.randomAlphabetic(3));
            }
        }
        catch (Exception e) {
            logger.info("Travelers last name pre-filled");
        }
        return this;
    }

    public C3AirMultiPassengerInfoFragment fillGender() {
        for (WebElement gender : findMany("ul.gender input#male")) {
            if (gender.isDisplayed()) {
                gender.click();
            }
        }
        return this;
    }

    public C3AirMultiPassengerInfoFragment fillBirthDay() {
        for (WebElement birthRow : findMany("div#dobRowId")) {
            if (birthRow.isDisplayed()) {
                setDate(birthRow, "select[id*='travelerForm.psgrDobMonth'] option[value='1']");
                setDate(birthRow, "select[id*='travelerForm.psgrDobDay'] option[value='1']");
                setDate(birthRow, "select[id*='travelerForm.psgrDobYear'] option[value='1980']");
            }
        }
        return this;
    }

    private void setDate(WebElement row, String selectCSS) {
        row.findElement(By.cssSelector(selectCSS)).click();
    }

    public C3AirMultiPassengerInfoFragment fillPhone(String phone) {
        setText("input.labeledPhoneNo", phone);
        return this;
    }
    /**
     * Fill email and confirmation email
     */
    public C3AirMultiPassengerInfoFragment fillEmails(String email) {
        for (WebElement emailField : findMany("input.email")) {
            if (emailField.isDisplayed()) {
                emailField.sendKeys(email);
            }
        }
        return this;
    }

    public C3AirBillingPage fillDriverAge() {
        findOne("#ageConfirmationCheckbox").click();
        return this;
    }
}

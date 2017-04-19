/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by v-bshukaylo on 5/28/2014.
 */
public class AccountTravelerNamesPage extends AbstractAccountPage {


    @FindBy(id = "travelers0.firstName")
    private WebElement firstTravelerFirstName;

    @FindBy(id = "travelers0.middleName")
    private WebElement firstTravelerMiddleName;

    @FindBy(id = "travelers0.lastName")
    private WebElement firstTravelerLastName;

    @FindBy(xpath = "//div[@class='TravelerNamesComp']//button[1]")
    private WebElement saveChangesButton;

    @FindBy(xpath = ".//li[contains(text(), 'Your traveler name(s) have been changed.') ]")
    private WebElement changeConfirmationMessage;

    public AccountTravelerNamesPage(WebDriver driver) {
        super(driver, "tile.account.travelerNames");
    }

    public String getFirstTravelerFirstName() {
        return firstTravelerFirstName.getAttribute("value");
    }

    public void setFirstTravelerFirstName(String firstTravelerFirstName) {
        this.firstTravelerFirstName.clear();
        this.firstTravelerFirstName.sendKeys(firstTravelerFirstName);
    }

    public String getFirstTravelerMiddleName() {
        return firstTravelerMiddleName.getAttribute("value");
    }

    public void setFirstTravelerMiddleName(String firstTravelerMiddleName) {
        this.firstTravelerMiddleName.clear();
        this.firstTravelerMiddleName.sendKeys(firstTravelerMiddleName);
    }

    public String getFirstTravelerLastName() {
        return firstTravelerLastName.getAttribute("value");
    }

    public void setFirstTravelerLastName(String firstTravelerLastName) {
        this.firstTravelerLastName.clear();
        this.firstTravelerLastName.sendKeys(firstTravelerLastName);
    }

    public void saveChanges() {
        this.saveChangesButton.click();
    }

    public boolean verifyInfoChanged() {
        String actual = this.changeConfirmationMessage.getText();
        String expected = "Your traveler name(s) have been changed.";
        return actual.equals(expected);
    }
}

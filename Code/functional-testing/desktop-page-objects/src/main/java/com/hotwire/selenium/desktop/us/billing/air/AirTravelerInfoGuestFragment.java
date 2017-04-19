/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.air;

import com.hotwire.selenium.desktop.us.billing.AbstractTravelerInfoFragment;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by IntelliJ IDEA. User: v-abudyak Date: 5/17/12 Time: 6:22 PM To change this template use File | Settings |
 * File Templates.
 */
public class AirTravelerInfoGuestFragment extends AbstractTravelerInfoFragment {

    @FindBy(css = ".addTravelerDropDown")
    private WebElement travelerDropDown;

    @FindBy(name = "travelerForm.psgrFirstName0")
    private WebElement firstName;

    @FindBy(name = "travelerForm.psgrLastName0")
    private WebElement lastName;

    @FindBy(name = "travelerForm.carForm.driverFirstName")
    private WebElement driverName;

    @FindBy(name = "travelerForm.carForm.driverLastName")
    private WebElement driverLastName;

    @FindBy(xpath = ".//select[contains(@name, 'travelerForm.psgrDobMonth')]")
    private WebElement dobMonth;

    @FindBy(xpath = ".//select[contains(@name, 'travelerForm.psgrDobDay')]")
    private WebElement dobDay;

    @FindBy(xpath = ".//select[contains(@name, 'travelerForm.psgrDobYear')]")
    private WebElement dobYear;

    @FindBy(id = "male")
    private WebElement genderM;

    @FindBy(id = "female")
    private WebElement genderF;

    @FindBy(id = "ageConfirmationCheckbox")
    private WebElement age;

    @FindBy(id = "icon0-seat")
    private WebElement seatPreference;

    @FindBy(xpath = ".//div[@class='divAlign']")
    private WebElement seatPreferenceText;

    public AirTravelerInfoGuestFragment(WebDriver webDriver) {
        super(webDriver);
    }

    private void selectByText(WebElement element, String value) {
        Select clickThis = new Select(element);
        clickThis.selectByVisibleText(value);
    }

    public Boolean verifySeatPreferenceExpandCollpase() {
        try {
            if (seatPreference.isDisplayed()) {
                seatPreference.click();
                seatPreference.click();
                return true;
            }
        }
        catch (NoSuchElementException e) {
            // Nothing to do if not found.
        }
        return false;
    }

    public Boolean verifySeatPreferenceText() {
        try {
            if (seatPreference.isDisplayed()) {
                seatPreference.click();
            }

            if (seatPreferenceText.isDisplayed()) {
                if (seatPreferenceText.getText().contains(
                    "Seat can be selected by calling 1-800-HOTWIRE after purchase")) {
                    return true;
                }
            }
        }
        catch (NoSuchElementException e) {
            // Nothing to do if not found.
        }
        return false;
    }

    public void checkAgeCheckbox() {
        if (!this.age.isSelected()) {
            this.age.click();
        }
    }

    public void selectDateOfBirthDay(String day) {
        selectByText(this.dobDay, day);
    }

    public void selectDateOfBirthMonth(String month) {
        selectByText(this.dobMonth, month);
    }

    public void selectDateOfBirthYear(String year) {
        selectByText(this.dobYear, year);
    }

    public void selectGender(String gender) {
        if (gender.equals("male")) {
            this.genderM.click();

        }
        else {
            this.genderF.click();
        }
    }
}

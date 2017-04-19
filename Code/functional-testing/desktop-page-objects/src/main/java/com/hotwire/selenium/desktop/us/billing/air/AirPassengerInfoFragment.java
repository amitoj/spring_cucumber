/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.air;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class AirPassengerInfoFragment extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirPassengerInfoFragment.class);

    private static final String CSS_FIRST_NAME = "input[name^='travelerForm.psgrFirstName']";
    private static final String CSS_LAST_NAME = "input[name^='travelerForm.psgrLastName']";
    private static final String CSS_MIDDLE_NAME = "input[name^='travelerForm.psgrMiddleName']";

    private static final String CSS_EXISTS_TRAVELER = "select[id^='participantTravelerIndexes']";

    private static final String CSS_DAY_OF_BIRTH = "select[name^='travelerForm.psgrDobDay']";
    private static final String CSS_MONTH_OF_BIRTH = "select[name^='travelerForm.psgrDobMonth']";
    private static final String CSS_YEAR_OF_BIRTH = "select[name^='travelerForm.psgrDobYear']";

    private static final String CSS_GENDER_MALE = "input#male";
    private static final String CSS_GENDER_FEMALE = "input#female";

    private static final String XPATH_NEW_PASSENGER =
            "//a[@class='postInputText' and contains(., 'Add traveler')]";

    private static final String NAME_FIRST_NAME = "//INPUT[@name='addTravelerNameForm.firstName']";
    private static final String NAME_LAST_NAME = "//INPUT[@name='addTravelerNameForm.lastName']";
    private static final String NAME_MIDDLE_NAME = "//INPUT[@name='addTravelerNameForm.middleName']";
    private static final String NAME_ADD_TRAVELER_BTN = "//INPUT[@name='btnAddTraveler']";

    @FindBy(id = "ageConfirmationCheckbox")
    private WebElement age;

    public AirPassengerInfoFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector("#travelers"));
    }

    private void selectByText(WebElement element, String value) {
        Select clickThis = new Select(element);
        clickThis.selectByVisibleText(value);
    }

    private WebElement getPassengerFirstNameByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_FIRST_NAME, index);
    }

    private WebElement getPassengerLastNameByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_LAST_NAME, index);
    }

    private WebElement getPassengerMiddleNameByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_MIDDLE_NAME, index);
    }

    private WebElement selectExistsPassengerByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_EXISTS_TRAVELER, index);
    }

    private WebElement selectDayOfBirthByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_DAY_OF_BIRTH, index);
    }

    private WebElement selectMonthOfBirthByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_MONTH_OF_BIRTH, index);
    }

    private WebElement selectYearOfBirthByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_YEAR_OF_BIRTH, index);
    }

    private WebElement selectFemaleByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_GENDER_FEMALE, index);
    }

    private WebElement selectMaleByIndex(int index) {
        return getPassengerInfoByCssSelector(CSS_GENDER_MALE, index);
    }

    private WebElement getPassengerInfoByCssSelector(String cssSelector, int index) {
        List<WebElement> elements = getWebDriver().findElements(By.cssSelector(cssSelector));
        if (!elements.isEmpty() && elements.size() > index) {
            return elements.get(index);
        }
        return null;
    }

    private void createNewPassenger(String firstName, String lastName, String middleName, int index) {
        String suffix = lastName;
        if (middleName != null && !middleName.isEmpty()) {
            suffix = middleName + " " + lastName;
        }
        LOGGER.info("Creating a new traveler {} {}", firstName, suffix);

        getWebDriver().findElements(By.xpath(XPATH_NEW_PASSENGER)).get(index).click();

        sendKeys(getWebDriver().findElement(By.xpath(NAME_FIRST_NAME)), firstName);
        sendKeys(getWebDriver().findElement(By.xpath(NAME_LAST_NAME)), lastName);
        sendKeys(getWebDriver().findElement(By.xpath(NAME_MIDDLE_NAME)), middleName);

        getWebDriver().findElement(By.xpath(NAME_ADD_TRAVELER_BTN)).click();
    }

    private String getPassengerFullNameAsString(String firstName, String middleName, String lastName) {
        StringBuilder sb = new StringBuilder(firstName);
        if (middleName != null || !"".equals(middleName)) {
            sb.append(" ").append(middleName);
        }
        sb.append(" ").append(lastName);
        return sb.toString();
    }

    public AirPassengerInfoFragment fillPassengerName(int i, String firstName, String middleName, String lastName) {

        String passengerName = getPassengerFullNameAsString(firstName, middleName, lastName);
        LOGGER.info("Trying to fill passenger information about {}", passengerName);

        WebElement existsTravelerDropdown = selectExistsPassengerByIndex(i);
        WebElement travelerFirstNameInput = getPassengerFirstNameByIndex(i);

        if (existsTravelerDropdown != null) {
            LOGGER.info("Search for traveler from dropdown list of exists persons..");
            Select passNameList = new Select(existsTravelerDropdown);
            List<WebElement> passNameListElements = passNameList.getAllSelectedOptions();

            if (passNameListElements.contains(passengerName)) {
                LOGGER.info("Traveler is found and selected..");
                selectByText(existsTravelerDropdown, passengerName);
                return this;
            }
            else {
                LOGGER.info("Traveler is not found..");
                createNewPassenger(firstName, lastName, middleName, i);
                return this;
            }
        }
        else if (travelerFirstNameInput != null) {
            LOGGER.info("Fill in traveler first, last and middle names into fields..");
            sendKeys(travelerFirstNameInput, firstName);
            sendKeys(getPassengerLastNameByIndex(i), lastName);
            sendKeys(getPassengerMiddleNameByIndex(i), middleName);
        }
        else {
            throw new RuntimeException("Can't fill the first, last or middle names of traveler..");
        }
        return this;
    }

    public AirPassengerInfoFragment fillPassengerBirthdayDate(int i,
                                                             String monthBirthdayDate,
                                                             String dayBirthdayDate,
                                                             String yearBirthdayDate) {
        List<WebElement> passengersAnotherDataFragment;
        passengersAnotherDataFragment = getWebDriver()
                .findElements(By.cssSelector(".BillingPanelSecureFlightsInfoComp"));
        // set birth date
        //Month
        if (!"".equals(monthBirthdayDate)) {
            selectByText(
                    passengersAnotherDataFragment
                            .get(i)
                            .findElement(By.cssSelector("select[id^=\"travelerForm.psgrDobMonth\"]")),
                    monthBirthdayDate);
        }
        else {
            LOGGER.info("Don't set Month of birthday date");
        }
        //Day
        if (!"".equals(dayBirthdayDate)) {
            selectByText(
                    passengersAnotherDataFragment
                            .get(i)
                            .findElement(By.cssSelector("select[id^=\"travelerForm.psgrDobDay\"]")),
                    dayBirthdayDate);
        }
        else {
            LOGGER.info("Don't set Day of birthday date");
        }
        //Year
        if (!"".equals(yearBirthdayDate)) {
            selectByText(
                    passengersAnotherDataFragment
                            .get(i)
                            .findElement(By.cssSelector("select[id^=\"travelerForm.psgrDobYear\"]")),
                    yearBirthdayDate);
        }
        else {
            LOGGER.info("Don't set Year of birthday date");
        }
        return this;
    }

    public AirPassengerInfoFragment fillPassengerGender(int i, String gender) {
        List<WebElement> passengersAnotherDataFragment;
        passengersAnotherDataFragment = getWebDriver()
                .findElements(By.cssSelector(".BillingPanelSecureFlightsInfoComp"));
        if (!"".equals(gender)) {
            if ("male".equalsIgnoreCase(gender)) {
                passengersAnotherDataFragment.get(i).findElement(By.cssSelector("#male")).click();
            }
            else if ("female".equalsIgnoreCase(gender)) {
                passengersAnotherDataFragment.get(i).findElement(By.cssSelector("#female")).click();
            }
            else {
                throw new IllegalArgumentException("Gender should be 'male' or 'female'!");
            }
        }
        else {
            LOGGER.info("Don't set Gender!");
        }
        return this;
    }

    public AirPassengerInfoFragment fillPassengerFFProgram(int i, String fFProgramName, String fFProgramNumber) {
        List<WebElement> passengersAnotherDataFragment;
        passengersAnotherDataFragment = getWebDriver()
                .findElements(By.cssSelector(".BillingPanelSecureFlightsInfoComp"));
        //Frequency program
        selectByText(
                passengersAnotherDataFragment
                        .get(i)
                        .findElement(By.cssSelector("select[name^=\"travelerForm.psgrFFProg\"]")),
                fFProgramName);
        passengersAnotherDataFragment
                .get(i)
                .findElement(By.cssSelector("input[name^=\"travelerForm.psgrFFAcct\"]"))
                .sendKeys(fFProgramNumber);

        return this;
    }

    public AirPassengerInfoFragment fillPassengerRedressNumber(int i, String redressNumber) {
        List<WebElement> passengersAnotherDataFragment;
        passengersAnotherDataFragment = getWebDriver()
                .findElements(By.cssSelector(".BillingPanelSecureFlightsInfoComp"));
        //Redress number
        if ("".equalsIgnoreCase(redressNumber)) {
            passengersAnotherDataFragment
                    .get(i)
                    .findElement(By.cssSelector("div[id^=\"icon\"]"))
                    .click();
            passengersAnotherDataFragment
                    .get(i)
                    .findElement(By.cssSelector("input[name^=\"travelerForm.psgrRedressNumber\"]"))
                    .sendKeys(redressNumber);
        }
        return this;
    }

    public AirPassengerInfoFragment checkAgeCheckbox() {
        if (!this.age.isSelected()) {
            this.age.click();
        }
        return this;
    }

    public AirPassengerInfoFragment fillTravelerContactInfo() {

        return this;
    }
}

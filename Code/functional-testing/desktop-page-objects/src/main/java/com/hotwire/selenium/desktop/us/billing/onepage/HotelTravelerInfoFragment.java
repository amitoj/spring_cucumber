/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.onepage;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Page object representing the traveler info fields on the one page billing
 * page.
 *
 * @author aguthrie
 */
public class HotelTravelerInfoFragment extends AbstractUSPage {

    private static final String TRAVELER_NAME_SELECT = "select[id='guest']";
    private static final String FIRST_NAME = "travelerInfoModel.travelers[0].firstName";
    private static final String LAST_NAME = "travelerInfoModel.travelers[0].lastName";
    private static final String ACCESSIBILITY_LIST = ".accessibilityList";
    private static final String ACCESSIBILITY_LIST_ITEMS = ACCESSIBILITY_LIST + " ul li";
    private static final String ACCESSIBILITY_INPUTS = ACCESSIBILITY_LIST_ITEMS + " input[type='checkbox']";
    private static final String ACCESSIBILITY_TEXTS = ACCESSIBILITY_LIST_ITEMS + " label";
    private static final String SIGN_IN_MODULE = ".billingSignInLayer";
    private static final String SIGN_IN_EMAIL = SIGN_IN_MODULE + " input[id='email']";
    private static final String SIGN_IN_PASSWORD = SIGN_IN_MODULE + " input[id='password']";
    private static final String SIGN_IN_SUBMIT = SIGN_IN_MODULE + " .buttons .btn";
    private static final String PASSWORDASSISTANCE = "#billingSignupRefreshable>form>a";
    private static final int DEFAULT_WAIT = 15;

    @FindBy(css = TRAVELER_NAME_SELECT)
    private WebElement travelerSavedNameSelect;

    @FindBy(name = FIRST_NAME)
    private WebElement travelerFirstName;

    @FindBy(name = LAST_NAME)
    private WebElement travelerLastName;

    @FindBy(name = "travelerInfoModel.emailAddress")
    private WebElement travelerEmailId;

    @FindBy(name = "travelerInfoModel.confirmEmailAddress")
    private WebElement travelerConfirmEmailId;

    @FindBy(name = "travelerInfoModel.phoneNumber")
    private WebElement travelerPrimaryPhoneNumber;

    @FindBy(css = ".accessibilityLink")
    private WebElement accessibilityLink;

    @FindBy(css = ACCESSIBILITY_INPUTS)
    private List<WebElement> accessibilityCheckboxes;

    @FindBy(css = ACCESSIBILITY_TEXTS)
    private List<WebElement> accessibilityCheckboxesTexts;

    @FindBy(css = ".signInLink a")
    private WebElement signInLink;

    public HotelTravelerInfoFragment(WebDriver webDriver) {
        super(webDriver, By.className("travelerInfoModule"));
    }

    public void selectAddNewTravelerFromSelection() {
        new Select(travelerSavedNameSelect).selectByValue("-1");
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.name(FIRST_NAME), true));
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.name(LAST_NAME), true));
    }

    public boolean isSavedTravelerNameSelectDisplayed() {
        try {
            return travelerSavedNameSelect.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean areTravelerNameInputsDisplayed() {
        try {
            return travelerFirstName.isDisplayed() && travelerLastName.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public HotelTravelerInfoFragment withFirstName(String firstName) {
        if (!getGlobalHeader().isLoggedIn()) {
            this.travelerFirstName.clear();
            this.travelerFirstName.sendKeys(firstName);
        }
        else {
            if (areTravelerNameInputsDisplayed()) {
                this.travelerFirstName.clear();
                this.travelerFirstName.sendKeys(firstName);
            }
        }
        return this;
    }

    public HotelTravelerInfoFragment withMiddleName(String middleName) {
        return this;
    }

    public HotelTravelerInfoFragment withLastName(String lastName) {
        if (!getGlobalHeader().isLoggedIn()) {
            this.travelerLastName.clear();
            this.travelerLastName.sendKeys(lastName);
        }
        else {
            if (areTravelerNameInputsDisplayed()) {
                this.travelerLastName.clear();
                this.travelerLastName.sendKeys(lastName);
            }
        }
        return this;
    }

    public HotelTravelerInfoFragment withPrimaryPhoneNumber(String primaryPhoneNumber) {
        if (!getGlobalHeader().isLoggedIn()) {
            this.travelerPrimaryPhoneNumber.clear();
            this.travelerPrimaryPhoneNumber.sendKeys(primaryPhoneNumber);
        }
        else {
            // This shouldn't happen, but it has. Not continuously, but enough to go "WTF?". Keep eye on this.
            if (StringUtils.isEmpty(this.travelerPrimaryPhoneNumber.getAttribute("value"))) {
                logger.info("Saved account phone number is empty. Need to investigate. Fill with dummy number.");
                this.travelerPrimaryPhoneNumber.sendKeys("1234567890");
            }
        }
        return this;
    }

    public HotelTravelerInfoFragment withEmailId(String emailId) {
        if (!getGlobalHeader().isLoggedIn()) {
            this.travelerEmailId.clear();
            this.travelerEmailId.sendKeys(emailId);
            this.travelerConfirmEmailId.clear();
            this.travelerConfirmEmailId.sendKeys(emailId);
        }
        return this;
    }

    public void continuePanel() {

    }

    public boolean isAccessibilityListDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(ACCESSIBILITY_LIST + " ul")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickAccessibilityLink() {
        accessibilityLink.click();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(ACCESSIBILITY_LIST + " ul")));
    }

    public List<String> getAccessibilityTexts() {
        List<String> texts = new ArrayList<String>();
        for (WebElement element : accessibilityCheckboxesTexts) {
            texts.add(element.getText());
        }
        return texts;
    }

    public List<WebElement> getAccessibilityCheckboxElements() {
        return accessibilityCheckboxes;
    }

    public String getEmail() {
        return travelerEmailId.getAttribute("value");
    }

    public String getConfirmationEmail() {
        return travelerConfirmEmailId.getAttribute("value");
    }

    public void clickSignInLink() {
        signInLink.click();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT).until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SIGN_IN_MODULE)));
    }

    public String getEmailFromSignInLinkModule() {
        new AbstractUSPage(getWebDriver(), By.cssSelector("div[id='billingSignInLayer']"));
        return getWebDriver().findElement(By.cssSelector("div[id='billingSignInLayer'] input[id='email']"))
                .getAttribute("value");
    }

    public HotelBillingOnePage signInAsUser(String user, String password) {
        clickSignInLink();
        WebElement email = getWebDriver().findElement(By.cssSelector(SIGN_IN_EMAIL));
        email.clear();
        email.sendKeys(user);
        WebElement passwd = getWebDriver().findElement(By.cssSelector(SIGN_IN_PASSWORD));
        passwd.clear();
        passwd.sendKeys(password);
        getWebDriver().findElement(By.cssSelector(SIGN_IN_SUBMIT)).click();
        return new HotelBillingOnePage(getWebDriver());
    }

    public void goToPasswordAssistance() {
        getWebDriver().findElement(By.cssSelector(PASSWORDASSISTANCE)).click();
    }
}

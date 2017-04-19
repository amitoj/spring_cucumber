/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.billing.car.impl.fragments.CarBillingLoginFormFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.onepage.OpChangePrimaryDriverForm;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: v-vzyryanov
 * Date: 1/28/13
 * Time: 2:44 AM
 */
public class CarTravelerInfoFragment extends AbstractPageObject implements CarTravelerInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarTravelerInfoFragment.class);
//    private static final String LOGIN_FORM = "div#loginForm #detailsLoginForm";

//    protected String travelerFirstName = "input[name='billingForm\\.travelerForm\\.driverFirstName']";
//    protected String travelerLastName = "input[name='billingForm\\.travelerForm\\.driverLastName']";
//    protected String ageConfirmation = "input[name='travelerForm.isDriverOldEnough'], input[id='is25User']";
//    protected String cardAcceptance = ".depositTerms .checkbox";
//    protected String travelerPhoneNumber = "input[name='billingForm\\.travelerForm\\.phoneNo']";
//    protected String savedTravelerPhoneNumber = "input[id*='phoneNo']";
    protected String emailChangeLink = "#driverEmail>div>a";
    //    protected String travelerEmail = "input[name='billingForm\\.travelerForm\\._NAE_email']";
    protected String travelerEmailPopUpForm = "billingForm.loginChangeForm._NAE_newEmail";
    protected String travelerEmailConfirmPopUpForm = "billingForm.loginChangeForm._NAE_confirmEmail";
    protected String saveEmailButtonPopUpForm = "#loginChangePopupLayerComp-bd .btn";
//    protected String travelerEmailConfirm = "input[name='billingForm\\.travelerForm\\._NAE_confirmEmail']";
//    protected String driverNameChangeLink = "a#addTravelerNameLink";
    protected String creditCardDepositAgreement = "Yes. I understand rental agency only accepts credit card deposits." +
                                                  " Debit/check card deposits are not accepted.";
    protected String creditDebitCardWithTicketAgreement = "Yes. I understand the rental agency will require proof of " +
             "a round-trip travel ticket for debit/check card deposits. Credit card deposits are also accepted.";
    protected String xpathDepositAgreementContainer = "Implement Me!";
    protected String xpathDepositAgreementLabel = "//div[@class='depositTerms ']//label";
//    protected String idDepositAgreementCheckbox = "acceptsCreditCardOnlyOrDebitLocalTermsCheckbox";
    protected String xpathDepositAgreementValidationLabel = "Implement Me!";

    protected String depositAgreementValidationMessage = "Please confirm your age and deposit terms";

    /**
     * Login form on billing page
     */
//    protected String loginUserEmail = LOGIN_FORM + " ";
//    protected String loginUserPassword = LOGIN_FORM + " input[name='billingForm.loginForm._NAE_password']";
//    protected String loginSignIn = LOGIN_FORM + " ";

    protected String cssOptionalSignInLink = ".loginPopupLink a";
    protected String cssPasswordAssistance = "#detailsLoginForm a";
    protected String cssOptionalSignInForm = "#detailsLoginForm";
    protected String nameOtionalSignInEmail = "billingForm.loginForm._NAE_email";
    protected String nameOtionalSignInPassword = "billingForm.loginForm._NAE_password";
    protected String cssOptionalSignInBtn = "#detailsLoginForm button";
//    protected String nameSubscriberCheckBox = "Implement Me!";
//    protected String subscriberCheckbox = "billingForm.travelerForm.wantsNewsLetter";

//    it's dynamical object and should be defined in code
//    @FindBy(id = "billingForm.travelerForm.participantTravelerIndexes")
//    private WebElement participantTravelerIndexes;

    @FindBy(id = "billingForm.travelerForm.driverFirstName")
    private WebElement driverFirstName;

    @FindBy(id = "billingForm.travelerForm.driverLastName")
    private WebElement driverLastName;

    @FindBy(css = "a#addTravelerNameLink")
    private WebElement driverNameChangeLink;

    @FindBy(id = "billingForm.travelerForm._NAE_email")
    private WebElement travelerEmail;

    @FindBy(id = "billingForm.travelerForm._NAE_confirmEmail")
    private WebElement travelerEmailConfirmation;

    @FindBy(id = "billingForm.travelerForm.phoneNo")
    private WebElement travelerPhoneNumber;

    @FindBy(name = "billingForm.travelerForm.wantsNewsLetter")
    private WebElement subscriberCheckbox;

    @FindBy(name = "billingForm.travelerForm.isDriverOldEnough")
    private WebElement ageConfirmation;

    @FindBy(id = "acceptsCreditCardOnlyOrDebitLocalTermsCheckbox")
    private WebElement cardAcceptance;

    @FindBy(css = "div#driverEmail strong")
    private WebElement savedTravelerEmail;

    public CarTravelerInfoFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(".driverInfoWrapper .infoDetails"));
    }

    public boolean isTravelerConfirmationEmailDisplayed() {
        try {
            return travelerEmailConfirmation.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Login form
     */
    @Override
    public void login(String username, String password) {
        openOptionalSignIn();
        new CarBillingLoginFormFragment(getWebDriver()).login(username, password);
    }

    public boolean isLoggedUserEmailPresent() {
        try {
            return savedTravelerEmail.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public CarTravelerInfo travelerPhoneNumber(String phone) {
        try {
            sendKeys(travelerPhoneNumber, phone);
            // temporary solution. Need to have logic in the purchase step to handle if there's a country code
            // element.
            if (getWebDriver().findElements(By.cssSelector("span.countryCode input")).size() > 0 &&
                    getWebDriver().findElement(By.cssSelector("span.countryCode input")).isDisplayed()) {
                getWebDriver().findElement(By.cssSelector("span.countryCode input")).sendKeys("1");
            }
        }
        catch (WebDriverException e) {
            LOGGER.info(e.toString());
        }
        return this;
    }

    @Override
    public CarTravelerInfo travelerInfo(String firstName, String lastName) {

        try {
            if (driverFirstName.isDisplayed()) {
                sendKeys(driverFirstName, firstName);
                sendKeys(driverLastName, lastName);
            }
        }
        catch (NoSuchElementException e) {
            //No op
        }
        return this;
    }

    @Override
    public CarTravelerInfo travelerEmail(String email) {
        if (!getGlobalHeader().isLoggedIn() && !isLoggedUserEmailPresent()) {
            travelerEmail(email, email);
        }
        return this;
    }

    @Override
    public CarTravelerInfo travelerEmail(String email, String confEmail) {
        if (!getGlobalHeader().isLoggedIn()) {
            sendKeys(travelerEmail, email);
            sendKeys(travelerEmailConfirmation, confEmail);
        }
        return this;
    }

    @Override
    public CarTravelerInfo travelerEmailForLoggedUser(String email, String confEmail) {
        getEmailChangeLink().click();
        sendKeys(getTravelerEmailPopUpFrom(), email);
        sendKeys(getTravelerConfirmEmailPopUpForm(), confEmail);
        getSaveEmailButton().click();
        return new CarTravelerInfoFragment(getWebDriver());
    }

    @Override
    public CarTravelerInfo creditCardAcceptance(boolean accepted) {

        WebElement cb = getCardAcceptance();

        if (cb != null && cb.isDisplayed()) {
            if (accepted && !cb.isSelected()) {
                cb.click();
            }

            if (!accepted && cb.isSelected()) {
                cb.click();
            }
        }
        return this;
    }

    private WebElement getCardAcceptance() {
        try {
            cardAcceptance.isDisplayed();
            return cardAcceptance;
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public CarTravelerInfo ageConfirmation(boolean confirm) {
        if (confirm && !ageConfirmation.isSelected()) {
            ageConfirmation.click();
        }
        if (!confirm && ageConfirmation.isSelected()) {
            ageConfirmation.click();
        }
        return this;
    }

    @Override
    public DepositCardAgreementType getDepositCardAgreementType() {
        try {
            String depositAgreement = getWebDriver().findElement(By.xpath(xpathDepositAgreementLabel)).getText();
            LOGGER.info("Deposit agreement near checkbox in driver's info:\n" + depositAgreement.toUpperCase());

            if (creditCardDepositAgreement.equals(depositAgreement)) {
                return DepositCardAgreementType.CREDIT_CARD_ONLY;
            }

            if (creditDebitCardWithTicketAgreement.equals(depositAgreement)) {
                return DepositCardAgreementType.CREDIT_DEBIT_CARD_WITH_TICKET;
            }

        }
        catch (NoSuchElementException ex) {
            LOGGER.info("Deposit agreement checkbox isn't displayed..");
            return DepositCardAgreementType.NONE;
        }
        throw new RuntimeException("Deposit agreement appears but text isn't correct..");
    }

    @Override
    public boolean isDepositAgreementCheckboxDisplayed() {
        try {
            return cardAcceptance.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean isDepositAgreementCheckboxSelected() {
        return cardAcceptance.isSelected();
    }

    @Override
    public void switchDepositAgreementState(boolean state) {
        if ((state && !cardAcceptance.isSelected()) || (!state && cardAcceptance.isSelected())) {
            cardAcceptance.click();
        }
    }

    @Override
    public boolean verifyDepositCardAgreementBoxValidation() {

        return getWebDriver().findElement(By.xpath(xpathDepositAgreementValidationLabel))
                .getText().contains(depositAgreementValidationMessage) &&
                getWebDriver().findElement(By.xpath(xpathDepositAgreementContainer))
                        .getAttribute("class").contains("boxError");
    }

    @Override
    public CarTravelerInfo continueGuest() {
        //No-op
        return new CarTravelerInfoFragment(getWebDriver());
    }

    @Override
    public CarTravelerInfo continuePanel() {
        //No op
        return new CarTravelerInfoFragment(getWebDriver());
    }

    @Override
    public boolean isSavedDriverNameExists() {
        try {
            return getWebDriver().
                    findElement(By.cssSelector(
                            "div#primaryDriverList " +
                                    "select")).
                    isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean isOptionalSignInLinkDisplayed() {
        try {
            return getOptionalSignInLink().isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean isSubscriberCheckBoxDisplayed() {
        try {
            return subscriberCheckbox.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean isSubscriberCheckBoxSelected() {
        return subscriberCheckbox.isSelected();
    }

    @Override
    public void openOptionalSignIn() {
        getOptionalSignInLink().click();
    }

    private WebElement getOptionalSignInLink() {
        return getWebDriver().findElement(By.cssSelector(cssOptionalSignInLink));
    }

    @Override
    public void goToPasswordAssistance() {
        getWebDriver().findElement(By.cssSelector(cssPasswordAssistance)).click();
    }

    public WebElement getOptionalSignInEmail() {
        return getOptionalSignInForm().findElement(By.name(nameOtionalSignInEmail));
    }

    public WebElement getOptionalSignInPassword() {
        return getOptionalSignInForm().findElement(By.name(nameOtionalSignInPassword));
    }

    private WebElement getOptionalSignInForm() {
        return getWebDriver().findElement(By.cssSelector(cssOptionalSignInForm));
    }

    @Override
    public void submitOptionalSignInForm() {
        getOptionalSignInForm().findElement(By.cssSelector(cssOptionalSignInBtn)).click();
    }

    @Override
    public void writeToOptionalSignInEmail(String email) {
        sendKeys(getOptionalSignInEmail(), email);
    }

    @Override
    public void writeToOptionalSignInPassword(String password) {
        sendKeys(getOptionalSignInPassword(), password);
    }

    @Override
    public void enterNewPrimaryDriverInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseExistingDriver() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public String getSelectedPrimaryDriver() {
        Select select = new Select(getWebDriver()
                                .findElement(By.id("billingForm.travelerForm.participantTravelerIndexes")));
        return select.getFirstSelectedOption().getText();
    }

    @Override
    public String getSavedTravelerPrimaryPhoneNumber() {
        return travelerPhoneNumber.getAttribute("value");
    }

    @Override
    public String getSavedTravelerEmail() {
        return savedTravelerEmail.getText();
    }

    @Override
    public void changeDriverInformation(String firstName, String lastName) {
        driverNameChangeLink.click();
        new OpChangePrimaryDriverForm(getWebDriver()).changePrimaryDriver(firstName, lastName);
    }

    protected GlobalHeader getGlobalHeader() {
        return new GlobalHeader(getWebDriver());
    }

    public WebElement getEmailChangeLink() {
        return getWebDriver().findElement(By.cssSelector(emailChangeLink));
    }

    public WebElement getTravelerEmailPopUpFrom() {
        return getWebDriver().findElement(By.id(travelerEmailPopUpForm));
    }

    public WebElement getSaveEmailButton() {
        return getWebDriver().findElement(By.cssSelector(saveEmailButtonPopUpForm));
    }

    public WebElement getTravelerConfirmEmailPopUpForm() {
        return getWebDriver().findElement(By.id(travelerEmailConfirmPopUpForm));
    }
}

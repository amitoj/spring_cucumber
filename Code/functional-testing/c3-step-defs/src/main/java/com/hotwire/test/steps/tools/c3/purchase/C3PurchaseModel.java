/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.purchase;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.tools.c3.purchase.C3FareFinder;
import com.hotwire.selenium.tools.c3.purchase.C3InterstitialPage;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.InsuranceDao;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Definition of purchase model for C3
 */
public abstract class C3PurchaseModel extends ToolsAbstractModel {

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    abstract void fillAllBillingInfo();

    abstract void clickBookButton();

    abstract void receiveConfirmation();

    abstract void proceedDetailsPage();

    abstract void processBillingPage();

    abstract void saveReferenceNumberFromDetailsPage();

    abstract void verifySearchParametersOnResultsPage();

    abstract void verifySearchParametersOnDetailsPage();

    abstract void verifyRefundRebookMsg();

    abstract void verifyAreaMapOnResults();

    abstract void verifyPrefillingOnBillingPage();

    abstract void fillSearchParametersAndSubmit();

    abstract void switchToRetail();

    abstract void switchToOpaque();

    abstract void verifyResultsPage();

    abstract void verifyValidationMsgOnBilling(String msg);

    abstract void checkStaticInsurance(boolean availability);

    abstract void verifyStaticInsurance();

    abstract void verifyDetailsPage();

    public abstract void waitForResults();

    public abstract String getReferenceNumberFromResultsPage();

    abstract void verifyLocationOnConfirmationPage(String location);

    public void selectFirstResult() {
        hotwireProduct.getResultsPage(getWebdriverInstance()).selectFirstResult();
    }

    public String getTypeOfFirstResult() {
        return hotwireProduct.getResultsPage(getWebdriverInstance()).getTypeOfFirstResult();
    }

    public void processBillingPageWithCarAddOn() {
        new C3AirPurchaseModel().processBillingPageWithCarAddOn();
    }

    void fillAllBillingInfoWithCarAddOn() {
        new C3AirPurchaseModel().fillAllBillingInfoWithCarAddOn();
    }

    public void checkConfirmationContactPhones(String phone1, String phone2) {
        By byPhone1 = By.xpath(".//li[contains(text(), '" + phone1 + "')]");
        By byPhone2 = By.xpath(".//li[contains(text(), '" + phone2 + "')]");

        new WebDriverWait(getWebdriverInstance(), 5).until(ExpectedConditions.visibilityOfElementLocated(byPhone1));

        assertThat(getWebdriverInstance().findElement(byPhone1).isDisplayed()).
                as("Phone number isn't present").
                isTrue();
        assertThat(getWebdriverInstance().findElement(byPhone2).isDisplayed()).
                as("Phone number isn't present").
                isTrue();
    }

    public void checkResultContactPhone(String phone) {
        By phoneLocator = By.xpath(".//div[contains(text(), '" + phone + "')]");
        new WebDriverWait(getWebdriverInstance(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(phoneLocator));

        boolean isPhoneDisplayed = getWebdriverInstance().findElement(phoneLocator).isDisplayed();
        assertThat(isPhoneDisplayed).as("Phone number isn't present").isTrue();
    }

    public void checkDetailsContactPhone(String phone) {
        checkResultContactPhone(phone);
    }

    public void checkBillingContactPhone(String phone) {
        checkResultContactPhone(phone);
    }

    public void processAlert() {
        try {
            acceptAlert();
        }
        catch (TimeoutException | NoSuchElementException e) {
            logSession("Alert processing failed");
        }
    }

    public void processSearchFrame() {
        if (getWebdriverInstance().getCurrentUrl().contains("/ccc/")) {
//            switchToDefaultContent();
            switchToFrame("iframeSearch");
        }
    }

    public void saveReferenceNumberFromResultsPage() {
        c3ItineraryInfo.setReferenceNumberResults(getReferenceNumberFromResultsPage());
    }

    public void verifyInsuranceInDatabase() {
        Map insurance = new InsuranceDao(getDataBaseConnection())
                .getInsuranceNumber(c3ItineraryInfo.getItineraryNumber());
        assertThat(insurance.get("FIRST_NAME").toString()).isNotEmpty();
        assertThat(insurance.get("LAST_NAME").toString()).isNotEmpty();
        assertThat(insurance.get("TP_INSURANCE_POLICY_NUMBER").toString()).isNotEmpty();
    }

    public void verifyFareFinder() {
        new C3FareFinder(getWebdriverInstance());
    }

    void addRentalCarOnDetail() {
        new C3AirPurchaseModel().addRentalCarOnDetail();
    }

    public void checkCarAddonOnConfirmation() throws Exception {

        By tryAgain = By.xpath(".//div[@class='carAddOnConfirmation']//strong[contains(text(), 'Unfortunately')]");
        try {
            new WebDriverWait(getWebdriverInstance(), 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(tryAgain));
            getWebdriverInstance().findElement(tryAgain).isDisplayed();
            throw new Exception("Car addon complete with issue. try again");
        }
        catch (TimeoutException e) {
            logItinerary();
        }
    }

    public void verifySignInModule(String option) {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        if ((null == option) || "".equals(option)) {
            assertThat(globalHeader.doesExistSignInDialogOpener())
                    .as("Sign in module is exists on the page")
                    .isTrue();
        }
        else if ("don't".equals(option.trim())) {
            assertThat(globalHeader.doesExistSignInDialogOpener())
                    .as("Sign in module is exists on the page")
                    .isFalse();
        }
        else {
            throw new UnimplementedTestException("Optional parameter wasn't found");
        }
    }

    private C3InterstitialPage getInterstitialPage() {
        return hotwireProduct.getInterstitialPage(getWebdriverInstance());
    }

    public void proceedInterstitialPage() {
        C3InterstitialPage iterPage = getInterstitialPage();
        switch (customerInfo.getCustomerType()) {
            case GUEST:
                customerInfo.setCustomerRandomEmail();
                iterPage.continueAsGuestUser();
                break;
            case NON_EXPRESS:
            case EXPRESS:
            case EXPRESS_ELITE:
                iterPage.continueAsUser(customerInfo.getEmail());
                break;
            case NEW:
                customerInfo.setEmail("test" + System.currentTimeMillis() + "@hotwire.com");
                iterPage.continueAsNewUser(customerInfo.getEmail(), customerInfo.getFirstName(),
                        customerInfo.getLastName());
                break;
            default:
                throw new UnimplementedTestException("No such customer type");
        }
    }

    public void checkSearchResultOnInterstitial() {
        Assertions.assertThat(getInterstitialPage().checkCustomerSearchResultIsPresent()).
                as("Search result is not present").
                isTrue();
    }

    public void searchCustomerWithCriteria(String criteria, String value) {
        if (criteria.equals("phone number")) {
            getInterstitialPage().searchByPhoneNumber(value);
        }
        else if (criteria.equals("last name")) {
            getInterstitialPage().searchByLastName(value);
        }
    }

    abstract  void saveConfirmationPageInBean();

    public abstract void verifyConfirmationPageForHotDollarsPurchase();
}

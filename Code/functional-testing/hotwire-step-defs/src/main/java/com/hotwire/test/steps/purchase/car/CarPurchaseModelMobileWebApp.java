/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.car;


import com.hotwire.selenium.mobile.MobileCarSearchPage;
import com.hotwire.selenium.mobile.billing.MobileBillingSignInPage;
import com.hotwire.selenium.mobile.billing.MobileCarBillingPage;
import com.hotwire.selenium.mobile.billing.PaymentMethodFragmentGuest;
import com.hotwire.selenium.mobile.confirmation.MobileCarConfirmationPage;
import com.hotwire.selenium.mobile.details.car.MobileCarDetailsPageProvider;
import com.hotwire.selenium.mobile.details.car.impl.MobileOldCarDetailsPage;
import com.hotwire.selenium.mobile.models.CarSolutionModel;
import com.hotwire.selenium.mobile.results.car.MobileCarResultsPage;
import com.hotwire.selenium.mobile.view.ViewIds;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.util.webdriver.functions.PageName;
import cucumber.api.PendingException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.List;

import static com.hotwire.selenium.mobile.results.car.MobileCarResultsPageProvider.getMobileCarResultsPage;
import static org.fest.assertions.Assertions.assertThat;


/**
 * @author vjong
 */
public class CarPurchaseModelMobileWebApp extends CarPurchaseModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarPurchaseModelMobileWebApp.class.getSimpleName());
    private static final String CAR_ALREADY_PURCHASED_MESSAGING =
            "A car rental booking with the same trip details was recently " +
                    "booked through your Hotwire account. Please change primary driver or search dates.";

    @Resource(name = "canadianZipCode")
    private String canadianZipCode;

    @Autowired
    @Qualifier("newValidAuthenticationParameters")
    private AuthenticationParameters newValidAuthenticationParameters;

    private CarSolutionModel carModel;
    private MobileCarBillingPage mCarBillingPage;

    @Override
    public void selectPGood() {
        MobileCarResultsPage mCarResultsPage = getMobileCarResultsPage(getWebdriverInstance());
        carModel = mCarResultsPage.select(
                this.purchaseParameters.getResultNumberToSelect(),
                this.purchaseParameters.getOpaqueRetail()
        );
        if (this.purchaseParameters.getOpaqueRetail().equals("opaque")) {
            this.carModel.setOpaque(true);
        }
        else {
            this.carModel.setOpaque(false);
        }

        this.carModel.setStartDate(this.searchParameters.getStartDate());
        this.carModel.setEndDate(this.searchParameters.getEndDate());
        this.carModel.setLocation(this.searchParameters.getDestinationLocation());


        new MobileOldCarDetailsPage(getWebdriverInstance(), new String[]{
            ViewIds.TILE_CAR_DETAILS_OPAQUE,
            ViewIds.TILE_CAR_DETAILS_RETAIL});

    }

    @Override
    public void completePurchase(Boolean isUserSignedIn) {
        continueToBilling(isUserSignedIn);
        fillPaymentInfo();
        proceedWPayment();
    }


    @Override
    public void continueToBilling(Boolean isUserSignedIn) {
        UserInformation userInformation = this.purchaseParameters.getUserInformation();
        if (this.newValidAuthenticationParameters.isUserAuthenticated()) {
            fillUserInfo(userInformation);
        }
        else if (signIn()) {

            fillUserInfo(userInformation);
        }
        else {
            fillGuestInfo(userInformation);
        }

        fillInsuranceInfo(mCarBillingPage);
    }

    private boolean signIn() {
        MobileBillingSignInPage mSignInPage = new MobileBillingSignInPage(getWebdriverInstance());
        mCarBillingPage = mSignInPage.continueCarPurchaseAsUser(this.authenticationParameters.getUsername(),
                this.authenticationParameters.getPassword());
        try {
            mCarBillingPage
                    .fillGuestTravelerInfo();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    private void fillGuestInfo(UserInformation userInformation) {
        mCarBillingPage
                .fillGuestTravelerInfo()
                .withFirstName(userInformation.getFirstName())
                .withLastName(userInformation.getLastName())
                .withPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber())
                .withEmailId(userInformation.getEmailId())
                .continuePanel();
    }

    private void fillUserInfo(UserInformation userInformation) {
        mCarBillingPage = new MobileCarBillingPage(getWebdriverInstance());
        mCarBillingPage
                .fillUserTravelerInfo()
                .withFirstName(userInformation.getFirstName())
                .withLastName(userInformation.getLastName())
                .withPhone(userInformation.getPrimaryPhoneNumber())
                .withOption()
                .continuePanel();
    }


    @Override
    public void fillPaymentInfo() {
        UserInformation userInformation = this.purchaseParameters.getUserInformation();
        //If car is Retail without insurance, it should skip the payment info filling.
        if (!(this.purchaseParameters.getOpaqueRetail().equalsIgnoreCase("retail") &&
                !this.purchaseParameters.getOptForInsurance())) {
            fillPaymentInfo(userInformation, mCarBillingPage);
        }

    }

    /**
     * Thie method will either opt in or out of car insurance based on the user's choice and submit the form
     *
     * @param mCarBillingPage
     */
    private void fillInsuranceInfo(MobileCarBillingPage mCarBillingPage) {
        mCarBillingPage.selectInsuranceOption().continuePanel(this.purchaseParameters.getOptForInsurance());
    }

    /**
     * This method will fill in the user's credit card information on the page and submit the form
     *
     * @param userInformation
     * @param mCarBillingPage
     */
    private void fillPaymentInfo(UserInformation userInformation, MobileCarBillingPage mCarBillingPage) {
        String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
            userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode : userInformation.getZipCode();
        PaymentMethodFragmentGuest paymentFragment = mCarBillingPage.fillPaymentMethod();

        paymentFragment.
                setCcType("Visa").
                setCcNumber(userInformation.getCcNumber()).
                setCcExpMonth(userInformation.getCcExpMonth()).
                setCcExpYear(userInformation.getCcExpYear()).
                setSecurityCode(userInformation.getSecurityCode()).
                setBillingAddress(userInformation.getBillingAddress()).
                setFirstName(userInformation.getFirstName()).
                setLastName(userInformation.getFirstName()).
                setCountry(userInformation.getBillingCountry()).
                setCity(userInformation.getCity()).
                setState(userInformation.getState(), userInformation.getBillingCountry()).
                setZipCode(zipCode);
    }

    @Override
    public void savePaymentInfo() {
        PaymentMethodFragmentGuest paymentFragment = mCarBillingPage.fillPaymentMethod();
        paymentFragment.saveBillingInfo(this.purchaseParameters.getSavedCreditCard().getNames().get(0));
    }

    @Override
    public void proceedWPayment() {
        try {
            PaymentMethodFragmentGuest paymentFragment = mCarBillingPage.fillPaymentMethod();
            paymentFragment.continuePanel();
        }
        catch (RuntimeException e) {
            LOGGER.info("Performing book, payment info capture was not needed for this flow.");
        }
        mCarBillingPage.book();
    }

    @Override
    public void verifySavedPaymentInfo() {
        this.mCarBillingPage.verifySavedPaymentMethod(this.purchaseParameters.getSavedCreditCard().getNames());
    }

    @Override
    public void receiveConfirmation() {
        if (new PageName().apply(getWebdriverInstance()).contains("results")) {
            List<WebElement> priceChange = getWebdriverInstance().findElements(By.cssSelector(" .priceChangeMssg"));
            if (priceChange.size() > 0 || getWebdriverInstance().getCurrentUrl().contains("PgoodUnavailable=true")) {
                // Assume price change element in the results page is only used for inventory changes and other errors
                // in this page will bypass this conditional and fail on the confirmation page instantiation.
                throw new PendingException(
                        "Issue completing booking purchase confirmation. Details:\n" +
                                ((priceChange.size() > 0) ? priceChange.get(0).getText() : "PgoodUnavailable=true") +
                                "\nIf this is consistently happening, notify mobile or car rental devs to investigate" +
                                " inventory issues.");
            }
        }
        else if (new PageName().apply(getWebdriverInstance()).contains(ViewIds.TILE_CAR_LANDING)) {
            List<WebElement> recentlyBookedMessage = getWebdriverInstance()
                    .findElements(By.cssSelector(" .priceChangeMssg"));
            assertThat(recentlyBookedMessage.size() != 0)
                    .as("Expected error message div on Car Search Page, found none").isTrue();
            // verify that the particular pGood was already purchased
            assertThat(getWebdriverInstance().getCurrentUrl().contains("isPgoodPurchased=true"))
                    .as("Expected to have isPgoodPurchased=true present in the URL").isTrue();

            // verify the proper error messaging is shown on the car search page
            MobileCarSearchPage mobileCarSearchPage = new MobileCarSearchPage(getWebdriverInstance());
            mobileCarSearchPage.assertHasFormErrors(CAR_ALREADY_PURCHASED_MESSAGING, recentlyBookedMessage.get(0));
        }
        else {
            // Purchase was successful.
            MobileCarConfirmationPage page = new MobileCarConfirmationPage(getWebdriverInstance());
            LOGGER.info("Car Confirmation Number: " + page.getConfirmationNumber());
            LOGGER.info("Hotwire Itinerary: " + page.getHotwireItinerary());
        }
    }

    @Override
    public void continueToBillingFromDetails() {
        MobileCarDetailsPageProvider.get(getWebdriverInstance()).continueSubmit();
    }

    @Override
    public void processCarDetailsPage() {
        // no op.
    }

    @Override
    public void verifyCarDetailsPage() {

        MobileCarDetailsPageProvider.get(getWebdriverInstance()).verifyDetailsPageInfo(this.carModel);

    }
}















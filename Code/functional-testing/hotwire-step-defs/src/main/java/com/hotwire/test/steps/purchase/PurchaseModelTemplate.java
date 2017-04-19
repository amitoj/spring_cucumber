/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase;

import static org.fest.assertions.Assertions.assertThat;

import com.hotwire.selenium.desktop.us.billing.AdditionalFeaturesFragment;
import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarBillingPage;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.testing.UnimplementedTestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author v-mzabuga
 * @since 2012.07
 */
public class PurchaseModelTemplate extends WebdriverAwareModel implements PurchaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseModelTemplate.class.getName());
    protected SearchParameters searchParameters;
    protected PurchaseParameters purchaseParameters;
    protected AuthenticationParameters authenticationParameters;

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    public void setPurchaseParameters(PurchaseParameters purchaseParameters) {
        this.purchaseParameters = purchaseParameters;
    }

    public void setAuthenticationParameters(AuthenticationParameters authenticationParameters) {
        this.authenticationParameters = authenticationParameters;
    }

    @Override
    public void selectPGood() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void continueToBillingFromDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillTravelerInfo(UserInformation userInformation, boolean isSignedInUser) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillBillingInformation(UserInformation userInformation, boolean isSignedInUser) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillCreditCard() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void saveBillingInformation(String cardName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void continueOnBillingPageOnPaymentFragment() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void returnToDetailsFromBilling() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyBillingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void completePurchase(Boolean guestOrUser) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void receiveConfirmation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateCredentialInfoErrors(String credentialProfile) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void attemptBillingInformationCompletion(boolean isSignedInUser) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void attemptToSignIn(AuthenticationParameters parameters, boolean signIn) {
//        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void attemptToSignIn(String email, String password) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void clickOnAgreeAndBookButton(Boolean agreeWithTerms) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectPGood(boolean killPopup) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyBookingFailureMessage(String message) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void continueFromActiviesAndInsurancePanels() {
        AdditionalFeaturesFragment additionalFeatureFragment = new AdditionalFeaturesFragment(getWebdriverInstance());
        assertThat(additionalFeatureFragment.continueWithoutSelectingInsurance())
            .as("Header is incorrect")
            .isTrue();
    }

    @Override
    public void validateInsuranceErrorMessage() {
        AdditionalFeaturesFragment additionalFeaturesFragment = new AdditionalFeaturesFragment(getWebdriverInstance());
        assertThat(additionalFeaturesFragment.verifyErrorForInsurance())
            .as("No Error Message")
            .isTrue();
    }

    @Override
    public void selectInsuranceAddonOption(boolean choice) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateNewAgreeAndBookSection() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateSetOfCreditCardFields(String setOfFields) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateSaveCreditCardPresence(String savedCreditCardPresence) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPage(String page) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPaymentStatus(String condition) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectPaymentMethod() {
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        if (purchaseParameters.getPaymentMethodType() == PurchaseParameters.PaymentMethodType.PayPal) {
            LOGGER.info("PayPal payment option");
            carBillingPage.getPaymentMethodFragment().processPayPal();
        }
    }

    @Override
    public void processDetailsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void processBillingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDollarOneAuth() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyInsuranceCostOnConfirmation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void freeze(double seconds) {
        long mils = (long) seconds * 1000;
        try {
            Thread.sleep(mils);
        }
        catch (InterruptedException e) {
            LOGGER.info("Error while freezing WebDriver!");
        }
    }

    @Override
    public void saveBillingInformation(String cardName, String password) {
        throw new UnimplementedTestException("Implement me!");
    }
}






/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase;

import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.authentication.AuthenticationParameters;

/**
 * @author v-mzabuga
 * @since 2012.07
 */
public class DelegatingPurchaseModel extends PurchaseModelTemplate implements PurchaseModel {

    private PurchaseModel hotelPurchaseModel;
    private PurchaseModel carPurchaseModel;
    private PurchaseModel airPurchaseModel;

    public void setAirPurchaseModel(PurchaseModel airPurchaseModel) {
        this.airPurchaseModel = airPurchaseModel;
    }

    public void setCarPurchaseModel(PurchaseModel carPurchaseModel) {
        this.carPurchaseModel = carPurchaseModel;
    }

    public void setHotelPurchaseModel(PurchaseModel hotelPurchaseModel) {
        this.hotelPurchaseModel = hotelPurchaseModel;
    }

    @Override
    public void fillTravelerInfo(UserInformation userInformation, boolean isSignedInUser) {
        getPurchaseModelToDelegate().fillTravelerInfo(userInformation, isSignedInUser);
    }

    @Override
    public void fillBillingInformation(UserInformation userInformation, boolean isSignedInUser) {
        getPurchaseModelToDelegate().fillBillingInformation(userInformation, isSignedInUser);
    }

    @Override
    public void fillCreditCard() {
        getPurchaseModelToDelegate().fillCreditCard();
    }

    @Override
    public void saveBillingInformation(String cardName) {
        getPurchaseModelToDelegate().saveBillingInformation(cardName);
    }

    @Override
    public void saveBillingInformation(String cardName, String password) {
        getPurchaseModelToDelegate().saveBillingInformation(cardName, password);
    }

    @Override
    public void continueOnBillingPageOnPaymentFragment() {
        getPurchaseModelToDelegate().continueOnBillingPageOnPaymentFragment();
    }

    @Override
    public void completePurchase(Boolean guestOrUser) {
        getPurchaseModelToDelegate().completePurchase(guestOrUser);
    }

    @Override
    public void selectPGood() {
        getPurchaseModelToDelegate().selectPGood();
    }

    @Override
    public void continueToBillingFromDetails() {
        getPurchaseModelToDelegate().continueToBillingFromDetails();
    }

    @Override
    public void returnToDetailsFromBilling() {
        getPurchaseModelToDelegate().returnToDetailsFromBilling();
    }

    @Override
    public void verifyBillingPage() {
        getPurchaseModelToDelegate().verifyBillingPage();
    }

    @Override
    public void receiveConfirmation() {
        getPurchaseModelToDelegate().receiveConfirmation();
    }

    @Override
    public void clickOnAgreeAndBookButton(Boolean agreeWithTerms) {
        getPurchaseModelToDelegate().clickOnAgreeAndBookButton(agreeWithTerms);
    }

    @Override
    public void verifyBookingFailureMessage(String message) {
        getPurchaseModelToDelegate().verifyBookingFailureMessage(message);
    }

    @Override
    public void attemptBillingInformationCompletion(boolean isSignedInUser) {
        getPurchaseModelToDelegate().attemptBillingInformationCompletion(isSignedInUser);
    }

    @Override
    public void attemptToSignIn(AuthenticationParameters parameters, boolean signIn) {
        getPurchaseModelToDelegate().attemptToSignIn(parameters, signIn);
    }

    @Override
    public void validateCredentialInfoErrors(String credentialProfile) {
        getPurchaseModelToDelegate().validateCredentialInfoErrors(credentialProfile);
    }

    @Override
    public void selectInsuranceAddonOption(boolean choice) {
        getPurchaseModelToDelegate().selectInsuranceAddonOption(choice);
    }

    private PurchaseModel getPurchaseModelToDelegate() {
        switch (searchParameters.getPGoodCode()) {
            case H:
                return hotelPurchaseModel;
            case A:
                return airPurchaseModel;
            case C:
                return carPurchaseModel;
            default:
                return null;
        }
    }

    @Override
    public void verifyPaymentStatus(String condition) {
        getPurchaseModelToDelegate().verifyPaymentStatus(condition);
    }

    @Override
    public void verifyDollarOneAuth() {
        getPurchaseModelToDelegate().verifyDollarOneAuth();
    }

    @Override
    public void verifyInsuranceCostOnConfirmation() {
        getPurchaseModelToDelegate().verifyInsuranceCostOnConfirmation();
    }

}

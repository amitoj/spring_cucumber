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
 * This interface provides methods to purchase a pgood.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public interface PurchaseModel {

    void selectPGood();

    void continueToBillingFromDetails();

    void fillTravelerInfo(UserInformation userInformation, boolean isSignedInUser);

    void fillBillingInformation(UserInformation userInformation, boolean isSignedInUser);

    void fillCreditCard();

    void saveBillingInformation(String cardName);

    void saveBillingInformation(String cardName, String password);

    void continueOnBillingPageOnPaymentFragment();

    void returnToDetailsFromBilling();

    void verifyBillingPage();

    void completePurchase(Boolean guestOrUser);

    void receiveConfirmation();

    void validateCredentialInfoErrors(String credentialProfile);

    void attemptBillingInformationCompletion(boolean isSignedInUser);

    void attemptToSignIn(AuthenticationParameters parameters, boolean signIn);

    void attemptToSignIn(String email, String password);

    /**
     * Clicks on <Agree and Book> button without any actions
     * with other fields. Just Click.Available for Credit card and PayPal payment methods.
     *
     * @param agreeWithTerms Boolean if TRUE checkbox will be checked
     */
    void clickOnAgreeAndBookButton(Boolean agreeWithTerms);

    void selectPGood(boolean killPopup);

    /**
     * Checks booking failure message at billing-dispatch page
     *
     * @param message String
     */
    void verifyBookingFailureMessage(String message);

    void continueFromActiviesAndInsurancePanels();

    void validateInsuranceErrorMessage();

    void selectInsuranceAddonOption(boolean choice);

    void validateNewAgreeAndBookSection();

    void validateSetOfCreditCardFields(String setOfFields);

    void validateSaveCreditCardPresence(String savedCreditCardPresence);

    void verifyPage(String page);

    void verifyPaymentStatus(String condition);

    void selectPaymentMethod();

    void processDetailsPage();

    void processBillingPage();

    void verifyDollarOneAuth();

    void verifyInsuranceCostOnConfirmation();

    void freeze(double seconds);
}





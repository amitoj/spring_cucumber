/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 4:16 AM
 */
public interface CarTravelerInfo {

    boolean isDepositAgreementCheckboxDisplayed();

    boolean isDepositAgreementCheckboxSelected();

    void switchDepositAgreementState(boolean state);

    /**
     * This enum counters type of payment card agreement
     */
    public enum DepositCardAgreementType {
        CREDIT_CARD_ONLY,
        CREDIT_DEBIT_CARD_WITH_TICKET,
        NONE;   // When vendor accepts credit and debit cards without any restrictions
    }

    /**
     * @return type of agreement by label's text
     *         near checkbox, and NONE if checkbox doesn't exist
     */
    DepositCardAgreementType getDepositCardAgreementType();

    /**
     * Verify that deposit agreement box is highlighted in red
     * and message "Please confirm your age and deposit terms" appears above.
     */
    boolean verifyDepositCardAgreementBoxValidation();

    void changeDriverInformation(String firstName, String lastName);

    String getSelectedPrimaryDriver();

    String getSavedTravelerPrimaryPhoneNumber();

    String getSavedTravelerEmail();

    //fill traveler information
    CarTravelerInfo travelerInfo(String firstName, String lastName);

    CarTravelerInfo travelerPhoneNumber(String phoneNumber);

    CarTravelerInfo travelerEmail(String email);

    /**
     * use it for validation when you need to write different email and confirmation email
     */
    CarTravelerInfo travelerEmail(String email, String confEmail);

    CarTravelerInfo travelerEmailForLoggedUser(String email, String confEmail);

    CarTravelerInfo creditCardAcceptance(boolean accepted);

    CarTravelerInfo ageConfirmation(boolean confirm);

    /**
     * Enter credentials on billing page
     */
    void login(String username, String password);

    CarTravelerInfo continueGuest();

    CarTravelerInfo continuePanel();

    boolean isSavedDriverNameExists();

    boolean isSubscriberCheckBoxDisplayed();

    boolean isSubscriberCheckBoxSelected();

    boolean isOptionalSignInLinkDisplayed();

    boolean isTravelerConfirmationEmailDisplayed();

    void openOptionalSignIn();

    void goToPasswordAssistance();

    void submitOptionalSignInForm();

    void writeToOptionalSignInEmail(String email);

    void writeToOptionalSignInPassword(String password);

    void enterNewPrimaryDriverInfo();

    void chooseExistingDriver();
}

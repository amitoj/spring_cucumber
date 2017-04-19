/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.authentication;

import java.util.Date;

/**
* Interface for authentication
 */
public interface AuthenticationModel {

    void attemptToAuthenticate(AuthenticationParameters parameters);

    void verifyAuthenticationExceptionRaised();

    void verifyValidationErrorOnPassword();

    void verifyValidationErrorOnUsername();

    void loginWithCreditcardNumber(String emailid, String creditcardnumber);

    void loginWithInvalidCreditCardNumber(String email);

    void loginInFourTimesToGetErrorMessage();

    /**
     * Checking if Optional SignIn link is exist on page
     * @param condition
     */
    void optionalSignInStatus(String condition);

    /**
     * Opening Optional SignIn PopUp by clicking on "Optional SignIn" link on billing page
     */
    void openOptionalSignInPopUp();
    /**
    *   Click on password assistance link on OptionalSignIn popUp
    */
    void goToPasswordAssistance();

    /**
     * Click on OptionalSignIn link, fill all required field and submit
     */
    void logInByOptionalSignIn();

    /**
     * Just click on SignIn button on Optional SignIn PopUp
     * This action used in validation checking
     */
    void submitOptionalSignInForm();

    /**
     * Write to specific field on Optional SignIn popUp
     * This action used in validation checking
     * @param fieldType
     * @param fieldValue
     */
    void writeToOptionalSignInField(String fieldType, String fieldValue);

    void setPasswordAssistanceEmail(String email);

    void verifyPasswordAssistanceEmailPrefilled();

    void verifyPasswordAssistanceEmailConfirmation();

    void verifyPrepopulatedPA_Email(String email);  //PA - Password Assistance

    void loginWithDepartureDate(String emailid);

    void verifySignInPage();

    void goToSignInPage();

    void tryToSignIn(Integer iTimes);

    void clickInAHurryLink();

    String getBlackListEmail();

    Date getTripDepartureDateFromDb(String bookingType, String email);

    void setPasswordAssistancePassword(String password, String confirmationPassword);

    void attemptToSignIn();

    void confirmErrorHandling(String errorMsg);
}

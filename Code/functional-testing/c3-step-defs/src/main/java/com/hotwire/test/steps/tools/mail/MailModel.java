/*
 * Copyright 2013-2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.mail;

import java.util.List;

/**
 * Mail model interface
 */
public interface MailModel {

    /**
     * Check Email
     * @param subject
     */
    void checkEmail(String subject);

    /**
     * This method finds emails with respect to server side and client side params
     * serverside - from and subject
     * clientside - body
     *
     * @param from
     * @param subject
     * @param startTime
     * @param count
     * @return
     */
    boolean assertEmailReceived(String from, String subject, Integer startTime, Integer count, List<String> body);

    /**
     * This method is to find emails just with server side inputs - from and subject.
     * @param from
     * @param subject
     * @param startTime
     * @param count
     * @return
     */
    boolean assertEmailReceived(String from, String subject, Integer startTime, Integer count);

    void setAuthParameters(String host, String user, String password);

    void verifyTextInEmail(String subject, String expectedText);

    void verifyTextInEmail(String expectedText) throws Exception;

    void goToResetPasswordPageFromEmail(String userLogin, String userPassword);

    void verifyOldFromHotwirePasswordAssistanceEmailGenerateError(String userLogin, String userPassword);

    void verifyTemporaryPasswordEmailForCSRAccount();

    void verifyPasswordAssistanceEmail();

    void verifyEmailForRecentPartialRefund(String itineraryNumber, String refundAmount);

    void verifyWeHaveTwoConfirmationHotelEmailsWithSameInformation();
}

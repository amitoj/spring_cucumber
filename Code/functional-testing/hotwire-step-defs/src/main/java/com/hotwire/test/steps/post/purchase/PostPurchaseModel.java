/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.post.purchase;

/**
 * User: v-ngolodiuk
 * Since: 8/21/14
 */

public interface PostPurchaseModel {

    void clickToSeePrintVersion();

    void verifyPrintVersionPage();

    void verifyCancelButtonShareItineraryWindow();

    void shareCarItineraryViaEmail(String email);

    void shareItineraryWithBadAndGoodEmails();

    void shareItineraryForMultiplicityEmails(String firstEmail, String secondEmail);

    void verifyPrintTripDetailsPage();
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.discount;

/**
 * Discount model interface.
 */
public interface DiscountModel {
    void confirmDiscountOnTripSummary(String negateString);

    void confirmDiscountNotificationOnResultsPage(String negation);

    void confirmDiscountAppliedToOrder(String negateString);

    void selectQuote();

    void selectVertical();

    void navigateToLandingPage();

    void confirmDiscountNotificationOnLandingPage(String negation);

    void addDiscountAmountToDiscountParameters(String discount);

    void navigateToLandingPageWithNewCode();

    void confirmNewDiscountNotificationOnLandingPage();

    void setupValidCode();

    void setupInValidCode();

    void setupExpiredCode();

    void confirmDiscountBannerInDetails(boolean isDisplayed);

    void setupPercentDiscountCode(int percent);

    void setupFixedAmountDiscountCode(int value, String currency);

    void verifyDiscountConditionOnLanding();

    void verifySubscriptionModuleOnConvergedHotelLandingForGuestUser(boolean isDisplayed, String pos);

    void subscribeWithEmailAndZipcode(String email, String zipCode);

    void verifySubscriptionIsSuccessful(boolean isSuccessful);

    void verifyLandingPage();

}

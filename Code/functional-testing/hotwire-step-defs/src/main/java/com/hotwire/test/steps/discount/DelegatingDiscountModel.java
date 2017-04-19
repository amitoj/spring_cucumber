/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.discount;

/**
 * Delegation Discount model
 */
public class DelegatingDiscountModel extends DiscountModelTemplate {

    private DiscountModel carDiscountModel;
    private DiscountModel hotelDiscountModel;
    private DiscountModel airDiscountModel;

    public void setCarDiscountModel(DiscountModel carDiscountModel) {
        this.carDiscountModel = carDiscountModel;
    }

    public void setHotelDiscountModel(DiscountModel hotelDiscountModel) {
        this.hotelDiscountModel = hotelDiscountModel;
    }

    public void setAirDiscountModel(DiscountModel airDiscountModel) {
        this.airDiscountModel = airDiscountModel;
    }

    @Override
    public void confirmDiscountNotificationOnLandingPage(String negation) {
        getDiscountModelBasedOnPGoodCode().confirmDiscountNotificationOnLandingPage(negation);
    }

    @Override
    public void confirmNewDiscountNotificationOnLandingPage() {
        getDiscountModelBasedOnPGoodCode().confirmNewDiscountNotificationOnLandingPage();
    }

    @Override
    public void navigateToLandingPage() {
        getDiscountModelBasedOnPGoodCode().navigateToLandingPage();
    }

    @Override
    public void navigateToLandingPageWithNewCode() {
        getDiscountModelBasedOnPGoodCode().navigateToLandingPageWithNewCode();
    }

    @Override
    public void addDiscountAmountToDiscountParameters(String discount) {
        getDiscountModelBasedOnPGoodCode().addDiscountAmountToDiscountParameters(discount);
    }

    @Override
    public void confirmDiscountOnTripSummary(String negateString) {
        getDiscountModelBasedOnPGoodCode().confirmDiscountOnTripSummary(negateString);
    }

    @Override
    public void confirmDiscountAppliedToOrder(String negateString) {
        getDiscountModelBasedOnPGoodCode().confirmDiscountAppliedToOrder(negateString);
    }

    @Override
    public void confirmDiscountNotificationOnResultsPage(String negation) {
        getDiscountModelBasedOnPGoodCode().confirmDiscountNotificationOnResultsPage(negation);
    }

    @Override
    public void selectQuote() {
        getDiscountModelBasedOnPGoodCode().selectQuote();
    }

    @Override
    public void setupInValidCode() {
        getDiscountModelBasedOnPGoodCode().setupInValidCode();
    }

    @Override
    public void setupValidCode() {
        getDiscountModelBasedOnPGoodCode().setupValidCode();
    }

    @Override
    public void setupExpiredCode() {
        getDiscountModelBasedOnPGoodCode().setupExpiredCode();
    }

    private DiscountModel getDiscountModelBasedOnPGoodCode() {
        switch (searchParameters.getPGoodCode()) {
            case H:
                return hotelDiscountModel;
            case C:
                return carDiscountModel;
            case A:
                return airDiscountModel;
            default:
                return null;
        }
    }

    @Override
    public void confirmDiscountBannerInDetails(boolean isDisplayed) {
        getDiscountModelBasedOnPGoodCode().confirmDiscountBannerInDetails(isDisplayed);
    }

    @Override
    public void setupFixedAmountDiscountCode(int value, String currency) {
        getDiscountModelBasedOnPGoodCode().setupFixedAmountDiscountCode(value, currency);
    }

    @Override
    public void setupPercentDiscountCode(int percent) {
        getDiscountModelBasedOnPGoodCode().setupPercentDiscountCode(percent);
    }

    @Override
    public void verifyDiscountConditionOnLanding() {
        getDiscountModelBasedOnPGoodCode().verifyDiscountConditionOnLanding();
    }

    @Override
    public void verifyLandingPage() {
        getDiscountModelBasedOnPGoodCode().verifyLandingPage();
    }
}

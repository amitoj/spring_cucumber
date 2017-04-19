/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.post.purchase;

/**
 * @author v-mzabuga
 * @since 2012.07
 */
public class DelegatingPostPurchaseModel extends PostPurchaseModelTemplate implements PostPurchaseModel {

    private PostPurchaseModel carPostPurchaseModel;


    public void setCarPostPurchaseModel(PostPurchaseModel carPostPurchaseModel) {
        this.carPostPurchaseModel = carPostPurchaseModel;
    }


    private PostPurchaseModel getPostPurchaseModelToDelegate() {
        switch (searchParameters.getPGoodCode()) {
            case C:
                return carPostPurchaseModel;
            default:
                return null;
        }
    }

    @Override
    public void clickToSeePrintVersion() {
        getPostPurchaseModelToDelegate().clickToSeePrintVersion();
    }

    @Override
    public void verifyPrintVersionPage() {
        getPostPurchaseModelToDelegate().verifyPrintVersionPage();
    }

    @Override
    public void verifyCancelButtonShareItineraryWindow() {
        getPostPurchaseModelToDelegate().verifyCancelButtonShareItineraryWindow();
    }

    @Override
    public void shareCarItineraryViaEmail(String email) {
        getPostPurchaseModelToDelegate().shareCarItineraryViaEmail(email);
    }

    @Override
    public void shareItineraryWithBadAndGoodEmails() {
        getPostPurchaseModelToDelegate().shareItineraryWithBadAndGoodEmails();
    }

    @Override
    public void shareItineraryForMultiplicityEmails(String firstEmail, String secondEmail) {
        getPostPurchaseModelToDelegate().shareItineraryForMultiplicityEmails(firstEmail, secondEmail);
    }

    @Override
    public void verifyPrintTripDetailsPage() {
        getPostPurchaseModelToDelegate().verifyPrintTripDetailsPage();
    }
}

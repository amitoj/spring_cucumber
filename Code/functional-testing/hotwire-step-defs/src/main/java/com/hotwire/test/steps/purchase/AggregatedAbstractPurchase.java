/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase;

import com.hotwire.test.steps.purchase.PurchaseParameters.PaymentMethodType;
import com.hotwire.testing.UnimplementedTestException;

/**
 * @author v-ikomarov
 * @since 1/24/2015
 */
public abstract class AggregatedAbstractPurchase {


    /**
     * Hotwire pages
     */
    public enum HotwirePages {
        UHP("UHP"),
        LANDING("LANDING"),
        RESULTS("RESULTS"),
        DETAILS("DETAILS"),
        BILLING("BILLING"),
        AFTERBILLING("AFTERBILLING"),
        CONFIRMATION("CONFIRMATION");

        private String page;

        HotwirePages(String page) {
            this.page = page;
        }

        public static HotwirePages validate(String page) {
            for (HotwirePages t : HotwirePages.values()) {
                if (t.getPageName().equalsIgnoreCase(page)) {
                    return t;
                }
            }
            throw new UnimplementedTestException("Not expected reservation hotwire page name: " + page);
        }

        public String getPageName() {
            return page;
        }
    }

    /**
     * Product type
     */
    public enum ProductType {
        OPAQUE("opaque"),
        RETAIL("retail"),
        ANY("any");

        private String opacity;

        ProductType(String opacity) {
            this.opacity = opacity;
        }

        public static ProductType validate(String text) {
            for (ProductType t : ProductType.values()) {
                if (t.getOpacity().equalsIgnoreCase(text)) {
                    return t;
                }
            }
            throw new UnimplementedTestException("Not expected reservation type: " + text);
        }

        public String getOpacity() {
            return opacity;
        }


    }

    public abstract AggregatedSteps getAggregatedSteps();

    public abstract HotwirePages getStartPage();

    public abstract HotwirePages getEndPage();

    public abstract PaymentMethodType getPaymentMethod();

    public void makeSearch() {
        getAggregatedSteps().getSearchStep().requestFares(null, null, null);
    }

    protected abstract void initializationDefaultPurchaseParameters();

    protected void loginProcessing() {
        if (isLoginWithRandomUser()) {
            getAggregatedSteps().getAuthenticationSteps().setValidUserNameAndPassword("random");
            getAggregatedSteps().getAuthenticationSteps().attemptToAuthenticateUser();
        }
    }

    protected abstract boolean isLoginWithRandomUser();

    public abstract void goToLandingPage();

    protected void goToHomePage() {
        getAggregatedSteps().getAccountAccessSteps().navigateToHomePage();
    }

    public abstract void processDetailsPage();

    public abstract void processResultsPage();

    @Deprecated
    public abstract void processBillingPage();

    public abstract void processBillingTravelerInfo();

    public abstract void processBillingInsurance();

    public abstract void processBillingCreditCardInfo();

    public abstract void processBillingAgreeAndTerms();

    public void processConfirmationPage() {
        switch (getPaymentMethod()) {
            case PayPal:
                getAggregatedSteps().getPayPalPurchaseSteps().loginPayPalSandbox();
                break;
            default:
                getAggregatedSteps().getPurchaseStep().receiveConfirmation();
        }
    }

    public final void run() {
        initializationDefaultPurchaseParameters();

        loginProcessing();
        goToHomePage();

        switch (getStartPage()) {

            case LANDING:
                goToLandingPage();
                if (getEndPage().equals(HotwirePages.LANDING)) {
                    break;
                }

            case UHP:
                if (getEndPage().equals(HotwirePages.UHP)) {
                    break;
                }
                makeSearch();

            case RESULTS:
                if (getEndPage().equals(HotwirePages.RESULTS)) {
                    break;
                }
                processResultsPage();

            case DETAILS:
                if (getEndPage().equals(HotwirePages.DETAILS)) {
                    break;
                }
                processDetailsPage();

            case BILLING:
                if (getEndPage().equals(HotwirePages.BILLING)) {
                    break;
                }

                //processBillingPage();
                processBillingTravelerInfo();
                processBillingInsurance();
                processBillingCreditCardInfo();
                processBillingAgreeAndTerms();

            case AFTERBILLING:
                if (getEndPage().equals(HotwirePages.AFTERBILLING)) {
                    break;
                }
            case CONFIRMATION:
            default: //confirmation page by default
                processConfirmationPage();
        }
    }

}

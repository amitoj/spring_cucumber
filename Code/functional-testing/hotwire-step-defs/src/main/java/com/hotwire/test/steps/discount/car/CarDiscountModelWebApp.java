/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.discount.car;

import com.hotwire.selenium.desktop.us.confirm.CarConfirmationPage;
import com.hotwire.selenium.desktop.us.discount.DiscountBillingErrorPage;
import com.hotwire.selenium.desktop.us.discount.DiscountLandingPage;
import com.hotwire.selenium.desktop.us.discount.DiscountTripSummaryPanel;
import com.hotwire.selenium.desktop.us.index.CarIndexPage;
import com.hotwire.selenium.desktop.us.results.car.CarResultsPage;
import com.hotwire.selenium.desktop.us.results.car.CarResultsPageProvider;
import com.hotwire.test.steps.discount.DiscountModelTemplate;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Car discount model for WebApp
 */
public class CarDiscountModelWebApp extends DiscountModelTemplate {

    public void init() {
        // Fail early if we cannot make a simple remote call.
    }

    public void setupValidCode() {
        String code = createDiscount(SampleType.VALID);

        //disable car details-billing combination page
        //applicationModel.getSessionModifingParams().addParameter("vt.ODB12=1");
        sessionModifingParams.addParameter("dccid=" + code);
    }

    public void setupInValidCode() {
        String code = createDiscount(SampleType.INVALID);

        //disable car details-billing combination page
        //applicationModel.getSessionModifingParams().addParameter("vt.ODB12=1");
        sessionModifingParams.addParameter("dccid=" + code);
    }

    public void setupExpiredCode() {
        String code = createDiscount(SampleType.EXPIRED);

        //disable car details-billing combination page
        //applicationModel.getSessionModifingParams().addParameter("vt.ODB12=1");
        sessionModifingParams.addParameter("dccid=" + code);
    }

    public void navigateToLandingPage() {
        try {
            URL carLandingPageUrl = new URL(applicationUrl, "car/index.jsp");
            getWebdriverInstance().navigate().to(carLandingPageUrl);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void navigateToLandingPageWithNewCode() {
        try {
            String code = createDiscount(5, "USD", SampleType.VALID);

            String urlSuffix = "car/index.jsp";
            URL carLandingPageUrl = new URL(applicationUrl, urlSuffix);
            getWebdriverInstance().navigate().to(carLandingPageUrl + "?dccid=" + code);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectQuote() {
        CarResultsPageProvider.get(getWebdriverInstance())
            .getResult()
            .select();
    }

    @Override
    public void confirmDiscountOnTripSummary(String negateString) {
        DiscountTripSummaryPanel billingPage = new DiscountTripSummaryPanel(getWebdriverInstance());
        boolean verifyDiscountApplied = billingPage.hasDiscount();
        if (StringUtils.isEmpty(negateString)) {
            assertThat(verifyDiscountApplied)
                .as("Discount notification should show up on Trip Summary panel. But currently it is not showing up")
                .isTrue();
        }
        else {
            assertThat(verifyDiscountApplied)
                .as("Discount notification should not show up on Trip Summary panel. But currently it is showing up")
                .isFalse();
        }
    }

    @Override
    public void confirmDiscountAppliedToOrder(String negation) {
        if (StringUtils.isNotEmpty(negation) && negation.equalsIgnoreCase("not")) {
            DiscountBillingErrorPage billingPage = new DiscountBillingErrorPage(getWebdriverInstance());
            Boolean isNotificationShown = billingPage.hasDiscountError();
            assertThat(isNotificationShown)
                .as("Discount error notification should show up on Billing page. But currently it is not showing up")
                .isTrue();
        }
        else {
            CarConfirmationPage carConfirmationPage = new CarConfirmationPage(getWebdriverInstance());
            assertThat(carConfirmationPage.verifyDiscountApplied())
                .as("Discount should be applied to the total amount on the confirmation page. But currently it is not")
                .isTrue();
        }
    }


    @Override
    public void confirmDiscountNotificationOnResultsPage(String negation) {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        boolean isNotificationShown = carResultsPage.isDiscountNoteDisplayed();
        if (StringUtils.isNotEmpty(negation) && negation.equalsIgnoreCase("don't")) {
            assertThat(isNotificationShown)
                .as("Discount notification should not show up on Results page. But currently it is showing up")
                .isFalse();
        }
        else {
            assertThat(isNotificationShown)
                .as("Discount notification should show up on Results page. But currently it is not showing up")
                .isTrue();
        }
    }

    @Override
    public void confirmDiscountNotificationOnLandingPage(String negation) {
        DiscountLandingPage discountCarLandingPage = new DiscountLandingPage(getWebdriverInstance());
        boolean isNotificationShown = discountCarLandingPage.hasDiscount();
        if (StringUtils.isNotEmpty(negation) && negation.equalsIgnoreCase("don't")) {
            assertThat(isNotificationShown)
                .as("Discount notification should not show up on landing page. But currently it is showing up")
                .isFalse();
        }
        else {
            assertThat(isNotificationShown)
                .as("Discount notification should show up on landing page. But currently it is not showing up")
                .isTrue();
        }
    }

    @Override
    public void confirmNewDiscountNotificationOnLandingPage() {
        if (discountParameters.getCurrentDiscountAmount() != null &&
                discountParameters.getNewDiscountAmount() != null) {

            assertThat(discountParameters.getCurrentDiscountAmount()
                    .equalsIgnoreCase(discountParameters.getNewDiscountAmount()))
                    .as("Old discount code amount should be different from new discount code amount to verify " +
                            "overriding functionality. Currently both amounts are the same")
                    .isFalse();
        }
    }

    @Override
    public void addDiscountAmountToDiscountParameters(String discount) {
        DiscountLandingPage discountCarLandingPage = new DiscountLandingPage(getWebdriverInstance());
        String discountAmount = discountCarLandingPage.getDiscountAmount();
        if (discount.equalsIgnoreCase("current")) {
            discountParameters.setCurrentDiscountAmount(discountAmount);
        }
        else {
            discountParameters.setNewDiscountAmount(discountAmount);
        }
    }

    @Override
    public void verifyLandingPage() {
        new CarIndexPage(getWebdriverInstance());
    }
}

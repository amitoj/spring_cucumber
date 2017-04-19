/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.discount.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.confirm.HotelConfirmationPage;
import com.hotwire.selenium.desktop.us.discount.DiscountLandingPage;
import com.hotwire.selenium.desktop.us.discount.DiscountTripSummaryPanel;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.discount.DiscountModelTemplate;
import com.hotwire.test.steps.discount.DiscountParameters;

/**
 *
 */
public class HotelDiscountModelWebApp extends DiscountModelTemplate {

    @Override
    public void setupValidCode() {
        String code = createDiscount(SampleType.VALID);
        sessionModifingParams.addParameter("dccid=" + code);
    }

    @Override
    public void setupInValidCode() {
        String code = createDiscount(SampleType.INVALID);
        sessionModifingParams.addParameter("dccid=" + code);
    }

    @Override
    public void setupExpiredCode() {
        String code = createDiscount(SampleType.EXPIRED);
        sessionModifingParams.addParameter("dccid=" + code);
    }

    @Override
    public void setupPercentDiscountCode(int percent) {
        String code = createDiscount(percent, "", SampleType.VALID);
        sessionModifingParams.addParameter("dccid=" + code);
    }

    @Override
    public void setupFixedAmountDiscountCode(int value, String currency) {
        String code = createDiscount(value, currency, SampleType.VALID);
        sessionModifingParams.addParameter("dccid=" + code);
    }

    @Override
    public void navigateToLandingPage() {
        new GlobalHeader(getWebdriverInstance()).navigateToVerticalLandingPageFromNavigationTab("Hotels");
    }

    @Override
    public void selectQuote() {
        HotelResultsPage hotelResultsPage = new HotelResultsPage(getWebdriverInstance());
        hotelResultsPage.select(purchaseParameters.getResultNumberToSelect());
    }

    @Override
    public void confirmDiscountOnTripSummary(String negateString) {
        DiscountTripSummaryPanel billingPage = new DiscountTripSummaryPanel(getWebdriverInstance());
        boolean verifyDiscountApplied = billingPage.hasDiscount();
        if (StringUtils.isEmpty(negateString)) {
            assertThat(verifyDiscountApplied).isTrue();
        }
        else {
            assertThat(verifyDiscountApplied).isFalse();
        }
    }

    @Override
    public void confirmDiscountNotificationOnLandingPage(String negation) {
        DiscountLandingPage discountHotelLandingPage = new DiscountLandingPage(getWebdriverInstance());
        boolean isNotificationShown = discountHotelLandingPage.hasDiscount();
        if (StringUtils.isNotEmpty(negation) &&
                negation.equalsIgnoreCase("don't")) {
            assertThat(isNotificationShown).isFalse();
        }
        else {
            assertThat(isNotificationShown).isTrue();
            assertThat(discountHotelLandingPage.getIsDiscountPercent())
                    .isEqualTo(discountParameters.getIsPercentDiscount());
        }
    }

    @Override
    public void addDiscountAmountToDiscountParameters(String discount) {
        DiscountLandingPage discountCarLandingPage = new DiscountLandingPage(
                getWebdriverInstance());
        String discountAmount = discountCarLandingPage.getDiscountAmount();
        if (discount.equalsIgnoreCase("current")) {
            discountParameters.setCurrentDiscountAmount(discountAmount);
        }
        else {
            discountParameters.setNewDiscountAmount(discountAmount);
        }
    }

    @Override
    public void confirmDiscountNotificationOnResultsPage(String negation) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        boolean isNotificationShown = resultsPage.verifyDiscountNoteShown();
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
    public void confirmDiscountAppliedToOrder(String negation) {
        HotelConfirmationPage hotelConfirmationPage = new HotelConfirmationPage(
                getWebdriverInstance());
        Boolean isNotificationShown = hotelConfirmationPage
                .verifyDiscountApplied();
        if (StringUtils.isNotEmpty(negation) && negation.equalsIgnoreCase("not")) {
            assertThat(isNotificationShown)
                .as("Discount should not be applied to the total amount on the confirmation page. But currently it is")
                .isFalse();
        }
        else {
            assertThat(isNotificationShown)
                .as("Discount should be applied to the total amount on the confirmation page. But currently it is not")
                .isTrue();
        }
    }

    @Override
    public void confirmDiscountBannerInDetails(boolean isDisplayed) {
        boolean bannerDisplayed = new HotelDetailsPage(getWebdriverInstance()).isDiscountBannerDisplayed();
        assertThat(bannerDisplayed)
            .as("Expected discount banner to be displayed to be " + isDisplayed +
                " but is " + bannerDisplayed + " instead.")
            .isEqualTo(isDisplayed);
    }

    @Override
    public void verifyDiscountConditionOnLanding() {
        DiscountLandingPage landingPage = new DiscountLandingPage(getWebdriverInstance());
        DiscountParameters.ConditionType condition = discountParameters.getDiscountCondition();
        int conditionValue = discountParameters.getConditionValue();
        String expectedMessage = "";

        if (condition.equals(DiscountParameters.ConditionType.MIN_TOTAL)) {
            expectedMessage = "on purchases of $" + Integer.toString(conditionValue) + " or more";
            assertThat(landingPage.getDiscountBannerText()).containsIgnoringCase(expectedMessage);
        }
        else if (condition.equals(DiscountParameters.ConditionType.MIN_RATING)) {
            assertThat(landingPage.getDiscountRule())
                    .containsIgnoringCase("Applies to Hotwire Hot Rates of " + conditionValue + "-star and up");
        }
        else if (condition.equals(DiscountParameters.ConditionType.MAX_DTA)) {
            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");
            Date endDate = DateUtils.addDays(new Date(), conditionValue);
            expectedMessage = "Check-in by " + sdf.format(endDate) + " to receive\nthe discount.";
            assertThat(landingPage.getDiscountBannerText()).containsIgnoringCase(expectedMessage);
        }
    }

    @Override
    public void verifyLandingPage() {
        new HotelIndexPage(getWebdriverInstance());
    }
}

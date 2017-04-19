/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.discount.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.hotwire.selenium.desktop.common.billing.HotelBillingPage;
import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.row.HomePage;
import com.hotwire.selenium.desktop.us.confirm.HotelConfirmationPage;
import com.hotwire.selenium.desktop.us.discount.DiscountTripSummaryPanel;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.discount.DiscountModelTemplate;
import com.hotwire.test.steps.discount.DiscountParameters;

/**
 * Created with IntelliJ IDEA.
 * User: v-ngolodiuk
 * Date: 10/10/13
 * Time: 3:53 PM
 */
public class HotelDiscountModelRowWebApp extends DiscountModelTemplate {

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
    public void confirmDiscountNotificationOnLandingPage(String negation) {
        HomePage homePage = new HomePage(getWebdriverInstance());
        boolean isNotificationShown = homePage.isDiscountBannerPresent();
        if (StringUtils.isNotEmpty(negation) &&
                negation.equalsIgnoreCase("don't")) {
            assertThat(isNotificationShown).isFalse();
        }
        else {
            assertThat(isNotificationShown).isTrue();
        }
    }

    @Override
    public void addDiscountAmountToDiscountParameters(String discount) {
        //do nothing, amount was set on discount creation
    }

    @Override
    public void confirmDiscountNotificationOnResultsPage(String negation) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        boolean isNotificationShown = resultsPage.isDiscountBannerPresent();
        if (StringUtils.isNotEmpty(negation) && negation.equalsIgnoreCase("don't")) {
            assertThat(isNotificationShown)
                    .as("Discount notification should not show up on Results page. But currently it is showing up")
                    .isFalse();
        }
        else {
            assertThat(isNotificationShown)
                    .as("Discount notification should show up on Results page. But currently it is not showing up")
                    .isTrue();
            assertThat(resultsPage.getDiscountAmount())
                    .as("Current discount amount")
                    .containsIgnoringCase(discountParameters.getCurrentDiscountAmount());
            assertThat(resultsPage.getIsDiscountPercent()).isEqualTo(discountParameters.getIsPercentDiscount());

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
    public void confirmDiscountOnTripSummary(String negateString) {
        boolean isDiscountApplied = new DiscountTripSummaryPanel(getWebdriverInstance()).hasDiscount();
        boolean isDiscountBannerPresent = new HotelBillingPage(getWebdriverInstance()).isDiscountBannerPresent();

        if (StringUtils.isEmpty(negateString)) {
            assertThat(isDiscountApplied && isDiscountBannerPresent).isTrue();
        }
        else {
            assertThat(isDiscountApplied || isDiscountBannerPresent).isFalse();
        }
    }

    @Override
    public void confirmDiscountAppliedToOrder(String negateString) {
        boolean isDiscountApplied = new HotelConfirmationPage(getWebdriverInstance()).verifyDiscountApplied();
        if (StringUtils.isNotEmpty(negateString) &&
                negateString.equalsIgnoreCase("not")) {
            assertThat(isDiscountApplied).isFalse();
        }
        else {
            assertThat(isDiscountApplied).isTrue();
        }
    }

    @Override
    public void verifyDiscountConditionOnLanding() {
        HomePage homePage = new HomePage(getWebdriverInstance());
        DiscountParameters.ConditionType condition = discountParameters.getDiscountCondition();
        int conditionValue = discountParameters.getConditionValue();
        String expectedMessage = "";

        if (condition.equals(DiscountParameters.ConditionType.MIN_TOTAL)) {
            expectedMessage = ".* on purchases of \\w+ " + Integer.toString(conditionValue) + " or more.*";
            Pattern pattern1 = Pattern.compile(expectedMessage, Pattern.MULTILINE);
            assertThat(pattern1.matcher(homePage.getDiscountBannerText()).find()).isTrue();
        }
        else if (condition.equals(DiscountParameters.ConditionType.MIN_RATING)) {
            assertThat(homePage.getDiscountRule())
                    .containsIgnoringCase("Applies to Hotwire Hot Rates of " + conditionValue + "-star and up");
        }
        else if (condition.equals(DiscountParameters.ConditionType.MAX_DTA)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            Date endDate = DateUtils.addDays(new Date(), conditionValue);
            expectedMessage = "Check-in by " + sdf.format(endDate) + " to receive\nthe discount.";
            assertThat(homePage.getDiscountBannerText()).containsIgnoringCase(expectedMessage);
        }
    }
}

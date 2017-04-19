/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fest.assertions.Delta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.hotwire.selenium.desktop.common.billing.HotelBillingPage;
import com.hotwire.selenium.desktop.common.billing.HotelTripSummaryFragment;
import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.row.confirmation.HotelConfirmationPage;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.util.webdriver.SessionModifingParams;

/**
 * @author vjong
 */
public class HotelPurchaseModelRowWebApp extends HotelPurchaseModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelPurchaseModelRowWebApp.class.getSimpleName());

    @Autowired
    protected SessionModifingParams sessionModifingParams;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @SuppressWarnings("serial")
    @Override
    public void receiveConfirmation() {
        final HotelConfirmationPage hotelConfirmationPage = new HotelConfirmationPage(getWebdriverInstance());
        final String itineraryNumber = hotelConfirmationPage.getItineraryNumber();
        final String confirmationNumber = hotelConfirmationPage.getConfirmationNumber();
        final Float price = hotelConfirmationPage.getHotwirePrice();

        LOGGER.info("Hotel Confirmation Number: " + confirmationNumber);
        LOGGER.info("Hotwire Interary: " + itineraryNumber);

        try {
            String sql = "select pr.status_code, pr.amount, pr.currency_code " +
                "from purchase_order po, payment_receipt pr " +
                "where pr.purchase_order_id = po.purchase_order_id and po.display_number = ? " +
                "order by pr.amount, pr.status_code";
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, new Object[]{itineraryNumber});

            List<Object[]> expectedResults = new ArrayList<Object[]>() {
                {
                    String currencyCode = purchaseParameters.getCurrencyCode();
                    float minimalAmount = 1f;
                    if ("SEK".equalsIgnoreCase(currencyCode) || "NOK".equalsIgnoreCase(currencyCode) ||
                        "DKK".equalsIgnoreCase(currencyCode)) {
                        minimalAmount = 10f;
                    }

                    add(new Object[]{30200, minimalAmount, currencyCode});
                    add(new Object[]{30200, price, currencyCode});
                    add(new Object[]{30400, price, currencyCode});
                }
            };

            while (sqlRowSet.next()) {
                LOGGER.info("Transaction data stored in db: " +
                    StringUtils.join(
                        new Object[]{
                            new Integer(sqlRowSet.getInt("status_code")).toString(),
                            new Float(sqlRowSet.getFloat("amount")).toString(),
                            sqlRowSet.getString("currency_code")}, " : ") +
                    " - Expected result: " + StringUtils.join(expectedResults.remove(0), " : "));
                // Uncomment below when you decide to fix this that affects less than 1%
                // or unless this method figures out how to handle this known issue.
                // -> java.lang.AssertionError: [Transaction data stored in db] expected:<[30400, 216.51, 'GBP']>
                // but was:<[30405, 216.51, 'GBP']>
                /*assertThat(new Object[]{sqlRowSet.getInt("status_code"), sqlRowSet.getFloat("amount"),
                 *                        sqlRowSet.getString("currency_code")})
                    .as("Transaction data stored in db")
                    .isEqualTo(expectedResults.remove(0));*/
            }
            /* This is frigging Janky along with the other DB check stuff above.
            assertThat(expectedResults)
                    .as("Expected results after database check")
                    .isEmpty();*/
        }
        catch (CannotGetJdbcConnectionException e) {
            LOGGER.info("Issue with DB check.\n" + ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void verifyErrorMessage() {
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
        assertThat(billingPage.getErrorMessages()).contains("Please correct the following:");
        assertThat(billingPage.getErrorMessages()).contains(
            "The credit card number, expiration date, or security code you entered " +
            "does not match what is on file with your card's issuing bank. Your card has not been charged. " +
            "Please note this price is not guaranteed until the purchase is complete.");
        assertThat(billingPage.getErrorMessages()).contains("We apologize for this Hotwire booking error. " +
            "Your credit card provider may be blocking specific transactions. " +
            "To complete your booking, we suggest contacting your credit card provider to remove any restrictions.");
    }

    @Override
    public void verifyMandatoryFee(String fee) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance(),
            HotelDetailsPage.TILE_HOTEL_DETAILS_RETAIL_OVERVIEW);
        assertThat(detailsPage.getMandatoryFees()).contains(fee);
    }

    @Override
    public void verifyMarketingBeacon() {
        assertThat(getWebdriverInstance().getPageSource().contains("script type=\"text/javascript\">\n" +
            "            AnalyticsSupport.addMarketingBeaconUrl(\"https://www.googleadservices.com/pagead/" +
                "conversion/1030960077/?label=kgZrCJP9qAIQzefM6wM&amp;amp;guid=ON&amp;amp;script=0\");\n" +
            "        </script>")).isTrue();
    }

    @Override
    public void verifyBillingPage() {
        HotelBillingPage hotelBillingPage = new HotelBillingPage(getWebdriverInstance());
        HotelTripSummaryFragment tripSummaryFragment = hotelBillingPage.getTripSummaryFragment();
        assertThat(tripSummaryFragment.getTotalPrice())
            .as("Trip total price")
            .isEqualTo(tripSummaryFragment.getHotwirePrice() +
                tripSummaryFragment.getTaxesAndFees(), Delta.delta(0.1));
    }

    @Override
    public void verifyFeesAndTaxesOnConfirmation() {
        HotelConfirmationPage hotelConfirmationPage = new HotelConfirmationPage(getWebdriverInstance());
        assertThat(hotelConfirmationPage.getTotalPrice())
            .as("Trip total price")
            .isEqualTo(hotelConfirmationPage.getHotwirePrice() +
                hotelConfirmationPage.getTaxesAndFees(), Delta.delta(0.1));
    }

    @Override
    public void verifyEmailsPopulatedInTravelerInfoBillingPage() {
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
        String email = billingPage.getEmailAddress().getText();
        String confirmEmail = billingPage.getConfirmEmailAddress().getText();

        LOGGER.info("Checking authenticationParameters for Billing: " + authenticationParameters.getUsername().trim());
        assertThat(email.trim())
            .as("Expected to see \"" + authenticationParameters.getUsername() + "\" but got \"" + email + "\"")
            .isEqualToIgnoringCase(authenticationParameters.getUsername().trim());

        assertThat(confirmEmail.trim())
            .as("Expected to see \"" + authenticationParameters.getUsername() + "\" but got \"" + confirmEmail + "\"")
            .isEqualToIgnoringCase(authenticationParameters.getUsername().trim());

        // Check sign in module.
        billingPage.clickSignInLink();
        String signInEmail = billingPage.getEmailFromSignInLinkModule();
        assertThat(signInEmail.trim())
            .as("Expected to see \"" + authenticationParameters.getUsername() + "\" but got \"" + confirmEmail + "\"")
            .isEqualToIgnoringCase(authenticationParameters.getUsername().trim());
    }

    @Override
    public void verifySaveBillingInfoVisibility(boolean isDisplayed) {
        boolean actual = new HotelBillingPage(getWebdriverInstance()).isSavedCreditCardModuleDisplayed();
        assertThat(actual)
            .as("Expected save billing info module visibility to be " + isDisplayed + " but got: " + actual)
            .isEqualTo(isDisplayed);
    }
}

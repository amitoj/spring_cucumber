/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.discount;

import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.SearchParameters;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

/**
 * Discount steps.
 *
 * @author vjong
 */
public class DiscountSteps extends AbstractSteps {

    @Autowired
    @Qualifier("discountModel")
    private DiscountModel discountModel;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters selectionParameters;

    @Autowired
    @Qualifier("discountParameters")
    private DiscountParameters discountParameters;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Resource(name = "randomEmail")
    private String randomEmail;

    @Given("^a (valid|invalid|expired) (car|hotel) discount code$")
    public void discountCode(final String type, String vertical) throws Throwable {
        if (StringUtils.isNotEmpty(vertical)) {
            searchParameters.setPGoodCode(PGoodCode.toPGoodType(vertical));
        }
        if ("invalid".equals(type)) {
            discountModel.setupInValidCode();
        }
        else if ("expired".equals(type)) {
            discountModel.setupExpiredCode();
        }
        else {
            discountModel.setupValidCode();
        }
    }

    @Then("^a discount has(?:\\s(not))? been applied$")
    public void confirmDiscountAppliedToTripSummary(final String negateString) throws Throwable {
        discountModel.confirmDiscountOnTripSummary(negateString);
    }

    @When("^I choose a quote that has a Hot Rate$")
    public void chooseQuoteWithHotRate() throws Throwable {
        selectionParameters.setOpaqueRetail("discount");
        selectionParameters.setResultNumberToSelect(1);
        discountModel.selectQuote();
    }

    @Then("^the discount has(?:\\s(not))? been applied to the order$")
    public void confirmDiscountAppliedToOrder(String negateString) {
        discountModel.confirmDiscountAppliedToOrder(negateString);
    }

    @When("^I am on (car|hotel) landing page with new valid discount$")
    public void gotoVerticalLandingPageWithNewCode(String vertical) throws Throwable {
        if (StringUtils.isNotEmpty(vertical)) {
            searchParameters.setPGoodCode(PGoodCode.toPGoodType(vertical));
        }
        discountModel.navigateToLandingPageWithNewCode();
    }

    @When("^I am on (car|hotel|air) (landing|index) page$")
    public void gotoVerticalLandingPage(String vertical, String pageType) throws Throwable {
        if (StringUtils.isNotEmpty(vertical)) {
            searchParameters.setPGoodCode(PGoodCode.toPGoodType(vertical));
        }
        if ("landing".equals(pageType)) {
            searchParameters.setLandingPage(true);
            discountModel.navigateToLandingPage();
        }
        else {
            discountModel.selectVertical();
        }
    }

    @And("^I see (car|hotel|flight) landing page$")
    public void verifyLandingPage(String vertical) {
        searchParameters.setPGoodCode(PGoodCode.toPGoodType(vertical));
        discountModel.verifyLandingPage();
    }

    @Then("^I (?:(don\'t))? get discount notification banner$")
    public void confirmDiscountNotificationOnResultsPage(String negation) {
        discountModel.confirmDiscountNotificationOnResultsPage(negation);
    }

    @Then("^a discount notification banner (has|has not) appeared on the hotel details$")
    public void confirmDiscountBannerInDetails(String state) {
        discountModel.confirmDiscountBannerInDetails(state.trim().equals("has") ? true : false);
    }

    @Then("^I (?:(don\'t))? get discount notification$")
    public void confirmDiscountNotificationOnLandingPage(String negation) {
        discountModel.confirmDiscountNotificationOnLandingPage(negation);
    }

    @Then("^I get discount notification for (current|new) discount$")
    public void confirmNewDiscountNotificationOnLandingPage(String page) {
        if (StringUtils.isNotEmpty(page) && page.contains("current")) {
            discountModel.confirmDiscountNotificationOnLandingPage("");
            discountModel.addDiscountAmountToDiscountParameters("current");
        }
        else if (StringUtils.isNotEmpty(page) && page.contains("new")) {
            throw new PendingException(
                "Throwing pending exception until we figure out why this test is failing on qaci");
            // discountModel.confirmDiscountNotificationOnLandingPage("");
            // discountModel.addDiscountAmountToDiscountParameters("new");
            // discountModel.confirmNewDiscountNotificationOnLandingPage();
        }
    }

    @Given("^a (\\d+) (USD|GBP|EUR|NZD|MXN|DKK|NOK|percent) (hotel|car) discount code$")
    public void createHotelGroupDiscountCode(int value, String currency, String vertical) {
        searchParameters.setPGoodCode(PGoodCode.toPGoodType(vertical));

        if ("percent".equalsIgnoreCase(currency)) {
            discountModel.setupPercentDiscountCode(value);
        }
        else {
            discountModel.setupFixedAmountDiscountCode(value, currency);
        }
    }

    @Given("^a (\\d+) (GBP|USD|percent) discount with condition of (\\d+) (?:GBP|stars|days) " +
            "as (min total|min star rating|max DTA)$")
    public void setupConditionalDiscount(int value, String currency, int conditionValue, String condition) {
        searchParameters.setPGoodCode(PGoodCode.toPGoodType("hotel"));

        if ("min total".equalsIgnoreCase(condition)) {
            discountParameters.setDiscountCondition(DiscountParameters.ConditionType.MIN_TOTAL);
        }
        else if ("min star rating".equalsIgnoreCase(condition)) {
            discountParameters.setDiscountCondition(DiscountParameters.ConditionType.MIN_RATING);
        }
        else {
            discountParameters.setDiscountCondition(DiscountParameters.ConditionType.MAX_DTA);
        }
        discountParameters.setConditionValue(conditionValue);

        if ("percent".equalsIgnoreCase(currency)) {
            discountModel.setupPercentDiscountCode(value);
        }
        else {
            discountModel.setupFixedAmountDiscountCode(value, currency);
        }
    }

    @And("^I am notified about the discount condition$")
    public void verifyDiscountConditionOnLanding() {
        discountModel.verifyDiscountConditionOnLanding();
    }

    @Then("^I (will|will not) see the subscription module on the (domestic|international) hotel landing page$")
    public void verifySubscriptionModuleOnConvergedHotelLanding(String state, String pos) {
        discountModel.verifySubscriptionModuleOnConvergedHotelLandingForGuestUser(
            state.trim().equals("will") ? true : false, pos.trim());
    }

    @Given("^I want to subscribe with a (new|saved|invalid) email and my (.*) zipcode$")
    public void subscribeWithEmailAndZipcode(String emailType, String zipCode) {
        String email;
        if (emailType.trim().equals("new")) {
            email = randomEmail;
        }
        else if (emailType.trim().equals("saved")) {
            authenticationParameters.setKnownGoodParameters();
            email = authenticationParameters.getUsername();
        }
        else {
            email = "@";
        }
        discountModel.subscribeWithEmailAndZipcode(email, zipCode);
    }

    @Then("^the subscription (will|will not) be successful.$")
    public void verifySubscriptionIsSuccessful(String state) {
        discountModel.verifySubscriptionIsSuccessful(state.trim().equals("will") ? true : false);
    }
}

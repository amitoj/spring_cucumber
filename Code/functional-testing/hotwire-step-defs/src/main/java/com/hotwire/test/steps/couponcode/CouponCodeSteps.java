/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.couponcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.couponcode.CouponCodeModelTemplate.SampleType;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author vjong
 */
public class CouponCodeSteps extends AbstractSteps {

    @Autowired
    @Qualifier("couponCodeModel")
    CouponCodeModel model;

    @Given("^I want to use a brand new (valid|invalid|expired|future) (hotel|car) coupon code$")
    public void generateAndStoreNewCouponCode(String type, String couponPgood) {
        SampleType sampleType = SampleType.INVALID;
        if (type.equalsIgnoreCase("valid")) {
            sampleType = SampleType.VALID;
        }
        else if (type.equalsIgnoreCase("expired")) {
            sampleType = SampleType.EXPIRED;
        }
        else if (type.equalsIgnoreCase("future")) {
            sampleType = SampleType.FUTURE;
        }
        model.generateAndStoreNewCouponCode(sampleType, couponPgood);
    }

    @Given(
        "I have a " +
        "(valid|minimum 3 star rating|valid mobile|valid native app|minimum amount|" +
        "invalid start date|invalid end date) hotel coupon code$")
    public void setCouponCode(String type) {
        model.setCouponCode(type);
    }

    @Given("^I append good discount dccid code$")
    public void appendGoodDccidCodeToUrl() {
        model.appendGoodDccidCodeToUrl();
    }

    @When(
        "^I apply a " +
        "(valid|minimum 3 star rating|valid mobile|valid native app|minimum amount|" +
        "invalid start date|invalid end date) hotel coupon code$")
    public void applyHotelCouponCode(String couponCodeType) {
        model.applyHotelCouponCode(couponCodeType);
    }

    @When("^I apply the brand new coupon code$")
    public void applyGeneratedCouponCode() {
        model.applyGeneratedCouponCode();
    }

    @Then("^I receive confirmation of hotel purchase with the brand new coupon discount applied$")
    public void verifyHotelPurchaseCouponConfirmation() {
        model.verifyHotelPurchaseCouponConfirmation();
    }

    @Then("^I (will|will not) see the coupon code discount applied in billing trip summary$")
    public void verifyCouponCodeDiscountDisplayedInBillingTripSummary(String state) {
        model.verifyCouponCodeDiscountDisplayedInBillingTripSummary(state.equalsIgnoreCase("will") ? true : false);
    }

    @Then("^coupon code entry (is|is not) disabled$")
    public void verifyCouponCodeDisplayed(String state) {
        model.verifyCouponCodeDisplayed(state.equals("is") ? true : false);
    }

    @Then("^the correct discount will be applied to the trip summary total with/without insurance after applying " +
          "a valid hotel coupon code$")
    public void verifyTripSummaryTotalWithValidCouponCodeAndInsurance() {
        model.verifyTripSummaryTotalWithValidCouponCodeAndInsurance();
    }
}

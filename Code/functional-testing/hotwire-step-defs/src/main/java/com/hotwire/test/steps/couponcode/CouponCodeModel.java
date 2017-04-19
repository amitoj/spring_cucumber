/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.couponcode;

import com.hotwire.test.steps.couponcode.CouponCodeModelTemplate.SampleType;

/**
 * @author vjong
 *
 */
public interface CouponCodeModel {

    void applyHotelCouponCode(String couponCodeType);

    void applyGeneratedCouponCode();

    void verifyCouponCodeDiscountDisplayedInBillingTripSummary(boolean shouldBeDisplayed);

    void verifyCouponCodeDisplayed(boolean shouldNotBeDisplayed);

    void appendGoodDccidCodeToUrl();

    void verifyTripSummaryTotalWithValidCouponCodeAndInsurance();

    void generateAndStoreNewCouponCode(SampleType sampleType, String couponPgood);

    void verifyHotelPurchaseCouponConfirmation();

    void setCouponCode(String type);
}

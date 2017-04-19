/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.coupons;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.util.DiscountJdoBuilder;
import cucumber.api.java.en.And;
import hotwire.biz.order.discount.DiscountJdo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-dsobko
 * Since: 01/20/15
 */
public class CouponCodesSteps extends AbstractSteps {

    private static final int COUPON_VALUE = 10;
    private static final int CROSS_VERTICAL_COUPON_VALUE = 15;
    private static final int ERROR_CODE_EXPIRED_COUPON = 3010;
    private static final int ERROR_CODE_FIRST_APP_PURCHASE = 3020;
    private static final String MOBILE_DEVICES_SITE_TYPE = "1005";

    @Autowired
    RequestProperties requestProperties;

    @Autowired
    private CouponCodesService couponCodesService;

    @And("^I validate expired (USA|UK) (hotel|car) percent coupon code from (UK|US)$")
    public void useExpiredCarCouponCodeForValidation(String countryCode, String vertical, String requestCountry) {
        String couponName = DiscountJdoBuilder.BDD_COUPON_PREFIX + System.currentTimeMillis();
        DiscountJdo discountJdo = new DiscountJdoBuilder()
                .withAmount(COUPON_VALUE)
                .withCurrency("")
                .withSiteType(MOBILE_DEVICES_SITE_TYPE)
                .withPgoodCode(vertical.charAt(0))
                .withCountryCodes(CouponCodesService.CountryCodes.getCodeByName(countryCode))
                .isFirstTimePurchase(false)
                .withCouponName(couponName)
                .withSampleType(CouponCodesService.SampleType.EXPIRED)
                .build();
        String discount = couponCodesService.createDiscount(CouponCodesService.SampleType.EXPIRED,
                discountJdo, couponName);
        requestProperties.setCountryCode(requestCountry);

        if (vertical.equalsIgnoreCase("hotel")) {
            couponCodesService.executeHotelRequestInvalidCoupon(discount, ERROR_CODE_EXPIRED_COUPON);
        }
        else {
            couponCodesService.executeCarRequestInvalidCoupon(discount, ERROR_CODE_EXPIRED_COUPON);
        }
    }

    @And("^I validate correct (USA|UK) (USD|GBP|EUR) (hotel|car) amount coupon code from (UK|US)$")
    public void generateAndReturnHotelAmountCoupon(String couponCodeCountry, String currency,
                                                   String vertical, String requestCountry) {
        String couponName = DiscountJdoBuilder.BDD_COUPON_PREFIX + System.currentTimeMillis();
        DiscountJdo discountJdo = new DiscountJdoBuilder()
                .withAmount(COUPON_VALUE)
                .withCurrency(currency)
                .withSiteType(MOBILE_DEVICES_SITE_TYPE)
                .withPgoodCode(vertical.charAt(0))
                .withCountryCodes(CouponCodesService.CountryCodes.getCodeByName(couponCodeCountry))
                .isFirstTimePurchase(false)
                .withCouponName(couponName)
                .withSampleType(CouponCodesService.SampleType.VALID)
                .build();
        String discount = couponCodesService.createDiscount(CouponCodesService.SampleType.VALID,
                discountJdo, couponName);

        requestProperties.setCountryCode(requestCountry);

        if (vertical.equalsIgnoreCase("hotel")) {
            couponCodesService.useHotelCouponForValidation(discount);
        }
        else {
            couponCodesService.useCarCouponForValidation(discount);
        }
    }

    @And("^I validate correct (USA|UK) (hotel|car) percent coupon code from (UK|US)$")
    public void generateAndReturnHotelPercentCoupon(String countryCode, String vertical, String requestCountry) {
        String couponName = DiscountJdoBuilder.BDD_COUPON_PREFIX + System.currentTimeMillis();

        DiscountJdo discountJdo = new DiscountJdoBuilder()
                .withAmount(COUPON_VALUE)
                .withCurrency("")
                .withSiteType(MOBILE_DEVICES_SITE_TYPE)
                .withPgoodCode(vertical.charAt(0))
                .withCountryCodes(CouponCodesService.CountryCodes.getCodeByName(countryCode))
                .isFirstTimePurchase(false)
                .withCouponName(couponName)
                .withSampleType(CouponCodesService.SampleType.VALID)
                .build();

        String discount = couponCodesService.createDiscount(CouponCodesService.SampleType.VALID,
                discountJdo, couponName);
        requestProperties.setCountryCode(requestCountry);

        if (vertical.equalsIgnoreCase("hotel")) {
            couponCodesService.useHotelCouponForValidation(discount);
        }
        else {
            couponCodesService.useCarCouponForValidation(discount);
        }
    }

    @And("^I validate correct (USA|UK) cross-vertical percent coupon code for (car|hotel) from (UK|US)$")
    public void generateAndReturnCrossVerticalPercentCoupon(String countryCode,
                                                            String vertical, String requestCountry) {
        String couponName = DiscountJdoBuilder.BDD_COUPON_PREFIX + System.currentTimeMillis();
        DiscountJdo discountJdo = new DiscountJdoBuilder()
                .withAmount(CROSS_VERTICAL_COUPON_VALUE)
                .withCurrency("")
                .withSiteType(MOBILE_DEVICES_SITE_TYPE)
                .withPgoodCode('X')
                .withCountryCodes(CouponCodesService.CountryCodes.getCodeByName(countryCode))
                .isFirstTimePurchase(false)
                .withCouponName(couponName)
                .withSampleType(CouponCodesService.SampleType.VALID)
                .build();
        String discount = couponCodesService.createDiscount(CouponCodesService.SampleType.VALID,
                discountJdo, couponName);
        requestProperties.setCountryCode(requestCountry);
        if (vertical.equals("hotel")) {
            couponCodesService.useHotelCouponForValidation(discount);
        }
        else {
            couponCodesService.useCarCouponForValidation(discount);
        }
    }

    @And("^I validate correct (USA|UK) cross-vertical percent coupon code which " +
            "is valid for first purchase for (car|hotel) from (UK|US)$")
    public void generateAndReturnCrossVerticalPercentCouponForFirstPurchase(String countryCode,
                                                            String vertical, String requestCountry) {
        String couponName = DiscountJdoBuilder.BDD_COUPON_PREFIX + System.currentTimeMillis();
        DiscountJdo discountJdo = new DiscountJdoBuilder()
                .withAmount(CROSS_VERTICAL_COUPON_VALUE)
                .withCurrency("")
                .withSiteType(MOBILE_DEVICES_SITE_TYPE)
                .withPgoodCode('X')
                .withCountryCodes(CouponCodesService.CountryCodes.getCodeByName(countryCode))
                .isFirstTimePurchase(true)
                .withCouponName(couponName)
                .withSampleType(CouponCodesService.SampleType.VALID)
                .build();
        String discount = couponCodesService.createDiscount(CouponCodesService.SampleType.VALID,
                discountJdo, couponName);
        requestProperties.setCountryCode(requestCountry);
        if (vertical.equals("hotel")) {
            couponCodesService.executeHotelRequestInvalidCoupon(discount, ERROR_CODE_FIRST_APP_PURCHASE);
        }
        else {
            couponCodesService.executeCarRequestInvalidCoupon(discount, ERROR_CODE_FIRST_APP_PURCHASE);
        }
    }

    @And("^I validate correct (USA|UK) (USD|GBP|EUR) cross-vertical amount coupon code for (car|hotel) from (UK|US)$")
    public void generateAndReturnCrossVerticalAmountCoupon(String countryCode, String currency,
                                                           String vertical, String requestCountry) {
        String couponName = DiscountJdoBuilder.BDD_COUPON_PREFIX + System.currentTimeMillis();
        DiscountJdo discountJdo = new DiscountJdoBuilder()
                .withAmount(CROSS_VERTICAL_COUPON_VALUE)
                .withCurrency(currency)
                .withSiteType(MOBILE_DEVICES_SITE_TYPE)
                .withPgoodCode('X')
                .withCountryCodes(CouponCodesService.CountryCodes.getCodeByName(countryCode))
                .isFirstTimePurchase(false)
                .withCouponName(couponName)
                .withSampleType(CouponCodesService.SampleType.VALID)
                .build();
        String discount = couponCodesService.createDiscount(CouponCodesService.SampleType.VALID,
                discountJdo, couponName);
        requestProperties.setCountryCode(requestCountry);
        if (vertical.equals("hotel")) {
            couponCodesService.useHotelCouponForValidation(discount);
        }
        else {
            couponCodesService.useCarCouponForValidation(discount);
        }
    }


    @And("^I see coupon details are present$")
    public void verifyBookingDatesAndLocation() {
        couponCodesService.verifyCouponDetails();
    }

    @And("^I see coupon summary of charges are present$")
    public void verifySummaryOfCharges() {
        couponCodesService.verifySummaryOfCharges();
    }

    @And("^I see coupon trip charges are present$")
    public void verifyTripCharges() {
        couponCodesService.verifyTripCharges();
    }

    @And("^I see coupon validation response is present$")
    public void verifyCouponValidationResponse() {
        couponCodesService.verifyCouponValidationResponse();
    }

    @And("^I see hotel coupon has been applied to the trip total price$")
    public void verifyHotelTotalPriceHasChanged() {
        couponCodesService.verifyHotelTotalPriceHasChanged(0);
    }

    @And("^I see currency of coupon is (USD|GBP|EUR)$")
    public void verifyCouponCurrency(String currency) {
        couponCodesService.verifyCouponCurrency(currency);
    }

    @And("^I see car coupon has been applied to the trip total price$")
    public void verifyCarTotalPriceHasChanged() {
        couponCodesService.verifyCarTotalPriceHasChanged();
    }

    @And("^I see there is no response$")
    public void verifyThatResponseIsNull() {
        couponCodesService.verifyThatResponseIsNull();
    }
}

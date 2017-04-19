/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.couponcode;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.assertions.Delta;

import com.hotwire.selenium.mobile.billing.PurchaseReviewFragment;
import com.hotwire.selenium.mobile.confirmation.MobileHotelConfirmationPage;
import com.hotwire.selenium.mobile.view.ViewIds;

/**
 * @author vjong
 *
 */
public class CouponCodeModelMobileWebApp extends CouponCodeModelTemplate {

    @Override
    public void appendGoodDccidCodeToUrl() {
        String url = applicationUrl.toString();
        getWebdriverInstance().navigate().to(
            url + (url.contains("?") ? "&" : "?") + "dccid=293AAF62C5CA0EF5C82D4F39500E9C20");
    }

    @Override
    public void applyHotelCouponCode(String couponCodeType) {
        String couponCodeId = hotelCouponCodeMap.get(couponCodeType);
        purchaseParameters.setPromoCode(couponCodeId);  // Use for later if necessary.
        String tileId = ViewIds.TILE_HOTEL_BILLING_REVIEW_DETAILS;
        PurchaseReviewFragment reviewFragment = new PurchaseReviewFragment(getWebdriverInstance(), tileId);
        purchaseParameters.setBillingTotal(reviewFragment.getTotalPrice().toString());  // Use later if necessary.
        reviewFragment.applyCouponCode(couponCodeId);
        reviewFragment = new PurchaseReviewFragment(getWebdriverInstance(), tileId);

        String statusMessage = reviewFragment.getApplyCouponCodeStatusMessage();
        if (couponCodeType.equals("valid")) {
            assertThat(statusMessage)
                .as("Expected success message but got: " + statusMessage)
                .containsIgnoringCase("Your promotion has been applied.");
        }
        else if (couponCodeType.equals("minimum 3 star rating")) {
            assertThat(statusMessage)
                .as("Expected error message but got: " + statusMessage)
                .containsIgnoringCase("This promotion is for 3-star hotels and above.");
        }
        else if (couponCodeType.equals("valid native app") || couponCodeType.equals("valid mobile")) {
            assertThat(statusMessage)
                .as("Expected error message but got: " + statusMessage)
                .containsIgnoringCase("This promotion isn't available on this type of device");
        }
        else if (couponCodeType.equals("minimum amount")) {
            assertThat(statusMessage)
                .as("Expected error message but got: " + statusMessage)
                .containsIgnoringCase("This promotion is for bookings of 10,000 or more.");
        }
        else if (couponCodeType.equals("invalid start date") || couponCodeType.equals("invalid end date")) {
            assertThat(statusMessage)
                .as("Expected error message but got: " + statusMessage)
                .containsIgnoringCase("This promo code isn't valid.");
        }
    }

    @Override
    public void verifyCouponCodeDisplayed(boolean shouldNotBeDisplayed) {
        String tileId = ViewIds.TILE_HOTEL_BILLING_REVIEW_DETAILS;
        if (shouldNotBeDisplayed) {
            assertThat(new PurchaseReviewFragment(getWebdriverInstance(), tileId).isCouponCodeModuleDisplayed())
                    .as("Expected coupon code module should be disabled.")
                    .isFalse();
        }
        else {
            assertThat(new PurchaseReviewFragment(getWebdriverInstance(), tileId).isCouponCodeModuleDisplayed())
                    .as("Expected coupon code module should be not disabled.")
                    .isTrue();
        }

    }

    @Override
    public void verifyHotelPurchaseCouponConfirmation() {
        MobileHotelConfirmationPage confirmationPage = new MobileHotelConfirmationPage(getWebdriverInstance());
        Float expectedDiscountAmount = getDiscountAmountFromCouponCodeId(hotelCouponCodeMap.get("valid"));
        Float expectedTotal = new Float(purchaseParameters.getBillingTotal().replaceAll("[^\\d,.]", ""));
        Float actualDiscount = confirmationPage.getDiscountAmount();
        Float actualTotal = confirmationPage.getTotalAmount();
        assertThat(actualDiscount)
            .as("Expected " + expectedDiscountAmount + " discount amount but got " + actualDiscount)
            .isEqualTo(expectedDiscountAmount);
        assertThat(actualTotal)
            .as("Expected " + expectedTotal + " total but got " + actualTotal)
            .isEqualTo(expectedTotal);
    }

    @Override
    public void verifyCouponCodeDiscountDisplayedInBillingTripSummary(boolean shouldBeDisplayed) {
        String tileId = ViewIds.TILE_HOTEL_BILLING_REVIEW_DETAILS;
        PurchaseReviewFragment summary = new PurchaseReviewFragment(getWebdriverInstance(), tileId);
        boolean actual = summary.isDiscountAmountDisplayed();
        assertThat(actual)
            .as("Expected discount displayed to be " + shouldBeDisplayed + " but got " + actual + " instead.")
            .isEqualTo(shouldBeDisplayed);

        if (shouldBeDisplayed) { // If true, do discount amount verification.
            Float expectedDiscountAmount = getDiscountAmountFromCouponCodeId(purchaseParameters.getPromoCode());
            Float actualDiscountAmount = summary.getDiscountAmount();
            assertThat(actualDiscountAmount)
                .as("Expected coupon code " + purchaseParameters.getPromoCode() + " of " + expectedDiscountAmount +
                    " but got " + actualDiscountAmount)
                .isEqualTo(expectedDiscountAmount);
            assertThat(new Float(purchaseParameters.getBillingTotal()) - summary.getTotalPrice())
                .as("Expected totals difference to equal discount amount.")
                .isEqualTo(actualDiscountAmount, Delta.delta(.01));
        }
    }

    @Override
    protected String getSiteType() {
        return "1001";
    }
}

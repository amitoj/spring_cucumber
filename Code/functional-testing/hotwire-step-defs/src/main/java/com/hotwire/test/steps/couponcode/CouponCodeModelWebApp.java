/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.couponcode;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.assertions.Delta;

import com.hotwire.selenium.desktop.common.billing.HotelTripSummaryFragment;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelBillingOnePage;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelBillingPromotionFragment;
import com.hotwire.selenium.desktop.us.confirm.HotelConfirmationPage;

/**
 * @author vjong
 *
 */
public class CouponCodeModelWebApp extends CouponCodeModelTemplate {

    @Override
    public void applyGeneratedCouponCode() {
        HotelBillingOnePage billing = new HotelBillingOnePage(getWebdriverInstance());
        Float beforeCouponTotal = billing.getTripSummaryFragment().getTotalPrice();
        HotelBillingPromotionFragment promo = billing.getHotelBillingPromotionFragment();
        promo.applyPromotionCode(purchaseParameters.getPromoCode());
        // Get new state of billing page.
        billing = new HotelBillingOnePage(getWebdriverInstance());
        HotelTripSummaryFragment summary = billing.getTripSummaryFragment();
        Float afterCouponTotal = summary.getTotalPrice();
        Float discount = summary.getDiscountAmount();
        assertThat(beforeCouponTotal - afterCouponTotal)
            .as("Expected discount amount to be " + discount + " but got " + (beforeCouponTotal - afterCouponTotal))
            .isEqualTo(discount, Delta.delta(.01));

        Float confirmTextTotal = new Float(billing.getConfirmPaymentText().replaceAll("[^\\d.,]", ""));
        assertThat(confirmTextTotal)
            .as("Expected total of " + afterCouponTotal + " but got " + confirmTextTotal)
            .isEqualTo(afterCouponTotal);
        // put the discounted total in bean for future use.
        purchaseParameters.setBillingTotal(afterCouponTotal.toString());
    }

    @Override
    public void verifyHotelPurchaseCouponConfirmation() {
        Float confirmTotal = new Float(
            new HotelConfirmationPage(getWebdriverInstance()).getTripTotal().replaceAll("[^\\d.,]", ""));
        Float billingTotal = new Float(purchaseParameters.getBillingTotal().replaceAll("[^\\d.,]", ""));
        assertThat(confirmTotal)
            .as("Expected confirmation total of " + confirmTotal + " to be same as billing total of " + billingTotal)
            .isEqualTo(billingTotal);
    }

    @Override
    public void applyHotelCouponCode(String couponCodeType) {
        String couponCodeId = hotelCouponCodeMap.get(couponCodeType);
        purchaseParameters.setPromoCode(couponCodeId);  // Use for later if necessary.
        HotelBillingPromotionFragment promo = new HotelBillingOnePage(getWebdriverInstance())
            .getHotelBillingPromotionFragment();
        promo.applyPromotionCode(couponCodeId);
        // Whether successful or not we should always get a message. Success and error messages use the same container.
        // This is a change in the state of the billing page and the promo fragment.
        promo = new HotelBillingOnePage(getWebdriverInstance()).getHotelBillingPromotionFragment();
        boolean gotMessage = promo.isApplyPromotionStatusMessageDisplayed();
        assertThat(gotMessage)
            .as("Expected either success or error message to be displayed.")
            .isTrue();

        String statusMessage = promo.getApplyPromotionMessages();
        if (couponCodeType.equals("valid")) {
            assertThat(statusMessage)
                .as("Expected success message but got: " + statusMessage)
                .isEqualTo("Your promotion has been applied.");
            // Store total after applying coupon code. Test to see if totals correct after applying coupon code
            // done in verifyTripSummaryTotalWithValidCouponCodeAndInsurance() method.
            purchaseParameters.setBillingTotal(
                new HotelBillingOnePage(getWebdriverInstance()).getTripSummaryFragment().getTotalPrice().toString());
        }
        else if (couponCodeType.equals("minimum 3 star rating")) {
            assertThat(statusMessage)
                .as("Expected error message but got: " + statusMessage)
                .isEqualTo("This promotion is for 3-star hotels and above.");
        }
        else if (couponCodeType.equals("valid native app") || couponCodeType.equals("valid mobile")) {
            assertThat(statusMessage)
                .as("Expected error message but got: " + statusMessage)
                .isEqualTo("This promotion isn't available on this type of device");
        }
        else if (couponCodeType.equals("minimum amount")) {
            assertThat(statusMessage)
                .as("Expected error message but got: " + statusMessage)
                .isEqualTo("This promotion is for bookings of 10,000 or more.");
        }
        else if (couponCodeType.equals("invalid start date") || couponCodeType.equals("invalid end date")) {
            assertThat(statusMessage)
                .as("Expected error message but got: " + statusMessage)
                .isEqualTo("This promo code isn't valid.");
        }
    }

    @Override
    public void verifyCouponCodeDiscountDisplayedInBillingTripSummary(boolean shouldBeDisplayed) {
        HotelTripSummaryFragment summary = new HotelBillingOnePage(getWebdriverInstance()).getTripSummaryFragment();
        boolean actual = summary.isDiscountDisplayed();
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
        }
    }

    @Override
    public void verifyTripSummaryTotalWithValidCouponCodeAndInsurance() {
        Float beforeCouponTotal = new HotelBillingOnePage(getWebdriverInstance())
            .getTripSummaryFragment().getTotalPrice();
        applyHotelCouponCode("valid");
        HotelBillingOnePage billing = new HotelBillingOnePage(getWebdriverInstance());
        HotelTripSummaryFragment summary = billing.getTripSummaryFragment();
        Float afterCouponTotal = summary.getTotalPrice();
        Float discount = summary.getDiscountAmount();
        assertThat(beforeCouponTotal - afterCouponTotal)
            .as("Expected discount amount to be " + discount + " but got " + (beforeCouponTotal - afterCouponTotal))
            .isEqualTo(discount, Delta.delta(.01));

        Float confirmTextTotal = new Float(billing.getConfirmPaymentText().replaceAll("[^\\d.,]", ""));
        assertThat(confirmTextTotal)
            .as("Expected total of " + afterCouponTotal + " but got " + confirmTextTotal)
            .isEqualTo(afterCouponTotal);
    }
}

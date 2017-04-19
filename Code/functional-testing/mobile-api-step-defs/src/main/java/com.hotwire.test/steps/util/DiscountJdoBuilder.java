/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.util;


import com.hotwire.test.steps.coupons.CouponCodesService;
import hotwire.biz.domain.fn.MoneyJdo;
import hotwire.biz.order.discount.DiscountJdo;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

/**
 * @author v-vbychkovskyy
 * @since 2015.04
 */
public class DiscountJdoBuilder {
    public static final String BDD_COUPON_PREFIX = "BDD_COUPON";

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountJdoBuilder.class);

    private String couponName;
    private boolean firstTimePurchase;
    private CouponCodesService.CountryCodes countryCodes;
    private char pGoodCode;
    private String siteType;
    private String currency;
    private int amount;
    private CouponCodesService.SampleType sampleType;

    public DiscountJdo build() {

        DiscountJdo discount = new DiscountJdo();
        discount.setEnabled(true);
        discount.setDescription("VALID discount code");
        discount.setDomainCode(1000);
        discount.setMaxUsageAmount(10000f);
        discount.setEncryptionKey(null);
        discount.setPublicInd('Y');

        discount.setpGoodCode(Character.toUpperCase(pGoodCode));

        discount.setCode("CCC_" + couponName);
        setFieldValue(discount, "coupon", couponName);

        setFieldValue(discount, "siteId", CouponCodesService.CountryCodes.getCountryCode(countryCodes));
        setFieldValue(discount, "siteTypeList", siteType);
        setFieldValue(discount, "isFirstTimePurchase", firstTimePurchase);

        populateDiscountValue(amount, currency, discount);
        populateDiscountDates(sampleType, discount);

        return discount;
    }

    public DiscountJdoBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public DiscountJdoBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public DiscountJdoBuilder withSiteType(String siteType) {
        this.siteType = siteType;
        return this;
    }

    public DiscountJdoBuilder withPgoodCode(char pGoodCode) {
        this.pGoodCode = pGoodCode;
        return this;
    }

    public DiscountJdoBuilder withCountryCodes(CouponCodesService.CountryCodes countryCodes) {
        this.countryCodes = countryCodes;
        return this;
    }

    public DiscountJdoBuilder isFirstTimePurchase(boolean firstTimePurchase) {
        this.firstTimePurchase = firstTimePurchase;
        return this;
    }

    public DiscountJdoBuilder withCouponName(String couponName) {
        this.couponName = couponName;
        return this;
    }

    public DiscountJdoBuilder withSampleType(CouponCodesService.SampleType sampleType) {
        this.sampleType = sampleType;
        return this;
    }


    private void setFieldValue(DiscountJdo discount, String fieldName, Object fieldValue) {
        try {
            Field couponField = DiscountJdo.class.getDeclaredField(fieldName);
            couponField.setAccessible(true);
            couponField.set(discount, fieldValue);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.info("Can't set value to field" + fieldName + e.getMessage());
        }
    }

    private void populateDiscountDates(CouponCodesService.SampleType sampleType, DiscountJdo discountCode) {
        Date startDate;
        Date endDate;
        switch (sampleType) {
            case VALID:
                startDate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
                endDate = DateUtils.addDays(startDate, 20);
                break;
            case EXPIRED:
                startDate = DateUtils.addDays(
                        DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), -30);
                endDate = DateUtils.addDays(startDate, 10);
                break;
            default:
                startDate = null;
                endDate = null;
        }

        discountCode.setStartDate(startDate);
        discountCode.setEndDate(endDate);
    }

    private boolean isPercentDiscount(String currency) {
        return currency.isEmpty();
    }

    private void populateDiscountValue(int amount, String currency, DiscountJdo discount) {
        if (isPercentDiscount(currency)) {
            discount.setPercent(amount);

            discount.setValue(new MoneyJdo(0, "USD"));
        }
        else {
            discount.setPercent(0);
            discount.setValue(new MoneyJdo(amount, currency));
        }
    }
}

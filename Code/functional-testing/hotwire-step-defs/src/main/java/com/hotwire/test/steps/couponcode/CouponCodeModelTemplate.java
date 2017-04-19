/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.couponcode;

import static org.fest.assertions.Assertions.assertThat;
import hotwire.biz.domain.fn.MoneyJdo;
import hotwire.biz.order.discount.DiscountJdo;
import hotwire.eis.dao.HwGenericDao;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.desktop.us.billing.onepage.HotelBillingOnePage;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelBillingPromotionFragment;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.testing.UnimplementedTestException;

/**
 * @author vjong
 *
 */
public class CouponCodeModelTemplate extends WebdriverAwareModel implements CouponCodeModel {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CouponCodeModelTemplate.class.getSimpleName());
    private static final String BDD_COUPON_PREFIX = "BDD_COUPON";

    @Autowired
    protected URL applicationUrl;

    @Autowired
    @Qualifier("purchaseParameters")
    protected PurchaseParameters purchaseParameters;

    @Resource(name = "hotelCouponCodeMap")
    protected HashMap<String, String> hotelCouponCodeMap;

    protected HwGenericDao hwGenericDaoProxy;

    public void setHwGenericDaoProxy(HwGenericDao hwGenericDaoProxy) {
        this.hwGenericDaoProxy = hwGenericDaoProxy;
    }

    /**
     * Type of coupon
     */
    public enum SampleType {
        VALID, EXPIRED, INVALID, FUTURE
    }


    /**
     * Countries for coupons
     */
    public enum CountryCodes {
        USA(1),
        UK(2);
        private final int code;

        private CountryCodes(int code) {
            this.code = code;
        }

        private int getCode() {
            return code;
        }

        public static CountryCodes getCodeByName(String name) {
            for (CountryCodes value : values()) {
                if (value.name().equals(name)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("No mapping for this country " + name);
        }

        public static int getCountryCode(CountryCodes country) {
            for (CountryCodes value : values()) {
                if (value == country) {
                    return value.getCode();
                }
            }
            throw new IllegalArgumentException("No mapping for this country " + country);
        }
    }

    @Override
    public void setCouponCode(String type) {
        String couponCode = hotelCouponCodeMap.get(type);
        LOGGER.info("Setting " + type + " coupon code in purchase params: " + couponCode);
        purchaseParameters.setPromoCode(couponCode);
    }

    @Override
    public void generateAndStoreNewCouponCode(SampleType sampleType, String couponPgood) {
        char pgood;
        if (couponPgood.equals("hotel")) {
            pgood = 'H';
        }
        else if (couponPgood.equals("car")) {
            pgood = 'C';
        }
        else {
            throw new UnimplementedTestException(couponPgood + " not implemented.");
        }

        String discount = createCouponCode(10, "USD", getSiteType(), sampleType, pgood,
                                           CountryCodes.getCodeByName("USA"));
        purchaseParameters.setPromoCode(discount);
        LOGGER.info("GENERATED COUPON CODE: " + discount);
    }

    protected String createCouponCode(int amount, String currency, String siteType, SampleType sampleType,
                                   char pGoodCode, CountryCodes countryCodes) {

        if (sampleType == SampleType.INVALID) {
            return "BOGUS_DISCOUNT";
        }

        DiscountJdo discount = new DiscountJdo();
        discount.setEnabled(true);
        discount.setDescription("VALID discount code");
        discount.setDomainCode(1000);
        discount.setMaxUsageAmount(10000f);
        discount.setEncryptionKey(null);
        discount.setPublicInd('Y');

        discount.setpGoodCode(pGoodCode);

        String couponName = BDD_COUPON_PREFIX + System.currentTimeMillis();
        discount.setCode("CCC_" + couponName);
        setFieldValue(discount, "coupon", couponName);
        setFieldValue(discount, "siteId", CountryCodes.getCountryCode(countryCodes));
        setFieldValue(discount, "siteTypeList", siteType);

        populateDiscountValue(amount, currency, discount);
        populateDiscountDates(sampleType, discount);

        // Insert it into the cache
        LOGGER.info("Before HwGenericDao.insert(DiscountJdo{pgoodCode={}, hash={}, code={}})",
                    discount.getPGoodCode(), discount.getEncryptedDiscountHash(), discount.getCode());
        hwGenericDaoProxy.insert(discount);
        LOGGER.info("After HwGenericDao.insert(DiscountJdo{pgoodCode={}, hash={}, code={}})",
                    discount.getPGoodCode(), discount.getEncryptedDiscountHash(), discount.getCode());
        DiscountJdo discountFromCache = hwGenericDaoProxy.findByKey(DiscountJdo.class, "coupon", couponName);
        LOGGER.info("Discount from cache " + discountFromCache.getCoupon());
        LOGGER.info("Discount Code from cache " + discountFromCache.getCode());

        return discountFromCache.getCoupon();
    }

    // ROW and US webapp site type is desktop main. Override for mobile webapp.
    protected String getSiteType() {
        return "1007";
    }

    @Override
    public void applyHotelCouponCode(String couponCodeType) {
        throw new UnimplementedTestException("Implement ME!");
    }

    @Override
    public void verifyCouponCodeDiscountDisplayedInBillingTripSummary(boolean shouldBeDisplayed) {
        throw new UnimplementedTestException("Implement ME!");
    }

    @Override
    public void verifyCouponCodeDisplayed(boolean shouldNotBeDisplayed) {
        new HotelBillingOnePage(getWebdriverInstance());
        boolean actual = HotelBillingPromotionFragment.isPromotionFragmentDisplayed(getWebdriverInstance());
        if (shouldNotBeDisplayed) {
            assertThat(actual)
                    .as("Expected promo fragment visibility to be " + shouldNotBeDisplayed + " but was " + actual)
                    .isFalse();
        }
        else {
            assertThat(actual)
                    .as("Expected promo fragment visibility to be " + shouldNotBeDisplayed + " but was " + actual)
                    .isTrue();
        }

    }

    @Override
    public void appendGoodDccidCodeToUrl() {
        String url = getWebdriverInstance().getCurrentUrl();
        getWebdriverInstance().navigate().to(
            url + (url.contains("?") ? "&" : "?") + "dccid=293AAF62C5CA0EF5C82D4F39500E9C20");
    }

    @Override
    public void verifyTripSummaryTotalWithValidCouponCodeAndInsurance() {
        throw new UnimplementedTestException("Implement ME!");
    }


    @Override
    public void applyGeneratedCouponCode() {
        throw new UnimplementedTestException("Implement ME!");
    }

    @Override
    public void verifyHotelPurchaseCouponConfirmation() {
        throw new UnimplementedTestException("Implement ME!");
    }

    protected void setFieldValue(DiscountJdo discount, String fieldName, Object fieldValue) {
        try {
            Field couponField = DiscountJdo.class.getDeclaredField(fieldName);
            couponField.setAccessible(true);
            couponField.set(discount, fieldValue);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.info("Can't set value to field" + fieldName + e.getMessage());
        }
    }


    protected void populateDiscountValue(int amount, String currency, DiscountJdo discount) {
        if (isPercentDiscount(currency)) {
            discount.setPercent(amount);

            discount.setValue(new MoneyJdo(0, "USD"));
        }
        else {
            discount.setPercent(0);
            discount.setValue(new MoneyJdo(amount, currency));
        }
    }

    protected boolean isPercentDiscount(String currency) {
        return currency.isEmpty();
    }

    protected void populateDiscountDates(SampleType sampleType, DiscountJdo discountCode) {
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
            case FUTURE:
                startDate = DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), 30);
                endDate = DateUtils.addDays(startDate, 10);
            default:
                startDate = null;
                endDate = null;
        }

        discountCode.setStartDate(startDate);
        discountCode.setEndDate(endDate);
    }

    protected Float getDiscountAmountFromCouponCodeId(String promoCode) {
        return new Float(promoCode.replaceAll("[^\\d.,]", ""));
    }
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.discount;

import com.hotwire.selenium.desktop.subscription.SubscriptionModuleFragment;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.selenium.desktop.us.search.MultiVerticalFareFinder;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.SessionModifingParams;
import hotwire.biz.domain.fn.MoneyJdo;
import hotwire.biz.order.discount.DiscountJdo;
import hotwire.eis.dao.HwGenericDao;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA. User: v-mzabuga Date: 7/18/12 Time: 6:41 AM To
 * change this template use File | Settings | File Templates.
 */
public class DiscountModelTemplate extends WebdriverAwareModel implements DiscountModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountModelTemplate.class);

    protected HwGenericDao hwGenericDaoProxy;

    protected URL applicationUrl;
    protected SearchParameters searchParameters;
    protected PurchaseParameters purchaseParameters;
    protected DiscountParameters discountParameters;
    protected SessionModifingParams sessionModifingParams;

    /**
     * Type of discount
     */
    protected enum SampleType {
        VALID, EXPIRED, INVALID
    }

    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public void setSessionModifingParams(SessionModifingParams sessionModifingParams) {
        this.sessionModifingParams = sessionModifingParams;
    }

    public void setPurchaseParameters(PurchaseParameters purchaseParameters) {
        this.purchaseParameters = purchaseParameters;
    }

    public void setDiscountParameters(DiscountParameters discountParameters) {
        this.discountParameters = discountParameters;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    public void selectVertical() {
        switch (searchParameters.getPGoodCode()) {
            case H:
                new MultiVerticalFareFinder(getWebdriverInstance()).chooseHotel();
                break;
            case A:
                new MultiVerticalFareFinder(getWebdriverInstance()).chooseAir();
                break;
            case C:
                new MultiVerticalFareFinder(getWebdriverInstance()).chooseCar();
                break;
            default:
                throw new RuntimeException("Invalid vertical character..");
        }
    }

    @Override
    public void addDiscountAmountToDiscountParameters(String discount) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setupInValidCode() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setupValidCode() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setupExpiredCode() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmDiscountOnTripSummary(String negateString) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmDiscountNotificationOnResultsPage(String negation) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmDiscountAppliedToOrder(String negateString) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectQuote() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToLandingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmDiscountNotificationOnLandingPage(String negation) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToLandingPageWithNewCode() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmNewDiscountNotificationOnLandingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    protected String createDiscount(int amount, String currency, SampleType sampleType) {

        if (sampleType == SampleType.INVALID) {
            return "BOGUS_DISCOUNT";
        }

        DiscountJdo discountCode = new DiscountJdo();
        discountCode.setEnabled(true);
        discountCode.setDescription("VALID discount code");
        discountCode.setDomainCode(1000);
        discountCode.setMaxUsageAmount(10000f);
        discountCode.setEncryptionKey(null);
        discountCode.setPublicInd('Y');
        discountCode.setpGoodCode(searchParameters.getPGoodCode().toChar());

        String hash = shortenDiscountHash();
        discountCode.setEncryptedDiscountHash(hash);
        discountCode.setCode(("BDD_" + hash).substring(0, 19));

        populateDiscountValue(amount, currency, discountCode);
        populateDiscountDates(sampleType, discountCode);

        populateDiscountConditionIfExists(discountCode);

        // Insert it into the cache
        LOGGER.info("Before HwGenericDao.insert(DiscountJdo{pgoodCode={}, hash={}, code={}})",
                    discountCode.getPGoodCode(), discountCode.getEncryptedDiscountHash(), discountCode.getCode());
        hwGenericDaoProxy.insert(discountCode);
        LOGGER.info("After HwGenericDao.insert(DiscountJdo{pgoodCode={}, hash={}, code={}})",
                    discountCode.getPGoodCode(), discountCode.getEncryptedDiscountHash(), discountCode.getCode());
        hwGenericDaoProxy.findByKey(DiscountJdo.class, "encryptedDiscountHash", hash);
        LOGGER.info("After HwGenericDao.findByKey(DiscountJdo.class, \"encryptedDiscountHash\", {})", hash);

        return hash;
    }

    private void populateDiscountDates(SampleType sampleType, DiscountJdo discountCode) {
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

    private void populateDiscountValue(int amount, String currency, DiscountJdo discountCode) {
        if (isPercentDiscount(currency)) {
            discountCode.setPercent(amount);

            String activeProfile = System.getProperty("spring.profiles.active");
            if (activeProfile.equalsIgnoreCase("RowWebApp")) {
                discountCode.setValue(new MoneyJdo(0, "GBP"));
            }
            else {
                discountCode.setValue(new MoneyJdo(0, "USD"));
            }
            discountParameters.setIsPercentDiscount(true);
        }
        else {
            discountCode.setPercent(0);
            discountCode.setValue(new MoneyJdo(amount, currency));
            discountParameters.setIsPercentDiscount(false);
        }
        discountParameters.setCurrentDiscountAmount(Integer.toString(amount));
    }

    private boolean isPercentDiscount(String currency) {
        return currency.isEmpty();
    }

    private String shortenDiscountHash() {
        String hash = "";

        //shorten the hash for testing purposes
        try {
            hash = printHexBinary(
                    MessageDigest.getInstance("MD5").digest(parseHexBinary(toHexString(getRandomString()))))
                    .substring(0, 32);
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            ex.printStackTrace();
        }
        return hash;
    }

    private void populateDiscountConditionIfExists(DiscountJdo discountCode) {
        if (discountParameters.getDiscountCondition() != null) {
            int conditionValue = discountParameters.getConditionValue();

            switch (discountParameters.getDiscountCondition()) {
                case MIN_TOTAL:
                    discountCode.setMinimumAmount(conditionValue);
                    break;
                case MIN_RATING:
                    discountCode.setMinStarRatingCode(conditionValue);
                    break;
                case MAX_DTA:
                    discountCode.setMaxDTD(conditionValue);
                    break;
                default:
                    throw new UnimplementedTestException("Implement me!");
            }
        }
    }

    private String toHexString(String string) {
        return String.format("%040x", new BigInteger(1, string.getBytes()));
    }

    private String getRandomString() {
        return "BDD_AUTO_SAMPLE" + Long.valueOf(Calendar.getInstance().getTimeInMillis())
                .toString() + Double.valueOf(Math.random()).toString();
    }

    protected String createDiscount(SampleType sampleType) {
        return createDiscount(20, "USD", sampleType);
    }

    public void setHwGenericDaoProxy(HwGenericDao hwGenericDaoProxy) {
        this.hwGenericDaoProxy = hwGenericDaoProxy;
    }

    @Override
    public void confirmDiscountBannerInDetails(boolean isDisplayed) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setupFixedAmountDiscountCode(int value, String currency) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setupPercentDiscountCode(int percent) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDiscountConditionOnLanding() {
        throw new UnimplementedTestException("Implement me!");
    }

    /**
     * Basic validation of subscription module for converged hotel landing page. This will work for intl and domestic.
     * Mobile will throw it's pending exception.
     */
    @Override
    public void verifySubscriptionModuleOnConvergedHotelLandingForGuestUser(boolean isDisplayed, String pos) {
        SubscriptionModuleFragment subscription =
            new HotelIndexPage(getWebdriverInstance()).getSubscriptionModuleFragment();
        boolean subscriptionVisible = subscription.isSubscriptionLayerDisplayed();
        assertThat(subscriptionVisible)
            .as("Expected visibility of subscription module to be " + isDisplayed + " but was " + subscriptionVisible)
            .isEqualTo(isDisplayed);
        assertThat(subscription.subscriptionEmailIsDisplayed())
            .as("Subscription Email input is not displayed.")
            .isTrue();
        assertThat(subscription.subscriptionZipCodeIsDisplayed())
            .as("Subscription zip code is not displayed.")
            .isTrue();
        if (pos.equals("international")) {
            assertThat(subscription.subscriptionLayerHasCoupon())
                .as("International POS should have no coupon.")
                .isFalse();
        }
        else {
            assertThat(subscription.subscriptionLayerHasCoupon())
                .as("Domestic POS should have coupon.")
                .isTrue();
        }
    }

    /**
     * Subscribe from the subscription module for converged hotel landing page. This will work for intl and domestic.
     * Mobile will throw it's pending exception.
     */
    @Override
    public void subscribeWithEmailAndZipcode(String email, String zipCode) {
        new HotelIndexPage(getWebdriverInstance()).getSubscriptionModuleFragment().subscribe(email, zipCode);
    }

    /**
     * Verify subscription is successful. This will work for intl and domestic.
     */
    @Override
    public void verifySubscriptionIsSuccessful(boolean isSuccessful) {
        boolean subscriptionState = new HotelIndexPage(getWebdriverInstance()).getSubscriptionModuleFragment()
            .isSubscriptionSuccessful();
        assertThat(subscriptionState)
            .as("Expected subscription to be " + isSuccessful + ", but was " + subscriptionState)
            .isEqualTo(isSuccessful);
        if (isSuccessful) {
            // Subscription module will create a user that is logged in. We're saving this session somewhere other than
            // cookie. Log out manually.
            getWebdriverInstance().navigate().refresh();
            new HotelIndexPage(getWebdriverInstance()).getGlobalHeader().logoutOfAccount();
        }
    }

    @Override
    public void verifyLandingPage() {
        throw new UnimplementedTestException("Implement me!");
    }
}

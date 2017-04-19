/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.accessibility;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.mobile.billing.MobileBillingPg;
import com.hotwire.selenium.mobile.details.MobileHotelDetailsPage;
import com.hotwire.selenium.mobile.results.MobileHotelResultsPage;
import com.hotwire.selenium.mobile.results.SearchSolution;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.PageName;


/**
 * @author vjong
 */
public class AccessibilityModelMobileWebApp extends AccessibilityModelTemplateWebApp {

    @Override
    public void chooseHotelResultsAccessibilitySolution() {
        MobileHotelResultsPage mobileHotelResultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        List<SearchSolution> searchSolutionList = mobileHotelResultsPage.getSearchSolutionList();
        for (int i = 0; i < searchSolutionList.size(); i++) {
            new MobileHotelResultsPage(getWebdriverInstance()).select(i);
            if (getWebdriverInstance().findElements(By.cssSelector(".priceChangeMssg")).size() > 0 &&
                    new PageName().apply(getWebdriverInstance()).contains(".results")) {
                // Got no rooms for this solution and we only care if we're put back to results page.
                new MobileHotelDetailsPage(getWebdriverInstance()).goBackToResults();
                continue;
            }
            MobileHotelDetailsPage mobileHotelDetailsPage = new MobileHotelDetailsPage(getWebdriverInstance());
            if (mobileHotelDetailsPage.isAdditionalAdaFeaturesDisplayed()) {
                return;
            }
            else {
                mobileHotelDetailsPage.goBackToResults();
            }
        }

        throw new ZeroResultsTestException("0 hotels that had accessibility amenities found.");
    }

    @Override
    public void clickAccessibilityLink() {
        new MobileBillingPg(getWebdriverInstance()).expandAccessibilityNeeds();
    }

    @Override
    public void verifyBillingPageAccessibilityOptions() {
        MobileBillingPg billingPage = new MobileBillingPg(getWebdriverInstance());
        boolean isDisplayed = billingPage.isAccessibilityOptionsDisplayed();
        assertThat(isDisplayed)
            .as("Accessibility options on billing page were not displayed.")
            .isTrue();
        List<String> texts = billingPage.getAccessibilityTexts();
        for (String text : texts) {
            LoggerFactory.getLogger(AccessibilityModelMobileWebApp.class.getSimpleName())
                    .info("Accessibility text: " + text);
            assertThat(StringUtils.isEmpty(text))
                .as("Labels for accessibility checkboxes should be visible, but wasn't.")
                .isFalse();
        }
    }
}

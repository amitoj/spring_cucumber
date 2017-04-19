/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.accessibility;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.us.billing.onepage.HotelBillingOnePage;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelTravelerInfoFragment;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.ZeroResultsTestException;

/**
 * @author vjong
 */
public class AccessibilityModelTemplateWebApp extends WebdriverAwareModel implements AccessibilityModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessibilityModelWebApp.class.getSimpleName());

    @Override
    public void chooseHotelResultsAccessibilitySolution() {
        List<String> accessibilityHrefs = new HotelResultsPage(getWebdriverInstance()).chooseOpaque()
            .getSearchResultsFragment().getAccessibilitySearchResultsHrefList();
        if (accessibilityHrefs.size() == 0) {
            throw new ZeroResultsTestException("0 hotels have accessibility amenities or 0 opaque search results.");
        }
        else {
            getWebdriverInstance().navigate().to(accessibilityHrefs.get(0));
        }
    }

    /**
     * Click accessibility needs link on hotel One Page Billing.
     */
    @Override
    public void clickAccessibilityLink() {
        new HotelBillingOnePage(getWebdriverInstance()).getTravelerInfoFragment().clickAccessibilityLink();
    }

    /**
     * Verify accessibility options on hotel One Page Billing.
     */
    @Override
    public void verifyBillingPageAccessibilityOptions() {
        HotelTravelerInfoFragment travelerInfoFragment = new HotelBillingOnePage(getWebdriverInstance())
            .getTravelerInfoFragment();
        assertThat(travelerInfoFragment.isAccessibilityListDisplayed())
            .as("Expected accessibility list to be displayed but it wasn't.")
            .isTrue();

        List<String> texts = travelerInfoFragment.getAccessibilityTexts();
        for (String text : texts) {
            LOGGER.info("Accessibility text: " + text);
            assertThat(StringUtils.isEmpty(text))
                .as("Labels for accessibility checkboxes should be visible, but wasn't.")
                .isFalse();
        }
        for (WebElement checkbox : travelerInfoFragment.getAccessibilityCheckboxElements()) {
            assertThat(checkbox.isDisplayed())
                .as("Billing accessibility checkboxes should be visible, but wasn't.")
                .isTrue();
        }
    }
}

/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.partners;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.JavascriptExecutor;

import com.hotwire.selenium.desktop.row.SubscriptionModule;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.testing.UnimplementedTestException;

/**
 * PartnersModel implementation for RowWebApp profile.
 */
public class PartnersModelRowWebApp extends PartnersModelTemplate {

    private AuthenticationParameters authenticationParameters;

    @Override
    public void subscribeToHotwireDeals() {
        SubscriptionModule subscriptionModule = new SubscriptionModule(this.getWebdriverInstance());
        assertThat(subscriptionModule.verifyPlacementCode()).isTrue();
        subscriptionModule.subscribe(authenticationParameters.getUsername());
    }

    @Override
    public void verifySubscriptionConfirmation() {
        SubscriptionModule subscriptionModule = new SubscriptionModule(this.getWebdriverInstance());
        assertThat(subscriptionModule.isConfirmationDisplayed()).isTrue();
    }

    public void setAuthenticationParameters(AuthenticationParameters authenticationParameters) {
        this.authenticationParameters = authenticationParameters;
    }

    @Override
    public void clickVerticalTab(String vertical) {
        // No op for convergence.
    }

    @Override
    public void verifyGoogleConvergencePixel(int daysToArrival) {
        String label;
        if (daysToArrival <= 7) {
            label = "LsnwCOzUpwUQnPXG1gM";
        }
        else if (daysToArrival <= 14) {
            label = "pVERCOy-lwUQnPXG1gM";
        }
        else if (daysToArrival <= 21) {
            label = "Qrc_COTVpwUQnPXG1gM";
        }
        else if (daysToArrival <= 30) {
            label = "MAavCPTTpwUQnPXG1gM";
        }
        else {
            throw new UnimplementedTestException("Days to arrival should be between 1 and 30 days!");
        }

        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        resultsPage.waitForUpdatingLayer();
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getWebdriverInstance();
        String script = "return $('div#tileName-3rdPartyPixelTracking script').text();";
        String beacons = javascriptExecutor.executeScript(script).toString();
        for (String beacon : beacons.split("\\n")) {
            if (beacon.contains("googleads.g.doubleclick.net")) {
                assertThat(beacon).contains("/986823324/");
                assertThat(beacon).contains(String.format("label=%s", label));
                return;
            }
        }
        throw new AssertionError("Google Convergence pixel is not present!");
    }
}

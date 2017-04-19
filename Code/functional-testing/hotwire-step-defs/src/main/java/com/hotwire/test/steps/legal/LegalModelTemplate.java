/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.legal;

import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-ngolodiuk
 * Since: 4/22/14
 */
public class LegalModelTemplate extends WebdriverAwareModel implements LegalModel {

    protected static final String RETAIL_RATES_DISCLAIMER_TEXT =
        "* Rates are shown in US dollars. " +
        "Total cost for retail rate reservations is an estimate based on the agency’s published rates, " +
        "taxes and fees, and are subject to change. " +
        "There is no guarantee that this price will be in effect at the time of your booking.";

    protected static final String OPAQUE_RATES_DISCLAIMER_TEXT =
        "* Rates are shown in US dollars. " +
        "Total cost for Hotwire Rates includes applicable tax recovery charges and fees.";

    @Override
    public void goToLegalPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyLegalPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void goToPrivacyPolicyPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void goToTermsOfUsePage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void goToRulesAndRestrictioinsPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void goToLowPriceGuaranteePage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickBackButton() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyLegalDetailsPage(String pageName) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyText(String text, boolean textPresent) {
        if (textPresent) {
            assertThat(getWebdriverInstance().getPageSource())
                .as("Page does not contain the following text: " + text)
                .containsIgnoringCase(text);
        }
        else {
            assertThat(getWebdriverInstance().getPageSource())
                .as("Text " + text + " is present on the page while should not.")
                .doesNotContain(text);
        }
    }

    @Override
    public void verifyCopiesOnSearchResults() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCopiesOnDetails() {
        throw new UnimplementedTestException("Not implemented yet");
    }
}

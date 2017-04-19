/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.external.AH;

import com.hotwire.selenium.desktop.us.results.AHResultsPage;
import com.hotwire.selenium.desktop.us.results.ExternalAHPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

/**
 * @author v-jhernandeziniguez
 *
 */
public class ExternalAHModelTemplate extends WebdriverAwareModel implements ExternalAHModel {
    @Override
    public ExternalAHPage accessDisambiguationPage(String baseUrl) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public AHResultsPage redirectedToResults() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public String getCurrentUrlAndAddDates(String departureDate, String returnDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setDatesFromNow(int departureDays, int returnDays) {
        throw new UnimplementedTestException("Implement me!");
    }
}

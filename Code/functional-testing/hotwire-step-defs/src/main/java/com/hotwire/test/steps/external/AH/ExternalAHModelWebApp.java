/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.external.AH;

import com.hotwire.selenium.desktop.us.results.AHResultsPage;
import com.hotwire.selenium.desktop.us.results.ExternalAHPage;

/**
 * @author v-jhernandeziniguez
 *
 */
public class ExternalAHModelWebApp extends ExternalAHModelTemplate {

    @Override
    public ExternalAHPage accessDisambiguationPage(String baseUrl) {
        ExternalAHPage page = new ExternalAHPage(getWebdriverInstance());
        getWebdriverInstance().get(baseUrl);
        return page;
    }

    @Override
    public AHResultsPage redirectedToResults() {
        return new AHResultsPage(getWebdriverInstance());
    }

    @Override
    public String getCurrentUrlAndAddDates(String departureDate, String returnDate) {
        String replacedDeparture = getWebdriverInstance().getCurrentUrl().replace("01/01/2015", departureDate);
        String replacedReturn = replacedDeparture.replace("01/05/2015", returnDate);
        getWebdriverInstance().get(replacedReturn);
        return replacedReturn;
    }
}

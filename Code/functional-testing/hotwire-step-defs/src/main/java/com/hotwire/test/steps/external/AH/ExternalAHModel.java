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
public interface ExternalAHModel {

    ExternalAHPage accessDisambiguationPage(String baseUrl);

    AHResultsPage redirectedToResults();

    String getCurrentUrlAndAddDates(String departureDate, String returnDate);

    void setDatesFromNow(int departureDays, int returnDays);

}

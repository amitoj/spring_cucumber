/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.riskManagement;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-vyulun on 6/25/2014.
 */

public class C3HotelReservationInfoPage extends ToolsAbstractPage {

    private static final String REFUND_BUTTON = "input[name='refundButton']";
    private static final String DEACTIVATE_ACCOUNT_BUTTON = "input[name='deactivateButton']";
    private static final String SEARCH_ACCOUNT_BUTTON = "input[name='searchButton']";
    private static final String LINK_ANALYSIS_BUTTON = "input[name='linkAnalysisButton']";
    private static final String BLOCK_EMAIL_AND_CARD_BUTTON = "input[name='BlockEmailAndCards']";

    public C3HotelReservationInfoPage(WebDriver webDriver) {
        super(webDriver, By.xpath("//td[text()='Hotel Reservation Information']"));
    }

    public void createNote(String note) {
        String reviewProcess = "Verified by Other";
        String reviewStatus = "Not Reviewed";
        String itineraryStatus = "Verified";
        findOne("#reviewProcess", DEFAULT_WAIT).sendKeys(reviewProcess);
        findOne("#reviewStatus", DEFAULT_WAIT).sendKeys(reviewStatus);
        findOne("#itineraryStatus", DEFAULT_WAIT).sendKeys(itineraryStatus);
        findOne("#note", DEFAULT_WAIT).sendKeys(note);
        logger.info("Try to create case note - " + note);
        findOne(".displayButton", DEFAULT_WAIT).click();
    }

    public void verifyCaseNoteByText(String note) {
        verifyTextOnPage(note);
    }


    public void verifyActionsButtons() {
        findOne(REFUND_BUTTON, DEFAULT_WAIT);
        findOne(DEACTIVATE_ACCOUNT_BUTTON, DEFAULT_WAIT);
        findOne(SEARCH_ACCOUNT_BUTTON, DEFAULT_WAIT);
        findOne(LINK_ANALYSIS_BUTTON, DEFAULT_WAIT);
        findOne(BLOCK_EMAIL_AND_CARD_BUTTON, DEFAULT_WAIT);
    }


}

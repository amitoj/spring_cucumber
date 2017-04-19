/*
 * Copyright 2014 Hotwire. All Rights Reserved. *
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.post.purchase;

import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-ngolodiuk
 * Since: 8/21/14
 */

public class PostPurchaseModelTemplate extends WebdriverAwareModel implements PostPurchaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostPurchaseModelTemplate.class.getName());

    @Autowired
    @Qualifier("searchParameters")
    protected SearchParameters searchParameters;

    @Autowired
    @Qualifier("applicationModel")
    protected ApplicationModel applicationModel;



    @Override
    public void clickToSeePrintVersion() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPrintVersionPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCancelButtonShareItineraryWindow() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void shareCarItineraryViaEmail(String email) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void shareItineraryWithBadAndGoodEmails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void shareItineraryForMultiplicityEmails(String firstEmail, String secondEmail) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPrintTripDetailsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    public void freeze(double seconds) {
        long mils = (long) seconds * 1000;
        try {
            Thread.sleep(mils);
        }
        catch (InterruptedException e) {
            LOGGER.info("Error while freezing WebDriver!");
        }
    }
}

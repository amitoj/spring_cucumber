/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.help;

import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

/**
 * @author
 *
 */
public abstract class HelpCenterModelTemplate extends WebdriverAwareModel implements HelpCenterModel {

    @Override
    public void openHelpCenterPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHelpCenterPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void openQASearchingAndBookingPage(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyQASearchingAndBookingPage(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void accessTravelFormalities() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTravelFormalities() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCenterAvailabilityHours(String countryName, String supportHours) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyEmailMsgWithinHotwireCustomerCareModuleForCountry(String country) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHoursOfAvailabilityMsgWithinHotwireCustomerCareModuleForCountry(String country) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPhoneContactsWithinHotwireCustomerCareModuleForCountry(String country) {
        throw new UnimplementedTestException("Implement me!");
    }
}

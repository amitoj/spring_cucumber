/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.help;

public interface HelpCenterModel {

    void openHelpCenterPage();

    void verifyHelpCenterPage();

    void openQASearchingAndBookingPage(String vertical);

    void verifyQASearchingAndBookingPage(String vertical);

    void accessTravelFormalities();

    void verifyTravelFormalities();

    void verifyCenterAvailabilityHours(String countryName, String supportHours);

    void verifyEmailMsgWithinHotwireCustomerCareModuleForCountry(String country);

    void verifyHoursOfAvailabilityMsgWithinHotwireCustomerCareModuleForCountry(String country);

    void verifyPhoneContactsWithinHotwireCustomerCareModuleForCountry(String country);
}

/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.about.us;

/**
 * About us Model
 * @author psuryadevara
 */
public interface AboutUsModel {

    void openAboutUsPage();

    void verifyTexts();

    void verifyCompanyLinks();

    void verifyLegalLinks();

    void verifyHotelPartnerOverviewLink();

    void verifyPressReleaseLink();

    void verifyAffiliatesLink();

}

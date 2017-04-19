/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.about.us;

import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

/**
 * @author psuryadevara
 */
public abstract class AboutUsModelTemplate extends WebdriverAwareModel implements AboutUsModel {

    @Override
    public void openAboutUsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTexts() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCompanyLinks() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyLegalLinks() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotelPartnerOverviewLink() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPressReleaseLink() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAffiliatesLink() {
        throw new UnimplementedTestException("Implement me!");
    }

}

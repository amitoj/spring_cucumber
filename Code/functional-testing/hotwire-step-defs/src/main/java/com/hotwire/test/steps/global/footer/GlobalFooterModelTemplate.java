/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.global.footer;

import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

/**
 * @author psuryadevara
 */
public class GlobalFooterModelTemplate extends WebdriverAwareModel implements GlobalFooterModel {

    @Override
    public void openContactUsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySendEmailText() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void openSiteMapPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotwireProducts() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyMyAccount() {
        throw new UnimplementedTestException("Implement me!");

    }

    @Override
    public void verify_hotwire_legal_declaimer() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verify_hotwire_copyright_info() {
        throw new UnimplementedTestException("Implement me!");
    }

}

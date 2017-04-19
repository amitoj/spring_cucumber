/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.escapeToEurope.rome;

import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

/**
 *
 * @author jhernandez
 *
 */
public class RomeModelTemplate extends WebdriverAwareModel implements RomeModel {

    @Override
    public void openGlobalFooter() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void openSiteMapPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void openRomePage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateText(String arg1) {
        throw new UnimplementedTestException("Implement me!");
    }

}

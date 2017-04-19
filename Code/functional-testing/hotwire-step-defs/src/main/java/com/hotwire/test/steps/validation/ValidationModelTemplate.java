/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.validation;

import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

/**
 * User: v-ngolodiuk
 * Since: 8/20/14
 */

public class ValidationModelTemplate extends WebdriverAwareModel implements ValidationModel {

    @Override
    public void primaryDriverFieldTesting() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void primaryDriverAgeValidation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void primaryPhoneNumberTesting() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void emailAddressFieldTesting() {
        throw new UnimplementedTestException("Implement me!");
    }
}

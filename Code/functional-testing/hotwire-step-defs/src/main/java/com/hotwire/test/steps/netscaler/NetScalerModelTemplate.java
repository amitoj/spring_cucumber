/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.netscaler;

import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

public class NetScalerModelTemplate extends WebdriverAwareModel implements NetScalerModel {

    @Override
    public void verifyLocalDateFormat(String format) {
        throw new UnimplementedTestException("Implement me!");
    }
}

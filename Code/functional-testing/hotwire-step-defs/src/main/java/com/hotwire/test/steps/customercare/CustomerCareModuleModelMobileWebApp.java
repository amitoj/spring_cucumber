/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.customercare;

import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

public class CustomerCareModuleModelMobileWebApp extends WebdriverAwareModel implements CustomerCareModuleModel {

    @Override
    public void verifyCustomerCareModules() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectResultsLiveChat() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void cancelLivePreChat() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyLivePreChat(boolean isDisplayed) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void assertResultsLiveChatDisplayState(boolean displayState) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void veriyfDetailsCustomerCareModules() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectDetailsLiveChat() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void assertDetailsLiveChatDisplayState(boolean displayState) {
        throw new UnimplementedTestException("Implement me!");
    }
}

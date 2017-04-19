/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.customercare;

public interface CustomerCareModuleModel {

    void verifyCustomerCareModules();

    void selectResultsLiveChat();

    void cancelLivePreChat();

    void verifyLivePreChat(boolean isDisplayed);

    void assertResultsLiveChatDisplayState(boolean displayState);

    void veriyfDetailsCustomerCareModules();

    void selectDetailsLiveChat();

    void assertDetailsLiveChatDisplayState(boolean displayState);
}

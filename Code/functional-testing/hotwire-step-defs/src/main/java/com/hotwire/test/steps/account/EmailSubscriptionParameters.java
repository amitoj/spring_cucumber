/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.account;

import java.util.Map;

public interface EmailSubscriptionParameters {

    Map<String, Boolean> getSubscriptionMap();

    void setSubscriptionMap(Map<String, Boolean> subscriptionMap);

    boolean getUnsubscribeAll();

    void setUnsubscribeAll(boolean unsubscribeAll);
}

/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.account;

import java.util.Map;

public class EmailSubscriptionParametersImpl implements EmailSubscriptionParameters {

    private Map<String, Boolean> subscriptionMap;
    private boolean unsubscribeAll;

    @Override
    public Map<String, Boolean> getSubscriptionMap() {
        return subscriptionMap;
    }

    @Override
    public void setSubscriptionMap(Map<String, Boolean> subscriptionMap) {
        this.subscriptionMap = subscriptionMap;
    }

    @Override
    public boolean getUnsubscribeAll() {
        return this.unsubscribeAll;
    }

    @Override
    public void setUnsubscribeAll(boolean unsubscribeAll) {
        this.unsubscribeAll = unsubscribeAll;
    }

}

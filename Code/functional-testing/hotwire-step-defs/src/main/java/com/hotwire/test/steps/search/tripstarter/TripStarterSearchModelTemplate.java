/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.tripstarter;

import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.testing.UnimplementedTestException;

/**
 * class TripStarterSearchModelTemplate
 */
public class TripStarterSearchModelTemplate extends SearchModelTemplate implements TripStarterSearchModel {

    public void launchSearch(String originLocation, String destinationLocation) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDetailsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyWhenToGoModule() {
        throw new UnimplementedTestException("Implement me!");
    }
}

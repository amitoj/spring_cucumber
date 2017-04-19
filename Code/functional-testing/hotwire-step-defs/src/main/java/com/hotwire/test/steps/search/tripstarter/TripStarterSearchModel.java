/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.tripstarter;

import com.hotwire.test.steps.search.SearchModel;

/**
 * interface TripStarterSearchModel
 */
public interface TripStarterSearchModel extends SearchModel {

    void launchSearch(String originLocation, String destinationLocation);

    void verifyDetailsPage();

    void verifyWhenToGoModule();
}

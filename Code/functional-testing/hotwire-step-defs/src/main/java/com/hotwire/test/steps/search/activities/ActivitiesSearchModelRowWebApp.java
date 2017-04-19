/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.activities;

import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.testing.UnimplementedTestException;

public class ActivitiesSearchModelRowWebApp extends SearchModelTemplate<ActivitiesSearchParameters>
    implements ActivitiesSearchModel {

    @Override
    public void verifyVacationPage(String destination) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyErrorMessage() {
        throw new UnimplementedTestException("Implement me!");
    }

}

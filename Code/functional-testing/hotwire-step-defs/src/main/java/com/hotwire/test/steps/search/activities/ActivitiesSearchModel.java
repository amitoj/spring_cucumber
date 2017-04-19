/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.activities;

import com.hotwire.test.steps.search.SearchModel;

public interface ActivitiesSearchModel extends SearchModel {

    void verifyVacationPage(String destination);

    void verifyErrorMessage();

}

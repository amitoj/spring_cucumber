/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.activities;

import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.search.BaseSearchParameters;

public class ActivitiesSearchParametersImp extends BaseSearchParameters implements ActivitiesSearchParameters {

    @Override
    protected PGoodCode getPGoodCode() {
        return PGoodCode.V;
    }
}

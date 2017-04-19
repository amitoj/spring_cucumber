/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search;

import com.hotwire.test.steps.common.PGoodCode;

/**
 * @author v-mzabuga
 * @since 7/17/12
 */
public abstract class BaseSearchParameters {

    private SearchParameters globalSearchParameters;

    public void setGlobalSearchParameters(SearchParameters globalSearchParameters) {
        this.globalSearchParameters = globalSearchParameters;
    }

    public SearchParameters getGlobalSearchParameters() {
        globalSearchParameters.setPGoodCode(getPGoodCode());
        return globalSearchParameters;
    }

    protected abstract PGoodCode getPGoodCode();

}

/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search;

import hotwire.view.jaxb.domain.mobileapi.search.SearchRS;

/**
 * User:  v-vbychkovskyy
 * Since: 01/20/15
 */
public class SearchResponseHolder {
    private SearchRS response;

    public SearchRS getResponse() {
        return response;
    }

    public void setResponse(SearchRS response) {
        this.response = response;
    }
}

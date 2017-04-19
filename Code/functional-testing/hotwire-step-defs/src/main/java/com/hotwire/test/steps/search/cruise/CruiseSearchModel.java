/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.cruise;

import com.hotwire.test.steps.search.SearchModel;

/**
 * @author akrivin
 */
public interface CruiseSearchModel extends SearchModel {

    String SEARCH_RESULTS_CSC_WEB_URL_PREFIX = "http://www.hotwire.cruiseshipcentersintl.com/Search.aspx?";
    String DETAILS_CSC_WEB_URL_PREFIX = "http://cruise.hotwire.com/Itinerary.aspx?item=";

    void navigateToCruisePage();

    void clickLastMinuteCruiseDeal();

    void clickTopCruiseDeal();

    void verifyCruiseSearchResultsUrl();

    void verifyCruiseDetailsUrl();

    void verifyOpinionLink();

    //need this method to navigate back to applicationURL without logging out
    void navigateBackFromCruiseResultsPage();
}

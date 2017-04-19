/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.packages;

import com.hotwire.test.steps.search.SearchModel;

/**
 * Created by IntelliJ IDEA.
 * User: akrivin
 */
public interface PackagesSearchModel extends SearchModel {

    String PACKAGE_SEARCH_RESULTS_WEB_URL_PREFIX = "http://vacation.hotwire.com/";

    void verifyPackagesResultsPage();

    void invokeHotelPartialStay();

    void addMoreDestinations();

    void verifyAddMoreDestinationResults();

    void clickVacationPackageRadioButton(String vacationPackage);

    void showAirPlusCar();

    void accessExternalLink(String sURI);

    void verifyVacationURL(String destination, String origin);

    void verifyAHCVacationSearchInDBForUser(String userName);

    void launchExternalAHCSearch();

    void verifyDefaultChildAgeIs11();

    void launchExternalCSearchToThemePage();

    void verifyPackageThemePage(String origination, String destination);

    void startPackageThemeSearchWithoutSpecifyingParameters();

    void verifyODPairsOnPackagesResultsPage(String origination, String destination);

    void corfirmErrorMsgIsReceivedAs(String errMsg);
}

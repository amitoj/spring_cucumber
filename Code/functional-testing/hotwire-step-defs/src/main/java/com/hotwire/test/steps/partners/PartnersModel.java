/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.partners;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cucumber.api.DataTable;

/**
 * Created by IntelliJ IDEA.
 * User: vjong
 * Date: Jul 5, 2012
 * Time: 9:22:45 AM
 */
public interface PartnersModel {

    void verifyPartnerBannerIsDisplayed(String partner);

    void clickVerticalTab(String vertical);

    void navigatePartnersLink(String partner);

    void verifyPartnerDealInResults();

    void subscribeToHotwireDeals();

    void verifySubscriptionConfirmation();

    void followSideDoorUrl() throws UnsupportedEncodingException;

    /**
     * Method verifies state of partner's checkboxes in search fragment
     * @param partnerName - {CarRentals.com | Expedia}
     * @param state - {checked | unchecked | hidden}
     */
    void verifyCarCompareWithPartnersCheckboxesState(String partnerName, String state);

    /**
     * Check visibility state of car partner's popup window
     * @param partnerName String
     * @param isVisible boolean
     */
    void verifyPartnerPopupVisibility(String partnerName, boolean isVisible);

    /**
     * Check visibility state of advertising of our suppliers on different pages
     * @param page String
     * @param isVisible boolean
     */
    void verifySupplierAdOnPage(String page, boolean isVisible);

    /**
     * Selecting partners to compare with our rates
     * @param partners List<String> - partners to select
     * @param blockId String - {D2C module|fare finder}
     */
    void selectPartnersToCompareWith(List<String> partners, String blockId);

    /**
     * Checks that search options from partner's site are the same as Hotwire's.
     * @param params DataTable with
     * | Partner name    | Location                                                |
     * | CarRentals.com  | John F. Kennedy International Airport - New York (JFK)  |
     */
    void verifyCarPartnerSearchOptions(DataTable params);

    /**
     * Checks state of car's retailers checkboxes
     */
    void allCarRetailersSelected(boolean state);

    /**
     * Click on CompareWith for D2C module
     */
    void compareAllRetailersFromD2C(String numberOfModule);

    /**
     * Checks carRentals banner on car results page
     * @param bannerType String {static|dynamic}
     */
    void verifyCarRentalsBannerOnResultsPage(String bannerType);

    void verifyTripAdvisorBadge(boolean isDisplayed);

    void verifyCarPartnerIsDisplayed(String partner, String visibility);

    void verifyGoogleConvergencePixel(int daysToArrival);

    void  verifyDestinationCitiesPage();

    void verifyMeSoAds(String page);

    void selectAllCarRetailers();

}

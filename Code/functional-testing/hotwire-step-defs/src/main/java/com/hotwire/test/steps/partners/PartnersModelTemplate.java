/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.partners;

import static org.fest.assertions.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

import cucumber.api.DataTable;

/**
 * PartnersModel template class.
 */
public class PartnersModelTemplate extends WebdriverAwareModel implements PartnersModel {

    @Override
    public void verifyPartnerBannerIsDisplayed(String partner) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void clickVerticalTab(String vertical) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigatePartnersLink(String partner) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPartnerDealInResults() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void subscribeToHotwireDeals() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySubscriptionConfirmation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void followSideDoorUrl() throws UnsupportedEncodingException {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCarCompareWithPartnersCheckboxesState(String partnerName, String state) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyPartnerPopupVisibility(String partnerName, boolean isVisible) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifySupplierAdOnPage(String page, boolean isVisible) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void selectPartnersToCompareWith(List<String> partners, String blockId) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCarPartnerSearchOptions(DataTable params) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void allCarRetailersSelected(boolean state) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void compareAllRetailersFromD2C(String numberOfModule) {
        throw new UnimplementedTestException("Not implemented yet");
    }


    @Override
    public void verifyCarRentalsBannerOnResultsPage(String bannerType) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    /**
     * Verify the trip advisor badge on converged Hotel Landing page. This will handle intl and domestic.
     * Since mobile webapp is not defined yet for partners steps package, that will throw the standard PendingException.
     */
    @Override
    public void verifyTripAdvisorBadge(boolean isDisplayed) {
        HotelIndexPage hotelIndexPage = new HotelIndexPage(getWebdriverInstance());
        boolean badgeDisplayedState = hotelIndexPage.isTripAdvisorBadgeDisplayed();
        assertThat(badgeDisplayedState)
            .as("Expected trip advisor display to be " + isDisplayed + " but it was " + badgeDisplayedState)
            .isEqualTo(isDisplayed);
    }

    @Override
    public void verifyCarPartnerIsDisplayed(String partner, String visibility) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyGoogleConvergencePixel(int daysToArrival) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyDestinationCitiesPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyMeSoAds(String page) {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void selectAllCarRetailers() {
        throw new UnimplementedTestException("Not implemented yet");
    }
}

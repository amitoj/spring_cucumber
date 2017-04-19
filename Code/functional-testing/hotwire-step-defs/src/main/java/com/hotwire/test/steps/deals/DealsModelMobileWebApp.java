/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.deals;

import com.hotwire.testing.UnimplementedTestException;

import cucumber.api.PendingException;

/**
 * Mobile implementation of deals steps.
 */
public class DealsModelMobileWebApp extends DealsModelTemplate {

    public DealsModelMobileWebApp() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void goToSuperPageLink(String vertical, String channel) {
        String url = null;
        if ("international".equalsIgnoreCase(channel)) {
            url = new DealLinkFactory().getMarketingLinkUrl(vertical, "intl_superPage");
        }
        else {
            url = new DealLinkFactory().getMarketingLinkUrl(vertical, "superPage");
        }

        String fullUrl = getFullyQualifiedUrlForApplication(url);
        getWebdriverInstance().navigate().to(fullUrl);
    }

    @Override
    public void goToDBMLink(String vertical) {
        goToDBMLink(vertical, "dbm");
    }

    @Override
    public void goToDBMLink(String vertical, String channel) {
        String url = new DealLinkFactory().getMarketingLinkUrl(vertical, channel);
        String fullUrl = getFullyQualifiedUrlForApplication(url);
        getWebdriverInstance().navigate().to(fullUrl);
    }

    @Override
    public void goToSearchOptionsLink(String vertical) {
        String url = new DealLinkFactory().getMarketingLinkUrl(vertical, "search_option");
        String fullUrl = getFullyQualifiedUrlForApplication(url);
        getWebdriverInstance().navigate().to(fullUrl);
    }

    @Override
    public void goToCrossSellLink(String vertical) {
        String url = new DealLinkFactory().getMarketingLinkUrl(vertical, "cross_sell");
        String fullUrl = getFullyQualifiedUrlForApplication(url);
        getWebdriverInstance().navigate().to(fullUrl);
    }

    private String getFullyQualifiedUrlForApplication(String url) {
        String currentUrl = getWebdriverInstance().getCurrentUrl();
        String[] tokens;
        // Mobile needs a different behavior.
        if (currentUrl.contains("/mobile")) {
            tokens = currentUrl.split("/mobile");
            if (url != null && !url.isEmpty() && url.startsWith("/")) {
                tokens[0] += "/mobile";
            }
            else {
                tokens[0] += "/mobile/";
            }
        }
        else {
            tokens = currentUrl.split("\\?");
        }
        String rootUrl = tokens[0];

        // Need to encode spaces in the URL, otherwise it wouldn't work on
        // the iWebDriver on iPhone
        return rootUrl + url.replaceAll("\\s", "%20");
    }

    @Override
    public void verifyDealsModuleIsDisplayed() {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void selectDetailsCrossSell(int dealIndex) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectHotelDeal() {
        throw new PendingException("Implement me!");
    }

    @Override
    public void verifyDealDetails() {
        throw new PendingException("Implement me!");
    }
}

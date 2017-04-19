/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.application;

import static com.hotwire.selenium.mobile.results.car.MobileCarResultsPageProvider.getMobileCarResultsPage;
import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.NotFoundException;

import com.hotwire.selenium.mobile.MobileCarSearchPage;
import com.hotwire.selenium.mobile.MobileHotelSearchPage;
import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.account.MobilePasswordAssistancePage;
import com.hotwire.selenium.mobile.account.MobileRegisterNewUserPage;
import com.hotwire.selenium.mobile.account.SignInPage;
import com.hotwire.selenium.mobile.results.MobileHotelResultsPage;
import com.hotwire.test.steps.common.PGoodCode;

/**
 * MobileApp implementation of ApplicationModel.
 */

public class ApplicationModelMobileWebApp extends ApplicationModelTemplate {

    @Override
    public void setupOrVerifyApplicationAndAccessMethod() {
        sessionModifingParams.addParameter("vt.HMH14=2");
        sessionModifingParams.addParameter("vt.HMR14=2");
        sessionModifingParams.addParameter("vt.HMD14=1");
        sessionModifingParams.addParameter("vt.HMC14=2");
        sessionModifingParams.addParameter("vt.RAG14=1");
        sessionModifingParams.addParameter("vt.MSP14=1");
        sessionModifingParams.addParameter("vt.RAD13=3");
        // MCR - new Mobile Car Results.
        sessionModifingParams.addParameter("vt.MCR14=1");
        sessionModifingParams.addParameter("vt.CMD14=1");

        super.setupOrVerifyApplicationAndAccessMethod();
        new MobileHotwireHomePage(getWebdriverInstance());
    }

    @Override
    public void setupURL_ToVisit(String uRL) {
        String countryOfOrigin = posToCountryOrigin.get(uRL);
        purchaseParameters.getUserInformation().setCountry(countryOfOrigin);  // For purchase tests.
        sessionParameters.setMobilePointOfSale(uRL);  // Set for accounts tests.
        MobileHotwireHomePage mUHP = new MobileHotwireHomePage(getWebdriverInstance());
        mUHP.setupURLToVisit(uRL);
    }

    @Override
    public void verifySearchPage(PGoodCode pGoodCode) {
        switch (pGoodCode) {
            case H:
                new MobileHotelSearchPage(getWebdriverInstance());
                break;
            case A:
                throw new UnsupportedOperationException("This vertical: " + pGoodCode +
                    " search page is not supported in mobile");
            case C:
                new MobileCarSearchPage(getWebdriverInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public void verifyResultsPage(PGoodCode pGoodCode) {
        switch (pGoodCode) {
            case H:
                new MobileHotelResultsPage(getWebdriverInstance());
                break;
            case C:
                getMobileCarResultsPage(getWebdriverInstance());
                break;
            case A:
                throw new UnsupportedOperationException("This vertical: " + pGoodCode +
                    " results page is not supported in mobile");
            default:
                break;
        }
    }

    @Override
    public void verifySignInPage() {
        new SignInPage(getWebdriverInstance());
    }

    @Override
    public void verifyRegistrationPage() {
        new MobileRegisterNewUserPage(getWebdriverInstance());
    }

    @Override
    public void verifyPasswordAssistancePage() {
        new MobilePasswordAssistancePage(getWebdriverInstance());
    }

    @Override
    public void selectFullSiteView() {
        MobileHotwireHomePage homePage = new MobileHotwireHomePage(getWebdriverInstance());
        homePage.navigateToFullSite();
    }

    @Override
    public void verifyHotwireBranded3rdPartyMobileCarSite() {
        String currentURL = getWebdriverInstance().getCurrentUrl();
        if (!currentURL.contains("cartrawler")) {
            throw new NotFoundException("Was expecting to be on cartrawler site but was on " + currentURL);
        }
    }

    @Override
    public void verifySite() {
        String currentURL = getWebdriverInstance().getCurrentUrl();
        if (!currentURL.contains("/mobile")) {
            throw new NotFoundException("Was expecting to be on mobile site but was on " + currentURL);
        }

    }

    @Override
    public void verifyHomepageHasTimeoutMessage() {
        MobileHotwireHomePage homePage = new MobileHotwireHomePage(getWebdriverInstance());
        homePage.hasTimeoutMessage();
    }

    /**
     * For testing purposes of this project only. Not to be used for real test cases and scenarios.
     */
    @Override
    public void verifyHomepageLandingForTestingOnly() {
        new MobileHotwireHomePage(getWebdriverInstance());
        String url = getWebdriverInstance().getCurrentUrl();
        assertThat(url.contains(applicationUrl.toString())).isTrue();
        assertThat(url.contains(testVT)).isTrue();
        assertThat(url.contains(testAutParam)).isTrue();
    }

    @Override
    public void selectPOS(String pos) {
        setupURL_ToVisit(pos);
    }
}

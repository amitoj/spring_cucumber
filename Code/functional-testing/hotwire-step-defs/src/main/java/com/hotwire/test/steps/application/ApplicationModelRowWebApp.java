/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.application;

import static org.fest.assertions.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.test.steps.util.RefreshPageHandler;

/**
 * implementation of ApplicationModelTemplate for ROW site
 */

public class ApplicationModelRowWebApp extends ApplicationModelTemplate {

    @Override
    public void selectCurrency(final String currencyCode, final boolean force) {
        if (force) {
            String url = getWebdriverInstance().getCurrentUrl();
            url += url.contains("?") ? "&" : "?";
            url += "currencyCode=";
            url += currencyCode;
            getWebdriverInstance().navigate().to(url);
        }
        else {
            GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
            globalHeader.changeCurrency(currencyCode);
        }

        purchaseParameters.setCurrencyCode(currencyCode);
    }

    @Override
    public void setupOrVerifyApplicationAndAccessMethod() {
        sessionModifingParams.addParameter("currencyCode=" + desktopPosCurrencyMap.get(posaPath.replaceAll("/", "")));
        super.setupOrVerifyApplicationAndAccessMethod();
        try {
            new HotelIndexPage(getWebdriverInstance(), true);
        }
        catch (WebDriverException e) {
            logger.info("WebDriver exception instantiating home page object. Most likely elements loaded too slow.");
            RefreshPageHandler.reloadPageIfDropdownSkinNotLoaded(getWebdriverInstance(), By.cssSelector(
                ".children span.ui-selectmenu-button a"));
            new HotelIndexPage(getWebdriverInstance(), true);
        }
        purchaseParameters.setCurrencyCode(new com.hotwire.selenium.desktop.globalheader.GlobalHeader(
            getWebdriverInstance()).getCurrency());
    }

    @Override
    public void setupOrVerifyDefaultData() {
        sessionModifingParams.addParameter("vt.ARO01=2");
        sessionModifingParams.addParameter("vt.STH01=2"); // display disclaimer on details page
        sessionModifingParams.addParameter("vt.SOP01=2"); // display disclaimer on list results page
        sessionModifingParams.addParameter("vt.CSV13=1"); // billing page validation
        sessionModifingParams.addParameter("vt.HBA01=1"); // show full set of field for credit card info on billing page
        // Mapped results vts
        sessionModifingParams.addParameter("vt.FVR13=1"); // Mapped results filter refresh.
        sessionModifingParams.addParameter("vt.NOA13=1"); // Mapped results facilities filters vt.
        sessionModifingParams.addParameter("vt.ITS13=2");
        sessionModifingParams.addParameter("vt.MAB14=1");
        sessionModifingParams.addParameter("vt.MUH14=1");
        sessionModifingParams.addParameter("vt.DUH14=1");
        sessionModifingParams.addParameter("vt.RCU14=1");
        sessionModifingParams.addParameter("vt.LPB13=1");
        sessionModifingParams.addParameter("vt.PTW13=1");
        sessionModifingParams.addParameter("vt.HTW14=1");
        sessionModifingParams.addParameter("vt.SKW01=1");
        sessionModifingParams.addParameter("vt.ETL01=2");
        // Details page.
        sessionModifingParams.addParameter("vt.CSI14=2"); // Retail details page opaque cross sell.
        sessionModifingParams.addParameter("vt.HDC14=2");
        sessionModifingParams.addParameter("vt.RAG14=1");
        sessionModifingParams.addParameter("vt.DDD14=1");
        sessionModifingParams.addParameter("vt.NHP14=2");
        sessionModifingParams.addParameter("vt.PDC01=2");
        sessionModifingParams.addParameter("vt.TAB14=1");
        sessionModifingParams.addParameter("vt.RAD13=3");
    }

    @Override
    public void setupOnlinePartnerMarketing() {
        sessionModifingParams.addParameter("vt.OPM01=2");
        sessionModifingParams.addParameter("sid=S174");
        sessionModifingParams.addParameter("bid=B265179");
    }

    @Override
    public void verifySupportedCurrencies(String... supportedCurrencies) {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        assertThat(globalHeader.getSupportedCurrencies()).containsOnly((Object[]) supportedCurrencies);
    }

    @Override
    public void activateHwRecommendations() {
        sessionModifingParams.addParameter("vt.TAV02=1");
    }

    /**
     * For testing purposes of this project only. Not to be used for real test cases and scenarios.
     */
    @Override
    public void verifyHomepageLandingForTestingOnly() {
        new HotelIndexPage(getWebdriverInstance());
        String url = getWebdriverInstance().getCurrentUrl();
        assertThat(url).contains(applicationUrl.toString());
        assertThat(url).contains(testVT);
        assertThat(url).contains(testAutParam);
    }

    @Override
    public void activateOpaqueRoomPhotos() {
        sessionModifingParams.addParameter("vt.ORP01=2");
    }
}

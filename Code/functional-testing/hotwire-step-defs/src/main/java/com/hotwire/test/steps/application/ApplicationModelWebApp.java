/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.application;

import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.selenium.desktop.account.PasswordAssistancePage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.subscription.SubscriptionModuleFragment;
import com.hotwire.selenium.desktop.thirdparty.CommentCardPage;
import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.test.steps.util.RefreshPageHandler;

/**
 * Main Hotwire webApp model
 */
public class ApplicationModelWebApp extends ApplicationModelTemplate {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String emailAccount;

    @Override
    public void selectCurrency(String currencyCode, boolean force) {
        if (force) {
            super.selectCurrency(currencyCode, force);
        }
        else {
            new GlobalHeader(getWebdriverInstance()).changeCurrency(currencyCode);
        }
        purchaseParameters.setCurrencyCode(currencyCode);
    }

    @Override
    public void setupOrVerifyDefaultData() {
        sessionModifingParams.addParameter("vt.ARO01=2");
        sessionModifingParams.addParameter("vt.ITS13=2");
        sessionModifingParams.addParameter("vt.MAB14=1");
        sessionModifingParams.addParameter("vt.MUH14=1");
        sessionModifingParams.addParameter("vt.DUH14=1");
        sessionModifingParams.addParameter("vt.RCU14=1");
        sessionModifingParams.addParameter("vt.DPA13=2");
        sessionModifingParams.addParameter("vt.NDP13=2");
        sessionModifingParams.addParameter("vt.NDT13=1");
        sessionModifingParams.addParameter("vt.LPB13=1");
        sessionModifingParams.addParameter("vt.PTW13=2");
        sessionModifingParams.addParameter("vt.HTW14=1");
        sessionModifingParams.addParameter("vt.FBR13=1");
        sessionModifingParams.addParameter("vt.TLM13=1");
        sessionModifingParams.addParameter("vt.FVR13=1");
        sessionModifingParams.addParameter("vt.NOA13=1");
        // C3 test dealing with customer care and live chat module visibility
        sessionModifingParams.addParameter("vt.CCD13=1");
        // Details page.
        sessionModifingParams.addParameter("vt.CSI14=2"); // Retail details page
                                                          // opaque cross
                                                          // sell.
        sessionModifingParams.addParameter("vt.NHP14=2");
        sessionModifingParams.addParameter("vt.HDC14=2");
        sessionModifingParams.addParameter("vt.RAG14=1");
        sessionModifingParams.addParameter("vt.DDD14=1");
        sessionModifingParams.addParameter("vt.PDC01=2");
        sessionModifingParams.addParameter("vt.HPC14=1");
        sessionModifingParams.addParameter("vt.TAB14=1");
        sessionModifingParams.addParameter("vt.AWA14=1");
        // This was set to 100%
        sessionModifingParams.addParameter("vt.RAD13=3");
    }

    @Override
    public void setupOrVerifyApplicationAndAccessMethod() {
        refreshProperties();
        super.setupOrVerifyApplicationAndAccessMethod();
        try {
            new HomePage(getWebdriverInstance());
        }
        catch (WebDriverException e) {
            if (!getWebdriverInstance().findElements(HotelSearchFragment.CONTAINER).isEmpty()) {
                logger
                    .info("WebDriver exception instantiating home page object. Most likely elements loaded too slow.");
                RefreshPageHandler.reloadPageIfDropdownSkinNotLoaded(getWebdriverInstance(),
                    By.cssSelector(".numChildren span.ui-selectmenu-button a"));
                new HomePage(getWebdriverInstance());
            }
            else {
                throw e;
            }
        }
    }

    @Override
    public void unifyCarDetailsAndBilling() {
        // Turn on billing on details
        sessionModifingParams.addParameter("vt.CDB01=2");
        sessionModifingParams.addParameter("vt.ODB12=2");
        // New design of details page
        sessionModifingParams.addParameter("vt.CDB12=1");
    }

    @Override
    public void activateLastMinuteCruiseDeals() {
        sessionModifingParams.addParameter("vt.LMC01=2");
    }

    @Override
    public void activateTopCruiseDeals() {
        sessionModifingParams.addParameter("vt.CMR01=1");
    }

    @Override
    public void loadTripStarter() {
        getWebdriverInstance().navigate().to(applicationUrl + "/tripstarter/index.jsp");
    }

    @Override
    public void loadGoLocalSearch() {
        getWebdriverInstance().navigate().to(applicationUrl + "/hotel/local-trips.jsp");
    }

    @Override
    public void deactivateIntentMediaLayer() {
        sessionModifingParams.addParameter("vt.IMVA2=1");
    }

    // Given the search history on the calls made to this, it looks like it's only called after the call of
    // Given the application is accessible. Since this has been refactored to use applicationUrl as the base, calling
    // this after any line further down in the scenario might not work... So call this only if you plan to call this
    // right after Given application is accessible.
    @Override
    public void loadItnlSite(String country) {
        String appUrl = applicationUrl.toString();
        String currentParams = "?" + getWebdriverInstance().getCurrentUrl().split("\\?")[1];
        String url = appUrl + (appUrl.toString().endsWith("/") ?
            country.toLowerCase() :
            "/" + country.toLowerCase()) +
            "/" + currentParams + "&currencyCode=" + desktopPosCurrencyMap.get(country.toLowerCase());
        logger.info("Intl URL: " + url);
        getWebdriverInstance().navigate().to(url);
    }

    @Override
    public void checkItnlSiteURL(String country) {
        String expectedCountry = country.toLowerCase();
        assertThat(getWebdriverInstance().getCurrentUrl().
            contains(expectedCountry)).as("Expected country - " + expectedCountry +
                " hanven't been contained in URL - " + getWebdriverInstance().getCurrentUrl()).isTrue();
    }

    @Override
    public void activateBillingValidationMarks() {
        sessionModifingParams.addParameter("vt.CSV13=2");
    }

    /**
     * For testing purposes of this project only. Not to be used for real test cases and scenarios.
     */
    @Override
    public void verifyHomepageLandingForTestingOnly() {
        new HomePage(getWebdriverInstance());
        String url = getWebdriverInstance().getCurrentUrl();
        assertThat(url.contains(applicationUrl.toString())).isTrue();
        assertThat(url.contains(testVT)).isTrue();
        assertThat(url.contains(testAutParam)).isTrue();
    }

    @Override
    public void activateUpdatedAgreeAndBookSectionOnBilling() {
        sessionModifingParams.addParameter("vt.AAB13=2");
    }

    @Override
    public void activateReducedNumberSetOfCreditCardFields() {
        sessionModifingParams.addParameter("vt.HBA01=2");
    }

    @Override
    public void verifyPasswordAssistancePage() {
        new PasswordAssistancePage(getWebdriverInstance());
    }

    @Override
    public void setNewAirLowPriceModule() {
        sessionModifingParams.addParameter("vt.ORF01=2");
    }

    @Override
    public void loadURLwithDestCity(String sDestination) {
        String www = applicationUrl.toString().replaceAll("http://qa", "http://www.qa"); // really necessary
        getWebdriverInstance().navigate().to(www + "/seo/car/d-city/" + sDestination);
    }

    @Override
    public void navigateToExternalLink(String sParameter) {
        String www = applicationUrl.toString().replaceAll("http://qa", "http://www.qa"); // really necessary
        getWebdriverInstance().navigate().to(www + sParameter);
    }

    @Override
    public void setupURLwithSidBid(String sSidBid) {
        String www = applicationUrl.toString().replaceAll("http://qa", "http://www.qa"); // really necessary
        String sSID = www + "/index.jsp?" + sSidBid;
        getWebdriverInstance().navigate().to(sSID);
    }

    @Override
    public void loadURLForReferralType(String referralType) {
        String url = applicationUrl.toString().replaceAll("http://qa", "http://www.qa"); // really necessary
        if ("S".equals(referralType)) {
            url += "?siteID=" + RandomStringUtils.randomAlphanumeric(
                11) + "+" + RandomStringUtils.randomAlphanumeric(22);
        }
        else if ("SB".equals(referralType)) {
            url += "?nid=N-" + RandomStringUtils.randomAlphanumeric(
                8) + "&vid=V-" + RandomStringUtils.randomAlphanumeric(8);
        }
        else if ("N".equals(referralType)) {
            url += "?sid=S" + RandomStringUtils.randomNumeric(
                4) + "&bid=B" + RandomStringUtils.randomNumeric(4);
        }
        getWebdriverInstance().navigate().to(url);
    }

    @Override
    public void verifyUHPPage() {
        new HomePage(getWebdriverInstance());
    }

    @Override
    public void verifyNonExistentPageForIncorrectURL() {
        String url = applicationUrl.toString().replaceAll("http://qa", "http://www.qa"); // really necessary
        getWebdriverInstance().navigate().to(url + "/ind.jsp");
        assertThat(getWebdriverInstance().getPageSource().contains("We couldn't process your request")).as(
            "Non existent page was found").isTrue();
        assertThat(
            getWebdriverInstance().getPageSource().contains(
                "Thank you for visiting Hotwire." +
                    " We're always working to improve our site. Occasionally, we reorganize our pages," +
                    " and as a result, bookmarks you have set for our site may no " +
                "longer be accurate.")).as("Error message for the non existent page is visible").isTrue();
    }

    @Override
    public void goToAndVerifyOldHotelIndexPage() {
        getWebdriverInstance().navigate().to(applicationUrl + "/hotel/index.jsp");
        new HotelIndexPage(getWebdriverInstance());
    }

    @Override
    public void submitCommentCard(String comment, int content, int design, int usability, int overall) {
        logger.info(">>>>> Switching to comment card window <<<<<");
        // Need to switch to comment card window. Looping through set as it is
        // not guaranteed what order will be
        // returned. Assuming there will only be 2 windows opened as anymore
        // will be problematic.
        WebDriver webdriverInstance = getWebdriverInstance();
        String parentWindow = webdriverInstance.getWindowHandle();
        // String parentTitle = webdriverInstance.getTitle();
        for (String handle : webdriverInstance.getWindowHandles()) {
            logger.info("Browser window handle: " + handle);
            if (!handle.equals(parentWindow)) {
                webdriverInstance.switchTo().window(handle);
                // Assume the window that doesn't have the parent title is the
                // comment card.
                if (webdriverInstance.getTitle().equals("Comment card")) {
                    logger.info("Found comment card window.");
                    break;
                }
            }
        }
        CommentCardPage commentCardPage = new CommentCardPage(getWebdriverInstance());
        commentCardPage.typeComment(comment);
        commentCardPage.rateContent(content);
        commentCardPage.rateDesign(design);
        commentCardPage.rateUsability(usability);
        commentCardPage.rateOverall(overall);
        commentCardPage.clickSubmitButton();

        logger.info("Comment card sent. Switching to parent browser window.");
        getWebdriverInstance().switchTo().window(getWebdriverInstance().getWindowHandles().toArray()[0].toString());
    }

    @Override
    public void confirmThePageHeader(String pageHeader) {
        AbstractDesktopPage page = new AbstractDesktopPage(getWebdriverInstance());
        assertThat(page.getPageHeader().equals(pageHeader)).as("Page header should be: " + pageHeader).isTrue();
    }

    @Override
    public void verifyContentInPage(String textInPage) {
        AbstractDesktopPage page = new AbstractDesktopPage(getWebdriverInstance());
        assertThat(page.isTextDisplayedInPage(textInPage)).as(
            "Following text should be displayed in page: " + textInPage).isTrue();
    }

    @Override
    public void enterEmailAddressInSubscriptionEmailField() {
        SubscriptionModuleFragment subsModule = new SubscriptionModuleFragment(getWebdriverInstance());
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("Mddhhmmss");
        this.emailAccount = "automation" + ft.format(dNow).toString().toLowerCase() + "@expedia.com";
        subsModule.typeEmail(emailAccount);
        subsModule.clickSubmit();
    }

    @Override
    public void verifyDBcolumnsWantsNewsLetterAndThirdParty() {
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> listResult;
        String sql = "select WANTS_NEWS_LETTER from CUSTOMER where email = '" + emailAccount + "'";
        listResult = jdbcTemplate.queryForList(sql);
        String[] auxResult = listResult.get(0).toString().split("=");
        String newsLetter = auxResult[1];
        auxResult = newsLetter.split("}");
        newsLetter = auxResult[0];
        assertThat(newsLetter.equalsIgnoreCase("Y")).as(
            "WANTS_NEWS_LETTER column in DB should be set to: [Y]").isTrue();
        sql = "select WANTS_3RDPARTY_NEWS_LETTER from CUSTOMER where email = '" + emailAccount + "'";
        listResult = jdbcTemplate.queryForList(sql);
        auxResult = listResult.get(0).toString().split("=");
        String thirdParty = auxResult[1];
        auxResult = thirdParty.split("}");
        thirdParty = auxResult[0];
        assertThat(thirdParty.equalsIgnoreCase("null")).as(
            "WANTS_3RDPARTY_NEWS_LETTER column in DB should be set to: [null]").isTrue();
    }

    @Override
    public void enterRegisteredEmailInSubscriptionEmailField() {
        SubscriptionModuleFragment subsModule = new SubscriptionModuleFragment(getWebdriverInstance());
        List<Map<String, Object>> listResult;
        String sql = "select EMAIL from CUSTOMER where WANTS_NEWS_LETTER='N'";
        listResult = jdbcTemplate.queryForList(sql);
        String[] auxResult = listResult.get(0).toString().split("=");
        String thirdParty = auxResult[1];
        auxResult = thirdParty.split("}");
        this.emailAccount = auxResult[0].toLowerCase();
        subsModule.typeEmail(emailAccount);
        subsModule.clickSubmit();
    }

    @Override
    public void browseOnePageBack() {
        getWebdriverInstance().navigate().back();
    }
}

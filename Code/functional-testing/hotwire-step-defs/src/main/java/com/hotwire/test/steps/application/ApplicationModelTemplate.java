/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.application;

//import static com.hotwire.selenium.mobile.results.car.MobileCarResultsPageProvider.getMobileCarResultsPage;
import static com.thoughtworks.selenium.SeleneseTestBase.assertEquals;
import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.fest.assertions.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.refreshUtil.RefreshUtilPage;
import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.selenium.mobile.MobileHotelSearchPage;
import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.account.mytrips.MobileTripSummaryPage;
import com.hotwire.selenium.mobile.results.MobileHotelResultsPage;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.SessionModifingParams;
import com.hotwire.util.webdriver.WebDriverManager;

import cucumber.runtime.CucumberException;

/**
 * This class is responsible for application set up parameters.
 */
public class ApplicationModelTemplate extends WebdriverAwareModel implements ApplicationModel {

    protected static String[] FULL_ENABLED_VERSION_TESTS = {"vt.CRB12=2",
                                                            "vt.CST12=2", "vt.CUT12=2", "optimizely_x1859720750=0"};
    //optimizely version test disables opening new tab from results page

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationModelTemplate.class);

    @Autowired
    protected String testVT;

    @Autowired
    protected String testAutParam;

    @Autowired
    protected URL angularAppUrl;

    @Autowired
    protected String posaPath;

    @Resource(name = "desktopPosCurrencyMap")
    protected HashMap<String, String> desktopPosCurrencyMap;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected URL applicationUrl;

    protected SessionModifingParams sessionModifingParams;

    @Resource
    protected Properties applicationProperties;

    @Autowired
    protected PurchaseParameters purchaseParameters;

    @Autowired
    protected SessionParameters sessionParameters;

    @Resource(name = "posToCountryOrigin")
    protected HashMap<String, String> posToCountryOrigin;

    // TO-DO: remove when refreshUtil will be completely headless
    private String refreshUtilPath;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("canadianZipCode")
    private String canadianZipCode;

    private String paymentProcessor;

    /**
     * Populate properties across view and biz servers via refreshUtil. Use applicationProperties placeholder to
     * override default values. Default values defined in cucumber-application-context.xml.
     *
     * @deprecated use RefreshUtilPropertyCustomizer instead
     */
    @Deprecated
    protected void refreshProperties() {
        if (applicationProperties.size() > 0) {
            StringBuilder sb = new StringBuilder("/test/refreshUtil.jsp?actionType=1");

            int i = 0;

            for (String s : applicationProperties.stringPropertyNames()) {
                logger.info(String.format("Property '%s' was set to '%s'", s, applicationProperties.getProperty(s)));
                sb.append("&propName{0}={1}&propValue{0}={2}".replace("{0}", Integer.toString(i++)).replace("{1}", s)
                    .replace("{2}", applicationProperties.getProperty(s)));
            }

            try {
                URL url = new URL(applicationUrl, sb.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                logger.info(String.format("refreshUtil returned response: %s", connection.getResponseMessage()));
            }
            catch (IOException e) {
                throw new CucumberException(e);
            }
        }
    }

    public void setSessionModifingParams(SessionModifingParams sessionModifingParams) {
        this.sessionModifingParams = sessionModifingParams;
    }

    public void setSessionParameters(SessionParameters sessionParameters) {
        this.sessionParameters = sessionParameters;
    }

    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public String getPaymentProcessor() {
        return this.paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessorName) {
        this.paymentProcessor = paymentProcessorName;
    }

    public void setRefreshUtilPath(String refreshUtilPath) {
        this.refreshUtilPath = refreshUtilPath;
    }

    @Override
    public void selectPOS(String pos) {
        GlobalHeader globalHeader = new AbstractDesktopPage(getWebdriverInstance()).getGlobalHeader();
        globalHeader.selectPOS(pos);
        String countryOfOrigin = posToCountryOrigin.get(pos);
        purchaseParameters.getUserInformation().setCountry(countryOfOrigin);  // For desktop purchase tests.
        purchaseParameters.getUserInformation().setBillingCountry(countryOfOrigin);
        if ("United Kingdom".equalsIgnoreCase(countryOfOrigin)) {
            purchaseParameters.getUserInformation().setZipCode("03057");
        }
        if ("Canada".equalsIgnoreCase(countryOfOrigin)) {
            purchaseParameters.getUserInformation().setZipCode(canadianZipCode);
        }
        sessionParameters.setPointOfSale(pos);  // Set for accounts tests.
        logger.info(
            "POS: " + pos + " - SessionParam PointOfSale: " + pos +  " - Billing Country Of Origin: " +
            countryOfOrigin);
        purchaseParameters.setCurrencyCode(
            new AbstractDesktopPage(getWebdriverInstance()).getGlobalHeader().getSelectedCurrency());
    }

    @Override
    public void selectCurrency(String currencyCode, boolean force) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setupOrVerifyDefaultData() {
        // No-op
    }

    @Override
    public void setupOrVerifyApplicationAndAccessMethod() {
        getWebdriverInstance().navigate().to(this.applicationUrl + "/" + this.sessionModifingParams.asUrlParameters());
    }

    @Deprecated
    private RefreshUtilPage openRefreshUtilTool() {
        getWebdriverInstance().navigate().to(this.applicationUrl + "/" + this.refreshUtilPath);
        return new RefreshUtilPage(getWebdriverInstance());
    }

    // TO-DO: remove when refreshUtil will be completely headless
    @Deprecated
    @Override
    public void dropRefreshUtilProperty(String propName) {
        openRefreshUtilTool().changePropertyToDefault(propName);
    }

    @Override
    public void addVersionTest(String vtName, String vtValue) {
        this.sessionModifingParams.addParameter(vtName + "=" + vtValue);
    }

    @Override
    public void selectFullSiteView() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySearchPage(PGoodCode pGoodCode) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHomePage(String channel) {
        if ("mobile".equalsIgnoreCase(channel)) {
            new MobileHotwireHomePage(getWebdriverInstance());
        }
        else if ("desktop domestic".equalsIgnoreCase(channel)) {
            new AbstractDesktopPage(
                getWebdriverInstance(), new String[]{
                    "tiles-def.uhp.index", "tile.hotwire.home", "tile.hotel.index", "tile.hotwire.hotel"});
        }
        else if ("desktop international".equalsIgnoreCase(channel)) {
            new AbstractDesktopPage(
                getWebdriverInstance(), new String[]{
                    "tile.home.epica, tile.home", "tile.hotel.index", "tile.hotwire.hotel"});
        }
        else {
            throw new UnsupportedOperationException("Channel: " + channel + " not supported");
        }
    }

    @Override
    public void verifyAirIndexPage() {
        new com.hotwire.selenium.desktop.us.index.AirIndexPage(getWebdriverInstance());
    }

    @Override
    public void verifyAirPageWithOrWithoutResults() {
        new com.hotwire.selenium.desktop.us.AbstractUSPage(getWebdriverInstance(), new String[]{
            "tiles-def.air.results*", "tiles-def.air.no-results"});
    }

    @Override
    public void verifyAirInformationPage() {
        // We verify that we are on the air information SEO page by looking at the URL.
        // We are not verifying the content of the page.
        String currentURL = getWebdriverInstance().getCurrentUrl();
        if (!currentURL.contains("flight-information")) {
            throw new org.openqa.selenium.NotFoundException("Was expecting to be on SEO flight information page" +
                " but was on" + currentURL);
        }
    }

    @Override
    public void verifySurveyPage(String vertical) {
        // We verify that we are on the desktop survey page by looking at the URL.
        // We are not verifying the content of the page.
        String currentURL = getWebdriverInstance().getCurrentUrl();
        if (!currentURL.contains(vertical + "/survey") || !currentURL.contains("isMobileFullSite=true")) {
            throw new org.openqa.selenium.NotFoundException("Was expecting to be on " + vertical + " survey page" +
                " but was on" + currentURL);
        }

    }

    @Override
    public void verifyMessageOnPage(String messageText, ErrorMessenger.MessageType messageType) {
        ErrorMessenger msg = new ErrorMessenger(getWebdriverInstance());
        msg.setMessageType(messageType);
        if (messageText == null) {
            assertThat(msg.getMessages().isEmpty()).as(messageType + "message does not appear").isFalse();
        }
        else {
            assertThat(msg.getMessages().toString().contains(messageText))
                    .as(messageText + "message does not appear").isTrue();
        }
    }

    @Override
    public void verifyPriceCheckMessageOnPage(String messageText, ErrorMessenger.MessageType messageType) {
        ErrorMessenger msg = new ErrorMessenger(getWebdriverInstance());
        msg.setMessageType(messageType);
        if (messageText == null) {
            assertThat(msg.getMessages().isEmpty()).as(messageType + "message does not appear").isFalse();
        }
        else {
            for (String m : msg.getMessages()) {
                assertThat(m.contains(messageText)).isTrue();
            }
        }
    }

    @Override
    public void verifyDestinationInformationPage(String destination) {
        // We verify that we are on the destination information SEO page by looking at the URL.
        // We are not verifying the content of the page.
        String currentURL = getWebdriverInstance().getCurrentUrl();
        String normalizedDestination = destination.toLowerCase();
        if (!currentURL.contains("hotel-rooms/" + normalizedDestination + ".jsp?mwr=1&isMobileFullSite=true")) {
            throw new org.openqa.selenium.NotFoundException("Was expecting to be on SEO" + destination +
                " information page but was on " + currentURL);
        }
    }

    @Override
    public void verifyResultsPage(PGoodCode pGoodCode) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void unifyCarDetailsAndBilling() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void activateCarVersionTests() {
        logger.info("Set of fully enabled version tests is activated..");
        for (String vt : FULL_ENABLED_VERSION_TESTS) {
            sessionModifingParams.addParameter(vt);
        }
    }

    @Override
    public void verifyHotwireBranded3rdPartyMobileCarSite() {
        throw new UnimplementedTestException("Not implemented");
    }

    @Override
    public void verifyTripSummaryPage() {
        new MobileTripSummaryPage(getWebdriverInstance());
    }

    @Override
    public void verifySite() {
        throw new UnimplementedTestException("Not implemented");
    }

    @Override
    public void verifySignInPage() {
        throw new UnimplementedTestException("Not implemented");
    }

    @Override
    public void verifyRegistrationPage() {
        throw new UnimplementedTestException("Not implemented");
    }

    @Override
    public void verifyPasswordAssistancePage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setupOnlinePartnerMarketing() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void activateLastMinuteCruiseDeals() {
        throw new UnimplementedTestException("Not implemented");
    }

    @Override
    public void activateTopCruiseDeals() {
        throw new UnimplementedTestException("Not implemented");
    }

    @Override //ROW and domestic
    public void verifyCurrency(String currency) {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        assertEquals(globalHeader.getCurrency(), currency);
    }

    @Override //ROW and domestic
    public void verifyCountry(String country) {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        assertEquals(globalHeader.getSelectedCountry(), country);
    }

    @Override
    public void verifySupportedCurrencies(String... supportedCurrencies) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void loadTripStarter() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void deactivateIntentMediaLayer() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void loadItnlSite(String country) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkItnlSiteURL(String country) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void activateHwRecommendations() {
        sessionModifingParams.addParameter("vt.TAV02=1");
    }

    @Override
    public void activateBillingValidationMarks() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void activateUpdatedAgreeAndBookSectionOnBilling() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void activateLowPriceGuaranteeModule() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void activateReducedNumberSetOfCreditCardFields() {
        throw new UnimplementedTestException("Implement me!");
    }

    /**
     * For testing purposes of this project only. Not to be used for real test cases and scenarios.
     */
    @Override
    public void setTestSessionModifyingParam() {
        sessionModifingParams.addParameter(testVT);
    }

    /**
     * For testing purposes of this project only. Not to be used for real test cases and scenarios.
     */
    @Override
    public void verifyHomepageLandingForTestingOnly() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHomepageHasTimeoutMessage() {
        throw new UnimplementedTestException("Not implemented");
    }

    @Override
    public void activateOpaqueRoomPhotos() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void refreshPage() {
        getWebdriverInstance().navigate().refresh();
    }

    /**
     * Activate version 1 or version 2 of cross sell in details page.
     * Used for all channels. It works for domestic and row. It won't hurt mobile.
     */
    @Override
    public void activateDetailsXsellList(boolean doListCrossSell) {
        sessionModifingParams.addParameter("vt.XSD13=" + (doListCrossSell ? "2" : "1"));
    }

    @Override
    public void verifyDBPurchaseStatus(String status) {
        String sql = "select ss.display_number, r.supplier_reservation_number, rs.status_code, " +
            "rs.crs_error_code, rs.crs_error_msg " +
            "from search_solution ss, reservation r, reservation_status rs " +
            "where ss.search_solution_id = r.search_solution_id " +
            "and r.reservation_id = rs.reservation_id " +
            "and ss.display_number = ? " +
            "order by status_code asc";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, new Object[]{
        /*purchaseParameters.getDisplayNumber()*/ "3716105357"});
        while (sqlRowSet.next()) {
            System.out.print("Display number and status: " + sqlRowSet.getString("display_number") + " " +
                sqlRowSet.getString("status_code"));
            assertThat(sqlRowSet.getString("status_code"))
                .as("Transaction data stored in db")
                .isEqualTo(status);
        }
    }

    @Override
    public void openUrlOnMobileDevice(String url) throws IOException {
        String location = getRedirectLocation(url);
        getWebdriverInstance().navigate().to(location);
    }

    @Override
    public void verifyCurrentUrl(String url) throws IOException {
        String currentUrl = getWebdriverInstance().getCurrentUrl()
                .replaceAll("[&]aut[=]true", "")
                .replaceAll("[&]useCluster[=][\\d]", "");
        assertThat(currentUrl)
                .as("Current URL doesn't contain destiny location")
                .contains(url);
    }

    @Override
    public void verifyMobileAppAfterRedirection(String content) {
        if (content.equals("hotel result page")) {
            new MobileHotelResultsPage(getWebdriverInstance());
        }
        else if (content.equals("home page")) {
            new MobileHotwireHomePage(getWebdriverInstance());
        }
        else {
            MobileHotelSearchPage mSearchPage = new MobileHotelSearchPage(getWebdriverInstance());
            mSearchPage.verifyHotelSearchDetails(content);
        }
    }

    private String getRedirectLocation(String url) throws IOException {
        HttpClient httpClient = HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) " +
                        "AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16")
                .disableRedirectHandling()
                .build();
        try {
            HttpResponse httpResponse = httpClient.execute(new HttpGet(new URL(url).toString()));
            Header[] headers = httpResponse.getHeaders("Location");
            return headers[0].getValue();
        }
        finally {
            logger.info("HttpClient version 4.3 needn't special close procedure");
        }
    }

    @Override
    public void verifyErrorMessageForIncorrectLocation(String typeOfLocation) {
        try {
            Alert alert = getWebdriverInstance().switchTo().alert();
            alert.accept();
        }
        catch (NoAlertPresentException ignored) {
            logger.info("Security alert is present");
        }

        String error_template_1 = "Please choose your location from the list. ";
        String error_template_2 = "If your location is not listed, please " +
                "check your spelling or make sure it is on our ";
        String error_template_3 = " cities list";
        String error_template_4  = "Did we guess what you wrote correctly? " +
                "If so, click it to continue. If not, check out our origin" +
                " and our destination cities list to ensure your cities are listed." +
                " Click your city to choose it";

        ErrorMessenger msg = new ErrorMessenger(getWebdriverInstance());
        msg.setMessageType(ErrorMessenger.MessageType.valueOf("error"));
        for (String m : msg.getMessages()) {
            if (null == typeOfLocation) {
                assertThat(m.contains(error_template_1 + error_template_2)).isTrue();
                assertThat(m.contains(error_template_3)).isTrue();
            }
            else {
                if (typeOfLocation.trim().equals("origin")) {
                    assertThat(m.contains("From " + error_template_1 + error_template_2 +
                            "origin" + error_template_3)).isTrue();
                }
                else if (typeOfLocation.trim().equals("destination")) {
                    assertThat(m.contains("To " + error_template_1 + error_template_2 +
                            "destination" + error_template_3)).isTrue();
                }
                else if (typeOfLocation.trim().equals("origin and destination")) {
                    assertThat(m.contains(error_template_4)).isTrue();
                }
            }
        }
    }

    @Override
    public void loadGoLocalSearch() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setNewAirLowPriceModule() {
        throw new UnimplementedTestException("Not implemented");
    }

    @Override
    public void loadURLwithDestCity(String sDestination) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void navigateToExternalLink(String sparameter) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDB_SIB_BID_Reg_Referral_Status(List<String> status, String table) {
        // status
        // place 0 - referral_type
        // place >1 - args

        String condition;
        if (table.equals("purchase")) {
            table = "purchase_referral";
            condition = "create_date > sysdate -.01";
        }
        else if (table.equals("registration")) {
            table = "registration_referral";
            condition = "customer_id =" + this.getCustomerIDFromDB(authenticationParameters.getUsername());
        }
        else { //search_referral
            table = "search_referral";
            condition = "search_id =" + this.getSearchIDFromDB(authenticationParameters.getUsername());
        }

        String sql = "select distinct referral_type, referrer_id, link_id, " +
                "version_id, keyword_id, match_type_id " +
                " from " + table + " where referral_type='" + status.get(0) + "'" +
                " and " + condition;

        System.out.println(sql);
        Map<String, Object> sqlRowSet = jdbcTemplate.queryForMap(sql);

        System.out.println("Expected params in DB" + status.toString());
        System.out.println("Actual param in DB" + sqlRowSet.values().toString());
        assertThat(status.toString())
                    .as("Transaction data stored in db")
                    .contains(sqlRowSet.values().toString());

        //check create date
        sql = "select create_date " +
                " from " + table + " where " + condition +
                " and referral_type='" + status.get(0) + "'" +
                " and rownum <= 1";


        sqlRowSet = jdbcTemplate.queryForMap(sql);
        DateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        Date current_date = new Date();
        Date dateDB = (Date) sqlRowSet.get("CREATE_DATE");
        assertThat(dateDB.toString())
                .as("Transaction data(CREATE_DATE) stored in db")
                .contains(formatterDate.format(current_date));
    }

    /**
     * For ROW and domestic.
     */
    @Override
    public void setupURL_ToVisit(String url) {
        String gotoUrl;
        if (!applicationUrl.toString().endsWith("/")) {
            if (url.startsWith("/")) {
                gotoUrl = applicationUrl + url;
            }
            else {
                gotoUrl = applicationUrl + "/" + url;
            }
        }
        else {
            if (!url.startsWith("/")) {
                gotoUrl = applicationUrl + url;
            }
            else {
                gotoUrl = applicationUrl + url.substring(1);
            }
        }
        getWebdriverInstance().navigate().to(gotoUrl);
    }

    @Override
    public void setupURLwithSidBid(String sSidBid) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void loadURLForReferralType(String referralType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public String getSearchIDFromDB(String email, String vertical) {
            // arg String vertical - A - air
            //                       H - hotel
            //                       C  - car
        String sql = "select SEARCH_ID from SEARCH a  inner join customer c on " +
                "a.CUSTOMER_ID = c.CUSTOMER_ID where " +
                "CLASS = '" + vertical + "' and " +
                "c.EMAIL = '" + email + "' " +
                "order by a.CREATE_DATE desc";
        Map<String, Object> result =  jdbcTemplate.queryForMap(sql);
        return result.get("SEARCH_ID").toString();
    }

    @Override
    public String getSearchIDFromDB(String email) {
        // good for unique user
        String sql = "select SEARCH_ID from SEARCH a  inner join customer c on " +
                "a.CUSTOMER_ID = c.CUSTOMER_ID where " +
                "c.EMAIL = '" + email + "' " +
                "order by a.CREATE_DATE desc";
        Map<String, Object> result  = jdbcTemplate.queryForMap(sql);
        return result.get("SEARCH_ID").toString();
    }

    @Override
    public String getCustomerIDFromDB(String email) {
        // works only for unique email
        String sql = "select CUSTOMER_ID from CUSTOMER where " +
                "EMAIL = '" + email + "' ";
        Map<String, Object> result  = jdbcTemplate.queryForMap(sql);
        return result.get("CUSTOMER_ID").toString();
    }

    /**
     * For Mobile, Row, and domestic.
     */
    @Override
    public void enableAngularApp(boolean enableAngular) {
        // just remove if already set.
        sessionModifingParams.removeParam("vt.AWA14=1");
        sessionModifingParams.removeParam("vt.AWA14=2");
        sessionModifingParams.addParameter("vt.AWA14=" + (enableAngular ? "2" : "1"));
        if (enableAngular) {
            sessionModifingParams.removeParam("vt.RAD13=1");
            sessionModifingParams.removeParam("vt.RAD13=2");
            sessionModifingParams.removeParam("vt.RAD13=3");
            sessionModifingParams.addParameter("vt.RAD13=1");
            sessionModifingParams.addParameter("vt.ALS14=2");
        }
    }

    @Override
    public void closeAllPopUpWindows() {

        String defaultHandle = WebDriverManager.getRootWindowHandle(getWebdriverInstance());
        int openedWindowsCount = getWebdriverInstance().getWindowHandles().size();

        if (openedWindowsCount > 1) {
            LOGGER.info("Try to close child browser's windows.. Found " +
                    (openedWindowsCount - 1) + " opened instances..");
        }

        for (String handle : getWebdriverInstance().getWindowHandles()) {
            if (!handle.equals(defaultHandle)) {
                try {
                    getWebdriverInstance().switchTo().window(handle);
                    // For debugging if we really want to fix this for reals. Did not choose
                    // to print out getTitle or getPageSource as titles may never be set and
                    // page source would just clutter up the logs. If this
                    // is seen manually debug the test which this would be seen after
                    // the test case is done running and fix the issue for reals if you chose to.
                    StringBuilder sb = new StringBuilder("DEBUG ME! Popup window found.");
                    sb.append("\nTitle: " + getWebdriverInstance().getTitle());
                    sb.append("\nUrl: " + getWebdriverInstance().getCurrentUrl());
                    sb.append("\nHandle: " + handle);
                    getWebdriverInstance().close();

                    sb.append("\nClosed...");
                    LOGGER.info(sb.toString());
                }
                catch (NoSuchWindowException e) {
                    LOGGER.info("Something goes wrong when try to close popups..");
                }
            }
        }

        LOGGER.info("Switching back to root browser window..");
        getWebdriverInstance().switchTo().window(defaultHandle);
        LOGGER.info("Switch done to.. " + getWebdriverInstance().getTitle());
    }

    @Override
    public void verifyPageURL(String url) {
        Assertions.assertThat(getWebdriverInstance().getCurrentUrl()).contains(url);
    }

    @Override
    public void clickOnLink(String linkText) {
        getWebdriverInstance().findElement(By.linkText(linkText)).click();
    }

    @Override
    public void verifySavedCreditCardInDB(String userEmail, String condition) {
        String sql = "select PAYMENT_METHOD_ID from payment_method" +
                " where customer_id = (select CUSTOMER_ID from customer" +
                " where email = \'" + userEmail + "\')";

        Map<String, Object> result;
        try {
            result = jdbcTemplate.queryForMap(sql);
            if (" no".equals(condition) && result.size() > 0) {
                throw new RuntimeException("DB has records about saved credit cards");
            }
        }
        catch (Exception e) {
            if (null == condition) {
                throw new RuntimeException("DB has no records about saved credit cards");
            }
        }
    }

    @Override
    public void verifyUHPPage() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void verifyNonExistentPageForIncorrectURL() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void goToAndVerifyOldHotelIndexPage() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void submitCommentCard(String comment, int content, int design, int usability, int overall) {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void confirmThePageHeader(String pageHeader) {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void verifyContentInPage(String textInPage) {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void maximizeWindow() {
        getWebdriverInstance().manage().window().maximize();
    }

    @Override
    public void enterEmailAddressInSubscriptionEmailField() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void verifyDBcolumnsWantsNewsLetterAndThirdParty() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void enterRegisteredEmailInSubscriptionEmailField() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void browseOnePageBack() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    public void enableESS() {
        sessionModifingParams.removeParam("vt.ESS14=1");
        sessionModifingParams.removeParam("vt.ESS14=2");
        //removed params if already set
        sessionModifingParams.addParameter("vt.ESS14=2");
    }

    public void goToBexBucket() {
        String bucket = "http://vacationhotwirecom.integration.sb.karmalab.net" +
                "/tools/abacus/overrides?abov=6774|0|0:6793|1|1";
        getWebdriverInstance().navigate().to(bucket);
    }

    public void clearCookies() {
        getWebdriverInstance().manage().deleteAllCookies();
    }
}


/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.setup;

import com.hotwire.selenium.desktop.account.AccountPaymentInfoPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.tools.c3.C3ErrorMessagingFragment;
import com.hotwire.selenium.tools.c3.C3IndexPage;
import com.hotwire.selenium.tools.c3.login.C3LoginPage;
import com.hotwire.selenium.tools.c3.purchase.C3FareFinder;
import com.hotwire.selenium.tools.myAccount.ChoseLocalSiteDropdown;
import com.hotwire.selenium.tools.myAccount.HWGlobalHeader;
import com.hotwire.selenium.tools.myAccount.HWMyAccount;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.AppMetaData;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.ProductVertical;
import com.hotwire.test.steps.tools.bean.TripInfo;
import com.hotwire.test.steps.tools.bean.c3.C3CaseNoteInfo;
import com.hotwire.test.steps.tools.bean.c3.C3CsrAccount;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryOperation;
import com.hotwire.test.steps.tools.bean.c3.C3MobileInfo;
import com.hotwire.test.steps.tools.bean.dmu.DMUAccountInfo;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3CaseNotesDao;
import com.hotwire.util.db.c3.C3CustomerDao;
import com.hotwire.util.db.c3.C3MobileDao;
import com.hotwire.util.db.c3.C3SearchDao;
import com.hotwire.util.db.c3.C3VerificationDao;
import com.hotwire.util.db.c3.lpg.C3LPGRefundRequestDao;
import com.hotwire.util.db.c3.lpg.C3LowPriceGuaranteeDao;
import com.hotwire.util.db.c3.service.C3PartialRefundDao;
import com.hotwire.util.db.c3.service.C3RefundDao;
import org.fest.assertions.Assertions;
import org.fest.assertions.Delta;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Template of C3 application model
 * User: v-abudyak
 */

public class ToolsSetupModel extends ToolsAbstractModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolsSetupModel.class.getName());
    @Resource
    Map<String, DMUAccountInfo> dmuProfiles;

    @Resource
    Map<String, String> mobileAppCodes;

    private Map referralData = null;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private C3CsrAccount c3CsrAccount;

    @Autowired
    private C3CaseNoteInfo c3CaseNoteInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private C3MobileInfo c3MobileInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private SimpleJdbcDaoSupport databaseSupport;

    @Autowired
    private TripInfo tripInfo;

    @Autowired
    private C3ItineraryOperation c3ItineraryOperation;

    @Autowired
    private AppMetaData appMetaData;

    @Autowired
    private BillingInfo billingInfo;

    public void setupApplication()  {
        getWebdriverInstance().manage().window().maximize();
        try {
            String url = appMetaData.getApplicationUrl().toString();
            addParamsToUrlAndGo(url, appMetaData.getVersionTests());
            LOGGER.info(appMetaData.getApplicationUrl().toString());
        }
        catch (MalformedURLException e) {
            logSession("Invalid initial URL");
        }
    }

    public void openLocale(String locale) {
        if (!locale.equalsIgnoreCase("US/CANADA")) {
            appMetaData.setIntl(true);
        }
        ChoseLocalSiteDropdown choseLocalSiteDropdown = new ChoseLocalSiteDropdown(getWebdriverInstance());
        choseLocalSiteDropdown.chooseLocale(locale);
    }





    public void loginIntoC3(String username) {
        //this need for checking if username is correct
        c3CsrAccount.setUsername(username);
        tryToLoginInC3(c3CsrAccount.getUsername(), c3CsrAccount.getPassword());
        logSession("Switching to C3 frame");
        try {
            switchToFrame("c3Frame");
        }
        catch (Exception e) {
            logSession("c3Frame not detected");
        }
    }

    public void tryToLoginInC3(String username, String password) {
        new C3LoginPage(getWebdriverInstance()).login(username, password);
    }



    public void verifyMessageIsDisplayed(String message) {
        assertThat(new C3ErrorMessagingFragment(getWebdriverInstance()).getListOfErrors().toString())
                .contains(message);
    }

    public void openTravelAdvisoryUpdates() {
        new C3IndexPage(getWebdriverInstance()).openTravelAdvisoryUpdates();
    }

    public void goToHotelOvercharges() {
        new C3IndexPage(getWebdriverInstance()).clickHotelOvercharges();
    }

    public void setupC3Purchase(String verticalName, String operation) {
        c3ItineraryOperation.setPurchaseOperation(operation);
        hotwireProduct.setVertical(verticalName);
        final String itinerary = hotwireProduct.getItinerary(c3ItineraryOperation.getC3Dao());
        c3ItineraryInfo.setItineraryNumber(itinerary);
        logItinerary();
        customerInfo.setCustomerEmailByItinerary(c3ItineraryInfo.getItineraryNumber());
    }

    public void setupC3AirPurchaseByEmail(String email) {
        c3ItineraryInfo.setItineraryNumber(new C3PartialRefundDao(getDataBaseConnection())
                .getAirPurchaseByEmail(email));
        customerInfo.setEmail(email);
    }

    public void setupPurchaseWithHotDollars(boolean withHotDollars) {
        c3ItineraryInfo.setItineraryNumber(new C3SearchDao(getDataBaseConnection())
                .getPurchaseWithHotDollars(withHotDollars));
    }

    public void setupCustomerWithHotDollars() {
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
        customerInfo.setEmail(new C3CustomerDao(getDataBaseConnection()).getCustomerEmailWithHotDollars());
        setupHotDollarBalance();
    }

    public void setupCustomerWithManyHotDollars() {
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
        customerInfo.setEmail(new C3CustomerDao(getDataBaseConnection()).getCustomerEmailWithManyHotDollars());
        setupHotDollarBalance();
    }

    public void setupHotDollarBalance() {
        String hd = new C3CustomerDao(getDataBaseConnection()).getHotDollarsAmount(customerInfo.getEmail());
        customerInfo.setHotDollars(hd == null ? "0.0" : hd);
    }

    public void setupCustomerWithPurchases() {
        final String productCode = hotwireProduct.getProductVertical().getProductCode();
        final Map customerWithPurchases = new C3CustomerDao(getDataBaseConnection())
                .getCustomerWithPurchases(productCode);
        customerInfo.setCustomerInformation(customerWithPurchases);
    }


    public void setupCustomerPurchaseWoCase() {
        c3ItineraryInfo.setItineraryNumber(new C3SearchDao(getDataBaseConnection())
                                               .getPurchaseWithoutCase(hotwireProduct.getProductVertical()
                                                       .getProductName()));
        customerInfo.setEmail(new C3CustomerDao(getDataBaseConnection())
                                .getEmailOfItinerary(c3ItineraryInfo.getItineraryNumber()));
    }

    public void setupC3AirVoidPurchase() {
        String itineraryNum = new C3RefundDao(getDataBaseConnection()).gerAirVoidPurchase();
        logItinerary();
        c3ItineraryInfo.setItineraryNumber(itineraryNum);
    }

    public void setupC3IntlPurchase() {
        String  itineraryNum = new C3RefundDao(getDataBaseConnection()).getIntlHotelPurchase();
        logItinerary();
        c3ItineraryInfo.setItineraryNumber(itineraryNum);
    }

    public void setupC3MobilePurchase() {
        Map purchaseInfo = new C3MobileDao(getDataBaseConnection())
                .getMobilePurchase(c3MobileInfo.getDeviceType().toString());
        String itinerary = purchaseInfo.get("DISPLAY_NUMBER").toString();
        logItinerary();
        c3ItineraryInfo.setItineraryNumber(itinerary);
        if (purchaseInfo.get("SITE_TYPE") == null) {
            c3MobileInfo.setApplicationType("Desktop");
        }
        else {
            String code = purchaseInfo.get("SITE_TYPE").toString();
            c3MobileInfo.setApplicationType(mobileAppCodes.get(code));
        }
        hotwireProduct.setProductVerticalByItinerary(c3ItineraryInfo.getItineraryNumber());
    }

    public void setupDesktopPurchase() {
        String itinerary = new C3MobileDao(getDataBaseConnection()).getDesktopPurchase();
        logItinerary();
        c3ItineraryInfo.setItineraryNumber(itinerary);
        c3MobileInfo.setApplicationType("Desktop");
        c3MobileInfo.setDeviceType("Other");
        hotwireProduct.setProductVerticalByItinerary(c3ItineraryInfo.getItineraryNumber());
    }



    public void setupPastSearch() {
        c3ItineraryInfo.setReferenceNumberResults("2" +
                new C3SearchDao(getDataBaseConnection())
                        .getSearchID(hotwireProduct.getProductVertical().getProductCode()));
    }

    public void setupPNRnumber() {
        c3ItineraryInfo.setPnrNumber(new C3SearchDao(getDataBaseConnection())
                .getPNR(hotwireProduct.getProductVertical().getProductCode()));
    }

    public void setupCustomerId() {
        customerInfo.setCustomerId(new C3CustomerDao(getDataBaseConnection()).getCustomerId());
        customerInfo.setEmail(new C3CustomerDao(getDataBaseConnection())
                .getEmailByCustomerId(customerInfo.getCustomerId()));
    }

    public void setupCaseId() {
        c3CaseNoteInfo.setCaseId(new C3CaseNotesDao(getDataBaseConnection()).getRandomCaseId());
        customerInfo.setEmail(new C3CaseNotesDao(getDataBaseConnection()).getEmailByCaseId(c3CaseNoteInfo.getCaseId()));
    }

    public void setupHotelReservationNumber() {
        c3ItineraryInfo.setHotelReservationNumber(new C3SearchDao(getDataBaseConnection())
                .getRandomHotelReservationNumber());
        customerInfo.setEmail(new C3CustomerDao(getDataBaseConnection())
                                        .getEmailByHotelReservationNumber(c3ItineraryInfo.getHotelReservationNumber()));
    }

    public void setupAirTicketNumber() {
        c3ItineraryInfo.setAirTicketNumber(new C3SearchDao(getDataBaseConnection())
                .getRandomAirTicketNumber());
        customerInfo.setEmail(new C3CustomerDao(getDataBaseConnection())
                .getEmailByAirTicketNumber(c3ItineraryInfo.getAirTicketNumber()));
    }

    public void setupCarReservationNumber() {
        c3ItineraryInfo.setCarReservationNumber(new C3SearchDao(getDataBaseConnection())
                .getRandomCarReservationNumber());
        customerInfo.setEmail(new C3CustomerDao(getDataBaseConnection())
                .getEmailByCarReservationNumber(c3ItineraryInfo.getCarReservationNumber()));
    }

    public void setupHotelPurchaseWithParams() {
        String opaqCode = hotwireProduct.getOpacity().getOpacityCode();
        String itineraryNum = new C3SearchDao(getDataBaseConnection())
                .getHotelItineraryWithParams(billingInfo.isWithInsurance(), opaqCode);
        logItinerary();
        c3ItineraryInfo.setItineraryNumber(itineraryNum);
    }

    public void goToHelpCenter() {
        new HWGlobalHeader(getWebdriverInstance()).clickHelpCenterLink();
    }

    public void forceVT(String versionTest) {
        getWebdriverInstance().get(getWebdriverInstance().getCurrentUrl() + "?vt." + versionTest);
    }

    public void setupCaseNoteID() {
        c3ItineraryInfo.setCaseNotesNumber(new C3CaseNotesDao(getDataBaseConnection()).getCaseNoteId());
    }

    public void setupCaseNoteEmail() {
        customerInfo.setEmail(new C3CaseNotesDao(getDataBaseConnection()).getCaseNotesEmail());
    }

    public void setupCaseNoteItinerary() {
        c3ItineraryInfo.setItineraryNumber(new C3CaseNotesDao(getDataBaseConnection()).getCaseNotesItinerary());
    }

    public void setupC3LPGClaim() {
        final String itinerary = hotwireProduct.getItinerary(new C3LowPriceGuaranteeDao(getDataBaseConnection()));
        c3ItineraryInfo.setItineraryNumber(itinerary);
    }

    public void setupLPGRefundRequest() {
        Map result = new C3LPGRefundRequestDao(getDataBaseConnection())
                .getPurchaseForLPGRefundRequest(hotwireProduct.getProductVerticalName());
        String itineraryNum = result.get("display_number").toString();
        logItinerary();
        c3ItineraryInfo.setItineraryNumber(itineraryNum);
        c3ItineraryInfo.setPurchaseOrderId(result.get("purchase_order_id").toString());
        c3ItineraryInfo.setReservationId(result.get("reservation_id").toString());
        customerInfo.setEmail(result.get("email").toString());
    }

    public void setupPreviousLPGPurchase() {
        c3ItineraryInfo.setItineraryNumber(new C3LowPriceGuaranteeDao(getDataBaseConnection())
                .getCarItineraryWithPreviousLPG(hotwireProduct.getProductVerticalName()));
    }

    public void setupExpiredLPGRefundRequest() {
        Map result = new C3LPGRefundRequestDao(getDataBaseConnection())
                .getItineraryWithExpiredRefundRequest(hotwireProduct.getProductVerticalName());
        c3ItineraryInfo.setItineraryNumber(result.get("display_number").toString());
        c3ItineraryInfo.setPurchaseOrderId(result.get("purchase_order_id").toString());
        customerInfo.setEmail(result.get("email").toString());
    }

    public void setupExpiredLPGPurchase() {
        c3ItineraryInfo.setItineraryNumber(new C3LowPriceGuaranteeDao(getDataBaseConnection())
                .getCarItineraryWithExpiredLPG());
    }

    public void verifyStatusInDataBase(String status, String table) {
        switch (C3VerificationDao.DataBaseTables.validate(table)) {
            case PURCHASE_ORDER:
                Assertions.assertThat(new C3VerificationDao(getDataBaseConnection())
                        .verifyPurchaseOrderStatus(c3ItineraryInfo.getItineraryNumber()))
                        .isEqualTo(status);
                break;
            case RESERVATION:
                Map<String, BigDecimal> result = new HashMap();
                result.put("STATUS_CODE", new BigDecimal(status));
                Assertions.assertThat(new C3VerificationDao(getDataBaseConnection())
                        .verifyReservationStatus(c3ItineraryInfo.getItineraryNumber())).contains(result);
                break;
            default:
                throw  new UnimplementedTestException("No such table or not implemented.");
        }
    }

    public void setupC3PurchaseByCustomerType() {
        switch (customerInfo.getCustomerType()) {
            case GUEST:
                c3ItineraryInfo.setItineraryNumber(new C3CustomerDao(getDataBaseConnection()).getGuestPurchase());
                break;
            case NON_EXPRESS:
                c3ItineraryInfo.setItineraryNumber(new C3CustomerDao(getDataBaseConnection()).getNonExpressPurchase());
                break;
            case EX_EXPRESS:
                Map exExpressPurchase = new C3CustomerDao(getDataBaseConnection()).getExExpressPurchase();
                c3ItineraryInfo.setItineraryNumber(exExpressPurchase.get("DISPLAY_NUMBER").toString());
                customerInfo.setExpressDownGradeDate(new DateTime(exExpressPurchase.get("is_preferred_change_date")));
                break;
            case EXPRESS:
                c3ItineraryInfo.setItineraryNumber(new C3CustomerDao(getDataBaseConnection()).getExpressPurchase());
                break;
            case EXPRESS_ELITE:
                c3ItineraryInfo.setItineraryNumber(new C3CustomerDao(getDataBaseConnection())
                        .getExpressElitePurchase());
                break;
            case PARTNER:
                c3ItineraryInfo.setItineraryNumber(new C3CustomerDao(getDataBaseConnection()).getPartnerPurchase());
                break;
            default:
                throw new UnimplementedTestException("No such customer type!");
        }
    }

    public void goToConfirmationPageByLink() throws MalformedURLException {
        getWebdriverInstance().get(getConfirmationPageLink(c3ItineraryInfo.getPurchaseOrderId()));
    }

    private String getConfirmationPageLink(String purchaseOrder) throws MalformedURLException {
        URL url = appMetaData.getApplicationUrl();
        switch (hotwireProduct.getProductVertical()) {
            case CAR:
                return url + "/account/car-purchase-details.jsp?purchaseOrderId=" + purchaseOrder;
            case AIR:
                return url + "/account/air-purchase-details.jsp?purchaseOrderId=" + purchaseOrder +
                        "&selectedReservationId=" + c3ItineraryInfo.getReservationId();
            default:
                return url + "/account-unverified/doTripDetails?purchaseOrderId=" + purchaseOrder;
        }
    }

    public void verifyMsgOnC3LoginPage(String msg) {
        C3LoginPage c3LoginPage = new C3LoginPage(getWebdriverInstance());
        assertThat(c3LoginPage.getValidationMsg()).isEqualTo(msg);
        assertThat(c3LoginPage.getEmailFormat()).isEqualTo("fieldError");
        assertThat(c3LoginPage.getPasswordFormat()).isEqualTo("fieldError");
    }

    public void switchToSiteInC3(String countryCode) {
        new C3FareFinder(getWebdriverInstance()).switchToSite(countryCode);
    }

    public void setupIPAddressCustomer() {
        customerInfo.setIpAddress(new C3CustomerDao(getDataBaseConnection()).getIPAddress());
    }

    public void setupIPAddressCustomerWithFewPurchase() {
        customerInfo.setIpAddress(new C3CustomerDao(getDataBaseConnection()).getIPAddressWithFewPurchase());
    }

    public void setupItineraryWithIPAddressCustomer() {
        c3ItineraryInfo.setItineraryNumber(new C3SearchDao(getDataBaseConnection()).getItineraryWithIPAddress());
    }

    public void setupExistingOrderID() {
        Map result = new C3SearchDao(getDataBaseConnection()).getOrderIDandDisplayNum();
        c3ItineraryInfo.setItineraryNumber(result.get("display_number").toString());
        c3ItineraryInfo.setPurchaseOrderId(result.get("purchase_order_id").toString());
        c3ItineraryInfo.setReservationId(result.get("reservation_num").toString());
        customerInfo.setEmail(result.get("email").toString());
    }

    public void openLandingPage() {
        final HWGlobalHeader hwGlobalHeader = new HWGlobalHeader(getWebdriverInstance());
        try {
            hotwireProduct.getProductVertical().equals(null);
        }
        catch (NullPointerException e) {
            hotwireProduct.setProductVertical(ProductVertical.HOTEL); // hotel by default
        }
        switch (hotwireProduct.getProductVertical()) {
            case AIR:
                hwGlobalHeader.goToAirLandingPage();
                break;
            case CAR:
                hwGlobalHeader.goToCarLandingPage();
                break;
            case HOTEL:
                hwGlobalHeader.goToHotelLandingPage();
                break;
            default:
                throw new UnimplementedTestException("No such product vertical");
        }
    }

    public void verifyDealHash() {
        new HWGlobalHeader(getWebdriverInstance()).verifyDealHash();
    }

    public void openReferralLink() throws MalformedURLException {
        addParamsToUrlAndGo(appMetaData.getApplicationUrl().toString(), appMetaData.getReferralParams());
    }

    public void verifyReferralInDb(String column, String expectedValue) {
        if (referralData == null) {
            String searchId = c3ItineraryInfo.getReferenceNumberResults().substring(1);
            referralData = new C3SearchDao(getDataBaseConnection()).getRecentReferralInfo(searchId);
        }
        if (!expectedValue.isEmpty()) {
            Assertions.assertThat(referralData.get(column).toString()).isEqualTo(expectedValue);
        }
    }

    public void goToCarReferralURL() {
        StringBuilder link = new StringBuilder();
        link.append(getWebdriverInstance().getCurrentUrl().replace("/car/index.jsp", ""));
        link.append("/seo/car/d-city/");
        link.append(tripInfo.getDestinationLocation());
        Map params = appMetaData.getReferralParams();
        for (String key: appMetaData.getReferralParams().keySet()) {
            link.append("/");
            link.append(key);
            link.append("/");
            link.append(params.get(key));
        }
        getWebdriverInstance().get(link.toString());
    }

    public void verifyPixelPresence(boolean presence, String pixelName) {
        String pageSource = getWebdriverInstance().getPageSource();
        boolean pixelVisibility;
        if (pixelName.equals("pointroll")) {
            pixelVisibility = pageSource.contains("http://clients.pointroll.com/clients");
        }
        else if (pixelName.equals("triggit")) {
            pixelVisibility = pageSource.contains("http://a.triggit.com");
        }
        else {
            pixelVisibility = false;
        }
        Assertions.assertThat(pixelVisibility).isEqualTo(presence);
    }

    public void setupDisplayNumberWithCreditCard() {
        c3ItineraryInfo.setItineraryNumber(new C3SearchDao(getDataBaseConnection()).getItineraryWithCreditCard());
    }

    public void setupC3CarOpacityPurchase(String opacity) {
        c3ItineraryInfo.setItineraryNumber(new C3SearchDao(getDataBaseConnection())
                .getItineraryForOpacityCarPurchase(opacity));
    }

    public void setupC3AirOpacityPurchase(String opacity) {
        c3ItineraryInfo.setItineraryNumber(new C3SearchDao(getDataBaseConnection())
                .getItineraryForOpacityAirPurchase(opacity));
    }

    public void setupC3HotelOpacityPurchase(String opacity) {
        c3ItineraryInfo.setItineraryNumber(new C3SearchDao(getDataBaseConnection())
                .getItineraryForOpacityHotelPurchase(opacity));
    }

    public void setCurrency() {
        new HWGlobalHeader(getWebdriverInstance()).setCurrency(billingInfo.getCurrencyCode());
    }

    public void setupURLwithBlankConfirmationNumber() throws MalformedURLException {
        String confirmationNumber = c3ItineraryInfo.getItineraryNumber();
        String searchReferenceNumber = c3ItineraryInfo.getReferenceNumberResults();
        confirmationNumber = (confirmationNumber == null) ? "blank" : confirmationNumber;
        searchReferenceNumber = (searchReferenceNumber == null) ? "blank" : searchReferenceNumber;
        String referralSource = appMetaData.getReferralParams().get("REFERRAL_SOURCE");

        String sUrl = appMetaData.getApplicationUrl().toString() + "/frameset.jsp?frameStrutsId=" +
                "c3-customer-search-results&frameParameterString=confirmationNumber@" + confirmationNumber + "@" +
                "@c3ReferralSource@" + referralSource + "@@searchReferenceNumber@" + searchReferenceNumber;
        LOGGER.info(sUrl);
        getWebdriverInstance().navigate().to(sUrl);
    }

    public String getItineraryAVC() {
        return new C3SearchDao(getDataBaseConnection()).getItineraryAVC();
    }

    public void setupC3PurchaseWithEmail(String email) {
        c3ItineraryInfo.setItineraryNumber(new C3CustomerDao(getDataBaseConnection())
                .getItineraryOfEmail(email));
    }

    public void openSavedUrl() {
        getWebdriverInstance().get(appMetaData.getUrl());
    }

    public void openDeeplinkResultsUrl() {
        getWebdriverInstance().get(appMetaData.getDeeplinkResultsUrl());
    }

    public void openDeeplinkDetailsUrl() {
        getWebdriverInstance().get(appMetaData.getDeeplinkDetailsUrl());
    }

    public void setupC3CustomerWithAirPartialRefund() {
        customerInfo.setEmail(new C3PartialRefundDao(getDataBaseConnection())
                .getEmailWithAirPartialRefund().get("EMAIL").toString());
        c3ItineraryInfo.setItineraryNumber(new C3PartialRefundDao(getDataBaseConnection())
                .getEmailWithAirPartialRefund().get("DISPLAY_NUMBER").toString());
        hotwireProduct.setVertical("Air");
    }


    public void gotoPaymentInfoOnDomesticSite() {
        new HWMyAccount(getWebdriverInstance()).openPaymentInfo();
    }

    public void verifyNoHotDollarsAvailableOnDomesticSite() {
        AccountPaymentInfoPage paymentInfoPage = new AccountPaymentInfoPage(getWebdriverInstance());

        assertThat(paymentInfoPage.isNoAvailableHotDollarsMsgExist())
                .as("No availabe hotdollars message doesn't exist").isTrue();
        assertThat(paymentInfoPage.doesNoExpiredHotDollarsMsgExist())
                .as("No expired hotdollars message doesn't exist").isTrue();
        assertThat(paymentInfoPage.isNoActiveHotDollarsMsgExist())
                .as("No active hotdollars message doesn't exist").isTrue();
    }

    public void setCustomerWithUniqueName() {
        String name = new C3CustomerDao(getDataBaseConnection()).getCustomerWithUniqueName();
        customerInfo.setCustomerByUniqueLastName(name);
    }

    public void verifyExpiredHotDollarsOnDomesticSite() {
        AccountPaymentInfoPage paymentInfoPage = new AccountPaymentInfoPage(getWebdriverInstance());

        List<Object> expiredHotdollars = new C3SearchDao(databaseSupport).
                getExpiredHotDollarsForCustomer(customerInfo.getEmail());

        List<String> expiredHDActual = paymentInfoPage.getExpiredHotDollarsList();

        assertThat(expiredHDActual.size()).as("size").isEqualTo(expiredHotdollars.size());

        for (int i = 0; i < expiredHDActual.size(); i++) {
            assertThat(expiredHDActual.get(i).toString()).as("position i=" + i)
                    .isEqualTo(expiredHotdollars.get(i).toString());
        }
    }

    public void setCustomerWithPopularName() {
        Map names = new C3CustomerDao(getDataBaseConnection()).getCustomerWithPopularName();
        customerInfo.setCustomerName(names);
    }

    public void clickLogo() {
        GlobalHeader globalHeader  = new GlobalHeader(getWebdriverInstance());
        globalHeader.clickOnHotwireLogo();
    }

    public void switchToC3Frame() {
        switchToDefaultContent();
        switchToFrame("c3Frame");
    }


    public void verifyRemainderOfDollarsOnDomesticSite() {
        AccountPaymentInfoPage paymentInfoPage = new AccountPaymentInfoPage(getWebdriverInstance());
        if (customerInfo.getHotDollarsAmount() > 0) {
            assertThat(Double.parseDouble(paymentInfoPage.getFirstAvailableHotDollarsAmount().replaceAll("[$,]", "")))
                    .as("Available hodollars on domestic site is incorrect")
                    .isEqualTo(customerInfo.getHotDollarsAmount(), Delta.delta(0.1));

        }
        else {
            assertThat(paymentInfoPage.isNoAvailableHotDollarsMsgExist())
                    .as("No availabe hotdollars message doesn't exist").isTrue();
        }
    }

    public void verifyHotDollarsActivityOnDomesticSite() {
        AccountPaymentInfoPage paymentInfoPage = new AccountPaymentInfoPage(getWebdriverInstance());
        assertThat(paymentInfoPage.getAmountsOfAllHotDollars())
                .as("Recently used hotdollars on domestic doesn't exist on the page")
                .contains(customerInfo.getRecentlyUsedDollars());
    }
}



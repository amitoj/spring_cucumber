/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.application;

import java.io.IOException;
import java.util.List;

import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.test.steps.common.PGoodCode;

/**
 * This class includes methods that manage application set up steps
 */
public interface ApplicationModel {

    void selectPOS(String pos);

    void selectCurrency(String currencyCode, boolean force);

    void setupOrVerifyDefaultData();

    void setupOrVerifyApplicationAndAccessMethod();

    /**
     * Add version test to url
     * @param vtName
     *            String
     * @param vtValue
     *            String
     */
    void addVersionTest(String vtName, String vtValue);

    /**
     * Setup property on the test/refreshUtil.jsp page to DEFAULT value
     * @param propName
     *            String
     */
    void dropRefreshUtilProperty(String propName);

    void setupURL_ToVisit(String url);

    void verifySearchPage(PGoodCode pGoodCode);

    void verifyHomePage(String channel);

    void verifySite();

    void verifySignInPage();

    void verifyRegistrationPage();

    void verifyPasswordAssistancePage();

    void verifyAirIndexPage();

    void verifyAirPageWithOrWithoutResults();

    void verifyAirInformationPage();

    void verifySurveyPage(String vertical);

    void verifyDestinationInformationPage(String destination);

    /**
     * Check that error of confirmation message appear in block with title "Please correct the following field(s)" or
     * "Confirmation"
     * @param messageText
     *            String
     * @param messageType
     *            String - {error | successful}
     */
    void verifyMessageOnPage(String messageText, ErrorMessenger.MessageType messageType);

    void verifyPriceCheckMessageOnPage(String messageText, ErrorMessenger.MessageType messageType);

    void verifyResultsPage(PGoodCode pGoodCode);

    void selectFullSiteView();

    void verifyTripSummaryPage();

    void unifyCarDetailsAndBilling();

    void activateCarVersionTests();

    void verifyHotwireBranded3rdPartyMobileCarSite();

    void setupOnlinePartnerMarketing();

    void activateLastMinuteCruiseDeals();

    void activateTopCruiseDeals();

    void verifyCurrency(String currency);

    void verifyCountry(String country);

    void verifySupportedCurrencies(String... supportedCurrencies);

    void loadTripStarter();

    void loadGoLocalSearch();

    void deactivateIntentMediaLayer();

    void loadItnlSite(String country);

    void checkItnlSiteURL(String country);

    void activateHwRecommendations();

    void activateBillingValidationMarks();

    void setTestSessionModifyingParam();

    void verifyHomepageLandingForTestingOnly();

    void activateUpdatedAgreeAndBookSectionOnBilling();

    void verifyHomepageHasTimeoutMessage();

    void activateLowPriceGuaranteeModule();

    void activateReducedNumberSetOfCreditCardFields();

    void activateOpaqueRoomPhotos();

    void refreshPage();

    void activateDetailsXsellList(boolean doListCrossSell);

    void verifyDBPurchaseStatus(String status);

    void openUrlOnMobileDevice(String url) throws IOException;

    void verifyCurrentUrl(String url) throws IOException;

    void verifyMobileAppAfterRedirection(String content);

    void verifyErrorMessageForIncorrectLocation(String typeOfLocation);

    void setNewAirLowPriceModule();

    void loadURLwithDestCity(String sDestination);

    void navigateToExternalLink(String sParameter);

    void verifyDB_SIB_BID_Reg_Referral_Status(List<String> status, String table);

    void setupURLwithSidBid(String sSidBid);

    void loadURLForReferralType(String referralType);

    String getSearchIDFromDB(String email, String vertical);

    String getSearchIDFromDB(String email);

    String getCustomerIDFromDB(String email);

    void enableAngularApp(boolean enableAngular);

    void closeAllPopUpWindows();

    void verifyPageURL(String url);

    void clickOnLink(String linkText);

    void verifySavedCreditCardInDB(String userEmail, String condition);

    void verifyUHPPage();

    void verifyNonExistentPageForIncorrectURL();

    void goToAndVerifyOldHotelIndexPage();

    void submitCommentCard(String comment, int content, int design, int usability, int overall);

    void confirmThePageHeader(String pageHeader);

    void verifyContentInPage(String textInPage);

    void maximizeWindow();

    void enterEmailAddressInSubscriptionEmailField();

    void verifyDBcolumnsWantsNewsLetterAndThirdParty();

    void enterRegisteredEmailInSubscriptionEmailField();

    void browseOnePageBack();

    String getPaymentProcessor();

    void goToBexBucket();

    void clearCookies();

    void enableESS();

}


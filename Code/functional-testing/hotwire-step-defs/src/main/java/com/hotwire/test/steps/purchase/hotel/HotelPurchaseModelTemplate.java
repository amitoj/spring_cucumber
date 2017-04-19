/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.annotation.Resource;

import com.hotwire.selenium.desktop.common.billing.HotelTripSummaryFragment;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelHotdollarsFragment;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelTripInsuranceFragment;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotwire.selenium.desktop.common.billing.HotelBillingPage;
import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelBillingOnePage;
import com.hotwire.selenium.desktop.us.confirm.HotelConfirmationPage;
import com.hotwire.selenium.desktop.us.confirm.HotelNewConfirmationPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsSearchResultsFragment;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.purchase.PurchaseModelTemplate;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters.PaymentMethodType;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.PageName;

import cucumber.api.PendingException;

/**
 * @author v-ypuchkova
 * @since 2013.04
 */
public class HotelPurchaseModelTemplate extends PurchaseModelTemplate implements HotelPurchaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelPurchaseModelTemplate.class);

    @Autowired
    @Qualifier("hotelSearchParameters")
    protected HotelSearchParameters hotelSearchParameters;

    @Resource(name = "canadianZipCode")
    private String canadianZipCode;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Good for US and ROW due to convergence.
     */
    @Override
    public void selectPGood() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        List<SearchSolution> searchSolutionList = resultsPage.getSearchSolutionList();
        hotelSearchParameters.setSelectedSearchSolution(searchSolutionList
                                                            .get(purchaseParameters.getResultNumberToSelect()));
        resultsPage.select(purchaseParameters.getResultNumberToSelect());

        if (new PageName().apply(getWebdriverInstance()).contains(".search.results") &&
            getWebdriverInstance().findElements(By.cssSelector(".errorMessage")).size() > 0) {
            throw new PendingException("Inventory check redirected to results page due to sold out.");
        }
    }

    /**
     * Good for US and ROW due to convergence of hotel details page.
     */
    @Override
    public void continueToBillingFromDetails() {
        new HotelDetailsPage(getWebdriverInstance()).clickOnBookButton();
        if (new PageName().apply(getWebdriverInstance()).contains(".search.results")) {
            throw new PendingException("Inventory check going to Billing redirected to results page due to sold out.");
        }
    }

    @Override
    public void bookSelectedHotel() {
        new HotelDetailsPage(getWebdriverInstance()).clickOnBookButton();
        if (new PageName().apply(getWebdriverInstance()).contains(".search.results")) {
            throw new PendingException("Inventory check going to Billing redirected to results page due to sold out.");
        }
    }

    @Override
    public void fillTravelInfoForHotel() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkTicketTypesSortedOrNot() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectActivities() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateInsuranceCostRegardingRoomNumbers() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateBillingErrors(String bookingProfile, boolean isSignedInUser) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateTravelerErrors(String bookingProfile) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkMoreDetailsLinks() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySummaryOfChargesChargesOnOpaqueHotelBillingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyErrorMessage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyMandatoryFee(String fee) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDiscountBannerOnHotelDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void editCard() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDiscountBannerOnHotelItinerary(boolean isSignedInUser) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void continueToBillingFromDetailsBySelectingPrice() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void landonBillingPageToPurchase() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyMarketingBeacon() {
        assertThat(
            getWebdriverInstance().getPageSource().contains(
                "script type=\"text/javascript\">\n" +
                    "            AnalyticsSupport.addMarketingBeaconUrl(\"https://www.googleadservices.com/pagead/" +
                    "conversion/1030960077/?label=kgZrCJP9qAIQzefM6wM&amp;amp;guid=ON&amp;amp;script=0\");\n" +
                "        </script>"))
                .isTrue();
    }

    @Override
    public void verifyFeesAndTaxesOnConfirmation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyValidationMarks(String fields, String marks) {
        throw new UnimplementedTestException("Implement me!");
    }

    /** Good for ROW and US */
    @Override
    public void verifyResortFeesOnDetailsPage(boolean resortFeeVisible) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        if (resortFeeVisible) {
            assertThat(detailsPage.isResortFeesDisplayed())
                    .as("Expected resort fees to be displayed but it was not.")
                    .isTrue();
            LOGGER.info("Resort Fee Text: " + detailsPage.getResortFeeText());
        }
        else {
            assertThat(detailsPage.isResortFeesDisplayed())
                    .as("Expected resort fees to be displayed but it was not.")
                    .isFalse();
        }
    }

    /** Good for ROW and US */
    @Override
    public void verifyLastHotelBookedOnOpaqueDetailsPage() {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        boolean lastBookedDisplayed = detailsPage.isLastBookedHotelMessageDisplayed();
        assertThat(lastBookedDisplayed)
                .as("Expected last booked hotel message to be displayed but it wasn't.")
                .isTrue();
        LOGGER.info("Last Booked Hotel Message: " + detailsPage.getLastBookedHotelMessage());
    }

    /** Good for ROW and US */
    @Override
    public void chooseHotelResultWhoseNameContains(String nameSubstring) {
        HotelResultsSearchResultsFragment resultsFragment = new HotelResultsPage(getWebdriverInstance())
            .getSearchResultsFragment();
        if (!resultsFragment.selectResultWhoseNameContains(nameSubstring)) {
            throw new ZeroResultsTestException("No results found whose name contains: " + nameSubstring);
        }
        if (new PageName().apply(getWebdriverInstance()).contains(".search.results") &&
            getWebdriverInstance().findElements(By.cssSelector(".errorMessage")).size() > 0) {
            throw new PendingException("Inventory check redirected to results page due to sold out.");
        }
    }

    /** Good for ROW and US */
    @Override
    public void chooseRetailResortResult() {
        HotelResultsSearchResultsFragment resultsFragment = new HotelResultsPage(getWebdriverInstance())
            .getSearchResultsFragment();
        boolean found = false;
        for (String href : resultsFragment.getSearchResultsHrefList()) {
            getWebdriverInstance().navigate().to(href);
            if (new PageName().apply(getWebdriverInstance()).contains(".search.results") &&
                getWebdriverInstance().findElements(By.cssSelector(".errorMessage")).size() > 0) {
                continue;
            }
            HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
            if (detailsPage.isResortFeesDisplayed()) {
                found = true;
                break;
            }
            detailsPage.clickBackToResults();
            new HotelResultsPage(getWebdriverInstance());
        }
        if (!found) {
            throw new PendingException("0 retail solutions with resort fees.");
        }
    }

    @Override
    public void verifyResortFeesOnBillingTripSummary(boolean resortFeeVisible) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySummaryOfChargesOnNewBillingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyRetailChargesAreStoredInDBCorrectly() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyOpaqueChargesAreStoredInDBCorrectly() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseAddNewTravelerFromSelection() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDetailsPage(String detailsType) {
        new HotelDetailsPage(getWebdriverInstance());
        String pageName = new PageName().apply(getWebdriverInstance());
        if (detailsType.equals("opaque")) {
            assertThat(pageName.contains("hotel.details.opaque")).isTrue();
        }
        else if (detailsType.equals("retail")) {
            assertThat(pageName.contains("hotel.details.retail")).isTrue();
        }
    }

    @Override
    public void verifyEmailsPopulatedInTravelerInfoBillingPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPointrollPixelOnCertainPage(String pageName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTriggItPixelOnCertainPage(String pageName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void filteredByHoodName(String hoodName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setADAAmenities() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkHotelADAAmenityConsist() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateCreditCardLogoVisibility(String visibility, String cardName) {
        if (visibility.contains("don't")) {
            assertThat(new HotelBillingPage(getWebdriverInstance()).isCreditCardLogoPresent(cardName))
                    .as("Maestro logo is presenting")
                    .isFalse();
        }
        else {
            assertThat(new HotelBillingPage(getWebdriverInstance()).isCreditCardLogoPresent(cardName))
                    .as("Maestro logo isn't presenting")
                    .isFalse();
        }
    }

    @Override
    public void fillTravelerUserInfoForGuestUser() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        hotelBillingPage.selectAsGuest();
        hotelBillingPage.fillTravelerInfo()
                .withFirstName(userInformation.getFirstName())
                .withMiddleName(userInformation.getMiddleName())
                .withLastName(userInformation.getLastName())
                .withPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber())
                .withEmailId(userInformation.getEmailId())
                .continuePanel();
    }

    @Override
    public void fillTravelerUserInfoForLoggedUser() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        hotelBillingPage.fillTravelerInfo()
                .withFirstName(userInformation.getFirstName())
                .withLastName(userInformation.getLastName())
                .withPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber())
                .continuePanel();
    }

    public void fillTravelerUserInfo(String firstName, String middleName, String lastName, String primaryPhoneNumber,
        String emailId) {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        if (firstName == null) {
            firstName = userInformation.getFirstName();
        }
        if (lastName == null) {
            lastName = userInformation.getLastName();
        }
        if (primaryPhoneNumber == null) {
            primaryPhoneNumber = userInformation.getPrimaryPhoneNumber();
        }
        if (emailId == null) {
            emailId = userInformation.getEmailId();
        }

        new HotelBillingOnePage(getWebdriverInstance()).fillTravelerInfo()
                .withFirstName(firstName)
                .withMiddleName(middleName)
                .withLastName(lastName)
                .withPrimaryPhoneNumber(primaryPhoneNumber)
                .withEmailId(emailId)
                .continuePanel();
    }

    @Override
    public void chooseRetailHeroResult() {
        new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment().clickRetailHeroResult();
        if (new PageName().apply(getWebdriverInstance()).contains(".search.results") &&
            getWebdriverInstance().findElements(By.cssSelector(".notificationMessages")).size() > 0) {
            throw new PendingException("Returned to results page due to inventory check.");
        }
    }

    @Override
    public void goBackToResultsFromDetailsPage() {
        new HotelDetailsPage(getWebdriverInstance()).clickBackToResults();
    }

    @Override
    public void verifySaveBillingInfoVisibility(boolean isDisplayed) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAllPaymentOptionsEnabled() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCreditCardOnlyEnabled() {
        throw new UnimplementedTestException("Implement me!");
    }

    // row and domestic
    @Override
    public void verifyInsuranceModuleVisibilityOnBillingPage(boolean isVisible) {
        assertThat(!new HotelTripInsuranceFragment(getWebdriverInstance()).isTripInsuranceUnavailable())
                .as("Visible of Trip insurance module should be " + isVisible)
                .isEqualTo(isVisible);
    }

    @Override
    public void verifyInsuranceUpdateOnBillingTripSummary(boolean isDisplayed) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public String getItineraryNumber() {
        return new HotelNewConfirmationPage(getWebdriverInstance()).getItineraryNumber();
    }

    @Override
    public void verifyPurchaseDetailsOnBillingAndConfirmationPages() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillCreditCard() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());

        String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
            userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode : userInformation
            .getZipCode();
        hotelBillingPage.fillCreditCard().withCcType(userInformation.getCcType())
                .withCcNumber(userInformation.getCcNumber())
                .withCcExpMonth(userInformation.getCcExpMonth())
                .withCcExpYear(userInformation.getCcExpYear())
                .withSecurityCode(userInformation.getSecurityCode())
                .withFirstName(userInformation.getCcFirstName())
                .withLastName(userInformation.getCcLastName())
                .withCountry(userInformation.getBillingCountry())
                .withBillingAddress(userInformation.getBillingAddress())
                .withCity(userInformation.getCity())
                .withState(userInformation.getState())
                .withZipCode(zipCode);
    }

    @Override
    public void selectSavedCreditCard() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        new HotelBillingOnePage(getWebdriverInstance()).fillCreditCard()
                .selectSavedCreditCard(purchaseParameters.getSavedCreditCard().getNames(),
                        userInformation.getSecurityCode());
    }

    private void fillTravelerInfo(boolean guestOrUser) {
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());

        if (guestOrUser) {
            if (!new GlobalHeader(getWebdriverInstance()).isLoggedIn()) {
                hotelBillingPage.selectAsUser(authenticationParameters.getUsername(),
                        authenticationParameters.getPassword());
            }
            this.fillTravelerUserInfoForLoggedUser();
        }
        else {
            this.fillTravelerUserInfoForGuestUser();
        }
    }

    public void processPaymentByType(PurchaseParameters.PaymentMethodType methodType) {
        UserInformation userInformation = purchaseParameters.getUserInformation();

        LOGGER.info("Processing payment information by type: {}", methodType);
        if (methodType == PaymentMethodType.HotDollars) {
            new HotelHotdollarsFragment(getWebdriverInstance()).setHotdollarsCheckBox(true);
        }
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());

        if (hotelBillingPage.isPaymentMethodsFragmentPresent()) {
            if (methodType.equals(PaymentMethodType.PayPal)) {
                hotelBillingPage.fillPayPalPaymentMethod()
                        .withBillingAddress(userInformation.getBillingAddress())
                        .withFirstName(userInformation.getFirstName())
                        .withLastName(userInformation.getFirstName())
                        .withCity(userInformation.getCity())
                        .withState(userInformation.getState())
                        .withZipCode(userInformation.getZipCode())
                        .continuePanel();
            }
            else if (methodType.equals(PaymentMethodType.CreditCard)) {
                LOGGER.info("Filling out credit card form.");
                fillCreditCard();
            }
            else if (methodType.equals(PaymentMethodType.SavedCreditCard)) {
                LOGGER.info("Looking for saved credit cards.");
                selectSavedCreditCard();
            }
            else {
                throw new IllegalArgumentException("Illegal type of payment method!!!");
            }
        }
    }

    public void processInsuranceOnBillingPage() {
        if (new GlobalHeader(getWebdriverInstance()).currentlyOnDomesticSite()) {
            this.selectInsuranceAddonOption(purchaseParameters.getOptForInsurance());
        }
    }

    public void processAgreeAndTerms() {
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        if (!purchaseParameters.getPaymentMethodType().equals(PurchaseParameters.PaymentMethodType.PayPal)) {
            LOGGER.info("PaymentMethodType is not paypal. Assume credit card type payment method.");
            hotelBillingPage.book();
            if (new PageName().apply(getWebdriverInstance()).contains(".search.results") &&
                    getWebdriverInstance().findElements(By.cssSelector(".errorMessage")).size() > 0) {
                throw new PendingException("Inventory check redirected to results page due to sold out.");
            }
        }
        else {
            LOGGER.info("PaymentMethodType set to paypal. Doing continuing to paypal flow.");
            hotelBillingPage.bookPaypal();
        }
    }

    /* ROW and domestic. */
    @Override
    public void completePurchase(Boolean guestOrUser) {

        this.fillTravelerInfo(guestOrUser);

        // Insurance only for domestic site.
        this.processInsuranceOnBillingPage();

        //select and fill payment
        this.processPaymentByType(purchaseParameters.getPaymentMethodType());

        // Store total price from after selecting or declining insurance.
        this.saveBillingTotalInBean();

        //Agree with terms
        this.processAgreeAndTerms();
    }

    /* ROW and domestic. */
    @Override
    public void processDetailsPage() {
        new HotelDetailsPage(getWebdriverInstance()).select();
    }

    /* ROW and domestic. */
    @Override
    public void processBillingPage() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
        billingPage
                .setFirstName(userInformation.getFirstName())
                .setLastName(userInformation.getLastName())
                .setEmailAddress(userInformation.getEmailId())
                .setConfirmEmailAddress(userInformation.getEmailId())
                .setPhoneCountry(userInformation.getPhoneCountryCode())
                .setPhoneNumber(userInformation.getPrimaryPhoneNumber())
                .setSubscriptionState(false);

        billingPage
                .setCreditCardNumber(userInformation.getCcNumber())
                .setCardExpiryMonth(userInformation.getCcExpMonth())
                .setCardExpiryYear(userInformation.getCcExpYear())
                .setCVPNumber(userInformation.getSecurityCode())
                .setBillingFirstName(userInformation.getCcFirstName())
                .setBillingLastName(userInformation.getCcLastName())
                .setCountryCode(userInformation.getBillingCountry())
                .setBillingAddress1(userInformation.getBillingAddress())
                .setCity(userInformation.getCity())
                .setProvince(userInformation.getState())
                .setPostalCode(userInformation.getZipCode())
                .setAgreement(true)
                .confirmPayment();
    }

    @Override
    public void saveBillingTotalInBean() {
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        String billingTotal = hotelBillingPage.getTripSummaryFragment().getRawTotalPrice();
        if (StringUtils.isEmpty(billingTotal)) {
            throw new PendingException("Billing total is empty!");
        }
        LOGGER.info("BILLING TOTAL: " + billingTotal);
        purchaseParameters.setBillingTotal(billingTotal);
    }

    @Override
    public void completeHotelBookingWithSavedUser() {
        purchaseParameters.setPaymentMethodType(PaymentMethodType.SavedCreditCard);
        this.completePurchase(true);
    }

    @Override
    public void selectInsuranceAddonOption(boolean choice) {
        try {
            insuranceAddOnSelection(new HotelBillingOnePage(getWebdriverInstance()), choice);
        }
        catch (WebDriverException e) {
            LOGGER.info("Insurance panel is unavalable");
        }
    }

    protected void fillTravelerNames(HotelBillingOnePage billingPage) {
        billingPage.fillTravelerInfo().withFirstName("Test").withLastName("User").continuePanel();
    }

    protected void insuranceAddOnSelection(HotelBillingOnePage billingPage, boolean wantInsurance) {
        billingPage.fillAdditionalFeatures().withInsurance(wantInsurance).continuePanel();
    }

    @Override
    public void chooseSolutionByName(String name) {
        new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment().selectResultByName(name);
    }

    @Override
    public void clickEditCreditCardLink() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillSavedCreditCard() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void saveBillingInformation(String cardName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public int getSolutionNumberWithPrice(String cost, String condition) {
        int intCost = Integer.parseInt(cost);
        List<SearchSolution> searchSolutionList = new HotelResultsPage(getWebdriverInstance()).getSearchSolutionList();

        if ("less".equals(condition)) {
            for (SearchSolution searchSolution : searchSolutionList) {
                // substring needed to remove $ sign, for other currencies other
                // substring will be needed.
                if (Integer.parseInt(searchSolution.getPrice().substring(1)) < intCost) {
                    return searchSolution.getNumber();
                }
            }
        }
        else if ("more".equals(condition)) {
            for (SearchSolution searchSolution : searchSolutionList) {
                if (Integer.parseInt(searchSolution.getPrice().substring(1)) > intCost / 5) {
                    // price for domestic is per day
                    return searchSolution.getNumber();
                }
            }
        }
        return -1; // No solution that meet condition.
    }

    @Override
    public void chooseOpaqueResultWithRoomType(String roomType) {
        new HotelResultsPage(getWebdriverInstance());
        new HotelResultsSearchResultsFragment(getWebdriverInstance()).selectOpaqueResultWithRoomType(roomType);
    }

    @Override
    public void clickBackToResultsButton() {
        new HotelDetailsPage(getWebdriverInstance()).clickBackToResults();
    }

    @Override
    public void chooseCurrency(String currency) {
        new GlobalHeader(getWebdriverInstance()).changeCurrency(currency);
        purchaseParameters.setCurrencyCode(currency);
    }

    @Override
    public void verifyHotelDetailsPage() {
        new HotelDetailsPage(getWebdriverInstance());
    }

    @Override
    public void verifyHotelTypeOnDetails(String hotelType) {
        assertThat(new HotelDetailsPage(getWebdriverInstance()).getHotelSecretType().toUpperCase().contains(hotelType))
                .as("Secret hotel type doesn't appropriate room type")
                .isTrue();
    }

    @Override
    public void verifyHotelTypeOnOneBilling(String hotelType) {
        assertThat(new HotelBillingPage(getWebdriverInstance()).getHotelName().toUpperCase().contains(hotelType))
                .as("Secret hotel type doesn't appropriate room type")
                .isTrue();
    }

    @Override
    public void clickConfirmationPageCarCrossSell() {
        throw new PendingException("Not implemented for this profile.");
    }

    /**
     * For ROW and domestic webapp.
     */
    @Override
    public void verifyConfirmationPageCarCrossSell(boolean shouldBeDisplayed) {
        boolean actual = new HotelConfirmationPage(getWebdriverInstance()).isCarCrossSellDisplayed();
        assertThat(actual)
                .as("Expected car cross sell displayed to be " + shouldBeDisplayed + " but was instead " + actual)
                .isEqualTo(shouldBeDisplayed);
    }

    /**
     * For Row and domestic
     */
    @Override
    public void verifyOnBillingPage() {
        new HotelBillingOnePage(getWebdriverInstance());
    }

    /**
     * For Row and domestic.
     */
    @Override
    public void navigateBackToDetailsPage() {
        new HotelBillingOnePage(getWebdriverInstance()).clickBackToDetailsLink();
    }

    @Override
    public void continueOnBillingPageOnPaymentFragment() {
        new HotelBillingOnePage(getWebdriverInstance()).book();
    }

    @Override
    public void verifyAutofillCountryCode(String countryCode) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void filteredByStarRating(String rating) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillTravelerInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillBillingInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectHotelResultByStarRating(String rating) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateInsuranceCostIsPercentageOfTotal(int expectedPercentage) {
        throw new UnimplementedTestException("Implement me!");
    }

    /* (non-Javadoc)
     * @see com.hotwire.test.steps.purchase.hotel.HotelPurchaseModel#fillInTravelerInfoWithPhoneNumber()
     */
    @Override
    public void fillInTravelerInfoWithPhoneNumberAndBook() {
        HotelBillingOnePage billingPage = new HotelBillingOnePage(getWebdriverInstance());
        UserInformation userInformation = purchaseParameters.getUserInformation();
        billingPage.fillTravelerInfo()
                .withFirstName(userInformation.getFirstName())
                .withLastName(userInformation.getLastName())
                .withPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber())
                .continuePanel();

        if (new GlobalHeader(getWebdriverInstance()).currentlyOnDomesticSite()) {
            billingPage.fillAdditionalFeatures()
                    .withInsurance(purchaseParameters.getOptForInsurance())
                    .continuePanel();
        }
        billingPage.fillCreditCard().withSavedSecurityCode("666");
        purchaseParameters.setBillingTotal(billingPage.getTripSummaryFragment().getRawTotalPrice());
        billingPage = new HotelBillingOnePage(getWebdriverInstance());
        billingPage.book();
    }

    @Override
    public void validateInsuranceCostInDb(String insuranceCost) {
        throw new UnimplementedTestException("Implement me!");
    }

//    For Row and domestic
    @Override
    public void checkInsuranceCostForHotelOnBillingPage(String insuranceCost) {
        assertThat(new HotelTripSummaryFragment(getWebdriverInstance()).getTripInsuranceCost())
                .as("Trip protection should be " + insuranceCost +
                        "but it " + new HotelTripSummaryFragment(getWebdriverInstance()).getTripInsuranceCost())
                .isEqualTo(insuranceCost);
    }

    @Override
    public String getHotelProtectionCostOnConfirmation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void saveBillingInformation(String cardName, String password) {
        throw new UnimplementedTestException("Implement me!");
    }
}

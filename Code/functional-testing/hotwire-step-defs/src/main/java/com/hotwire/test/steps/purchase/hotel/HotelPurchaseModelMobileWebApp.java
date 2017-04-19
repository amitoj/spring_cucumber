/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.mobile.billing.MobileBillingPg;
import com.hotwire.selenium.mobile.billing.MobileBillingSignInPage;
import com.hotwire.selenium.mobile.billing.PaymentMethodFragmentGuest;
import com.hotwire.selenium.mobile.confirmation.MobileHotelConfirmationPage;
import com.hotwire.selenium.mobile.details.MobileHotelDetailsPage;
import com.hotwire.selenium.mobile.results.MobileHotelResultsPage;
import com.hotwire.selenium.mobile.view.ViewIds;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.functions.PageName;

import cucumber.api.PendingException;

/**
 * @author v-vyulun
 * @since 2012.03
 */
public class HotelPurchaseModelMobileWebApp extends HotelPurchaseModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelPurchaseModelMobileWebApp.class.getName());

    @Resource(name = "canadianZipCode")
    private String canadianZipCode;

    @Override
    public void selectPGood() {
        MobileHotelResultsPage mHotelResultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        mHotelResultsPage.select(this.purchaseParameters.getResultNumberToSelect());
        if (new PageName().apply(getWebdriverInstance()).contains(".results") &&
            getWebdriverInstance().findElements(By.cssSelector(".priceChangeMssg")).size() > 0) {
            // Got no rooms for this solution.
            throw new PendingException("Inventory check redirected to results page due to sold out.");
        }
    }

    @Override
    public void continueToBillingFromDetails() {
        MobileHotelDetailsPage mobileHotelDetailsPage = new MobileHotelDetailsPage(getWebdriverInstance());
        mobileHotelDetailsPage.select();
    }

    @Override
    public void fillTravelerInfo(UserInformation userInformation, boolean isSignedInUser) {

        if (isSignedInUser) {
            try {
                MobileBillingSignInPage mobileBillingSignInPage = new MobileBillingSignInPage(getWebdriverInstance());
                mobileBillingSignInPage.continueAsUser(
                    this.authenticationParameters.getUsername(),
                    this.authenticationParameters.getPassword()
                );
            }
            catch (RuntimeException e) {
                LOGGER.info("User already autentificated!");

                if (userInformation != null) {
                    MobileBillingPg mobileBillingPg = new MobileBillingPg(getWebdriverInstance());
                    mobileBillingPg.fillUserTravelerInfo().setTravelerFirstName(userInformation.getFirstName())
                        .setTravelerLastName(userInformation.getLastName())
                        .setTravelerPhoneNumber(userInformation.getPrimaryPhoneNumber());

                }
            }
        }
        else {
            MobileBillingSignInPage mobileBillingSignInPage = new MobileBillingSignInPage(getWebdriverInstance());
            MobileBillingPg mobileBillingPg = mobileBillingSignInPage.continueAsGuest();
            mobileBillingPg.fillGuestTravelerInfo()
                .withFirstName(userInformation.getFirstName())
                .withLastName(userInformation.getLastName())
                .withPhoneCountryCode(userInformation.getPhoneCountryCode())
                .withPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber())
                .withEmailId(userInformation.getEmailId())
                .continuePanel();
        }
    }

    @Override
    public void fillBillingInformation(UserInformation userInformation, boolean isSignedInUser) {
        MobileBillingPg mobileBillingPg = new MobileBillingPg(getWebdriverInstance());
        String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
            userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode :
            userInformation.getZipCode();
        if (isSignedInUser) {
            if (mobileBillingPg.fillPaymentMethod(isSignedInUser).isCcListAvalible()) {
                try {
                    mobileBillingPg
                        .fillPaymentMethod(isSignedInUser)
                        .setSavedCc(userInformation.getCcType())
                        .setSecurityCode(userInformation.getSecurityCode());
                    return;
                }
                catch (WebDriverException e) {
                    mobileBillingPg
                        .fillPaymentMethod(isSignedInUser)
                        .setSavedCc("Pay with new card");
                }
            }
        }
        else {
            mobileBillingPg
                .fillPaymentMethod(isSignedInUser)
                .setCcType(userInformation.getCcType());
        }
        mobileBillingPg
            .fillPaymentMethod(isSignedInUser)
            .setCcNumber(userInformation.getCcNumber())
            .setSecurityCode(userInformation.getSecurityCode())
            .setCcExpMonth(userInformation.getCcExpMonth())
            .setCcExpYear(userInformation.getCcExpYear())
            .setFirstName(userInformation.getBillingInformationName())
            .setLastName(userInformation.getLastName());
        try {
            mobileBillingPg.fillPaymentMethod(isSignedInUser).setCountry(userInformation.getBillingCountry());
        }
        catch (WebDriverException e) {
            LOGGER.info("Country drop down not present.");
        }
        try {
            mobileBillingPg.fillPaymentMethod(isSignedInUser).setBillingAddress(userInformation.getBillingAddress());
        }
        catch (WebDriverException e) {
            LOGGER.info("Billing address fields not present.");
        }
        try {
            mobileBillingPg.fillPaymentMethod(isSignedInUser).setCity(userInformation.getCity());
        }
        catch (WebDriverException e) {
            LOGGER.info("City field not present.");
        }
        try {
            mobileBillingPg.fillPaymentMethod(isSignedInUser).setState(
                userInformation.getState(), userInformation.getBillingCountry());
        }
        catch (WebDriverException e) {
            LOGGER.info("State or province field not present");
        }
        try {
            mobileBillingPg
                .fillPaymentMethod(isSignedInUser).setZipCode(zipCode);
        }
        catch (WebDriverException e) {
            LOGGER.info("Country has no zip code");
        }
    }

    @Override
    public void fillTravelerInfo() {
        fillTravelerInfo(purchaseParameters.getUserInformation(), false);
    }


    @Override
    public void fillBillingInfo() {
        fillBillingInformation(purchaseParameters.getUserInformation(), false);
        new PaymentMethodFragmentGuest(getWebdriverInstance(), ViewIds.TILE_BILLING_PAYMENT_INFO)
            .clickContinueButton();
    }

    @Override
    public void completePurchase(Boolean isSignedInUser) {
        fillTravelerInfo(purchaseParameters.getUserInformation(), isSignedInUser);
        fillBillingInformation(purchaseParameters.getUserInformation(), isSignedInUser);
        MobileBillingPg mobileBillingPg = new MobileBillingPg(getWebdriverInstance());
        PurchaseParameters.PaymentMethodType methodType = purchaseParameters.getPaymentMethodType();
        switch (methodType) {
            case PayPal:

                break;
            case HotDollars:
                mobileBillingPg.clickHotDollars();
                break;
            default:
                break;
        }
        // Store the total from the billing page of signed in or guest user.
        purchaseParameters.setBillingTotal(StringUtils.isEmpty(
            purchaseParameters.getPromoCode()) ?
                mobileBillingPg.book(isSignedInUser) :
                mobileBillingPg.book(isSignedInUser, purchaseParameters.getPromoCode()));
    }

    @Override
    public void continueToBillingFromDetailsBySelectingPrice() {
        MobileHotelDetailsPage mHotelDetailsPage = new MobileHotelDetailsPage(getWebdriverInstance());
        mHotelDetailsPage.priceSelect();
    }

    @Override
    public void landonBillingPageToPurchase() {
        new MobileBillingPg(getWebdriverInstance(), "tile.billing.signIn");
    }

    @Override
    public void attemptBillingInformationCompletion(boolean isSignedInUser) {
        fillTravelerInfo(purchaseParameters.getUserInformation(), isSignedInUser);
        fillBillingInformation(purchaseParameters.getUserInformation(), isSignedInUser);
        //   if (isSignedInUser) {
        // If you are a hotel signed in user, you get the one page billing
        // experience. Here you need to call the mHotelBillingPage.book() method so
        // that you can submit the form to trigger the errors Not needed for
        // guests as they have a vt of one page vs multi-page for now.
        MobileBillingPg mobileBillingPg = new MobileBillingPg(getWebdriverInstance());
        mobileBillingPg.book(isSignedInUser);
        //  }
    }

    @Override
    public void attemptToSignIn(AuthenticationParameters parameters, boolean signIn) {
        MobileBillingSignInPage mSignInPage = new MobileBillingSignInPage(getWebdriverInstance());
        if (signIn) {
            mSignInPage.continueAsUser(parameters.getUsername(), parameters.getPassword());
        }
        else {
            mSignInPage.continueAsGuest();
        }
    }

    @Override
    public void validateBillingErrors(String bookingProfile, boolean isSignedInUser) {

        // Set up the tile id and errors that are specific to registered/guest
        // users
        String viewId = isSignedInUser ?
            ViewIds.TILE_HOTEL_BILLING_ONE_PAGE_CHECKOUT : ViewIds.TILE_BILLING_PAYMENT_INFO;

        // Create the page and check for the presence of the errors
        MobileBillingPg mHotelBillingPage = new MobileBillingPg(getWebdriverInstance(), viewId);

        List<String> errors = new ArrayList<String>();
        // Set up the common errors to both registered and guest users
        if ("blank".equals(bookingProfile)) {
            if (!isSignedInUser) {
                errors.add("Select a card type.");
            }
            errors.add("Credit card expiration date is incorrect.");
            errors.add("Enter the cardholder's first name.");
            errors.add("Enter the cardholder's last name.");
        }
        else if ("special characters".equals(bookingProfile)) {
            errors.add("characters. First name and last name must be at least 2 characters and maximum of 32 " +
                "First name, middle initial, or last name fields cannot contain numbers or special characters.");
        }
        else if ("obsolete".equals(bookingProfile)) {
            errors.add("Credit card expiration date is incorrect.");
        }
        else if ("numbers in name".equals(bookingProfile)) {
            errors.add("characters. First name and last name must be at least 2 characters and maximum of 32 " +
                "First name, middle initial, or last name fields cannot contain numbers or special characters.");
        }
        else {
            throw new IllegalArgumentException();
        }

        errors.add("Enter a valid security code. This is the 3 or 4-digit number on the back of your card.");
        errors.add("This credit card number is invalid. Please make sure that your entry matches the number " +
            "on your credit card. Then try again.");
        errors.add("Enter a valid billing address.");
        errors.add("Enter a valid city name.");
        errors.add("Enter a valid postal code.");

        for (String error : errors) {
            mHotelBillingPage.assertHasFormErrors(error);
        }
    }

    @Override
    public void validateTravelerErrors(String bookingProfile) {
        MobileBillingPg mHotelBillingPage = new MobileBillingPg(getWebdriverInstance(),
            ViewIds.TILE_BILLING_HOTEL_TRAVELER_INFO);
        List<String> errors = new ArrayList<String>();

        if ("blank".equals(bookingProfile)) {
            errors.add("Enter a phone number.");

            errors.add("Please enter guest first name.");
            errors.add("Please enter guest last name.");
        }
        else if ("special characters".equals(bookingProfile)) {
            errors.add("Enter a valid number for country code. Do not include the \"+\" sign or leading zeroes.");
        }
        else if ("invalid phone number".equals(bookingProfile)) {
            errors.add("Enter a valid number for country code. Do not include the \"+\" sign or leading zeroes.");
        }
        else if ("blank country".equals(bookingProfile)) {

            errors.add("Enter a phone number.");
        }
        else if ("digital name".equals(bookingProfile)) {
            errors.add("First name, middle initial, or last name fields cannot contain numbers or special " +
                "characters. First name and last name must be at least 2 characters and maximum of 32 characters.");
        }
        else if ("too long name".equals(bookingProfile)) {
            errors.add("The name you entered is too long. The first name, middle name, and last name must each " +
                "be less than 32 characters.");
        }
        else {
            throw new IllegalArgumentException();
        }

        errors.add("Enter a valid email address.");
        errors.add("First name, middle initial, or last name fields cannot contain numbers or special characters. " +
            "First name and last name must be at least 2 characters and maximum of 32 characters.");

        for (String error : errors) {
            mHotelBillingPage.assertHasFormErrors(error);
        }
    }

    @Override
    public void receiveConfirmation() {
        MobileHotelConfirmationPage page = new MobileHotelConfirmationPage(getWebdriverInstance());
        assertThat(StringUtils.isEmpty(page.getConfirmationNumber()))
            .as("Expected confirmation number but was null/empty.")
            .isFalse();
        assertThat(StringUtils.isEmpty(page.getHotwireItinerary()))
            .as("Expected itinerary number but was null/empty.")
            .isFalse();
        LOGGER.info("Hotel Confirmation Number: " + page.getConfirmationNumber());
        LOGGER.info("Hotwire Itinerary: " + page.getHotwireItinerary());
        // Hack. The steps don't account for going to the home page to get to
        // "My Account".
        // line!
        getWebdriverInstance().findElement(By.id("home")).click();
    }

    @Override
    public void validateCredentialInfoErrors(String credentialProfile) {
        MobileBillingSignInPage signInPage = new MobileBillingSignInPage(getWebdriverInstance());
        if ("blank".equalsIgnoreCase(credentialProfile)) {
            signInPage.assertHasFormErrors("Enter a valid email address.");
            signInPage
                .assertHasFormErrors("Password must contain 6-30 characters and no spaces. It is case-sensitive.");
        }
        else if ("invalid".equalsIgnoreCase(credentialProfile)) {
            signInPage.assertHasFormErrors("The email address or password is incorrect.");
        }
        else if ("special characters".equalsIgnoreCase(credentialProfile)) {
            signInPage.assertHasFormErrors("Enter a valid email address.");
        }
        else {
            throw new IllegalArgumentException("Illegal argument found : " + credentialProfile);
        }
    }

    @Override
    public void verifyDiscountBannerOnHotelDetails() {
        MobileHotelDetailsPage mHotelDetailsPage = new MobileHotelDetailsPage(getWebdriverInstance());
        LOGGER.info(String.format("Discount banner avaliable on Hotel details page: %s",
            mHotelDetailsPage.verifyDiscountBannerOnResultsPage()));
        assertThat(mHotelDetailsPage.verifyDiscountBannerOnResultsPage())
            .as("Discount code banner avaliable or not on Details page")
            .isTrue();
    }

    @Override
    public void verifyDiscountBannerOnHotelItinerary(boolean isSignedInUser) {
        if (isSignedInUser) {
            MobileBillingPg mobileBillingPg = new MobileBillingPg(getWebdriverInstance(),
                "tile.hotel.billing.one-page-checkout");
            LOGGER.info(String.format("Discount banner avaliable on Registered Itinerary: %s",
                mobileBillingPg.verifyDiscountBannerOnBillingPage()));
            assertThat(mobileBillingPg.verifyDiscountBannerOnBillingPage())
                .as("Discount code banner avaliable or not on Details page")
                .isTrue();
        }
        else {
            MobileBillingPg mobileBillingPg = new MobileBillingPg(getWebdriverInstance(),
                "tile.hotel.billing.traveler-info");
            LOGGER.info(String.format("Discount banner avaliable on Guest Itinerary: %s",
                mobileBillingPg.verifyDiscountBannerOnBillingPage()));
            assertThat(mobileBillingPg.verifyDiscountBannerOnBillingPage())
                .as("Discount code banner avaliable or not on Details page")
                .isTrue();
        }
    }

    @Override
    public void chooseSolutionByName(String name) {
        throw new PendingException("Implement me!");
    }

    @Override
    public void chooseRetailResortResult() {
        throw new PendingException("Implement Me!");
    }

    @Override
    public void verifyResortFeesOnDetailsPage(boolean resortFeeVisible) {
        throw new PendingException("Implement Me!");
    }

    @Override
    public void verifyLastHotelBookedOnOpaqueDetailsPage() {
        throw new PendingException("Implement Me!");
    }

    @Override
    public void verifyDetailsPage(String detailsType) {
        // Right now there is only opaque purchase path. The only retail path is through retail as gateway which
        // puts the user on the full site retail details page. So we're ignoring detailsType.
        new MobileHotelDetailsPage(getWebdriverInstance());
    }

    @Override
    public void filteredByHoodName(String hoodName) {
        MobileHotelResultsPage mobileHotelResultsPage = new MobileHotelResultsPage(getWebdriverInstance());
        mobileHotelResultsPage.filteredByHoodName(hoodName);
    }

    @Override
    public void setADAAmenities() {
        MobileHotelDetailsPage mobileHotelDetailsPage = new MobileHotelDetailsPage(getWebdriverInstance());
        purchaseParameters.getListADAAmenities().
            addAll(Arrays.asList(mobileHotelDetailsPage.getADAAmenities().split(",")));
    }

    @Override
    public void checkHotelADAAmenityConsist() {
        MobileBillingPg billingPage = new MobileBillingPg(getWebdriverInstance());
        boolean isDisplayed = billingPage.isAccessibilityOptionsDisplayed();
        assertThat(isDisplayed)
            .as("Accessibility options on billing page were not displayed.")
            .isTrue();
        assertThat(billingPage.getAccessibilityArrowConsist())
            .as("Accessibility arrow consist is wrong")
            .contains("Down");
        List<String> texts = billingPage.getAccessibilityTexts();
        for (String text : purchaseParameters.getListADAAmenities()) {
            assertThat(texts.contains(text.trim()))
                .as("Next amenities from details page don't place in drop-down list on billig: " + text)
                .isTrue();
        }
        billingPage.collapseAccessibilityNeeds();
    }

    @Override
    public void chooseRetailHeroResult() {
        new MobileHotelResultsPage(getWebdriverInstance()).clickRetailHeroResult();
    }

    @Override
    public void goBackToResultsFromDetailsPage() {
        if (new PageName().apply(getWebdriverInstance()).contains(".details.retail")) {
            // We're on desktop app of retail details page.
            new HotelDetailsPage(getWebdriverInstance()).clickBackToResults();
        }
        else {
            // Assume we're on opaque mweb details page. Let any errors bubble up.
            new MobileHotelDetailsPage(getWebdriverInstance()).goBackToResults();
        }
    }

    @Override
    public void chooseHotelResultWhoseNameContains(String nameSubstring) {
        throw new PendingException("Implement me!");
    }

    @Override
    public void completeHotelBookingWithSavedUser() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillTravelerUserInfoForGuestUser() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectInsuranceAddonOption(boolean choice) {
        throw new PendingException("Insurance not implemented for Mobile.");
    }

    @Override
    public void verifyConfirmationPageCarCrossSell(boolean shouldBeDisplayed) {
        throw new PendingException("Implement Me!");
    }

    @Override
    public void verifyOnBillingPage() {
        new MobileBillingSignInPage(getWebdriverInstance());
    }

    @Override
    public void navigateBackToDetailsPage() {
        new MobileBillingSignInPage(getWebdriverInstance()).clickBackButton();
    }

    @Override
    public void filteredByStarRating(String rating) {
        new MobileHotelResultsPage(getWebdriverInstance()).filterByStarRating(rating);
    }

    @Override
    public void selectHotelResultByStarRating(String rating) {
        List<WebElement> elements = new MobileHotelResultsPage(getWebdriverInstance()).getClickableHrefList();
        for (WebElement element : elements) {
            String starRating = element.findElement(By.cssSelector("div.cost div.price img")).getAttribute("title");
            if (starRating.startsWith(rating)) {
                element.findElement(By.cssSelector(".price")).click();
                break;
            }
        }
    }
}




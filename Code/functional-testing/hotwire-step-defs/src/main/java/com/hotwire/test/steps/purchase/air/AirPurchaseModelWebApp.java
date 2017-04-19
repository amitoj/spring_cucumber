/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.air;

import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.billing.ActivitiesFragment;
import com.hotwire.selenium.desktop.us.billing.CreditCardInfoFragment;
import com.hotwire.selenium.desktop.us.billing.PaymentMethodFragment;
import com.hotwire.selenium.desktop.us.billing.PurchaseReviewFragment;
import com.hotwire.selenium.desktop.us.billing.air.AirBillingPage;
import com.hotwire.selenium.desktop.us.billing.air.AirCarDriverInfoFragment;
import com.hotwire.selenium.desktop.us.billing.air.AirCreditCardFragment;
import com.hotwire.selenium.desktop.us.billing.air.AirPassengerInfoFragment;
import com.hotwire.selenium.desktop.us.billing.air.AirTravelerInfoGuestFragment;
import com.hotwire.selenium.desktop.us.confirm.AirConfirmationPage;
import com.hotwire.selenium.desktop.us.details.AboutYourFlightPage;
import com.hotwire.selenium.desktop.us.details.AirDetailsPage;
import com.hotwire.selenium.desktop.us.results.AirResultsPage;
import com.hotwire.selenium.desktop.us.results.ExpediaAirResultsPage;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.search.air.PassengerData;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.WebDriverManager;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.functions.Wait;

import cucumber.api.PendingException;

//import org.openqa.selenium.support.ui.Select;

/**
 * @author v-abudyak
 * @since 2012.05
 */
public class AirPurchaseModelWebApp extends AirPurchaseModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirPurchaseModelWebApp.class);

    private String retailPrice;
    private String totalPrice;
    private String opaquePrice;
    private String topPrice;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "canadianZipCode")
    private String canadianZipCode;

    @Override
    public void selectPGood() {
        selectPGood(true /* Kill popup browser windows if present */);
    }

    @Override
    public void selectPGood(boolean killPopup) {
        select(killPopup, 3);
    }

    private void select(boolean killPopup, int retries) {

        if (retries == 0) {
            // No price change or sold out error.
            throw new RuntimeException(
                    "Aborting book solution due to issue other than sold out or price change. " +
                            "Have devs look at this issue.");
        }

        LOGGER.info("Try to select an air result.. {} retries left", retries);

        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());

        if ("opaque".equals(purchaseParameters.getOpaqueRetail()) && !airResultsPage.chooseOpaque()) {
            throw new ZeroResultsTestException("Opaque results are not available to proceed further!");
        }

        airResultsPage.select(purchaseParameters.getResultNumberToSelect(), purchaseParameters.getOpaqueRetail());

        if (killPopup) {
            WebDriverManager.closeAllPopups.apply(getWebdriverInstance());
        }
        // Retry mechanism.
        String pageName = new PageName().apply(getWebdriverInstance());

        if (pageName.contains("results")) {
            LOGGER.info("Landed back on air results after choosing solution.");

            List<WebElement> priceChange = getWebdriverInstance().findElements(By.cssSelector(".PriceChangeComp div"));

            if (priceChange.size() > 0) {
                LOGGER.info("Reason for landing back on air results: {} Retrying...", priceChange.get(0).getText());
                select(killPopup, retries - 1);
            }
            else {
                // It will fail a test..
                select(killPopup, 0);
            }
        }
    }

    @Override
    public void continueToBillingFromDetails() {
        new AirDetailsPage(getWebdriverInstance()).select();
    }

    @Override
    public void addRentalCar() {
        new AirDetailsPage(getWebdriverInstance()).addRentalCar();
    }

    @Override
    public void setPassengerName(Integer number, String firstName, String middleName, String lastName) {
        List<PassengerData> passengerDataList = purchaseParameters.getPassengerList();
        PassengerData currentPassengerData = passengerDataList.get(number - 1);

        currentPassengerData.setFirstName(firstName);
        currentPassengerData.setMiddleName(middleName);
        currentPassengerData.setLastName(lastName);

        passengerDataList.set(number - 1, currentPassengerData);
        purchaseParameters.setPassengerList(passengerDataList);
    }

    @Override
    public void setRandomPassengerNameBirthdayGender(Integer number) {
        Random random = new Random();
        List<PassengerData> passengerDataList = purchaseParameters.getPassengerList();
        PassengerData currentPassengerData = passengerDataList.get(number - 1);

        currentPassengerData.setFirstName("Test" + RandomStringUtils.randomAlphabetic(5));
        currentPassengerData.setMiddleName("");
        currentPassengerData.setLastName("Hotwire" + RandomStringUtils.randomAlphabetic(5));

        currentPassengerData.setMonthBirthdayDate(String.format("%02d", random.nextInt(11) + 1));
        currentPassengerData.setDayBirthdayDate(String.format("%02d", random.nextInt(28) + 1));
        currentPassengerData.setYearBirthdayDate("" + (random.nextInt(30) + 1960));

        if (random.nextBoolean()) {
            currentPassengerData.setGender("male");
        }
        else {
            currentPassengerData.setGender("female");
        }

        currentPassengerData.setfFProgramName(null);
        currentPassengerData.setfFNumber(null);
        currentPassengerData.setRedressNumber(null);

        passengerDataList.set(number - 1, currentPassengerData);
        purchaseParameters.setPassengerList(passengerDataList);
    }


    @Override
    public void fillTravelerAndPassengerInfo(boolean guestOrUser) {
        // Handler for booking review page... like if price changes or somethng.
        if (new PageName().apply(getWebdriverInstance()).contains("air.review-fare")) {
            getWebdriverInstance().findElement(By.cssSelector("button[id='airDetailsRetailBookNow']")).click();
        }
        AirBillingPage airBillingPage = new AirBillingPage(getWebdriverInstance());
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        if (guestOrUser) {
            if (!globalHeader.isLoggedIn()) {
                airBillingPage.selectAsUser(authenticationParameters.getUsername(),
                        authenticationParameters.getPassword());
            }
            else {
                LOGGER.info("User is signed already!");
            }
        }
        else {
            airBillingPage.selectAsGuest();
        }

        AirPassengerInfoFragment airPassengerInfoFragment = new AirPassengerInfoFragment(getWebdriverInstance());
        List<PassengerData> passengerList = purchaseParameters.getPassengerList();
        for (int i = 0; i < passengerList.size(); i++) {
            PassengerData passengerData = passengerList.get(i);
            airPassengerInfoFragment
                    .fillPassengerName(i, passengerData.getFirstName(), passengerData.getMiddleName(),
                            passengerData.getLastName());

            airPassengerInfoFragment = new AirPassengerInfoFragment(getWebdriverInstance());
            airPassengerInfoFragment
                    .fillPassengerBirthdayDate(i, passengerData.getMonthBirthdayDate(),
                            passengerData.getDayBirthdayDate(), passengerData.getYearBirthdayDate())
                    .fillPassengerGender(i, passengerData.getGender());
        }

        UserInformation userInformation = purchaseParameters.getUserInformation();

        if (!passengerList.isEmpty()) {
            PassengerData passengerData = passengerList.get(0);
            new AirCarDriverInfoFragment(getWebdriverInstance()).fillDriverInformation(
                passengerData.getFirstName(),
                passengerData.getLastName(),
                passengerData.getMiddleName()
            );
        }

        airBillingPage.setAge("25+")
                .setPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber());
        if (!guestOrUser) {
            if (!globalHeader.isLoggedIn()) {
                airBillingPage.setEmailId(userInformation.getEmailId());
            }
            else {
                LOGGER.info("User is signed already!");
            }
        }
        airBillingPage.submit();
    }

    @Override
    public void selectInsuranceAddonOption(boolean choice) {
        AirBillingPage airBillingPage = new AirBillingPage(getWebdriverInstance());
        airBillingPage.fillAdditionalFeatures().withInsurance(purchaseParameters.getOptForInsurance())
                .continuePanel();
    }

    @Override
    public void attemptBillingInformationCompletion(boolean isSignedInUser) {
        completePurchase(isSignedInUser);
    }

    @Override
    public void continueOnBillingPageOnPaymentFragment() {
        new Wait<>(new IsAjaxDone()).maxWait(2).apply(getWebdriverInstance());
        PaymentMethodFragment paymentMethodFragment = new PaymentMethodFragment(getWebdriverInstance());
        paymentMethodFragment.clickContinueButton();
    }

    @Override
    public void verifyRedFieldsOnAirBillingPage() {
        CreditCardInfoFragment creditCardInfoFragment = new CreditCardInfoFragment(getWebdriverInstance());
        assertThat(creditCardInfoFragment.colorPaymentCreditCardNumber())
                .as("CC number field is not Red")
                .isEqualToIgnoringCase("rgba(255, 0, 0, 1)");
        assertThat(creditCardInfoFragment.colorPaymentExpMonth())
                .as("CC exp month field is not Red")
                .isEqualToIgnoringCase("rgba(255, 0, 0, 1)");
        assertThat(creditCardInfoFragment.colorPaymentExpYear())
                .as("CC exp year field is not Red")
                .isEqualToIgnoringCase("rgba(255, 0, 0, 1)");
        assertThat(creditCardInfoFragment.colorPaymentCreditCardNumber())
                .as("CC number field is not Red")
                .isEqualToIgnoringCase("rgba(255, 0, 0, 1)");
        assertThat(creditCardInfoFragment.colorPaymentCreditCardNumber())
                .as("CC number field is not Red")
                .isEqualToIgnoringCase("rgba(255, 0, 0, 1)");
        assertThat(creditCardInfoFragment.colorPaymentCreditCardNumber())
                .as("CC number field is not Red")
                .isEqualToIgnoringCase("rgba(255, 0, 0, 1)");
    }

    private void fillDriverInformation(PassengerData passengerData) {

    }

    // It will be invoke traveler information in air billing page
    @Override
    public void selectTravalerInfo() {
        new AirBillingPage(getWebdriverInstance()).selectAsGuest();
    }

    @Override
    public void verifySeatPreference() {
        AirTravelerInfoGuestFragment travlerInfo = new AirTravelerInfoGuestFragment(getWebdriverInstance());
        assertThat(travlerInfo.verifySeatPreferenceExpandCollpase()).isEqualTo(true);
        assertThat(travlerInfo.verifySeatPreferenceText()).isEqualTo(true);
    }

    @Override
    public void completePurchase(Boolean guestOrUser) {
        fillTravelerAndPassengerInfo(guestOrUser);

        UserInformation userInformation = purchaseParameters.getUserInformation();
        String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
                userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode :
                userInformation.getZipCode();
        AirBillingPage airBillingPage = new AirBillingPage(getWebdriverInstance());
        airBillingPage.selectActivities();

        airBillingPage.fillAdditionalFeatures().withInsurance(purchaseParameters.getOptForInsurance())
            .continuePanel();

        airBillingPage.fillCreditCard()
            .withCcType(userInformation.getCcType())
            .withCcNumber(userInformation.getCcNumber())
            .withCcExpMonth(userInformation.getCcExpMonth())
            .withCcExpYear(userInformation.getCcExpYear())
            .withSecurityCode(userInformation.getSecurityCode())
            .withBillingAddress(userInformation.getBillingAddress())
            .withFirstName(userInformation.getFirstName())
            .withMiddleName(userInformation.getMiddleName())
            .withLastName(userInformation.getFirstName())
            .withCountry(userInformation.getBillingCountry())
            .withCity(userInformation.getCity())
            .withState(userInformation.getState())
            .withZipCode(zipCode)
            .clickContinueButton();
        new AirBillingPage(getWebdriverInstance()).book();
    }

    @Override
    public void fillCreditCard() {
        UserInformation userInformation = purchaseParameters.getUserInformation();

        String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
                userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode :
                userInformation.getZipCode();

        AirCreditCardFragment airCreditCardFragment = new AirCreditCardFragment(getWebdriverInstance());
        airCreditCardFragment.newOrSavedCCUsing(false);

        AirBillingPage airBillingPage = new AirBillingPage(getWebdriverInstance());
        airBillingPage.fillCreditCard()
                .withCcType(userInformation.getCcType())
                .withCcNumber(userInformation.getCcNumber())
                .withCcExpMonth(userInformation.getCcExpMonth())
                .withCcExpYear(userInformation.getCcExpYear())
                .withSecurityCode(userInformation.getSecurityCode())
                .withBillingAddress(userInformation.getBillingAddress())
                .withFirstName(userInformation.getCcFirstName())
                .withMiddleName(userInformation.getCcMiddleName())
                .withLastName(userInformation.getCcLastName())
                .withCountry(userInformation.getBillingCountry())
                .withCity(userInformation.getCity())
                .withState(userInformation.getState())
                .withZipCode(zipCode);
    }

    @Override
    public void saveBillingInformation(String cardName) {
        AirCreditCardFragment airCreditCardFragment = new AirCreditCardFragment(getWebdriverInstance());
        airCreditCardFragment.saveBillingInfo(cardName);
    }

    @Override
    public void continueToReviewBillingPage() {
        PaymentMethodFragment paymentMethodFragment = new PaymentMethodFragment(getWebdriverInstance());
        paymentMethodFragment.clickContinueButton();
    }

    @Override
    public void clickOnAgreeAndBookButton(Boolean agreeWithTerms) {
        PurchaseReviewFragment purchaseReviewFragment = new PurchaseReviewFragment(getWebdriverInstance());
        purchaseReviewFragment.setAgreeWithTerms(agreeWithTerms);
        purchaseReviewFragment.continuePanel();
    }

    @Override
    public void receiveConfirmation() {
        // Check notify page for inventory change.
        if (new PageName().apply(getWebdriverInstance()).contains("notify")) {
            // Work with air devs to add attribute to the message specific for inventory issues and get
            // rid of verification by String value.
            List<WebElement> elements = getWebdriverInstance().findElements(By.cssSelector(" .ml15 h2"));
            if (elements.size() > 0) {
                String msg = elements.get(0).getText();
                if (msg.contains("itinerary you selected is no longer available")) {
                    // App is working in terms of UI level. To determine if this actually has occurred from an inventory
                    // being sold out or whatever needs to be done in a unit test or integration test.
                    throw new PendingException(
                        "Issue completing booking purchase confirmation. Details:\n" + msg +
                        "\nIf this is consistently happening, notify car rental devs to investigate inventory issues.");
                }
            }
        }
        AirConfirmationPage page = new AirConfirmationPage(getWebdriverInstance());
        LOGGER.info("Air Confirmation Number: " + page.getConfirmationCode());
        LOGGER.info("Hotwire Itinerary: " + page.getHotwireItinerary());
    }

    @Override
    public void landOnAirResultsPage() {
        new AbstractDesktopPage(getWebdriverInstance()).waitForDoneFiltering();
        retailPrice = new AirResultsPage(getWebdriverInstance()).getRetailPrice();
        topPrice = new AirResultsPage(getWebdriverInstance()).getTopPrice();
    }

    @Override
    public void verifySearchParametersOnExpediaAirResultsPage() throws ParseException {
        ExpediaAirResultsPage results = new ExpediaAirResultsPage(getWebdriverInstance());
        assertThat(results.getResultsList()).as("Result list is empty").isNotEmpty();
        assertThat(results.getNumberOfTravelers()).as("Number of travelers in results page doesn't match w/ the " +
                "input at the air search").isEqualTo(this.purchaseParameters.getPassengerList().size());
        assertThat(results.getReturnLocation()).as("Return location doesn't match w/ the " +
                "input at the air search").containsIgnoringCase(this.searchParameters.getDestinationLocation());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        Date departureDate = sdf.parse(sdf.format(this.searchParameters.getStartDate()));
        Date returnDate = sdf.parse(sdf.format(this.searchParameters.getEndDate()));
        assertThat(results.getDepartureDate()).as("Departure date on results page doesn't match")
                .isEqualTo(departureDate);
        assertThat(results.getReturnDate()).as("Return date on results page doesn't match").isEqualTo(returnDate);

    }


    @Override
    public void verifyActiviesGT() {
        assertThat(new ActivitiesFragment(getWebdriverInstance()).verifyActiviesGT()).isEqualTo(true);
    }

    @Override
    public void verifyGT() {
        assertThat(new ActivitiesFragment(getWebdriverInstance()).verifyGT()).isEqualTo(true);
    }

    @Override
    public void compareTopPriceWithRetailPriceOpaqueMasked() {
        LOGGER.info("Top Price: " + topPrice + " Retail Price: " + retailPrice);
        assertThat(topPrice.contains(retailPrice)).isTrue();
    }

    @Override
    public void compareTopPriceWithOpaquePriceOpaqueUnMasked() {
        String actualOpaquePrice = new AirResultsPage(getWebdriverInstance()).getOpaquePrice();
        System.out.println("Top Price: " + topPrice + " Opaque Price: " + actualOpaquePrice);
        LOGGER.info("Top Price: " + topPrice + " Opaque Price: " + actualOpaquePrice);
        assertThat(topPrice.contains(actualOpaquePrice)).isTrue();
    }

    @Override
    public void compareMaskedOpaqueWithRetailPrice() {
        opaquePrice = new AirDetailsPage(getWebdriverInstance()).getOpaquePrice();
        priceComparision(opaquePrice, retailPrice);
    }

    @Override
    public void compareUnmaskedOpaqueWithRetailPrice() {
        String actualOpaquePrice = new AirResultsPage(getWebdriverInstance()).getOpaquePrice();
        priceComparision(actualOpaquePrice, retailPrice);
    }

    private void priceComparision(String opaquePrice, String retailPrice) {
        LOGGER.info("Opaque Price: " + opaquePrice + " Retail Price: " + retailPrice);
        assertThat(Integer.parseInt(opaquePrice) < Integer.parseInt(retailPrice)).isTrue();
    }

    @Override
    public void verifyOpaqueNotificationPopup() {
        ArrayList<String> handles = new ArrayList<String>(getWebdriverInstance().getWindowHandles());
        boolean popupFound = false;
        for (String handle : handles) {
            getWebdriverInstance().switchTo().window(handle);
            String pageName = new PageName().apply(getWebdriverInstance());
            if (pageName.contains("opaque.popup")) {
                popupFound = true;
                break;
            }
        }
        try {
            assertThat(popupFound)
                .as("Expected opaque notification popup to be true, but instead got " + popupFound)
                .isTrue();
        }
        finally {
            WebDriverManager.closeAllPopups.apply(getWebdriverInstance());
        }
    }

    @Override
    public void opaqueQuoteInResults() {
        if (!new AirResultsPage(getWebdriverInstance()).chooseOpaque()) {
            throw new PendingException("No opaque quote in Air search results.");
        }
        LOGGER.info("Opaque quote(s) found in Air search results.");
    }

    @Override
    public void getFlightPriceFromDetails() {
        opaquePrice = new AirDetailsPage(getWebdriverInstance()).getOpaquePrice();
        totalPrice = new AirDetailsPage(getWebdriverInstance()).gettotalPrice();
    }

    @Override
    public void compareResultsAndDetailsPrice(boolean match) {
        LOGGER.info(" Unit Price: " + opaquePrice + "Total Price: " + totalPrice);
        assertThat(Integer.parseInt(opaquePrice) < Integer.parseInt(totalPrice)).as(
            "For multiple passanger both Unit and total price are not equal").isTrue();
    }

    @Override
    public void addRentalCarViaPackage() {
        new AirDetailsPage(getWebdriverInstance()).addRentalCarViaPackage();
    }

    @Override
    public void verifyPage(String page) {
        switch (page) {
            case "result":
                throw new RuntimeException("Implement me!");
            case "details":
                throw new RuntimeException("Implement me!");
            case "billing":
                throw new RuntimeException("Implement me!");
            default:
                return;
        }

    }

    @Override
    public void verifyConfirmationPageCarCrossSell(boolean shouldBeDisplayed) {
        boolean actual = new AirConfirmationPage(getWebdriverInstance()).isCarCrossSellDisplayed();
        assertThat(actual)
            .as("Expected car cross sell displayed to be " + shouldBeDisplayed + " but was instead " + actual)
            .isEqualTo(shouldBeDisplayed);
    }

    @Override
    public void clickConfirmationPageCarCrossSell() {
        new AirConfirmationPage(getWebdriverInstance()).clickCarCrossSell();
    }

    @Override
    public void clickOnBookNow() {
        AboutYourFlightPage details = new AboutYourFlightPage(getWebdriverInstance());
        details.getBookNowButton().click();
    }

    @Override
    public void fillTravelerInformation() {
        AirBillingPage airBillingPage = new AirBillingPage(getWebdriverInstance());
        airBillingPage.submit();
    }

    @Override
    public void payWithSavedCCard() {
        try {
            Thread.sleep(5000);
            WebElement savedCCRadio = new AirCreditCardFragment(getWebdriverInstance()).getSavedCCRadio();
            savedCCRadio.click();
            getWebdriverInstance().findElement(By.cssSelector("input[name='paymentForm._NAE_cpvNumber']")).clear();
            getWebdriverInstance().findElement(By.cssSelector("input[name='paymentForm._NAE_cpvNumber']"))
                .sendKeys("999");
            getWebdriverInstance().findElement(By.name("btnPaymentInfo")).click();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void validateProtectionDetailsOnDb(String insuranceCost) {
        AirConfirmationPage confirmation = new AirConfirmationPage(getWebdriverInstance());
        String myItinerary = confirmation.getHotwireItinerary();

        System.out.println("My Itinerary " + myItinerary);
        System.out.println("Insurance " + insuranceCost);

        String sql = "select po.display_number, oao.purchase_order_id, r.reservation_id, " +
            "p.tp_insurance_policy_number, r.reservation_num, oao.type_code, oao.price_amount " +
            "from participant p, reservation r, order_add_on oao, purchase_order po " +
            "where r.reservation_id = p.reservation_id " +
            "and po.purchase_order_id = oao.reservation_id " +
            "and r.reservation_id = oao.reservation_id " +
            "and po.display_number = " + myItinerary;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : rows) {
            System.out.println("PRICE_AMOUNT : " + row.get("PRICE_AMOUNT"));
            assertThat(((BigDecimal) row.get("PRICE_AMOUNT")).floatValue() ==
                Float.parseFloat(insuranceCost)).as("Total amount into DB: " +
                    ((BigDecimal) row.get("PRICE_AMOUNT")).toString() +
                        " equals total amount on confirmation page :" + insuranceCost).isTrue();
            String policy = row.get("TP_INSURANCE_POLICY_NUMBER").toString();
            String typeCode = row.get("TYPE_CODE").toString();
            assertThat(!typeCode.isEmpty()).as("Empty type code").isTrue();
            assertThat(!policy.isEmpty()).as("Empty policy number").isTrue();
        }
    }

    @Override
    public String getFlightInsuranceCost() {
        PurchaseReviewFragment purchaseReviewFragment = new PurchaseReviewFragment(getWebdriverInstance());
        String insuranceCost = null;
        try {
            Thread.sleep(3000);
            insuranceCost = (purchaseReviewFragment.getInsurance().getText()).substring(1);
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        return insuranceCost;
    }

    @Override
    public String getInsuranceCheckText() {
        PurchaseReviewFragment purchaseReviewFragment = new PurchaseReviewFragment(getWebdriverInstance());
        String insuranceChecked = null;
        try {
            Thread.sleep(3000);
            insuranceChecked = purchaseReviewFragment.getInsuranceChecked().getText();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        return insuranceChecked;
    }
}




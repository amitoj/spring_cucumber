/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.hotwire.selenium.desktop.us.billing.onepage.HotelCreditCardFragment;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelBillingOnePage;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelTravelerInfoFragment;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelPayPalFragment;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelVmeFragment;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelTripInsuranceFragment;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fest.assertions.Delta;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.hotwire.qa.client.db.Db;
import com.hotwire.qa.client.db.ResRow;
import com.hotwire.qa.client.db.Selected;
import com.hotwire.qa.data.UEnum;
import com.hotwire.qa.systems.HwRunEnv;
import com.hotwire.selenium.desktop.common.billing.HotelBillingPage;
import com.hotwire.selenium.desktop.common.billing.HotelTripSummaryFragment;
import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.us.billing.ActivitiesFragment;
import com.hotwire.selenium.desktop.us.confirm.HotelConfirmationPage;
import com.hotwire.selenium.desktop.us.confirm.HotelNewConfirmationPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters.PaymentMethodType;
import com.hotwire.test.steps.purchase.PurchaseParametersImpl;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.functions.PageName;

import cucumber.api.PendingException;

/**
 * This class provides methods to execute a purchase a hotel.
 * @author Alok Gupta
 * @since 2012.04
 */
public class HotelPurchaseModelWebApp extends HotelPurchaseModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelPurchaseModelWebApp.class.getSimpleName());

    @Autowired
    @Qualifier("hotelSearchParameters")
    private HotelSearchParameters hotelSearchParameters;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParametersImpl purchaseParameters;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String runEnv_name;

    public void setRunEnv_name(String runEnv_name) {
        this.runEnv_name = runEnv_name;
    }

    public String getRunEnv_name() {
        return runEnv_name;
    }

    private HwRunEnv getEnv() {
        HwRunEnv runEnv = UEnum.match(HwRunEnv.class, getRunEnv_name(), false);
        return runEnv;
    }

    @Override
    public void attemptBillingInformationCompletion(boolean isSignedInUser) {
        completePurchase(isSignedInUser);
    }

    // This method will be invoked to fill the travel information in hotel
    // billing page and click on activities & services with out doing complete
    // purchase.
    @Override
    public void fillTravelInfoForHotel() {
        HotelBillingOnePage hotelBillingOnePage = new HotelBillingOnePage(getWebdriverInstance());
        hotelBillingOnePage.fillTravelerInfo().continuePanel();
    }

    @Override
    public void selectActivities() {
        ActivitiesFragment activites = new ActivitiesFragment(getWebdriverInstance());
        activites.selectActivities();
    }

    // It will verify the whether the ticket types are sorted in ascending order
    // or not
    @Override
    public void checkTicketTypesSortedOrNot() {
        ActivitiesFragment activites = new ActivitiesFragment(getWebdriverInstance());
        boolean checkTicketTypeOrder = true;
        List<String> ticketsTypeSortedList = activites.getListOfActivitiesTicketTypes();
        Collections.sort(ticketsTypeSortedList);
        List<String> ticketsTypeUnSortedList = activites.getListOfActivitiesTicketTypes();

        for (int i = 0; i < ticketsTypeSortedList.size(); i++) {
            if (!ticketsTypeSortedList.get(i).equals(ticketsTypeUnSortedList.get(i))) {
                checkTicketTypeOrder = false;
            }
        }
        assertThat(checkTicketTypeOrder).as("Ticket type order").isEqualTo(true);
    }

    @Override
    public void receiveConfirmation() {
        // Assuming that if we get back up hotel details page, the app is
        // working correctly and only inventory
        // issues will be present on this page.
        if (StringUtils.contains(new PageName().apply(getWebdriverInstance()), "backup.hotel.details")) {
            List<WebElement> elements = getWebdriverInstance().findElements(By.cssSelector(" .message"));
            ArrayList<String> msgs = new ArrayList<String>();
            for (WebElement element : elements) {
                msgs.add(element.getText());
            }
            throw new PendingException("Issue completing booking purchase confirmation. Details:\n" +
                StringUtils.join(msgs, " ") + "\nIf this is happening consistently, " +
                "notify Hotel Supply or Hotel Consumer teams to investigate inventory issues.");
        }
        String hotwireItinerary;
        String tripTotal;
        HotelNewConfirmationPage page = new HotelNewConfirmationPage(getWebdriverInstance());
        LOGGER.info("Hotel Confirmation Number: " + page.getConfirmationNumber());
        LOGGER.info("Hotwire Itinerary: " + page.getItineraryNumber());
        hotwireItinerary = page.getItineraryNumber();
        tripTotal = page.getTripTotal();

        // Verified we're on a confirmation page. Let's do some asserts.
        assertThat(StringUtils.isEmpty(hotwireItinerary)).as(
            "Expected Hotwire Itinerary, but got null or empty string.").isFalse();
        purchaseParameters.setItinerary(hotwireItinerary);
        assertThat(new Double(purchaseParameters.getBillingTotal().trim().replaceAll("[^0-9.]", ""))).as(
            "Expected trip total from billing to be the same on confirmation. Expected: " +
                purchaseParameters.getBillingTotal() + " got: " + tripTotal).isEqualTo(
                    new Double(tripTotal.trim().replaceAll("[^0-9.]", "")), Delta.delta(0.02));
    }

    @Override
    public void attemptToSignIn(String email, String password) {
        new HotelBillingOnePage(getWebdriverInstance()).selectAsUser(email, password);
    }

    @Override
    public void clickOnAgreeAndBookButton(Boolean agreeWithTerms) {
        new HotelBillingOnePage(getWebdriverInstance()).book();
    }

    @Override
    public void checkMoreDetailsLinks() {
        ActivitiesFragment activities = new ActivitiesFragment(getWebdriverInstance());
        assertThat(activities.verifyMoreDetailsLinks()).as(
            "More details links are not displayed and/or ds pop-up is not invoked").isTrue();
    }

    /**
     * validates summary of charges on old confirmation page
     */
    @Override
    public void verifySummaryOfChargesChargesOnOpaqueHotelBillingPage() {
        HotelConfirmationPage confPage = new HotelConfirmationPage(getWebdriverInstance());
        String ratePerNightText = confPage.getRatePerNight();
        assertThat(ratePerNightText.indexOf(".00") > 0).as("Rate per Night is round").isTrue();
        int taxesAndFees = Integer.parseInt(confPage.getTaxesAndFees().replaceAll("[^0-9]", ""));
        assertThat(taxesAndFees).as("Taxes and fees").isGreaterThan(0);
        int ratePerNight = Integer.parseInt(ratePerNightText.replaceAll("[^0-9]", ""));
        int nights = confPage.getNights();
        int rooms = confPage.getRooms();
        int tripTotal = Integer.parseInt(confPage.getTripTotal().replaceAll("[^0-9]", ""));
        LOGGER.info("Rate Per Night: " + ratePerNight);
        LOGGER.info("Number of Rooms: " + rooms);
        LOGGER.info("Number of  Nights: " + nights);
        LOGGER.info("Extected Trip Total: " + tripTotal);
        LOGGER.info("Actual trip Total:" + (ratePerNight * nights * rooms + taxesAndFees));
        assertThat(tripTotal).as("Trip Total (rate_per_night * nights * rooms + taxes_and_fees)").isEqualTo(
            ratePerNight * nights * rooms + taxesAndFees);
    }

    /**
     * validates values on new confirmation page
     */
    @Override
    public void verifySummaryOfChargesOnNewBillingPage() {
        HotelNewConfirmationPage confPage = new HotelNewConfirmationPage(getWebdriverInstance());
        LOGGER.info("Hotel Confirmation Number: " + confPage.getConfirmationNumber());
        LOGGER.info("Hotwire Itinerary: " + confPage.getItineraryNumber());
        String ratePerNightText = confPage.getRatePerNight();
        // assertThat(ratePerNightText.indexOf(".00") >
        // 0).as("Rate per Night is round").isTrue();
        int taxesAndFees;
        if (!(confPage.getTaxesAndFees() == null)) {
            taxesAndFees = Integer.parseInt(confPage.getTaxesAndFees().replaceAll("[^0-9]", ""));
            assertThat(taxesAndFees).as("Taxes and fees").isGreaterThan(0);
        }
        else {
            taxesAndFees = 0;
        }

        int travelInsurance;
        if (!(confPage.getTravelInsurance() == null)) {
            travelInsurance = Integer.parseInt(confPage.getTravelInsurance());
        }
        else {
            travelInsurance = 0;
        }
        int ratePerNight = Integer.parseInt(ratePerNightText.replaceAll("[^0-9]", ""));
        int nights = confPage.getNights();
        int rooms = confPage.getRooms();
        int tripTotal = Integer.parseInt(confPage.getTripTotal().replaceAll("[^0-9]", ""));
        LOGGER.info("Rate Per Night: " + ratePerNight);
        LOGGER.info("Number of Rooms: " + rooms);
        LOGGER.info("Number of  Nights: " + nights);
        LOGGER.info("Travel Insurance: " + travelInsurance);
        LOGGER.info("Extected Trip Total: " + tripTotal);
        LOGGER.info("Actual trip Total:" + (ratePerNight * nights * rooms + taxesAndFees + travelInsurance));
        assertThat(tripTotal).as("Trip Total (rate_per_night * nights * rooms + taxes_and_fees)").isEqualTo(
            ratePerNight * nights * rooms + taxesAndFees + travelInsurance);
    }

    /**
     * validates that values are stored correctly in DB for retail solution
     */
    @Override
    public void verifyRetailChargesAreStoredInDBCorrectly() {
        Db db;

        db = getEnv().dbCfg.getDb("rpt_user", "rpt_user");

        HotelNewConfirmationPage confPage = new HotelNewConfirmationPage(getWebdriverInstance());

        String getCostsQuery = "select s.crs_base_cost, s.crs_total_tax, s.crs_total_cost, s.crs_extra_person_fee, " +
            "s.crs_compensation, s.local_base_amount, s.local_total_tax_amount, s.total_taxes_fees, " +
            "p.BASE_COST_AMOUNT, p.LOCAL_BASE_AMOUNT, p.LOCAL_TOTAL_TAX_AMOUNT, p.pgood_id, m.amount " +
            "from sold_hotel_room s " + "inner join search_pgood p on s.pgood_id=p.pgood_id " +
            "inner join price_modifier m on s.pgood_id=m.sold_hotel_room_id " + "where " +
            "p.search_solution_id in (select search_solution_id from search_solution where display_number like '" +
            confPage.getItineraryNumber() + "')";

        Selected costsQueryResult = db.select(getCostsQuery);
        ResRow[] costsRows = costsQueryResult.getRes(1);

        int nights = confPage.getNights();
        int rooms = confPage.getRooms();
        double taxesAndFees = Double.parseDouble(confPage.getTaxesAndFees().replaceAll("[^0-9\\.]", ""));
        double ratePerNight = Double.parseDouble(confPage.getRatePerNight().replaceAll("[^0-9\\.]", ""));

        BigDecimal ratePerNightBigDecimal = new BigDecimal(ratePerNight).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxesAndFeesBigDecimal = new BigDecimal(taxesAndFees).setScale(2, RoundingMode.HALF_UP);

        double crs_base_cost = Double.parseDouble(costsRows[0].get("CRS_BASE_COST").toString());
        double crs_total_tax = Double.parseDouble(costsRows[0].get("CRS_TOTAL_TAX").toString());
        double crs_extra_person_fee = Double.parseDouble((costsRows[0].get("CRS_EXTRA_PERSON_FEE") == null) ? "0" :
            costsRows[0].get("CRS_EXTRA_PERSON_FEE").toString());

        double total_taxes = crs_extra_person_fee * nights * rooms + crs_total_tax;

        BigDecimal crsBaseCostBigDecimal = new BigDecimal(crs_base_cost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal crsTotalTaxBigDecimal = new BigDecimal(total_taxes).setScale(2, RoundingMode.HALF_UP);

        assertThat(ratePerNightBigDecimal).as("Rate per night").isEqualTo(crsBaseCostBigDecimal);
        assertThat(taxesAndFeesBigDecimal).as("Taxes and fees").isEqualTo(crsTotalTaxBigDecimal);
    }

    /**
     * validates that values are stored correctly in DB for opaque solution
     */
    @Override
    public void verifyOpaqueChargesAreStoredInDBCorrectly() {
        Db db;
        db = getEnv().dbCfg.getDb("rpt_user", "rpt_user");

        HotelNewConfirmationPage confPage = new HotelNewConfirmationPage(getWebdriverInstance());

        String getCostsQuery = "select s.crs_base_cost, s.crs_total_tax, s.crs_total_cost, s.crs_extra_person_fee, " +
            "s.crs_compensation, s.local_base_amount, s.local_total_tax_amount, s.total_taxes_fees, " +
            "p.BASE_COST_AMOUNT, p.LOCAL_BASE_AMOUNT, p.LOCAL_TOTAL_TAX_AMOUNT, p.pgood_id, m.amount " +
            "from sold_hotel_room s " + "inner join search_pgood p on s.pgood_id=p.pgood_id " +
            "inner join price_modifier m on s.pgood_id=m.sold_hotel_room_id " + "where " +
            "p.search_solution_id in (select search_solution_id from search_solution where display_number like '" +
            confPage.getItineraryNumber() + "')";

        Selected costsQueryResult = db.select(getCostsQuery);
        ResRow[] costsRows = costsQueryResult.getRes(1);

        String pGoodId = costsRows[0].get("PGOOD_ID").toString();

        String getAmountQuery = "select * from price_modifier where sold_hotel_room_id =" + pGoodId +
            " order by type_code ASC";

        Selected amountQueryResult = db.select(getAmountQuery);
        ResRow[] amountRows = amountQueryResult.getRes(5);
        double amount10011 = Double.parseDouble(amountRows[1].get("AMOUNT").toString());
        double amount10012 = Double.parseDouble(amountRows[2].get("AMOUNT").toString());
        double amount10015 = Double.parseDouble(amountRows[3].get("AMOUNT").toString());
        double amount80231 = Double.parseDouble(amountRows[4].get("AMOUNT").toString());

        double taxesAndFees = Double.parseDouble(confPage.getTaxesAndFees().replaceAll("[^0-9\\.]", ""));
        double ratePerNight = Double.parseDouble(confPage.getRatePerNight().replaceAll("[^0-9\\.]", ""));
        int nights = confPage.getNights();
        int rooms = confPage.getRooms();
        double tripTotal = Double.parseDouble(confPage.getTripTotal().replaceAll("[^0-9\\.]", ""));

        double crs_base_cost = Double.parseDouble(costsRows[0].get("CRS_BASE_COST").toString());
        double crs_extra_person_fee = Double.parseDouble((costsRows[0].get("CRS_EXTRA_PERSON_FEE") == null) ? "0" :
            costsRows[0].get("CRS_EXTRA_PERSON_FEE").toString());
        double crs_total_tax = Double.parseDouble(costsRows[0].get("CRS_TOTAL_TAX").toString());
        double crs_total_cost = crs_base_cost + crs_extra_person_fee + (crs_total_tax / (nights * rooms));
        double crs_compensation = Double.parseDouble(costsRows[0].get("CRS_COMPENSATION").toString());
        double local_total_tax_amount_from_DB = Double.parseDouble(costsRows[0].get("LOCAL_TOTAL_TAX_AMOUNT")
            .toString());
        double local_base_amount_from_DB = Double.parseDouble(costsRows[0].get("LOCAL_BASE_AMOUNT").toString());

        double local_total_tax_amount = ((crs_total_tax / (nights * rooms)) -
            (0.023 * (crs_base_cost + crs_extra_person_fee))) * nights * rooms;
        double price_modifier_80231 = crs_compensation + (0.015 * crs_total_cost);
        double local_base_amount = crs_total_cost -
            ((local_total_tax_amount / (nights * rooms)) + price_modifier_80231);
        BigDecimal localTotalTaxAmountBigDecimal = new BigDecimal(local_total_tax_amount).setScale(2,
            RoundingMode.HALF_UP);
        BigDecimal localTotalTaxAmountFromDBBigDecimal = new BigDecimal(local_total_tax_amount_from_DB).setScale(2,
            RoundingMode.HALF_UP);
        BigDecimal localBaseAmountBigDecimal = new BigDecimal(local_base_amount).setScale(2, RoundingMode.HALF_UP);
        BigDecimal localBaseAmountFromDBBigDecimal = new BigDecimal(local_base_amount_from_DB).setScale(2,
            RoundingMode.HALF_UP);
        BigDecimal priceModifier80231BigDecimal =
            new BigDecimal(price_modifier_80231).setScale(2, RoundingMode.HALF_UP);
        BigDecimal amount80231BigDecimal = new BigDecimal(amount80231).setScale(2, RoundingMode.HALF_UP);
        assertThat(localTotalTaxAmountBigDecimal).as("Total tax amount")
        .isEqualTo(localTotalTaxAmountFromDBBigDecimal);
        assertThat(localBaseAmountBigDecimal).as("Local base amount").isEqualTo(localBaseAmountFromDBBigDecimal);
        assertThat(priceModifier80231BigDecimal).as("CRS compensation").isEqualTo(amount80231BigDecimal);

        double rate_per_night_calculated = local_base_amount + price_modifier_80231 + amount10015;
        double taxes_and_fees_calculated = local_total_tax_amount + nights * rooms * (amount10011 + amount10012);
        double total_price_calculated = rate_per_night_calculated * nights * rooms + taxes_and_fees_calculated;

        BigDecimal ratePerNightBigDecimal =
            new BigDecimal(rate_per_night_calculated).setScale(2, RoundingMode.HALF_UP);
        BigDecimal ratePerNightUIBigDecimal =
            new BigDecimal(ratePerNight).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxesAndFeesBigDecimal =
            new BigDecimal(taxes_and_fees_calculated).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxesAndFeesUIBigDecimal =
            new BigDecimal(taxesAndFees).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalPriceBigDecimal =
            new BigDecimal(total_price_calculated).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalPriceUIBigDecimal =
            new BigDecimal(tripTotal).setScale(2, RoundingMode.HALF_UP);

        assertThat(ratePerNightUIBigDecimal).as("Rate per night").isEqualTo(ratePerNightBigDecimal);
        assertThat(taxesAndFeesUIBigDecimal).as("Taxes and fees").isEqualTo(taxesAndFeesBigDecimal);
        assertThat(totalPriceUIBigDecimal).as("Trip total").isEqualTo(totalPriceBigDecimal);
    }

    @Override
    public void fillTravelerInfo(UserInformation userInformation, boolean guestOrUser) {
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
        billingPage.setFirstName(userInformation.getFirstName()).setLastName(userInformation.getLastName())
        .setEmailAddress(userInformation.getEmailId()).setPhoneNumber(userInformation.getPrimaryPhoneNumber())
        .setPostalCode(userInformation.getZipCode());
    }

    @Override
    public void verifyValidationMarks(String fields, String marks) {
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
        String expectedClass = "field" + marks.toLowerCase();
        if ("traveler info".equals(fields)) {
            assertThat(billingPage.getFirstName().getAttribute("class").toLowerCase()).isEqualTo(expectedClass);
            assertThat(billingPage.getLastName().getAttribute("class").toLowerCase()).isEqualTo(expectedClass);
        }
        else if ("email".equals(fields)) {
            assertThat(billingPage.getEmailAddress().getAttribute("class").toLowerCase()).isEqualTo(expectedClass);
            assertThat(billingPage.getConfirmEmailAddress().getAttribute("class").toLowerCase()).isEqualTo(
                expectedClass);
        }
        else if ("phone number".equals(fields)) {
            assertThat(billingPage.getPhoneNumber().getAttribute("class").toLowerCase()).isEqualTo(expectedClass);
        }
        else {
            throw new UnimplementedTestException("Implement me!");
        }
    }

    @Override
    public void validateNewAgreeAndBookSection() {
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
        assertThat(billingPage.getAgreeAndBookFragment().getAttribute("class")).isEqualTo("agreeAndBook");
    }

    @Override
    public void validateSetOfCreditCardFields(String setOfFields) {
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());

        if ("full".equals(setOfFields)) {
            assertThat(billingPage.getBillingAddress1()).isNotNull();
            assertThat(billingPage.getBillingAddress2()).isNotNull();
            assertThat(billingPage.getCity()).isNotNull();
            assertThat(billingPage.getProvince()).isNotNull();
        }
        else if ("reduced".equals(setOfFields)) {
            assertThat(billingPage.getBillingAddress1()).isNull();
            assertThat(billingPage.getBillingAddress2()).isNull();
            assertThat(billingPage.getCity()).isNull();
            assertThat(billingPage.getProvince()).isNull();
        }
        else {
            throw new UnimplementedTestException("Implement me!");
        }
    }

    @Override
    public void validateSaveCreditCardPresence(String savedCreditCardPresence) {
        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());

        if ("is not".equals(savedCreditCardPresence)) {
            assertThat(billingPage.getSaveCreditCardModule()).isNull();
        }
        else if ("is".equals(savedCreditCardPresence)) {
            assertThat(billingPage.getSaveCreditCardModule()).isNotNull();
        }
        else {
            throw new UnimplementedTestException("Unknown parameter for validateSaveCreditCardPresence method");
        }
    }

    @Override
    public void verifyResortFeesOnBillingTripSummary(boolean resortFeeVisible) {
        new HotelBillingOnePage(getWebdriverInstance());
        boolean isDisplayed = new HotelTripSummaryFragment(getWebdriverInstance()).isResortFeesDetailsDisplayed();
        assertThat(isDisplayed).as(
            "Expected " + resortFeeVisible + " resort fees to be displayed but got " + isDisplayed).isEqualTo(
                resortFeeVisible);
    }

    @Override
    public void chooseAddNewTravelerFromSelection() {
        new HotelBillingOnePage(getWebdriverInstance());
        HotelTravelerInfoFragment travelerInfoFragment = new HotelTravelerInfoFragment(getWebdriverInstance());
        travelerInfoFragment.selectAddNewTravelerFromSelection();
    }

    @Override
    public void verifyEmailsPopulatedInTravelerInfoBillingPage() {
        HotelBillingOnePage billingPage = new HotelBillingOnePage(getWebdriverInstance());
        HotelTravelerInfoFragment fragment = billingPage.getTravelerInfoFragment();
        String email = fragment.getEmail();
        String confirmEmail = fragment.getConfirmationEmail();

        LOGGER.info("Checking authenticationParameters for Billing: " + authenticationParameters.getUsername().trim());
        assertThat(email.trim()).as(
            "Expected to see \"" + authenticationParameters.getUsername() + "\" but got \"" + email + "\"")
            .isEqualToIgnoringCase(authenticationParameters.getUsername().trim());

        assertThat(confirmEmail.trim()).as(
            "Expected to see \"" + authenticationParameters.getUsername() + "\" but got \"" + confirmEmail + "\"")
            .isEqualToIgnoringCase(authenticationParameters.getUsername().trim());

        // Check sign in module.
        fragment.clickSignInLink();
        String signInEmail = fragment.getEmailFromSignInLinkModule();
        assertThat(signInEmail.trim()).as(
            "Expected to see \"" + authenticationParameters.getUsername() + "\" but got \"" + confirmEmail + "\"")
            .isEqualToIgnoringCase(authenticationParameters.getUsername().trim());
    }

    private String getPPString() {
        StringBuilder pp = new StringBuilder("hotel|");
        pp.append(purchaseParameters.getCurrencyCode()).append("|");
        try {
            pp.append(searchParameters.getDestinationLocation().replace(", ", "%2C+")).append("|");
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchParameters.getStartDate());
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(searchParameters.getEndDate());
            int emonth = cal.get(Calendar.MONTH) + 1;
            int eday = cal.get(Calendar.DAY_OF_MONTH);
            pp.append(day).append("|");
            pp.append(eday).append("|");
            pp.append(month).append("|");
            pp.append(emonth).append("|");

            pp.append("2|0|1|uk");
        }
        catch (NullPointerException e) {
            pp.append("||||||||uk");
        }

        return pp.toString();
    }

    private String getTItString() {
        StringBuilder tp = new StringBuilder("");

        try {
            tp.append("&dcrc=");
            tp.append(searchParameters.getDestinationLocation().replace(", ", "%2C+"));
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchParameters.getStartDate());
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            cal.setTime(searchParameters.getEndDate());
            int emonth = cal.get(Calendar.MONTH) + 1;
            int eday = cal.get(Calendar.DAY_OF_MONTH);
            int eyear = cal.get(Calendar.YEAR);

            tp.append("&sdd=").append(day);
            tp.append("&sdm=").append(month);
            tp.append("&sdy=").append(year);
            tp.append("&edd=").append(eday);
            tp.append("&edm=").append(emonth);
            tp.append("&edy=").append(eyear);
            tp.append("&numa=2&numc=0&status=[Not%20Logged%20In]&rooms=1&sid=&bid=&fid=&pos=uk");
        }
        catch (NullPointerException e) {
            tp.append("https://a.triggit.com/px?u=lx&rtv=ukhw,homepage");
        }

        return tp.toString();
    }

    @Override
    public void verifyTriggItPixelOnCertainPage(String pageName) {
        String triggitText = "";

        switch (pageName) {
            case "index":

                triggitText = getWebdriverInstance().getPageSource();
                if (!purchaseParameters.getCurrencyCode().equals("GBP")) {
                    assertThat(triggitText.contains("http://a.triggit.com/px?u=lx")).as(
                        "Triggit pixel presents on non-UK index page").isFalse();
                    return;
                }

                assertThat(triggitText.contains("http://a.triggit.com/px?u=lx")).as(
                    "Triggit pixel doesn't present on index page").isTrue();
                return;
            case "results":
                triggitText = getWebdriverInstance().getPageSource().replace("&amp;", "&");
                assertThat(triggitText.contains(getTItString())).as("Triggit pixel doesn't present on results page")
                .isTrue();
                return;
            case "details":
                new HotelDetailsPage(getWebdriverInstance());
                triggitText = getWebdriverInstance().getPageSource().replace("&amp;", "&");
                assertThat(triggitText.contains(getPPString())).as("Triggit pixel doesn't present on details page")
                .isTrue();
                return;
            case "billing":
                new HotelBillingOnePage(getWebdriverInstance());
                triggitText = getWebdriverInstance().getPageSource().replace("&amp;", "&");
                assertThat(triggitText.contains(getPPString())).as("Triggit pixel doesn't present on billing page")
                .isTrue();
                return;
            case "confirmation":
                new HotelConfirmationPage(getWebdriverInstance());
                triggitText = getWebdriverInstance().getPageSource().replace("&amp;", "&");
                System.out.print(triggitText);
                assertThat(
                    triggitText.contains("https://a.triggit.com/px?u=lx&rtv=ukhwc," +
                        "ukhwhc&ct=hwukconvtag&ctval1=hotel&ctval2=&ctval3=&ctval4=")).as(
                            "Triggit pixel presents on confirmation page").isTrue();
                return;

            default:
                return;
        }
    }

    @Override
    public void verifyPointrollPixelOnCertainPage(String pageName) {
        String pointPixelText = "";

        switch (pageName) {
            case "index":
                pointPixelText = getWebdriverInstance().getPageSource();
                if (!purchaseParameters.getCurrencyCode().equals("GBP")) {
                    assertThat(pointPixelText.contains(getPPString())).as("Point pixel presents on non-UK index page")
                    .isFalse();
                    return;
                }
                assertThat(pointPixelText.contains(getPPString())).as("Point pixel doesn't present on index page")
                .isTrue();
                return;
            case "results":
                pointPixelText = getWebdriverInstance().getPageSource();
                assertThat(pointPixelText.contains(getPPString())).as("Point pixel doesn't present on results page")
                .isTrue();
                return;
            case "details":
                new HotelDetailsPage(getWebdriverInstance());
                pointPixelText = getWebdriverInstance().getPageSource();
                assertThat(pointPixelText.contains(getPPString())).as("Point pixel doesn't present on details page")
                .isTrue();
                return;
            case "billing":
                new HotelBillingOnePage(getWebdriverInstance());
                pointPixelText = getWebdriverInstance().getPageSource();
                assertThat(pointPixelText.contains(getPPString())).as("Point pixel doesn't present on billing page")
                .isTrue();
                return;
            case "confirmation":
                new HotelConfirmationPage(getWebdriverInstance());
                pointPixelText = getWebdriverInstance().getPageSource();
                assertThat(pointPixelText.contains(getPPString())).as("Point pixel presents on confirmation page")
                .isFalse();
                return;

            default:
                return;
        }
    }

    @Override
    public void verifyPage(String page) {
        switch (page) {
            case "result":
                new HotelResultsPage(getWebdriverInstance());
                return;
            case "details":
                new HotelDetailsPage(getWebdriverInstance());
                return;
            case "billing":
                throw new RuntimeException("Implement me!");
            default:
                return;
        }
    }

    @Override
    public void verifyPaymentStatus(String condition) {
        boolean paymentMethodState;
        switch (purchaseParameters.getPaymentMethodType()) {
            case PayPal:
                paymentMethodState = new HotelPayPalFragment(getWebdriverInstance()).isEnabled();
                break;
            case V_ME:
                paymentMethodState = new HotelVmeFragment(getWebdriverInstance()).isEnabled();
                break;
            default:
                String paymentMethodType = purchaseParameters.getPaymentMethodType().getText();
                throw new UnimplementedTestException(String.format("Unknown payment method \"%s\"", paymentMethodType));
        }
        assertThat(paymentMethodState).as(
            String.format("Payment method %s state", purchaseParameters.getPaymentMethodType())).isEqualTo(
                "available".equalsIgnoreCase(condition));
    }

    @Override
    public void verifySaveBillingInfoVisibility(boolean isDisplayed) {
        HotelBillingOnePage page =
                new HotelBillingOnePage(getWebdriverInstance());
        boolean actual = page.isSaveBillingInfoModuleDisplayed();
        assertThat(actual).as(
                "Expected save billing info module visibility to be " + isDisplayed + " but got: " + actual).isEqualTo(
                isDisplayed);
    }

    @Override
    public void verifyAllPaymentOptionsEnabled() {
        List<WebElement> payments = new HotelBillingOnePage(getWebdriverInstance()).getAllPaymentMethodElements();
        for (WebElement element : payments) {
            assertThat(element.isEnabled()).as(
                "Expected " + element.getAttribute("id") + " to be enabled but it was not.").isTrue();
        }
    }

    @Override
    public void verifyCreditCardOnlyEnabled() {
        boolean isUnavailable = new HotelTripInsuranceFragment(getWebdriverInstance()).isTripInsuranceUnavailable();
        List<WebElement> payments = new HotelBillingOnePage(getWebdriverInstance()).getAllPaymentMethodElements();
        for (WebElement element : payments) {
            String id = element.getAttribute("id");
            if (id.contains("savedCreditCards") || id.contains("useNewCard") || id.contains("newCreditCard")) {
                assertThat(element.isEnabled()).as("Expected " + id + " to be enabled but it was not.").isTrue();
            }
            else {
                if (!isUnavailable) { // No use checking these if trip insurance
                    // is not available.
                    assertThat(element.isEnabled()).as("Expected " + id + " to be disabled but it was not.").isFalse();
                }
            }
        }
    }

    /**
     * Need to make call to select Yes for insurance for {@link HotelBillingOnePage} first which will handle the the
     * ajax completion wait if the test specifies that it is expecting Insurance to be displayed in trip summary.
     */
    @Override
    public void verifyInsuranceUpdateOnBillingTripSummary(boolean isDisplayed) {
        new HotelBillingOnePage(getWebdriverInstance());
        HotelTripInsuranceFragment insuranceFragment = new HotelTripInsuranceFragment(getWebdriverInstance());
        HotelTripSummaryFragment summaryFragment = new HotelTripSummaryFragment(getWebdriverInstance());
        if (insuranceFragment.isTripInsuranceUnavailable() && isDisplayed) {
            throw new PendingException("Trip insurance is not available so nothing to verify on trip summary. " +
                "This maybe an temporary communication issue with Allianz or insurance can not be done with this " +
                "solution.");
        }

        boolean isInsuranceDisplayed = summaryFragment.isTripInsuranceDisplayed();
        assertThat(isInsuranceDisplayed).as(
            "Expected billing page trip summary visibility to be: " + isDisplayed + " but it was: " +
                isInsuranceDisplayed).isEqualTo(isDisplayed);
        if (isDisplayed) {
            LOGGER.info("Test specified expecting insurance to be visible. " +
                "Asserting Insurance and Trip summary insurance costs.");
            String insFragmentCost = insuranceFragment.getTripInsuranceCost();
            String summaryFragmentCost = summaryFragment.getTripInsuranceCost();
            assertThat(insFragmentCost).as(
                "Expected insurance module cost: " + insFragmentCost + " to be equal to cost in trip summary:" + " " +
                    summaryFragmentCost).isEqualTo(summaryFragmentCost);
            purchaseParameters.setInsuranceTotal(summaryFragmentCost);
        }
    }

    @Override
    public void validateInsuranceCostRegardingRoomNumbers() {
        float costFromPage = new HotelTripSummaryFragment(getWebdriverInstance()).getInsuranceCost();
        float roomNumbers = new HotelTripSummaryFragment(getWebdriverInstance()).getRoomNumbers();
        float nightNumbers = new HotelTripSummaryFragment(getWebdriverInstance()).getNightNumbers();
        float calculatedCost = 5 * roomNumbers * nightNumbers;
        assertThat(costFromPage).isEqualTo(calculatedCost);
    }

    @Override
    public void validateTravelerErrors(String bookingProfile) {
        HotelBillingPage hotelBillingPage = new HotelBillingPage(getWebdriverInstance());
        ArrayList<String> errors = new ArrayList<String>();

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
            hotelBillingPage.assertHasFormErrors(error);
        }
    }

    @Override
    public void validateBillingErrors(String bookingProfile, boolean isSignedInUser) {
        /*
         * // Set up the tile id and errors that are specific to registered/guest // users String viewId =
         * isSignedInUser ? ViewIds.TILE_HOTEL_BILLING_ONE_PAGE_CHECKOUT : ViewIds.TILE_BILLING_PAYMENT_INFO;
         */
        // Create the page and check for the presence of the errors
        HotelBillingPage hotelBillingPage = new HotelBillingPage(getWebdriverInstance());

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
        else if ("numbers in city".equals(bookingProfile)) {
            errors.add("Enter a valid city name.");
        }
        else if ("numbers in address".equals(bookingProfile)) {
            errors.add("Enter a valid billing address.");
        }
        else if ("AAA in security".equals(bookingProfile)) {
            errors.add("Enter a valid security code. This is the 3 or 4-digit number on the back of your card.");
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
            hotelBillingPage.assertHasFormErrors(error);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void verifyPurchaseDetailsOnBillingAndConfirmationPages() {

        HotelBillingPage billingPage = new HotelBillingPage(getWebdriverInstance());
        DateFormat formatterDate = new SimpleDateFormat("EEE, MMM dd, yyyy");
        purchaseParameters.setPaymentMethodType(PaymentMethodType.CreditCard);

        final Float billingTotalPrice = billingPage.getTripSummaryFragment().getTotalPrice();
        final Float billingTaxesAndFees = billingPage.getTripSummaryFragment().getTaxesAndFees();

        String confirmationRatePerNightPrice;
        String confirmationTaxesAndFees;
        String confirmationTripTotal;
        String confirmationCheckInDate = null;
        String confirmationCheckOutDate = null;
        String confirmationItineraryNumber = null;

        assertThat(
            formatterDate.format(hotelSearchParameters.getGlobalSearchParameters().getStartDate()).equals(
                billingPage.getTripSummaryFragment().getCheckInDate())).as(
                    "Check-in date on the billing page is correct: " +
                        billingPage.getTripSummaryFragment().getCheckInDate())
                        .isTrue();

        assertThat(
            formatterDate.format(hotelSearchParameters.getGlobalSearchParameters().getEndDate()).equals(
                billingPage.getTripSummaryFragment().getCheckOutDate())).as(
                    "Check-out date on the billing page is correct: " +
                        billingPage.getTripSummaryFragment().getCheckOutDate())
                        .isTrue();

        long nights = (hotelSearchParameters.getGlobalSearchParameters().getEndDate().getTime() - hotelSearchParameters
            .getGlobalSearchParameters().getStartDate().getTime()) /
            (1000 * 60 * 60 * 24); // transform millisec to days;

        assertThat(billingPage.getTripSummaryFragment().getNightNumbers().longValue() == nights).as(
            "Numbers of nights on the billing page is correct: " + nights).isTrue();

        assertThat(
            Float.parseFloat(hotelSearchParameters.getSelectedSearchSolution().getPrice().replace("$", "")) == Float
            .parseFloat(billingPage.getTripSummaryFragment().getPricePerNight())).as(
                "Price per night on the billing page is correct").isTrue();

        completePurchase(false);
        receiveConfirmation();

        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.equals("tiles-def.hotel.confirm.base")) {
            HotelConfirmationPage page = new HotelConfirmationPage(getWebdriverInstance());
            LOGGER.info("Hotel Confirmation Number: " + page.getConfirmationCode());
            LOGGER.info("Hotwire Itinerary: " + page.getHotwireItinerary());
            purchaseParameters.setItinerary(page.getHotwireItinerary());
            confirmationRatePerNightPrice = page.getRatePerNight().replace("$", "");
            confirmationTaxesAndFees = page.getTaxesAndFees().replace("$", "");
            confirmationTripTotal = page.getTripTotal().replace("$", "");
        }
        else if (pageName.equals("tile.hotel.purchase-confirm") || pageName.equals("tile.hotel.booking-confirm")) {
            HotelNewConfirmationPage page = new HotelNewConfirmationPage(getWebdriverInstance());
            LOGGER.info("Hotel Confirmation Number: " + page.getConfirmationNumber());
            LOGGER.info("Hotwire Itinerary: " + page.getItineraryNumber());
            purchaseParameters.setItinerary(page.getItineraryNumber());
            confirmationRatePerNightPrice = page.getRatePerNight().replace("$", "");
            confirmationTaxesAndFees = page.getTaxesAndFees().replace("$", "").replace(",", "");
            confirmationTripTotal = page.getTripTotal().replace("$", "").replace(",", "");
            confirmationCheckInDate = page.getCheckInDate();
            confirmationCheckOutDate = page.getCheckOutDate();
            confirmationItineraryNumber = page.getItineraryNumber();
        }
        else {
            throw new RuntimeException("Expected Hotel Confirmation page but got " + pageName);
        }

        String sql = "select  * from PURCHASE_ORDER where DISPLAY_NUMBER =" + confirmationItineraryNumber;

        Map<String, Object> dbResult = jdbcTemplate.queryForMap(sql);

        assertThat(((BigDecimal) dbResult.get("TOTAL_AMOUNT")).floatValue() ==
            Float.parseFloat(confirmationTripTotal)).as("Total amount into DB: " +
                ((BigDecimal) dbResult.get("TOTAL_AMOUNT")).toString() +
                " equals total amount on confirmation page :" + confirmationTripTotal).isTrue();

        assertThat(
            Float.parseFloat(hotelSearchParameters.getSelectedSearchSolution().getPrice().replace("$", "")) == Float
            .parseFloat(confirmationRatePerNightPrice)).as("Price per night on the confirmation page is correct")
            .isTrue();

        assertThat(Float.parseFloat(confirmationTaxesAndFees) == billingTaxesAndFees).as(
            "Taxes and fees on confirmation page is correct: " + confirmationTaxesAndFees).isTrue();

        assertThat(Float.parseFloat(confirmationTripTotal) == billingTotalPrice).as(
            "Total price on confirmation page is correct: " + confirmationTripTotal).isTrue();

        assertThat(
            formatterDate.format(hotelSearchParameters.getGlobalSearchParameters().getStartDate()).equals(
                confirmationCheckInDate)).as(
                    "Check-in date on the confirmation page is correct: " + confirmationCheckInDate).isTrue();

        assertThat(
            formatterDate.format(hotelSearchParameters.getGlobalSearchParameters().getEndDate()).equals(
                confirmationCheckOutDate)).as(
                    "Check-out date on the confirmation page is correct: " + confirmationCheckOutDate).isTrue();

        assertThat(
            ((Float.parseFloat(confirmationRatePerNightPrice) * nights) + billingTaxesAndFees) == billingTotalPrice)
            .as("(Price per night) * (number of night) + taxes == Total price").isTrue();
    }

    @SuppressWarnings("serial")
    @Override
    public void verifyDollarOneAuth() {
        final com.hotwire.selenium.desktop.row.confirmation.HotelConfirmationPage hotelConfirmationPage =
            new com.hotwire.selenium.desktop.row.confirmation.HotelConfirmationPage(getWebdriverInstance());
        final String itineraryNumber = hotelConfirmationPage.getItineraryNumber();
        final String confirmationNumber = hotelConfirmationPage.getConfirmationNumber();
        final Float price = hotelConfirmationPage.getHotwirePrice();
        purchaseParameters.setCurrencyCode(hotelConfirmationPage.getGlobalHeader().getSelectedCurrencyCode());

        LOGGER.info("Hotel Confirmation Number: " + confirmationNumber);
        LOGGER.info("Hotwire Interary: " + itineraryNumber);

        try {
            String sql = "select pr.status_code, pr.amount, pr.currency_code " +
                "from purchase_order po, payment_receipt pr " +
                "where pr.purchase_order_id = po.purchase_order_id and po.display_number = ? " +
                "order by pr.amount, pr.status_code";
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, new Object[] {itineraryNumber});

            List<Object[]> expectedResults = new ArrayList<Object[]>() {
                {
                    String currencyCode = purchaseParameters.getCurrencyCode();
                    float minimalAmount = 1f;
                    if ("SEK".equalsIgnoreCase(currencyCode) || "NOK".equalsIgnoreCase(currencyCode) ||
                        "DKK".equalsIgnoreCase(currencyCode)) {
                        minimalAmount = 10f;
                    }
                    if (price > 300) {
                        add(new Object[] {minimalAmount, currencyCode});
                    }
                    add(new Object[] {price, currencyCode});
                    add(new Object[] {price, currencyCode});
                }
            };

            while (sqlRowSet.next()) {
                assertThat(new Object[] {sqlRowSet.getFloat("amount"), sqlRowSet.getString("currency_code")}).as(
                    "Transaction data stored in db").isEqualTo(expectedResults.remove(0));
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            LOGGER.info("Issue with DB check.\n" + ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void clickEditCreditCardLink() {
        HotelBillingPage hotelBillingPage = new HotelBillingPage(getWebdriverInstance());
        hotelBillingPage.getEditCreditCard().click();
    }

    @Override
    public void saveBillingInformation(String cardName) {
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        hotelBillingPage.fillCreditCard().getSaveBillingInformation().click();
        hotelBillingPage.fillCreditCard().setNameForNewSavedCard(cardName);
    }

    @Override
    public void saveBillingInformation(String cardName, String password) {
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        hotelBillingPage.fillCreditCard().getSaveBillingInformation().click();
        hotelBillingPage.fillCreditCard().setNameForNewSavedCard(cardName);

        if (!authenticationParameters.isUserAuthenticated()) {
            hotelBillingPage.fillCreditCard().setPasswordForNewSavedCard(password);
        }
    }

    @Override
    public void fillSavedCreditCard() {
        HotelBillingOnePage hotelBillingPage = new HotelBillingOnePage(getWebdriverInstance());
        UserInformation userInformation = purchaseParameters.getUserInformation();
        hotelBillingPage.fillCreditCard().withSavedCcExpMonth(userInformation.getCcExpMonth())
        .withSavedCcExpYear(userInformation.getCcExpYear())
        .withSavedSecurityCode(userInformation.getSecurityCode())
        .withSavedFirstName(userInformation.getCcFirstName()).withSavedLastName(userInformation.getCcLastName())
        .withSavedCountry(userInformation.getBillingCountry())
        .withSavedBillingAddress(userInformation.getBillingAddress()).withSavedCity(userInformation.getCity())
        .withSavedState(userInformation.getState()).withSavedZipCode(userInformation.getZipCode());

        // Store total price from after selecting or declining insurance.
        saveBillingTotalInBean();
    }

    @Override
    public void editCard() {
        HotelCreditCardFragment fragment = new HotelCreditCardFragment(getWebdriverInstance(), true);
        String label = fragment.getLabel();

        UserInformation userInformation = purchaseParameters.getUserInformation();

        String securityCode;
        if (label.contains("American")) {
            securityCode = "9274";
        }
        else {
            securityCode = userInformation.getSecurityCode();
        }


        fragment
                .withSavedCcExpMonth(userInformation.getCcExpMonth())
                .withSavedCcExpYear(userInformation.getCcExpYear())
                .withSavedSecurityCode(securityCode)
                .withSavedFirstName(userInformation.getCcFirstName()).withSavedLastName(userInformation.getCcLastName())
                .withSavedCountry(userInformation.getBillingCountry())
                .withSavedBillingAddress(userInformation.getBillingAddress()).withSavedCity(userInformation.getCity())
                .withSavedState(userInformation.getState()).withSavedZipCode(userInformation.getZipCode());
        saveBillingTotalInBean();
    }

    @Override
    public void clickConfirmationPageCarCrossSell() {
        new HotelConfirmationPage(getWebdriverInstance()).clickCarCrossSell();
    }

    @Override
    public void verifyAutofillCountryCode(String countryCode) {
        HotelBillingPage hotelBillingPage = new HotelBillingPage(getWebdriverInstance());
        assertThat(hotelBillingPage.getPhoneCountry().equals(countryCode)).as(
            "Country code should be: " + countryCode + " for: " + hotelBillingPage.getCountry()).isTrue();
    }

    @Override
    public void validateInsuranceCostIsPercentageOfTotal(int expectedPercentage) {
        float insuranceCost = new HotelTripSummaryFragment(getWebdriverInstance()).getInsuranceCost();
        float taxesAndFees = new HotelTripSummaryFragment(getWebdriverInstance()).getTaxesAndFees();
        float subTotal = new HotelTripSummaryFragment(getWebdriverInstance()).getSubTotalPrice();
        int insurancePercentage = (int) ((insuranceCost / (subTotal + taxesAndFees)) * 100);
        assertThat(expectedPercentage == insurancePercentage)
            .as("Expected does not meet actual percentage")
            .isTrue();
    }

    @Override
    public void validateInsuranceCostInDb(String insuranceCost) {

        String sql = "select po.display_number, oao.purchase_order_id, r.reservation_id, " +
            "p.tp_insurance_policy_number, r.reservation_num, oao.type_code, oao.price_amount " +
            "from participant p, reservation r, order_add_on oao, purchase_order po " +
            "where r.reservation_id = p.reservation_id " +
            "and po.purchase_order_id = oao.reservation_id " +
            "and r.reservation_id = oao.reservation_id " +
            "and po.display_number = '" + purchaseParameters.getItinerary() + "'";

        Map<String, Object> dbResult = jdbcTemplate.queryForMap(sql);

        String dbInsuranceAmount = dbResult.get("PRICE_AMOUNT").toString();
        assertThat(insuranceCost.equals(dbInsuranceAmount)).as("DB vs GUI amount mismatch").isTrue();
    }

    @Override
    public String getHotelProtectionCostOnConfirmation() {
        HotelNewConfirmationPage page = new HotelNewConfirmationPage(getWebdriverInstance());
        LOGGER.info("Insurance cost is : " + page.getTravelInsurance());
        return page.getTravelInsurance();
    }

    @Override
    public void verifyInsuranceCostOnConfirmation() {
        String confirmationInsuranceTotal;
        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.equals("tiles-def.hotel.confirm.base")) {
            HotelConfirmationPage page = new HotelConfirmationPage(getWebdriverInstance());
            LOGGER.info("Hotel Confirmation Number: " + page.getConfirmationCode());
            LOGGER.info("Hotwire Itinerary: " + page.getHotwireItinerary());
            purchaseParameters.setItinerary(page.getHotwireItinerary());
            confirmationInsuranceTotal = page.getInsuranceTotal();
        }
        else if (pageName.equals("tile.hotel.booking-confirm")) {
            HotelNewConfirmationPage page = new HotelNewConfirmationPage(getWebdriverInstance());
            LOGGER.info("Hotel Confirmation number : " + page.getConfirmationNumber());
            LOGGER.info("Hotwire itinerary : " + page.getItineraryNumber());
            confirmationInsuranceTotal = page.getTravelInsurance();
        }
        else if (pageName.equals("tile.hotel.purchase-confirm")) {
            HotelNewConfirmationPage page = new HotelNewConfirmationPage(getWebdriverInstance());
            LOGGER.info("Hotel Confirmation Number: " + page.getConfirmationNumber());
            LOGGER.info("Hotwire Itinerary: " + page.getItineraryNumber());
            purchaseParameters.setItinerary(page.getItineraryNumber());
            confirmationInsuranceTotal = page.getTravelInsurance();
        }
        else {
            throw new RuntimeException("Expected Hotel Confirmation page but got " + pageName);
        }
        assertThat((purchaseParameters.getInsuranceTotal()).equals(confirmationInsuranceTotal)).as(
            "Insurance Total on confirmation != Total price. Expected " + purchaseParameters.getInsuranceTotal() +
            " but actual " + confirmationInsuranceTotal).isTrue();
    }
}




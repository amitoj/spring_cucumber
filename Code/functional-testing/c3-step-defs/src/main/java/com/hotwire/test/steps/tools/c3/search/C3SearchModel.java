/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.search;

import com.hotwire.selenium.tools.c3.C3IndexPage;
import com.hotwire.selenium.tools.c3.C3SearchResultPage;
import com.hotwire.selenium.tools.c3.breadCrumbs.C3BreadcrumbsFragment;
import com.hotwire.selenium.tools.c3.casenotes.C3SearchForCasePage;
import com.hotwire.selenium.tools.c3.customer.C3CaseHistoryPage;
import com.hotwire.selenium.tools.c3.customer.C3CustomerInfoFragment;
import com.hotwire.selenium.tools.c3.customer.C3CustomerMainPage;
import com.hotwire.selenium.tools.c3.customer.C3HotelPageUponDetails;
import com.hotwire.selenium.tools.c3.customer.C3MultipleAccountsSearchResultsPage;
import com.hotwire.selenium.tools.c3.customer.C3PastBookingPage;
import com.hotwire.selenium.tools.c3.customer.C3PaymentDetailsFragment;
import com.hotwire.selenium.tools.c3.customer.C3PaymentDetailsPage;
import com.hotwire.selenium.tools.c3.customer.C3RecentSearchPage;
import com.hotwire.selenium.tools.c3.customer.C3SearchCustomerPage;
import com.hotwire.selenium.tools.c3.customer.C3SearchPartnerPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3AirItineraryPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3CarItineraryPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3HotelItineraryPage;
import com.hotwire.selenium.tools.c3.purchase.air.C3AirConfirmationPage;
import com.hotwire.selenium.tools.c3.purchase.car.C3CarConfirmationPage;
import com.hotwire.selenium.tools.c3.purchase.car.C3OldBillingPage;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3HotelConfirmationPage;
import com.hotwire.selenium.tools.c3.hotel.C3BlockedHotelsPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelBlockConfirmationPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelStarRatingPage;
import com.hotwire.test.steps.tools.State;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.TripInfo;
import com.hotwire.test.steps.tools.bean.c3.C3CaseNoteInfo;
import com.hotwire.test.steps.tools.bean.c3.C3FlightInfo;
import com.hotwire.test.steps.tools.bean.c3.C3HotelSupplyInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.test.steps.tools.bean.c3.C3MobileInfo;
import com.hotwire.test.steps.tools.c3.purchase.C3AirPurchaseModel;
import com.hotwire.test.steps.tools.c3.purchase.C3CarPurchaseModel;
import com.hotwire.test.steps.tools.c3.purchase.C3HotelPurchaseModel;
import com.hotwire.test.steps.tools.c3.purchase.C3PurchaseModel;
import com.hotwire.test.steps.tools.mail.MailModel;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3CaseNotesDao;
import com.hotwire.util.db.c3.C3CustomerDao;
import com.hotwire.util.db.c3.C3HotelSupplyDao;
import com.hotwire.util.db.c3.C3RefundRecoveryCustomerDao;
import com.hotwire.util.db.c3.C3SearchDao;
import cucumber.api.PendingException;
import org.fest.assertions.Delta;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 */

public class C3SearchModel extends ToolsAbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3SearchModel.class);
    private static final String UNAVAILABLE = "N/A";
//    private String windowHandler = null;

    @Resource(name = "customerProfiles")
    Map<String, CustomerInfo> customerProfiles;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private CustomerInfo nonExpressCustomer;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private C3FlightInfo c3FlightInfo;

    @Autowired
    private C3CaseNoteInfo c3CaseNotesInfo;

    @Autowired
    private C3MobileInfo c3MobileInfo;

    @Autowired
    private TripInfo tripInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private C3HotelSupplyInfo c3HotelSupplyInfo;

    @Autowired
    private C3HotelPurchaseModel hotelPurchaseModel;

    @Autowired
    private C3CarPurchaseModel carPurchaseModel;

    @Autowired
    private C3AirPurchaseModel airPurchaseModel;

    @Autowired
    private MailModel mailModel;

    private C3PurchaseModel getPurchaseModel() {
        switch (hotwireProduct.getProductVertical()) {
            case HOTEL:
                return hotelPurchaseModel;
            case CAR:
                return carPurchaseModel;
            case AIR:
                return airPurchaseModel;
            default:
                return null;
        }
    }

    public void searchForCustomerByEmail(String email) {
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        customerInfo.setEmail(email);
        searchCustomerPage.setEmailInput(email);
        searchCustomerPage.clickSubmit();
    }

    public void searchForCustomerByPhone(String phone) {
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        customerInfo.setPhoneNumber(phone);
        searchCustomerPage.searchByPhone(phone);
    }

    public void searchForCustomerByItinerary(String itinerary) {
        new C3SearchCustomerPage(getWebdriverInstance()).setConfirmationNumber(itinerary);
    }

    public void searchForCustomerByPnr(String pnrNumber) {
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.searchByPNR(pnrNumber);
    }

    public void searchForCustomerByCustomerId(String customerId) {
        this.switchToDefaultContent();
        this.switchToFrame("c3Frame");
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.setCustomerId(customerId);
        searchCustomerPage.clickSubmit();
    }

    public void searchForCustomerByCaseId(String caseId) {
        this.switchToDefaultContent();
        this.switchToFrame("c3Frame");
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.setCaseId(caseId);
        searchCustomerPage.clickSubmit();
    }

    public void searchForCustomerByHotelReservationNumber(String hotelReservationNumber) {
        this.switchToDefaultContent();
        this.switchToFrame("c3Frame");
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.setHotelReservationNumber(hotelReservationNumber);
        searchCustomerPage.clickSubmit();
    }

    public void searchForCustomerByCarReservationNumber(String carReservationNumber) {
        this.switchToDefaultContent();
        this.switchToFrame("c3Frame");
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.setCarReservationNumber(carReservationNumber);
        searchCustomerPage.clickSubmit();
    }

    public void searchForCustomerByAirTicketNumber(String airTicketNumber) {
        this.switchToDefaultContent();
        this.switchToFrame("c3Frame");
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.setAirTicketNumber(airTicketNumber);
        searchCustomerPage.clickSubmit();
    }

    public void searchForCustomerByLastName(String value) {
        this.switchToDefaultContent();
        this.switchToFrame("c3Frame");
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.setLastName(value);
        searchCustomerPage.clickSubmit();
    }

    public void checkSearchResults(String value) {
        C3SearchResultPage c3SearchResultPage = new C3SearchResultPage(getWebdriverInstance());
        assertThat(c3SearchResultPage.isResultsPresent()).isTrue();
        assertThat(c3SearchResultPage.getResultsHeaderText()).contains(value);
        assertThat(c3SearchResultPage.countOfSearchResults()).isGreaterThan(1);
    }

    public void verifyErrorMessage(String message) {
        assertThat(new C3SearchCustomerPage(getWebdriverInstance()).getErrorMessage())
                .as("Error message doesn't match").
                contains(message);
    }

    public void verifySearchCountResultsByPhone(int value) {
        assertThat(new C3SearchResultPage(getWebdriverInstance()).countOfSearchResults())
                .as("Count search results more than 20")
                .isLessThan(value);
    }

    public void selectSomeCustomer(int value) {
        new C3SearchResultPage(getWebdriverInstance()).selectSomeCustomer(value);
    }

    public void verifyItineraryDetailsPage() {
        logItinerary();
        assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance()).getItineraryNumber())
                .contains(c3ItineraryInfo.getItineraryNumber());
    }

    public void verifyNecessaryFieldsOnCarItineraryDetailsPage() {
        C3CarItineraryPage c3CarItineraryPage = new C3CarItineraryPage(getWebdriverInstance());
        assertThat(c3ItineraryInfo.getDriverName())
                .as("Driver name is wrong").isEqualToIgnoringCase(c3CarItineraryPage.getDriverName());
        assertThat(c3ItineraryInfo.getCompany())
                .as("Company is wrong").isEqualToIgnoringCase(c3CarItineraryPage.getVendorName());
        assertThat(c3ItineraryInfo.getItineraryNumber())
                .as("Itinerary number is wrong").isEqualToIgnoringCase(c3CarItineraryPage.getItineraryNumber());
        assertThat(c3ItineraryInfo.getCarReservationNumber())
                .as("Car reservation number is wrong")
                .isEqualToIgnoringCase(c3CarItineraryPage.getCarReservationNumber());
        assertThat(tripInfo.getShiftEndDate() - tripInfo.getShiftStartDate() + "")
                .as("Number of days is wrong").isEqualToIgnoringCase(c3CarItineraryPage.getNumberOfDays());
        assertThat(tripInfo.getDestinationLocation())
                .as("Pick Up location ins wrong").isEqualToIgnoringCase(c3CarItineraryPage.getPickUpLocation());
        String pickUpDate = c3CarItineraryPage.getPickUpDate();
        String dropOffDate = c3CarItineraryPage.getDropOffDate();
        try {
            Date date = new SimpleDateFormat("dd-MMM-yy 'at' HH:mm a").parse(pickUpDate);
            pickUpDate = new SimpleDateFormat("EEE, MMM d, yyyy 'at' HH:mma").format(date);
            date = new SimpleDateFormat("dd-MMM-yy 'at' HH:mm a").parse(dropOffDate);
            dropOffDate = new SimpleDateFormat("EEE, MMM d, yyyy 'at' HH:mma").format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        assertThat(pickUpDate)
                .as("Pick up date is wrong").isEqualToIgnoringCase(c3ItineraryInfo.getPickUpDate());
        assertThat(dropOffDate)
                .as("Drop Off date is wrong").isEqualToIgnoringCase(c3ItineraryInfo.getDropOffDate());

        assertThat(Double.valueOf(c3ItineraryInfo.getTotalCost()))
                .as("Comparing amounts with rounding issue handling.")
                .isEqualTo(Double.valueOf(c3CarItineraryPage.getTotalCost().replace("$", "")) +
                        Double.valueOf(c3CarItineraryPage.getTripProtection().replace("$", "")), Delta.delta(0.1));
    }

    public void verifyMobileDeviceType() {
        assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance()).getDeviceType().toUpperCase())
                .isEqualTo(c3MobileInfo.getDeviceType().toString());
    }

    public void verifyApplicationType() {
        try {
            assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance()).getApplicationType())
                    .isEqualTo(c3MobileInfo.getApplicationType());
        }
        catch (AssertionError e) {
            assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance()).getApplicationType())
                        .isEqualTo("DSS Widget");
        }
    }

    public void clickViewConfirmation() {
        hotwireProduct.getItineraryPage(getWebdriverInstance()).clickViewConfirmation();
    }

    public void verifyItineraryPageUponConfirmation() {
        switch (hotwireProduct.getProductVertical()) {
            case HOTEL:
                new C3HotelConfirmationPage(getWebdriverInstance());
                break;
            case CAR:
                new C3CarConfirmationPage(getWebdriverInstance());
                break;
            case AIR:
                new C3AirConfirmationPage(getWebdriverInstance());
                break;
            default:
                unimplementedTest();
        }
    }

    public void verifyItineraryPageUponDetails() {
        switch (hotwireProduct.getProductVertical()) {
            case HOTEL:
                new C3HotelPageUponDetails(getWebdriverInstance());
                break;
            default:
                throw new UnimplementedTestException("Invalid ProductVertical or not implemented.");
        }
    }

    public void verifyGoogleMap() {
        C3HotelPageUponDetails hotelDetails = new C3HotelPageUponDetails(getWebdriverInstance());
        assertThat(hotelDetails.isGoogleMapDisplayed()).isTrue();
    }

    public void searchByReferenceNumber() {
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.searchByReferenceNumber(getRefNumber());
    }

    public void startSearchByReferenceNumberOnCustomerInfo() {
        C3CustomerMainPage searchCustomerPage = new C3CustomerMainPage(getWebdriverInstance());
        searchCustomerPage.startSearchByReferenceNumber(c3ItineraryInfo.getReferenceNumberResults());
    }

    public void checkTheSameReferenceNumber() {
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.searchByReferenceNumber(c3ItineraryInfo.getReferenceNumberResults());
    }

    public void searchByReferenceNumber(String referenceNumber) {
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.searchByReferenceNumber(referenceNumber);
    }

    public void searchWithFirstLastName(String firstName, String lastName) {
        C3SearchCustomerPage searchCustomerPage = new C3SearchCustomerPage(getWebdriverInstance());
        searchCustomerPage.setFirstName(firstName);
        searchCustomerPage.setLastName(lastName);
    }

    public void clickCustomerSearchLink() {
        new C3IndexPage(getWebdriverInstance()).clickOnCustomerLink();
    }

    public void verifyAccountPageByName(String name) {
        assertThat(new C3CustomerInfoFragment(getWebdriverInstance()).getName())
                .as("Account name doesn't match")
                .isEqualTo(name);
    }

    public void verifyMultipleAccountsSearchResultsPage() {
        new C3MultipleAccountsSearchResultsPage(getWebdriverInstance());
    }

    public void choosePastPurchasesForFirstAccountOnMultiplePage(String vertical) {
        new C3MultipleAccountsSearchResultsPage(getWebdriverInstance())
                .selectPastPurchasesForFirstResults(vertical);
    }

    public void verifyAccountPageByCustomerId() {
        assertThat(new C3CustomerInfoFragment(getWebdriverInstance()).getEmail())
                .as("Accout email doesn't match customer ID")
                .isEqualTo(new C3CustomerDao(getDataBaseConnection())
                        .getEmailByCustomerId(customerInfo.getCustomerId()));
    }

    public void verifyAccountPageByCaseId() {
        assertThat(new C3CustomerInfoFragment(getWebdriverInstance()).getEmail())
                .as("Account email doesn't match case ID")
                .isEqualTo(new C3CaseNotesDao(getDataBaseConnection())
                        .getEmailByCaseId(c3CaseNotesInfo.getCaseId()));
    }

    public void verifyPurchaseByGDS() {
        assertThat(new C3CustomerInfoFragment(getWebdriverInstance()).getEmail())
                .as("Account email doesn't match purchase GDS number")
                .isEqualTo(new C3CustomerDao(getDataBaseConnection())
                        .getEmailByHotelReservationNumber(c3ItineraryInfo.getHotelReservationNumber()));
        assertThat(new C3HotelItineraryPage(getWebdriverInstance()).getPNR()
                        .startsWith(c3ItineraryInfo.getHotelReservationNumber()))
                .as("Purchase GDS number is not the same as in DB")
                .isTrue();
    }

    public void verifyPurchaseByAirTicketNumber() {
        assertThat(new C3CustomerInfoFragment(getWebdriverInstance()).getEmail())
                .as("Account email doesn't match Air ticket number")
                .isEqualTo(new C3CustomerDao(getDataBaseConnection())
                        .getEmailByAirTicketNumber(c3ItineraryInfo.getAirTicketNumber()));
        assertThat(new C3AirItineraryPage(getWebdriverInstance()).getAirTicketNumbers()
                .contains(c3ItineraryInfo.getAirTicketNumber()))
                .as("Air ticket number is not the same as in DB " + c3ItineraryInfo.getAirTicketNumber())
                .isTrue();
    }

    public void returnToIndexC3Page() {
        new C3SearchCustomerPage(getWebdriverInstance()).returnToIndexC3Page();
    }

    public void navigateToRejectedPurchaseDetails() {
        new C3PastBookingPage(getWebdriverInstance()).clickOnLastBooking();
    }

    /**
     *   This method skip Abandoned case notes alert, that sometimes appear.
     *   This is pop-up alert, not WebPage.
     */
    public void discardAbandonedCaseNotesAlert() {
        try {
            getWebdriverInstance().switchTo().frame("notesFrame");
            getWebdriverInstance().findElement(By.id("discardCaseConfirmPopupLayer_confirmButton")).click();
            LOGGER.info("Processing discardAbandonedCaseNotesAlert");
        }
        catch (NoSuchFrameException | NoSuchElementException e) {
            LOGGER.info("NO Alert. Skip");
        }
    }

    public void saveHotelAddress() {
        c3HotelSupplyInfo.setHotelAddress(hotwireProduct.getItineraryPage(getWebdriverInstance()).getHotelAddress());
    }

    public void verifyPNRonItineraryDetails() {
        assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance()).getPNR())
                .contains(c3ItineraryInfo.getPnrNumber());
    }

    public void clickOnCaseNotesSearch() {
        new C3IndexPage(getWebdriverInstance()).searchForCaseNotes();
    }

    public void searchCaseNotesById() {
        new C3SearchForCasePage(getWebdriverInstance()).searchCaseByID(c3ItineraryInfo.getCaseNotesID());
    }

    public void searchCaseNotesByEmail() {
        new C3SearchForCasePage(getWebdriverInstance()).searchCaseByEmail(customerInfo.getEmail());
    }

    public void searchCaseNotesByCSRName(Integer days) {
        new C3SearchForCasePage(getWebdriverInstance()).searchCaseByCSR(days);
    }

    public void searchCaseNotesByItinerary() {
        new C3SearchForCasePage(getWebdriverInstance()).searchCaseByItinerary(c3ItineraryInfo.getItineraryNumber());
    }

    public void clickSiteSearchLink() {
        new C3CustomerMainPage(getWebdriverInstance()).clickSiteSearchLink();
    }

    public void verifyCustomerEmail() {
        assertThat(customerInfo.getEmail())
                .isEqualTo(new C3CustomerInfoFragment(getWebdriverInstance()).getEmail());
        LOGGER.info("Customer Email: OK");
    }

    public void verifyCustomerType() {
        String type = customerInfo.getCustomerType().toString();
        CustomerInfo profile = customerProfiles.get(type);
        assertThat(profile.getAccountType())
                .isEqualTo(new C3CustomerInfoFragment(getWebdriverInstance()).getAccountType());
        LOGGER.info("Customer Type: OK");
        assertThat(profile.getWatermark())
                .isEqualTo(new C3CustomerInfoFragment(getWebdriverInstance()).getWatermark());
        LOGGER.info("Customer Watermark: OK");

        verifyBreadCrumbs(profile.getBreadcrumbs());
        switch (customerInfo.getCustomerType()) {
            case PARTNER:
                LOGGER.info("Partner purchase");
                break;
            case EX_EXPRESS:
                LOGGER.info("Former express customer purchase");
                final String expressProgramStatus = new C3CustomerInfoFragment(getWebdriverInstance())
                        .getExpressProgramStatus();
                assertThat(expressProgramStatus)
                        .contains("Former");
                String timestamp = expressProgramStatus.replaceFirst("^.*\\(", "").replaceFirst("\\)$", "");
                DateTime parsedTimestamp = DateTimeFormat.forPattern("dd-MMM-yy h:mm a")
                        .parseDateTime(timestamp.replace(" at", ""));
                //Assert that timestamp that is displayed in C3 not differs timestamp
                // from DB or differs in 5 seconds range
                assertThat(
                        Math.abs(parsedTimestamp.getMillis() - customerInfo.getExpressDownGradeDate().getMillis()) /
                                10000
                )
                        .isLessThanOrEqualTo(5);
                break;
            default:
                assertThat(profile.getExpressProgram())
                        .isEqualTo(new C3CustomerInfoFragment(getWebdriverInstance()).getExpressProgramStatus());
        }
        LOGGER.info("Express Program: OK");

    }

    private void verifyBreadCrumbs(String customerIdentifier) {
        customerInfo.setCustomerInfoByItinerary(c3ItineraryInfo.getItineraryNumber());
        String breadcrumbs = new C3BreadcrumbsFragment(getWebdriverInstance()).getBreadcrumbs();
        String expected;
        if (customerInfo.getCustomerType().equals(CustomerInfo.CustomerTypes.PARTNER)) {
            expected = String.format("View itinerary %s details",
                    c3ItineraryInfo.getItineraryNumber());
        }
        else {
            expected = String.format("%s \\(?%s\\)?( )?> View Past Bookings > View itinerary (GP)?%s details",
                    customerInfo.getFullName(),
                    customerIdentifier,
                    c3ItineraryInfo.getItineraryNumber());
        }
        assertThat(breadcrumbs).matches(expected);
        LOGGER.info("Customer Breadcrumbs: OK");
    }

    public void verifyCustomerName() {
        assertThat(customerInfo.getFirstName() + " " + customerInfo.getLastName())
                .isEqualTo(new C3CustomerInfoFragment(getWebdriverInstance()).getName());
        LOGGER.info("Customer Name: OK");
    }

    public void verifyPrimaryPhone() {
        assertThat(customerInfo.getPhoneNumber())
                .as("Phones does not match").
                contains(getPhoneLast7Digits(new C3CustomerInfoFragment(getWebdriverInstance()).getPhoneNumber()));
        LOGGER.info("Customer Primary phone: OK");
    }

    public void verifyAltPhone() {
        assertThat(customerInfo.getAltPhone())
                .as("Alt phone doesn't match").
                contains(getPhoneLast7Digits(new C3CustomerInfoFragment(getWebdriverInstance())
                        .getAltPhoneNumber()));
        LOGGER.info("Customer Alternative phone: OK");
    }

    private String getPhoneLast7Digits(String rowPhone) {
        String digits = rowPhone.replaceAll("[+()\\- ]", "");
        return digits.length() > 7 ? digits.substring(digits.length() - 7) : digits;
    }

    public void verifyAddress() {
        assertThat(customerInfo.getZipCode())
                .isEqualTo(new C3CustomerInfoFragment(getWebdriverInstance()).getZip());
        LOGGER.info("Customer Zip: OK");
        assertThat(customerInfo.getCountry())
                .isEqualTo(new C3CustomerInfoFragment(getWebdriverInstance()).getCountry());
        LOGGER.info("Customer Country: OK");
    }

    public void verifyCustomerInfo() {
        verifyCustomerName();
        verifyCustomerEmail();
        verifyPrimaryPhone();
        verifyAltPhone();
        verifyAddress();
    }

    public void clickPartnerSearchLink() {
        new C3IndexPage(getWebdriverInstance()).clickSearchForPartnerLink();
    }

    public void searchForPartnerByItinerary(String itineraryNumber) {
        new C3SearchPartnerPage(getWebdriverInstance()).searchForItinerary(itineraryNumber);
    }

    public void clickOnViewResources() {
        new C3IndexPage(getWebdriverInstance()).clickOnViewResources();
    }

    public void setupHotelPurchaseWithStarRating() {
        final Map rating = new C3HotelSupplyDao(getDataBaseConnection()).getHotelPurchaseWithStarRating();
        c3ItineraryInfo.setItineraryNumber(getRatingFromDB(rating, "DISPLAY_NUMBER"));
        c3HotelSupplyInfo.setHotelID(getRatingFromDB(rating, "HOTEL_ID"));
        c3HotelSupplyInfo.setFullHotelName(getRatingFromDB(rating, "HOTEL_NAME"));
        c3HotelSupplyInfo.setHotwireRating(getRatingFromDB(rating, "RATING_CODE"));
        c3HotelSupplyInfo.setExpediaRating(getRatingFromDB(rating, "EXPEDIA_STAR_RATING"));
        //check if rating was changed
        setLastRatingChangeFromHotel(rating);
        //check if hotel has survey
        c3HotelSupplyInfo.setHotelSurvey(new C3HotelSupplyDao(getDataBaseConnection())
                .isHotelHasSurvey(c3HotelSupplyInfo.getHotelID()));
    }

    private String getRatingFromDB(Map rating, String columnName) {
        if (rating.get(columnName) != null) {
            return rating.get(columnName).toString();
        }
        else {
            return UNAVAILABLE;
        }
    }

    private void setLastRatingChangeFromHotel(Map rating) {
        if (new C3HotelSupplyDao(getDataBaseConnection()).isHotelRatingChanged(c3HotelSupplyInfo.getHotelID())) {
            String changeDate = rating.get("RATING_CODE_CHANGE_DATE").toString();
            c3HotelSupplyInfo.setLastRatingChange(new DateTime(changeDate.replaceFirst("\\s+.*$", "")));
            c3HotelSupplyInfo.setRatingChanged(true);
        }
        else {
            c3HotelSupplyInfo.setRatingChanged(false);
        }
    }

    public void verifyHotelStarRatingLink(boolean negation) {
        final boolean starRatingLinkDisplayed = hotwireProduct.getItineraryPage(getWebdriverInstance())
                .isStarRatingLinkDisplayed();
        if (negation) {
            assertThat(starRatingLinkDisplayed).isTrue();
        }
        else {
            assertThat(starRatingLinkDisplayed).isFalse();
        }
    }

    public void openHotelStarRating() {
        hotwireProduct.getItineraryPage(getWebdriverInstance()).clickOnHotelStarRatingLink();
    }

    public void verifyStarRatingWindow() {
        C3HotelStarRatingPage ratingPage = new C3HotelStarRatingPage(getWebdriverInstance());
        assertThat(ratingPage.getBenchmarkHotelID()).isEqualTo(c3HotelSupplyInfo.getHotelID());
        assertThat(ratingPage.getHotelName().trim())
                .isEqualTo(c3HotelSupplyInfo.getFullHotelName().replaceAll("\\(.*\\)", "").trim());
//        verification of star rating details section
        verifyRating(ratingPage.getExpediaStarRating(), c3HotelSupplyInfo.getExpediaRating());
        verifyRating(ratingPage.getHotwireStarRating(), c3HotelSupplyInfo.getHotwireRating());
    }

    private void verifyRating(String actual, String expected) {
        try {
            assertThat(actual).isEqualTo(expected);
        }
        catch (AssertionError e) {
            assertThat(Double.valueOf(actual)).isEqualTo(Double.valueOf(expected));
        }
    }

    public void verifyStarRatingChange() {
        final C3HotelStarRatingPage c3HotelStarRatingPage = new C3HotelStarRatingPage(getWebdriverInstance());
        if (c3HotelSupplyInfo.isRatingChanged()) {
            assertThat(DateTimeFormat.forPattern("d MMM YYYY")
                    .parseDateTime(c3HotelStarRatingPage.getLastRatingChange()))
                    .isEqualTo(c3HotelSupplyInfo.getLastRatingChange());
            assertThat(c3HotelStarRatingPage.getRatingChange()).isNotEqualTo(UNAVAILABLE);
            c3HotelStarRatingPage.verifyRatingChangeFragment();
        }
        else {
            assertThat(c3HotelStarRatingPage.getLastRatingChange()).isEqualTo(UNAVAILABLE);
            assertThat(c3HotelStarRatingPage.getRatingChange()).contains(UNAVAILABLE);
        }
    }

    public void verifyHotelSurvey() {
        if (c3HotelSupplyInfo.isHotelSurvey()) {
            new C3HotelStarRatingPage(getWebdriverInstance()).verifySurveyFragment();
        }
    }

    public void gotoDisplayBillingPageForItinerary() {
        hotwireProduct.getItineraryPage(getWebdriverInstance()).clickOnDisplayBillingPageForItinerary();
    }

    public void checkDisplayBillingPageForItinerary() {
        C3OldBillingPage billing = new C3OldBillingPage(getWebdriverInstance());
        assertThat(billing.getBookTripButton().isDisplayed())
                .as("Book trip button is not displayed").isTrue();
        assertThat(billing.getAgreementCheckBox().isSelected())
                .as("Agreement checkbox is not selected").isTrue();
    }

    public void selectRecentSearchPage() {
        new C3CustomerMainPage(getWebdriverInstance()).chooseRecentSearches();
    }

    public void selectPastBookingTab(String tab) {
        hotwireProduct.setProductVertical(tab);
        new C3PastBookingPage(getWebdriverInstance()).choosePastBookingTab(tab);
    }

    public void selectCaseHistoryPage() {
        new C3BreadcrumbsFragment(getWebdriverInstance()).gotoMainCustomerPage();
        new C3CustomerMainPage(getWebdriverInstance()).clickEditCustomerCaseHistory();
    }

    public void verifyCaseNoteForItinerary() {
        assertThat(new C3CaseHistoryPage(getWebdriverInstance())
                        .isCaseForItineraryExist(c3ItineraryInfo.getItineraryNumber()))
                .as("Itinerary " + c3ItineraryInfo.getItineraryNumber() + " is not visible on page")
                .isTrue();
    }

    public void resendEmailsForCheckesClick() {
        new C3PastBookingPage(getWebdriverInstance()).resendEmailsForCheckesClick();
    }

    public void verifyViewAllView15Links() {
        C3RecentSearchPage recentSearchPage = new C3RecentSearchPage(getWebdriverInstance());
        assertThat(recentSearchPage.verifyViewAllLink())
                .as("'View All' link is not displayed").isTrue();
        recentSearchPage.clickViewAllLink();
        assertThat(recentSearchPage.verifyView15Link())
                .as("'View 15' link is not displayed").isTrue();
    }

    public void clickOnDetailsUponConfirmation() {
        hotwireProduct.getItineraryPage(getWebdriverInstance()).clickOnDetailsUponConfirmation();
    }

    public void returnToCustomerAccount() {
        getWebdriverInstance().switchTo().defaultContent();
        getWebdriverInstance().switchTo().frame("c3Frame");
        getWebdriverInstance().findElements(By.cssSelector("td.breadcrumbbar>a")).get(0).click();
    }

    private String getRefNumber() {
        String refNumber;
        if (c3ItineraryInfo.getReferenceNumberResults() != null &&
                !c3ItineraryInfo.getReferenceNumberResults().isEmpty()) {
            refNumber = c3ItineraryInfo.getReferenceNumberResults();
        }
        else {
            if (c3ItineraryInfo.getReferenceNumberDetails() != null &&
                    !c3ItineraryInfo.getReferenceNumberDetails().isEmpty()) {
                refNumber = c3ItineraryInfo.getReferenceNumberDetails();
            }
            else {
                refNumber = "";
            }
        }
        return refNumber;
    }

    public void checkRecentSearchBySearchID() {
        C3RecentSearchPage recentSearchPage = new C3RecentSearchPage(getWebdriverInstance());
        String expected = getRefNumber();
        String actual = recentSearchPage.getLastSearchID();
        assertThat(expected.contains(actual)).as("SearchID was -" + expected +
                ", but actual -" + actual).isTrue();
    }

    public void checkPastBookingByItinerary() {
        C3PastBookingPage pastBookingPage = new C3PastBookingPage(getWebdriverInstance());
        String expected = c3ItineraryInfo.getItineraryNumber();
        String actual = pastBookingPage.getLastItinerary();
        assertThat(expected.equals(actual)).as("Itinerary was -" + expected +
                ", but actual -" + actual).isTrue();
    }

    public void selectPastBookingLink() {
        new C3CustomerMainPage(getWebdriverInstance()).choosePastBooking();
    }

    public void selectRecentSearchBySearchID() {
        new C3RecentSearchPage(getWebdriverInstance()).
                selectRecentSearchByID(getRefNumber());
        this.getPurchaseModel().processSearchFrame();
    }

    public void completeLastSearchFromRecentSearch() {
        new C3RecentSearchPage(getWebdriverInstance())
                .selectLastSearchButton();
        hotwireProduct.getPurchaseModel().processAlert();
        hotwireProduct.getPurchaseModel().processSearchFrame();
//         hotwireProduct.getPurchaseModel().verifyCustomerSalesPage();
        hotwireProduct.getPurchaseModel().waitForResults();
    }

    public void verifySearchIDOnResultPage(boolean isExists) {
        String expectedReferenceNumber = c3ItineraryInfo.getReferenceNumberResults();
        this.getPurchaseModel().processSearchFrame();
        this.getPurchaseModel().saveReferenceNumberFromResultsPage();
        Long iRef = Long.parseLong(this.getPurchaseModel().getReferenceNumberFromResultsPage()) - 1;
        String actualReferenceNumber = iRef.toString();
        assertThat(expectedReferenceNumber.equals(actualReferenceNumber))
                .as("expected " + expectedReferenceNumber + " actual " + actualReferenceNumber).isEqualTo(isExists);
    }

    public void checkRecentSearchByCSR(String param) {
        String expectedReferenceNumber = getRefNumber();
        String csr = new C3RecentSearchPage(getWebdriverInstance()).
                getRecentSearchByCSR(expectedReferenceNumber);
        param = (param.equals("not ")) ? "No" : "Yes";
        assertThat(csr).as("Searched by CSR").isEqualTo(param);
    }

    public void checkPreviousRecentSearchByCSR() {
        Long iRef = Long.parseLong(getRefNumber()) - 1; // previous ref
        c3ItineraryInfo.setItineraryNumber(iRef.toString());
        this.checkRecentSearchByCSR("");
    }

    public void checkNoPurchaseMessage(String tab) {
        String year = Calendar.getInstance().getTime().toString().split(" ")[5];
        String expected = String.format("This customer did not purchase any %s itineraries in %s", tab, year);
        assertThat(verifyTextOnPageBoolean(expected)).isTrue();
    }

    public void choosePNRLinkForItinerary() {
        C3PastBookingPage pastBookingPage = new C3PastBookingPage(getWebdriverInstance());
        String itineraryNumber = c3ItineraryInfo.getItineraryNumber();
        c3ItineraryInfo.setPnrNumber(pastBookingPage.getPNRByItinerary(itineraryNumber));
        pastBookingPage.choosePNRLinkForItinerary(itineraryNumber);
    }

    public void checkCustomerSearchErrors() {
        assertThat(verifyTextOnPageBoolean("Your search did not match any customer records. You may create an account" +
                " for this customer\n            or try your search again. " +
                "If you reattempt your search, we suggest the following:"))
                .as("Your search did not match any customer records. You may create an account for this customer\n" +
                        "            or try your search again. If you reattempt your search, we suggest the following:")
                .isTrue();

        assertThat(verifyTextOnPageBoolean("Make sure all words are spelled correctly"))
                .as("Make sure all words are spelled correctly").isTrue();

        assertThat(verifyTextOnPageBoolean("Try searching on different information"))
                .as("Try searching on different information").isTrue();

        assertThat(verifyTextOnPageBoolean("Try searching only on last name"))
                .as("Try searching only on last name").isTrue();

        assertThat(verifyTextOnPageBoolean("Create a new customer account using the search criteria"))
                .as("Create a new customer account using the search criteria").isTrue();
    }

    public void clickFirstCarConfirmationLink() {
        C3PastBookingPage pastBookingPage = new C3PastBookingPage(getWebdriverInstance());
        hotwireProduct.setProductVertical("car");
        c3ItineraryInfo.setItineraryNumber(pastBookingPage.getLastItinerary());
        pastBookingPage.clickFirstCarConfirmationLink();
    }

    public void verifyCustomerDetailsPage() {
        new C3CustomerInfoFragment(getWebdriverInstance());
    }

    public void givenPurchaseRefundedByCRR() {
        switch(hotwireProduct.getProductVertical()) {
            case HOTEL:
                c3ItineraryInfo.setItineraryNumber(new C3RefundRecoveryCustomerDao(getDataBaseConnection())
                        .getHotelItineraryRefundedByCSR());
                break;
            case CAR:
                c3ItineraryInfo.setItineraryNumber(new C3RefundRecoveryCustomerDao(getDataBaseConnection())
                    .getCarItineraryRefundedByCSR());
                break;
            default:
                unimplementedTest();
        }
    }

    public void givenPurchaseRefundedByCustomer() {
        switch(hotwireProduct.getProductVertical()) {
            case HOTEL:
                c3ItineraryInfo.setItineraryNumber(new C3RefundRecoveryCustomerDao(getDataBaseConnection())
                        .getHotelItineraryRefundedByCustomer());
                break;
            default:
                unimplementedTest();
        }
    }

    public void getBackToRecentSearchTab() {
        getWebdriverInstance().switchTo().parentFrame();
        getWebdriverInstance().findElement(By.xpath("//a[text()='View recent searches']")).click();
    }

    public void getPurchaseDoneByCsr(String isCSR) {
        String itinerary = "";
        if (isCSR.equals("not ")) {
            itinerary = new C3PastBookingPage(getWebdriverInstance()).
                    getPurchaseDoneByDomestic();
        }
        else {
            itinerary = new C3PastBookingPage(getWebdriverInstance()).
                    getPurchaseDoneByCsr();
        }
        if (itinerary.equals("none")) {
            throw new PendingException("Cant find " + isCSR + " csr made purchase");
        }
        c3ItineraryInfo.setItineraryNumber(itinerary);
    }

    public void clickViewCaseAttachedLink() {
        new C3PastBookingPage(getWebdriverInstance()).
                clickViewCaseAttachedLink(c3ItineraryInfo.getItineraryNumber());
    }

    public void checkIntlSiteBookingFlag() {
        assertThat(new C3PastBookingPage(getWebdriverInstance()).
                getIntlSiteBookingFlag(c3ItineraryInfo.getItineraryNumber())).isEqualTo("Y\n" +
                "United Kingdom");
    }

    public void verifyPurchaseByCarReservationNumber() {
        assertThat(new C3CustomerInfoFragment(getWebdriverInstance()).getEmail())
                .as("Account email doesn't match Air ticket number")
                .isEqualTo(new C3CustomerDao(getDataBaseConnection())
                        .getEmailByCarReservationNumber(c3ItineraryInfo.getCarReservationNumber()));
        assertThat(new C3CarItineraryPage(getWebdriverInstance()).getCarReservationNumber()
                .contains(c3ItineraryInfo.getCarReservationNumber()))
                .as("Air ticket number is not the same as in DB " + c3ItineraryInfo.getCarReservationNumber())
                .isTrue();
    }

    public void checkHotelParametersForRecentSearch() {
        C3RecentSearchPage recentSearchPage = new C3RecentSearchPage(getWebdriverInstance());
        //actual from tripInfo
        String locationActual = tripInfo.getFromLocation();
        String checkInActual = new DateTime(new DateTime()).plusDays(tripInfo.getShiftStartDate())
                .toLocalDate().toString("d-MMM-yy");
        String checkOutActual = new DateTime(new DateTime()).plusDays(tripInfo.getShiftEndDate())
                .toLocalDate().toString("d-MMM-yy");
        Integer roomsActual = tripInfo.getNumberOfHotelRooms();
        Integer adultsActual = tripInfo.getNumberOfAdults();
        Integer childrenActual = tripInfo.getNumberOfChildren();
        Integer nightsActual = tripInfo.getShiftEndDate() - tripInfo.getShiftStartDate();
        // expected from UI
        String referenceNumberResults = getRefNumber();
        String locationExpected = recentSearchPage.getLocationBySearchID(referenceNumberResults);
        String checkInExpected = recentSearchPage.getCheckInBySearchID(referenceNumberResults);
        String checkOutExpected = recentSearchPage.getCheckOutBySearchID(referenceNumberResults);
        String roomsExpected = recentSearchPage.getRoomsBySearchID(referenceNumberResults);
        String adultsExpected = recentSearchPage.getAdultsBySearchID(referenceNumberResults);
        String childrenExpected = recentSearchPage.getChildrenBySearchID(referenceNumberResults);
        String nightsExpected = recentSearchPage.getNightsBySearchID(referenceNumberResults);
        //assertion
        assertThat(locationExpected).as("not equals actual and expected").contains(locationActual);
        assertThat(checkInActual).as("not equals actual and expected").isEqualTo(checkInExpected);
        assertThat(checkOutActual).as("not equals actual and expected").isEqualTo(checkOutExpected);
        assertThat(roomsActual.toString()).as("not equals actual and expected").isEqualTo(roomsExpected);
        assertThat(adultsActual.toString()).as("not equals actual and expected").isEqualTo(adultsExpected);
        assertThat(childrenActual.toString()).as("not equals actual and expected").isEqualTo(childrenExpected);
        assertThat(nightsActual.toString()).as("not equals actual and expected").isEqualTo(nightsExpected);

    }

    public void checkCarParametersForRecentSearch() {
        C3RecentSearchPage recentSearchPage = new C3RecentSearchPage(getWebdriverInstance());
        //actual from tripInfo
        String locationActual = tripInfo.getFromLocation();
        String checkInActual = new DateTime(new DateTime()).plusDays(tripInfo.getShiftStartDate())
                .toLocalDate().toString("d-MMM-yy");
        String checkOutActual = new DateTime(new DateTime()).plusDays(tripInfo.getShiftEndDate())
                .toLocalDate().toString("d-MMM-yy");
        String pickupTimeActual = tripInfo.getPickupTime().toUpperCase();
        String dropoffTimeActual = tripInfo.getDropoffTime().toUpperCase();
        String cardTypeActual = "Credit Card";
        // expected from UI
        String referenceNumberResults = getRefNumber();
        String locationExpected = recentSearchPage.getLocationBySearchID(referenceNumberResults);
        String checkInExpected = recentSearchPage.getCheckInBySearchID(referenceNumberResults);
        String checkOutExpected = recentSearchPage.getCheckOutBySearchID(referenceNumberResults);
        String pickupTimeExpected = recentSearchPage.getPickUpTimeBySearchID(referenceNumberResults);
        String dropoffTimeExpected = recentSearchPage.getDropOffTimeBySearchID(referenceNumberResults);
        //assertion
        assertThat(locationExpected).as("not equals actual and expected").contains(locationActual);
        assertThat(checkInActual).as("not equals actual and expected").isEqualTo(checkInExpected);
        assertThat(checkOutActual).as("not equals actual and expected").isEqualTo(checkOutExpected);
        assertThat(pickupTimeActual).as("not equals actual and expected").isEqualTo(pickupTimeExpected);
        assertThat(dropoffTimeActual).as("not equals actual and expected").isEqualTo(dropoffTimeExpected);
    }

    public void checkCustomerInfoForNonExpress() {
        C3CustomerInfoFragment customerInfoFragment = new C3CustomerInfoFragment(getWebdriverInstance());

        assertThat(customerInfoFragment.getName()).as("Account name is correct")
                .isEqualTo(nonExpressCustomer.getFirstName() + " " + nonExpressCustomer.getLastName());
        assertThat(customerInfoFragment.getAccountType()).as("Account type is correct")
                .isEqualTo(customerInfo.getAccountType());
        assertThat(customerInfoFragment.getEmail()).as("Email is correct")
                .isEqualTo(customerInfo.getEmail());

        assertThat(customerInfoFragment.getPhoneNumber().replaceAll("\\D", "")).as("Phone number is correct")
                .isEqualTo(customerInfo.getPhoneNumber());
//        customerInfoFragment.getAltPhoneNumber();

        assertThat(customerInfoFragment.getCountry()).as("Country is correct")
                .isEqualTo(customerInfo.getCountry());
        assertThat(customerInfoFragment.getZip()).as("Zip is correct")
                .isEqualTo(billingInfo.getZipCode());

    }

    public void resendEmailsForMultiplyItinerariesAndCheck() throws Exception {
        String firstItinerary;
        String secondItinerary;

        C3PastBookingPage pastBookingPage = new C3PastBookingPage(getWebdriverInstance());

        Integer firstConfirmedPurchaseNumber  = pastBookingPage.getNumbersOfConfirmedPurchase().get(0);
        Integer secondConfirmedPurchaseNumber  = pastBookingPage.getNumbersOfConfirmedPurchase().get(1);

        pastBookingPage.clickCheckBoxByNum(firstConfirmedPurchaseNumber);
        pastBookingPage.clickCheckBoxByNum(secondConfirmedPurchaseNumber);

        firstItinerary = pastBookingPage.getItineraryByNum(firstConfirmedPurchaseNumber);
        secondItinerary = pastBookingPage.getItineraryByNum(secondConfirmedPurchaseNumber);

        pastBookingPage.resendEmailsForCheckesClick();

        assertThat(pastBookingPage.getConfirmationMessage()).as("Confirmation message is correct")
                .isEqualTo("Itinerary confirmation or Destination Services Voucher e-mail successfully sent");

        mailModel.verifyTextInEmail(firstItinerary);
        mailModel.verifyTextInEmail(secondItinerary);

    }

    public void verifyNecessaryFieldsOnHotelItineraryDetailsPage() {
        C3HotelItineraryPage pg = new C3HotelItineraryPage(getWebdriverInstance());
        assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance()).getItineraryNumber())
                .isEqualTo(c3ItineraryInfo.getItineraryNumber());
        assertThat(pg.getHotelReservationNumber()).contains(c3ItineraryInfo.getHotelReservationNumber());
        assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance()).getGuestName())
                .isEqualTo(c3HotelSupplyInfo.getHotelGuestName());
        assertThat(pg.getFullHotelName()).isEqualTo(c3HotelSupplyInfo.getFullHotelName());
        assertThat(pg.getAmenities()).isEqualTo(c3HotelSupplyInfo.getAmenities());
        assertThat(pg.getStarRating()).contains(c3HotelSupplyInfo.getStarRating());
        assertThat(pg.getNights()).isEqualTo(c3HotelSupplyInfo.getNights().toString());
        assertThat(pg.getRooms()).isEqualTo(c3HotelSupplyInfo.getRooms().toString());
        //CheckIn CheckOut dates
        String checkInDate = c3HotelSupplyInfo.getCheckInDate();
        String checkOutDate = c3HotelSupplyInfo.getCheckOutDate();
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE, MMM dd, yyyy").parse(checkInDate);
            checkInDate = new SimpleDateFormat("dd-MMM-yy").format(date);
            date = new SimpleDateFormat("EEE, MMM dd, yyyy").parse(checkOutDate);
            checkOutDate = new SimpleDateFormat("dd-MMM-yy").format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        assertThat(pg.getCheckInDate()).isEqualTo(checkInDate);
        assertThat(pg.getCheckOutDate()).isEqualTo(checkOutDate);
        //Total for customer
        assertThat(Double.valueOf(billingInfo.getTotalAmountText().replace("$", "")))
                .as("Comparing amounts with rounding issue handling.")
                .isEqualTo(Double.valueOf(hotwireProduct.getItineraryPage(getWebdriverInstance())
                        .getTotalCost().replace("$", "")) +
                        Double.valueOf(pg.getTripProtectionAmount().replace("$", "")), Delta.delta(0.1));
        //address
        String stateAbbr = c3HotelSupplyInfo.getHotelAddress().
                substring(c3HotelSupplyInfo.getHotelAddress().length() - 8).split(" ")[0];
        String address = c3HotelSupplyInfo.getHotelAddress().
                replace(stateAbbr, State.valueOfAbbreviation(stateAbbr).toString()).replaceAll(",", "");
        assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance())
                .getHotelAddress().replaceAll(",", "")).contains(address);
    }

    public void checkAirParametersForRecentSearch() {
        C3RecentSearchPage recentSearchPage = new C3RecentSearchPage(getWebdriverInstance());
        //actual from tripInfo
        String fromActual = tripInfo.getFromLocation();
        String toActual = tripInfo.getDestinationLocation();
        String tripDateToActual = new DateTime(new DateTime()).plusDays(tripInfo.getShiftStartDate())
                .toLocalDate().toString("d-MMM-yy");
        String tripDateBackActual = new DateTime(new DateTime()).plusDays(tripInfo.getShiftEndDate())
                .toLocalDate().toString("d-MMM-yy");
        Integer ticketsActual = tripInfo.getPassengers();

        // expected from UI
        String ref = getRefNumber();
        String fromExpected = recentSearchPage.getFromAirportBySearchID(ref);
        String toExpected = recentSearchPage.getToAirportBySearchID(ref);

        String tripDateFromExpected = recentSearchPage.getCheckInBySearchID(ref);
        String tripDateToExpected = recentSearchPage.getCheckOutBySearchID(ref);
        String ticketsExpected = recentSearchPage.getTicketsBySearchID(ref);
        //assertion
        assertThat(fromActual).as("not equals actual and expected").contains(fromExpected);
        assertThat(toActual).as("not equals actual and expected").contains(toExpected);

        assertThat(tripDateToActual).as("not equals actual and expected").isEqualTo(tripDateFromExpected);
        assertThat(tripDateBackActual).as("not equals actual and expected").isEqualTo(tripDateToExpected);

        assertThat(ticketsActual.toString()).as("not equals actual and expected").isEqualTo(ticketsExpected);
    }

    public void blockHotel() {
        new C3HotelBlockConfirmationPage(getWebdriverInstance()).blockHotelByCustomer("Amenities not available");
    }

    public void unblockHotel() {
        new C3HotelBlockConfirmationPage(getWebdriverInstance()).unblockHotelByCustomer("Hotel block made an error");
    }

    public void cancelHotelWhileBlocking() {
        new C3HotelBlockConfirmationPage(getWebdriverInstance()).cancelHotelByCustomer("Amenities not available");
    }

    public void cancelHotelWhileUnblocking() {
        new C3HotelBlockConfirmationPage(getWebdriverInstance()).cancelHotelByCustomer("Hotel block made an error");
    }

    public void verifyHotelIsBlockedInDB() {
        DateFormat formatterDate = new SimpleDateFormat("dd-MM-yy");
        C3SearchDao searchDao = new C3SearchDao(getDataBaseConnection());
        Map hotel = searchDao.getBlockedHotelByItinerary(c3ItineraryInfo.getItineraryNumber());

        Date blockActionDate = (Timestamp) hotel.get("BLOCK_ACTION_DATE");
        Date createDate = (Timestamp) hotel.get("UPDATE_DATE");

        assertThat(formatterDate.format(java.util.Calendar.getInstance().getTime()))
                .as("Block action date is correct")
                .isEqualTo(formatterDate.format(blockActionDate));

        assertThat(formatterDate.format(java.util.Calendar.getInstance().getTime()))
                .as("Update date is correct")
                .isEqualTo(formatterDate.format(createDate));

        assertThat(hotel.get("UNBLOCK_ACTION_DATE"))
                .as("Unblock action date is empty")
                .isNull();
    }

    public void verifyHotelIsUnBlockedInDB() {
        DateFormat formatterDate = new SimpleDateFormat("dd-MM-yy");
        C3SearchDao searchDao = new C3SearchDao(getDataBaseConnection());
        Map hotel = searchDao.getUnblockedHotelByItinerary(c3ItineraryInfo.getItineraryNumber());

        Date unblockActionDate = (Timestamp) hotel.get("UNBLOCK_ACTION_DATE");
        Date createDate = (Timestamp) hotel.get("UPDATE_DATE");

        assertThat(formatterDate.format(java.util.Calendar.getInstance().getTime()))
                .as("Unblock action date is correct")
                .isEqualTo(formatterDate.format(unblockActionDate));

        assertThat(formatterDate.format(java.util.Calendar.getInstance().getTime()))
                .as("Update date is correct")
                .isEqualTo(formatterDate.format(createDate));

        assertThat(hotel.get("BLOCK_ACTION_DATE"))
                .as("Block action date is empty")
                .isNull();
    }

    public void givenHotelPurchaseForBlocking() {
        switch (hotwireProduct.getProductVertical()) {
            case HOTEL:
                c3ItineraryInfo.setItineraryNumber(new C3SearchDao(getDataBaseConnection())
                        .getHotelItineraryForBlocking());
                break;
            default:
                unimplementedTest();
        }
    }

    public void verifyHotelInBlockedList() {
        C3BlockedHotelsPage blockedHotelsPage = new C3BlockedHotelsPage(getWebdriverInstance());
        assertThat(blockedHotelsPage.isHotelInBlockedList(c3ItineraryInfo.getItineraryNumber()))
                .as("Hotels blocked list contains " + c3ItineraryInfo.getItineraryNumber() + " itinerary")
                .isTrue();
    }

    public void verifyHotelDateOfBlock() {
        DateFormat formatterDate = new SimpleDateFormat("dd-MMM-yy");
        C3BlockedHotelsPage blockedHotelsPage = new C3BlockedHotelsPage(getWebdriverInstance());
        assertThat(blockedHotelsPage.getInformationOfBlockedHotel(c3ItineraryInfo.getItineraryNumber()))
                .as("Blocked hotel contains correct date of block")
                .containsIgnoringCase(formatterDate.format(java.util.Calendar.getInstance().getTime()));
    }

    public void unblockHotelFromFromBlockedHotelsPage() {
        C3BlockedHotelsPage blockedHotelsPage = new C3BlockedHotelsPage(getWebdriverInstance());
        blockedHotelsPage.clickUnblockHotel(c3ItineraryInfo.getItineraryNumber());
        new C3HotelBlockConfirmationPage(getWebdriverInstance()).unblockHotelByCustomer("Hotel block made an error");
    }


    public void verifyHotelIsAbsentInBlockedList() {
        String errorText = null;
        C3BlockedHotelsPage blockedHotelsPage = null;
        try {
            errorText =  getWebdriverInstance().findElement(By.id("errorMessaging")).getText();
            assertThat(errorText).as("Error message doesn't match").
                    contains("There are no hotel blockings associated with this customer.");
        }
        catch (Exception e) {
            blockedHotelsPage = new C3BlockedHotelsPage(getWebdriverInstance());
            assertThat(blockedHotelsPage.isHotelInBlockedList(c3ItineraryInfo.getItineraryNumber()))
                    .as("Hotels blocked list contains " + c3ItineraryInfo.getItineraryNumber() + " itinerary")
                    .isFalse();
        }
    }

    public void clickBlockHotelFromItineraryDetailsPage() {
        new C3HotelItineraryPage(getWebdriverInstance()).clickBlockHotel();
    }

    public void clickUnblockHotelFromItineraryDetailsPage() {
        new C3HotelItineraryPage(getWebdriverInstance()).clickUnblockHotel();
    }

    public void checkReferenceNumberCustomerValidationError() {
        assertThat(new C3CustomerMainPage(getWebdriverInstance()).getReferenceNumberError()).
                isEqualTo("This search was performed as a different customer. " +
                        "Please start a new search from this customer account or close " +
                        "out your case and enter the search reference number on the search for customer screen.");
    }

    public void checkReferenceNumberCorrectValidationError() {
        assertThat(new C3CustomerMainPage(getWebdriverInstance()).getReferenceNumberError()).
                isEqualTo("Please correct the Search Reference ID.");
    }

    public void verifyRecentC3SearchOnPage() {
        C3RecentSearchPage recentSearchPage = new C3RecentSearchPage(getWebdriverInstance());
        assertThat(recentSearchPage.getCsrStatusBySearchID(new C3SearchDao(getDataBaseConnection())
                .getSearchIdByEmail(customerInfo.getEmail())))
                .as("CSR search status is correct")
                .isEqualToIgnoringCase("Yes");
    }

    public void returnToC3HomePage() {
        getWebdriverInstance().switchTo().parentFrame();
        getWebdriverInstance().findElement(By.cssSelector(".homeItem")).click();

    }

    public void verifyAirDepartingSegments() {
        C3AirItineraryPage airItineraryPage = new C3AirItineraryPage(getWebdriverInstance());
        String headersOfDepartingSegments = airItineraryPage.getHeadersOfDepartingSegments();
        for (String expectedHeader : C3AirItineraryPage.EXPECTED_HEADERS_FOR_DEPARTING_SEGMENTS) {
            assertThat(headersOfDepartingSegments)
                    .as("Header '" + expectedHeader + "' doesn't exist")
                    .containsIgnoringCase(expectedHeader);

        }
    }

    public void verifyAirReturningSegments() {
        C3AirItineraryPage airItineraryPage = new C3AirItineraryPage(getWebdriverInstance());
        String headersOfDepartingSegments = airItineraryPage.getHeadersOfReturningSegments();
        for (String expectedHeader : C3AirItineraryPage.EXPECTED_HEADERS_FOR_DEPARTING_SEGMENTS) {
            assertThat(headersOfDepartingSegments)
                    .as("Header '" + expectedHeader + "' doesn't exist")
                    .containsIgnoringCase(expectedHeader);

        }
    }

    public void verifyContentOfAirDepartingSegments() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMM-yy");
        SimpleDateFormat formatTime = new SimpleDateFormat("h:mm a");
        C3AirItineraryPage airItineraryPage = new C3AirItineraryPage(getWebdriverInstance());
        Integer sizeOfTransferForDeparting = airItineraryPage.getDepartingFlightNumber().size();


//   assertThat(airItineraryPage.getDepartingCarrier()).isEqualToIgnoringCase(c3FlightInfo.getDepartingCarrier());
// assertThat(airItineraryPage.getDepartingFlightType()).isEqualToIgnoringCase(c3FlightInfo.getDepartingTypeOfFlight());

        for (int i = 0; i < sizeOfTransferForDeparting; i++) {
            assertThat(airItineraryPage.getDepartingFlightNumber().get(i))
                    .as("Flight number is incorrect for departing segment")
                    .isEqualToIgnoringCase(c3FlightInfo.getDepartingFlightNumber().get(i));
            assertThat(c3FlightInfo.getDepartingFromLocation().get(i))
                    .as("From location is incorrect for departing segment")
                    .containsIgnoringCase(airItineraryPage.getDepartingFromLocation().get(i));
            assertThat(c3FlightInfo.getDepartingToLocation().get(i))
                    .as("To location is incorrect for departing segment")
                    .containsIgnoringCase(airItineraryPage.getDepartingToLocation().get(i));

            assertThat(airItineraryPage.getDepartingStartDateAndTime().get(i))
                    .as("Start date and time is incorrect for departing segment")
                    .isEqualToIgnoringCase(formatDate.format(c3FlightInfo.getDepartingStartDateAndTime().get(i)) +
                            " at " + formatTime.format(c3FlightInfo.getDepartingStartDateAndTime().get(i)));
            assertThat(airItineraryPage.getDepartingEndDateAndTime().get(i))
                    .as("End date and time is incorrect for departing segment")
                    .isEqualToIgnoringCase(formatDate.format(c3FlightInfo.getDepartingEndDateAndTime().get(i)) +
                            " at " + formatTime.format(c3FlightInfo.getDepartingEndDateAndTime().get(i)));

            assertThat(airItineraryPage.getDepartingTotalTripTime().get(i).replace(" ", ""))
                    .as("Total trip time is incorrect for departing segment")
                    .isEqualToIgnoringCase(c3FlightInfo.getDepartingTotalTripTime().get(i).replaceAll("[ ()]", ""));


            assertThat(airItineraryPage.getDepartingAirlineRecordLocator().get(i))
                    .as("Airline record locator is incorrect for departing segment")
                    .isEqualToIgnoringCase(c3FlightInfo.getConfirmationNumber());
        }



    }

    public void verifyContentOfAirReturningSegments() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMM-yy");
        SimpleDateFormat formatTime = new SimpleDateFormat("h:mm a");
        C3AirItineraryPage airItineraryPage = new C3AirItineraryPage(getWebdriverInstance());
        Integer sizeOfTransferForReturning = airItineraryPage.getReturningFlightNumber().size();

//   assertThat(airItineraryPage.getReturningCarrier()).isEqualToIgnoringCase(c3FlightInfo.getReturningCarrier());
// assertThat(airItineraryPage.getReturningFlightType()).isEqualToIgnoringCase(c3FlightInfo.getReturningTypeOfFlight());

        for (int i = 0; i < sizeOfTransferForReturning; i++) {
            assertThat(airItineraryPage.getReturningFlightNumber().get(i))
                    .as("Flight number is incorrect for returning segment")
                    .isEqualToIgnoringCase(c3FlightInfo.getReturningFlightNumber().get(i));
            assertThat(c3FlightInfo.getReturningFromLocation().get(i))
                    .as("From location is incorrect for returning segment")
                    .containsIgnoringCase(airItineraryPage.getReturningFromLocation().get(i));
            assertThat(c3FlightInfo.getReturningToLocation().get(i))
                    .as("To location is incorrect for returning segment")
                    .containsIgnoringCase(airItineraryPage.getReturningToLocation().get(i));

            assertThat(airItineraryPage.getReturningStartDateAndTime().get(i))
                    .as("Start date and time is incorrect for returning segment")
                    .isEqualToIgnoringCase(formatDate.format(c3FlightInfo.getReturningStartDateAndTime().get(i)) +
                            " at " + formatTime.format(c3FlightInfo.getReturningStartDateAndTime().get(i)));
            assertThat(airItineraryPage.getReturningEndDateAndTime().get(i))
                    .as("End date and time is incorrect for returning segment")
                    .isEqualToIgnoringCase(formatDate.format(c3FlightInfo.getReturningEndDateAndTime().get(i)) +
                            " at " + formatTime.format(c3FlightInfo.getReturningEndDateAndTime().get(i)));

            assertThat(airItineraryPage.getReturningTotalTripTime().get(i).replace(" ", ""))
                    .as("Total trip time is incorrect for returning segment")
                    .isEqualToIgnoringCase(c3FlightInfo.getReturningTotalTripTime().get(i).replaceAll("[ ()]", ""));
            assertThat(airItineraryPage.getReturningAirlineRecordLocator().get(i))
                    .as("Airline record locator is incorrect for returning segment")
                    .isEqualToIgnoringCase(c3FlightInfo.getConfirmationNumber());
        }
    }

    public void verifyNecessaryFieldsOnAirItineraryDetailsPage() {
        Integer numOfDepSegments = c3FlightInfo.getDepartingStartDateAndTime().size() - 1;
        Integer numOfRetSegments = c3FlightInfo.getReturningStartDateAndTime().size() - 1;


        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
        String airportCodeFromLocationDeparting = c3FlightInfo
                .getDepartingFromLocation().get(0).split("[(]")[1].split("[)]")[0];
        String airportCodeToLocationDeparting = c3FlightInfo
                .getDepartingToLocation().get(numOfDepSegments).split("[(]")[1].split("[)]")[0];
        String airportCodeFromLocationReturning = c3FlightInfo
                .getReturningFromLocation().get(0).split("[(]")[1].split("[)]")[0];
        String airportCodeToLocationReturning = c3FlightInfo
                .getReturningToLocation().get(numOfRetSegments).split("[(]")[1].split("[)]")[0];

        C3AirItineraryPage airItineraryPage = new C3AirItineraryPage(getWebdriverInstance());

        assertThat(airItineraryPage.getItineraryNumber())
                .as("Itinerary number is incorrect").isEqualToIgnoringCase(c3ItineraryInfo.getItineraryNumber());

        for (String airTicketNumber : c3FlightInfo.getAirTicketsNumbers()) {
            assertThat(airItineraryPage.getAirTicketNumbers())
                    .as("Number of ticket is incorrect").containsIgnoringCase(airTicketNumber);
        }

        for (String nameOfPassengers : c3FlightInfo.getNamesOfPassengers()) {
            assertThat(airItineraryPage.getNamesOfPassengers())
                    .as("Name of the passenger is incorrect").containsIgnoringCase(nameOfPassengers);
        }

        assertThat(airItineraryPage.getNumberOfPassengers())
                .as("Number of passengers is incorrect")
                .isEqualToIgnoringCase(c3FlightInfo.getNumberOfTickets());
        assertThat(airItineraryPage.getNumberOfTickets())
                .as("Number of tickets is incorrect")
                .isEqualToIgnoringCase(c3FlightInfo.getNumberOfTickets());

        assertThat(airItineraryPage.getDepartLocationFromItinerarySection().replaceAll("\n", ""))
                .as("Depart field is incorrect")
                .isEqualTo(format.format(c3FlightInfo.getDepartingStartDateAndTime().get(0)) +
                        airportCodeFromLocationDeparting + "/" + airportCodeToLocationDeparting);

        assertThat(airItineraryPage.getReturnLocationFromItinerarySection().replaceAll("\n", ""))
                .as("Return field is incorrect")
                .isEqualTo(format.format(c3FlightInfo.getReturningEndDateAndTime().get(numOfRetSegments)) +
                        airportCodeFromLocationReturning + "/" + airportCodeToLocationReturning);

        for (int i = 0; i <= numOfDepSegments; i++) {
            assertThat(airItineraryPage.getPlatingCarrier())
                    .containsIgnoringCase(c3FlightInfo.getDepartingCarrier().get(i));
        }

        for (int i = 0; i <= numOfRetSegments; i++) {
            assertThat(airItineraryPage.getPlatingCarrier())
                    .as("Plating carrier is incorrect")
                    .containsIgnoringCase(c3FlightInfo.getReturningCarrier().get(i));
        }

        assertThat(airItineraryPage.getBookingType())
                .as("Booking type is incorrect")
                .containsIgnoringCase("Air- " + hotwireProduct.getOpacity().getRateType());
        assertThat(airItineraryPage.getTransactionDate())
                .as("Transaction date is incorrect").containsIgnoringCase(format.format(new Date()));
        assertThat(airItineraryPage.getDeviceType())
                .as("Device type is incorrect").containsIgnoringCase("Other");
        assertThat(airItineraryPage.getApplicationType())
                .as("Application type is incorrect").containsIgnoringCase("Desktop");
        assertThat(airItineraryPage.getLoggedIn1())
                .as("Logged1 is incorrect").containsIgnoringCase("Yes");

        assertThat(airItineraryPage.getTripProtection().toLowerCase()
                .contains(tripInfo.getTripInsurance() ? "yes" : "no")).as("Trip protection is incorrect").isTrue();
        assertThat(airItineraryPage.getLoggedIn2())
                .as("Logged2 is incorrect").containsIgnoringCase("Yes");
        assertThat(airItineraryPage.getRentalCarProtection().toLowerCase()
                .contains(tripInfo.getCarInsurance() ? "yes" : "no")).as("Car trip protection is incorrect").isTrue();

        assertThat(airItineraryPage.getSearchedBookedByCSR())
                .as("Booked by CSR is incorrect").isEqualToIgnoringCase("Y/Y");
        assertThat(airItineraryPage.getBilledBy())
                .as("Billed by is incorrect").isEqualToIgnoringCase(hotwireProduct.getOpacity().getBilledBy());
        assertThat(airItineraryPage.getOutBoundConnection())
                .as("Outbound connections is incorrect").isEqualTo(numOfDepSegments.toString());
        assertThat(airItineraryPage.getInBoundConnection())
                .as("Inbound connections is incorrect").isEqualTo(numOfRetSegments.toString());

        Double totalCostFromAirItinerary;
        totalCostFromAirItinerary = Double.parseDouble(airItineraryPage.getTotalCost().replaceAll("[$,]", ""));
        if (null != airItineraryPage.getTripProtectionBookedDetails()) {
            totalCostFromAirItinerary += Double.parseDouble(airItineraryPage.getTripProtectionBookedDetails()
                    .replaceAll("[$,]", ""));
        }
        assertThat(airItineraryPage.getTotalCost())
                .as("Currency code for total cost is incorrect").startsWith(c3ItineraryInfo.getCurrencyCode());

        assertThat(totalCostFromAirItinerary)
                .as("Total cost value is incorrect").isEqualTo(c3ItineraryInfo.getTotalCost());
    }

    public void openPurchaseDetailsByItinerary(String itinerary) {
        getWebdriverInstance().switchTo().defaultContent();
        getWebdriverInstance().switchTo().frame("c3Frame");
        new C3PastBookingPage(getWebdriverInstance()).choosePurchaseByItinerary(itinerary);
    }

    public void verifyAirSearchPurchaseStatus(String searched, String booked) {
        assertThat(new C3AirItineraryPage(getWebdriverInstance()).getSearchedBookedByCSR())
                .as("Booked by CSR is incorrect").isEqualTo(searched + "/" + booked);
    }

    public void verifyTemplateOfPaymentDetailsPage(String table) {
        C3PaymentDetailsFragment paymentDetailsFragment = new C3PaymentDetailsFragment(getWebdriverInstance());
        String rootWindowHandler = getWebdriverInstance().getWindowHandle();

        if ("full authorization".equals(table)) {
            paymentDetailsFragment.getFullAuthorizationTable().clickDetailsLink();
        }
        else if ("bill".equals(table)) {
            paymentDetailsFragment.getBillTable().clickDetailsLink();
        }



        for (String h : getWebdriverInstance().getWindowHandles()) {
            if (!h.equals(rootWindowHandler) && !h.isEmpty()) {

                C3PaymentDetailsPage  paymentDetailsPage =
                        new C3PaymentDetailsPage(getWebdriverInstance().switchTo().window(h));

                paymentDetailsPage.verifyTextOnPage("Customer Email");
                paymentDetailsPage.verifyTextOnPage("HW Itinerary");
                paymentDetailsPage.verifyTextOnPage("Payment Method");
                paymentDetailsPage.verifyTextOnPage("Transaction Date");
                paymentDetailsPage.verifyTextOnPage("Amount");
                paymentDetailsPage.verifyTextOnPage("Event");
                paymentDetailsPage.verifyTextOnPage("Event Status");
                paymentDetailsPage.verifyTextOnPage("Payment Gateway/Payment Processor");
                paymentDetailsPage.verifyTextOnPage("Payment Gateway Error Message");
                paymentDetailsPage.verifyTextOnPage("Request Number");
                paymentDetailsPage.verifyTextOnPage("Authorization Code");
                paymentDetailsPage.verifyTextOnPage("Bank Error Message");
                paymentDetailsPage.verifyTextOnPage("Address Verification Response Code");
                paymentDetailsPage.verifyTextOnPage("Card Presence Verification Response Code");
                paymentDetailsPage.verifyTextOnPage("VbV/SecureCode Response Code");
                try {
                    paymentDetailsPage.verifyTextOnPage("Billed by");
                }
                catch (AssertionError e) {
                    paymentDetailsPage.verifyTextOnPage("Auth by");
                }
                paymentDetailsPage.verifyTextOnPage("VbV/SecureCode ECI Codes");
                paymentDetailsPage.verifyTextOnPage("Fraud Score");
                paymentDetailsPage.verifyTextOnPage("Fraud Score Transaction Status");

                getWebdriverInstance().switchTo().window(h).close();
            }
        }
        getWebdriverInstance().switchTo().window(rootWindowHandler);
        switchToFrame("c3Frame");
    }
}

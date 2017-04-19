/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.reservations;

import com.hotwire.selenium.tools.c3.hotel.C3HotelSupplierSearchResults;
import com.hotwire.selenium.tools.c3.hotelCreditCard.C3AdjustHotelCreditCardAccount;
import com.hotwire.selenium.tools.c3.hotelCreditCard.C3hotelCTAInformationPage;
import com.hotwire.selenium.tools.c3.hotelCreditCard.C3hotelInformationSearchForm;
import com.hotwire.selenium.tools.c3.hotelCreditCard.C3hotelReservationInformationPage;
import com.hotwire.selenium.tools.c3.hotelCreditCard.C3hotelReservationSearchResultsPage;
import com.hotwire.selenium.tools.c3.hotelCreditCard.C3hotelSupplierInformationPage;
import com.hotwire.selenium.tools.c3.mailRebate.C3mailInRebateSearchForm;
import com.hotwire.selenium.tools.c3.riskManagement.C3AirTransactionsForReviewPage;
import com.hotwire.selenium.tools.c3.riskManagement.C3CarTransactionsForReviewPage;
import com.hotwire.selenium.tools.c3.riskManagement.C3ClearOldTransactionsSearchForm;
import com.hotwire.selenium.tools.c3.riskManagement.C3FraudWatchListSearchForm;
import com.hotwire.selenium.tools.c3.riskManagement.C3FraudWatchListSearchResults;
import com.hotwire.selenium.tools.c3.riskManagement.C3HotelReservationInfoPage;
import com.hotwire.selenium.tools.c3.riskManagement.C3HotelTransactionsForReviewPage;
import com.hotwire.selenium.tools.c3.riskManagement.C3ReservationSearchResultsPage;
import com.hotwire.selenium.tools.c3.riskManagement.C3RiskManagementAdminPage;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.c3.C3HotelSupplyInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Definition of Risk Management Admin for C3 reservation
 */
public class C3FinanceModel extends ToolsAbstractModel {

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private C3HotelSupplyInfo c3HotelSupplyInfo;

    public void selectReservationVertical(String vertical) {
        new C3RiskManagementAdminPage(getWebdriverInstance()).selectVertical(vertical);
    }

    public void searchReservationSearchEmail(String email) {
        new C3RiskManagementAdminPage(getWebdriverInstance()).searchReservationByEmail(email);
    }


    public void selectReservationByNumFromSearchTable(Integer number) {
        C3ReservationSearchResultsPage resultsPage =
                new C3ReservationSearchResultsPage(getWebdriverInstance());
        c3ItineraryInfo.setItineraryNumber(resultsPage.getItineraryByNum(number));
        resultsPage.selectItineraryByNum(number);
    }


    public void createCaseNote() {
        String randCaseNote = "test_note_" + System.currentTimeMillis();
        c3ItineraryInfo.setCaseNotesNumber("1");
        c3ItineraryInfo.setCaseNotesText(randCaseNote);
        C3HotelReservationInfoPage rezervInfo = new C3HotelReservationInfoPage(getWebdriverInstance());
        rezervInfo.createNote(randCaseNote);
        verifyCreatedCaseNote();
    }

    public void verifyCreatedCaseNote() {
        C3HotelReservationInfoPage rezervInfo = new C3HotelReservationInfoPage(getWebdriverInstance());
        rezervInfo.verifyCaseNoteByText(c3ItineraryInfo.getCaseNotesText());
    }

    public void searchReservationSearchItinerary() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByItinerary(c3ItineraryInfo.getItineraryNumber());
    }

    public void searchReservationSearchCCNumber() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByCcNumber(billingInfo.getCcNumber());
    }

    public void searchReservationSearchPhoneNumber() {
        String[] phone  = customerInfo.getPhoneNumber().split("[|]");
        new C3RiskManagementAdminPage(getWebdriverInstance()).searchReservationByPhoneNumber(phone[1], phone[2]);
    }

    public void searchReservationSearchPassword() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByPassword("hotwire");
    }

    public void searchReservationSearchCCNumberBin() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByCcNumberBin("411111");
    }

    public void searchReservationSearchPassengerLastName() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByPassengerLastName(customerInfo.getLastName());

    }

    public void searchReservationSearchPaymentHolderLastName() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByPaymentHolderLastName(billingInfo.getCcLastName());
    }

    public void searchReservationSearchCustomerLastName() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByCustomerLastName(customerInfo.getLastName());
    }

    public void addFraudRecord() {
        new C3FraudWatchListSearchForm(getWebdriverInstance()).
                addFraudWatchReecord(customerInfo.getEmail(), "other", "unconfirmed");
    }

    public void searchFraudRecord() {
        new C3FraudWatchListSearchForm(getWebdriverInstance()).
                searchFraudWatchReecord(customerInfo.getEmail());
    }

    public void deactivateFraudAccount() {
        new C3FraudWatchListSearchResults(getWebdriverInstance()).
                    deactivateAccount().verifyTextOnPage("The selected entries were successfully deactivated.");
    }

    public void isDeactivatedFraudAccount() {
        assertThat(new C3FraudWatchListSearchResults(getWebdriverInstance()).
                isActiveAccount()).as("Fraud account still active").isFalse();
    }

    public void selectNextAirTransactionsForReview(Integer count) {
        new C3AirTransactionsForReviewPage(getWebdriverInstance()).
                selectNextTransactionsForReview(count);
    }

    public void selectNextHotelTransactionsForReview(Integer count) {
        new C3HotelTransactionsForReviewPage(getWebdriverInstance()).
                selectNextTransactionsForReview(count);
    }

    public void selectNextCarTransactionsForReview(Integer count) {
        new C3CarTransactionsForReviewPage(getWebdriverInstance()).
                selectNextTransactionsForReview(count);
    }

    public void searchReservationSearchIPAddress() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByIPAddress(customerInfo.getIpAddress());
    }

    public void searchReservationSearchOrderID() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                searchReservationByOrderID(c3ItineraryInfo.getPurchaseOrderId());
    }

    public void checkOrderIDInfoOnPage() {
        C3HotelReservationInfoPage rezervInfo = new C3HotelReservationInfoPage(getWebdriverInstance());
        rezervInfo.verifyTextOnPage(c3ItineraryInfo.getItineraryNumber());
        rezervInfo.verifyTextOnPage(customerInfo.getEmail());
    }

    public void clearOldTransactions() {
        new C3ClearOldTransactionsSearchForm(getWebdriverInstance()).
                clickClearOldTransactionButton();
    }

    public void clearOldTransactions(int daysBefore, Integer amount) {
        new C3ClearOldTransactionsSearchForm(getWebdriverInstance()).
                clearOldTransactions(daysBefore, amount);
    }

    public void addRecordToMailInRebate(String statusCode) {
        new C3mailInRebateSearchForm(getWebdriverInstance()).
                addRecordToMainInRebate(c3ItineraryInfo.getItineraryNumber(),
                        "80", "215", statusCode);
    }

    public void searchHotelInformationByConfirmationNum() {
        new C3hotelInformationSearchForm(getWebdriverInstance()).
                searchHotelInformationByConfirmationNum(c3ItineraryInfo.getItineraryNumber());
    }

    public void checkHotelTotalCharge() {
        new C3hotelReservationInformationPage(getWebdriverInstance()).
                clickTotalCharges();
        verifyTextOnPopupPage("Charges associated with Itinerary " +
                c3ItineraryInfo.getItineraryNumber());
    }

    public void searchHotelInformationByReservationNum() {
        new C3hotelInformationSearchForm(getWebdriverInstance()).
                searchHotelInformationByReservationNum(c3ItineraryInfo.getReservationId());
    }

    public void checkHotelReservationInfo() {
        String itinerary = c3ItineraryInfo.getItineraryNumber();
        String reservationNum =  c3ItineraryInfo.getReservationId();
        if (reservationNum == null) {
            reservationNum =  c3ItineraryInfo.getReservationNumByItinerary();
        }
        new C3hotelReservationInformationPage(getWebdriverInstance());
        assertThat(verifyTextOnPageBoolean(itinerary)).as("Itinerary " + itinerary +
                " is not found on HotelReservationInfo page").isTrue();
        assertThat(verifyTextOnPageBoolean(reservationNum)).as("Reservation number '" + reservationNum +
                "' is not found on HotelReservationInfo page").isTrue();
    }

    public void searchHotelInformationByCTA_Account() {
        new C3hotelInformationSearchForm(getWebdriverInstance()).
                searchHotelInformationByCTA_Account("03300");
    }

    public void checkCTAAccountInformation() {
        new C3hotelCTAInformationPage(getWebdriverInstance());
        verifyTextOnPage("CTA03300");
    }

    public void searchHotelInformationByGuestNameAndDates() {
        String name = "Hotwire";
        c3HotelSupplyInfo.setHotelGuestName(name);
        new C3hotelInformationSearchForm(getWebdriverInstance()).
                searchHotelInformationByNameAndDates(name);
    }

    public void selectHotelReservationSearchResult() {
        C3hotelReservationSearchResultsPage results =
                new C3hotelReservationSearchResultsPage(getWebdriverInstance());
        c3ItineraryInfo.setItineraryNumber(results.getItineraryByNum(1));
        results.selectSearchResultByNum(1);
    }

    public void modifyCreditCard() {
        new C3hotelReservationInformationPage(getWebdriverInstance()).modifyCreditCard();
        C3AdjustHotelCreditCardAccount pg = new C3AdjustHotelCreditCardAccount(getWebdriverInstance());
        List<String> expectedFileds = new ArrayList<String>();
        expectedFileds.add("Adjust Hotel Credit Card Account");
        expectedFileds.add("Hotel Reservation Details");
        expectedFileds.add("Credit Card Details for Hotel reservation");
        expectedFileds.add("Update CTA Account");
        expectedFileds.add("Credit Limit");
        expectedFileds.add("Begining Date");
        expectedFileds.add("# of Transactions Allowed");
        expectedFileds.add("Reason");
        expectedFileds.add("Finance Department Notes");
        expectedFileds.add("MCC group");
        expectedFileds.add("Ending Date");
        for (int i = 0; i < expectedFileds.size(); i++) {
            assertThat(verifyTextOnPageBoolean(expectedFileds.get(i)))
                    .as(expectedFileds.get(i)).isTrue();
        }
        assertThat(pg.getSendUpdateButtonText()).isEqualTo("Send Update Request to Wright Express");
        assertThat(pg.getViewSearchButtonText()).isEqualTo("View Search Results");
    }

    public void updateTaxRate() {
        String hotelID = c3HotelSupplyInfo.getHotelID(c3ItineraryInfo.getItineraryNumber());
        new C3hotelReservationInformationPage(getWebdriverInstance()).updateTaxRate();
        new C3hotelSupplierInformationPage(getWebdriverInstance());
        assertThat(verifyTextOnPageBoolean(hotelID)).as("Hotel ID -'" + hotelID +
                "' is not found on C3 hotel Supplier Information Page").isTrue();
    }

    public void checkMCCGroupPopup() {
        new C3hotelReservationInformationPage(getWebdriverInstance()).
                clickMCCGroupLink();
        verifyTextOnPopupPage("Charges associated with Itinerary " +
                c3ItineraryInfo.getItineraryNumber());
    }

    public void searchHotelInformationByHotelNameAndState(String hotelName, String state) {
        c3HotelSupplyInfo.setFullHotelName(hotelName);
        c3HotelSupplyInfo.setHotelState(state);
        new C3hotelInformationSearchForm(getWebdriverInstance()).
                searchHotelInformationByHotelNameAndState(hotelName, state);
    }

    public void checkSearchResultsToHotelNameAndState() {
        C3HotelSupplierSearchResults results = new C3HotelSupplierSearchResults(getWebdriverInstance());
        Integer size = results.getHotelNamesList().size();
        List<String> names = results.getHotelNamesList();
        List<String> states = results.getHotelStateList();
        String expectedName = c3HotelSupplyInfo.getFullHotelName();
        String expectedSate = c3HotelSupplyInfo.getHotelState();
        for (int i = 0; i < size; i++) {
            String name = names.get(i);
            String state = states.get(i);
            assertThat(name.contains(expectedName)).
                    as("Hotel name contains '" + name + "' instead '" + expectedName + "'").isTrue();
            assertThat(state.contains(expectedSate)).
                    as("Hotel state contains '" + state + "' instead '" + expectedSate + "'").isTrue();
        }
    }

    public void changeFaxInfo() {
        new C3hotelReservationInformationPage(getWebdriverInstance()).
                changeFaxInfo("Test Booking", "(555) 000-0000");
        assertThat(verifyTextOnPageBoolean("The fax was queued for delivery to number (555) 000-0000."))
                .as("Expected 'The fax was queued for delivery to number (555) 000-0000.'" +
                        " not found on hotel details").isTrue();
    }


    public void verifyActionButtons() {
        C3HotelReservationInfoPage rezervInfo = new C3HotelReservationInfoPage(getWebdriverInstance());
        rezervInfo.verifyActionsButtons();
    }

    public void checkHotelReservationInfoForGuest() {
        String guestName = new C3hotelReservationInformationPage(getWebdriverInstance()).getGuestName();
        assertThat(guestName).as("Guest name incorrect").contains(c3HotelSupplyInfo.getHotelGuestName());
    }
}

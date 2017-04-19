/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.reservations;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.testing.UnimplementedTestException;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * User: v-vyulun
 * Date: 06/25/14
 * Time: 9:27 AM
 */

public class C3ReservationSteps extends ToolsAbstractSteps {

    @Autowired
    @Qualifier("financeModel")
    private C3FinanceModel financeModel;

    @Autowired
    private CustomerInfo customerInfo;


    @Given("^I make search for reservations for (all|car|air|hotel) vertical" +
            " via (email|itinerary|credit card number|phone number|password|" +
            "credit card bin|passenger last name|payment holder last name|" +
            "customer last name|IP address|orderID)$")
    public void makeSearchByPhoneNumber(String vertical, String by) {
        financeModel.selectReservationVertical(vertical);
        switch (by) {
            case "email":
                financeModel.searchReservationSearchEmail(customerInfo.getEmail());
                break;
            case "itinerary":
                financeModel.searchReservationSearchItinerary();
                break;
            case "credit card number":
                financeModel.searchReservationSearchCCNumber();
                break;
            case "phone number":
                financeModel.searchReservationSearchPhoneNumber();
                break;
            case "password":
                financeModel.searchReservationSearchPassword();
                break;
            case "credit card bin":
                financeModel.searchReservationSearchCCNumberBin();
                break;
            case "passenger last name":
                financeModel.searchReservationSearchPassengerLastName();
                break;
            case "payment holder last name":
                financeModel.searchReservationSearchPaymentHolderLastName();
                break;
            case "customer last name":
                financeModel.searchReservationSearchCustomerLastName();
                break;
            case "IP address":
                financeModel.searchReservationSearchIPAddress();
                break;
            case "orderID":
                financeModel.searchReservationSearchOrderID();
                break;
            default:
                throw new UnimplementedTestException("No such search type!");

        }
    }

    @Given("^I choose reservation from search list$")
    public void chooseReservationByNum() {
        financeModel.selectReservationByNumFromSearchTable(1);
    }

    @Given("^I see according orderID info on page$")
    public void checkOrderIDInfoOnPage() {
        financeModel.checkOrderIDInfoOnPage();
    }

    @Given("^I create new test Case Note$")
    public void createCaseNote() {
        financeModel.createCaseNote();
    }

    @Given("^I see action buttons at the bottom of Itinerary$")
    public void verifyActionButtons() {
        financeModel.verifyActionButtons();
    }

    @Given("^Add record to fraud watch list$")
    public void addFraudRecord() {
        financeModel.addFraudRecord();
    }

    @Given("^I choose Clear Old Transactions without parameters$")
    public void clearOldTransactionsWOParameters() {
        financeModel.clearOldTransactions();
    }

    @Given("^Search email in fraud watch list$")
    public void searchFraudRecord() {
        financeModel.searchFraudRecord();
    }

    @Given("^I deactivate fraud account$")
    public void activationFraudAccount() {
        financeModel.deactivateFraudAccount();
    }

    @Given("^I see fraud account deactivated$")
    public void checkFraudAccountDeactivated() {
        financeModel.isDeactivatedFraudAccount();
    }

    @Given("^I choose (\\d+) (air|hotel|car) transaction for review$")
    public void checkFraudAccountDeactivated(Integer count, String vertical) {
        switch (vertical) {
            case "air":
                financeModel.selectNextAirTransactionsForReview(count);
                break;
            case "hotel":
                financeModel.selectNextHotelTransactionsForReview(count);
                break;
            case "car":
                financeModel.selectNextCarTransactionsForReview(count);
                break;
            default:
                throw new UnimplementedTestException("No such search type!");
        }
    }

    @Given("^I clear all transactions before (\\d+) days and under (\\d+) amount$")
    public void clearOldTransactions(int daysBefore, Integer amount) {
        financeModel.clearOldTransactions(daysBefore, amount);
    }

    @Given("^I try to add Mail-In Rebate record with (A|D|R) status code$")
    public void addRecordToMailInRebate(String statusCode) {
        financeModel.addRecordToMailInRebate(statusCode);
    }

    @Given("^I try to search hotel information by confirmation number$")
    public void searchHotelInformationByConfirmationNum() {
        financeModel.searchHotelInformationByConfirmationNum();
    }

    @Given("^I try to search hotel information by reservation number$")
    public void searchHotelInformationByReservationNum() {
        financeModel.searchHotelInformationByReservationNum();
    }

    @Given("^I try to search hotel information by guest name and dates$")
    public void searchHotelInformationByGuestNameAndDates() {
        financeModel.searchHotelInformationByGuestNameAndDates();
    }

    @Given("^I try to search hotel information by hotel name and state$")
    public void searchHotelInformationByHotelNameAndState() {
        financeModel.searchHotelInformationByHotelNameAndState("Hilton", "California");
    }

    @Given("^I try to search hotel information by international supplier$")
    public void searchHotelInformationByInternationalSupplier() {
        financeModel.searchHotelInformationByHotelNameAndState("Hilton", "British Columbia");
    }


    @Given("^I try to search hotel information by CTA account$")
    public void searchHotelInformationByCTA_Account() {
        financeModel.searchHotelInformationByCTA_Account();
    }

    @Given("^I check hotel Reservation Information$")
    public void checkHotelReservationInfo() {
        financeModel.checkHotelReservationInfo();
    }

    @Given("^I check hotel Reservation Information for guest$")
    public void checkHotelReservationInfoForGuest() {
        financeModel.checkHotelReservationInfoForGuest();
    }

    @Given("^I see results for according hotel in expected state$")
    public void checkSearchResultsToHotelNameAndState() {
        financeModel.checkSearchResultsToHotelNameAndState();
    }

    @Given("^I check hotel CTA account information$")
    public void checkHotelCTAAccountInfo() {
        financeModel.checkCTAAccountInformation();
    }

    @Given("^I check total charge on hotel Reservation Information$")
    public void checkHotelTotalCharge() {
        financeModel.checkHotelTotalCharge();
    }

    @Given("^I check Hotel MCC Group Popup on hotel Reservation Information$")
    public void checkMCCGroupPopup() {
        financeModel.checkMCCGroupPopup();
    }

    @Given("^I select hotel reservation search from results$")
    public void selectHotelReservationSearchResult() {
        financeModel.selectHotelReservationSearchResult();
    }

    @Given("^I could to modify credit card$")
    public void modifyCreditCard() {
        financeModel.modifyCreditCard();
    }

    @Given("^I could to update tax rate$")
    public void updateTaxRate() {
        financeModel.updateTaxRate();
    }

    @Given("^I could to change fax information$")
    public void changeFaxInfo() {
        financeModel.changeFaxInfo();
    }

}


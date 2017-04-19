/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.purchase;

import com.hotwire.selenium.tools.c3.purchase.C3FareFinder;
import com.hotwire.selenium.tools.c3.purchase.air.C3AirConfirmationPage;
import com.hotwire.selenium.tools.c3.purchase.air.C3AirDetailsPage;
import com.hotwire.selenium.tools.c3.purchase.air.C3AirResultsPage;
import com.hotwire.selenium.tools.c3.purchase.air.C3AirSearchFragment;
import com.hotwire.selenium.tools.c3.purchase.air.billing.C3AirBillingReviewFragment;
import com.hotwire.selenium.tools.c3.purchase.air.billing.C3AirMultiPassengerInfoFragment;
import com.hotwire.selenium.tools.c3.purchase.air.billing.C3AirPaymentInfoFragment;
import com.hotwire.selenium.tools.c3.purchase.air.billing.C3InsuranceFragment;
import com.hotwire.selenium.tools.c3.purchase.car.C3CarAddonPage;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.TripInfo;
import com.hotwire.test.steps.tools.bean.c3.C3FlightInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.testing.UnimplementedTestException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Air purchase model for web app
 */

public class C3AirPurchaseModel extends C3PurchaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3AirPurchaseModel.class);

    @Autowired
    private TripInfo tripInfo;

    @Autowired
    private C3FlightInfo c3FlightInfo;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Override
    public void fillSearchParametersAndSubmit() {
        C3AirSearchFragment searchFragment = new C3FareFinder(getWebdriverInstance()).chooseAir();
        searchFragment.setDates(tripInfo.getStartDate(), tripInfo.getEndDate());
        searchFragment.setDestinations(tripInfo.getFromLocation(), tripInfo.getDestinationLocation());
        searchFragment.setPassengers(tripInfo.getPassengers());
        searchFragment.submit();
    }

    @Override
    void switchToRetail() {
        unimplementedTest();
    }

    @Override
    void verifyResultsPage() {
        new C3AirResultsPage(getWebdriverInstance()).verifyResults();
    }

    @Override
    public void verifyDetailsPage() {
        new C3AirDetailsPage(getWebdriverInstance());
    }

    @Override
    public void waitForResults() {
        waitPolling("div.mask");
    }

    @Override
    public void checkStaticInsurance(boolean availability) {
        unimplementedTest();
    }

    @Override
    public void verifyStaticInsurance() {
        unimplementedTest();
    }

    @Override
    public void addRentalCarOnDetail() {
        C3CarAddonPage pg = new C3CarAddonPage(getWebdriverInstance());
        pg.addRentalCar();
        pg.bookNow();
    }

    @Override
    public String getReferenceNumberFromResultsPage() {
        return new C3AirResultsPage(getWebdriverInstance()).getReferenceNumber();
    }

    @Override
    public void verifyValidationMsgOnBilling(String msg) {
        unimplementedTest();
    }

    @Override
    public void verifyPrefillingOnBillingPage() {
        unimplementedTest();
    }

    @Override
    public void receiveConfirmation() {
        C3AirConfirmationPage confPage = new C3AirConfirmationPage(getWebdriverInstance());
        c3ItineraryInfo.setItineraryNumber(confPage.getItineraryNumber());
    }

    @Override
    public void proceedDetailsPage() {
        //This stuff is needed because of blinking windows on our environments
        //Because of these windows webdriver lose the frame
        try {
            getWebdriverInstance().switchTo().frame("c3Frame");
            getWebdriverInstance().switchTo().frame("iframeSearch");
        }
        catch (NoSuchFrameException e) {
            LOGGER.info("No extra windows");
        }
        new C3AirDetailsPage(getWebdriverInstance()).bookNow();
    }

    @Override
    public void fillAllBillingInfo() {
        fillPassengers();
        setInsurance(tripInfo.getTripInsurance(), tripInfo.getCarInsurance());
        if (billingInfo.isChangedCardFlag()) {
            fillCCPaymentInfo();
        }
        else {
            fillPaymentInfo();
        }
    }

    @Override
    public void fillAllBillingInfoWithCarAddOn() {
        tripInfo.setCarInsurance(true);
        fillPassengersWithCarAddOn();
        setInsurance(tripInfo.getTripInsurance(), tripInfo.getCarInsurance());
        if (billingInfo.isChangedCardFlag()) {
            fillCCPaymentInfo();
        }
        else {
            fillPaymentInfo();
        }
    }

    private void fillPassengers() {
        LOGGER.info("Filling passengers info");
        if (customerInfo.isGuest()) {
            new C3AirMultiPassengerInfoFragment(getWebdriverInstance())
                    .fillGuestName()
                    .fillBirthDay()
                    .fillGender()
                    .fillPhone(customerInfo.getPhoneNumber())
                    .fillEmails(customerInfo.getEmail())
                    .proceed();
        }
        else {
            new C3AirMultiPassengerInfoFragment(getWebdriverInstance())
                    .fillCustomerName()
                    .fillBirthDay()
                    .fillGender()
                    .fillPhone(customerInfo.getPhoneNumber())
                    .proceed();
        }
    }

    private void fillPassengersWithCarAddOn() {
        if (customerInfo.isGuest()) {
            new C3AirMultiPassengerInfoFragment(getWebdriverInstance())
                    .fillGuestName()
                    .fillBirthDay()
                    .fillGender()
                    .fillPhone(customerInfo.getPhoneNumber())
                    .fillEmails(customerInfo.getEmail())
                    .fillDriverAge()
                    .proceed();
        }
        else {
            new C3AirMultiPassengerInfoFragment(getWebdriverInstance())
                    .fillCustomerName()
                    .fillBirthDay()
                    .fillGender()
                    .fillPhone(customerInfo.getPhoneNumber())
                    .fillDriverAge()
                    .proceed();
        }
    }

    private void setInsurance(boolean withTripInsurance, boolean withCarInsurance) {
        LOGGER.info("Setting the insurance");
        new C3InsuranceFragment(getWebdriverInstance()).setInsurance(withTripInsurance, withCarInsurance).proceed();
    }

    private void fillPaymentInfo() {
        C3AirPaymentInfoFragment fragment = new C3AirPaymentInfoFragment(getWebdriverInstance());
        try {
            fragment.useSavedCreditCard();
            LOGGER.info("Use saved credit card");
        }
        catch (NoSuchElementException | ElementNotVisibleException | TimeoutException e) {
            fragment.setVisaCreditCard(billingInfo.getCcNumber(), billingInfo.getSecurityCode())
                    .setBillingName(billingInfo.getCcFirstName(), billingInfo.getCcLastName())
                    .setUSBillingAddress(billingInfo.getBillingAddress(),
                            billingInfo.getCity(),
                            billingInfo.getState(),
                            billingInfo.getZipCode());
            LOGGER.info("New credit card");
        }
        fragment.proceed();
    }

    private void fillCCPaymentInfo() {
        C3AirPaymentInfoFragment fragment = new C3AirPaymentInfoFragment(getWebdriverInstance());
        fragment.useCreditCard().setVisaCreditCard(billingInfo.getCcNumber(), billingInfo.getSecurityCode())
                    .setBillingName(billingInfo.getCcFirstName(), billingInfo.getCcLastName())
                    .setUSBillingAddress(billingInfo.getBillingAddress(),
                            billingInfo.getCity(),
                            billingInfo.getState(),
                            billingInfo.getZipCode()).proceed();
        LOGGER.info("New credit card");
    }

    public void reviewAndBook() {
        LOGGER.info("Review and book");
        new C3AirBillingReviewFragment(getWebdriverInstance()).agreeTerms().proceed();
    }

    @Override
    public void clickBookButton() {
        reviewAndBook();
    }

    public void processBillingPage() {
        fillAllBillingInfo();
        reviewAndBook();
    }

    public void processBillingPageWithCarAddOn() {
        fillAllBillingInfoWithCarAddOn();
        reviewAndBook();
    }

    @Override
    public void saveReferenceNumberFromDetailsPage() {
        String ref = "2" + getWebdriverInstance().getCurrentUrl().split("searchId=")[1]
                .toString().split("&")[0].toString();
        c3ItineraryInfo.setReferenceNumberDetails(ref);
    }

    @Override
    public void verifySearchParametersOnResultsPage() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy"); //US_DATE_FORMAT
        C3AirResultsPage c3AirResultsPage = new C3AirResultsPage(getWebdriverInstance());
        c3AirResultsPage.openChangeSearchPopup();
        c3AirResultsPage = new C3AirResultsPage(getWebdriverInstance());
        assertThat(c3AirResultsPage.getOrigCity())
                .as("Origination city: ")
                .containsIgnoringCase(tripInfo.getFromLocation());
        assertThat(c3AirResultsPage.getDestCity())
                .as("Destination city: ")
                .containsIgnoringCase(tripInfo.getDestinationLocation());
        assertThat(c3AirResultsPage.getStartDate())
                .as("Start date: ")
                .isEqualToIgnoringCase(df.format(tripInfo.getStartDate()));
//        assertThat(c3AirResultsPage.getEndDate())
//                .as("End date: ")
//                .isEqualToIgnoringCase(df.format(tripInfo.getEndDate()));
        assertThat(c3AirResultsPage.getPassengers())
                .as("Passengers: ")
                .isEqualToIgnoringCase(tripInfo.getPassengers().toString());
        c3AirResultsPage.closeChangeSearchPopup();
    }

    @Override
    public void verifySearchParametersOnDetailsPage() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM dd"); //US_DATE_FORMAT
        C3AirDetailsPage c3AirDetailsPage = new C3AirDetailsPage(getWebdriverInstance());

        assertThat(c3AirDetailsPage.getOrigCity())
                .as("Origination city: ")
                .containsIgnoringCase(tripInfo.getFromLocation());
        assertThat(c3AirDetailsPage.getDestCity())
                .as("Destination city: ")
                .containsIgnoringCase(tripInfo.getDestinationLocation());
        assertThat(c3AirDetailsPage.getStartDate())
                .as("Start date: ")
                .isEqualToIgnoringCase(df.format(tripInfo.getStartDate()));
        assertThat(c3AirDetailsPage.getEndDate())
                .as("End date: ")
                .isEqualToIgnoringCase(df.format(tripInfo.getEndDate()));
        assertThat(c3AirDetailsPage.getPassengers())
                .as("Passengers: ")
                .isEqualToIgnoringCase(tripInfo.getPassengers().toString());
    }

    @Override
    public void verifyRefundRebookMsg() {
        unimplementedTest();
    }

    @Override
    void verifyAreaMapOnResults() {
        unimplementedTest();
    }

    @Override
    public void verifyLocationOnConfirmationPage(String location) {
        unimplementedTest();
    }

    @Override
    public void saveConfirmationPageInBean() {
        C3AirConfirmationPage airConfirmationPage = new C3AirConfirmationPage(getWebdriverInstance());
        c3FlightInfo.setConfirmationNumber(airConfirmationPage.getConfirmationCode());

        c3FlightInfo.setDepartingFlightNumber(airConfirmationPage.getDepartingFlightNumber());
        c3FlightInfo.setReturningFlightNumber(airConfirmationPage.getReturningFlightNumber());

        c3FlightInfo.setDepartingStartDateAndTime(airConfirmationPage.getDepartingStartDateAndTime());
        c3FlightInfo.setDepartingEndDateAndTime(airConfirmationPage.getDepartingEndDateAndTime());
        c3FlightInfo.setReturningStartDateAndTime(airConfirmationPage.getReturningStartDateAndTime());
        c3FlightInfo.setReturningEndDateAndTime(airConfirmationPage.getReturningEndDateAndTime());

        c3FlightInfo.setDepartingTotalTripTime(airConfirmationPage.getDepartingTotalTripTime());
        c3FlightInfo.setReturningTotalTripTime(airConfirmationPage.getReturningTotalTripTime());

        c3FlightInfo.setDepartingCarrier(airConfirmationPage.getDepartingCarrier());
        c3FlightInfo.setReturningCarrier(airConfirmationPage.getReturningCarrier());

        c3FlightInfo.setDepartingFromLocation(airConfirmationPage.getDepartingFromLocation());
        c3FlightInfo.setDepartingToLocation(airConfirmationPage.getDepartingToLocation());
        c3FlightInfo.setReturningFromLocation(airConfirmationPage.getReturningFromLocation());
        c3FlightInfo.setReturningToLocation(airConfirmationPage.getReturningToLocation());

        c3FlightInfo.setAirTicketsNumbers(airConfirmationPage.getAirTicketsNumbers());
        c3FlightInfo.setNamesOfPassengers(airConfirmationPage.getNamesOfPassengers());
        c3FlightInfo.setNumberOfTickets(airConfirmationPage.getNumberOfTickets());

        c3ItineraryInfo.setTotalCost(airConfirmationPage.getTotalCost());
    }

    @Override
    public void verifyConfirmationPageForHotDollarsPurchase() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    void switchToOpaque() {
        throw new UnimplementedTestException("Implement me!");
    }
}

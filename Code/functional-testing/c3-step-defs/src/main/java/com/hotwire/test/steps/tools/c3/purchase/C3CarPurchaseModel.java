/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.purchase;

import com.hotwire.selenium.tools.c3.purchase.C3AreaMapPage;
import com.hotwire.selenium.tools.c3.purchase.C3FareFinder;
import com.hotwire.selenium.tools.c3.purchase.car.C3CarBillingPage;
import com.hotwire.selenium.tools.c3.purchase.car.C3CarConfirmationPage;
import com.hotwire.selenium.tools.c3.purchase.car.C3CarResultsPage;
import com.hotwire.selenium.tools.c3.purchase.car.C3CarSearchFragment;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.TripInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.testing.UnimplementedTestException;
import org.fest.assertions.Assertions;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Car purchase model for web app
 */
public class C3CarPurchaseModel extends C3PurchaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3CarPurchaseModel.class);
    private static final String REFUND_REBOOK_MSG = "The customer has been refunded";
//            " for Hotwire Itinerary %s. The itinerary has been cancelled in the GDS." +
//            " Confirmation of the refund has been sent to %s.";

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private TripInfo tripInfo;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private CustomerInfo customerInfo;

    public void fillSearchParametersAndSubmit() {
        C3FareFinder searchFragment = new C3FareFinder(getWebdriverInstance()).chooseCar();
        searchFragment.setDestination(tripInfo.getDestinationLocation());
        searchFragment.setDates(tripInfo.getStartDate(), tripInfo.getEndDate());
        if (!tripInfo.getPickupTime().equals("noon")) {
            searchFragment.setTimes(tripInfo.getPickupTime(), tripInfo.getDropoffTime());
        }
        searchFragment.submit();
    }

    public void setPickupTime(String startTime) {
        tripInfo.setPickupTime(startTime);
    }

    public void setDropoffTime(String endTime) {
        tripInfo.setDropoffTime(endTime);
    }

    @Override
    public void switchToRetail() {
        unimplementedTest();
    }

    @Override
    public void verifyResultsPage() {
        new C3CarResultsPage(getWebdriverInstance()).verifyResults();
    }

    @Override
    public void verifyDetailsPage() {
        new C3CarBillingPage(getWebdriverInstance());
    }

    @Override
    public void waitForResults() {
        waitPolling("div.mask");
    }

    @Override
    public void checkStaticInsurance(boolean availability) {
        if (availability) {
            String staticInsuranceText = new C3CarBillingPage(getWebdriverInstance())
                    .getStaticInsuranceModule().getText();
            Assertions.assertThat(staticInsuranceText)
                    .contains("Rental Car Protector can protect you against unforeseen expenses.")
                    .contains("Provides coverage for covered collision, loss or damage.")
                    .contains("Coverage is primary so you do not have to file with your personal insurance.")
                    .contains("Travel insurance offered by Allianz Global Assistance. " +
                            "Terms, conditions, and exclusions apply.");
        }
        else {
            unimplementedTest();
        }
    }

    public void verifyStaticInsurance() {
        unimplementedTest();
    }

    @Override
    public String getReferenceNumberFromResultsPage() {
        return new C3CarResultsPage(getWebdriverInstance()).getReferenceNumber();
    }

    @Override
    public void verifyValidationMsgOnBilling(String msg) {
        throw new UnimplementedTestException("unimplemented");
    }

    @Override
    public void verifyPrefillingOnBillingPage() {
        C3CarBillingPage billingPage = new C3CarBillingPage(getWebdriverInstance());
        try {
            Assertions.assertThat(billingPage.getFirstTravelerName()).isNotEmpty();
            Assertions.assertThat(billingPage.getLastTravelerName()).isNotEmpty();
        }
        catch (NoSuchElementException e) {
            Assertions.assertThat(billingPage.getPrimaryDiver()).isNotEmpty();
        }
        try {
            Assertions.assertThat(billingPage.getTravelerEmail()).isEqualTo(customerInfo.getEmail());
            Assertions.assertThat(billingPage.getTravelerConfEmail()).isEqualTo(customerInfo.getEmail());
        }
        catch (NoSuchElementException e) {
            Assertions.assertThat(billingPage.getSavedEmail()).isEqualTo(customerInfo.getEmail());
        }
        Assertions.assertThat(billingPage.getTravelerPhone()).isNotEmpty();
//        Assertions.assertThat(billingPage.getTravelerAge()).isTrue();
    }

    @Override
    public void fillAllBillingInfo() {
        C3CarBillingPage carBillingPage = new C3CarBillingPage(getWebdriverInstance());
        carBillingPage.fillTravelerFullInfoPanel(
                customerInfo.getFirstName(),
                customerInfo.getLastName(),
                customerInfo.getPhoneNumber(),
                customerInfo.getEmail());

        carBillingPage.fillInsurance(true);
        //Switch to new card if saved card is chosen
        try {
            carBillingPage.payWithSavedCard();
        }
        catch (NoSuchElementException | ElementNotVisibleException e) {
            LOGGER.info("No saved cards");
            fillPaymentBlock();
        }
    }

    private void fillPaymentBlock() {
        C3CarBillingPage carBillingPage = new C3CarBillingPage(getWebdriverInstance());
        carBillingPage.fillCreditCard(billingInfo.getCcNumber(),
                billingInfo.getCcExpMonth(),
                billingInfo.getCcExpYear(),
                billingInfo.getSecurityCode());
        carBillingPage.fillCardHolder(customerInfo.getFirstName(), customerInfo.getLastName());
        carBillingPage.fillCardHolderContacts(billingInfo.getBillingAddress(),
                billingInfo.getCity(),
                billingInfo.getState(),
                billingInfo.getZipCode());
    }

    @Override
    public void clickBookButton() {
        new C3CarBillingPage(getWebdriverInstance()).agreeAndBook();
    }

    @Override
    public void receiveConfirmation() {
        C3CarConfirmationPage c3CarConfirmationPage = new C3CarConfirmationPage(getWebdriverInstance());
        c3ItineraryInfo.setItineraryNumber(c3CarConfirmationPage.getItineraryNumber());
        c3ItineraryInfo.setDriverName(c3CarConfirmationPage.getDriverName());
        c3ItineraryInfo.setPickUpLocation(c3CarConfirmationPage.getPickUpLocation());
        c3ItineraryInfo.setPickUpDate(c3CarConfirmationPage.getPickUpDateTime());
        c3ItineraryInfo.setDropOffDate(c3CarConfirmationPage.getDropOffDateTime());
        c3ItineraryInfo.setTotalCost(c3CarConfirmationPage.getTotalPrice());
        c3ItineraryInfo.setCompany(c3CarConfirmationPage.getCarCompany());
        c3ItineraryInfo.setCarReservationNumber(c3CarConfirmationPage.getCarReservationNumber());
    }

    @Override
    public void proceedDetailsPage() {
        /**
         * No needs to proceed to details page
         */
    }

    public void processBillingPage() {
        fillAllBillingInfo();
        clickBookButton();
    }

    @Override
    public void saveReferenceNumberFromDetailsPage() {
        c3ItineraryInfo.setReferenceNumberDetails(new C3CarBillingPage(getWebdriverInstance()).getReferenceNumber());
    }

    @Override
    public void verifySearchParametersOnResultsPage() {
        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForResults();
        processSearchFrame();
        String df = "MM/dd/yy";
        C3CarSearchFragment ff = new C3CarSearchFragment(getWebdriverInstance());
        assertThat(ff.getDestination())
                .as("Location: ")
                .isEqualTo(tripInfo.getDestinationLocation());
        assertThat(ff.getStartDate())
                .as("Start date: ")
                .isEqualTo(new SimpleDateFormat(df).format(tripInfo.getStartDate()));
        assertThat(ff.getEndDate())
                .as("End date: ")
                .isEqualTo(new SimpleDateFormat(df).format(tripInfo.getEndDate()));
    }

    @Override
    public void verifySearchParametersOnDetailsPage() {
        String df = "EEE, MMM d";
        C3CarBillingPage c3CarBillingPage = new C3CarBillingPage(getWebdriverInstance());
        assertThat(c3CarBillingPage.getLocation())
                .as("Location: ")
                .containsIgnoringCase(tripInfo.getDestinationLocation());
        assertThat(c3CarBillingPage.getStartDate())
                .as("Start date: ")
                .isEqualTo(new SimpleDateFormat(df).format(tripInfo.getStartDate()));
        assertThat(c3CarBillingPage.getEndDate())
                .as("End date: ")
                .isEqualTo(new SimpleDateFormat(df).format(tripInfo.getEndDate()));
    }

    @Override
    public void verifyRefundRebookMsg() {
        Assertions.assertThat(new C3CarConfirmationPage(getWebdriverInstance()).getRefundRebookMsg())
                .as("verify Confirmation message")
                .contains(REFUND_REBOOK_MSG)
                .contains(c3ItineraryInfo.getRefundAmount().toString())
                .contains(c3ItineraryInfo.getCurrencyCode())
                .contains(c3ItineraryInfo.getItineraryNumber())
                .contains(customerInfo.getEmail());
    }

    public void verifyAreaMapOnResults() {
        C3CarResultsPage carResultsPage = new C3CarResultsPage(getWebdriverInstance());
        carResultsPage.selectSeeMapLink();
        C3AreaMapPage areaMapPage = new C3AreaMapPage(getWebdriverInstance());
        assertThat(areaMapPage.getYourSearch())
                 .as("Area map contains destination location in the Your search section")
                 .containsIgnoringCase(tripInfo.getDestinationLocation());
        areaMapPage.clickCloseBtn();
    }

    @Override
    public void verifyLocationOnConfirmationPage(String location) {
        C3CarConfirmationPage carConfirmationPage = new C3CarConfirmationPage(getWebdriverInstance());
        assertThat(carConfirmationPage.getDestinationLocation())
                .as("Car confirmation page contains correct location")
                .contains(location);
    }

    @Override
    void saveConfirmationPageInBean() {
        throw new UnimplementedTestException("Implement me!");
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

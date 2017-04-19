/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.purchase;

import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.tools.c3.purchase.C3FareFinder;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3HotelConfirmationPage;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3HotelDetailsPage;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3HotelResultsPage;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3HotelSearchFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3IntlHotelSearchFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3HotDollarsFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3HotelBillingFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3HotelBillingPage;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3HotelTripSummary;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3TripInsuranceFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.TravelerFragments.C3GuestTravelerFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.TravelerFragments.C3KnownTravelerFragment;
import com.hotwire.test.steps.tools.bean.AppMetaData;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.TripInfo;
import com.hotwire.test.steps.tools.bean.c3.C3HotelSupplyInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3CustomerDao;
import org.fest.assertions.Assertions;
import org.fest.assertions.Delta;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.SimpleDateFormat;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Hotel purchase model of web application
 */
public class C3HotelPurchaseModel extends C3PurchaseModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(C3HotelPurchaseModel.class.getName());

//    private static final String REFUND_REBOOK_MSG = "The customer has been refunded %s" +
//        " for Hotwire Itinerary %s. The itinerary has been cancelled in the GDS." +
//        " Confirmation of the refund has been sent to %s.";

    private static final String REFUND_REBOOK_MSG = "The customer has been refunded";

    private static final Double HOTEL_INSURANCE_PERCENT = 0.04;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    @Qualifier("c3HotelInfo")
    private C3HotelSupplyInfo c3HotelSupplyInfo;

    @Autowired
    private TripInfo tripInfo;

    @Autowired
    private AppMetaData appMetaData;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private CustomerInfo nonExpressCustomer;

    @Override
    public void fillSearchParametersAndSubmit() {
        C3HotelSearchFragment searchFragment;
        if (appMetaData.isIntl()) {
            searchFragment = new C3IntlHotelSearchFragment(getWebdriverInstance());
            searchFragment.uncheckPartners();
        }
        else {
            searchFragment = new C3FareFinder(getWebdriverInstance()).chooseHotel();
            searchFragment.uncheckPartners();
        }
        searchFragment.setDestination(tripInfo.getFromLocation());
        searchFragment.setDates(tripInfo.getStartDate(), tripInfo.getEndDate());
        searchFragment.setHotelRooms(tripInfo.getNumberOfHotelRooms());
        searchFragment.setGuests(tripInfo.getNumberOfAdults(), tripInfo.getNumberOfChildren());
        searchFragment.submit();
    }

    @Override
    public void verifyResultsPage() {
        new C3HotelResultsPage(getWebdriverInstance()).verifyResults();
    }

    @Override
    public void verifyDetailsPage() {
        new C3HotelDetailsPage(getWebdriverInstance());
    }

    @Override
    public void waitForResults() {
        waitPolling("div.loadingMask");
    }

    @Override
    public void checkStaticInsurance(boolean availability) {
        if (availability) {
            assertThat(new C3TripInsuranceFragment(getWebdriverInstance()).getInsuranceText())
                    .doesNotContain("Even when nothing goes wrong... " +
                            "it is very reassuring to know Allianz is in my corner");
        }
        else {
            assertThat(new C3HotelBillingPage(getWebdriverInstance()).isInsuranceDisplayed()).isFalse();
        }
    }

    @Override
    public void verifyStaticInsurance() {
        //checking withOUT insurance
        Double totalWOInsurance = c3ItineraryInfo.extractPrice(new C3HotelTripSummary(getWebdriverInstance())
                .getHotwireTotal());
        assertThat(new C3HotelTripSummary(getWebdriverInstance()).isHotelProtectionAvailable()).isFalse();

        //checking WITH insurance
        new C3TripInsuranceFragment(getWebdriverInstance()).setInsurance(true);
        Double totalWithInsurance = c3ItineraryInfo.extractPrice(new C3HotelTripSummary(getWebdriverInstance())
                .getHotwireTotal());
        Double insuranceAmount =  c3ItineraryInfo.extractPrice(new C3HotelTripSummary(getWebdriverInstance())
                .getHotelProtectionAmount());
        assertThat(totalWithInsurance - totalWOInsurance).isEqualTo(insuranceAmount, Delta.delta(1));
        assertThat(totalWithInsurance - totalWOInsurance)
                .isEqualTo(totalWOInsurance * HOTEL_INSURANCE_PERCENT, Delta.delta(1));
        //turning off insurance again
        new C3TripInsuranceFragment(getWebdriverInstance()).setInsurance(false);
        assertThat(new C3HotelTripSummary(getWebdriverInstance()).isHotelProtectionAvailable()).isFalse();
    }

    public void selectOpaqueResult() {
        new HotelResultsPage(getWebdriverInstance()).chooseOpaque();
        new WebDriverWait(getWebdriverInstance(), 15)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("ul.results li.opaque a.detailsLink"))).click();
    }

    @Override
    public void fillAllBillingInfo() {
        new C3HotelBillingPage(getWebdriverInstance());
        fillTravelerInfo();
        setInsurance();
        fillPaymentInfo();
        setTripTotal();
    }

    private void setInsurance() {
        if (!appMetaData.isIntl()) {
            C3TripInsuranceFragment tripInsuranceFragment = new C3TripInsuranceFragment(getWebdriverInstance());
            try {
                if (billingInfo.isWithInsurance()) {
                    tripInsuranceFragment.setInsurance(true);
                    billingInfo.setInsuranceAmount(new C3HotelTripSummary(getWebdriverInstance())
                            .getHotelProtectionAmount().replaceAll("[$,]", ""));
                }
                else {
                    tripInsuranceFragment.setInsurance(false);
                    billingInfo.setInsuranceAmount("0.0");
                }

            }
            catch (TimeoutException e) {
                LOGGER.warn("Insurance not loaded after 30 seconds");
                billingInfo.setInsurance(false);
                billingInfo.setInsuranceAmount("0.0");
            }
        }
        else {
            LOGGER.info("No insurance for INTL purchase");
            billingInfo.setInsurance(false);
            billingInfo.setInsuranceAmount("0.0");
        }
    }

    public void setTripTotal() {
        billingInfo.setTotalAmount(new C3HotelTripSummary(getWebdriverInstance()).getHotwireTotal().replace(",", ""));
    }

    @Override
    public void clickBookButton() {
        C3HotelBillingPage hotelBillingPage = new C3HotelBillingPage(getWebdriverInstance());
        hotelBillingPage.book();
    }

    @Override
    public void verifyRefundRebookMsg() {
        String message = new C3HotelConfirmationPage(getWebdriverInstance()).getInfoMsg().replace(",", "");
        Assertions.assertThat(message)
                .as("verify Confirmation message")
                .contains(REFUND_REBOOK_MSG)
                .contains(c3ItineraryInfo.getCurrencyCode())
                .contains(c3ItineraryInfo.getItineraryNumber())
                .contains(customerInfo.getEmail());
        assertThat(c3ItineraryInfo.parseAmount(message
                .replaceAll("\\. .*$", "")
                .replaceAll(" for Hotwire Itinerary.*$", "")))
                .as("Comparing amounts with rounding issue handling.")
                .isEqualTo(c3ItineraryInfo.getRefundAmount(), Delta.delta(0.1));
    }

    @Override
    public void switchToRetail() {
        new C3HotelResultsPage(getWebdriverInstance()).switchToRetail();
    }

    @Override
    void switchToOpaque() {
        new C3HotelResultsPage(getWebdriverInstance()).switchToOpaque();
    }

    private void fillTravelerInfo() {
        if (customerInfo.isGuest()) {
            C3GuestTravelerFragment travelFragment = new C3GuestTravelerFragment(getWebdriverInstance());
            travelFragment.setCustomerName(customerInfo.getFirstName(), customerInfo.getLastName());
            travelFragment.setEmail(customerInfo.getEmail());
            travelFragment.setPhone(customerInfo.getPhoneNumber());
        }
        else {
            if (new C3CustomerDao(getDataBaseConnection()).isCustomerHasTravelers(customerInfo.getEmail()) &&
                new C3KnownTravelerFragment(getWebdriverInstance()).isSelectAvailable()) {
                C3KnownTravelerFragment c3KnownTravelerFragment = new C3KnownTravelerFragment(getWebdriverInstance());
                c3KnownTravelerFragment.selectTraveler();
            }
            else {
                C3GuestTravelerFragment travelFragment = new C3GuestTravelerFragment(getWebdriverInstance());
                travelFragment.setCustomerName(customerInfo.getFirstName(), customerInfo.getLastName());
                travelFragment.setPhone(customerInfo.getPhoneNumber());
            }
        }
    }

    private void setCreditCardInfo() {
        if (billingInfo.isChoosePayWithNewCreditCard()) {
            new C3HotelBillingFragment(getWebdriverInstance()).chooseNewCreditCard();
        }
        try {
            fillCreditCardInfo();
        }
        catch (TimeoutException e) {
            payBySavedCard();
        }
    }

    private void fillCreditCardInfo() {
        C3HotelBillingFragment billingPage = new C3HotelBillingFragment(getWebdriverInstance());
        billingPage.setCreditCard(
                billingInfo.getCcNumber(),
                billingInfo.getCcExpMonth(),
                billingInfo.getCcExpYear(),
                billingInfo.getSecurityCode()
        );
        billingPage.setCreditCardOwner(billingInfo.getCcFirstName(), billingInfo.getCcLastName());
        //Environment issue between QA and Dev
        //On Dev environment even on INTL site only US site is displayed.
        if (getWebdriverInstance().getCurrentUrl().contains(".qa.hotwire")) {
            if (!appMetaData.isIntl()) {
                billingPage.setCreditCardState(billingInfo.getState());
            }
        }
        else {
            if (appMetaData.isIntl()) {
                billingPage.setCreditCardCountry(billingInfo.getCountry());
            }
            billingPage.setCreditCardState(billingInfo.getState());
        }
        billingPage.setCreditCardAddress(billingInfo.getBillingAddress(), billingInfo.getCity());
        billingPage.setZipCode(billingInfo.getZipCode());
    }

    private void fillPaymentInfo() {
        switch (customerInfo.getCustomerType()) {
            case GUEST:
            case NEW:
            default:
                //check HotDollars state
                if (billingInfo.isWithHotDollars()) {
                    payWithHotDollars();
                }
                else {
                    setCreditCardInfo();
                }
                break;
        }
    }


    public void payBySavedCard() {
        new C3HotelBillingFragment(getWebdriverInstance()).setSavedCardCode();
    }

    private void payWithHotDollars() {

        Double usedHotDollars;
        Double remainderHotDollars;

        billingInfo.setTotalAmountWithOutHotDollars(Double
                .parseDouble(new C3HotelTripSummary(getWebdriverInstance()).getHotwireTotal().replaceAll("[$,]", "")));

        new C3HotDollarsFragment(getWebdriverInstance()).chooseHotDollars();


        billingInfo.setHotDollarsAmount(new C3HotelTripSummary(getWebdriverInstance()).getHotDollarsAmount());


        usedHotDollars = Double.parseDouble(billingInfo.getHotDollarsAmountText().replaceAll("[$,]", ""));
        remainderHotDollars = customerInfo.getHotDollarsAmount() - usedHotDollars;

        customerInfo.setHotDollars(remainderHotDollars.toString());
        customerInfo.setRecentlyUsedDollars(usedHotDollars);

        if (billingInfo.isWithInsurance() ||
                Double.parseDouble(new C3HotelTripSummary(getWebdriverInstance())
                        .getHotwireTotal().replaceAll("[$,]", "")) > 0.0) {
            setCreditCardInfo();
        }
//        else {
//            throw new PendingException("Insurance unavailable");
//        }
    }

    @Override
    public void receiveConfirmation() {
        C3HotelConfirmationPage pg = new C3HotelConfirmationPage(getWebdriverInstance());
        c3ItineraryInfo.setItineraryNumber(pg.getItineraryNumber());
        c3ItineraryInfo.setHotelReservationNumber(pg.getConfirmationCode());
        customerInfo.setCustomerTypeByEmail(customerInfo.getEmail());
        assertThat(c3ItineraryInfo.compareAmounts(pg.getTotalAmount(), billingInfo.getTotalAmountText())).isTrue();
        c3HotelSupplyInfo.setFullHotelName(pg.getFullHotelName());
        c3HotelSupplyInfo.setAmenities(pg.getAmenities());
        c3HotelSupplyInfo.setHotelGuestName(pg.getHotelGuestName());
        c3HotelSupplyInfo.setHotelAddress(pg.getHotelAddress());
        c3HotelSupplyInfo.setStarRating(pg.getStarRating());
        c3HotelSupplyInfo.setNights(pg.getNights().toString());
        c3HotelSupplyInfo.setRooms(pg.getRooms().toString());
        c3HotelSupplyInfo.setCheckInDate(pg.getCheckInDate());
        c3HotelSupplyInfo.setCheckOutDate(pg.getCheckOutDate());
        c3ItineraryInfo.setTotalCost(pg.getTotalAmount());
    }

    @Override
    public void proceedDetailsPage() {
//        String pageName = new PageName().apply(getWebdriverInstance());
//        if (".results".contains(pageName) &&
//                getWebdriverInstance().findElements(By.cssSelector(".priceChangeResultMessage")).size() > 0) {
//            // Inventory went no longer available. Possible valid outcome going to details page. Investigate though
//            // if app is using test harness.
//            throw new PendingException("Going to details page, inventory became unavailable.");
//        }
        new C3HotelDetailsPage(getWebdriverInstance()).select();
    }

    @Override
    public void processBillingPage() {
        fillAllBillingInfo();
        clickBookButton();
    }

    @Override
    public void verifyPrefillingOnBillingPage() {
        new C3HotelBillingPage(getWebdriverInstance());
        switch (customerInfo.getCustomerType()) {
            case GUEST:
                C3GuestTravelerFragment guestFragment = new C3GuestTravelerFragment(getWebdriverInstance());
                Assertions.assertThat(guestFragment.getFirstName()).isNotEmpty();
                Assertions.assertThat(guestFragment.getLastName()).isNotEmpty();
                Assertions.assertThat(guestFragment.getEmail()).isEqualTo(customerInfo.getEmail());
                Assertions.assertThat(guestFragment.getPhone()).isNotEmpty();
                break;
            default:
                C3KnownTravelerFragment knownTravelerFragment = new C3KnownTravelerFragment(getWebdriverInstance());
//                Assertions.assertThat(knownTravelerFragment.getName()).isNotEmpty();
                Assertions.assertThat(knownTravelerFragment.getEmail()).isEqualTo(customerInfo.getEmail());
                Assertions.assertThat(knownTravelerFragment.getPhone()).isNotEmpty();
                break;
        }
    }

    @Override
    public void saveReferenceNumberFromDetailsPage() {
        c3ItineraryInfo.setReferenceNumberDetails(new C3HotelDetailsPage(getWebdriverInstance()).getReferenceNumber());
        c3ItineraryInfo.setItineraryNumber(c3ItineraryInfo.getReferenceNumberDetails().substring(1));
    }

    @Override
    public String getReferenceNumberFromResultsPage() {
        return new C3HotelResultsPage(getWebdriverInstance()).getReferenceNumber();
    }

    @Override
    public void verifySearchParametersOnResultsPage() {
        SimpleDateFormat df = new SimpleDateFormat("M/d/yy"); //US_DATE_FORMAT
        C3HotelResultsPage c3HotelResultsPage = new C3HotelResultsPage(getWebdriverInstance());
        c3HotelResultsPage.clickEditSearch();
        c3HotelResultsPage = new C3HotelResultsPage(getWebdriverInstance());
        assertThat(c3HotelResultsPage.getLocation())
                .as("Location: ")
                .contains(tripInfo.getFromLocation());
        assertThat(c3HotelResultsPage.getStartDate())
                .as("Start date: ")
                .isEqualToIgnoringCase(df.format(tripInfo.getStartDate()));
        assertThat(c3HotelResultsPage.getEndDate())
                .as("End date: ")
                .isEqualToIgnoringCase(df.format(tripInfo.getEndDate()));
        assertThat(c3HotelResultsPage.getRooms())
                .as("Rooms: ")
                .isEqualToIgnoringCase(tripInfo.getNumberOfHotelRooms().toString());
        assertThat(c3HotelResultsPage.getAdults())
                .as("Adults: ")
                .isEqualToIgnoringCase(tripInfo.getNumberOfAdults().toString());
        assertThat(c3HotelResultsPage.getChildren())
                .as("Children: ")
                .isEqualToIgnoringCase(tripInfo.getNumberOfChildren().toString());
    }

    @Override
    public void verifySearchParametersOnDetailsPage() {
        SimpleDateFormat df = new SimpleDateFormat("MMM d"); //US_DATE_FORMAT
        C3HotelDetailsPage c3HotelDetailsPage = new C3HotelDetailsPage(getWebdriverInstance());

//        assertThat(c3HotelDetailsPage.getLocation())
//                .as("Location: ")
//                .contains(tripInfo.getFromLocation());
        assertThat(c3HotelDetailsPage.getStartDate())
                .as("Start date: ")
                .contains(df.format(tripInfo.getStartDate()));
        assertThat(c3HotelDetailsPage.getEndDate())
                .as("End date: ")
                .contains(df.format(tripInfo.getEndDate()));
        assertThat(c3HotelDetailsPage.getRooms())
                .as("Rooms: ")
                .isEqualToIgnoringCase(tripInfo.getNumberOfHotelRooms().toString());
        assertThat(c3HotelDetailsPage.getAdults())
                .as("Adults: ")
                .isEqualToIgnoringCase(tripInfo.getNumberOfAdults().toString());
        if (tripInfo.getNumberOfChildren() != 0) {
            assertThat(c3HotelDetailsPage.getChildren())
                    .as("Children: ")
                    .isEqualToIgnoringCase(tripInfo.getNumberOfChildren().toString());
        }
    }

    @Override
    public void verifyValidationMsgOnBilling(String msg) {
        Assertions.assertThat(new C3HotelBillingPage(getWebdriverInstance()).getValidationMessage()).isEqualTo(msg);
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
    void saveConfirmationPageInBean() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyConfirmationPageForHotDollarsPurchase() {
        C3HotelConfirmationPage hotelConfirmationPage = new C3HotelConfirmationPage(getWebdriverInstance());
        Double insuranceAmount = Double.parseDouble(billingInfo.getInsuranceAmount());
        Double totalWithHotDollars = billingInfo.getTotalAmountWithOutHotDollars() -
                customerInfo.getRecentlyUsedDollars();


        assertThat(Double.parseDouble(hotelConfirmationPage.getHotDollarAmount().replaceAll("[$,]", "")))
                .as("Hot dollars amount is incorrect")
                .isEqualTo(customerInfo.getRecentlyUsedDollars(), Delta.delta(0.1));


        if (customerInfo.getHotDollarsAmount() > 0) {
            assertThat(Double.parseDouble(hotelConfirmationPage.getTotalAmount().replaceAll("[$,]", "")))
                    .as("Wrong total amount")
                    .isEqualTo(insuranceAmount, Delta.delta(insuranceAmount == 0.0 ? 0.0 : 0.1));

        }
        else {
            assertThat(Double.parseDouble(hotelConfirmationPage.getTotalAmount().replaceAll("[$,]", "")))
                    .as("Wrong total amount")
                    .isEqualTo(totalWithHotDollars, Delta.delta(0.1));
        }
    }
}

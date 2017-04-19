/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.search.hotel;

import com.hotwire.selenium.tools.c3.customer.C3CustomerInfoFragment;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3ItineraryDetailsPage;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3HotDollarsFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3HotelBillingFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.C3TripInsuranceFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.TravelerFragments.C3GuestTravelerFragment;
import com.hotwire.selenium.tools.c3.purchase.hotel.billing.TravelerFragments.C3KnownTravelerFragment;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 7/16/13
 * Time: 8:11 AM
 */
public class C3SearchForAHotelModel extends ToolsAbstractModel {
    private static final String OPAQUE_BOOKING_NOTE =
            "ALL BOOKINGS ARE FINAL and cannot be cancelled, refunded, changed, exchanged, or transferred.";

    @Resource
    Map<String, String> hotwireTerms;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    public void openBillingPage() {
        C3ItineraryDetailsPage itineraryDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        C3CustomerInfoFragment custInfo =  new C3CustomerInfoFragment(getWebdriverInstance());
        customerInfo.setEmail(custInfo.getEmail());
        customerInfo.setFirstName(itineraryDetailsPage.getGuestName().split(" ")[0]);
        customerInfo.setLastName(itineraryDetailsPage.getGuestName().split(" ")[1]);
//        customerInfo.setCustomerType(custInfo.getCustomerType());
        if (billingInfo.isWithHotDollars()) {
            billingInfo.setHotDollarsAmount(itineraryDetailsPage.getHotDollarsAmount());
        }
        else {
            String[] billingFullName = itineraryDetailsPage.getBillingName().split(" ");
            billingInfo.setCcFirstName(billingFullName[0]);
            billingInfo.setCcLastName(billingFullName[1]);
        }
        itineraryDetailsPage.openBillingPage();
    }

    public void verifyHotelBillingPage() {
        customerInfo.setCustomerInfoByItinerary(c3ItineraryInfo.getItineraryNumber());
        switchToFrame("iframeBillingPage");
        switch (customerInfo.getCustomerType()) {
            case GUEST:
                C3GuestTravelerFragment billingPage = new C3GuestTravelerFragment(getWebdriverInstance());
                //verify traveler information
                assertThat(billingPage.getFirstName()).isEqualToIgnoringCase(customerInfo.getParticipantFN());
                assertThat(billingPage.getLastName()).isEqualToIgnoringCase(customerInfo.getParticipantLN());
                assertThat(billingPage.getEmail()).isEqualTo(customerInfo.getEmail());
                assertThat(billingPage.getConfEmail()).isEqualTo(customerInfo.getEmail());
                assertThat(billingPage.getPhoneCountryCode()).isNotEmpty();
                assertThat(billingPage.getPhone()).isNotEmpty();
                //verify billing information
                verifyCreditCardFields();
                break;
            case NEW:
                break;
            default:
                C3KnownTravelerFragment knownTravelerFragment = new C3KnownTravelerFragment(getWebdriverInstance());
                assertThat(new C3KnownTravelerFragment(getWebdriverInstance()).getName()).isNotEmpty();
                assertThat(knownTravelerFragment.getEmail()).isEqualTo(customerInfo.getEmail());
                assertThat(knownTravelerFragment.getPhone()).isNotEmpty();
                //verify saved credit cards
                if (new C3HotelBillingFragment(getWebdriverInstance()).isSavedCardAvailable()) {
                    assertThat(new C3HotelBillingFragment(getWebdriverInstance())
                            .isSavedCardCodeAvailable()).isTrue();
                }
                else {
                    verifyCreditCardFields();
                }
        }
        verifyInsurance();
        //verify HotDollars
        if (billingInfo.isWithHotDollars()) {
            verifyHotDollars();
        }
    }

    private void verifyInsurance() {
        //verify insurance
        assertThat(new C3TripInsuranceFragment(getWebdriverInstance()).getInsurance())
                .isEqualTo(billingInfo.isWithInsurance());
    }

    private void verifyHotDollars() {
        C3HotDollarsFragment hdFragment = new C3HotDollarsFragment(getWebdriverInstance());
        assertThat(hdFragment.getHotDollars().isDisplayed()).isTrue();
        assertThat(c3ItineraryInfo.extractPrice(hdFragment.getHotDollarsAmount()))
                .isEqualTo(c3ItineraryInfo.extractPrice(billingInfo.getHotDollarsAmountText()));
    }

    private void verifyCreditCardFields() {
        C3HotelBillingFragment billingPage = new C3HotelBillingFragment(getWebdriverInstance());
        assertThat(billingPage.getCardFirstName()).isEqualTo(billingInfo.getCcFirstName());
        assertThat(billingPage.getCardLastName()).isEqualTo(billingInfo.getCcLastName());
        assertThat(billingPage.getCardNum()).isNotEmpty();
        assertThat(billingPage.getExpMonth()).isNotEmpty();
        assertThat(billingPage.getExpYear()).isNotEmpty();
        assertThat(billingPage.getCountry()).isNotEmpty();
        logSession("Credit card contacts verification. Itinerary:" + c3ItineraryInfo.getItineraryNumber());
        assertThat(billingPage.getCardAddress()).isNotEmpty();
        assertThat(billingPage.getCity()).isNotEmpty();
        assertThat(billingPage.getState()).isNotEmpty();
        assertThat(billingPage.getZip()).isNotEmpty();
    }

    public void verifyTermsBullets() {
        C3HotelBillingFragment billingPage = new C3HotelBillingFragment(getWebdriverInstance());
        if (hotwireProduct.isOpaque()) {
            assertThat(billingPage.getTermsBulletsText())
                    .isEqualTo(hotwireTerms.get(hotwireProduct.getOpacity().getProductType()));
            assertThat(billingPage.getBookingNotesBulletsText())
                    .contains(OPAQUE_BOOKING_NOTE);
        }
        else {
            assertThat(billingPage.getTermsBulletsText())
                    .contains(hotwireTerms.get(hotwireProduct.getOpacity().getProductType()));
        }

    }
}






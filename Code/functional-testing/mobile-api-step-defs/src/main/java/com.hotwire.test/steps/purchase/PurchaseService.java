/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import hotwire.view.jaxb.domain.mobileapi.Address;
import hotwire.view.jaxb.domain.mobileapi.BillingInfo;
import hotwire.view.jaxb.domain.mobileapi.CardHolderInfo;
import hotwire.view.jaxb.domain.mobileapi.ClientInfoType;
import hotwire.view.jaxb.domain.mobileapi.LegalLink;
import hotwire.view.jaxb.domain.mobileapi.Name;
import hotwire.view.jaxb.domain.mobileapi.NewPaymentCard;
import hotwire.view.jaxb.domain.mobileapi.PaymentInstrument;
import hotwire.view.jaxb.domain.mobileapi.Reservation;
import hotwire.view.jaxb.domain.mobileapi.SearchResult;
import hotwire.view.jaxb.domain.mobileapi.SearchResultReference;
import hotwire.view.jaxb.domain.mobileapi.booking.BookingRQ;
import hotwire.view.jaxb.domain.mobileapi.booking.BookingRS;
import hotwire.view.jaxb.domain.mobileapi.search.SearchDetailsRQ;
import hotwire.view.jaxb.domain.mobileapi.search.SearchDetailsRS;
import org.joda.time.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-ngolodiuk
 * Since: 1/13/15
 */
public class PurchaseService extends MobileApiService {

    private RequestProperties requestProperties;

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    protected void setClientInfo(BookingRQ request) {
        request.setClientInfo(new ClientInfoType());
        request.getClientInfo().setCurrencyCode(requestProperties.getCurrencyCode());
        request.getClientInfo().setCountryCode(requestProperties.getCountryCode());
        request.getClientInfo().setClientID(requestProperties.getClientId());
        request.getClientInfo().setCustomerID(requestProperties.getCustomerId());
    }

    protected void setClientInfoForDetails(SearchDetailsRQ request) {
        request.setClientInfo(new ClientInfoType());
        request.getClientInfo().setCurrencyCode(requestProperties.getCurrencyCode());
        request.getClientInfo().setCountryCode(requestProperties.getCountryCode());
        request.getClientInfo().setClientID(requestProperties.getClientId());
        request.getClientInfo().setCustomerID(requestProperties.getCustomerId());
        request.getClientInfo().setLatLong(requestProperties.getLatLong());
    }

    protected void checkDetailsResultExist(SearchDetailsRS detailsRS) {
        assertThat(detailsRS.getSearchResultDetails().getSolution().size()).isEqualTo(1);
    }

    protected void checkDetailsResponseExists(SearchDetailsRS detailsRS) {
        assertThat(detailsRS).isNotNull();
    }

    protected void setSearchResultReference(BookingRQ request, String resultId) {
        SearchResultReference searchResultReference = new SearchResultReference();
        SearchResult searchResult = new SearchResult();
        searchResultReference.setSearchResult(searchResult);
        searchResultReference.getSearchResult().setResultID(resultId);
        searchResultReference.getSearchResult().setProductVertical(requestProperties.getVertical());
        request.setSearchResultReference(searchResultReference);
    }

    protected void setPaymentInstrument(UserInformation userInfo,
                                        BookingRQ request) throws DatatypeConfigurationException {
        PaymentInstrument paymentInstrument = new PaymentInstrument();
        request.setPaymentInstrument(paymentInstrument);

        PaymentInstrument currentPaymentInstrument = request.getPaymentInstrument();
        currentPaymentInstrument.setHotDollars(new PaymentInstrument.HotDollars());
        if (requestProperties.getIsNewCard()) {
            NewPaymentCard newPaymentCard = new NewPaymentCard();
            newPaymentCard.setCardNickName(userInfo.getSavedCardName());
            newPaymentCard.setPaymentCardType(userInfo.getCcType());
            newPaymentCard.setCardNumber(userInfo.getCcNumber());
            newPaymentCard.setSecurityCode(String.valueOf(userInfo.getSecurityCode()));
            setExpirationDate(newPaymentCard);

            if (requestProperties.isHotDollarsPayment()) {
                currentPaymentInstrument.getHotDollars().setApplyHotDollars(true);
                newPaymentCard.setAddNewCard(true);
            }

            CardHolderInfo cardHolderInfo = new CardHolderInfo();
            newPaymentCard.setCardHolderInfo(cardHolderInfo);

            Name name = new Name();
            name.setFirstName(userInfo.getFirstName());
            name.setLastName(userInfo.getLastName());
            newPaymentCard.getCardHolderInfo().setName(name);

            Address address = new Address(userInfo.getBillingAddress(),
                                          "",
                                          userInfo.getCity(),
                                          userInfo.getState(),
                                          userInfo.getZipCode(),
                                          requestProperties.getCountryCode());
            newPaymentCard.getCardHolderInfo().setBillingAddress(address);
            currentPaymentInstrument.setNewPaymentCard(newPaymentCard);
        }
        else {
            PaymentInstrument.PaymentCardOnAccount savedCard = new PaymentInstrument.PaymentCardOnAccount();
            savedCard.setCardNickName(userInfo.getSavedCardName());
            savedCard.setSecurityCode(String.valueOf(userInfo.getSecurityCode()));
            request.getPaymentInstrument().setPaymentCardOnAccount(savedCard);
        }
    }

    private void setExpirationDate(NewPaymentCard newPaymentCard) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new DateTime().plusYears(4).toGregorianCalendar().getTime());

        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        newPaymentCard.setExpirationDate(datatypeFactory.newXMLGregorianCalendar(gregorianCalendar));
    }

    protected void verifyBillingInfo(BookingRS bookingResponse) {
        BillingInfo billingInfo = bookingResponse.getBookingConfirmation().getTripDetails().getBillingInfo();
        assertThat(billingInfo).isNotNull();
        UserInformation userInfo = requestProperties.getUserInformation();
        if (requestProperties.getIsPaymentRequired()) {
            assertThat(billingInfo.getName()).isNotEmpty();
        }
        assertThat(userInfo.getEmailId()).isEqualToIgnoringCase(billingInfo.getContactEmail());
        //matches patterns like  Visa************1111 or American Express************1007
        assertThat(billingInfo.getChargedTo()).contains(userInfo.getCcType()).matches("\\w+\\s?\\w+\\*+\\d{4}");
        assertThat(billingInfo.getDateBooked()).isNotNull();
        assertThat(billingInfo.getContactPhone()).isNotNull();
    }

    protected void verifyReservationInfoDetails(Reservation reservation) {
        assertThat("Confirmed").as("Booking status is not as expected.").
            isEqualToIgnoringCase(reservation.getInformation().getBookingStatus());
        assertThat(reservation.getInformation().getConfirmationCode()).isNotEmpty();
        assertThat(reservation.getDuration().getEndDate()).isNotNull();
        assertThat(reservation.getDuration().getStartDate()).isNotNull();
        assertThat(reservation.getLocation().getOriginalLocation()).isNotNull();
        assertThat(reservation.getReservationDetails().getOpacityCode()).isIn(new String[]{"Y", "N"});
    }

    protected void verifyTravelAdvisory(BookingRS bookingResponse) {
        Reservation reservation = bookingResponse.getBookingConfirmation().getTripDetails().getReservation().get(0);
        assertThat(reservation.getReservationDetails().getTravelerAdvisory()).isNotNull();
        assertThat(reservation.getReservationDetails().getTravelerAdvisory().getKnowBeforeYouGo()).isNotEmpty();
        checkLegalLinksForBooking(bookingResponse);
    }

    public void checkLegalLinksForBooking(BookingRS bookingResponse) {
        Reservation reservation = bookingResponse.getBookingConfirmation().getTripDetails().getReservation().get(0);
        List<LegalLink> legalLinks = reservation.getReservationDetails().getTravelerAdvisory().getLegalLinks();
        //There should be 3 links for both car and hotel: Terms of use, Privacy Policy and Booking rules and conditions
        //Car bookings have their own check
        assertThat(legalLinks.size()).isGreaterThanOrEqualTo(3);
        assertThat(legalLinks.get(0).getRel()).isNotEmpty();
        assertThat(legalLinks.get(0).getHref()).isNotEmpty();
    }


}

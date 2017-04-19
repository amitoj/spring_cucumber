/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.trips;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import cucumber.api.PendingException;
import hotwire.view.jaxb.domain.mobileapi.AccountData;
import hotwire.view.jaxb.domain.mobileapi.Address;
import hotwire.view.jaxb.domain.mobileapi.CardHolderInfo;
import hotwire.view.jaxb.domain.mobileapi.HotelDuration;
import hotwire.view.jaxb.domain.mobileapi.Name;
import hotwire.view.jaxb.domain.mobileapi.PaymentCard;
import hotwire.view.jaxb.domain.mobileapi.PaymentCardData;
import hotwire.view.jaxb.domain.mobileapi.Reservation;
import hotwire.view.jaxb.domain.mobileapi.TripSummary;
import hotwire.view.jaxb.domain.mobileapi.customer.RetrieveCustomerProfileRQ;
import hotwire.view.jaxb.domain.mobileapi.customer.RetrieveCustomerProfileRS;
import hotwire.view.jaxb.domain.mobileapi.customer.RetrieveTripSummaryRQ;
import hotwire.view.jaxb.domain.mobileapi.customer.RetrieveTripSummaryRS;
import hotwire.view.jaxb.domain.mobileapi.customer.UpdateCustomerProfileRQ;
import hotwire.view.jaxb.domain.mobileapi.customer.UpdateCustomerProfileRS;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-dsobko
 * Since: 01/19/15
 */
public class TripSummaryService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripSummaryService.class.getName());

    private static final int CREATED_STATUS_CODE = 201;
    private static final int BAD_REQUEST_STATUS_CODE = 400;
    private static final String LOCATION = "Location";
    private static final String DEFAULT_SORTING_TYPE = "default";
    private static final String FIRST_PAGE = "first";


    private RetrieveTripSummaryRS tripSummaryRS;
    private RetrieveTripSummaryRS firstPageOfTripSummaryRS;
    private RetrieveTripSummaryRS secondPageOfTripSummaryRS;
    private RetrieveCustomerProfileRS customerProfileRS;
    private UpdateCustomerProfileRS deleteCreditCardResponse;
    private Response addCreditCardResponse;

    private RequestProperties requestProperties;
    private RequestPaths paths;
    private UserInformation userInformation;

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public void executeTripSummaryRequest(String sorting, boolean isStartDateEnabled, boolean isEndDateEnabled) {
        RetrieveTripSummaryRQ tripSummaryRQ = new RetrieveTripSummaryRQ();
        if (!sorting.equalsIgnoreCase(DEFAULT_SORTING_TYPE)) {
            tripSummaryRQ.setSortBy(setSortingType(sorting));
        }
        if (isStartDateEnabled) {
            tripSummaryRQ.setStartCheckInDate(generateStartCheckInDate());
        }
        if (isEndDateEnabled) {
            tripSummaryRQ.setEndCheckInDate(generateEndCheckInDate());
        }
        tripSummaryRQ.setExcludeCanceledRefunded(requestProperties.getExcludeCanceledRefunded());
        try {
            client.reset()
                    .path(paths.getTripSummaryPath())
                    .accept(MediaType.APPLICATION_XML_TYPE)
                    .header("Authorization", "Bearer " + requestProperties.getOauthToken());
            tripSummaryRS = client.post(tripSummaryRQ, RetrieveTripSummaryRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Trip summary request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Trip summary request has failed with  " +  message, e);
        }
    }

    public void executeTripSummaryRequestWithPagination(String pageNumber, String sortingMethod,
                                                        boolean isStartDateEnabled, boolean isEndDateEnabled) {
        RetrieveTripSummaryRQ tripSummaryRQ = new RetrieveTripSummaryRQ();
        if (!sortingMethod.equalsIgnoreCase(DEFAULT_SORTING_TYPE)) {
            tripSummaryRQ.setSortBy(setSortingType(sortingMethod));
        }
        if (isStartDateEnabled) {
            tripSummaryRQ.setStartCheckInDate(generateStartCheckInDate());
        }
        if (isEndDateEnabled) {
            tripSummaryRQ.setEndCheckInDate(generateEndCheckInDate());
        }
        tripSummaryRQ.setExcludeCanceledRefunded(requestProperties.getExcludeCanceledRefunded());
        tripSummaryRQ.setLimit(requestProperties.getLimit());
        tripSummaryRQ.setOffset(requestProperties.getOffset());
        try {
            client.reset()
                    .path(paths.getTripSummaryPath())
                    .accept(MediaType.APPLICATION_XML_TYPE)
                    .header("Authorization", "Bearer " + requestProperties.getOauthToken());
            if (pageNumber.equalsIgnoreCase(FIRST_PAGE)) {
                firstPageOfTripSummaryRS = client.post(tripSummaryRQ, RetrieveTripSummaryRS.class);
            }
            else {
                secondPageOfTripSummaryRS = client.post(tripSummaryRQ, RetrieveTripSummaryRS.class);
            }
        }
        catch (WebApplicationException e) {
            LOGGER.error("Trip summary request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Trip summary request has failed with  " +  message, e);
        }
    }

    public XMLGregorianCalendar generateStartCheckInDate() {
        GregorianCalendar gregorianCalendar = new DateTime().minusYears(1).toGregorianCalendar();
        return getXmlGregorianCalendar(gregorianCalendar);
    }

    public XMLGregorianCalendar generateEndCheckInDate() {
        GregorianCalendar gregorianCalendar = new DateTime().plusMonths(11).toGregorianCalendar();
        return getXmlGregorianCalendar(gregorianCalendar);
    }

    private XMLGregorianCalendar getXmlGregorianCalendar(GregorianCalendar gregorianCalendar) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        }
        catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public void executeCustomerProfileRequestWithCreditCardData()  {
        RetrieveCustomerProfileRQ customerProfileRQ = new RetrieveCustomerProfileRQ();

        customerProfileRQ.getAccountData().add(new PaymentCard());
        try {
            client.reset()
                    .path(paths.getRetrieveCustomerProfilePath())
                    .accept(MediaType.APPLICATION_XML_TYPE)
                    .header("Authorization", "Bearer " + requestProperties.getOauthToken());
            customerProfileRS = client.post(customerProfileRQ, RetrieveCustomerProfileRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Customer profile request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Customer profile request has failed with  " +  message, e);
        }
    }


    public void addCreditCard() {
        PaymentCardData addCreditCardRequest = new PaymentCardData();
        addCreditCardRequest.setPaymentCard(useRandomCreditCard());
        executeAddCreditCardRequest(addCreditCardRequest);
    }

    public void addInvalidCreditCard(String invalidCriteria) {
        PaymentCardData addCreditCardRequest = new PaymentCardData();
        addCreditCardRequest.setPaymentCard(useInvalidRandomCreditCard(invalidCriteria));
        executeAddCreditCardRequest(addCreditCardRequest);
    }

    private void executeAddCreditCardRequest(PaymentCardData addCreditCardRequest) {
        try {
            client.reset()
                    .path(paths.getAddCreditCardPath())
                    .accept(MediaType.APPLICATION_XML_TYPE)
                    .header("User-Agent", "SomeMobileClient")
                    .header("Authorization", "Bearer " + requestProperties.getOauthToken());
            addCreditCardResponse = client.post(addCreditCardRequest);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Add invalid credit card request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Add invalid credit card request has failed with  " +  message, e);
        }
    }

    public void executeDeleteCreditCardRequest() {
        UpdateCustomerProfileRQ deleteCreditCardRequest = new UpdateCustomerProfileRQ();
        deleteCreditCardRequest.getAccountData().add(deleteRandomCreditCard());
        try {
            client.reset()
                    .path(paths.getUpdateCustomerProfilePath())
                    .accept(MediaType.APPLICATION_XML_TYPE)
                    .header("User-Agent", "SomeMobileClient")
                    .header("Authorization", "Bearer " + requestProperties.getOauthToken());
            deleteCreditCardResponse = client.post(deleteCreditCardRequest, UpdateCustomerProfileRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Delete credit card request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Delete credit card request has failed with  " +  message, e);
        }
    }


    private PaymentCard useRandomCreditCard() {
        PaymentCard newPaymentCard = new PaymentCard();
        setRandomCardCredentials(newPaymentCard);
        newPaymentCard.setCardNumber(userInformation.getRandomCreditCardNumber());
        newPaymentCard.setExpirationDate(userInformation.getExpirationDate());

        setCardHolderInfo(newPaymentCard);

        return newPaymentCard;
    }


    private PaymentCard useInvalidRandomCreditCard(String invalidCriteria) {
        PaymentCard newPaymentCard = new PaymentCard();
        setRandomCardCredentials(newPaymentCard);

        if (invalidCriteria.equalsIgnoreCase("number")) {
            newPaymentCard.setCardNumber(userInformation.getRandomCreditCardNumber() + "11111111");
            newPaymentCard.setExpirationDate(userInformation.getExpirationDate());
        }
        else  {
            newPaymentCard.setCardNumber(userInformation.getRandomCreditCardNumber());
            try {
                GregorianCalendar gregorianCalendar = new DateTime().minusYears(1).toGregorianCalendar();
                DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
                newPaymentCard.setExpirationDate(datatypeFactory.newXMLGregorianCalendar(gregorianCalendar));
            }
            catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
        }

        setCardHolderInfo(newPaymentCard);

        return newPaymentCard;
    }

    private void setRandomCardCredentials(PaymentCard newPaymentCard) {
        newPaymentCard.setCardNickName(userInformation.getRandomCreditCardNickName());
        newPaymentCard.setPaymentCardType(userInformation.getCcType());
    }

    private void setCardHolderInfo(PaymentCard newPaymentCard) {
        CardHolderInfo cardHolderInfo = new CardHolderInfo();

        Name name = new Name();
        name.setFirstName(userInformation.getFirstName());
        name.setLastName(userInformation.getLastName());
        cardHolderInfo.setName(name);

        Address address = getAddress();
        cardHolderInfo.setBillingAddress(address);

        newPaymentCard.setCardHolderInfo(cardHolderInfo);
    }

    private PaymentCard deleteRandomCreditCard() {
        PaymentCard newPaymentCard = new PaymentCard();
        newPaymentCard.setCardNickName(userInformation.getRandomCreditCardNickName());
        newPaymentCard.setExpirationDate(userInformation.getExpirationDate());

        CardHolderInfo cardHolderInfo = new CardHolderInfo();

        Address address = getAddress();
        cardHolderInfo.setBillingAddress(address);

        newPaymentCard.setCardHolderInfo(cardHolderInfo);

        return newPaymentCard;
    }

    private Address getAddress() {
        return new Address(userInformation.getBillingAddress(), null, userInformation.getCity(),
                userInformation.getState(), userInformation.getZipCode(), userInformation.getCountryCode());
    }



   //VERIFICATION

    public void verifyCreditCardAdded() {
        assertThat(addCreditCardResponse.getStatus()).isEqualTo(CREATED_STATUS_CODE);
        assertThat(addCreditCardResponse.getMetadata().get(LOCATION)).isNotNull();
    }

    public void verifyCreditCardNotAdded() {
        assertThat(addCreditCardResponse.getStatus()).isEqualTo(BAD_REQUEST_STATUS_CODE);
        assertThat(addCreditCardResponse.getMetadata().get(LOCATION)).isNull();
    }


    public void verifyTripSummaryResponse() {
        assertThat(tripSummaryRS.getErrors()).isNull();
        assertThat(tripSummaryRS.getTripSummary()).isNotNull();
    }

    public void verifyFirstPageOfTripSummaryResponse() {
        assertThat(firstPageOfTripSummaryRS.getErrors()).isNull();
        assertThat(firstPageOfTripSummaryRS.getTripSummary()).isNotNull();
    }

    public void verifySecondPageOfTripSummaryResponse() {
        assertThat(secondPageOfTripSummaryRS.getErrors()).isNull();
        assertThat(secondPageOfTripSummaryRS.getTripSummary()).isNotNull();
    }

    public void verifyCustomerProfileResponse() {
        assertThat(customerProfileRS.getErrors()).isNull();
        assertThat(customerProfileRS.getAccountData().get(0)).isNotNull();
    }

    public void verifyDeleteCreditCardResponse() {
        assertThat(deleteCreditCardResponse.getErrors()).isNull();
        assertThat(deleteCreditCardResponse).isNotNull();
    }

    public void verifyConfirmationCode() {
        if (!tripSummaryRS.getTripSummary().isEmpty()) {
            for (TripSummary trip : tripSummaryRS.getTripSummary()) {
                for (Reservation reservation : trip.getReservation()) {
                    if (!reservation.getProductVertical().equals("Hotel")) {     //   BUG53339
                        assertThat(trip.getConfirmationCode()).isNotEmpty();
                        assertThat(reservation.getInformation().getConfirmationCode()).isNotEmpty();
                    }
                }
            }
        }
    }

    public void verifyItineraryNumber() {
        LOGGER.info("Itinerary:  " + getTripSummaryFromResponse(0).getItineraryNumber());
        assertThat(getTripSummaryFromResponse(0).getItineraryNumber()).isNotEmpty();
    }

    public void verifyBookingStatus() {
        assertThat(getReservationFromTripSummary(tripSummaryRS, 0, 0).getInformation().getBookingStatus()).isNotEmpty();
    }

    public void verifyProductVertical() {
        assertThat(getReservationFromTripSummary(tripSummaryRS, 0, 0).getProductVertical()).isNotEmpty();
    }

    public void verifySupplierInfo() {
        assertThat(getReservationFromTripSummary(tripSummaryRS, 0, 0).getSuppliers()
                .getSupplierNames().get(0)).isNotEmpty();
        assertThat(getReservationFromTripSummary(tripSummaryRS, 0, 0).getSupplier()
                .get(0).getPhoneNumber()).isNotEmpty();
    }

    public void verifyBookingDatesAndLocation() {
        assertThat(getReservationFromTripSummary(tripSummaryRS, 0, 0).getDuration().getStartDate()).isNotNull();
        assertThat(getReservationFromTripSummary(tripSummaryRS, 0, 0).getDuration().getEndDate()).isNotNull();
        assertThat(getReservationFromTripSummary(tripSummaryRS, 0, 0).getLocation()).isNotNull();
    }

    public void verifySortingMethodIsPresent() {
        assertThat(tripSummaryRS.getSortBy()).isNotNull();
    }

    public void verifySortingMethodIsPresentInBothPages() {
        assertThat(firstPageOfTripSummaryRS.getSortBy()).isNotNull();
        assertThat(secondPageOfTripSummaryRS.getSortBy()).isNotNull();
    }

    public void verifySortingMethodInResponse(String sortingMethod) {
        assertThat(tripSummaryRS.getSortBy()).isEqualToIgnoringCase(sortingMethod + "Date");
    }

    public void verifySortingMethodInBothPages(String sortingMethod) {
        assertThat(firstPageOfTripSummaryRS.getSortBy()).isEqualToIgnoringCase(sortingMethod + "Date");
        assertThat(secondPageOfTripSummaryRS.getSortBy()).isEqualToIgnoringCase(sortingMethod + "Date");
    }

    public void verifyTripsAreInCorrectDateRangeInResponse() {
        verifyTripsAreInCorrectDateRange(tripSummaryRS);
    }

    public void verifyTripsAreInCorrectDateRangeInBothPages() {
        verifyTripsAreInCorrectDateRange(firstPageOfTripSummaryRS);
        verifyTripsAreInCorrectDateRange(secondPageOfTripSummaryRS);
    }

    private void verifyTripsAreInCorrectDateRange(RetrieveTripSummaryRS tripSummaryResponse) {
        if (tripSummaryResponse.getTripSummary().size() > 1) {
            assertThat((getReservationFromTripSummary(tripSummaryResponse, 0, 0).getDuration()
                        .getStartDate()).toGregorianCalendar()
                        .compareTo(generateEndCheckInDate().toGregorianCalendar())).isLessThanOrEqualTo(0);
            assertThat((getReservationFromTripSummary(
                        tripSummaryResponse, tripSummaryResponse.getTripSummary().size() - 1, 0).getDuration()
                        .getStartDate()).toGregorianCalendar()
                        .compareTo(generateStartCheckInDate().toGregorianCalendar())).isGreaterThanOrEqualTo(0);
        }
    }

    public void verifyTripsAreSortedByTravelDateInResponse() {
        verifyTripsAreSortedByTravelDate(tripSummaryRS);
    }

    private void verifyTripsAreSortedByTravelDate(RetrieveTripSummaryRS tripSummaryResponse) {
        if (tripSummaryResponse.getTripSummary().size() > 1) {
            assertThat((getReservationFromTripSummary(tripSummaryResponse, 0, 0).getDuration().getStartDate())
                    .compare(getReservationFromTripSummary(tripSummaryResponse, 0, 0)
                            .getDuration().getStartDate())).isGreaterThanOrEqualTo(0);
        }
    }

    public void verifyTripsAreSortedByTravelDateInBothPages() {
        verifyTripsAreSortedByTravelDate(firstPageOfTripSummaryRS);
        verifyTripsAreSortedByTravelDate(secondPageOfTripSummaryRS);
        assertThat((getReservationFromTripSummary(firstPageOfTripSummaryRS, 0, 0).getDuration().getStartDate())
                .compare(getReservationFromTripSummary(secondPageOfTripSummaryRS, 0, 0)
                        .getDuration().getStartDate())).isGreaterThanOrEqualTo(0);
    }

    public void verifyTripsAreSortedByPurchaseDateInResponse() {
        verifyTripsAreSortedByPurchaseDate(tripSummaryRS);
    }

    public void verifyTripsAreSortedByPurchaseDate(RetrieveTripSummaryRS tripSummaryRS) {
        if (tripSummaryRS.getTripSummary().size() > 1) {
            assertThat((getTrip(tripSummaryRS, 0).getPurchaseDate())
                    .compare(getTrip(tripSummaryRS, 1).getPurchaseDate())).isGreaterThanOrEqualTo(0);
        }
    }

    public void verifyTripsAreSortedByPurchaseDateInBothPages() {
        verifyTripsAreSortedByPurchaseDate(firstPageOfTripSummaryRS);
        verifyTripsAreSortedByPurchaseDate(secondPageOfTripSummaryRS);
        assertThat((getTrip(firstPageOfTripSummaryRS, 0).getPurchaseDate())
                .compare(getTrip(secondPageOfTripSummaryRS, 0).getPurchaseDate())).isGreaterThanOrEqualTo(0);
    }

    public void verifyAddressLatitudeLongitude() {
        for (TripSummary trip : tripSummaryRS.getTripSummary()) {
            for (Reservation reservation : trip.getReservation()) {
                if (reservation.getProductVertical().equals("Car") ||
                        reservation.getProductVertical().equals("Hotel")) {
                    if (reservation.getLocation().getOrigin() != null) {
                        assertThat(reservation.getLocation().getOrigin().getLatLong()).isNotNull();
                    }
                }
            }
        }
    }

    public void verifyCheckInCheckOutTime() {
        for (TripSummary trip : tripSummaryRS.getTripSummary()) {
            for (Reservation reservation : trip.getReservation()) {
                if (reservation.getProductVertical().equals("Hotel")) {
                    LOGGER.info("Itinerary:  " + trip.getItineraryNumber());
                    if (((HotelDuration) reservation.getDuration()).getCheckOutTime() != null) {
                        assertThat(((HotelDuration) reservation.getDuration()).getCheckInTime()).isNotNull();
                    }
                }
            }
        }
    }

    public void verifyCancelledTripsAreAbsent() {
        for (TripSummary trip : tripSummaryRS.getTripSummary()) {
            for (Reservation reservation : trip.getReservation()) {
                if ((reservation.getInformation().getBookingStatus().contains("Refund")) ||
                         (reservation.getInformation().getBookingStatus().contains("Cancelled"))) {
                    fail("The trip with status " + reservation.getInformation().getBookingStatus() + " is present");
                }
            }
        }
    }

    private String setSortingType(String sortingMethod) {
        String[] sorting =  sortingMethod.split(" ");
        return sorting[0] + "Date";
    }

    private Reservation getReservationFromTripSummary(RetrieveTripSummaryRS tripSummaryRS,
                                                      int tripSummaryPosition, int reservationPosition) {
        return getTrip(tripSummaryRS, tripSummaryPosition).getReservation().get(reservationPosition);
    }

    private TripSummary getTripSummaryFromResponse(int position) {
        if (tripSummaryRS.getTripSummary().isEmpty()) {
            throw new PendingException("Customer has no booked trips");
        }
        else {
            return getTrip(tripSummaryRS, position);
        }
    }

    private TripSummary getTrip(RetrieveTripSummaryRS tripSummaryRS, int position)  {
        return tripSummaryRS.getTripSummary().get(position);
    }

    public void verifyRandomCreditCardFromCustomerProfile() {
        boolean isCreditCardPresent = false;
        for (AccountData paymentCard : customerProfileRS.getAccountData()) {
            if ((((PaymentCard) paymentCard).getCardNumber().substring(12))
                    .equals(getLastDigitsOfRandomCreditCard())) {
                assertThat(((PaymentCard) paymentCard).getCardNickName())
                        .isEqualTo(userInformation.getRandomCreditCardNickName());
                assertThat(((PaymentCard) paymentCard).getPaymentCardType())
                        .isEqualTo(userInformation.getCcType());
                assertThat(((PaymentCard) paymentCard).getExpirationDate().getYear())
                        .isEqualTo(userInformation.getExpirationDate().getYear());
                assertThat(((PaymentCard) paymentCard).getExpirationDate().getMonth())
                        .isEqualTo(userInformation.getExpirationDate().getMonth());
                isCreditCardPresent = true;
            }
        }
        assertThat(isCreditCardPresent).isTrue();
    }

    public void verifyRandomCreditCardFromTripSummaryResponse() {
        assertThat(getLastPaymentCardFromTripSummaryResponse().getCardNumber().substring(12))
                .isEqualTo(getLastDigitsOfRandomCreditCard());
        assertThat(getLastPaymentCardFromTripSummaryResponse().getCardNickName())
                .isEqualTo(userInformation.getRandomCreditCardNickName());
        assertThat(getLastPaymentCardFromTripSummaryResponse().getPaymentCardType())
                .isEqualTo(userInformation.getCcType());
    }

    private PaymentCard getLastPaymentCardFromTripSummaryResponse() {
        return (PaymentCard) tripSummaryRS.getAccountPaymentInfo()
                .getPaymentMethod().get(tripSummaryRS.getAccountPaymentInfo()
                .getPaymentMethod().size() - 1);
    }

    private String getLastDigitsOfRandomCreditCard() {
        return userInformation.getRandomCreditCardNumber().substring(12);
    }


}

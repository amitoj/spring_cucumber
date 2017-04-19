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
import com.hotwire.util.db.mobileapi.MobileApiDao;
import hotwire.view.jaxb.domain.mobileapi.AirSummaryOfCharges;
import hotwire.view.jaxb.domain.mobileapi.CarReservationDetails;
import hotwire.view.jaxb.domain.mobileapi.CarSummaryOfCharges;
import hotwire.view.jaxb.domain.mobileapi.HotelSummaryOfCharges;
import hotwire.view.jaxb.domain.mobileapi.Reservation;
import hotwire.view.jaxb.domain.mobileapi.SummaryOfCharges;
import hotwire.view.jaxb.domain.mobileapi.customer.RetrieveTripDetailsRQ;
import hotwire.view.jaxb.domain.mobileapi.customer.RetrieveTripDetailsRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-dsobko
 * Since: 01/19/15
 */
public class TripDetailsService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripDetailsService.class.getName());

    private RetrieveTripDetailsRS retrieveTripDetailsResponse;

    private RequestProperties requestProperties;
    private RequestPaths paths;
    private SimpleJdbcDaoSupport databaseSupport;
    private String randomEmailId;
    private UserInformation userInformation;



    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }


    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setDatabaseSupport(SimpleJdbcDaoSupport databaseSupport) {
        this.databaseSupport = databaseSupport;
    }

    public void setRandomEmailId(String randomEmailId) {
        this.randomEmailId = randomEmailId;
    }

    public void executeTripDetailsRequestForRandomItinerary(String vertical) {
        RetrieveTripDetailsRQ retrieveTripDetailsRequest = new RetrieveTripDetailsRQ();
        retrieveTripDetailsRequest.setReservationRQ(getReservationRequestWithVertical(vertical));
        try {
            client.reset()
                    .path(paths.getTripDetailsPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_XML_TYPE)
                    .header("Authorization", "Bearer " + requestProperties.getOauthToken());

            retrieveTripDetailsResponse = client.post(retrieveTripDetailsRequest, RetrieveTripDetailsRS.class);
            fail("Test failed because trip details request for random itinerary was processed with no error");
        }
        catch (NotAuthorizedException e) {
            e.getResponse().bufferEntity();
            LOGGER.error("Trip details returned status " + e.getResponse().getStatus());
            LOGGER.error("Response body:\n" + e.getResponse().getEntity());
            String response = e.getResponse().readEntity(String.class);
//            assertThat(response).contains("Security Login error : you are not authorized for this action");
            assertThat(response).contains("1002");
        }
    }

    public void executeTripDetailsRequestForItinerary(String user, String vertical) {
        RetrieveTripDetailsRQ retrieveTripDetailsRequest = new RetrieveTripDetailsRQ();
        if (user.equalsIgnoreCase("payable")) {
            retrieveTripDetailsRequest.setReservationRQ(getReservationRequestWithVerticalFromPayableUser(vertical));
        }
        else {
            retrieveTripDetailsRequest.setReservationRQ(getReservationRequestWithVerticalFromRandomUser(vertical));
        }
        try {
            client.reset()
                    .path(paths.getTripDetailsPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_XML_TYPE)
                    .header("Authorization", "Bearer " + requestProperties.getOauthToken());

            retrieveTripDetailsResponse = client.post(retrieveTripDetailsRequest, RetrieveTripDetailsRS.class);

        }
        catch (WebApplicationException e) {
            LOGGER.error("Trip details request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Trip details  request has failed with  " +  message, e);
        }
    }

    private RetrieveTripDetailsRQ.ReservationRQ getReservationRequestWithVertical(String vertical) {
        RetrieveTripDetailsRQ.ReservationRQ reservationRQ = new RetrieveTripDetailsRQ.ReservationRQ();
        reservationRQ.setItineraryNumber(getRandomItinerary(vertical));
        return reservationRQ;
    }

    private RetrieveTripDetailsRQ.ReservationRQ getReservationRequestWithVerticalFromRandomUser(String vertical) {
        RetrieveTripDetailsRQ.ReservationRQ reservationRQ = new RetrieveTripDetailsRQ.ReservationRQ();
        reservationRQ.setItineraryNumber(getItineraryFromRandomUser(vertical));
        return reservationRQ;
    }

    private RetrieveTripDetailsRQ.ReservationRQ getReservationRequestWithVerticalFromPayableUser(String vertical) {
        RetrieveTripDetailsRQ.ReservationRQ reservationRQ = new RetrieveTripDetailsRQ.ReservationRQ();
        reservationRQ.setItineraryNumber(getItineraryFromPayableUser(vertical));
        return reservationRQ;
    }

    private String getItineraryFromRandomUser(String vertical) {
        return new MobileApiDao(databaseSupport).getItineraryFromRandomCustomer(vertical.substring(0, 1).toUpperCase(),
                randomEmailId);
    }

    private String getItineraryFromPayableUser(String vertical) {
        return new MobileApiDao(databaseSupport).getItineraryFromRandomCustomer(vertical.substring(0, 1).toUpperCase(),
                userInformation.getEmailId());
    }

    private String getRandomItinerary(String vertical) {
        return new MobileApiDao(databaseSupport).getRandomItinerary(vertical.substring(0, 1).toUpperCase(),
                randomEmailId);
    }


    public void verifyRetrieveTripDetailsResponse() {
        assertThat(retrieveTripDetailsResponse.getErrors()).isNull();
        assertThat(retrieveTripDetailsResponse.getTripDetails().getItineraryNumber()).isNotNull();

        assertThat(getReservationFromTripDetails(0).getProductVertical()).isNotNull();
    }

    public void verifyRetrieveTripDetailsResponseIsAbsent() {
        assertThat(retrieveTripDetailsResponse).isNull();
    }

    public void verifyAddressLongitudeLatitudeTripDetails() {
        assertThat(getReservationFromTripDetails(0).getLocation().getOrigin().getLatLong()).isNotNull();
        assertThat(getReservationFromTripDetails(0).getLocation().getOrigin().getAddress()).isNotNull();
    }

    public void verifyItineraryInTripDetails() {
        assertThat(retrieveTripDetailsResponse.getTripDetails().getItineraryNumber()).isNotEmpty();
    }

    public void verifyBookingStatusInTripDetails() {
        assertThat(getReservationFromTripDetails(0).getInformation().getBookingStatus()).isNotEmpty();
    }

    public void verifyProdVerticalInTripDetails(String vertical) {
        if (retrieveTripDetailsResponse.getTripDetails().getReservation().size() == 1) {
            assertThat(getReservationFromTripDetails(0).getProductVertical()).isEqualToIgnoringCase(vertical);
        }
        else {
            Set<String> verticals = new HashSet<>();
            for (Reservation reservation : retrieveTripDetailsResponse.getTripDetails().getReservation()) {
                verticals.add(reservation.getProductVertical().toUpperCase());
            }
            assertThat(verticals).contains(vertical.toUpperCase());
        }
    }

    public void verifyBookingDatesLocationInTripDetails() {
        assertThat(getReservationFromTripDetails(0).getDuration().getStartDate()).isNotNull();
        assertThat(getReservationFromTripDetails(0).getDuration().getEndDate()).isNotNull();
        assertThat(getReservationFromTripDetails(0).getLocation()).isNotNull();
    }

    public void verifyCarReservationDetails() {
        CarReservationDetails carReservationDetails = (CarReservationDetails) getReservationFromTripDetails(0)
            .getReservationDetails();
        assertThat(carReservationDetails.getDropOffLocation()).isNotNull();
        assertThat(carReservationDetails.getPickupLocation()).isNotNull();
        assertThat(carReservationDetails.getDropoffTime()).isNotNull();
        assertThat(carReservationDetails.getPickupTime()).isNotNull();
    }

    public void verifySummaryOfChargesInTripDetails() {
        for (Reservation reservation : retrieveTripDetailsResponse.getTripDetails().getReservation()) {
            SummaryOfCharges summaryOfCharges = reservation.getReservationDetails().getSummaryOfCharges();
            assertThat(summaryOfCharges.getTotal()).isNotNull();
            assertThat(summaryOfCharges.getSubTotal()).isNotNull();
            if (reservation.getProductVertical().equalsIgnoreCase("hotel")) {
                HotelSummaryOfCharges hotelSummaryOfCharges = (HotelSummaryOfCharges) summaryOfCharges;
                assertThat(hotelSummaryOfCharges.getResortFeeTotal()).isNotNull();
                assertThat(hotelSummaryOfCharges.getTotalWithResortFee()).isNotNull();
                assertThat(hotelSummaryOfCharges.getResortFeeTotal() + hotelSummaryOfCharges.getTotal()).
                        isGreaterThanOrEqualTo(hotelSummaryOfCharges.getTotalWithResortFee());
            }
            else if (reservation.getProductVertical().equalsIgnoreCase("car")) {
                CarSummaryOfCharges carSummaryOfCharges = (CarSummaryOfCharges) summaryOfCharges;
                assertThat(carSummaryOfCharges.getDailyRate()).isNotNull();
                assertThat(carSummaryOfCharges.getTaxesAndFees()).isNotNull();
            }
            else if (reservation.getProductVertical().equalsIgnoreCase("air")) {
                AirSummaryOfCharges airSummaryOfCharges = (AirSummaryOfCharges) summaryOfCharges;
                assertThat(airSummaryOfCharges.getTicketPrice()).isNotNull();
                assertThat(airSummaryOfCharges.getNumberOfPassengers()).isNotNull();
            }
        }
    }

    private Reservation getReservationFromTripDetails(int position) {
        return retrieveTripDetailsResponse.getTripDetails().getReservation().get(position);
    }


}

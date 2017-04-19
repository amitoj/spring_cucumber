/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import cucumber.api.PendingException;
import hotwire.view.jaxb.domain.mobileapi.Address;
import hotwire.view.jaxb.domain.mobileapi.HotelReservationDetails;
import hotwire.view.jaxb.domain.mobileapi.HotelSummaryOfCharges;
import hotwire.view.jaxb.domain.mobileapi.Name;
import hotwire.view.jaxb.domain.mobileapi.PersonInfo;
import hotwire.view.jaxb.domain.mobileapi.Reservation;
import hotwire.view.jaxb.domain.mobileapi.RoomInfo;
import hotwire.view.jaxb.domain.mobileapi.TripDetails;
import hotwire.view.jaxb.domain.mobileapi.booking.BookingRQ;
import hotwire.view.jaxb.domain.mobileapi.booking.BookingRS;
import hotwire.view.jaxb.domain.mobileapi.booking.hotel.HotelBookingRQ;
import hotwire.view.jaxb.domain.mobileapi.search.ImageUrls;
import hotwire.view.jaxb.domain.mobileapi.search.SearchDetailsRQ;
import hotwire.view.jaxb.domain.mobileapi.search.SearchDetailsRS;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.AssociatedHotelSearchSolution;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.HotelMetaData;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.LevelOfOpacity;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.NearbyAirport;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.OpaqueHotelSearchSolutionDetails;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.RetailHotelSearchSolutionDetails;
import org.fest.assertions.Delta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;

import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-ngolodiuk
 * Since: 1/8/15
 */
public class HotelPurchaseService extends MobileApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelPurchaseService.class.getName());

    private static final String OPAQUE = "opaque";
    private static final String RETAIL = "retail";

    private SearchDetailsRS detailsResponse;
    private BookingRS bookingResponse;

    private RequestProperties requestProperties;
    private RequestPaths paths;
    private HashMap<String, String> hotelCouponCodeMap;
    private PurchaseService purchaseService;

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public void setHotelCouponCodeMap(HashMap<String, String>  hotelCouponCodeMap) {
        this.hotelCouponCodeMap = hotelCouponCodeMap;
    }

    /*REQUEST BUILDING SECTION*/

    public void executeDetailsRequest(String detailsType) {
        SearchDetailsRQ detailsRequest = createHotelDetailsRequest();

        try {
            client.reset();
            if (detailsType.equalsIgnoreCase(OPAQUE)) {
                client.path(paths.getHotelDetailsOpaquePath());
            }
            if (detailsType.equalsIgnoreCase(RETAIL)) {
                client.path(paths.getHotelDetailsRetailPath());
            }
            client.accept(MediaType.APPLICATION_XML_TYPE)
                .header("X-Channel-ID", requestProperties.getChannelID());

            detailsResponse = client.post(detailsRequest, SearchDetailsRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Hotel details request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            if (message.contains("3018")) {
                throw new PendingException(
                        "The deal's gone. The good ones come and go fast. Please select another deal.");
            }
            fail("Hotel details request has failed with " +  message, e);
        }
    }

    private SearchDetailsRQ createHotelDetailsRequest() {
        SearchDetailsRQ request = new SearchDetailsRQ();

        purchaseService.setClientInfoForDetails(request);

        request.setResultID(requestProperties.getResultId());

        return request;
    }

    public void executeSecureBookingRequest() throws DatatypeConfigurationException {
        BookingRQ bookingRequest = createSecureHotelBookingRequest();

        bookingRequest.setCouponCode(requestProperties.getPromoCode());

        try {
            client.reset()
                .path(paths.getHotelSecureBookingPath())
                .accept(MediaType.APPLICATION_XML_TYPE)
                .header("Authorization", "Bearer " + requestProperties.getOauthToken());

            bookingResponse = client.post(bookingRequest, BookingRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Hotel secure booking request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Hotel secure booking request has failed with " +  message, e);
        }
    }

    public void executeBookingRequest() throws DatatypeConfigurationException {
        BookingRQ bookingRequest = createSecureHotelBookingRequest();

        bookingRequest.setCouponCode(requestProperties.getPromoCode());

        try {
            client.reset().path(paths.getHotelBookingPath())
                    .accept(MediaType.APPLICATION_XML_TYPE);

            bookingResponse = client.post(bookingRequest, BookingRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Hotel booking request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            if (message.contains("3009")) {
                throw new PendingException(
                        "The user has not enough hot dollars for this payment");
            }
            fail("Hotel booking request has failed with   " +  message, e);
        }
    }

    public void executeBookingRequestAndCatchCardValidationError() throws DatatypeConfigurationException {
        executeBookingRequestAndCatchError("This credit card number is invalid", "1006", "invalid credit card");
    }

    public void executeBookingRequestAndCatchHotDollarsError() throws DatatypeConfigurationException {
        executeBookingRequestAndCatchError("You're out of HotDollars. Enter payment info.", "3009",
             "account without Hot dollars");
    }

    public void executeBookingRequestAndCatchCouponCodeError() throws DatatypeConfigurationException {
        executeBookingRequestAndCatchError("This promo code isn't valid.", "3010",
             "invalid coupon");
    }

    private void executeBookingRequestAndCatchError(String errorMessage, String errorCode, String failCondition)
        throws DatatypeConfigurationException {
        BookingRQ bookingRequest = createSecureHotelBookingRequest();

        bookingRequest.setCouponCode(requestProperties.getPromoCode());

        try {
            client.reset().path(paths.getHotelBookingPath())
                    .accept(MediaType.APPLICATION_XML_TYPE);

            bookingResponse = client.post(bookingRequest, BookingRS.class);
            fail("Test failed since booking with " + failCondition +  " was processed with no error");
        }
        catch (InternalServerErrorException e) {
            e.getResponse().bufferEntity();
            LOGGER.error("Booking returned status " + e.getResponse().getStatus());
            LOGGER.error("Response body:\n" + e.getResponse().getEntity());
            String response = e.getResponse().readEntity(String.class);
            assertThat(response).contains(errorMessage);
            assertThat(response).contains(errorCode);
        }
    }

    private BookingRQ createSecureHotelBookingRequest() throws DatatypeConfigurationException {
        UserInformation userInfo = requestProperties.getUserInformation();

        HotelBookingRQ request = new HotelBookingRQ();
        purchaseService.setClientInfo(request);

        if (requestProperties.getIsPaymentRequired()) {
            purchaseService.setPaymentInstrument(userInfo, request);
        }

        HotelBookingRQ.HotelGuests hotelGuests = new HotelBookingRQ.HotelGuests();
        request.setHotelGuests(hotelGuests);

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(new Name());
        personInfo.getName().setFirstName(userInfo.getFirstName());
        personInfo.getName().setLastName(userInfo.getLastName());
        personInfo.setPrimary(true);
        personInfo.setPhoneNumber(userInfo.getPrimaryPhoneNumber());
        personInfo.setEmailAddress(userInfo.getEmailId());
        request.getHotelGuests().getGuestInfo().add(personInfo);

        request.setAcceptedDepositTerms(true);
        purchaseService.setSearchResultReference(request, requestProperties.getResultId());
        return request;
    }

    public void setCouponCode(String type) {
        String couponCode = hotelCouponCodeMap.get(type);
        LOGGER.info("Setting " + type + " coupon code in purchase params: " + couponCode);
        requestProperties.setPromoCode(couponCode);
    }


    /*RESPONSE VERIFICATIONS SECTION*/

    protected OpaqueHotelSearchSolutionDetails getOpaqueDetailsSolution() {
        return (OpaqueHotelSearchSolutionDetails) detailsResponse.getSearchResultDetails().getSolution().get(0);
    }

    protected RetailHotelSearchSolutionDetails getRetailDetailsSolution() {
        return (RetailHotelSearchSolutionDetails) detailsResponse.getSearchResultDetails().getSolution().get(0);
    }

    public void checkDetailsResultExist() {
        purchaseService.checkDetailsResultExist(detailsResponse);
    }

    public void checkDetailsResponseExists() {
        purchaseService.checkDetailsResponseExists(detailsResponse);
    }

    public void checkBookingResultExist() {
        TripDetails tripDetails = bookingResponse.getBookingConfirmation().getTripDetails();
        assertThat(tripDetails.getReservation().size()).isEqualTo(1);
        assertThat(tripDetails.getItineraryNumber()).isNotNull().matches("\\d+");
    }

    public void checkBillingInfo() {
        purchaseService.verifyBillingInfo(bookingResponse);
    }

    public void verifyReservationInfo() {
        Reservation reservation =
            bookingResponse.getBookingConfirmation().getTripDetails().getReservation().get(0);
        purchaseService.verifyReservationInfoDetails(reservation);

        assertThat(reservation.getProductVertical()).isEqualToIgnoringCase("Hotel");
        assertThat(reservation.getReservationDetails().getOpacityCode()).isIn(new String[]{"Y", "N"});
    }

    public void checkBookingSummaryOfCharges() {
        HotelSummaryOfCharges charges = (HotelSummaryOfCharges) bookingResponse.getBookingConfirmation().
            getTripDetails().getReservation().get(0).getReservationDetails().getSummaryOfCharges();
        assertThat(charges.getTotal()).isGreaterThan(charges.getSubTotal());
        assertThat(charges.getDailyRate()).isNotNull();
        assertThat(charges.getTaxesAndFees()).isNotNull();
        assertThat(charges.getResortFeeTotal()).isNotNull();
        assertThat(charges.getTotalWithResortFee()).isNotNull();
    }

    public void checkTravelAdvisory() {
        purchaseService.verifyTravelAdvisory(bookingResponse);
    }

    public void checkHotelDetails() {
        HotelReservationDetails details = extractHotelReservationDetails();
        assertThat(details.getHotelDetails()).isNotNull();
        assertThat(details.getHotelDetails().getHotelName()).isNotEmpty();
        assertThat(details.getHotelDetails().getAddress()).isNotNull();
        assertThat(details.getHotelDetails().getStarRating()).isNotNull();
        assertThat(details.getHotelDetails().getNeighborhoodId()).isNotNull();
    }

    protected HotelReservationDetails extractHotelReservationDetails() {
        Reservation reservation = bookingResponse.getBookingConfirmation().getTripDetails().getReservation().get(0);
        return (HotelReservationDetails) reservation.getReservationDetails();
    }

    public void checkBookingNumberOfHotelAmenities(int i) {
        HotelReservationDetails hotelReservationDetails = extractHotelReservationDetails();
        assertThat(hotelReservationDetails.getHotelDetails().getHotelAmenities()).isNotNull();
        assertThat(hotelReservationDetails.getHotelDetails().getHotelAmenities().size()).isGreaterThanOrEqualTo(i);
    }

    public void checkBookingNumberOfHotelPhotos() {
        HotelReservationDetails hotelReservationDetails = extractHotelReservationDetails();
        assertThat(hotelReservationDetails.getHotelDetails().getHotelPhotos()).isNotNull();
    }

    public void checkExtendMyStay() {
        HotelReservationDetails hotelReservationDetails = extractHotelReservationDetails();
        assertThat(hotelReservationDetails.getExtendMyStay()).isNotNull();
    }

    public void checkRoomInfo() {
        HotelReservationDetails hotelReservationDetails = extractHotelReservationDetails();
        assertThat(hotelReservationDetails.getRoomInfo()).isNotNull();
    }

    public void verifyDetailsMetaData() {
        assertThat(detailsResponse.getSearchResultDetails().getSearchCriteria()).isNotNull();
        assertThat(detailsResponse.getMetaData()).isNotNull();
        HotelMetaData metaData = (HotelMetaData) detailsResponse.getMetaData();
        assertThat(metaData.getNeighborhoods().getNeighborhood().size()).isEqualTo(1);
    }

    public void verifyRevealFieldsForDetails() {
        OpaqueHotelSearchSolutionDetails solutionDetails = getOpaqueDetailsSolution();

        assertThat(solutionDetails.getLevelOfOpacity()).isEqualTo(LevelOfOpacity.FULLY_DISCLOSED.value());
        assertThat(solutionDetails.getHotelName()).isNotEmpty();
        Address hotelAddress = solutionDetails.getHotelAddress();
        assertThat(hotelAddress).isNotNull();
        assertThat(hotelAddress.getAddressLine1()).isNotEmpty();
        assertThat(hotelAddress.getCity()).isNotEmpty();
        assertThat(hotelAddress.getCountry()).isNotEmpty();
        ImageUrls hotelImageUrls = solutionDetails.getHotelImageUrls();
        assertThat(hotelImageUrls).isNotNull();
        assertThat(solutionDetails.getHotelLatLong()).isNotNull();
        if (solutionDetails.getRatingInfo().getTripAdvisorRating() != null) {
            assertThat(solutionDetails.getRatingInfo().getTripAdvisorNumOfReview()).isNotNull();
            assertThat(solutionDetails.getRatingInfo().getTripAdvisorNumOfReviewMessage()).isNotNull();
        }
    }

    public void verifyOpacityIsMaintainedForDetails() {
        OpaqueHotelSearchSolutionDetails solution = getOpaqueDetailsSolution();
        assertThat(solution.getLevelOfOpacity()).isEqualTo(LevelOfOpacity.FULLY_OPAQUE.value());
        assertThat(solution.getHotelName()).isNullOrEmpty();
        assertThat(solution.getHotelAddress()).isNull();
        assertThat(solution.getHotelImageUrls()).isNull();
    }



    public void verifySummaryOfChargesOnDetails(String solutionType) {
        if (solutionType.equalsIgnoreCase(OPAQUE)) {
            OpaqueHotelSearchSolutionDetails details = (OpaqueHotelSearchSolutionDetails) detailsResponse.
                    getSearchResultDetails().getSolution().get(0);
            HotelSummaryOfCharges charges = details.getSummaryOfCharges();
            verifySummaryOfCharges(charges);
        }
        else {
            RetailHotelSearchSolutionDetails details = (RetailHotelSearchSolutionDetails) detailsResponse.
                    getSearchResultDetails().getSolution().get(0);
            HotelSummaryOfCharges charges = details.getSummaryOfCharges();
            verifySummaryOfCharges(charges);
        }
    }

    public void verifySummaryOfChargesOnBooking() {
        HotelSummaryOfCharges charges = (HotelSummaryOfCharges) extractHotelReservationDetails().getSummaryOfCharges();
        verifySummaryOfCharges(charges);
    }

    private void verifySummaryOfCharges(HotelSummaryOfCharges charges) {
        assertThat(charges.getTotal()).isGreaterThan(charges.getSubTotal());
        assertThat(charges.getSubTotal()).isNotNull();
        assertThat(charges.getResortFeeTotal()).isNotNull();
        assertThat(charges.getTotalWithResortFee()).isNotNull();
        assertThat(charges.getExtraPersonFees()).isNotNull();
        assertThat(charges.getDailyRate()).isNotNull();
        assertThat(charges.getTaxesAndFees()).isNotNull();
        float resortFeeTotal = charges.getResortFeeTotal();
        float total =  charges.getTotal();
        float totalWithResortFee =  charges.getTotalWithResortFee();
        float delta = 0.1f;
        assertThat(resortFeeTotal + total).isEqualTo(totalWithResortFee, Delta.delta(delta));
//        assertThat(charges.getResortFeeTotal() + charges.getTotal()).
//                isGreaterThanOrEqualTo(charges.getTotalWithResortFee());
    }

    public void verifyOpaqueDetailsNumberOfHotelAmenities(int arg1) {
        assertThat(getOpaqueDetailsSolution().getHotelAmenities().getAmenity().size()).isGreaterThanOrEqualTo(arg1);
    }

    public void verifyOpaqueDetailsNumberOfSampleHotels(String solutionType, int arg1) {
        if (solutionType.equalsIgnoreCase(OPAQUE)) {
            assertThat(getOpaqueDetailsSolution().getStarRatingHotels().getSampleHotelChain().size())
                    .isGreaterThan(arg1);
        }
        else {
            assertThat(getRetailDetailsSolution().getStarRatingHotels().getSampleHotelChain().size())
                    .isGreaterThan(arg1);
        }
    }

    public void verifyOpaqueDetailsAdditionalInfo(String solutionType) {
        if (solutionType.equalsIgnoreCase(OPAQUE)) {
            assertThat(getOpaqueDetailsSolution().getHotelAdditionalInfo()).isNotNull();
        }
        else {
            assertThat(getRetailDetailsSolution().getHotelAdditionalInfo()).isNotNull();
        }
    }

    public void verifyOpaqueDetailsAccessibility(String solutionType) {
        if (solutionType.equalsIgnoreCase(OPAQUE)) {
            assertThat(getOpaqueDetailsSolution().getHotelAdditionalInfo().getAccessibility()).isNotEmpty();
        }
        else {
            assertThat(getRetailDetailsSolution().getHotelAdditionalInfo().getAccessibility()).isNotEmpty();
        }
    }

    public void verifyOpaqueDetailsKnowBeforeYouGo(String solutionType) {
        if (solutionType.equalsIgnoreCase(OPAQUE)) {
            assertThat(getOpaqueDetailsSolution().getHotelAdditionalInfo().getKnowBeforeYouGo()).isNotEmpty();
        }
        else {
            assertThat(getRetailDetailsSolution().getHotelAdditionalInfo().getKnowBeforeYouGo()).isNotEmpty();
        }
    }

    public void verifyOpaqueDetailsCancellationPolicy(String solutionType) {
        if (solutionType.equalsIgnoreCase(OPAQUE)) {
            assertThat(getOpaqueDetailsSolution().getHotelAdditionalInfo().getCancellationPolicy()).isNotEmpty();
        }
        else {
            assertThat(getRetailDetailsSolution().getHotelAdditionalInfo().getCancellationPolicy()).isNotEmpty();
        }
    }

    public void verifyNearbyAirports() {
        OpaqueHotelSearchSolutionDetails solutionDetails = getOpaqueDetailsSolution();
        assertThat(solutionDetails.getNearbyAirportsInfo().getNearbyAirports().size())
                .as("List of nearby airports is empty.")
                .isGreaterThan(0);
        NearbyAirport nearbyAirport = solutionDetails.getNearbyAirportsInfo().getNearbyAirports().get(0);
        assertThat(nearbyAirport.getAirportCode()).isNotNull();
        assertThat(nearbyAirport.getAirportName()).isNotNull();
        assertThat(nearbyAirport.getDistanceFromNeighborhood()).isNotNull();
    }

    public void verifyNumberOfTripAdvisorReviews(int arg1) {
        assertThat(getRetailDetailsSolution().getTripAdvisorReviews().getTripAdvisorReview().size()).
                isGreaterThan(arg1);
    }

    public void verifyRetailHotelAddress() {
        assertThat(getRetailDetailsSolution().getHotelAddress()).isNotNull();
    }

    public void verifyNumberOfHotelImages(int arg1) {
        assertThat(getRetailDetailsSolution().getHotelImageUrls().getImageURL().size()).isGreaterThan(arg1);
    }

    public void verifyLastHotelBookedData() {
        OpaqueHotelSearchSolutionDetails solution = getOpaqueDetailsSolution();
        if (solution.getLastHotelBookedInfo() != null) {
            assertThat(solution.getLastHotelBookedInfo().getHotelName()).isNotEmpty();
        }
    }

    public void verifyDetailsLegalLinks(String solutionType) {
        if (solutionType.equalsIgnoreCase(OPAQUE)) {
            OpaqueHotelSearchSolutionDetails solution = getOpaqueDetailsSolution();
            assertThat(solution.getHotelAdditionalInfo().getLegalLinks().get(0).getHref()).isNotEmpty();
            assertThat(solution.getHotelAdditionalInfo().getLegalLinks().get(0).getRel()).isNotEmpty();
        }
        else {
            RetailHotelSearchSolutionDetails solution = getRetailDetailsSolution();
            assertThat(solution.getHotelAdditionalInfo().getLegalLinks().get(0).getHref()).isNotEmpty();
            assertThat(solution.getHotelAdditionalInfo().getLegalLinks().get(0).getRel()).isNotEmpty();
        }

    }

    public void verifyRoomTypesInfo() {
        RoomInfo roomInfo = getRetailDetailsSolution().getRoomInfos().getRoomInfo().get(0);
        assertThat(roomInfo.getResultID()).isNotEmpty();
        assertThat(roomInfo.getAverageRatePerNight()).isNotNull();
        assertThat(roomInfo.getRoomType()).isNotEmpty();
        assertThat(roomInfo.getOccupancy()).isGreaterThanOrEqualTo(2);
    }

    public void verifyAssociatedDealsInfo() {
        if (!getRetailDetailsSolution().getAssociatedUpgradeSolutionInfo().getAssociatedSolution().isEmpty()) {
            AssociatedHotelSearchSolution upgradeDeal = getRetailDetailsSolution().getAssociatedUpgradeSolutionInfo().
                getAssociatedSolution().get(0);
            assertThat(upgradeDeal.getNeighborhoodId()).isNotNull();
            assertThat(upgradeDeal.getStarRating()).isNotNull();
            assertThat(upgradeDeal.getTripTotal()).isNotNull();
            AssociatedHotelSearchSolution associatedDeal = getRetailDetailsSolution().getAssociatedDealSolutionInfo().
                getAssociatedSolution().get(0);
            assertThat(associatedDeal.getNeighborhoodId()).isNotNull();
            assertThat(associatedDeal.getStarRating()).isNotNull();
            assertThat(associatedDeal.getTripTotal()).isNotNull();
        }
    }

    public void verifyDistanceMeasurementUnit(String unit) {
        OpaqueHotelSearchSolutionDetails solutionDetails = getOpaqueDetailsSolution();
        assertThat(unit.toLowerCase()).isEqualToIgnoringCase(solutionDetails.getNearbyAirportsInfo().
                getNearbyAirports().get(0).getDistanceUnit());
    }

    public void verifyCurrencyInBookingResponse() {
        String tripDetailsCurrency = bookingResponse.getBookingConfirmation().getTripDetails().getLocalCurrencyCode();
        String usersCurrency = requestProperties.getCurrencyCode();
        assertThat(usersCurrency).as("User's currency was " + usersCurrency +
                " and currency in booking response is " + tripDetailsCurrency).
                isEqualToIgnoringCase(tripDetailsCurrency);
    }

    public void verifyHotDollarsInfo() {
        TripDetails tripDetails = bookingResponse.getBookingConfirmation().getTripDetails();
        assertThat(tripDetails.getTripTotal()).isEqualTo(0.0f);
        assertThat(tripDetails.getHotDollars()).isGreaterThan(0.0f);
    }
}

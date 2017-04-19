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
import com.hotwire.testing.UnimplementedTestException;
import cucumber.api.PendingException;
import hotwire.eis.db.DatabaseCodes;
import hotwire.view.jaxb.domain.mobileapi.CarReservationDetails;
import hotwire.view.jaxb.domain.mobileapi.CarSummaryOfCharges;
import hotwire.view.jaxb.domain.mobileapi.LegalLink;
import hotwire.view.jaxb.domain.mobileapi.Name;
import hotwire.view.jaxb.domain.mobileapi.PersonInfo;
import hotwire.view.jaxb.domain.mobileapi.Reservation;
import hotwire.view.jaxb.domain.mobileapi.Terms;
import hotwire.view.jaxb.domain.mobileapi.TravelerAdvisory;
import hotwire.view.jaxb.domain.mobileapi.TripDetails;
import hotwire.view.jaxb.domain.mobileapi.booking.BookingRQ;
import hotwire.view.jaxb.domain.mobileapi.booking.BookingRS;
import hotwire.view.jaxb.domain.mobileapi.booking.car.CarBookingRQ;
import hotwire.view.jaxb.domain.mobileapi.insurance.Insurance;
import hotwire.view.jaxb.domain.mobileapi.search.SearchDetailsRQ;
import hotwire.view.jaxb.domain.mobileapi.search.SearchDetailsRS;
import hotwire.view.jaxb.domain.mobileapi.search.car.AirportCarLocation;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarInfo;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarMetaData;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarSearchCriteria;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarSearchDetailsSolution;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarSearchSolution;
import hotwire.view.jaxb.domain.mobileapi.search.car.LocalCarLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-ngolodiuk
 * Since: 1/13/15
 */
public class CarPurchaseService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelPurchaseService.class.getName());

    private SearchDetailsRS detailsResponse;
    private BookingRS bookingResponse;

    private RequestProperties requestProperties;
    private RequestPaths paths;
    private PurchaseService purchaseService;


    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void executeSecureBookingRequest() throws DatatypeConfigurationException {
        BookingRQ bookingRequest = createSecureCarBookingRequest();

        try {
            client.reset()
                .path(paths.getCarSecureBookingPath())
                .accept(MediaType.APPLICATION_XML_TYPE)
                .header("Authorization", "Bearer " + requestProperties.getOauthToken());

            bookingResponse = client.post(bookingRequest, BookingRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Car Secure booking request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Car Secure booking request has failed with " +  message, e);
        }
    }

    public void executeBookingRequest() throws DatatypeConfigurationException {
        BookingRQ bookingRequest = createSecureCarBookingRequest();

        try {
            client.reset()
                .path(paths.getCarBookingPath())
                .accept(MediaType.APPLICATION_XML_TYPE);

            bookingResponse = client.post(bookingRequest, BookingRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Car booking request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            if (message.contains("3009")) {
                throw new PendingException(
                        "The user has not enough hot dollars for this payment");
            }
            fail("Car booking request has failed with " +  message, e);
        }
    }

    public void executeBookingRequestAndCatchCardValidationError() throws DatatypeConfigurationException {
        executeBookingRequestAndCatchError("This credit card number is invalid", "1006", "invalid credit card");
    }

    public void executeBookingRequestAndCatchHotDollarsError() throws DatatypeConfigurationException {
        executeBookingRequestAndCatchError("You're out of HotDollars. Enter payment info.", "3009",
                "account without Hotdollars");
    }

    private void executeBookingRequestAndCatchError(String errorMessage, String errorCode, String failCondition)
        throws DatatypeConfigurationException {
        BookingRQ bookingRequest = createSecureCarBookingRequest();

        try {
            client.reset()
                    .path(paths.getCarBookingPath())
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

    private BookingRQ createSecureCarBookingRequest() throws DatatypeConfigurationException {
        CarBookingRQ request = new CarBookingRQ();
        UserInformation userInfo = requestProperties.getUserInformation();
        purchaseService.setClientInfo(request);
        purchaseService.setSearchResultReference(request, requestProperties.getResultId());

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(new Name());
        personInfo.getName().setFirstName(userInfo.getDriverFirstName());
        personInfo.getName().setLastName(userInfo.getDriverLastName());
        personInfo.setPrimary(true);
        personInfo.setPhoneNumber(userInfo.getDriverPhoneNumber());
        personInfo.setEmailAddress(userInfo.getDriverEmailAddress());
        personInfo.setAgeUnder25(false);
        request.setDriverInfo(personInfo);

        if (requestProperties.getIsPaymentRequired()) {
            purchaseService.setPaymentInstrument(userInfo, request);
        }
        request.setInsuranceSelected(requestProperties.getIsInsuranceSelected());
        request.setAcceptedDepositTerms(true);
        return request;
    }

    public void executeCarDetails() {
        SearchDetailsRQ detailsRequest = createCarDetailsRequest();

        try {
            client.reset()
                    .path(paths.getCarDetailsPath())
                    .accept(MediaType.APPLICATION_XML_TYPE);

            detailsResponse = client.post(detailsRequest, SearchDetailsRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Car details request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            if (message.contains("3018")) {
                throw new PendingException(
                        "The deal's gone. The good ones come and go fast.");
            }
            fail("Car details request has failed with " +  message, e);
        }
    }

    private SearchDetailsRQ createCarDetailsRequest() {
        SearchDetailsRQ request = new SearchDetailsRQ();

        purchaseService.setClientInfoForDetails(request);
        request.setResultID(requestProperties.getResultId());

        return request;
    }

    public void setInsurance(boolean insuranceSelected) {
        requestProperties.setIsInsuranceSelected(insuranceSelected);
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
        assertThat(reservation.getProductVertical()).isEqualToIgnoringCase("Car");
        assertThat(reservation.getLocation().getDestinationLocation()).isNotEmpty();
    }

    public void verifySummaryOfChargesOnDetails() {
        CarSearchDetailsSolution details = (CarSearchDetailsSolution) detailsResponse.getSearchResultDetails().
            getSolution().get(0);
        CarSummaryOfCharges charges = details.getCarSummaryOfCharges();
        verifySummaryOfCharges(charges);
    }

    public void verifySummaryOfChargesOnBooking() {
        CarSummaryOfCharges charges = (CarSummaryOfCharges) getCarReservationDetails().getSummaryOfCharges();
        assertThat(charges.getAdditionalFeatures().getRentalCarProtectionAmount()).isNotNull();
        verifySummaryOfCharges(charges);
    }

    private void verifySummaryOfCharges(CarSummaryOfCharges charges) {
        assertThat(charges.getTotal()).isGreaterThan(charges.getSubTotal());
        assertThat(charges.getSubTotal()).isNotNull();
        assertThat(charges.getStrikeThruPrice()).isNotNull();
        assertThat(charges.getDailyRate()).isNotNull();
        assertThat(charges.getTaxesAndFees()).isNotNull();
    }

    private CarReservationDetails getCarReservationDetails() {
        return (CarReservationDetails) bookingResponse.getBookingConfirmation().
            getTripDetails().getReservation().get(0).getReservationDetails();
    }

    private CarSearchDetailsSolution getCarSearchDetailsSolution() {
        return (CarSearchDetailsSolution) detailsResponse.getSearchResultDetails().getSolution().get(0);
    }

    public void checkTravelAdvisory() {
        purchaseService.verifyTravelAdvisory(bookingResponse);
        checkLegalLinksInBooking();
    }

    private void checkLegalLinksInBooking() {
        CarReservationDetails carReservation = getCarReservationDetails();
        List<LegalLink> legalLinks = carReservation.getTravelerAdvisory().getLegalLinks();
        int expectedLegalLinksQty = 3;
        //there is 4th link specific for retail solutions - supplier terms and conditions
        if (carReservation.getOpacityCode().equals(String.valueOf(DatabaseCodes.OPACITY_CODE_RETAIL))) {
            expectedLegalLinksQty = 4;
        }
        verifyLegalLinks(legalLinks, expectedLegalLinksQty);
    }

    private void verifyLegalLinks(List<LegalLink> legalLinks, int expectedLegalLinksQty) {
        assertThat(legalLinks.size()).isGreaterThanOrEqualTo(expectedLegalLinksQty);
        assertThat(legalLinks.get(0).getHref()).isNotEmpty();
        assertThat(legalLinks.get(0).getRel()).isNotEmpty();
    }

    public void checkCarDetails() {
        CarReservationDetails.CarDetails carDetails = getCarReservationDetails().getCarDetails();
        assertThat(carDetails.getSeatings()).isNotEmpty();
        assertThat(carDetails.getModel()).isNotEmpty();
        assertThat(carDetails.getFeatures().size()).isGreaterThanOrEqualTo(1);
    }

    public void verifyInsuranceInfoOnDetails() {
        Insurance insurance = getCarSearchDetailsSolution().getInsurance();
        assertThat(insurance.getOptInCopy()).isNotEmpty();
        assertThat(insurance.getOptOutCopy()).isNotEmpty();
        assertThat(insurance.getLegalCopy()).isNotEmpty();
        assertThat(insurance.getPolicyDetailsLink()).isNotEmpty();
        assertThat(insurance.getCostPerDay()).isNotNull();
        assertThat(insurance.getTotalCost()).isNotNull();
        assertThat(insurance.getCurrencyCode()).isNotNull();
        assertThat(insurance.getType()).isNotEmpty();
        assertThat(insurance.getDescriptionCopy()).isNotEmpty();
        assertThat(insurance.getOptionsCopy()).isNotEmpty();
        assertThat(insurance.getAddProtectionCopy()).isNotEmpty();
        assertThat(insurance.getPolicyDetailsCopy()).isNotEmpty();
        assertThat(insurance.getPolicyLinkCopy()).isNotEmpty();
    }

    public void verifyInsuranceInfoOnBooking() {
        CarSummaryOfCharges charges = (CarSummaryOfCharges) getCarReservationDetails().getSummaryOfCharges();
        TravelerAdvisory travelerAdvisory = getCarReservationDetails().getTravelerAdvisory();

        if (requestProperties.getIsInsuranceSelected()) {
            assertThat(charges.getAdditionalFeatures().getRentalCarProtectionAmount()).
                as("Insurance cost is 0").isNotEqualTo(0.00f);
            assertThat(travelerAdvisory.getInsuranceInformation()).isNotEmpty();
        }
        else {
            LOGGER.info("Verifying that insurance info is absent in reservation response.");
            assertThat(charges.getAdditionalFeatures().getRentalCarProtectionAmount()).
                as("Insurance cost should be 0").isEqualTo(0.0f);
            assertThat(travelerAdvisory.getInsuranceInformation()).isNull();
        }
    }

    public void verifyLongitudeAndLatitudePresenceInDetails() {
        CarSearchDetailsSolution carSearchDetailsSolution = getCarSearchDetailsSolution();
        if (carSearchDetailsSolution.getPickupLocation() instanceof AirportCarLocation)  {
            AirportCarLocation carPickupLocation = (AirportCarLocation) carSearchDetailsSolution.getPickupLocation();
            assertThat(carPickupLocation.getLatLong().getLatitude()).isNotNull();
            assertThat(carPickupLocation.getLatLong().getLongitude()).isNotNull();
        }
        if (carSearchDetailsSolution.getDropoffLocation() instanceof AirportCarLocation)  {
            AirportCarLocation carDropOffLocation = (AirportCarLocation) carSearchDetailsSolution.getDropoffLocation();
            assertThat(carDropOffLocation.getLatLong().getLatitude()).isNotNull();
            assertThat(carDropOffLocation.getLatLong().getLongitude()).isNotNull();
        }
        if (carSearchDetailsSolution.getPickupLocation() instanceof LocalCarLocation)  {
            LocalCarLocation carPickupLocation = (LocalCarLocation) carSearchDetailsSolution.getPickupLocation();
            assertThat(carPickupLocation.getLatLong().getLatitude()).isNotNull();
            assertThat(carPickupLocation.getLatLong().getLongitude()).isNotNull();
        }
        if (carSearchDetailsSolution.getDropoffLocation() instanceof LocalCarLocation)  {
            LocalCarLocation carDropOffLocation = (LocalCarLocation) carSearchDetailsSolution.getDropoffLocation();
            assertThat(carDropOffLocation.getLatLong().getLatitude()).isNotNull();
            assertThat(carDropOffLocation.getLatLong().getLongitude()).isNotNull();
        }
    }

    public void verifyLongitudeAndLatitudePresenceInBooking() {
        CarReservationDetails carReservation = getCarReservationDetails();
        if (carReservation.getPickupLocation() instanceof AirportCarLocation)  {
            AirportCarLocation carPickupLocation = (AirportCarLocation) carReservation.getPickupLocation();
            assertThat(carPickupLocation.getLatLong().getLatitude()).isNotNull();
            assertThat(carPickupLocation.getLatLong().getLongitude()).isNotNull();
        }
        if (carReservation.getDropOffLocation() instanceof AirportCarLocation)  {
            AirportCarLocation carDropOffLocation = (AirportCarLocation) carReservation.getDropOffLocation();
            assertThat(carDropOffLocation.getLatLong().getLatitude()).isNotNull();
            assertThat(carDropOffLocation.getLatLong().getLongitude()).isNotNull();
        }
        if (carReservation.getPickupLocation() instanceof LocalCarLocation)  {
            LocalCarLocation carPickupLocation = (LocalCarLocation) carReservation.getPickupLocation();
            assertThat(carPickupLocation.getLatLong().getLatitude()).isNotNull();
            assertThat(carPickupLocation.getLatLong().getLongitude()).isNotNull();
        }
        if (carReservation.getDropOffLocation() instanceof LocalCarLocation)  {
            LocalCarLocation carDropOffLocation = (LocalCarLocation) carReservation.getDropOffLocation();
            assertThat(carDropOffLocation.getLatLong().getLatitude()).isNotNull();
            assertThat(carDropOffLocation.getLatLong().getLongitude()).isNotNull();
        }
    }

    public void verifyCarTypeImageUrlInBooking() {
        String regex = "^http:\\/\\/.+png$";
        CarReservationDetails carReservation = getCarReservationDetails();
        String largeImageUrl = carReservation.getCarType().getCarTypeImageUrls().getLargeImageUrl();
        String smallImageUrl = carReservation.getCarType().getCarTypeImageUrls().getSmallImageUrl();
        assertThat(smallImageUrl).isNotEmpty().contains("181x82");
        assertThat(smallImageUrl).matches(regex);
        assertThat(largeImageUrl).isNotEmpty().contains("289x137");
        assertThat(largeImageUrl).matches(regex);
    }

    public void verifyAcceptedCardTypeInBooking() {
        CarReservationDetails carReservation = getCarReservationDetails();
        assertThat(carReservation.getAcceptedCardType()).isNotNull();
    }

    public void verifyCarDetailsResultId() {
        LOGGER.info("Check Car Details");
        CarSearchDetailsSolution carSolution = getCarSearchDetailsSolution();
        String hwRefNumber = carSolution.getHwRefNumber();
        LOGGER.info("hwRefNumber:" + hwRefNumber);
        String resultID = carSolution.getResultID();
        LOGGER.info("resultID:" + resultID);
        assertThat(hwRefNumber).as("hwRefNumber is present").isNotEmpty();
        assertThat(resultID).as("resultID is present").isNotEmpty();
    }

    public void verifyCarTypeImagesUrlInDetails() {
        Set<CarInfo> carInfo = ((CarMetaData) detailsResponse.getMetaData()).getCarInfoMetaData().getCarInfo();
        String regex = "^http:\\/\\/.+png$";
        for (CarInfo car : carInfo) {
            String smallImageUrl = car.getCarTypeImageUrls().getSmallImageUrl();
            assertThat(smallImageUrl).isNotEmpty().contains("181x82");
            assertThat(smallImageUrl).matches(regex);

            String largeImageUrl = car.getCarTypeImageUrls().getLargeImageUrl();
            assertThat(largeImageUrl).isNotEmpty().contains("289x137");
            assertThat(largeImageUrl).matches(regex);
        }
    }

    public void verifyCarInfoMetadataOnDetails() {
        CarMetaData metaData = (CarMetaData) detailsResponse.getMetaData();
        Set<CarInfo> carInfo = metaData.getCarInfoMetaData().getCarInfo();
        List<CarInfo> list = new ArrayList<>();
        list.addAll(carInfo);
        CarInfo carInfo1 = list.get(0);
        assertThat(carInfo1.getCarTypeName()).isNotEmpty();
        assertThat(carInfo1.getCarTypeCode()).isNotEmpty();
        assertThat(carInfo1.getSeating().getNumberOfAdults()).isNotNull();
        assertThat(carInfo1.getSeating().getNumberOfChildren()).isNotNull();
        assertThat(carInfo1.getModels()).isNotEmpty();
        assertThat(carInfo1.getFeatures().size()).isGreaterThan(1);
        if ((getCarSearchDetailsSolution().getPickupLocation() instanceof AirportCarLocation) ||
                (getCarSearchDetailsSolution().getDropoffLocation() instanceof AirportCarLocation)) {
            assertThat(metaData.getAirportInfoMetaData().getAirports()).isNotNull();
        }
    }


    public void verifySearchCriteria() {
        CarSearchCriteria searchCriteria =
                (CarSearchCriteria) detailsResponse.getSearchResultDetails().getSearchCriteria();
        assertThat(searchCriteria.getStartAndEndDate()).isNotNull();
        assertThat(searchCriteria.getLocation().getOriginalLocation())
                .containsIgnoringCase(requestProperties.getPickupLocation());
        assertThat(searchCriteria.getAgeOfDriver()).isNotNull();
        assertThat(searchCriteria.getDepositMethod()).isNotNull();
        assertThat(searchCriteria.isOneWay()).isEqualTo(requestProperties.getIsOneWayTrip());
    }

    public void verifyTermsOfUseOnDetails() {
        Terms terms = getCarSearchDetailsSolution().getTerms();
        assertThat(terms.getAgeTerms()).isNotEmpty();
    }

    public void verifyDebitUnfriendlyFlagOnDetails() {
        CarSearchSolution solution = getCarSearchDetailsSolution();
        assertThat(solution.isDebitUnfriendly()).isFalse();
    }

    public void verifyAcceptedCardTypeFieldOnDetails() {
        CarSearchSolution solution = getCarSearchDetailsSolution();
        assertThat(solution.getAcceptedCardType()).isNotNull();
    }

    public void verifyPrepaidRetailFieldOnDetails() {
        CarSearchSolution solution = getCarSearchDetailsSolution();
        assertThat(solution.isPrepaidRetail()).isNotNull();
    }

    public void verifyPriceChange(String isPriceUp) {
        CarSearchDetailsSolution details = getCarSearchDetailsSolution();
        boolean actIsPriceUp = details.isPriceHigherFlag();
        boolean actIsPriceDown = details.isPriceLowerFlag();

        switch (isPriceUp) {
            case "up":
                assertThat(actIsPriceUp).as("Expected price up flag to be true").isTrue();
                assertThat(actIsPriceDown).as("Expected price down flag to be false").isFalse();
                break;
            case "down":
                assertThat(actIsPriceUp).as("Expected price up flag to be false").isFalse();
                assertThat(actIsPriceDown).as("Expected price down flag to be true").isTrue();
                break;
            case "same":
                assertThat(actIsPriceUp).as("Expected price up flag to be false").isFalse();
                assertThat(actIsPriceDown).as("Expected price down flag to be false").isFalse();
                break;
            default:
                throw new UnimplementedTestException("Please implement support of case " + isPriceUp);
        }
    }

    public void verifyCurrencyInCarBookingResponse() {
        String tripDetailsCurrency = bookingResponse.getBookingConfirmation().getTripDetails().getLocalCurrencyCode();
        String usersCurrency = requestProperties.getCurrencyCode();
        assertThat(usersCurrency).
                as("User's currency was " + usersCurrency + " and currency in booking response is " +
                        tripDetailsCurrency).isEqualToIgnoringCase(tripDetailsCurrency);
    }

    public void verifyHotDollarsInfo() {
        TripDetails tripDetails = bookingResponse.getBookingConfirmation().getTripDetails();
        assertThat(tripDetails.getTripTotal()).isEqualTo(0.0f);
        assertThat(tripDetails.getHotDollars()).isGreaterThan(0.0f);
    }
}

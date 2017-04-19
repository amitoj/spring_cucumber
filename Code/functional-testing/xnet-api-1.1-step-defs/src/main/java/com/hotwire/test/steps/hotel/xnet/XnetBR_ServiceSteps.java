/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.hotel.xnet;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.hotel.xnet.XnetBR_Service;
import com.hotwire.test.hotel.xnet.XnetServiceException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * @author: scheedhella
 */
public class XnetBR_ServiceSteps extends XnetAbstractSteps {

    @Autowired
    @Qualifier("xnetBRService")
    private XnetBR_Service xnetBRService;

    private Exception exception = null;

    @Given("^my booking retrieval hotel id is (\\d+)$")
    public void my_valid_hotel_id_is(int arg1) {
        xnetBRService.setHotelId(arg1);
    }

    @Given("^my minutes in the past is (-?\\d+)$")
    public void my_minutes_in_past(int arg1) {
        xnetBRService.setMinutesInPast(arg1);
    }

    @Given("^I retrieve booking$")
    public void retrieve_booking() {
        try {
            xnetBRService.retrieveBooking();
        }
        catch (Exception e) {
            exception = e;
        }
    }

    @Then("^I should get (.*) booking response$")
    public void validate_booking_response(String arg1) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateBookingResponse()).isTrue();

    }

    @Then("^I should expect (.*) error$")
    public void i_should_expect_proper_error(String errorType) {
        if ("No".equals(errorType)) {
            assertThat(exception)
                .as("Expected exception to be null")
                .isEqualTo(null);
        }
        else {
            String errorCode = exception instanceof XnetServiceException ? ((XnetServiceException) exception)
                .getErrorCode() : XnetServiceException.ERROR_CODE_UNDEFINED;
            Map<String, String> expectedMessageToErrorCode = new HashMap<String, String>();

            expectedMessageToErrorCode.put("Invalid hotel id", XnetServiceException.ERROR_CODE_INVALID_HOTEL_ID);
            expectedMessageToErrorCode.put("Internal", XnetServiceException.ERROR_CODE_INTERNAL_ERROR);
            expectedMessageToErrorCode.put("authentication", XnetServiceException.ERROR_CODE_UNAUTHORIZED);
            expectedMessageToErrorCode.put("minutes in past", XnetServiceException.ERROR_CODE_BR_INVALID_MINS_IN_PAST);
            expectedMessageToErrorCode.put("The current API version only supports booking " +
                "retrieval by hotel id AND minutes in past", XnetServiceException.ERROR_CODE_UNSUPPORTED_OPERATION);
            if (!expectedMessageToErrorCode.get(errorType).equals(errorCode)) {
                throw new RuntimeException(exception.getMessage(), exception);
            }
        }
    }

    // @Then("^I should get arrival date (.*)$")
    // public void setArrivalDate(@Transform(ChronicConverter.class) Calendar start) {
    // assert exception == null;
    // // Date convertedArrivalDate;
    // // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    // // try {
    // // convertedArrivalDate = dateFormat.parse(arrivalDate);
    // // } catch (ParseException e) {
    // // return;
    // // }
    // assert xnetBRService.validateArrivalDate(start.getTime());
    // }

    // @Then("^I should get departure date is (.*)$")
    // public void setDepartureDate(String departureDate) {
    // assert exception == null;
    // // Date convertedDepartureDate;
    // // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    // // try {
    // // convertedDepartureDate = dateFormat.parse(departureDate);
    // // } catch (ParseException e) {
    // // return;
    // // }
    // // assert xnetBRService.validateDepartureDate(convertedDepartureDate);
    //
    // }

    @Then("^I should get number of adults is (\\d+)$")
    public void i_should_get_num_of_adults(int adults) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateNumberOfAdults(adults)).isTrue();
    }

    @Then("^I should get number of children is (\\d+)$")
    public void i_should_get_num_of_children(int children) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateNumberOfChildren(children)).isTrue();

    }

    @Then("^I should get rates in currency is (.*)$")
    public void i_should_get_rates_in_currency(String crsCurrencyCode) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateCurrencyCode(crsCurrencyCode)).isTrue();

    }

    @Then("^I should get currency code is (.*)$")
    public void i_should_get_currency_code(String crsCurrencyCode) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateTotalAmountCurrencyCode(crsCurrencyCode)).isTrue();

    }

    @Then("^I should get room stay whose id is (.*)$")
    public void i_should_get_room_stay_id(String roomStayId) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateRoomStayId(roomStayId)).isTrue();
    }

    @Then("^I should get room stay whose rate plan id is (.*)$")
    public void i_should_get_room_rate_planId(String roomRatePlanId) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateRoomRatePlanId(roomRatePlanId)).isTrue();
    }

    @Then("^I should get number of rooms is (\\d+)$")
    public void i_should_get_number_of_rooms(int numOfRooms) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateNumberOfRooms(numOfRooms)).isTrue();

    }

    @Then("^I should get number of per day rates is (\\d+)$")
    public void i_should_get_number_of_nights(int numOfNights) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateNumberOfNights(numOfNights)).isTrue();

    }

    @Then("^I should get booking of type is (.*)$")
    public void i_should_get_booking_type(String bookingType) {
        assertThat(exception)
            .as("Expected exception to be null")
            .isEqualTo(null);
        assertThat(xnetBRService.validateBookingType(bookingType)).isTrue();
    }

}

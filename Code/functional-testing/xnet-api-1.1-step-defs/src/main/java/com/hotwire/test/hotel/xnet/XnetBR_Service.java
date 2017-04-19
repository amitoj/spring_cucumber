/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet;


/**
 *
 * @author: scheedhella
 *
 */
public interface XnetBR_Service {

    /**
     * Retrieve Booking. On success returns with no exceptions.
     *
     * @throws XnetServiceException if any error occurs
     */
    void retrieveBooking() throws XnetServiceException;

    /**
     * Sets hotel id
     *
     * @param hotelId - the id to set
     */
    void setHotelId(long hotelId);


    /**
     * Sets minutes in past
     *
     * @param minutes
     */
    void setMinutesInPast(int minutes);

    /**
     * validates empty response
     */
    boolean validateBookingResponse() throws XnetServiceException;

    /**
     * Validates if the booking returned is of type 'Book' or 'Cancel'
     *
     * @param bookingType
     * @return
     * @throws XnetServiceException
     */

    boolean validateBookingType(String bookingType) throws XnetServiceException;

    /**
     * Validates currency code
     *
     * @param currencyCode
     * @return
     * @throws XnetServiceException
     */
    boolean validateCurrencyCode(String currencyCode) throws XnetServiceException;


    /**
     * Validates number of adults for whom the booking was made
     *
     * @param numAdults
     * @return
     * @throws XnetServiceException
     */
    boolean validateNumberOfAdults(int numAdults) throws XnetServiceException;


    /**
     * Validates number of children for whom the booking was made
     *
     * @param numChildren
     * @return
     * @throws XnetServiceException
     */
    boolean validateNumberOfChildren(int numChildren) throws XnetServiceException;


    /**
     * Validates the room stay id
     *
     * @param roomStayId
     * @return
     * @throws XnetServiceException
     */
    boolean validateRoomStayId(String roomStayId) throws XnetServiceException;


    /**
     * Validates the rate plan id
     *
     * @param roomRatePlanId
     * @return
     * @throws XnetServiceException
     */
    boolean validateRoomRatePlanId(String roomRatePlanId) throws XnetServiceException;


    /**
     * Validates number of rooms
     *
     * @param numOfRooms
     * @return
     * @throws XnetServiceException
     */
    boolean validateNumberOfRooms(int numOfRooms) throws XnetServiceException;


    /**
     * Validates the currency code of the element 'TotalAmount'
     *
     * @param currencyCode
     * @return
     * @throws XnetServiceException
     */
    boolean validateTotalAmountCurrencyCode(String currencyCode) throws XnetServiceException;


    /**
     * Validates the number of nights for which the booking was made.
     *
     * @param numOfNights
     * @return
     * @throws XnetServiceException
     */
    boolean validateNumberOfNights(int numOfNights) throws XnetServiceException;


}

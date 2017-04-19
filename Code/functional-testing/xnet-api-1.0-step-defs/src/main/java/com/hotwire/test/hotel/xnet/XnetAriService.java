/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * We treat Xnet ARI as a service for a supplier. He can use this service to upload his rates.
 * The actual implementation (xnet web app or xnet api or something else in the future) is hidden behind this interface.
 * This interface should have only the stuff a supplier can do with this service
 * but with no reference to actual implementation.
 * This helps to write more implementation agnostic step defs and feature files.
 *
 * @author Renat Zhilkibaev
 */
public interface XnetAriService {

    /**
     * Sets the username used to authenticate a supplier
     * @param username - the suppliers username
     */
//    void setUsername(String username);

    /**
     * Sets the password used to authenticate a supplier
     * @param password - the suppliers password
     */
//    void setPassword(String password);

    /**
     * Sets hotel id
     *
     * @param hotelId - the id to set
     */
    void setHotelId(long hotelId);

    /**
     * Sets date range
     *
     * @param from - the start date
     * @param to   - the end date (inclusive)
     */
    void addAvailRateUpdate(Date from,
                                   Date to,
                                   Boolean isSun,
                                   Boolean isMon,
                                   Boolean isTue,
                                   Boolean isWed,
                                   Boolean isThu,
                                   Boolean isFri,
                                   Boolean isSat);

    /**
     * Sets room type for the latest date range
     *
     * @param roomType - the room type id
     */
    void addRoomType(String roomType);

    /**
     * Sets rate plan for the latest room type
     *
     * @param ratePlan - the rate plan id
     */
    void setRatePlan(String ratePlan);

    /**
     * Sets currency for the latest room type
     *
     * @param currency - the currency
     */
    void setCurrency(String currency);

    /**
     * Sets Sold out for the room type
     *
     * @param soldOut - sold out flag
     */
    void setSoldOut(boolean soldOut);

    /**
     * Sets closed to arrival for the room type
     *
     * @param closedToArrival - closed to arrival flag
     */
    void setClosedToArrival(boolean closedToArrival);


    /**
     * Sets per day rate for the latest room type
     *
     * @param perDayRate - the rate for a one day
     */
    void setPerDayRate(float perDayRate);

    /**
     * Sets rate for extra person for the latest room type
     *
     * @param extraPersonRate - the rate for extra person
     */
    void setExtraPersonFee(float extraPersonRate);

    /**
     * Sets total inventory available for the current room type.
     *
     * @param totalInventoryAvailable - the total number of available inventory
     */
    void setTotalInventoryAvailable(int totalInventoryAvailable);

    /**
     * Updates inventory. On success returns with no exceptions.
     *
     * @throws XnetAriServiceException if any error occurs
     */
    void updateInventory() throws XnetAriServiceException;

    /**
     * Checks the endpoint is accessible
     *
     * @throws IOException if any error occurs
     */
    void isXnetServiceAccessible() throws IOException, TimeoutException;

    /**
     * Sets invalid user name & password
     */
    void setInvalidSupplier();

    /**
     * Sets valid user name & password
     */
    void setValidSupplier();

    /**
     * verify inventory update
     * @param successFailure
     * @param exception
     */
    void verifyUpdateInventory(String successFailure, Exception exception);
}

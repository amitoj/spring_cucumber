/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet;

import java.util.Date;
import java.util.List;

/**
 * This interface is for all the functionality that lives in XnetWebApp only.
 * There's no point in adding functions to Ari and BR Services which are not part of those APIs.
 *
 * @author adeshmukh
 *
 */
public interface XnetWebApp {

    /**
     * Sets discount for current hotel
     *
     * @param hotelDiscount
     */
    void setDiscount(String discount);

    /**
     * Sets start hour for current hotel
     *
     * @param startHour
     */
    void setStartHour(String startHour);

    /**
     * applies discount and startTime to a hotel
     */

    void applyNightfallChanges();

    /**
     * validates the confirmation message received after applying nightFall discount and startTime
     */
    void verifyNightfallConfirmation(String discountPercent, String startHour);

    /**
     * applies late booking end time
     *
     * @param lateBookingEndTime
     */
    void applyLateBookingTime(String lateBookingEndTime);

    /**
     * validates the booking time confirmation message and actual booking time set.
     *
     * @param lateBookingTime
     */
    void confirmLateBookingTime(String lateBookingTime);

    /**
     * Click on hotel Details link on hotelOverview page
     */
    void clickHotelDetails();

    /**
     * Validate room type on hotelOverviewPage listed under RoomType dropdown
     *
     * @param validate
     * @param roomType
     */
    void validateRoomTypes(String validate, String roomType);

    /**
     * click Room Type Link on HotelOverview page
     */
    void clickRoomType();

    /**
     * Validate the settings for any given room type on EditRoomTYpe page
     *
     * @param roomTypeSettings
     */
    void validateSettings(List<String> roomTypeSettings);

    /**
     * Change the settigns of a give Room
     *
     * @param roomTypeSettings
     */
    void changeRoomTypeSetting(List<String> roomTypeSettings);

    /**
     * Click HotelOverView page in the header.
     */
    void clickHotelOverviewPage();

    /**
     * This will allow to pass email address and password from feature file.
     * @param emailAddress
     * @param password
     */
    void customizedSignIn(String emailAddress, String password);

    /**
     * clicks on the change password link on extranet
     */
    void accessChangePasswordPage();

    /**
     * This method passes the old and new password to desktop page objects.
     * @param old_password
     * @param new_password
     */
    void changePassword(String old_password, String new_password);

    /**
     * access xnet user permission page
     */
    void accessUserPemissionsPage();

    /**
     * add or update or delete a user hotel affiliation
     * @param addDeleteUpdate
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     * @param permissionLevel
     */
    void addUpdateDeleteUserPermissions(String addDeleteUpdate,
                                        String firstName,
                                        String lastName,
                                        String email,
                                        String phone,
                                        String permissionLevel);

    /**
     * once the permissions are updated validate the access level code in the xnet_user_hotel table
     * @param access_level_code
     */
    void validateAccessLevelCodeInDB(String access_level_code, String hotelId, String user);

    /**
     * Once user permissions are changed, make sure the confirmation is received
     */
    void validateUserPermissionConfirmtion();

    /**
     * Enters the date range on review changes page and clicks GO
     * @param startDate
     * @param endDate
     */
    void reviewChanges(Date startDate, Date endDate);

    /**
     * Returns true if user lands on review changes page and review changes table exists
     */
    void assertReviewChangePage();

    /**
     * takes users to review changes page from hotel overview.
     */
    void accessReviewChangesPage();

    /**
     * Validates rate
     *
     * @param rate
     * @return
     */
    boolean validateRate(String rate);

    /**
     * Retrieves booking for the date range specified with respect to arrival or booked date
     * @param time
     * @param time1
     */
    void selectReviewBookingsDateRange(Date time, Date time1, String searchType);


}

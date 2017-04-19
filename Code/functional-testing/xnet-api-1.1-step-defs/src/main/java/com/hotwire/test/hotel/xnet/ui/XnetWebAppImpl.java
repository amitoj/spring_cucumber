/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet.ui;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.qa.client.db.Db;
import com.hotwire.qa.client.db.ResRow;
import com.hotwire.qa.client.db.Selected;
import com.hotwire.qa.data.UEnum;
import com.hotwire.qa.systems.HwRunEnv;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetChangePasswordPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetEditRoomPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetHotelDetailsPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetHotelOverviewPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetHotelReviewBookingsDetailsPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetHotelReviewBookingsPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetReviewChangeRequestPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetRoomType;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetSignInPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetUserPemissionsPage;
import com.hotwire.selenium.desktop.hotelextranet.ExtranetWelcomeAdminPage;
import com.hotwire.test.hotel.xnet.XnetAriService;
import com.hotwire.test.hotel.xnet.XnetBR_Service;
import com.hotwire.test.hotel.xnet.XnetWebApp;
import com.hotwire.test.steps.common.WebdriverAwareModel;

/**
 * Implements {@link com.hotwire.test.hotel.xnet.XnetAriService} for xnet api.
 *
 * @author Sridhar Ramakrihnan
 */
public class XnetWebAppImpl extends WebdriverAwareModel implements XnetAriService, XnetBR_Service, XnetWebApp {

    private String validUserName;
    private String validPassword;
    private String invalidUserName;
    private String invalidPassword;
    private String normalUserName;
    private String normalPassword;
    private String runEnv_name;


    @Autowired
    @Qualifier("xnetApplicationUrlString")
    private String xnetApplicationUrlString;

    public void setInvalidSupplier() {
        ExtranetSignInPage extranetSignInPage = new ExtranetSignInPage(getWebdriverInstance());
        extranetSignInPage.xnetSignIn(invalidUserName, invalidPassword);
    }

    public void setValidSupplier(String userType) {
        ExtranetSignInPage extranetSignInPage = new ExtranetSignInPage(getWebdriverInstance());
        if (userType.equals("valid")) {
            extranetSignInPage.xnetSignIn(validUserName, validPassword);
        }
        else if (userType.equals("normal")) {
            extranetSignInPage.xnetSignIn(normalUserName, normalPassword);
        }

    }

    public void isXnetServiceAccessible() {
        getWebdriverInstance().navigate().to(xnetApplicationUrlString);
    }

    @Override
    public void updateInventory() {
        ExtranetEditRoomPage extranetEditRoomPage = new ExtranetEditRoomPage(getWebdriverInstance());
        extranetEditRoomPage.previewAndSaveChanges();
    }

    @Override
    public void setHotelId(long hotelId) {
        ExtranetWelcomeAdminPage extranetWelcomeAdminPage = new ExtranetWelcomeAdminPage(getWebdriverInstance());
        extranetWelcomeAdminPage.searchHotel(hotelId);
    }

    @Override
    public void addAvailRateUpdate(Date from, Date to, Boolean isSun, Boolean isMon, Boolean isTue, Boolean isWed,
        Boolean isThu, Boolean isFri, Boolean isSat) {
        ExtranetHotelOverviewPage extranetHotelOverviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        extranetHotelOverviewPage.selectDates(from, to);
    }

    @Override
    public void addRoomType(String roomType) {
        ExtranetHotelOverviewPage extranetHotelOverviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        extranetHotelOverviewPage.selectRoomType(roomType);
        extranetHotelOverviewPage.clickGoButton();
    }

    @Override
    public void setTotalInventoryAvailable(int totalInventoryAvailable) {
        ExtranetEditRoomPage extranetEditRoomPage = new ExtranetEditRoomPage(getWebdriverInstance());
        extranetEditRoomPage.updateBulkRooms(totalInventoryAvailable);
    }

    @Override
    public void setRatePlan(String ratePlan) {
        // Not application for UI
    }

    @Override
    public void setCurrency(String currency) {
        // Not application for UI
    }

    @Override
    public void setPerDayRate(float perDayRate) {
        ExtranetEditRoomPage extranetEditRoomPage = new ExtranetEditRoomPage(getWebdriverInstance());
        extranetEditRoomPage.updateBulkRate(perDayRate);
    }

    @Override
    public void setExtraPersonFee(float extraPersonRate) {
        ExtranetEditRoomPage extranetEditRoomPage = new ExtranetEditRoomPage(getWebdriverInstance());
        extranetEditRoomPage.updateBulkEPF(extraPersonRate);
    }

    @Override
    public void setSoldOut(boolean soldOut) {

    }

    @Override
    public void setClosedToArrival(boolean closedToArrival) {

    }

    @Override
    public void verifyUpdateInventory(String successFailure, Exception exception) {
        ExtranetEditRoomPage extranetEditRoomPage = new ExtranetEditRoomPage(getWebdriverInstance());
        if ("No".equals(successFailure)) {
            extranetEditRoomPage.verifyConfirmationMessage();
        }
    }

    public void setValidUserName(String validUserName) {
        this.validUserName = validUserName;
    }

    public String getValidUserName() {
        return validUserName;
    }

    public void setValidPassword(String validPassword) {
        this.validPassword = validPassword;
    }

    public String getValidPassword() {
        return validPassword;
    }

    public void setInvalidUserName(String invalidUserName) {
        this.invalidUserName = invalidUserName;
    }

    public String getInvalidUserName() {
        return invalidUserName;
    }

    public void setInvalidPassword(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public void setNormalUserName(String normalUserName) {
        this.normalUserName = normalUserName;
    }

    public void setNormalPassword(String normalPassword) {
        this.normalPassword = normalPassword;
    }

    public String getrunEnv_name() {
        return runEnv_name;
    }

    public void setrunEnv_name(String runEnv_name) {
        this.runEnv_name = runEnv_name;
    }

    /**
     * Retrieve Booking. On success returns with no exceptions.
     */
    @Override
    public void retrieveBooking() {
        ExtranetHotelOverviewPage extranetHotelOverviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        extranetHotelOverviewPage.clickReviewBookingLink();

    }

    /**
     * Select Date range for booking retrieval
     *
     * @param startDate
     *            , endDate
     */
    @Override
    public void selectReviewBookingsDateRange(Date startDate, Date endDate, String searchType) {
        ExtranetHotelReviewBookingsPage extranetHotelReviewBookingsPage = new ExtranetHotelReviewBookingsPage(
            getWebdriverInstance());
        extranetHotelReviewBookingsPage.selectDates(startDate, endDate, searchType);
        extranetHotelReviewBookingsPage.clickGoButton();
    }

    /**
     * Sets minutes in past
     *
     * @param minutes
     */
    @Override
    public void setMinutesInPast(int minutes) {
        // do nothing for web app
    }

    /**
     * validates empty response
     */
    @Override
    public boolean validateBookingResponse() {
        ExtranetHotelReviewBookingsDetailsPage extranetHotelReviewBookingsDetailsPage =
                new ExtranetHotelReviewBookingsDetailsPage(getWebdriverInstance());
        boolean result = extranetHotelReviewBookingsDetailsPage.validateReviewBookingTableExist();
        if (!result) {
            return false;
        }
        return true;
    }

    /**
     * Validates if the booking returned is of type 'Book' or 'Cancel'
     *
     * @param bookingType
     * @return
     */
    @Override
    public boolean validateBookingType(String bookingType) {
        return false;
    }

    /**
     * Validates currency code
     *
     * @param currencyCode
     * @return
     */
    @Override
    public boolean validateCurrencyCode(String currencyCode) {
        return false;
    }

    /**
     * Validates number of adults for whom the booking was made
     *
     * @param numAdults
     * @return
     */
    @Override
    public boolean validateNumberOfAdults(int numAdults) {
        return false;
    }

    /**
     * Validates number of children for whom the booking was made
     *
     * @param numChildren
     * @return
     */
    @Override
    public boolean validateNumberOfChildren(int numChildren) {
        return false;
    }

    /**
     * Validates the room stay id
     *
     * @param roomStayId
     * @return
     */
    @Override
    public boolean validateRoomStayId(String roomStayId) {
        return false;
    }

    /**
     * Validates the rate plan id
     *
     * @param roomRatePlanId
     * @return
     */
    @Override
    public boolean validateRoomRatePlanId(String roomRatePlanId) {
        return false;
    }

    /**
     * Validates number of rooms
     *
     * @param numOfRooms
     * @return
     */
    @Override
    public boolean validateNumberOfRooms(int numOfRooms) {
        return false;

    }

    /**
     * Validates the currency code of the element 'TotalAmount'
     *
     * @param currencyCode
     * @return
     */
    @Override
    public boolean validateTotalAmountCurrencyCode(String currencyCode) {
        return false;

    }

    /**
     * Validates the number of nights for which the booking was made.
     *
     * @param numOfNights
     * @return
     */
    @Override
    public boolean validateNumberOfNights(int numOfNights) {
        return false;

    }

    /**
     * Validate rate
     *
     * @param rate
     * @return
     */
    @Override
    public boolean validateRate(String rate) {
        ExtranetHotelReviewBookingsDetailsPage extranetHotelReviewBookingsDetailsPage =
            new ExtranetHotelReviewBookingsDetailsPage(getWebdriverInstance());
        return extranetHotelReviewBookingsDetailsPage.validateRateInReviewBookingTable(rate);

    }

    /**
     * sets Discount via app
     *
     * @param discount
     */

    @Override
    public void setDiscount(String discount) {
        ExtranetHotelOverviewPage extranetHotelOverviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        extranetHotelOverviewPage.setNighfallDiscount(discount);

    }

    /**
     * sets start time via app
     *
     * @param startHour
     */

    @Override
    public void setStartHour(String startHour) {
        ExtranetHotelOverviewPage extranetHotelOverviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        extranetHotelOverviewPage.setNighfallStartHour(startHour);

    }

    @Override
    public void applyNightfallChanges() {
        ExtranetHotelOverviewPage extranetHotelOverviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        extranetHotelOverviewPage.applyNightfallChanges();

    }

    @Override
    public void verifyNightfallConfirmation(String discountPercent, String startHour) {
        ExtranetHotelOverviewPage extranetHotelOverviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        assertThat(extranetHotelOverviewPage.validateConfirmation(discountPercent, startHour)).as(
            "Discount Percentage or Start time is not set").isTrue();

    }

    /**
     * Sets late booking end time.
     *
     * @param=lateBookingEndTime
     */
    @Override
    public void applyLateBookingTime(String lateBookingEndTime) {
        ExtranetHotelDetailsPage hotelDetailsPage = new ExtranetHotelDetailsPage(getWebdriverInstance());
        hotelDetailsPage.applyLateBookingEndTime(lateBookingEndTime);

    }

    /**
     * validates the late booking end time
     *
     * @param=late booking end time set
     */
    @Override
    public void confirmLateBookingTime(String lateBookingTime) {
        ExtranetHotelDetailsPage hotelDetailsPage = new ExtranetHotelDetailsPage(getWebdriverInstance());
        assertThat(hotelDetailsPage.validateLateBookingConfirmaton(lateBookingTime)).as(
            "Late booking end time was not set properly").isTrue();
    }

    @Override
    public void clickHotelDetails() {
        ExtranetHotelOverviewPage hotelOverviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        hotelOverviewPage.clickHotelDetails();
    }

    /**
     * Depending on 'should or shouldn't' this method asserts the boolean returned by
     * overviewPage.validateRoomType(roomType)
     */
    @Override
    public void validateRoomTypes(String validate, String roomType) {
        ExtranetHotelOverviewPage overviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        if (validate.equals("should")) {
            assertThat(overviewPage.validateRoomType(roomType)).as("Room should be in the list").isTrue();
        }
        else {
            assertThat(overviewPage.validateRoomType(roomType)).as("Room shouldn't be in the list").isFalse();
        }

    }

    /**
     * Click on Room Type Link
     */
    @Override
    public void clickRoomType() {
        ExtranetHotelOverviewPage overviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        overviewPage.clickRoomType();
    }

    /**
     * Validates whether the settings are saved correctly or not.
     *
     * room type settings are read in this order - bedType, maxRoom/Book, maxGuests/room, maxAdults/room,
     * maxChildren/room, isActiveFlag
     */
    @Override
    public void validateSettings(List<String> roomTypeSettings) {
        ExtranetRoomType roomTypePage = new ExtranetRoomType(getWebdriverInstance());
        assertThat(roomTypePage.savedChanges(roomTypeSettings)).as("Room Type Settings are incorrect").isTrue();
    }

    /**
     * Method changes the room type settings
     *
     * room type settings are read/changed in this order - bedType, maxRoom/Book, maxGuests/room, maxAdults/room,
     * maxChildren/room, isActiveFlag
     */
    @Override
    public void changeRoomTypeSetting(List<String> roomTypeSettings) {
        ExtranetRoomType roomTypePage = new ExtranetRoomType(getWebdriverInstance());
        roomTypePage.changeRoomTypeSetting(roomTypeSettings);
    }

    /**
     * Click Hotel Overview Link
     */
    @Override
    public void clickHotelOverviewPage() {
        ExtranetRoomType roomTypePage = new ExtranetRoomType(getWebdriverInstance());
        roomTypePage.clickHotelOverview();
    }

    @Override
    public void setXnetServiceHeaders() {
        // Not applicable to WEB

    }

    @Override
    public void customizedSignIn(String emailAddress, String password) {
        ExtranetSignInPage extranetSignIn = new ExtranetSignInPage(getWebdriverInstance());
        extranetSignIn.xnetSignIn(emailAddress, password);
    }


    @Override
    public void accessChangePasswordPage() {
        ExtranetWelcomeAdminPage welcomePage = new ExtranetWelcomeAdminPage(getWebdriverInstance());
        welcomePage.accessChangePasswordPage();
    }

    @Override
    public void changePassword(String old_password, String new_password) {
        ExtranetChangePasswordPage changePasswordPage = new ExtranetChangePasswordPage(getWebdriverInstance());
        changePasswordPage.changePassword(old_password, new_password);
    }


    @Override
    public void accessUserPemissionsPage() {
        ExtranetHotelOverviewPage overviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        overviewPage.accessUserPermissionPage();
    }


    @Override
    public void addUpdateDeleteUserPermissions(String addDeleteUpdate,
                                               String firstName,
                                               String lastName,
                                               String email,
                                               String phone,
                                               String permissionLevel) {
        ExtranetUserPemissionsPage userPermissionPage = new ExtranetUserPemissionsPage(getWebdriverInstance());

        if (addDeleteUpdate.equals("add")) {
            userPermissionPage.addUser(firstName, lastName, email, phone, permissionLevel);
        }
        else if (addDeleteUpdate.equals("update")) {
            userPermissionPage.changeUserPermissions(permissionLevel);
        }
        else if (addDeleteUpdate.equals("delete")) {
            userPermissionPage.deleteUser();
        }
    }


    @Override
    public void validateAccessLevelCodeInDB(String access_level_code, String hotel_id, String user) {
        HwRunEnv runEnv = UEnum.match(HwRunEnv.class, runEnv_name, false);
        Db db = runEnv.dbCfg.getDb("rpt_user", "rpt_user");
        db.echo();
        Selected selected = db.select("select access_level_code from xnet_user_hotel where hotel_id = " + hotel_id +
            " and xnet_user_id in (select xnet_user_id from xnet_user where email like '" + user + "')");
        ResRow[] rows = selected.getRes(1);
        assertThat(rows[0].get("access_level_code").toString().equals(access_level_code)).
            as("Access level code is incorrect").isTrue();
    }


    @Override
    public void validateUserPermissionConfirmtion() {
        ExtranetUserPemissionsPage userPermissionPage = new ExtranetUserPemissionsPage(getWebdriverInstance());
        assertThat(userPermissionPage.validateConfirmationMessage()).as("Confirmation wasn't received").isTrue();
    }

    @Override
    public void reviewChanges(Date startDate, Date endDate) {
        ExtranetReviewChangeRequestPage reviewChangesPage = new ExtranetReviewChangeRequestPage(getWebdriverInstance());
        reviewChangesPage.reviewChanges(startDate, endDate);
    }


    @Override
    public void assertReviewChangePage() {
        ExtranetReviewChangeRequestPage reviewChangesPage = new ExtranetReviewChangeRequestPage(getWebdriverInstance());
        assertThat(reviewChangesPage.validateReviewChangeTable()).
            as("Review changes page or a table didn't load").isTrue();
    }

    @Override
    public void accessReviewChangesPage() {
        ExtranetHotelOverviewPage overviewPage = new ExtranetHotelOverviewPage(getWebdriverInstance());
        overviewPage.accessReviewChangePage();
    }
}

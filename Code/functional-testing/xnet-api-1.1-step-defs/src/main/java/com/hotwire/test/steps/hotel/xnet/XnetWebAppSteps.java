/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.hotel.xnet;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.hotel.xnet.XnetWebApp;

import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.transformer.jchronic.ChronicConverter;

/**
 * These steps are pertaining to webApp only.
 *
 * @author adeshmukh
 *
 */
public class XnetWebAppSteps extends XnetAbstractSteps {

    @Autowired
    @Qualifier("xnetWebApp")
    private XnetWebApp xnetWebApp;

    @Given("^discount is (.*)%$")
    public void set_discount(String discount) {
        xnetWebApp.setDiscount(discount);
    }

    @Given("^start time is (.*)\\s?(?:[APap][Mm])?$")
    public void set_start_hour(String startHour) {
        xnetWebApp.setStartHour(startHour);
    }

    @When("^I apply the NightFall changes$")
    public void apply_Nightfall_changes() {
        xnetWebApp.applyNightfallChanges();
    }

    @Then("^I should get a confirmation about discountPercent = (.*)% and startTime = (.*)\\s?(?:[APap][Mm])?$")
    public void changes_applied_successfully(String discountPercent, String startHour) {
        xnetWebApp.verifyNightfallConfirmation(discountPercent, startHour);
    }

    @When("^I access hotel details page$")
    public void accessHotelDetailsPage() {
        xnetWebApp.clickHotelDetails();
    }

    @When("^I set late booking end time = (.*)\\s?(?:AM)?$")
    public void setLateBookingTime(String lateBookingEndTime) {
        xnetWebApp.applyLateBookingTime(lateBookingEndTime);
    }

    @Then("^I should see late booking confirmation message and late booking end time being set to (.*)\\s?(?:AM)?$")
    public void validateLateBookingEndTime(String lateBookingEndTime) {
        xnetWebApp.confirmLateBookingTime(lateBookingEndTime);
    }

    @Then("^I (.*) see (.*) in the room type list$")
    public void validateRoomTypeList(String validate, String roomType) {
        xnetWebApp.validateRoomTypes(validate, roomType);
    }

    @When("^I click on room type link$")
    public void clickRoomType() {
        xnetWebApp.clickRoomType();
    }

    @Then("^the changes are saved successfully:$")
    public void validateDefaultSetting(List<String> roomTypeSetting) {
        xnetWebApp.validateSettings(roomTypeSetting);
    }

    @Given("^I change the following room type settings:$")
    public void changeRoomTypeSettings(List<String> roomTypeSetting) {
        xnetWebApp.changeRoomTypeSetting(roomTypeSetting);
    }

    @When("^I go to hotel overview page$")
    public void clickHotelOverviewPage() {
        xnetWebApp.clickHotelOverviewPage();
    }

    @Given("^I sign in with (.*) and (.*)$")
    public void signInWithCustomizedCredentials(String emailAddress, String password) {
        xnetWebApp.customizedSignIn(emailAddress, password);
    }

    @And("^I go to change password page$")
    public void accessChangePasswordPage() {
        xnetWebApp.accessChangePasswordPage();
    }

    @And("^I change the password from (.*) to (.*)$")
    public void changePassword(String old_password, String new_password) {
        xnetWebApp.changePassword(old_password, new_password);
    }

    @And("^I want to update user permissions$")
    public void accessUserPermissionPage() {
        xnetWebApp.accessUserPemissionsPage();
    }

    @When("^I (.*) a user with (.*), (.*), (.*), (.*) and (.*)$")
    public void addUpdateDeleteUserPermissions(String addDeleteUpdate,
                                               String firstName,
                                               String lastName,
                                               String email,
                                               String phone,
                                               String permissionLevel) {

        xnetWebApp.addUpdateDeleteUserPermissions(addDeleteUpdate,
                                                      firstName,
                                                      lastName,
                                                      email,
                                                      phone,
                                                      permissionLevel);

    }

    @Then("^I see a confirmation message$")
    public void verifyConfirmation() {
        xnetWebApp.validateUserPermissionConfirmtion();
    }

    @And("^I see (.*) affiliation of (.*) and (.*) in the DB$")
    public void validateAccessLevelCode(String access_level_code, String hotelId, String user) {
        xnetWebApp.validateAccessLevelCodeInDB(access_level_code, hotelId, user);
    }

    @Given("^I access review changes page$")
    public void accessReviewChangesPage() {
        xnetWebApp.accessReviewChangesPage();
    }

    @And("^I review the changes between (.*) to (.*)$")
    public void reviewChanges(@Transform(ChronicConverter.class) Calendar from,
                              @Transform(ChronicConverter.class) Calendar to) {
        xnetWebApp.reviewChanges(from.getTime(), to.getTime());
    }

    @Then("^I see a review changes table$")
    public void validateReviewChangesPage() {
        xnetWebApp.assertReviewChangePage();
    }

    @When("^I review bookings by (.*) and date range (\\-*\\d+) to (\\-*\\d+) days from now$")
    public void review_bookings_by_date_range(String searchType, Integer from, Integer to) {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, from);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, to);
        xnetWebApp.selectReviewBookingsDateRange(startDate.getTime(), endDate.getTime(), searchType);
    }

    @Then("^I should get rate is (.*)$")
    public void i_should_get_rates_is(String rate) {
        assertThat(xnetWebApp.validateRate(rate)).isTrue();
    }


}

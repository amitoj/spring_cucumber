/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.hotel.xnet;

import java.io.IOException;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.hotel.xnet.XnetAriService;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.transformer.jchronic.ChronicConverter;

/**
 * Steps for Xnet ARI service.
 * These steps are related to update inventory and are mapped to webapp as well as ari API
 *
 * @author Renat Zhilkibaev
 */
public class XnetAriServiceSteps extends XnetAbstractSteps {

    @Autowired
    @Qualifier("xnetAriService")
    private XnetAriService xnetAriService;

    private Exception exception;

    @Given("^I'm a (valid|normal) supplier$")
    public void i_m_a_valid_supplier(String userType) {
        xnetAriService.setValidSupplier(userType);
    }

    @Given("^I pass request with headers$")
    public void set_xnet_headers() {
        xnetAriService.setXnetServiceHeaders();
    }

    @Given("^I'm an invalid supplier$")
    public void i_m_an_invalid_supplier() {
        xnetAriService.setInvalidSupplier();
    }

    @Given("^the xnet service is accessible$")
    public void the_xnet_service_is_accessible() throws IOException, TimeoutException {
        // check that the remote service is up
        xnetAriService.isXnetServiceAccessible();
    }

    @Given("^my hotel id is (\\d+)$")
    public void my_hotel_id_is(int arg1) {

        xnetAriService.setHotelId(arg1);
    }

    @Given("^date range is between (.*) and (.*) every " +
           "(\\b(?:(?:Sun|Mon|Tues|Wednes|Thurs|Fri|Satur)?day(?:,? *))+\\b)$")
    public void date_range_is_between_x_days_from_now_and_x_days_from_now(
        @Transform(ChronicConverter.class) Calendar from, @Transform(ChronicConverter.class) Calendar to, String days) {
        Boolean isMon = null, isTue = null, isWed = null, isThu = null, isFri = null, isSat = null, isSun = null;

        StringTokenizer tokenizer = new StringTokenizer(days, ",");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.equalsIgnoreCase("day")) {
                break;
            }
            else if (token.equalsIgnoreCase("Monday")) {
                isMon = true;
            }
            else if (token.equalsIgnoreCase("Tuesday")) {
                isTue = true;
            }
            else if (token.equalsIgnoreCase("Wednesday")) {
                isWed = true;
            }
            else if (token.equalsIgnoreCase("Thursday")) {
                isThu = true;
            }
            else if (token.equalsIgnoreCase("Friday")) {
                isFri = true;
            }
            else if (token.equalsIgnoreCase("Saturday")) {
                isSat = true;
            }
            else if (token.equalsIgnoreCase("Sunday")) {
                isSun = true;
            }
        }
        xnetAriService
            .addAvailRateUpdate(from.getTime(), to.getTime(), isSun, isMon, isTue, isWed, isThu, isFri, isSat);
    }

    @Given("^room type is (.*)$")
    public void room_type_is(String roomType) {
        xnetAriService.addRoomType(roomType);
    }

    @Given("^total inventory available is (-?\\d+)$")
    public void total_inventory_available(int totalInventoryAvailable) {
        xnetAriService.setTotalInventoryAvailable(totalInventoryAvailable);
    }

    /**
     * sets the ratePlan
     *
     * @param ratePlan
     */
    @Given("^rate plan is (.*)$")
    public void rate_plan_is(String ratePlan) {
        xnetAriService.setRatePlan(ratePlan);
    }

    @Given("^sold out is (Active|Inactive)$")
    public void sold_out_is(String soldOut) {
        if (soldOut.contains("Active")) {
            xnetAriService.setSoldOut(true);
        }
        else if (soldOut.contains("Inactive")) {
            xnetAriService.setSoldOut(false);
        }
    }

    @Given("^closed to arrival is (Active|Inactive)$")
    public void closed_to_arrival_is(String closedToArrival) {
        if (closedToArrival.contains("Active")) {
            xnetAriService.setClosedToArrival(true);
        }
        else if (closedToArrival.contains("Inactive")) {
            xnetAriService.setClosedToArrival(false);
        }
    }

    @Given("^extra person fee is (\\d*)$")
    public void extra_person_fees_is(int extraPersonFees) {
        xnetAriService.setExtraPersonFee(extraPersonFees);
    }

    @Given("^xnet currency is (.*)$")
    public void currency_is(String currency) {
        xnetAriService.setCurrency(currency);
    }

    @Given("^rate per day is ([0-9]*\\.?[0-9]+)$")
    public void enter_ratePerDay(float ratePerday) {
        xnetAriService.setPerDayRate(ratePerday);
    }

    @When("^I update Inventory$")
    public void i_update_Inventory() {
        try {
            xnetAriService.updateInventory();
        }
        catch (Exception e) {
            exception = e;
        }
    }

    @Then("^I should get (.*) error$")
    public void i_should_get_success_or_failure(String successFailure) {
        xnetAriService.verifyUpdateInventory(successFailure, exception);

    }
}

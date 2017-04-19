/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search;

import com.hotwire.test.steps.common.AbstractSteps;


import com.hotwire.test.steps.purchase.AggregatedAbstractPurchase.ProductType;
import com.hotwire.test.steps.purchase.AggregatedAbstractPurchase.HotwirePages;
import com.hotwire.test.steps.purchase.AggregatedSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.car.AggregatedCarPurchase;
import com.hotwire.test.steps.purchase.hotel.AggregatedHotelPurchase;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.transformer.jchronic.ChronicConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * Created by v-ikomarov on 12/12/2014.
 */
public class AggregatedSearchSteps extends AbstractSteps {

    @Autowired
    public AggregatedSteps aggregatedSteps;

    @Autowired
    public AggregatedCarPurchase aggregatedCarPurchase;

    @Autowired
    public AggregatedHotelPurchase aggregatedHotelPurchase;

    @Given("^Make default (opaque|retail|any) car purchase with (.*) location between (.*)" +
            "  and (.*) and stop on billing")
    public void defaultCarPurchaseWithCarType(String carType, String location,
                                              @Transform(ChronicConverter.class)Calendar startDate,
                                              @Transform(ChronicConverter.class)Calendar endDate) {


        //AggregatedCarPurchase carPurchaseAggregated = new AggregatedCarPurchase(aggregatedSteps);
        aggregatedCarPurchase
                .setLocation(location)
                .setSearchDates(startDate, endDate)
               // .setOneWayLocations("SFO", "LAX")
                .setSearchTimes("1:00pm", "5:00pm")
                .setCarType(ProductType.OPAQUE)
                .setStartPage(HotwirePages.LANDING)
                .setEndPage(HotwirePages.BILLING)
                .run();

    }

    @Given("^Make default (opaque|retail|any) aggregated car purchase from billing")
    public void defaultCarPurchaseFromResults(String carType) {

//        AggregatedCarPurchase carPurchaseAggregated = new AggregatedCarPurchase(aggregatedSteps);
        aggregatedCarPurchase
                .setStartPage(HotwirePages.BILLING)
                .setEndPage(HotwirePages.CONFIRMATION)
                .setInsurance(true)
                .setPaymentMethod(PurchaseParameters.PaymentMethodType.CreditCard)
                .run();

    }


    @Given("^Make new car purchase$")
    public void defaultNewCarPurchase() {
        AggregatedCarPurchase carPurchaseAggregated =  new AggregatedCarPurchase(aggregatedSteps);
        carPurchaseAggregated
                .run();

    }

    @Given("^Make default opaque hotel purchase with SFO location$")
    public void defaultHotelPurchase() {
       // AggregatedHotelPurchase aggregatedHotelPurchase = new AggregatedHotelPurchase(aggregatedSteps);
        aggregatedHotelPurchase
                .setNumberOfAdults(3)
                .setNumberOfChildren(4)
                .setNumberOfRooms(3)
                .setGuestPurchase(true) //we will not login on billing
                .setPaymentMethod(PurchaseParameters.PaymentMethodType.CreditCard)
                .setStartPage(HotwirePages.LANDING)
                .setHotelType(ProductType.OPAQUE)
                .setInsurance(false)
                .setLocation("SFO")
                .run();
    }



    @Given("^I make new (opaque|retail|any) car purchase with (.*) location " +
            "and stop on (billing|results|confirmation)$")
    public void carPurchase(String carType, String location, String endPage) {
        AggregatedCarPurchase carPurchase = new AggregatedCarPurchase(aggregatedSteps);
        carPurchase
                .setLocation(location)
                .setCarType(carType)
                .setInsurance(false)
                .setEndPage(endPage)
                .run();

    }

    @Given("^I make new (opaque|retail|any) car purchase " +
            "from (billing|results|landing|details) " +
            "with (.*) location " +
            "between (.*) and (.*) " +
            "and stop on (billing|results|confirmation)$")
    public void carPurchase(String carType, String startPage, String location,
                            @Transform(ChronicConverter.class) Calendar startDate,
                            @Transform(ChronicConverter.class) Calendar endDate,
                            String endPage) {
        AggregatedCarPurchase carPurchase = new AggregatedCarPurchase(aggregatedSteps);
        carPurchase
                .setLocation(location)
                .setSearchDates(startDate, endDate)
                .setStartPage(startPage)
                .setCarType(carType)
                .setInsurance(false)
                .setEndPage(endPage)
                .run();

    }

    @Given("^I complete car purchase from (results|details|billing|confirmation)$")
    public void completeCarPurchase(String startPage) {
        aggregatedCarPurchase
                .setStartPage(startPage)
                .setEndPage(HotwirePages.CONFIRMATION)
                .run();
    }

    @Given("^I have purchased a car as a registered customer (.*) and (.*) and (.*)$")
    public void carPurchase(@Transform(ChronicConverter.class) Calendar startDate,
                              @Transform(ChronicConverter.class) Calendar endDate, String location) {
        aggregatedCarPurchase
                .setLocation(location)
                .setSearchDates(startDate, endDate)
                .setLoginWithRandomUser(true)
                .run();
    }

    @When("^I attempt to purchase a car with identical search parameters and same traveler's name$")
    public void makePurchaseWithSameParams() {
        aggregatedCarPurchase
                .setLoginWithRandomUser(false)
                .setEndPage(HotwirePages.AFTERBILLING)
                .run();
    }

    @When("^I purchase another car with identical search parameters and NEW traveler's name$")
    public void makePurchaseWithIdenticalParamsAndDifferentNames() {
        aggregatedCarPurchase
                .setLoginWithRandomUser(false)
                .setDriversName("Another drivers name")
                .run();


    }

    @When("^I try to make car purchase from results$")
    public  void test() {
        aggregatedCarPurchase.setStartPage(HotwirePages.RESULTS).run();
    }

}

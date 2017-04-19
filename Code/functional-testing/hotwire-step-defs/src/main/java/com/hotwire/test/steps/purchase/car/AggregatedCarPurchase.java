/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.car;

import com.hotwire.test.steps.purchase.AggregatedAbstractPurchase;
import com.hotwire.test.steps.purchase.AggregatedSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters.PaymentMethodType;
import com.hotwire.testing.UnimplementedTestException;


import java.util.Calendar;


/**
 * Created by v-ikomarov on 1/23/2015.
 * AggregatedCarPurchase
 */
public final class AggregatedCarPurchase extends AggregatedAbstractPurchase {

    private static final String VERTICAL = "Cars";
    private static final String RANDOM_LOCATION = "CITY";   //random city location
    private AggregatedSteps aggregatedSteps;
    private HotwirePages startPage;
    private HotwirePages endPage;
    private ProductType carType;
    private PaymentMethodType paymentMethod;
    private Boolean loginWithRandomUser;
    private String location;
    private Calendar startDate;
    private Calendar endDate;
    private String pickUpLocation;
    private String dropOffLocation;
    private String pickUpTime;
    private String dropOffTime;
    private String driversAge;
    private String countryOfResidence;
    private Boolean insurance;
    private String driversName;


    public AggregatedCarPurchase(AggregatedSteps aggregatedSteps) {
        this.aggregatedSteps = aggregatedSteps;
    }

    @Override
    public AggregatedSteps getAggregatedSteps() {
        return aggregatedSteps;
    }

    public AggregatedCarPurchase setStartPage(HotwirePages startPage) {
        this.startPage = startPage;
        return this;
    }
    public AggregatedCarPurchase setStartPage(String startPage) {
        this.startPage = HotwirePages.validate(startPage);
        return this;
    }

    public AggregatedCarPurchase setEndPage(HotwirePages endPage) {
        this.endPage = endPage;
        return this;
    }

    public AggregatedCarPurchase setEndPage(String  endPage) {
        this.endPage = HotwirePages.validate(endPage);
        return this;
    }

    @Override
    public HotwirePages getStartPage() {
        return startPage;
    }

    @Override
    public HotwirePages getEndPage() {
        return endPage;
    }

    public AggregatedCarPurchase setSearchDates(Calendar startDate, Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        return this;
    }

    public AggregatedCarPurchase setLoginWithRandomUser(Boolean loginWithRandomUser) {
        this.loginWithRandomUser = loginWithRandomUser;
        return this;
    }

    public AggregatedCarPurchase setDriversName(String driversName) {
        this.driversName = driversName;
        return this;
    }


    @Override
    public void initializationDefaultPurchaseParameters() {
        aggregatedSteps.getCarSearchStep().setCarDestinationLocation(location == null ? RANDOM_LOCATION : location);

        if (null == startDate || null == endDate) {
            aggregatedSteps.getSearchStep().setRandomValidSearchDates();
        }
        else {
            aggregatedSteps.getSearchStep().setSearchDates(startDate, endDate);
        }

        if (null != pickUpLocation && null != dropOffLocation) {
            aggregatedSteps.getCarSearchStep().setOneWayLocations(pickUpLocation, dropOffLocation);
        }

        if (insurance != null) {
            aggregatedSteps.getCarPurchaseInsuranceSteps().setInsuranceOption(insurance ? "" : "don't");
        }

        if (null != pickUpTime && null != dropOffTime) {
            aggregatedSteps.getCarSearchStep().setSearchTimes(pickUpTime, dropOffTime);
        }

        if (null != driversAge) {
            aggregatedSteps.getCarSearchStep().setDriversAge(driversAge);
        }

        if (null != countryOfResidence) {
            aggregatedSteps.getCarSearchStep().setCountryOfResidence(countryOfResidence);
        }
    }

    @Override
    protected boolean isLoginWithRandomUser() {
        return loginWithRandomUser;
    }

    public AggregatedCarPurchase setLocation(String location) {
        this.location = location;
        return this;
    }

    public AggregatedCarPurchase setCarType(String carType) {
        this.carType = ProductType.validate(carType);
        return this;
    }

    public AggregatedCarPurchase setCarType(ProductType carType) {
        this.carType = carType;
        return this;
    }

    public AggregatedCarPurchase setPaymentMethod(PaymentMethodType paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    @Override
    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    public AggregatedCarPurchase setInsurance(Boolean insurance) {
        this.insurance = insurance;
        return this;
    }

    public AggregatedCarPurchase setOneWayLocations(String pickUpLocation, String dropOffLocation) {
        this.pickUpLocation = pickUpLocation;
        this.dropOffLocation = dropOffLocation;
        return this;
    }

    public AggregatedCarPurchase setSearchTimes(String pickUpTime, String dropOffTime) {
        this.pickUpTime = pickUpTime;
        this.dropOffTime = dropOffTime;
        return this;
    }

    public AggregatedCarPurchase setDriversAge(String age) {
        this.driversAge = age;
        return this;
    }

    public AggregatedCarPurchase setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
        return this;
    }


    //-----------------------------------------------------------------------------------------------
    @Override
    public void goToLandingPage() {
        aggregatedSteps.getPartnersStep().clickVerticalTab(VERTICAL);
    }


    @Override
    public void processDetailsPage() {
        aggregatedSteps.getCarPurchaseSteps().goFromDetailsToBilling();
    }

    @Override
    public void processResultsPage() {
        aggregatedSteps.getSearchStep().verifyIfResultsExistOnPageOrNot("-non");
        aggregatedSteps.getCarPurchaseSteps().selectFirstCarResult(carType.getOpacity(), "stay on details");
    }

    @Override
    public void processBillingPage() {
        aggregatedSteps.getCarPurchaseSteps().setPaymentMethod(paymentMethod);
        if (null != driversName) {
            aggregatedSteps.getCarPurchaseSteps().changeDriverNames(driversName);
        }
        aggregatedSteps.getCarPurchaseSteps().processAllBillingInformation(false);
        aggregatedSteps.getPurchaseStep().clickAgreeAndBookButton(null);
    }

    @Override
    public void processBillingTravelerInfo() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void processBillingInsurance() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void processBillingCreditCardInfo() {
        throw new UnimplementedTestException("Implement me!!!");
    }

    @Override
    public void processBillingAgreeAndTerms() {
        throw new UnimplementedTestException("Implement me!!!");
    }

}

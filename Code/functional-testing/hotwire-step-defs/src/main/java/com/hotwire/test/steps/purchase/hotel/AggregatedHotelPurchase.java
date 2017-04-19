/*
* Copyright 2015 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/
package com.hotwire.test.steps.purchase.hotel;

import com.hotwire.test.steps.purchase.AggregatedAbstractPurchase;
import com.hotwire.test.steps.purchase.AggregatedSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters.PaymentMethodType;
import java.util.Calendar;

/**
* Created by v-ikomarov on 1/27/2015.
*/
public class AggregatedHotelPurchase extends AggregatedAbstractPurchase {
    private static final String VERTICAL = "Hotels";
    private AggregatedSteps aggregatedSteps;
    private HotwirePages startPage;
    private HotwirePages endPage;
    private String location;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private Integer numberOfRooms;
    private ProductType hotelType;
    private Boolean guestPurchase;
    private PaymentMethodType paymentMethod;
    private Boolean loginWithRandomUser;
    private Calendar startDate;
    private Calendar endDate;

    public AggregatedHotelPurchase(AggregatedSteps aggregatedSteps) {
        this.aggregatedSteps = aggregatedSteps;
    }

    public AggregatedHotelPurchase setSearchDates(Calendar startDate, Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        return this;
    }

    public AggregatedHotelPurchase setLoginWithRandomUser(Boolean loginWithRandomUser) {
        this.loginWithRandomUser = loginWithRandomUser;
        return this;
    }

    public AggregatedHotelPurchase setStartPage(HotwirePages startPage) {
        this.startPage = startPage;
        return this;
    }

    public AggregatedHotelPurchase setEndPage(HotwirePages endPage) {
        this.endPage = endPage;
        return this;
    }

    public AggregatedHotelPurchase setLocation(String location) {
        this.location = location;
        return this;
    }

    public AggregatedHotelPurchase setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
        return this;
    }

    public AggregatedHotelPurchase setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
        return this;

    }

    public AggregatedHotelPurchase setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
        return this;
    }

    public AggregatedHotelPurchase setHotelType(ProductType hotelType) {
        this.hotelType = hotelType;
        return this;
    }

    public AggregatedHotelPurchase setHotelType(String hotelType) {
        this.hotelType = ProductType.validate(hotelType);
        return this;
    }

    public AggregatedHotelPurchase setInsurance(Boolean insurance) {
        aggregatedSteps.getCarPurchaseInsuranceSteps().setInsuranceOption(insurance ? "" : "don't");
        return this;
    }

    public AggregatedHotelPurchase setPaymentMethod(PaymentMethodType paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public AggregatedHotelPurchase setGuestPurchase(Boolean guestPurchase) {
        this.guestPurchase = guestPurchase;
        return this;
    }

    @Override
    public AggregatedSteps getAggregatedSteps() {
        return aggregatedSteps;
    }

    @Override
    public HotwirePages getStartPage() {
        return startPage;
    }

    @Override
    public HotwirePages getEndPage() {
        return endPage;
    }

    @Override
    public void goToLandingPage() {
        aggregatedSteps.getPartnersStep().clickVerticalTab(VERTICAL);
    }

    @Override
    protected boolean isLoginWithRandomUser() {
        return loginWithRandomUser;
    }

    @Override
    public PaymentMethodType getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public void initializationDefaultPurchaseParameters() {
        aggregatedSteps.getHotelSearchSteps().setHotelDestinationLocation(location);
        if (startDate == null && endDate == null) {
            aggregatedSteps.getSearchStep().setRandomValidSearchDates();
        }
        else {
            aggregatedSteps.getSearchStep().setSearchDates(startDate, endDate);
        }
        if (numberOfChildren != null) {
            aggregatedSteps.getHotelSearchSteps().setNumberOfChildren(numberOfChildren);
        }
        if (numberOfRooms != null) {
            aggregatedSteps.getHotelSearchSteps().setNumberOfHotelRooms(numberOfRooms);
        }
        if (numberOfAdults != null) {
            aggregatedSteps.getHotelSearchSteps().setNumberOfAdults(numberOfAdults);
        }
        if (paymentMethod != null) {
            aggregatedSteps.getPurchaseStep().setPaymentMethod(getPaymentMethod());
        }

    }

    //---processing pages----------
    @Override
    public void processResultsPage() {
       // aggregatedSteps.getSearchStep().verifyIfResultsExistOnPageOrNot("-non");
        aggregatedSteps.getHotelSearchSteps().chooseHotelsResultsByType(hotelType.getOpacity());
        //aggregatedSteps.getHotelSearchSteps().verifySearchResultsPage(hotelType);
        aggregatedSteps.getHotelPurchaseSteps().chooseHotelResult();
    }



    @Override
    public void processDetailsPage() {
        aggregatedSteps.getHotelPurchaseSteps().continueToBillingFromDetails();
    }

    @Override
    public void processBillingTravelerInfo() {
        if (guestPurchase) {
            aggregatedSteps.getHotelPurchaseSteps().fillTravelerInfoForGuestUser();
        }
        else {
            aggregatedSteps.getHotelPurchaseSteps().fillTravelerInfoForLoggedUser();
        }
    }

    @Override
    public void processBillingInsurance() {
        aggregatedSteps.getHotelPurchaseSteps().processInsuranceOnBillingPage();
    }

    @Override
    public void processBillingCreditCardInfo() {
        aggregatedSteps.getHotelPurchaseSteps().processPaymentByType(getPaymentMethod());
        aggregatedSteps.getHotelPurchaseSteps().saveBillingTotalInBean();
    }

    @Override
    public void processBillingAgreeAndTerms() {
        aggregatedSteps.getHotelPurchaseSteps().processAgreeAndTerms();
    }

    @Override
    public void processBillingPage() {
        throw new RuntimeException(">>>>Deprecated");
    }
}



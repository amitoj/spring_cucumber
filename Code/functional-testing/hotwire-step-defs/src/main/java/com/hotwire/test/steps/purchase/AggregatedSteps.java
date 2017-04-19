/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase;

import com.hotwire.test.steps.account.AccountAccessSteps;
import com.hotwire.test.steps.authentication.AuthenticationSteps;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.partners.PartnersSteps;
import com.hotwire.test.steps.purchase.car.CarPurchaseSteps;


import com.hotwire.test.steps.purchase.car.steps.CarPurchaseInsuranceSteps;
import com.hotwire.test.steps.purchase.hotel.HotelPurchaseSteps;
import com.hotwire.test.steps.purchase.paypal.PayPalPurchaseSteps;
import com.hotwire.test.steps.search.SearchSteps;
import com.hotwire.test.steps.search.car.CarSearchSteps;
import com.hotwire.test.steps.search.hotel.HotelSearchSteps;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by v-ikomarov on 12/12/2014.
 */
public class AggregatedSteps extends AbstractSteps {


    private CarPurchaseSteps carPurchaseSteps;
    private CarSearchSteps carSearchStep;
    private SearchSteps searchStep;
    private PurchaseSteps purchaseStep;
    private PartnersSteps partnersStep;
    private CarPurchaseInsuranceSteps carPurchaseInsuranceSteps;
    private HotelSearchSteps hotelSearchSteps;
    private HotelPurchaseSteps hotelPurchaseSteps;
    private PayPalPurchaseSteps  payPalPurchaseSteps;
    private AuthenticationSteps authenticationSteps;
    private AccountAccessSteps accountAccessSteps;

    @Autowired
    public void setCarPurchaseSteps(CarPurchaseSteps carPurchaseSteps) {
        this.carPurchaseSteps = carPurchaseSteps;
    }

    public CarPurchaseSteps getCarPurchaseSteps() {
        return carPurchaseSteps;
    }

    @Autowired
    public void setCarSearchStep(CarSearchSteps carSearchStep) {
        this.carSearchStep = carSearchStep;
    }

    public CarSearchSteps getCarSearchStep() {
        return carSearchStep;
    }

    @Autowired
    public void setSearchStep(SearchSteps searchStep) {
        this.searchStep = searchStep;
    }

    public SearchSteps getSearchStep() {
        return searchStep;
    }

    @Autowired
    public void setPurchaseStep(PurchaseSteps purchaseStep) {
        this.purchaseStep = purchaseStep;
    }

    public PurchaseSteps getPurchaseStep() {
        return purchaseStep;
    }

    @Autowired
    public void setPartnersStep(PartnersSteps partnersStep) {
        this.partnersStep = partnersStep;
    }

    public PartnersSteps getPartnersStep() {
        return partnersStep;
    }

    @Autowired
    public void setCarPurchaseInsuranceSteps(CarPurchaseInsuranceSteps carPurchaseInsuranceSteps) {
        this.carPurchaseInsuranceSteps = carPurchaseInsuranceSteps;
    }

    public CarPurchaseInsuranceSteps getCarPurchaseInsuranceSteps() {
        return carPurchaseInsuranceSteps;
    }

    @Autowired
    public void setHotelSearchSteps(HotelSearchSteps hotelSearchSteps) {
        this.hotelSearchSteps = hotelSearchSteps;
    }

    public HotelSearchSteps getHotelSearchSteps() {
        return hotelSearchSteps;
    }


    public HotelPurchaseSteps getHotelPurchaseSteps() {
        return hotelPurchaseSteps;
    }

    @Autowired
    public void setHotelPurchaseSteps(HotelPurchaseSteps hotelPurchaseSteps) {
        this.hotelPurchaseSteps = hotelPurchaseSteps;
    }

    public PayPalPurchaseSteps getPayPalPurchaseSteps() {
        return payPalPurchaseSteps;
    }

    @Autowired
    public void setPayPalPurchaseSteps(PayPalPurchaseSteps payPalPurchaseSteps) {
        this.payPalPurchaseSteps = payPalPurchaseSteps;
    }

    public AuthenticationSteps getAuthenticationSteps() {
        return authenticationSteps;
    }

    @Autowired
    public void setAuthenticationSteps(AuthenticationSteps authenticationSteps) {
        this.authenticationSteps = authenticationSteps;
    }

    public AccountAccessSteps getAccountAccessSteps() {
        return accountAccessSteps;
    }

    @Autowired
    public void setAccountAccessSteps(AccountAccessSteps accountAccessSteps) {
        this.accountAccessSteps = accountAccessSteps;
    }
}

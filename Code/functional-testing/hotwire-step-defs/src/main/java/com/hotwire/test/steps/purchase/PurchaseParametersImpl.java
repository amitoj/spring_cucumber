/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase;

import java.util.List;

import com.hotwire.test.steps.account.ParticipantInformation;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.search.air.PassengerData;

/**
 * This class provides parameters to select and buy a pgood.
 * @author Alok Gupta
 * @since 2012.04
 */
public class PurchaseParametersImpl implements PurchaseParameters {
    private Integer resultNumberToSelect;
    private UserInformation userInformation;
    private boolean optForInsurance;
    private boolean skipOptForInsurance;
    private String opaqueRetail;
    private Boolean isPrepaidSolution;
    private PaymentMethodType paymentMethodType;
    private boolean shouldSetState;
    private String priceTypeToSelect;
    private String priceConditionToSelect;
    private float costToSelect;
    private String currencyCode;
    private String displayNumber;
    private String itinerary;
    private List<PassengerData> passengerList;
    private List<String>listADAAmenities;
    private String billingTotal;
    private String insuranceTotal;
    private CreditCard savedCreditCardName;
    private String promoCode;

    @Override
    public String getItinerary() {
        return itinerary;
    }

    @Override
    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    @Override
    public String getBillingTotal() {
        return billingTotal;
    }

    @Override
    public void setBillingTotal(String billingTotal) {
        this.billingTotal = billingTotal;
    }

    @Override
    public List<String> getListADAAmenities() {
        return listADAAmenities;
    }

    @Override
    public void setListADAAmenities(List<String> listADAAmenities) {
        this.listADAAmenities = listADAAmenities;
    }


    @Override
    public boolean isShouldSetState() {
        return this.shouldSetState;
    }

    @Override
    public void setShouldSetState(boolean shouldSetState) {
        this.shouldSetState = shouldSetState;
    }

    @Override
    public Integer getResultNumberToSelect() {
        return this.resultNumberToSelect;
    }

    @Override
    public void setResultNumberToSelect(Integer resultNumberToSelect) {
        this.resultNumberToSelect = resultNumberToSelect;
    }

    @Override
    public String getPriceTypeToSelect() {
        return this.priceTypeToSelect;
    }

    @Override
    public void setPriceTypeToSelect(String priceType) {
        this.priceTypeToSelect = priceType;
    }

    @Override
    public String getPriceConditionToSelect() {
        return this.priceConditionToSelect;
    }

    @Override
    public void setPriceConditionToSelect(String condition) {
        this.priceConditionToSelect = condition;
    }

    @Override
    public float getCostToSelect() {
        return costToSelect;
    }

    @Override
    public void setCostToSelect(float cost) {
        this.costToSelect = cost;
    }

    @Override
    public UserInformation getUserInformation() {
        return this.userInformation;
    }

    @Override
    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    @Override
    public boolean getOptForInsurance() {
        return this.optForInsurance;
    }

    @Override
    public void setOptForInsurance(boolean optForInsurance) {
        this.optForInsurance = optForInsurance;
    }

    @Override
    public void skipOptForInsurance() {
        this.skipOptForInsurance = true;
    }

    @Override
    public boolean isSkippedOptForInsurance() {
        return skipOptForInsurance;
    }

    @Override
    public String getOpaqueRetail() {
        return this.opaqueRetail;
    }

    @Override
    public void setOpaqueRetail(String opaqueRetail) {
        if (opaqueRetail.equals("any")) {
            this.opaqueRetail = "retail"; //to avoid filling of billing info
        }
        else {
            this.opaqueRetail = opaqueRetail.replaceAll("[^a-z]", "").toLowerCase();
        }

    }

    @Override
    public Boolean isPrepaidSolution() {
        return this.isPrepaidSolution;
    }

    @Override
    public void setIsPrepaidSolution(Boolean prepaidSolution) {
        this.isPrepaidSolution = prepaidSolution;
    }

    @Override
    public PaymentMethodType getPaymentMethodType() {
        return this.paymentMethodType;
    }

    @Override
    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    @Override
    public void setParticipantInformation(ParticipantInformation participant) {
        this.userInformation.setParticipantInformation(participant);
    }

    @Override
    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public void setDisplayNumber(String number) {
        this.displayNumber = number;
    }

    @Override
    public String getDisplayNumber() {
        return this.displayNumber;
    }

    @Override
    public List<PassengerData> getPassengerList() {
        return passengerList;
    }

    @Override
    public void setPassengerList(List<PassengerData> passengerList) {
        this.passengerList = passengerList;
    }

    @Override
    public String getInsuranceTotal() {
        return insuranceTotal;
    }

    @Override
    public void setInsuranceTotal(String insuranceTotal) {
        this.insuranceTotal = insuranceTotal;
    }

    @Override
    public CreditCard getSavedCreditCard() {
        return savedCreditCardName;
    }

    public void setSavedCreditCard(CreditCard savedCreditCardName) {
        this.savedCreditCardName = savedCreditCardName;
    }

    @Override
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @Override
    public String getPromoCode() {
        return promoCode;
    }
}

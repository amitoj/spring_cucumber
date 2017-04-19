/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase;

import java.util.ArrayList;
import java.util.List;

import com.hotwire.test.steps.account.ParticipantInformation;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.search.air.PassengerData;

/**
 * This interface provides parameters to select and buy a pgood.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public interface PurchaseParameters {

    /**
     * Types of accessible payment methods
     */
    enum PaymentMethodType {
        CreditCard("creditCard"),
        PayPal("payPal"),
        HotDollars("hotDollars"),
        V_ME(""),
        SavedCreditCard("CREDIT_CARD"),
        SavedVisa("savedVisa"),
        SavedMasterCard("savedMasterCard"),
        none("");

        private final String text;

        PaymentMethodType(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static PaymentMethodType fromString(String text) {
            if (text != null) {
                for (PaymentMethodType b : PaymentMethodType.values()) {
                    if (text.equalsIgnoreCase(b.text)) {
                        return b;
                    }
                }
            }
            return null;
        }
    }

    /**
     * Credit cards. contains all existed names for the credit cards
     */
    enum CreditCard {
        VISA1111("Visa1111, Visa-1111, Visa ending in 1111"),
        VISA1103("Visa1103, Visa-1103, Visa ending in 1103"),
        VISA1129("Visa1129, Visa-1129, Visa ending in 1129");


        private String cardNames;

        CreditCard(String cardNames) {
            this.cardNames = cardNames;
        }

        public List<String> getNames() {
            List<String> listOfNames = new ArrayList<String>();
            for (String name : cardNames.split(",")) {
                listOfNames.add(name);
            }
            return listOfNames;
        }
    }

    Integer getResultNumberToSelect();

    void setResultNumberToSelect(Integer resultNumberToSelect);

    UserInformation getUserInformation();

    void setUserInformation(UserInformation userInformation);

    boolean getOptForInsurance();

    void setOptForInsurance(boolean optForInsurance);

    void skipOptForInsurance();

    boolean isSkippedOptForInsurance();

    void setOpaqueRetail(String opaqueRetail);

    String getOpaqueRetail();

    Boolean isPrepaidSolution();

    void setIsPrepaidSolution(Boolean isPrepaidSolution);

    PaymentMethodType getPaymentMethodType();

    void setPaymentMethodType(PaymentMethodType paymentMethodType);

    void setShouldSetState(boolean shouldSetState);

    boolean isShouldSetState();

    void setParticipantInformation(ParticipantInformation participant);

    String getPriceTypeToSelect();

    void setPriceTypeToSelect(String priceType);

    String getPriceConditionToSelect();

    void setPriceConditionToSelect(String condition);

    float getCostToSelect();

    void setCostToSelect(float cost);

    String getCurrencyCode();

    void setCurrencyCode(String currencyCode);

    String getDisplayNumber();

    void setDisplayNumber(String number);

    String getItinerary();

    void setItinerary(String itinerary);

    List<String> getListADAAmenities();

    void setListADAAmenities(List<String> listADAAmenities);

    List<PassengerData> getPassengerList();

    void setPassengerList(List<PassengerData> passengerList);

    String getBillingTotal();

    void setBillingTotal(String billingTotal);

    String getInsuranceTotal();

    void setInsuranceTotal(String insuranceTotal);

    CreditCard getSavedCreditCard();

    void setPromoCode(String promoCode);

    String getPromoCode();
}





